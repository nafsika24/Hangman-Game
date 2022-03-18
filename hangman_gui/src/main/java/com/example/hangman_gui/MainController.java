package com.example.hangman_gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.TilePane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import static com.example.hangman_gui.Current_Word.Current_Word_Method;
import static com.example.hangman_gui.DictionaryCreate.Dictionary_method;
import static com.example.hangman_gui.Letter.Letter_Method;
import static com.example.hangman_gui.Possibility.Possibility_Method;
import static java.lang.Integer.parseInt;

public class MainController  {

    private static ArrayList<String> equal_characters(ArrayList<String> final_content, char charAt, int c) {
        ArrayList<String> final_res  = new ArrayList<String>();
        for(int i = 0; i < final_content.size(); i++){
            if(final_content.get(i).charAt(c) == charAt){
                final_res.add(final_content.get(i));
            }
        }
        return final_res;
    }


    private static ArrayList<String> not_equal_characters(ArrayList<String> final_content, char charAt, int c) {
        ArrayList<String> final_res  = new ArrayList<String>(final_content);
        for(int i = 0; i < final_content.size(); i++){
            if(final_content.get(i).charAt(c) == charAt){
                final_res.remove(final_content.get(i));
            }
        }
        return final_res;
    }

    @FXML
    private Label welcomeText;
    @FXML private TextField ID_usr;
    @FXML private TextField ID_dict;
    @FXML private TextField idsearch;
    @FXML private TextField curr_word;
    @FXML private TextField tries_left_;
    @FXML private Label total_points;
    @FXML private Label suggested_letter;
    @FXML public TextField position;
    @FXML public TextField dictionary_load;
    @FXML private Label num_words;
    @FXML private Label percentage_xml;
    @FXML private Circle head;
    @FXML private Line hand1;
    @FXML private Line hand2;
    @FXML private Line body;
    @FXML private Line leg1;
    @FXML private Line leg2;
    @FXML private Label games_details;
    @FXML private Label total_games;

    private static int total_games_played = 0;


    @FXML Button btn1;
    @FXML TextField ID6;
    @FXML TextField ID7;
    @FXML TextField ID10;


    private int tries_left =6;
    private int total ;
    private int correct = 0;
    private float percentage = 0.0F;
    private static List<String> content ;
    private static List<List<String>> rounds_details = new ArrayList<>();
    private static String word;
    private static int points;
    private static ArrayList<String> final_content  = new ArrayList<String>();


    @FXML
        public void Create(ActionEvent actionEvent)throws IOException {
            Parent root = FXMLLoader.load(getClass().getResource("create_window.fxml"));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Create new Dictionary!");
            stage.setScene(scene);
            stage.show();
        }
          public void setOn() {
          }

