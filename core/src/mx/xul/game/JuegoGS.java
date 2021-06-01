package mx.xul.game;
/*
Esta clase representa el juego
Autor: Carlos Arroyo - Ricardo Solis - Andrea Espinosa
 */

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
//import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.Viewport;

import mx.xul.game.pantallaBienvenida.ColoresLumil;

public class JuegoGS extends Pantalla {

    private Lux juego;

    private EscenaPausa escenaPausa;
    private ProcesadorEntrada procesadorEntrada;

    // AssetManager
    private AssetManager manager;

    //Duración de las secciones
    private float duracionVerde = 20; //Valor que representa los segundos de duración aproximados de la sección 1.
    private float duracionRojo = 50; //Valor que representa los segundos de duración aproximados de la sección 2.
    private float duracionAzul = 80; //Valor que representa los segundos de duración aproximados de la sección 3.
    private float duracionBlanco = 95; //Valor que representa los segundos de duración aproximados de la sección 4.

    //Velocidad normal de las secciones
    private final float velocidadVerde = 300; //Valor que repreenta la velocidad normal de la sección 1.
    private final float velocidadRojo = 430; //Valor que repreenta la velocidad normal de la sección 2.
    private final float velocidadAzul = 560; //Valor que repreenta la velocidad normal de la sección 3.
    private final float velocidadBlanco = 690; //Valor que repreenta la velocidad normal de la sección 4.

    //Distancia
    private float distanciaVerde = velocidadVerde * duracionVerde; //Valor que representa los segundos de duración aproximados de la sección 1.
    private float distanciaRojo = velocidadRojo * duracionRojo; //Valor que representa los segundos de duración aproximados de la sección 2.
    private float distanciaAzul = velocidadAzul * duracionAzul; //Valor que representa los segundos de duración aproximados de la sección 3.
    private float distanciaBlanco = velocidadBlanco * duracionBlanco; //Valor que representa los segundos de duración aproximados de la sección 4.

    //Velocidad normal de la oscuridad según las secciones
    private final float velocidadOscVerde = velocidadVerde; //Valor que repreenta la velocidad normal de la Oscuridad de la sección 1.
    private final float velocidadOscRojo = velocidadRojo*1.043f; //Valor que repreenta la velocidad normal de la Oscuridad de la sección 2.
    private final float velocidadOscAzul = velocidadAzul*1.024f; //Valor que repreenta la velocidad normal de la Oscuridad de la sección 3.
    private final float velocidadOscBlanco = velocidadBlanco*1.031f; //Valor que repreenta la velocidad normal de la Oscuridad de la sección 4.

    //Velocidad normal de los hijos de la oscuridad según las secciones
    private final float velocidadHijoOscVerde = velocidadVerde*1.4f; //Valor que repreenta la velocidad normal de los hijos de la Oscuridad de la sección 1.
    private final float velocidadHijoOscRojo = velocidadRojo*1.45f; //Valor que repreenta la velocidad normal de los hijos de la Oscuridad de la sección 2.
    private final float velocidadHijoOscAzul = velocidadAzul*1.50f; //Valor que repreenta la velocidad normal de los hijos de la Oscuridad de la sección 3.
    private final float velocidadHijoOscBlanco = velocidadBlanco*1.55f; //Valor que repreenta la velocidad normal de los hijos de la Oscuridad de la sección 4.

    //Márgenes
    private float margen = 20;
    //Valor mínimo X fuera de la pantalla para que aparezcan los objetos que se mueven de derecha a izq.
    private float valorXMinExt = ANCHO + 200;
    //Valor máximo X fuera de la pantalla para que aparezcan los objetos que se mueven de derecha a izq.
    private float valorXMaxExt = ANCHO * 1.5f;
    //Valor mínimo Y del margen para aparecer los objetos que se mueven de derecha a izquierda.
    private float valorYMinMarg = 0;
    //Valor máximo Y del margen para aparecer los objetos que se mueven de derecha a izquierda.
    private float valorYMaxMarg = ALTO;

    //Fondo de bosque en movimiento

    private Texture bosqueCapaCero;
    private Texture bosqueCapaUno;
    private Texture bosqueCapaDos;
    private Texture bosqueCapaTres;
    private Texture bosqueCapaCuatro;
    private Texture bosqueCapaEstatica;
    private FondoParallax bosqueParallax;

    private Texture bosqueCapaFrente;
    private Texture bosqueCapaFrenteVerde;

    private Texture nubeFondo;
    private Texture texturaLuna;
    private Objeto luna;

    private Texture texturaEfectoAzulFrente;
    private Texture texturaEfectoAzulAtras;
    private Texture texturaEfectoRojoFrente;
    private Texture texturaEfectoRojoAtras;

    private Objeto efectoAzulFrente;
    private Objeto efectoAzulAtras;
    private Objeto efectoRojoFrente;
    private Objeto efectoRojoAtras;

    //Estrellas
    private Array<BrilloLumil> arrEstrellas;

    // Texto
    //Muestra los valores en la pantalla
    private Texto texto;

    //Personaje principal: Lumil
    private Lumil lumil;
    private Texture texturaLumilJugando;
    float tiempoLumil = 0f; //Acumulador
    private final float DY_LUMIL_INICIAL= 10;
    private float dyLumil = DY_LUMIL_INICIAL; //Avance vertical de lumil
    private final float duracionFrameLumil = 1/10f; //Duración en segundos  inicial de los frames de la animación d Lumil
    private final float incrementoVelocidadVerde = 1.65f; //Numero de veces en las que se incrementará la velocidad al usar la habilidad de la gema verde
    private Vector3 posicionDedo;
    private boolean isMoving = false; //indica si el perosnaje principal debe moverse hacia arriba o abajo
    private ColoresLumil colorLumil = ColoresLumil.BLANCO; // Representa el color que tiene el personaje principal en el momento.
    private Texture texturaBrillo;
    private BrilloLumil brilloLumil;

    private Lumil lumilG;
    private Texture texturaLumilG;

    private Lumil lumilR;
    private Texture texturaLumilR;

    private Lumil lumilB;
    private Texture texturaLumilB;

    //Enemigo principal: La Oscuridad
    private Oscuridad oscuridad;
    private Texture texturaOscuridad;
    float tiempoOsc =0f; //Acumulador
    private float velocidadOscuridad = velocidadOscVerde;//Velocidad Actual de la oscuridad
    private float velocidadHijosOscuridad = velocidadHijoOscVerde;//Velocidad Actual de hijos de la oscuridad
    //private float positionXStart = -900;
    private float positionX = (-ANCHO/2)+margen*2;
    private float positionY = ALTO/2;
    private long startTimeOscuridad = 0;
    //private EstadoOscuridad estadoOscuridad = EstadoOscuridad.QUIETO;

    //Velocidades generales
    private float velocidadInicial = velocidadVerde;
    private float velocidad = velocidadVerde;

    //General
    //private float velocidad = velocidadBosque;

    //Vidas
    private int contadorVidas = 3;
    private Texture texturaVida;
    private LuzVida luzVida;
    private Vidas vidas;

    //Estado Juego
    private EstadoJuego estado = EstadoJuego.INICIANDO;

    //Personajes
    private Esgrun esgrun;
    private Rojel rojel;
    private Shiblu shiblu;
    private Sound sonidoPrimarios;
    private Luz luz;
    private float DX_PASO_ESGRUN=2.5f;
    private float DX_PASO_ROJEL=2.5f;
    private float DX_PASO_SHIBLU=2.5f;
    private float DX_PASO_LUZ=2.5f;
    public static float aparicion = 0;

    //Luz Final
    private PersonajeSecundario luzFinal;//Para la luz final que es el ultimo personaje con el que Lumil debe reunirse para derrotar a la oscuridad.
    private Texture texturaLuz;

    //Hijos de la Oscuridad: Del tipo que Bloquean el paso
    private Array<Bloque> arrBloques;
    private Bloque bloque;
    private Texture texturaBloque;
    private float timerCrearBloque = 0;//Acumulador
    private float tiempoParaCrearBloque =2f; //Se espera esos segundos en crear el bloque.
    private float escalaMinimaBloque = 0.5f;
    private float escalaMaximaBloque = 0.66f;

    //Hijos de la Oscuridad: Del tipo que quitan vidas
    private Array<HijoOscuridad> arrHijosOscuridad;
    private HijoOscuridad hijoOscuridad;
    private Texture texturaHijoOscuridad;
    private float timerCrearHijoOscuridad = 0;//Acumulador
    private float tiempoParaCrearHijoOscuridad =2.2f; //Se espera esos segundos en crear el bloque.
    private float tiempoHijoOsc = 0;


    //Barra Avance
    private BarraAvance barraGS;
    private BarraAvance barraRS;
    private BarraAvance barraBS;
    private BarraAvance barraWS;
    private float distanciaRecorrida = 0;
    private float distanciaRecorridaG = 0; //Usar si vamos a medir dstancia para saber si ya se logró el objetivo.
    private float distanciaRecorridaR = 0; //Usar si vamos a medir dstancia para saber si ya se logró el objetivo.
    private float distanciaRecorridaB = 0; //Usar si vamos a medir dstancia para saber si ya se logró el objetivo.
    private float distanciaRecorridaW = 0; //Usar si vamos a medir dstancia para saber si ya se logró el objetivo.
    private float distanciaRecorridaControl = 0; //Usar si vamos a medir dstancia para saber si ya se logró el objetivo.
    private Texture fondoBarra; //Representa el Fondo para la barra de avance

