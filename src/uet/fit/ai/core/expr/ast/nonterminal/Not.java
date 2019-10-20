package uet.fit.ai.core.expr.ast.nonterminal;

import uet.fit.ai.core.expr.ast.IExpression;

public class Not extends NonTerminal {
    public void setChild(IExpression child) {
        setLeft(child);
    }

    @Override
    public void setRight(IExpression right) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean interpret() {
        return !left.interpret();
    }

    @Override
    public String toString() {
        return String.format("!%s", left);
    }
}
