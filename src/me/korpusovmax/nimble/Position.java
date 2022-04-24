package me.korpusovmax.nimble;

public class Position {
    public int idx, ln, col;
    public String ftxt;
    public Position(int idx, int ln, int col, String ftxt) {
        this.idx = idx;
        this.col = col;
        this.ln = ln;
        this.ftxt = ftxt;
    }

    public Position advance(char ch) {
        idx++;
        col++;
        if ("\n\r".contains(Character.toString(ch))) {
            ln++;
            col = 0;
        }
        return this;
    }
    public Position advance() {
        idx++;
        col++;
        return this;
    }
    public Position copy() {
        return new Position(idx, ln, col, ftxt);
    }
}

