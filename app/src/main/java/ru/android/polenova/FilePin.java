package ru.android.polenova;

import android.content.Context;
import android.nfc.Tag;
import android.text.TextUtils;
import android.util.Log;
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
            Toast.makeText(context, "пароль сохранен", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Toast.makeText(context, "пароль  не сохранен", Toast.LENGTH_SHORT).show();
        }
    }

    /*static boolean exportPIN(Context context, String stringPin) {
        String stringPassword = stringPin.toString();
        BufferedWriter bufferedWriter = null;
        try {
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(context.openFileOutput(FILE_PASSWORD, Context.MODE_PRIVATE)));
            bufferedWriter.write(stringPassword);
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Toast.makeText(context, "пароль сохранен", Toast.LENGTH_SHORT).show();
        return false;
    }

    static String importPIN(Context context) {
        String stringPassword;
        BufferedReader bufferedReader = null;
        InputStreamReader streamReader = null;
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = context.openFileInput(FILE_PASSWORD);
            streamReader = new InputStreamReader(fileInputStream);
            bufferedReader = new BufferedReader(streamReader);
            stringPassword = bufferedReader.readLine().toString();
            return stringPassword;
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (streamReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }*/
}
