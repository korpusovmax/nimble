package me.korpusovmax.nimble;

import java.util.ArrayList;
import java.util.HashMap;

public class Lexer {
    public String data;
    public int lim;
    public Position pos;
    public char currentChar;
    public Lexer (String data) {
        this.data = data + " ";
        lim = data.length();
        pos = new Position(-1, 0, -1, data);
        advance();
    }
    public void advance() {
        pos.advance(currentChar);
        if (pos.idx <= lim) {
            currentChar = data.charAt(pos.idx);
        } else {
            currentChar = 0;
        }
    }

    public Either generateTokens () {
        ArrayList<Token> tokens = new ArrayList<Token>();

        while (currentChar != 0) {
            if (";\n".contains(Character.toString(currentChar))) {
                tokens.add(new Token(TypeToken.NEWLINE, pos));
                advance();
            } else if ("\t\r ".contains(Character.toString(currentChar))) {
                advance();
            } else if (currentChar == '+') {
                tokens.add(new Token(TypeToken.PLUS, pos));
                advance();
            } else if (currentChar == '-') {
                tokens.add(new Token(TypeToken.MINUS, pos));
                advance();
            } else if (currentChar == '/') {
                tokens.add(new Token(TypeToken.DIV, pos));
                advance();
            } else if (currentChar == '*') {
                tokens.add(new Token(TypeToken.MUL, pos));
                advance();
            } else if (currentChar == '^') {
                tokens.add(new Token(TypeToken.POW, pos));
                advance();
            } else if (currentChar == '(') {
                tokens.add(new Token(TypeToken.LPAREN, pos));
                advance();
            } else if (currentChar == ')') {
                tokens.add(new Token(TypeToken.RPAREN, pos));
                advance();
            } else if ("0123456789".contains(String.valueOf(currentChar)) || currentChar == '.') {
                tokens.add(_number());
            } else if (currentChar == '"') {
                tokens.add(_string());
            } else {
                Position posStart = pos.copy();
                char ch = currentChar;
                advance();
                return Either.error(new Errors.IllegalCharError(posStart, pos, "'" + ch + "'"));
            }
        }
        tokens.add(new Token(TypeToken.EOF, pos));
        return Either.success(tokens);
    }

    public Token _number() {
        String num = "";
        Position posStart = pos.copy();
        int dot = 0;
        while (currentChar != 0 && "0123456789.".contains(Character.toString(this.currentChar))) {
            if (currentChar == '.') {
                if (dot == 1) {
                    break;
                }
                dot++;
            }
            num = num + this.currentChar;
            advance();
        }
        if (dot == 1) {
            return new Token(TypeToken.FLOAT, num, posStart, pos);
        } else {
            return new Token(TypeToken.INT, num, posStart, pos);
        }
    }
    public Token _string() {
        Position posStart = pos.copy();
        String string = "";
        Boolean escapeCharacter = false;
        advance();

        HashMap<String, String> escapeCharacters = new HashMap<>();
        escapeCharacters.put("n", "\n");
        escapeCharacters.put("t", "\t");

        while (currentChar != 0 && (currentChar != '"'|| escapeCharacter)) {
            if (escapeCharacter) {
                escapeCharacter = false;
                if (escapeCharacters.containsKey(Character.toString(currentChar))) {
                    string = string + escapeCharacters.get("" + currentChar);
                } else {
                    string = string + currentChar;
                }
            } else {
                if (currentChar == '\\') {
                    escapeCharacter = true;
                } else {
                    escapeCharacter = false;
                    string = string + currentChar;
                }
            }
            advance();
        }
        advance();
        return new Token(TypeToken.STRING, string, posStart, pos.copy());
    }
}