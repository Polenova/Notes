package ru.android.polenova;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.EmptyStackException;


public class SettingsActivity extends AppCompatActivity {

    private EditText editNewPin;
    private EditText editOldPin;
    private ImageButton btnEysNewPin;
    private ImageButton btnEysOldPin;
    private String stringNewPassword;
    private String stringOldPassword;
    private Keystore keystore = App.getKeystore();


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
        editNewPin = findViewById(R.id.editTextNewPin);
        editOldPin = findViewById(R.id.editTextOldPin);
        btnEysNewPin = findViewById(R.id.buttonEyeNewPin);
        btnEysOldPin = findViewById(R.id.buttonEyeOldPin);
        Button btnSavePin = findViewById(R.id.buttonSavePin);
        btnSavePin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                savePinFile();
            }
        });
        btnEysNewPin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setVisibleTextNewPin();
            }
        });
        btnEysOldPin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setVisibleTextOldPin();
            }
        });
    }

    private void setVisibleTextOldPin() {
        int typeNow = editOldPin.getInputType();
        if (typeNow != (InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD | InputType.TYPE_CLASS_NUMBER)) {
            btnEysOldPin.setImageResource(R.drawable.ic_remove_red_eye_black_24dp);
            editOldPin.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD | InputType.TYPE_CLASS_NUMBER);
            editOldPin.setSelection(editNewPin.length());
        } else {
            btnEysOldPin.setImageResource(R.drawable.ic_visibility_off_black_24dp);
            editOldPin.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD);
            editOldPin.setSelection(editNewPin.length());
        }
    }

    private void setVisibleTextNewPin() {
        int typeNow = editNewPin.getInputType();
        if (typeNow != (InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD | InputType.TYPE_CLASS_NUMBER)) {
            btnEysNewPin.setImageResource(R.drawable.ic_remove_red_eye_black_24dp);
            editNewPin.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD | InputType.TYPE_CLASS_NUMBER);
            editNewPin.setSelection(editNewPin.length());
        } else {
            btnEysNewPin.setImageResource(R.drawable.ic_visibility_off_black_24dp);
            editNewPin.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD);
            editNewPin.setSelection(editNewPin.length());
        }
    }

    private void savePinFile() {
        stringNewPassword = editNewPin.getText().toString();
        int count = stringNewPassword.length();
        boolean charBoolean = false;
        char[] chars = stringNewPassword.toCharArray();
        for (char element : chars) {
            charBoolean = Character.isLetter(element);
        }
        if (count < 4) {
            Toast.makeText(this, "введите 4 цифры", Toast.LENGTH_SHORT).show();
        } else {
            if (charBoolean == true) {
                Toast.makeText(this, "введите цифры", Toast.LENGTH_SHORT).show();
            } else {
                if (keystore.checkPin(stringNewPassword)) {
                    /*String stringInputOldPassword = editOldPin.getText().toString();
                    if ("".equals(stringInputOldPassword)) {
                        Toast.makeText(this, "введите текущий PIN", Toast.LENGTH_SHORT).show();
                    } else if (!stringInputOldPassword.equals(stringOldPassword)) {
                        Toast.makeText(this, "не верно введен PIN", Toast.LENGTH_SHORT).show();
                    } else {*/
                        keystore.saveNew(stringNewPassword);

                }
            }
        }
    }
}

/*if (charBoolean == true) {
        Toast.makeText(this, "введите цифры", Toast.LENGTH_SHORT).show();
        } else {
        try {
        stringOldPassword = FilePin.importPIN(this);
        } catch (EmptyStackException e) {
        e.getMessage();
        }
        if (stringOldPassword != null) {
        String stringInputOldPassword = editOldPin.getText().toString();
        if ("".equals(stringInputOldPassword)) {
        Toast.makeText(this, "введите текущий PIN", Toast.LENGTH_SHORT).show();
        } else if (!stringInputOldPassword.equals(stringOldPassword)) {
        Toast.makeText(this, "не верно введен PIN", Toast.LENGTH_SHORT).show();
        } else {
        FilePin.exportPIN(this, stringNewPassword);
        }
        } else {
        FilePin.exportPIN(this, stringNewPassword);
        }
        }
*/
