package it.jump3.sudoku.controller;

import it.jump3.sudoku.model.SudokuAjaxOut;
import it.jump3.sudoku.model.SudokuTable;
import it.jump3.sudoku.sudoku.SudokuEngine;
import it.jump3.sudoku.sudoku.SudokuGenerator;
import it.jump3.sudoku.util.SudokuUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@RestController
@RequestMapping(value = "/rest/sudoku")
public class SudokuRestController extends BaseController {

    @Autowired
    private SudokuUtil sudokuUtil;

    @Autowired
    private SudokuEngine sudokuEngine;

    @Autowired
    private SudokuGenerator sudokuGenerator;

    @PostMapping(value = "/validate", produces = "application/json")
    public ResponseEntity<SudokuAjaxOut> validateAjax(@RequestBody SudokuTable sudokuTable, Locale locale) {

        SudokuAjaxOut sudokuAjaxOut = new SudokuAjaxOut();

        if (!sudokuUtil.isComplete(sudokuTable)) {
            sudokuAjaxOut.setMsg(getMsgFromBundle("validate.not-complete", locale));
            sudokuAjaxOut.setSolved(false);
            return new ResponseEntity<>(sudokuAjaxOut, HttpStatus.OK);
        }

        int[][] board = sudokuUtil.getMatrix(sudokuTable);
        int[][] boardSolved = sudokuGenerator.getCloneBoard(sudokuTable.getDifficulty(), sudokuTable.getBoardId());
        sudokuEngine.solve(boardSolved);

        sudokuAjaxOut.setSolved(sudokuUtil.validate(board, boardSolved));
        sudokuAjaxOut.setMsg(getMsgFromBundle(sudokuAjaxOut.isSolved() ? "validate.ok" : "validate.ko", locale));

        return new ResponseEntity<>(sudokuAjaxOut, HttpStatus.OK);
    }

    @PostMapping(value = "/helpMe", produces = "application/json")
    public ResponseEntity<SudokuTable> helpMe(@RequestBody SudokuTable sudokuTable) {

        //int[][] board = sudokuUtil.getMatrix(sudokuTable);
        int[][] boardSolved = sudokuGenerator.getCloneBoard(sudokuTable.getDifficulty(), sudokuTable.getBoardId());
        sudokuEngine.solve(boardSolved);

        if (!sudokuUtil.isComplete(sudokuTable)) {
            SudokuTable sudokuTableSolved = sudokuUtil.getSudokuTable(boardSolved);
            sudokuUtil.helpMe(sudokuTable, sudokuTableSolved);
        }

        return new ResponseEntity<>(sudokuTable, HttpStatus.OK);
    }
}