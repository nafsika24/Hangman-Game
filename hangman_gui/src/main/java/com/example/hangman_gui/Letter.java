package com.example.hangman_gui;

import java.util.List;
import java.util.Map;

public class Letter {
    public static int Letter_Method(String letter, List<Map<Character,Integer>> possibilities, Integer len, Integer pos) {
        float possib = 0.0F;
        int point = 0;
        if(!possibilities.get(pos).containsKey(letter.charAt(0))){
            return 30;
        }
        int y = possibilities.get(pos).get(letter.charAt(0));
        possib = y * 100/ len.floatValue();

        if(possib >= 60){
            point = 5;
            return point;
        } else if (possib >= 40) {
            point = 10;
            return point;
        }
        else if(possib >= 25){
            point = 15;
            return point;
        }
        else {
            return 30;
        }
    }
}
