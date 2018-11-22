package com.sixrr.stockmetrics.methodCalculators;

import com.intellij.psi.*;
import com.sixrr.metrics.utils.MethodUtils;

public class NumTernaryCalculator extends NumSimpleElementCalculator {
    @Override
    protected PsiElementVisitor createVisitor() {
        return new NumTernaryCalculator.Visitor();
    }

    private class Visitor extends NumSimpleElementCalculator.Visitor {
        @Override
        public void visitConditionalExpression(PsiConditionalExpression expression) {
            super.visitConditionalExpression(expression);
            elementsCounter++;
        }
    }
}
