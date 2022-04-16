package me.korpusovmax.nimble;

import java.util.ArrayList;

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

        public Either posedValue(BaseValue nw, Value val) {
            nw.setPos(getPosStart(), val.getPosEnd());
            return Either.success(nw);
        }
    }
    public static class Integer extends BaseValue implements Value {
        public int value;
        public Integer (int value) {
            this.value = value;
        }

        public Either addedTo(Value val) {
            if (val instanceof Values.Integer) {
                Values.Integer newValue = new Values.Integer(value + ((Integer)val).value);
                return posedValue(newValue, val);
            }
            if (val instanceof Values.Float) {
                Values.Float newValue = new Values.Float(value + ((Float)val).value);
                return posedValue(newValue, val);
            }
            return Either.error(new Errors.RuntimeError(getPosStart(), val.getPosEnd(), "Illegal Operation"));
        }
        public Either subbedBy(Value val) {
            if (val instanceof Values.Integer) {
                Values.Integer newValue = new Values.Integer(value - ((Integer)val).value);
                return posedValue(newValue, val);
            }
            if (val instanceof Values.Float) {
                Values.Float newValue = new Values.Float(value - ((Float)val).value);
                return posedValue(newValue, val);
            }
            return Either.error(new Errors.RuntimeError(getPosStart(), val.getPosEnd(), "Illegal Operation"));
        }
        public Either multedBy(Value val) {
            if (val instanceof Values.Integer) {
                Values.Integer newValue = new Values.Integer(value * ((Integer)val).value);
                return posedValue(newValue, val);
            }
            if (val instanceof Values.Float) {
                Values.Float newValue = new Values.Float(value * ((Float)val).value);
                return posedValue(newValue, val);
            }
            return Either.error(new Errors.RuntimeError(getPosStart(), val.getPosEnd(), "Illegal Operation"));
        }
        public Either dividedBy(Value val) {
            if (val instanceof Values.Integer) {
                Values.Float newValue = new Values.Float(value / (double)((Integer)val).value);
                return posedValue(newValue, val);
            }
            if (val instanceof Values.Float) {
                Values.Float newValue = new Values.Float(value / ((Float)val).value);
                return posedValue(newValue, val);
            }
            return Either.error(new Errors.RuntimeError(getPosStart(), val.getPosEnd(), "Illegal Operation"));
        }
        public Either powedBy(Value val) {
            if (val instanceof Values.Integer) {
                Values.Float newValue = new Values.Float(Math.pow(value, ((Integer)val).value));
                return posedValue(newValue, val);
            }
            if (val instanceof Values.Float) {
                Values.Float newValue = new Values.Float(Math.pow(value, ((Float)val).value));
                return posedValue(newValue, val);
            }
            return Either.error(new Errors.RuntimeError(getPosStart(), val.getPosEnd(), "Illegal Operation"));
        }

        public java.lang.String toString() {
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
                return posedValue(newValue, val);
            }
            if (val instanceof Values.Float) {
                Values.Float newValue = new Values.Float(value + ((Float)val).value);
                return posedValue(newValue, val);
            }
            return Either.error(new Errors.RuntimeError(getPosStart(), val.getPosEnd(), "Illegal Operation"));
        }
        public Either subbedBy(Value val) {
            if (val instanceof Values.Integer) {
                Values.Float newValue = new Values.Float(value - ((Integer)val).value);
                return posedValue(newValue, val);
            }
            if (val instanceof Values.Float) {
                Values.Float newValue = new Values.Float(value - ((Float)val).value);
                return posedValue(newValue, val);
            }
            return Either.error(new Errors.RuntimeError(getPosStart(), val.getPosEnd(), "Illegal Operation"));
        }
        public Either multedBy(Value val) {
            if (val instanceof Values.Integer) {
                Values.Float newValue = new Values.Float(value * ((Integer)val).value);
                return posedValue(newValue, val);
            }
            if (val instanceof Values.Float) {
                Values.Float newValue = new Values.Float(value * ((Float)val).value);
                return posedValue(newValue, val);
            }
            return Either.error(new Errors.RuntimeError(getPosStart(), val.getPosEnd(), "Illegal Operation"));
        }
        public Either dividedBy(Value val) {
            if (val instanceof Values.Integer) {
                Values.Float newValue = new Values.Float(value / ((Integer)val).value);
                return posedValue(newValue, val);
            }
            if (val instanceof Values.Float) {
                Values.Float newValue = new Values.Float(value / ((Float)val).value);
                return posedValue(newValue, val);
            }
            return Either.error(new Errors.RuntimeError(getPosStart(), val.getPosEnd(), "Illegal Operation"));
        }
        public Either powedBy(Value val) {
            if (val instanceof Values.Integer) {
                Values.Float newValue = new Values.Float(Math.pow(value, ((Integer)val).value));
                return posedValue(newValue, val);
            }
            if (val instanceof Values.Float) {
                Values.Float newValue = new Values.Float(Math.pow(value, ((Float)val).value));
                return posedValue(newValue, val);
            }
            return Either.error(new Errors.RuntimeError(getPosStart(), val.getPosEnd(), "Illegal Operation"));
        }

        public java.lang.String toString() {
            return "" + value;
        }
    }
    public static class String extends BaseValue implements Value {
        public java.lang.String value;
        public String(java.lang.String value) {
            this.value = value;
        }

        public Either addedTo(Value val) {
            if (val instanceof String) {
                Values.String newValue = new Values.String(value + ((String) val).value);
                return posedValue(newValue, val);
            }
            return Either.error(new Errors.RuntimeError(getPosStart(), val.getPosEnd(), "Illegal Operation"));
        }

        public java.lang.String toString() {
            return value;
        }
    }
    public static class List extends BaseValue implements Value {
        public ArrayList<Value> elements;

        public List(ArrayList<Value> elements) {
            this.elements = elements;
        }

        @Override
        public java.lang.String toString() {
            java.lang.String result = "[";
            for (Value i : elements) {
                result = result + i.toString() + ", ";
            }
            if (elements.size() > 0) {
                result = result.substring(0, result.length() - 2);
            }
            return result + "]";
        }
    }
}
