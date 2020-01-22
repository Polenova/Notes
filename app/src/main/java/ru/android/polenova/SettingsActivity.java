package ru.android.polenova;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;


public class SettingsActivity extends AppCompatActivity {

    private EditText editPin;
    private ImageButton btnEys;
    private Button btnSavePin;
    private String filePassword = "File_Password";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        this.setTitle("настройки");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initView();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            Intent intent = new Intent(SettingsActivity.this, ListNoteActivity.class);
            startActivity(intent);
            return false;
        }
        return true;
    }

    private void initView() {
        editPin = findViewById(R.id.editTextPin);
        btnEys = findViewById(R.id.buttonEye);
        btnSavePin = findViewById(R.id.buttonSavePin);
        btnSavePin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                savePinFile();
            }
        });
        btnEys.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setVisibleText();
            }
        });
    }

    private void setVisibleText() {
        int typeNow = editPin.getInputType();
        if (typeNow != (InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD | InputType.TYPE_CLASS_NUMBER)) {
            btnEys.setImageResource(R.drawable.ic_remove_red_eye_black_24dp);
            editPin.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD | InputType.TYPE_CLASS_NUMBER);
            editPin.setSelection(editPin.length());
        } else {
            btnEys.setImageResource(R.drawable.ic_visibility_off_black_24dp);
            editPin.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD);
            editPin.setSelection(editPin.length());
        }
    }

    private void savePinFile() {
        String stringPassword = editPin.getText().toString();
        int count = stringPassword.length();
        boolean charBoolean = false;
        char[] chars = stringPassword.toCharArray();
        for (char element: chars) {
            charBoolean = Character.isLetter(element);        }
        if (count < 4) {
            Toast.makeText(this, "введите 4 цифры", Toast.LENGTH_SHORT).show();
        } else {
            if (charBoolean == true) {
                Toast.makeText(this, "введите цифры", Toast.LENGTH_SHORT).show();
            } else {
                BufferedWriter bufferedWriter = null;
                try {
                    FileOutputStream fileOutputStreamLogin = openFileOutput(filePassword, MODE_PRIVATE);
                    OutputStreamWriter outputStreamWriterLogin = new OutputStreamWriter(fileOutputStreamLogin);
                    bufferedWriter = new BufferedWriter(outputStreamWriterLogin);
                    bufferedWriter.write(stringPassword);
                    bufferedWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Toast.makeText(this, "пароль сохранен", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
