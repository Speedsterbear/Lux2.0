package mx.xul.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;

// Clase que implementa la pantalla que aparece al perder el jugador
// Autores: Andrea Espinosa Azuela y Carlos Arroyo

public class PantallaPerdida extends Pantalla{

    private Lux juego;
    private Stage escenaPerdio;

    //Control
    private float tiempo = 0;//Acumulador del tiempo para la animación.
    private float angulo = 0;//Acumulador para generar movimiento a partir de función Seno.
    private final float DY_VERTICAL_CRISTAL = 25;//Distancia que sube y bajael cristal
    private final float DX_HORIZONTAL_BRILLO = 230;//Movimiento horizontal del brillo
    private float aumentoAngulo = 2;

    //Fondo
    private Texture texturaFondo; //Fondo de la pantalla
    private Texture texturaHumo; //Textura para dar el eefecto de humo o sombras moviéndose en el fondo
    private ObjetoAnimado humo; //Para la animación del himo/sombras moviéndose en el fondo
    private Texture reflector;

    //Cristal
    private Texture texturaCristalPerder;
    private Objeto cristal;

    //Brillo
    private Texture texturaBrillo;
    private BrilloLumil brilloFrente;
    private BrilloLumil brilloAtras;
    private BrilloLumil brilloMasFrente;
    private final float SCALA_INICIAL = 0.7f;
    private final float CAMBIO_ESCALA = 0.3f;

    //Para el Fade
    private Transicion fadeNegro;
    private Transicion fadeBlanco;
    private final float DURACION_FADE =1;
    private EstadoPantalla estadoPantalla=EstadoPantalla.MISMA_PANTALLA;

    //Mensaje de perder
    private Texture mensajePerder;

    //Musica de pantalla
    private boolean musicaON = false;
    private Sound musicaPerder;
    private long idSonidoJuegoPerder;
    private boolean isSonidoPerderFadingOut = false;
    private static final float TIEMPO_FADE_SONIDOJUEGO = 1f ;
    private float volumenSonidoPerder =  0.7f;

    public PantallaPerdida(Lux juego) {
        this.juego=juego;
    }

    private Button crearBoton(String archivo, String archivoclick) {
        Texture texturaBoton=new Texture(archivo);
        TextureRegionDrawable trdBtnRunner=new TextureRegionDrawable(texturaBoton);

        Texture texturaClick=new Texture(archivoclick);
        TextureRegionDrawable trdBtnClick=new TextureRegionDrawable(texturaClick);

        return new Button(trdBtnRunner,trdBtnClick);
    }

    @Override
    public void show() {
        crearFondo();
        crearCristal();
        crearBrillo();
        crearPerdio();
        crearMusica();

        //Iniciar la transición
        fadeNegro = new Transicion(0,0,0,1,ALTO,ANCHO);
        fadeBlanco = new Transicion(1,1,1,0,ALTO,ANCHO);

        // Bloquear la tecla de back
        Gdx.input.setCatchKey(Input.Keys.BACK,true);
    }

    private void crearMusica() {
        musicaPerder = Gdx.audio.newSound(Gdx.files.internal("Sonidos/musicaPerder.mp3"));

        Preferences prefs = Gdx.app.getPreferences("MusicPrefernce");
        musicaON = prefs.getBoolean("musicON", true);
        // Si el valo leído es musica prendida entonces se ejecuta...

        if (musicaON == true){
            idSonidoJuegoPerder = musicaPerder.play();
        }
        musicaPerder.setVolume(idSonidoJuegoPerder,0.7f);

    }

    private void crearBrillo() {
        texturaBrillo = new Texture("Utileria/brilloLumil.png");
        brilloFrente = new BrilloLumil(texturaBrillo,(ANCHO/3)-DX_HORIZONTAL_BRILLO,ALTO/2);
        brilloAtras = new BrilloLumil(texturaBrillo,(ANCHO/3)-DX_HORIZONTAL_BRILLO,ALTO/2);
        brilloMasFrente = new BrilloLumil(texturaBrillo,(ANCHO/3)-DX_HORIZONTAL_BRILLO,ALTO/2);
    }

    private void crearCristal() {
        texturaCristalPerder  = new Texture ("PantallaPerdida/cristalPantallaPerder.png");
        cristal = new Objeto(texturaCristalPerder,ANCHO/3,ALTO/2);
    }

    private void crearFondo() {
        texturaFondo = new Texture ("PantallaHistoria/fondo_humo.jpg");

        texturaHumo = new Texture ("PantallaHistoria/Humo.png");
        humo = new ObjetoAnimado(texturaHumo,ANCHO/2,ALTO/2,5,1,1/6f,2);

        reflector = new Texture ("PantallaPerdida/reflector.png");
        mensajePerder = new Texture("PantallaPerdida/tryAgain.png");
    }

