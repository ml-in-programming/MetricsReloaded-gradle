package com.sixrr.stockmetrics.methodMetrics;

import com.sixrr.metrics.MetricCalculator;
import com.sixrr.metrics.MetricType;
import com.sixrr.stockmetrics.i18n.StockMetricsBundle;
import com.sixrr.stockmetrics.methodCalculators.NumUsedPackagesCalculator;
import com.sixrr.stockmetrics.methodCalculators.NumUsedTypesCalculator;
import org.jetbrains.annotations.NotNull;

public class NumUsedPackagesMetric extends MethodMetric {
    @NotNull
    @Override
    public String getDisplayName() {
        return StockMetricsBundle.message("number.of.used.packages.display.name");
    }

    @NotNull
    @Override
    public String getAbbreviation() {
        return StockMetricsBundle.message("number.of.used.packages.abreviation");
    }

    @NotNull
    @Override
    public MetricType getType() {
        return MetricType.Count;
    }

    @NotNull
    @Override
    public MetricCalculator createCalculator() {
        return new NumUsedPackagesCalculator();
    }
}
