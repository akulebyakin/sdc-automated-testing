package com.kulebiakin.triangle;

public class TriangleClassifier {

    public boolean isValidTriangle(int a, int b, int c) {
        // Check that sides are positive
        if (a <= 0 || b <= 0 || c <= 0) {
            return false;
        }

        // sum of any two sides must be greater than the third
        // (Using long to prevent integer overflow)
        return ((long) a + b > c) && ((long) b + c > a) && ((long) a + c > b);
    }

    public boolean isEquilateral(int a, int b, int c) {
        return a == b && b == c;
    }

    // Exactly two sides are equal (but not all three)
    public boolean isIsosceles(int a, int b, int c) {
        return (a == b || b == c || a == c) && !(a == b && b == c);
    }

    // All sides are different
    public boolean isScalene(int a, int b, int c) {
        return a != b && b != c && a != c;
    }

    public TriangleType classify(int a, int b, int c) {
        // Validate non negative parameters
        if (a < 0 || b < 0 || c < 0) {
            throw new IllegalArgumentException("Side lengths cannot be negative");
        }

        // Check if it's a valid triangle
        if (!isValidTriangle(a, b, c)) {
            return TriangleType.NOT_A_TRIANGLE;
        }

        // Classify the triangle type
        if (isEquilateral(a, b, c)) {
            return TriangleType.EQUILATERAL;
        } else if (isIsosceles(a, b, c)) {
            return TriangleType.ISOSCELES;
        } else {
            return TriangleType.SCALENE;
        }
    }
}
