package com.example.toan.sudoku;

import android.database.Cursor;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Tuan Anh on 07/06/2017.
 */

public class HighScore extends AppCompatActivity {

    TextView tv_highscores,time;
    String thoigian = "",tenuser="";
    TextView ten;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscores);

        addStyleTextView();

        time=(TextView) findViewById(R.id.tvTime);
        ten=(TextView) findViewById(R.id.tvName);
        getHighScore();
        time.setText(thoigian);
        ten.setText(tenuser);
    }

    protected void addStyleTextView() //Ham set font chu cho textview
    {
        tv_highscores=(TextView)findViewById(R.id.tvHighscore);
        final Typeface face= Typeface.createFromAsset(this.getAssets(),"iciel Cadena.ttf");
        tv_highscores.setTypeface(face);
    }

    protected void getHighScore() //ham lay du lieu nguoi choi tu data
    {
        Cursor c;
        ArrayList<String> danhsach= new ArrayList<>();
        c = MainActivity.DatabaseSudoku.layDataHighScore();
        if (c.moveToFirst() == true) {
            do {
                danhsach.add(c.getString(0));
            } while (c.moveToNext());
        }
        Collections.sort(danhsach);
        for(int i=1;i<danhsach.size();i++) {
            if(i>10) break;
            String[] ten= danhsach.get(i).split("   ");
            if(i==10)thoigian +=" "+String.valueOf(i)+"   "+ ten[0]+"\n";
            else thoigian += "   "+String.valueOf(i)+"   "+ ten[0]+"\n";
            if(ten[1].length()>10)
            tenuser +=ten[1].substring(0,10)+"\n";
            else tenuser +=ten[1]+"\n";
        }
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