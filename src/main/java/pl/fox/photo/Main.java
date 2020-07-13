package pl.fox.photo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;

public class Main {

    private static final Logger LOG = LoggerFactory.getLogger(Main.class);
    private static final int LOWER_BORDER = 0;
    private static final int UPPER_BORDER = 100;

    public static void main(String[] args) {
        ImageFileProcessor p = new ImageFileProcessor();
        p.process(setBorderValue());
    }

    private static int setBorderValue(){
        Scanner sc = new Scanner(System.in);
        LOG.info("\nINPUT BORDER VALUE FOR BRIGHT AND DARK");
        while(true){
            String borderValue = sc.next();
            if(!borderValue.matches("\\d+") ||
                    Integer.parseInt(borderValue) <= LOWER_BORDER ||
                        Integer.parseInt(borderValue) > UPPER_BORDER){
                LOG.error("Invalid input!");
            }else{
                return Integer.parseInt(borderValue);
            }
        }
    }
}