          public void action(String word,List<Map<Character,Integer>> possibilities, int num_correct_letters,
                             HashMap<Integer, String> current_word ) throws  InvalidInputException, InvalidCharacter {
              // check if the input is valid
              if(!position.getText().contains(",")){
                  throw new InvalidInputException("Invalid Input.");
              }

              String[] res = position.getText().split(",");
              if(res.length!=2){
                  throw new InvalidInputException("Invalid Input.");
              }
              String players_position = res[0];
              if(!players_position.matches("\\d+")){
                  throw new InvalidInputException("Invalid Input.");
              }

              if(parseInt(players_position) > word.length()){
                  throw new InvalidInputException("Invalid Input.");
              }
              if(parseInt(players_position) < 0){
                  throw new InvalidInputException("Invalid Input.");
              }

              String players_letter = res[1];
              // check if provided letter is uppercase
              if(players_letter.length()!=1){
                  throw new InvalidCharacter("Not uppercase character");
              }
              if(!Character.isUpperCase(players_letter.charAt(0))){
                  throw new InvalidCharacter("Not uppercase character");
              }

                  if (word.charAt(parseInt(players_position)) == players_letter.charAt(0)) {
                      correct +=1;
                      total +=1;
                      int pos;
                      pos = parseInt(players_position);
                      int gained = Letter_Method(players_letter, possibilities, final_content.size(), pos);

                      // check for all the occurences of the found letter
                      num_correct_letters += 1;
                      points = points + gained;
                      current_word.put(parseInt(players_position), players_letter);
                      curr_word.setText(Current_Word_Method(word.length(), current_word));
                      final_content = equal_characters(final_content, players_letter.charAt(0), parseInt(players_position));
                      //System.out.println(final_content);


                  } else {
                      total +=1;
                      points = points - 15;
                      tries_left = tries_left - 1;
                      tries_left_.setText(String.valueOf(tries_left));
                      if(tries_left == 5){
                          head.setVisible(true);
                      }
                      else if(tries_left == 4){
                          body.setVisible(true);
                      }
                      else if(tries_left == 3){
                          hand1.setVisible(true);
                      }
                      else if (tries_left == 2){
                          hand2.setVisible(true);
                      }
                      else if (tries_left == 1){
                          leg1.setVisible(true);
                      }

                      else if (tries_left == 0){
                          leg2.setVisible(true);
                          List<String> roundxx = new ArrayList<>();
                          roundxx.add(word);
                          roundxx.add(String.valueOf(total));
                          roundxx.add("Computer");
                          rounds_details.add(roundxx);

                          Alert a1 = new Alert(AlertType.INFORMATION);
                          a1.setHeaderText("YOU LOST! THE CORRECT WORD WAS: " + word);
                          a1.showAndWait();
                          return;
                      }
                      final_content = not_equal_characters(final_content, players_letter.charAt(0), parseInt(players_position));
                      //System.out.println(final_content);

                  }
                  if (points < 0) {
                      points = 0;
                  }
                  total_points.setText(String.valueOf(points));
                  List<Set<Character>> possibilities_round = new ArrayList<>();

                  for (int i = 0; i < word.length(); i++) {
                      if (!current_word.containsKey(i)) {
                          Map<Character, Integer> possiblities_result = Possibility_Method(i, final_content);
                          possibilities_round.add(possiblities_result.keySet());
                          possibilities.add(possiblities_result);
                      } else {
                          // add empty list
                          Map<Character, Integer> hm = new HashMap<Character, Integer>();
                          possibilities.add(hm);
                      }
                  }
                  List<String> edit_suggestions = new ArrayList<>();
                  for (int i = 0; i < possibilities_round.size(); i++) {
                      String temp = String.valueOf(possibilities_round.get(i));
                      edit_suggestions.add("Position " + i + ": " + temp + "\n" + "\n");
                  }

                    suggested_letter.setText(String.valueOf(edit_suggestions).replaceAll(",", ""));
                    percentage = (correct*100)/total;
                    percentage_xml.setText(String.valueOf(percentage));


              if(current_word.size() == word.length()){
                  // add info of the current round
                  List<String> roundxx = new ArrayList<>();
                  roundxx.add(word);
                  roundxx.add(String.valueOf(total));
                  roundxx.add("Player");
                  rounds_details.add(roundxx);

                  Alert a1 = new Alert(AlertType.INFORMATION);
                  a1.setHeaderText("CONGRATS! YOU FOUND THE WORD.");
                  a1.showAndWait();
                  return;
              }
              }

