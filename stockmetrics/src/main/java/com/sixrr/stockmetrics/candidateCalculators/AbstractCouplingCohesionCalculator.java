package com.sixrr.stockmetrics.candidateCalculators;

import com.intellij.psi.JavaRecursiveElementVisitor;
import com.intellij.psi.PsiMethod;
import com.sixrr.stockmetrics.execution.BaseMetricsCalculator;
import org.jetbrains.research.groups.ml_methods.utils.ExtractionCandidate;

import java.util.ArrayList;

public abstract class AbstractCouplingCohesionCalculator extends BaseMetricsCalculator {

    private ArrayList<ExtractionCandidate> candidates;

    public class CandidateVisitor extends JavaRecursiveElementVisitor {
        @Override
        public void visitMethod(PsiMethod method) {
            super.visitMethod(method);

        }
    }

    protected double abstractMethod() {

    }

    protected double getCoupling(){}
    protected double getCohesion(){}
}
