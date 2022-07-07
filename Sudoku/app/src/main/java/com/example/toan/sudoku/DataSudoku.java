package com.example.toan.sudoku;

/**
 * Created by Tuan Anh on 04/06/2017.
 */

public class DataSudoku {
    public String de_easy, de_medium, de_hard, da_easy, da_medium, da_hard;

    public DataSudoku()
    {

    }

    public DataSudoku(String de_easy, String de_medium, String de_hard, String da_easy, String da_medium, String da_hard)
    {
        this.de_easy=de_easy;
        this.de_medium=de_medium; this.de_hard=de_hard;
        this.da_easy=da_easy; this.da_medium=da_medium;
        this.da_hard=da_hard;
    }
}
