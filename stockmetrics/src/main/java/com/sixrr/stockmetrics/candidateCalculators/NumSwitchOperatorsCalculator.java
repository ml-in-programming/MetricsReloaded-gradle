package com.sixrr.stockmetrics.candidateCalculators;

import com.intellij.psi.PsiConditionalExpression;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.PsiSwitchStatement;
import org.jetbrains.research.groups.ml_methods.utils.ExtractionCandidate;

import java.util.ArrayList;

public class NumSwitchOperatorsCalculator extends CandidateCalculator {

    public NumSwitchOperatorsCalculator(ArrayList<ExtractionCandidate> candidates) {
        super(candidates);
    }

    @Override
    protected PsiElementVisitor createVisitor() {
        return new NumSwitchOperatorsCalculator.Visitor();
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
