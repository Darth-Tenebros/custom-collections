package core;

import java.util.Optional;

public interface Collection<T> extends Iterable<T>{
    void add(T element);
    void addAll(Collection<T> collection);
    void remove(T element);
    boolean contains(T element);
    Optional<T> get(int index);
    Optional<Integer> indexOf(T element);
    int size();
    boolean isEmpty();
    void clear();
}
