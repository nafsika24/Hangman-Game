package com.example.hangman_gui;

import java.util.HashMap;

public class Current_Word {
    /**
     *
     * @param len
     * @param current_word
     * @return
     */
    public static String Current_Word_Method(Integer len, HashMap<Integer, String> current_word ){
        String str = "";
        for(int i = 0; i < len; i++) {
            if (current_word.containsKey(i)) {
                str = str + current_word.get(i);
            } else {
                str = str + " __ ";
            }
        }
        return(str);

    }
}
