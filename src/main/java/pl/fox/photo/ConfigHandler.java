package pl.fox.photo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class ConfigHandler {

    private static final Logger LOG = LoggerFactory.getLogger(ConfigHandler.class);

    private static final String FILENAME = "config.conf"; //default config file name
    private static final File CONFIG_FILE = new File(FILENAME);
    private String IN = "in"; //default image input directory
    private String OUT = "out"; //default image output directory
    private int MAX_THREADS = 12; //default number of threads ran parallelly

    public ConfigHandler() {
        createConf();
        readConf();
        checkOutputFolder();
    }

    private void readConf(){
        try{
            Scanner sc = new Scanner(CONFIG_FILE); //read config (set in and output folders to variables)
            String s;
            LOG.info("READING CONFIG");
            while (sc.hasNextLine()) {
                s = sc.nextLine();

                if(s.startsWith("#")){
                    continue;
                }

                if(s.contains("IN:")){
                    IN = s.replace("IN:", "");
                    IN = IN.replaceAll(" ", ""); // make sure there are no whitespaces
                    LOG.info("Set input directory as {}", IN);
                }
                if(s.contains("OUT:")){
                    OUT = s.replace("OUT:", "");
                    OUT = OUT.replaceAll(" ", ""); // make sure there are no whitespaces
                    LOG.info("Set output directory as {}", OUT);
                }
                if(s.contains("MAX_THREADS:")){
                    MAX_THREADS = Integer.parseInt(s.replace("MAX_THREADS:", ""));
                    LOG.info("Set maximum threads to {}", MAX_THREADS);
                }
            }
        }catch(FileNotFoundException f){
            LOG.error("There was an error reading config file! Setting default values");
        }
    }

    private void createConf(){
        try {
            if(CONFIG_FILE.exists()){   //Check if file already exists
                String firstLine;
                try(Scanner fileReader = new Scanner(CONFIG_FILE)) {
                    firstLine = fileReader.nextLine();  //Get first line of the file to check if it's valid
                }
                if(!firstLine.equals("#CONFIG")){  //If first line equals "#CONFIG", the file is valid
                    LOG.error("First line is not config! Deleting");
                    Files.deleteIfExists(Paths.get(FILENAME)); //else -> delete file and proceed to creating new
                }
            }
            if (CONFIG_FILE.createNewFile()) { // if file doesn't exist, create a new default config file
                FileWriter myWriter = new FileWriter(FILENAME);
                myWriter.write("#CONFIG\n");
                myWriter.write("#Specify input and output directory here\n");
                myWriter.write("IN:" + IN + "\n");
                myWriter.write("OUT:"+ OUT + "\n");     //Write default values to config file
                myWriter.write("#\n#\n#Specify maximum threads to run during image processing\n");
                myWriter.write("MAX_THREADS:" + MAX_THREADS);
                myWriter.close();
                LOG.info("A default config file has been created " +
                        "with values:\n" +
                        "Input folder: " + IN + "\n" +
                        "Output folder: " + OUT + "\n");
                LOG.info("You can make changes to config or copy photos to input directory\n" +
                        "RESTART THE PROGRAM WHEN YOU'RE DONE");
                System.exit(0);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void checkOutputFolder(){
        File out = new File(OUT);
        if(!out.exists()){
            if(out.mkdirs()){
                LOG.info("Directory \"{}\" made", out.getName());
            }
        }
    }

    public String getInputFolder(){
        return IN;
    }

    public String getOutputFolder(){
        return OUT;
    }

    public int getMaxThreads() {
        return MAX_THREADS;
    }

}
