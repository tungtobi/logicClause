package uet.fit.ai.core.pos;

import uet.fit.ai.core.expr.ast.IExpression;
import uet.fit.ai.core.expr.ast.terminal.Variable;
import uet.fit.ai.core.truth.Interpretation;
import uet.fit.ai.core.truth.TruthTable;
import uet.fit.ai.core.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class TermGeneration {
    /**
     * Generate term in format POS from a list of expression.
     *
     * @param exps list of expression.
     * @return list of term in format POS.
     */
    public List<Term> exec(List<IExpression> exps) {
        List<Term> maxterms = new ArrayList<>();

        for (IExpression exp : exps) {
            for (Term maxterm : exec(exp))
                if (!maxterms.contains(maxterm))
                    maxterms.add(maxterm);
        }

        return maxterms;
    }

    /**
     * Generate term in format POS from a expression.
     *
     * @param expr - expression.
     * @return list of term in format POS.
     */
    public List<Term> exec(IExpression expr) {
        List<Term> maxterms = genMaxterms(TruthTable.generate(expr));

        maxterms = new Simplification().exec(maxterms);

        System.out.println(expr + " = " + StringUtils.termsToString(maxterms));

        return maxterms;
    }

    private Term genMaxterm(Interpretation interpret) {
        String maxterm = "";
        for (int i = 0; i < interpret.size() - 1; i++) {
            Variable var = interpret.get(i);
            /*
             * Add variable
             * (uppercase for negative, lowercase for positive).
             */
            char newVar;
            if (!var.value)
                newVar = Character.toLowerCase(var.name);
            else // if (val == true)
                newVar = Character.toUpperCase(var.name);

            if (maxterm.indexOf(newVar) < 0)
                maxterm += newVar;
        }

        return new Term(maxterm);
    }

    private List<Term> genMaxterms(TruthTable table) {
        List<Term> terms = new ArrayList<>();
        for (Interpretation interpret : table.genFalseList())
            terms.add(genMaxterm(interpret));
        return terms;
    }
}
