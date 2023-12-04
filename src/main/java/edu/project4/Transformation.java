package edu.project4;

// функция-преобразование
@FunctionalInterface
public interface Transformation {
    Point apply(Point p);
}
