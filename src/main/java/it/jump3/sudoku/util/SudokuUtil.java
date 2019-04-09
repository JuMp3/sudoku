package it.jump3.sudoku.util;

import it.jump3.sudoku.model.Column;
import it.jump3.sudoku.model.Row;
import it.jump3.sudoku.model.SudokuTable;
import it.jump3.sudoku.sudoku.SudokuEngine;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@Component
public class SudokuUtil {

    public SudokuTable getSudokuTable(int[][] board) {

        SudokuTable sudokuTable = new SudokuTable();

        for (int i = 0; i < SudokuEngine.SIZE; i++) {
            Row row = new Row();
            for (int j = 0; j < SudokuEngine.SIZE; j++) {
                Column column = new Column();
                column.setCell(board[i][j]);
                row.getColumns().add(column);
            }
            sudokuTable.getRows().add(row);
        }

        return sudokuTable;
    }

    public int[][] getMatrix(SudokuTable sudokuTable) {

        if (!CollectionUtils.isEmpty(sudokuTable.getRows())) {
            int[][] board = new int[SudokuEngine.SIZE][SudokuEngine.SIZE];

            for (int i = 0; i < SudokuEngine.SIZE; i++) {
                for (int j = 0; j < SudokuEngine.SIZE; j++) {
                    board[i][j] = sudokuTable.getRows().get(i).getColumns().get(j).getCell();
                }
            }

            return board;
        }

        return null;
    }

    public boolean isComplete(SudokuTable sudokuTable) {

        int numTot = SudokuEngine.SIZE * SudokuEngine.SIZE;
        int count = 0;

        if (!CollectionUtils.isEmpty(sudokuTable.getRows())) {
            for (Row row : sudokuTable.getRows()) {
                for (Column column : row.getColumns()) {
                    if (!Integer.valueOf(SudokuEngine.EMPTY).equals(column.getCell())) {
                        count++;
                    }
                }
            }
        }

        return numTot == count;
    }

//    public int[][] getBoard(int id) {
//
//        int[][] board = null;
//
//        switch (id) {
//            case 1: {
//                // TODO rendere dinamico
//                board = new int[][]{
//                        {9, 0, 0, 1, 0, 0, 0, 0, 5},
//                        {0, 0, 5, 0, 9, 0, 2, 0, 1},
//                        {8, 0, 0, 0, 4, 0, 0, 0, 0},
//                        {0, 0, 0, 0, 8, 0, 0, 0, 0},
//                        {0, 0, 0, 7, 0, 0, 0, 0, 0},
//                        {0, 0, 0, 0, 2, 6, 0, 0, 9},
//                        {2, 0, 0, 3, 0, 0, 0, 0, 6},
//                        {0, 0, 0, 2, 0, 0, 9, 0, 0},
//                        {0, 0, 1, 9, 0, 4, 5, 7, 0},
//                };
//                break;
//            }
//            case 2: {
//                board = new int[][]{
//                        {3, 0, 6, 5, 0, 8, 4, 0, 0},
//                        {5, 2, 0, 0, 0, 0, 0, 0, 0},
//                        {0, 8, 7, 0, 0, 0, 0, 3, 1},
//                        {0, 0, 3, 0, 1, 0, 0, 8, 0},
//                        {9, 0, 0, 8, 6, 3, 0, 0, 5},
//                        {0, 5, 0, 0, 9, 0, 6, 0, 0},
//                        {1, 3, 0, 0, 0, 0, 2, 5, 0},
//                        {0, 0, 0, 0, 0, 0, 0, 7, 4},
//                        {0, 0, 5, 2, 0, 6, 3, 0, 0}
//                };
//                break;
//            }
//        }
//
//        return board;
//    }

    public int[][] generateRandom() {

        Random r = new Random();
        int i1 = r.nextInt(8);
        int firstval = i1;
        int x = firstval, v = 1;
        int a[][] = new int[SudokuEngine.SIZE][SudokuEngine.SIZE];
        for (int i = 0; i < SudokuEngine.SIZE; i++) {
            for (int j = 0; j < SudokuEngine.SIZE; j++) {
                if ((x + j + v) <= SudokuEngine.SIZE)
                    a[i][j] = j + x + v;
                else
                    a[i][j] = j + x + v - SudokuEngine.SIZE;
                if (a[i][j] == SudokuEngine.SIZE + 1)
                    a[i][j] = 1;
                // System.out.print(a[i][j]+" ");
            }
            x += 3;
            if (x >= SudokuEngine.SIZE)
                x = x - SudokuEngine.SIZE;
            // System.out.println();
            if (i == 2) {
                v = 2;
                x = firstval;
            }
            if (i == 5) {
                v = 3;
                x = firstval;
            }
        }
        return a;
    }

    public boolean validate(int[][] boardInput, int[][] boardSolved) {

        boolean validate = true;

        exitLoop:
        for (int i = 0; i < SudokuEngine.SIZE; i++) {
            for (int j = 0; j < SudokuEngine.SIZE; j++) {
                if (boardInput[i][j] != boardSolved[i][j]) {
                    validate = false;
                    break exitLoop;
                }
            }
        }

        return validate;
    }

    public void helpMe(int[][] boardInput, int[][] boardSolved) {

        boolean helped = false;

        while (!helped) {

            int i = getRandomNumber(0, SudokuEngine.SIZE);
            int j = getRandomNumber(0, SudokuEngine.SIZE);
            if (boardInput[i][j] == SudokuEngine.EMPTY && boardSolved[i][j] != SudokuEngine.EMPTY) {
                boardInput[i][j] = boardSolved[i][j];
                helped = true;
            }
        }
    }

    public int getRandomNumber(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max);
    }

    public int[][] clone(int[][] matrix) {
        int[][] myInt = new int[matrix.length][];
        for (int i = 0; i < matrix.length; i++) {
            myInt[i] = matrix[i].clone();
        }
        return myInt;
    }
}
