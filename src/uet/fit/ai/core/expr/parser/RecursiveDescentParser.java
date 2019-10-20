package uet.fit.ai.core.expr.parser;

import uet.fit.ai.core.expr.Lexer;
import uet.fit.ai.core.expr.ast.IExpression;
import uet.fit.ai.core.expr.ast.nonterminal.And;
import uet.fit.ai.core.expr.ast.nonterminal.Implies;
import uet.fit.ai.core.expr.ast.nonterminal.Not;
import uet.fit.ai.core.expr.ast.nonterminal.Or;
import uet.fit.ai.core.expr.ast.terminal.Variable;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

public class RecursiveDescentParser {
    private Lexer lexer;
    private int symbol;
    private IExpression root;
    private List<Variable> variables = new ArrayList<>();

    public RecursiveDescentParser(Lexer lexer) {
        this.lexer = lexer;
    }

    public static IExpression parse(String expr) {
        checkWeakValid(expr);
        Lexer lexer = new Lexer(new ByteArrayInputStream(expr.replaceAll(" ", "").getBytes()));
        RecursiveDescentParser parser = new RecursiveDescentParser(lexer);
        return parser.build();
    }

    private static void checkWeakValid(String expr) {
        String tmp = expr.replaceAll("[^()a-z!|&\\-> ]", "#");

        int index = tmp.indexOf('-');

        if (tmp.indexOf('#') > 0 || (index > 0 && tmp.charAt(index + 1) != '>'))
            throw new RuntimeException("Expression Malformed");
    }

    public IExpression build() {
        parse();
        return root;
    }

    private void parse() {
        expression();
        while (symbol == Lexer.IMPLIES) {
            Implies implies = new Implies();
            implies.setLeft(root);
            expression();
            implies.setRight(root);
            root = implies;
        }
    }

    private void expression() {
        term();
        while (symbol == Lexer.OR) {
            Or or = new Or();
            or.setLeft(root);
            term();
            or.setRight(root);
            root = or;
        }
    }

    private void term() {
        factor();
        while (symbol == Lexer.AND) {
            And and = new And();
            and.setLeft(root);
            factor();
            and.setRight(root);
            root = and;
        }
    }

    private void factor() {
        symbol = lexer.nextSymbol();

        if (symbol == Lexer.VARIABLE) { // variables
            Variable var = new Variable(lexer.getVariable());
            if (variables.contains(var)) {
                int index = variables.indexOf(var);
                var = variables.get(index);
            } else
                variables.add(var);
            root = var;
            symbol = lexer.nextSymbol();
        } else if (symbol == Lexer.NOT) {
            Not not = new Not();
            factor();
            not.setChild(root);
            root = not;
        } else if (symbol == Lexer.LEFT) {
            parse();
            symbol = lexer.nextSymbol(); // we don't care about ')'
        } else {
            throw new RuntimeException("Expression Malformed");
        }
    }
}