package mx.xul.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;


/**
 * Pantalla intermedia entre el menú y el juego
 *
 * @author Andrea Espinosa Azuela
 */
public class PantallaCargando extends Pantalla {
    private Lux juego;

    // La cámara y vista principal
    private OrthographicCamera camara;
    private Viewport vista;
    private SpriteBatch batch;

    // Imagen cargando
    //private Texture texturaCargando;
    private Texture texturaCarga;
    //private Sprite spriteCargando;
    private BarraAvance barraAvance;
    private float margen=80; // Margen de las barras

    private Pantallasenum siguientePantalla;
    private int avance; // % de carga

    // AssetManager
    private AssetManager manager;

    //private AssetManager assetManager;  // Asset manager principal

    public PantallaCargando(Lux juego, Pantallasenum siguientePantalla) {
        this.juego = juego;
        manager = juego.getAssetManager();
        this.siguientePantalla = siguientePantalla;
    }

    @Override
    public void show() {
        // Crea la cámara con las dimensiones del mundo
        camara = new OrthographicCamera(ANCHO, ALTO);
        // En el centro de la pantalla. (x,y) de la cámara en el centro geométrico
        camara.position.set(ANCHO / 2, ALTO / 2, 0);
        camara.update();
        // La vista que escala los elementos gráficos
        vista = new StretchViewport(ANCHO, ALTO, camara);
        // El objeto que administra los trazos gráficos
        batch = new SpriteBatch();
        crearBarra();
        cargarRecursosSigPantalla();
    }

    private void crearBarra() {
        barraAvance = new BarraAvance(1,1,1,0.5f,margen,ALTO/2,15,ANCHO-(2*margen),120);
        texturaCarga = new Texture("PantallaCargando/SiluetaBarra.png");
    }

    private void cargarRecursosHistoria() {
        manager.load("PantallaHistoria/mensaje_inicial.png", Texture.class);
        manager.load("PantallaHistoria/Humo.png", Texture.class);
        manager.load("PantallaHistoria/fondo_humo.jpg", Texture.class);
        manager.load("PantallaHistoria/Letras_1.png", Texture.class);
        manager.load("PantallaHistoria/Letras_2.png", Texture.class);
        manager.load("PantallaHistoria/Letras_3.png", Texture.class);
        manager.load("PantallaHistoria/Letras_4.png", Texture.class);
        manager.load("PantallaHistoria/Letras_5.png", Texture.class);
        manager.load("PantallaHistoria/Letras_6.png", Texture.class);
        manager.load("PantallaHistoria/Vitral_No1.png", Texture.class);
        manager.load("PantallaHistoria/Vitral_No2.png", Texture.class);
        manager.load("PantallaHistoria/Vitral_No3.png", Texture.class);
        manager.load("PantallaHistoria/Vitral_No4.png", Texture.class);
        manager.load("PantallaHistoria/Vitral_No5.png", Texture.class);
        manager.load("PantallaHistoria/Vitral_No6.png", Texture.class);
    }

    private void cargarRecursosSigPantalla() {
        manager = juego.getAssetManager();
        avance = 0;
        switch (siguientePantalla) {
            case JUEGOGS:
                cargarRecursosJuego();
                break;
            case PANTALLAAYUDA:
                cargarRecursosAyuda();
                break;
            case PANTALLANOSOTROS:
                cargarRecursosNosotros();
                break;
            case PANTALLAHISTORIA:
                cargarRecursosHistoria();
                break;
        }
    }

    private void cargarRecursosNosotros() {
        manager.load("Botones/btnTransparente.png", Texture.class);
        manager.load("Botones/btnTransparenteVertical.png", Texture.class);
        manager.load("Utileria/cristalRomboBlanco.png", Texture.class);
        manager.load("Utileria/brilloLumil.png", Texture.class);
        manager.load("Nosotros/mailst.png",Texture.class);
        manager.load("Nosotros/tecst.png",Texture.class);
       //manager.load("Nosotros/Androidst.png", Texture.class);
        manager.load("Nosotros/fondoAbout.jpg", Texture.class);
        manager.load("Menu/buttonback.png", Texture.class);
        manager.load("Menu/clickback.png", Texture.class);
        manager.load("Nosotros/btnFlechaDer_OFF.png", Texture.class);
        manager.load("Nosotros/btnFlechaDer_ON.png", Texture.class);
        manager.load("Nosotros/btnFlechaIzq_OFF.png", Texture.class);
        manager.load("Nosotros/btnFlechaIzq_ON.png", Texture.class);
        manager.load("Nosotros/btnFlechaArriba_OFF.png", Texture.class);
        manager.load("Nosotros/btnFlechaArriba_ON.png", Texture.class);
        manager.load("Nosotros/flechaColorSolidoArriba.png", Texture.class);
        manager.load("Nosotros/flechaColorSolidoDer.png", Texture.class);
        manager.load("Nosotros/flechaColorSolidoIzq.png", Texture.class);
        manager.load("Nosotros/Cristales-W.png", Texture.class);
        manager.load("Nosotros/Cristales-G.png", Texture.class);
        manager.load("Nosotros/Cristales-R.png", Texture.class);
        manager.load("Nosotros/Cristales-B.png", Texture.class);
        manager.load("Nosotros/Cristales-Y.png", Texture.class);
        manager.load("Nosotros/Cristales-C.png", Texture.class);
        manager.load("Nosotros/andrea.png", Texture.class);
        manager.load("Nosotros/carlos.png", Texture.class);
        manager.load("Nosotros/david.png", Texture.class);
        manager.load("Nosotros/eduardo.png", Texture.class);
        manager.load("Nosotros/ricardo.png", Texture.class);
        manager.load("Nosotros/CasoInicial.png", Texture.class);
    }


