package org.pomphrey.sudokusolver.utils;

import org.pomphrey.sudokusolver.model.*;

import java.util.ArrayList;
import java.util.List;

public class SolutionUtils {

    public static boolean processRowsColumnsAndSquares(Board board) throws Exception{

        boolean changeMade = false;

        for (int number = 0; number < 9; number++) {
            for (int row = 0; row < 9; row++) {
                if (Utils.isNumberMissing(board.rows.get(row), number)) {
                    //try to fit number in different positions on row
                    List<Integer> potentialPositions = new ArrayList();
                    for (int column = 0; column < 9; column++) {
                        boolean numberIsMissing = Utils.isNumberMissing(board.columns.get(column), number);
                        boolean boxIsFilled = board.rows.get(row).numbers.get(column)==0? false : true;
                        if (numberIsMissing && !boxIsFilled) {
                            potentialPositions.add(column);
                        }
                    }
                    if (potentialPositions.size()==1) {
                        board.outputBoard();
                        System.out.println("  Number " + number + " is missing from row " + row + " and can only be fitted into column " + potentialPositions.get(0));
                        board.updateNumber(number, row, potentialPositions.get(0));
                        changeMade = true;
                    }
                }
            }
            for (int column = 0; column < 9; column++) {
                if(Utils.isNumberMissing(board.columns.get(column),number)){
                    //try to fit number in different positions on column
                    List<Integer> potentialPositions = new ArrayList();
                    for (int row = 0; row < 9; row++) {
                        boolean numberIsMissing = Utils.isNumberMissing(board.rows.get(row), number);
                        boolean boxIsFilled = board.columns.get(column).numbers.get(row)==0? false : true;
                        if (numberIsMissing && !boxIsFilled) {
                            potentialPositions.add(row);
                        }
                    }
                    if (potentialPositions.size()==1) {
                        System.out.println("  Number " + number + " is missing from column " + column + " and can only be fitted into row " + potentialPositions.get(0));
                        board.updateNumber(number, potentialPositions.get(0), column);
                        changeMade = true;
                    }
                }
            }
            for (int square = 0; square < 9; square++) {
                if (Utils.isNumberMissing(board.squares.get(square), number)) {
                    //get the coordinates of all the positions in the square
                    List<Coordinate> coordinates = Utils.getCoordinatesForSquare(square);
                    //try to fit number in different positions on column
                    List<Integer> potentialPositions = new ArrayList();
                    for (int position = 0; position < 9; position++) {
                        int row = coordinates.get(position).row;
                        int col = coordinates.get(position).column;
                        boolean numberIsMissingFromRow = Utils.isNumberMissing(board.rows.get(row), number);
                        boolean numberIsMissingFromColumn = Utils.isNumberMissing(board.columns.get(col), number);
                        boolean isPositionFilled = board.squares.get(square).numbers.get(position) == 0 ? false : true;
                        if (numberIsMissingFromRow && numberIsMissingFromColumn && !isPositionFilled) {
                            potentialPositions.add(position);
                        }
                    }
                    if (potentialPositions.size()==1) {
                        System.out.println("  Number " + number + " is missing from square " + square + " and can only be fitted into position " + potentialPositions.get(0));
                        board.updateNumber(number, coordinates.get(potentialPositions.get(0)).row, coordinates.get(potentialPositions.get(0)).column);
                        changeMade = true;
                    }

                }
            }
        }

        return changeMade;
    }

    public static List<UnoccupiedSquare> findPossibleValuesForAllSpots(Board board){
        List<UnoccupiedSquare> unoccupiedSquares = new ArrayList<>();
        for(int rowIndex = 0; rowIndex<9; rowIndex++){
            for(int columnIndex = 0; columnIndex<9; columnIndex++){
                List<Integer> possibleValues = analyseSpot(board, rowIndex, columnIndex);
                if(possibleValues.size()>0){
                    UnoccupiedSquare unoccupiedSquare = new UnoccupiedSquare(rowIndex, columnIndex, possibleValues);
                    unoccupiedSquares.add(unoccupiedSquare);
                }
            }
        }
        return unoccupiedSquares;
    }

    public static boolean tryToFillAllSpots(Board board) throws Exception{
        boolean updateMade = false;
        for(int rowIndex = 0; rowIndex<9; rowIndex++){
            for(int columnIndex = 0; columnIndex<9; columnIndex++){
                boolean result = tryToFillSpot(board, rowIndex, columnIndex);
                if(result){
                    updateMade = true;
                }
            }
        }
        return updateMade;
    }

    public static boolean tryToFillSpot(Board board, int rowIndex, int columnIndex) throws Exception{
        List<Integer> possibleValues = analyseSpot(board, rowIndex, columnIndex);
        if(possibleValues.size()==1){
            int number = possibleValues.get(0);
            System.out.println("(" + rowIndex + "," + columnIndex + ") MUST BE " + number);
            board.updateNumber(number, rowIndex, columnIndex);
            return true;
        }
        return false;
    }

    public static List<Integer> analyseSpot(Board board, int rowIndex, int columnIndex){
        List<Integer> possibleValues = new ArrayList();
        Row row = board.rows.get(rowIndex);
        Column column = board.columns.get(columnIndex);
        Square square = board.squares.get(Utils.getSquareFromCoordinates(rowIndex, columnIndex));
        if(board.getNumber(rowIndex, columnIndex)!=0){
            return possibleValues;
        }
        boolean numberFound[] = {false, false, false, false, false, false, false, false, false, false};
        for(int number: row.numbers){
            numberFound[number] = true;
        }
        for(int number: column.numbers){
            numberFound[number] = true;
        }
        for(int number: square.numbers){
            numberFound[number] = true;
        }
        for(int i=1; i<10; i++){
            if(numberFound[i]==false){
                possibleValues.add(i);
            }
        }
        return possibleValues;
    }

}
