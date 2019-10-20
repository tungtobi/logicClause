package uet.fit.ai.core.pos;

import uet.fit.ai.core.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class Simplification {
    /**
     * Simplification a list of terms.
     *
     * @param terms given list of terms.
     *
     * @return new simplification list.
     */
    public List<Term> exec(List<Term> terms) {
        while (canSimple(terms)) {
            List<Term> newTerms = new ArrayList<>();
            boolean mark[] = new boolean[terms.size()];

            for (int i = 0; i < terms.size()-1; i++)
                for (int j = i + 1; j < terms.size(); j++) {
                    Term term1 = terms.get(i);
                    Term term2 = terms.get(j);

                    int index = findDiffIndex(term1, term2);
                    if (index >= 0) {
                        String expr = term1.getExpr();
                        expr = expr.replaceAll(expr.charAt(index)+"", " ");
                        Term newTerm = new Term(expr);
                        mark[i] = true;
                        mark[j] = true;
                        if (!newTerms.contains(newTerm))
                            newTerms.add(newTerm);
                    }
                }

            for (int i = 0; i < mark.length; i++)
                if (!mark[i] && !newTerms.contains(terms.get(i)))
                    newTerms.add(terms.get(i));

            terms = newTerms;
        }

        formatExpr(terms);

        return terms;
    }



    private int findDiffIndex(Term term1, Term term2) {
        int index = -1;

        String expr1 = term1.getExpr();
        String expr2 = term2.getExpr();

        if (expr1.length() != expr2.length())
            return index;


        for (int i = 0; i < expr1.length(); i++) {
            if (expr1.charAt(i) != expr2.charAt(i)) {
                if (index == -1)
                    index = i;
                else
                    return -1;
            }
        }

        return index;
    }

    private void formatExpr(List<Term> maxterms) {
        for (Term maxterm : maxterms) {
            String expr = StringUtils.sortAlphabet(
                    maxterm.getExpr().replaceAll(" ", ""));
            maxterm.setExpr(expr);
        }
    }

    private boolean canSimple(List<Term> maxterms) {
        for (int i = 0; i < maxterms.size() - 1; i++) {
            for (int j = i + 1; j < maxterms.size(); j++) {
                if (findDiffIndex(maxterms.get(i), maxterms.get(j)) >= 0)
                    return true;
            }
        }

        return false;
    }
}
