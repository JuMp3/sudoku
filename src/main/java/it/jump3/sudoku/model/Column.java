package it.jump3.sudoku.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class Column implements Serializable {
    private Integer cell;
    private boolean disabled;
    private boolean correct;
}
