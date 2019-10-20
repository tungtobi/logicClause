package uet.fit.ai.core.utils;

import uet.fit.ai.core.expr.ast.terminal.Variable;

import java.util.ArrayList;
import java.util.Collection;

public class VariableList extends ArrayList<Variable> {
    @Override
    public boolean addAll(Collection<? extends Variable> c) {
        boolean result = true;

        for (Variable var : c) {
            boolean r = add(var);
            if (!r)
                result = false;
        }

        return result;
    }

    @Override
    public boolean add(Variable variable) {
        if (!contains(variable))
            return super.add(variable);
        return false;
    }
}
