package com.example.toan.sudoku;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Tuan Anh on 05/06/2017.
 */

public class Difficult extends AppCompatActivity {

    TextView choosedifficult;
    Button easy,medium,hard;
    Boolean checkstatus = false; //bien kiem tra xem nguoi choi da chon do kho hay chua

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_difficult);
        addControl();
    }

    @Override
    protected void onResume() {
        super.onResume();
        addStyleButton();
        if(MainActivity.sound)
            MainActivity.sudokusound.start(); //chay am thanh
    }

    @Override
    protected void onPause() {
        super.onPause();
        MainActivity.sudokusound.pause();    // ngung chay am thanh
        if(checkstatus)
        finish();
    }

    protected void addControl() //Ham gan su kien cac button
    {
        easy=(Button)findViewById(R.id.btnEasy);
        easy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Start(0);
            }
        });
        medium=(Button)findViewById(R.id.btnMedium);
        medium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Start(1);
            }
        });
        hard=(Button)findViewById(R.id.btnHard);
        hard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Start(2);
            }
        });
    }

    protected void Start(int i) //ham chon do kho theo tham so truyen vao i, chuyen sang man hinh choi game
    {
        Game.difficult=i;
        Intent intent =new Intent(Difficult.this,Game.class);
        this.startActivity(intent);
        checkstatus=true;
    }

    protected void addStyleButton()// ham set font chu cho cac button
    {
        choosedifficult=(TextView)findViewById(R.id.tvDifficult);
        final Typeface face= Typeface.createFromAsset(this.getAssets(),"iciel Cadena.ttf");
        easy.setTypeface(face);
        medium.setTypeface(face);
        hard.setTypeface(face);
        choosedifficult.setTypeface(face);
    }

}
