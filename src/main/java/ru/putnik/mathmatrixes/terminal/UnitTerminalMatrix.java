package ru.putnik.mathmatrixes.terminal;

public class UnitTerminalMatrix extends DefaultTerminalMatrix{
    public UnitTerminalMatrix(int length) {
        super(new double[length][length]);
        completedMatrix=new double[length][length];

        for (int a=0;a<length;a++){
            completedMatrix[a][a]=1;
        }
    }
}
