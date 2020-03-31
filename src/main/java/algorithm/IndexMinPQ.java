package algorithm;

import java.util.NoSuchElementException;

public class IndexMinPQ<Key extends Comparable<Key>> {
    private int maxPQElementsNumber;
    private int PQElementsNumber;
    private int[] priorityQueue;
    private int[] inversePriorityQueue;
    private Key[] keys;      // keys[i] = priority of i

    public IndexMinPQ(int maxPQElementsNumber) {
        if (maxPQElementsNumber < 0) throw new IllegalArgumentException();
        this.maxPQElementsNumber = maxPQElementsNumber;
        PQElementsNumber = 0;
        keys = (Key[]) new Comparable[maxPQElementsNumber + 1];
        priorityQueue   = new int[maxPQElementsNumber + 1];
        inversePriorityQueue   = new int[maxPQElementsNumber + 1];
        for (int i = 0; i <= maxPQElementsNumber; i++)
            inversePriorityQueue[i] = -1;
    }

    public boolean isEmpty() {
        return PQElementsNumber == 0;
    }

    public boolean contains(int i) {
        validateIndex(i);
        return inversePriorityQueue[i] != -1;
    }

    public int size() {
        return PQElementsNumber;
    }

    public void insert(int i, Key key) {
        validateIndex(i);
        if (contains(i)) throw new IllegalArgumentException("index is already in the priority queue");
        PQElementsNumber++;
        inversePriorityQueue[i] = PQElementsNumber;
        priorityQueue[PQElementsNumber] = i;
        keys[i] = key;
        swim(PQElementsNumber);
    }

    public int delMinElementInPQ() {
        if (PQElementsNumber == 0) throw new NoSuchElementException("Priority queue underflow");
        int min = priorityQueue[1];
        exch(1, PQElementsNumber--);
        sink(1);
        assert min == priorityQueue[PQElementsNumber +1];
        inversePriorityQueue[min] = -1;        // delete
        keys[min] = null;    // to help with garbage collector
        priorityQueue[PQElementsNumber +1] = -1;        // not needed
        return min;
    }

    public void changeKey(int i, Key key) {
        validateIndex(i);
        if (!contains(i)) throw new NoSuchElementException("index is not in the priority queue");
        keys[i] = key;
        swim(inversePriorityQueue[i]);
        sink(inversePriorityQueue[i]);
    }

    public void change(int i, Key key) {
        changeKey(i, key);
    }

    // throw an IllegalArgumentException if i is an invalid index
    private void validateIndex(int index) {
        if (index < 0) throw new IllegalArgumentException("index is negative: " + index);
        if (index >= maxPQElementsNumber) throw new IllegalArgumentException("index >= capacity: " + index);
    }

    private boolean greater(int i, int j) {
        return keys[priorityQueue[i]].compareTo(keys[priorityQueue[j]]) > 0;
    }

    private void exch(int i, int j) {
        int swap = priorityQueue[i];
        priorityQueue[i] = priorityQueue[j];
        priorityQueue[j] = swap;
        inversePriorityQueue[priorityQueue[i]] = i;
        inversePriorityQueue[priorityQueue[j]] = j;
    }

    private void swim(int k) {
        while (k > 1 && greater(k/2, k)) {
            exch(k, k/2);
            k = k/2;
        }
    }

    private void sink(int k) {
        while (2*k <= PQElementsNumber) {
            int j = 2*k;
            if (j < PQElementsNumber && greater(j, j+1)) j++;
            if (!greater(k, j)) break;
            exch(k, j);
            k = j;
        }
    }
 
}
