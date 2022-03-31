package me.korpusovmax.nimble;

public class Token {
    public TypeToken type;
    public String value;
    public Position posStart, posEnd;

    public Token (TypeToken type, String value, Position... a) {
        this.type = type;
        this.value = value;
        this.initPosition(a);
    }
    public Token (TypeToken type, Position... a) {
        this.type = type;
        this.initPosition(a);
    }
    private void initPosition(Position... a) {
        if (a.length > 0) {
            this.posStart = a[0].copy();
            this.posEnd = this.posStart.copy();
            this.posEnd.advance();
        }
        if (a.length > 1) {
            this.posEnd = a[1];
        }
    }

    public Token copy() {
        return new Token(type, value, posStart.copy(), posEnd.copy());
    }
    public String toString () {
        return type.name;
    }
}
