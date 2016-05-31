package com.nagot.vectorflappy;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Created by Nagot on 31/05/2016.
 */

/*
Esta classe é responsável por definir o objeto Ship
 */
public class PlayerShip {
    private Bitmap bitmap;
    private int x, y;
    private int speed = 0;

    /*
    No construtor damos as cordenadas iniciais da nave, seu speed inicial e seu sprite
     */

    public PlayerShip(Context context) {
        x = 50;
        y = 50;
        speed = 1;
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.ship);
    }

    /*
    Adicionamos +1 a variável x
     */

    public void update(){
        x++;
    }

    // Retornamos o valor da variável bitmap

    public Bitmap getBitmap(){
        return bitmap;
    }

    // Retornamos o valor da variável speed

    public int getSpeed(){
        return speed;
    }

    // Retornamos o valor da variável x

    public int getX(){
        return x;
    }

    // Retornamos o valor da variável y

    public int getY() {
        return y;
    }
}
