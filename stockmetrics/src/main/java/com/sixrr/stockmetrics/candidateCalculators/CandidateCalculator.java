package com.sixrr.stockmetrics.candidateCalculators;

import com.intellij.openapi.progress.EmptyProgressIndicator;
import com.intellij.openapi.progress.ProgressManager;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.PsiStatement;
import com.sixrr.stockmetrics.execution.BaseMetricsCalculator;
import org.jetbrains.research.groups.ml_methods.utils.ExtractionCandidate;

import java.util.ArrayList;

abstract class CandidateCalculator extends BaseMetricsCalculator {

    ArrayList<ExtractionCandidate> candidates;
    ArrayList<ExtractionCandidate> methodCandidates;

    void postMetric(ExtractionCandidate candidate, int numerator, int denominator) {
        resultsHolder.postCandidateMetric(metric, candidate, (double) numerator, (double) denominator);
    }

    void postMetric(ExtractionCandidate candidate, int value) {
        resultsHolder.postCandidateMetric(metric, candidate, (double) value);
    }

    void postMetric(ExtractionCandidate candidate, double value) {
        resultsHolder.postCandidateMetric(metric, candidate, value);
    }

    void setInsideCandidate(PsiStatement currentStatement) {
        for (ExtractionCandidate candidate: methodCandidates) {

            if (candidate.getStart().equals(currentStatement))
                candidate.setInCandidate(true);

            if (candidate.getEnd().equals(currentStatement))
                candidate.setInCandidate(false);
        }
    }
}
