package strassen;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class EntryTest {

    @Test
    void translatesCoordinatesAndPreservesValue() {
        Entry<Double> entry = new Entry<>(new Coordinates(2, 3), 4.5);

        assertEquals(new Entry<>(new Coordinates(1, 5), 4.5),
                entry.translated(new Coordinates(-1, 2)));
    }

    @Test
    void rejectsNullConstructorArgumentsAndOffset() {
        assertThrows(NullPointerException.class, () -> new Entry<>(null, 1.0));
        assertThrows(NullPointerException.class, () -> new Entry<>(Coordinates.ORIGIN, null));

        Entry<Double> entry = new Entry<>(Coordinates.ORIGIN, 1.0);
        assertThrows(NullPointerException.class, () -> entry.translated(null));
    }
}
