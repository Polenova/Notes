package ru.android.polenova;

import android.widget.CheckBox;

interface Keystore {
    boolean hasPin();
    boolean deletePin();
    boolean checkPin(String pin);
    void saveNew(String pin);
}