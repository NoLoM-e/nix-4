package com.company.util;


import java.math.BigDecimal;

public class MathSet<T extends Number> {

    private Object[] elements;
    private int size;
    private int capacity;
    private final int DEFAULT_CAPACITY = 10;

    public MathSet(){
        this.capacity = DEFAULT_CAPACITY;
        this.size = 0;
        elements = new Object[this.capacity];
    }

    public MathSet(int capacity){
        this.capacity = capacity;
        this.size = 0;
        elements = new Object[this.capacity];
    }

    public MathSet(Number[] numbers){
        this.capacity = numbers.length;
        this.size = numbers.length;
        this.elements = numbers;
    }

    public MathSet(Number[] ... numbers){
        for (Number[] arr : numbers){
            this.elements = concat(this.elements, arr);
        }
    }

    public MathSet(MathSet numbers){
        this.elements = numbers.elements;
        this.size = numbers.size;
        this.capacity = numbers.capacity;
    }

    public MathSet(MathSet ... numbers){
        MathSet newSet = new MathSet();
        newSet.join(numbers);
        this.elements = newSet.elements;
        this.size = newSet.size;
        this.capacity = newSet.capacity;
    }


    void add(T t){
        if(t == null){
            throw new RuntimeException("null element");
        }

        if(this.indexOf(t) == -1){
            checkCapacity(this.size + 1);
            this.elements[size] = t;
            this.size++;
        }
    }

    void add(T ... n){
        for (T number : n){
            this.add(number);
        }
    }

    void join(MathSet ms){
        for (int i = 0; i < ms.size; i++) {
            this.add((T) ms.get(i));
        }
    }

    void join(MathSet ... ms){
        for (MathSet set : ms){
            this.join(set);
        }
    }

    void sortDesc(){
        for (int i = 0; i < this.size; i++) {
            for (int j = i; j < this.size; j++) {
                if (compare((Number) this.elements[j], (Number) this.elements[i]) > 0){
                    Object temp = this.elements[j];
                    this.elements[j] = this.elements[i];
                    this.elements[i] = temp;
                }
            }
        }
    }

    void sortDesc(int firstIndex, int lastIndex){
        if(lastIndex >= this.size || firstIndex >= lastIndex || firstIndex < 0){
            throw new RuntimeException("incorrect index");
        }

        for (int i = firstIndex; i < lastIndex; i++) {
            for (int j = i; j < lastIndex; j++) {
                if (compare((Number) this.elements[j], (Number) this.elements[i]) > 0){
                    Object temp = this.elements[j];
                    this.elements[j] = this.elements[i];
                    this.elements[i] = temp;
                }
            }
        }
    }

    void sortDesc(T value){
        this.add(value);
        this.sortDesc();
    }

    void sortAsc(){
        for (int i = 0; i < this.size; i++) {
            for (int j = i; j < this.size; j++) {
                if (compare((Number) this.elements[j], (Number) this.elements[i]) < 0){
                    Object temp = this.elements[j];
                    this.elements[j] = this.elements[i];
                    this.elements[i] = temp;
                }
            }
        }
    }

    void sortAsc(int firstIndex, int lastIndex){
        if(lastIndex >= this.size || firstIndex >= lastIndex || firstIndex < 0){
            throw new RuntimeException("incorrect index");
        }

        for (int i = firstIndex; i < lastIndex; i++) {
            for (int j = i; j < lastIndex; j++) {
                if (compare((Number) this.elements[j], (Number) this.elements[i]) < 0){
                    Object temp = this.elements[j];
                    this.elements[j] = this.elements[i];
                    this.elements[i] = temp;
                }
            }
        }
    }

    void sortAsc(T value){
        this.add(value);
        this.sortAsc();
    }

    T get(int index){
        if(index >= size){
            throw new RuntimeException("incorrect index");
        }
        return (T) this.elements[index];
    }

    Number getMax(){
        Number max = (Number) this.elements[0];
        for (int i = 1; i < this.size; i++){
            if(compare(max, (Number) this.elements[i]) > 0){
                max = (Number) this.elements[i];
            }
        }
        return max;
    }

    Number getMin(){
        Number min = (Number) this.elements[0];
        for (int i = 1; i < this.size; i++){
            if(compare(min, (Number) this.elements[i]) < 0){
                min = (Number) this.elements[i];
            }
        }
        return min;
    }

    Number getAverage(){
        BigDecimal sum = new BigDecimal("0");
        for (int i = 0; i < this.size; i++) {
            sum = sum.add(new BigDecimal(this.elements[i].toString()));
        }
        return sum.divide(new BigDecimal(this.size));
    }

    Number getMedian(){
        Number[] arr = (Number[]) this.elements.clone();

        for (int i = 0; i < arr.length; i++) {
            for (int j = i; j < arr.length; j++) {
                if (compare(arr[j], arr[i]) > 0){
                    Number temp = arr[j];
                    arr[j] = arr[i];
                    arr[i] = temp;
                }
            }
        }

        return arr[Math.round(this.size / 2)];
    }

    Number[] toArray(){
        return (Number[]) this.elements;
    }

    Number[] toArray(int firstIndex, int lastIndex){
        if(firstIndex < 0 || lastIndex >= this.size || firstIndex >= lastIndex){
            throw new RuntimeException("Incorrect index");
        }
        Number[] arr = new Number[lastIndex - firstIndex + 1];
        System.arraycopy(this.elements, firstIndex, arr, 0, lastIndex - firstIndex);
        return arr;
    }

    MathSet squash(int firstIndex, int lastIndex){
        if(lastIndex >= size || firstIndex < 0 || firstIndex >= lastIndex){
            throw new RuntimeException("Incorrect index");
        }

        Number[] arr = new Number[size - (lastIndex - firstIndex + 1)];

        for (int i = 0, j = 0; i < size; i++) {
            if(i >= firstIndex && i <= lastIndex){
                continue;
            }
            arr[j++] = (Number) this.elements[i];
        }
        return new MathSet(arr);
    }

    void clear(){
        this.elements = new Object[DEFAULT_CAPACITY];
        this.size = 0;
    }

    void clear(Number[] numbers){
        for (int i = 0; i < numbers.length; i++) {
            int index = indexOf((T) numbers[i]);
            if(index != -1){
                fastRemove(this.elements, index);
            }
        }
    }

    private void checkCapacity(int i){
        if(i > capacity){
            this.capacity = Math.max(capacity * 2, capacity + i + 1);
            Object[] arr = new Object[this.capacity];
            System.arraycopy(this.elements, 0, arr, 0, this.size);
            this.elements = arr;
        }
    }

    private int indexOf(T t){
        for (int i = 0; i < this.size; i++) {
            if(t.equals(elements[i])){
                return i;
            }
        }
        return -1;
    }

    private int compare(Number i, Number i1){
        return new BigDecimal(i.toString()).compareTo(new BigDecimal(i1.toString()));
    }

    private void fastRemove(Object[] es, int i) {
        final int newSize;
        if ((newSize = size - 1) > i)
            System.arraycopy(es, i + 1, es, i, newSize - i);
        size = newSize;
        es[size] = null;
    }

    private Object[] concat(Object[] arr, Object[] arr1){
        Object[] newArr = new Object[arr.length + arr1.length];
        System.arraycopy(arr, 0, newArr, 0, arr.length);
        System.arraycopy(arr1, 0, newArr, arr.length, arr1.length);
        return newArr;
    }
}
