package com.sixrr.stockmetrics.methodCalculators;

import com.intellij.psi.*;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.refactoring.psi.TypeParametersVisitor;
import com.sixrr.metrics.utils.MethodUtils;
import com.sixrr.stockmetrics.utils.TypeUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

public class NumUsedTypesCalculator extends MethodCalculator {
    private int methodNestingDepth = 0;
    private Set<PsiType> typeSet = null;

    @Override
    protected PsiElementVisitor createVisitor() {
        return new Visitor();
    }

    private class Visitor extends JavaRecursiveElementVisitor {

        @Override
        public void visitMethod(PsiMethod method) {
            if (methodNestingDepth == 0) {
                typeSet = new HashSet<PsiType>();
            }
            TypeUtils.addTypesFromMethodTo(typeSet, method);

            methodNestingDepth++;
            super.visitMethod(method);
            methodNestingDepth--;
            if (methodNestingDepth == 0 && !MethodUtils.isAbstract(method)) {
                postMetric(method, typeSet.size());
            }
        }

        @Override
        public void visitElement(PsiElement element) {
            super.visitElement(element);
            TypeUtils.tryAddTypeOfElementTo(typeSet, element);
        }
    }
}
