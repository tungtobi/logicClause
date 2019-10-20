package uet.fit.ai.core.expr.ast.nonterminal;

import uet.fit.ai.core.utils.VariableList;
import uet.fit.ai.core.expr.ast.IExpression;
import uet.fit.ai.core.expr.ast.terminal.Variable;

import java.util.List;

public abstract class NonTerminal implements IExpression {
    protected IExpression left, right;

    public void setLeft(IExpression left) {
        this.left = left;
    }

    public void setRight(IExpression right) {
        this.right = right;
    }

    @Override
    public List<Variable> getVariables() {
        List<Variable> list = new VariableList();

        if (left != null)
            list.addAll(left.getVariables());
        if (right != null)
            list.addAll(right.getVariables());

        return list;
    }

    @Override
    public IExpression getNegative() {
        Not not = new Not();
        not.setChild(this);
        return not;
    }
}
