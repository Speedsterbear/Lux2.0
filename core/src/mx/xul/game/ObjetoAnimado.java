package mx.xul.game;

/*
Representa objetos en el juego (Personajes, enemigos, etc..)
puede usarse tanto para los animados como para los no animados
Autor: Carlos Uriel Arroyo Herrera
 */

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class ObjetoAnimado extends Actor {

    //protected es para quelas clases que heredan puedan acceder a la variable

    //Usar como sprite
    protected Sprite sprite; // Imagen, posición

    //Para animar
    protected Animation animation;
    protected TextureRegion[] regionVector;
    protected TextureRegion [] [] regionMatriz;
    private TextureRegion frameActual;
    private float duracionFrame = 0;
    private int tipoAnimacion = 1;

    //Constructor. Inicializa el objeto con la imagen y la posición
    public ObjetoAnimado(Texture textura, float x, float y, int column, int row, float duracion, int tipo ) {
        //sprite = new Sprite(textura);
        //sprite.setPosition(x - (textura.getWidth()/column/2),y-(textura.getHeight()/row/2));
        regionMatriz = TextureRegion.split(textura,textura.getWidth()/column, textura.getHeight()/row);

        //Convertir la matiz en Vector para poder usarla
        regionVector = new TextureRegion[column*row];
        int k=0;
        for (int i =0; i<row; i++){
            for (int j=0; j<column; j++) {
                regionVector [k] = regionMatriz [i][j];
                k++;
            }
        }

        duracionFrame = duracion;
        tipoAnimacion = tipo;

        //Inicializar el Sprite
        sprite = new Sprite(regionVector [0]);
        sprite.setPosition(x - (sprite.getWidth()/2),y-(sprite.getHeight()/2));

        //Se llama al inició para definir los valores de duración y el tipo de animación del objeto animado
        animation = new Animation (duracion,regionVector);
        animationUpdate(duracion,tipo);
    }

    public void removeFrame (int frameToRemove){
        TextureRegion[] frames = new TextureRegion[regionVector.length-1];

        for (int i =0; i<regionVector.length; i++){
            if (i<frameToRemove){
                frames [i]=regionVector[i];
            } else if (i>frameToRemove){
                frames [i-1]=regionVector[i];
            }
        }

        animation = new Animation (duracionFrame,frames);
        animationUpdate(duracionFrame,tipoAnimacion);
    }

    public void setAnimationSetion (int frameStart,int frameEnd){
        TextureRegion[] frames = new TextureRegion[frameEnd-frameStart+1];
        int j =0;

        for (int i =0; i<regionVector.length; i++){
            if (i>= frameStart && i<= frameEnd){
                frames [j]=regionVector[i];
                j++;
            }
        }

        animation = new Animation (duracionFrame,frames);
        animationUpdate(duracionFrame,tipoAnimacion);

    }

    //Se utiliza para poder cambiar la velocidad de la animación (más rapida o más lenta)
    //Puede usarse tambien para cambiar el tipo de animación
    public void animationUpdate (float duracion, int tipo) {

        duracionFrame = duracion;
        tipoAnimacion = tipo;

        //animation = new Animation (duracion,regionVector);
        animation.setFrameDuration(duracion);

        switch (tipo) {
            case 1:
                animation.setPlayMode(Animation.PlayMode.LOOP);
                break;
            case 2:
                animation.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);
                break;
            case 3:
                animation.setPlayMode(Animation.PlayMode.LOOP_RANDOM);
                break;
            case 4:
                animation.setPlayMode(Animation.PlayMode.LOOP_REVERSED);
                break;
            case 5:
                animation.setPlayMode(Animation.PlayMode.NORMAL);
                break;
            case 6:
                animation.setPlayMode(Animation.PlayMode.REVERSED);
                break;
            default:
                animation.setPlayMode(Animation.PlayMode.LOOP);
                break;
        }

    }


    // Sirve para poder animar el Sprite
    public void animationRender(SpriteBatch batch, float tiempo){
        float Deg = sprite.getRotation();
        float X = sprite.getX();
        float Y = sprite.getY();
        float scaleX = sprite.getScaleX();
        float scaleY = sprite.getScaleY();
        float alfa = sprite.getColor().a;

        frameActual = (TextureRegion) animation.getKeyFrame(tiempo);
        sprite = new Sprite(frameActual);
        sprite.setPosition(X,Y);
        sprite.setRotation(Deg);
        sprite.setScale(scaleX,scaleY);
        sprite.setAlpha(alfa);
        sprite.draw(batch);

    }

    //Sirve para seleccionar cual de los cuadros de la textura es el que Mostrará en el Sprite
    public void setFrame (int row, int column) {
        float Deg = sprite.getRotation();
        float X = sprite.getX();
        float Y = sprite.getY();
        sprite = new Sprite(regionMatriz [row][column]);
        sprite.setPosition(X,Y);
        sprite.setRotation(Deg);
    }

    //Sirve para dibujar uno de los cuadroa (regiones) de la textura sin animación
    public void frameRender (SpriteBatch batch) {
        sprite.draw(batch);
    }

}
