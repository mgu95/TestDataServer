package pl.mgu95.testDataServer.tools;

import java.io.*;
import java.util.Scanner;

public class CallsCounter {

    public void countCall() {
        int calls = Integer.parseInt(getCallsFromFile());
        calls++;
        System.out.println("Calls made = " + calls);
        saveCallsToFile(calls);
    }

    private String getCallsFromFile() {
        try {
            File file = new File("calls.txt");
            Scanner myReader = new Scanner(file);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                return data;
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            File file = new File("calls.txt");
            try {
                file.createNewFile();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            System.out.println("An error occurred. Try Again!");
        }
        return null;
    }

    private void saveCallsToFile(int calls) {
        try {
            FileWriter fileWriter = new FileWriter("calls.txt");
            PrintWriter printWriter = new PrintWriter(fileWriter);
            printWriter.print(calls);
            printWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
