package com.example.weilai.myapplication;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 启动活动
 * Created by weilai on 2016/11/1.
 */
public class MainActivity extends Activity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener, RadioGroup.OnCheckedChangeListener {

    // -------------------------------- //
    private RadioButton easy;
    private RadioButton medium;
    private RadioButton hard;
    private Button right;
    private Button left;
    private Button down;
    private Button change;
    // -------------------------------- //
    private Boolean music = false;
    private int level = 500;
    // -------------------------------- //
    private GameView gameView;
    private NextBlockView nextBlockView;
    private MediaPlayer media;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.begin);
        Button startButton = findViewById(R.id.start_button);
        Button exit = findViewById(R.id.exit_button);
        ToggleButton sound = findViewById(R.id.ck_background_music);
        RadioGroup level = findViewById(R.id.level);
        hard = findViewById(R.id.hard);
        easy = findViewById(R.id.easy);
        medium = findViewById(R.id.medium);
        startButton.setOnClickListener(this);
        sound.setOnCheckedChangeListener(this);
        level.setOnCheckedChangeListener(this);
        exit.setOnClickListener(this);
        music = sound.isChecked();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.start_button:
                setContentView(R.layout.game);
                gameView = findViewById(R.id.gmview);
                nextBlockView = findViewById(R.id.nbview);
                right = findViewById(R.id.right);
                left = findViewById(R.id.left);
                down = findViewById(R.id.down);
                change = findViewById(R.id.change);
                right.setOnClickListener(this);
                left.setOnClickListener(this);
                change.setOnClickListener(this);
                down.setOnClickListener(this);
                timer.scheduleAtFixedRate(new MyTask(), 1, level);
                if (music) {
                    media = MediaPlayer.create(this, R.raw.music);
                    media.setLooping(true);
                    media.start();
                }
                Log.d("Test", "变量的值是 music:" + music + " level:" + level);
                break;
            case R.id.exit_button:
                finish();
                break;
            case R.id.right:
                gameView.block.right(gameView.map.map);
                gameView.invalidate();
                break;
            case R.id.left:
                gameView.block.left(gameView.map.map);
                gameView.invalidate();
                break;
            case R.id.down:
                gameView.block.down(gameView.map.map);
                gameView.invalidate();
                break;
            case R.id.change:
                gameView.block.change(gameView.map.map);
                gameView.invalidate();
                break;
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        switch (compoundButton.getId()) {
            case R.id.ck_background_music:
                if (compoundButton.isChecked()) {
                    Toast.makeText(this, "打开声音", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(this, "关闭声音", Toast.LENGTH_SHORT).show();
                }
                music = isChild();
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
            gameView.block.change(gameView.map.map);
            gameView.invalidate();
            return true;
        }
        if (keyCode == KeyEvent.KEYCODE_DPAD_DOWN) {
            gameView.block.down(gameView.map.map);
            gameView.invalidate();
            return true;
        }
        if (keyCode == KeyEvent.KEYCODE_DPAD_LEFT) {
            gameView.block.left(gameView.map.map);
            gameView.invalidate();
            return true;
        }
        if (keyCode == KeyEvent.KEYCODE_DPAD_RIGHT) {
            gameView.block.right(gameView.map.map);
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
                    if (!gameView.block.down(gameView.map.map)) {
                        gameView.map.resetMap(gameView.block.ps);
                        score = score + 100 * gameView.map.deleteLine();
                        if (!gameView.map.isOver()) {
                            gameView.block = nextBlockView.block;
                            nextBlockView.block = new Block();
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
                                        ToggleButton sound = findViewById(R.id.ck_background_music);
                                        sound.setOnCheckedChangeListener(MainActivity.this);
                                        RadioGroup level = findViewById(R.id.level);
                                        easy = findViewById(R.id.easy);
                                        medium = findViewById(R.id.medium);
                                        hard = findViewById(R.id.hard);
                                        level.setOnCheckedChangeListener(MainActivity.this);
                                        Button exit = findViewById(R.id.exit_button);
                                        exit.setOnClickListener(MainActivity.this);
                                        timer = new Timer();
                                        music = sound.isChecked();
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
        media.pause();
    }

}
