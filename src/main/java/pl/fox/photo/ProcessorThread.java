package pl.fox.photo;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ProcessorThread implements Runnable {

    private File f;
    private int borderValue;
    private String outputFolder;

    public ProcessorThread(File f, int borderValue, String outputFolder) {
        this.f = f;
        this.borderValue = borderValue;
        this.outputFolder = outputFolder;
    }

    @Override
    public void run() {
        BufferedImage b;
        try {
            b = ImageIO.read(f); // reading BufferedImage from File path
        } catch (IOException e) {
            System.err.println("There was a problem reading " + f.getName());
            return;
        }

        long sum = 0;
        int iter = 0;
        for (int i = 0; i < b.getHeight() ; i+= 4) {  //iteration
            for (int j = 0; j < b.getWidth() ; j+= 4) { // based on sampling
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

    private void copy(File f, BufferedImage b, String db, int percentage){
        String name = getPureName(f.getName());  // get name without extension
        String extension = f.getName().substring(f.getName().lastIndexOf(".") + 1); // get extension
        try { // copy images to given output directory with suitable names
            ImageIO.write(b, extension, new File(outputFolder +
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
}
