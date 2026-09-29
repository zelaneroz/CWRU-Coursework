package strassen;

import java.util.Objects;

/** Describes an operation attempted on matrices with different sizes. */
public final class InconsistentSizeException extends Exception {

    private static final long serialVersionUID = 1L;

    private final int referenceSize;
    private final int otherSize;

    /** Creates an exception describing the two inconsistent sizes. */
    public InconsistentSizeException(int referenceSize, int otherSize) {
        super("matrix sizes differ: " + referenceSize + " and " + otherSize);
        this.referenceSize = referenceSize;
        this.otherSize = otherSize;
    }

    public int getReferenceSize() {
        return referenceSize;
    }

    public int getOtherSize() {
        return otherSize;
    }

    /**
     * Validates that {@code otherMatrix} is non-null and has the reference
     * size.
     */
    public static void validate(int referenceSize, Matrix otherMatrix) {
        Objects.requireNonNull(otherMatrix, "otherMatrix must not be null");
        if (referenceSize != otherMatrix.size()) {
            throw new IllegalArgumentException(
                    new InconsistentSizeException(referenceSize, otherMatrix.size()));
        }
    }
}
