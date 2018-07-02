package ru.putnik.mathmatrixes.conveyor;

public class UnitConveyorMatrix extends DefaultConveyorMatrix {
    public UnitConveyorMatrix(int length) {
        super(new double[length][length]);
        completedMatrix=new double[length][length];

        for (int a=0;a<length;a++){
            completedMatrix[a][a]=1;
        }
    }
}
