package uet.fit.ai.core.expr.ast;

import uet.fit.ai.core.expr.ast.terminal.Variable;

import java.util.List;

/**
 * Present a boolean expression
 *
 * or = '|'
 * and = '&'
 * not = '!'
 * implies = '->'
 */
public interface IExpression {
    /**
     * Calculate the boolean value of the expression
     *
     * @return boolean value of the expression
     */
    boolean interpret();

    /**
     * Get all boolean variables in the expression
     *
     * @return list of variable
     */
    List<Variable> getVariables();

    /**
     * Get the negative of current expression
     *
     * @return negative expression
     */
    IExpression getNegative();
}
