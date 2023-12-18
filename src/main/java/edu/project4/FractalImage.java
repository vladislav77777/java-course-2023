package edu.project4;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

public class FractalImage {

    private final Pixel[][] data;
    public final int width;
    public final int height;
    public static final Logger LOGGER = Logger.getLogger(FractalImage.class.getName());

    public FractalImage(int width, int height) {
        this.width = width;
        this.height = height;
        this.data = new Pixel[width][height];
        initialize();
    }

    private void initialize() {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                data[x][y] = new Pixel(1, 1, 2, 0, 0);
            }
        }
    }

    public boolean contains(int x, int y) {
        return x >= 0 && x < width && y >= 0 && y < height;
    }

    public Pixel pixel(int x, int y) {
        return data[x][y];
    }

    public void updatePixel(int x, int y, Pixel pixel) {
        synchronized (data[x][y]) {
            data[x][y] = pixel;
        }
    }

    public void saveToFile(ImageFormat format) {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        correction(); // add gamma correction
        // Assuming each pixel is represented by an instance of the Pixel class
        final int redShift = 16;
        final int greenShift = 8;

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Pixel pixel = pixel(x, y);
                int rgb = ((pixel.r()) << redShift) | ((pixel.g() + 1) << greenShift) | (pixel.b() + 1);
                image.setRGB(x, y, rgb);
            }
        }

        try {
            String fileExtension = format.name().toLowerCase(); // Get the file extension from enum
            File output = new File("fractal_output." + fileExtension);  // Change the file name and format
            ImageIO.write(image, fileExtension, output);
            LOGGER.info("Fractal image saved to: " + output.getAbsolutePath());
        } catch (IOException e) {
            LOGGER.severe("Error saving fractal image: " + e.getMessage());
        }
    }

    void correction() { // gamma-correction
        double max = 0.0;
        final double gamma = 2.2;

        // Find the maximum normal value
        for (int row = 0; row < width; row++) {
            for (int col = 0; col < height; col++) {
                Pixel pixel = pixel(row, col);
                if (pixel.hitCount() != 0) {
                    pixel.setNormal(Math.log10(pixel.hitCount()));
                    if (pixel.normal() > max) {
                        max = pixel.normal();
                    }
                }
            }
        }

        // Normalize and apply gamma correction
        for (int row = 0; row < width; row++) {
            for (int col = 0; col < height; col++) {
                Pixel pixel = pixel(row, col);
                if (pixel.hitCount() != 0) {
                    pixel.setNormal(pixel.normal() / max);
                    pixel.setR((int) (pixel.r() * Math.pow(pixel.normal(), (1.0 / gamma))));
                    pixel.setG((int) (pixel.g() * Math.pow(pixel.normal(), (1.0 / gamma))));
                    pixel.setB((int) (pixel.b() * Math.pow(pixel.normal(), (1.0 / gamma))));
                }
            }
        }
    }
}

