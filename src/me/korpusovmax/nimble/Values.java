package me.korpusovmax.nimble;

public class Values {
    public static class BaseValue {
        public Position posStart, posEnd;
        public void setPos(Position posStart, Position posEnd) {
            this.posStart = posStart;
            this.posEnd = posEnd;
        }

        public Position getPosStart() {
            return posStart;
        }

        public Position getPosEnd() {
            return posEnd;
        }
    }
    public static class Integer extends BaseValue implements Value {
        public int value = 0;
        public Integer (int value) {
            this.value = value;
        }

        public Either addedTo(Value val) {
            if (val instanceof Values.Integer) {
                Values.Integer newValue = new Values.Integer(value + ((Integer)val).value);
                newValue.setPos(getPosStart(), val.getPosEnd());
                return Either.success(newValue);
            }
            if (val instanceof Values.Float) {
                Values.Float newValue = new Values.Float(value + ((Float)val).value);
                newValue.setPos(getPosStart(), val.getPosEnd());
                return Either.success(newValue);
            }
            return Either.error(new Errors.RuntimeError(getPosStart(), val.getPosEnd(), "Illegal Operation"));
        }
        public Either subbedBy(Value val) {
            if (val instanceof Values.Integer) {
                Values.Integer newValue = new Values.Integer(value - ((Integer)val).value);
                newValue.setPos(getPosStart(), val.getPosEnd());
                return Either.success(newValue);
            }
            if (val instanceof Values.Float) {
                Values.Float newValue = new Values.Float(value - ((Float)val).value);
                newValue.setPos(getPosStart(), val.getPosEnd());
                return Either.success(newValue);
            }
            return Either.error(new Errors.RuntimeError(getPosStart(), val.getPosEnd(), "Illegal Operation"));
        }
        public Either multedBy(Value val) {
            if (val instanceof Values.Integer) {
                Values.Integer newValue = new Values.Integer(value * ((Integer)val).value);
                newValue.setPos(getPosStart(), val.getPosEnd());
                return Either.success(newValue);
            }
            if (val instanceof Values.Float) {
                Values.Float newValue = new Values.Float(value * ((Float)val).value);
                newValue.setPos(getPosStart(), val.getPosEnd());
                return Either.success(newValue);
            }
            return Either.error(new Errors.RuntimeError(getPosStart(), val.getPosEnd(), "Illegal Operation"));
        }
        public Either dividedBy(Value val) {
            if (val instanceof Values.Integer) {
                Values.Float newValue = new Values.Float(value / (double)((Integer)val).value);
                newValue.setPos(getPosStart(), val.getPosEnd());
                return Either.success(newValue);
            }
            if (val instanceof Values.Float) {
                Values.Float newValue = new Values.Float(value / ((Float)val).value);
                newValue.setPos(getPosStart(), val.getPosEnd());
                return Either.success(newValue);
            }
            return Either.error(new Errors.RuntimeError(getPosStart(), val.getPosEnd(), "Illegal Operation"));
        }
        public Either powedBy(Value val) {
            if (val instanceof Values.Integer) {
                Values.Float newValue = new Values.Float(Math.pow(value, ((Integer)val).value));
                newValue.setPos(getPosStart(), val.getPosEnd());
                return Either.success(newValue);
            }
            if (val instanceof Values.Float) {
                Values.Float newValue = new Values.Float(Math.pow(value, ((Float)val).value));
                newValue.setPos(getPosStart(), val.getPosEnd());
                return Either.success(newValue);
            }
            return Either.error(new Errors.RuntimeError(getPosStart(), val.getPosEnd(), "Illegal Operation"));
        }

        public String toString() {
            return "" + value;
        }
    }
    public static class Float extends BaseValue implements Value {
        public double value;
        public Float (double value) {
            this.value = value;
        }

        public Either addedTo(Value val) {
            if (val instanceof Values.Integer) {
                Values.Float newValue = new Values.Float(value + ((Integer)val).value);
                newValue.setPos(getPosStart(), val.getPosEnd());
                return Either.success(newValue);
            }
            if (val instanceof Values.Float) {
                Values.Float newValue = new Values.Float(value + ((Float)val).value);
                newValue.setPos(getPosStart(), val.getPosEnd());
                return Either.success(newValue);
            }
            return Either.error(new Errors.RuntimeError(getPosStart(), val.getPosEnd(), "Illegal Operation"));
        }
        public Either subbedBy(Value val) {
            if (val instanceof Values.Integer) {
                Values.Float newValue = new Values.Float(value - ((Integer)val).value);
                newValue.setPos(getPosStart(), val.getPosEnd());
                return Either.success(newValue);
            }
            if (val instanceof Values.Float) {
                Values.Float newValue = new Values.Float(value - ((Float)val).value);
                newValue.setPos(getPosStart(), val.getPosEnd());
                return Either.success(newValue);
            }
            return Either.error(new Errors.RuntimeError(getPosStart(), val.getPosEnd(), "Illegal Operation"));
        }
        public Either multedBy(Value val) {
            if (val instanceof Values.Integer) {
                Values.Float newValue = new Values.Float(value * ((Integer)val).value);
                newValue.setPos(getPosStart(), val.getPosEnd());
                return Either.success(newValue);
            }
            if (val instanceof Values.Float) {
                Values.Float newValue = new Values.Float(value * ((Float)val).value);
                newValue.setPos(getPosStart(), val.getPosEnd());
                return Either.success(newValue);
            }
            return Either.error(new Errors.RuntimeError(getPosStart(), val.getPosEnd(), "Illegal Operation"));
        }
        public Either dividedBy(Value val) {
            if (val instanceof Values.Integer) {
                Values.Float newValue = new Values.Float(value / ((Integer)val).value);
                newValue.setPos(getPosStart(), val.getPosEnd());
                return Either.success(newValue);
            }
            if (val instanceof Values.Float) {
                Values.Float newValue = new Values.Float(value / ((Float)val).value);
                newValue.setPos(getPosStart(), val.getPosEnd());
                return Either.success(newValue);
            }
            return Either.error(new Errors.RuntimeError(getPosStart(), val.getPosEnd(), "Illegal Operation"));
        }
        public Either powedBy(Value val) {
            if (val instanceof Values.Integer) {
                Values.Float newValue = new Values.Float(Math.pow(value, ((Integer)val).value));
                newValue.setPos(getPosStart(), val.getPosEnd());
                return Either.success(newValue);
            }
            if (val instanceof Values.Float) {
                Values.Float newValue = new Values.Float(Math.pow(value, ((Float)val).value));
                newValue.setPos(getPosStart(), val.getPosEnd());
                return Either.success(newValue);
            }
            return Either.error(new Errors.RuntimeError(getPosStart(), val.getPosEnd(), "Illegal Operation"));
        }

        public String toString() {
            return "" + value;
        }
    }
}
