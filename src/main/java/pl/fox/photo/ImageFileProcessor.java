package pl.fox.photo;

import java.io.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ImageFileProcessor implements IProcessor {

    private final ImgReader imgReader;
    private final ConfigHandler configHandler;
    private final Handler handler;

    public ImageFileProcessor() {
        handler = new Handler(this);
        configHandler = new ConfigHandler();
        imgReader = new ImgReader(handler);
    }

    @Override
    public void process(int borderValue) {
        ExecutorService pool = Executors.newFixedThreadPool(configHandler.getMaxThreads());
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
}
