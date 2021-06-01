package mx.xul.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
// Implementa al personaje verde
// Autor: Carlos Arroyo

public class Esgrun extends Objeto {
    private float posicionY=0;
    private float angulo=0;
    private float avanceXSalida = 0;
    private float avanceYSalida = 0;
    private float tiempoSalida = 1;

    public Esgrun(Texture textura, float x, float y){
        super(textura, x, y);
        posicionY = y-(textura.getHeight()/2);
    }

    public void moverHorizontal(float dx){
        angulo +=3;
        sprite.setX(sprite.getX()-dx);
        sprite.setY(posicionY+(MathUtils.sinDeg(angulo)*10));
    }



    public void dibujar(SpriteBatch batch){
        sprite.draw(batch);
    }

    Rectangle getRectangle() {
        float margenIzq = 20;
        float margenDer = 20;
        float margenAbajo = 20;
        float margenArriba = 20;
        Rectangle rectangle = new Rectangle();
        rectangle.setX(sprite.getBoundingRectangle().getX()+(margenIzq*sprite.getScaleX()));
        rectangle.setY(sprite.getBoundingRectangle().getY()+(margenAbajo*sprite.getScaleY()));
        rectangle.setWidth(sprite.getBoundingRectangle().getWidth()-(margenIzq+margenDer)*sprite.getScaleX());
        rectangle.setHeight(sprite.getBoundingRectangle().getHeight()-(margenAbajo+margenArriba)*sprite.getScaleY());
        return rectangle;
    }
}
