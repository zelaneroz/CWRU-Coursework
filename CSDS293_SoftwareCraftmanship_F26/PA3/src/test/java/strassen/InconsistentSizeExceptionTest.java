package strassen;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Map;
import org.junit.jupiter.api.Test;

class InconsistentSizeExceptionTest {

    @Test
    void exposesBothSizes() {
        InconsistentSizeException exception = new InconsistentSizeException(2, 4);

        assertEquals(2, exception.getReferenceSize());
        assertEquals(4, exception.getOtherSize());
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
    }
}
