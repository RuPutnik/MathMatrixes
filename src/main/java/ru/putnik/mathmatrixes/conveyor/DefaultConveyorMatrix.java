package ru.putnik.mathmatrixes.conveyor;

import ru.putnik.mathmatrixes.Matrix;

public class DefaultConveyorMatrix extends Matrix{
    public DefaultConveyorMatrix(){}
    public DefaultConveyorMatrix(Matrix matrix){
        completedMatrix=matrix.getArray();
    }
    public DefaultConveyorMatrix(double[][] matrix){
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
        if(this.completedMatrix==null|| this.completedMatrix.length==0){
            return "0x0";
        }else {
            return String.valueOf(this.completedMatrix.length) + "x" + String.valueOf(this.completedMatrix[0].length);
        }
    }
    public double valueAt(int row, int column){
        return this.completedMatrix[row-1][column-1];
    }
    public double trace(){
        if(isSquare()){
            int length= this.completedMatrix.length;
            double trace=0;
            for (int a=0;a<length;a++){
                trace=trace+this.completedMatrix[a][a];
            }
            return trace;
        }else {
            System.out.println("След можно вычислить только для квадратных матриц!");
            return 0;
        }
    }
    public void setElement(int row, int column, double value){
        this.completedMatrix[row][column]=value;
    }
    public void setMatrixArray(double[][] array){
        completedMatrix=array;
    }
    public double[][] getArray(){
        return completedMatrix;
    }

    public boolean isSquare(){
        boolean square=false;

        if(this.completedMatrix.length==this.completedMatrix[0].length) return true;

        return square;
    }
    public int getCountColumns(){
        String sizeMatrix=size();
        return Integer.parseInt(sizeMatrix.split("x")[1]);
    }
    public int getCountRows(){
        String sizeMatrix=size();
        return Integer.parseInt(sizeMatrix.split("x")[0]);
    }

    public boolean equalsSizes(DefaultConveyorMatrix matrix) {
        return this.size().equals(matrix.size());
    }

