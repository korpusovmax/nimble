package me.korpusovmax.nimble;

import java.util.ArrayList;

public class Parser {
    private ArrayList<Token> tokens;
    private int tokenIndex;
    private Token currentToken;
    public Parser(ArrayList<Token> tokens) {
        tokenIndex = -1;
        this.tokens = tokens;
        advance();
    }
    public Token advance() {
        tokenIndex++;
        if (tokenIndex < tokens.size() && tokenIndex >= 0) {
            currentToken = tokens.get(tokenIndex);
        }
        return currentToken;
    }

    public ParseResult parse() {
        ParseResult res = _expr();
        return res;
    }
    //reading nodes
    public ParseResult _expr() {
        ParseResult res = new ParseResult();
        ParseResult left = res.register(_term());
        if (res.state.error()) {
            return res;
        }
        while (currentToken.type == TypeToken.PLUS || currentToken.type == TypeToken.MINUS) {
            Token token = currentToken;
            res.registerAdvancement();
            advance();
            ParseResult right = res.register(_term());
            if (right.error()) {
//                if (res.state.getSuccess() == null) {
//                    Position start = ((Node) left.state.getSuccess()).getPosStart();
//                    Position end = ((Node) left.state.getSuccess()).getPosEnd();
//                    res.state = Either.error(new Errors.InvalidSyntax(start, end, "expected smth bro"));
//                    return res;
//                }
//                if (res.state.error()) {
//                    return r///
//                }
                return res;
            }
            left.state = Either.success(new Nodes.BinOpNode((Node) left.state.getSuccess(), token, (Node) right.state.getSuccess()));
        }
        res.state = left.state;
        return res;
    }
    public ParseResult _term() {
        ParseResult res = new ParseResult();
        ParseResult left = res.register(_factor());
        if (res.error()) {
            return res;
        }
        while (currentToken.type == TypeToken.MUL || currentToken.type == TypeToken.DIV) {
            Token token = currentToken;
            res.registerAdvancement();
            advance();
            ParseResult right = res.register(_factor());
            if (res.error()) {
                return res;
            }
            left.state = Either.success(new Nodes.BinOpNode((Node) left.state.getSuccess(), token, (Node) right.state.getSuccess()));
        }
        res.state = left.state;
        return res;
    }
    public ParseResult _factor() {
        ParseResult res = new ParseResult();
        if (currentToken.type == TypeToken.PLUS || currentToken.type == TypeToken.MINUS) {
            Token token = currentToken;
            res.registerAdvancement();
            advance();
            ParseResult factor = res.register(_factor());
            if (factor.error()) {
                return res;
            }
            res.state = Either.success(new Nodes.UnaryOpNode(token, (Node) factor.state.getSuccess()));
            return res;
        }
        return _power();
    }
    public ParseResult _power() {
        ParseResult res = new ParseResult();
        ParseResult left = res.register(_atom());
        if (res.error()) {
            return res;
        }
        while (currentToken.type == TypeToken.POW) {
            Token token = currentToken;
            res.registerAdvancement();
            advance();
            ParseResult right = res.register(_factor());
            if (res.error()) {
                return res;
            }
            res.state = Either.success(new Nodes.BinOpNode((Node) left.state.getSuccess(), token, (Node) right.state.getSuccess()));
        }
        return res;
    }
    public ParseResult _atom() {
        ParseResult res = new ParseResult();
        TypeToken type = currentToken.type;
        if (type == TypeToken.INT || type == TypeToken.FLOAT || type == TypeToken.STRING) {
            Token token = currentToken.copy();
            res.registerAdvancement();
            advance();
            res.state = Either.success(new Nodes.AtomNode(token));
            return res;
        }
        res.state = Either.error(new Errors.InvalidSyntax(currentToken.posStart, currentToken.posEnd, "unexpected " + currentToken.type));
        return res;
    }
}
