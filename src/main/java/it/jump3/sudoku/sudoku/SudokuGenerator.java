package it.jump3.sudoku.sudoku;

import it.jump3.sudoku.model.DifficultyEnum;
import it.jump3.sudoku.util.SudokuUtil;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;

@Data
@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class SudokuGenerator {

    @Autowired
    private SudokuUtil sudokuUtil;

    @Value(value = "${sudoku.num_boards}")
    private int numBoard;

    private Map<DifficultyEnum, List<int[][]>> boardMap;

    int[][] board;

    /**
     * Constructor.  Resets board to zeros
     */
    @PostConstruct
    private void init() {

        boardMap = new HashMap<>();

        for (DifficultyEnum difficultyEnum : DifficultyEnum.values()) {
            List<int[][]> boardList = new ArrayList<>();
            for (int cnt = 0; cnt < getNumBoard(); cnt++) {
                board = new int[SudokuEngine.SIZE][SudokuEngine.SIZE];
                int difficulty = sudokuUtil.getRandomNumber(difficultyEnum.getMinHoles() + 1, difficultyEnum.getMaxHoles() + 1);
                nextBoard(difficulty);
                boardList.add(board);
            }
            boardMap.put(difficultyEnum, boardList);
        }
    }

    /**
     * Driver method for nextBoard.
     *
     * @param difficulty the number of blank spaces to insert
     * @return board, a partially completed 9x9 Sudoku board
     */
    public int[][] nextBoard(int difficulty) {
        board = new int[SudokuEngine.SIZE][SudokuEngine.SIZE];
        nextCell(0, 0);
        makeHoles(difficulty);
        return board;

    }

    /**
     * Recursive method that attempts to place every number in a cell.
     *
     * @param x x value of the current cell
     * @param y y value of the current cell
     * @return true if the board completed legally, false if this cell
     * has no legal solutions.
     */
    public boolean nextCell(int x, int y) {
        int nextX = x;
        int nextY = y;
        int[] toCheck = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        Random r = new Random();
        int tmp = 0;
        int current = 0;
        int top = toCheck.length;

        for (int i = top - 1; i > 0; i--) {
            current = r.nextInt(i);
            tmp = toCheck[current];
            toCheck[current] = toCheck[i];
            toCheck[i] = tmp;
        }

        for (int i = 0; i < toCheck.length; i++) {
            if (legalMove(x, y, toCheck[i])) {
                board[x][y] = toCheck[i];
                if (x == SudokuEngine.SIZE - 1) {
                    if (y == SudokuEngine.SIZE - 1)
                        return true;//We're done!  Yay!
                    else {
                        nextX = 0;
                        nextY = y + 1;
                    }
                } else {
                    nextX = x + 1;
                }
                if (nextCell(nextX, nextY))
                    return true;
            }
        }
        board[x][y] = 0;
        return false;
    }

    /**
     * Given a cell's coordinates and a possible number for that cell,
     * determine if that number can be inserted into said cell legally.
     *
     * @param x       x value of cell
     * @param y       y value of cell
     * @param current The value to check in said cell.
     * @return True if current is legal, false otherwise.
     */
    private boolean legalMove(int x, int y, int current) {
        for (int i = 0; i < SudokuEngine.SIZE; i++) {
            if (current == board[x][i])
                return false;
        }
        for (int i = 0; i < SudokuEngine.SIZE; i++) {
            if (current == board[i][y])
                return false;
        }
        int cornerX = 0;
        int cornerY = 0;
        if (x > 2)
            if (x > 5)
                cornerX = 6;
            else
                cornerX = 3;
        if (y > 2)
            if (y > 5)
                cornerY = 6;
            else
                cornerY = 3;
        for (int i = cornerX; i < 10 && i < cornerX + 3; i++)
            for (int j = cornerY; j < 10 && j < cornerY + 3; j++)
                if (current == board[i][j])
                    return false;
        return true;
    }

    /**
     * Given a completed board, replace a given amount of cells with 0s
     * (to represent blanks)
     *
     * @param holesToMake How many 0s to put in the board.
     */
    public void makeHoles(int holesToMake) {
		/* We define difficulty as follows:
			Easy: 32+ clues (49 or fewer holes)
			Medium: 27-31 clues (50-54 holes)
			Hard: 26 or fewer clues (54+ holes)
			This is human difficulty, not algorighmically (though there is some correlation)
		*/
        double remainingSquares = SudokuEngine.SIZE * SudokuEngine.SIZE;
        double remainingHoles = (double) holesToMake;

        for (int i = 0; i < SudokuEngine.SIZE; i++)
            for (int j = 0; j < SudokuEngine.SIZE; j++) {
                double holeChance = remainingHoles / remainingSquares;
                if (Math.random() <= holeChance) {
                    board[i][j] = 0;
                    remainingHoles--;
                }
                remainingSquares--;
            }
    }

    /**
     * Prints a representation of board on stdout
     */
    public void print() {
        for (int i = 0; i < SudokuEngine.SIZE; i++) {
            for (int j = 0; j < SudokuEngine.SIZE; j++)
                System.out.print(board[i][j] + " ");
            System.out.println();
        }
    }

    public int[][] getBoard(DifficultyEnum difficultyEnum, int i) {

        assert i < getNumBoard();
        return boardMap.get(difficultyEnum).get(i);
    }

    public int[][] getCloneBoard(DifficultyEnum difficultyEnum, int i) {

        assert i < getNumBoard();
        return sudokuUtil.clone(boardMap.get(difficultyEnum).get(i));
    }
}
