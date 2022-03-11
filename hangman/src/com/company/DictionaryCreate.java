package com.company;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.net.*;
import java.io.InputStream;
import org.json.JSONObject;
import java.util.Random;

public class DictionaryCreate {
    // inputs the first token given
    public static String read_ids (String msg) {
        System.out.println(msg);
        System.out.print("ID: ");
        Scanner scanner = new Scanner(System.in);
        StringTokenizer tokens = new StringTokenizer(scanner.nextLine());
        return tokens.nextToken();
    }

    public static String check (String msg) {
        System.out.println(msg);
        System.out.print("Answer: ");
        Scanner scanner = new Scanner(System.in);
        StringTokenizer tokens = new StringTokenizer(scanner.nextLine());
        return tokens.nextToken();
    }

    public static void Dictionary_method() throws UndersizeException,InvalidCountExeception,NoDescriptionExeception,UnbalancedException
    {
        // give dictionary ID
        String output1 = "Please type a DICTIONARY ID - any alpha-arithmetic.";
        String dict_ID = read_ids(output1);

        // give OPEN LIBRARY ID
        String output2 = "Please type the OPEN LIBRARY ID you want.";
        String open_lib_ID = read_ids(output2);


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
                throw new UndersizeException("Size of dictionary must be more than 20 words!");
            }

            // duplicates
            Set<String> set = new HashSet<String>(final_words);
            final_words.clear();
            final_words.addAll(set);
             if(set.size() < final_words.size()) {
                 throw new InvalidCountExeception("Dictionary Should not have duplicates.");
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
                throw new UnbalancedException("Unbalanced Dictionary.");
            }

            System.out.print("Dictionary: ");
            System.out.println(final_words);
            TxtWriter.FileWriterMethod(final_words,dict_ID);

            System.out.println(" ");
            System.out.println("==============================================================");
            System.out.println(" ");

            String check = "Would you like to add a new dictionary? (yes/no)";
            String check2 = check(check);
            if(check2.equals("yes")){
                Dictionary_method();
            }
            System.out.println(" ");
            // choose file to play with
            System.out.println("Current Files");
            File directoryPath = new File("./medialab");
            //List of all files and directories
            String contents[] = directoryPath.list();
            for(int i=0; i<contents.length; i++) {
                System.out.println(contents[i]);
            }

        }
        catch (IOException e) { System.out.println("Error at Creating Dictionary"); }

    }

    }

