package edu.hw9;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public final class StatsCollector {
    private final ConcurrentHashMap<String, List<Double>> data;

    StatsCollector() {
        this.data = new ConcurrentHashMap<>();
    }

    public void push(String metricName, double[] values) {
        if (!data.containsKey(metricName)) {
            data.put(metricName, Collections.synchronizedList(new ArrayList<>()));
        }

        List<Double> metricData = data.get(metricName);
        synchronized (metricData) {
            for (double value : values) {
                metricData.add(value);
            }
        }
    }

    public List<StatsResult> stats() throws InterruptedException {
        List<StatsResult> results = new ArrayList<>();
        List<Callable<StatsResult>> tasks = new ArrayList<>();

        for (String metricName : data.keySet()) {
            tasks.add(() -> calculateStats(metricName));
        }

        ExecutorService executorService =
            Executors.newFixedThreadPool(Math.min(data.size(), Runtime.getRuntime().availableProcessors()));

        try {
            List<Future<StatsResult>> futures = executorService.invokeAll(tasks);

            for (Future<StatsResult> future : futures) {
                results.add(future.get());
            }
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } finally {
            executorService.shutdown();
        }

        return results;
    }

    private StatsResult calculateStats(String metricName) {
        List<Double> values = data.get(metricName);
        double sum = 0.0;
        double min = Double.MAX_VALUE;
        double max = Double.MIN_VALUE;

        synchronized (values) {
            for (double value : values) {
                sum += value;
                min = Math.min(min, value);
                max = Math.max(max, value);
            }
        }

        double average = values.isEmpty() ? 0.0 : sum / values.size();
        return new StatsResult(metricName, sum, average, min, max);
    }

}


