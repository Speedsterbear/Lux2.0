package mx.xul.game;

/*
Representa objetos en el juego (Personajes, enemigos, etc..)
Es particularmente para los no animados
Autor: Carlos Uriel Arroyo Herrera
 */

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Objeto
{
    //protected es para quelas clases que heredan puedan acceder a la variable
    protected Sprite sprite; // Imagen, posición
    protected float alfaSprite=0; // Representa el canal alfa del Sprite

    //Constructor. Inicializa el objeto con la imagen y la posición
    public Objeto(Texture textura, float x, float y) {
        sprite = new Sprite(textura);
        sprite.setPosition(x-(textura.getWidth()/2),y-(textura.getHeight()/2));
    }

    // Dibujar el objeto -> Begin     end<-
    public void render(SpriteBatch batch){
        sprite.draw(batch);
    }

    public void fadeIn (float delta, float fadeDuration) {
        if (alfaSprite<=1){
            alfaSprite += delta/fadeDuration;
            if (alfaSprite>=1){
                alfaSprite =1;
            }
            sprite.setAlpha(alfaSprite);

        }
    }

    public void fadeOut (float delta, float fadeDuration) {
        if (alfaSprite>0){
            alfaSprite -= delta/fadeDuration;
            if (alfaSprite<=0){
                alfaSprite =0;
            }
            sprite.setAlpha(alfaSprite);
        }
    }


}

