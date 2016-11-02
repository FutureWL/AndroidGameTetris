package com.example.weilai.myapplication;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import java.util.Random;

/**
 * Created by weilai on 2016/11/1.
 */

public class Block {


    public int[][] ps;
    public int[][] initps;
    public int code;

    int[][][] block = {
            {{0, 0}, {0, 1}, {1, 1}, {1, 2}},   // s
            {{0, 0}, {1, 0}, {2, 0}, {3, 0}},   // z
            {{0, 0}, {0, 1}, {1, 0}, {1, 1}},   // l
            {{1, 0}, {1, 1}, {0, 1}, {0, 2}},   // j
            {{0, 0}, {0, 1}, {1, 1}, {2, 1}},   // i
            {{2, 0}, {2, 1}, {1, 1}, {0, 1}},   // o
            {{0, 1}, {1, 0}, {1, 1}, {2, 1}}    // t
    };


    public Block() {
        ps = new int[4][2];
        initps = new int[4][2];
        newrblock();
    }

    public void newrblock() {
        int i;
        Random random = new Random();
        i = random.nextInt(100) % 7;
        for (int j = 0; j < 4; j++) {
            ps[j][0] = block[i][j][0];
            ps[j][1] = block[i][j][1];
            initps[j][0] = ps[j][0];
            initps[j][1] = ps[j][1];
        }
        code = i + 1;
    }

    public boolean islimit(int[][] p) {
        int i;
        for (i = 0; i < 4; i++) {
            if (p[i][0] < 0 || p[i][0] > 9 || p[i][1] < 0 || p[i][1] > 19)
                return true;
        }
        return false;
    }

    public boolean isconflic(int[][] b, int[][] map) {
        int i;
        for (i = 0; i < 4; i++) {
            if ((map[b[i][1]][b[i][0]]) == 1) return true;
        }
        return false;
    }


    /**
     * 方块左移动
     *
     * @param map
     */
    public void left(int[][] map) {
        int[][] tp = new int[4][2];
        for (int i = 0; i < 4; i++) {
            tp[i][0] = ps[i][0] - 1;
            tp[i][1] = ps[i][1];
        }
        if (!islimit(tp) && !isconflic(tp, map)) {
            ps = tp;
        }
    }

    /**
     * 方块右移动
     *
     * @param map
     */
    public void right(int[][] map) {
        int[][] tp = new int[4][2];
        for (int i = 0; i < 4; i++) {
            tp[i][0] = ps[i][0] + 1;
            tp[i][1] = ps[i][1];
        }
        if (!islimit(tp) && !isconflic(tp, map)) {
            ps = tp;
        }
    }

    /**
     * 方块下移动
     *
     * @param map
     * @return
     */
    public boolean down(int[][] map) {
        int[][] tp = new int[4][2];
        for (int i = 0; i < 4; i++) {
            tp[i][0] = ps[i][0];
            tp[i][1] = ps[i][1] + 1;
        }
        if (!islimit(tp) && !isconflic(tp, map)) {
            ps = tp;
            return true;
        }
        return false;
    }

