package uet.fit.ai.core;

import uet.fit.ai.core.expr.ast.IExpression;
import uet.fit.ai.core.pos.Term;
import uet.fit.ai.core.pos.TermGeneration;
import uet.fit.ai.core.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class Robinson {
    private StringBuilder log = new StringBuilder();

    /**
     * Robinson algorithm.
     *
     * @param premises has given.
     * @param proofs need to prove.
     *
     * @return whether proofs is possible or not.
     */
    public boolean exec(List<IExpression> premises, List<IExpression> proofs) {
        /*
         * Add negative proofs into premises - G set.
         */
        for (IExpression proof : proofs)
            premises.add(proof.getNegative());

        /*
         * Convert all expression to POS format and get all maxterms.
         */
        System.out.println("\n[Generate product of sum]");
        List<Term> maxterms = (new TermGeneration()).exec(premises);

        /*
         * Create a empty prev list.
         */
//        List<Term> prevTerms = new ArrayList<>();
        int prevSize = 0;

        System.out.println("\n[Execute Robinson Algorithms]");

        /*
         * Display the G set the first time.
         */
        log.append(termListToString(maxterms));

        /*
         * Repeat util generate a empty term or can't generate anymore.
         */
        while (/*prevTerms.size() != maxterms.size()*/ prevSize != maxterms.size()) {
            /*
             * Update prev list equals to current list.
             */
//            update(prevTerms, maxterms);
            prevSize = maxterms.size();

            /*
             * Check all pair of term in list.
             */
            for (int i = 0; i < maxterms.size() - 1; i++)
                for (int j = i + 1; j < maxterms.size(); j++) {
                    /*
                     * Resolution a possible pair of term.
                     */
                    Term newTerm = res(maxterms.get(i), maxterms.get(j));

                    /*
                     * Check whether resolution success or not.
                     */
                    if (newTerm != null) {
                        /*
                         * Add new term into current list.
                         */
                        if (!maxterms.contains(newTerm)) {
                            maxterms.add(newTerm);
                            log.append("res(").append(maxterms.get(i)).append(", ")
                                    .append(maxterms.get(j)).append(") = ").append(newTerm).append("\n");
                            log.append(termListToString(maxterms));
                        }

                        /*
                         * Exit when generation a empty term.
                         */
                        if (newTerm.getExpr().equals(""))
                            return true;
                    }
                }
        }

        log.append("Can't generate new term");

        return false;
    }

    /**
     * Display the list of terms.
     *
     * @param maxterms list.
     */
    private String termListToString(List<Term> maxterms) {
        StringBuilder g = new StringBuilder();
        g.append("G = {");
        for (int i = 0; i < maxterms.size() - 1; i++)
            g.append(maxterms.get(i)).append(", ");
        g.append(maxterms.get(maxterms.size() - 1));
        g.append("}\n");
        return g.toString();
    }

    /**
     * Update the prev list of terms.
     *
     * @param prev list.
     * @param cur list.
     */
    private void update(List<Term> prev, List<Term> cur) {
        for (Term maxterm : cur)
            if (!prev.contains(maxterm))
                prev.add(maxterm);
    }

    public String getLog() {
        return log.toString();
    }

    /**
     * Resolution two term.
     *
     * @return new term if success and null if failure.
     */
    private Term res(Term maxterm1, Term maxterm2) {
        String expr1 = maxterm1.getExpr();
        String expr2 = maxterm2.getExpr();

        /*
         * Find the opposition prop in 2 term.
         */
        int index = -1;
        for (int i = 0; i < expr1.length(); i++) {
            if (expr2.indexOf(StringUtils.reverseCase(expr1.charAt(i))) >= 0) {
                if (index < 0)
                    index = i;
                else
                    return null;
            }
        }

        /*
         * Can't resolution.
         */
        if (index < 0)
            return null;

        /*
         * Resolution 2 term.
         */
        char var = expr1.charAt(index);
        String expr = StringUtils.removeDuplicate(
                StringUtils.sortAlphabet(expr1.replaceAll(var + "", "")
                .concat(expr2.replaceAll(StringUtils.reverseCase(var) + "", ""))));

        return new Term(expr);
    }
}