    private void cargarRecursosAyuda() {
        manager.load("PantallaAyuda/fondoAyuda.jpg", Texture.class);    // Cargar imagen ded fondo
        manager.load("PantallaAyuda/ayudaHijosOscuridad.png", Texture.class);
        manager.load("PantallaAyuda/ayudaHistoria.png", Texture.class);
        manager.load("PantallaAyuda/ayudaLineas.png", Texture.class);
        manager.load("PantallaAyuda/ayudaLumil.png", Texture.class);
        manager.load("PantallaAyuda/ayudaOscuridad.png", Texture.class);
        manager.load("PantallaAyuda/ayudaPrimarios.png", Texture.class);
        manager.load("PantallaAyuda/btnAyudaHistoriaOFF.png", Texture.class);
        manager.load("PantallaAyuda/btnAyudaHistoriaON.png", Texture.class);
        manager.load("PantallaAyuda/btnAyudaHijosOFF.png", Texture.class);
        manager.load("PantallaAyuda/btnAyudaHijosON.png", Texture.class);
        manager.load("PantallaAyuda/btnAyudaLumilOFF.png", Texture.class);
        manager.load("PantallaAyuda/btnAyudaLumilON.png", Texture.class);
        manager.load("PantallaAyuda/btnAyudaOscuridadOFF.png", Texture.class);
        manager.load("PantallaAyuda/btnAyudaOscuridadON.png", Texture.class);
        manager.load("PantallaAyuda/btnAyudaPrimariosOFF.png", Texture.class);
        manager.load("PantallaAyuda/btnAyudaPrimariosON.png", Texture.class);
        manager.load("Menu/buttonback.png", Texture.class);
        manager.load("Menu/clickback.png", Texture.class);
        manager.load("Utileria/brilloLumil.png",Texture.class);

    }

