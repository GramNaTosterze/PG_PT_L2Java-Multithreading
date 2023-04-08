package org.example;

import java.util.HashMap;
import java.util.Stack;

public class Main {
    public static void main(String[] args) {
        int[] source = {6007,	6011,	6029,	6037,	6043,	6047,	6053,	6067,	6073,	6079,	6089,	6091,	6101,	6113,	6121,	6131,	6133};
        Stack<Integer> sharedSource = new Stack<>();
        //shared hashmap
        HashMap<Integer, Boolean> sharedStorage = new HashMap<Integer, Boolean>();
        for (int s : source)
            sharedSource.add(s);



        //create threads
        int numOfThreads = 4;
        Thread[] threads = new Thread[numOfThreads];

        ThreadController controller = new ThreadController(threads, sharedSource, sharedStorage);
        Thread mainThread = new Thread(controller);
        mainThread.start();

        for(int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(new IsPrimeTask(sharedSource, sharedStorage));
            threads[i].start();
        }

        while(!sharedSource.empty() && threads != null) {
            //slowdown
        }
        System.out.println(sharedStorage);
        controller.stop();
    }

}