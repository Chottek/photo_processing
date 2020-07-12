package pl.fox.photo;

import java.io.*;
import java.util.List;

public class Processor {

    private Handler handler;
    private ImgReader imgReader;
    private ConfigHandler configHandler;

    public Processor(){
        handler = new Handler(this);
        configHandler = new ConfigHandler(handler);
        imgReader = new ImgReader(handler);
    }

    public void process(int borderValue){
        System.out.println("Border value between bright and dark was set to " + borderValue + "\n");
        List<File> photos = imgReader.getImages();
        for(File f: photos){
            ProcessorThread t = new ProcessorThread(f, borderValue, configHandler.getOutputFolder());
            t.run();
        }
    }

    public ConfigHandler getConfigHandler(){
        return configHandler;
    }

}
