package model;

import java.util.ArrayList;
import java.util.List;

public class ProcessResult {

    public boolean solved;
    public List<UnoccupiedSquare> unoccupiedSquareList;

    public ProcessResult(){
        unoccupiedSquareList = new ArrayList<>();
    }

}
