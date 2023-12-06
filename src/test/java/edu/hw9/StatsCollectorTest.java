package edu.hw9;

import org.junit.jupiter.api.Test;
import java.util.List;

public class StatsCollectorTest {
    @Test
    void testCollector() {
        StatsCollector collector = new StatsCollector();

        collector.push("metric_name1", new double[] {0.1, 0.05, 1.4, 5.1, 0.3});
        collector.push("metric_name2", new double[] {2.0, 3.0, 4.0});

        List<StatsResult> statsResults;
        try {
            statsResults = collector.stats();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        for (StatsResult result : statsResults) {
            System.out.println("Metric: " + result.metricName());
            System.out.println("Sum: " + result.sum());
            System.out.println("Average: " + result.average());
            System.out.println("Min: " + result.min());
            System.out.println("Max: " + result.max());
            System.out.println();
        }
    }
}
