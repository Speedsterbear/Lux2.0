package mx.xul.game;

/*
Esta clase se usa para crear el efecto de desvanecer a blanco o a negro
Puede usarse para cualquier color.
Esta pantalla crea un rectangulo para no importar una imagen y la sobre pone a la pantalla para dar el efecto de Fade.
Autor: Carlos Uriel Arroyo Herrera.
 */

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Transicion {

    protected ShapeRenderer rectangulo;
    protected float h = 0;
    protected float w = 0;
    protected float colorR=0;
    protected float colorG=0;
    protected float colorB=0;
    protected boolean isFadeOutFinished = false;
    protected boolean isFadeInFinished = false;

    protected float alfaRectangulo=0; // Representa el canal alfa del Sprite

    //Constructor. Inicializa el objeto con la imagen y la posición
    public Transicion(float r, float g, float b, float alfa, float height,float width) {
        rectangulo = new ShapeRenderer();
        rectangulo.setColor(r,g,b,alfa);
        h = height;
        w = width;
        alfaRectangulo = alfa;
        colorR= r;
        colorG=g;
        colorB=b;
    }

    // Dibujar el objeto
    public void render(OrthographicCamera camara)
    {
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        rectangulo.setProjectionMatrix(camara.combined);
        rectangulo.begin(ShapeRenderer.ShapeType.Filled);
        rectangulo.box(0,0,0,w,h,0);
        rectangulo.setColor(colorR,colorG,colorB,alfaRectangulo);
        rectangulo.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);

    }

    // Modifica el valor de Alfa hasta ser 1, por lo tanto sirve para desvanecer a negro
    public void fadeOut(float delta, float fadeDuration) {


        if (alfaRectangulo<1){
            alfaRectangulo += delta/fadeDuration;
            if (alfaRectangulo>=1){
                alfaRectangulo =1;
                isFadeOutFinished = true;
            }
        }
        /*
        if (alfaRectangulo <=1){
            alfaRectangulo += delta/fadeDuration;
        }else {
            alfaRectangulo =1;
            isFadeOutFinished = true;
        }

         */
    }

    // Modifica el valor de Alfa hasta ser 0, por lo tanto sirve para pasar de un fondo negro a uno con colores
    public void fadeIn(float delta, float fadeDuration) {

        if (alfaRectangulo>0){
            alfaRectangulo -= delta/fadeDuration;
            if (alfaRectangulo<=0){
                alfaRectangulo =0;
                isFadeInFinished = true;
            }
        }
    }
}
