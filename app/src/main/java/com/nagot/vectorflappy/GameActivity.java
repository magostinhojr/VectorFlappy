package com.nagot.vectorflappy;

import android.app.Activity;
import android.os.Bundle;

/*
Esta atividade controla o que será mostrado no game. Não existe um layout mostrando esta activity. Ela
é totalmente gerenciada pela VFView.java que mostra os elementos na tela.
 */

public class GameActivity extends Activity {

    private VFView gameView;

    /*
    No onCreate instanciamos o objeto gameView e o chamamos no setContentView
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gameView = new VFView(this);
        setContentView(gameView);
    }

    /*
    No onPause chamamos o método pause() da classe VGView. Este método pausa a thread caso
    o jogador pause ou caso algo de maior prioridade seja invocado, como uma ligação, por exemplo
     */

    @Override
    protected void onPause(){
        super.onPause();
        gameView.pause();
    }

    /*
    No onResume chamamos o método resume() da classe VGView. Este método despausa a thread
     */

    @Override
    protected void onResume() {
        super.onResume();
        gameView.resume();
    }
}
