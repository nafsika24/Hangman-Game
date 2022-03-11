package com.company;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import static com.company.Current_Word.Current_Word_Method;
import static com.company.Possibility.Possibility_Method;
import static com.company.TerminalVisualization.TerminalVisualization_Method;
import static java.lang.Integer.parseInt;

public class Game {

    public static int correct_letter(String letter, List<Map<Character,Integer>> possibilities, Integer len, Integer pos) {
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
    public static String choose(String msg, String msg2) {
        System.out.println(msg);
        System.out.print(msg2);
        Scanner scanner = new Scanner(System.in);
        StringTokenizer tokens = new StringTokenizer(scanner.nextLine());
        return tokens.nextToken();
    }
    private static ArrayList<String> equal_characters(ArrayList<String> final_content, char charAt, int c) {
        ArrayList<String> final_res  = new ArrayList<String>();
        for(int i = 0; i < final_content.size(); i++){
            if(final_content.get(i).charAt(c) != charAt){
                final_res.add(final_content.get(i));
            }
        }
        return final_res;
    }

    public static void Game_Method() throws IOException, InvalidPosition {
        int tries_left = 6;
        int points = 0;
        int num_correct_letters = 0;
        HashMap<Integer, String> current_word = new HashMap<Integer, String>();


        // choose file to play
        String out1 = "Choose a file to play with from the above to play with";
        String res2 = choose(out1, "Filename: ");
        List<String> content = Files.readAllLines(Paths.get("./medialab/" + res2));

        // random choose word to play
        Random rand = new Random();
        String word = content.get(rand.nextInt(content.size()));
        ArrayList<String> final_content  = new ArrayList<String>();

        System.out.print("word: ");
        System.out.println(word);

        int len_word = word.length();


        // remove words that have not equal length from the set
        for (int i = 0; i < content.size(); i++) {
            if (content.get(i).length() == len_word && content.get(i) != word) {
                final_content.add(content.get(i));
            }
        }
        System.out.println(final_content);
        //List<String> playerGuesses = new ArrayList<String>();

        System.out.println("");
        System.out.println("==========================");
        System.out.println("LET THE HANGMAN GAME BEGIN");
        System.out.println("==========================");
        System.out.println();

        while (tries_left != 0){
            // check if the player has found the correct word
            if(current_word.size() == word.length()){
                System.out.println("Congrats!!! YOU FOUND THE CORRECT WORD!");
                return;
            }

            // check if the player has no points
            if(points < 0){
                points = 0;
            }
            // visualize hangman in terminal
            TerminalVisualization_Method(tries_left);
            System.out.println();

            System.out.println("You have " + tries_left + " wrong left chances and " + points + " points!");
            //System.out.println("Missing Word: ");

     /*       for (int i = 0; i < word.length(); i++) {
                if (playerGuesses.contains(String.valueOf(word.charAt(i)))) {
                    System.out.print(word.charAt(i));
                } else {
                    System.out.print("_");
                }
            }*/

            // show current word
            Current_Word_Method(len_word,current_word);
            System.out.println();
            System.out.println();
            System.out.print("Suggested Letters(Right -> Biggest occurrences): ");
            System.out.println();

            List<Map<Character,Integer>> possibilities = new ArrayList<Map<Character,Integer>>();

            // count possibilities for each letter
            // in every unknown position
            for (int i = 0; i < word.length(); i++) {
                if(!current_word.containsKey(i)){
                    System.out.println("Possition " + i + ":");
                    Map<Character, Integer> possiblities_result  = Possibility_Method(i, final_content);
                    possibilities.add(possiblities_result);
                }
                else{
                    // add empty list
                    Map <Character, Integer> hm = new HashMap<Character, Integer>();
                    possibilities.add(hm);
                }
            }

            String out2 = "Choose position to suggest.";
            String players_position = choose(out2, "Position: ");
            if (parseInt(players_position) > len_word) {
                throw new InvalidPosition("Invalid Position.");
            }
            String out3 = "Choose letter to suggest (uppercase).";
            String players_letter = choose(out3, "Letter: ");
            if (word.charAt(parseInt(players_position)) == players_letter.charAt(0)) {
                int pos;
                pos = parseInt(players_position);
                int gained = correct_letter(players_letter,possibilities, final_content.size(), pos );

                 // check for all the occurences of the found letter
                num_correct_letters += 1;
                points = points + gained;
                current_word.put(parseInt(players_position),players_letter);
                System.out.println("Congrats! You found the correct letter and gained " + gained + " points!");
            } else {
                points = points - 15;
                tries_left -=1;
                System.out.println("Wrong letter. You can try again.");
            }

            final_content = equal_characters(final_content,players_letter.charAt(0), Integer.parseInt(players_position));
            //playerGuesses.add(players_letter);
            System.out.println();
        }

        TerminalVisualization_Method(0);
        System.out.println("You are left out of tries! The word was " + word +". You can try to play again.");

    }

}