package strassen;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.Test;

class QuadrantsTest {

    @Test
    void assemblesBlocksInTheirSemanticQuadrants() {
        Matrix assembled = Quadrants.from(Map.of(
                Coordinates.ORIGIN, filledBlock(1.0F),
                Coordinates.HORIZONTAL_UNIT, filledBlock(2.0F),
                Coordinates.VERTICAL_UNIT, filledBlock(3.0F),
                Coordinates.DIAGONAL_UNIT, filledBlock(4.0F)))
                .toMatrix();

        assertEquals(4, assembled.size());
        assertEquals(1.0F, assembled.get(new Coordinates(0, 0)));
        assertEquals(2.0F, assembled.get(new Coordinates(0, 2)));
        assertEquals(3.0F, assembled.get(new Coordinates(2, 0)));
        assertEquals(4.0F, assembled.get(new Coordinates(2, 2)));
    }

    @Test
    void roundTripsAFullMatrixThroughItsQuadrants() {
        Matrix original = Matrix.from(Map.of(
                new Coordinates(0, 0), 1.0F,
                new Coordinates(0, 2), 3.0F,
                new Coordinates(1, 1), 6.0F,
                new Coordinates(1, 3), 8.0F,
                new Coordinates(2, 0), 6.0F,
                new Coordinates(2, 2), 8.0F,
                new Coordinates(3, 1), 4.0F,
                new Coordinates(3, 3), 2.0F));

        Matrix rebuilt = Quadrants.from(Map.of(
                Coordinates.ORIGIN, original.quadrant(false, false),
                Coordinates.HORIZONTAL_UNIT, original.quadrant(true, false),
                Coordinates.VERTICAL_UNIT, original.quadrant(false, true),
                Coordinates.DIAGONAL_UNIT, original.quadrant(true, true)))
                .toMatrix();

        assertEquals(original.size(), rebuilt.size());
        assertEquals(original.stream().toList(), rebuilt.stream().toList());
    }

    @Test
    void preservesLogicalSizeWhenAllQuadrantsAreSparseZeroMatrices() {
        Matrix populated = Matrix.from(Map.of(new Coordinates(1, 1), 5.0F));
        Matrix sparseZero = populated.minus(populated);
        Quadrants quadrants = Quadrants.from(Map.of(
                Coordinates.ORIGIN, sparseZero,
                Coordinates.HORIZONTAL_UNIT, sparseZero,
                Coordinates.VERTICAL_UNIT, sparseZero,
                Coordinates.DIAGONAL_UNIT, sparseZero));

        Matrix assembled = quadrants.toMatrix();

        assertEquals(4, quadrants.getSize());
        assertEquals(4, assembled.size());
        assertEquals(List.of(), assembled.stream().toList());
    }

    @Test
    void defensivelyCopiesTheSuppliedMap() {
        Matrix one = Matrix.from(Map.of(Coordinates.ORIGIN, 1.0F));
        Matrix two = Matrix.from(Map.of(Coordinates.ORIGIN, 2.0F));
        Map<Coordinates, Matrix> blocks = new HashMap<>(Map.of(
                Coordinates.ORIGIN, one,
                Coordinates.HORIZONTAL_UNIT, one,
                Coordinates.VERTICAL_UNIT, one,
                Coordinates.DIAGONAL_UNIT, one));
        Quadrants quadrants = Quadrants.from(blocks);

        blocks.put(Coordinates.ORIGIN, two);

        assertEquals(1.0F, quadrants.toMatrix().get());
    }

    @Test
    void rejectsNullMissingAndNullValuedComponents() {
        Matrix block = Matrix.from(Map.of(Coordinates.ORIGIN, 1.0F));
        Map<Coordinates, Matrix> missing = new HashMap<>(validBlocks(block));
        missing.remove(Coordinates.DIAGONAL_UNIT);
        Map<Coordinates, Matrix> nullValued = new HashMap<>(validBlocks(block));
        nullValued.put(Coordinates.DIAGONAL_UNIT, null);

        assertThrows(NullPointerException.class, () -> Quadrants.from(null));
        assertThrows(NullPointerException.class, () -> Quadrants.from(missing));
        assertThrows(NullPointerException.class, () -> Quadrants.from(nullValued));
    }

    @Test
    void ignoresComponentsOutsideTheFourExpectedQuadrants() {
        Matrix block = Matrix.from(Map.of(Coordinates.ORIGIN, 1.0F));
        Map<Coordinates, Matrix> blocks = new HashMap<>(validBlocks(block));
        blocks.put(new Coordinates(2, 2), filledBlock(9.0F));

        Matrix assembled = Quadrants.from(blocks).toMatrix();

        assertEquals(2, assembled.size());
        assertEquals(1.0F, assembled.get());
    }

    @Test
    void identifiesTheQuadrantWhoseSizeDiffers() {
        Matrix sizeOne = Matrix.from(Map.of(Coordinates.ORIGIN, 1.0F));
        Matrix sizeTwo = filledBlock(2.0F);
        Map<Coordinates, Matrix> blocks = new HashMap<>(validBlocks(sizeOne));
        blocks.put(Coordinates.VERTICAL_UNIT, sizeTwo);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class, () -> Quadrants.from(blocks));
        InconsistentSizeException cause = assertInstanceOf(
                InconsistentSizeException.class, exception.getCause());

        assertEquals(1, cause.getReferenceSize());
        assertEquals(2, cause.getOtherSize());
        assertEquals(Optional.of(Coordinates.VERTICAL_UNIT), cause.getQuadrant());
    }

    @Test
    void validatesQuadrantSizesInHorizontalVerticalDiagonalOrder() {
        Matrix sizeOne = Matrix.from(Map.of(Coordinates.ORIGIN, 1.0F));
        Matrix sizeTwo = filledBlock(2.0F);
        Map<Coordinates, Matrix> blocks = new HashMap<>(validBlocks(sizeOne));
        blocks.put(Coordinates.HORIZONTAL_UNIT, sizeTwo);
        blocks.put(Coordinates.VERTICAL_UNIT, sizeTwo);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class, () -> Quadrants.from(blocks));
        InconsistentSizeException cause = assertInstanceOf(
                InconsistentSizeException.class, exception.getCause());

        assertEquals(Optional.of(Coordinates.HORIZONTAL_UNIT), cause.getQuadrant());
    }

    private static Matrix filledBlock(Float value) {
        return Matrix.from(Map.of(
                Coordinates.ORIGIN, value,
                new Coordinates(1, 1), value));
    }

    private static Map<Coordinates, Matrix> validBlocks(Matrix block) {
        return Map.of(
                Coordinates.ORIGIN, block,
                Coordinates.HORIZONTAL_UNIT, block,
                Coordinates.VERTICAL_UNIT, block,
                Coordinates.DIAGONAL_UNIT, block);
    }
}
