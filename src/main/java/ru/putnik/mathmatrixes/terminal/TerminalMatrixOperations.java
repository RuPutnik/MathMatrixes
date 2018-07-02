package ru.putnik.mathmatrixes.terminal;

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
}
