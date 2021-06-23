package com.company;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class InputThread implements Runnable{

    private static final Logger logger = LoggerFactory.getLogger(InputThread.class);

    private FileWriter fileWriter;

    private final File file;


    public InputThread(File file) throws IOException {
        this.file = file;
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
                    try {
                        fileWriter = new FileWriter(this.file);
                        fileWriter.write(current);
                        fileWriter.flush();
                    } catch (IOException e){
                        logger.error("Unable to write to file", e);
                        throw new RuntimeException(e);
                    }
                }

                previous = current;
                wait(1000);
            }
        } catch (InterruptedException e) {
            logger.error("Was interrupted", e);
            throw new RuntimeException(e);
        }
    }
}
