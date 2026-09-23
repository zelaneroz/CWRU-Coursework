package strassen;

import java.util.Objects;

/** Associates a non-null value with its coordinates in a matrix. */
public record Entry<T>(Coordinates coordinates, T value) {

    public Entry {
        Objects.requireNonNull(coordinates, "coordinates must not be null");
        Objects.requireNonNull(value, "value must not be null");
    }

    /** Returns this entry translated to new coordinates. */
    public Entry<T> translated(Coordinates offset) {
        Objects.requireNonNull(offset, "offset must not be null");
        return new Entry<>(coordinates.plus(offset), value);
    }
}
