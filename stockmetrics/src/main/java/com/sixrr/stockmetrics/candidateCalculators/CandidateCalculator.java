package com.sixrr.stockmetrics.candidateCalculators;

import com.intellij.psi.JavaRecursiveElementVisitor;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.PsiStatement;
import com.sixrr.metrics.utils.MethodUtils;
import com.sixrr.stockmetrics.execution.BaseMetricsCalculator;
import org.jetbrains.research.groups.ml_methods.utils.ExtractionCandidate;

import java.util.ArrayList;
import java.util.stream.Collectors;

abstract class CandidateCalculator extends BaseMetricsCalculator {

    private ArrayList<ExtractionCandidate> candidates;

    public CandidateCalculator(ArrayList<ExtractionCandidate> candidates) {
        this.candidates = new ArrayList<>(candidates);
    }

    void postMetric(ExtractionCandidate candidate, int numerator, int denominator) {
        resultsHolder.postCandidateMetric(metric, candidate, (double) numerator, (double) denominator);
    }

    void postMetric(ExtractionCandidate candidate, int value) {
        resultsHolder.postCandidateMetric(metric, candidate, (double) value);
    }

    void postMetric(ExtractionCandidate candidate, double value) {
        resultsHolder.postCandidateMetric(metric, candidate, value);
    }

    public class CandidateVisitor extends JavaRecursiveElementVisitor {
        private int methodNestingDepth = 0;
        ArrayList<ExtractionCandidate> methodCandidates;
        ArrayList<Integer> counts = new ArrayList<>();
        boolean isInsideMethod = false;

        @Override
        public void visitMethod(PsiMethod method) {
            if (methodNestingDepth == 0) {
                methodCandidates = getCandidatesOfMethod(method, candidates);
                counts.clear();
                methodCandidates.forEach(elem -> counts.add(0));
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
        public void visitStatement(PsiStatement statement) {
            super.visitStatement(statement);
            setInsideCandidate(statement, methodCandidates);
        }

        void incrementCounters() {
            for (int i = 0; i < methodCandidates.size(); i++) {
                if (methodCandidates.get(i).isInCandidate()) {
                    counts.set(i, counts.get(i) + 1);
                }
            }
        }
    }

    private static ArrayList<ExtractionCandidate> getCandidatesOfMethod(
            PsiMethod method,
            ArrayList<ExtractionCandidate> allCandidates)
    {
        return allCandidates.stream()
                .filter(elem -> elem.getSourceMethod().equals(method))
                .collect(Collectors.toCollection(ArrayList::new));

    }

    private static void setInsideCandidate(PsiStatement currentStatement, ArrayList<ExtractionCandidate> candidates) {
        for (ExtractionCandidate candidate: candidates) {

            if (candidate.getStart().equals(currentStatement))
                candidate.setInCandidate(true);

            if (candidate.getEnd().equals(currentStatement))
                candidate.setInCandidate(false);
        }
    }
}
