package com.nagot.vectorflappy;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by Nagot on 31/05/2016.
 */

/*
Esta classe desenha todos os elementos na tela e controla alguns outros elementos
 */
public class VFView extends SurfaceView implements Runnable {

    volatile boolean playing;
    Thread gameThread = null;
    private PlayerShip player;
    private Paint paint;
    private Canvas canvas;
    private SurfaceHolder ourHolder;

    /*
    Neste construtor criamos um SurfaceHolder ourHolder para travar nossa canvas quando for desenha-la.
    Criamos também um objeto paint para desenhar na tela.
    Por fim, instanciamos nossa nave passando como contexto esta atividade.
     */

    public VFView(Context context) {
        super(context);
        ourHolder = getHolder();
        paint = new Paint();
        player = new PlayerShip(context);
    }

    /*
    Aqui damos override no método run, que será executado toda vez que esta classe for chamada.
    Quando ela for executada, chamará o método update(), draw() e control().
     */
    @Override
    public void run() {
        while (playing) {
            update();
            draw();
            control();
        }
    }

    // Chama o método update() da classe PlayerShip que adiciona + 1 para a variáve x toda vez que executado.

    private void update() {
        player.update();
    }

    /*
    Este método desenha os elementos na tela. Ele checa se o SurfaceHolder é válido. Se sim,
    Ele irá travar o canvas para que possamos desenhar nele.
    Chamamos o objeto canvas.drawColor e atribuimos uma cor a ele juntamente com seu alfa.
    Por fim, chamamos o canvas.drawBitmap especificando a imagem .png pelo método player.getBitmap(),
    pegando a posição x por player.getX(), de y por player.getY() e, por fim, desenhamos chamando paint.
    Por último, destravamos o canvas por intermédio de nossa variável ourHolder

     */

    private void draw() {
        if (ourHolder.getSurface().isValid()) {

            canvas = ourHolder.lockCanvas();

            canvas.drawColor(Color.argb(255, 0, 0, 0));

            canvas.drawBitmap(
                    player.getBitmap(),
                    player.getX(),
                    player.getY(),
                    paint);

            ourHolder.unlockCanvasAndPost(canvas);
        }
    }

    /*
    Chama o método da classe Thread sleep(). Este método trabalha com milisegundos. Para dar a sensação de 60 FPS,
     dividimos 1000/60 onde o resultado é aproximadamente 17.
     */

    private void control() {
        try {
            gameThread.sleep(17);
        } catch (InterruptedException e) {

        }
    }

    /*
    Aqui pausamos a thread. Caso playing = false chamaremos a classe join(), que espera a thread morrer.
     */

    public void pause() {
        playing = false;
        try {
            gameThread.join();
        } catch (InterruptedException e) {

        }
    }

    /*
    Caso playing = true, a thread é começa a rodar novamente.
     */

    public void resume() {
        playing = true;
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_UP:
                player.stopBoosting();
                break;
            case MotionEvent.ACTION_DOWN:
                player.setBoosting();
                break;
        }
        return true;
    }
}


