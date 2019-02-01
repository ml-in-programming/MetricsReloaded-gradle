package com.sixrr.stockmetrics.candidateMetrics;

import com.intellij.psi.PsiField;
import com.sixrr.metrics.MetricCalculator;
import com.sixrr.metrics.MetricType;
import com.sixrr.stockmetrics.candidateCalculators.AbstractCouplingCohesionCalculator;
import com.sixrr.stockmetrics.i18n.StockMetricsBundle;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.research.groups.ml_methods.utils.ExtractionCandidate;

import java.util.ArrayList;

public class FieldCohesion2CandidateMetric extends CandidateMetric {
    private ArrayList<ExtractionCandidate> candidates;

    public FieldCohesion2CandidateMetric(ArrayList<ExtractionCandidate> candidates) {
        this.candidates = candidates;
    }

    @NotNull
    @Override
    public String getDisplayName() {
        return StockMetricsBundle.message("field.cohesion.2.candidate.display.name");
    }

    @NotNull
    @Override
    public String getAbbreviation() {
        return StockMetricsBundle.message("field.cohesion.2.candidate.abbreviation");
    }

    @NotNull
    @Override
    public MetricType getType() {
        return MetricType.Count;
    }

    @NotNull
    @Override
    public MetricCalculator createCalculator() {
        return new AbstractCouplingCohesionCalculator<>(
                candidates,
                PsiField.class,
                false,
                false
        );
    }
}