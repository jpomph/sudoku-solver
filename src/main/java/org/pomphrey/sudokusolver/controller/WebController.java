package org.pomphrey.sudokusolver.controller;

import org.pomphrey.sudokusolver.model.Board;
import org.pomphrey.sudokusolver.model.BoardAsNumbers;
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

        model.addAttribute("boardAsNumbers", Utils.getBoardAsNumbers(board));
        model.addAttribute("message", "");


        return "home";

    }

    @RequestMapping(value = "/process", params={"update"})
    public String process(final BoardAsNumbers boardAsNumbers, final BindingResult bindingResult, Model model) {

        Board board = Utils.getBoardFromNumbers(boardAsNumbers);

        String message = "";

        if(board.integrity){
            message = "Board OK";
        } else {
            message = "Board does not have integrity!!!!";
        }

        model.addAttribute("message", message);

        return "home";

    }



}
