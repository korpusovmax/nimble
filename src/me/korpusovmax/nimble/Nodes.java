package me.korpusovmax.nimble;

import java.util.ArrayList;

public class Nodes {
    public static class IdNode extends Node {
        public ArrayList<Token> names;
        public IdNode(ArrayList<Token> names) {
            this.names = names;
            //maybe here (copy)
            setPos(
                    names.get(0).posStart.copy(),
                    names.get(names.size() - 1).posEnd.copy()
            );
        }

        @Override
        public String toString() {
            java.lang.String result = "";
            for (Token i : names) {
                result = result + i.value + ".";
            }
            if (names.size() > 0) {
                result = result.substring(0, result.length() - 1);
            }
            return result;
        }
    }
    public static class AtomNode extends Node {
        public Token token;
        public AtomNode(Token tok) {
            token = tok;
            setPos(token.posStart, token.posEnd);
        }

        @Override
        public String toString() {
            return token.toString();
        }
    }
    public static class BinOpNode extends Node {
        public Token operation;
        public Node leftNode, rightNode;
        public BinOpNode (Node left, Token operation, Node right) {
            this.operation = operation;
            leftNode = left;
            rightNode = right;
            setPos(leftNode.posStart, rightNode.posEnd);
        }

        @Override
        public String toString() {
            return "(" + leftNode.toString() + " " + operation.toString() + " " + rightNode.toString() + ")";
        }
    }
    public static class UnaryOpNode extends Node {
        private Token token;
        private Node node;
        public UnaryOpNode(Token token, Node node) {
            this.node = node;
            this.token = token;
            setPos(token.posStart, node.posEnd);
        }

        @Override
        public String toString() {
            return "(" + token.toString() + " " + node.toString() + ")";
        }
    }
    public static class ListNode extends Node {
        public ArrayList<Node> elements;
        public ListNode(ArrayList<Node> elements) {
            this.elements = elements;
            //maybe here (copy)
            posStart = elements.get(0).posStart.copy();
            posStart.idx = posStart.idx - 2;
            posStart.advance();
            //maybe here (copy)
            posEnd = elements.get(elements.size() - 1).posEnd.advance().copy();
        }

        @Override
        public String toString() {
            java.lang.String result = "[";
            for (Node i : elements) {
                result = result + i.toString() + ", ";
            }
            if (elements.size() > 0) {
                result = result.substring(0, result.length() - 2);
            }
            return result + "]";
        }
    }
    public static class VarAssigmentNode extends Node {
        public IdNode var;
        public Node value;
        public Token operation;
        public VarAssigmentNode(IdNode var, Token tok, Node value) {
            this.var = var;
            this.value = value;
            this.operation = tok;
            this.posStart = var.posStart.copy();
            this.posEnd = value.posEnd.copy();
        }

        @Override
        public String toString() {
            return var.toString() + " " + operation.toString() + " " + value.toString();
        }
    }
    public static class VarAccessNode extends Node {
        public IdNode var;
        public VarAccessNode(IdNode var) {
            this.var = var;
            this.posStart = var.posStart.copy();
            this.posEnd = var.posEnd.copy();
        }
    }
}

