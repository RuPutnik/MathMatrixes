package ru.putnik.mathmatrixes;

public abstract class Matrix {
    protected double[][] completedMatrix;
    public double[][] getArray(){
        return completedMatrix;
    }
    public void setMatrix(Matrix matrix){
        completedMatrix=matrix.getArray();
    }
}
