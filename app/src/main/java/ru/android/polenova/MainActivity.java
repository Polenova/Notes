package ru.android.polenova;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Keystore keystore = App.getKeystore();
    private TextView textViewPIN;
    private ImageView radioButton1;
    private ImageView radioButton2;
    private ImageView radioButton3;
    private ImageView radioButton4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        this.setTitle(R.string.title_notes);
        findOldPIN();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(MainActivity.this, ListNoteActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
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

    private void initRadioButtons() {
        switch (textViewPIN.getText().length()) {
            case 0:
                radioButton1.setImageResource(R.drawable.ic_radio_button_unchecked_grey_24dp);
                radioButton2.setImageResource(R.drawable.ic_radio_button_unchecked_grey_24dp);
                radioButton3.setImageResource(R.drawable.ic_radio_button_unchecked_grey_24dp);
                radioButton4.setImageResource(R.drawable.ic_radio_button_unchecked_grey_24dp);
                break;
            case 1:
                radioButton1.setImageResource(R.drawable.ic_radio_button_checked_blue_24dp);
                radioButton2.setImageResource(R.drawable.ic_radio_button_unchecked_grey_24dp);
                radioButton3.setImageResource(R.drawable.ic_radio_button_unchecked_grey_24dp);
                radioButton4.setImageResource(R.drawable.ic_radio_button_unchecked_grey_24dp);
                break;
            case 2:
                radioButton1.setImageResource(R.drawable.ic_radio_button_checked_blue_24dp);
                radioButton2.setImageResource(R.drawable.ic_radio_button_checked_blue_24dp);
                radioButton3.setImageResource(R.drawable.ic_radio_button_unchecked_grey_24dp);
                radioButton4.setImageResource(R.drawable.ic_radio_button_unchecked_grey_24dp);
                break;
            case 3:
                radioButton1.setImageResource(R.drawable.ic_radio_button_checked_blue_24dp);
                radioButton2.setImageResource(R.drawable.ic_radio_button_checked_blue_24dp);
                radioButton3.setImageResource(R.drawable.ic_radio_button_checked_blue_24dp);
                radioButton4.setImageResource(R.drawable.ic_radio_button_unchecked_grey_24dp);
                break;
            case 4:
                radioButton1.setImageResource(R.drawable.ic_radio_button_checked_blue_24dp);
                radioButton2.setImageResource(R.drawable.ic_radio_button_checked_blue_24dp);
                radioButton3.setImageResource(R.drawable.ic_radio_button_checked_blue_24dp);
                radioButton4.setImageResource(R.drawable.ic_radio_button_checked_blue_24dp);
                checkPin();
                break;

        }
    }

    private void checkPin() {
        if (!keystore.checkPin(textViewPIN.getText().toString())) {
            Toast.makeText(this, R.string.toast_error_PIN, Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent(MainActivity.this, ListNoteActivity.class);
            startActivity(intent);
        }
        textViewPIN.setText("");
    }


    private void initView() {
        radioButton1 = findViewById(R.id.radioPIN1);
        radioButton2 = findViewById(R.id.radioPIN2);
        radioButton3 = findViewById(R.id.radioPIN3);
        radioButton4 = findViewById(R.id.radioPIN4);
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
                String stringPIN = textViewPIN.getText().toString();
                if (!"".equals(stringPIN)) {
                    stringPIN = stringPIN.substring(0, stringPIN.length() - 1);
                    textViewPIN.setText(stringPIN.toString());
                }
                initRadioButtons();
            }
        });
    }
}


