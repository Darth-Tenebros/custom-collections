package core;

import java.util.Optional;

public interface Iterator<T> {
    boolean hasNext();
    Optional<T> next();
    void remove();
}
