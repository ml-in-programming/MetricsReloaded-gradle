package com.sixrr.stockmetrics.candidateCalculators;

import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.PsiSwitchStatement;
import org.jetbrains.research.groups.ml_methods.utils.ExtractionCandidate;

import java.util.ArrayList;

public class NumTypeAccessesCalculator extends AbstractNumCandidateCalculator {

    public NumTypeAccessesCalculator(ArrayList<ExtractionCandidate> candidates) {
        super(candidates);
    }

    @Override
    protected PsiElementVisitor createVisitor() {
        return new NumTypeAccessesCalculator.Visitor();
    }

    private class Visitor extends CandidateVisitor {

        @Override
        public void visitSwitchStatement(PsiSwitchStatement statement) {
            super.visitSwitchStatement(statement);

            if (!isInsideMethod)
                return;
            incrementCounters();
        }
    }
}
