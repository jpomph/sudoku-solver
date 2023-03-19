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

        if(processBoard(currentBoard)){
            System.out.println("BOARD SOLVED!!!");
        } else {
            System.out.println("BOARD COULD NOT BE SOLVED!!!");
        }

    }

    public static boolean processBoard(Board board) throws Exception{

        List<UnoccupiedSquare> unoccupiedSquareList = processToEndOrStuck(board);

        if(Utils.isBoardFinished(board)){
            return true;
        }

        List<UnoccupiedSquare> unoccupiedSquaresWith2Possibilities = new ArrayList();
        for(UnoccupiedSquare unoccupiedSquare: unoccupiedSquareList){
            if (unoccupiedSquare.possibleValues.size()==2){
                unoccupiedSquaresWith2Possibilities.add(unoccupiedSquare);
            }
        }

        if(unoccupiedSquaresWith2Possibilities.size()>0){
            int rowToTry = unoccupiedSquaresWith2Possibilities.get(0).row;
            int columnToTry = unoccupiedSquaresWith2Possibilities.get(0).column;
            int number1ToTry = unoccupiedSquaresWith2Possibilities.get(0).possibleValues.get(0);
            int number2ToTry = unoccupiedSquaresWith2Possibilities.get(0).possibleValues.get(1);
            //try with number 1
            Board temporaryBoard = Utils.copyBoard(board);
            temporaryBoard.updateNumber(number1ToTry, rowToTry, columnToTry);
            if(processBoard(temporaryBoard)){
                board = Utils.copyBoard(temporaryBoard);
                return true;
            } else {
                //try with number 2
                temporaryBoard = Utils.copyBoard(board);
                temporaryBoard.updateNumber(number2ToTry, rowToTry, columnToTry);
                if(processBoard(temporaryBoard)){
                    board = Utils.copyBoard(temporaryBoard);
                    return true;
                }
            }

        }

        return false;

    }

    public static List<UnoccupiedSquare> processToEndOrStuck(Board board) throws Exception{
        boolean moreProcessing = true;

        while(moreProcessing){
            boolean changeMadeProcess1 = processRowsColumnsAndSquares(board);
            boolean changeMadeProcess2 = SolutionUtils.tryToFillAllSpots(board);

            if(!changeMadeProcess1 && !changeMadeProcess2){
                moreProcessing = false;
            }

            if (!board.integrity){
                moreProcessing=false;
            }
        }

        return SolutionUtils.findPossibleValuesForAllSpots(board);

    }

}