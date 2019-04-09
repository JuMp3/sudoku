package it.jump3.sudoku.controller;

import it.jump3.sudoku.util.SudokuUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Locale;

@Controller
public class DefaultController {

    @Autowired
    private SudokuUtil sudokuUtil;

    @GetMapping({"/", "/home"})
    public String home(Model model, Locale locale) {
        return "redirect:/sudoku";
    }

    @GetMapping({"/home/easy"})
    public String homeEasy(Model model, Locale locale) {
        return "redirect:/sudoku/easy";
    }

    @GetMapping({"/home/medium"})
    public String homeMedium(Model model, Locale locale) {
        return "redirect:/sudoku/medium";
    }

    @GetMapping({"/home/hard"})
    public String homeHard(Model model, Locale locale) {
        return "redirect:/sudoku/hard";
    }

    @GetMapping("favicon.ico")
    @ResponseBody
    void returnNoFavicon() {
    }
}
