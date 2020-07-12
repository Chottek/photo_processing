package pl.fox.photo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class ConfigHandler {

    private Handler handler;

    private static final String FILENAME = "config.conf"; //default config file name
    private static final File CONFIG_FILE = new File(FILENAME);
    private String IN = "in"; //default image input directory
    private String OUT = "out"; //default image output directory

    public ConfigHandler(Handler handler) {
        this.handler = handler;

        createConf();
        readConf();
        checkOutputFolder();
    }

    private void readConf(){
        try{
            Scanner sc = new Scanner(CONFIG_FILE); //read config (set in and output folders to variables)
            String s;
            System.out.println("READING CONFIG");
            while (sc.hasNextLine()) {
                s = sc.nextLine();
                if(s.contains("IN:")){
                    IN = s.replace("IN:", "");
                    IN = IN.replaceAll(" ", ""); // make sure there are no whitespaces
                    System.out.println("Set input folder as: " + IN);
                }
                if(s.contains("OUT:")){
                    OUT = s.replace("OUT:", "");
                    OUT = OUT.replaceAll(" ", ""); // make sure there are no whitespaces
                    System.out.println("Set output folder as: "+ OUT);
                }
            }
        }catch(FileNotFoundException f){
            System.err.println("There was an error reading config file");
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
                    System.out.println("First line is not config, deleting");
                    Files.deleteIfExists(Paths.get(FILENAME)); //else -> delete file and proceed to creating new
                }
            }
            if (CONFIG_FILE.createNewFile()) { // if file doesn't exist, create a new default config file
                FileWriter myWriter = new FileWriter(FILENAME);
                myWriter.write("#CONFIG\n");
                myWriter.write("#Specify input and output directory here\n");
                myWriter.write("IN:" + IN + "\n");
                myWriter.write("OUT:"+ OUT + "\n");     //Write default values to config file
                myWriter.close();
                System.out.println("A default config file has been created " +
                        "with values:\n" +
                        "Input folder: " + IN + "\n" +
                        "Output folder: " + OUT + "\n");
                System.out.println("You can make changes to config or copy photos to input directory\n" +
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
                System.out.println("Directory \""+ out.getName() +"\" made");
            }
        }
    }

    public String getInputFolder(){
        return IN;
    }

    public String getOutputFolder(){
        return OUT;
    }

}
