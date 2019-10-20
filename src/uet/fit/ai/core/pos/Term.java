package uet.fit.ai.core.pos;

import uet.fit.ai.core.utils.StringUtils;

public class Term {
    /**
     * Term describe in format POS,
     * uppercase for negative, lowercase for positive.
     *
     * E.g: AbcD ---> -a + b + c + -d.
     */
    private String expr;

    public Term(String expr) {
        this.expr = expr;
    }

    @Override
    public String toString() {
        if (expr.equals(""))
            return "null";

        String str = StringUtils.convertToExpression(expr);

        if (str.length() > 2)
            str = "(" + str + ")";

        return str;
    }

    public String getExpr() {
        return expr;
    }

    public void setExpr(String expr) {
        this.expr = expr;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Term) {
            return ((Term) obj).getExpr().equals(expr);
        }

        return false;
    }

    @Override
    protected Object clone() {
        return new Term(expr);
    }
}
