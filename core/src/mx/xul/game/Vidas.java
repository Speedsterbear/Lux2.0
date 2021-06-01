package mx.xul.game;


/*
Representa al conjunto de vidas que tiene el personaje.
El personaje solo tendrá  3 vidas.
Autor: Carlos Uriel Arroyo
 */

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Vidas
{
    // Para las Vidas

    // Vida de hasta la izquierda
    protected LuzVida vidaUno;

    // Vida que va en medio
    protected LuzVida vidaDos;

    // Vida de hasta la derecha
    protected LuzVida vidaTres;

    //Contador de la vida
    protected boolean inicial = false; //Se coloca en true luego de que aparecen las tres Vidas iniciales.

    // Método que posisiciona las vidas en la pantalla
    public Vidas(Texture textura, float x, float y) {
        vidaTres =  new LuzVida(textura,x,y,5,1,1/8f,6);

        vidaDos =  new LuzVida(textura,x-(textura.getWidth()*3/10f),y,5,1,1/8f,6);

        vidaUno =  new LuzVida(textura,x-(textura.getWidth()*6/10f),y,5,1,1/8f,6);
    }

    public void vidasRender (int numeroVida, SpriteBatch batch) {
        switch (numeroVida){
            case 3:
                casoInicial(batch);
                break;
            case 2:
                vidaUno.frameRender(batch);
                vidaDos.frameRender(batch);
                vidaTres.transicionRender(batch);
                break;
            case 1:
                vidaUno.frameRender(batch);
                vidaDos.transicionRender(batch);
                vidaTres.transicionRender(batch);
                break;
            case 0:
                vidaUno.transicionRender(batch);
                vidaDos.transicionRender(batch);
                vidaTres.transicionRender(batch);
                break;
            default:
                vidaUno.transicionRender(batch);
                vidaDos.transicionRender(batch);
                vidaTres.transicionRender(batch);
                break;
        }

    }

    //Al inicio se hace la animación de que aparecen las 3 vidas y luego se mantienen los 3 simbolos de las 3 vias hasta que se hace el cambio.
    public void casoInicial(SpriteBatch batch) {
        if (inicial) {
            //Se muestras los 3 simbolos de que tiene 3 vidas
            vidaUno.frameRender(batch);
            vidaDos.frameRender(batch);
            vidaTres.frameRender(batch);
        } else {
            //Aparecen los 3 simbolos de las vidas
            vidaUno.transicionRender(batch);
            vidaDos.transicionRender(batch);
            vidaTres.transicionRender(batch);

            if(vidaUno.animationfinalizada()){
                //Hace que la animación ocurra en la secuencia de quitar vida, pues así se usará siempre apartir de aqui.
                vidaUno.animationUpdate(1/8f, 5);
                vidaDos.animationUpdate(1/8f, 5);
                vidaTres.animationUpdate(1/8f, 5);

                //Resetea la trancición para que al llamarse de nuevo se vea dicha animación.
                vidaUno.transicionReset();
                vidaDos.transicionReset();
                vidaTres.transicionReset();
                inicial = true;

            }

        }

    }

}
