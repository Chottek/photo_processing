package pl.fox.photo;

import java.io.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ImageFileProcessor implements IProcessor {

    private final ImgReader imgReader;
    private final ConfigHandler configHandler;
    private final Handler handler;

    private ExecutorService pool; //for testing purposes only

    private int listSize;

    public ImageFileProcessor() {
        handler = new Handler(this);
        configHandler = new ConfigHandler();
        imgReader = new ImgReader(handler);
    }

    @Override
    public void process(int borderValue) {
        pool = Executors.newFixedThreadPool(configHandler.getMaxThreads());
        listSize = imgReader.getImages().size();
        for (File f : imgReader.getImages()) {
            Runnable r = new ProcessorThread(f, borderValue, configHandler.getOutputFolder(), handler);
            pool.execute(r);
        }
        pool.shutdown();
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
