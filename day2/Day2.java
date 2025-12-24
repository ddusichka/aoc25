package day2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Day2 {
    static final String regex = "(\\d+)-(\\d+)";
    static final Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);

    long invalidSum = 0;

    // part 2
    void main(String[] args) {
        String filePath = "day2/day2Input.txt";

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line = br.readLine();
            String[] idList = line.split(",");
            for (String id : idList) {
                processIdPart2(id);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.printf("Invalid sum: %s%n", invalidSum);
    }

    private void processId(String id) {
        final Matcher matcher = pattern.matcher(id);
        if (!matcher.find()) {
            throw new IllegalArgumentException(String.format("Invalid input: %s%n", id));
        }

        long start = Long.parseLong(matcher.group(1));
        long end = Long.parseLong(matcher.group(2));

        for (long numInRange = start; numInRange <= end; numInRange++) {
            String s = Long.toString(numInRange);
            String firstHalf = s.substring(0, s.length() / 2);
            String secondHalf = s.substring(s.length() / 2);

            if (firstHalf.equals(secondHalf)) {
                invalidSum += numInRange;
            }
        }
    }


    private void processIdPart2(String id) {
        final Matcher matcher = pattern.matcher(id);
        if (!matcher.find()) {
            throw new IllegalArgumentException(String.format("Invalid input: %s%n", id));
        }

        long start = Long.parseLong(matcher.group(1));
        long end = Long.parseLong(matcher.group(2));

        for (long numInRange = start; numInRange <= end; numInRange++) {
           String s = Long.toString(numInRange);

           Set<Integer> factors = getAllFactors(s.length());

           for (Integer factor: factors) {
               int otherFactor = s.length() / factor;
               String firstChunk = s.substring(0, factor);
               String repeated = firstChunk.repeat(otherFactor);
               if (otherFactor > 1 && s.contains(repeated)) {
                   invalidSum += numInRange;
                   break;
               }
           }
        }
    }

    private static Set<Integer> getAllFactors(int n) {
        Set<Integer> factors = new HashSet<>();
        int step = n % 2 == 0 ? 1 : 2;
        for (int i = 1; i <= Math.sqrt(n); i += step) {
            if (n % i == 0) {
                factors.add(i);
                factors.add(n / i);
            }
        }
        return factors;
    }

}