package ru.putnik.mathmatrixes.terminal;

import ru.putnik.mathmatrixes.conveyor.DefaultConveyorMatrix;

public class TerminalMatrixOperations {
    public static boolean equalsSizes(DefaultTerminalMatrix matrix1, DefaultTerminalMatrix matrix2) {
        return matrix1.size().equals(matrix2.size());
    }
    public static double trace(DefaultTerminalMatrix defaultMatrix){
        if(isSquare(defaultMatrix)){
            int length= defaultMatrix.getArray().length;
            double trace=0;
            for (int a=0;a<length;a++){
                trace=trace+ defaultMatrix.getArray()[a][a];
            }
            return trace;
        }else {
            System.out.println("След можно вычислить только для квадратных матриц!");
            return 0;
        }
    }
    public static DefaultTerminalMatrix add(DefaultTerminalMatrix matrix1, DefaultTerminalMatrix matrix2){
        if(equalsSizes(matrix1,matrix2)){
            double[][] resultValues=new double[matrix1.getArray().length][matrix1.getArray()[0].length];

            for(int row=0;row<matrix1.getArray().length;row++){
                for(int column=0;column<matrix1.getArray()[0].length;column++){
                    resultValues[row][column]=matrix1.getArray()[row][column]+matrix2.getArray()[row][column];
                }
            }
            DefaultTerminalMatrix resultMatrix=new DefaultTerminalMatrix(resultValues);
            return resultMatrix;
        }else {
            System.out.println("Можно складывать матрицы только с одинаковыми размерами!");
            return null;
        }
    }
    public static DefaultTerminalMatrix substract(DefaultTerminalMatrix matrix1, DefaultTerminalMatrix matrix2){
        if(equalsSizes(matrix1,matrix2)){
            double[][] resultValues=new double[matrix1.getArray().length][matrix1.getArray()[0].length];

            for(int row=0;row<matrix1.getArray().length;row++){
                for(int column=0;column<matrix1.getArray()[0].length;column++){
                    resultValues[row][column]=matrix1.getArray()[row][column]-matrix2.getArray()[row][column];
                }
            }
            DefaultTerminalMatrix resultMatrix=new DefaultTerminalMatrix(resultValues);
            return resultMatrix;
        }else {
            System.out.println("Можно складывать матрицы только с одинаковыми размерами!");
            return null;
        }
    }
    public static DefaultTerminalMatrix multiple(DefaultTerminalMatrix matrix1, DefaultTerminalMatrix matrix2){
        if(matrix1.getCountColumns()==matrix2.getCountRows()){
            double[][] resultValues=new double[matrix1.getCountRows()][matrix2.getCountColumns()];
            for (int a = 0; a<matrix1.getCountRows(); a++){
                for (int b = 0; b<matrix2.getCountColumns(); b++){
                    for (int c = 0; c<matrix1.getCountColumns(); c++){
                        resultValues[a][b]=resultValues[a][b]+(matrix1.getArray()[a][c]*matrix2.getArray()[c][b]);
                    }

                }
            }
            DefaultTerminalMatrix resultMatrix=new DefaultTerminalMatrix(resultValues);
            return resultMatrix;
        }else {
            System.out.println("Можно умножать матрицы только если количество столбцов первой равно количеству строк второй!");
            return null;
        }

    }
    public static DefaultTerminalMatrix multiple(DefaultTerminalMatrix matrix, double multipler){
        for (int row=0;row<matrix.getArray().length;row++){
            for (int column=0;column<matrix.getArray()[0].length;column++){
                matrix.getArray()[row][column]=matrix.getArray()[row][column]*multipler;
            }
        }
        return matrix;
    }
    public static DefaultTerminalMatrix pow(DefaultTerminalMatrix matrix,int degree){
        DefaultTerminalMatrix result;
        if(!isSquare(matrix)){
            System.out.println("Возводить в степень можно только квадратные матрицы!");
            return null;
        }else {
            result=matrix;
            if(degree==0) {
                return new UnitTerminalMatrix(matrix.getCountColumns());
            }else if(degree<0){
                return pow(reverse(matrix),-degree);
            }else {
                for (int a = 0; a < degree - 1; a++) {
                    result = multiple(result,matrix);
                }
            }
        }
        return result;
    }
    public static DefaultTerminalMatrix kronekerMultiple(DefaultTerminalMatrix matrix1,DefaultTerminalMatrix matrix2){
        int countRow=matrix1.getCountRows()*matrix2.getCountRows();
        int countColumn=matrix1.getCountColumns()*matrix2.getCountColumns();
        DefaultTerminalMatrix resultMatrix=new DefaultTerminalMatrix(new double[countRow][countColumn]);

        int row=0;
        int column=0;

        for(int row1=0;row1<matrix1.getCountRows();row1++) {
            for (int row2 = 0; row2 < matrix2.getCountRows();row2++) {

                for (int column1 = 0; column1 < matrix1.getCountColumns(); column1++) {
                    for (int column2 = 0; column2 < matrix2.getCountColumns(); column2++) {
                        resultMatrix.setElement(row, column, matrix1.valueAt(row1 + 1, column1 + 1)
                                * matrix2.valueAt(row2 + 1, column2 + 1));
                        column++;
                    }
                }
                column=0;
                row++;
            }
        }

        return resultMatrix;
    }
    public static DefaultTerminalMatrix division(DefaultTerminalMatrix matrix, double divider){
        for (int row=0;row<matrix.getArray().length;row++){
            for (int column=0;column<matrix.getArray()[0].length;column++){
                matrix.getArray()[row][column]=matrix.getArray()[row][column]/divider;
            }
        }
        return matrix;
    }
    public static DefaultTerminalMatrix trans(DefaultTerminalMatrix matrix){
        double[][] transMatrix=new double[matrix.getArray()[0].length][matrix.getArray().length];
        for (int row=0;row<matrix.getArray().length;row++){
            for (int column=0;column<matrix.getArray()[0].length;column++){
                transMatrix[column][row]=matrix.getArray()[row][column];
            }
        }
        DefaultTerminalMatrix defaultMatrix =new DefaultTerminalMatrix(transMatrix);
        return defaultMatrix;
    }
    public static DefaultTerminalMatrix rotate90(DefaultTerminalMatrix matrix){
        DefaultTerminalMatrix resultMatrix=new DefaultTerminalMatrix(new double[matrix.getCountColumns()][matrix.getCountRows()]);
        for (int row=0;row<resultMatrix.getCountRows();row++){
            for(int column=0;column<resultMatrix.getCountColumns();column++){
                resultMatrix.setElement(row,column,matrix.valueAt(resultMatrix.getCountColumns()-column,row+1));
            }
        }
        return resultMatrix;
    }
    public static DefaultTerminalMatrix rotate180(DefaultTerminalMatrix matrix){
        DefaultTerminalMatrix resultMatrix=new DefaultTerminalMatrix(new double[matrix.getCountRows()][matrix.getCountColumns()]);
        for (int row=0;row<resultMatrix.getCountRows();row++){
            for(int column=0;column<resultMatrix.getCountColumns();column++){
                resultMatrix.setElement(row,column,matrix.valueAt(resultMatrix.getCountRows()-row,resultMatrix.getCountColumns()-column));
            }
        }
        return resultMatrix;
    }
    public static DefaultTerminalMatrix rotate270(DefaultTerminalMatrix matrix){
        DefaultTerminalMatrix resultMatrix=new DefaultTerminalMatrix(new double[matrix.getCountColumns()][matrix.getCountRows()]);
        for (int row=0;row<resultMatrix.getCountRows();row++){
            for(int column=0;column<resultMatrix.getCountColumns();column++){
                resultMatrix.setElement(row,column,matrix.valueAt(column+1,resultMatrix.getCountRows()-row));
            }
        }
        return resultMatrix;
    }
    public static DefaultTerminalMatrix rotate(DefaultTerminalMatrix matrix,int count90){
        DefaultTerminalMatrix resultMatrix=matrix;
        for (int countRt=0;countRt<count90;countRt++){
            resultMatrix=rotate90(resultMatrix);
        }
        return resultMatrix;
    }
    public static double det(DefaultTerminalMatrix matrix){
        double result=0;
        if(isSquare(matrix)){
            if(matrix.getCountColumns()==1&&matrix.getCountRows()==1)
                return matrix.getArray()[0][0];
            else{
                for(int a=0;a<matrix.getArray()[0].length;a++){
                    result=result+Math.pow(-1,a)*matrix.getArray()[0][a]*det(moll(matrix,a));
                }
                return result;
            }
        }else {
            System.out.println("Определитель можно вычислить только для квадратных матриц!");
            return 0;
        }
    }
    public static int rank(DefaultTerminalMatrix defaultMatrix){
        int rank = 0;
        int sizeStageMatrix = 1;
        int countRow=defaultMatrix.getCountRows();
        int countColumns=defaultMatrix.getCountColumns();
        while(sizeStageMatrix<=min(defaultMatrix.getCountRows(),defaultMatrix.getCountColumns())){ // проверка: порядок матрицы меньше или равен минимальному из размеров матрицы?
            // если да
            double[][] stageMatrix=new double[sizeStageMatrix][sizeStageMatrix]; // создаем новую матрицу размера q

            for(int row=0;row<(countRow-(sizeStageMatrix-1));row++){ // тут начинается перебор матриц q-го порядка
                for(int column=0;column<(countColumns-(sizeStageMatrix-1));column++){
                    for(int c=0;c<sizeStageMatrix;c++){
                        for(int d=0;d<sizeStageMatrix;d++){
                            stageMatrix[c][d] = defaultMatrix.getArray()[row+c][column+d];
                        }
                    }
                    if(!(det(new DefaultTerminalMatrix(stageMatrix))==0)){ // если определитель матрицы отличен от нуля
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
    public static DefaultTerminalMatrix sectionMatrix(DefaultTerminalMatrix defaultMatrix, int rowStart, int rowEnd, int columnStart, int columnEnd){
        if(rowEnd<rowStart||columnEnd<columnStart){
            System.out.println("Границы области заданы неверно!");
            return null;
        }
        double[][] values=new double[rowEnd-rowStart+1][columnEnd-columnStart+1];
        for (int row = 0; row<defaultMatrix.getCountRows(); row++){
            for(int column = 0; column<defaultMatrix.getCountColumns(); column++){
                if(row>=rowStart&&row<=rowEnd&&column>=columnStart&&column<=columnEnd){
                    values[row-rowStart][column-columnStart]= defaultMatrix.getArray()[row][column];
                }
            }
        }

        DefaultTerminalMatrix resultMatrix=new DefaultTerminalMatrix(values);
        return resultMatrix;
    }
    private static DefaultTerminalMatrix moll(DefaultTerminalMatrix matrix, int columnNumber){
        double[][] resultValues=new double[matrix.getArray().length-1][matrix.getArray()[0].length-1];

        int cl=0;
        for (int row=1;row<matrix.getArray().length;row++){
            for (int column=0;column<matrix.getArray()[0].length;column++){
                if(column!=columnNumber) {
                    resultValues[row-1][cl] = matrix.getArray()[row][column];
                    cl++;
                }
            }
            cl=0;
        }

        DefaultTerminalMatrix defaultMatrix =new DefaultTerminalMatrix(resultValues);
        return defaultMatrix;
    }

    private static DefaultTerminalMatrix moll1(DefaultTerminalMatrix matrix, int rowNumber, int columnNumber){
        double[][] resultValues=new double[matrix.getArray().length-1][matrix.getArray()[0].length-1];

        int cl=0;
        int rw=0;
        for (int row=0;row<matrix.getArray().length;row++){
            if(row!=rowNumber) {
                for (int column = 0; column < matrix.getArray()[0].length; column++) {
                    if (column != columnNumber) {
                        resultValues[rw][cl] = matrix.getArray()[row][column];
                        cl++;
                    }
                }
                rw++;
            }
            cl=0;
        }

        DefaultTerminalMatrix defaultMatrix =new DefaultTerminalMatrix(resultValues);
        return defaultMatrix;
    }
    private static double cofactor(DefaultTerminalMatrix matrix, int rowNumber, int columnNumber){
        double result;

        double detMatrix=det(moll1(matrix,rowNumber,columnNumber));
        result=detMatrix*Math.pow(-1,rowNumber+columnNumber);

        return result;
    }
    public static DefaultTerminalMatrix reverse(DefaultTerminalMatrix matrix){
        if(matrix.getCountRows()==matrix.getCountColumns()){
            if(det(matrix)!=0) {
                double[][] values = new double[matrix.getArray().length][matrix.getArray()[0].length];

                for (int row=0;row<matrix.getArray().length;row++){
                    for (int column=0;column<matrix.getArray()[0].length;column++){
                        values[row][column]=cofactor(matrix,row,column);
                    }
                }


                DefaultTerminalMatrix unionMatrix = new DefaultTerminalMatrix(values);
                DefaultTerminalMatrix reverseMatrix =division(trans(unionMatrix), det(matrix));
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
    public static DefaultTerminalMatrix[] decompositionLU(DefaultTerminalMatrix matrix){
        if(isSquare(matrix)) {
            if(matrix.valueAt(1,1)!=0&&det(matrix)!=0) {
                DefaultTerminalMatrix a = matrix;
                DefaultTerminalMatrix l = new DefaultTerminalMatrix(new double[a.getCountColumns()][a.getCountColumns()]);
                DefaultTerminalMatrix u = new DefaultTerminalMatrix(new double[a.getCountColumns()][a.getCountColumns()]);

                for (int column = 0; column < u.getCountColumns(); column++) {
                    u.setElement(0, column, a.valueAt(1, column + 1));
                }
                for (int row = 0; row < l.getCountRows(); row++) {
                    l.setElement(row, 0, (a.valueAt(row + 1, 1) / u.valueAt(1, 1)));
                }
                for (int i = 1; i < a.getCountColumns(); i++) {
                    for (int j = i; j < a.getCountColumns(); j++) {
                        double sum = 0;
                        for (int k = 0; k < i; k++) {
                            sum = sum + (l.valueAt(i + 1, k + 1) * u.valueAt(k + 1, j + 1));
                        }
                        u.setElement(i, j, (a.valueAt(i + 1, j + 1) - sum));
                        double sum1 = 0;
                        for (int k = 0; k < i; k++) {
                            sum1 = sum1 + (l.valueAt(j + 1, k + 1) * u.valueAt(k + 1, i + 1));
                        }
                        l.setElement(j, i, (1.0 / u.valueAt(i + 1, i + 1)) * (a.valueAt(j + 1, i + 1) - sum1));
                    }
                }


                return new DefaultTerminalMatrix[]{l, u};
            }else {
                System.out.println("LU разложение осуществимо только для невырожденных матриц, при условии, что элемент с индексом [0,0] не равен 0!");
                return null;
            }
        }else{
            System.out.println("LU разложение осуществимо только для квадратных матриц!");
            return null;
        }
    }
    public static DefaultTerminalMatrix[] decompositionLUP(DefaultTerminalMatrix matrix){
        if(isSquare(matrix)) {
            DefaultTerminalMatrix c=matrix;
            int countRow = matrix.getCountRows();

            //загружаем в матрицу P единичную матрицу
            DefaultTerminalMatrix e = new UnitTerminalMatrix(countRow);
            DefaultTerminalMatrix l = new UnitTerminalMatrix(countRow);
            DefaultTerminalMatrix u = new UnitTerminalMatrix(countRow);
            DefaultTerminalMatrix p = new UnitTerminalMatrix(countRow);

            for(int i = 0; i < countRow; i++){
                //поиск опорного элемента
                double pivotValue = 0;
                int pivot = -1;
                for(int row = i; row < countRow-1; row++){
                    if(Math.abs(c.valueAt(row+1,i+1)) > pivotValue){
                        pivotValue = Math.abs(c.valueAt(row+1,i+1));
                        pivot = row;
                    }
                }
                if(pivotValue != 0){
                    //меняем местами i-ю строку и строку с опорным элементом
                    p=swapRows(p,pivot, i);
                    c=swapRows(c,pivot, i);
                    for(int j = i+1; j < countRow; j++){
                        c.setElement(j,i,c.valueAt(j+1,i+1)/c.valueAt(i+1,i+1));
                        for(int k = i+1; k < countRow; k++) {
                            c.setElement(j, k, c.valueAt(j + 1, k + 1) - c.valueAt(j + 1, i + 1) * c.valueAt(i + 1, k + 1));
                        }
                    }

                }
            }

            c=add(c,e);
            for (int row = 0; row<matrix.getCountRows(); row++){
                for(int column = 0; column<=row; column++){
                    if(row==column){
                        l.setElement(row,column,1);
                    }else {
                        l.setElement(row, column, c.valueAt(row + 1, column + 1));
                    }
                }
            }
            for (int row = 0; row<matrix.getCountRows(); row++){
                for(int column = matrix.getCountColumns()-1; column>=row; column--){
                    if(row==column){
                        u.setElement(row,column,c.valueAt(row+1,column+1)-1);
                    }else {
                        u.setElement(row,column, c.valueAt(row + 1, column + 1));
                    }
                }
            }

            return new DefaultTerminalMatrix[]{l,u,p};
        }else{
            System.out.println("LUP разложение осуществимо только для квадратных матриц!");
            return null;
        }
    }
    public static DefaultTerminalMatrix swapRows(DefaultTerminalMatrix matrix,int row1,int row2){
        double[] tempRow=new double[matrix.getCountColumns()];

        for (int column=0;column<matrix.getCountColumns();column++){
            tempRow[column]=matrix.valueAt(row1+1,column+1);
        }
        for (int column=0;column<matrix.getCountColumns();column++){
            matrix.setElement(row1,column,matrix.valueAt(row2+1,column+1));
        }
        for (int column=0;column<matrix.getCountColumns();column++){
            matrix.setElement(row2,column,tempRow[column]);
        }
        return matrix;
    }
    public static DefaultTerminalMatrix getUnitMatrix(int length){
        double[][] values=new double[length][length];

        for (int a=0;a<length;a++){
            values[a][a]=1;
        }

        DefaultTerminalMatrix defaultMatrix =new DefaultTerminalMatrix(values);
        return defaultMatrix;
    }
    public static DefaultTerminalMatrix getZeroMatrix(int countRow, int countColumns){
        double[][] values=new double[countRow][countColumns];
        DefaultTerminalMatrix defaultMatrix =new DefaultTerminalMatrix(values);
        return defaultMatrix;
    }
    public static boolean isSquare(DefaultTerminalMatrix defaultMatrix){
        boolean square=false;

        if(defaultMatrix.getArray().length==defaultMatrix.getArray()[0].length) return true;

        return square;
    }
    public static boolean isSymmetric(DefaultTerminalMatrix defaultMatrix){
        return trans(defaultMatrix).equalsValues(defaultMatrix);
    }
    public static boolean isDiag(DefaultTerminalMatrix defaultMatrix){
        boolean diag=true;

        if(!isSquare(defaultMatrix)) {
            diag = false;
        }else {
            for (int row = 1; row < defaultMatrix.getCountRows() + 1; row++) {
                for (int column = 1; column < defaultMatrix.getCountColumns() + 1; column++) {
                    if (defaultMatrix.valueAt(row, column) != 0 && (row - 1 != column - 1)) diag = false;
                }
            }
        }

        return diag;
    }
    public static boolean isUnit(DefaultTerminalMatrix defaultMatrix){
        boolean unit=true;
        if(!isDiag(defaultMatrix)) unit=false;
        for (int diagElement=0;diagElement<defaultMatrix.getCountColumns();diagElement++){
            if(defaultMatrix.valueAt(diagElement,diagElement)!=1) unit=false;
        }
        return unit;
    }
    public static boolean isZero(DefaultTerminalMatrix defaultMatrix){
        boolean zero=true;
        for (int row=0;row<defaultMatrix.getCountRows();row++){
            for(int column=0;column<defaultMatrix.getCountColumns();column++){
                if(defaultMatrix.valueAt(row,column)!=0){
                    zero=false;
                    break;
                }
            }
        }
        return zero;
    }
}
