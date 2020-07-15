package pl.fox.photo;

import java.io.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ImageFileProcessor implements IProcessor {

    private final ImgReader imgReader;
    private final ConfigHandler configHandler;
    private final Handler handler;

    private ExecutorService pool;

    private int listSize; //variable for getting a size of full images list

    public ImageFileProcessor() {
        handler = new Handler(this);
        configHandler = new ConfigHandler();
        imgReader = new ImgReader(handler);
    }

    @Override
    public void process(int borderValue) {
        pool = Executors.newFixedThreadPool(configHandler.getMaxThreads()); //initializing thread pool with maximum size (given in config)
        listSize = imgReader.getImages().size();  //getting full images list size
        for (File f : imgReader.getImages()) {    //iterating over all images in list
            Runnable r = new ProcessorThread(f, borderValue, configHandler.getOutputFolder(), handler);
            pool.execute(r); //starting new thread to process image
        }
        pool.shutdown(); //shutting down the pool when finished
    }

    public ConfigHandler getConfigHandler() {
        return configHandler;
    }

    public void removeImage(File f){
        imgReader.getImages().remove(f);
    }

    public ExecutorService getPool(){
        return pool;  // for testing purposes only
    }

    public int getActiveListSize(){
        return imgReader.getImages().size();
    }

    public int getFullListSize(){
        return listSize;
    }
}
