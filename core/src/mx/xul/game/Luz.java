package mx.xul.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import mx.xul.game.Objeto;
// Implementa a luz del final del juego
// Autor: Carlos Arroyo
public class Luz extends ObjetoAnimado {
    private float posicionY=0;
    private float angulo=0;

    public Luz(Texture textura, float x, float y, int column, int row, float duracion, int tipo) {
        super(textura, x, y, column, row, duracion, tipo);
        posicionY = y-(textura.getHeight()/2);
    }

    public void moverHorizontal(float dx){
        angulo +=3;
        sprite.setX(sprite.getX()-dx);
        sprite.setY(posicionY+(MathUtils.sinDeg(angulo)*10));
    }

    public Rectangle getRectangle() {
        float margenIzq = 70;
        float margenDer = 70;
        float margenAbajo = 70;
        float margenArriba = 70;
        Rectangle rectangle = new Rectangle();
        rectangle.setX(sprite.getX()+margenIzq);
        rectangle.setY(sprite.getY()+margenAbajo);
        rectangle.setWidth(sprite.getWidth()-margenIzq-margenDer);
        rectangle.setHeight(sprite.getHeight()-margenAbajo-margenArriba);
        return rectangle;
    }
}
