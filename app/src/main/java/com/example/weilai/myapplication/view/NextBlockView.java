package com.example.weilai.myapplication.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.View;

import com.example.weilai.myapplication.entity.Block;
import com.example.weilai.myapplication.R;

/**
 * Created by weilai on 2016/11/1.
 */
public class NextBlockView extends View {

    private Block block = new Block();
    private Bitmap bitmap;

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
        block.drawBlock(canvas, bitmap, block.getPs());
    }

    public Block getBlock() {
        return block;
    }

    public void setBlock(Block block) {
        this.block = block;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}
