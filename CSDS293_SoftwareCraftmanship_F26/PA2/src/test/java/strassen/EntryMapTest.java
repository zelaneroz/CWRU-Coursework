package strassen;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

class EntryMapTest {

    @Test
    void retrievesValuesAndComputesSparseDimensions() {
        EntryMap<Double> entries = EntryMap.from(Map.of(
                new Coordinates(0, 1), 2.0,
                new Coordinates(2, 0), 3.0));

        assertEquals(2.0, entries.get(new Coordinates(0, 1)));
        assertNull(entries.get(new Coordinates(1, 1)));
        assertEquals(0.0, entries.getOrDefault(new Coordinates(1, 1), 0.0));
        assertEquals(3, entries.rows());
        assertEquals(2, entries.columns());
        assertEquals(3, entries.size());
    }

    @Test
    void reportsZeroDimensionsWhenEmpty() {
        EntryMap<Double> entries = EntryMap.from(Map.of());

        assertEquals(0, entries.rows());
        assertEquals(0, entries.columns());
        assertEquals(0, entries.size());
    }

    @Test
    void copiesTheSourceMap() {
        Map<Coordinates, Double> source = new HashMap<>();
        source.put(Coordinates.ORIGIN, 1.0);
        EntryMap<Double> entries = EntryMap.from(source);

        source.put(new Coordinates(4, 4), 2.0);

        assertNull(entries.get(new Coordinates(4, 4)));
        assertEquals(1, entries.size());
    }

    @Test
    void rejectsInvalidPublicArguments() {
        assertThrows(NullPointerException.class, () -> EntryMap.from(null));
        assertThrows(NullPointerException.class,
                () -> EntryMap.from(java.util.Collections.singletonMap(null, 1.0)));
        assertThrows(NullPointerException.class,
                () -> EntryMap.from(java.util.Collections.singletonMap(Coordinates.ORIGIN, null)));
        assertThrows(IllegalArgumentException.class,
                () -> EntryMap.from(Map.of(new Coordinates(-1, 0), 1.0)));

        EntryMap<Double> entries = EntryMap.from(Map.of());
        assertThrows(NullPointerException.class, () -> entries.get(null));
        assertThrows(NullPointerException.class, () -> entries.getOrDefault(null, 0.0));
        assertThrows(NullPointerException.class,
                () -> entries.getOrDefault(Coordinates.ORIGIN, null));
    }
}
