package uet.fit.ai.core.truth;

import uet.fit.ai.core.expr.ast.terminal.Variable;

import java.util.ArrayList;

public class Interpretation extends ArrayList<Variable> {
    public Variable getResult() {
        return get(size() - 1);
    }
}
