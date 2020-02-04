package ru.android.polenova;

interface Keystore {
    boolean hasPin();
    boolean deletePin();
    boolean checkPin(String pin);
    void saveNew(String pin);
}