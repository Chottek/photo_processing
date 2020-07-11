package pl.fox.photo;


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
        System.out.println("Border value between bright and dark was set to " + borderValue);
    }


    public ConfigHandler getConfigHandler(){
        return configHandler;
    }

}
