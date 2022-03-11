package com.company;

import java.io.IOException;

public class TerminalVisualization {
    public static void TerminalVisualization_Method(Integer num) {
        if(num == 6){
            System.out.println("------------------------");
            return;
        }
        else if(num == 5){
            System.out.println("------------------------");
            System.out.println("          O");
            return;
        }
        else if(num == 4){
            System.out.println("------------------------");
            System.out.println("          O");
            System.out.println("          |");
            return;
        }
        else if(num == 3){
            System.out.println("------------------------");
            System.out.println("          O");
            System.out.println("         /|");
            return;
        }

        else if(num == 2){
            System.out.println("------------------------");
            System.out.println("          O");
            System.out.println("         /|\\");
            return;
        }
        else if(num == 1){
            System.out.println("------------------------");
            System.out.println("          O");
            System.out.println("         /|\\");
            System.out.println("         /");
            return;
        }
        else if(num == 0){
            System.out.println("------------------------");
            System.out.println("------------------------");
            System.out.println("          O");
            System.out.println("         /|\\");
            System.out.println("         /\\");
            return;
        }




    }
}
