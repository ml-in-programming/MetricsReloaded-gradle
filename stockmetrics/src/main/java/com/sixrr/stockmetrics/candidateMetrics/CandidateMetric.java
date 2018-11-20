package com.sixrr.stockmetrics.candidateMetrics;

import com.sixrr.metrics.MetricCategory;
import com.sixrr.stockmetrics.metricModel.BaseMetric;
import org.jetbrains.annotations.NotNull;

public abstract class CandidateMetric extends BaseMetric {

    @NotNull
    @Override
    public MetricCategory getCategory() {
        return MetricCategory.ExtractionCandidate;
    }
}