    public void Start() throws IOException, InvalidInputException, noDictionaryException {
        if(content == null){
            Alert a1 = new Alert(AlertType.INFORMATION);
            a1.setHeaderText("There is no dictionary loaded.");
            a1.showAndWait();
            throw new noDictionaryException("There is no dictionary loaded.");
        }
        final_content.clear();
        position.setText("");
        total_games_played +=1;
        total_games.setText(String.valueOf(total_games_played));
        percentage_xml.setText("-");
        correct = 0;
        total = 0;
        points = 0;
        body.setVisible(false);
        hand1.setVisible(false);
        hand2.setVisible(false);
        leg1.setVisible(false);
        leg2.setVisible(false);

        num_words.setText(String.valueOf(content.size()));
        tries_left = 6;
        int num_correct_letters = 0;
        HashMap<Integer, String> current_word = new HashMap<Integer, String>();

        // randomly choose word to play
        Random rand = new Random();
         word = content.get(rand.nextInt(content.size()));
        //System.out.println(word);

        // initialize current word shown
        curr_word.setText(Current_Word_Method(word.length(), current_word));
        int len_word = word.length();

        // remove words that have not equal length from the set
        for (int i = 0; i < content.size(); i++) {
            if (content.get(i).length() == len_word && content.get(i) != word) {
                final_content.add(content.get(i));
            }
        }

             tries_left_.setText(String.valueOf(tries_left));
             total_points.setText(String.valueOf(0));

            List<Map<Character,Integer>> possibilities = new ArrayList<Map<Character,Integer>>();
            List<Set<Character>> possibilities_round = new ArrayList<>();

            // count possibilities for each letter
            // in every unknown position
            for (int i = 0; i < word.length(); i++) {
                if(!current_word.containsKey(i)){
                    Map<Character, Integer> possiblities_result  = Possibility_Method(i, final_content);
                    possibilities_round.add(possiblities_result.keySet());
                    possibilities.add(possiblities_result);
                }
                else{
                    // add empty list
                    Map <Character, Integer> hm = new HashMap<Character, Integer>();
                    possibilities.add(hm);
                }
            }
             List<String> edit_suggestions = new ArrayList<>();

            for(int i = 0; i < possibilities_round.size(); i++){
                String temp = String.valueOf(possibilities_round.get(i));
                edit_suggestions.add("Position "+ i + ": "+ temp + "\n" + "\n");
            }
                suggested_letter.setText(String.valueOf(edit_suggestions).replaceAll(",", ""));
                btn1.setOnAction(e -> {
                    try {
                        action(word,possibilities,num_correct_letters, current_word);
                    } catch ( InvalidInputException | InvalidCharacter ex) {
                        ex.printStackTrace();
                    }
                });

    }
    public void Load(ActionEvent actionEvent) throws IOException {
      Parent root = FXMLLoader.load(getClass().getResource("dictionary_search.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
       stage.setTitle("Load Dictionary");
       stage.setScene(scene);
       stage.show();

    }

    public void Exit(ActionEvent actionEvent) {
        Alert a1 = new Alert(AlertType.INFORMATION);
        a1.setHeaderText("See you next time!");
        a1.showAndWait();
        System.exit(0);
    }

    public void idCreate(MouseEvent mouseEvent) throws InvalidRangeException, UndersizeException, UnbalancedException, NoDescriptionException, InvalidIDException {
        String ID_file = ID_usr.getText();
        String ID_dictionary = ID_dict.getText();
        boolean creation = Dictionary_method(ID_file,ID_dictionary);

        if(creation){
            Alert a1 = new Alert(AlertType.INFORMATION);
            a1.setHeaderText("The dictionary was successfully created into folder medialab.");
            a1.showAndWait();
        }
        else{
            Alert a1 = new Alert(AlertType.INFORMATION);
            a1.setHeaderText("Something went wrong. The dictionary was not created. Try again.");
            a1.showAndWait();
        }

    }
    public void set(String s){
        dictionary_load.setText(s);
    }


    public void IDSearch(MouseEvent mouseEvent) throws IOException{
        try {
            String IDtosearch = idsearch.getText();
            String res3 = ".txt";
            content = Files.readAllLines(Paths.get("./medialab/hangman_"+IDtosearch+res3));

            Alert a1 = new Alert(AlertType.INFORMATION);
            a1.setHeaderText("Selected dictionary was set to default.");
            a1.showAndWait();

            return;

        }    catch (IOException e) {
            Alert a1 = new Alert(AlertType.ERROR);
            a1.setHeaderText("Something went wrong. Try again.");
            a1.showAndWait();
        }

    }
    public void DictionaryStatistics(ActionEvent actionEvent) throws IOException, noDictionaryException {
        Parent root = FXMLLoader.load(getClass().getResource("Dictionary_Details.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setTitle("Dictionary Details");
        stage.setScene(scene);
        stage.show();
    }
    public void ShowStatistics(MouseEvent mouseEvent) throws noDictionaryException {
        if(content == null){
        Alert a1 = new Alert(AlertType.ERROR);
        a1.setHeaderText("There is no dictionary loaded!\"");
        a1.showAndWait();
        throw new  noDictionaryException("There is no dictionary loaded!");
    }
        int counter6 = 0;
        int counter7_9 = 0;
        int counter10 = 0;
        int total = content.size();

        if (total == 0) {
            Alert a1 = new Alert(AlertType.INFORMATION);
            a1.setHeaderText("There is no dictionary loaded.");
            a1.showAndWait();
        } else {

            for (int i = 0; i < content.size(); i++) {
                if (content.get(i).length() == 6) {
                    counter6++;
                } else if (content.get(i).length() <= 9) {
                    counter7_9++;
                } else {
                    counter10++;
                }
            }
            float res1 = (counter6 * 100) / total;
            float res2 = (counter7_9 * 100) / total;
            float res3 = (counter10 * 100) / total;

            ID6.setText(String.valueOf(res1) + "%");
            ID7.setText(String.valueOf(res2) + "%");
            ID10.setText(String.valueOf(res3) + "%");

        }
    }
    public void Rounds(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("rounds.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setTitle("Rounds Details");
        stage.setScene(scene);
        stage.show();
    }

    public void ShowDetails(MouseEvent mouseEvent) {
        if(rounds_details.size() < 5){
            Alert a1 = new Alert(AlertType.INFORMATION);
            a1.setHeaderText("You should complete 5 games to see the details of the rounds.");
            a1.showAndWait();
        }

        List<String> det = new ArrayList<>();
        if(rounds_details.size() >0){
            for(int i = 0; i < rounds_details.size(); i++){
                String temp = rounds_details.get(i).get(0) + "             " + rounds_details.get(i).get(1) + "             " + rounds_details.get(i).get(2);
                det.add(temp + "\n" +"\n" +"\n" );
            }
            String temp1 = String.valueOf(det.subList(det.size()-5, det.size())).replaceAll("[\\[\\](){}] ", "");
            String temp2 = temp1.replaceAll(",", "");
            games_details.setText(temp2);
        }
    }

    public void Solution() throws InvalidInputException, IOException, noDictionaryException {
        suggested_letter.setText("");
        List<String> roundxx = new ArrayList<>();
        roundxx.add(word);
        roundxx.add(String.valueOf(total));
        roundxx.add("Computer");
        rounds_details.add(roundxx);
        Alert a1 = new Alert(AlertType.INFORMATION);
        a1.setHeaderText("YOU LOST! THE CORRECT WORD WAS: " + word);
        a1.showAndWait();

    }


}