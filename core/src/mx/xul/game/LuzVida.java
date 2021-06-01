package mx.xul.game;

/*
Representa cada una de las vidas del personaje principal
Autor: Carlos Arroyo
 */

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class LuzVida extends ObjetoAnimado {

    //Boleanos de Control
    protected boolean terminoTransicion = false;

    //Tiempo de animaciones
    protected float tiempo = 0f; //Acumulador

    public LuzVida(Texture textura, float x, float y, int column, int row, float duracion, int tipo) {
        super(textura, x, y, column, row, duracion, tipo);
    }

    //Sirve para hacer la animación de restar la vida
    public void transicionRender(SpriteBatch batch) {
        if (terminoTransicion) {
            frameRender(batch);
        } else {
            //float duracionTotal = animation.getAnimationDuration();
            tiempo += Gdx.graphics.getDeltaTime();
            animationRender(batch, tiempo);

            if (animation.isAnimationFinished(tiempo)) {
                terminoTransicion = true;
                tiempo = 0;
            }

        }

    }

    //Sirve para poder resetear la animación y asi poder mostrar nuevamente el movimiento.
    public void transicionReset() {
        terminoTransicion = false;
    }


    //Es para revisar si la animación ya terminó.
    public boolean animationfinalizada() {
        return terminoTransicion;
    }

}




