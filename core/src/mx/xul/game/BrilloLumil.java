package mx.xul.game;

/*
Esta clase sirve para representar el brillo del personaje principal.
Es únicamente estético.
Autor: Carlos Arroyo.
 */

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;

public class BrilloLumil {

    protected Sprite brilloAtras; // Imagen, posición
    protected Sprite brilloFrente; // Imagen, posición

    private float angulo = 0;

    public BrilloLumil(Texture textura, float x, float y) {
        //super(textura, x, y);
        brilloAtras = new Sprite(textura);
        brilloAtras.setPosition(x-textura.getWidth()/2,y-textura.getHeight()/2);

        brilloFrente = new Sprite(textura);
        brilloFrente.setPosition(x-textura.getWidth()/2,y-textura.getHeight()/2);
    }

    //Sirve para darle movimiento vertical al brillo.
    public  void mover (float y){
        brilloFrente.setY(y-(brilloFrente.getHeight()/2));
        brilloAtras.setY(y-(brilloAtras.getHeight()/2));

    }

    public  void moverX (float x){
        brilloFrente.setX(x-(brilloFrente.getWidth()/2));
        brilloAtras.setX(x-(brilloAtras.getWidth()/2));

    }

    public float getXCentro(){
        float x= brilloAtras.getX()+(brilloAtras.getWidth()/2);
        return x;
    }

    public float getYCentro(){
        float y= brilloAtras.getY()+(brilloAtras.getHeight()/2);
        return y;
    }


    //Sirve para hacer la ilusión de brillo
    public void actualizar (float r, float g, float b,float scaleBase){
        angulo ++;
        brilloAtras.rotate(0.3f);
        brilloAtras.setScale(scaleBase + (MathUtils.sinDeg(angulo)*(scaleBase/9f)));
        brilloAtras.setColor(r,g,b,0.5f + (MathUtils.sinDeg(angulo)*0.3f));

        brilloFrente.rotate(-0.3f);
        brilloFrente.setScale(scaleBase*0.7f + (MathUtils.sinDeg(angulo)*(scaleBase/9f)));
        brilloAtras.setAlpha(0.5f + (MathUtils.sinDeg(angulo)*0.1f));

    }

    //Dibuja al brillo Lumil.
    public void render(SpriteBatch batch) {
        brilloAtras.draw(batch);
        brilloFrente.draw(batch);
    }
}
