package com.company;

import java.util.ArrayList;
import java.util.HashMap;

public class Current_Word {
    public static void Current_Word_Method(Integer len,HashMap<Integer, String> current_word ){
        String str = "";
        for(int i = 0; i < len; i++) {
            if (current_word.containsKey(i)) {
                str = str + current_word.get(i);
            } else {
                str = str + "_";
            }
        }

        System.out.println();
        System.out.println("Missing Word: " + str);

    }
}
