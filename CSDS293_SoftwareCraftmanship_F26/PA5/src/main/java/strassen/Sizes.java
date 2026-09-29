package strassen;

/** Shared size calculations for sparse matrix representations. */
final class Sizes {

    private Sizes() {
        throw new AssertionError("not instantiable");
    }

    static int rounded(int size) {
        assert size >= 0 : "size must not be negative";
        if (size < 2) {
            return size;
        }

        int highestPower = Integer.highestOneBit(size);
        return highestPower == size
                ? size
                : Math.multiplyExact(highestPower, 2);
    }

    static boolean isRounded(int size) {
        assert size >= 0 : "size must not be negative";
        return size == 0 || Integer.bitCount(size) == 1;
    }
}
