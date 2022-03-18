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
     * ckeck method is used to check if the id the user typed for the name of the dictionary contains only numbers or characters
     * @param s string input
     * @return   check method returns boolean (true/false)
     */

          public static boolean check(String s) {
                if (s == null) // checks if the String is null {
                    return false;
            int len = s.length();
              for (int i = 0; i < len; i++) {
                // checks whether the character is neither a letter nor a digit
                // if it is neither a letter nor a digit then it will return false
                if ((Character.isLetterOrDigit(s.charAt(i)) == false)) {
                    return false;
                }
            }
              return true;
        }

    /***
     * Dictionary_method is used for creating the dictionary according at user's ID and open library ID.
     * From the link https://openlibrary.org/books/" + open_lib_ID + ".json" we keep the content inside the description field.
     * After processing the description we end up having a dictionary inside the folder medialab that contains one word in each line.
     * @param dict_ID        any string the contains numbers and/or letters and is the ending of the name of the dictionary that is going to be created
     * @param open_lib_ID    a string that is an open library ID
     * @return               boolean, true if the dictionary was created successfully, false otherwise
     * @throws UndersizeException           exception that occurs when the size of the dictionary contains less than 20 words
     * @throws InvalidRangeException        exception that occurs when the dictionary has duplicates
     * @throws NoDescriptionException       InvalidRangeException when the return from the API Call has no description field
     * @throws UnbalancedException          InvalidRangeException when the dicitonary is unbalanced
     * @throws InvalidIDException           InvalidRangeException when the ID the user gave contains characters that are not letters or numbers
     */
    public static Boolean Dictionary_method(String dict_ID, String open_lib_ID ) throws UndersizeException, InvalidRangeException,NoDescriptionException,UnbalancedException,InvalidIDException
    {
        try {
            String url = "https://openlibrary.org/books/" + open_lib_ID + ".json";
            InputStream response = new URL(url).openStream();
            Scanner scanner = new Scanner(response);
            String responseBody = scanner.useDelimiter("\\A").next();

            // ckeck if there is discription field
            if(!responseBody.contains("description")){
                throw new NoDescriptionException("This dictionary contains no description field. Please choose another one.");
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
                throw new InvalidRangeException("Dictionary Should not have duplicates.");
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
            // check if the id the user gave contains only letters and/or digits
            if(!check(dict_ID)){
                throw new InvalidIDException("Invalid ID");
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


