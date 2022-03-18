package com.example.hangman_gui;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class TxtWriter {
    public static void FileWriterMethod(ArrayList<String> final_words, String dict_ID)
    {
        try {
            String str1 = "//medialab//";
            String str2 = "hangman_";
            String final_path = str1 + str2 + dict_ID + ".txt";

            java.io.FileWriter writer = new java.io.FileWriter(new File(".").getAbsolutePath()+final_path,true);
            for(String str: final_words) {
                writer.write(str + System.lineSeparator());
            }
            writer.close();
        }

        catch (IOException e) { System.out.println("Error at saving dictionary into .txt file"); }
        }
    }

