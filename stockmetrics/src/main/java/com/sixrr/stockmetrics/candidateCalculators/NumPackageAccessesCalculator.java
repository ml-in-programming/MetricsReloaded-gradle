package com.sixrr.stockmetrics.candidateCalculators;

import com.intellij.psi.*;
import com.sixrr.metrics.utils.ClassUtils;
import com.sixrr.stockmetrics.utils.TypeUtils;
import org.jetbrains.research.groups.ml_methods.utils.ExtractionCandidate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class NumPackageAccessesCalculator extends AbstractNumCandidateCalculator {

    public NumPackageAccessesCalculator(ArrayList<ExtractionCandidate> candidates) {
        super(candidates);
    }

    @Override
    protected PsiElementVisitor createVisitor() {
        return new NumPackageAccessesCalculator.Visitor();
    }

    private class Visitor extends CandidateVisitor {

        ArrayList<HashSet<PsiPackage>> usedPackages;

        @Override
        protected void initCounters() {
            if (usedPackages == null) {
                usedPackages = new ArrayList<>();
            }
            usedPackages.clear();
            methodCandidates.forEach(cand -> usedPackages.add(new HashSet<>()));
        }

        @Override
        protected int getCounterForCand(int i) {
            return usedPackages.get(i).size();
        }

        @Override
        public void visitMethod(PsiMethod method) {
            super.visitMethod(method);
            List<PsiPackage> containingPackages = Arrays.asList(
                    ClassUtils.calculatePackagesRecursive(method)
            );

            for (int i = 0; i < methodCandidates.size(); i++) {
                usedPackages.get(i).addAll(containingPackages);
            }
        }

        @Override
        public void visitReferenceElement(PsiJavaCodeReferenceElement reference) {
            super.visitReferenceElement(reference);
            if (!isInsideMethod)
                return;

            PsiElement element = reference.resolve();
            if (element == null || element.getContainingFile() == null) // for packages, dirs etc
                return;

            List<PsiPackage> packages = Arrays.asList(ClassUtils.calculatePackagesRecursive(element));

            for (int i = 0; i < methodCandidates.size(); i++) {
                if (methodCandidates.get(i).isInCandidate()) {
                    usedPackages.get(i).addAll(packages);
                }
            }
        }
    }
}
