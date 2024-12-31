package collections.stack;

import collections.list.CustomArrayList;
import core.Collection;
import core.Iterator;


import java.util.NoSuchElementException;
import java.util.Optional;

public class CustomStack<T> implements Collection<T> {
    private final CustomArrayList<T> store = new CustomArrayList<>();
    public void push(T element){
        store.add(element);
    }

    public Optional<T> pop(){
        if(store.size() <= 0){
            return Optional.empty();
        }
        Optional<T> item = store.get(store.size()-1);
        if(item.isPresent()){
            store.remove(item.get());
            return item;
        }
        return Optional.empty();
    }

    public Optional<T> peek(){
        if(store.size() <= 0){
            return Optional.empty();
        }
        return Optional.ofNullable(
                store.get(store.size()-1).isPresent() ? store.get(store.size()-1).get() : null
        );
    }

    @Override
    public void addAll(Collection<T> collection) {
        Iterator<T> iterator = collection.iterator();
        while(iterator.hasNext()){
            this.store.add(iterator.next().get());
        }
    }

    @Override
    public boolean contains(T element) {
        return store.contains(element);
    }

    @Override
    public int size() {
        return store.size();
    }

    @Override
    public boolean isEmpty() {
        return store.isEmpty();
    }

    @Override
    public void clear() {
        store.clear();
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private int index = size() - 1;
            @Override
            public boolean hasNext() {
                return index >= 0;
            }

            @Override
            public Optional<T> next() {
                if(!hasNext()){
                    throw new NoSuchElementException("no next element");
                }
                return store.get(index--);
            }

            @Override
            public void remove() {}
        };
    }
}
