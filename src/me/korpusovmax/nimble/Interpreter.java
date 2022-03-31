package me.korpusovmax.nimble;

import java.util.HashMap;

public class Interpreter {
    public Either visitNode(Node nodeToVisit) {
        try {
            java.lang.reflect.Method method = this.getClass().getMethod("visit" + nodeToVisit.getClass().getSimpleName(), nodeToVisit.getClass());
            return (Either) method.invoke(this, nodeToVisit);
        } catch (Exception e) {}
        return Either.error(new Errors.RuntimeError(nodeToVisit.getPosEnd(), nodeToVisit.getPosEnd(), "no such node:(\n" + nodeToVisit.getClass().getSimpleName()));
    }

    public Either visitAtomNode(Nodes.AtomNode node) {
        if (node.token.type == TypeToken.INT) {
            Values.Integer value = new Values.Integer(Integer.parseInt(node.token.value));
            value.setPos(node.getPosStart(), node.getPosEnd());
            return Either.success(value);
        } else if (node.token.type == TypeToken.FLOAT){
            Values.Float value = new Values.Float((float) Double.parseDouble(node.token.value));
            value.setPos(node.getPosStart(), node.getPosEnd());
            return Either.success(value);
        } else {
            Values.String value = new Values.String((node.token.value).toString());
            value.setPos(node.getPosStart(), node.getPosEnd());
            return Either.success(value);
        }
    }
    public Either visitBinOpNode(Nodes.BinOpNode node) {
        HashMap<String, String> funcs = new HashMap<>();
        funcs.put(TypeToken.PLUS.name, "addedTo");
        funcs.put(TypeToken.MINUS.name, "subbedBy");
        funcs.put(TypeToken.MUL.name, "multedBy");
        funcs.put(TypeToken.DIV.name, "dividedBy");
        funcs.put(TypeToken.POW.name, "powedBy");

        Either leftState = visitNode(node.leftNode);
        if (leftState.error()) {
            return leftState;
        }
        Either rightState = visitNode(node.rightNode);
        if (rightState.error()) {
            return rightState;
        }

        Either result = null;
        try {
            java.lang.reflect.Method method = leftState.getSuccess().getClass().getMethod(funcs.get(node.operation.type.name), Value.class);
            result = (Either) method.invoke(leftState.getSuccess(), rightState.getSuccess());
        } catch (Exception e) {}
        if (result.error()) {
            return result;
        }
        Values.BaseValue val = (Values.BaseValue) result.getSuccess();
        val.posStart = node.posStart;
        val.posEnd = node.posEnd;
        return Either.success(val);
    }
}
