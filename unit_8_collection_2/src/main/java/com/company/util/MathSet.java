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


    public void add(T t){
        if(t == null){
            throw new RuntimeException("null element");
        }

        if(this.indexOf(t) == -1){
            checkCapacity(this.size + 1);
            this.elements[size] = t;
            this.size++;
        }
    }

    public void add(T ... n){
        for (T number : n){
            this.add(number);
        }
    }

    public void join(MathSet ms){
        for (int i = 0; i < ms.size; i++) {
            this.add((T) ms.get(i));
        }
    }

    public void join(MathSet ... ms){
        for (MathSet set : ms){
            this.join(set);
        }
    }

    public void sortDesc(){
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

    public void sortDesc(int firstIndex, int lastIndex){
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

    public void sortDesc(T value){
        this.add(value);
        this.sortDesc();
    }

    public void sortAsc(){
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

    public void sortAsc(int firstIndex, int lastIndex){
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

    public void sortAsc(T value){
        this.add(value);
        this.sortAsc();
    }

    public T get(int index){
        if(index >= size){
            throw new RuntimeException("incorrect index");
        }
        return (T) this.elements[index];
    }

    public Number getMax(){
        Number max = (Number) this.elements[0];
        for (int i = 1; i < this.size; i++){
            if(compare(max, (Number) this.elements[i]) < 0){
                max = (Number) this.elements[i];
            }
        }
        return max;
    }

    public Number getMin(){
        Number min = (Number) this.elements[0];
        for (int i = 1; i < this.size; i++){
            if(compare(min, (Number) this.elements[i]) > 0){
                min = (Number) this.elements[i];
            }
        }
        return min;
    }

    public Number getAverage(){
        BigDecimal sum = new BigDecimal("0");
        for (int i = 0; i < this.size; i++) {
            sum = sum.add(new BigDecimal(this.elements[i].toString()));
        }
        return sum.divide(new BigDecimal(this.size));
    }

    public Number getMedian(){
        Object[] arr =  this.elements.clone();

        for (int i = 0; i < this.size; i++) {
            for (int j = i; j < this.size; j++) {
                if (compare((Number) arr[j],(Number) arr[i]) > 0){
                    Object temp = arr[j];
                    arr[j] = arr[i];
                    arr[i] = temp;
                }
            }
        }

        if(this.size % 2 == 0){
            return new BigDecimal(arr[this.size / 2 - 1].toString()).add(new BigDecimal(arr[this.size / 2].toString())).divide(new BigDecimal(2));
        }
        return (Number) arr[Math.round(this.size / 2) - 1];
    }

    public Number[] toArray(){
        return (Number[]) this.elements;
    }

    public Number[] toArray(int firstIndex, int lastIndex){
        if(firstIndex < 0 || lastIndex >= this.size || firstIndex >= lastIndex){
            throw new RuntimeException("Incorrect index");
        }
        Number[] arr = new Number[lastIndex - firstIndex + 1];
        System.arraycopy(this.elements, firstIndex, arr, 0, lastIndex - firstIndex);
        return arr;
    }

    public MathSet squash(int firstIndex, int lastIndex){
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

    public void clear(){
        this.elements = new Object[DEFAULT_CAPACITY];
        this.size = 0;
    }

    public void clear(Number[] numbers){
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

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("");
        stringBuilder.append("size = ");
        stringBuilder.append(this.size);
        stringBuilder.append("\n");
        for (int i = 0; i < this.size; i++) {
            stringBuilder.append(i);
            stringBuilder.append(" : ");
            stringBuilder.append(this.elements[i]);
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }
}
