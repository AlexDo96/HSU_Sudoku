package com.example.toan.sudoku;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.Locale;
import java.util.Random;

/**
 * Created by Tuan Anh on 05/06/2017.
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button buttonnew,buttoncontinue,buttonintruction,buttonhighscore;
    ImageButton imgbtnsound,imgbtnvie,imgbtneng;

    static MediaPlayer sudokusound; //am thanh nhac nen
    static boolean sound;//bien luu trang thai am thanh trong va sau khi thoat game

    String language;//bien luu  trang thai ngon ngu

    static DataSudoku data;//doi tuong lay du lieu tu class DataSudoku
    static Database DatabaseSudoku;//contructor DataSudoku

    static String decontinue, dapancontinue, manchoicontinue, timecontinue;//cac bien luu man choi,dap an, thoi gian continue
    static Cursor c;// con tro de doc du lieu
    int back=0;


    static SharedPreferences sharedPreferences;// doi tuong luu tru du lieu tren sharedpreferences (du lieu am thanh)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addControl();
        addStyleButton();
        createSound();
        sudokusound.start();


    }


    @Override
    protected void onResume() {
        super.onResume();
        getData();
        getDataSound();

        getDataLanguage();
        changeLanguage();
        setContinue();


        setIconSound();
    }


    @Override
    protected void onPause() {
        super.onPause();
        sudokusound.pause();
        saveSoundStatus(sound);

        saveLanguageStatus(language);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        sudokusound.release();
    }

    @Override
    public void onBackPressed() {
        back+=1;
        Toast.makeText(this,R.string.exit,Toast.LENGTH_SHORT).show();
        if(back==2)
        super.onBackPressed();
    }

    protected void addControl()//Ham gan su kien cho cac button
    {
        buttonnew=(Button) findViewById(R.id.btnNewgame);
        buttonnew.setOnClickListener(this);
        buttoncontinue=(Button) findViewById(R.id.btnContinue);
        buttoncontinue.setOnClickListener(this);
        buttonintruction=(Button) findViewById(R.id.btnTutorial);
        buttonintruction.setOnClickListener(this);
        imgbtnsound=(ImageButton)findViewById(R.id.imgButtonSound);
        imgbtnsound.setOnClickListener(this);
        buttonhighscore =(Button) findViewById(R.id.btnHighscore);
        buttonhighscore.setOnClickListener(this);
        imgbtnvie=(ImageButton) findViewById(R.id.imgButtonVie);
        imgbtnvie.setOnClickListener(this);
        imgbtneng=(ImageButton) findViewById(R.id.imgButtonEng);
        imgbtneng.setOnClickListener(this);
    }

    protected void addStyleButton()//Ham set font  chu cho cac button
    {
        final Typeface face= Typeface.createFromAsset(this.getAssets(),"iciel Cadena.ttf");
        buttonnew.setTypeface(face);
        buttoncontinue.setTypeface(face);
        buttonintruction.setTypeface(face);
        buttonhighscore.setTypeface(face);
    }

    protected void createSound()//Ham khoi tao am thanh nhac nen
    {
        sudokusound= MediaPlayer.create(this,R.raw.sudoku_soundtrack);
        sudokusound.setLooping(true);
    }

    public void getDataSound() //Ham lay trang thai am thanh da luu
    {
        sharedPreferences =getSharedPreferences("sound",MODE_PRIVATE);
        sound=sharedPreferences.getBoolean("sound",true);
    }

    static void saveSoundStatus(boolean sound)//Ham luu lai trang thai am thanh
    {
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putBoolean("sound",sound);
        editor.commit();
    }

    protected void setIconSound()//Ham set hinh anh on/off cua button am thanh
    {
        if(sound) {
            imgbtnsound.setBackgroundResource(R.drawable.soundonicon);
            sudokusound.start();
        }
        else
        {
            sudokusound.pause();
            imgbtnsound.setBackgroundResource(R.drawable.soundofficon);
        }
    }

    protected void getDataLanguage()//Ham lay trang thai ngon ngu da luu
    {
        language=sharedPreferences.getString("language","en");
    }


    private void changeLanguage()//Ham doi ngon ngu
    {
        Locale myLocale = new Locale(language);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
        this.setContentView(R.layout.activity_main);
        getData();
        addControl();
        addStyleButton();
        setIconSound();
    }

    static void saveLanguageStatus(String language)//Ham luu lai trang thai ngon ngu
    {
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("language",language);
        editor.commit();
    }


    protected void setContinue()//Ham an/hien continue
    {
        if(decontinue == null || decontinue.compareTo("")==0) buttoncontinue.setVisibility(View.INVISIBLE);

        else  buttoncontinue.setVisibility(View.VISIBLE);
    }

    protected void getData()//Ham lay du lieu man choi cu tu data
    {
        DatabaseSudoku = new Database(MainActivity.this);
        DatabaseSudoku.open();
        c = DatabaseSudoku.layData();

        Random r = new Random();
        if (c.moveToPosition(r.nextInt(4)) == true) {
            String de_easy = c.getString(1);
            String de_medium = c.getString(2);
            String de_hard = c.getString(3);
            String da_easy = c.getString(4);
            String da_medium = c.getString(5);
            String da_hard = c.getString(6);
            data = new DataSudoku(de_easy, de_medium, de_hard, da_easy, da_medium, da_hard);
        }

        if(c.moveToPosition(0)==true)    // tro den dong dau tien cua cot thu 7 (cot tam)
        {
            decontinue =c.getString(7);
        }
        if(c.moveToPosition(1)==true)   // tro den dong thu hai cua cot thu 7 (cot tam)
        {
            manchoicontinue =c.getString(7);
        }
        if(c.moveToPosition(2)==true)
        {
            dapancontinue =c.getString(7);
        }
        if(c.moveToPosition(4)==true)
        {
            timecontinue =c.getString(7);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnContinue: {
                {
                        Intent intent = new Intent(MainActivity.this, Game.class);
                        this.startActivity(intent);
                }
            };break;
            case R.id.btnTutorial:{
                Intent intent =new Intent(MainActivity.this,Tutorial.class);
                this.startActivity(intent);
            };break;
            case R.id.btnNewgame: chooseDifficult();break;
            case R.id.btnHighscore:
            {
                Intent intent =new Intent(MainActivity.this,HighScore.class);
                this.startActivity(intent);
            };break;
            case R.id.imgButtonSound:{
                if(sound==true)
                {
                    imgbtnsound.setBackgroundResource(R.drawable.soundofficon);
                    sudokusound.pause();
                    sound=false;
                }
                else {
                    imgbtnsound.setBackgroundResource(R.drawable.soundonicon);
                    sudokusound.start();
                    sound=true;
                }
            }break;



            case R.id.imgButtonEng: language="en";
                changeLanguage();
                setContinue();

                break;
            case R.id.imgButtonVie: language="vi";
                changeLanguage();
                setContinue();

                break;


        }
    }

    private void chooseDifficult() //Ham chuyen qua menu chon do kho
    {
        decontinue =null;
        dapancontinue =null;
        manchoicontinue =null;
        timecontinue =null;
        Intent intent =new Intent(MainActivity.this,Difficult.class);
        this.startActivity(intent);
    }

}
