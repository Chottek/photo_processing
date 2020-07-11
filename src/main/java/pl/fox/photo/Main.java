package pl.fox.photo;

import java.util.Scanner;

public class Main {

    private static final int LOWER_BORDER = 0;
    private static final int UPPER_BORDER = 100;

    public static void main(String[] args) {
        Processor p = new Processor();
        p.process(setBorderValue());
    }

    private static int setBorderValue(){
        Scanner sc = new Scanner(System.in);
        System.out.println("\nINPUT BORDER VALUE FOR BRIGHT AND DARK");
        while(true){
            String borderValue = sc.next();
            if(!borderValue.matches("\\d+") ||
                    Integer.parseInt(borderValue) <= LOWER_BORDER ||
                        Integer.parseInt(borderValue) > UPPER_BORDER){
                System.err.println("Invalid input!");
            }else{
                return Integer.parseInt(borderValue);
            }
        }
    }
}
