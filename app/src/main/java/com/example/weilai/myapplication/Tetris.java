package com.example.weilai.myapplication;

import android.app.Activity;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by weilai on 2016/11/1.
 */

public class Tetris extends Activity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener, RadioGroup.OnCheckedChangeListener {
    RadioButton easy, medium, hard;
    Boolean music = false;
    int level = 500;

    GameView gameView;
    NextBlockView nextBlockView;
    MediaPlayer media;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.begin);
        Button startButton = (Button) findViewById(R.id.start_button);
        startButton.setOnClickListener(this);
        CheckBox sound = (CheckBox) findViewById(R.id.ck_background_music);
        sound.setOnCheckedChangeListener(this);
        RadioGroup level = (RadioGroup) findViewById(R.id.level);
        easy = (RadioButton) findViewById(R.id.easy);
        medium = (RadioButton) findViewById(R.id.medium);
        hard = (RadioButton) findViewById(R.id.hard);
        level.setOnCheckedChangeListener(this);
        Button exit = (Button) findViewById(R.id.exit_button);
        exit.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.start_button:
                setContentView(R.layout.game);
                gameView = (GameView) findViewById(R.id.gmview);
                nextBlockView = (NextBlockView) findViewById(R.id.nbview);
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
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        System.out.println(isChild());
        music = isChild();
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

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    if (!gameView.block.down(gameView.map.map)) {
                        gameView.map.resetmap(gameView.block.ps);
                        score = score + 100 * gameView.map.deleteline();
                        if (!gameView.map.isover()) {
                            gameView.block = nextBlockView.block;
                            nextBlockView.block = new Block();
                            TextView textView = (TextView) findViewById(R.id.score);
                            textView.setText("score" + score);
                        } else {
                            timer.cancel();
                            String s;
                            s = String.valueOf(score);
                            new AlertDialog.Builder(Tetris.this)
                                    .setTitle("提示")
                                    .setMessage("游戏结束你的得分是：" + s)
                                    .setIcon(R.mipmap.ic_launcher)
                                    .setPositiveButton("退出", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            setResult(RESULT_OK);
                                            finish();
                                            Tetris.this.finish();
                                        }
                                    })
                                    .setNegativeButton("重新开始", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            setTitle("俄罗斯方块");
                                            score = 0;
                                            setContentView(R.layout.begin);
                                            Button startButton = (Button) findViewById(R.id.start_button);
                                            startButton.setOnClickListener(Tetris.this);
                                            CheckBox sound = (CheckBox) findViewById(R.id.ck_background_music);
                                            sound.setOnCheckedChangeListener(Tetris.this);
                                            RadioGroup level = (RadioGroup) findViewById(R.id.level);
                                            easy = (RadioButton) findViewById(R.id.easy);
                                            medium = (RadioButton) findViewById(R.id.medium);
                                            hard = (RadioButton) findViewById(R.id.hard);
                                            level.setOnCheckedChangeListener(Tetris.this);
                                            Button exit = (Button) findViewById(R.id.exit_button);
                                            exit.setOnClickListener(Tetris.this);
                                            timer = new Timer();
                                        }
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
}
