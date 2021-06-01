package mx.xul.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
//Esta clase representa el juego
//Autor: Carlos Arroyo

public class HijoOscuridad extends ObjetoAnimado {

    protected boolean isHijoFadingOut = false;
    protected float tiempoHijoSalida=0;

    public HijoOscuridad(Texture textura, float x, float y, int column, int row,float duracion, int tipo) {
        super(textura, x, y, column, row,duracion,tipo);
        setAnimationSetion(0,2);
    }

    public float getTiempoSalida (){
        return tiempoHijoSalida;
    }

    public void incrementoTiempoSalida () {
        if (isHijoFadingOut){
            tiempoHijoSalida += Gdx.graphics.getDeltaTime();
        }
    }

    public boolean isHijoGone (){
        if (tiempoHijoSalida >= 1){
            return  true;
        }
        return false;
    }

    //Metodo para mover el Sprite
    // Recibe velocidad de referencia para utilizar velocidad relativa.
    public void mover(float velocidadRef, float velocidadObj, float delta) {

        //Al restar las velocidades se puede simular el movimiento de la oscuridad
        float dx = (velocidadObj - velocidadRef) * delta;
        sprite.setX(sprite.getX() + dx);

    }

    public float getX() {
        return sprite.getX();
    }

    Rectangle getRectangle() {
        Rectangle rectangle = new Rectangle();
        if (true){
            float margenIzq = 55;
            float margenDer = 10;
            float margenAbajo = 20;
            float margenArriba = 15;
            rectangle.setX(sprite.getBoundingRectangle().getX()+(margenIzq*sprite.getScaleX()));
            rectangle.setY(sprite.getBoundingRectangle().getY()+(margenAbajo*sprite.getScaleY()));
            rectangle.setWidth(sprite.getBoundingRectangle().getWidth()-(margenIzq+margenDer)*sprite.getScaleX());
            rectangle.setHeight(sprite.getBoundingRectangle().getHeight()-(margenAbajo+margenArriba)*sprite.getScaleY());
        }else {
            rectangle = sprite.getBoundingRectangle();
            rectangle.setHeight(0);
            rectangle.setWidth(0);
        }

        return rectangle;

    }


}


