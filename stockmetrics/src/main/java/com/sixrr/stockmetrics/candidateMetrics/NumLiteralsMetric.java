package com.sixrr.stockmetrics.candidateMetrics;

import com.sixrr.metrics.MetricCalculator;
import com.sixrr.metrics.MetricType;
import com.sixrr.stockmetrics.candidateCalculators.NumLiteralsCalculator;
import com.sixrr.stockmetrics.i18n.StockMetricsBundle;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.research.groups.ml_methods.utils.ExtractionCandidate;

import java.util.ArrayList;

public class NumLiteralsMetric extends CandidateMetric {

    private ArrayList<ExtractionCandidate> candidates;

    public NumLiteralsMetric(ArrayList<ExtractionCandidate> candidates) {
        this.candidates = candidates;
    }


    // need I change 2 different metrics
    @NotNull
    @Override
    public String getDisplayName() {
        return StockMetricsBundle.message("number.of.literals.candidates.display.name");
    }

    @NotNull
    @Override
    public String getAbbreviation() {
        return StockMetricsBundle.message("number.of.literals.candidates.abbreviation");
    }

    @NotNull
    @Override
    public MetricType getType() {
        return MetricType.Count;
    }

    @NotNull
    @Override
    public MetricCalculator createCalculator() {
        return new NumLiteralsCalculator(candidates);
    }
}
