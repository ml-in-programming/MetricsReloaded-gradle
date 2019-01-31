package com.sixrr.stockmetrics.utils;

import com.intellij.psi.PsiMethod;
import com.intellij.psi.PsiStatement;
import org.jetbrains.research.groups.ml_methods.utils.ExtractionCandidate;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class CandidateUtils {

    public static ArrayList<ExtractionCandidate> getCandidatesOfMethod(
            PsiMethod method,
            ArrayList<ExtractionCandidate> allCandidates)
    {
        return allCandidates.stream()
                .filter(elem -> elem.getSourceMethod().equals(method))
                .collect(Collectors.toCollection(ArrayList::new));

    }

    public static void setInsideCandidate(PsiStatement currentStatement, ArrayList<ExtractionCandidate> candidates) {
        for (ExtractionCandidate candidate: candidates) {

            if (candidate.getStart().equals(currentStatement))
                candidate.setInCandidate(true);

            if (candidate.getEnd().equals(currentStatement))
                candidate.setInCandidate(false);
        }
    }
}
