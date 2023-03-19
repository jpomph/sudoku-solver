package org.pomphrey.sudokusolver.controller;

import org.pomphrey.sudokusolver.model.Board;
import org.pomphrey.sudokusolver.utils.Utils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class WebController {
    @RequestMapping(value = "/home")
    public String index(Model model) {

        Board board = Utils.buildBoard();

        model.addAttribute("board", board);

        return "home";

    }

}
