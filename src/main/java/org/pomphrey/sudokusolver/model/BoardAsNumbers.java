package org.pomphrey.sudokusolver.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Data
public class BoardAsNumbers {

    public List<Integer> numbers;

    public BoardAsNumbers(){
        numbers = new ArrayList<>();
    }


}
