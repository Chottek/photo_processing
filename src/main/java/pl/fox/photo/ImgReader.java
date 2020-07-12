package pl.fox.photo;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ImgReader {

    private Handler handler;

    private static final String[] EXTENSIONS = new String[]{ "png", "jpg" }; //handled extensions

    private static final FilenameFilter FILTER = (dir, name) -> { //overriden method filtering extensions
        for(String s : EXTENSIONS){
            if(name.endsWith("." + s)){
                return true;
            }
        }
        return false;
    };

    private List<BufferedImage> images;

    public ImgReader(Handler handler) {
        this.handler = handler;
        images = new ArrayList<>();
        readPhotos(new File(handler.getProcessor().getConfigHandler().getInputFolder()));
    }

    public void readPhotos(File dir){ //Method for reading photos from given directory
        if(dir.isDirectory()){     // Checking if given path is a directory
            if(dir.listFiles().length > 0){   // Checking if given directory is not empty
                System.out.println("\nREADING DIRECTORY \""+ dir.getName() +"\"");
                for (File f : dir.listFiles(FILTER)){
                    try{
                        images.add(ImageIO.read(f)); // Reading images and adding them into List
                    }catch(IOException e){
                        System.err.println("There was a problem reading image " + f.getName());
                        System.exit(1);
                    }
                }
                System.out.println("Got " + dir.listFiles(FILTER).length + " images");
            }else{
                System.err.println("Given directory \"" + dir.getName() + "\" is empty");
                System.exit(1);
            }
        }else{
            System.err.println("Given directory \"" + dir.getName() + "\" doesn't exist");
            System.exit(1);
        }
    }


    public List<BufferedImage> getImages(){
        return images;
    }

}
