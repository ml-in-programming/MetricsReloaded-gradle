package com.sixrr.stockmetrics.methodCalculators;

import com.intellij.psi.JavaRecursiveElementVisitor;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.PsiLocalVariable;
import com.intellij.psi.PsiMethod;
import com.sixrr.metrics.utils.MethodUtils;

public class NumLocalVarsAccessCalculator extends NumSimpleElementCalculator {

    @Override
    protected PsiElementVisitor createVisitor() {
        return new NumLocalVarsAccessCalculator.Visitor();
    }

    private class Visitor extends NumSimpleElementCalculator.Visitor {

        @Override
        public void visitLocalVariable(PsiLocalVariable variable) {
            super.visitLocalVariable(variable);
            elementsCounter++;
        }
    }
}