package mx.xul.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
// Pantalla que implementa la información de ayuda de la oscuridad
// Autor: Eduardo Alejandro García y Carlos Arroyo
public class PantallaAyudaOscuridad extends Pantalla {
    private Lux juego;
    private Texture texturaFondo;
    private Stage escenaMenu;
    private Oscuridad oscuridad;
    private Texture texturaOscuridad;
    private float time = 0;
    private Texture texturaInfo;

    private float angulo=0;//Acumulador para movimiento usando la función Seno.
    private final float POSICIÓNX_INIICAL_OSCURIDAD =0;

    public PantallaAyudaOscuridad(Lux juego) {

        this.juego = juego;
    }

    @Override
    public void show() {
        texturaFondo = new Texture("PantallaAyuda/pantallasAyudaSubFondo.jpg");
        texturaInfo = new Texture("PantallaAyuda/PantallaAyudaOscuridad.png");
        texturaOscuridad = new Texture("Personajes/oscuridad.png");
        oscuridad = new Oscuridad(texturaOscuridad, POSICIÓNX_INIICAL_OSCURIDAD, ALTO/2, 2, 2, 1/10f,2);
        crearAyudaOscuridad();
        // Bloquear la tecla de back
        Gdx.input.setCatchKey(Input.Keys.BACK,true);
    }

    private void crearAyudaOscuridad() {
        // texturaFonfo = new Texture("Ayuda/Slide2.jpeg");
        escenaMenu = new Stage(vista);

        Button btnBack = crearBoton("Menu/buttonback.png", "Menu/clickback.png");
        btnBack.setPosition(160, ALTO-80, Align.center);
        btnBack.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // juego.setScreen(new PantallaAyuda(juego));
                juego.setScreen(new PantallaCargando(juego, Pantallasenum.PANTALLAAYUDA));
            }
        });

        escenaMenu.addActor(btnBack);
        Gdx.input.setInputProcessor(escenaMenu);
    }

    private Button crearBoton(String archivo, String archivoClick) {
        Texture textureBoton = new Texture(archivo);
        TextureRegionDrawable trdBtnBack = new TextureRegionDrawable(textureBoton);
        Texture texturaClick = new Texture(archivoClick);
        TextureRegionDrawable trdBtnClick = new TextureRegionDrawable(texturaClick);
        return new Button(trdBtnBack, trdBtnClick);
    }

    @Override
    public void render(float delta) {
        borrarPantalla(0, 0, 1);
        time += delta;
        moverOscuridad();
        if(Gdx.input.isKeyPressed(Input.Keys.BACK)){
            // Regresar a la pantalla anterior (ACCION)
            juego.setScreen(new PantallaCargando(juego,Pantallasenum.PANTALLAAYUDA));
        }
        batch.setProjectionMatrix(camara.combined);
        batch.begin();
        batch.draw(texturaFondo, 0, 0);
        batch.draw(texturaInfo, 0, 0);
        oscuridad.animationRender(batch, time);
        batch.end();
        escenaMenu.draw();
    }

    private void moverOscuridad() {
        angulo += 1;
        oscuridad.sprite.setX((POSICIÓNX_INIICAL_OSCURIDAD-(oscuridad.sprite.getWidth()/2))+(MathUtils.sinDeg(angulo)*ANCHO/12f));
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        texturaFondo.dispose();
        texturaInfo.dispose();
        texturaOscuridad.dispose();
    }
}
