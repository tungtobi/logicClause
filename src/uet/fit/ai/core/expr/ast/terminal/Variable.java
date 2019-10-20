package uet.fit.ai.core.expr.ast.terminal;

import uet.fit.ai.core.utils.VariableList;

import java.util.List;

public class Variable extends Terminal {
    public char name;

    public Variable(char name) {
        this.name = name;
    }

    public Variable(char name, boolean value) {
        this.value = value;
        this.name = name;
    }

    @Override
    public boolean interpret() {
        return value;
    }

    @Override
    public List<Variable> getVariables() {
        List<Variable> list = new VariableList();
        list.add(this);
        return list;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Variable) {
            return ((Variable) obj).name == name;
        }

        return false;
    }

    @Override
    public Object clone() {
        return new Variable(name, value);
    }

    @Override
    public String toString() {
        return String.format("%c", name);
    }
}