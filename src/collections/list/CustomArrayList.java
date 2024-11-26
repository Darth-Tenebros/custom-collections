package collections.list;

import core.*;
import core.Iterable;

import java.util.Arrays;
import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.function.Predicate;

public class CustomArrayList<T> implements Collection<T>, Iterable<T>, Sortable<T>, Filterable<T> {
    private static final int DEFAULT_CAPACITY = 10;
    private Object[] elements;
    private int size;

    public CustomArrayList(){
        this.elements = new Object[DEFAULT_CAPACITY];
        this.size = 0;
    }

    @Override
    public void add(T element) {
        ensureCapacity();
        elements[size++] = element;
    }

    private void ensureCapacity(){
        if(size == elements.length){
            Object[] newElements = new Object[elements.length * 2];
            System.arraycopy(elements, 0, newElements, 0, elements.length);
            elements = newElements;
        }
    }

    @Override
    public void remove(T element) {
        // Handle null element case
        if (element == null) {
            throw new IllegalArgumentException("cannot remove null element");
        }

        // Regular case for non-null elements
        for (int i = 0; i < size; i++) {
            // Only compare if current element is not null
            if (elements[i] != null && element.equals(elements[i])) {
                removeAt(i);
                return;
            }
        }
    }

    private void removeAt(int index){
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Invalid index");
        }
        System.arraycopy(elements, index + 1, elements, index, size - index - 1);
        elements[--size] = null;
    }

    @Override
    public boolean contains(T element) {
        // Handle null element case
        if (element == null) {
            throw new IllegalArgumentException("cannot find null element");
        }

        // Regular case for non-null elements
        for (int i = 0; i < size; i++) {
            // Only compare if current element is not null
            if (elements[i] != null && element.equals(elements[i])) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public void clear() {
        for(int i = 0; i < size; i++){
            elements[i] = null;
        }
        size = 0;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Collection<T> filter(Predicate<T> predicate) {
        CustomArrayList<T> filtered = new CustomArrayList<>();
        for(int i = 0; i < size; i++){
            T element = (T) elements[i];
            if(predicate.test(element)){
                filtered.add(element);
            }
        }
        return filtered;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private int currentIndex = 0;
            @Override
            public boolean hasNext() {
                return currentIndex < size;
            }

            @Override
            @SuppressWarnings("unchecked")
            public T next() {
                if(!hasNext()){
                    throw new NoSuchElementException();
                }
                return (T) elements[currentIndex++];
            }

            @Override
            public void remove() {
                if (currentIndex == 0) {
                    throw new IllegalStateException("Cannot remove before first next() call");
                }
                removeAt(currentIndex - 1);
            }
        };
    }

    @Override
    @SuppressWarnings("unchecked")
    public void sort(Comparator<T> comparator) {
        Object[] temp = Arrays.copyOf(elements, size);
        Arrays.sort((T[]) temp, 0, size, comparator);
        System.arraycopy(temp, 0, elements, 0, size);
    }
}
