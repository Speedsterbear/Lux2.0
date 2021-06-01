package mx.xul.game;

/*
Esta Clase representa al fondo en movimiento con las diferentes secciones para dar el efecto de parallax.
Autor: Carlos Arroyo
 */

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class FondoEnMovimiento
{

    //Se declaran las texturas que se necesitarán
    protected Texture texturaFon;
    protected Texture texturaAt;
    protected Texture texturaMed;
    protected Texture texturaFre;
    protected float xatras =0;
    protected float xmedio =0;
    protected float xfrente=0;

    public FondoEnMovimiento (Texture texturaFondo, Texture texturaAtras, Texture texturaMedio, Texture texturaFrente) {
        texturaFon = texturaFondo;
        texturaAt = texturaAtras;
        texturaMed = texturaMedio;
        texturaFre = texturaFrente;
    }


    public void movSeccionesCompletas (float velocity, float delta, SpriteBatch batch, boolean shouldmove) {

        if (shouldmove) {

            //Se mueven las texturas a diferentes velocidades

            xatras -= velocity*.05f*delta;
            if (xatras <= -texturaAt.getWidth()) {
                xatras = 0;
            }

            xmedio -= velocity*delta;
            if (xmedio <= -texturaMed.getWidth()) {
                xmedio = 0;
            }

            xfrente -= velocity*delta*3;
            if (xfrente <= -texturaFre.getWidth()) {
                xfrente = 0;
            }

            // Se dibujan las texturas
            batch.draw(texturaFon, 0, 0);

            batch.draw(texturaAt,xatras,0);
            batch.draw(texturaAt, xatras + texturaAt.getWidth(),0);

            batch.draw(texturaMed,xmedio,0);
            batch.draw(texturaMed, xmedio + texturaMed.getWidth(),0);

            batch.draw(texturaFre,xfrente,0);
            batch.draw(texturaFre, xfrente + texturaFre.getWidth()-10,0);

        }


    }

}
