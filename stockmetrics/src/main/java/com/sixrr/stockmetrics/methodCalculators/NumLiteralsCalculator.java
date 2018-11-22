package com.sixrr.stockmetrics.methodCalculators;

import com.intellij.psi.*;
import com.sixrr.metrics.utils.MethodUtils;

public class NumLiteralsCalculator extends NumSimpleElementCalculator {

    @Override
    protected PsiElementVisitor createVisitor() {
        return new NumLiteralsCalculator.Visitor();
    }

    private class Visitor extends NumSimpleElementCalculator.Visitor {

        @Override
        public void visitLiteralExpression(PsiLiteralExpression literal) {
            super.visitLiteralExpression(literal);
            elementsCounter++;
        }
    }
}
