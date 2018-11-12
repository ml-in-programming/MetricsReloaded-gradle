package com.sixrr.stockmetrics.methodCalculators;

import com.intellij.psi.*;
import com.intellij.refactoring.psi.TypeParametersVisitor;
import com.sixrr.metrics.utils.MethodUtils;

import java.lang.reflect.Method;

public class NumUsedTypesCalculator extends MethodCalculator {
    private int methodNestingDepth = 0;
    private int typeCount = 0;

    @Override
    protected PsiElementVisitor createVisitor() {
        return new Visitor();
    }

    private class Visitor extends JavaRecursiveElementVisitor {

        @Override
        public void visitMethod(PsiMethod method) {
            if (methodNestingDepth == 0) {
                typeCount = 0;
            }
            typeCount++;

            methodNestingDepth++;
            super.visitMethod(method);
            methodNestingDepth--;
            if (methodNestingDepth == 0 && !MethodUtils.isAbstract(method)) {
                postMetric(method, typeCount);
            }
        }

        @Override
        public void visitElement(PsiElement element) {
            super.visitElement(element);

            Method gettingTypeMethod = null;
            try {
                gettingTypeMethod = element.getClass().getMethod("getType", (Class<?>[]) null);
            } catch (NoSuchMethodException | SecurityException ignored) {
            }

            if (gettingTypeMethod != null)
                typeCount++;
        }
    }
}
