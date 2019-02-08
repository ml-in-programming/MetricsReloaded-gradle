package com.sixrr.stockmetrics.methodCalculators;

import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.PsiLiteralExpression;
import com.intellij.psi.PsiSwitchStatement;

public class NumSwitchCalculator extends NumSimpleElementCalculator {

    @Override
    protected PsiElementVisitor createVisitor() {
        return new NumSwitchCalculator.Visitor();
    }

    private class Visitor extends NumSimpleElementCalculator.Visitor {

        @Override
        public void visitSwitchStatement(PsiSwitchStatement statement) {
            super.visitSwitchStatement(statement);
            elementsCounter++;
        }
    }
}
