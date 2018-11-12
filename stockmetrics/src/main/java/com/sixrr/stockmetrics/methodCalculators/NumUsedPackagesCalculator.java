package com.sixrr.stockmetrics.methodCalculators;

import com.intellij.psi.*;
import com.intellij.psi.util.PsiTreeUtil;
import com.sixrr.metrics.utils.MethodUtils;

import java.util.HashSet;
import java.util.Set;

public class NumUsedPackagesCalculator extends MethodCalculator {
    private int methodNestingDepth = 0;
    private Set<PsiPackage> usedPackages = null;

    @Override
    protected PsiElementVisitor createVisitor() {
        return new Visitor();
    }

    private class Visitor extends JavaRecursiveElementVisitor {

        @Override
        public void visitMethod(PsiMethod method) {
            if (methodNestingDepth == 0) {
                usedPackages = new HashSet<PsiPackage>();
            }

            methodNestingDepth++;
            super.visitMethod(method);
            methodNestingDepth--;
            if (methodNestingDepth == 0 && !MethodUtils.isAbstract(method)) {
                postMetric(method, usedPackages.size());
            }
        }

        @Override
        public void visitElement(PsiElement element) {
            super.visitElement(element);

            PsiElement declaration = element.getReferences()[0].resolve();
            PsiPackage pack = PsiTreeUtil.getParentOfType(declaration, PsiPackage.class);

            while (pack != null) {
                usedPackages.add(pack);
                pack = pack.getParentPackage();
            }
        }
    }
}
