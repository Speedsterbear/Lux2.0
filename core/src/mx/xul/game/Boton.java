package mx.xul.game;

/*
Esta clase sirve para simular los botonoes cuando no se pueda agregar la escena que los controla.
Autores: Carlos Arroyo y David Gaona
 */

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Boton {
    protected Objeto boton;
    protected Texture texturaBtnClicked;
    protected boolean isClicked = false;

    public Boton(Texture texturaBtn, Texture texturaClicked, float x, float y) {
        boton = new Objeto(texturaBtn,x,y);
        texturaBtnClicked = texturaClicked;
    }

    public void active () {
        isClicked = true;
    }

    public void inactive () {
        isClicked = false;
    }

    public void disabled (){
        boton.sprite.setAlpha(0.5f);
    }

    public void abled () {
        boton.sprite.setAlpha(1);
    }

    public void render (SpriteBatch batch) {
        if (isClicked){
            batch.draw(texturaBtnClicked, boton.sprite.getX(),boton.sprite.getY());
        }else {
            boton.render(batch);
        }
    }

    public Rectangle getRectangle (float margen) {

        float xBoton = boton.sprite.getX()+margen;
        float yBoton = boton.sprite.getY()+margen;
        float anchoBoton = boton.sprite.getWidth()-(2*margen);
        float altoBoton = boton.sprite.getHeight()-(2*margen);

        Rectangle rectBoton = new Rectangle(xBoton,yBoton,anchoBoton,altoBoton);

        return rectBoton;
    }

}
