package ru.android.polenova;

import android.widget.CheckBox;

interface Keystore {
    boolean hasPin();
    boolean checkPin(String pin);
    void saveNew(String pin);
}