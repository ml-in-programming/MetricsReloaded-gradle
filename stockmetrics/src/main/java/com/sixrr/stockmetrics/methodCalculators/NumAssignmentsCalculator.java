package com.sixrr.stockmetrics.methodCalculators;

import com.intellij.psi.*;
import com.sixrr.metrics.utils.MethodUtils;

public class NumAssignmentsCalculator extends NumSimpleElementCalculator {
    @Override
    protected PsiElementVisitor createVisitor() {
        return new Visitor();
    }

    private class Visitor extends NumSimpleElementCalculator.Visitor {

        @Override
        public void visitAssignmentExpression(PsiAssignmentExpression expression) {
            super.visitAssignmentExpression(expression);
            elementsCounter++;
        }
    }
}
