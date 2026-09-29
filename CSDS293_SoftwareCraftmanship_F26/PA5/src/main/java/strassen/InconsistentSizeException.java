package strassen;

import java.util.Objects;
import java.util.Optional;

/** Describes an operation attempted on matrices with different sizes. */
public final class InconsistentSizeException extends Exception {

    private static final long serialVersionUID = 1L;

    private final int referenceSize;
    private final int otherSize;
    private final Optional<Coordinates> quadrant;

    /** Creates an exception describing the two inconsistent sizes. */
    public InconsistentSizeException(int referenceSize, int otherSize) {
        this(referenceSize, otherSize, Optional.empty());
    }

    /** Creates an exception describing inconsistent sizes within a quadrant. */
    public InconsistentSizeException(
            int referenceSize,
            int otherSize,
            Optional<Coordinates> quadrant) {
        super("matrix sizes differ: " + referenceSize + " and " + otherSize);
        this.referenceSize = referenceSize;
        this.otherSize = otherSize;
        this.quadrant = Objects.requireNonNull(quadrant, "quadrant must not be null");
    }

    public int getReferenceSize() {
        return referenceSize;
    }

    public int getOtherSize() {
        return otherSize;
    }

    public Optional<Coordinates> getQuadrant() {
        return quadrant;
    }

    /**
     * Validates that {@code otherMatrix} is non-null and has the reference
     * size.
     */
    public static void validate(int referenceSize, Matrix otherMatrix) {
        validate(referenceSize, otherMatrix, Optional.empty());
    }

    /**
     * Validates that {@code otherMatrix} is non-null and has the reference
     * size, recording {@code quadrant} when the sizes differ.
     */
    public static void validate(
            int referenceSize,
            Matrix otherMatrix,
            Optional<Coordinates> quadrant) {
        Objects.requireNonNull(quadrant, "quadrant must not be null");
        Objects.requireNonNull(otherMatrix, "otherMatrix must not be null");
        if (referenceSize != otherMatrix.size()) {
            throw new IllegalArgumentException(
                    new InconsistentSizeException(referenceSize, otherMatrix.size(), quadrant));
        }
    }
}
