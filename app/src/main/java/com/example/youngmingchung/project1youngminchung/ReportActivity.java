package com.example.youngmingchung.project1youngminchung;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class ReportActivity extends AppCompatActivity {

    TextView textViewReading;
    TextView textViewMsg2;
    Button buttonDelAll;
    Button buttonDelOne;
    String readingList="";
    ScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        textViewReading = findViewById(R.id.textViewReading);
        textViewMsg2 = findViewById(R.id.textViewMsg2);
        readingList = readBookList();
        textViewReading.setText(readingList);
        buttonDelOne = findViewById(R.id.buttonDelOne);
        buttonDelAll = findViewById(R.id.buttonDelAll);
        scrollView = findViewById(R.id.scrollView);
    }

    // Learning how to use JSON from INFO-3138 (write to JSON / delete from JSON)
    // Google has gson, so this app use JSON to save/delete record data
    public String readBookList(){
        final Type listType = new TypeToken<List<BookShelf>>(){}.getType();
        readingList = "";

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

                int num = 1;

                for (BookShelf r : bookList) {

                    readingList += num + ". Date: " + r.getDate() + " \n    Title: " + r.getTitle() +
                            " \n    Author: " + r.getAuthor() + " \n    Genre: " + r.getGenre() + "\n";
                    num++;
                }
            } catch (FileNotFoundException e) {
                readingList = "No Record";

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return readingList;
    }

    public void onClickDelOne(View view) {

        readingList = deleteBookList();
        textViewReading.setText(readingList);
    }

    // Learning how to use JSON from INFO-3138 (write to JSON / delete from JSON)
    // Google has gson, so this app use JSON to save/delete record data
    // delete latest data here
    public String deleteBookList(){
        final Type listType = new TypeToken<List<BookShelf>>(){}.getType();
        readingList = "";

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

                int num = 1;

                if(!bookList.isEmpty()) {
                    bookList.remove((bookList.size() - 1));

                    OutputStreamWriter osw = null;
                    try {
                        Gson gson = new GsonBuilder().setPrettyPrinting().create();
                        String jsonInString = gson.toJson(bookList);

                        osw = new OutputStreamWriter(openFileOutput("book.json", MODE_PRIVATE));
                        osw.write(jsonInString);
                        osw.flush();
                        Toast.makeText(getApplicationContext(),"The latest book is deleted", Toast.LENGTH_SHORT).show();
                    } catch (IOException ioe) {
                        ioe.printStackTrace();
                        Toast.makeText(getApplicationContext(), "Error occurs", Toast.LENGTH_SHORT).show();

                    } finally {
                        if (osw != null) {
                            try {
                                osw.close();
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Bookshelf is empty. Grab a book, dude!!", Toast.LENGTH_SHORT).show();
                }

                for (BookShelf r : bookList) {
                    readingList += num + ". Date: " + r.getDate() + " / Title: " + r.getTitle() +
                            "\n    Author: " + r.getAuthor() + " / Genre: " + r.getGenre() + "\n";
                    num++;
                }

            } catch (FileNotFoundException e) {
                readingList = "No Record";

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return readingList;
    }
    public void onClickDelAll(View view) {
        readingList = deleteAllBookList();
        textViewReading.setText(readingList);
    }

    // Learning how to use JSON from INFO-3138 (write to JSON / delete from JSON)
    // Google has gson, so this app use JSON to save/delete record data
    // delete all data here
    public String deleteAllBookList(){
        final Type listType = new TypeToken<List<BookShelf>>(){}.getType();
        readingList = "";
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

                int num = 1;

                if(!bookList.isEmpty()) {
                    bookList.removeAll(bookList);

                    OutputStreamWriter osw = null;
                    try {
                        Gson gson = new GsonBuilder().setPrettyPrinting().create();
                        String jsonInString = gson.toJson(bookList);

                        osw = new OutputStreamWriter(openFileOutput("book.json", MODE_PRIVATE));
                        osw.write(jsonInString);
                        osw.flush();
                        Toast.makeText(getApplicationContext(),"All data are deleted", Toast.LENGTH_SHORT).show();
                    } catch (IOException ioe) {
                        ioe.printStackTrace();
                        Toast.makeText(getApplicationContext(), "Error occurs", Toast.LENGTH_SHORT).show();

                    } finally {
                        if (osw != null) {
                            try {
                                osw.close();
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Bookshelf is empty. Grab a book, dude!!", Toast.LENGTH_SHORT).show();
                }

                for (BookShelf r : bookList) {
                    readingList += num + ". Date: " + r.getDate() + "\n    Title: " + r.getTitle() +
                            "\n    Author: " + r.getAuthor() + "\n    Genre: " + r.getGenre() + "\n";
                    num++;
                }
            } catch (FileNotFoundException e) {
                readingList = "No Record";

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return readingList;
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
