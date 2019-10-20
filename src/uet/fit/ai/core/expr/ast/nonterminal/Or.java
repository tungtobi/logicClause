package uet.fit.ai.core.expr.ast.nonterminal;

public class Or extends NonTerminal {
    @Override
    public boolean interpret() {
        return left.interpret() || right.interpret();
    }

    @Override
    public String toString() {
        return String.format("(%s | %s)", left, right);
    }
}
