package mx.xul.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;

// Pantalla que implmenta la oscuridad
// Autor: Ricardo Solis y Carlos Arroyo

public class Oscuridad extends ObjetoAnimado
{

    private int faseSecuenciaMordida = 0;
    private boolean yaMordio = false;

    public Oscuridad(Texture textura, float x, float y, int column, int row,float duracion, int tipo) {
        super(textura, x, y, column, row,duracion,tipo);
    }


    //Metodo para mover el Sprite
    // Recibe velocidad de referencia para utilizar velocidad relativa.
    public void mover(float velocidadRef, float velocidadObj, float delta) {

        //Al restar las velocidades se puede simular el movimiento de la oscuridad
        float dx = (velocidadObj - velocidadRef) * delta;
        sprite.setX(sprite.getX() + dx);
    }

    public void reversa(float velocidad) {
        sprite.setX(sprite.getX() - velocidad);
    }

    //Secuencia para simmular que la oscuridad se come a la luz
    public void granMordida (){
        float scale = 1.60f; //Qué tanto va a crecer la oscuridad para que abarque toda la pantalla
        float avance = 35; //Qué tanto avanza la oscuridad

        switch (faseSecuenciaMordida) {
            case 0:
                //Aqui se puede poner el sonido de perder ya que solo se entra a esta sección 1 vez
                faseSecuenciaMordida=1; //Avanzar en la secuencia
                break;
            case 1:
                if (sprite.getX()>= -sprite.getWidth()*scale/1.5f){
                    sprite.setX(sprite.getX()-avance); //se mueve hacia atrás para salirse de la pantalla

                }else {
                    sprite.setScale(scale);//Hacer crecer a la oscuridad y que así su boca cubra toda la pantalla
                    faseSecuenciaMordida = 2; //Avanzar en la seceuencia
                    animationUpdate(1/10f,5);//Para que solo abra la boca
                    sprite.setY(sprite.getY()+50);
                }
                break;
            case 2:
                if (sprite.getX()<= -(sprite.getWidth()*scale)/5){
                    sprite.setX(sprite.getX()+avance); //Se mueve hacia adelante
                } else {
                    animationUpdate(1 / 4f, 6);//Para que solo cierre la boca
                    yaMordio = true;
                }
                break;
        }
    }

    public boolean getYaMordio () {
        return yaMordio;
    }


}
