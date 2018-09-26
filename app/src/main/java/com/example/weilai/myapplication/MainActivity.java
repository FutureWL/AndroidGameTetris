package com.example.weilai.myapplication;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.weilai.myapplication.entity.Block;
import com.example.weilai.myapplication.view.GameView;
import com.example.weilai.myapplication.view.NextBlockView;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 启动活动
 * Created by weilai on 2016/11/1.
 */
public class MainActivity extends Activity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {

    // -------------------------------- //
    private RadioButton easy;
    private RadioButton medium;
    private RadioButton hard;
    // -------------------------------- //
    private int level = 500;
    // -------------------------------- //
    private GameView gameView;
    private NextBlockView nextBlockView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.begin);
        Button startButton = findViewById(R.id.start_button);
        Button exit = findViewById(R.id.exit_button);
        RadioGroup level = findViewById(R.id.level);
        hard = findViewById(R.id.hard);
        easy = findViewById(R.id.easy);
        medium = findViewById(R.id.medium);
        startButton.setOnClickListener(this);
        level.setOnCheckedChangeListener(this);
        exit.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.start_button:
                setContentView(R.layout.game);
                gameView = findViewById(R.id.gmview);
                nextBlockView = findViewById(R.id.nbview);
                Button right = findViewById(R.id.right);
                Button left = findViewById(R.id.left);
                Button down = findViewById(R.id.down);
                Button change = findViewById(R.id.change);
                right.setOnClickListener(this);
                left.setOnClickListener(this);
                change.setOnClickListener(this);
                down.setOnClickListener(this);
                timer.scheduleAtFixedRate(new MyTask(), 1, level);
                break;
            case R.id.exit_button:
                finish();
                break;
            case R.id.right:
                gameView.getBlock().right(gameView.getMap().getMap());
                gameView.invalidate();
                break;
            case R.id.left:
                gameView.getBlock().left(gameView.getMap().getMap());
                gameView.invalidate();
                break;
            case R.id.down:
                gameView.getBlock().down(gameView.getMap().getMap());
                gameView.invalidate();
                break;
            case R.id.change:
                gameView.getBlock().change(gameView.getMap().getMap());
                gameView.invalidate();
                break;
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        if (i == easy.getId()) {
            level = 1000;
        } else if (i == medium.getId()) {
            level = 500;
        } else if (i == hard.getId()) {
            level = 200;
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_DPAD_UP) {
            gameView.getBlock().change(gameView.getMap().getMap());
            gameView.invalidate();
            return true;
        }
        if (keyCode == KeyEvent.KEYCODE_DPAD_DOWN) {
            gameView.getBlock().down(gameView.getMap().getMap());
            gameView.invalidate();
            return true;
        }
        if (keyCode == KeyEvent.KEYCODE_DPAD_LEFT) {
            gameView.getBlock().left(gameView.getMap().getMap());
            gameView.invalidate();
            return true;
        }
        if (keyCode == KeyEvent.KEYCODE_DPAD_RIGHT) {
            gameView.getBlock().right(gameView.getMap().getMap());
            gameView.invalidate();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    Timer timer = new Timer();
    int score = 0;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @SuppressLint("SetTextI18n")
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    if (!gameView.getBlock().down(gameView.getMap().getMap())) {
                        gameView.getMap().resetMap(gameView.getBlock().getPs());
                        score = score + 100 * gameView.getMap().deleteLine();
                        if (!gameView.getMap().isOver()) {
                            gameView.setBlock(nextBlockView.getBlock());
                            nextBlockView.setBlock(new Block());
                            TextView textView = findViewById(R.id.score);
                            textView.setText("score" + score);
                        } else {
                            timer.cancel();
                            String s;
                            s = String.valueOf(score);
                            new AlertDialog.Builder(MainActivity.this)
                                    .setTitle("提示")
                                    .setMessage("游戏结束你的得分是：" + s)
                                    .setIcon(R.mipmap.ic_launcher)
                                    .setPositiveButton("退出", (dialogInterface, i) -> {
                                        setResult(RESULT_OK);
                                        finish();
                                        MainActivity.this.finish();
                                    })
                                    .setNegativeButton("重新开始", (dialogInterface, i) -> {
                                        setTitle("俄罗斯方块");
                                        score = 0;
                                        setContentView(R.layout.begin);
                                        Button startButton = findViewById(R.id.start_button);
                                        startButton.setOnClickListener(MainActivity.this);
                                        RadioGroup level = findViewById(R.id.level);
                                        easy = findViewById(R.id.easy);
                                        medium = findViewById(R.id.medium);
                                        hard = findViewById(R.id.hard);
                                        level.setOnCheckedChangeListener(MainActivity.this);
                                        Button exit = findViewById(R.id.exit_button);
                                        exit.setOnClickListener(MainActivity.this);
                                        timer = new Timer();
                                    }).show();
                        }
                        break;
                    }
                    gameView.invalidate();
                    break;
            }
        }
    };

    private class MyTask extends TimerTask {
        @Override
        public void run() {
            Message message = new Message();
            message.what = 1;
            handler.sendMessage(message);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

}
