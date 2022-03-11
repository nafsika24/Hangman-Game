package com.example.hangman_gui;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.net.*;
import java.io.InputStream;
import org.json.JSONObject;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;

public class DictionaryCreate {
    /**
     *
     * @param dict_ID
     * @param open_lib_ID
     * @return
     * @throws UndersizeException
     * @throws InvalidRangeExeception
     * @throws NoDescriptionExeception
     * @throws UnbalancedException
     */
    public static Boolean Dictionary_method(String dict_ID, String open_lib_ID ) throws UndersizeException, InvalidRangeExeception,NoDescriptionExeception,UnbalancedException
    {
        try { // GET Request to OPEN-LIBRARY for book ID
            String url = "https://openlibrary.org/books/" + open_lib_ID + ".json";
            InputStream response = new URL(url).openStream();
            Scanner scanner = new Scanner(response);
            String responseBody = scanner.useDelimiter("\\A").next();

            // ckeck if there is discription field
            if(!responseBody.contains("description")){
                throw new NoDescriptionExeception("This dictionary contains no description field. Please choose another one.");
            }
            JSONObject jsonObject =  new JSONObject(responseBody);
            String value = jsonObject.get("description").toString();

            // check if the field value is inside
            if (value.contains("value")){
                value = jsonObject.getJSONObject("description").getString("value");
            }

            // remove punctuation marks from result
            value = value.replaceAll("[^a-zA-Z0-9]", " ");

            // remove numbers
            value = value.replaceAll("\\d","");

            // convert string to an array of words
            String[] words = value.split("\\W+");

            ArrayList<String> final_words  = new ArrayList<String>();

            // save in a list only the words with length >= 6 in uppercase
            for (int i = 0; i < words.length; i++){
                if(words[i].length() >= 6 ){
                    final_words.add(words[i].toUpperCase());
                }
            }

            // ======== EXCEPTIONS ========

            // size
            int list_size = final_words.size();
            if(list_size < 20){
                Alert a1 = new Alert(AlertType.ERROR);
                a1.setHeaderText("Size of dictionary must be more than 20 words!");
                a1.showAndWait();
                throw new UndersizeException("Size of dictionary must be more than 20 words!");
            }

            // duplicates
            Set<String> set = new HashSet<String>(final_words);
            final_words.clear();
            final_words.addAll(set);
            if(set.size() < final_words.size()) {
                Alert a1 = new Alert(AlertType.ERROR);
                a1.setHeaderText("Error Dictionary Should not have duplicates.");
                a1.showAndWait();
                throw new InvalidRangeExeception("Dictionary Should not have duplicates.");
            }
            // unbalanced
            int counter = 0;
            for(int i = 0; i < final_words.size(); i++) {
                if (final_words.get(i).length() >= 9) {
                    counter += 1;
                }
            }
            int res = counter * 100 /final_words.size();
            if(res < 20){

                Alert a1 = new Alert(AlertType.ERROR);
                a1.setHeaderText("Error Unbalanced Dictionary.");
                a1.showAndWait();
                throw new UnbalancedException("Unbalanced Dictionary.");
            }

            TxtWriter.FileWriterMethod(final_words,dict_ID);

            // choose file to play with
            File directoryPath = new File("./medialab");
            //List of all files and directories
            String contents[] = directoryPath.list();
            return true;
        }
        catch (IOException e) { System.out.println("Error");
            return false;}

    }

}

