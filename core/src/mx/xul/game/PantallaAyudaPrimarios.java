package mx.xul.game;
// Pantalla que implementa la información de ayuda de los colores primarios
// Autor: Eduardo Alejandro García y Carlos Arroyo
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

public class PantallaAyudaPrimarios extends Pantalla {
    private Lux juego;
    private Texture texturaFondo;
    private Texture texturaInfo;
    private Stage escenaMenu;

    //Personajes
    private Shiblu shiblu;
    private Texture texturaShiblu;
    private Texture texturaInfoShiblu;
    private Objeto infoShiblu;

    private Esgrun esgrun;
    private Texture textureEsgrun;
    private Texture texturaInfoEsgrun;
    private Objeto infoEsgrun;

    private Rojel rojel;
    private Texture textureRojel;
    private Texture texturaInfoRojel;
    private Objeto infoRojel;

    //Control
    private float time = 0; //Acomulador para la animación
    private MostrarPrimario mostrarPrimario = MostrarPrimario.ESGRUN;
    private final float DURACION_FADE = 1;//Tiempo que tarda en hacer los face en segundos.
    private float angulo =0; //Contador para el angulo
    private final float DISTANCIA_MAX_MOVY_PRIMARIOS = 20;

    public PantallaAyudaPrimarios(Lux juego) {

        this.juego = juego;
    }

    @Override
    public void show() {
        crearFondo();
        crearPersonajes();
        crearAyudaPrimarios();

        // Bloquear la tecla de back
        Gdx.input.setCatchKey(Input.Keys.BACK,true);
    }

    private void crearPersonajes() {
        texturaShiblu = new Texture("PantallaAyuda/ayudaShiblu.png");
        shiblu = new Shiblu(texturaShiblu, (ANCHO/2)+(texturaShiblu.getWidth()/2), ALTO/2);
        shiblu.sprite.setAlpha(0);

        textureEsgrun = new Texture("PantallaAyuda/ayudaEsgrun.png");
        esgrun = new Esgrun(textureEsgrun, (ANCHO/2)+(textureEsgrun.getWidth()/2), ALTO/2);
        esgrun.sprite.setAlpha(0);

        textureRojel = new Texture("PantallaAyuda/ayudaRogel.png");
        rojel = new Rojel(textureRojel, (ANCHO/2)+(textureRojel.getWidth()/2), ALTO/2);
        rojel.sprite.setAlpha(0);

        //info
        texturaInfoRojel = new Texture("PantallaAyuda/pantallaAyudaPrimariosRogel.png");
        infoRojel = new Objeto(texturaInfoRojel,ANCHO - texturaInfoRojel.getWidth()/2,ALTO/2);
        infoRojel.sprite.setAlpha(0);

        texturaInfoEsgrun = new Texture("PantallaAyuda/pantallaAyudaPrimariosEsgrun.png");
        infoEsgrun = new Objeto(texturaInfoEsgrun,ANCHO - texturaInfoEsgrun.getWidth()/2,ALTO/2);
        infoEsgrun.sprite.setAlpha(0);

        texturaInfoShiblu = new Texture("PantallaAyuda/pantallaAyudaPrimariosShiblu.png");
        infoShiblu = new Objeto(texturaInfoShiblu,ANCHO - texturaInfoShiblu.getWidth()/2,ALTO/2);
        infoShiblu.sprite.setAlpha(0);
    }

    private void crearFondo() {
        texturaFondo = new Texture("PantallaAyuda/pantallasAyudaSubFondo.jpg");
        texturaInfo = new  Texture ("PantallaAyuda/pantallaAyudaPrimarios.png");
    }

