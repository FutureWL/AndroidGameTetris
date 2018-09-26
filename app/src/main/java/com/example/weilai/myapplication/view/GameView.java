package com.example.weilai.myapplication.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.View;

import com.example.weilai.myapplication.entity.Block;
import com.example.weilai.myapplication.entity.Map;
import com.example.weilai.myapplication.R;

/**
 * Created by weilai on 2016/11/1.
 */
public class GameView extends View {

    private Bitmap bitmap;
    private Map map = new Map();
    private Block block = new Block();

    public GameView(Context context) {
        super(context);
    }

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        BitmapDrawable bitmapDrawable = (BitmapDrawable) context.getResources().getDrawable(R.drawable.block);
        bitmap = bitmapDrawable.getBitmap();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float width = (canvas.getWidth() / 12) * 5 / 6;
        float height = width;
        int i, k;
        float x, y;
        x = y = 0;
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.BLACK);
        for (i = 1; i <= 11; i++) {
            canvas.drawLine(x, 0, x, width * 20, paint);
            x = x + width;
        }
        for (k = 1; k <= 21; k++) {
            canvas.drawLine(0, y, height * 10, y, paint);
            y = y + height;
        }
        map.drawMap(canvas, bitmap);
        block.drawBlock(canvas, bitmap, block.getPs());
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public Block getBlock() {
        return block;
    }

    public void setBlock(Block block) {
        this.block = block;
    }
}
