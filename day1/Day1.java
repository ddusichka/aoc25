package day1;

import java.io.BufferedReader;
import java.io.FileReader; 
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Day1 {
    static final String regex = "([LR])(\\d+)";
    static final Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);
    int exactZeroes = 0;
    int passThroughZeroes = 0;

    // part 2
    public void main(String[] args) {
        String filePath = "day1/day1Input.txt";
        int currentPosition = 50;

        try (BufferedReader br = new BufferedReader(new FileReader(filePath));) {
            for (String line = br.readLine(); line != null; line = br.readLine()) {
                currentPosition = getPositionCountingZeroes(currentPosition, line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.printf("Exact zeroes: %d%n", exactZeroes);
        System.out.printf("Pass through zeroes: %d%n", passThroughZeroes);
    }

    private int getPositionCountingZeroes(int startingPosition, String rotation) {
        final Matcher matcher = pattern.matcher(rotation);
        if (!matcher.find()) {
            throw new IllegalArgumentException(String.format("Invalid input: %s%n", rotation));
        }

        String direction = matcher.group(1);
        int delta = Integer.parseInt(matcher.group(2));
        int dial = startingPosition;

        for (int i = 0; i < delta; i++) {
            if (direction.equals("L")) {
                dial = (dial - 1) % 100;
            } else {
                dial = (dial + 1) % 100;
            }

            if (dial == 0) {
                passThroughZeroes++;
            }
        }

        if (dial == 0) {
            exactZeroes++;
        }

        return dial;
    }
}