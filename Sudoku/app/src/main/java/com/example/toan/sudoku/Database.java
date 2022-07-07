package com.example.toan.sudoku;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Tuan Anh on 04/06/2017.
 */

public class Database extends SQLiteOpenHelper {
    private Context context;

    private String DB_PATH = "data/data/com.example.toan.sudoku/";
    private static String DB_NAME = "database.sqlite";
    private SQLiteDatabase myDatabase;

    @Override
    public void onCreate(SQLiteDatabase db) {

    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public Database(Context context) //contructor khoi tao database
    {
        super(context, DB_NAME, null, 1);

        this.context = context;
        boolean dbexist = checkDatabase();

        if(dbexist)
        {
            //Neu Database ton tai thi ko lam gi ca
        }
        else
        {
            System.out.println("Database doesn't exist!");

            createDatabse();
        }
    }

    private boolean checkDatabase() //ham kiem tra database ton tai
    {

        boolean checkdb = false;

        try
        {
            String myPath = DB_PATH + DB_NAME;
            File dbfile = new File(myPath);
            checkdb = dbfile.exists();
        }
        catch (SQLiteException e)
        {
            System.out.println("Database doesn't exist!");
        }

        return checkdb;
    }

    public void createDatabse() //ham khoi tao database
    {

        this.getReadableDatabase();

        try
        {
            copyDatabase();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void copyDatabase() throws IOException //ham copy database vao thu muc data cua game
    {
        InputStream myinput = context.getAssets().open(DB_NAME);

        String outFileName = DB_PATH + DB_NAME;

        OutputStream myOutput = new FileOutputStream(outFileName);

        byte[] buffer = new byte[1024];
        int length;

        while ((length = myinput.read(buffer)) > 0)   //Copy tung Mb, doc Database tu tu
        {
            myOutput.write(buffer, 0, length);
        }

        myOutput.flush();
        myOutput.close();
        myinput.close();
    }

    public void open()//ham mo database
    {
        String myPath = DB_PATH + DB_NAME;
        myDatabase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
    }

    public synchronized void close() //ham dong database (co dong bo hoa)
    {
        myDatabase.close();
        super.close();
    }

    public void update(String update,String hang) //ham update du lieu moi vao database
    {
        ContentValues up = new ContentValues();
        up.put("tam",update);
        myDatabase.update("DBSudoku",up,"_id=?", new String[] {hang});
    }

    public Cursor layData() //ham get du lieu man choi tu database
    {
        Cursor contro=myDatabase.rawQuery("select * from DBSudoku", null);
        return contro;
    }

    public void insertHighScore(String time) //ham luu lai ket qua sau khi choi xong
    {
        // Tao bang HighScore voi mot cot Time va truyen chuoi time vao cot Time
        ContentValues values=new ContentValues();
        values.put("Time",time);
        myDatabase.insert("HighScore",null,values);
    }

    public Cursor layDataHighScore()//ham get du lieu ket qua choi tu database
    {
        Cursor contro=myDatabase.rawQuery("select * from HighScore", null);
        return contro;
    }

}
