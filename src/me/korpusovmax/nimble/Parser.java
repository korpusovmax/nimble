package me.korpusovmax.nimble;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;

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
    public Token reverse(int cnt) {
        tokenIndex = tokenIndex - cnt - 1;
        advance();
        return currentToken;
    }
    public void skipLines() {
        ParseResult res = new ParseResult();
        while (currentToken.type == TypeToken.NEWLINE) {
            res.registerAdvancement();
            advance();
        }
    }

    public ParseResult parse() {
        ParseResult res = _statements(true);
        return res;
    }
    //reading nodes
    public ParseResult _statements(Boolean global) {
        ParseResult res = new ParseResult();
        ArrayList<Node> statements = new ArrayList<>();

        skipLines();
        ParseResult statement = res.register(_expr());
        if (res.error()) {
            return res;
        }
        statements.add((Node) statement.state.getSuccess());
        Boolean moreState = true;
        while (true) {
            ParseResult local = new ParseResult();
            while (currentToken.type == TypeToken.NEWLINE) {
                res.registerAdvancement();
                advance();
                moreState = true;
            }
            if ((currentToken.type == TypeToken.EOF) || (currentToken.type == TypeToken.RBRACE && !global)) {
                res.state = Either.success(new Nodes.ListNode(statements));
                return res;
            }
            if (!moreState) {
                break;
            }
            ParseResult statement2 = res.tryRegister(_expr(), res);
            if (statement2.error()) {
                reverse(res.reverseCount);
                moreState = false;
            } else {
                statements.add((Node) statement2.state.getSuccess());
            }
        }
        skipLines();
        res.state = Either.success(new Nodes.ListNode(statements));
        return res;
    }
    public ParseResult _expr() {
        ParseResult res = new ParseResult();
        if (currentToken.type == TypeToken.ID) {
            int idx = tokenIndex; //for backward move
            ParseResult varRes = res.register(_id());
            if (res.error()) {
                return res;
            }
            Nodes.IdNode var = (Nodes.IdNode) varRes.state.getSuccess();
            TypeToken type = currentToken.type;
            Token operation = currentToken.copy();
            if (type == TypeToken.EQ || type == TypeToken.PE || type == TypeToken.ME) {
                res.registerAdvancement();
                advance();
                ParseResult exprRes = res.register(_expr());
                if (res.error()) {
                    return res;
                }
                res.state = Either.success(new Nodes.VarAssigmentNode(var, operation, (Node) exprRes.state.getSuccess()));
                return res;
            }
            //backward
            tokenIndex = idx - 1;
            res.registerAdvancement();
            advance();
        }
        ParseResult left = res.register(_comparisonExpr());
        if (res.error()) {
            return res;
        }
        while (currentToken.type == TypeToken.AND || currentToken.type == TypeToken.OR) {
            Token tok = currentToken.copy();
            res.registerAdvancement();
            advance();
            ParseResult right = res.register(_comparisonExpr());
            if (res.error()) {
                return res;
            }
            left.state = Either.success(new Nodes.BinOpNode((Node) left.state.getSuccess(), tok, (Node) right.state.getSuccess()));
        }
        return res.register(left);
    }
    public ParseResult _comparisonExpr() {
        ParseResult res = new ParseResult();
        if (currentToken.type == TypeToken.NOT) {
            Token tok = currentToken.copy();
            res.registerAdvancement();
            advance();
            ParseResult notted = res.register(_comparisonExpr());
            if (res.error()) {
                return res;
            }
            res.state = Either.success(new Nodes.UnaryOpNode(tok, (Node) notted.state.getSuccess()));
            return res;
        }
        ParseResult left = res.register(_arithExpr());
        if (res.error()) {
            return res;
        }
        ArrayList<TypeToken> types = new ArrayList<TypeToken>(Arrays.asList(
                TypeToken.EE,
                TypeToken.NE,
                TypeToken.GT,
                TypeToken.LT,
                TypeToken.GTE,
                TypeToken.LTE
        ));
        while (types.contains(currentToken.type)){
            Token tok = currentToken.copy();
            ParseResult right = res.register(_arithExpr());
            if (res.error()) {
                return res;
            }
            left.state = Either.success(new Nodes.BinOpNode((Node) left.state.getSuccess(), tok, (Node) right.state.getSuccess()));
        }
        res.state = Either.success((Node) left.state.getSuccess());
        return res;
    }
    public ParseResult _arithExpr() {
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
        TypeToken type = currentToken.copy().type;
        if (type == TypeToken.ID) {
            res = res.register(_id());
            if (res.error()) {
                return res;
            }
            res.state = Either.success(new Nodes.VarAccessNode((Nodes.IdNode) res.state.getSuccess()));
            return res;
        }
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

    //help-readers
    public ParseResult _id() {
        ParseResult res = new ParseResult();
        ArrayList<Token> names = new ArrayList<>();
        while (currentToken.type == TypeToken.ID) {
            names.add(currentToken.copy());
            res.registerAdvancement();
            advance();
            if (currentToken.type == TypeToken.DOT) {
                res.registerAdvancement();
                advance();
                if (currentToken.type == TypeToken.ID) {
                    continue;
                }
                res.state = Either.error(new Errors.InvalidSyntax(currentToken.posStart, currentToken.posEnd, "Expected <id>"));
                return res;
            }
            break;
        }
        if (names.size() > 0) {
            res.state = Either.success(new Nodes.IdNode(names));
            return res;
        }
        res.state = Either.error(new Errors.InvalidSyntax(currentToken.posStart, currentToken.posEnd, "Expected <id>"));
        return res;
    }
    //TODO (maybe) binary node function (for comparison, arith, term, factors)
}

