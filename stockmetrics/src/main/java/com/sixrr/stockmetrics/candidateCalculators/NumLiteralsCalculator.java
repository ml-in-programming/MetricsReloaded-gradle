package com.sixrr.stockmetrics.candidateCalculators;

import com.intellij.psi.*;
import com.sixrr.metrics.utils.MethodUtils;
import org.jetbrains.research.groups.ml_methods.utils.ExtractionCandidate;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class NumLiteralsCalculator extends CandidateCalculator {
    private int methodNestingDepth = 0;
    private ArrayList<Integer> counts = new ArrayList<>();

    private boolean isInsideMethod = false;

    public NumLiteralsCalculator(ArrayList<ExtractionCandidate> candidates) {
        this.candidates = candidates;
    }

    @Override
    protected PsiElementVisitor createVisitor() {
        return new NumLiteralsCalculator.Visitor();
    }

    private class Visitor extends JavaRecursiveElementVisitor {

        @Override
        public void visitMethod(PsiMethod method) {
            if (methodNestingDepth == 0) {
                methodCandidates = candidates.stream()
                        .filter(elem -> elem.getSourceMethod().equals(method))
                        .collect(Collectors.toCollection(ArrayList::new));

                counts.clear();
                candidates.forEach(elem -> counts.add(0));
                isInsideMethod = true;
            }

            methodNestingDepth++;
            super.visitMethod(method);
            methodNestingDepth--;

            if (methodNestingDepth == 0 && !MethodUtils.isAbstract(method)) {
                for (int i = 0; i < methodCandidates.size(); i++) {
                    postMetric(methodCandidates.get(i), counts.get(i));
                }
                candidates.removeAll(methodCandidates);
                isInsideMethod = false;
            }
        }

        @Override
        public void visitLiteralExpression(PsiLiteralExpression literal) {
            super.visitLiteralExpression(literal);
            if (isInsideMethod) {
                for (int i = 0; i < methodCandidates.size(); i++) {
                    if (methodCandidates.get(i).isInCandidate()) {
                        counts.set(i, counts.get(i) + 1);
                    }
                }
            }
        }

        @Override
        public void visitStatement(PsiStatement statement) {
            super.visitStatement(statement);
            setInsideCandidate(statement);
        }
    }
}