    /**
     * 方块变形
     *
     * @param map
     * @return
     */
    public boolean change(int[][] map) {

        int[][] tp = new int[4][2];
        for (int i = 0; i < 4; i++) {
            tp[i][0] = ps[i][0];
            tp[i][1] = ps[i][1];
        }

        switch (code) {
            case 1:
                tp[0][0] += 2;//横坐标加二
                tp[1][0]++;
                tp[1][1]--;
                tp[3][0]--;
                tp[3][1]--;
                if (!islimit(tp) && !isconflic(tp, map)) {
                    ps = tp;
                    code = 11;
                    return true;
                } else
                    return false;
            case 11:
                tp[0][0] -= 2;
                tp[1][0]--;
                tp[1][1]++;
                tp[3][0]++;
                tp[3][1]++;
                if (!islimit(tp) && !isconflic(tp, map)) {
                    ps = tp;
                    code = 1;
                    return true;
                } else
                    return false;
            case 2:
                tp[1][0]--;
                tp[1][1]++;
                tp[2][0] -= 2;
                tp[2][1] += 2;
                tp[3][0] -= 3;
                tp[3][1] += 3;
                if (!islimit(tp) && !isconflic(tp, map)) {
                    ps = tp;
                    code = 21;
                    return true;
                } else
                    return false;
            case 21:
                tp[1][0]++;
                tp[1][1]--;
                tp[2][0] += 2;
                tp[2][1] -= 2;
                tp[3][0] += 3;
                tp[3][1] -= 3;
                if (!islimit(tp) && !isconflic(tp, map)) {
                    ps = tp;
                    code = 2;
                    return true;
                } else
                    return false;
            case 3:
                if (!islimit(tp) && !isconflic(tp, map)) {
                    ps = tp;
                    code = 3;
                    return true;
                } else
                    return false;
            case 4:
                tp[0][0]++;
                tp[0][1]++;
                tp[2][0]++;
                tp[2][1]--;
                tp[3][1] -= 2;
                if (!islimit(tp) && !isconflic(tp, map)) {
                    ps = tp;
                    code = 41;
                    return true;
                } else
                    return false;
            case 41:
                tp[0][0]--;
                tp[0][1]--;
                tp[2][0]--;
                tp[2][1]++;
                tp[3][1] += 2;
                if (!islimit(tp) && !isconflic(tp, map)) {
                    ps = tp;
                    code = 4;
                    return true;
                } else
                    return false;
            case 5:
                tp[0][0]++;
                tp[1][1]--;
                tp[2][0]--;
                tp[3][0] -= 2;
                tp[3][1]++;
                if (!islimit(tp) && !isconflic(tp, map)) {
                    ps = tp;
                    code = 51;
                    return true;
                } else
                    return false;
            case 51:
                tp[0][0]++;
                tp[0][1]++;
                tp[1][0] += 2;
                tp[2][0]++;
                tp[2][1]--;
                tp[3][1] -= 2;
                if (!islimit(tp) && !isconflic(tp, map)) {
                    ps = tp;
                    code = 52;
                    return true;
                } else
                    return false;
            case 52:
                tp[0][0] -= 2;
                tp[0][1]++;
                tp[1][0]--;
                tp[1][1] += 2;
                tp[2][1]++;
                tp[3][0]++;
                if (!islimit(tp) && !isconflic(tp, map)) {
                    ps = tp;
                    code = 53;
                    return true;
                } else
                    return false;
            case 53:
                tp[0][1] -= 2;
                tp[1][0]--;
                tp[1][1]--;
                tp[3][0]++;
                tp[3][1]++;
                if (!islimit(tp) && !isconflic(tp, map)) {
                    ps = tp;
                    code = 5;
                    return true;
                } else
                    return false;
            case 6:
                tp[0][0]--;
                tp[0][1] += 2;
                tp[1][0] -= 2;
                tp[1][1]++;
                tp[2][0]--;
                tp[3][1]--;
                if (!islimit(tp) && !isconflic(tp, map)) {
                    ps = tp;
                    code = 61;
                    return true;
                } else
                    return false;
            case 61:
                tp[0][0]--;
                tp[0][1]--;
                tp[1][1] -= 2;
                tp[2][0]++;
                tp[2][1]--;
                tp[3][0] += 2;
                if (!islimit(tp) && !isconflic(tp, map)) {
                    ps = tp;
                    code = 62;
                    return true;
                } else
                    return false;
            case 62:
                tp[0][1]--;
                tp[1][0]++;
                tp[2][1]++;
                tp[3][0]--;
                tp[3][1] += 2;
                if (!islimit(tp) && !isconflic(tp, map)) {
                    ps = tp;
                    code = 63;
                    return true;
                } else
                    return false;
            case 63:
                tp[0][0] += 2;
                tp[1][0]++;
                tp[1][1]++;
                tp[3][0]--;
                tp[3][1]--;
                if (!islimit(tp) && !isconflic(tp, map)) {
                    ps = tp;
                    code = 6;
                    return true;
                } else
                    return false;
            case 7:
                tp[0][1]--;
                tp[1][1]++;
                tp[2][0]--;
                tp[3][0] -= 2;
                tp[3][1]++;
                if (!islimit(tp) && !isconflic(tp, map)) {
                    ps = tp;
                    code = 71;
                    return true;
                } else
                    return false;
            case 71:
                tp[0][0] += 2;
                tp[2][0]++;
                tp[2][1]--;
                tp[3][1] -= 2;
                if (!islimit(tp) && !isconflic(tp, map)) {
                    ps = tp;
                    code = 72;
                    return true;
                } else
                    return false;
            case 72:
                tp[0][0]--;
                tp[0][1] += 2;
                tp[1][0]--;
                tp[2][1]++;
                tp[3][0]++;
                if (!islimit(tp) && !isconflic(tp, map)) {
                    ps = tp;
                    code = 73;
                    return true;
                } else
                    return false;
            case 73:
                tp[0][0]--;
                tp[0][1]--;
                tp[1][0]++;
                tp[1][1]--;
                tp[3][0]++;
                tp[3][1]++;
                if (!islimit(tp) && !isconflic(tp, map)) {
                    ps = tp;
                    code = 7;
                    return true;
                } else
                    return false;
        }
        return false;
    }

    public void drawblock(Canvas canvas, Bitmap bitmap, int[][] ps) {
        Rect rect = new Rect();
        int i;
        int x, y;
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.BLUE);
        int width = (canvas.getWidth() / 12) * 5 / 6;
        int height = width;
        for (i = 0; i < 4; i++) {
            x = ps[i][0] * width;
            y = ps[i][1] * height;
            rect.set(x, y, x + width, y + height);
            canvas.drawBitmap(bitmap, null, rect, paint);
        }
    }
}
