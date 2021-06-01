package mx.xul.game;

// Pantalla que implmenta la información de ayuda de enemigos
// Autor: Eduardo Alejandro García y Carlos Arroyo

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;

public class PantallaAyudaHabitantes extends Pantalla {
    private Lux juego;
    private Texture texturaFondo;
    private Stage escenaMenu;
    private HijoOscuridad hijoOscuro;
    private Texture texturaHijoOscuro;
    private Bloque bloque;
    private Texture texturaBloque;
    private float time = 0;
    private Texture texturaInfo;

    public PantallaAyudaHabitantes(Lux juego) {

        this.juego = juego;
    }

    @Override
    public void show() {
        texturaFondo = new Texture("PantallaAyuda/pantallasAyudaSubFondo.jpg");
        texturaInfo = new Texture("PantallaAyuda/pantallaAyudaHijos.png");
        texturaHijoOscuro = new Texture("Personajes/HijoOsc_sprite.png");
        hijoOscuro = new HijoOscuridad(texturaHijoOscuro,ANCHO/2.6f, ALTO*3/4.2f, 5, 2, 1/10f, 2);
        texturaBloque = new Texture("Personajes/bloque.png");
        bloque = new Bloque(texturaBloque, ANCHO*3/4.85f, ALTO/3.33f, 3, 2, 1/8f, 2);
        // escenaMenu = new Stage(vista);
        crearAyudaHabitantes();

        // Bloquear la tecla de back
        Gdx.input.setCatchKey(Input.Keys.BACK,true);
    }

    private void crearAyudaHabitantes() {
        // texturaFondo = new Texture("Ayuda/Slide3.jpeg");
        escenaMenu = new Stage(vista);

        Button btnBack = crearBoton("Menu/buttonback.png", "Menu/clickback.png");
        btnBack.setPosition(160, ALTO-80, Align.center);
        btnBack.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // juego.setScreen(new PantallaAyuda(juego));
                juego.setScreen(new PantallaCargando(juego,Pantallasenum.PANTALLAAYUDA));
            }
        });
        escenaMenu.addActor(btnBack);
        Gdx.input.setInputProcessor(escenaMenu);
    }

    private Button crearBoton(String archivo, String archivoClick) {
        Texture texturaBoton = new Texture(archivo);
        TextureRegionDrawable trdBtnBack = new TextureRegionDrawable(texturaBoton);
        Texture texturaClick = new Texture(archivoClick);
        TextureRegionDrawable trdBtnClick = new TextureRegionDrawable(texturaClick);
        return new Button(trdBtnBack, trdBtnClick);
    }

    @Override
    public void render(float delta) {
        borrarPantalla(0, 0, 1);
        time += delta;
        if(Gdx.input.isKeyPressed(Input.Keys.BACK)){
            // Regresar a la pantalla anterior (ACCION)
            juego.setScreen(new PantallaCargando(juego,Pantallasenum.PANTALLAAYUDA));
        }
        batch.setProjectionMatrix(camara.combined);
        batch.begin();
        batch.draw(texturaFondo,0 ,0);
        batch.draw(texturaInfo,0, 0);
        hijoOscuro.animationRender(batch, time);
        bloque.animationRender(batch, time);
        batch.end();
        escenaMenu.draw();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        texturaBloque.dispose();
        texturaFondo.dispose();
        texturaHijoOscuro.dispose();
        texturaInfo.dispose();
    }
}
