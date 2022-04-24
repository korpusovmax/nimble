package me.korpusovmax.nimble;

public enum TypeToken {
    INT("int"),
    FLOAT("float"),
    STRING("string"),
    ID("<ID>"),
    KEYWORD("<KEYWORD>"),
    PLUS("'+'"),
    MINUS("'-'"),
    MUL("'*'"),
    DIV("'/'"),
    POW("'**'"),
    EQ("'='"),
    PE("+="),
    ME("-="),
    LPAREN("'('"),
    RPAREN("')'"),
    LBRACE("'{'"),
    RBRACE("'}'"),
    LSQUARE("'['"),
    RSQUARE("']'"),
    COMA("','"),
    DOT("'.'"),
    AND("'&&'"),
    OR("'||'"),
    NOT("'!'"),
    EE("'=='"),
    NE("'!='"),
    LT("'<'"),
    GT("'>'"),
    LTE("'<='"),
    GTE("'>='"),
    NEWLINE("<NEWLINE>"),
    EOF("<EOF>");

    String name;
    TypeToken (String name) {
        this.name = name;
    }

    @Override
    public String toString () {
        return name;
    }
}
