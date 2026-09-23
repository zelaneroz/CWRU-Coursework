# PA2: Sparse Matrix Foundations

This Java 17 project implements the sparse-matrix building blocks required by
CSDS 293 Programming Assignment 2. Strassen multiplication is intentionally not
included because the assignment reserves it for a later programming assignment.

## Project structure

```text
PA2/
├── pom.xml
└── src/
    ├── main/java/strassen/     production classes
    └── test/java/strassen/     matching JUnit tests
```


Maven is used for repeatable Java 17 compilation, JUnit dependency management,
test execution, and packaging. This avoids machine-specific JAR files and manual
classpath configuration, and lets VS Code import the same build used from the
terminal.

## Architecture and craftsmanship

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


## Build commands

```text
mvn clean test
mvn package
```

The packaged classes are written under `target/`; no executable application is
expected for this library-style assignment.
