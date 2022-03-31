package me.korpusovmax.nimble;

public class Nodes {
    public static class AtomNode extends Node {
        public Token token;
        public AtomNode(Token tok) {
            token = tok;
            posStart = token.posStart;
            posEnd = token.posEnd;
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
            posStart = leftNode.getPosStart();
            posEnd = rightNode.getPosEnd();
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
            posStart = token.posStart;
            posEnd = node.getPosEnd();
        }

        @Override
        public String toString() {
            return "(" + token.toString() + " " + node.toString() + ")";
        }
    }
}
