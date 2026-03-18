package edu.cmu.cs.cs214.rec02;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

public class IntQueueTest {

    private IntQueue mQueue;
    private List<Integer> testList;

    @Before
    public void setUp() {

        // LinkedIntQueue тестлэх бол:
        // mQueue = new LinkedIntQueue();

        // ArrayIntQueue тестлэх бол:
        mQueue = new ArrayIntQueue();

        testList = new ArrayList<>(List.of(1, 2, 3));
    }

    @Test
    public void testIsEmpty() {
        assertTrue(mQueue.isEmpty());
    }

    @Test
    public void testNotEmpty() {
        mQueue.enqueue(10);
        assertFalse(mQueue.isEmpty());
    }

    @Test
    public void testPeekEmptyQueue() {
        assertThrows(NoSuchElementException.class, () -> mQueue.peek());
    }

    @Test
    public void testDequeueEmptyQueue() {
        assertThrows(NoSuchElementException.class, () -> mQueue.dequeue());
    }

    @Test
    public void testPeekNoEmptyQueue() {
        mQueue.enqueue(10);
        mQueue.enqueue(20);

        assertEquals(Integer.valueOf(10), mQueue.peek());
    }

    @Test
    public void testEnqueue() {

        for (int i = 0; i < testList.size(); i++) {

            mQueue.enqueue(testList.get(i));

            assertEquals(testList.get(0), mQueue.peek());
            assertEquals(i + 1, mQueue.size());
        }
    }

    @Test
    public void testDequeue() {

        mQueue.enqueue(10);
        mQueue.enqueue(20);

        assertEquals(Integer.valueOf(10), mQueue.dequeue());
        assertEquals(Integer.valueOf(20), mQueue.dequeue());

        assertTrue(mQueue.isEmpty());
    }

    @Test
    public void testEnsureCapacity() {

        for (int i = 0; i < 15; i++) {
            mQueue.enqueue(i);
        }

        assertEquals(15, mQueue.size());

        for (int i = 0; i < 15; i++) {
            assertEquals(Integer.valueOf(i), mQueue.dequeue());
        }

        assertTrue(mQueue.isEmpty());
    }

    @Test
    public void testClear() {

        mQueue.enqueue(1);
        mQueue.enqueue(2);

        mQueue.clear();

        assertEquals(0, mQueue.size());
        assertTrue(mQueue.isEmpty());

        assertThrows(NoSuchElementException.class, () -> mQueue.peek());
    }

    @Test
    public void testWrapAround() {

        for (int i = 0; i < 10; i++) {
            mQueue.enqueue(i);
        }

        assertEquals(Integer.valueOf(0), mQueue.dequeue());
        assertEquals(Integer.valueOf(1), mQueue.dequeue());

        mQueue.enqueue(10);
        mQueue.enqueue(11);

        for (int i = 2; i < 12; i++) {
            assertEquals(Integer.valueOf(i), mQueue.dequeue());
        }

        assertTrue(mQueue.isEmpty());
    }

    @Test
    public void testContent() throws IOException {

        InputStream in = new FileInputStream("src/test/resources/data.txt");

        List<Integer> correctResult = new ArrayList<>();

        try (Scanner scanner = new Scanner(in)) {

            scanner.useDelimiter("\\s*fish\\s*");

            while (scanner.hasNextInt()) {

                int input = scanner.nextInt();
                correctResult.add(input);

                mQueue.enqueue(input);
            }

            for (Integer result : correctResult) {
                assertEquals(result, mQueue.dequeue());
            }
        }
    }
}