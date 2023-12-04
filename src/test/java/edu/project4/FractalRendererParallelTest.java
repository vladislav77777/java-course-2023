package edu.project4;

import java.math.BigInteger;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.junit.jupiter.api.Test;
import java.util.concurrent.TimeUnit ;

public class FractalRendererParallelTest {

    private static final double XMIN = -1.777, XMAX = 1.777; // world parameters
    private static final double YMIN = -1, YMAX = 1; // world parameters
    private static final int width = 1920; // output image parameters
    private static final int height = 1080; // output image parameters
    private static final int NUM_THREADS = 10; // Set the desired number of threads

    @Test
    void testRenderParallel() {
        // set up the parameters
        Rect world = new Rect(XMIN, YMIN, 2 * XMAX, 2 * YMAX);
        FractalImage canvas = new FractalImage(width, height);
        List<Transformation> variations = FractalRenderer.getVariations();
        int samples = 40000;
        short iterPerSample = 1000;
        int eqCount = 8;
        long seed = new BigInteger("d23c14a4138969db", 16).longValue();
        int symmetry = 1;
        FractalRenderer fractalRenderer = new FractalRenderer();

        ExecutorService executorService = Executors.newFixedThreadPool(NUM_THREADS);
        int samplesPerThread = samples / NUM_THREADS;
        long startTime = System.currentTimeMillis(); // Measure start time

        try {
            for (int i = 0; i < NUM_THREADS; i++) {
                executorService.submit(() -> fractalRenderer.render(
                    canvas,
                    world,
                    variations,
                    samplesPerThread,
                    iterPerSample,
                    seed,
                    symmetry,
                    eqCount
                ));
            }
        } finally {
            executorService.shutdown();
            try {
                Boolean b = executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        // call the render method

        long endTime = System.currentTimeMillis(); // Measure end time

        System.out.println("Parallel execution time (40_000 iterations): " + (endTime - startTime) + " milliseconds");

        canvas.saveToFile(ImageFormat.PNG); // choose any
    }
}
