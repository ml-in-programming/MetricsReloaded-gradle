package com.sixrr.stockmetrics.candidateCalculators;

import com.intellij.psi.*;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.psi.util.PsiUtil;
import org.jetbrains.research.groups.ml_methods.utils.ExtractionCandidate;

import java.util.ArrayList;

public class NumVarsAccessCandidateCalculator extends AbstractNumCandidateCalculator {

    public NumVarsAccessCandidateCalculator(ArrayList<ExtractionCandidate> candidates) {
        super(candidates);
    }

    @Override
    protected PsiElementVisitor createVisitor() {
        return new NumVarsAccessCandidateCalculator.Visitor();
    }

    private class Visitor extends CandidateVisitor {

        PsiMethod currentMethod = null;

        @Override
        public void visitMethod(PsiMethod method) {
            if (methodNestingDepth == 0)
                currentMethod = method;

            super.visitMethod(method);
            currentMethod = null;
        }

        @Override
        public void visitLocalVariable(PsiLocalVariable variable) {
            super.visitLocalVariable(variable);
            if (isInsideMethod)
                updateCounters();
        }

        @Override
        public void visitReferenceElement(PsiJavaCodeReferenceElement reference) {
            super.visitReferenceElement(reference);
            PsiElement elem = reference.resolve();
            if (elem instanceof PsiLocalVariable && isInsideMethod &&
                    PsiTreeUtil.isAncestor(currentMethod, elem, true)) {
                updateCounters();
            }
        }
    }
}
