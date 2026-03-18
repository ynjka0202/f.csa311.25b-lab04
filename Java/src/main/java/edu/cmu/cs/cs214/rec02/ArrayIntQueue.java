package edu.cmu.cs.cs214.rec02;

import java.util.Arrays;
import java.util.NoSuchElementException;

/**
 * A resizable-array implementation of the IntQueue interface.
 * The head of the queue starts out at the head of the array,
 * allowing the queue to grow and shrink in constant time.
 */
public class ArrayIntQueue implements IntQueue {

    private static final int INITIAL_SIZE = 10;

    /** Array holding queue data */
    private Integer[] elementData;

    /** Index of next element to dequeue */
    private int head;

    /** Number of elements in queue */
    private int size;

    /** Constructs empty queue */
    public ArrayIntQueue() {
        elementData = new Integer[INITIAL_SIZE];
        head = 0;
        size = 0;
    }

    /** Remove all elements */
    @Override
    public void clear() {
        Arrays.fill(elementData, null);
        size = 0;
        head = 0;
    }

    /** Remove and return head element */
    @Override
    public Integer dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        Integer value = elementData[head];
        elementData[head] = null;

        head = (head + 1) % elementData.length;
        size--;

        return value;
    }

    /** Add element to queue */
    @Override
    public boolean enqueue(Integer value) {
        ensureCapacity();

        int tail = (head + size) % elementData.length;
        elementData[tail] = value;
        size++;

        return true;
    }

    /** Check if queue empty */
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    /** Return head element */
    @Override
    public Integer peek() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        return elementData[head];
    }

    /** Return size */
    @Override
    public int size() {
        return size;
    }

    /** Increase capacity if needed */
    private void ensureCapacity() {
        if (size == elementData.length) {

            int oldCapacity = elementData.length;
            int newCapacity = 2 * oldCapacity + 1;

            Integer[] newData = new Integer[newCapacity];

            for (int i = 0; i < size; i++) {
                newData[i] = elementData[(head + i) % oldCapacity];
            }

            elementData = newData;
            head = 0;
        }
    }
}