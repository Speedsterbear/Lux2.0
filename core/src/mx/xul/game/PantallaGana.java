package mx.xul.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
// Clase que implementa la pantalla cuando el jugador gana
// Autor: Carlos, Andrea, David

public class PantallaGana extends Pantalla{

    private Lux juego;

    //Fondo de bosque en movimiento
    private Texture bosqueFondoDia;
    private Texture bosqueAtrasDia;
    private Texture bosqueMedioDia;
    private Texture bosqueFrenteDia;
   // private Texture musicaPantallasSecundarias;
    private FondoEnMovimiento bosqueDia;

    //Personaje principal: Lumil
    private Lumil lumil;
    private Texture texturaLumilJugando;
    float tiempoLumil = 0f; //Acumulador
    private float DELTA_Y = 2; //Avance vertical de lumil
    private final float duracionFrameLumil = 1/8f; //Duración en segundos  inicial de los frames de la animación d Lumil
    private Texture texturaBrillo;
    private BrilloLumil brilloLumil;
    private float alturaLumil = ALTO/6; // Representa la altura que se le sumará a ANCHO/2 para que lumil suba y baje
    private float contadorDireccionLumil = 0; //Cuenta el tiempo que lumil esperará para cambiar de posicion.
    private final float ESPERA_CAMBIO_DIRECCION = 3; //Segundos que espera lumil para cambiar de direccion

    private Stage escenaGana;

    //Fade a Blanco
    private ShapeRenderer rectangulo;
    private float alfaRectangulo = 1;
    private Transicion transicionBlanca;

    //Mensaje Victoria
    private Texture mensajeGanar; //Textura que muestra el mensaje de que se ganó el juego

    // Texto
    private Texto texto; //Muestra los valores en la pantalla

    // AssetManager
   // private AssetManager manager;

    //Musica de pantalla
    private boolean musicaON = false;
    private Sound musicaGanar;
    private long idSonidoJuegoGanar;
    private boolean isSonidoGanarFadingOut = false;
    private static final float TIEMPO_FADE_SONIDOJUEGO = 1f ;
    private float volumenSonidoGanar =  0.6f;

    public PantallaGana(Lux juego) {
        this.juego=juego;
     //   manager = juego.getAssetManager();
    }

    private Button crearBoton(String archivo, String archivoclick) {
        Texture texturaBoton=new Texture(archivo);
        TextureRegionDrawable trdBtnRunner=new TextureRegionDrawable(texturaBoton);

        Texture texturaClick= new Texture(archivoclick);
        TextureRegionDrawable trdBtnClick=new TextureRegionDrawable(texturaClick);

        return new Button(trdBtnRunner,trdBtnClick);
    }

    @Override
    public void show() {
        crearFondoDia();
        crearGana();
        crearPersonaje();
        crearBrillo();
        crearRectangulo();
        crearMensaje();
        crearTexto();
        crearMusica();
        // Bloquear la tecla de back
        Gdx.input.setCatchKey(Input.Keys.BACK,true);
    }

    private void crearMusica() {
        musicaGanar = Gdx.audio.newSound(Gdx.files.internal("Sonidos/musicaGanar.mp3"));

        Preferences prefs = Gdx.app.getPreferences("MusicPrefernce");
        musicaON = prefs.getBoolean("musicON", true);
        // Si el valo leído es musica prendida entonces se ejecuta...

        if (musicaON == true){
            idSonidoJuegoGanar = musicaGanar.play();
        }
        musicaGanar.setVolume(idSonidoJuegoGanar,0.7f);

    }

    private void crearTexto() {
        texto = new Texto();
    }

    private void crearMensaje() {
        mensajeGanar = new Texture("PantallaGanar/mensajeGanar.png");
    }

    private void crearRectangulo() {
        rectangulo = new ShapeRenderer();
        //rectangulo.setColor(1,1,1,1);
        transicionBlanca = new Transicion(1,1,1,1f,ALTO,ANCHO);
    }

    //Aqui se crea el fondo similar a la pantalla de juego
    private void crearFondoDia() {
        bosqueFondoDia = new Texture("Escenarios/fondo_dia.jpg");
        bosqueAtrasDia = new Texture("Escenarios/dia_atras.png");
        bosqueMedioDia = new Texture("Escenarios/dia_medio.png");
        bosqueFrenteDia =new Texture("Escenarios/dia_frente.png");
        bosqueDia = new FondoEnMovimiento(bosqueFondoDia,bosqueAtrasDia,bosqueMedioDia,bosqueFrenteDia);
    }

    private void crearPersonaje() {
        //Personaje principal: Lumil (Blanco)
        texturaLumilJugando = new Texture ("Personajes/Lumil_Sprites.png");
        lumil = new Lumil(texturaLumilJugando,ANCHO/4, ALTO/2,4,1,duracionFrameLumil,1);
    }