    //Secciones
    private EstadoSeccion seccion = EstadoSeccion.VERDE;
    private EstadoSeccion seccionAnterior = EstadoSeccion.VERDE;

    //Botones

    //Gemas
    private float timerGemas = 0;//Acumulador
    private float tiempoParaGemas = 3f; //Se espera esos segundos en crear el bloque.
    private boolean powerups = true;
    private boolean powerupActive = true;
    //TODO Poder Activado

    private Boton gemaVerde; //Gema verde  para activar el poder
    private Texture texturaGemaVerdeOFF;
    private Texture texturaGemaVerdeON;
    private boolean boolPowerUpGemaVerde = false;
    private boolean activarPowerUpGemaVerde = false;
    private float timerPowerUpGemaVerde = 0;
    private float tiempoParaPowerUpGemaVerde = 0.5f;
    private float contadorFadeInGemaVerde = 0f; //Acumulador para el fade in de cuando aparece la gema.

    private Boton gemaRoja; //Gema roja para activar el poder
    private Texture texturaGemaRojaOFF;
    private Texture texturaGemaRojaON;
    private boolean boolPowerUpGemaRoja = false;
    private boolean activarPowerUpGemaRoja = false;
    private float timerPowerUpGemaRoja = 0;
    private float tiempoParaPowerUpGemaRoja = 3f;
    private float contadorFadeInGemaRoja = 0f; //Acumulador para el fade in de cuando aparece la gema.

    private Boton gemaAzul; //Gema Azul para activar el poder
    private Texture texturaGemaAzulON;
    private Texture texturaGemaAzulOFF;
    private boolean boolPowerUpGemaAzul = false;
    private boolean colisionPowerUpGemaAzul = false;
    private boolean activarPowerUpGemaAzul = false;
    private float timerPowerUpGemaAzul = 0;
    private float tiempoParaPowerUpGemaAzul = 3f;
    private float contadorFadeInGemaAzul = 0f; //Acumulador para el fade in de cuando aparece la gema.

    boolean colisionLumilFront = false;
    boolean colisionLumilUp = false;
    boolean colisionLumilDown = false;

    private Boton botonPausa; //Boton para activar la pausa
    private Texture texturaPausaON;//Botón de Regreso
    private Texture texturaPausaOFF;

    //Fade a Negro
    private Transicion transicionFade;
    private final float DURACION_FADE = 4;
    private ShapeRenderer rectNegro;
    private float alfaRectanguloNegro = 0;
    private boolean isQuitPressed = false;
    private float  contadorInicial = 0; //Acumulador para aparecer elementos al inicio

    //Fade a Blanco
    private Transicion transicionGana;

    //Sonidos y musica
    private Sound sonidocomeOscuridad;
    private Sound sonidoPoderActivado;
    private Sound sonidoquitavidas;
    private Sound sonidoTocaLuzBlanca;
    //private Sound sonidoTocaPrimario;

    //Musica de Fondo
    private Music musicaFondo;
    private Sound sonidoJuego;
    private long idSonidoJuego;
    private float valorPitch = 1;//Valor del pitch del sonido principal
    private float volumenSonidoPrincipal = 0;
    private boolean isPitchChanging = false;
    private boolean isSonidoJuegoFadingIn = false;
    private boolean isSonidoJuegoFadingOut = false;
    private final float VALOR_MAXIMO_PITCH=1.09f;
    private final float VOLUMEN_MAXIMO_SONIDOJUEGO = 0.5f;
    private final float TIEMPO_FADE_SONIDOJUEGO = 10; //Tiempo que tarda en realizar los fade in y out.
    private boolean musicaON = true;

    public JuegoGS (Lux juego) {
        this.juego=juego;
        manager = juego.getAssetManager();
    }

    @Override
    public void show() {
        juego.inicializarCuentaSegundos();
        posicionDedo = new Vector3(0, 0, 0); //Posición del dedo
        crearTransicion();
        crearFondo();
        crearPersonajes();
        crearBrillo();
        crearVidas();
        crearTexturaHijoOscuridad();
        crearBloques();
        crearBarra();
        crearBotones();
        crearRectangulo();
        crearEstrellas();
        crearSonidos();
        crearTexto();

        //cargarRecursos();
        crearObjetos();
        // Bloquear la tecla de back
        Gdx.input.setCatchKey(Input.Keys.BACK,true);

        procesadorEntrada = new ProcesadorEntrada();
        Gdx.input.setInputProcessor(procesadorEntrada);

        //Ahora la misma pantalla RECIBE y PROCESA los eventos
        Gdx.input.setInputProcessor( new ProcesadorEntrada());
    }

    private void crearTexto() {
        texto = new Texto();
    }

    private void crearEstrellas() {
        arrEstrellas = new Array<>();
        for (int  i=0; i<=35; i++){
            BrilloLumil estrella = new BrilloLumil(texturaBrillo, MathUtils.random(0,ANCHO),MathUtils.random(ALTO/3,ALTO));
            arrEstrellas.add(estrella);
        }
    }

    private void crearTransicion() {
        transicionFade = new Transicion(0,0,0,1,ALTO,ANCHO);
        transicionGana = new Transicion(1,1,1,0,ALTO,ANCHO);
    }

    private void crearObjetos() {
    }

    private void crearSonidos() {
        sonidocomeOscuridad = manager.get("Sonidos/sonidoComeOscuridad.wav");
        sonidoPoderActivado = manager.get("Sonidos/sonidoPoderActivado.wav");
        //sonidoPoderActivado.setVolume(0.5f,0.2f);
        sonidoquitavidas = manager.get("Sonidos/sonidoquitavidas.wav");
        sonidoTocaLuzBlanca = manager.get("Sonidos/sonidoTocaLuzBlanca.wav");
        //musicaFondo = manager.get("Sonidos/JugarLoop.mp3");
        sonidoJuego = manager.get("Sonidos/JugarLoop.mp3");
        sonidoPrimarios = manager.get("Sonidos/sonidoTocaPrimario.wav");

        //musicaFondo.setLooping(true);
        //musicaFondo.setVolume(0.2f);
        //musicaFondo.play();
        Preferences prefs = Gdx.app.getPreferences("MusicPrefernce");
        boolean musicaON = prefs.getBoolean("musicON", true);
        // Si el valo leído es musica prendida entonces se ejecuta...
        if (musicaON == true){
            // juego.playMusica();
            idSonidoJuego= sonidoJuego.play();
            // musicaFondo.play();
        }
        //idSonidoJuego= sonidoJuego.play();
        sonidoJuego.setVolume(idSonidoJuego,0);
        sonidoJuego.setLooping(idSonidoJuego,true);
        isSonidoJuegoFadingIn=true; //Se inicializa la entrada

    }

    private void crearRectangulo() {
        //Rectangulo usado para hacer Fade a negro
        rectNegro = new ShapeRenderer();
    }

    private void crearBrillo() {
        texturaBrillo = manager.get("Utileria/brilloLumil.png");
        brilloLumil = new BrilloLumil(texturaBrillo,ANCHO/2+(texturaBrillo.getWidth()/7),ALTO/2);
        brilloLumil.actualizar(1,1,1,0.7f);
    }

    private void crearBotones() {
        //Gema Verde
        texturaGemaVerdeON= manager.get("BotonesGemas/gemaVerde_ON.png");
        texturaGemaVerdeOFF = manager.get("BotonesGemas/gemaVerde_OFF.png");
        gemaVerde = new Boton(texturaGemaVerdeOFF,texturaGemaVerdeON,margen+(texturaGemaVerdeOFF.getWidth()/2),margen+(texturaGemaVerdeOFF.getHeight()/2));
        gemaVerde.boton.sprite.setAlpha(0);
        //Gema Roja
        texturaGemaRojaON = manager.get("BotonesGemas/gemaRoja_ON.png");
        texturaGemaRojaOFF = manager.get("BotonesGemas/gemaRoja_OFF.png");
        gemaRoja = new Boton(texturaGemaRojaOFF, texturaGemaRojaON,margen+(3*texturaGemaRojaOFF.getWidth()/2),margen+(texturaGemaRojaOFF.getHeight()/2));
        gemaRoja.boton.sprite.setAlpha(0);
        //Gema Azul
        texturaGemaAzulON =manager.get("BotonesGemas/gemaAzul_ON.png");
        texturaGemaAzulOFF = manager.get("BotonesGemas/gemaAzul_OFF.png");
        gemaAzul = new Boton(texturaGemaAzulOFF, texturaGemaAzulON,margen+(texturaGemaVerdeOFF.getWidth()),(3*texturaGemaVerdeOFF.getHeight()/2)-10);
        gemaAzul.boton.sprite.setAlpha(0);
        //Boton Pausa
        texturaPausaON = manager.get("Botones/pausa_ON.png");
        texturaPausaOFF = manager.get("Botones/pausa_OFF.png");
        botonPausa = new Boton(texturaPausaOFF,texturaPausaON,margen+(texturaGemaVerdeOFF.getWidth()),ALTO-(margen/2) -(texturaPausaOFF.getHeight()/2));
    }

    private void crearBarra() {
        //Los parametros de ancho final y distancia son para escalar el avance.
        fondoBarra = manager.get("Utileria/atrasBarraAvance.png");
        barraGS = new BarraAvance(0,1,0,1,(ANCHO/4), ALTO-margen*3,12,ANCHO/8,distanciaVerde);//27000 es 1 minuto y medio
        barraRS = new BarraAvance(1,0,0,1,(ANCHO/4)+(ANCHO/8), ALTO-margen*3,12,ANCHO/8,distanciaRojo);//54000 es para 3 minutos
        barraBS = new BarraAvance(0,0,1,1,(ANCHO/4)+(ANCHO*2/8), ALTO-margen*3,12,ANCHO/8,distanciaAzul);//54000 es para 3 minutos
        barraWS = new BarraAvance(1,1,1,1,(ANCHO/4)+(ANCHO*3/8), ALTO-margen*3,12,ANCHO/8, distanciaBlanco);//54000 es para 3 minutos
    }

