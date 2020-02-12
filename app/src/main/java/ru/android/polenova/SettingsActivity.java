package ru.android.polenova;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;


public class SettingsActivity extends AppCompatActivity {

    private EditText editNewPin;
    private EditText editOldPin;
    private ImageButton btnEysNewPin;
    private ImageButton btnEysOldPin;
    private String stringNewPassword;
    private CheckBox checkOffPin;
    private SharedPreferences sharedPrefs;

    public static final String myPrefs = "myPrefs";
    public static final String nameKey = "nameKey";
    private String pinOff = "pinOff";

    private Keystore keystore = App.getKeystore();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        this.setTitle(R.string.title_setting);
        sharedPrefs = getSharedPreferences(myPrefs, MODE_PRIVATE);
        initView();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            if (keystore.hasPin()) {
                if (!checkOffPin.isChecked() && keystore.checkPin(pinOff)) {
                    Toast.makeText(SettingsActivity.this, R.string.toast_addPin_offPin, Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(SettingsActivity.this, ListNoteActivity.class);
                    startActivity(intent);
                }
            }
        }
        return true;
    }

    private void initView() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        editNewPin = findViewById(R.id.editTextNewPin);
        editOldPin = findViewById(R.id.editTextOldPin);
        btnEysNewPin = findViewById(R.id.buttonEyeNewPin);
        btnEysOldPin = findViewById(R.id.buttonEyeOldPin);
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
        btnEysOldPin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setVisibleTextOldPin();
            }
        });
        checkOffPin = findViewById(R.id.checkBoxOffPIN);
        if (sharedPrefs.contains(nameKey)) {
            checkOffPin.setChecked(sharedPrefs.getBoolean(nameKey, false));
        }
        checkOffPin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    switchOffPin();
                } else {
                    checkOffPin.setChecked(false);
                    setSharedPreferences();
                }
            }
        });
    }

    public void switchOffPin() {
        if (keystore.hasPin()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(SettingsActivity.this);
            final EditText input = new EditText(SettingsActivity.this);
            input.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD);
            input.setFilters(new InputFilter[]{new InputFilter.LengthFilter(4)});
            builder.setTitle(R.string.dialog_OffPin)
                    .setCancelable(false)
                    .setView(input)
                    .setPositiveButton(R.string.dialog_OK, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String string = input.getText().toString();
                            String inputString = string.replaceAll("[,]", "").toString();
                            if (keystore.checkPin(inputString)) {
                                checkOffPin.setChecked(true);
                                editNewPin.getText().clear();
                                editOldPin.getText().clear();
                                keystore.saveNew(pinOff);
                                Toast.makeText(SettingsActivity.this, R.string.toast_clear, Toast.LENGTH_SHORT).show();
                                onRestart();
                            } else {
                                checkOffPin.setChecked(false);
                                Toast.makeText(SettingsActivity.this, R.string.toast_error_PIN, Toast.LENGTH_SHORT).show();
                            }
                        }
                    })
                    .setNegativeButton(R.string.dialog_cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            checkOffPin.setChecked(false);
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
        } else {
            checkOffPin.setChecked(true);
            keystore.saveNew(pinOff);
            Toast.makeText(SettingsActivity.this, R.string.toast_saved_PINOff, Toast.LENGTH_SHORT).show();
        }
        setSharedPreferences();
    }

    private void setSharedPreferences() {
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.putBoolean(nameKey, checkOffPin.isChecked()).commit();
    }

    private void setVisibleTextOldPin() {
        int typeNow = editOldPin.getInputType();
        if (typeNow != (InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD | InputType.TYPE_CLASS_NUMBER)) {
            btnEysOldPin.setImageResource(R.drawable.ic_remove_red_eye_black_24dp);
            editOldPin.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD | InputType.TYPE_CLASS_NUMBER);
            editOldPin.setSelection(editOldPin.length());
        } else {
            btnEysOldPin.setImageResource(R.drawable.ic_visibility_off_black_24dp);
            editOldPin.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD);
            editOldPin.setSelection(editOldPin.length());
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
        if (count < 4) {
            Toast.makeText(this, R.string.toast_enter4, Toast.LENGTH_SHORT).show();
        } else {
            if (!keystore.hasPin() || keystore.checkPin(pinOff)) {
                keystore.saveNew(stringNewPassword);
                checkOffPin.setChecked(false);
            } else {
                String stringInputOldPassword = editOldPin.getText().toString();
                if ("".equals(stringInputOldPassword)) {
                    Toast.makeText(this, R.string.toast_enter_PIN, Toast.LENGTH_SHORT).show();
                } else if (!keystore.checkPin(stringInputOldPassword)) {
                    Toast.makeText(this, R.string.toast_error_PIN, Toast.LENGTH_SHORT).show();
                } else {
                    keystore.saveNew(stringNewPassword);
                    checkOffPin.setChecked(false);
                }
            }
        }
    }
}
