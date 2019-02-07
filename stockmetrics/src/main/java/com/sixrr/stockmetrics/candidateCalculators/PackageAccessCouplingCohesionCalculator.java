package com.sixrr.stockmetrics.candidateCalculators;

import com.intellij.psi.*;
import com.sixrr.metrics.utils.ClassUtils;
import com.sixrr.stockmetrics.utils.BlocksUtils;
import com.sixrr.stockmetrics.utils.TypeUtils;
import org.jetbrains.research.groups.ml_methods.utils.BlockOfMethod;
import org.jetbrains.research.groups.ml_methods.utils.ExtractionCandidate;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class PackageAccessCouplingCohesionCalculator extends AbstractCouplingCohesionCalculator<PsiPackage> {

    public PackageAccessCouplingCohesionCalculator(
            ArrayList<ExtractionCandidate> candidates,
            boolean isCouplingMethod,
            boolean isFirstPlace) {

        super(candidates, PsiPackage.class, isCouplingMethod, isFirstPlace);
    }

    @Override
    protected PsiElementVisitor createVisitor() {
        return new PackageAccessCouplingCohesionCalculator.Visitor();
    }

    public class Visitor extends CandidateVisitor {}

    @Override
    protected Set<PsiPackage> getElementsFromBlock(BlockOfMethod block) {
        assert block.getStatementsCount() > 0;

        Set<PsiPackage> result = new HashSet<>(Arrays.asList(
                ClassUtils.calculatePackagesRecursive(block.getFirstStatement())
        ));

        for (int i = 0; i < block.getStatementsCount(); i++) {
            block.get(i).accept(new JavaRecursiveElementVisitor() {
                @Override
                public void visitReferenceElement(PsiJavaCodeReferenceElement reference) {
                    super.visitReferenceElement(reference);
                    PsiElement elem = reference.resolve();
                    PsiPackage[] packages = ClassUtils.calculatePackagesRecursive(elem);
                    result.addAll(Arrays.asList(packages));
                }

                @Override
                public void visitPackage(PsiPackage aPackage) {
                    super.visitPackage(aPackage);
                    result.add(aPackage);
                }
            });
        }
        return result;
    }

    private static int ourCount = 0;

    @Override
    protected int getCountOfElementFromBlock(BlockOfMethod block, PsiPackage psiPackage) {
        ourCount = 0;

        for (int i = 0; i < block.getStatementsCount(); i++) {
            block.get(i).accept(new JavaRecursiveElementVisitor() {
                @Override
                public void visitReferenceElement(PsiJavaCodeReferenceElement reference) {
                    super.visitReferenceElement(reference);
                    PsiPackage[] packages = ClassUtils.calculatePackagesRecursive(reference.resolve());
                    for (PsiPackage pack: packages) {
                        if (pack == psiPackage)
                            ourCount++;
                    }
                }

                @Override
                public void visitPackage(PsiPackage aPackage) {
                    super.visitPackage(aPackage);
                    if (aPackage == psiPackage)
                        ourCount++;
                }
            });
        }
        return ourCount;
    }

    @Override
    protected double getFreqOfElementFromBlock(BlockOfMethod block, PsiPackage elem) {
        int count = getCountOfElementFromBlock(block, elem);
        return (double)count / BlocksUtils.getNumStatementsRecursively(block);
    }
}
