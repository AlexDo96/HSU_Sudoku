package com.example.toan.sudoku;

import android.content.Intent;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tuan Anh on 05/06/2017.
 */

public class Game extends AppCompatActivity {
    PuzzleView puzzleView;
    Button one, two, three, four, five, six, seven, eight, nine, checkresult, pause, gomenu, resume;

    protected int so;//bien de gan so khi click vao button
    static int difficult=-1;//bien quan ly do kho cua man choi

    Integer[][] puzzlenguoichoi = new Integer[9][9];//mang nguoi choi dien vao
    Integer[][] puzzle = new Integer[9][9];//mang de    
    Integer[][] puzzledapan = new Integer[9][9];//mang dap an
    boolean checkdapan; //bien trang thai khi click vao button check
    Chronometer time; //dong ho dem thoi gian
    boolean isChronometerRunnig =true;//kiem tra trang thai dong ho dem
    long timecontinue;//bien luu lai gia tri thoi gian khi tam dung
    String manchoi;//chuoi luu lai man choi
    String dapan;//chuoi luu lai dap an
    String de;//chuoi luu lai de

    RelativeLayout draw;
    LinearLayout layoutmenu,layoutgame;
    ImageButton imgbtnsound;

    //Integer getX,getY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        layoutgame=(LinearLayout)findViewById(R.id.activity_game);
        draw = (RelativeLayout) findViewById(R.id.game);
        puzzleView = new PuzzleView(this);
        draw.addView(puzzleView);
        puzzleView.requestFocus();

        time = (Chronometer) findViewById(R.id.time);
        addControl();
        addEvent();

        if(MainActivity.decontinue == null)
            xulyNewgame();
        else
            xulyContinue();

