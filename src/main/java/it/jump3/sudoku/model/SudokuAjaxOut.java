package it.jump3.sudoku.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class SudokuAjaxOut implements Serializable {

    private boolean solved;
    private String msg;
}
