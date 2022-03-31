package me.korpusovmax.nimble;

public interface Value {
    String toString();
    Position getPosStart();
    Position getPosEnd();

    default Either addedTo(Value val) {
        return getRTError(val);
    }
    default Either subbedBy(Value val) {
        return getRTError(val);
    }
    default Either multedBy(Value val) {
        return getRTError(val);
    }
    default Either dividedBy(Value val) {
        return getRTError(val);
    }
    default Either powedBy(Value val) {
        return getRTError(val);
    }

    default Either getRTError(Value val) {
        return Either.error(new Errors.RuntimeError(this.getPosStart(), val.getPosEnd(), "Illegal Operation"));
    }
}