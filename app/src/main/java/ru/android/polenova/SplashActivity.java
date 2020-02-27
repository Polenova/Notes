package ru.android.polenova;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

public class SplashActivity extends AppCompatActivity {

    private Keystore keystore = App.getKeystore();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.SplashTheme);
        initActivity();
    }

    private void initActivity() {
        if (keystore.hasPin()) {
            if (keystore.checkPin("pinOff")) {
                SplashActivity.this.startActivity(new Intent(SplashActivity.this, ListNoteActivity.class));
            } else {
                SplashActivity.this.startActivity(new Intent(SplashActivity.this, MainActivity.class));
            }
        } else {
            SplashActivity.this.startActivity(new Intent(SplashActivity.this, SettingsActivity.class));
        }
        SplashActivity.this.finish();
    }
}
