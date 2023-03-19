package org.pomphrey.sudokusolver.model;

import java.util.List;

public class UnoccupiedSquare {

    public int row;
    public int column;
    public List<Integer> possibleValues;

    public UnoccupiedSquare(int row, int column, List<Integer> possibleValues){
        this.row = row;
        this.column = column;
        this.possibleValues = possibleValues;
    }

}

