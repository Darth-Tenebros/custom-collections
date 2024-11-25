package core;

import java.util.Comparator;

public interface Sortable<T> {
    void sort(Comparator<T> comparator);
}
