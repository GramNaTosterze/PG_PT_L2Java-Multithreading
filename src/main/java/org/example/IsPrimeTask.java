package org.example;

import java.util.HashMap;
import java.util.Stack;

public class IsPrimeTask implements Runnable{
    private Stack<Integer> source;
    private int currThread;
    private boolean end;
    private HashMap<Integer, Boolean> storage;
    public static boolean IsPrimary(int num) {
        for(int i = 2; i < num; i++) {
            if(num % i == 0)
                return false;
        }
        return true;
    }
    public IsPrimeTask(Stack<Integer> _source, HashMap<Integer, Boolean> _storage) {
        source = _source;
        storage = _storage;
    }
    public void run() {
        int num = source.pop();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        synchronized(storage) {
            storage.put(num, IsPrimary(num));
        }
        System.out.println("\nThread: " + Thread.currentThread().threadId() + " " + num + "->" + storage.get(num));

        if(!source.empty()) run();
    }
}