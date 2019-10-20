package uet.fit.ai.core;

import uet.fit.ai.core.expr.Lexer;
import uet.fit.ai.core.expr.ast.IExpression;
import uet.fit.ai.core.expr.parser.RecursiveDescentParser;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        List<IExpression> premises = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter number of premises: ");
        int size = scanner.nextInt();
        scanner.nextLine();

        System.out.println("\n[Enter premises]");

        for (int i = 0; i < size; i++) {
            String expr = scanner.nextLine();
            Lexer lexer = new Lexer(new ByteArrayInputStream(expr.getBytes()));
            RecursiveDescentParser parser = new RecursiveDescentParser(lexer);
            premises.add(parser.build());
        }

        System.out.println("\n[Enter proof]");
        String[] proofs = scanner.nextLine().split(",");

        List<IExpression> proofExpr = new ArrayList<>();
        for (String proof : proofs) {
            Lexer lexer = new Lexer(new ByteArrayInputStream(proof.getBytes()));
            RecursiveDescentParser parser = new RecursiveDescentParser(lexer);
            proofExpr.add(parser.build());
        }

        System.out.println(new Robinson().exec(premises, proofExpr));
    }
}
