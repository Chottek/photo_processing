package pl.fox.photo;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
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

            BufferedImage b;
            try {
                b = ImageIO.read(f); // reading BufferedImage from File path
            } catch (IOException e) {
                System.err.println("There was a problem reading " + f.getName());
                continue;
            }

            long sum = 0;
            int iter = 0;
            for (int i = 0; i < b.getHeight() ; i+= 4) {
                for (int j = 0; j < b.getWidth() ; j+= 4) {
                   sum += calculateRGB(b.getRGB(j, i));
                   iter++;
                }
            }

            // 765 - 100%  <- (255 * 3)
            // 99 - x %
            // x = (100 * 99) / 765

            int percentage = (int) (100 - (100 * (sum / iter)) / 765);

            if(percentage > borderValue){
                copy(f, b, "dark", percentage);
            }else{
                copy(f, b, "bright", percentage);
            }

            System.out.println("Processed " + f.getName());
        }
    }


    private void copy(File f, BufferedImage b, String db, int percentage){
        String name = getPureName(f.getName());  // get name without extension
        String extension = f.getName().substring(f.getName().lastIndexOf(".") + 1); // get extension
        try { // copy images to given output directory with suitable names
            ImageIO.write(b, extension, new File(configHandler.getOutputFolder() +
                                    "/" + name + "_" + db + "_" + percentage + "." + extension));
        } catch (IOException e) {
            System.err.println("There was a problem copying file " + f.getName() + " to output directory");
        }
    }

    private String getPureName(String s){
        int pos = s.lastIndexOf(".");
        return s.substring(0, pos);
    }

    private int calculateRGB(int pixel) {
        int r = (pixel >> 16) & 0xff;
        int g = (pixel >> 8) & 0xff;
        int b = (pixel) & 0xff;

        return r + g + b;
    }

    public ConfigHandler getConfigHandler(){
        return configHandler;
    }

}
