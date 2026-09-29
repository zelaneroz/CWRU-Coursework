package strassen;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

class MatrixTest {

    @Test
    void createsSparseMatrixAndDelegatesAccess() {
        Matrix matrix = Matrix.from(Map.of(
                Coordinates.ORIGIN, 1.0F,
                new Coordinates(0, 1), 0.0F,
                new Coordinates(2, 2), 3.0F));

        assertEquals(4, matrix.size());
        assertEquals(1.0F, matrix.get());
        assertEquals(0.0F, matrix.get(new Coordinates(0, 1)));
        assertEquals(List.of(
                new Entry<>(Coordinates.ORIGIN, 1.0F),
                new Entry<>(new Coordinates(2, 2), 3.0F)),
                matrix.stream().toList());
    }

    @Test
    void acceptsAnEntryMapAndRejectsNullFactories() {
        EntryMap<Float> entries = EntryMap.from(Map.of(Coordinates.ORIGIN, 2.0F));

        assertEquals(2.0F, Matrix.from(entries).get());
        assertThrows(NullPointerException.class,
                () -> Matrix.from((EntryMap<Float>) null));
        assertThrows(NullPointerException.class,
                () -> Matrix.from((Map<Coordinates, Float>) null));
    }

    @Test
    void negatesWithoutChangingTheOriginal() {
        Matrix matrix = Matrix.from(Map.of(
                Coordinates.ORIGIN, 2.5F,
                new Coordinates(1, 1), -3.0F));

        Matrix negated = matrix.negated();

        assertEquals(-2.5F, negated.get());
        assertEquals(3.0F, negated.get(new Coordinates(1, 1)));
        assertEquals(2.5F, matrix.get());
        assertEquals(matrix.size(), negated.size());
    }

    @Test
    void addsAndSubtractsSparseMatrices() {
        Matrix left = Matrix.from(Map.of(
                Coordinates.ORIGIN, 2.0F,
                new Coordinates(1, 1), 3.0F));
        Matrix right = Matrix.from(Map.of(
                Coordinates.ORIGIN, -2.0F,
                new Coordinates(0, 1), 4.0F));

        Matrix sum = left.plus(right);
        Matrix difference = left.minus(right);

        assertEquals(0.0F, sum.get());
        assertEquals(4.0F, sum.get(new Coordinates(0, 1)));
        assertEquals(3.0F, sum.get(new Coordinates(1, 1)));
        assertEquals(4.0F, difference.get());
        assertEquals(-4.0F, difference.get(new Coordinates(0, 1)));
        assertEquals(3.0F, difference.get(new Coordinates(1, 1)));
    }

    @Test
    void preservesLogicalSizeWhenAllEntriesCancel() {
        Matrix matrix = Matrix.from(Map.of(new Coordinates(2, 2), 5.0F));

        Matrix zero = matrix.minus(matrix);

        assertEquals(4, zero.size());
        assertEquals(List.of(), zero.stream().toList());
    }