    private void crearVidas() {
        texturaVida = manager.get("Utileria/vida.png");
        //Se usa getHeight en ambas (x y) para que quede el mismo margen tanto arriba como a la derecha
        vidas = new Vidas(texturaVida,ANCHO-margen-(texturaVida.getHeight()/2),margen+(texturaVida.getHeight()/2));
    }

    private Button crearBoton(String archivo, String archivoclick) {
        Texture texturaBoton=manager.get(archivo);
        TextureRegionDrawable trdBtnRunner=new TextureRegionDrawable(texturaBoton);

        Texture texturaClick=manager.get(archivoclick);
        TextureRegionDrawable trdBtnClick=new TextureRegionDrawable(texturaClick);

        return new Button(trdBtnRunner,trdBtnClick);
    }

    private void crearPersonajes() {
        //Personaje principal: Lumil (Blanco)
        texturaLumilJugando = manager.get("Personajes/LumilJuego.png");
        lumil = new Lumil(texturaLumilJugando,ANCHO/2, ALTO/2,4,1,duracionFrameLumil,1);
        lumil.sprite.setScale(1f);
        //System.out.println(lumil.sprite.getX());

        //Personaje principal: Lumil (Verde)
        texturaLumilG = manager.get("Personajes/lumilG.png");
        lumilG = new Lumil(texturaLumilG,ANCHO/2, ALTO/2,4,1,duracionFrameLumil *  (1/incrementoVelocidadVerde),1);

        //Personaje principal: Lumil (Rojo)
        texturaLumilR = manager.get ("Personajes/lumilR.png");
        lumilR = new Lumil(texturaLumilR,ANCHO/2, ALTO/2,4,1,duracionFrameLumil,1);

        //Personaje principal: Lumil (Azul)
        texturaLumilB = manager.get ("Personajes/lumilB.png");
        lumilB = new Lumil(texturaLumilB,ANCHO/2, ALTO/2,4,1,duracionFrameLumil,1);

        //Enemigo principal: Oscuridad
        texturaOscuridad = manager.get("Personajes/oscuridad.png");
        oscuridad = new Oscuridad(texturaOscuridad,positionX,positionY,2,2,1/8f,2);

        texto=new Texto();
    }

    private void crearTexturaHijoOscuridad(){
        //Para el que quita vida
        texturaHijoOscuridad = manager.get("Personajes/HijoOsc_sprite.png");
        arrHijosOscuridad= new Array<>();

        //Para el que bloquea
        texturaBloque = manager.get("Personajes/bloque.png");

        arrBloques = new Array<>();
    }

    private void crearEsgrun(){
        Texture texturaEsgrun = manager.get("Personajes/Esgrun.png");
        esgrun = new Esgrun(texturaEsgrun, ANCHO + texturaEsgrun.getWidth(), positionY);
    }

    private void crearRojel(){
        Texture texturaRojel = manager.get("Personajes/Rojel.png");
        rojel = new Rojel(texturaRojel, ANCHO + texturaRojel.getWidth(), positionY);
    }

    private void crearLuz(){

        texturaLuz = manager.get("Personajes/luzPersonaje.png");

        luz = new Luz(texturaLuz,ANCHO+texturaLuz.getWidth(),ALTO/2,4,1,1/7f,2);
    }

    private void crearShiblu(){
        Texture texturaShiblu = manager.get("Personajes/Shiblu.png");
        shiblu = new Shiblu(texturaShiblu, ANCHO + texturaShiblu.getWidth(), positionY);
    }

    //Este método sirve para crear los objetos que se moveran y bloquearán el paso al jugador
    private void crearBloques() {
        //Crear Enemigo
        float xBloque = MathUtils.random(valorXMinExt,valorXMaxExt);
        float yBloque = MathUtils.random(valorYMinMarg,valorYMaxMarg);
        Bloque bloque = new Bloque(texturaBloque,xBloque,yBloque,3,2,1/8f,1);
        bloque.sprite.setScale(MathUtils.random(escalaMinimaBloque,escalaMaximaBloque));
        arrBloques.add(bloque);
    }

    private void crearHijosOscuridad(){
        //Crear Enemigo
        float xHijo = MathUtils.random(ANCHO - valorXMaxExt,ANCHO-valorXMinExt);
        float yHijo = MathUtils.random(valorYMinMarg,valorYMaxMarg);
        HijoOscuridad hijoOscuridad = new HijoOscuridad(texturaHijoOscuridad,xHijo,yHijo,5,2,1/10f,1);

        if (seccion==EstadoSeccion.VERDE || seccion==EstadoSeccion.ROJO){
            hijoOscuridad.sprite.setScale(MathUtils.random(0.55f,0.7f));
        }else {hijoOscuridad.sprite.setScale(MathUtils.random(0.65f,0.85f));}
        arrHijosOscuridad.add(hijoOscuridad);
    }


    private void crearFondo() {
        //Crear elementos del bosque en movimiento
        bosqueCapaCero = manager.get("Escenarios/capa0.png");
        bosqueCapaUno = manager.get("Escenarios/capa1.png");
        bosqueCapaDos = manager.get("Escenarios/capa2.png");
        bosqueCapaTres = manager.get("Escenarios/capa3Completa.png");
        bosqueCapaCuatro = manager.get("Escenarios/capa4Completa.png");
        bosqueCapaEstatica = manager.get("Escenarios/capaEstatica.png");
        bosqueCapaFrente = manager.get("Escenarios/capaFrente.png");
        bosqueCapaFrenteVerde = manager.get("Escenarios/capaFrenteVerde.png");

        bosqueParallax = new FondoParallax(bosqueCapaCero,bosqueCapaEstatica,bosqueCapaUno,bosqueCapaDos,
                bosqueCapaTres, bosqueCapaCuatro, bosqueCapaFrente,bosqueCapaFrenteVerde);

        //Crear elementos del fondo del bosque
        nubeFondo = manager.get("Escenarios/nubes.png");
        texturaLuna = manager.get("Escenarios/Luna.png");
        luna = new Objeto(texturaLuna,0,0);

        texturaEfectoAzulAtras = manager.get("Escenarios/efectoAzulAtras.png");
        texturaEfectoAzulFrente = manager.get("Escenarios/efectoAzulFrente.png");
        texturaEfectoRojoAtras = manager.get("Escenarios/efectoRojoAtras.png");
        texturaEfectoRojoFrente = manager.get("Escenarios/efectoRojoFrente.png");

        efectoAzulAtras = new Objeto(texturaEfectoAzulAtras,ANCHO/2,ALTO/2);
        efectoAzulAtras.sprite.setAlpha(0);
        efectoAzulFrente = new Objeto(texturaEfectoAzulFrente,ANCHO/2,ALTO/2);
        efectoAzulFrente.sprite.setAlpha(0);
        efectoRojoAtras = new Objeto(texturaEfectoRojoAtras,ANCHO/2,ALTO/2);
        efectoRojoAtras.sprite.setAlpha(0);
        efectoRojoFrente = new Objeto(texturaEfectoRojoFrente,ANCHO/2,ALTO/2);
        efectoRojoFrente.sprite.setAlpha(0);
    }

