package com.winnie.step06fragment;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class SubActivity extends AppCompatActivity implements MyFragment.MyFragmentListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);
    }

    @Override
    public void showMessage(int count) {
        new AlertDialog.Builder(this)
                .setMessage("Count : "+count)
                .setNeutralButton("confirm", null)
                .create()
                .show();
    }
}
