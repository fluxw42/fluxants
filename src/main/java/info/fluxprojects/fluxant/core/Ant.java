package info.fluxprojects.fluxant.core;

public class Ant {

    private static int HASH_GENERATOR = 0;

    private final int hash;
    private Position position = null;

    public Ant(Position position) {
        this.hash = HASH_GENERATOR++;
        this.position = position;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Ant ant = (Ant) o;

        if (hashCode() != ant.hashCode()) {
            return false;
        }

        return true;
    }

    public Position getPosition() {
        return position;
    }

    @Override
    public int hashCode() {
        return hash;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("Ant");
        sb.append("{hash=").append(hash);
        sb.append(", position=").append(position);
        sb.append('}');
        return sb.toString();
    }

}
