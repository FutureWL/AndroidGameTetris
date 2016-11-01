package com.example.weilai.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by weilai on 2016/11/1.
 */

public class NextBlockView extends View {

    Block block = new Block();
    Bitmap bitmap;

    public NextBlockView(Context context) {
        super(context);
    }

    public NextBlockView(Context context, AttributeSet attrs) {
        super(context, attrs);
        BitmapDrawable bitmapDrawable = (BitmapDrawable) context.getResources().getDrawable(R.drawable.block);
        bitmap = bitmapDrawable.getBitmap();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        block.drawblock(canvas, bitmap, block.ps);
    }
}
