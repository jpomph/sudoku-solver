package org.pomphrey.sudokusolver.controller;

import org.pomphrey.sudokusolver.model.Board;
import org.pomphrey.sudokusolver.model.BoardAsNumbers;
import org.pomphrey.sudokusolver.utils.SolutionUtils;
import org.pomphrey.sudokusolver.utils.Utils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class WebController {
    @RequestMapping(value = "/home")
    public String initialise(Model model) {

        Board board = Utils.intitaliseBoard();

        model.addAttribute("startingBoard", Utils.getBoardAsNumbers(board));
        model.addAttribute("solvedBoard", Utils.getBoardAsNumbers(board));
        model.addAttribute("message", "");


        return "home";

    }

    @RequestMapping(value = "/process", params={"update"})
    public String update(@ModelAttribute("startingBoard") final BoardAsNumbers startingBoard,
                          @ModelAttribute("solvedBoard") final BoardAsNumbers solvedBoard,
                          final BindingResult bindingResult,
                          Model model) {

        Board board = Utils.getBoardFromNumbers(startingBoard);

        String message = "";

        if(board.integrity){
            message = "Board OK";
        } else {
            message = "Board does not have integrity!!!!";
        }

        model.addAttribute("message", message);

        return "home";

    }

    @RequestMapping(value = "/process", params={"solve"})
    public String solve(@ModelAttribute("startingBoard") final BoardAsNumbers startingBoard,
                          @ModelAttribute("solvedBoard") BoardAsNumbers solvedBoard,
                          final BindingResult bindingResult,
                          Model model) throws Exception{

        Board workingBoard = Utils.getBoardFromNumbers(startingBoard);

        if(SolutionUtils.processBoard(workingBoard)){
            model.addAttribute("message", "Board solved!");
        } else {
            model.addAttribute("message", "Board couldn't be solved :-(");
        }

        solvedBoard = Utils.getBoardAsNumbers(workingBoard);
        model.addAttribute("solvedBoard", solvedBoard);


        return "home";

    }

}
