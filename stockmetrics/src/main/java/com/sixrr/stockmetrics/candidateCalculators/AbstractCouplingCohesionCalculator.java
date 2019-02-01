package com.sixrr.stockmetrics.candidateCalculators;

import com.intellij.psi.JavaRecursiveElementVisitor;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiMethod;
import com.sixrr.stockmetrics.execution.BaseMetricsCalculator;
import com.sixrr.stockmetrics.utils.BlocksUtils;
import com.sixrr.stockmetrics.utils.CandidateUtils;
import org.jetbrains.research.groups.ml_methods.utils.BlockOfMethod;
import org.jetbrains.research.groups.ml_methods.utils.ExtractionCandidate;

import java.util.*;

public abstract class AbstractCouplingCohesionCalculator<T extends PsiElement> extends BaseMetricsCalculator {

    private ArrayList<ExtractionCandidate> candidates;
    private Class<T> aClass;
    private double coupling;
    private double cohesion;
    private boolean isCouplingMethod;
    private boolean isFirstPlace;

    public AbstractCouplingCohesionCalculator(
            ArrayList<ExtractionCandidate> candidates,
            Class<T> aClass,
            boolean isCouplingMethod,
            boolean isFirstPlace) {

        this.candidates = new ArrayList<>(candidates);
        this.aClass = aClass;
        this.isCouplingMethod = isCouplingMethod;
        this.isFirstPlace = isFirstPlace;
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
        private ArrayList<ExtractionCandidate> methodCandidates;

        @Override
        public void visitMethod(PsiMethod method) {
            super.visitMethod(method);
            methodCandidates = CandidateUtils.getCandidatesOfMethod(method, candidates);
            for (ExtractionCandidate candidate: methodCandidates) {
                abstractMethod(candidate);
                postMetric(candidate, getResult());
            }
        }
    }

    private void abstractMethod(ExtractionCandidate candidate) {
        PsiMethod sourceMethod = candidate.getSourceMethod();
        BlockOfMethod sourceBlock = BlocksUtils.getBlockFromMethod(sourceMethod);
        BlockOfMethod candidateBlock = candidate.getBlock();
        Set<T> elements = BlocksUtils.getElementsOfBlock(candidateBlock, aClass);

        HashMap<T, Double> ratio = new HashMap<>();

        for (T e: elements) {
            double freqCandidate = BlocksUtils.getFreqOfElementFromBlock(candidateBlock, e);
            double freqMethod = BlocksUtils.getFreqOfElementFromBlock(sourceBlock, e);
            ratio.put(e, freqCandidate / freqMethod);
        }

        T bestElem = getElementFromRatio(ratio);
        coupling = ratio.get(bestElem);

        int loc = candidateBlock.getStatementsCount();
        int count = BlocksUtils.getCountOfElementFromBlock(candidateBlock, bestElem);
        cohesion = (double)count / loc;

    }

    private T getElementFromRatio(HashMap<T, Double> ratio) {
        T elem = getMaxRatio(ratio);
        if (isFirstPlace)
            return elem;

        ratio.remove(elem);
        return getMaxRatio(ratio);
    }

    private T getMaxRatio(HashMap<T, Double> ratio) {
        T maxElem = null;
        Double maxDouble = -1.0;

        for (Map.Entry<T, Double> entry: ratio.entrySet()) {
            if (entry.getValue() > maxDouble) {
                maxElem = entry.getKey();
                maxDouble = entry.getValue();
            }
        }
        return maxElem;
    }

    private double getResult() {
        return isCouplingMethod ? getCoupling() : getCohesion();
    }

    private double getCoupling() {
        return coupling;
    }

    private double getCohesion() {
        return cohesion;
    }
}