    private void crearAyudaPrimarios() {
        escenaMenu = new Stage(vista);
        final Button btnGemaVerde = crearBoton("BotonesGemas/gemaVerde_OFF.png","BotonesGemas/gemaVerde_ON.png");
        final Button btnGemaRoja = crearBoton("BotonesGemas/gemaRoja_OFF.png","BotonesGemas/gemaRoja_ON.png");
        final Button btnGemaAzul = crearBoton("BotonesGemas/gemaAzul_OFF.png","BotonesGemas/gemaAzul_ON.png");
        btnGemaVerde.setChecked(true);
        btnGemaVerde.setDisabled(true);

        Button btnBack = crearBoton("Menu/buttonback.png","Menu/clickback.png");
        btnBack.setPosition(160, ALTO-80, Align.center);
        btnBack.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // juego.setScreen(new PantallaAyuda(juego));
                juego.setScreen(new PantallaCargando(juego, Pantallasenum.PANTALLAAYUDA));
            }
        });
        escenaMenu.addActor(btnBack);

        btnGemaVerde.setPosition(ANCHO/8,20+(btnGemaVerde.getHeight()/2), Align.center);
        btnGemaVerde.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // juego.setScreen(new PantallaAyuda(juego));
                mostrarPrimario = MostrarPrimario.ESGRUN;
                btnGemaVerde.setDisabled(true);
                btnGemaRoja.setDisabled(false);
                btnGemaAzul.setDisabled(false);

                btnGemaRoja.setChecked(false);
                btnGemaAzul.setChecked(false);
            }
        });
        escenaMenu.addActor(btnGemaVerde);

        btnGemaRoja.setPosition(ANCHO/4,20+(btnGemaRoja.getHeight()/2), Align.center);
        btnGemaRoja.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // juego.setScreen(new PantallaAyuda(juego));
                mostrarPrimario = MostrarPrimario.ROJEL;
                btnGemaVerde.setDisabled(false);
                btnGemaRoja.setDisabled(true);
                btnGemaAzul.setDisabled(false);

                btnGemaVerde.setChecked(false);
                btnGemaAzul.setChecked(false);
            }
        });
        escenaMenu.addActor(btnGemaRoja);

        btnGemaAzul.setPosition(ANCHO*3/8,20+(btnGemaAzul.getHeight()/2), Align.center);
        btnGemaAzul.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // juego.setScreen(new PantallaAyuda(juego));
                mostrarPrimario = MostrarPrimario.SHIBLU;
                btnGemaVerde.setDisabled(false);
                btnGemaRoja.setDisabled(false);
                btnGemaAzul.setDisabled(true);

                btnGemaRoja.setChecked(false);
                btnGemaVerde.setChecked(false);
            }
        });
        escenaMenu.addActor(btnGemaAzul);

        Gdx.input.setInputProcessor(escenaMenu);
    }

    private Button crearBoton(String archivo, String archivoClick) {
        Texture texturaBoton = new Texture(archivo);
        TextureRegionDrawable trdBtnBack = new TextureRegionDrawable(texturaBoton);
        Texture texturaClick = new Texture(archivoClick);
        TextureRegionDrawable trdBtnClick = new TextureRegionDrawable(texturaClick);
        Texture texturaToggle = new Texture(archivoClick);
        TextureRegionDrawable trdBtnToggle = new TextureRegionDrawable(texturaToggle);
        return new Button(trdBtnBack,trdBtnClick,trdBtnToggle);
    }

    @Override
    public void render(float delta) {
        borrarPantalla(0, 0, 1);
        time += delta;
        actualizar(delta);
        if(Gdx.input.isKeyPressed(Input.Keys.BACK)){
            // Regresar a la pantalla anterior (ACCION)
            juego.setScreen(new PantallaCargando(juego,Pantallasenum.PANTALLAAYUDA));
        }
        batch.setProjectionMatrix(camara.combined);
        batch.begin();
        //Dibujar Fondo
        batch.draw(texturaFondo, 0, 0);
        batch.draw(texturaInfo,0,0);

        //Dibujar ESGRUN
        esgrun.render(batch);
        infoEsgrun.render(batch);

        //Dibujar ROJEL
        rojel.render(batch);
        infoRojel.render(batch);

        //Dibujar SHIBLU
        shiblu.render(batch);
        infoShiblu.render(batch);


        // shiblu.
        batch.end();
        escenaMenu.draw();
    }

    private void actualizar(float delta) {
        moverPrimarios();
        switch (mostrarPrimario){
            case ESGRUN:
                infoRojel.fadeOut(delta,DURACION_FADE);
                infoShiblu.fadeOut(delta,DURACION_FADE);
                infoEsgrun.fadeIn(delta,DURACION_FADE);

                rojel.fadeOut(delta,DURACION_FADE);
                shiblu.fadeOut(delta,DURACION_FADE);
                esgrun.fadeIn(delta,DURACION_FADE);

                break;
            case ROJEL:
                infoEsgrun.fadeOut(delta,DURACION_FADE);
                infoShiblu.fadeOut(delta,DURACION_FADE);
                infoRojel.fadeIn(delta,DURACION_FADE);

                esgrun.fadeOut(delta,DURACION_FADE);
                shiblu.fadeOut(delta,DURACION_FADE);
                rojel.fadeIn(delta,DURACION_FADE);

                break;
            case SHIBLU:
                infoEsgrun.fadeOut(delta,DURACION_FADE);
                infoRojel.fadeOut(delta,DURACION_FADE);
                infoShiblu.fadeIn(delta,DURACION_FADE);

                esgrun.fadeOut(delta,DURACION_FADE);
                rojel.fadeOut(delta,DURACION_FADE);
                shiblu.fadeIn(delta,DURACION_FADE);
                break;
        }
    }

    private void moverPrimarios() {
        angulo += 1;
        rojel.sprite.setY((ALTO/2)+(MathUtils.sinDeg(angulo)*DISTANCIA_MAX_MOVY_PRIMARIOS) - (rojel.sprite.getHeight()/2));
        esgrun.sprite.setY((ALTO/2)+(MathUtils.sinDeg(angulo)*DISTANCIA_MAX_MOVY_PRIMARIOS)- (esgrun.sprite.getHeight()/2));
        shiblu.sprite.setY((ALTO/2)+(MathUtils.sinDeg(angulo)*DISTANCIA_MAX_MOVY_PRIMARIOS)- (shiblu.sprite.getHeight()/2));
        
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
    texturaInfoEsgrun.dispose();
    texturaInfoRojel.dispose();
    texturaInfoShiblu.dispose();
    textureEsgrun.dispose();
    textureRojel.dispose();
    texturaShiblu.dispose();
    }

    private enum MostrarPrimario
    {
        ESGRUN,
        ROJEL,
        SHIBLU
    }
}
