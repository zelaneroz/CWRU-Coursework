package HW2;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class NumLinkedListTest {

    private NumLinkedList emptyList;
    private NumLinkedList singleElementList;
    private NumLinkedList multiElementList;
    private NumLinkedList sortedList;
    private NumLinkedList unsortedList;

    @Before
    public void setUp() {
        emptyList = new NumLinkedList(); // Empty list

        singleElementList = new NumLinkedList();
        singleElementList.add(5); // List with one element

        multiElementList = new NumLinkedList();
        multiElementList.add(1);
        multiElementList.add(2);
        multiElementList.add(3); // List with multiple elements

        sortedList = new NumLinkedList();
        sortedList.add(1);
        sortedList.add(2);
        sortedList.add(3); // Already sorted list

        unsortedList = new NumLinkedList();
        unsortedList.add(3);
        unsortedList.add(2);
        unsortedList.add(1); // Unsorted list
    }

    @Test
    public void testAdd() {
        emptyList.add(10);
        assertEquals(1, emptyList.size());
        assertEquals(10, emptyList.head.getElement());
    }

    @Test
    public void testSize() {
        assertEquals(0, emptyList.size());
        assertEquals(1, singleElementList.size());
        assertEquals(3, multiElementList.size());
    }

    @Test
    public void testIsSorted() {
        assertTrue(emptyList.isSorted()); // An empty list is sorted
        assertTrue(singleElementList.isSorted()); // A single element list is sorted
        assertTrue(sortedList.isSorted()); // A sorted list should return true
        assertFalse(unsortedList.isSorted()); // An unsorted list should return false
    }

    @Test
    public void testReverse() {
        singleElementList.reverse();
        assertEquals(5, singleElementList.head.getElement()); // Single element remains unchanged

        multiElementList.reverse();
        assertEquals(3, multiElementList.head.getElement()); // The first element should be 3
        assertEquals(1, multiElementList.head.getNextNode().getNextNode().getElement()); // The last element should be 1
    }

    @Test
    public void testMergeSortedLists() {
        NumLinkedList merged = NumLinkedList.merge(sortedList, multiElementList);
        merged.printList(); // Optional, for visual debugging

        assertEquals(6, merged.size());
        assertTrue(merged.isSorted());
    }

    @Test
    public void testMergeUnsortedLists() {
        NumLinkedList merged = NumLinkedList.merge(unsortedList, multiElementList);
        merged.printList(); // Optional, for visual debugging

        assertEquals(6, merged.size());
        assertFalse(merged.isSorted()); // Merged result is unsorted, because unsortedList is unsorted
    }

    @Test
    public void testDuplicate() {
        NumLinkedList duplicateList = NumLinkedList.duplicate(multiElementList);
        assertNotSame(multiElementList, duplicateList); // Ensure they are different objects
        assertEquals(multiElementList.size(), duplicateList.size());
        duplicateList.printList(); // Optional, for visual debugging
    }

    @Test
    public void testPrintList() {
        singleElementList.printList(); // Should print: 5
        multiElementList.printList();  // Should print: 1, 2, 3
        emptyList.printList();         // Should print nothing
    }

    @Test
    public void testEdgeCases() {
        // Test adding to empty list
        emptyList.add(100);
        assertEquals(1, emptyList.size());
        assertEquals(100, emptyList.head.getElement());

        // Test reverse on empty list
        emptyList.reverse();
        assertNull(emptyList.head); // Should remain null (empty)

        // Test merging two empty lists
        NumLinkedList mergedEmptyLists = NumLinkedList.merge(emptyList, emptyList);
        assertEquals(0, mergedEmptyLists.size());
    }

    @Test(expected = NullPointerException.class)
    public void testNullHead() {
        // Attempting operations when head is null should gracefully handle or throw appropriate errors
        emptyList.head.getNextNode(); // Should throw NullPointerException
    }
}


//public class HW2.NumLinkedListTest {
//    public static void main(String[] args){
//        HW2.NumLinkedList list1 = new HW2.NumLinkedList();
//        HW2.NumLinkedList list2 = new HW2.NumLinkedList();
//        System.out.println("Lists created.");
//
//        //ADDING ELEMENTS TO LIST1
//        list1.add(1);
//        list1.add(3);
//        list1.add(5);
//
//        //ADDING ELEMENTS TO LIST2
//        list1.add(2);
//        list1.add(4);
//        list1.add(7);
//
//        list1.printList();
//
//        //DUPLICATE LIST1
//        HW2.NumLinkedList duplicated = HW2.NumLinkedList.duplicate(list1);
//        duplicated.printList();
//    }
//}
