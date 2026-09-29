package strassen;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.Test;

class InconsistentSizeExceptionTest {

    @Test
    void exposesBothSizes() {
        InconsistentSizeException exception = new InconsistentSizeException(2, 4);

        assertEquals(2, exception.getReferenceSize());
        assertEquals(4, exception.getOtherSize());
        assertEquals(Optional.empty(), exception.getQuadrant());
    }

    @Test
    void exposesTheInconsistentQuadrant() {
        InconsistentSizeException exception = new InconsistentSizeException(
                2, 4, Optional.of(Coordinates.DIAGONAL_UNIT));

        assertEquals(Optional.of(Coordinates.DIAGONAL_UNIT), exception.getQuadrant());
        assertThrows(NullPointerException.class,
                () -> new InconsistentSizeException(2, 4, null));
    }

    @Test
    void validatesNullAndInconsistentMatrices() {
        Matrix matrix = Matrix.from(Map.of(new Coordinates(2, 2), 1.0F));

        assertThrows(NullPointerException.class,
                () -> InconsistentSizeException.validate(4, null));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> InconsistentSizeException.validate(2, matrix));
        InconsistentSizeException cause = assertInstanceOf(
                InconsistentSizeException.class, exception.getCause());
        assertEquals(2, cause.getReferenceSize());
        assertEquals(4, cause.getOtherSize());
        assertEquals(Optional.empty(), cause.getQuadrant());
    }

    @Test
    void validatesMatricesWithinAQuadrant() {
        Matrix matrix = Matrix.from(Map.of(new Coordinates(2, 2), 1.0F));
        Optional<Coordinates> quadrant = Optional.of(Coordinates.HORIZONTAL_UNIT);

        InconsistentSizeException.validate(4, matrix, quadrant);
        assertThrows(NullPointerException.class,
                () -> InconsistentSizeException.validate(4, matrix, null));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> InconsistentSizeException.validate(2, matrix, quadrant));
        InconsistentSizeException cause = assertInstanceOf(
                InconsistentSizeException.class, exception.getCause());
        assertEquals(2, cause.getReferenceSize());
        assertEquals(4, cause.getOtherSize());
        assertEquals(quadrant, cause.getQuadrant());
    }
}
