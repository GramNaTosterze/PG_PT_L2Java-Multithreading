package org.example;

import java.util.HashMap;
import java.util.Scanner;
import java.util.Stack;

public class ThreadController implements Runnable{
    Thread[] threadList;
    Stack<Integer> sharedSource;
    HashMap<Integer, Boolean> sharedStorage;
    private boolean run;
    public ThreadController(Thread[] _threadList, Stack<Integer> _sharedSource, HashMap<Integer, Boolean> _sharedStorage) {
        threadList = _threadList;
        sharedSource = _sharedSource;
        sharedStorage = _sharedStorage;
        run = true;
    }
    public void run() {
        Scanner scanner = new Scanner(System.in);
        while(run) {
            String line = scanner.nextLine();
            if(line.equals("new"))
                addThread();
            else if(line.equals("end"))
                stop();
        }
    }
    private void addThread() {
        System.out.println("added new Thread to queue\ncurrently running: "+(threadList.length+1));
        Thread[] newArr = new Thread[threadList.length + 1];
        for(int i = 0; i < threadList.length; i++)
            newArr[i] = threadList[i];

        newArr[threadList.length] = new Thread(new IsPrimeTask(sharedSource, sharedStorage));
        newArr[threadList.length].start();

        threadList = newArr;

    }
    private void stopThreads() throws InterruptedException {
        //for(IsPrimeThread thread : threadList)
            //thread.stop();

    }
    public void stop(){
        System.out.println("Closing app");
        sharedSource.clear();
        run = false;
    }
}