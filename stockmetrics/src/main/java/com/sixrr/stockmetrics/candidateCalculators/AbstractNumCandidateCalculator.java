package com.sixrr.stockmetrics.candidateCalculators;

import com.intellij.psi.*;
import com.sixrr.metrics.utils.MethodUtils;
import com.sixrr.stockmetrics.execution.BaseMetricsCalculator;
import com.sixrr.stockmetrics.utils.CandidateUtils;
import org.jetbrains.research.groups.ml_methods.utils.ExtractionCandidate;

import java.util.ArrayList;

abstract class AbstractNumCandidateCalculator extends BaseMetricsCalculator {

    private ArrayList<ExtractionCandidate> candidates;

    public AbstractNumCandidateCalculator(ArrayList<ExtractionCandidate> candidates) {
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
        protected int methodNestingDepth = 0;
        ArrayList<ExtractionCandidate> methodCandidates;
        ArrayList<Integer> counts = new ArrayList<>();
        boolean isInsideMethod = false;

        @Override
        public void visitMethod(PsiMethod method) {
            if (methodNestingDepth == 0) {
                methodCandidates = CandidateUtils.getCandidatesOfMethod(method, candidates);
                initCounters();
                isInsideMethod = true;
            }

            methodNestingDepth++;
            super.visitMethod(method);
            methodNestingDepth--;

            if (methodNestingDepth == 0 && !MethodUtils.isAbstract(method)) {
                for (int i = 0; i < methodCandidates.size(); i++) {
                    postMetric(methodCandidates.get(i), getCounterForCand(i));
                }
                candidates.removeAll(methodCandidates);
                isInsideMethod = false;
            }
        }

        protected void initCounters() {
            counts.clear();
            methodCandidates.forEach(elem -> counts.add(0));
        }

        protected int getCounterForCand(int i) {
            return counts.get(i);
        }

        protected void updateCounters() {
            for (int i = 0; i < methodCandidates.size(); i++) {
                updateCounter(i);
            }
        }

        protected void updateCounter(int i) {
            if (methodCandidates.get(i).isInCandidate()) {
                counts.set(i, counts.get(i) + 1);
            }
        }

        @Override
        public void visitStatement(PsiStatement statement) {
            CandidateUtils.checkStartOfCandidates(statement, methodCandidates);

            super.visitStatement(statement);

            CandidateUtils.checkEndOfCandidates(statement, methodCandidates);
        }
    }
}
