package com.ntrungnghia.formula;

public class FormulaFactory {

    public enum FormulaType {
        LEIBNIZ, NILAKANTHA
    }

    public static Formula getFormula(FormulaType type) {

        if (type == FormulaFactory.FormulaType.LEIBNIZ) {
            return LeibnizFormula.getInstance();
        } else if (type == FormulaFactory.FormulaType.NILAKANTHA) {
            return NilakanthaFormula.getInstance();
        }

        return null;
    }
}
