package mx.xul.game;

// Pantalla que implementa la información de ayuda de los colores primarios
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

public class PantallaAyudaLumil extends Pantalla {

    private Lux juego;
    private Texture texturaFondo;
    private Stage escenaMenu;
    private Lumil lumil;
    private Texture textureLumil;
    private float time = 0;
    private float DELTA_Y = 2; //Avance vertical de lumil
    private Texture texturaBrillo;
    private BrilloLumil brilloLumil;
    private float alturaLumil = ALTO/6; // Representa la altura que se le sumará a ANCHO/2 para que lumil suba y baje
    private float contadorDireccionLumil = 0; //Cuenta el tiempo que lumil esperará para cambiar de posicion.
    private final float ESPERA_CAMBIO_DIRECCION = 3; //Segundos que espera lumil para cambiar de direccion
    private Texture texturaInfo;

    public PantallaAyudaLumil(Lux juego) {

        this.juego = juego;
    }

    @Override
    public void show() {
        texturaFondo = new Texture("PantallaAyuda/pantallasAyudaSubFondo.jpg");
        texturaInfo = new Texture("PantallaAyuda/pantallaAyudaLumil.png");
        textureLumil = new Texture("Personajes/Lumil_Sprites.png");
        lumil = new Lumil(textureLumil, ANCHO*7/8f, ALTO*.625f, 4, 1, 1/10f, 1); // Centro Lumil ANCHO/
        crearAyudaLumil();
        crearBrillo();

        // Bloquear la tecla de back
        Gdx.input.setCatchKey(Input.Keys.BACK,true);
    }

    private void crearAyudaLumil() {
       // texturaFondo = new Texture("PantallaAyuda/pantallasAyudaSubFondo.jpg");
        escenaMenu = new Stage(vista);

        Button btnBack = crearBoton("Menu/buttonback.png", "Menu/clickback.png");
        btnBack.setPosition(160, ALTO-80, Align.center);
        btnBack.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // juego.setScreen(new PantallaAyuda(juego));
                juego.setScreen(new PantallaCargando(juego, Pantallasenum.PANTALLAAYUDA));
            }
        });
        escenaMenu.addActor(btnBack);
        Gdx.input.setInputProcessor(escenaMenu);
    }

    private void crearBrillo() {
        texturaBrillo = new Texture("Utileria/brilloLumil.png");
        brilloLumil = new BrilloLumil(texturaBrillo,ANCHO*7/8f+(texturaBrillo.getWidth()/6),ALTO*.625f); // Centro brillo ANCHO/

    }
    private void moverPersonaje(float delta) {
        contadorDireccionLumil += Gdx.graphics.getDeltaTime();//incrementar el contador
        //Invierte la el signo para que cambie de dirección
        if ((lumil.sprite.getY()+(lumil.sprite.getHeight()/2)==alturaLumil+(ALTO*.625f))&&contadorDireccionLumil>=ESPERA_CAMBIO_DIRECCION) {
            alturaLumil = -alturaLumil;
            contadorDireccionLumil=0; //Resetear el contador
        }
        lumil.mover(DELTA_Y,(ALTO*.625f)+alturaLumil);
        brilloLumil.mover(lumil.sprite.getY() + (lumil.sprite.getHeight() / 2));
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
        brilloLumil.actualizar(1,1,1,0.9f);
        moverPersonaje(delta);
        if(Gdx.input.isKeyPressed(Input.Keys.BACK)){
            // Regresar a la pantalla anterior (ACCION)
            juego.setScreen(new PantallaCargando(juego,Pantallasenum.PANTALLAAYUDA));
        }
        batch.setProjectionMatrix(camara.combined);
        batch.begin();
        batch.draw(texturaFondo, 0, 0);
        batch.draw(texturaInfo, 0, 0);
        brilloLumil.render(batch);
        lumil.animationRender(batch, time);
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
        texturaBrillo.dispose();
        texturaFondo.dispose();
        texturaInfo.dispose();
        textureLumil.dispose();
    }
}
