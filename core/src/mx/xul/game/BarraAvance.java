package mx.xul.game;

/*
Sirve para representar la barra de avance que lleva el personaje principal.
Autor: Carlos Arroyo
 */

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;

public class BarraAvance {

    protected ShapeRenderer rectangulo;
    protected ShapeRenderer circulo;
    protected float px = 0;
    protected float py = 0;
    protected float h = 0;
    protected float w = 0;
    protected float d = 0;
    protected float recorrido = 0;

    public BarraAvance (float r, float g, float b, float alfa, float x, float y, float height,float widthfinal,float distancia) {

        rectangulo = new ShapeRenderer();
        rectangulo.setColor(r,g,b,alfa);
        circulo = new ShapeRenderer();
        circulo.setColor(1,1,1,1);
        px = x;
        py = y-(height/2); // Para que el centro sea la mitad
        h = height;
        w = widthfinal;
        d = distancia;

    }

    public void renderAvance (float distancia, OrthographicCamera camara){
        //recorrido  += delta * velocidad * w /d;
        recorrido = distancia*w/d;
        rectangulo.setProjectionMatrix(camara.combined);
        rectangulo.begin(ShapeRenderer.ShapeType.Filled);
        rectangulo.box(px,py,0,recorrido,h,0);
        rectangulo.end();
        circulo.setProjectionMatrix(camara.combined);
        circulo.begin(ShapeRenderer.ShapeType.Filled);
        circulo.circle(px+recorrido,py+(h/2),h+1);
        circulo.end();
    }

    public void renderEstatico (OrthographicCamera camara){
        rectangulo.setProjectionMatrix(camara.combined);
        rectangulo.begin(ShapeRenderer.ShapeType.Filled);
        rectangulo.box(px,py,0,w,h,0);
        rectangulo.end();
    }

    public void renderAvanceCarga (float distancia, OrthographicCamera camara){
        recorrido = distancia*w/d;
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        rectangulo.setProjectionMatrix(camara.combined);
        rectangulo.begin(ShapeRenderer.ShapeType.Filled);
        rectangulo.box(px,py,0,recorrido,h,0);
        rectangulo.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);
    }

}
