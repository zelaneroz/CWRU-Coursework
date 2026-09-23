package strassen;

import java.util.Comparator;
import java.util.Objects;

/** Identifies a matrix entry by its zero-based row and column indexes. */
public record Coordinates(int row, int column) implements Comparable<Coordinates> {

    public static final Coordinates ORIGIN = new Coordinates(0, 0);
    public static final Coordinates HORIZONTAL_UNIT = new Coordinates(1, 0);
    public static final Coordinates VERTICAL_UNIT = new Coordinates(0, 1);
    public static final Coordinates DIAGONAL_UNIT = new Coordinates(1, 1);
    public static final Coordinates NEGATIVE_HORIZONTAL_UNIT = new Coordinates(-1, 0);

    public static final Comparator<Coordinates> COMPARATOR =
            Comparator.comparingInt(Coordinates::row)
                    .thenComparingInt(Coordinates::column);

    /** Returns coordinates with both indexes negated. */
    public Coordinates negated() {
        return new Coordinates(-row, -column);
    }

    /** Returns these coordinates translated by {@code offset}. */
    public Coordinates plus(Coordinates offset) {
        Objects.requireNonNull(offset, "offset must not be null");
        return new Coordinates(row + offset.row, column + offset.column);
    }

    /** Returns these coordinates relative to {@code origin}. */
    public Coordinates minus(Coordinates origin) {
        Objects.requireNonNull(origin, "origin must not be null");
        return new Coordinates(row - origin.row, column - origin.column);
    }

    /** Returns coordinates with both indexes multiplied by {@code scale}. */
    public Coordinates times(int scale) {
        return new Coordinates(row * scale, column * scale);
    }

    @Override
    public int compareTo(Coordinates other) {
        Objects.requireNonNull(other, "other must not be null");
        return COMPARATOR.compare(this, other);
    }
}
