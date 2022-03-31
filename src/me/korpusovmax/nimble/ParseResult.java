package me.korpusovmax.nimble;

public class ParseResult {
    public Either state; //the parsing result (ast (Node)/error)
    private int advanceCount, reverseCount;
    public ParseResult() {
        state = Either.error(null);
    }
    public void registerAdvancement() {
        advanceCount++;
    }
    public ParseResult register(ParseResult res) {
        state = res.state;
        advanceCount = advanceCount + res.advanceCount;
        return res;
    }
    public ParseResult tryRegister (ParseResult res, ParseResult l) {
        if (res.state.error()) {
            l.reverseCount = res.advanceCount;
            return res;
        }
        return l.register(res);
    }

    public Boolean error() {
        return state.error();
    }
}
