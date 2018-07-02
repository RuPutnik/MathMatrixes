package ru.putnik.mathmatrixes.terminal;

import ru.putnik.mathmatrixes.Matrix;

public class DefaultTerminalMatrix extends Matrix{
    public DefaultTerminalMatrix(){}
    public DefaultTerminalMatrix(Matrix matrix){
        completedMatrix=matrix.getArray();
    }
    public DefaultTerminalMatrix(double[][] matrix){
        int maxColumnSize=0;
        for (int a=0;a<matrix.length;a++){
            if(maxColumnSize<matrix[a].length){
                maxColumnSize=matrix[a].length;
            }
        }
        completedMatrix=new double[matrix.length][maxColumnSize];
        for(int row=0;row<matrix.length;row++){
            for (int column=0;column<matrix[row].length;column++){
                completedMatrix[row][column]=matrix[row][column];
            }
        }
    }
    public String size(){
        if(completedMatrix==null||completedMatrix.length==0){
            return "0x0";
        }else {
            return String.valueOf(completedMatrix.length) + "x" + String.valueOf(completedMatrix[0].length);
        }
    }
    public double valueAt(int column, int row){
        return completedMatrix[row-1][column-1];
    }
    public void setElement(int row, int column, double value){
        this.completedMatrix[row][column]=value;
    }
    public void setMatrixArray(double[][] array){
        completedMatrix=array;
    }
    public int getCountColumns(){
        String sizeMatrix=this.size();
        return Integer.parseInt(sizeMatrix.split("x")[1]);
    }
    public int getCountRows(){
        String sizeMatrix=this.size();
        return Integer.parseInt(sizeMatrix.split("x")[0]);
    }
    @Override
    public String toString() {
        StringBuilder matrixLine= new StringBuilder();
        double maxNumber=0;
        for(int row=0;row<completedMatrix.length;row++){
            for (int column=0;column<completedMatrix[row].length;column++){
                if(maxNumber<completedMatrix[row][column]){
                    maxNumber=completedMatrix[row][column];
                }
            }
        }
        String lineMaxNumber=String.valueOf(maxNumber);
        int lengthMaxNumber=lineMaxNumber.length();

        for(int row=0;row<completedMatrix.length;row++){
            matrixLine.append("|");
            for (int column=0;column<completedMatrix[row].length;column++){
               matrixLine.append(String.valueOf(completedMatrix[row][column])).append("  ");
               for (int a=lengthMaxNumber;a>String.valueOf(completedMatrix[row][column]).length();a--){
                   matrixLine.append(" ");
               }
            }
            matrixLine = new StringBuilder(matrixLine.toString().trim() + "|\n");

        }
        return matrixLine.toString();
    }
}