    private void crearBrillo() {
        texturaBrillo = new Texture("Utileria/brilloLumil.png");
        brilloLumil = new BrilloLumil(texturaBrillo,ANCHO/4+(texturaBrillo.getWidth()/6),ALTO/2);

    }
    private void crearGana() {
        //final Music musicaPantallasSecundarias = Gdx.audio.newMusic(Gdx.files.internal("Sonidos/musicaPantallasSecundarias.ogg"));

        escenaGana = new Stage(vista);

        Button btnExit = crearBoton("Botones/btnHome_OFF.png","Botones/btnHome_ON.png");
        btnExit.setPosition((ANCHO/2)+207.5f, 160, Align.center);
        escenaGana.addActor(btnExit);
        btnExit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //musicaPantallasSecundarias.stop();
                isSonidoGanarFadingOut = true;
            }
        });

        Gdx.input.setInputProcessor(escenaGana);
    }

    @Override
    public void render(float delta) {
        borrarPantalla(0,0,0);

        if(Gdx.input.isKeyPressed(Input.Keys.BACK)){
            // Regresar a la pantalla anterior (ACCION)
            juego.setScreen(new PantallaCargando(juego,Pantallasenum.JUEGOGS));
        }

        actualizar(delta);

        batch.setProjectionMatrix(camara.combined);

        batch.begin();
        //Dibujar elementos del bosque
        bosqueDia.movSeccionesCompletas(200, delta, batch, true);
        //Dibujar a Lumil
        brilloLumil.render(batch);
        lumil.animationRender(batch, tiempoLumil);

        //Dibujar Mensjae de Ganar
        batch.draw(mensajeGanar,ANCHO/2,0);

        //Mostrar Tiempo Usado
        //System.out.println(Float.toString(tiempoLumil));
        texto.mostrarMensaje(batch,"TIME SPENT",(ANCHO/2)+420f, 70 + (210*2/3f));
        texto.mostrarMensaje(batch,juego.getCuentaSegundos(),(ANCHO/2)+420f, 70 + (210/3));

        batch.end();

        escenaGana.draw();
        if (tiempoLumil<=2.5f) {
            //System.out.println(transicionBlanca.isFadeInFinished);
            if (tiempoLumil>=1 && transicionBlanca.isFadeInFinished==false){
                transicionBlanca.fadeIn(delta,1);
            }
        }

        transicionBlanca.render(camara);

        if (isSonidoGanarFadingOut){
            System.out.println(transicionBlanca.isFadeOutFinished);
            transicionBlanca.fadeOut(delta,TIEMPO_FADE_SONIDOJUEGO);
            sonidoPricipalFadeOut(delta);
        }

        if (transicionBlanca.isFadeOutFinished){
            juego.setScreen(new PantallaMenu(juego));
        }

    }

    private void sonidoPricipalFadeOut(float delta) {


        //Control para el Fade out
        if (isSonidoGanarFadingOut){
            volumenSonidoGanar -=(delta/TIEMPO_FADE_SONIDOJUEGO);
            if(volumenSonidoGanar <=0){
                volumenSonidoGanar = 0;
                //isSonidoGanarFadingOut =false;
            }
            musicaGanar.setVolume(idSonidoJuegoGanar, volumenSonidoGanar);//Asignar el nuevo valor de volumen.
        }
    }


    private void actualizar(float delta) {
        //Tiempo que pasó entre render.
        tiempoLumil += Gdx.graphics.getDeltaTime();
        brilloLumil.actualizar(1,1,1,0.9f);
        moverPersonaje(delta);

    }

    private void moverPersonaje(float delta) {
        contadorDireccionLumil += Gdx.graphics.getDeltaTime();//incrementar el contador
        //Invierte la el signo para que cambie de dirección
        if ((lumil.sprite.getY()+(lumil.sprite.getHeight()/2)==alturaLumil+(ALTO/2))&&contadorDireccionLumil>=ESPERA_CAMBIO_DIRECCION) {
            alturaLumil = -alturaLumil;
            contadorDireccionLumil=0; //Resetear el contador
        }
        lumil.mover(DELTA_Y,(ALTO/2)+alturaLumil);
        brilloLumil.mover(lumil.sprite.getY() + (lumil.sprite.getHeight() / 2));
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
    bosqueAtrasDia.dispose();
    bosqueFondoDia.dispose();
    bosqueFrenteDia.dispose();
    bosqueMedioDia.dispose();
    texturaBrillo.dispose();
    texturaLumilJugando.dispose();
    mensajeGanar.dispose();
    musicaGanar.dispose();
    }
}

