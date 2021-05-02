package com.company.util;

import java.util.*;

public class OrderedList<T extends Comparable> implements List<T> {

    private final static int DEFAULT_CAPACITY = 10;


    private T[] elements;
    private int capacity;
    private int size;


    public OrderedList(){
        this.elements = (T[]) new Comparable[DEFAULT_CAPACITY];
        this.capacity = DEFAULT_CAPACITY;
        this.size = 0;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    @Override
    public boolean contains(Object o) {

        for (Object obj : this.elements) {
            if(obj.equals(o)){
                return true;
            }
        }
        return false;
    }

    @Override
    public Iterator<T> iterator() {
        Iterator<T> it;
        it = new Itr();
        return it;
    }

    private class Itr implements Iterator<T> {

        protected int currentIndex = 0;

        @Override
        public boolean hasNext() {
            return currentIndex < size() && elements[currentIndex] != null;
        }

        @Override
        public T next() {
            return (T) elements[currentIndex++];
        }
    }

    private class ListItr extends Itr implements ListIterator<T> {

        ListItr(int index) {
            super();
            currentIndex = index;
        }

        @Override
        public boolean hasPrevious() {
            return currentIndex - 1 > 0 && elements[currentIndex - 1] != null;
        }

        @Override
        public T previous() {
            return (T) elements[currentIndex--];
        }

        @Override
        public int nextIndex() {
            if (this.hasNext()) {
                return currentIndex + 1;
            } else {
                return size();
            }

        }

        @Override
        public int previousIndex() {
            if (this.hasPrevious()) {
                return currentIndex - 1;
            } else {
                throw new IndexOutOfBoundsException();
            }
        }

        @Override
        public void remove() {
            try {
                OrderedList.this.remove(currentIndex);
            } catch(IndexOutOfBoundsException ex) {
                throw new ConcurrentModificationException();
            }
        }

        @Override
        public void set(T t) {
            try {
                OrderedList.this.set(currentIndex, t);
            } catch (IndexOutOfBoundsException ex) {
                throw new ConcurrentModificationException();
            }

        }

        @Override
        public void add(T t) {
            OrderedList.this.add(t);

        }
    }

    @Override
    public Object[] toArray() {
        return Arrays.copyOf(this.elements, 0);
    }

    @Override
    public Object[] toArray(Object[] t1s) {
        if (t1s.length < this.size) {
            return Arrays.copyOf(this.elements, this.size, t1s.getClass());
        } else {
            System.arraycopy(this.elements, 0, t1s, 0, this.size);
            if (t1s.length > this.size) {
                t1s[this.size] = null;
            }

            return t1s;
        }
    }

    @Override
    public boolean add(T t) {
        try {
            T[] temp = this.elements.clone();

            if (this.size + 1 > this.capacity) {
                this.capacity *= 2;

                this.elements = (T[]) new Object[this.capacity];

                copy(temp);
            }

            for (int i = 0; i < this.size; i++) {
                if (t.compareTo(this.elements[i]) < 0) {
                    this.elements[i] = t;
                    this.size++;
                    for (int j = i + 1; j < this.size; j++) {
                        this.elements[j] = temp[j - 1];
                    }
                    return true;
                }
            }
            this.elements[size] = t;
            this.size++;
            return true;
        } catch (Exception e){
            return false;
        }
    }

    @Override
    public boolean remove(Object o) {
        for(int i = 0; i < size; i++){
            if(this.elements[i].equals(o)){
                fastRemove(this.elements, i);
                return true;
            }
        }
        return false;
    }


    private void fastRemove(Object[] arr, int i) {
        int newSize;
        if ((newSize = this.size - 1) > i) {
            System.arraycopy(arr, i + 1, arr, i, newSize - i);
        }

        this.size = newSize;
        arr[this.size] = null;
    }

    @Override
    public boolean containsAll(Collection<?> collection) {
        if(collection.isEmpty()){
            return false;
        }
        for (Object o: collection) {
            if(this.indexOf(o) == -1){
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends T> collection) {
        for (T t : collection){
            add(t);
        }
        return true;
    }

    @Override
    public boolean addAll(int j, Collection<? extends T> collection) {
        try {
            T[] arr = (T[]) collection.toArray();
            for(int i = j; i < arr.length; i++){
                this.add(arr[i]);
            }
            return true;
        } catch (Exception e){
            return false;
        }
    }

    @Override
    public boolean removeAll(Collection<?> collection) {
        try{
            for (Object o: collection) {
                this.remove(o);
            }
            return true;
        } catch (Exception e){
            return false;
        }
    }

    @Override
    public boolean retainAll(Collection<?> collection) {
        try{
            for (Object o: collection) {
                if(this.indexOf(o) == -1) {
                    this.remove(o);
                }
            }
            return true;
        } catch (Exception e){
            return false;
        }
    }

    @Override
    public void clear() {
        for (int i = 0; i < this.size; i++) {
            this.elements[i] = null;
        }

        this.size = 0;
    }

    @Override
    public T get(int i) {
        return this.elements[i];
    }

    @Override
    public T set(int i, T t) {
        T old = this.elements[i];
        remove(i);
        add(t);
        return old;
    }

    @Override
    public void add(int i, T t) {
        this.add(t);
    }

    @Override
    public T remove(int i) {
        if(i >= size){
            throw new RuntimeException("wrong index");
        }
        T old = this.elements[i];
        this.fastRemove(this.elements, i);
        return old;
    }

    @Override
    public int indexOf(Object o) {
        for(int i = 0; i < this.size; i++){
            if(this.elements[i].equals(o)){
                return i;
            }
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        for (int i = this.size; i >= 0; i--){
            if(this.elements[i].equals(o)){
                return i;
            }
        }
        return -1;
    }

    @Override
    public ListIterator<T> listIterator() {
        return new ListItr(0);
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        return new ListItr(index);
    }

    @Override
    public List<T> subList(int i, int i1) {
        if(i1 > size || i < 0 || i1 <= i){
            throw new RuntimeException("Wrong index");
        }
        List<T> list = new OrderedList<T>();
        for (; i < i1; i++){
            list.add(this.elements[i]);
        }
        return list;
    }


    private void copy(T[] arr){
        for (int i = 0; i < arr.length; i++) {
            this.elements[i] = arr[i];
        }
    }
}
