package me.korpusovmax.nimble;

import java.util.ArrayList;

public class Error {
    public String errorName, details;
    public Position posStart, posEnd;
    public int[] lines;

    @Override
    public String toString() {
        return errorName + ": " + details + "\n" +
                "line: " + (posStart.ln + 1) + "\n" +
                arrows();
    }

    private String arrows () {
        StringBuilder sb = new StringBuilder();
        int from = posStart.idx;
        int to = posEnd.idx;
        while (from > 0 && from < posStart.ftxt.length()) {
            from--;
            if (posStart.ftxt.charAt(from) == '\n') {
                break;
            }
            sb.append(' ');
        }
        for (int i = posStart.idx; i < posEnd.idx; i++) {
            sb.append('^');
        }
        while (to < posEnd.ftxt.length()) {
            to++;
            if (posStart.ftxt.charAt(from) == '\n') {
                break;
            }
            sb.append(' ');
        }
        if (from < posStart.ftxt.length() && to < posStart.ftxt.length()) {
            return posStart.ftxt.substring(from, to) + "\n" + sb;
        }
        return "";
    }
}
