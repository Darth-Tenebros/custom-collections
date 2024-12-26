package core;

import java.util.Optional;

public interface OrderedCollection<T> extends Collection<T>{
    Optional<T> getFirst();
    Optional<T> getLast();
    void addFirst(T element);
    void addLast(T element);
}
