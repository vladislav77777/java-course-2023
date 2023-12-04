package edu.project4;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FractalRenderer implements Renderer {

    private static final int RGB_MAX_VALUE = 256;

    static List<Transformation> getVariations() {
        List<Transformation> variations = new ArrayList<>();

        // Example variations for an interesting fractal
        Transformation linear = (Point p) -> new Point(p.x(), p.y());  // Linear variation
        Transformation sinusoidal = (Point p) -> new Point(Math.sin(p.x()), Math.sin(p.y()));  // Sinusoidal variation
        Transformation swirl = (Point p) -> {
            double rsq = p.x() * p.x() + p.y() * p.y();
            double newX = p.x() * Math.sin(rsq) - p.y() * Math.cos(rsq);
            double newY = p.x() * Math.cos(rsq) + p.y() * Math.sin(rsq);
            return new Point(newX, newY);
        };

        // Horseshoe variation
        Transformation horseshoe = (Point p) -> {
            double r = Math.sqrt(p.x() * p.x() + p.y() * p.y());
            double theta = Math.atan2(p.x(), p.y());
            double newX = r * Math.cos(2 * theta);
            double newY = r * Math.sin(2 * theta);
            return new Point(newX, newY);
        };

        // Ripple variation
        Transformation ripple = (Point p) -> {
            double r = Math.sqrt(p.x() * p.x() + p.y() * p.y());
            double newX = p.x() * Math.sin(r * r) - p.y() * Math.cos(r * r);
            double newY = p.x() * Math.cos(r * r) + p.y() * Math.sin(r * r);
            return new Point(newX, newY);
        };
        Transformation heart = (Point p) -> {
            double r = Math.sqrt(p.x() * p.x() + p.y() * p.y());
            double theta = Math.atan2(p.x(), p.y());
            double newX = r * Math.sin(theta * r);
            double newY = -r * Math.cos(theta * r);
            return new Point(newX, newY);
        };
        Transformation v18 = (Point p) -> {
            double expXMinus1 = Math.exp(p.x() - 1);
            double newX = expXMinus1 * Math.cos(Math.PI * p.y());
            double newY = expXMinus1 * Math.sin(Math.PI * p.y());
            return new Point(newX, newY);
        };

        Transformation disc = (Point p) -> {
            double r = Math.sqrt(p.x() * p.x() + p.y() * p.y());
            double theta = Math.atan2(p.x(), p.y());
            double newX = theta / Math.PI * Math.sin(Math.PI * r);
            double newY = theta / Math.PI * Math.cos(Math.PI * r);
            return new Point(newX, newY);
        };

        Transformation handkerchief = (Point p) -> {
            double r = Math.sqrt(p.x() * p.x() + p.y() * p.y());
            double theta = Math.atan2(p.x(), p.y()); // Using arctan(x/y) for θ
            double newX = r * (Math.sin(theta + r));
            double newY = r * (Math.cos(theta - r));
            return new Point(newX, newY);
        };
        Transformation polar = (Point p) -> {
            double r = Math.sqrt(p.x() * p.x() + p.y() * p.y());
            double theta = Math.atan2(p.x(), p.y()); // Using arctan(x/y) for θ
            double newX = theta / Math.PI;
            double newY = r - 1;
            return new Point(newX, newY);
        };
        Transformation spherical = (Point p) -> {
            double r = p.x() * p.x() + p.y() * p.y();
            double newX = p.x() / r;
            double newY = p.y() / r;
            return new Point(newX, newY);
        };

        Transformation spiral = (Point p) -> {
            double r = Math.sqrt(p.x() * p.x() + p.y() * p.y());
            double theta = Math.atan2(p.x(), p.y()); // Using arctan(x/y) for θ
            double newX = (1 / r) * (Math.cos(theta) + Math.sin(r));
            double newY = (1 / r) * (Math.sin(theta) - Math.cos(r));
            return new Point(newX, newY);
        };

        Transformation popcorn = (Point p) -> {
            double c = 1.0; // You can adjust the value of c as needed
            double f = 1.0;
            final int three = 3;
            double newX = p.x() + c * Math.sin(Math.tan(three * p.y()));
            double newY = p.y() + f * Math.sin(Math.tan(three * p.x()));
            return new Point(newX, newY);
        };

        variations.add(heart);

        return variations;
    }

    @SuppressWarnings("checkstyle:ParameterNumber")
    @Override
    public void render(
        FractalImage canvas,
        Rect world,
        List<Transformation> variations,
        int samples,
        short iterPerSample,
        long seed,
        int symmetry,
        int eqCount
    ) {
        Random random = new Random(seed);
        final double XMAX = world.width() / 2;
        final double XMIN = world.x();
        final double YMAX = world.height() / 2;
        final double YMIN = world.y();
        final int numIterSkip = 20;
        List<List<Double>> affineTransformations = generateAffineTransformations(eqCount);

        for (int num = 0; num < samples; ++num) {
            Point pw = randomPoint(world, random); // choose random point

            for (short step = 0; step < iterPerSample; ++step) {

                int i = random.nextInt(eqCount);
                pw = applyRandomTransformation(pw, affineTransformations, i); // apply one of the affine transformations

                Transformation variation = randomVariation(variations, random.nextInt(variations.size()));
                pw = variation.apply(pw); //  Apply non-linear transformation

                double theta2 = 0.0;
                for (int s = 0; s < symmetry; theta2 += Math.PI * 2 / symmetry, ++s) {
                    pw = rotate(pw, theta2);
                    if (step < numIterSkip || !world.contains(pw)) {  // skip first 20 iterations
                        continue;
                    }

                    int x1 = (int) (canvas.width - ((XMAX - pw.x()) / (XMAX - XMIN)) * canvas.width);
                    int y1 = (int) (canvas.height - ((YMAX - pw.y()) / (YMAX - YMIN)) * canvas.height);
                    // If the point is in the image area
                    if (!canvas.contains(x1, y1)) {
                        continue;
                    }

                    int hit = canvas.pixel(x1, y1).hitCount();
                    Pixel pixel = canvas.pixel(x1, y1);
                    // We’re checking to see if it’s her first time
                    final int redValue = 6;
                    final int greenValue = 7;
                    final int blueValue = 8;
                    if (hit == 0) {  // synchronized
                        canvas.updatePixel(
                            x1,
                            y1,

                            new Pixel(
                                (int) (RGB_MAX_VALUE * affineTransformations.get(i).get(redValue)),
                                (int) (RGB_MAX_VALUE * affineTransformations.get(i).get(greenValue)),
                                (int) (RGB_MAX_VALUE * affineTransformations.get(i).get(blueValue)),
                                hit + 1, 0
                            )
                        );
                    } else {  // synchronized
                        canvas.updatePixel(
                            x1,
                            y1,
                            new Pixel(
                                (pixel.r() + (int) (RGB_MAX_VALUE * affineTransformations.get(i).get(redValue))) / 2,
                                (pixel.g() + (int) (RGB_MAX_VALUE * affineTransformations.get(i).get(greenValue))) / 2,
                                (pixel.b() + (int) (RGB_MAX_VALUE * affineTransformations.get(i).get(blueValue))) / 2,
                                hit + 1, 0
                            )
                        );
                    }
                }

            }
        }
    }


    private List<List<Double>> generateAffineTransformations(int eqCount) {
        List<List<Double>> transformations = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < eqCount; i++) {
            List<Double> transformation = new ArrayList<>();

            // Generate coefficients for the linear transformation
            double a;
            double b;
            double c;
            double d;
            double e;
            double f;

            do {
                a = random.nextDouble() * 2 - 1; // Range [-1, 1)
                b = random.nextDouble() * 2 - 1; // Range [-1, 1)
                c = random.nextDouble() * 2 - 1; // Range [-1, 1)
                d = random.nextDouble() * 2 - 1; // Range [-1, 1)
                e = random.nextDouble() * 2 - 1; // Range [-1, 1)
                f = random.nextDouble() * 2 - 1; // Range [-1, 1)
            } while (a * a + d * d >= 1 || b * b + e * e >= 1
                || a * a + d * d + b * b + e * e >= 1 + Math.pow(a * e - b * d, 2));

            // Generate RGB start color values
            double red = random.nextDouble();
            double green = random.nextDouble();
            double blue = random.nextDouble();

            // Add coefficients and start color values to the transformation list
            transformation.add(a);
            transformation.add(b);
            transformation.add(c);
            transformation.add(d);
            transformation.add(e);
            transformation.add(f);
            transformation.add(red);
            transformation.add(green);
            transformation.add(blue);

            transformations.add(transformation);
        }

        return transformations;
    }

    private Point applyRandomTransformation(Point pw, List<List<Double>> affineTransformations, int i) {
        final int three = 3;
        final int four = 4;
        final int five = 5;
        double a = affineTransformations.get(i).get(0);
        double b = affineTransformations.get(i).get(1);
        double c = affineTransformations.get(i).get(2);
        double d = affineTransformations.get(i).get(three);
        double e = affineTransformations.get(i).get(four);
        double f = affineTransformations.get(i).get(five);

        return new Point(a * pw.x() + b * pw.y() + c, d * pw.x() + e * pw.y() + f);
    }

    private Point randomPoint(Rect rect, Random random) {
        double x = rect.x() + rect.width() * random.nextDouble();
        double y = rect.y() + rect.height() * random.nextDouble();
        return new Point(x, y);
    }

    private Point rotate(Point point, double angle) {
        double x = point.x() * Math.cos(angle) - point.y() * Math.sin(angle);
        double y = point.x() * Math.sin(angle) + point.y() * Math.cos(angle);
        return new Point(x, y);
    }

    private Transformation randomVariation(List<Transformation> variations, int random) {
        return variations.get(random);
    }
}
