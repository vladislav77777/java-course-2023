package edu.project4;

import java.util.List;

@FunctionalInterface
public interface Renderer {
    @SuppressWarnings("checkstyle:ParameterNumber")
    void render(
        FractalImage canvas, Rect world, List<Transformation> variations,
        int samples, short iterPerSample, long seed, int symmetry, int eqCount
    );
}

