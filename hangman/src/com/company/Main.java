package com.company;

import java.io.IOException;


public class Main {


    public static void main(String[] args) throws UndersizeException, InvalidCountExeception, NoDescriptionExeception, UnbalancedException, IOException, InvalidPosition {

        // create dictionaries
        DictionaryCreate.Dictionary_method();
        // begin game
        Game.Game_Method();

    }
}
