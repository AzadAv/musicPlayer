package com.example.geeksmp3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.media.MediaPlayer;
import android.view.View;
import android.os.Bundle;
import android.os.Handler;
import android.widget.SeekBar;




public class MainActivity extends AppCompatActivity {

    MediaPlayer mp;
    private int playing;
    private Handler myHandler = new Handler();
    private SeekBar seekBar;

    private boolean isPlayed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Adding the music file to our newly created object
        if(seekBar == null) {
            playing = R.raw.air;
            mp = MediaPlayer.create(this, playing);

            seekBar = (SeekBar) findViewById(R.id.seekBar);
        }

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                double position = mp.getDuration() * ((double)seekBar.getProgress() / 100);
                mp.seekTo((int)position);
            }
        });

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mp.isPlaying()){
            mp.stop();
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        isPlayed = false;
        outState.putInt("POSITION_STATE", mp.getCurrentPosition());
        outState.putInt("SONG_PLAYING", playing);
        outState.putBoolean("IS_PLAYED", isPlayed);
        outState.putInt("SEEK_BAR_PROGRESS", seekBar.getProgress());
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        playing = savedInstanceState.getInt("SONG_PLAYING");
        mp = MediaPlayer.create(this, savedInstanceState.getInt("SONG_PLAYING"));
        mp.seekTo(savedInstanceState.getInt("POSITION_STATE"));
        isPlayed = savedInstanceState.getBoolean("IS_PLAYED");

        seekBar = (SeekBar) findViewById(R.id.seekBar);
        seekBar.setProgress(savedInstanceState.getInt("SEEK_BAR_PROGRESS"));
    }

    //play air beats
    public void clickedAirBeats(View v){

        mp.pause();
        playing = R.raw.air;
        mp = MediaPlayer.create(this,playing);
        isPlayed = true;
        seekBar.setProgress(0);
        myHandler.post(seekBarProgressThread);
        mp.start();


    }

    //play water beats
    public void clickedWaterBeats(View v){

        mp.pause();
        playing = R.raw.water;
        mp = MediaPlayer.create(this,playing);
        isPlayed = true;
        seekBar.setProgress(0);
        myHandler.post(seekBarProgressThread);
        mp.start();
    }

    //play wind beats
    public void clickedWindBeats(View v){

        mp.pause();
        playing = R.raw.wind;
        mp = MediaPlayer.create(this,playing);
        isPlayed = true;
        seekBar.setProgress(0);
        myHandler.post(seekBarProgressThread);
        mp.start();
    }
    //play fire beats
    public void clickedFireBeats(View v){

        mp.pause();
        playing = R.raw.fire;
        mp = MediaPlayer.create(this,playing);
        isPlayed = true;
        seekBar.setProgress(0);

        mp.start();
    }

    //Play
    public void musicplay(View v){

        mp.start();
        isPlayed=true;
        myHandler.post(seekBarProgressThread);
    }

    // Pause
    public void musicpause(View v)
    {
        mp.pause();
        isPlayed =false;
        myHandler.removeCallbacks(seekBarProgressThread);
    }

    // Stop
    public void musicstop(View v)
    {
        mp.stop();
        mp= MediaPlayer.create(
                this, R.raw.wind);
        isPlayed=false;
        seekBar.setProgress(0);
        mp.seekTo(0);
        myHandler.removeCallbacks(seekBarProgressThread);
        //onDestroy();
    }

    // rewind
    public void rewind(View view) {
        mp.seekTo(mp.getCurrentPosition() - 5000);
    }
    //forward
    public void forward(View view) {
        mp.seekTo(mp.getCurrentPosition() + 5000);
    }


    private Runnable seekBarProgressThread = new Runnable() {
        @Override
        public void run() {
            double position = (double) mp.getCurrentPosition() / mp.getDuration() * 100;
            seekBar.setProgress((int)position);
            if (mp.getCurrentPosition() < mp.getDuration()-1){
                myHandler.post(this);
            } else {

                isPlayed = false;
                seekBar.setProgress(0);
                mp.seekTo(0);
                myHandler.removeCallbacks(this);
            }
            if (!isPlayed) {
                myHandler.removeCallbacks(this);
            }
        }

    };
}