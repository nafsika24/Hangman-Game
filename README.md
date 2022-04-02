# Hangman Game

## About the Project
Hangman game Project for NTUA-ECE Cource Multimedia Technology - 9th Semester 

![alt text](https://github.com/nafsika24/Hangman-Game/blob/main/MediaLab%20Screenshots/mainpage.png)

## Description
In the game, a word is randomly chosen from a dictionary(the user has created). The user should insert in each round the guessed position and letter. At the right of the page there is a list with suggested letters for each position. These suggested letters are a result of theis occurrences at the same position in the other words of the dictionary. This list is updated in each round. You allowed to make 5 wrong suggestions. After each round there is a pop up window with the result.

## Steps
1) Dictionary Create: Navigate at Application -> Create and add a desired name for your new dictionay. Then add the ID of the book from the OpenLibrary Site you want to use for the dictionary. In the uploaded project in the folder medialab there are two dictionaries with IDS 1 and 2.
2) Dictionary Load: Navigate at Application -> Load and add the desired ID of your dictionary to be loaded (ex. 1 or 2).
3) Start Game: Navigate at Application -> Start. Now a word is randomly chosen from the dictionary you enabled and you can start the game.
4) If you loose, the hidden word will be revealed.
5) Dictionary Statistics: Navigate at Details -> Dictionary to see statistics about the words included.
6) Rounds Statistics: After you have played at lest 5 rounds, you can navigate at Details -> Rounds and see statistics of your last 5 games.
7) Solution: If you want to end the game and see the solution you can navigate at  Details -> Solution. Automatically the computer will be the winner.
8) Exit: Navigate at Application -> Exit to exit the game.

## Built with
* [Java](https://www.java.com/en/)
* [JavaFX](https://openjfx.io/)

## Getting Started

To run the project, first you should configure JavaFx and SceneBuilder in your IDE and add a json package in the libraries.
The project was developed in IntelliJ IDEA. For the configuration you can follow these steps:

* https://www.jetbrains.com/help/idea/javafx.html
* https://www.jetbrains.com/help/idea/opening-fxml-files-in-javafx-scene-builder.html

## Contact
nafsika.abatzi@gmail.com
