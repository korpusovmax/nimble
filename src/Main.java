import me.korpusovmax.nimble.*;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class Main {
    public static void main(String args[]) throws IOException {
        String code = Files.readString(Path.of("src/main.imb"), Charset.defaultCharset());
        System.out.println("Lexer:");
        Either lexerResult = new Lexer(code).generateTokens();
        if (lexerResult.error()) {
            System.out.println((lexerResult.getError()).toString());
        } else {
            System.out.println((ArrayList<Token>)lexerResult.getSuccess());
            System.out.println("\nParser:");
            ParseResult parseResult = new Parser((ArrayList<Token>)lexerResult.getSuccess()).parse();
            Either r = parseResult.state;
            if (r.error()) {
                System.out.println(r.getError().toString());
            } else {
                System.out.println((Node)r.getSuccess());
                System.out.println("\nInterpreter:");
                Interpreter inter = new Interpreter();
                inter.m.put("pi", new Values.Float(3.1415f));
                Either runtimeResult = inter.visitNode((Node) r.getSuccess());
                if (runtimeResult.error()) {
                    //System.out.println(((me.korpusovmax.nimble.Error)runtimeResult.getError()).details);
                    System.out.println(runtimeResult.getError());
                } else {
                    System.out.println(runtimeResult.getSuccess());
                }
            }
        }
    }
}
