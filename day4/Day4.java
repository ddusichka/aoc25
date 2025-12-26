package day4;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


public class Day4 {
    long accessibleRolls = 0;
    List<List<Boolean>> map = new ArrayList<>();

    void main(String[] args) {
        String filePath = "day4/day4Input.txt";

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            for (String line = br.readLine(); line != null; line = br.readLine()) {
                map.add(Arrays.stream(line.split("")).map(string -> string.equals("@")).collect(Collectors.toCollection(ArrayList::new)));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        int totalRemoved = 0;
        boolean removedSomething = true;
        while (removedSomething) {
            int removed = parseMapAndRemove();
            totalRemoved += removed;
            removedSomething = removed > 0;
        }

        parseMap();

        System.out.printf("Total removed: %s", totalRemoved);
    }

    private void parseMap() {
        for (int rowIndex = 0; rowIndex < map.size(); rowIndex++) {
            List<Boolean> row = map.get(rowIndex);

            for (int colIndex = 0; colIndex < row.size(); colIndex++) {
                if (row.get(colIndex)) {
                    int rolls = countAdjacentRolls(rowIndex, colIndex);
                    if (rolls < 4) {
                        accessibleRolls++;
                    }
                }
            }
        }
    }

    private int parseMapAndRemove() {
        int removedRolls = 0;
        for (int rowIndex = 0; rowIndex < map.size(); rowIndex++) {
            List<Boolean> row = map.get(rowIndex);

            for (int colIndex = 0; colIndex < row.size(); colIndex++) {
                if (row.get(colIndex)) {
                    int rolls = countAdjacentRolls(rowIndex, colIndex);
                    if (rolls < 4) {
                        map.get(rowIndex).set(colIndex, false);
                        removedRolls++;
                    }
                }
            }
        }

        return removedRolls;
    }


    private int countAdjacentRolls(int row, int col) {
        int rolls = -1; // avoid double counting actual row
        int minRow = Math.max(0, row - 1);
        int maxRow = Math.min(map.size() - 1, row + 1);
        int minCol = Math.max(0, col - 1);
        int maxCol = Math.min(map.getFirst().size() - 1, col + 1);

        for (int i = minRow; i <= maxRow; i++) {
            for (int j = minCol; j <= maxCol; j++) {
                if (map.get(i).get(j)) {
                    rolls++;
                }
            }
        }

        return rolls;
    }
}

