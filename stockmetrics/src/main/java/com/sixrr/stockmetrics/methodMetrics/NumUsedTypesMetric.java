package com.sixrr.stockmetrics.methodMetrics;

import com.sixrr.metrics.MetricCalculator;
import com.sixrr.metrics.MetricType;
import com.sixrr.stockmetrics.i18n.StockMetricsBundle;
import com.sixrr.stockmetrics.methodCalculators.NumAssertsCalculator;
import com.sixrr.stockmetrics.methodCalculators.NumUsedTypesCalculator;
import org.jetbrains.annotations.NotNull;

public class NumUsedTypesMetric extends MethodMetric {
    @NotNull
    @Override
    public String getDisplayName() {
        return StockMetricsBundle.message("number.of.used.types.display.name");
    }

    @NotNull
    @Override
    public String getAbbreviation() {
        return StockMetricsBundle.message("number.of.used.types.abbreviation");
    }

    @NotNull
    @Override
    public MetricType getType() {
        return MetricType.Count;
    }

    @NotNull
    @Override
    public MetricCalculator createCalculator() {
        return new NumUsedTypesCalculator();
    }
}
