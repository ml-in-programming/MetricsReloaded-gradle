package com.sixrr.stockmetrics.candidateCalculators;

import com.intellij.psi.*;
import com.sixrr.stockmetrics.utils.BlocksUtils;
import com.sixrr.stockmetrics.utils.TypeUtils;
import org.jetbrains.research.groups.ml_methods.utils.BlockOfMethod;
import org.jetbrains.research.groups.ml_methods.utils.ExtractionCandidate;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class TypeAccessCouplingCohesionCalculator extends AbstractCouplingCohesionCalculator<PsiType> {

    public TypeAccessCouplingCohesionCalculator(
            ArrayList<ExtractionCandidate> candidates,
            boolean isCouplingMethod,
            boolean isFirstPlace) {

        super(candidates, PsiType.class, isCouplingMethod, isFirstPlace);
    }

    @Override
    protected PsiElementVisitor createVisitor() {
        return new Visitor();
    }

    public class Visitor extends AbstractCouplingCohesionCalculator.CandidateVisitor {}

    @Override
    protected Set<PsiType> getElementsFromBlock(BlockOfMethod block) {
        Set<PsiType> result = new HashSet<>();

        for (int i = 0; i < block.getStatementsCount(); i++) {
            block.get(i).accept(new JavaRecursiveElementVisitor() {
                @Override
                public void visitElement(PsiElement element) {
                    super.visitElement(element);
                    TypeUtils.tryAddTypeOfElementTo(result, element);
                }
            });
        }
        return result;
    }

    private static int ourCount = 0;

    @Override
    protected int getCountOfElementFromBlock(BlockOfMethod block, PsiType psiType) {
        ourCount = 0;

        for (int i = 0; i < block.getStatementsCount(); i++) {
            block.get(i).accept(new JavaRecursiveElementVisitor() {

                @Override
                public void visitElement(PsiElement element) {
                    super.visitElement(element);

                    Method gettingTypeMethod = TypeUtils.tryGetGetTypeMethod(element);
                    if (gettingTypeMethod == null)
                        return;

                    try {
                        if (gettingTypeMethod.invoke(element) == psiType)
                            ourCount++;
                    } catch (IllegalAccessException | InvocationTargetException ignored) {}
                }

                @Override
                public void visitMethod(PsiMethod method) {
                    super.visitMethod(method);

                    if (psiType == method.getReturnType())
                        ourCount++;
                    ourCount += Arrays.
                            stream(method.getParameterList().getParameters()).
                            map(PsiParameter::getType).
                            filter(t -> t == psiType).
                            count();
                }
            });
        }
        return ourCount;
    }

    @Override
    protected double getFreqOfElementFromBlock(BlockOfMethod block, PsiType elem) {
        int count = getCountOfElementFromBlock(block, elem);
        return (double)count / BlocksUtils.getNumStatementsRecursively(block);
    }
}
