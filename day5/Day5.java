package day5;

import com.google.common.primitives.Longs;
import javafx.util.Pair;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day5 {
    static final String regex = "(\\d+)-(\\d+)";
    static final Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);
    long freshIngredients = 0;
    long allFreshIngredients = 0;
    List<Pair<Long, Long>> map = new ArrayList<>();

    void main(String[] args) {
        String filePath = "day5/day5Input.txt";

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            boolean isParsingRanges = true;
            String line;
            while ((line = br.readLine()) != null) {
                if (line.isBlank()) {
                    isParsingRanges = false;
                    Comparator<Pair<Long, Long>> myComp = Comparator.<Pair<Long, Long>, Long>comparing(Pair::getKey)
                            .thenComparing(Pair::getValue);
                    map.sort(myComp);
                    continue;
                }

                if (isParsingRanges) {
                    processRangeLine(line);
                } else {
                    processIngredientLine(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        allFreshIngredients = findAllFreshIngredients();

        System.out.printf("Fresh ingredients in inventory: %s%n", freshIngredients);
        System.out.printf("All fresh ingredients: %s%n", allFreshIngredients);

    }

    private void processRangeLine(String line) {
        Matcher matcher = pattern.matcher(line);
        if (matcher.find()) {
            long start = Long.parseLong(matcher.group(1));
            long end = Long.parseLong(matcher.group(2));
            map.add(new Pair<>(start, end));
        } else {
            throw new IllegalArgumentException("Input doesn't match expected format!");
        }
    }

    private void processIngredientLine(String line) {
        Long id = Longs.tryParse(line.trim());
        if (id != null && isIdFresh(id)) {
            freshIngredients++;
        }
    }

    private boolean isIdFresh(long ingredient) {
        for (Pair<Long, Long> pair : map) {
            if (ingredient >= pair.getKey() && ingredient <= pair.getValue()) {
                return true;
            }
        }

        return false;
    }

    private long findAllFreshIngredients() {
        long currentStart = map.getFirst().getKey();
        long currentEnd = map.getFirst().getValue();

        for (Pair<Long, Long> next : map) {
            if (next.getKey() <= currentEnd) {
                currentEnd = Math.max(currentEnd, next.getValue());
            } else {
                allFreshIngredients += currentEnd - currentStart + 1;
                currentStart = next.getKey();
                currentEnd = next.getValue();
            }
        }

        allFreshIngredients += (currentEnd - currentStart + 1);
        return allFreshIngredients;
    }
}

