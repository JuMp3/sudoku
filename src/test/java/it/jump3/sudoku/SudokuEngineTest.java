package it.jump3.sudoku;

import it.jump3.sudoku.control.DancingLinksAlgorithm;
import it.jump3.sudoku.sudoku.SudokuEngine;
import it.jump3.sudoku.sudoku.SudokuGenerator;
import it.jump3.sudoku.util.SudokuUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SudokuEngineTest {

    @Autowired
    private DancingLinksAlgorithm dancingLinksAlgorithm;

    @Autowired
    private SudokuEngine sudokuEngine;

    @Autowired
    private SudokuUtil sudokuUtil;

    @Autowired
    private SudokuGenerator sudokuGenerator;

    // we define a simple grid to solve. Grid is stored in a 2D Array
    public static int[][] GRID_TO_SOLVE = {
            {9, 0, 0, 1, 0, 0, 0, 0, 5},
            {0, 0, 5, 0, 9, 0, 2, 0, 1},
            {8, 0, 0, 0, 4, 0, 0, 0, 0},
            {0, 0, 0, 0, 8, 0, 0, 0, 0},
            {0, 0, 0, 7, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 2, 6, 0, 0, 9},
            {2, 0, 0, 3, 0, 0, 0, 0, 6},
            {0, 0, 0, 2, 0, 0, 9, 0, 0},
            {0, 0, 1, 9, 0, 4, 5, 7, 0},
    };

    @Test
    public void dancingLinksAlgorithmTest() {
        dancingLinksAlgorithm.solve(GRID_TO_SOLVE);
    }

    @Test
    public void backtrackingAlgorithmTest1() {

        int[][] board = sudokuEngine.init(GRID_TO_SOLVE);
        System.out.println("Sudoku grid to solve");
        sudokuEngine.display(board);

        // we try resolution
        boolean solved = sudokuEngine.solve(board);
        Assert.assertTrue("Unsolvable", solved);

        System.out.println("Sudoku Grid solved with simple BT");
        sudokuEngine.display(board);
    }

    @Test
    public void generateRandom() {
        sudokuEngine.display(sudokuUtil.generateRandom());
    }

    @Test
    public void testGenerator() {
        sudokuGenerator.nextBoard(50);
        sudokuGenerator.print();
    }
}
