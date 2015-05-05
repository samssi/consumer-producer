package fi.samssi.consumerproducer;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

public class FileWriter {
    private final int threadId;

    public FileWriter(int threadId) {
        this.threadId = threadId;
    }

    public void append(String message) {
        String fileName = new StringBuilder().append(String.valueOf(threadId)).append(".csv").toString();
        File file = new File(fileName);
        try {
            FileUtils.writeStringToFile(file, message + System.getProperty("line.separator"), true);
        } catch (IOException e) {
            System.out.println("Thread # " + threadId + " failed to write to it's file");
            e.printStackTrace();
        }
    }
}
