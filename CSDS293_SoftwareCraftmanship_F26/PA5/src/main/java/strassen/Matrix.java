package strassen;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/** An immutable square matrix of floating-point values. */
public final class Matrix {

    private static final Float ZERO = 0.0F;

    private final EntryMap<Float> representation;
    private final int size;

    private Matrix(EntryMap<Float> representation) {
        assert representation != null : "representation must not be null";
        this.representation = representation;
        size = representation.sizeRounded();
    }

    private Matrix(EntryMap<Float> representation, int size) {
        assert representation != null : "representation must not be null";
        assert Sizes.isRounded(size) : "size must be zero or a power of two";
        assert size >= representation.sizeRounded() : "size must contain the representation";
        this.representation = representation;
        this.size = size;
    }

    /** Returns a matrix backed by a non-zero copy of {@code entryMap}. */
    public static Matrix from(EntryMap<Float> entryMap) {
        Objects.requireNonNull(entryMap, "entryMap must not be null");
        return new Matrix(nonZeroEntries(entryMap.stream()));
    }

    /** Returns a matrix backed by a non-zero copy of {@code entryMap}. */
    public static Matrix from(Map<Coordinates, Float> entryMap) {
        Objects.requireNonNull(entryMap, "entryMap must not be null");
        return from(EntryMap.from(entryMap));
    }

    /**
     * Returns a matrix with an explicit logical size. This factory is reserved
     * for package collaborators that already validated the requested size.
     */
    static Matrix from(EntryMap<Float> entryMap, int size) {
        Objects.requireNonNull(entryMap, "entryMap must not be null");
        return new Matrix(nonZeroEntries(entryMap.stream()), size);
    }

    /** Returns the padded, power-of-two size of this matrix. */
    public int size() {
        return size;
    }

    /** Returns the value at {@code coordinates}, or zero when no value is stored. */
    public Float get(Coordinates coordinates) {
        Objects.requireNonNull(coordinates, "coordinates must not be null");
        return representation.getOrDefault(coordinates, ZERO);
    }

    /** Returns the value at the origin. */
    public Float get() {
        return get(Coordinates.ORIGIN);
    }

    /** Returns the stored non-zero entries in row-major order. */
    public Stream<Entry<Float>> stream() {
        return representation.stream();
    }

    /** Returns a new matrix with every stored value reversed in sign. */
    public Matrix negated() {
        EntryMap<Float> negated = representation.remap(
                Function.identity(), value -> -value);
        return new Matrix(negated, size);
    }

    /** Returns the element-wise sum of this matrix and {@code other}. */
    public Matrix plus(Matrix other) {
        return operatedWith(other, Float::sum);
    }

    /** Returns the element-wise difference of this matrix and {@code other}. */
    public Matrix minus(Matrix other) {
        return operatedWith(other, (left, right) -> left - right);
    }

    /**
     * Returns the quadrant selected by the supplied horizontal and vertical
     * offsets. A false offset selects the left or top half, respectively.
     */
    public Matrix quadrant(boolean horizontalOffset, boolean verticalOffset) {
        int quadrantSize = size / 2;
        Coordinates origin = new Coordinates(
                verticalOffset ? quadrantSize : 0,
                horizontalOffset ? quadrantSize : 0);
        Coordinates bound = origin.plus(new Coordinates(quadrantSize, quadrantSize));
        return subMatrix(origin, bound);
    }

    /** Returns the product of this matrix and {@code other} using Strassen's algorithm. */
    public Matrix times(Matrix other) {
        InconsistentSizeException.validate(size, other);
        if (size == 0) {
            return new Matrix(EntryMap.from(Map.of()), 0);
        }
        if (size == 1) {
            Entry<Float> product = new Entry<>(Coordinates.ORIGIN, get() * other.get());
            return new Matrix(nonZeroEntries(Stream.of(product)), 1);
        }

        Matrix a = quadrant(false, false);
        Matrix b = quadrant(true, false);
        Matrix c = quadrant(false, true);
        Matrix d = quadrant(true, true);
        Matrix e = other.quadrant(false, false);
        Matrix f = other.quadrant(true, false);
        Matrix g = other.quadrant(false, true);
        Matrix h = other.quadrant(true, true);

        Matrix s1 = a.times(f.minus(h));
        Matrix s2 = a.plus(b).times(h);
        Matrix s3 = c.plus(d).times(e);
        Matrix s4 = d.times(g.minus(e));
        Matrix s5 = a.plus(d).times(e.plus(h));
        Matrix s6 = b.minus(d).times(g.plus(h));
        Matrix s7 = a.minus(c).times(e.plus(f));

        Matrix i = s5.plus(s6).plus(s4).minus(s2);
        Matrix j = s1.plus(s2);
        Matrix k = s3.plus(s4);
        Matrix l = s1.minus(s7).minus(s3).plus(s5);

        return Quadrants.from(Map.of(
                Coordinates.ORIGIN, i,
                Coordinates.HORIZONTAL_UNIT, j,
                Coordinates.VERTICAL_UNIT, k,
                Coordinates.DIAGONAL_UNIT, l))
                .toMatrix();
    }