    @Test
    void rejectsNullAndDifferentlySizedArithmeticOperands() {
        Matrix sizeOne = Matrix.from(Map.of(Coordinates.ORIGIN, 1.0F));
        Matrix sizeTwo = Matrix.from(Map.of(new Coordinates(1, 1), 1.0F));

        assertThrows(NullPointerException.class, () -> sizeOne.plus(null));
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class, () -> sizeOne.minus(sizeTwo));
        assertInstanceOf(InconsistentSizeException.class, exception.getCause());
    }

    @Test
    void extractsAndTranslatesSubMatrix() {
        Matrix matrix = Matrix.from(Map.of(
                new Coordinates(0, 0), 1.0F,
                new Coordinates(0, 1), 2.0F,
                new Coordinates(0, 2), 3.0F,
                new Coordinates(1, 0), 5.0F,
                new Coordinates(1, 1), 6.0F,
                new Coordinates(1, 2), 7.0F,
                new Coordinates(2, 0), 6.0F,
                new Coordinates(2, 1), 7.0F,
                new Coordinates(2, 2), 8.0F));

        Matrix lowerRight = matrix.subMatrix(
                new Coordinates(2, 2), new Coordinates(4, 4));

        assertEquals(2, lowerRight.size());
        assertEquals(8.0F, lowerRight.get());
        assertEquals(0.0F, lowerRight.get(new Coordinates(1, 1)));
        assertEquals(List.of(new Entry<>(Coordinates.ORIGIN, 8.0F)),
                lowerRight.stream().toList());
    }

    @Test
    void rejectsInvalidSubMatrixBounds() {
        Matrix matrix = Matrix.from(Map.of(new Coordinates(2, 2), 1.0F));

        assertThrows(NullPointerException.class,
                () -> matrix.subMatrix(null, Coordinates.ORIGIN));
        assertThrows(NullPointerException.class,
                () -> matrix.subMatrix(Coordinates.ORIGIN, null));
        assertThrows(IllegalArgumentException.class,
                () -> matrix.subMatrix(new Coordinates(-1, 0), Coordinates.ORIGIN));
        assertThrows(IllegalArgumentException.class,
                () -> matrix.subMatrix(new Coordinates(2, 2), new Coordinates(1, 3)));
        assertThrows(IllegalArgumentException.class,
                () -> matrix.subMatrix(Coordinates.ORIGIN, new Coordinates(5, 4)));
    }

    @Test
    void extractsEachQuadrantWithTheRequiredOffsets() {
        Matrix matrix = matrixFrom(new float[][] {
                {1, 2, 3, 4},
                {5, 6, 7, 8},
                {6, 7, 8, 9},
                {5, 4, 3, 2}
        });

        assertMatrixEquals(new float[][] {{1, 2}, {5, 6}},
                matrix.quadrant(false, false));
        assertMatrixEquals(new float[][] {{3, 4}, {7, 8}},
                matrix.quadrant(true, false));
        assertMatrixEquals(new float[][] {{6, 7}, {5, 4}},
                matrix.quadrant(false, true));
        assertMatrixEquals(new float[][] {{8, 9}, {3, 2}},
                matrix.quadrant(true, true));
    }

    @Test
    void multipliesOneByOneMatricesAndPreservesAZeroResultSize() {
        Matrix left = Matrix.from(Map.of(Coordinates.ORIGIN, 6.0F));
        Matrix right = Matrix.from(Map.of(Coordinates.ORIGIN, 7.0F));

        assertMatrixEquals(new float[][] {{42}}, left.times(right));

        Matrix logicalZero = left.minus(left);
        Matrix zeroProduct = logicalZero.times(right);
        assertEquals(1, zeroProduct.size());
        assertEquals(0.0F, zeroProduct.get());
    }

    @Test
    void multipliesTwoByTwoMatrices() {
        Matrix left = matrixFrom(new float[][] {{1, 2}, {3, 4}});
        Matrix right = matrixFrom(new float[][] {{5, 6}, {7, 8}});

        assertMatrixEquals(new float[][] {{19, 22}, {43, 50}}, left.times(right));
    }

    @Test
    void multipliesFourByFourMatrices() {
        Matrix left = matrixFrom(new float[][] {
                {1, 2, 3, 4},
                {5, 6, 7, 8},
                {6, 7, 8, 9},
                {5, 4, 3, 2}
        });
        Matrix right = matrixFrom(new float[][] {
                {1, 2, 0, 1},
                {0, 1, 1, 0},
                {2, 0, 1, 2},
                {1, 1, 0, 1}
        });

        assertMatrixEquals(new float[][] {
                {11, 8, 5, 11},
                {27, 24, 13, 27},
                {31, 28, 15, 31},
                {13, 16, 7, 13}
        }, left.times(right));
    }

    @Test
    void validatesMultiplicationOperandsAndHandlesEmptyMatrices() {
        Matrix sizeOne = Matrix.from(Map.of(Coordinates.ORIGIN, 1.0F));
        Matrix sizeTwo = Matrix.from(Map.of(new Coordinates(1, 1), 1.0F));

        assertThrows(NullPointerException.class, () -> sizeOne.times(null));
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class, () -> sizeOne.times(sizeTwo));
        assertInstanceOf(InconsistentSizeException.class, exception.getCause());

        Matrix empty = Matrix.from(Map.of());
        Matrix emptyProduct = empty.times(empty);
        assertEquals(0, emptyProduct.size());
        assertEquals(List.of(), emptyProduct.stream().toList());
    }

    private static Matrix matrixFrom(float[][] values) {
        Map<Coordinates, Float> entries = new HashMap<>();
        for (int row = 0; row < values.length; row++) {
            for (int column = 0; column < values[row].length; column++) {
                entries.put(new Coordinates(row, column), values[row][column]);
            }
        }
        return Matrix.from(entries);
    }

    private static void assertMatrixEquals(float[][] expected, Matrix actual) {
        assertEquals(expected.length, actual.size());
        for (int row = 0; row < expected.length; row++) {
            for (int column = 0; column < expected[row].length; column++) {
                assertEquals(expected[row][column], actual.get(new Coordinates(row, column)),
                        "entry at (" + row + ", " + column + ")");
            }
        }
    }
}