    private void crearPerdio() {
        escenaPerdio = new Stage(vista);

        Button btnreiniciar = crearBoton("PantallaPerdida/btnYesOFF.png","PantallaPerdida/btnYesON.png");
        btnreiniciar.setPosition(ANCHO*3/4f, (ALTO/2)-(btnreiniciar.getHeight()/2), Align.center);
        escenaPerdio.addActor(btnreiniciar);
        btnreiniciar.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
               estadoPantalla = EstadoPantalla.REPLAY;
                //juego.setScreen(new JuegoGS(juego));
            }
        });


        Button btnExit = crearBoton("PantallaPerdida/btnNoOFF.png","PantallaPerdida/btnNoON.png");
        btnExit.setPosition(ANCHO*3/4f, (btnreiniciar.getY())-(btnExit.getHeight()), Align.center);
        escenaPerdio.addActor(btnExit);
        btnExit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                estadoPantalla = EstadoPantalla.MENU_PRINCIPAL;
                //juego.setScreen(new PantallaMenu(juego));
            }
        });

        Gdx.input.setInputProcessor(escenaPerdio);
    }

    @Override
    public void render(float delta) {
        borrarPantalla(0,0,0);
        actualizar(delta);

        // Para controlar la tecla de Atras del telefono
        if(Gdx.input.isKeyPressed(Input.Keys.BACK)){
            // Regresar a la pantalla anterior (ACCION)
            juego.setScreen(new PantallaCargando(juego,Pantallasenum.JUEGOGS));
        }

        batch.setProjectionMatrix(camara.combined);

        batch.begin();

        //Dibujar fondo
        batch.draw(texturaFondo,0,0);
        humo.animationRender(batch,tiempo);


        //Dibujar Cristal
        if (angulo>=90 && angulo <270){
            cristal.render(batch);
            brilloAtras.render(batch);
            brilloFrente.render(batch);
            brilloMasFrente.render(batch);
        }else {
            brilloAtras.render(batch);
            brilloFrente.render(batch);
            brilloMasFrente.render(batch);
            cristal.render(batch);
        }


        batch.draw(reflector,(ANCHO/3)-(reflector.getWidth()/2),0);
        batch.draw(mensajePerder,ANCHO/2,(ALTO/2)+30);
        batch.end();

        escenaPerdio.draw();

        fadeBlanco.render(camara);
        fadeNegro.render(camara);
        if (fadeNegro.isFadeOutFinished){
            switch (estadoPantalla){
                case REPLAY:
                    juego.setScreen(new PantallaCargando(juego,Pantallasenum.JUEGOGS));
                    break;
                case MENU_PRINCIPAL:
                    juego.setScreen(new PantallaMenu(juego));
                    break;
            }
        }

        if (isSonidoPerderFadingOut){
            sonidoPricipalFadeOut(delta);
        }
    }

    private void sonidoPricipalFadeOut(float delta) {


        //Control para el Fade out
        if (isSonidoPerderFadingOut){
            volumenSonidoPerder -=(delta/TIEMPO_FADE_SONIDOJUEGO);
            if(volumenSonidoPerder <=0){
                volumenSonidoPerder = 0;
                isSonidoPerderFadingOut =false;
            }
            musicaPerder.setVolume(idSonidoJuegoPerder, volumenSonidoPerder);//Asignar el nuevo valor de volumen.
        }
    }

    private void actualizar(float delta) {
        tiempo += delta;
        angulo +=aumentoAngulo;
        if (angulo>=360){
            angulo=0;
        }
        actualizarCristal();
        actualizarBrillo();
        actualizaTransicion(delta);
    }

    private void actualizaTransicion(float delta) {
        switch (estadoPantalla){
            case MISMA_PANTALLA:
                fadeNegro.fadeIn(delta,DURACION_FADE);
                break;
            case REPLAY:
                aumentoAngulo = 10;
                isSonidoPerderFadingOut = true;
                fadeBlanco.fadeOut(delta,DURACION_FADE*2);
                if (fadeBlanco.isFadeOutFinished){
                    fadeNegro.fadeOut(delta,DURACION_FADE/2f);
                }
                break;
            case MENU_PRINCIPAL:
                aumentoAngulo = 2;
                isSonidoPerderFadingOut = true;
                fadeBlanco.fadeOut(delta,DURACION_FADE*2);
                if (fadeBlanco.isFadeOutFinished){
                    fadeNegro.fadeOut(delta,DURACION_FADE/2f);
                }
                break;
        }

    }

    private void actualizarBrillo() {
        brilloAtras.actualizar(1,1,1,SCALA_INICIAL+((MathUtils.sinDeg(angulo-90)*CAMBIO_ESCALA)));
        brilloAtras.moverX((ANCHO/3)+((MathUtils.sinDeg(angulo)*DX_HORIZONTAL_BRILLO)));

        brilloFrente.actualizar(1,1,1,SCALA_INICIAL-0.2f+((MathUtils.sinDeg(angulo-90)*CAMBIO_ESCALA)));
        brilloFrente.moverX((ANCHO/3)+((MathUtils.sinDeg(angulo)*DX_HORIZONTAL_BRILLO)));

        brilloMasFrente.actualizar(1,1,1,SCALA_INICIAL-0.4f+((MathUtils.sinDeg(angulo-90)*CAMBIO_ESCALA)));
        brilloMasFrente.moverX((ANCHO/3)+((MathUtils.sinDeg(angulo)*DX_HORIZONTAL_BRILLO)));
    }

    private void actualizarCristal() {
        cristal.sprite.setY((ALTO/2)-(cristal.sprite.getHeight()/2)+(MathUtils.sinDeg(angulo)*DY_VERTICAL_CRISTAL));
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
     texturaCristalPerder.dispose();
     texturaFondo.dispose();
     texturaHumo.dispose();
    }

    //Mientras no se oprima algun botón, el estado es misma_pantalla, los otros estados son para cada botón.
    private enum EstadoPantalla
    {
        MISMA_PANTALLA,
        REPLAY,
        MENU_PRINCIPAL
    }
}
