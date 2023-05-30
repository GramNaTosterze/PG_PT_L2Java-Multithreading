package org.example;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Stack;

public class ThreadController implements Runnable{
    Thread[] threadList;
    IsPrimeTask[] tasks;
    Stack<Long> sharedSource;
    File sharedStorage;
    private boolean run;
    public ThreadController(int _numOfThreads, Stack<Long> _sharedSource, File _sharedStorage) {
        threadList = new Thread[_numOfThreads];
        tasks = new IsPrimeTask[_numOfThreads];
        sharedSource = _sharedSource;
        sharedStorage = _sharedStorage;
        run = true;
    }
    public void run() {
        for(int i = 0; i < threadList.length; i++) {
            tasks[i] = new IsPrimeTask(sharedSource, sharedStorage);
            threadList[i] = new Thread(tasks[i]);
            threadList[i].start();
        }
        Scanner scanner = new Scanner(System.in);
        while(run) {
            String line = scanner.nextLine();
            String[] command = line.split(" ");
            if(command[0].equals("new"))
                addThread();
            else if(command[0].equals("end"))
                stop();
            else if(command[0].equals("add")) {
                synchronized (sharedSource) {
                    sharedSource.add(Long.parseLong(command[1]));
                }
            }
        }
    }
    private void addThread() {
        System.out.println("added new Thread to queue\ncurrently running: "+(threadList.length+1));
        Thread[] newArr = new Thread[threadList.length + 1];
        IsPrimeTask[] newTasks = new IsPrimeTask[threadList.length + 1];
        for(int i = 0; i < threadList.length; i++) {
            newArr[i] = threadList[i];
            newTasks[i] = tasks[i];
        }

        newTasks[threadList.length] = new IsPrimeTask(sharedSource, sharedStorage);
        newArr[threadList.length] = new Thread(newTasks[threadList.length]);
        newArr[threadList.length].start();

        threadList = newArr;
        tasks = newTasks;
    }
    private void stopThreads() {
        for(IsPrimeTask task : tasks)
            task.endTask();

    }
    public void stop(){
        System.out.println("Closing app");
        sharedSource.clear();
        run = false;
        stopThreads();
    }
}