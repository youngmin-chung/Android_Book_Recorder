package com.example.youngmingchung.project1youngminchung;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    TextView currentBook;
    TextView textViewInspired;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // get the values from the bundle
        currentBook = findViewById(R.id.currentBook);
        String readingList = readBookList();
        currentBook.setText(readingList);
        textViewInspired = findViewById(R.id.textViewInspired);
    }

    // Learning how to use JSON from INFO-3138 (write to JSON / delete from JSON)
    // Google has gson, so this app use JSON to save/delete record data
    public String readBookList(){
        final Type listType = new TypeToken<List<BookShelf>>(){}.getType();
        String titleList = "";
        {
            List<BookShelf> bookList = new ArrayList<>();
            String str = "";

            try (InputStreamReader isr = new InputStreamReader(openFileInput("book.json"))){

                char[] inputBuffer = new char[100];

                int charRead;
                while ((charRead = isr.read(inputBuffer)) > 0) {
                    //copy
                    String readString = String.copyValueOf(inputBuffer, 0, charRead);
                    str += readString;
                }

                Gson gsonRead = new Gson();
                bookList = gsonRead.fromJson(str, listType);


                for (BookShelf r : bookList) {

                    titleList = "\"" + r.getTitle() + "\"";
                }

            } catch (FileNotFoundException e) {
                titleList = "No Record";


            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        if (titleList.equals(""))
        {
            titleList = "no book";
            return titleList;
        }
        else
            return titleList;
    }

    public void onClickHome(View view) {
        Intent homeIntent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(homeIntent);
    }

    public void onClickAdd(View view) {
        Intent addIntent = new Intent(getApplicationContext(), AddActivity.class);
        startActivity(addIntent);
    }

    public void onClickShelf(View view) {
        Intent reportIntent = new Intent(getApplicationContext(), ReportActivity.class);
        startActivity(reportIntent);
    }

    public void onClickSetting(View view) {
        Intent aboutIntent = new Intent(getApplicationContext(), AboutActivity.class);
        startActivity(aboutIntent);
    }
}

