package day3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Day3 {
    long joltage = 0;

    void main(String[] args) {
        String filePath = "day3/day3Input.txt";

        try (BufferedReader br = new BufferedReader(new FileReader(filePath));) {
            for (String line = br.readLine(); line != null; line = br.readLine()) {
                joltage += calculateJoltagePart2(line, 12);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.printf("Joltage: %s", joltage);
    }

    private void calculateJoltage(String line) {
        String[] splitLine = line.split("");

        // find the first instance of the largest integer, but make sure it's not the last one
        int largestIndex = 0;
        for (int i = 1; i < splitLine.length - 1; i++) {
            if (Integer.parseInt(splitLine[i]) > Integer.parseInt(splitLine[largestIndex])) {
                largestIndex = i;
            }
        }

        int secondIndex = largestIndex + 1;
        for (int i = secondIndex; i < splitLine.length; i++) {
            if (Integer.parseInt(splitLine[i]) > Integer.parseInt(splitLine[secondIndex])) {
                secondIndex = i;
            }
        }

        joltage += (Integer.parseInt(splitLine[largestIndex]) * 10L) + Integer.parseInt(splitLine[secondIndex]);
    }

    private long calculateJoltagePart2(String line, int count) {
        StringBuilder result = new StringBuilder();
        int startIndex = 0;

        for (int remaining = count; remaining > 0; remaining--) {
            int endIndex = line.length() - remaining;
            int maxIndex = startIndex;
            char maxDigit = '0';

            for (int i = startIndex; i <= endIndex; i++) {
                if (line.charAt(i) > maxDigit) {
                    maxDigit = line.charAt(i);
                    maxIndex = i;
                }
            }

            result.append(maxDigit);
            startIndex = maxIndex + 1;
        }

        return Long.parseLong(result.toString());
    }
}