        addData();
    }

    @Override
    protected void onResume() {  //chuong trinh dang chay thi nhac chay va dong ho chay
        super.onResume();
        if(MainActivity.sound)
            MainActivity.sudokusound.start();
        if(isChronometerRunnig) {
            time.setBase((SystemClock.elapsedRealtime() + timecontinue));
            time.start();
        }
        setIconSound();
    }
    @Override
    protected void onPause() {  //chuong trinh dang ngung thi nhac ngung va dong ho ngung
        super.onPause();
        time.stop();
        MainActivity.sudokusound.pause();
        MainActivity.saveSoundStatus(MainActivity.sound);
        if(isChronometerRunnig)
        updateData();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void updateData()// ham luu lai du lieu man choi, dap an, de, time khi ngung choi
    {
        MainActivity.DatabaseSudoku.update(de,"1");
        manchoi="";
        for (int i = 0; i < 9; i++)
            for (int j = 0; j < 9; j++) {
                manchoi+=puzzlenguoichoi[j][i].toString();
            }
        MainActivity.DatabaseSudoku.update(manchoi,"2");
        MainActivity.DatabaseSudoku.update(dapan,"3");
        timecontinue = (time.getBase()- SystemClock.elapsedRealtime());
        MainActivity.DatabaseSudoku.update(String.valueOf(timecontinue),"5");
    }

    private void addControl()//ham anh xa cac button
    {
        one = (Button) findViewById(R.id.button1);
        two = (Button) findViewById(R.id.button2);
        three = (Button) findViewById(R.id.button3);
        four = (Button) findViewById(R.id.button4);
        five = (Button) findViewById(R.id.button5);
        six = (Button) findViewById(R.id.button6);
        seven = (Button) findViewById(R.id.button7);
        eight = (Button) findViewById(R.id.button8);
        nine = (Button) findViewById(R.id.button9);
        checkresult=(Button)findViewById(R.id.btnCheckresult);
        imgbtnsound=(ImageButton)findViewById(R.id.imgButtonSound);
        pause=(Button)findViewById(R.id.btnPause);
        gomenu=(Button)findViewById(R.id.btnGomenu);
        resume=(Button)findViewById(R.id.btnResume);
        layoutmenu=(LinearLayout)findViewById(R.id.layoutmenu);
    }

    private void addEvent() //ham gan su kien cho cac button
     {
        one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                so = 1;
                winGame();
                puzzleView.invalidate();

            }
        });
        two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                so = 2;
                winGame();
                puzzleView.invalidate();
            }
        });
        three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                so = 3;
                winGame();
                puzzleView.invalidate();
            }
        });
        four.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                so = 4;
                winGame();
                puzzleView.invalidate();
            }
        });
        five.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                so = 5;
                winGame();
                puzzleView.invalidate();
            }
        });
        six.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                so = 6;
                winGame();
                puzzleView.invalidate();
            }
        });
        seven.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                so = 7;
                winGame();
                puzzleView.invalidate();
            }
        });
        eight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                so = 8;
                winGame();
                puzzleView.invalidate();
            }
        });
        nine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                so = 9;
                winGame();
                puzzleView.invalidate();
            }
        });
        checkresult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkdapan=true;
                time.setBase(time.getBase()-9000);
                puzzleView.invalidate();
            }
        });
        imgbtnsound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(MainActivity.sound==true)
                {
                    imgbtnsound.setBackgroundResource(R.drawable.soundofficon);
                    MainActivity.sudokusound.pause();
                    MainActivity.sound=false;
                }
                else {
                    imgbtnsound.setBackgroundResource(R.drawable.soundonicon);
                    MainActivity.sudokusound.start();
                    MainActivity.sound=true;
                }
            }
        });
        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    layoutgame.setBackgroundResource(R.drawable.bggame);
                    disableButton();
                    time.stop();
                    isChronometerRunnig=false;
                    updateData();
                    resume.setVisibility(View.VISIBLE);
                    layoutmenu.setVisibility(View.VISIBLE);
                    draw.setVisibility(View.GONE);
                    pause.setBackgroundResource(R.drawable.btnr);
            }
        });
        resume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layoutgame.setBackgroundResource(R.drawable.test);
                enableButton();
                time.setBase((SystemClock.elapsedRealtime() + timecontinue));
                isChronometerRunnig=true;
                time.start();
                resume.setVisibility(View.GONE);
                layoutmenu.setVisibility(View.GONE);
                draw.setVisibility(View.VISIBLE);
                pause.setBackgroundResource(R.drawable.btnp);
            }
        });
        gomenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }


    protected void setIconSound()//ham set hinh nen on/off cho button am thanh
    {
        if(MainActivity.sound) {
            imgbtnsound.setBackgroundResource(R.drawable.soundonicon);
        }
        else imgbtnsound.setBackgroundResource(R.drawable.soundofficon);
    }

    protected void disableButton() //ham an cac button khi Pause chuong trinh
    {
        one.setVisibility(View.INVISIBLE);
        two.setVisibility(View.GONE);
        three.setVisibility(View.GONE);
        four.setVisibility(View.INVISIBLE);
        five.setVisibility(View.GONE);
        six.setVisibility(View.GONE);
        seven.setVisibility(View.INVISIBLE);
        eight.setVisibility(View.GONE);
        nine.setVisibility(View.GONE);
        checkresult.setVisibility(View.INVISIBLE);
        pause.setVisibility(View.INVISIBLE);
    }

    protected void enableButton() //ham hien cac button khi chay chuong trinh
    {
        one.setVisibility(View.VISIBLE);
        two.setVisibility(View.VISIBLE);
        three.setVisibility(View.VISIBLE);
        four.setVisibility(View.VISIBLE);
        five.setVisibility(View.VISIBLE);
        six.setVisibility(View.VISIBLE);
        seven.setVisibility(View.VISIBLE);
        eight.setVisibility(View.VISIBLE);
        nine.setVisibility(View.VISIBLE);
        checkresult.setVisibility(View.VISIBLE);
        pause.setVisibility(View.VISIBLE);
    }

    protected void xulyNewgame() //Ham tao man choi moi khi btnNewgame
    {
        switch (difficult) {
            case 0: {
                de = MainActivity.data.de_easy;
                dapan = MainActivity.data.da_easy;
                manchoi = MainActivity.data.de_easy;
            }
            break;
            case 1: {
                de = MainActivity.data.de_medium;
                dapan = MainActivity.data.da_medium;
                manchoi = MainActivity.data.de_medium;
            }
            break;
            case 2: {
                de = MainActivity.data.de_hard;
                dapan = MainActivity.data.da_hard;
                manchoi = MainActivity.data.de_hard;
            }
            break;
        }
    }

    protected void xulyContinue() //ham lay gia tri man cho cu tu data
    {
        de = MainActivity.decontinue;
        dapan = MainActivity.dapancontinue;
        manchoi = MainActivity.manchoicontinue;
        timecontinue = Long.valueOf(MainActivity.timecontinue);
    }

    protected void addData() //ham add du lieu tu chuoi vao mang
    {
        for (int i = 0; i < 9; i++)
            for (int j = 0; j < 9; j++) {
                puzzle[i][j] = de.charAt(j * 9 + i) - '0';
            }
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                puzzlenguoichoi[i][j] = manchoi.charAt(j * 9 + i) - '0';
            }
        }
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                puzzledapan[i][j] = dapan.charAt(j * 9 + i) - '0';
            }
        }
    }

    protected void setValue(int x, int y, int value) {
        //ham gan gia tri moi cho phan tu trong mang
        puzzlenguoichoi[x][y]=value;
    }

    protected String laygiatride(int x, int y) {
        // ham lay gia tri tu chuoi de
        int v = puzzle[x][y];
        if (v == 0)
            return "";
        else
            return String.valueOf(v);
    }

    protected String laygiatringuoichoi(int x, int y) {
        //ham lay gia tri tu chuoi nguoi choi da dien
        int v = puzzlenguoichoi[x][y];
        if (v == 0)
            return "";
        else
            return String.valueOf(v);
    }

    protected String laygiatridapan(int x, int y) {
        // ham lay gia tri tu chuoi dapan
        int v = puzzledapan[x][y];
        return String.valueOf(v);
    }

    /* Cac buoc kiem tra Logic bai toan Sudoku */

    protected List<Integer> checkcot(int x,int y)
    {
        //ham kiem tra trung theo cot
        List<Integer> mangsai=new ArrayList<Integer>();
        for (int k = x; k < x + 1; k++)
            for (int j = 0; j < 9; j++) {
                if (x == k && y == j) ;
                else {
                    if (laygiatringuoichoi(x, y) == laygiatringuoichoi(k,j)) {
                        mangsai.add(j*9+k);
                    }
                }
            }
        return mangsai;
    }

    protected List<Integer> checkhang(int x,int y)
    {
        //ham kiem tra trung theo hang
        List<Integer> mangsai=new ArrayList<Integer>();
        for (int k = y; k < y + 1; k++)
            for (int j = 0; j < 9; j++) {
                if (x == j && y == k) ;
                else {
                    if (laygiatringuoichoi(x, y) == laygiatringuoichoi(j,k)) {
                        mangsai.add( k*9+j);
                    }
                }
            }
        return mangsai;
    }

    protected List<Integer> check3x3(int x, int y) {
        //ham kiem tra trung theo o 3x3
        List<Integer> mangsai=new ArrayList<Integer>();
        int k = 0;
        int h = 0;
        if (x < 3) k = 3;
        else if (x < 6) k = 6;
        else if (x < 9) k = 9;
        if (y < 3) h = 3;
        else if (y < 6) h = 6;
        else if (y < 9) h = 9;
        for (int i = k - 3; i < k; i++)
            for (int j = h - 3; j < h; j++)
                if (i == x && j == y) ;
                else {
                    if (laygiatringuoichoi(x, y) == laygiatringuoichoi(i, j)) {
                        mangsai.add(j*9+i);
                    }
                }
        return  mangsai;
    }
    /* -------------------------------------------  */

    protected void winGame() //ham so sanh dap an voi man choi, neu dung auto tvTimewin
    {
        int checkwin=0;
        for (int i = 0; i < 9; i++)
            for (int j = 0; j < 9; j++) {
                if(laygiatringuoichoi(i,j)==laygiatridapan(i,j))
                    checkwin+=1;
            }
        //if(checkwin==80)
        {
            de="";
            dapan="";
            manchoi="";
            updateData();
            time.stop();
            finish();
            Intent intent =new Intent(Game.this,Win.class);
            intent.putExtra("WIN",time.getText().toString());
            this.startActivity(intent);
        }
    }

}


