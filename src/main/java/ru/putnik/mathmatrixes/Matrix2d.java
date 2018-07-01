package ru.putnik.mathmatrixes;

import java.util.ArrayList;

public class Matrix2d {
    private double[][] completedMatrix;
    public Matrix2d(double[][] matrix){
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
    public static String size(Matrix2d matrix2d){
        if(matrix2d.completedMatrix==null||matrix2d.completedMatrix.length==0){
            return "0x0";
        }else {
            return String.valueOf(matrix2d.completedMatrix.length) + "x" + String.valueOf(matrix2d.completedMatrix[0].length);
        }
    }
    public static double valueAt(Matrix2d matrix2d,int column,int row){
        return matrix2d.completedMatrix[row-1][column-1];
    }
    public static double trace(Matrix2d matrix2d){
        if(Matrix2d.isSquare(matrix2d)){
            int length=matrix2d.completedMatrix.length;
            double trace=0;
            for (int a=0;a<length;a++){
                trace=trace+matrix2d.completedMatrix[a][a];
            }
            return trace;
        }else {
            System.out.println("След можно вычислить только для квадратных матриц!");
            return 0;
        }
    }
    public static Matrix2d set(Matrix2d matrix, int row, int column, double value){
        matrix.completedMatrix[row][column]=value;
        return matrix;
    }
    public static Matrix2d getUnitMatrix(int length){
        double[][] values=new double[length][length];

        for (int a=0;a<length;a++){
            values[a][a]=1;
        }

        Matrix2d matrix2d=new Matrix2d(values);
        return matrix2d;
    }
    public static Matrix2d getZeroMatrix(int countRow,int countColumns){
        double[][] values=new double[countRow][countColumns];
        Matrix2d matrix2d=new Matrix2d(values);
        return matrix2d;
    }
    public static boolean isSquare(Matrix2d matrix2d){
        boolean square=false;

        if(matrix2d.completedMatrix.length==matrix2d.completedMatrix[0].length) return true;

        return square;
    }
    public static int getCountColumns(Matrix2d matrix2d){
        String sizeMatrix=Matrix2d.size(matrix2d);
        return Integer.parseInt(sizeMatrix.split("x")[1]);
    }
    public static int getCountRows(Matrix2d matrix2d){
        String sizeMatrix=Matrix2d.size(matrix2d);
        return Integer.parseInt(sizeMatrix.split("x")[0]);
    }

    public static boolean equalsSizes(Matrix2d matrix1,Matrix2d matrix2) {
        return Matrix2d.size(matrix1).equals(Matrix2d.size(matrix2));
    }
    public static Matrix2d add(Matrix2d matrix1,Matrix2d matrix2){
        if(equalsSizes(matrix1,matrix2)){
            double[][] resultValues=new double[matrix1.completedMatrix.length][matrix1.completedMatrix[0].length];

            for(int row=0;row<matrix1.completedMatrix.length;row++){
                for(int column=0;column<matrix1.completedMatrix[0].length;column++){
                    resultValues[row][column]=matrix1.completedMatrix[row][column]+matrix2.completedMatrix[row][column];
                }
            }
            Matrix2d resultMatrix=new Matrix2d(resultValues);
            return resultMatrix;
        }else {
            System.out.println("Можно складывать матрицы только с одинаковыми размерами!");
            return null;
        }
    }
    public static Matrix2d substract(Matrix2d matrix1,Matrix2d matrix2){
        if(equalsSizes(matrix1,matrix2)){
            double[][] resultValues=new double[matrix1.completedMatrix.length][matrix1.completedMatrix[0].length];

            for(int row=0;row<matrix1.completedMatrix.length;row++){
                for(int column=0;column<matrix1.completedMatrix[0].length;column++){
                    resultValues[row][column]=matrix1.completedMatrix[row][column]-matrix2.completedMatrix[row][column];
                }
            }
            Matrix2d resultMatrix=new Matrix2d(resultValues);
            return resultMatrix;
        }else {
            System.out.println("Можно складывать матрицы только с одинаковыми размерами!");
            return null;
        }
    }
    public static Matrix2d multiple(Matrix2d matrix1,Matrix2d matrix2){
        if(Matrix2d.getCountColumns(matrix1)==Matrix2d.getCountRows(matrix2)){
            double[][] resultValues=new double[Matrix2d.getCountRows(matrix1)][Matrix2d.getCountColumns(matrix2)];
            for (int a=0;a<Matrix2d.getCountRows(matrix1);a++){
                for (int b=0;b<Matrix2d.getCountColumns(matrix2);b++){
                    for (int c=0;c<Matrix2d.getCountColumns(matrix1);c++){
                        resultValues[a][b]=resultValues[a][b]+(matrix1.completedMatrix[a][c]*matrix2.completedMatrix[c][b]);
                    }

                }
            }
            Matrix2d resultMatrix=new Matrix2d(resultValues);
            return resultMatrix;
        }else {
            System.out.println("Можно умножать матрицы только если количество столбцов первой равно количеству строк второй!");
            return null;
        }

    }
    public static Matrix2d multiple(Matrix2d matrix,double multipler){
         for (int row=0;row<matrix.completedMatrix.length;row++){
             for (int column=0;column<matrix.completedMatrix[0].length;column++){
                matrix.completedMatrix[row][column]=matrix.completedMatrix[row][column]*multipler;
             }
         }
         return matrix;
    }
    public static Matrix2d division(Matrix2d matrix,double divider){
        for (int row=0;row<matrix.completedMatrix.length;row++){
            for (int column=0;column<matrix.completedMatrix[0].length;column++){
                matrix.completedMatrix[row][column]=matrix.completedMatrix[row][column]/divider;
            }
        }
        return matrix;
    }
    public static Matrix2d trans(Matrix2d matrix){
        double[][] transMatrix=new double[matrix.completedMatrix[0].length][matrix.completedMatrix.length];
        for (int row=0;row<matrix.completedMatrix.length;row++){
            for (int column=0;column<matrix.completedMatrix[0].length;column++){
                transMatrix[column][row]=matrix.completedMatrix[row][column];
            }
        }
        Matrix2d matrix2d=new Matrix2d(transMatrix);
        return matrix2d;
    }
    public static double det(Matrix2d matrix){
        double result=0;
        if(Matrix2d.isSquare(matrix)){
            if(Matrix2d.getCountColumns(matrix)==1&&Matrix2d.getCountRows(matrix)==1)
                return matrix.completedMatrix[0][0];
            else{
                for(int a=0;a<matrix.completedMatrix[0].length;a++){
                   result=result+Math.pow(-1,a)*matrix.completedMatrix[0][a]*det(moll(matrix,a));
                }
                return result;
            }
        }else {
            System.out.println("Определитель можно вычислить только для квадратных матриц!");
            return 0;
        }
    }
    public static int rank(Matrix2d matrix2d){
        int matrixRank=0;

        Matrix2d moll=new Matrix2d(new double[][]{});
        Matrix2d mask=Matrix2d.getZeroMatrix(Matrix2d.getCountRows(matrix2d),Matrix2d.getCountColumns(matrix2d));
        ArrayList<Matrix2d> invalidMolls=new ArrayList<>();

        while (Matrix2d.getCountRows(moll)<Matrix2d.getCountRows(matrix2d)&&Matrix2d.getCountColumns(moll)<Matrix2d.getCountColumns(matrix2d)){
            Matrix2d[] results=expandBorderMatrix(matrix2d,moll,mask,invalidMolls);
            Matrix2d potentMoll=results[0];
            if(Matrix2d.det(potentMoll)!=0){
                moll=potentMoll;
                matrixRank++;
                mask=results[1];
            }else {
                invalidMolls.add(potentMoll);
            }
        }
        return matrixRank;
    }
    public static int rank1(Matrix2d matrix2d){
        int rank = 0;
        int sizeStageMatrix = 1;
        int countRow=Matrix2d.getCountRows(matrix2d);
        int countColumns=Matrix2d.getCountColumns(matrix2d);
        while(sizeStageMatrix<=min(Matrix2d.getCountRows(matrix2d),Matrix2d.getCountColumns(matrix2d))){ // проверка: порядок матрицы меньше или равен минимальному из размеров матрицы?
         // если да
            double[][] stageMatrix=new double[sizeStageMatrix][sizeStageMatrix]; // создаем новую матрицу размера q

            for(int row=0;row<(countRow-(sizeStageMatrix-1));row++){ // тут начинается перебор матриц q-го порядка
                for(int column=0;column<(countColumns-(sizeStageMatrix-1));column++){
                    for(int c=0;c<sizeStageMatrix;c++){
                        for(int d=0;d<sizeStageMatrix;d++){
                            stageMatrix[c][d] = matrix2d.completedMatrix[row+c][column+d];
                        }
                    }
                    if(!(Matrix2d.det(new Matrix2d(stageMatrix))==0)){ // если определитель матрицы отличен от нуля
                     // то
                        rank = sizeStageMatrix; // присваиваем рангу значение q
                    }
                }
            }
            sizeStageMatrix++; // прибавляем 1
        }
        return rank;
    }
    private static int min(int a, int b){
        if(a>=b)return b;
        else return a;
    }
    public static Matrix2d[] expandBorderMatrix(Matrix2d fullMatrix,Matrix2d moll,Matrix2d mask,ArrayList<Matrix2d> invalidMolls){
        if(moll==null){
            moll=new Matrix2d(new double[][]{});
        }
        if(mask==null){
            mask=new Matrix2d(new double[][]{});
        }
        double[][] values=new double[Matrix2d.getCountRows(moll)+1][Matrix2d.getCountColumns(moll)+1];
        double[][] maskValues=new double[Matrix2d.getCountRows(fullMatrix)][Matrix2d.getCountColumns(fullMatrix)];
        double[][] values1=new double[Matrix2d.getCountRows(fullMatrix)][Matrix2d.getCountColumns(fullMatrix)];

        int rw=0;
        int cl=0;
        for (int row=0;row<Matrix2d.getCountRows(mask);row++){
            for (int column=0;column<Matrix2d.getCountColumns(mask);column++){
                if(Matrix2d.valueAt(mask,row+1,column+1)==1){
                    values1[row][column]=Matrix2d.valueAt(moll,rw+1,cl+1);
                    cl++;
                }
            }
            rw++;
            cl=0;
        }


        int countNewDigit=0;
        Matrix2d mask1=mask;

            for (int row = 0; row < Matrix2d.getCountRows(fullMatrix); row++) {
                countNewDigit = countOneInMaskRow(mask1, row);
                for (int column = 0; column < Matrix2d.getCountColumns(fullMatrix); column++) {
                    if (Matrix2d.valueAt(mask, row+1, column+1) == 0 && (countNewDigit < Matrix2d.getCountRows(moll))) {
                        values1[row][column] = Matrix2d.valueAt(fullMatrix, row+1, column+1);
                        if(!contentInArrayListInvalidMoll(invalidMolls,values1,mask1,values.length,values[0].length)) {
                            mask = Matrix2d.set(mask, row, column, 1);
                            countNewDigit++;
                        }
                    }
                }
            }
        Matrix2d m=new Matrix2d(values1);
        System.out.println(m);


        int rw1=0;
        int cl1=1;
        for (int row=0;row<Matrix2d.getCountRows(mask);row++){
            for (int column=0;column<Matrix2d.getCountColumns(mask);column++) {
                if(Matrix2d.valueAt(mask,row+1,column+1)==1){
                    values[rw1][cl1]=values1[row][column];
                    cl1++;
                }
            }
            cl1=0;
            rw1++;
        }


        Matrix2d resultMoll=new Matrix2d(values);
        Matrix2d maskMatrix=new Matrix2d(maskValues);
        return new Matrix2d[]{resultMoll,maskMatrix};
    }
    public static Matrix2d sectionMatrix(Matrix2d matrix2d, int rowStart,int rowEnd,int columnStart,int columnEnd){
        if(rowEnd<rowStart||columnEnd<columnStart){
            System.out.println("Границы области заданы неверно!");
            return null;
        }
        double[][] values=new double[rowEnd-rowStart+1][columnEnd-columnStart+1];
        for (int row=0;row<Matrix2d.getCountRows(matrix2d);row++){
            for(int column=0;column<Matrix2d.getCountColumns(matrix2d);column++){
                if(row>=rowStart&&row<=rowEnd&&column>=columnStart&&column<=columnEnd){
                    values[row-rowStart][column-columnStart]=matrix2d.completedMatrix[row][column];
                }
            }
        }

        Matrix2d resultMatrix=new Matrix2d(values);
        return resultMatrix;
    }
    private static int countOneInMaskRow(Matrix2d mask,int row){
        int count=0;
        for (int column=0;column<Matrix2d.getCountColumns(mask);column++){
            if(Matrix2d.valueAt(mask,column+1,row+1)==1){
                count++;
            }
        }
        return count;
    }
    private static boolean contentInArrayListInvalidMoll(ArrayList<Matrix2d> list,double[][] moll,Matrix2d mask,int row,int column){
        double[][] values=new double[row+1][column+1];
        int rw1=0;
        int cl1=1;
        for (int rw=0;rw<Matrix2d.getCountRows(mask);rw++){
            for (int cl=0;cl<Matrix2d.getCountColumns(mask);cl++) {
                if(Matrix2d.valueAt(mask,rw,cl)==1){
                    values[rw1][cl1]=moll[rw][cl];
                    cl1++;
                }
            }
            cl1=0;
            rw1++;
        }
        Matrix2d resultMoll=new Matrix2d(values);
        for(Matrix2d m:list){
            if(m.equals(resultMoll))
                return true;
        }
        return false;
    }
    private static Matrix2d moll(Matrix2d matrix,int columnNumber){
        double[][] resultValues=new double[matrix.completedMatrix.length-1][matrix.completedMatrix[0].length-1];

        int cl=0;
        for (int row=1;row<matrix.completedMatrix.length;row++){
            for (int column=0;column<matrix.completedMatrix[0].length;column++){
                if(column!=columnNumber) {
                    resultValues[row-1][cl] = matrix.completedMatrix[row][column];
                    cl++;
                }
            }
            cl=0;
        }

        Matrix2d matrix2d=new Matrix2d(resultValues);
        return matrix2d;
    }

    private static Matrix2d moll1(Matrix2d matrix, int rowNumber, int columnNumber){
        double[][] resultValues=new double[matrix.completedMatrix.length-1][matrix.completedMatrix[0].length-1];

        int cl=0;
        int rw=0;
        for (int row=0;row<matrix.completedMatrix.length;row++){
            if(row!=rowNumber) {
                for (int column = 0; column < matrix.completedMatrix[0].length; column++) {
                    if (column != columnNumber) {
                        resultValues[rw][cl] = matrix.completedMatrix[row][column];
                        cl++;
                    }
                }
                rw++;
            }
            cl=0;
        }

        Matrix2d matrix2d=new Matrix2d(resultValues);
        return matrix2d;
    }
    private static double cofactor(Matrix2d matrix,int rowNumber,int columnNumber){
        double result;

        double detMatrix=Matrix2d.det(Matrix2d.moll1(matrix,rowNumber,columnNumber));
        result=detMatrix*Math.pow(-1,rowNumber+columnNumber);

        return result;
    }
    public static Matrix2d reverse(Matrix2d matrix){
        if(Matrix2d.getCountRows(matrix)==Matrix2d.getCountColumns(matrix)){
            if(Matrix2d.det(matrix)!=0) {
                double[][] values = new double[matrix.completedMatrix.length][matrix.completedMatrix[0].length];

                for (int row=0;row<matrix.completedMatrix.length;row++){
                    for (int column=0;column<matrix.completedMatrix[0].length;column++){
                        values[row][column]=Matrix2d.cofactor(matrix,row,column);
                    }
                }

                Matrix2d unionMatrix = new Matrix2d(values);
                Matrix2d reverseMatrix = Matrix2d.division(Matrix2d.trans(unionMatrix), Matrix2d.det(matrix));
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

    public static void main(String[] args) {
        double array[][]={{0,6}};
        double array1[][]={{1,-3,2,5},
                           {-2,4,3,-1},
                           {0,2,7,11},
                           {7,-15,-7,2},
                           {-1,1,5,6}};
        Matrix2d a=new Matrix2d(array);
        Matrix2d b=new Matrix2d(array1);
        double[][] v=new double[][]{};
        Matrix2d c=new Matrix2d(v);
        System.out.println(Matrix2d.rank1(a));
        //System.out.println(Matrix2d.det(a));
        //System.out.println(Matrix2d.cofactor(Matrix2d.moll1(b,2,1),2,1));
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
