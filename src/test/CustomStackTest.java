package test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.NoSuchElementException;
import collections.stack.CustomStack;
import core.Collection;
import core.Iterator;

public class CustomStackTest {
    private CustomStack<String> stack;

    @BeforeEach
    void setUp() {
        stack = new CustomStack<>();
    }

    // Basic State Tests
    @Test
    void testInitialState() {
        assertTrue(stack.isEmpty(), "New stack should be empty");
        assertEquals(0, stack.size(), "New stack size should be 0");
        assertFalse(stack.peek().isPresent(), "Peek on empty stack should return empty Optional");
    }

    // Push Tests
    @Test
    void testPush() {
        stack.push("First");
        assertFalse(stack.isEmpty(), "Stack should not be empty after pushing");
        assertEquals(1, stack.size(), "Stack size should increase after pushing");
        assertTrue(stack.contains("First"), "Pushed element should be in the stack");
    }

    @Test
    void testMultiplePushes() {
        stack.push("First");
        stack.push("Second");
        stack.push("Third");
        assertEquals(3, stack.size(), "Stack size should reflect multiple pushes");
        assertEquals("Third", stack.peek().get(), "Top element should be last pushed");
    }

    // Pop Tests
    @Test
    void testPop() {
        stack.push("First");
        stack.push("Second");

        assertEquals("Second", stack.pop().get(), "Pop should return top element");
        assertEquals(1, stack.size(), "Size should decrease after pop");
        assertEquals("First", stack.peek().get(), "New top element should be correct");
    }

    @Test
    void testPopEmpty() {
        assertFalse(stack.pop().isPresent(), "Pop on empty stack should return empty Optional");
    }

    // Peek Tests
    @Test
    void testPeek() {
        stack.push("First");
        stack.push("Second");

        assertEquals("Second", stack.peek().get(), "Peek should return top element");
        assertEquals(2, stack.size(), "Peek should not affect size");
        assertEquals("Second", stack.peek().get(), "Repeated peek should return same element");
    }

    // Clear Tests
    @Test
    void testClear() {
        stack.push("First");
        stack.push("Second");
        stack.clear();

        assertTrue(stack.isEmpty(), "Stack should be empty after clear");
        assertEquals(0, stack.size(), "Size should be 0 after clear");
        assertFalse(stack.peek().isPresent(), "Peek should return empty Optional after clear");
    }

    // Contains Tests
    @Test
    void testContains() {
        stack.push("First");
        stack.push("Second");

        assertTrue(stack.contains("First"), "Stack should contain first pushed element");
        assertTrue(stack.contains("Second"), "Stack should contain second pushed element");
        assertFalse(stack.contains("Third"), "Stack should not contain unpushed element");
    }

    // Iterator Tests
    @Test
    void testIterator() {
        stack.push("First");
        stack.push("Second");
        stack.push("Third");

        Iterator<String> iterator = stack.iterator();
        assertTrue(iterator.hasNext(), "Iterator should have next element");
        assertEquals("Third", iterator.next().get(), "First iterated element should be top of stack");
        assertEquals("Second", iterator.next().get(), "Second iterated element should be second from top");
        assertEquals("First", iterator.next().get(), "Third iterated element should be bottom of stack");
        assertFalse(iterator.hasNext(), "Iterator should be exhausted");
    }

    @Test
    void testIteratorExceptions() {
        Iterator<String> iterator = stack.iterator();

        assertThrows(NoSuchElementException.class, () -> {
            iterator.next().get();
        }, "Calling next() on empty stack should throw exception");
    }

    // AddAll Tests
    @Test
    void testAddAll() {
        CustomStack<String> otherStack = new CustomStack<>();
        otherStack.push("First");
        otherStack.push("Second");

        stack.addAll(otherStack);
        assertEquals(2, stack.size(), "Size should reflect added elements");
        assertTrue(stack.contains("First"), "Stack should contain first added element");
        assertTrue(stack.contains("Second"), "Stack should contain second added element");
    }

    // Edge Cases
    @Test
    void testLargeNumberOfPushes() {
        for (int i = 0; i < 1000; i++) {
            stack.push("Element " + i);
        }
        assertEquals(1000, stack.size(), "Stack should handle large number of elements");
        assertEquals("Element 999", stack.peek().get(), "Top element should be correct after many pushes");
    }

    @Test
    void testPushPopAlternating() {
        for (int i = 0; i < 100; i++) {
            stack.push("Element " + i);
            assertEquals("Element " + i, stack.pop().get(), "Pop should return just pushed element");
        }
        assertTrue(stack.isEmpty(), "Stack should be empty after equal pushes and pops");
    }
}