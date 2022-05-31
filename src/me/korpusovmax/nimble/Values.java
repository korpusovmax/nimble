package me.korpusovmax.nimble;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Function;

public class Values {
    public static class BaseValue {
        private java.lang.String type;
        public HashMap<java.lang.String, Function> fields;
        private Position posStart, posEnd;

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

        public void setType(java.lang.String type) {
            this.type = type;
        }
        public java.lang.String getType() {
            return type;
        }
    }
    public static class Integer extends BaseValue implements Value {
        public int value;
        public Integer (int value) {
            this.value = value;
            setType("Integer");
            fields = new HashMap<>();

            Function<Value, Either> f = x -> posedValue(
                    new Values.Integer(value + ((Values.Integer) x).value
                    ), x);
            fields.put(TypeToken.PLUS.name, f);
        }
        //old code:
        /*public Either addedTo(Value val) {
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
                Values.Float newValue = new Values.Float(value / (float)((Integer)val).value);
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
                Values.Float newValue = new Values.Float((float) Math.pow(value, ((Integer)val).value));
                return posedValue(newValue, val);
            }
            if (val instanceof Values.Float) {
                Values.Float newValue = new Values.Float((float) Math.pow(value, ((Float)val).value));
                return posedValue(newValue, val);
            }
            return Either.error(new Errors.RuntimeError(getPosStart(), val.getPosEnd(), "Illegal Operation"));
        }*/

        public java.lang.String toString() {
            return "" + value;
        }
    }
    public static class Float extends BaseValue implements Value {
        public float value;
        public Float (float value) {
            this.value = value;
            setType("Float");
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
                Values.Float newValue = new Values.Float((float) Math.pow(value, ((Integer)val).value));
                return posedValue(newValue, val);
            }
            if (val instanceof Values.Float) {
                Values.Float newValue = new Values.Float((float) Math.pow(value, ((Float)val).value));
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
            setType("String");
        }

        public Either addedTo(Value val) {
            Values.String newValue = new Values.String(value + val);
            return posedValue(newValue, val);
        }

        public java.lang.String toString() {
            return value;
        }
    }
    public static class List extends BaseValue implements Value {
        public ArrayList<Value> elements;

        public List(ArrayList<Value> elements) {
            this.elements = elements;
            setType("List");
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
    public static class Table extends BaseValue implements Value {
        public HashMap<java.lang.String, Value> value;

        public Table() {
            this.value = new HashMap<>();
            setType("Table");
        }
        public Table(HashMap<java.lang.String, Value> map) {
            value = map;
        }

        public Either get(java.lang.String key) {
            try {
                return Either.success(value.get(key));
            } catch (Exception e) {
                return Either.error(new Errors.RuntimeError(getPosStart(), getPosEnd(), "no such key: " + key));
            }
        }

        @Override
        public java.lang.String toString() {
            java.lang.String result = "{";
            java.lang.String[] keys = value.keySet().toArray(java.lang.String[]::new);
            for (java.lang.String i : keys) {
                result = result + i.toString() + ": " + value.get(i) + ", ";
            }
            if (keys.length > 0) {
                result = result.substring(0, result.length() - 2);
            }
            return result + "}";
        }
    }
}

