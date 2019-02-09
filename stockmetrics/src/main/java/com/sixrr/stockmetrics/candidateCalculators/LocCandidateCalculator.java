package com.sixrr.stockmetrics.candidateCalculators;

import com.intellij.psi.JavaRecursiveElementVisitor;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.PsiMethod;
import com.sixrr.stockmetrics.execution.BaseMetricsCalculator;
import com.sixrr.stockmetrics.utils.BlocksUtils;
import com.sixrr.stockmetrics.utils.CandidateUtils;
import org.jetbrains.research.groups.ml_methods.utils.BlockOfMethod;
import org.jetbrains.research.groups.ml_methods.utils.ExtractionCandidate;

import java.util.ArrayList;

public class LocCandidateCalculator extends BaseMetricsCalculator {

    private ArrayList<ExtractionCandidate> candidates;

    public LocCandidateCalculator(ArrayList<ExtractionCandidate> candidates) {
        this.candidates = new ArrayList<>(candidates);
    }

    private void postMetric(ExtractionCandidate candidate, int value) {
        resultsHolder.postCandidateMetric(metric, candidate, (double) value);
    }

    @Override
    protected PsiElementVisitor createVisitor() {
        return new JavaRecursiveElementVisitor() {

            @Override
            public void visitMethod(PsiMethod method) {
                super.visitMethod(method);
                ArrayList<ExtractionCandidate> candidatesOfMethod =
                        CandidateUtils.getCandidatesOfMethod(method, candidates);

                for (ExtractionCandidate cand: candidatesOfMethod) {
                    postMetric(cand, BlocksUtils.getNumStatementsRecursively(cand.getBlock()));
                }
            }
        };
    }
}
