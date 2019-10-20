package uet.fit.ai.core.truth;

import uet.fit.ai.core.expr.ast.IExpression;
import uet.fit.ai.core.expr.ast.terminal.Variable;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TruthTable extends ArrayList<Interpretation> {

    public static TruthTable generate(IExpression expr) {
        TruthTable table = new TruthTable();
        table.recursiveGenTable(table, expr, 0);
        return table;
    }

    private void recursiveGenTable(TruthTable table, IExpression expr, int index) {
        /*
         * Generate all value for variables.
         */
        if (index == expr.getVariables().size()) {
            /*
             * Get all value of variables when truth value equals to ZERO.
             * Generate term and add into list.
             */
            table.add(genInterpretation(expr));
            return;
        }

        /*
         * Get variable.
         * Update value 0 or 1 and recursive.
         */
        Variable var = expr.getVariables().get(index);

        var.value = false;
        recursiveGenTable(table, expr, index + 1);

        var.value = true;
        recursiveGenTable(table, expr, index + 1);
    }

    private Interpretation genInterpretation(IExpression expr) {
        Interpretation interpret = new Interpretation();
        for (Variable var : expr.getVariables())
            interpret.add((Variable) var.clone());
        Variable result = new Variable(' ');
        result.value = expr.interpret();
        interpret.add(result);
        return interpret;
    }

    public List<Interpretation> genFalseList() {
        return this.stream().filter(x -> !x.getResult().value).collect(Collectors.toList());
    }
}
