package com.example.weilai.myapplication;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

/**
 * Created by weilai on 2016/11/1.
 */

public class Map {
    int[][] map;

    public Map() {
        this.map = new int[20][10];
    }

    public void drawmap(Canvas canvas, Bitmap bitmap) {
        Rect rect = new Rect();
        int i, k;
        int x, y;
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.BLUE);
        int width = (canvas.getWidth() / 12) * 5 / 6;
        int height = width;
        for (i = 0; i < 20; i++) {
            for (k = 0; k < 10; k++) {
                x = k * width;
                y = i * height;
                if (map[i][k] == 1) {
                    rect.set(x, y, x + width, y + height);
                    canvas.drawBitmap(bitmap, null, rect, paint);
                }
            }
        }
    }

    public boolean isover() {
        for (int i = 0; i < 10; i++) {
            if (map[0][i] == 1) {
                return true;
            }
        }
        return false;
    }

    public void resetmap(int[][] b) {
        for (int i = 0; i < 4; i++) {
            map[b[i][1]][b[i][0]] = 1;
        }
    }

    public int deleteline() {
        boolean flag;
        int line = 0;

        for (int i = 0; i < 20; i++) {

            flag = true;

            for (int k = 0; k < 10; k++) {

                if (map[i][k] == 0) {
                    flag = false;
                    break;
                }

            }

            if (flag) {
                line++;
                for (int l = i; l > 0; l--) {
                    for (int j = 0; j < 10; j++) {
                        map[l][j] = map[l - 1][j];
                    }
                }
            }
        }
        return line;
    }

}
