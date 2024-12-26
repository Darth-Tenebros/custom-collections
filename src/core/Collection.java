package core;

import java.util.Optional;

public interface Collection<T> {
    void add(T element);
    void remove(T element);
    boolean contains(T element);
    Optional<T> get(int index);
    Optional<Integer> indexOf(T element);
    int size();
    boolean isEmpty();
    void clear();
}
