package org.example;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

public class IsPrimeTask implements Runnable{
    private Stack<Long> source;
    private int currThread;
    private boolean end;
    private File storage;
    public static Set<Long> IsPrimary(Long num) {
        Set<Long> ret = new HashSet<>();
        for(Long i = 1L; i <= num; i++) {
            if(num % i == 0)
                ret.add(i);

        }
        return ret;
    }
    public IsPrimeTask(Stack<Long> _source, File _storage) {
        source  = _source;
        storage = _storage;
        end     = false;
    }
    public void run() {
        while(source.empty()) {
            //wait
            if (end)
                return;
        }


        Long num;
        synchronized (source) {
            num = source.pop();
        }

        Set<Long> d = new HashSet<>();
        synchronized(storage) {
            try {
                FileWriter writer = new FileWriter(storage, true);
                d = IsPrimary(num);
                writer.write("liczba " + num + ": " + d + "\n");
                writer.close();
            } catch(IOException e) {
                System.out.println("coś z plikiem zapisu");
            }
        }
        System.out.println("\nThread: " + Thread.currentThread().threadId() + " " + num + "->" + (d.size() == 2));

        run();
    }
    public void endTask() {
        end = true;
    }
}