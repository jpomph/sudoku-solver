package org.pomphrey.sudokusolver.utils;

import org.pomphrey.sudokusolver.config.StartingBoard;
import org.pomphrey.sudokusolver.model.BoardAsNumbers;
import org.pomphrey.sudokusolver.model.Coordinate;
import org.pomphrey.sudokusolver.model.Board;
import org.pomphrey.sudokusolver.model.NumberSet;

import java.util.ArrayList;
import java.util.List;

public class Utils {

    public static Board buildBoard(){
        Board board = new Board();
        int row = -1;
        int column = -1;
        for(int i=0; i<81; i++){
            if(i%9==0){
                column = 0;
                row ++;
            } else {
                column ++;
            }
            board.setNumber(StartingBoard.numbers[i], row, column);
        }
        return board;
    }

    public static Board intitaliseBoard(){
        Board board = new Board();
        for(int row=0; row<9; row++) {
            for (int col = 0; col < 9; col++) {
                board.setNumber(0, row, col);
            }
        }
        return board;
    }

    public static List<Integer> whichNumbersAreMissing(NumberSet numberSet){
        List missingNumbers = new ArrayList();
        for (int i=0; i<9; i++) {
            boolean numberFound = false;
            for (int j=0; j<9; j++){
                if(i==numberSet.numbers.get(j)){
                    numberFound=true;
                }
            }
            if(numberFound==false){
                missingNumbers.add(i);
            }
        }
        return missingNumbers;
    }

    public static boolean isNumberMissing(NumberSet numberSet, Integer number){

        boolean missing = true;
        for (int i=0; i<9; i++){
            if (numberSet.numbers.get(i)==number){
                missing = false;
            }
        }
        return missing;
    }

    public static List<Coordinate> getCoordinatesForSquare(int squareNumber){
        int squareRow = 0;
        int squareColumn = 0;
        List<Coordinate> coordinates = new ArrayList<>();
        if (squareNumber==0){
            squareRow = 0;
            squareColumn = 0;
        }
        if (squareNumber==1){
            squareRow = 0;
            squareColumn = 1;
        }
        if (squareNumber==2){
            squareRow = 0;
            squareColumn = 2;
        }
        if (squareNumber==3){
            squareRow = 1;
            squareColumn = 0;
        }
        if (squareNumber==4){
            squareRow = 1;
            squareColumn = 1;
        }
        if (squareNumber==5){
            squareRow = 1;
            squareColumn = 2;
        }
        if (squareNumber==6){
            squareRow = 2;
            squareColumn = 0;
        }
        if (squareNumber==7){
            squareRow = 2;
            squareColumn = 1;
        }
        if (squareNumber==8){
            squareRow = 2;
            squareColumn = 2;
        }
        coordinates.add(new Coordinate((squareRow*3),(squareColumn*3)));
        coordinates.add(new Coordinate((squareRow*3),(squareColumn*3)+1));
        coordinates.add(new Coordinate((squareRow*3),(squareColumn*3)+2));
        coordinates.add(new Coordinate((squareRow*3)+1,(squareColumn*3)));
        coordinates.add(new Coordinate((squareRow*3)+1,(squareColumn*3)+1));
        coordinates.add(new Coordinate((squareRow*3)+1,(squareColumn*3)+2));
        coordinates.add(new Coordinate((squareRow*3)+2,(squareColumn*3)));
        coordinates.add(new Coordinate((squareRow*3)+2,(squareColumn*3)+1));
        coordinates.add(new Coordinate((squareRow*3)+2,(squareColumn*3)+2));

        return coordinates;
    }

    public static int getSquareFromCoordinates(int row, int column){
        int squareRow = row / 3;
        int squareColumn = column / 3;
        int squareNumber = (squareRow * 3) + squareColumn;
        return squareNumber;
    }

    public static boolean isBoardFinished(Board board){
        boolean finished = true;
        for (int row=0; row<9; row++){
            for (int col=0; col<9; col++){
                if(board.getNumber(row, col)==0){
                    finished = false;
                }
            }
        }
        return finished;
    }

    public static Board copyBoard(Board board){
        Board newBoard = new Board();
        for (int row=0; row<9; row++){
            for (int col=0; col<9; col++){
                newBoard.setNumber(board.getNumber(row,col),row,col);
            }
        }
        return newBoard;
    }

    public static boolean checkNumberSetIntegrity(NumberSet numberSet, String type, int id){
        boolean integrity = true;
        boolean[] numberFound = {false,false,false,false,false,false,false,false,false,false };
        for(int number: numberSet.numbers){
            if (number>0 && numberFound[number]==true){
                System.out.println("NUMBER " + number + " FOUND TWICE IN " + type + " " + id);
                integrity = false;
            } else {
                numberFound[number]=true;
            }
        }
        return integrity;
    }

    public static BoardAsNumbers getBoardAsNumbers(Board board){
        BoardAsNumbers boardAsNumbers = new BoardAsNumbers();
        for(int row=0; row<9; row++) {
            for (int col = 0; col < 9; col++) {
                boardAsNumbers.getNumbers().add(board.getNumber(row, col));
            }
        }
        return boardAsNumbers;
    }

    public static Board getBoardFromNumbers(BoardAsNumbers boardAsNumbers){
        Board board = new Board();
        int i = 0;
        for(int row=0; row<9; row++) {
            for (int col = 0; col < 9; col++) {
                board.setNumber(boardAsNumbers.getNumbers().get(i), row, col);
                i++;
            }
        }
        board.checkIntegrity();
        return board;
    }

}
