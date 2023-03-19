package org.pomphrey.sudokusolver.model;

import java.util.ArrayList;
import java.util.List;

public class Process2Result {

    public List<UnoccupiedSquare> unoccupiedSquareList;
    public boolean changeMade;

    public Process2Result(){
        changeMade = false;
        unoccupiedSquareList = new ArrayList<>();
    }

}