    @Override
    public void render(float delta) {

        controlSonidoPrincipal(delta);

        if (estado != EstadoJuego.PAUSADO){
            actualizar(delta);

        }

        if(estado == EstadoJuego.PAUSADO)
            velocidad = 0;


        //Estado del juego
        if (contadorVidas == 0) {
            estado = EstadoJuego.PIERDE;
        }

        //Tiempo que pasó entre render.
        tiempoLumil += Gdx.graphics.getDeltaTime();
        //Tiempo que pasó entre render.
        tiempoOsc += Gdx.graphics.getDeltaTime();
        //Tiempo que pasó entre render.
        tiempoHijoOsc += Gdx.graphics.getDeltaTime();

        borrarPantalla(0.14f, 0.14f, 0.14f);
        batch.setProjectionMatrix(camara.combined);

        //Se dibujan los elementos
        batch.begin();

        //Dibujar Estrellas
        if (arrEstrellas!=null){
            for(BrilloLumil estrella: arrEstrellas){
                estrella.render(batch);
            }

        }

        //Dibujar elementos del bosque
        //bosque.movSeccionesCompletas(velocidad, delta, batch, true);
        //Dibujar elementos del bosque
        batch.draw(nubeFondo,0,0);
        luna.render(batch);

        //Dibujar Efectos Fondo Atras
        efectoRojoAtras.render(batch);
        efectoAzulAtras.render(batch);

        bosqueParallax.render(batch);

        //Dibujar Efectos Fondo Frente
        efectoRojoFrente.render(batch);
        efectoAzulFrente.render(batch);


        //Dibujar brillo del personaje principal
        brilloLumil.render(batch);

        //Dibujar personaje principal
        dibujarLumil(batch);


        //Dibujar los Hijos que quitan vida
        for (HijoOscuridad hijoOscuridad : arrHijosOscuridad) {
            hijoOscuridad.animationRender(batch, tiempoHijoOsc);
        }

        //Dibujar los bloques
        for (Bloque bloque : arrBloques) {
            bloque.animationRender(batch, tiempoLumil);
        }

        //Dibujar
        if(esgrun!=null)
            esgrun.render(batch);

        if(rojel!=null)
            rojel.render(batch);

        if(shiblu!=null)
            shiblu.render(batch);

        if(luz!=null)
            luz.animationRender(batch,tiempoLumil);

        //Dibujar los elementos del bosque de frente
        bosqueParallax.renderFrente(batch);

        //Dibujar enemigo oscuridad
        oscuridad.animationRender(batch, tiempoOsc);

        // Dibujar gemas

        gemaVerde.render(batch);
        gemaRoja.render(batch);
        gemaAzul.render(batch);

        botonPausa.render(batch);


        //Dibujar vidas
        if (transicionFade.isFadeInFinished){
            vidas.vidasRender(contadorVidas, batch);
        }


       //Dibujar lo de atras de la barra de avance
        batch.draw(fondoBarra,(ANCHO/4)-4,ALTO-fondoBarra.getHeight());

        //Mostrar mensaje
        texto.mostrarMensaje(batch,"TIME",ANCHO*7/8f, ALTO-(fondoBarra.getHeight()/5));
        texto.mostrarMensaje(batch,juego.getCuentaSegundos(),ANCHO*7/8f, ALTO-(fondoBarra.getHeight()/2f));

        batch.end();

        distanciaRecorridaControl += velocidad *delta;

        //Velocidad
        //Eso divide la pantalla en las secciones de cada color.
        //Para cambiar de seccion se debe cumpir la condición de la distancia y de que chocaste con el monito
        aparicion += delta;


        //Dibujar barra progreso
        if(estado!=EstadoJuego.INICIANDO){
            dibujarBarras(delta);
        }

        if (oscuridad.getYaMordio() || isQuitPressed ){
            fadeNegro();
        }

        //Dibujar el Fade de entrada
        transicionFade.render(camara);

        //Dibujar el Fade de entrada
        transicionGana.render(camara);

        if(Gdx.input.isKeyPressed(Input.Keys.BACK)){
            if (estado != EstadoJuego.INICIANDO){
                if (escenaPausa == null) {      // Inicialización lazy
                    escenaPausa = new EscenaPausa(vista);
                }
                estado = EstadoJuego.PAUSADO;
                // CAMBIAR el procesador de entrada
                Gdx.input.setInputProcessor(escenaPausa);
            }
        }

        if (estado == EstadoJuego.PAUSADO && escenaPausa != null) {
            escenaPausa.act();
            escenaPausa.draw();
        } else {escenaPausa=null;}
    }

    private void controlSonidoPrincipal(float delta) {

        sonidoPrincipalFadeIn(delta);
        sonidoPricipalFadeOut(delta);
        sonidoPrincipalCambioPitch(delta);


    }

    private void sonidoPrincipalCambioPitch(float delta) {

        //Control para el cambio del Pitch del sonido principal
        if (isPitchChanging){
            float valorPitchDeseado = 1;
            switch (seccion) {
                case VERDE:
                    valorPitchDeseado = 1;
                    break;
                case ROJO:
                    valorPitchDeseado = 1+((VALOR_MAXIMO_PITCH-1)/3);
                    break;
                case AZUL:
                    valorPitchDeseado = 1+(2*(VALOR_MAXIMO_PITCH-1)/3);
                    break;
                case BLANCO:
                    valorPitchDeseado = VALOR_MAXIMO_PITCH;
                    break;
            }
            valorPitch +=0.005f;//incrementa poco a poco
            if (valorPitch>=valorPitchDeseado){
                valorPitch = valorPitchDeseado;
                isPitchChanging = false;
            }

            sonidoJuego.setPitch(idSonidoJuego,valorPitch); //asignar el nuevo valor del pitch
        }
    }

    private void sonidoPricipalFadeOut(float delta) {


        //Control para el Fade out
        if (isSonidoJuegoFadingOut){
            volumenSonidoPrincipal-=(5*delta/TIEMPO_FADE_SONIDOJUEGO);
            if(volumenSonidoPrincipal<=0){
                volumenSonidoPrincipal = 0;
                isSonidoJuegoFadingOut=false;
            }
            sonidoJuego.setVolume(idSonidoJuego,volumenSonidoPrincipal);//Asignar el nuevo valor de volumen.
        }
    }

    private void sonidoPrincipalFadeIn(float delta) {

        //Control para el Fade in
        if (isSonidoJuegoFadingIn){
            volumenSonidoPrincipal+=(delta/TIEMPO_FADE_SONIDOJUEGO);
            if(volumenSonidoPrincipal>=VOLUMEN_MAXIMO_SONIDOJUEGO){
                volumenSonidoPrincipal = VOLUMEN_MAXIMO_SONIDOJUEGO;
                isSonidoJuegoFadingIn=false;
            }
            sonidoJuego.setVolume(idSonidoJuego,volumenSonidoPrincipal);//Asignar el nuevo valor de volumen.

        }

    }

    private void fadeNegro() {
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        rectNegro.setProjectionMatrix(camara.combined);
        rectNegro.setColor(0, 0, 0, alfaRectanguloNegro);
        rectNegro.begin(ShapeRenderer.ShapeType.Filled);
        rectNegro.box(0,0,0,ANCHO,ALTO,0);
        alfaRectanguloNegro += (Gdx.graphics.getDeltaTime()*1.5f);
        rectNegro.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);

