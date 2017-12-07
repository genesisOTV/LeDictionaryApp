package com.generic.dictionaryapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.PrintStream;
import java.io.PrintWriter;

public class AddWordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_word);
    }

    public void addNewEntry(View view) {
        //write to the vocab file
        EditText wordText = (EditText)findViewById(R.id.enterNewWord);
        EditText defnText = (EditText)findViewById(R.id.enterNewDefinition);

        String newWord = wordText.getText().toString();
        String newDefn = defnText.getText().toString();

        try {
            PrintStream output = new PrintStream(openFileOutput("added_words.txt", MODE_PRIVATE | MODE_APPEND));
            output.println(newWord + "\t" + newDefn);
            output.close();
            Toast toast = Toast.makeText(getApplicationContext(), "Successfully added new entry to dictionary!", Toast.LENGTH_SHORT);
            toast.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //go back to start menu activity
        finish();
    }
}
