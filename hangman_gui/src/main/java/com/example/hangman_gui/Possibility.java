package com.example.hangman_gui;

import java.util.*;

public class Possibility {
    // function to sort hashmap by values
    public static HashMap<Character, Integer> sortByValue(HashMap<Character, Integer> hm)
    {
        // Create a list from elements of HashMap
        List<Map.Entry<Character, Integer> > list =
                new LinkedList<Map.Entry<Character, Integer> >(hm.entrySet());

        // Sort the list
        Collections.sort(list, new Comparator<Map.Entry<Character, Integer> >() {
            public int compare(Map.Entry<Character, Integer> o1,
                               Map.Entry<Character, Integer> o2)
            {
                return (o1.getValue()).compareTo(o2.getValue());
            }
        });

        // put data from sorted list to hashmap
        HashMap<Character, Integer> temp = new LinkedHashMap<Character, Integer>();
        for (Map.Entry<Character, Integer> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
        }
        return temp;
    }

    public static HashMap<Character, Integer> countChars(String str){
        HashMap<Character, Integer> occurencies = new HashMap<Character, Integer>();
        char[] strArr;
        while(str.length() != 0){
            strArr = str.toCharArray();
            char ch = strArr[0];
            int count = 1;
            for(int i = 1; i < strArr.length; i++){
                if(ch == strArr[i]){
                    count++;
                }
            }
            // We don't need to count spaces
            if(((ch != ' ') && (ch != '\t'))){
                occurencies.put(ch,count);
            }
            // replace all occurrence of the character
            // which is already iterated and counted
            str = str.replace(""+ch, "");
        }
        return occurencies;
    }
    public static Map<Character, Integer> Possibility_Method(Integer x, ArrayList<String> words) {
        String new_word = "";
        for(int i = 0; i < words.size(); i++){
            String w = words.get(i);
            String letter = String.valueOf(w.charAt(x));
            new_word = new_word + letter;
        }
        HashMap<Character, Integer> occurencies = new HashMap<Character, Integer>();
        occurencies = countChars(new_word);
        Map<Character, Integer> hm1 = sortByValue(occurencies);
        return hm1;
    }
}
