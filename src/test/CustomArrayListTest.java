package test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Comparator;
import java.util.NoSuchElementException;
import collections.list.*;
import core.*;

public class CustomArrayListTest {
    private CustomArrayList<String> list;

    @BeforeEach
    void setUp() {
        list = new CustomArrayList<>();
    }

    // Basic Addition and Size Tests
    @Test
    void testInitialState() {
        assertTrue(list.isEmpty(), "New list should be empty");
        assertEquals(0, list.size(), "New list size should be 0");
    }

    @Test
    void testAdd() {
        list.add("First");
        assertFalse(list.isEmpty(), "List should not be empty after adding an element");
        assertEquals(1, list.size(), "List size should increase after adding an element");
        assertTrue(list.contains("First"), "Added element should be in the list");
    }

    @Test
    void testMultipleAdds() {
        list.add("First");
        list.add("Second");
        list.add("Third");
        assertEquals(3, list.size(), "List size should reflect multiple additions");
    }

    // Remove Tests
    @Test
    void testRemove() {
        list.add("First");
        list.add("Second");
        list.remove("First");
        assertEquals(1, list.size(), "Size should decrease after removal");
        assertFalse(list.contains("First"), "Removed element should not be in the list");
    }

    @Test
    void testRemoveNonExistent() {
        list.add("First");
        list.remove("Second");
        assertEquals(1, list.size(), "Size should remain unchanged when removing non-existent element");
    }

    // Clear and Empty Tests
    @Test
    void testClear() {
        list.add("First");
        list.add("Second");
        list.clear();
        assertTrue(list.isEmpty(), "List should be empty after clear");
        assertEquals(0, list.size(), "Size should be 0 after clear");
    }

    // Contains Tests
    @Test
    void testContains() {
        list.add("First");
        assertTrue(list.contains("First"), "List should contain added element");
        assertFalse(list.contains("Second"), "List should not contain unadded element");
    }

    // Iterator Tests
    @Test
    void testIterator() {
        list.add("First");
        list.add("Second");
        list.add("Third");

        Iterator<String> iterator = list.iterator();
        assertTrue(iterator.hasNext(), "Iterator should have next element");
        assertEquals("First", iterator.next().get(), "First element should be correct");
        assertEquals("Second", iterator.next().get(), "Second element should be correct");

        iterator.remove();
        assertEquals(2, list.size(), "Size should decrease after iterator remove");
        assertFalse(list.contains("Second"), "Removed element should not be in list");
    }

    @Test
    void testIteratorExceptions() {
        list.add("First");
        Iterator<String> iterator = list.iterator();
        iterator.next();

        assertThrows(NoSuchElementException.class, () -> {
            iterator.next().get();
        }, "Calling next() beyond elements should throw exception");
    }

    // Sorting Tests
    @Test
    void testSort() {
        list.add("Banana");
        list.add("Apple");
        list.add("Cherry");

        list.sort(Comparator.naturalOrder());

        Iterator<String> iterator = list.iterator();
        assertEquals("Apple", iterator.next().get(), "First element after sorting should be Apple");
        assertEquals("Banana", iterator.next().get(), "Second element after sorting should be Banana");
        assertEquals("Cherry", iterator.next().get(), "Third element after sorting should be Cherry");
    }

    @Test
    void testCustomSort() {
        list.add("Short");
        list.add("Longer");
        list.add("Longest");

        list.sort(Comparator.comparingInt(String::length));

        Iterator<String> iterator = list.iterator();
        assertEquals("Short", iterator.next().get(), "First element should be shortest");
        assertEquals("Longer", iterator.next().get(), "Next element should be next shortest");
        assertEquals("Longest", iterator.next().get(), "Last element should be longest");
    }

    // Filtering Tests
    @Test
    void testFilter() {
        list.add("Apple");
        list.add("Banana");
        list.add("Cherry");
        list.add("Date");

        Collection<String> filtered = list.filter(s -> s.length() > 4);

        assertEquals(3, filtered.size(), "Filtered collection should have correct size");
        assertTrue(filtered.contains("Apple"));
        assertTrue(filtered.contains("Cherry"));
        assertFalse(filtered.contains("Date"));
    }

    // Edge Case Tests
    @Test
    void testResizing() {
        // Add more elements than initial capacity
        for (int i = 0; i < 20; i++) {
            list.add("Element " + i);
        }
        assertEquals(20, list.size(), "List should dynamically resize");
    }
}