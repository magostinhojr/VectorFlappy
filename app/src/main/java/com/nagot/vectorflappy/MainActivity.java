package com.nagot.vectorflappy;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;

// Begin improvements

/*
Classe de entrada do Game. Quando inicializar, será chamado o método
onCreate() da activity
*/
public class MainActivity extends Activity {

    private MediaPlayer player;
    Thread gameThread = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Troca a fonte da title screen

        final TextView title = (TextView) findViewById(R.id.title);
        Typeface customFont = Typeface.createFromAsset(getAssets(),"fonts/Vectorb.otf");
        title.setTypeface(customFont);

        // Cria um MediaPlayer para tocar a música de abertura.

        try {
            AssetManager assetManager = this.getAssets();
            AssetFileDescriptor descriptor;

            descriptor = assetManager.openFd("title.ogg");
            player = new MediaPlayer();
            player.setDataSource(descriptor.getFileDescriptor(), descriptor.getStartOffset(), descriptor.getLength());
            player.setVolume(0.1f, 0.1f);
            player.setLooping(true);
            player.prepare();
            player.start();

        } catch (IOException e) {
            Log.e("error", "failed to load sound file or file is missing");
        }

        // Seta o listener no botão. Ao ser clicado a Activity GameActivity.class será acionada

        SharedPreferences prefs;
        SharedPreferences.Editor editor;

        // Checa se o arquivo HighScores existe. Caso não o cria

        prefs = getSharedPreferences("HighScores", MODE_PRIVATE);

        final Button buttonPlay = (Button) findViewById(R.id.buttonPlay);

        final TextView textFastestTime = (TextView) findViewById(R.id.textHighScore);

        // Coloca a tag fastestTime no arquivo e lhe atribui o valor de 1000000 caso não tenha valor algum

        long fastestTime = prefs.getLong("fastestTime", 1000000);

        // Muda o valor do text view para o encontrado no arquivo

        textFastestTime.setText("Fastest Time: " + fastestTime);

        buttonPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, GameActivity.class);
                startActivity(i);
                player.stop();
                finish();
            }
        });

        // Cria um movimento do background através da classe ObjectAnimator

        ObjectAnimator backgroundAnimation =
                ObjectAnimator.ofFloat(findViewById(R.id.backgroundImageView), "x", 40, -40);
        backgroundAnimation.setDuration(5000);
        backgroundAnimation.setRepeatCount(ValueAnimator.INFINITE);
        backgroundAnimation.setRepeatMode(ValueAnimator.REVERSE);
        backgroundAnimation.start();

    }


    // Caso o jogador aperte o botão back do smartphone o game será finalizado

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            player.stop();
            finish();
        }
        return false;
    }

    @Override
    protected void onPause() {
        super.onPause();
        player.pause();
    }

    @Override
    protected void onStart() {
        super.onStart();
        player.start();
    }
}
