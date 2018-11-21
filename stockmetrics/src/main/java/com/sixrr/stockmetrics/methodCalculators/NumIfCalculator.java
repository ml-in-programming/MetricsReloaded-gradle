package com.sixrr.stockmetrics.methodCalculators;

import com.intellij.psi.PsiIfStatement;

public class NumIfCalculator extends NumSimpleElementCalculator {

    class Visitor extends NumSimpleElementCalculator.Visitor {

        @Override
        public void visitIfStatement(PsiIfStatement statement) {
            super.visitIfStatement(statement);
            elementsCounter++;
        }
    }
}