    public DefaultConveyorMatrix add(DefaultConveyorMatrix matrix){
        if(this.equalsSizes(matrix)){
            double[][] resultValues=new double[this.completedMatrix.length][this.completedMatrix[0].length];

            for(int row=0;row<this.completedMatrix.length;row++){
                for(int column=0;column<this.completedMatrix[0].length;column++){
                    resultValues[row][column]=this.completedMatrix[row][column]+matrix.completedMatrix[row][column];
                }
            }
            DefaultConveyorMatrix resultMatrix=new DefaultConveyorMatrix(resultValues);
            return resultMatrix;
        }else {
            System.out.println("Можно складывать матрицы только с одинаковыми размерами!");
            return null;
        }
    }
    public DefaultConveyorMatrix substract(DefaultConveyorMatrix matrix){
        if(this.equalsSizes(matrix)){
            double[][] resultValues=new double[this.completedMatrix.length][this.completedMatrix[0].length];

            for(int row=0;row<this.completedMatrix.length;row++){
                for(int column=0;column<this.completedMatrix[0].length;column++){
                    resultValues[row][column]=this.completedMatrix[row][column]-matrix.completedMatrix[row][column];
                }
            }
            DefaultConveyorMatrix resultMatrix=new DefaultConveyorMatrix(resultValues);
            return resultMatrix;
        }else {
            System.out.println("Можно складывать матрицы только с одинаковыми размерами!");
            return null;
        }
    }
    public DefaultConveyorMatrix multiple(DefaultConveyorMatrix matrix){
        if(this.getCountColumns()==matrix.getCountColumns()){
            double[][] resultValues=new double[this.getCountRows()][matrix.getCountColumns()];
            for (int a = 0; a<this.getCountRows(); a++){
                for (int b = 0; b<matrix.getCountColumns(); b++){
                    for (int c = 0; c<this.getCountColumns(); c++){
                        resultValues[a][b]=resultValues[a][b]+(this.completedMatrix[a][c]*matrix.completedMatrix[c][b]);
                    }

                }
            }
            DefaultConveyorMatrix resultMatrix=new DefaultConveyorMatrix(resultValues);
            return resultMatrix;
        }else {
            System.out.println("Можно умножать матрицы только если количество столбцов первой равно количеству строк второй!");
            return null;
        }

    }
    public DefaultConveyorMatrix multiple(double multipler){
         for (int row=0;row<this.completedMatrix.length;row++){
             for (int column=0;column<this.completedMatrix[0].length;column++){
                this.completedMatrix[row][column]=this.completedMatrix[row][column]*multipler;
             }
         }
         return this;
    }
    public DefaultConveyorMatrix division(double divider){
        for (int row=0;row<this.completedMatrix.length;row++){
            for (int column=0;column<this.completedMatrix[0].length;column++){
                this.completedMatrix[row][column]=this.completedMatrix[row][column]/divider;
            }
        }
        return this;
    }
    public DefaultConveyorMatrix pow(int degree){
        DefaultConveyorMatrix result;
        if(!this.isSquare()){
            System.out.println("Возводить в степень можно только квадратные матрицы!");
            return null;
        }else {
            result=this;
            if(degree==0) {
                return new UnitConveyorMatrix(this.getCountColumns());
            }else if(degree<0){
                return result.reverse().pow(-degree);
            }else {
                for (int a = 0; a < degree - 1; a++) {
                    result = result.multiple(this);
                }
            }
        }
        return result;
    }
    public DefaultConveyorMatrix kronekerMultiple(DefaultConveyorMatrix matrix){
        int countRow=this.getCountRows()*matrix.getCountRows();
        int countColumn=this.getCountColumns()*matrix.getCountColumns();
        DefaultConveyorMatrix resultMatrix=new DefaultConveyorMatrix(new double[countRow][countColumn]);

            int row=0;
            int column=0;

                for(int row1=0;row1<this.getCountRows();row1++) {
                    for (int row2 = 0; row2 < matrix.getCountRows();row2++) {

                        for (int column1 = 0; column1 < this.getCountColumns(); column1++) {
                            for (int column2 = 0; column2 < matrix.getCountColumns(); column2++) {
                                resultMatrix.setElement(row, column, this.valueAt(row1 + 1, column1 + 1)
                                        * matrix.valueAt(row2 + 1, column2 + 1));
                                column++;
                        }
                }
                column=0;
                row++;
            }
        }

        return resultMatrix;
    }
    public DefaultConveyorMatrix trans(){
        double[][] transMatrix=new double[this.completedMatrix[0].length][this.completedMatrix.length];
        for (int row=0;row<this.completedMatrix.length;row++){
            for (int column=0;column<this.completedMatrix[0].length;column++){
                transMatrix[column][row]=this.completedMatrix[row][column];
            }
        }
        DefaultConveyorMatrix defaultMatrix =new DefaultConveyorMatrix(transMatrix);
        return defaultMatrix;
    }
    public DefaultConveyorMatrix rotate90(){
        DefaultConveyorMatrix resultMatrix=new DefaultConveyorMatrix(new double[this.getCountColumns()][this.getCountRows()]);
        for (int row=0;row<resultMatrix.getCountRows();row++){
            for(int column=0;column<resultMatrix.getCountColumns();column++){
                resultMatrix.setElement(row,column,this.valueAt(resultMatrix.getCountColumns()-column,row+1));
            }
        }
        return resultMatrix;
    }
    public DefaultConveyorMatrix rotate180(){
        DefaultConveyorMatrix resultMatrix=new DefaultConveyorMatrix(new double[this.getCountRows()][this.getCountColumns()]);
        for (int row=0;row<resultMatrix.getCountRows();row++){
            for(int column=0;column<resultMatrix.getCountColumns();column++){
                resultMatrix.setElement(row,column,this.valueAt(resultMatrix.getCountRows()-row,resultMatrix.getCountColumns()-column));
            }
        }
        return resultMatrix;
    }
    public DefaultConveyorMatrix rotate270(){
        DefaultConveyorMatrix resultMatrix=new DefaultConveyorMatrix(new double[this.getCountColumns()][this.getCountRows()]);
        for (int row=0;row<resultMatrix.getCountRows();row++){
            for(int column=0;column<resultMatrix.getCountColumns();column++){
                resultMatrix.setElement(row,column,this.valueAt(column+1,resultMatrix.getCountRows()-row));
            }
        }
        return resultMatrix;
    }
    public DefaultConveyorMatrix rotate(int count90){
        DefaultConveyorMatrix resultMatrix=this;
        for (int countRt=0;countRt<count90;countRt++){
            resultMatrix=resultMatrix.rotate90();
        }
        return resultMatrix;
    }
    public double det(){
        double result=0;
        if(this.isSquare()){
            if(this.getCountColumns()==1&&this.getCountRows()==1)
                return this.completedMatrix[0][0];
            else{
                for(int a=0;a<this.completedMatrix[0].length;a++){
                   result=result+Math.pow(-1,a)*this.completedMatrix[0][a]*this.moll(a).det();
                }
                return result;
            }
        }else {
            System.out.println("Определитель можно вычислить только для квадратных матриц!");
            return 0;
        }
    }
    public int rank(){
        int rank = 0;
        int sizeStageMatrix = 1;
        int countRow= this.getCountRows();
        int countColumns= this.getCountColumns();
        while(sizeStageMatrix<=min(this.getCountRows(), this.getCountColumns())){ // проверка: порядок матрицы меньше или равен минимальному из размеров матрицы?
         // если да
            double[][] stageMatrix=new double[sizeStageMatrix][sizeStageMatrix]; // создаем новую матрицу размера q

            for(int row=0;row<(countRow-(sizeStageMatrix-1));row++){ // тут начинается перебор матриц q-го порядка
                for(int column=0;column<(countColumns-(sizeStageMatrix-1));column++){
                    for(int c=0;c<sizeStageMatrix;c++){
                        for(int d=0;d<sizeStageMatrix;d++){
                            stageMatrix[c][d] = this.completedMatrix[row+c][column+d];
                        }
                    }
                    if(new DefaultConveyorMatrix(stageMatrix).det()!=0){ // если определитель матрицы отличен от нуля
                     // то
                        rank = sizeStageMatrix; // присваиваем рангу значение q
                    }
                }
            }
            sizeStageMatrix++; // прибавляем 1
        }
        return rank;
    }
    private int min(int a, int b){
        if(a>=b)return b;
        else return a;
    }
    public DefaultConveyorMatrix sectionMatrix(int rowStart, int rowEnd, int columnStart, int columnEnd){
        if(rowEnd<rowStart||columnEnd<columnStart){
            System.out.println("Границы области заданы неверно!");
            return null;
        }
        double[][] values=new double[rowEnd-rowStart+1][columnEnd-columnStart+1];
        for (int row = 0; row< this.getCountRows(); row++){
            for(int column = 0; column<this.getCountColumns(); column++){
                if(row>=rowStart&&row<=rowEnd&&column>=columnStart&&column<=columnEnd){
                    values[row-rowStart][column-columnStart]= this.completedMatrix[row][column];
                }
            }
        }

        DefaultConveyorMatrix resultMatrix=new DefaultConveyorMatrix(values);
        return resultMatrix;
    }
    private DefaultConveyorMatrix moll(int columnNumber){
        double[][] resultValues=new double[this.completedMatrix.length-1][this.completedMatrix[0].length-1];

        int cl=0;
        for (int row=1;row<this.completedMatrix.length;row++){
            for (int column=0;column<this.completedMatrix[0].length;column++){
                if(column!=columnNumber) {
                    resultValues[row-1][cl] = this.completedMatrix[row][column];
                    cl++;
                }
            }
            cl=0;
        }

        DefaultConveyorMatrix defaultMatrix =new DefaultConveyorMatrix(resultValues);
        return defaultMatrix;
    }

