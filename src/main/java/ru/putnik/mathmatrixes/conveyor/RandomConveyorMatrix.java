package ru.putnik.mathmatrixes.conveyor;

import java.text.DecimalFormat;

public class RandomConveyorMatrix extends DefaultConveyorMatrix {
    public RandomConveyorMatrix(int countRow,int countColumn, double lowBorder, double topBorder,boolean integer){
        double[][] values=new double[countRow][countColumn];

        for (int row=0;row<countRow;row++){
            for(int column=0;column<countColumn;column++){
                double randomValue=lowBorder+Math.random()*(topBorder-lowBorder);
                randomValue=Double.parseDouble(new DecimalFormat("#0.000").format(randomValue).replace(",","."));
                if(integer){
                    randomValue=(int)randomValue;
                }
                values[row][column]=randomValue;
            }
        }
        completedMatrix=values;
    }
}
