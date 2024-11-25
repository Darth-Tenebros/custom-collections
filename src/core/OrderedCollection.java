package core;

public interface OrderedCollection<T> extends Collection<T>{
    T getFirst();
    T getLast();
    void addFirst();
    void addLast();
}
