package com.kulebiakin.triangle;

import java.util.Scanner;

public class TriangleApp {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        TriangleClassifier classifier = new TriangleClassifier();

        System.out.println("Triangle Classification");
        System.out.println("Enter three non-negative integers:");

        System.out.print("Side a: ");
        int a = scanner.nextInt();

        System.out.print("Side b: ");
        int b = scanner.nextInt();

        System.out.print("Side c: ");
        int c = scanner.nextInt();

        try {
            TriangleType result = classifier.classify(a, b, c);
            System.out.println("\nResult: " + result);
            System.out.println(result.getDescription());
        } catch (IllegalArgumentException e) {
            System.out.println("\nError: " + e.getMessage());
        }

        scanner.close();
    }
}
