package it.jump3.sudoku.controller;

import it.jump3.sudoku.model.DifficultyEnum;
import it.jump3.sudoku.model.SudokuTable;
import it.jump3.sudoku.sudoku.SudokuEngine;
import it.jump3.sudoku.sudoku.SudokuGenerator;
import it.jump3.sudoku.util.SudokuUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Locale;

@Controller
@RequestMapping(value = "/sudoku")
public class SudokuController extends BaseController {

    @Autowired
    private SudokuUtil sudokuUtil;

    @Autowired
    private SudokuEngine sudokuEngine;

    @Autowired
    private SudokuGenerator sudokuGenerator;

    private static final String HOME_PAGE = "/home";

    @GetMapping
    public String initSudoku(Model model, Locale locale) {
        return initSudoku(model, DifficultyEnum.MEDIUM);
    }

    @GetMapping("/easy")
    public String initSudokuEasy(Model model, Locale locale) {
        return initSudoku(model, DifficultyEnum.EASY);
    }

    @GetMapping("/medium")
    public String initSudokuMedium(Model model, Locale locale) {
        return initSudoku(model, DifficultyEnum.MEDIUM);
    }

    @GetMapping("/hard")
    public String initSudokuHard(Model model, Locale locale) {
        return initSudoku(model, DifficultyEnum.HARD);
    }

    private String initSudoku(Model model, DifficultyEnum difficultyEnum) {
        int boardId = sudokuUtil.getRandomNumber(0, SudokuGenerator.NUM_BOARDS);
        SudokuTable sudokuTable = sudokuUtil.getSudokuTable(sudokuGenerator.getBoard(difficultyEnum, boardId));
        sudokuTable.setDifficulty(difficultyEnum);
        sudokuTable.setBoardId(boardId);
        loadAttribute(model, null, sudokuTable);
        return HOME_PAGE;
    }

    @PostMapping("/validate")
    public String validate(@ModelAttribute("sudokuTable") SudokuTable sudokuTable, BindingResult bindingResult,
                           Model model, Locale locale, RedirectAttributes redirectAttributes) {

        if (!sudokuUtil.isComplete(sudokuTable)) {
            bindingResult.rejectValue("errMsg", "validate.not-complete");
            loadAttribute(model, null, sudokuTable);
            return HOME_PAGE;
        }

        int[][] boardInput = sudokuUtil.getMatrix(sudokuTable);
        int[][] board = sudokuGenerator.getCloneBoard(sudokuTable.getDifficulty(), sudokuTable.getBoardId());
        sudokuEngine.solve(board);

        model.addAttribute("solved", sudokuUtil.validate(boardInput, board));
        model.addAttribute("sudokuTable", sudokuTable);

        return HOME_PAGE;
    }

    private void loadAttribute(Model model, Boolean solved, SudokuTable sudokuTable) {
        //model.addAttribute("rows", sudokuTable.getRows());
        model.addAttribute("solved", solved != null ? solved.booleanValue() : false);
        model.addAttribute("sudokuTable", sudokuTable);
    }

    @PostMapping("/solve")
    public String solve(@ModelAttribute("sudokuTable") SudokuTable sudokuTable, Model model, Locale locale) {

        int[][] board = sudokuGenerator.getCloneBoard(sudokuTable.getDifficulty(), sudokuTable.getBoardId());
        sudokuEngine.solve(board);
        SudokuTable sudokuTableSolved = sudokuUtil.getSudokuTable(board);
        loadAttribute(model, Boolean.TRUE, sudokuTableSolved);

        return HOME_PAGE;
    }
}
