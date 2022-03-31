package me.korpusovmax.nimble;

import java.util.ArrayList;

public class Errors {
    public static class IllegalCharError extends Error {
        public IllegalCharError(Position posStart, Position posEnd, String details) {
            this.posEnd = posEnd;
            this.posStart = posStart;
            this.details = details;
            this.errorName = "Illegal Character";
        }
    }
    public static class InvalidSyntax extends Error {
        public InvalidSyntax (Position posStart, Position posEnd, String details) {
            this.posEnd = posEnd;
            this.posStart = posStart;
            this.details = details;
            this.errorName = "InvalidSyntax";
        }
    }
    public static class RuntimeError extends Error {
        public RuntimeError (Position posStart, Position posEnd, String details) {
            this.posEnd = posEnd;
            this.posStart = posStart;
            this.details = details;
            this.errorName = "RuntimeError";
        }
    }
}
