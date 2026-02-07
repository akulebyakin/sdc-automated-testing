package com.kulebiakin.triangle;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
class TriangleClassifierTest {

    private final TriangleClassifier classifier = new TriangleClassifier();

    // isValidTriangle() Tests
    @ParameterizedTest(name = "Valid triangle: ({0}, {1}, {2})")
    @CsvSource({"3, 4, 5", "5, 5, 5", "3, 3, 5"})
    @DisplayName("isValidTriangle: Should return true for valid triangles")
    void testIsValidTriangleValid(int a, int b, int c) {
        assertThat(classifier.isValidTriangle(a, b, c)).isTrue();
    }

    @ParameterizedTest(name = "Invalid triangle: ({0}, {1}, {2})")
    @CsvSource({"1, 2, 3", "1, 1, 3", "0, 3, 3"})
    @DisplayName("isValidTriangle: Should return false for invalid triangles")
    void testIsValidTriangleInvalid(int a, int b, int c) {
        assertThat(classifier.isValidTriangle(a, b, c)).isFalse();
    }

    // Type Check Methods Tests
    @Test
    @DisplayName("isEquilateral: Should correctly identify equilateral triangles")
    void testIsEquilateral() {
        assertThat(classifier.isEquilateral(3, 3, 3)).isTrue();
        assertThat(classifier.isEquilateral(3, 3, 5)).isFalse();
    }

    @Test
    @DisplayName("isIsosceles: Should correctly identify isosceles triangles")
    void testIsIsosceles() {
        assertThat(classifier.isIsosceles(3, 3, 5)).isTrue();
        assertThat(classifier.isIsosceles(5, 3, 3)).isTrue();
        assertThat(classifier.isIsosceles(3, 3, 3)).isFalse(); // equilateral
        assertThat(classifier.isIsosceles(3, 4, 5)).isFalse(); // scalene
    }

    @Test
    @DisplayName("isScalene: Should correctly identify scalene triangles")
    void testIsScalene() {
        assertThat(classifier.isScalene(3, 4, 5)).isTrue();
        assertThat(classifier.isScalene(3, 3, 5)).isFalse();
    }

    // classify() Parameterized Tests
    @ParameterizedTest(name = "classify({0}, {1}, {2}) = {3}")
    @MethodSource("triangleTestCases")
    @DisplayName("classify: Should correctly classify all triangle types")
    void testClassify(int a, int b, int c, TriangleType expected) {
        assertThat(classifier.classify(a, b, c)).isEqualTo(expected);
    }

    static Stream<Arguments> triangleTestCases() {
        return Stream.of(
            // Equilateral
            Arguments.of(3, 3, 3, TriangleType.EQUILATERAL),
            Arguments.of(100, 100, 100, TriangleType.EQUILATERAL),

            // Isosceles (different orderings)
            Arguments.of(3, 3, 5, TriangleType.ISOSCELES),
            Arguments.of(5, 3, 3, TriangleType.ISOSCELES),
            Arguments.of(3, 5, 3, TriangleType.ISOSCELES),

            // Scalene
            Arguments.of(3, 4, 5, TriangleType.SCALENE),
            Arguments.of(7, 10, 12, TriangleType.SCALENE),

            // Not a triangle
            Arguments.of(1, 2, 3, TriangleType.NOT_A_TRIANGLE),
            Arguments.of(1, 1, 3, TriangleType.NOT_A_TRIANGLE),
            Arguments.of(0, 3, 3, TriangleType.NOT_A_TRIANGLE)
        );
    }

    // Order Invariance Test
    @Test
    @DisplayName("Order invariance: Result should be same regardless of side order")
    void testOrderInvariance() {
        TriangleType result = classifier.classify(3, 4, 5);

        assertThat(result).isEqualTo(TriangleType.SCALENE);
        assertThat(classifier.classify(4, 5, 3)).isEqualTo(result);
        assertThat(classifier.classify(5, 3, 4)).isEqualTo(result);
    }

    // Boundary Tests
    @Test
    @DisplayName("Boundary: Edge cases should be handled correctly")
    void testBoundaryConditions() {
        assertThat(classifier.classify(1, 1, 1))
            .as("Minimal equilateral")
            .isEqualTo(TriangleType.EQUILATERAL);

        assertThat(classifier.classify(1, 2, 3))
            .as("Degenerate triangle (a + b = c)")
            .isEqualTo(TriangleType.NOT_A_TRIANGLE);

        assertThat(classifier.classify(1000000, 1000000, 1000000))
            .as("Large values")
            .isEqualTo(TriangleType.EQUILATERAL);
    }

    // Exception Tests
    @Test
    @DisplayName("Exception: Should throw for negative sides")
    void testNegativeSidesThrowException() {
        assertThatThrownBy(() -> classifier.classify(-1, 3, 3))
            .isInstanceOf(IllegalArgumentException.class);

        assertThatThrownBy(() -> classifier.classify(3, -1, 3))
            .isInstanceOf(IllegalArgumentException.class);

        assertThatThrownBy(() -> classifier.classify(3, 3, -1))
            .isInstanceOf(IllegalArgumentException.class);
    }
}
