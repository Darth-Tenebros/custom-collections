package core;

import java.util.function.Predicate;

public interface Filterable<T> {
    Collection<T> filter(Predicate<T> predicate);
}
