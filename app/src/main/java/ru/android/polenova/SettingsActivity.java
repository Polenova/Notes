package ru.android.polenova;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;


public class SettingsActivity extends AppCompatActivity {

    private EditText editNewPin;
    private ImageButton btnEysNewPin;
    private String stringNewPassword;
    private String pinOff = "pinOff";

    private Keystore keystore = App.getKeystore();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UtilsSpinner.onActivityCreateSetTheme(SettingsActivity.this);
        setContentView(R.layout.activity_settings);
        this.setTitle(R.string.title_setting);
        initView();
    }

    // Кнопки ********

    private void initView() {
        editNewPin = findViewById(R.id.editTextNewPin);
        btnEysNewPin = findViewById(R.id.buttonEyeNewPin);
        findViewById(R.id.buttonSavePin).setOnClickListener(new View.OnClickListener() {
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

    // Сохранение ********

    private void savePinFile() {
        stringNewPassword = editNewPin.getText().toString();
        int count = stringNewPassword.length();
        if (count < 4) {
            Toast.makeText(this, R.string.toast_enter4, Toast.LENGTH_SHORT).show();
        } else {
            keystore.saveNew(stringNewPassword);
            startActivity(new Intent(SettingsActivity.this, ListNoteActivity.class));
        }
    }
}

