package com.sixrr.stockmetrics.candidateMetrics;

import com.sixrr.metrics.MetricCalculator;
import com.sixrr.metrics.MetricType;
import com.sixrr.stockmetrics.candidateCalculators.NumSwitchOperatorsCalculator;
import com.sixrr.stockmetrics.i18n.StockMetricsBundle;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.research.groups.ml_methods.utils.ExtractionCandidate;

import java.util.ArrayList;

public class NumSwitchOperatorsCandidateMetric extends CandidateMetric {

    private ArrayList<ExtractionCandidate> candidates;

    public NumSwitchOperatorsCandidateMetric(ArrayList<ExtractionCandidate> candidates) {
        this.candidates = candidates;
    }

    @NotNull
    @Override
    public String getDisplayName() {
        return StockMetricsBundle.message("number.of.switch.candidates.display.name");
    }

    @NotNull
    @Override
    public String getAbbreviation() {
        return StockMetricsBundle.message("number.of.switch.candidates.abbreviation");
    }

    @NotNull
    @Override
    public MetricType getType() {
        return MetricType.Count;
    }

    @NotNull
    @Override
    public MetricCalculator createCalculator() {
        return new NumSwitchOperatorsCalculator(candidates);
    }
}
