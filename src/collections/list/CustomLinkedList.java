package collections.list;

import core.*;

import java.util.Arrays;
import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Predicate;

public class CustomLinkedList<T> implements List<T>, Collection<T>, Sortable<T>, Filterable<T>, OrderedCollection<T> {

    private class Node {
        T data;
        Node next;
        Node prev;

        Node(T data){
            this.data = data;
        }
    }

    public CustomLinkedList() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    private Node head;
    private Node tail;
    private int size;

    @Override
    public Optional<T> getFirst() {
        return Optional.ofNullable(head.data);
    }

    @Override
    public Optional<T> getLast() {
        return Optional.ofNullable(tail.data);
    }

    @Override
    public void addFirst(T element) {
        if(element == null){
            throw new IllegalArgumentException("cannot add null element");
        }

        Node newNode = new Node(element);
        if(isEmpty()){
            head = tail = newNode;
        }else{
            newNode.next = head;
            head.prev = newNode;
            head = newNode;
        }
        size++;
    }

    @Override
    public void addLast(T element) {
        if(element == null){
            throw new IllegalArgumentException("cannot add null element");
        }

        Node newNode = new Node(element);
        if(isEmpty()){
            head = tail = newNode;
        }else {
            newNode.prev = tail;
            tail.next = newNode;
            tail = newNode;
        }
        size++;
    }

    @Override
    public void add(T element) {
        if(element == null){
            throw new IllegalArgumentException("cannot add null element");
        }
        addLast(element);
    }

    @Override
    public void addAll(Collection<T> collection) {
        Iterator<T> iterator = collection.iterator();
        while(iterator.hasNext()){
            add(iterator.next().get());
        }
    }

    @Override
    public void remove(T element) {
        if(element == null){
            throw new IllegalArgumentException("cannot add null element");
        }

        Node current = head;
        while(current != null){
            if(current.data.equals(element)){
                removeNode(current);
                return;
            }
            current = current.next;
        }
    }

    private void removeNode(Node node){
        if(node == head){
            head = head.next;
        }else{
            node.prev.next = node.next;
        }

        if(node == tail){
            tail = node.prev;
        }else{
            node.next.prev = node.prev;
        }
        size--;
    }

    @Override
    public boolean contains(T element) {
        if(element == null){
            throw new IllegalArgumentException("cannot add null element");
        }

        if(size == 0) return false;

        Node current = head;
        while(current != null){
            if(current.data.equals(element)){
                return true;
            }
            current = current.next;
        }
        return false;
    }

    @Override
    public Optional<T> get(int index) {
        if(index < 0 || index > size){
            throw new IndexOutOfBoundsException("index out of bounds for CustomArrayList of size: " + size);
        }

        Node current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return Optional.ofNullable(current.data);
    }

    @Override
    public Optional<Integer> indexOf(T element) {
        if(element == null){
            throw new IllegalArgumentException("cannot add null element");
        }

        Node current = head;
        int index = 0;
        while(current != null){
            if(current.data.equals(element)){
                return Optional.of(index);
            }
            current = current.next;
            index++;
        }

        return Optional.empty();
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public void clear() {
        head = tail = null;
        size = 0;
    }

    @Override
    public Collection<T> filter(Predicate<T> predicate) {
        CustomLinkedList<T> filtered = new CustomLinkedList<>();
        Node current = head;
        while(current != null){
            if(predicate.test(current.data)){
                filtered.add(current.data);
            }
            current = current.next;
        }

        return  filtered;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private Node current = head;
            private Node lastReturned = null;
            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public Optional<T> next() {
                if(!hasNext()){
                    throw new NoSuchElementException("there is no next element");
                }
                lastReturned = current;
                current = current.next;
                return Optional.of(lastReturned.data);
            }

            @Override
            public void remove() {
                if(lastReturned == null){
                    throw new IllegalStateException("that's illegal");
                }
                removeNode(lastReturned);
                lastReturned = null;
            }
        };
    }

    @Override
    @SuppressWarnings("unchecked")
    public void sort(Comparator<T> comparator) {
        if(size()  <= 1) return;

        // convert ll to array
        T[] arr = (T[]) new Object[size()];
        Node current = head;
        int index = 0;
        while(current != null){
            arr[index] = current.data;
            index++;
            current = current.next;
        }

        //sort
        Arrays.sort(arr, comparator);

        //rebuild
        clear();
        Iterator<T> iterator = this.iterator();
        for(T element: arr){
            add(element);
        }
    }
}
