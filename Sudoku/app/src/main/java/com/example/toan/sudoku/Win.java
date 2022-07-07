package com.example.toan.sudoku;

import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by Tuan Anh on 07/06/2017.
 */

public class Win extends AppCompatActivity {
    TextView win, congratualation1, congratualation2, editname;
    Button Gomenu;
    EditText name;

    static MediaPlayer winningsound; //am thanh win game

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_win);

        setTextWinTime();
        addStyleTextView();
        createSound();
    }

    protected void addStyleTextView() //Ham set font chu cho textview
    {
        congratualation1=(TextView)findViewById(R.id.tvCongratulation);
        congratualation2=(TextView)findViewById(R.id.tvCongra);
        editname=(TextView)findViewById(R.id.tvEditname);
        final Typeface face= Typeface.createFromAsset(this.getAssets(),"iciel Cadena.ttf");
        congratualation1.setTypeface(face);
        congratualation2.setTypeface(face);
        editname=(TextView)findViewById(R.id.tvEditname);
    }

    protected void setTextWinTime() //Ham set text cho textview tvTimewin
    {
        win=(TextView)findViewById(R.id.tvTimewin);
        name=(EditText) findViewById(R.id.name);
        Bundle gettime= getIntent().getExtras();
        final String time=gettime.get("WIN").toString();

        if(gettime!=null)
            win.setText("Time:"+time);   //lay t/gian cua nguoi choi sau khi choi xong

        Gomenu=(Button) findViewById(R.id.btnGomenu);
        Gomenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if( name.getText().toString().compareTo("")!=0)
                {
                    MainActivity.DatabaseSudoku.insertHighScore(time+"   "+name.getText()); //luu ten va t/gian choi cua nguoi choi vao DB
                    finish();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }

    protected void createSound()//Ham khoi tao am thanh win game
    {
        winningsound= MediaPlayer.create(this,R.raw.win_sound);
        winningsound.setLooping(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        winningsound.start();
    }
    @Override
    protected void onPause() {
        winningsound.pause();
        super.onPause();
    }


}
