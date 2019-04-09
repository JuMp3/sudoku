package it.jump3.sudoku.model;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class SudokuTable implements Serializable {

    private List<Row> rows;
    private String errMsg;
    private DifficultyEnum difficulty;
    private int boardId;

    public SudokuTable() {
        rows = new ArrayList<>();
    }
}
