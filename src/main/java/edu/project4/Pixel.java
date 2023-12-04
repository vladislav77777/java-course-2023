package edu.project4;

// доменная модель, поверх которой работает алгоритм
public class Pixel {
    private int r;
    private int g;
    private int b;
    private int hitCount;
    private double normal;

    public Pixel(int r, int g, int b, int hitCount, double normal) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.hitCount = hitCount;
        this.normal = normal;
    }

    // Add getters and setters as needed

    public int r() {
        return r;
    }

    public void setR(int r) {
        this.r = r;
    }

    public int g() {
        return g;
    }

    public void setG(int g) {
        this.g = g;
    }

    public int b() {
        return b;
    }

    public void setB(int b) {
        this.b = b;
    }

    public int hitCount() {
        return hitCount;
    }

    public double normal() {
        return normal;
    }

    public void setNormal(double normal) {
        this.normal = normal;
    }
}
