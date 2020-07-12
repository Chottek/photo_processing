package pl.fox.photo;


import java.awt.image.BufferedImage;
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
        System.out.println("Border value between bright and dark was set to " + borderValue);
        List<BufferedImage> photos = imgReader.getImages();
        for(BufferedImage b: photos){
            System.out.println(b.getWidth() + ", " + b.getHeight());

            long sum = 0;
            int iter = 0;
            for (int i = 0; i < b.getHeight() ; i+= 4) {
                for (int j = 0; j < b.getWidth() ; j+= 4) {
                   sum += calculateRGB(b.getRGB(j, i));
                   iter++;
                }
            }

            //765 - 100%
            // 99 - x %
            // x = (100 * 99) / 765

            System.out.println(100 - (100 * (sum / iter)) / 765 + "%");

        }
    }

    //TODO: Implement image copying with metadata


    private int calculateRGB(int pixel) {
//        int alpha = (pixel >> 24) & 0xff;
        int r = (pixel >> 16) & 0xff;
        int g = (pixel >> 8) & 0xff;
        int b = (pixel) & 0xff;

        return r + g + b;
//        return (int) Math.floor((r + g + b) / 3);
    }

    public ConfigHandler getConfigHandler(){
        return configHandler;
    }

}
