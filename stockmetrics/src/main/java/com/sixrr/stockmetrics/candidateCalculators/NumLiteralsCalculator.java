package com.sixrr.stockmetrics.candidateCalculators;

import com.intellij.psi.*;
import org.jetbrains.research.groups.ml_methods.utils.ExtractionCandidate;

import java.util.ArrayList;

public class NumLiteralsCalculator extends AbstractNumCandidateCalculator {

    public NumLiteralsCalculator(ArrayList<ExtractionCandidate> candidates) {
        super(candidates);
    }

    @Override
    protected PsiElementVisitor createVisitor() {
        return new NumLiteralsCalculator.Visitor();
    }

    private class Visitor extends CandidateVisitor {

        @Override
        public void visitLiteralExpression(PsiLiteralExpression literal) {
            super.visitLiteralExpression(literal);

            if (!isInsideMethod)
                return;
            incrementCounters();
        }
    }
}