    private DefaultConveyorMatrix moll1(int rowNumber, int columnNumber){
        double[][] resultValues=new double[this.completedMatrix.length-1][this.completedMatrix[0].length-1];

        int cl=0;
        int rw=0;
        for (int row=0;row<this.completedMatrix.length;row++){
            if(row!=rowNumber) {
                for (int column = 0; column < this.completedMatrix[0].length; column++) {
                    if (column != columnNumber) {
                        resultValues[rw][cl] = this.completedMatrix[row][column];
                        cl++;
                    }
                }
                rw++;
            }
            cl=0;
        }

        DefaultConveyorMatrix defaultMatrix =new DefaultConveyorMatrix(resultValues);
        return defaultMatrix;
    }
    private double cofactor(int rowNumber, int columnNumber){
        double result;

        double detMatrix=this.moll1(rowNumber,columnNumber).det();
        result=detMatrix*Math.pow(-1,rowNumber+columnNumber);

        return result;
    }
    public DefaultConveyorMatrix reverse(){
        if(this.getCountRows()==this.getCountColumns()){
            if(this.det()!=0) {
                double[][] values = new double[this.completedMatrix.length][this.completedMatrix[0].length];

                for (int row=0;row<this.completedMatrix.length;row++){
                    for (int column=0;column<this.completedMatrix[0].length;column++){
                        values[row][column]= this.cofactor(row,column);
                    }
                }

                DefaultConveyorMatrix unionMatrix = new DefaultConveyorMatrix(values);
                DefaultConveyorMatrix reverseMatrix = unionMatrix.trans().division(this.det());
                return reverseMatrix;
            }else {
                System.out.println("Определитель матрицы равен нулю. Построение обратной матрицы невозможно.");
                return null;
            }
        }else {
            System.out.println("Обратную матрицу можно построить только для квадратных матриц!");
            return null;
        }
    }
    public DefaultConveyorMatrix[] decompositionLU(){
        if(this.isSquare()) {
            DefaultConveyorMatrix a = this;
            DefaultConveyorMatrix l=new DefaultConveyorMatrix(new double[a.getCountColumns()][a.getCountColumns()]);
            DefaultConveyorMatrix u=new DefaultConveyorMatrix(new double[a.getCountColumns()][a.getCountColumns()]);

            for (int column=0;column<u.getCountColumns();column++){
                u.setElement(0,column,a.valueAt(1,column+1));
            }
            for (int row=0;row<l.getCountRows();row++){
                l.setElement(row,0,(a.valueAt(row+1,1)/u.valueAt(1,1)));
            }
            for(int row=1;row<u.getCountRows();row++){
                for(int column=row;column<u.getCountColumns();column++){
                    double sum=0;
                    for(int k=0;k<row-1;k++){
                        sum=sum+(l.valueAt(row+1,k+1)*u.valueAt(k+1,column+1));
                    }
                    u.setElement(row,column,a.valueAt(row+1,column+1)-sum);
                }
            }
            for(int column=1;column<l.getCountColumns();column++){
                for(int row=column;row<l.getCountRows();row++){
                    double sum=0;
                    for(int k=0;k<column-1;k++){
                        sum=sum+(l.valueAt(column+1,k+1)*u.valueAt(k+1,row+1));
                    }
                    l.setElement(row,column,(1.0/u.valueAt(column+1,column+1))*(a.valueAt(row+1,column+1)-sum));
                }
            }


            return new DefaultConveyorMatrix[]{l,u};
        }else{
            System.out.println("LU разложение осуществимо только для квадратных матриц!");
            return null;
        }
    }
    public boolean isSymmetric(){
        return this.trans().equalsValues(this);
    }
    public boolean isDiag(){
        boolean diag=true;
        if(!this.isSquare()) {
            diag = false;
        }else {
            for (int row = 1; row < this.getCountRows() + 1; row++) {
                for (int column = 1; column < this.getCountColumns() + 1; column++) {
                    if (this.valueAt(row, column) != 0 && (row - 1 != column - 1)) diag = false;
                }
            }
        }

        return diag;
    }
    public boolean isUnit(){
        boolean unit=true;
        if(!this.isDiag()) unit=false;
        for (int diagElement=0;diagElement<this.getCountColumns();diagElement++){
            if(this.valueAt(diagElement,diagElement)!=1) unit=false;
        }
        return unit;
    }
    public boolean isZero(){
        boolean zero=true;
        for (int row=0;row<this.getCountRows();row++){
            for(int column=0;column<this.getCountColumns();column++){
                if(this.valueAt(row,column)!=0){
                    zero=false;
                    break;
                }
            }
        }
        return zero;
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
