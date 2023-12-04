package edu.project4;

import org.junit.jupiter.api.Test;
import java.math.BigInteger;
import java.util.List;
import static edu.project4.FractalRenderer.getVariations;

public class FractalRendererTest {

    private static final double XMIN = -1.777, XMAX = 1.777; // world parameters
    private static final double YMIN = -1, YMAX = 1; // world parameters
    private static final int width = 1920; // output image parameters
    private static final int height = 1080; // output image parameters

    @Test
    void testRender(){
        // set up the parameters

        Rect world = new Rect(XMIN, YMIN, 2 * XMAX, 2 * YMAX);
        FractalImage canvas = new FractalImage(width, height);
        List<Transformation> variations = getVariations();
        int samples = 40000;
        short iterPerSample = 1000;
        int eqCount = 8;
        long seed = new BigInteger("d23c14a4138969db", 16).longValue();
        int symmetry = 1;

        FractalRenderer fractalRenderer = new FractalRenderer();

        // call the render method
        long startTime = System.currentTimeMillis(); // Measure start time

        fractalRenderer.render(canvas, world, variations, samples, iterPerSample, seed, symmetry, eqCount);
        long endTime = System.currentTimeMillis(); // Measure end time

        System.out.println("Single execution time (40_000 iterations): " + (endTime - startTime) + " milliseconds");

        canvas.saveToFile(ImageFormat.JPEG);
    }
}