        if (alfaRectanguloNegro >= 1){
            sonidoJuego.stop();//Detener por completo el sonido del juego principal de fondo
            if (estado==EstadoJuego.PIERDE){
                juego.setScreen(new PantallaPerdida(juego));
            } else {
                juego.setScreen(new PantallaMenu(juego));
            }
        }

    }

    private void dibujarLumil(SpriteBatch batch) {

        switch (colorLumil) {
            case VERDE:
                lumilG.animationRender(batch, tiempoLumil);
                break;
            case ROJO:
                lumilR.animationRender(batch, tiempoLumil);
                break;
            case AZUL:
                lumilB.animationRender(batch, tiempoLumil);
                break;
            case BLANCO:
                lumil.animationRender(batch, tiempoLumil);
                break;
        }

    }

    private void actualizar(float delta) {

        actualizarBosque(delta);
        actualizarEstrellas();
        //Actualizar el Brillo (Posición y forma)
        actualizarBrillo();
        if (estado == EstadoJuego.INICIANDO){
            contadorInicial +=delta;
            transicionFade.fadeIn(delta,DURACION_FADE);
            introFondo(delta);

            if (transicionFade.isFadeInFinished){
                estado = EstadoJuego.JUGANDO;
                //estado = EstadoJuego.GANA;
            }

        }

        //Cuando la velocidad sea = 0, la oscuridad avanzará rápido por nuestro personaje.
        if (estado == EstadoJuego.JUGANDO) {
            juego.incrementarCuentaSegundos(delta);
            actualizarFondo(delta);

            actualizarGemas(delta);

            //Detecta el cambio de sección
            if (seccion!=seccionAnterior){
                //Se ejecuta una sola vez cada que se cambia de sección
                cambioSeccion();
            }

            distanciaRecorrida += velocidad * delta;

            colisionLumilFront = hijoOscuridadColisionFront();
            colisionLumilUp = hijoOscuridadColisionUp();
            colisionLumilDown = hijoOscuridadColisionDown();
            hijoOscuridadColisionGeneral();


            /*
            if(!boolPowerUpGemaRoja){
                colisionLumilFront = hijoOscuridadColisionFront();
                colisionLumilUp = hijoOscuridadColisionUp();
                colisionLumilDown = hijoOscuridadColisionDown();
            }

             */


            //Esto es lo de CORREGIRBLOQUES

            timerCrearBloque += delta;
            if (timerCrearBloque >= tiempoParaCrearBloque && colisionLumilFront == false) {
                timerCrearBloque = 0;
                crearBloques();
            }

            timerCrearHijoOscuridad += delta;
            if (timerCrearHijoOscuridad >= tiempoParaCrearHijoOscuridad) {
                timerCrearHijoOscuridad = 0;
                crearHijosOscuridad();
            }


            //Mover Oscuridad
           // velocidadOscuridad = velocidadOscVerde;
            moverOscuridad(delta);

            //Mover Hijo de Oscuridad (va afuera porque se sige moviendo aunque lúmil no se mueva, es como la oscuridad grande)
            moverHijoOscuridad(delta);

            //Bloques
            moverBloques(delta);

            //Mover y Depurar Oscuridad
            oscuridadColision();


            //--------------------------------------------------------------------------------------
            float dyLumilUp =0;
            float dyLumilDown=0;

            if (colisionLumilFront  && boolPowerUpGemaRoja ==false) {
                //moverLumil(isMoving, dyLumil);
                dyLumilUp =dyLumil;
                dyLumilDown=dyLumil;
                velocidad = 0;
            } else {
                dyLumilUp =dyLumil;
                dyLumilDown=dyLumil;
                //moverLumil(isMoving, dyLumil);
                velocidad = velocidadInicial;
            }

            if (colisionLumilDown && boolPowerUpGemaRoja ==false) {
                    //moverLumilxy(isMoving, dyLumil, 0);
                    //oscuridadColision();
                    dyLumilUp =dyLumil;
                    dyLumilDown=0;
                    //velocidad = velocidadInicial;
            } else  if (colisionLumilUp && boolPowerUpGemaRoja ==false) {
                    //moverLumilxy(isMoving, 0, dyLumil);
                    //oscuridadColision();
                    dyLumilUp =0;
                    dyLumilDown=dyLumil;
                    //velocidad = velocidadInicial;
            }

            moverLumilxy(isMoving, dyLumilUp, dyLumilDown);

            /*
            if (colisionLumilDown) {
                if (boolPowerUpGemaRoja ==false){
                    moverLumilxy(isMoving, dyLumil, 0);
                    //oscuridadColision();
                }
            } else  if (colisionLumilUp) {
                if (boolPowerUpGemaRoja==false){
                    moverLumilxy(isMoving, 0, dyLumil);
                    //oscuridadColision();
                }
            } else if (colisionLumilFront) {
                moverLumil(isMoving, dyLumil);
                if (boolPowerUpGemaRoja == false) {
                    velocidad = 0;
                } else {
                    velocidad = velocidadInicial;
                }
            } else {
                moverLumil(isMoving, dyLumil);
                velocidad = velocidadInicial;
            }

             */

            //--------------------------------------------------------------------------------------

            if(boolPowerUpGemaVerde){
                powerUpGemaVerde();
            }

            if(boolPowerUpGemaRoja){
                powerUpGemaRoja();
            }

            if(boolPowerUpGemaAzul){
                powerUpGemaAzul();
            }

            if(!powerups){
                activePowerups();
            }

            if(esgrun!=null){
                moverEsgrun();
                if(depurarEsgrun(delta)){
                    seccion = EstadoSeccion.ROJO;
                }
                if(esgrunPierde()){
                    estado = EstadoJuego.PIERDE;
                }

                //if (esgrun.alfaSprite == 0){
                    //esgrun = null;
                //}
            }

            if(rojel!=null){
                moverRojel();
                if(depurarRojel()){
                    seccion = EstadoSeccion.AZUL;
                }

                if(rojelPierde()){
                    estado = EstadoJuego.PIERDE;
                }
            }

            if(shiblu!=null){
                moverShiblu();
                if(depurarShiblu()){
                    seccion = EstadoSeccion.BLANCO;
                }
                if(shibluPierde()){
                    estado = EstadoJuego.PIERDE;
                }
            }

            if(luz!=null){
                moverLuz();
                depurarLuz();
                if(luzPierde()){
                    estado = EstadoJuego.PIERDE;
                }
            }

            // Código Ricardo
            /*
            if (hijoOscuridad != null) {
                moverHijoOscuridad(delta);
            }

             */
            //Depurar Elementos
            depurarBloques();
            depurarHijosOscuridad();


        } //----------------------------------------------------------------------------------------
        if (estado == EstadoJuego.PIERDE) {
            velocidad = 0;
            velocidadOscuridad = 0;
            aparicion = 0;
            //Aqui se debe agregar la animación antes de cambiar de pantlla
            isSonidoJuegoFadingOut=true; //comenzar a desaparecer el sonido
            oscuridad.granMordida();

        }

        if (estado == EstadoJuego.GANA) {
            velocidad = 0;
            velocidadOscuridad = 0;
            //falta la transición a Blanco, en esta poner isPitchChanging = true;
            transicionGana.fadeOut(delta,DURACION_FADE/2);
            isSonidoJuegoFadingOut=true; //Comenzar el fade de la musica principal del juego
            if (transicionGana.isFadeOutFinished){
                sonidoJuego.stop();
                juego.setScreen(new PantallaGana(juego));
            }
            //juego.setScreen(new PantallaGana(juego));
            //Aqui se llama la secuencia de final (o sea la pantalla de andrea)
        }

    }

    private void actualizarEstrellas() {
        if (arrEstrellas != null) {
            for (BrilloLumil estrella : arrEstrellas) {
                estrella.actualizar(1, 1, 1, 0.02f + MathUtils.random(0, .03f));
            }
        }
    }

    private void actualizarFondo(float delta) {
        switch (seccion){
            case VERDE:
                if(esgrun==null){
                    float dxLunaG = (ANCHO/4)/(duracionVerde/delta);
                    float dyLunaG = (ALTO/4)/(duracionVerde/delta);
                    luna.sprite.setX(luna.sprite.getX()+dxLunaG);
                    luna.sprite.setY(luna.sprite.getY()+dyLunaG);
                }
                break;
            case ROJO:
                if (rojel==null){
                    float dxLunaR = (ANCHO/8)/(duracionRojo/delta);
                    float dyLunaR = (ALTO/6)/(duracionRojo/delta);
                    float dScaleLunaR = (0.3f)/(duracionRojo/delta);
                    luna.sprite.setX(luna.sprite.getX()+dxLunaR);
                    luna.sprite.setY(luna.sprite.getY()+dyLunaR);
                    luna.sprite.setScale(luna.sprite.getScaleX()-dScaleLunaR,luna.sprite.getScaleY()-dScaleLunaR);
                }
                break;
            case AZUL:
                if (shiblu==null){
                    float dxLunaA = (ANCHO/8)/(duracionAzul/delta);
                    float dyLunaA = (ALTO/8)/(duracionAzul/delta);
                    float dScaleLunaA = (0.3f)/(duracionAzul/delta);
                    luna.sprite.setX(luna.sprite.getX()+dxLunaA);
                    luna.sprite.setY(luna.sprite.getY()+dyLunaA);
                    luna.sprite.setScale(luna.sprite.getScaleX()-dScaleLunaA,luna.sprite.getScaleY()-dScaleLunaA);
                }
                efectoRojoAtras.fadeIn(delta,DURACION_FADE*2/3f);
                efectoRojoFrente.fadeIn(delta,DURACION_FADE*2/3f);
                break;
            case BLANCO:
                if (luz==null){
                    float dxLunaW = (ANCHO/10)/(duracionBlanco/delta);
                    float dyLunaW = (ALTO/10)/(duracionBlanco/delta);
                    float dScaleLunaW = (0.2f)/(duracionBlanco/delta);
                    luna.sprite.setX(luna.sprite.getX()+dxLunaW);
                    luna.sprite.setY(luna.sprite.getY()+dyLunaW);
                    luna.sprite.setScale(luna.sprite.getScaleX()-dScaleLunaW,luna.sprite.getScaleY()-dScaleLunaW);
                }
                efectoAzulAtras.fadeIn(delta,DURACION_FADE*2/3f);
                efectoAzulFrente.fadeIn(delta,DURACION_FADE*2/3f);
                break;

        }
    }

    private void introFondo(float delta) {
        float dxLunaInicial = (ANCHO/4)/(DURACION_FADE/delta);
        float dyLunaInicial = (ALTO/4)/(DURACION_FADE/delta);
        luna.sprite.setX(luna.sprite.getX()+dxLunaInicial);
        luna.sprite.setY(luna.sprite.getY()+dyLunaInicial);
    }

    private void actualizarBosque(float delta) {
        bosqueParallax.actualizar(velocidad,delta,seccion);
    }

    private void actualizarGemas(float delta) {
        //Aparecer las Gemas
        if (activarPowerUpGemaVerde){
            if (contadorFadeInGemaVerde<=2){
                contadorFadeInGemaVerde+=delta;
                gemaVerde.boton.fadeIn(delta,2); //Se tarda 2 segundos en aparecer
            }else if (powerups){
                gemaVerde.abled();
            } else {gemaVerde.disabled();}
        }

        if (activarPowerUpGemaAzul){
            if (contadorFadeInGemaAzul<=2){
                contadorFadeInGemaAzul+=delta;
                gemaAzul.boton.fadeIn(delta,2); //Se tarda 2 segundos en aparecer
            }else if (powerups){
                gemaAzul.abled();
            } else {gemaAzul.disabled();}
        }

        if (activarPowerUpGemaRoja){
            if (contadorFadeInGemaRoja<=2){
                contadorFadeInGemaRoja +=2;
                gemaRoja.boton.fadeIn(delta,2); //Se tarda 2 segundos en aparecer
            }else if (powerups){
                gemaRoja.abled();
            } else {gemaRoja.disabled();}
        }


    }


    private void cambioSeccion() {
        // Activar la Múscia del poder
        //Actualizan las velocidaddades de los frames en las animacioens de Lumil
        //El número 1 en Tipo, representa que es un Loop infinito el tipo de animación de los frames.
        lumil.animationUpdate((duracionFrameLumil)*(velocidadVerde/ velocidadInicial),1);
        lumilR.animationUpdate((duracionFrameLumil)*(velocidadVerde/ velocidadInicial),1);
        lumilG.animationUpdate((duracionFrameLumil/incrementoVelocidadVerde)*(velocidadVerde/ velocidadInicial),1);
        lumilB.animationUpdate((duracionFrameLumil)*(velocidadVerde/ velocidadInicial),1);

        //Cambiar el pitch porque cambió la sección
        isPitchChanging=true;

        //Aqui se colocan las modificaciones de queden ocurrir al inicio de una sección en específico
        //la sección verde no se incluye porque los valores para ésta son los iniciales.
        switch (seccion){
            case ROJO:
                //Aqui pueden asignarse los nuevos valores de velocidad para lumil, la oscuridad y los hijos de la oscuridad.
                velocidadInicial = velocidadRojo;
                velocidadOscuridad = velocidadOscRojo;
                velocidadHijosOscuridad = velocidadHijoOscRojo;
                tiempoParaCrearHijoOscuridad = 2;
                dyLumil = DY_LUMIL_INICIAL*1.2f;
                tiempoParaCrearBloque =1.6f; //Se espera esos segundos en crear el bloque.
                escalaMinimaBloque = 0.65f;
                escalaMaximaBloque = 0.90f;
                break;
            case AZUL:
                //Aqui pueden asignarse los nuevos valores de velocidad para lumil, la oscuridad y los hijos de la oscuridad.
                velocidadInicial = velocidadAzul;
                velocidadOscuridad = velocidadOscAzul;
                velocidadHijosOscuridad = velocidadHijoOscAzul;
                tiempoParaCrearHijoOscuridad = 1.9f;
                dyLumil = DY_LUMIL_INICIAL*1.4f;
                tiempoParaCrearBloque =0.6f; //Se espera esos segundos en crear el bloque.
                escalaMinimaBloque = 0.5f;
                escalaMaximaBloque = 0.6f;
                break;
            case BLANCO:
                //Aqui pueden asignarse los nuevos valores de velocidad para lumil, la oscuridad y los hijos de la oscuridad.
                velocidadInicial = velocidadBlanco;
                velocidadOscuridad = velocidadOscBlanco;
                velocidadHijosOscuridad = velocidadHijoOscBlanco;
                tiempoParaCrearHijoOscuridad = 0.65f;
                dyLumil = DY_LUMIL_INICIAL*1.6f;
                tiempoParaCrearBloque =1.4f; //Se espera esos segundos en crear el bloque.
                escalaMinimaBloque = 0.55f;
                escalaMaximaBloque = 0.7f;
                break;
        }
        seccionAnterior = seccion;
    }

    private void actualizarBrillo() {
        //Mover Brillo
        brilloLumil.mover(lumil.sprite.getY() + (lumil.sprite.getHeight() / 2));

        //Asignar Color de brillo y simular la acción del brillo
        switch (colorLumil) {
            case VERDE:
                brilloLumil.actualizar(0,1,0,0.75f);
                break;
            case ROJO:
                brilloLumil.actualizar(1,0,0,0.75f);
                break;
            case AZUL:
                brilloLumil.actualizar(0,0,1,0.75f);
                break;
            case BLANCO:
                brilloLumil.actualizar(1,1,1,0.75f);
                break;
        }

    }

    private void returnVelocidadBosque(){
        velocidad = velocidadInicial;
    }

    private void depurarHijosOscuridad() {

        //Eliminar bloques Fuera del rango de la pantalla
        for (int  i=arrHijosOscuridad.size-1; i>=0; i--){
            HijoOscuridad hijoOscuridad = arrHijosOscuridad.get(i);


            if(arrHijosOscuridad!= null && lumil.getRectangle().overlaps(hijoOscuridad.getRectangle()) && hijoOscuridad.isHijoFadingOut==false) {
                hijoOscuridad.setAnimationSetion(3,9);
                hijoOscuridad.animationUpdate(1/10f,5);
                hijoOscuridad.isHijoFadingOut=true;
                tiempoHijoOsc = 0;
                if(!boolPowerUpGemaAzul)//Cambiar por azul
                    contadorVidas --;
                sonidoquitavidas.play();
                continue;
            }


            if (hijoOscuridad.isHijoGone()){
                arrHijosOscuridad.removeIndex(i);
            }

            hijoOscuridad.incrementoTiempoSalida();

            if(arrHijosOscuridad!= null && hijoOscuridad.getX()>(3*ANCHO/2)) { //Logicamente necesito solo la X del objeto
                arrHijosOscuridad.removeIndex(i);
            }
        }
    }

    private boolean esgrunPierde(){
        if(esgrun!=null && esgrun.sprite.getX()<(0)) {
            return true;
        }
        return false;
    }

    private boolean rojelPierde(){
        if(rojel!=null && rojel.sprite.getX()<(0)) {
            return true;
        }
        return false;
    }

    private boolean shibluPierde(){
        if(shiblu!=null && shiblu.sprite.getX()<(0)) {
            return true;
        }
        return false;
    }

    private boolean luzPierde(){
        if(luz != null && luz.sprite.getX()<(0)) {
            return true;
        }
        return false;
    }

    private boolean depurarEsgrun(float delta) {
        if(lumil.getRectangle().overlaps(esgrun.getRectangle())){
            esgrun = null;
            //esgrun.fadeOut(delta,2);
            activarPowerUpGemaVerde = true;
            sonidoPrimarios.play();
            return true;
        }
        return false;
    }

    private boolean depurarRojel() {
        if(lumil.getRectangle().overlaps(rojel.getRectangle())){
            sonidoPrimarios.play();
            rojel = null;
            activarPowerUpGemaRoja = true;
            return true;
        }
        return false;
    }

    private void depurarLuz() {
        if(lumil.getRectangle().overlaps(luz.getRectangle())){
            luz = null;
            sonidoTocaLuzBlanca.play(); //Este sonido debe de estar en la colisión
            estado = EstadoJuego.GANA;
        }
    }

    private boolean depurarShiblu() {
        if(lumil.getRectangle().overlaps(shiblu.getRectangle())){
            sonidoPrimarios.play();
            shiblu = null;
            activarPowerUpGemaAzul = true;
            return true;
        }
        return false;
    }

    private void depurarBloques(){
        //Eliminar bloques Fuera del rango de la pantalla
        for (int  i=arrBloques.size-1; i>=0; i--){
            Bloque bloque = arrBloques.get(i);
            if(arrBloques!= null && bloque.getX()<-(ANCHO/2)) { //Logicamente necesito solo la X del objeto
                arrBloques.removeIndex(i);
            }
        }
    }

    private boolean hijoOscuridadColisionFront(){
        for(Bloque bloque: arrBloques){
            if(lumil.getFrontRectangle().overlaps(bloque.sprite.getBoundingRectangle())){
                return true;
            }
        }

        return false;
    }

    private boolean hijoOscuridadColisionUp(){
        for(Bloque bloque: arrBloques){
            if(lumil.getUpperRectangle().overlaps(bloque.sprite.getBoundingRectangle())){
                return true;
            }
        }
        return false;
    }

    private boolean hijoOscuridadColisionDown(){
        for(Bloque bloque: arrBloques){
            if(lumil.getLowerRectangle().overlaps(bloque.sprite.getBoundingRectangle())){
                return true;
            }
        }
        return false;
    }

    private void hijoOscuridadColisionGeneral(){
        for(Bloque bloque: arrBloques){
            if(lumil.getLowerRectangle().overlaps(bloque.sprite.getBoundingRectangle())
            || lumil.getUpperRectangle().overlaps(bloque.sprite.getBoundingRectangle())
            || lumil.getFrontRectangle().overlaps(bloque.sprite.getBoundingRectangle())){
                bloque.cambiarBloque(true);
            } else {
                bloque.cambiarBloque(false);
            }
        }
    }


    private void oscuridadColision() {
        //Si la posición en X (izquierda) de Lumil es mayor que la posición en X (derecha) de la oscuridad - 100 px, quiere decir que ya lo tocó
        //El 100 es un número arbitrario elegido de acuerdo al Sprite de Lumil
        if(lumil.sprite.getX()<=(oscuridad.sprite.getX()+oscuridad.sprite.getWidth()-120)) {
            contadorVidas=0;//Muere =(
            sonidocomeOscuridad.play();
            isSonidoJuegoFadingOut=true; //Comenzar el fade de la musica principal del juego
        }

    }

    private void moverEsgrun(){
        esgrun.moverHorizontal(DX_PASO_ESGRUN);
    }

    private void moverRojel(){
        rojel.moverHorizontal(DX_PASO_ROJEL);
    }

    private void moverShiblu(){
        shiblu.moverHorizontal(DX_PASO_SHIBLU);
    }

    private void moverLuz(){
        luz.moverHorizontal(DX_PASO_LUZ);
    }

    private void moverBloques(float delta){
        //Mover los Enemigos
        for (Bloque bloque:arrBloques) {
            bloque.mover(delta, velocidad);
        }
        //depurarBloques();
    }

    public void moverLumil(boolean Moving, float DELTAY){

        //Realmente el que realiza la función de moverse y girar es Lumil (blanco), cuando se cambia de color, éstos toman la posición y la rotación de Lumil blanco.

        switch (colorLumil){
            case VERDE:
                lumilG.sprite.setY(lumil.sprite.getY());
                lumilG.sprite.setRotation(lumil.sprite.getRotation());
                break;
            case ROJO:
                lumilR.sprite.setY(lumil.sprite.getY());
                lumilR.sprite.setRotation(lumil.sprite.getRotation());
                break;
            case AZUL:
                lumilB.sprite.setY(lumil.sprite.getY());
                lumilB.sprite.setRotation(lumil.sprite.getRotation());
                break;
        }


        //Aqui se genera realmente el movimiento
        if (Moving){
            lumil.mover(DELTAY,posicionDedo.y);
        } else {lumil.sprite.setRotation(0);}

        //lumilG.sprite.setY(lumil.sprite.getY());
        //lumilG.sprite.setRotation(lumil.sprite.getRotation());

    }

    public void moverLumilxy(boolean Moving, float up, float down){
        if (Moving){
            lumil.moverxy(up, down, posicionDedo.y);
        } else {lumil.sprite.setRotation(0);}

        //Realmente el que realiza la función de moverse y girar es Lumil (blanco), cuando se cambia de color, éstos toman la posición y la rotación de Lumil blanco.
        switch (colorLumil){
            case VERDE:
                lumilG.sprite.setY(lumil.sprite.getY());
                lumilG.sprite.setRotation(lumil.sprite.getRotation());
                break;
            case ROJO:
                lumilR.sprite.setY(lumil.sprite.getY());
                lumilR.sprite.setRotation(lumil.sprite.getRotation());
                break;
            case AZUL:
                lumilB.sprite.setY(lumil.sprite.getY());
                lumilB.sprite.setRotation(lumil.sprite.getRotation());
                break;
        }

    }

    private void activePowerups() {
        timerGemas += Gdx.graphics.getDeltaTime();
        if(timerGemas >= tiempoParaGemas){
            powerups = true;
            timerGemas = 0;
        }
    }

    /*
    public void regresarVelocidadOscuridad(EstadoSeccion seccion){
        if(seccion == EstadoSeccion.VERDE){
            velocidadOscuridad = velocidadOscVerde;
        }else if(seccion == EstadoSeccion.ROJO){
            velocidadOscuridad = velocidadOscRojo;
        }else if(seccion == EstadoSeccion.AZUL){
            velocidadOscuridad = velocidadOscAzul;
        }else if(seccion == EstadoSeccion.BLANCO){
            velocidadOscuridad = velocidadOscBlanco;
        }
    }

     */

    private void powerUpGemaVerde(){
        //velocidadOscuridad = 0;
        velocidad *= incrementoVelocidadVerde;
        timerPowerUpGemaVerde += Gdx.graphics.getDeltaTime();
        //colorLumil = ColoresLumil.VERDE;
        if(timerPowerUpGemaVerde >= tiempoParaPowerUpGemaVerde){
            //Power Up
            //velocidadOscuridad = velocidadOscVerde;
            velocidad = velocidadInicial;

            //Set original values
            powerupActive = true;
            boolPowerUpGemaVerde = false;
            gemaVerde.inactive();
            colorLumil = ColoresLumil.BLANCO;
            timerGemas = 0;
            powerups = false;
            timerPowerUpGemaVerde = 0;
        }
    }

    private void powerUpGemaRoja(){
        timerPowerUpGemaRoja += Gdx.graphics.getDeltaTime();
        //velocidad *= 1.1f;
        //colorLumil = ColoresLumil.ROJO;
        if(timerPowerUpGemaRoja >= tiempoParaPowerUpGemaRoja){
            velocidad = velocidadInicial;

            //TODO: Red Power Up Stuff

            powerupActive = true;
            boolPowerUpGemaRoja = false;
            gemaRoja.inactive();
            colorLumil = ColoresLumil.BLANCO;
            timerGemas = 0;
            powerups = false;
            timerPowerUpGemaRoja = 0;
        }
    }

    private void powerUpGemaAzul(){

        colisionLumilFront = false;
        colisionLumilUp = false;
        colisionLumilDown = false;

        velocidad *= 1.1f;

        timerPowerUpGemaAzul+= Gdx.graphics.getDeltaTime();
        //colorLumil = ColoresLumil.AZUL;
        if(timerPowerUpGemaAzul >= tiempoParaPowerUpGemaAzul){

            velocidad = velocidadInicial;

            //TODO: Blue Power Up Stuff

            powerupActive = true;
            boolPowerUpGemaAzul = false;
            gemaAzul.inactive();
            colorLumil = ColoresLumil.BLANCO;
            timerGemas = 0;
            powerups = false;
            timerPowerUpGemaAzul = 0;
        }
    }

    private void moverOscuridad(float delta){
            oscuridad.mover(velocidad, velocidadOscuridad, delta);
    }

    private void moverHijoOscuridad(float delta){
        for (HijoOscuridad hijoOscuridad:arrHijosOscuridad) {
            hijoOscuridad.mover(velocidad, velocidadHijosOscuridad,delta);
        }

        //hijoOscuridad.mover(velocidadBosque,velocidadHijosOsc, delta);
    }


    public void dibujarBarras(float delta){
        //Dibujar las barras
        switch (seccion) {
            case VERDE:
                if(distanciaRecorridaG<=distanciaVerde){
                    distanciaRecorridaG += velocidad *delta;
                }else if(esgrun==null){
                    crearEsgrun();
                }
                barraGS.renderAvance(distanciaRecorridaG,camara);
                break;
            case ROJO:
                if(distanciaRecorridaR<=distanciaRojo){
                    distanciaRecorridaR += velocidad *delta;
                }else if(rojel==null){
                    crearRojel();
                }
                barraGS.renderEstatico(camara);
                barraRS.renderAvance(distanciaRecorridaR,camara);
                break;
            case AZUL:
                if(distanciaRecorridaB<=distanciaAzul){
                    distanciaRecorridaB += velocidad *delta;
                }else if(shiblu==null){
                    crearShiblu();
                }
                barraGS.renderEstatico(camara);
                barraRS.renderEstatico(camara);
                barraBS.renderAvance(distanciaRecorridaB,camara);
                break;
            case BLANCO:
                //distanciaRecorridaW += velocidad *delta;
                barraGS.renderEstatico(camara);
                barraRS.renderEstatico(camara);
                barraBS.renderEstatico(camara);
                if(distanciaRecorridaW<=distanciaBlanco){
                    distanciaRecorridaW += velocidad *delta;
                }else if(luz==null){
                    crearLuz();
                }
               // if (distanciaRecorridaW>=velocidadBlanco*duracionBlanco){
                   // barraWS.renderEstatico(camara);
                   // estado = EstadoJuego.GANA;
                barraWS.renderAvance(distanciaRecorridaW,camara);
                break;
        }
    }



    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        // Los assets se liberan a través del assetManager
       // AssetManager assetManager = juego.getAssetManager();
        manager.unload("BotonesGemas/gemaVerde_ON.png");
        manager.unload("BotonesGemas/gemaVerde_OFF.png");
        manager.unload("BotonesGemas/gemaRoja_ON.png");
        manager.unload("BotonesGemas/gemaRoja_OFF.png");
        manager.unload("BotonesGemas/gemaAzul_ON.png");
        manager.unload("BotonesGemas/gemaAzul_OFF.png");
        manager.unload("Botones/pausa_ON.png");
        manager.unload("Botones/pausa_OFF.png");
        manager.unload("Utileria/atrasBarraAvance.png");
        manager.unload("Utileria/brilloLumil.png");
        manager.unload("Utileria/vida.png");
        manager.unload("Personajes/LumilJuego.png");
        manager.unload("Personajes/lumilG.png");
        manager.unload("Personajes/lumilR.png");
        manager.unload("Personajes/lumilB.png");
        manager.unload("Personajes/oscuridad.png");
        manager.unload("Personajes/HijoOsc_sprite.png");
        manager.unload("Personajes/bloque.png");
        manager.unload("Personajes/Esgrun.png");
        manager.unload("Personajes/Rojel.png");
        manager.unload("Personajes/Shiblu.png");
        manager.unload("Personajes/luzPersonaje.png");
        manager.unload("Sonidos/sonidoComeOscuridad.wav");
        manager.unload("Sonidos/sonidoPoderActivado.wav");
        manager.unload("Sonidos/sonidoquitavidas.wav");
        manager.unload("Sonidos/sonidoTocaLuzBlanca.wav");
        manager.unload("Sonidos/JugarLoop.mp3");

        manager.unload("Escenarios/capa0.png");
        manager.unload("Escenarios/capa1.png");
        manager.unload("Escenarios/capa2.png");
        manager.unload("Escenarios/capa3Completa.png");
        manager.unload("Escenarios/capa4Completa.png");
        manager.unload("Escenarios/capaEstatica.png");
        manager.unload("Escenarios/capaFrente.png");
        manager.unload("Escenarios/capaFrenteVerde.png");

        manager.unload("Escenarios/nubes.png");
        manager.unload("Escenarios/Luna.png");

        arrHijosOscuridad.clear();
        arrBloques.clear();
        arrEstrellas.clear();

        juego.stopMusica();
    }

    //Procesador de pantalla

    private class ProcesadorEntrada implements InputProcessor
    {

        @Override
        public boolean keyDown(int keycode) {
            return false;
        }

        @Override
        public boolean keyUp(int keycode) {
            return false;
        }

        @Override
        public boolean keyTyped(char character) {
            return false;
        }

        // se ejecuta justo cuando toca la pantall (a penas pone el dedo)
        //Guarda las coordenadas del dedo y habilita el movimiento hacia rriba y abajo del personaje principal
        @Override
        public boolean touchDown(int screenX, int screenY, int pointer, int button) {

            if(estado==EstadoJuego.PIERDE){
                velocidad =0;
                velocidadOscuridad =0;
                //Creo que esto puede sacarse del touch Down y estar junto a los otros estados.

                //Codigo Ricardo
                /*
                contadorVidas = 3;
                oscuridad.sprite.setX(positionXStart);
                estado=EstadoJuego.JUGANDO;
                 */

            }else {



                posicionDedo.x=screenX;
                posicionDedo.y=screenY;
                camara.unproject(posicionDedo);

                if (posicionDedo.x>=ANCHO/4) {
                    isMoving = true;


                } else if (gemaVerde.getRectangle(20).contains(posicionDedo.x,posicionDedo.y) && activarPowerUpGemaVerde &&  powerups && powerupActive ){
                    gemaVerde.active();


                } else if (gemaRoja.getRectangle(20).contains(posicionDedo.x,posicionDedo.y) && activarPowerUpGemaRoja && powerups && powerupActive) {
                    gemaRoja.active();


                } else if (gemaAzul.getRectangle(20).contains(posicionDedo.x,posicionDedo.y) && activarPowerUpGemaAzul &&  powerups && powerupActive) {
                    gemaAzul.active();


                } else if (botonPausa.getRectangle(10).contains(posicionDedo.x,posicionDedo.y)&& estado != EstadoJuego.INICIANDO) {
                    botonPausa.active();

                }

            }

            return true; //Porque el juego ya procesó el evento (si si hacemos algo hay que regresar TRUE)
        }

        // se ejecuta justo cuando el usuario quita el dedo de la pantalla
        @Override
        public boolean touchUp(int screenX, int screenY, int pointer, int button) {
            if(estado==EstadoJuego.PIERDE){
                velocidad =0;
                velocidadOscuridad =0;

            } else {


                posicionDedo.x = screenX;
                posicionDedo.y = screenY;
                camara.unproject(posicionDedo);

                if (posicionDedo.x >= ANCHO / 4) {
                    isMoving = true;

                } else if (gemaVerde.getRectangle(20).contains(posicionDedo.x, posicionDedo.y)&& activarPowerUpGemaVerde &&  powerups && powerupActive) {
                    //gemaVerde.active();
                    sonidoPoderActivado.play(0.2f);
                    boolPowerUpGemaVerde = true;
                    colorLumil=ColoresLumil.VERDE; //Cambia de color a Lumil
                    powerupActive = false;
                } else if (gemaRoja.getRectangle(20).contains(posicionDedo.x, posicionDedo.y) && activarPowerUpGemaRoja && powerups && powerupActive) {
                    //gemaRoja.active();
                    sonidoPoderActivado.play(0.2f);
                    boolPowerUpGemaRoja = true;
                    colorLumil=ColoresLumil.ROJO; //Cambia de color a Lumil
                    powerupActive = false;
                } else if (gemaAzul.getRectangle(20).contains(posicionDedo.x, posicionDedo.y)&& activarPowerUpGemaAzul &&  powerups && powerupActive) {
                    //gemaAzul.active();
                    sonidoPoderActivado.play(0.2f);
                    boolPowerUpGemaAzul = true;
                    colorLumil=ColoresLumil.AZUL; //Cambia de color a Lumil
                    powerupActive = false;
                } else if (botonPausa.getRectangle(10).contains(posicionDedo.x, posicionDedo.y)&& estado != EstadoJuego.INICIANDO) {

                    if (escenaPausa == null) {      // Inicialización lazy
                        escenaPausa = new EscenaPausa(vista);
                    }
                    estado = EstadoJuego.PAUSADO;
                    // CAMBIAR el procesador de entrada
                    Gdx.input.setInputProcessor(escenaPausa);

                }
            }

            isMoving = false;

            return true;

        }

        //Cuando arrastro el dedo por la pantalla
        @Override
        public boolean touchDragged(int screenX, int screenY, int pointer) {

            if(estado==EstadoJuego.PIERDE){
                velocidad =0;
                velocidadOscuridad =0;


            }else {

                posicionDedo.x = screenX;
                posicionDedo.y = screenY;
                camara.unproject(posicionDedo);

                if (posicionDedo.x >= ANCHO / 4) {
                    isMoving = true;
                } else if (gemaVerde.getRectangle(20).contains(posicionDedo.x, posicionDedo.y)&& activarPowerUpGemaVerde &&  powerups && powerupActive) {
                    gemaVerde.active();
                } else if (gemaRoja.getRectangle(20).contains(posicionDedo.x, posicionDedo.y) && activarPowerUpGemaRoja && powerups && powerupActive) {
                    gemaRoja.active();
                } else if (gemaAzul.getRectangle(20).contains(posicionDedo.x, posicionDedo.y)&& activarPowerUpGemaAzul &&  powerups && powerupActive) {
                    gemaAzul.active();
                } else if (botonPausa.getRectangle(10).contains(posicionDedo.x, posicionDedo.y)&& estado != EstadoJuego.INICIANDO) {
                    botonPausa.active();
                } else {
                    gemaVerde.inactive();
                    gemaRoja.inactive();
                    gemaAzul.inactive();
                    botonPausa.inactive();
                }
            }

            return true;
        }



        @Override
        public boolean mouseMoved(int screenX, int screenY) {
            return false;
        }

        @Override
        public boolean scrolled(float amountX, float amountY) {
            return false;
        }
    }

    // La escena que se muestra cuendo el juagador pausa le juego
    private class EscenaPausa extends Stage
    {

        private Texture texturaFondo;
        private Texture texturaON;
        private Texture texturaOFF;
        public EscenaPausa(Viewport vista) {
            super(vista);       // Pasa la vista al constructor de Stage
            // Imagen de la ventana de pausa
            switch (seccion){
                case VERDE:
                    texturaFondo = manager.get("Pausa/pausaBlanca.png");
                    break;
                case ROJO:
                    texturaFondo = manager.get("Pausa/pausaVerde.png");
                    break;
                case AZUL:
                    texturaFondo = manager.get("Pausa/pausaRoja.png");
                    break;
                case BLANCO:
                    texturaFondo = manager.get("Pausa/pausaAzul.png");
                    break;
            }
            //texturaFondo = new Texture("personajes/fondoPausa.png");
            final com.badlogic.gdx.scenes.scene2d.ui.Image imgFondo = new Image(texturaFondo);
            imgFondo.setPosition(ANCHO/2, ALTO/2, Align.center);
            addActor(imgFondo);

            imgFondo.addAction(Actions.sequence(Actions.alpha(0), Actions.fadeIn(0.6f)));


            //Boton Quit
            final Button btnQuit = crearBoton("Pausa/btnQuit_OFF.png","Pausa/btnQuit_ON.png");
            btnQuit.setPosition(ANCHO-350, 320, Align.center);
            addActor(btnQuit);
            btnQuit.addAction(Actions.sequence(Actions.alpha(0), Actions.fadeIn(0.6f)));

            //Boton Resume
            final Button btn = crearBoton("Pausa/btnResume_OFF.png","Pausa/btnResume_ON.png");
            btn.setPosition(350, 320, Align.center);
            addActor(btn);
            btn.addAction(Actions.sequence(Actions.alpha(0), Actions.fadeIn(0.6f)));
            // Agregar el listener del botón
            btn.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    super.clicked(event, x, y);
                    // QUITAR LA PAUSA
                    // No es necesario
                        imgFondo.addAction(Actions.fadeOut(0.6f));
                        btnQuit.addAction(Actions.fadeOut(0.6f));
                        //btn.addAction(Actions.fadeOut(1f));
                        btn.addAction(Actions.sequence(Actions.fadeOut(0.6f),Actions.run(new Runnable() {
                                @Override
                                public void run() {
                                    botonPausa.inactive();
                                    estado = EstadoJuego.JUGANDO;
                                    Gdx.input.setInputProcessor(procesadorEntrada);
                                    //escenaPausa.dispose();
                                }
                            })
                    ));



                }
            } );


            btnQuit.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    super.clicked(event, x, y);
                    // QUITAR LA PAUSA
                    // No es necesario
                    imgFondo.addAction(Actions.fadeOut(0.2f));
                    btn.addAction(Actions.fadeOut(0.2f));
                    btnQuit.addAction(Actions.sequence(Actions.fadeOut(0.2f),Actions.run(new Runnable() {
                                @Override
                                public void run() {
                                    isQuitPressed = true;
                                    botonPausa.inactive();
                                    //estado = EstadoJuego.JUGANDO;
                                    //Gdx.input.setInputProcessor(procesadorEntrada);
                                    //escenaPausa.dispose();
                                }
                            })
                    ));
                }
            } );


            //Control de Musica
            texturaON = new Texture("Botones/VolumeON.png");
            texturaOFF = new Texture("Botones/VolumeOFF.png");
            TextureRegionDrawable trdON = new TextureRegionDrawable(texturaON);
            TextureRegionDrawable trdOFF =  new TextureRegionDrawable(texturaOFF);

            Button.ButtonStyle estiloON = new Button.ButtonStyle(trdON, trdOFF, trdOFF);
            Button.ButtonStyle estiloOFF = new Button.ButtonStyle(trdOFF, trdON, trdON);
            Preferences prefs = Gdx.app .getPreferences("MusicPrefernce");
            musicaON = prefs.getBoolean("musicON", true);

            Button btnVolumen = crearBoton("Botones/VolumeON.png", "Botones/VolumeOFF.png");
            prefs = Gdx.app.getPreferences("MusicPrefernce");
            musicaON = prefs.getBoolean("musicON", true);
            if (musicaON == false){
                btnVolumen.setStyle(estiloOFF);
                // System.out.println("Estilo OFF");
            }else {
                btnVolumen.setStyle(estiloON);
            }
            btnVolumen.setPosition(1190, 650, Align.center);
            addActor(btnVolumen);
            btnVolumen.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    //juego.setScreen(new PantallaCargando(juego,Pantallasenum.PANTALLAAYUDA));
                    // isTransicionFadingOut = true;
                    // pantallaSiguienteMenu = PantallaSiguienteMenu.HELP;
                    Preferences prefs = Gdx.app.getPreferences("MusicPrefernce");
                    musicaON = prefs.getBoolean("musicON", true);
                    if (musicaON){ // Si la musica esta prendida
                        prefs.putBoolean("musicON", false);
                        prefs.flush();
                        sonidoJuego.stop();
                        //musicaFondo.play();
                        //juego.stopMusica();
                        // musicaON = false;
                    }else { // La musica esta apagada
                        prefs.putBoolean("musicON", true);
                        prefs.flush();
                        idSonidoJuego = sonidoJuego.play();
                        sonidoJuego.setPitch(idSonidoJuego,valorPitch);
                        sonidoJuego.setVolume(idSonidoJuego,volumenSonidoPrincipal);//Asignar el nuevo valor de volumen.
                        sonidoJuego.setLooping(idSonidoJuego,true);


                        // musicaFondo.play();
                        //juego.playMusica();
                        // musicaON = true;

                    }
                    // Guardar en las preferencias la variable musicaON
                }
                //musicaON
            });
            //----------------------------------------------------



        }
    }

}


