package com.str.escapecontrol;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.util.Timer;

public class Mansion extends AppCompatActivity {

    private Timer timer;
    private Mansion.AsyncDataClass jsonAsync;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mansion);

    }
}