package com.sixrr.stockmetrics.candidateMetrics;

import com.sixrr.metrics.MetricCalculator;
import com.sixrr.metrics.MetricType;
import com.sixrr.stockmetrics.candidateCalculators.PackageAccessCouplingCohesionCalculator;
import com.sixrr.stockmetrics.candidateCalculators.TypeAccessCouplingCohesionCalculator;
import com.sixrr.stockmetrics.i18n.StockMetricsBundle;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.research.groups.ml_methods.utils.ExtractionCandidate;

import java.util.ArrayList;

public class PackageAccessCohesion2CandidateMetric extends CandidateMetric {
    private ArrayList<ExtractionCandidate> candidates;

    public PackageAccessCohesion2CandidateMetric(ArrayList<ExtractionCandidate> candidates) {
        this.candidates = candidates;
    }

    @NotNull
    @Override
    public String getDisplayName() {
        return StockMetricsBundle.message("package.access.cohesion.2.candidate.display.name");
    }

    @NotNull
    @Override
    public String getAbbreviation() {
        return StockMetricsBundle.message("package.access.cohesion.2.candidate.abbreviation");
    }

    @NotNull
    @Override
    public MetricType getType() {
        return MetricType.Ratio;
    }

    @NotNull
    @Override
    public MetricCalculator createCalculator() {
        return new PackageAccessCouplingCohesionCalculator(
                candidates,
                false,
                false
        );
    }
}
