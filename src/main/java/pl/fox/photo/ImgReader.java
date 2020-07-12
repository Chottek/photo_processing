package pl.fox.photo;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ImgReader {

    private Handler handler;

    private static final String[] EXTENSIONS = new String[]{ "png", "jpg", "jpeg" }; //handled extensions

    private static final FilenameFilter FILTER = (dir, name) -> { // overridden method filtering extensions
        for(String s : EXTENSIONS){
            if(name.endsWith("." + s)){
                return true;
            }
        }
        return false;
    };

    private List<File> images;

    public ImgReader(Handler handler) {
        this.handler = handler;
        images = new ArrayList<>();
        readPhotos(new File(handler.getProcessor().getConfigHandler().getInputFolder()));
    }

    public void readPhotos(File dir){ //Method for reading photos from given directory
        if(dir.isDirectory()){     // Checking if given path is a directory
            if(dir.listFiles().length > 0){   // Checking if given directory is not empty
                System.out.println("\nREADING DIRECTORY \""+ dir.getName() +"\"");

                images.addAll(Arrays.asList(dir.listFiles(FILTER))); // Reading images and adding them into List

                System.out.println("Got " + dir.listFiles(FILTER).length + " images");
            }else{
                System.err.println("Given directory \"" + dir.getName() + "\" is empty");
                System.exit(1);  // exit if input directory is empty
            }
        }else{
            System.err.println("Given directory \"" + dir.getName() + "\" doesn't exist");
            System.exit(1); // exit if input directory doesn't exist
        }
    }

    public List<File> getImages(){
        return images;
    }

}
