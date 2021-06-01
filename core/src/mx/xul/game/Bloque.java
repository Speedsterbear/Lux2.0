package mx.xul.game;
/*
Esta Clase representa cada uno de los obstáculos que el personaje principal debe esquivar.
Autor:  Carlos Arroyo
 */

import com.badlogic.gdx.graphics.Texture;

public class Bloque extends ObjetoAnimado{

    protected boolean isBloqueTouchedAnterior = false;

    public Bloque(Texture textura, float x, float y, int column, int row, float duracion, int tipo) {
        super(textura, x, y, column, row, duracion, tipo);
        setAnimationSetion(0,2);
    }

    //Este método sirve para mover los bloques.
    // Recibe de parámetro la velocidad y el tiempo para calcular su desplazamiento

    public void mover (float delta, float velocidad){
        float dx = velocidad*delta*.35f;
        sprite.setX(sprite.getX()-dx);
    }

    public void cambiarBloque (boolean isBloqueTouched) {
        if (isBloqueTouched) {
            if (isBloqueTouchedAnterior == false) {
                setAnimationSetion(3, 5);
            }
        } else {
            if (isBloqueTouchedAnterior) {
                setAnimationSetion(0, 2);
            }
        }
        isBloqueTouchedAnterior = isBloqueTouched;
    }

    public float getX() {
        return sprite.getX();
    }
}


