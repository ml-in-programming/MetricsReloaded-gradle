package com.sixrr.stockmetrics.candidateMetrics;

import com.sixrr.metrics.MetricCalculator;
import com.sixrr.metrics.MetricType;
import com.sixrr.stockmetrics.candidateCalculators.NumPackageAccessesCalculator;
import com.sixrr.stockmetrics.candidateCalculators.NumTypeAccessesCalculator;
import com.sixrr.stockmetrics.i18n.StockMetricsBundle;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.research.groups.ml_methods.utils.ExtractionCandidate;

import java.util.ArrayList;

public class NumPackagesCandidateMetric extends CandidateMetric {

    private ArrayList<ExtractionCandidate> candidates;

    public NumPackagesCandidateMetric(ArrayList<ExtractionCandidate> candidates) {
        this.candidates = candidates;
    }

    @NotNull
    @Override
    public String getDisplayName() {
        return StockMetricsBundle.message("number.of.used.packages.candidates.display.name");
    }

    @NotNull
    @Override
    public String getAbbreviation() {
        return StockMetricsBundle.message("number.of.used.packages.candidates.abbreviation");
    }

    @NotNull
    @Override
    public MetricType getType() {
        return MetricType.Count;
    }

    @NotNull
    @Override
    public MetricCalculator createCalculator() {
        return new NumPackageAccessesCalculator(candidates);
    }
}
