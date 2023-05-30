package org.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.Stack;

public class Main {
    public static void main(String[] args) {
        Stack<Long> sharedSource = ReadFile();
        File sharedStorage = new File("storage.txt");
        try {
            sharedStorage.createNewFile();
            FileWriter writer = new FileWriter(sharedStorage);
            writer.write("");
        } catch(IOException e) {
            System.out.println("problem z tworzeniem pliku");
        }

        //create threads
        int numOfThreads = 4;
        if(args.length >= 1)
            numOfThreads = Integer.parseInt(args[0]);

        ThreadController controller = new ThreadController(numOfThreads, sharedSource, sharedStorage);
        Thread mainThread = new Thread(controller);
        mainThread.start();
    }
    public static Stack<Long> ReadFile() {
        Stack<Long> numbers = new Stack<>();
        try {
            File file = new File("liczby.txt");
            Scanner reader = new Scanner(file);
            while(reader.hasNextLine()) {
                numbers.add(Long.parseLong(reader.nextLine()));
            }
        } catch (FileNotFoundException e){
            System.out.println("brak pliku");
        }
        return numbers;
    }
}