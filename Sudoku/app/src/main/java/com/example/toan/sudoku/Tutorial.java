package com.example.toan.sudoku;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Created by Tuan Anh on 05/06/2017.
 */
public class Tutorial extends AppCompatActivity {

    TextView tv_tutorial;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);

        addStyleTextView();
    }

    protected void addStyleTextView() //Ham set font chu cho textview
    {
        tv_tutorial=(TextView)findViewById(R.id.tvTutorial);
        final Typeface face= Typeface.createFromAsset(this.getAssets(),"iciel Cadena.ttf");
        tv_tutorial.setTypeface(face);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(MainActivity.sound)
            MainActivity.sudokusound.start();

    }
    @Override
    protected void onPause() {
        MainActivity.sudokusound.pause();
        super.onPause();
    }
}
