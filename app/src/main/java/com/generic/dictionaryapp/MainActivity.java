package com.generic.dictionaryapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.TreeMap;

public class MainActivity extends AppCompatActivity {

    /*private static final String[] words = {
            "mediocre", "blah",
            "underachiever", "blahblah",
            "jerk", "you",
            "defenestrate", "to throw out of a window"
    };*/

    private Map<String, String> dictionary;
    private List<String> wordsList;
    private ListView list;
    private TextView displayWord;

    private void readFile()
    {
        try {
            Scanner scan = new Scanner(getResources().openRawResource(R.raw.words));
            while (scan.hasNextLine()) {
                String line = scan.nextLine();
                String[] parts = line.split("\t");
                dictionary.put(parts[0], parts[1]);
                wordsList.add(parts[0]);
            }

            Scanner scan2 = new Scanner(openFileInput("added_words.txt"));
            while (scan2.hasNextLine()) {
                String line = scan2.nextLine();
                String[] parts = line.split("\t");
                dictionary.put(parts[0], parts[1]);
                wordsList.add(parts[0]);
            }
        } catch (Exception ioe){
            //No file created
        }
    }

    private void chooseTheWord()
    {
        Random randy = new Random();
        int randomIndex = randy.nextInt(wordsList.size());
        //pick random word
        String theWord = wordsList.get(randomIndex);
        //get correct definition for random word
        String correctDefinition = dictionary.get(theWord);

        //pick 4 other(wrong) definitions at random
        List<String> wrongDefinitions = new ArrayList<>(dictionary.values());
        //Remove correct definition and shuffle
        wrongDefinitions.remove(correctDefinition);
        Collections.shuffle(wrongDefinitions);
        //"Draw" the first 4 definitions and create a new list with them
        wrongDefinitions = wrongDefinitions.subList(0, 4);
        //Add back the correct definition and shuffle the "deck"
        wrongDefinitions.add(correctDefinition);
        Collections.shuffle(wrongDefinitions);

        displayWord.setText(theWord);

        //list = (ListView) findViewById(R.id.wordList);
        //Converts(adapts) raw data into list items
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, //activity
                android.R.layout.simple_list_item_1, //layout
                wrongDefinitions//raw list data source
        );
        list.setAdapter(adapter);
    }

    //Use for initialization
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dictionary = new HashMap<>();
        wordsList = new ArrayList<>();
        readFile();

        list = (ListView) findViewById(R.id.wordList);
        displayWord = (TextView) findViewById(R.id.displayWord);

        chooseTheWord();

        /*dictionary = new HashMap<>();
        //Convert array into map(shitty java version of a dictionary); Keys are words, values are definitions
        for(int i = 0; i < words.length; i+=2)
        {
            dictionary.put(words[i], words[i+1]);
        }*/

        //Converts(adapts) raw data into list items
        /*ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, //activity
                android.R.layout.simple_list_item_1, //layout
                new ArrayList<String>(dictionary.keySet())//raw list data source
        );
        list.setAdapter(adapter);*/

        //Set onClick handler
        list.setOnItemClickListener((parent, view, position, id) -> {
                String selectedDefinition = parent.getItemAtPosition(position).toString();
                String correctDefiniton = dictionary.get(displayWord.getText().toString());

                if(selectedDefinition == correctDefiniton)
                {
                    Toast toast = Toast.makeText(getApplicationContext(), "Correct!", Toast.LENGTH_SHORT);
                    toast.show();
                }
                else
                {
                    Toast toast = Toast.makeText(getApplicationContext(), "Incorrect, try again", Toast.LENGTH_SHORT);
                    toast.show();
                }
                /*String definition = dictionary.get(word);
                Toast toast = Toast.makeText(getApplicationContext(), definition, Toast.LENGTH_SHORT);
                toast.show();*/

                chooseTheWord();
            }
        );
    }
}
