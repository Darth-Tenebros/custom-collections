package core;

import java.util.Optional;

public interface Collection<T> extends Iterable<T>{
    void add(T element);
    void addAll(Collection<T> collection);
    boolean contains(T element);
    int size();
    boolean isEmpty();
    void clear();
}
