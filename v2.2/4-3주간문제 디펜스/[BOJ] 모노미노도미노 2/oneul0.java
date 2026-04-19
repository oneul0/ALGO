import java.io.*;
import java.util.*;

public class Main {

    static int n;
    static int score;

    static boolean[][] greenBoard = new boolean[6][4];
    static boolean[][] blueBoard = new boolean[4][6];

    public static void main(String[] args) throws Exception {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        n = Integer.parseInt(bufferedReader.readLine());

        for (int i = 0; i < n; i++) {
            StringTokenizer stringTokenizer = new StringTokenizer(bufferedReader.readLine());
            int blockType = Integer.parseInt(stringTokenizer.nextToken());
            int x = Integer.parseInt(stringTokenizer.nextToken());
            int y = Integer.parseInt(stringTokenizer.nextToken());

            dropToGreen(blockType, y);
            dropToBlue(blockType, x);

            clearGreenFullRows();
            clearBlueFullColumns();

            processGreenLightArea();
            processBlueLightArea();
        }

        int tileCount = countTiles();

        System.out.println(score);
        System.out.println(tileCount);
    }

    static void dropToGreen(int blockType, int y) {
        if (blockType == 1) {
            int row = 0;
            while (row + 1 < 6 && !greenBoard[row + 1][y]) {
                row++;
            }
            greenBoard[row][y] = true;
        } else if (blockType == 2) {
            int row = 0;
            while (row + 1 < 6 && !greenBoard[row + 1][y] && !greenBoard[row + 1][y + 1]) {
                row++;
            }
            greenBoard[row][y] = true;
            greenBoard[row][y + 1] = true;
        } else {
            int topRow = 0;
            while (topRow + 2 < 6 && !greenBoard[topRow + 2][y]) {
                topRow++;
            }
            greenBoard[topRow][y] = true;
            greenBoard[topRow + 1][y] = true;
        }
    }

    static void dropToBlue(int blockType, int x) {
        if (blockType == 1) {
            int column = 0;
            while (column + 1 < 6 && !blueBoard[x][column + 1]) {
                column++;
            }
            blueBoard[x][column] = true;
        } else if (blockType == 2) {
            int leftColumn = 0;
            while (leftColumn + 2 < 6 && !blueBoard[x][leftColumn + 2]) {
                leftColumn++;
            }
            blueBoard[x][leftColumn] = true;
            blueBoard[x][leftColumn + 1] = true;
        } else {
            int column = 0;
            while (column + 1 < 6 && !blueBoard[x][column + 1] && !blueBoard[x + 1][column + 1]) {
                column++;
            }
            blueBoard[x][column] = true;
            blueBoard[x + 1][column] = true;
        }
    }

    static void clearGreenFullRows() {
        while (true) {
            boolean removed = false;

            for (int row = 2; row < 6; row++) {
                if (isGreenRowFull(row)) {
                    removeGreenRow(row);
                    score++;
                    removed = true;
                }
            }

            if (!removed) {
                break;
            }
        }
    }

    static void clearBlueFullColumns() {
        while (true) {
            boolean removed = false;

            for (int column = 2; column < 6; column++) {
                if (isBlueColumnFull(column)) {
                    removeBlueColumn(column);
                    score++;
                    removed = true;
                }
            }

            if (!removed) {
                break;
            }
        }
    }

    static boolean isGreenRowFull(int row) {
        for (int column = 0; column < 4; column++) {
            if (!greenBoard[row][column]) {
                return false;
            }
        }
        return true;
    }

    static boolean isBlueColumnFull(int column) {
        for (int row = 0; row < 4; row++) {
            if (!blueBoard[row][column]) {
                return false;
            }
        }
        return true;
    }

    static void removeGreenRow(int removedRow) {
        for (int row = removedRow; row >= 1; row--) {
            for (int column = 0; column < 4; column++) {
                greenBoard[row][column] = greenBoard[row - 1][column];
            }
        }

        for (int column = 0; column < 4; column++) {
            greenBoard[0][column] = false;
        }
    }

    static void removeBlueColumn(int removedColumn) {
        for (int column = removedColumn; column >= 1; column--) {
            for (int row = 0; row < 4; row++) {
                blueBoard[row][column] = blueBoard[row][column - 1];
            }
        }

        for (int row = 0; row < 4; row++) {
            blueBoard[row][0] = false;
        }
    }

    static void processGreenLightArea() {
        int shiftCount = 0;

        for (int row = 0; row <= 1; row++) {
            if (hasTileInGreenRow(row)) {
                shiftCount++;
            }
        }

        if (shiftCount == 0) {
            return;
        }

        for (int row = 5; row >= shiftCount; row--) {
            for (int column = 0; column < 4; column++) {
                greenBoard[row][column] = greenBoard[row - shiftCount][column];
            }
        }

        for (int row = 0; row < shiftCount; row++) {
            for (int column = 0; column < 4; column++) {
                greenBoard[row][column] = false;
            }
        }
    }

    static void processBlueLightArea() {
        int shiftCount = 0;

        for (int column = 0; column <= 1; column++) {
            if (hasTileInBlueColumn(column)) {
                shiftCount++;
            }
        }

        if (shiftCount == 0) {
            return;
        }

        for (int column = 5; column >= shiftCount; column--) {
            for (int row = 0; row < 4; row++) {
                blueBoard[row][column] = blueBoard[row][column - shiftCount];
            }
        }

        for (int column = 0; column < shiftCount; column++) {
            for (int row = 0; row < 4; row++) {
                blueBoard[row][column] = false;
            }
        }
    }

    static boolean hasTileInGreenRow(int row) {
        for (int column = 0; column < 4; column++) {
            if (greenBoard[row][column]) {
                return true;
            }
        }
        return false;
    }

    static boolean hasTileInBlueColumn(int column) {
        for (int row = 0; row < 4; row++) {
            if (blueBoard[row][column]) {
                return true;
            }
        }
        return false;
    }

    static int countTiles() {
        int count = 0;

        for (int row = 0; row < 6; row++) {
            for (int column = 0; column < 4; column++) {
                if (greenBoard[row][column]) {
                    count++;
                }
            }
        }

        for (int row = 0; row < 4; row++) {
            for (int column = 0; column < 6; column++) {
                if (blueBoard[row][column]) {
                    count++;
                }
            }
        }

        return count;
    }
}
