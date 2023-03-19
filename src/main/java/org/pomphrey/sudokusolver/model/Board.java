package org.pomphrey.sudokusolver.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.pomphrey.sudokusolver.utils.Utils;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Data
public class Board {

    public List<Row> rows;

    public List<Column> columns;

    public List<Square> squares;

    public boolean integrity;

    public Board() {
        rows = new ArrayList<Row>();
        columns = new ArrayList<Column>();
        squares = new ArrayList<Square>();
        for(int i=0; i<9; i++){
            rows.add(new Row());
            columns.add(new Column());
            squares.add(new Square());
        }
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

        this.integrity = integrity;

        return integrity;

    }

}
