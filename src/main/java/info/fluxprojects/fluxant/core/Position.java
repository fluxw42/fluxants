package info.fluxprojects.fluxant.core;

public class Position {

    private final int col;
    private final int row;

    public Position(int col, int row) {
        this.col = col;
        this.row = row;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || !(o instanceof Position)) {
            return false;
        }

        Position position = (Position) o;

        if (col != position.col) {
            return false;
        }
        if (row != position.row) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = col;
        result = 31 * result + row;
        return result;
    }

    public int getCol() {
        return col;
    }

    public int getRow() {
        return row;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append('[').append(getCol()).append(", ").append(getRow()).append(']');
        return sb.toString();
    }
}
