package com.company;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class TxtWriter {
    public static void FileWriterMethod(ArrayList<String> final_words, String dict_ID)
    {
        try {
            String str1 = "//medialab//";
            String str2 = "hangman_DICTIONARΥ-";
            String final_path = str1 + str2 + dict_ID + ".txt";

            java.io.FileWriter writer = new java.io.FileWriter(new File(".").getAbsolutePath()+final_path,true);
            for(String str: final_words) {
                writer.write(str + System.lineSeparator());
            }
            writer.close();
            System.out.println("The dictionary was successfully created into folder medialab.");
        }

        catch (IOException e) { System.out.println("Error at saving dictionary into .txt file"); }
        }
    }

