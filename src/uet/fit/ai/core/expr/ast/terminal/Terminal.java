package uet.fit.ai.core.expr.ast.terminal;

import uet.fit.ai.core.expr.ast.IExpression;
import uet.fit.ai.core.expr.ast.nonterminal.Not;

/**
 * Leaf in the abstract syntax tree.
 * This type of node doesn't have any child.
 */
public abstract class Terminal implements IExpression {
    public boolean value;

    public String toString() {
        return String.format("%s", value);
    }

    @Override
    public IExpression getNegative() {
        Not not = new Not();
        not.setChild(this);
        return not;
    }
}
