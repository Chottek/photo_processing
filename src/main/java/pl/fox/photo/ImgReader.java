package pl.fox.photo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ImgReader {

    private static final Logger LOG = LoggerFactory.getLogger(ImgReader.class);

    private static final String[] EXTENSIONS = new String[]{ "png", "jpg", "jpeg" }; //handled extensions

    private static final FilenameFilter FILTER = (dir, name) -> { // overridden method filtering extensions
        for(String s : EXTENSIONS){
            if(name.toUpperCase().endsWith("." + s.toUpperCase())){ //checking if any extension matches, ex. (jpg and JPG)
                return true;
            }
        }
        return false;
    };

    private List<File> images;

    public ImgReader(Handler handler) {
        images = new ArrayList<>();
        readPhotos(new File(handler.getImageFileProcessor().getConfigHandler().getInputFolder()));
    }

    public void readPhotos(File dir){ //Method for reading photos from given directory
        if(dir.isDirectory()){     // Checking if given path is a directory
            if(dir.listFiles().length > 0){   // Checking if given directory is not empty

                images.addAll(Arrays.asList(dir.listFiles(FILTER))); // Reading images and adding them into List

                LOG.info("Got {} images", dir.listFiles(FILTER).length);
            }else{
                LOG.error("Given directory \"{}\" is empty!", dir.getName());
                System.exit(1);  // exit if input directory is empty
            }
        }else{
            LOG.error("Given directory \"{}\" doesn't exist!", dir.getName());
            System.exit(1); // exit if input directory doesn't exist
        }
    }

    public List<File> getImages(){
        return images;
    }

}
