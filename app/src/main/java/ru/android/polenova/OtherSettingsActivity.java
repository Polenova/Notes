package ru.android.polenova;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import static android.view.View.GONE;

public class OtherSettingsActivity extends AppCompatActivity {

    private SharedPreferences sharedPrefs;

    public static final String myPrefs = "myPrefs";
    public static final String nameKey = "nameKey";
    private String pinOff = "pinOff";

    private Keystore keystore = App.getKeystore();

    private Switch checkOffPin;
    private Spinner spinnerTheme;
    private EditText editNewPin;
    private EditText editOldPin;
    private ImageButton btnEysNewPin;
    private ImageButton btnEysOldPin;
    private ImageView btnChangePin;
    private Button btnSaveMode;

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

    // Кнопки *******

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
        editNewPin = findViewById(R.id.editTextNewPin);
        editOldPin = findViewById(R.id.editTextOldPin);
        btnEysNewPin = findViewById(R.id.buttonEyeNewPin);
        btnEysOldPin = findViewById(R.id.buttonEyeOldPin);
        btnSaveMode = findViewById(R.id.buttonSaveMode);
        spinnerTheme = findViewById(R.id.spinnerTheme);
        btnChangePin = findViewById(R.id.imageViewOpen);
        checkOffPin = findViewById(R.id.switchPin);
        editNewPin.setVisibility(GONE);
        editOldPin.setVisibility(GONE);
        btnEysNewPin.setVisibility(GONE);
        btnEysOldPin.setVisibility(GONE);
        btnSaveMode.setVisibility(GONE);
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
        btnChangePin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modePinOpenClose();
            }
        });
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
        initSpinnerTheme();
        btnSaveMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!"".equals(editNewPin.getText().toString())) {
                    savePinFile();
                }
            }
        });
    }

    // Развернуть изменение Pin

    private void modePinOpenClose() {
        if (editNewPin.getVisibility() == View.GONE) {
           editNewPin.setVisibility(View.VISIBLE);
            editOldPin.setVisibility(View.VISIBLE);
            btnEysNewPin.setVisibility(View.VISIBLE);
            btnEysOldPin.setVisibility(View.VISIBLE);
            btnSaveMode.setVisibility(View.VISIBLE);
            btnChangePin.setImageResource(R.drawable.ic_arrow_up);
        } else {
            editNewPin.setVisibility(GONE);
            editOldPin.setVisibility(GONE);
            btnEysNewPin.setVisibility(GONE);
            btnEysOldPin.setVisibility(GONE);
            btnSaveMode.setVisibility(GONE);
            editNewPin.getText().clear();
            editOldPin.getText().clear();
            hideSoftKeyboard(this);
            btnEysNewPin.setImageResource(R.drawable.ic_visibility_off_black_24dp);
            btnEysOldPin.setImageResource(R.drawable.ic_visibility_off_black_24dp);
            btnChangePin.setImageResource(R.drawable.ic_arrow_down);
        }
    }

    public static void hideSoftKeyboard(Activity activity) {
        View decorView = activity.getWindow().getDecorView();
        InputMethodManager manager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (manager != null) {
            manager.hideSoftInputFromWindow(decorView.getWindowToken(), 0);
            manager.hideSoftInputFromWindow(decorView.getApplicationWindowToken(), 0);
        }
    }

    // Спинер****

    private void initThemeOnSave() {
        switch (spinnerTheme.getSelectedItem().toString()) {
            default:
            case " ":
                break;
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

    private void initSpinnerTheme() {

        ArrayAdapter<CharSequence> adapterTheme = ArrayAdapter.createFromResource(this, R.array.theme, android.R.layout.simple_spinner_item);
        adapterTheme.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTheme.setAdapter(adapterTheme);
        spinnerTheme.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                initThemeOnSave();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    // Глаз ****

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

    // Сохранение Pin ********

    private void savePinFile() {
        String stringInputOldPassword;
        String stringNewPassword = editNewPin.getText().toString();
        int count = stringNewPassword.length();
        if (count < 4) {
            Toast.makeText(this, R.string.toast_enter4, Toast.LENGTH_SHORT).show();
        } else if (keystore.hasPin() && !keystore.checkPin(pinOff)) {
            stringInputOldPassword = editOldPin.getText().toString();
            if ("".equals(stringInputOldPassword)) {
                Toast.makeText(this, R.string.toast_enter_PIN, Toast.LENGTH_SHORT).show();
            } else if (!keystore.checkPin(stringInputOldPassword)) {
                Toast.makeText(this, R.string.toast_error_PIN, Toast.LENGTH_SHORT).show();
            } else {
                keystore.saveNew(stringNewPassword);
                checkOffPin.setChecked(false);
                modePinOpenClose();
            }
        } else {
                keystore.saveNew(stringNewPassword);
                checkOffPin.setChecked(false);
                modePinOpenClose();

        }
    }

    // Отключение Pin и сохранение состояния *********

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
