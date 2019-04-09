package it.jump3.sudoku.model;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class Row implements Serializable {

    private List<Column> columns;

    public Row() {
        columns = new ArrayList<>();
    }
}
