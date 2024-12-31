package core;

import java.util.Optional;

public interface List<T> {
    void remove(T element);
    Optional<T> get(int index);
    Optional<Integer> indexOf(T element);

}
