package ru.android.polenova;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private ArrayList<String> textList = new ArrayList<>();
    private String fileNote = "notes";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btn = (findViewById(R.id.button2));
        Button btn2 = (findViewById(R.id.buttonNote));
        TextView textView = findViewById(R.id.textView);
        BufferedReader bufferedReaderNameNote = null;
        try {
            bufferedReaderNameNote = new BufferedReader(new InputStreamReader(openFileInput(fileNote)));
            String txt = bufferedReaderNameNote.readLine();
            while (txt != null){
                textList.add(txt);
                txt = bufferedReaderNameNote.readLine();
            }
            bufferedReaderNameNote.close();
            textView.setText(textList.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }



        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NewNoteActivity.class);
                startActivity(intent);
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ListNoteActivity.class);
                startActivity(intent);
            }
        });
    }
}
