# PA3: Matrix with Arithmetic, Block Extraction, Rounded Dimensions, & Size Validation

This Java 17 project implements the sparse-matrix building blocks required by
CSDS 293 Programming Assignment 3. 

## PA3 functionality

- `Coordinates` and `Entry` identify entries in half-open row and column ranges.
- `EntryMap` provides ordered streaming, functional remapping, and power-of-two sizing.
- `Matrix` provides sparse access, negation, addition, subtraction, and translated block extraction.
- `InconsistentSizeException` records both sizes when arithmetic operands are incompatible.
- Arithmetic and block extraction preserve logical dimensions even when trailing values are zero.


## Project structure

```text
PA3/
├── pom.xml
└── src/
    ├── main/java/strassen/     production classes
    └── test/java/strassen/     matching JUnit tests
```


Maven is used for repeatable Java 17 compilation, JUnit dependency management,
test execution, and packaging. This avoids machine-specific JAR files and manual
classpath configuration, and lets VS Code import the same build used from the
terminal.

## Architecture

```mermaid
classDiagram
    class Coordinates {
        <<record>>
        +int row
        +int column
        +Coordinates ORIGIN$
        +Coordinates HORIZONTAL_UNIT$
        +Coordinates VERTICAL_UNIT$
        +Coordinates DIAGONAL_UNIT$
        +Coordinates NEGATIVE_HORIZONTAL_UNIT$
        +Comparator~Coordinates~ COMPARATOR$
        +negated() Coordinates
        +plus(Coordinates offset) Coordinates
        +minus(Coordinates origin) Coordinates
        +times(int scale) Coordinates
        +isInRows(int lower, int upper) boolean
        +isInColumns(int lower, int upper) boolean
        +compareTo(Coordinates other) int
    }

    class Entry~T~ {
        <<record>>
        +Coordinates coordinates
        +T value
        +translated(Coordinates offset) Entry~T~
        +isInRows(int lower, int upper) boolean
        +isInColumns(int lower, int upper) boolean
    }

    class EntryMap~T~ {
        <<final class>>
        -NavigableMap~Coordinates,T~ entryMap
        -int rows
        -int columns
        -EntryMap(NavigableMap~Coordinates,T~ entryMap)
        +from(Map~Coordinates,T~ entryMap)$ EntryMap~T~
        +get(Coordinates coordinates) T
        +getOrDefault(Coordinates coordinates, T defaultValue) T
        +rows() int
        +columns() int
        +size() int
        +stream() Stream~Entry~
        +remap(Function~Coordinates,Coordinates~ coordinatesMapper, Function~T,T~ valueMapper) EntryMap~T~
        +sizeRounded() int
    }

    class Matrix {
        <<final class>>
        -Float ZERO$
        -EntryMap~Float~ representation
        -int size
        -Matrix(EntryMap~Float~ representation)
        -Matrix(EntryMap~Float~ representation, int size)
        +from(EntryMap~Float~ entryMap)$ Matrix
        +from(Map~Coordinates,Float~ entryMap)$ Matrix
        +size() int
        +get(Coordinates coordinates) Float
        +get() Float
        +stream() Stream~Entry~
        +negated() Matrix
        +plus(Matrix other) Matrix
        +minus(Matrix other) Matrix
        +subMatrix(Coordinates origin, Coordinates bound) Matrix
    }

    class InconsistentSizeException {
        <<final exception>>
        -long serialVersionUID$
        -int referenceSize
        -int otherSize
        +InconsistentSizeException(int referenceSize, int otherSize)
        +getReferenceSize() int
        +getOtherSize() int
        +validate(int referenceSize, Matrix otherMatrix)$ void
    }

    class Sizes {
        <<package-private helper>>
        -Sizes()
        ~rounded(int size)$ int
        ~isRounded(int size)$ boolean
    }

    Entry~T~ *-- Coordinates : coordinates
    EntryMap~T~ --> Coordinates : map keys
    EntryMap~T~ ..> Entry~T~ : streams and remaps
    Matrix *-- EntryMap~Float~ : representation
    Matrix ..> Coordinates : lookup and bounds
    Matrix ..> InconsistentSizeException : validates arithmetic
    EntryMap~T~ ..> Sizes : rounds size
    Matrix ..> Sizes : preserves logical size
```

## Craftsmanship

- **Small, focused types:** `Coordinates` handles location and ordering,
  `Entry<T>` binds a location to a value, and `EntryMap<T>` owns sparse storage.
  Each class has one clear reason to change.
- **Immutable value objects:** `Coordinates` and `Entry<T>` are records, so their
  data, equality, hash code, and string representation have consistent value
  semantics. Operations return new objects instead of changing existing ones,
  which makes later matrix algorithms easier to reason about.
- **Protected ownership:** `EntryMap.from` validates and defensively copies the
  caller's map into an unmodifiable `NavigableMap`. Later changes to the source
  map therefore cannot silently change the matrix.
- **One ordering rule:** natural ordering and `Coordinates.COMPARATOR` both use
  row first and column second. The `TreeMap` uses that same comparator, avoiding
  conflicting definitions of coordinate order.
- **Efficient repeated queries:** `EntryMap` calculates row and column dimensions
  once during construction. `rows()`, `columns()`, and `size()` are then constant
  time instead of rescanning every entry.
- **Clear failure behavior:** public methods reject invalid or null arguments
  with exceptions close to the source of the error. The private constructor uses
  an assertion for its internal invariant, following the assignment's boundary
  between public and non-public error handling.
- **Room for extension:** generic values allow numbers now and matrix blocks
  later. The public API does not expose the backing map, so its implementation
  can evolve without breaking clients when later assignments add operations.


## Test design



### `CoordinatesTest`

- `exposesRequiredConstants` catches incorrect names or row/column directions.
- `performsCoordinateArithmeticWithoutChangingTheOriginal` verifies every
  arithmetic operation and proves the record remains immutable.
- `comparesByRowThenColumn` protects the ordering required by the sparse map.
- `rejectsNullCoordinateOperands` verifies the public precondition policy.

### `EntryTest`

- `translatesCoordinatesAndPreservesValue` checks that translation moves only
  the coordinates and retains the generic value.
- `rejectsNullConstructorArgumentsAndOffset` prevents incomplete entries and
  confirms null offsets fail immediately.

### `EntryMapTest`

- `retrievesValuesAndComputesSparseDimensions` covers present and missing lookups,
  default values, rectangular sparse dimensions, and overall size together.
- `reportsZeroDimensionsWhenEmpty` protects the important empty-matrix boundary.
- `copiesTheSourceMap` proves defensive copying by changing the original map
  after construction and checking that the `EntryMap` does not change.
- `rejectsInvalidPublicArguments` covers null maps, keys, values, lookup arguments,
  defaults, and negative matrix coordinates so invalid state cannot enter the
  representation.
- Streaming, remapping, and rounded-size tests cover the PA3 additions.

### `MatrixTest` and `InconsistentSizeExceptionTest`

- Factory and accessor tests verify sparse zero behavior and power-of-two sizing.
- Arithmetic tests cover negation, addition, subtraction, cancellation, nulls,
  and inconsistent sizes.
- Submatrix tests verify half-open filtering, coordinate translation, retained
  logical dimensions, and invalid bounds.


## Build commands

```text
mvn clean test
mvn package
```

The packaged classes are written under `target/`; no executable application is
expected for this library-style assignment.
