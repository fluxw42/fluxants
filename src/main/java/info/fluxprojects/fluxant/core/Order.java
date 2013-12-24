package info.fluxprojects.fluxant.core;

import info.fluxprojects.fluxant.utils.MapUtils;

/**
 * Represents an order to be issued.
 */
public class Order {

    private final int row;

    private final int col;

    private final Direction direction;

    /**
     * Creates new {@link Order} object.
     *
     * @param position  map position with my ant
     * @param direction direction in which to move my ant
     */
    public Order(Position position, Direction direction) {
        row = position.getRow();
        col = position.getCol();
        this.direction = direction;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "o " + row + " " + col + " " + direction.getSymbol();
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public Direction getDirection() {
        return direction;
    }

    public Position getOldPosition() {
        return new Position(col, row);
    }

    public Position getNewPosition(int cols, int rows) {
        return MapUtils.getPosition(getOldPosition(), direction, cols, rows);
    }

}
