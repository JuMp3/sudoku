package it.jump3.sudoku.model;

import lombok.Data;

import java.util.List;

@Data
public class OutputItem {

    private List<Item> items;
    private Double salesTaxesTot;
    private Double total;
}
