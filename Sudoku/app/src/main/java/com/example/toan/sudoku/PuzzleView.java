package com.example.toan.sudoku;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by Tuan Anh on 06/06/2017.
 */
public class PuzzleView extends View {

    private final Game game;
    private final Rect selRect = new Rect(); //hinh chu nhat the hien o dang chon
    private float width;
    private float height;
    protected int selX=-1; //stt cot
    protected int selY=-1; // stt hang

    public PuzzleView(Context context) {
        super(context);
        this.game = (Game) context;
        setFocusable(true);
        setFocusableInTouchMode(true);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) { //chia man hinh thanh 9 cot 9 hang
        width = w / 9f;
        height = h / 9f;
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onDraw(Canvas canvas) { // Ve man choi Sudoku
        super.onDraw(canvas);
        //chon mau khung choi
        Paint day = new Paint();
        day.setColor(getResources().getColor(R.color.vien));
        day.setStrokeWidth(7f);
        Paint mong = new Paint();
        mong.setColor(getResources().getColor(R.color.vien));
        mong.setStrokeWidth(4f);
        // dinh dang mau sac va kich co so de
        Paint sode = new Paint(Paint.ANTI_ALIAS_FLAG);
        sode.setColor(Color.BLACK);
        sode.setStyle(Paint.Style.FILL);
        sode.setTextSize(height * 0.75f);
        sode.setTextScaleX(width / height);
        sode.setTextAlign(Paint.Align.CENTER);
        // dinh dang mau sac va kich co so dien sai
        Paint sosai = new Paint(Paint.ANTI_ALIAS_FLAG);
        sosai.setColor(Color.RED);
        sosai.setStyle(Paint.Style.FILL);
        sosai.setTextSize(height * 0.75f);
        sosai.setTextScaleX(width / height);
        sosai.setTextAlign(Paint.Align.CENTER);
        //dinh dang mau sac va kich co so dien vao
        Paint sonhap = new Paint(Paint.ANTI_ALIAS_FLAG);
        sonhap.setColor(Color.BLUE);
        sonhap.setStyle(Paint.Style.FILL);
        sonhap.setTextSize(height * 0.75f);
        sonhap.setTextScaleX(width / height);
        sonhap.setTextAlign(Paint.Align.CENTER);
        //tham so de ve so vao giua o choi
        Paint.FontMetrics fm = sode.getFontMetrics();
        float x = width / 2;
        float y = height / 2 - (fm.ascent + fm.descent) / 2;
        // chon mau o dang duoc chon
        Paint selected = new Paint();
        selected.setColor(Color.GREEN);
        //ve so nguoi choi dien vao
        {
        if (selX >= 0 && selY >= 0 && this.game.laygiatride(selX, selY) == "") {
                canvas.drawRect(selRect, selected);
                if (this.game.so != 0) {
                    this.game.setValue(selX, selY, this.game.so);
                    if (this.game.laygiatringuoichoi(selX, selY) != "")
                        canvas.drawRect(selRect, selected);
                    else canvas.drawText(this.game.laygiatringuoichoi(selX, selY), selX * width + x, selY * height + y, sonhap);
                    this.game.so = 0;
                }
        }
        else  this.game.so=0;
            //ve lai ban choi sau khi nhap
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    if (this.game.laygiatride(i, j) != "")
                        canvas.drawText(this.game.laygiatringuoichoi(i, j), i * width + x, j * height + y, sode);
                    else
                        canvas.drawText(this.game.laygiatringuoichoi(i, j), i * width + x, j * height + y, sonhap);
                }
            }

            //cac dkien vẽ màu để ktra cac thuat toan giai Sudoku
            if (selX >= 0 && selY >= 0) {
                //check theo logic
                {
                    //lay gia tri x,y cac o trung theo cot,hang,o3x3
                    List<Integer> cot = this.game.checkcot(selX, selY);
                    List<Integer> hang = this.game.checkhang(selX, selY);
                    List<Integer> o3x3 = this.game.check3x3(selX, selY);
                    int saix = 0;
                    int saiy = 0;
                    if (cot.size() > 0) {
                        canvas.drawText(this.game.laygiatringuoichoi(selX, selY), selX * width + x, selY * height + y, sosai);
                        for (int i = 0; i < cot.size(); i++) {
                            saix = cot.get(i) % 9;
                            saiy = cot.get(i) / 9;
                            canvas.drawText(this.game.laygiatringuoichoi(saix, saiy), saix * width + x, saiy * height + y, sosai);
                        }
                    }
                    if (hang.size() > 0) {
                        canvas.drawText(this.game.laygiatringuoichoi(selX, selY), selX * width + x, selY * height + y, sosai);
                        for (int i = 0; i < hang.size(); i++) {
                            saix = hang.get(i) % 9;
                            saiy = hang.get(i) / 9;
                            canvas.drawText(this.game.laygiatringuoichoi(saix, saiy), saix * width + x, saiy * height + y, sosai);
                        }
                    }
                    if (o3x3.size() > 0) {
                        canvas.drawText(this.game.laygiatringuoichoi(selX, selY), selX * width + x, selY * height + y, sosai);
                        for (int i = 0; i < o3x3.size(); i++) {
                            saix = o3x3.get(i) % 9;
                            saiy = o3x3.get(i) / 9;
                            canvas.drawText(this.game.laygiatringuoichoi(saix, saiy), saix * width + x, saiy * height + y, sosai);
                        }
                    }
                }
                //check theo dap an
                if (this.game.checkdapan) {
                    if (this.game.laygiatride(selX, selY) == "") {
                        if (this.game.laygiatringuoichoi(selX, selY) != this.game.laygiatridapan(selX, selY) &&
                                this.game.laygiatringuoichoi(selX, selY) != "") {
                            Paint mausai = new Paint();
                            mausai.setColor(Color.RED);
                            canvas.drawRect(selX * width, selY * height, selX * width + width, selY * height + height, mausai);
                        }
                        canvas.drawText(this.game.laygiatringuoichoi(selX, selY), selX * width + x, selY * height + y, sonhap);
                    }
                }
            }
            this.game.checkdapan = false;

            //ve ban choi
            for (int i = 0; i < 10; i++) {
                canvas.drawLine(0, i * height, getWidth(), i * height,mong);
                canvas.drawLine(i * width, 0, i * width, getHeight(), mong);
            }
            for (int i = 0; i < 10; i++) {
                if (i % 3 != 0) continue;
                {
                    canvas.drawLine(0, i * height, getWidth(), i * height, day);
                    canvas.drawLine(i * width, 0, i * width, getHeight(), day);
                }
            }
            {
                canvas.drawLine(0, 0 * height+5, getWidth(), 0 * height+5, day);
                canvas.drawLine(0 * width+5, 0, 0 * width+5, getHeight(), day);
                canvas.drawLine(0, 9 * height-5, getWidth(), 9 * height-5, day);
                canvas.drawLine(9 * width-5, 0, 9 * width-5, getHeight(), day);
            }
        }
    }

    private void getRect(int x, int y, Rect rect) {
        //ham xu ly chieu dai chieu rong de set kich thuoc hinh chu nhat
        rect.set((int) (x * width), (int) (y * height), (int) (x * width + width), (int) (y * height + height));
    }

    private void select(int x, int y) {
        invalidate(selRect);
        //xu ly toa do nguoi dung chon tren man hinh thanh gia tri theo hang cot
        selX = Math.min(Math.max(x, 0), 8);
        selY = Math.min(Math.max(y, 0), 8);
        getRect(selX, selY, selRect);
        invalidate(selRect);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //ham set su kien touch tren man hinh
        if (event.getAction() != MotionEvent.ACTION_DOWN)
            return super.onTouchEvent(event);
        select((int) (event.getX() / width), (int) (event.getY() / height));
        Log.d(TAG, "onTouchEvent: x " + selX + ", y " + selY);
        return true;
    }
}



