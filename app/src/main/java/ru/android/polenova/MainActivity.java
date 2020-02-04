package ru.android.polenova;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Keystore keystore = App.getKeystore();
    private TextView textViewPIN;
    private RadioButton radioButton1;
    private RadioButton radioButton2;
    private RadioButton radioButton3;
    private RadioButton radioButton4;
    private String stringPIN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        this.setTitle(R.string.title_notes);
        findOldPIN();
    }

    private void findOldPIN() {
        if (!keystore.hasPin()) {
            startActivity(new Intent(MainActivity.this, SettingsActivity.class));
        } else {
            if (keystore.checkPin("pinOff")) {
                startActivity(new Intent(MainActivity.this, ListNoteActivity.class));
            } else {
                initView();
            }
        }
    }

    private void clearPin() {
        if (radioButton4.isClickable()) {
            radioButton4.setChecked(false);
            radioButton4.setClickable(false);
            radioButton3.setChecked(false);
            radioButton3.setClickable(false);
            radioButton2.setChecked(false);
            radioButton2.setClickable(false);
            radioButton1.setChecked(false);
            radioButton1.setClickable(false);
            textViewPIN.setText("");
        } else if (radioButton3.isClickable()) {
            radioButton3.setChecked(false);
            radioButton3.setClickable(false);
        } else if (radioButton2.isClickable()) {
            radioButton2.setChecked(false);
            radioButton2.setClickable(false);
        } else if (radioButton1.isClickable()) {
            radioButton1.setChecked(false);
            radioButton1.setClickable(false);
        }
        stringPIN = textViewPIN.getText().toString();
        if (!"".equals(stringPIN)) {
            stringPIN = stringPIN.substring(0, stringPIN.length() - 1);
            textViewPIN.setText(stringPIN.toString());
        }
    }

    private void initRadioButtons() {
        radioButton1 = findViewById(R.id.radioPIN1);
        radioButton2 = findViewById(R.id.radioPIN2);
        radioButton3 = findViewById(R.id.radioPIN3);
        radioButton4 = findViewById(R.id.radioPIN4);
        if (!radioButton1.isClickable()) {
            radioButton1.setClickable(true);
            radioButton1.setChecked(true);
        } else if (!radioButton2.isClickable()) {
            radioButton2.setClickable(true);
            radioButton2.setChecked(true);
        } else if (!radioButton3.isClickable()) {
            radioButton3.setClickable(true);
            radioButton3.setChecked(true);
        } else if (!radioButton4.isClickable()) {
            radioButton4.setClickable(true);
            radioButton4.setChecked(true);
            if (!keystore.checkPin(textViewPIN.getText().toString())) {
                clearPin();
                Toast.makeText(this, R.string.toast_error_PIN, Toast.LENGTH_SHORT).show();
            } else {
                Intent intent = new Intent(MainActivity.this, ListNoteActivity.class);
                startActivity(intent);
            }
        }
    }

    private void initView() {
        textViewPIN = findViewById(R.id.textPIN);
        textViewPIN.setVisibility(View.INVISIBLE);
        findViewById(R.id.button0).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textViewPIN.append("0");
                initRadioButtons();
            }
        });
        findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textViewPIN.append("1");
                initRadioButtons();
            }
        });
        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textViewPIN.append("2");
                initRadioButtons();
            }
        });
        findViewById(R.id.button3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textViewPIN.append("3");
                initRadioButtons();
            }
        });
        findViewById(R.id.button4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textViewPIN.append("4");
                initRadioButtons();
            }
        });
        findViewById(R.id.button5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textViewPIN.append("5");
                initRadioButtons();
            }
        });
        findViewById(R.id.button6).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textViewPIN.append("6");
                initRadioButtons();
            }
        });
        findViewById(R.id.button7).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textViewPIN.append("7");
                initRadioButtons();
            }
        });
        findViewById(R.id.button8).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textViewPIN.append("8");
                initRadioButtons();
            }
        });
        findViewById(R.id.button9).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textViewPIN.append("9");
                initRadioButtons();
            }
        });
        findViewById(R.id.imageButtonDelete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearPin();
            }
        });
    }
}


