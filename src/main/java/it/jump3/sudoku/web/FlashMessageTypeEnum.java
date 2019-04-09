package it.jump3.sudoku.web;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum FlashMessageTypeEnum {

    SUCCESS("SUCCESS"),
    WARNING("WARNING"),
    INFO("INFO"),
    DANGER("DANGER");

    @Getter
    private String value;

}
