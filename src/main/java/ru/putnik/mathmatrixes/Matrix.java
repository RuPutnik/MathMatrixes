package ru.putnik.mathmatrixes;

import java.util.Arrays;

public abstract class Matrix {
    protected double[][] completedMatrix;
    public double[][] getArray(){
        return completedMatrix;
    }
    public void setMatrix(Matrix matrix){
        completedMatrix=matrix.getArray();
    }
    public boolean equalsValues(Object obj) {
        Matrix matrix;
        try {
            matrix= (Matrix) obj;
        }catch (ClassCastException ex){
            return false;
        }
        return Arrays.deepEquals(this.getArray(),matrix.getArray());
    }
}
