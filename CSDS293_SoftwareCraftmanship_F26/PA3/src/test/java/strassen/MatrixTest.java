package strassen;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
}
