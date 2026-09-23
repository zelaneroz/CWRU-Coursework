# Java

# Packages
Groups related Java classes together.
```
package animals;
public class Dog{
}
```

A different file can import it:
```
import animals.Dog;
```

A typical project can lok like:
```
src/
    somepackage/
        ClassA.java
        ClassB.java
        ClassC.java
```

## Access Modifiers: public, private, protected, or no modifier

## Comparable<T>

## Comparator<T>

## Method References
Given a `Person`, call its `age()` method.
```
Person::age
```

## Generics

# Records
A Java `record` represents a small object whose main purpose is to hold data.
* Records can have methods
* A method can also return another record

Instead of:
```
public final class Point {
    private final int x;
    private final int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int x() {
        return x;
    }

    public int y() {
        return y;
    }
}
```

you can write:
```
public record Point (int x, int y){
}
```

and it automatically gives you:
```
Point.x()
Point.y()
```


## Imutability