    // Carga los recursos a través del administrador de assets (siguiente pantalla)
    private void cargarRecursosJuego() {
        manager.load("Escenarios/efectoAzulAtras.png", Texture.class);
        manager.load("Escenarios/efectoAzulFrente.png", Texture.class);
        manager.load("Escenarios/efectoRojoAtras.png", Texture.class);
        manager.load("Escenarios/efectoRojoFrente.png", Texture.class);
        manager.load("Pausa/pausaBlanca.png", Texture.class);
        manager.load("Pausa/pausaVerde.png", Texture.class);
        manager.load("Pausa/pausaRoja.png", Texture.class);
        manager.load("Pausa/pausaAzul.png", Texture.class);
        manager.load("Pausa/btnResume_OFF.png", Texture.class);
        manager.load("Pausa/btnResume_ON.png", Texture.class);
        manager.load("Pausa/btnQuit_OFF.png", Texture.class);
        manager.load("Pausa/btnQuit_ON.png", Texture.class);
        manager.load("BotonesGemas/gemaVerde_ON.png", Texture.class);
        manager.load("BotonesGemas/gemaVerde_OFF.png", Texture.class);
        manager.load("BotonesGemas/gemaRoja_ON.png", Texture.class);
        manager.load("BotonesGemas/gemaRoja_OFF.png", Texture.class);
        manager.load("BotonesGemas/gemaAzul_ON.png", Texture.class);
        manager.load("BotonesGemas/gemaAzul_OFF.png", Texture.class);
        manager.load("Botones/pausa_ON.png", Texture.class);
        manager.load("Botones/pausa_OFF.png", Texture.class);
        manager.load("Utileria/atrasBarraAvance.png", Texture.class);
        manager.load("Utileria/brilloLumil.png", Texture.class);
        manager.load("Utileria/vida.png", Texture.class);
        manager.load("Personajes/LumilJuego.png", Texture.class);
        manager.load("Personajes/lumilG.png", Texture.class);
        manager.load("Personajes/lumilR.png", Texture.class);
        manager.load("Personajes/lumilB.png", Texture.class);
        manager.load("Personajes/oscuridad.png", Texture.class);
        manager.load("Personajes/HijoOsc_sprite.png", Texture.class);
        manager.load("Personajes/bloque.png", Texture.class);
        manager.load("Personajes/Esgrun.png", Texture.class);
        manager.load("Personajes/Rojel.png", Texture.class);
        manager.load("Personajes/Shiblu.png", Texture.class);
        manager.load("Personajes/luzPersonaje.png",Texture.class);
        manager.load("Botones/VolumeON.png",Texture.class);
        manager.load("Botones/VolumeOFF.png",Texture.class);
        manager.load("Sonidos/sonidoComeOscuridad.wav", Sound.class);
        manager.load("Sonidos/sonidoPoderActivado.wav", Sound.class);
        manager.load("Sonidos/sonidoquitavidas.wav", Sound.class);
        manager.load("Sonidos/sonidoTocaLuzBlanca.wav", Sound.class);
        manager.load("Sonidos/sonidoTocaPrimario.wav", Sound.class);
        //manager.load("Sonidos/musicaJugar.ogg",Sound.class);
        manager.load("Sonidos/JugarLoop.mp3",Sound.class);
        //manager.load("Sonidos/musicaMenuLoop.mp3",Sound.class);

        //Escenario
        manager.load("Escenarios/capa0.png",Texture.class);
        manager.load("Escenarios/capa1.png",Texture.class);
        manager.load("Escenarios/capa2.png",Texture.class);
        manager.load("Escenarios/capa3Completa.png",Texture.class);
        manager.load("Escenarios/capa4Completa.png",Texture.class);
        manager.load("Escenarios/capaEstatica.png",Texture.class);
        manager.load("Escenarios/capaFrente.png",Texture.class);
        manager.load("Escenarios/capaFrenteVerde.png",Texture.class);
        manager.load("Escenarios/nubes.png",Texture.class);
        manager.load("Escenarios/Luna.png",Texture.class);


    }

    @Override
    public void render(float delta) {
        // Actualizar carga
        actualizar();


        // Dibujar
        borrarPantalla();
        //spriteCargando.setRotation(spriteCargando.getRotation() + 15);

        //Dibujar Barra
        barraAvance.renderAvanceCarga(avance*1.2f,camara);

        batch.setProjectionMatrix(camara.combined);

        // Entre begin-end dibujamos nuestros objetos en pantalla
        batch.begin();
        batch.draw(texturaCarga,(margen+(avance*1.2f*(ANCHO-(margen*2))/120))-(texturaCarga.getWidth()/2)-20,(ALTO/2)-(texturaCarga.getHeight()/2));
        //spriteCargando.draw(batch);
        batch.end();

        actualizarCargaRecursos();
    }

    private void actualizarCargaRecursos() {
        if (manager.update()) { // Terminó?
            switch (siguientePantalla) {
                case JUEGOGS:
                    juego.setScreen(new JuegoGS(juego));   // 100% de carga
                    break;
                case PANTALLAAYUDA:
                    juego.setScreen(new PantallaAyuda(juego));   // 100% de carga
                    break;
                case PANTALLAGANA:
                    juego.setScreen(new PantallaGana(juego));
                    break;
                case PANTALLANOSOTROS:
                    juego.setScreen(new PantallaNosotros(juego));
                    break;
                case PANTALLAHISTORIA:
                    juego.setScreen(new PantallaHistoria(juego));
                    break;
            }
        }
        avance = (int) (manager.getProgress() * 100);
    }


    private void actualizar() {

        if (manager.update()==false) {
            // Aún no termina la carga de assets, leer el avance
            //float avance = manager.getProgress()*100;
            //Gdx.app.log("Cargando",avance+"%");
        }

        /*
        if (manager.update()) {
            // Terminó la carga, cambiar de pantalla
            //juego.setScreen(new JuegoGS(juego));
        } else {
            // Aún no termina la carga de assets, leer el avance
            float avance = manager.getProgress()*100;
            Gdx.app.log("Cargando",avance+"%");
        }
        */
    }


    @Override
    public void resize(int width, int height) {
        vista.update(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        texturaCarga.dispose();
        // Los assets de PantallaJuego se liberan en el método dispose de PantallaJuego
    }
}