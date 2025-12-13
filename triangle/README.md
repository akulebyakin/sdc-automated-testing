# Triangle Classification Module

A Java module for determining whether three non-negative integers can form a triangle, and if so, classifying the
triangle type (equilateral, isosceles, or scalene).

## Task Description

Given three non-negative integers, determine:

1. Whether these numbers can be the sides of a triangle
2. If yes, determine the type of triangle (isosceles, equilateral, or scalene)

## Triangle Rules

### Triangle Validity (Triangle Inequality Theorem)

For three sides a, b, c to form a valid triangle:

- All sides must be positive (> 0)
- a + b > c
- b + c > a
- a + c > b

### Triangle Classification

- **Equilateral**: All three sides are equal (a = b = c)
- **Isosceles**: Exactly two sides are equal
- **Scalene**: All three sides are different

## Domain Testing Analysis

### Input Domain

- Three non-negative integers representing triangle sides (a, b, c)
- Valid range: 0 to Integer.MAX_VALUE

### Domain Testing Table

| Category             | Condition     | Test Values (a, b, c)           | Expected Result          | Rationale              |
|----------------------|---------------|---------------------------------|--------------------------|------------------------|
| **Equilateral**      | a = b = c     | (3, 3, 3)                       | EQUILATERAL              | Standard case          |
|                      |               | (100, 100, 100)                 | EQUILATERAL              | Larger values          |
| **Isosceles**        | a = b, a != c | (3, 3, 5)                       | ISOSCELES                | First two equal        |
|                      | b = c, a != b | (5, 3, 3)                       | ISOSCELES                | Last two equal         |
|                      | a = c, a != b | (3, 5, 3)                       | ISOSCELES                | First and last equal   |
| **Scalene**          | a != b != c   | (3, 4, 5)                       | SCALENE                  | Pythagorean triple     |
|                      |               | (7, 10, 12)                     | SCALENE                  | Non-consecutive        |
| **Not Triangle**     | a + b <= c    | (1, 2, 3)                       | NOT_A_TRIANGLE           | Degenerate (a + b = c) |
|                      | a + b < c     | (1, 1, 3)                       | NOT_A_TRIANGLE           | Sum less than third    |
|                      | Any side = 0  | (0, 3, 3)                       | NOT_A_TRIANGLE           | Zero side              |
| **Boundary**         | Minimal valid | (1, 1, 1)                       | EQUILATERAL              | Smallest valid         |
|                      | Large values  | (1000000, 1000000, 1000000)     | EQUILATERAL              | Large numbers          |
| **Order Invariance** | Permutations  | (3, 4, 5), (4, 5, 3), (5, 3, 4) | SCALENE                  | Same result            |
| **Negative Input**   | Any side < 0  | (-1, 3, 3)                      | IllegalArgumentException | Invalid input          |

## Test Summary

| Test Method                     | Test Count | Description                          |
|---------------------------------|------------|--------------------------------------|
| testIsValidTriangle_Valid       | 3          | Valid triangle combinations          |
| testIsValidTriangle_Invalid     | 3          | Invalid triangles (inequality, zero) |
| testIsEquilateral               | 1          | Equilateral identification           |
| testIsIsosceles                 | 1          | Isosceles identification             |
| testIsScalene                   | 1          | Scalene identification               |
| testClassify (parameterized)    | 10         | All triangle types via @MethodSource |
| testOrderInvariance             | 1          | Permutation consistency              |
| testBoundaryConditions          | 1          | Edge cases                           |
| testNegativeSidesThrowException | 1          | Exception handling                   |
| testTriangleTypeEnum            | 1          | Enum validation                      |
| **Total**                       | **23**     |                                      |

## Running the Application

```bash
# Build the project
mvn clean package -DskipTests

# Run the console application
java -jar triangle/target/triangle-1.0-SNAPSHOT.jar
```

Example session:

```
=== Triangle Classification ===
Enter three non-negative integers:
Side a: 3
Side b: 4
Side c: 5

Result: SCALENE
Scalene triangle - all sides are different
```

## Running Tests

```bash
# From project root
mvn clean test

# From triangle module
cd triangle
mvn test
```
