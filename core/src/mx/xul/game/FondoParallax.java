package mx.xul.game;
/*
Esta clase representa el fondo del juego. Por medio de ésta se puede crear el efecto "Parallax"
Para esta clase las texturas se nombran como capas y se dibujan en orden ascendente.
Capa 0
Capa Estática
Capa 1
Capa 2
Capa 3
Capa 4
Autor: Carlos Arroyo
 */

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class FondoParallax {

    //Se declaran las texturas que se necesitarán para el efecto Parallax
    protected Texture capaCero;
    protected Texture capaEstatica;
    protected Texture capaUno;
    protected Texture capaDos;
    protected Texture capaTres;
    protected Texture capaCuatro;
    protected Texture capaFrente;
    protected Texture capaFrenteVerde;
    protected Texture capaFrenteaDibujar;
    protected Objeto capaVerdeUno;
    protected Objeto capaVerdeDos;

    protected float xCapaCero =0;
    protected float xCapaUno =0;
    protected float xCapaDos=0;
    protected float xCapaTres=0;
    protected float xCapaCuatro=0;
    protected float xCapaFrente=0;

    public FondoParallax (Texture texCapaCero,Texture texCapaEstatica,Texture texCapaUno,Texture texCapaDos,
                          Texture texCapaTres, Texture texCapaCuatro,Texture texCapaFrente,
                          Texture texCapaFrenteVerde) {
        capaCero = texCapaCero;
        capaEstatica = texCapaEstatica;
        capaUno = texCapaUno;
        capaDos = texCapaDos;
        capaTres = texCapaTres;
        capaCuatro = texCapaCuatro;
        capaFrente = texCapaFrente;
        capaFrenteVerde = texCapaFrenteVerde;

        capaFrenteaDibujar = capaFrente;
        capaVerdeUno = new Objeto(capaFrenteVerde,640,360);
        capaVerdeUno.sprite.setAlpha(0);
        capaVerdeDos = new Objeto(capaFrenteVerde,640,360);
        capaVerdeDos.sprite.setAlpha(0);

    }

    public void actualizar (float velocidad, float delta, EstadoSeccion seccion){
        //Capa 0
        xCapaCero -= velocidad*.05f*delta;
        if (xCapaCero <= -capaCero.getWidth()) {
            xCapaCero = 0;
        }

        //Capa 1
        xCapaUno-= velocidad*.1f*delta;
        if (xCapaUno <= -capaUno.getWidth()) {
            xCapaUno = 0;
        }

        //Capa 2
        xCapaDos-= velocidad*.3f*delta;
        if (xCapaDos <= -capaDos.getWidth()) {
            xCapaDos = 0;
        }


        //Capa 3
        xCapaTres -= velocidad*.65f*delta;
        if (xCapaTres <= -capaTres.getWidth()) {
            xCapaTres = 0;
        }

        //Capa 4
        xCapaCuatro -= velocidad*delta;
        if (xCapaCuatro <= -capaCuatro.getWidth()) {
            xCapaCuatro = 0;
        }

        //Capa Frente
        xCapaFrente -= velocidad*1.4f*delta;
        if (xCapaFrente <= -capaFrenteaDibujar.getWidth()) {
            xCapaFrente = 0;
            capaFrente= capaFrenteaDibujar;
        }

        capaVerdeUno.sprite.setX(xCapaFrente);
        capaVerdeDos.sprite.setX(xCapaFrente+capaVerdeDos.sprite.getWidth());

        if(seccion!=EstadoSeccion.VERDE){
            capaVerdeUno.fadeIn(delta,6);
            capaVerdeDos.fadeIn(delta,6);
            //capaFrenteaDibujar = capaFrenteVerde;

        }
    }

    public void render (SpriteBatch batch){

        // Se dibujan las texturas

        //Capa 0
        batch.draw(capaCero,xCapaCero,0);
        batch.draw(capaCero, xCapaCero+ capaCero.getWidth(),0);

        //Capa Estática
        batch.draw(capaEstatica,0,0);

        //Capa 1
        batch.draw(capaUno,xCapaUno,0);
        batch.draw(capaUno, xCapaUno+ capaUno.getWidth(),0);

        //Capa 2
        batch.draw(capaDos,xCapaDos,0);
        batch.draw(capaDos, xCapaDos+ capaDos.getWidth(),0);

        //Capa 3
        batch.draw(capaTres,xCapaTres,0);
        batch.draw(capaTres, xCapaTres+ capaTres.getWidth(),0);

        //Capa 4
        batch.draw(capaCuatro,xCapaCuatro,0);
        batch.draw(capaCuatro, xCapaCuatro+ capaCuatro.getWidth(),0);
    }

    public void renderFrente (SpriteBatch batch){
        //Capa Frente
        batch.draw(capaFrente,xCapaFrente,0);
        batch.draw(capaFrenteaDibujar, xCapaFrente+ capaFrente.getWidth(),0);
        capaVerdeUno.render(batch);
        capaVerdeDos.render(batch);
    }

}
