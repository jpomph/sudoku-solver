import Utils.Utils;
import model.Coordinate;
import model.Board;
import model.Process2Result;
import model.UnoccupiedSquare;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        Board currentBoard = Utils.buildBoard();

        System.out.println("BOARD AT START ");
        currentBoard.OutputBoard();

        processToEndOrStuck(currentBoard, 0);

        if(Utils.isBoardFinished(currentBoard)){
            System.out.println("BOARD SOLVED!!!!!");
            return;
        }

        //do process2 again to get a list of unoccupied squares with up to 2 possible values
        Process2Result process2Result = processSquareBySquare(currentBoard,3);

        if (!processMultiplePossibilities(process2Result.unoccupiedSquareList, currentBoard)){
            System.out.println("BOARD COULDN'T BE SOLVED!!!!!");
        }

    }

    public static boolean processMultiplePossibilities(List<UnoccupiedSquare> unoccupiedSquares, Board currentBoard){
        int i = 0;
        while(i<unoccupiedSquares.size()){
            int row = unoccupiedSquares.get(i).row;
            int col = unoccupiedSquares.get(i).column;
            for (int attempt = 0; attempt< unoccupiedSquares.get(i).possibleValues.size(); attempt++){
                int numberToTry = unoccupiedSquares.get(i).possibleValues.get(attempt);
                System.out.println("TRYING WITH " + numberToTry + " IN " + row + "," + col);
                Board temporaryBoard = Utils.copyBoard(currentBoard);
                temporaryBoard.setNumber(numberToTry, row, col);
                processToEndOrStuck(temporaryBoard, 0);
                if(Utils.isBoardFinished(temporaryBoard)){
                    System.out.println("BOARD SOLVED!!!!!");
                    return true;
                }
            }
            i++;
        }
        return false;
    }

    public static void processToEndOrStuck(Board board, int iteration) {
        boolean moreProcessing = true;
        while(moreProcessing){
            iteration++;
            boolean changeMadeProcess1 = processRowsColumnsAndSquares(board);
            Process2Result process2Result = processSquareBySquare(board,1);

            if(!changeMadeProcess1 && !process2Result.changeMade){
                moreProcessing = false;
            } else {
                System.out.println("BOARD AT ITERATION " + iteration);
                board.OutputBoard();
            }
        }
    }

    public static Process2Result processSquareBySquare(Board board, int maxPossibilities){
        Process2Result result = new Process2Result();
        for (int row = 0; row < 9; row++) {
            for (int column = 0; column < 9; column++) {
                int square = Utils.getSquareFromCoordinates(row, column);
                if(board.getNumber(row,column)==0){
                    //check which numbers this square can hold
                    List<Integer> possibleNumbers = new ArrayList<>();
                    StringBuilder stringBuilder = new StringBuilder();
                    for(int number=1; number<10; number++){
                        if(
                                Utils.isNumberMissing(board.rows.get(row), number) &&
                                        Utils.isNumberMissing(board.columns.get(column), number) &&
                                        Utils.isNumberMissing(board.squares.get(square), number)
                        ) {
                            possibleNumbers.add(number);
                            stringBuilder.append(number);
                        }
                    }
                    if(possibleNumbers.size()==1){
                        System.out.println("(" + row + "," + column + ") can only be " + stringBuilder);
                        board.setNumber(possibleNumbers.get(0), row, column);
                        result.changeMade = true;
                    }
                    if(possibleNumbers.size()>1 && possibleNumbers.size()<=maxPossibilities){
                        UnoccupiedSquare unoccupiedSquare = new UnoccupiedSquare(row, column, possibleNumbers);
                        result.unoccupiedSquareList.add(unoccupiedSquare);
//                        System.out.println("(" + row + "," + column + ") can only be one of: " + stringBuilder);
                    }

                }
            }
        }
        return result;
    }

    public static boolean processRowsColumnsAndSquares(Board board){

        boolean changeMade = false;

        for (int number = 0; number < 9; number++) {
            for (int row = 0; row < 9; row++) {
                if (Utils.isNumberMissing(board.rows.get(row), number)) {
//                    System.out.println("  Number " + number + " is missing from row " + row);
                    //try to fit number in different positions on row
                    List<Integer> potentialPositions = new ArrayList();
                    StringBuilder stringBuilder = new StringBuilder();
                    for (int column = 0; column < 9; column++) {
                        boolean numberIsMissing = Utils.isNumberMissing(board.columns.get(column), number);
                        boolean boxIsFilled = board.rows.get(row).numbers.get(column)==0? false : true;
                        if (numberIsMissing && !boxIsFilled) {
                            potentialPositions.add(column);
                            stringBuilder.append(column);
                        }
                    }
                    if (potentialPositions.size()==1) {
                        System.out.println("  Number " + number + " is missing from row " + row + " and can only be fitted into column " + potentialPositions.get(0));
                        board.setNumber(number, row, potentialPositions.get(0));
                        changeMade = true;
                    }
                }
            }
            for (int column = 0; column < 9; column++) {
                if(Utils.isNumberMissing(board.columns.get(column),number)){
//                    System.out.println("  Number " + number + " is missing from column " + column);
                    //try to fit number in different positions on column
                    List<Integer> potentialPositions = new ArrayList();
                    StringBuilder stringBuilder = new StringBuilder();
                    for (int row = 0; row < 9; row++) {
                        boolean numberIsMissing = Utils.isNumberMissing(board.rows.get(row), number);
                        boolean boxIsFilled = board.columns.get(column).numbers.get(row)==0? false : true;
                        if (numberIsMissing && !boxIsFilled) {
                            potentialPositions.add(row);
                            stringBuilder.append(row);
                        }
                    }
                    if (potentialPositions.size()==1) {
                        System.out.println("  Number " + number + " is missing from column " + column + " and can only be fitted into row " + potentialPositions.get(0));
                        board.setNumber(number, potentialPositions.get(0), column);
                        changeMade = true;
                    }
                }
            }
            for (int square = 0; square < 9; square++) {
                if (Utils.isNumberMissing(board.squares.get(square), number)) {
//                    System.out.println("  Number " + number + " is missing from square " + square);
                    //get the coordinates of all the positions in the square
                    List<Coordinate> coordinates = Utils.getCoordinatesForSquare(square);
                    //try to fit number in different positions on column
                    List<Integer> potentialPositions = new ArrayList();
                    StringBuilder stringBuilder = new StringBuilder();
                    for (int position = 0; position < 9; position++) {
                        boolean numberIsMissingFromRow = Utils.isNumberMissing(board.rows.get(coordinates.get(position).row), number);
                        boolean numberIsMissingFromColumn = Utils.isNumberMissing(board.columns.get(coordinates.get(position).column), number);
                        boolean isPositionFilled = board.squares.get(square).numbers.get(position) == 0 ? false : true;
                        if (numberIsMissingFromRow && numberIsMissingFromColumn && !isPositionFilled) {
                            potentialPositions.add(position);
                            stringBuilder.append(position);
                        }
                    }
                    if (potentialPositions.size()==1) {
                        System.out.println("  Number " + number + " is missing from square " + square + " and can only be fitted into position " + potentialPositions.get(0));
                        board.setNumber(number, coordinates.get(potentialPositions.get(0)).row, coordinates.get(potentialPositions.get(0)).column);
                        changeMade = true;
                    }

                }
            }
        }

        return changeMade;
    }
}