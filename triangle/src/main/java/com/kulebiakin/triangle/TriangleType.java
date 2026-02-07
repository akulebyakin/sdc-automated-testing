package com.kulebiakin.triangle;

public enum TriangleType {
    EQUILATERAL("Equilateral triangle - all sides are equal"),
    ISOSCELES("Isosceles triangle - two sides are equal"),
    SCALENE("Scalene triangle - all sides are different"),
    NOT_A_TRIANGLE("Not a valid triangle - violates triangle inequality");

    private final String description;

    TriangleType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
