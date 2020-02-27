package ru.android.polenova;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

public class OtherSettingsActivity extends AppCompatActivity {

    private SharedPreferences sharedPrefs;

    public static final String myPrefs = "myPrefs";
    public static final String nameKey = "nameKey";
    private String pinOff = "pinOff";

    private Keystore keystore = App.getKeystore();

    private Switch checkOffPin;
    private Spinner spinnerTheme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UtilsSpinner.onActivityCreateSetTheme(OtherSettingsActivity.this);
        setContentView(R.layout.activity_other_settings);
        this.setTitle(R.string.title_setting);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        sharedPrefs = getSharedPreferences(myPrefs, MODE_PRIVATE);
        initView();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            Intent intent = new Intent(OtherSettingsActivity.this, ListNoteActivity.class);
            startActivity(intent);
        }
        return true;
    }

    private void initView() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        checkOffPin = findViewById(R.id.switchPin);
        if (keystore.checkPin(pinOff)) {
            checkOffPin.setChecked(true);
        } else {
            checkOffPin.setChecked(sharedPrefs.getBoolean(nameKey, false));
        }
        checkOffPin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    switchOffPin();
                } else {
                    checkOffPin.setChecked(false);
                    setSharedPreferences();
                    if (keystore.checkPin(pinOff)) {
                        Intent intent = new Intent(OtherSettingsActivity.this, SettingsActivity.class);
                        startActivity(intent);
                    }
                }
            }
        });
        spinnerTheme = findViewById(R.id.spinnerTheme);
        initSpinnerTheme();
        Button btnSaveMode = findViewById(R.id.buttonSaveMode);
        btnSaveMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (spinnerTheme.getSelectedItem().toString()) {
                    default:
                    case "Холодный":
                        UtilsSpinner.changeToTheme(OtherSettingsActivity.this, UtilsSpinner.THEME_COLD);
                        break;
                    case "Классический":
                        UtilsSpinner.changeToTheme(OtherSettingsActivity.this, UtilsSpinner.THEME_CLASSIC);
                        break;
                    case "Темный":
                        UtilsSpinner.changeToTheme(OtherSettingsActivity.this, UtilsSpinner.THEME_DARK);
                        break;
                    case "Яркий":
                        UtilsSpinner.changeToTheme(OtherSettingsActivity.this, UtilsSpinner.THEME_FUN);
                        break;
                }
            }
        });
    }

    private void initSpinnerTheme() {
        ArrayAdapter<CharSequence> adapterTheme = ArrayAdapter.createFromResource(this, R.array.theme, android.R.layout.simple_spinner_item);
        adapterTheme.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTheme.setAdapter(adapterTheme);
    }

    // Отключение пинкода и сохранение состояния *********

    public void switchOffPin() {
        if (keystore.hasPin()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(OtherSettingsActivity.this);
            final EditText input = new EditText(OtherSettingsActivity.this);
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
                                keystore.saveNew(pinOff);
                                Toast.makeText(OtherSettingsActivity.this, R.string.toast_clear_Pin, Toast.LENGTH_SHORT).show();
                                onRestart();
                            } else {
                                input.getText().clear();
                                Toast.makeText(OtherSettingsActivity.this, R.string.toast_error_PIN, Toast.LENGTH_SHORT).show();
                                switchOffPin();
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
            Toast.makeText(OtherSettingsActivity.this, R.string.toast_saved_PINOff, Toast.LENGTH_SHORT).show();
        }
        setSharedPreferences();
    }

    private void setSharedPreferences() {
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.putBoolean(nameKey, checkOffPin.isChecked()).commit();
    }
}
