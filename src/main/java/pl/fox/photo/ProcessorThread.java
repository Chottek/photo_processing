package pl.fox.photo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ProcessorThread implements Runnable {

    private static final Logger LOG = LoggerFactory.getLogger(ProcessorThread.class);

    private final File f;
    private final int borderValue;
    private final String outputFolder;
    private final Handler handler;

    public ProcessorThread(File f, int borderValue, String outputFolder, Handler handler) {
        this.f = f;
        this.borderValue = borderValue;
        this.outputFolder = outputFolder;
        this.handler = handler;
    }

    @Override
    public void run() {
        BufferedImage b;
        try {
            b = ImageIO.read(f); // reading BufferedImage from File path
        } catch (IOException e) {
            LOG.error("There was a problem reading {}", f.getName());
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

        handler.getImageFileProcessor().removeImage(f);

        LOG.info("Processed ({} of {}) -> {}",(Math.abs(handler.getImageFileProcessor().getFullListSize() - handler.getImageFileProcessor().getActiveListSize())),
                handler.getImageFileProcessor().getFullListSize(),f.getName());
    }

    private void copy(File f, BufferedImage b, String db, int percentage){
        String name = getPureName(f.getName());  // get name without extension
        String extension = f.getName().substring(f.getName().lastIndexOf(".") + 1); // get extension
        try {
            String pathname = outputFolder + // copy images to given output directory with suitable names
                    "/" + name +
                    "_" + db +
                    "_" + percentage +
                    "." + extension;

            ImageIO.write(b, extension, new File(pathname));
        } catch (IOException e) {
            LOG.error("There was a problem copying {} to output directory", f.getName());
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
