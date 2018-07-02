package ru.putnik.mathmatrixes.conveyor;

public class EmptyConveyorMatrix extends DefaultConveyorMatrix {
    public EmptyConveyorMatrix(int countRow, int countColumn) {
        super(new double[countRow][countColumn]);
    }
}
