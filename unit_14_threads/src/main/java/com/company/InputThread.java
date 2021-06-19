package com.company;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class InputThread implements Runnable{

    private static final Logger logger = LoggerFactory.getLogger(InputThread.class);

    private FileWriter fileWriter;


    public InputThread(File file) throws IOException {
        fileWriter = new FileWriter(file);
    }

    @Override
    public synchronized void run(){
        try {
            String previous = "";

            while (true){
                String current = Main.input.toString();

                if(current.endsWith("quit")){
                    break;
                }

                if(!current.equals(previous)){
                    fileWriter.write(current);
                    fileWriter.flush();
                }

                previous = current;
                wait(1000);
            }

            fileWriter.close();
        } catch (IOException | InterruptedException e) {
            logger.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
