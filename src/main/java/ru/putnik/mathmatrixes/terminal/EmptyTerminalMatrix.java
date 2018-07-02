package ru.putnik.mathmatrixes.terminal;

public class EmptyTerminalMatrix extends DefaultTerminalMatrix {
    public EmptyTerminalMatrix(int countRow,int countColumn) {
        super(new double[countRow][countColumn]);
    }
}
