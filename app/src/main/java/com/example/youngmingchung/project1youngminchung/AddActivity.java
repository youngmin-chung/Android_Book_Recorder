package com.example.youngmingchung.project1youngminchung;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;


public class AddActivity extends AppCompatActivity {

    EditText editTextName;
    EditText editAuthorName;
    Spinner spinnerNum;
    TextView textViewDate;
    int counter = 0;
    String currentdate;
    Button buttonSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ArrayList<BookShelf> mBookShelfList;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        editTextName = findViewById(R.id.editTextName);
        editAuthorName = findViewById(R.id.editAuthorName);
        spinnerNum = findViewById(R.id.spinner);
        textViewDate = findViewById(R.id.textViewDate);
        buttonSave = findViewById(R.id.buttonSave);

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd G 'at' hh:mm:ss a zzz");
        currentdate = dateFormat.format(calendar.getTime());
        textViewDate.setText(currentdate);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //restore our shared_pref
        SharedPreferences settings = getSharedPreferences("datapersistance",
                Context.MODE_PRIVATE);
        editTextName.setText(settings.getString("title",""));
        editAuthorName.setText(settings.getString("author",""));
        spinnerNum.setSelection(settings.getInt("spinner",0));
    }

    @Override
    protected void onPause() {
        super.onPause();
        // save shared_pref
        SharedPreferences settings = getSharedPreferences("datapersistance",
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("title",editTextName.getText().toString());
        editor.putString("author",editAuthorName.getText().toString());
        editor.putInt("spinner",spinnerNum.getSelectedItemPosition());

        // write shared pref to file
        editor.apply();
    }

    // Save the name to file
    // Learning how to use JSON from INFO-3138 (write to JSON / delete from JSON)
    // Google has gson, so this app use JSON to save/delete record data
    public void onButtonSave(View view) {

        final Type listType = new TypeToken<List<BookShelf>>(){}.getType();
        List<BookShelf> bookList;
        {
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

            } catch (FileNotFoundException e) {
                bookList = new ArrayList<>();

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        OutputStreamWriter osw = null;
        try
        {
            BookShelf newRecord = new BookShelf();
            newRecord.setDate(textViewDate.getText().toString());
            newRecord.setTitle(editTextName.getText().toString());
            newRecord.setAuthor(editAuthorName.getText().toString());
            newRecord.setGenre(spinnerNum.getSelectedItem().toString());

            bookList.add(newRecord);

            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String jsonInString = gson.toJson(bookList);

            osw = new OutputStreamWriter(openFileOutput("book.json",MODE_PRIVATE));
            osw.write(jsonInString);
            osw.flush();
        }
        catch(IOException ioe)
        {
            ioe.printStackTrace();
            Toast.makeText(getApplicationContext(),"Error occurs", Toast.LENGTH_SHORT).show();
            return;
        }
        finally {
            if (osw != null){
                try {
                    osw.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        Toast.makeText(getApplicationContext(),"Book Added", Toast.LENGTH_SHORT).show();
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
        Intent reportIntent = new Intent(this, ReportActivity.class);
        startActivity(reportIntent);
    }

    public void onClickSetting(View view) {
        Intent aboutIntent = new Intent(getApplicationContext(), AboutActivity.class);
        startActivity(aboutIntent);
    }
}
