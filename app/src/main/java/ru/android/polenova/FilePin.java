package ru.android.polenova;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;


public class FilePin implements Keystore {

    private static final String FILE_PASSWORD = "password_notes";
    private Context context;

    public FilePin(Context context) {
        this.context = context;
    }


    @Override
    public boolean hasPin() {
        return !TextUtils.isEmpty(readPin());
    }

    @Nullable
    private String readPin() {
        String stringPassword;
        try (FileInputStream fileInputStream = context.openFileInput(FILE_PASSWORD);
             InputStreamReader streamReader = new InputStreamReader(fileInputStream);
             BufferedReader bufferedReader = new BufferedReader(streamReader)) {
            stringPassword = bufferedReader.readLine();
            return stringPassword;
        } catch (IOException ex) {
            return null;
        }
    }

    @Override
    public boolean checkPin(String pin) {
        return pin.equals(readPin());
    }

    @Override
    public void saveNew(String pin) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(context.openFileOutput(FILE_PASSWORD, Context.MODE_PRIVATE)))) {
            bufferedWriter.write(pin);
            bufferedWriter.close();
            Toast.makeText(context, R.string.toast_saved_PIN, Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Toast.makeText(context, R.string.toast_not_saved_PIN, Toast.LENGTH_SHORT).show();
        }
    }
}
