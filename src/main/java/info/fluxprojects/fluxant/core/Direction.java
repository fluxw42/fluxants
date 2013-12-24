package info.fluxprojects.fluxant.core;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents a direction in which to move an ant.
 */
public enum Direction {
    /**
     * North direction, or up.
     */
    NORTH(0, -1, 'n'),

    /**
     * East direction or right.
     */
    EAST(1, 0, 'e'),

    /**
     * South direction or down.
     */
    SOUTH(0, 1, 's'),

    /**
     * West direction or left.
     */
    WEST(-1, 0, 'w');

    private static final Map<Character, Direction> symbolLookup = new HashMap<Character, Direction>();

    static {
        symbolLookup.put('n', NORTH);
        symbolLookup.put('e', EAST);
        symbolLookup.put('s', SOUTH);
        symbolLookup.put('w', WEST);
    }

    private final int rowDelta;

    private final int colDelta;

    private final char symbol;

    Direction(int colDelta, int rowDelta, char symbol) {
        this.rowDelta = rowDelta;
        this.colDelta = colDelta;
        this.symbol = symbol;
    }

    /**
     * Returns rows delta.
     *
     * @return rows delta.
     */
    public int getRowDelta() {
        return rowDelta;
    }

    /**
     * Returns columns delta.
     *
     * @return columns delta.
     */
    public int getColDelta() {
        return colDelta;
    }

    /**
     * Returns symbol associated with this direction.
     *
     * @return symbol associated with this direction.
     */
    public char getSymbol() {
        return symbol;
    }

    /**
     * Returns direction associated with specified symbol.
     *
     * @param symbol <code>n</code>, <code>e</code>, <code>s</code> or <code>w</code> character
     * @return direction associated with specified symbol
     */
    public static Direction fromSymbol(char symbol) {
        return symbolLookup.get(symbol);
    }

    public Direction getNext() {
        switch (this) {
            case NORTH:
                return EAST;
            case EAST:
                return SOUTH;
            case SOUTH:
                return WEST;
            case WEST:
                return NORTH;
        }
        throw new IllegalStateException("Unable to get next direction for [" + this + "]!");
    }

    public Direction getPrevious() {
        switch (this) {
            case NORTH:
                return WEST;
            case EAST:
                return NORTH;
            case SOUTH:
                return EAST;
            case WEST:
                return SOUTH;
        }
        throw new IllegalStateException("Unable to get previous direction for [" + this + "]!");
    }

    public Direction getOpposite() {
        switch (this) {
            case NORTH:
                return SOUTH;
            case EAST:
                return WEST;
            case SOUTH:
                return NORTH;
            case WEST:
                return EAST;
        }
        throw new IllegalStateException("Unable to get opposite direction for [" + this + "]!");
    }

}
