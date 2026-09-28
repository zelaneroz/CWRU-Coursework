package strassen;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class CoordinatesTest {

    @Test
    void exposesRequiredConstants() {
        assertEquals(new Coordinates(0, 0), Coordinates.ORIGIN);
        assertEquals(new Coordinates(1, 0), Coordinates.HORIZONTAL_UNIT);
        assertEquals(new Coordinates(0, 1), Coordinates.VERTICAL_UNIT);
        assertEquals(new Coordinates(1, 1), Coordinates.DIAGONAL_UNIT);
        assertEquals(new Coordinates(-1, 0), Coordinates.NEGATIVE_HORIZONTAL_UNIT);
    }

    @Test
    void performsCoordinateArithmeticWithoutChangingTheOriginal() {
        Coordinates coordinates = new Coordinates(1, 2);

        assertEquals(new Coordinates(-1, -2), coordinates.negated());
        assertEquals(new Coordinates(4, 6), coordinates.plus(new Coordinates(3, 4)));
        assertEquals(new Coordinates(-2, -2), coordinates.minus(new Coordinates(3, 4)));
        assertEquals(new Coordinates(3, 6), coordinates.times(3));
        assertEquals(new Coordinates(1, 2), coordinates);
    }

    @Test
    void comparesByRowThenColumn() {
        assertTrue(new Coordinates(1, 0).compareTo(new Coordinates(0, 1)) > 0);
        assertTrue(new Coordinates(0, 1).compareTo(new Coordinates(0, 0)) > 0);
        assertEquals(0, Coordinates.COMPARATOR.compare(
                new Coordinates(2, 3), new Coordinates(2, 3)));
    }

    @Test
    void rejectsNullCoordinateOperands() {
        Coordinates coordinates = Coordinates.ORIGIN;

        assertThrows(NullPointerException.class, () -> coordinates.plus(null));
        assertThrows(NullPointerException.class, () -> coordinates.minus(null));
        assertThrows(NullPointerException.class, () -> coordinates.compareTo(null));
    }
}
