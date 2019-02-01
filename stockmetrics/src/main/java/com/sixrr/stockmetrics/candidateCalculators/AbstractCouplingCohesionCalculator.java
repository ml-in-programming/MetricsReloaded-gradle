package com.sixrr.stockmetrics.candidateCalculators;

import com.intellij.psi.JavaRecursiveElementVisitor;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiMethod;
import com.sixrr.stockmetrics.execution.BaseMetricsCalculator;
import com.sixrr.stockmetrics.utils.BlocksUtils;
import com.sixrr.stockmetrics.utils.CandidateUtils;
import org.graalvm.util.Pair;
import org.jetbrains.research.groups.ml_methods.utils.BlockOfMethod;
import org.jetbrains.research.groups.ml_methods.utils.ExtractionCandidate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Set;

public abstract class AbstractCouplingCohesionCalculator<T extends PsiElement> extends BaseMetricsCalculator {

    private ArrayList<ExtractionCandidate> candidates;
    private Class<T> aClass;
    private double coupling;
    private double cohesion;

    public AbstractCouplingCohesionCalculator(
            ArrayList<ExtractionCandidate> candidates,
            Class<T> aClass) {

        this.candidates = new ArrayList<>(candidates);
        this.aClass = aClass;
    }

    public class CandidateVisitor extends JavaRecursiveElementVisitor {
        private ArrayList<ExtractionCandidate> methodCandidates;

        @Override
        public void visitMethod(PsiMethod method) {
            super.visitMethod(method);
            methodCandidates = CandidateUtils.getCandidatesOfMethod(method, candidates);
            for (ExtractionCandidate candidate: methodCandidates) {
                abstractMethod(candidate);
            }
        }
    }

    protected void abstractMethod(ExtractionCandidate candidate) {
        PsiMethod sourceMethod = candidate.getSourceMethod();
        BlockOfMethod sourceBlock = getBlockFromMethod(sourceMethod);
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

    protected static BlockOfMethod getBlockFromMethod(PsiMethod method) {
        return new BlockOfMethod(method.getBody().getStatements());
    }

    protected abstract T getElementFromRatio(HashMap<T, Double> ratio);

    protected double getCoupling() {
        return coupling;
    }

    protected double getCohesion() {
        return cohesion;
    }
}
