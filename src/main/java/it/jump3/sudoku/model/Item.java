package it.jump3.sudoku.model;

import lombok.Data;

@Data
public class Item {

    private ItemType itemType;
    private String name;
    private boolean imported;
    private Integer quantity;
    private Double price;
}
