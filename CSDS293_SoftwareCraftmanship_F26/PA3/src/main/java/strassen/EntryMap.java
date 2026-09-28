package strassen;

import java.util.Collections;
import java.util.Map;
import java.util.NavigableMap;
import java.util.Objects;
import java.util.TreeMap;

/**
 * An immutable sparse-matrix representation that stores only explicitly
 * supplied entries.
 *
 * @param <T> the type of value stored at each occupied coordinate
 */
public final class EntryMap<T> {

    private final NavigableMap<Coordinates, T> entryMap;
    private final int rows;
    private final int columns;

    private EntryMap(NavigableMap<Coordinates, T> entryMap) {
        assert entryMap != null : "entryMap must not be null";
        this.entryMap = entryMap;

        int largestRow = -1;
        int largestColumn = -1;
        for (Coordinates coordinates : entryMap.navigableKeySet()) {
            largestRow = Math.max(largestRow, coordinates.row());
            largestColumn = Math.max(largestColumn, coordinates.column());
        }
        rows = largestRow + 1;
        columns = largestColumn + 1;
    }

    /** Returns an entry map containing a defensive copy of {@code entryMap}. */
    public static <T> EntryMap<T> from(Map<Coordinates, T> entryMap) {
        Objects.requireNonNull(entryMap, "entryMap must not be null");

        NavigableMap<Coordinates, T> copy = new TreeMap<>(Coordinates.COMPARATOR);
        entryMap.forEach((coordinates, value) -> {
            Objects.requireNonNull(coordinates, "coordinates must not be null");
            Objects.requireNonNull(value, "value must not be null");
            if (coordinates.row() < 0 || coordinates.column() < 0) {
                throw new IllegalArgumentException(
                        "matrix coordinates must not be negative: " + coordinates);
            }
            copy.put(coordinates, value);
        });
        return new EntryMap<>(Collections.unmodifiableNavigableMap(copy));
    }

    /** Returns the value at {@code coordinates}, or {@code null} when absent. */
    public T get(Coordinates coordinates) {
        Objects.requireNonNull(coordinates, "coordinates must not be null");
        return entryMap.get(coordinates);
    }

    /** Returns the value at {@code coordinates}, or {@code defaultValue} when absent. */
    public T getOrDefault(Coordinates coordinates, T defaultValue) {
        Objects.requireNonNull(coordinates, "coordinates must not be null");
        Objects.requireNonNull(defaultValue, "defaultValue must not be null");
        return entryMap.getOrDefault(coordinates, defaultValue);
    }

    /** Returns one more than the largest occupied row, or zero when empty. */
    public int rows() {
        return rows;
    }

    /** Returns one more than the largest occupied column, or zero when empty. */
    public int columns() {
        return columns;
    }

    /** Returns the larger of {@link #rows()} and {@link #columns()}. */
    public int size() {
        return Math.max(rows, columns);
    }
}
