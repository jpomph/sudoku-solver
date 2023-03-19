package org.pomphrey.sudokusolver.model;

import org.pomphrey.sudokusolver.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class Board {

    public List<Row> rows;

    public List<Column> columns;

    public List<Square> squares;

    public List<Integer> numbers;

    public boolean integrity;

    public Board() {
        rows = List.of(new Row(), new Row(), new Row(), new Row(), new Row(), new Row(), new Row(), new Row(), new Row());
        columns = List.of(new Column(), new Column(), new Column(), new Column(), new Column(), new Column(), new Column(), new Column(), new Column());
        squares = List.of(new Square(), new Square(), new Square(), new Square(), new Square(), new Square(), new Square(), new Square(), new Square());
    }

    public boolean updateNumber(Integer number, Integer row, Integer column) throws Exception{
        setNumber(number,row, column );
        outputBoard();
        return checkIntegrity();
    }

    public void setNumber(Integer number, Integer row, Integer column){

        //add number to correct row
        Row currentRow = rows.get(row);
        for(int i=0; i<9; i++){
            if (i == (column)) {
                currentRow.numbers.set(i, number);
            }
        }

        //add number to correct column
        Column currentColumn = columns.get(column);
        for(int i=0; i<9; i++){
            if (i == (row)) {
                currentColumn.numbers.set(i, number);
            }
        }

        //add number to correct square
        int squareRow = row / 3;
        int squareColumn = column / 3;
        int squareNumber = (squareRow * 3) + squareColumn;

        Square currentSquare = squares.get(squareNumber);

        int rowInSquare = row - (squareRow * 3);
        int columnInSquare = column - (squareColumn * 3);
        int positionInSquare = (rowInSquare * 3) + columnInSquare;

        for(int i=0; i<9; i++){
            if (i == (positionInSquare)) {
                currentSquare.numbers.set(i, number);
            }
        }

        convertBoardToNumberList();

    }

    public int getNumber(int row, int column){
        return rows.get(row).numbers.get(column);
    }

    public void outputBoard(){
        System.out.println("========================");
        for (int row=0; row<9; row++){
            StringBuilder stringBuilder = new StringBuilder();
            for (int col=0; col<9; col++){
                stringBuilder.append(getNumber(row, col));
                stringBuilder.append("    ");
                if(col==2||col==5){
                    stringBuilder.append("        ");
                }
            }
            System.out.println(stringBuilder);
            if(row==2||row==5){
                System.out.println("");
            }
        }
        System.out.println("========================");
    }

    public boolean checkIntegrity(){
        boolean integrity = true;
        for(int row=0; row<9; row++){
            if(!Utils.checkNumberSetIntegrity(rows.get(row), "ROW", row)){
                integrity = false;
            }
        }
        for(int col=0; col<9; col++){
            if(!Utils.checkNumberSetIntegrity(columns.get(col), "COLUMN", col)){
                integrity = false;
            }
        }
        for(int square=0; square<9; square++){
            if(!Utils.checkNumberSetIntegrity(squares.get(square), "SQUARE", square)){
                integrity = false;
            }
        }

        if(!integrity){
            integrity = false;
            System.out.println("BOARD DOES NOT HAVE INTEGRITY");
        }

        return integrity;

    }

    public void convertBoardToNumberList(){
        numbers = new ArrayList<>();
        for(int row=0; row<9; row++) {
            for (int col = 0; col < 9; col++) {
                numbers.add(getNumber(row, col));
            }
        }
    }

    public List<Integer> getNumbers(){
        return numbers;
    }

}
