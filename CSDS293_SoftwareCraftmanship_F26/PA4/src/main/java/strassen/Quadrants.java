package strassen;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/** Four equal-size matrix blocks that can be assembled into one matrix. */
public final class Quadrants {

    private static final Set<Coordinates> EXPECTED_COORDINATES = Set.of(
            Coordinates.ORIGIN,
            Coordinates.HORIZONTAL_UNIT,
            Coordinates.VERTICAL_UNIT,
            Coordinates.DIAGONAL_UNIT);

    private final EntryMap<Matrix> quadrants;
    private final int size;

    private Quadrants(EntryMap<Matrix> quadrants, int size) {
        assert quadrants != null : "quadrants must not be null";
        assert 0 <= size : "size must not be negative";
        this.quadrants = quadrants;
        this.size = size;
    }

    /**
     * Returns quadrants containing the four required, equally sized matrix
     * blocks.
     */
    public static Quadrants from(Map<Coordinates, Matrix> quadrants) {
        Objects.requireNonNull(quadrants, "quadrants must not be null");

        Matrix reference = Objects.requireNonNull(
                quadrants.get(Coordinates.ORIGIN),
                "origin quadrant must not be null");
        Matrix horizontal = requiredQuadrant(quadrants, Coordinates.HORIZONTAL_UNIT);
        Matrix vertical = requiredQuadrant(quadrants, Coordinates.VERTICAL_UNIT);
        Matrix diagonal = requiredQuadrant(quadrants, Coordinates.DIAGONAL_UNIT);

        validateSize(reference, horizontal, Coordinates.HORIZONTAL_UNIT);
        validateSize(reference, vertical, Coordinates.VERTICAL_UNIT);
        validateSize(reference, diagonal, Coordinates.DIAGONAL_UNIT);

        int matrixSize = Math.multiplyExact(reference.size(), 2);
        EntryMap<Matrix> requiredQuadrants = EntryMap.from(Map.of(
                Coordinates.ORIGIN, reference,
                Coordinates.HORIZONTAL_UNIT, horizontal,
                Coordinates.VERTICAL_UNIT, vertical,
                Coordinates.DIAGONAL_UNIT, diagonal));
        return new Quadrants(requiredQuadrants, matrixSize);
    }

    /** Returns the size of the matrix formed by these quadrants. */
    public int getSize() {
        return size;
    }

    /** Returns a new matrix formed by placing each block in its quadrant. */
    public Matrix toMatrix() {
        int quadrantSize = size / 2;
        Map<Coordinates, Float> entries = quadrants.stream()
                .flatMap(entry -> translatedEntries(entry, quadrantSize))
                .collect(Collectors.toMap(Entry::coordinates, Entry::value));
        return Matrix.from(EntryMap.from(entries), size);
    }

    private static Matrix requiredQuadrant(
            Map<Coordinates, Matrix> quadrants,
            Coordinates coordinates) {
        assert quadrants != null : "quadrants must not be null";
        assert coordinates != null : "coordinates must not be null";
        return Objects.requireNonNull(
                quadrants.get(coordinates),
                "quadrant " + coordinates + " must not be null");
    }

    private static void validateSize(
            Matrix reference,
            Matrix quadrant,
            Coordinates coordinates) {
        assert reference != null : "reference must not be null";
        assert quadrant != null : "quadrant must not be null";
        assert coordinates != null : "coordinates must not be null";
        InconsistentSizeException.validate(
                reference.size(), quadrant, Optional.of(coordinates));
    }

    private static Stream<Entry<Float>> translatedEntries(
            Entry<Matrix> quadrant,
            int quadrantSize) {
        assert quadrant != null : "quadrant must not be null";
        assert 0 <= quadrantSize : "quadrantSize must not be negative";

        Coordinates offset = matrixOffset(quadrant.coordinates(), quadrantSize);
        return quadrant.value().stream().map(entry -> entry.translated(offset));
    }

    private static Coordinates matrixOffset(Coordinates quadrant, int quadrantSize) {
        assert quadrant != null : "quadrant must not be null";
        assert EXPECTED_COORDINATES.contains(quadrant) : "unexpected quadrant coordinate";

        // Quadrant coordinates are semantic: row represents horizontal and
        // column represents vertical movement, opposite matrix indexes.
        return new Coordinates(
                quadrant.column() * quadrantSize,
                quadrant.row() * quadrantSize);
    }
}
