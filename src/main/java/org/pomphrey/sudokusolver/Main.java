package org.pomphrey.sudokusolver;

import org.pomphrey.sudokusolver.model.*;
import org.pomphrey.sudokusolver.utils.SolutionUtils;
import org.pomphrey.sudokusolver.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import static org.pomphrey.sudokusolver.utils.SolutionUtils.processRowsColumnsAndSquares;

public class Main {
    public static void main(String[] args) throws Exception{

        Board currentBoard = Utils.buildBoard();

        System.out.println("BOARD AT START ");
        currentBoard.outputBoard();

        if(currentBoard.checkIntegrity()){
            System.out.println("BOARD INTEGRITY OK ");
        } else {
            return;
        }

        Board solvedBoard = SolutionUtils.processBoard(currentBoard);

        if(Utils.isBoardFinished(solvedBoard)){
            System.out.println("BOARD SOLVED!!!");
        } else {
            System.out.println("BOARD COULD NOT BE SOLVED!!!");
        }

    }


}