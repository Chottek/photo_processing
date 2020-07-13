package pl.fox.photo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ImageFileProcessor implements IProcessor{

    private static final Logger LOG = LoggerFactory.getLogger(ImageFileProcessor.class);

    private final Handler handler;
    private final ImgReader imgReader;
    private final ConfigHandler configHandler;


    public ImageFileProcessor(){
        handler = new Handler(this);
        configHandler = new ConfigHandler();
        imgReader = new ImgReader(handler);
    }

    @Override
    public void process(int borderValue){
        LOG.info("Border value was set to {} \n", borderValue);
        List<File> photos = imgReader.getImages();
        ExecutorService pool = Executors.newFixedThreadPool(configHandler.getMaxThreads());
        for(File f: photos){
           // new Thread(new ProcessorThread(f, borderValue, configHandler.getOutputFolder())).start();

            Runnable r = new ProcessorThread(f, borderValue, configHandler.getOutputFolder());
            pool.execute(r);
        }
        pool.shutdown();
    }

    public ConfigHandler getConfigHandler(){
        return configHandler;
    }

}
