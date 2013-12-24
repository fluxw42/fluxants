package info.fluxprojects.fluxant.utils;

import info.fluxprojects.fluxant.core.Direction;
import info.fluxprojects.fluxant.core.Position;

import java.util.Arrays;

public class MapUtils {

    public static int getCols(long[][] array) {
        return array.length;
    }

    public static int getCols(int[][] array) {
        return array.length;
    }

    public static int getCols(boolean[][] array) {
        return array.length;
    }

    public static int getRows(long[][] array) {
        return array[0].length;
    }

    public static int getRows(int[][] array) {
        return array[0].length;
    }

    public static int getRows(boolean[][] array) {
        return array[0].length;
    }

    public static int getCorrectedRow(int row, int rows) {
        int correctedRow = row % rows;
        return correctedRow < 0 ? correctedRow + rows : correctedRow;
    }

    public static int getCorrectedCol(int col, int cols) {
        int correctedCol = col % cols;
        return correctedCol < 0 ? correctedCol + cols : correctedCol;
    }

    public static int getCorrectedCol(boolean[][] array, int col) {
        int correctedCol = col % getCols(array);
        return correctedCol < 0 ? correctedCol + getCols(array) : correctedCol;
    }

    public static Position getPosition(Position position, Direction direction, int cols, int rows) {
        return new Position(
                getCorrectedCol(position.getCol() + direction.getColDelta(), cols),
                getCorrectedRow(position.getRow() + direction.getRowDelta(), rows)
        );
    }

    public static Direction getBestMove(long[][] map, int col, int row) {
        int cols = getCols(map);
        int rows = getRows(map);

        long c = map[col][row];
        long e = map[getCorrectedCol(col + 1, cols)][row];
        long w = map[getCorrectedCol(col - 1, cols)][row];
        long n = map[col][getCorrectedRow(row - 1, rows)];
        long s = map[col][getCorrectedRow(row + 1, rows)];

        long[] directions = new long[]{n, e, s, w, c};
        Arrays.sort(directions);

        long max = directions[directions.length - 1];

        if (n == max) {
            return Direction.NORTH;
        } else if (e == max) {
            return Direction.EAST;
        } else if (s == max) {
            return Direction.SOUTH;
        } else if (w == max) {
            return Direction.WEST;
        } else {
            return null;
        }
    }

}