    /**
     * Returns the block beginning at {@code origin} (inclusive) and ending at
     * {@code bound} (exclusive), translated so the block begins at the origin.
     */
    public Matrix subMatrix(Coordinates origin, Coordinates bound) {
        Objects.requireNonNull(origin, "origin must not be null");
        Objects.requireNonNull(bound, "bound must not be null");
        if (!isValidBlock(origin, bound)) {
            throw new IllegalArgumentException(
                    "invalid submatrix range " + origin + " to " + bound
                            + " for matrix size " + size);
        }

        Stream<Entry<Float>> translatedEntries = stream()
                .filter(entry -> isInBlock(entry, origin, bound))
                .map(entry -> entry.translated(origin.negated()));
        return new Matrix(nonZeroEntries(translatedEntries), blockSize(origin, bound));
    }

    private Matrix operatedWith(Matrix other, BinaryOperator<Float> operation) {
        assert operation != null : "operation must not be null";
        InconsistentSizeException.validate(size, other);

        Map<Coordinates, Float> result = entriesByCoordinates();
        other.stream().forEach(entry -> merge(result, entry, operation));
        return new Matrix(EntryMap.from(result), size);
    }

    private Map<Coordinates, Float> entriesByCoordinates() {
        return stream().collect(Collectors.toMap(Entry::coordinates, Entry::value));
    }

    private static void merge(
            Map<Coordinates, Float> entries,
            Entry<Float> entry,
            BinaryOperator<Float> operation) {
        assert entries != null : "entries must not be null";
        assert entry != null : "entry must not be null";
        assert operation != null : "operation must not be null";

        Float value = operation.apply(entries.getOrDefault(entry.coordinates(), ZERO), entry.value());
        store(entries, entry.coordinates(), value);
    }

    private static void store(
            Map<Coordinates, Float> entries,
            Coordinates coordinates,
            Float value) {
        assert entries != null : "entries must not be null";
        assert coordinates != null : "coordinates must not be null";
        assert value != null : "value must not be null";
        if (isZero(value)) {
            entries.remove(coordinates);
        } else {
            entries.put(coordinates, value);
        }
    }

    private static EntryMap<Float> nonZeroEntries(Stream<Entry<Float>> entries) {
        assert entries != null : "entries must not be null";
        Map<Coordinates, Float> values = entries
                .filter(entry -> !isZero(entry.value()))
                .collect(Collectors.toMap(Entry::coordinates, Entry::value, (first, second) -> second,
                        HashMap::new));
        return EntryMap.from(values);
    }

    private boolean isValidBlock(Coordinates origin, Coordinates bound) {
        assert origin != null : "origin must not be null";
        assert bound != null : "bound must not be null";
        return isValidRange(origin.row(), bound.row())
                && isValidRange(origin.column(), bound.column());
    }

    private boolean isValidRange(int lower, int upper) {
        return 0 <= lower && lower <= upper && upper <= size;
    }

    private static boolean isInBlock(
            Entry<Float> entry,
            Coordinates origin,
            Coordinates bound) {
        assert entry != null : "entry must not be null";
        assert origin != null : "origin must not be null";
        assert bound != null : "bound must not be null";
        return entry.isInRows(origin.row(), bound.row())
                && entry.isInColumns(origin.column(), bound.column());
    }

    private static int blockSize(Coordinates origin, Coordinates bound) {
        assert origin != null : "origin must not be null";
        assert bound != null : "bound must not be null";
        int rows = bound.row() - origin.row();
        int columns = bound.column() - origin.column();
        return Sizes.rounded(Math.max(rows, columns));
    }

    private static boolean isZero(Float value) {
        assert value != null : "value must not be null";
        return value == 0.0F;
    }
}
