package org.example;

import java.util.HashMap;

public class IsPrimeThread implements Runnable{
    private int num;
    private int currThread;
    private HashMap<Integer, Boolean> storage;
    public static boolean IsPrimary(int num) {
        for(int i = 2; i < num; i++) {
            if(num % i == 0)
                return false;
        }
        return true;
    }
    public IsPrimeThread(int _num, HashMap<Integer, Boolean> _storage, int thread) {
        num = _num;
        storage = _storage;
        currThread = thread;
    }
    public void run() {
        synchronized(storage) {
            storage.put(num, IsPrimary(num));
        }
        System.out.println("\nThread: " + currThread + " " + num + "->" + storage.get(num));
    }
}
