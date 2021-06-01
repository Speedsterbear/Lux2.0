package mx.xul.game;


/*
Representa al cristal de fondo para la pantalla de creditos y la de nosotros.
Autor: Carlos Uriel Arroyo Herrera
 */


import com.badlogic.gdx.graphics.Texture;

public class Cristal extends ObjetoAnimado
{

    public Cristal(Texture textura, float x, float y, int column, int row,float duracion, int tipo){
        super(textura, x, y, column, row,duracion,tipo);

        removeFrame(4);

    }
}