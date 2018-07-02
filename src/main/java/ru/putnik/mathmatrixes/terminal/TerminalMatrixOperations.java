package ru.putnik.mathmatrixes.terminal;

public class TerminalMatrixOperations {
    public static boolean equalsSizes(DefaultTerminalMatrix matrix1, DefaultTerminalMatrix matrix2) {
        return matrix1.size().equals(matrix2.size());
    }
    public static double trace(DefaultTerminalMatrix defaultMatrix){
        if(isSquare(defaultMatrix)){
            int length= defaultMatrix.completedMatrix.length;
            double trace=0;
            for (int a=0;a<length;a++){
                trace=trace+ defaultMatrix.completedMatrix[a][a];
            }
            return trace;
        }else {
            System.out.println("След можно вычислить только для квадратных матриц!");
            return 0;
        }
    }
    public static DefaultTerminalMatrix add(DefaultTerminalMatrix matrix1, DefaultTerminalMatrix matrix2){
        if(equalsSizes(matrix1,matrix2)){
            double[][] resultValues=new double[matrix1.completedMatrix.length][matrix1.completedMatrix[0].length];

            for(int row=0;row<matrix1.completedMatrix.length;row++){
                for(int column=0;column<matrix1.completedMatrix[0].length;column++){
                    resultValues[row][column]=matrix1.completedMatrix[row][column]+matrix2.completedMatrix[row][column];
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
            double[][] resultValues=new double[matrix1.completedMatrix.length][matrix1.completedMatrix[0].length];

            for(int row=0;row<matrix1.completedMatrix.length;row++){
                for(int column=0;column<matrix1.completedMatrix[0].length;column++){
                    resultValues[row][column]=matrix1.completedMatrix[row][column]-matrix2.completedMatrix[row][column];
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
        if(DefaultTerminalMatrix.getCountColumns(matrix1)== DefaultTerminalMatrix.getCountRows(matrix2)){
            double[][] resultValues=new double[DefaultTerminalMatrix.getCountRows(matrix1)][DefaultTerminalMatrix.getCountColumns(matrix2)];
            for (int a = 0; a< DefaultTerminalMatrix.getCountRows(matrix1); a++){
                for (int b = 0; b< DefaultTerminalMatrix.getCountColumns(matrix2); b++){
                    for (int c = 0; c< DefaultTerminalMatrix.getCountColumns(matrix1); c++){
                        resultValues[a][b]=resultValues[a][b]+(matrix1.completedMatrix[a][c]*matrix2.completedMatrix[c][b]);
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
        for (int row=0;row<matrix.completedMatrix.length;row++){
            for (int column=0;column<matrix.completedMatrix[0].length;column++){
                matrix.completedMatrix[row][column]=matrix.completedMatrix[row][column]*multipler;
            }
        }
        return matrix;
    }
    public static DefaultTerminalMatrix division(DefaultTerminalMatrix matrix, double divider){
        for (int row=0;row<matrix.completedMatrix.length;row++){
            for (int column=0;column<matrix.completedMatrix[0].length;column++){
                matrix.completedMatrix[row][column]=matrix.completedMatrix[row][column]/divider;
            }
        }
        return matrix;
    }
    public static DefaultTerminalMatrix trans(DefaultTerminalMatrix matrix){
        double[][] transMatrix=new double[matrix.completedMatrix[0].length][matrix.completedMatrix.length];
        for (int row=0;row<matrix.completedMatrix.length;row++){
            for (int column=0;column<matrix.completedMatrix[0].length;column++){
                transMatrix[column][row]=matrix.completedMatrix[row][column];
            }
        }
        DefaultTerminalMatrix defaultMatrix =new DefaultTerminalMatrix(transMatrix);
        return defaultMatrix;
    }
    public static double det(DefaultTerminalMatrix matrix){
        double result=0;
        if(isSquare(matrix)){
            if(DefaultTerminalMatrix.getCountColumns(matrix)==1&& DefaultTerminalMatrix.getCountRows(matrix)==1)
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
    public static int rank(DefaultTerminalMatrix defaultMatrix){
        int rank = 0;
        int sizeStageMatrix = 1;
        int countRow= DefaultTerminalMatrix.getCountRows(defaultMatrix);
        int countColumns= DefaultTerminalMatrix.getCountColumns(defaultMatrix);
        while(sizeStageMatrix<=min(DefaultTerminalMatrix.getCountRows(defaultMatrix), DefaultTerminalMatrix.getCountColumns(defaultMatrix))){ // проверка: порядок матрицы меньше или равен минимальному из размеров матрицы?
            // если да
            double[][] stageMatrix=new double[sizeStageMatrix][sizeStageMatrix]; // создаем новую матрицу размера q

            for(int row=0;row<(countRow-(sizeStageMatrix-1));row++){ // тут начинается перебор матриц q-го порядка
                for(int column=0;column<(countColumns-(sizeStageMatrix-1));column++){
                    for(int c=0;c<sizeStageMatrix;c++){
                        for(int d=0;d<sizeStageMatrix;d++){
                            stageMatrix[c][d] = defaultMatrix.completedMatrix[row+c][column+d];
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
        for (int row = 0; row< DefaultTerminalMatrix.getCountRows(defaultMatrix); row++){
            for(int column = 0; column< DefaultTerminalMatrix.getCountColumns(defaultMatrix); column++){
                if(row>=rowStart&&row<=rowEnd&&column>=columnStart&&column<=columnEnd){
                    values[row-rowStart][column-columnStart]= defaultMatrix.completedMatrix[row][column];
                }
            }
        }

        DefaultTerminalMatrix resultMatrix=new DefaultTerminalMatrix(values);
        return resultMatrix;
    }
    private static DefaultTerminalMatrix moll(DefaultTerminalMatrix matrix, int columnNumber){
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

        DefaultTerminalMatrix defaultMatrix =new DefaultTerminalMatrix(resultValues);
        return defaultMatrix;
    }

    private static DefaultTerminalMatrix moll1(DefaultTerminalMatrix matrix, int rowNumber, int columnNumber){
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
        if(DefaultTerminalMatrix.getCountRows(matrix)== DefaultTerminalMatrix.getCountColumns(matrix)){
            if(det(matrix)!=0) {
                double[][] values = new double[matrix.completedMatrix.length][matrix.completedMatrix[0].length];

                for (int row=0;row<matrix.completedMatrix.length;row++){
                    for (int column=0;column<matrix.completedMatrix[0].length;column++){
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

        if(defaultMatrix.completedMatrix.length==defaultMatrix.completedMatrix[0].length) return true;

        return square;
    }
}
