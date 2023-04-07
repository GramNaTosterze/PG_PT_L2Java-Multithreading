package org.example;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Stack;
public class Main {
    public static void main(String[] args) {
        //naiwne sprawdzanie czy 2*i+1 to liczba piewsza w wątkach
        Integer[] source = {6007,	6011,	6029,	6037,	6043,	6047,	6053,	6067,	6073,	6079,	6089,	6091,	6101,	6113,	6121,	6131,	6133};
        Stack<Integer> sharedSource = new Stack<Integer>();
        sharedSource.addAll(Arrays.asList(source));

        //shared hashmap
        HashMap<Integer, Boolean> sharedStorage = new HashMap<Integer, Boolean>();
        //create threads
        int numOfThreads = 4;
        Thread[] threads = new Thread[numOfThreads];


        while(!sharedSource.empty()) {
            for(int i = 0; i < threads.length; i++) {
                if (threads[i] == null || !threads[i].isAlive()) {
                    threads[i] = new Thread(new IsPrimeThread(sharedSource.pop(), sharedStorage, i));
                    threads[i].start();
                    break;
                }
            }
        }
        System.out.println(sharedStorage);

    }

}