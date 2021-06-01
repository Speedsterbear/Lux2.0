package mx.xul.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;

// Pantalla que implementa el menu del juego
// Autor: Andrea, Carlos y Eduardo

public class PantallaMenu extends Pantalla {
    private Lux juego;
    private Texture texturafondo;
    private  Texture texturaON;
    private  Texture texturaOFF;

    private Texture texturaFondoMenu;
    private Texture texturaFondoMenuFrente;
    private Texture texturaLumil;
    private Texture texturaBrillo;
    private Objeto lumil;
    private float angulo = 0; //acumulador para mover con sinDeg
    private Array<BrilloLumil> arrEstrellas;
    private Array<BrilloLumil> arrEstrellasChicas;


    private Stage escenaMenu;

    //Para el Fade
    private Transicion fadeNegro;
    private float tiempoFadeIn = 0; //Acumulador para controlar el fade in
    private boolean isTransicionFadingOut = false; //Al volverse true, se activa el fade out

    //Para pasar a la siguiente pantalla
    private PantallaSiguienteMenu pantallaSiguienteMenu = PantallaSiguienteMenu.PLAY;

   private boolean musicaON = true;

    public PantallaMenu(Lux juego) {
        this.juego=juego;
    }

    @Override
    public void show() {
        crearMenu();
        crearEstrellas();
        crearLumil();

        //Fade
        fadeNegro = new Transicion(0,0,0,1,ALTO,ANCHO);
        Gdx.input.setCatchKey(Input.Keys.BACK,false); // Si regresa al sistema operativo
    }

    private void crearLumil() {
        texturaLumil = new Texture("Menu/menuLumil.png");
        lumil = new Objeto(texturaLumil,ANCHO/4,ALTO/2);
        lumil.sprite.setScale(0.8f);
    }

    private void crearEstrellas() {
        arrEstrellas = new Array<>();
        arrEstrellasChicas = new Array<>();
        texturaBrillo = new Texture("Utileria/brilloLumil.png");
        for (int i = 0; i <= 35; i++) {
            BrilloLumil estrella = new BrilloLumil(texturaBrillo, MathUtils.random(0, ANCHO), MathUtils.random(ALTO/3, ALTO));
            arrEstrellas.add(estrella);
        }

        for (int i = 0; i <= 35; i++) {
            BrilloLumil estrella = new BrilloLumil(texturaBrillo, MathUtils.random(0, ANCHO), MathUtils.random(ALTO/3, ALTO));
            arrEstrellasChicas.add(estrella);
        }
    }

    private void crearMenu() {
        //Fondo
        texturafondo= new Texture("Menu/fondo.jpg");
        texturaFondoMenu = new Texture("Menu/fondoMenu.png");
        texturaFondoMenuFrente = new Texture("Menu/fondoMenuFrente.png");

        // Escena
        escenaMenu=new Stage(vista);

        //Musica
       // Leer de las preferencias si la musica quedo prendida o apagada

        Preferences prefs = Gdx.app.getPreferences("MusicPrefernce");
        musicaON = prefs.getBoolean("musicON", true);
        // Si el valo leído es musica prendida entonces se ejecuta...
        if (musicaON == true){
            juego.playMusica();
        }




        // Actores (botones)
        Button btnJuego = crearBoton("Menu/buttonjuego.png", "Menu/clickjuego.png");
        btnJuego.setPosition(2*ANCHO/3,1.5f*ALTO/2, Align.center);
        btnJuego.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Cambiar de pantalla a Juego
                //juego.setScreen(new PantallaJuego(juego));
                /*
                musicaPantallasSecundarias.stop();
                musicaPantallasSecundariasIntro.stop();
                 */
                //juego.stopMusica();
                //juego.setScreen(new PantallaCargando(juego,Pantallasenum.JUEGOGS));
                isTransicionFadingOut = true;
                pantallaSiguienteMenu = PantallaSiguienteMenu.PLAY;
            }
        });


        Button btnNosotros = crearBoton("Menu/buttonnosotros.png","Menu/clicknosotros.png");
        btnNosotros.setPosition(2*ANCHO/3,1.2f*ALTO/2.5f, Align.center);
        // Registrar el evento click para el boton
        btnNosotros.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                isTransicionFadingOut = true;
                pantallaSiguienteMenu = PantallaSiguienteMenu.ABOUT_US;
            }
        });

        Button btnAyuda= crearBoton("Menu/buttonayuda.png", "Menu/clickayuda.png");
        btnAyuda.setPosition(2*ANCHO/3,0.8f*ALTO/4, Align.center);
        btnAyuda.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                isTransicionFadingOut = true;
                pantallaSiguienteMenu = PantallaSiguienteMenu.HELP;
            }
        });


        texturaON = new Texture("Botones/VolumeONW.png");
        texturaOFF = new Texture("Botones/VolumeOFFW.png");
        TextureRegionDrawable trdON = new TextureRegionDrawable(texturaON);
        TextureRegionDrawable trdOFF =  new TextureRegionDrawable(texturaOFF);

        Button.ButtonStyle estiloON = new Button.ButtonStyle(trdON, trdOFF, trdOFF);
        Button.ButtonStyle estiloOFF = new Button.ButtonStyle(trdOFF, trdON, trdON);

        Button btnVolumen = crearBoton("Botones/VolumeONW.png", "Botones/VolumeOFFW.png");
        prefs = Gdx.app.getPreferences("MusicPrefernce");
        musicaON = prefs.getBoolean("musicON", true);
        if (musicaON == false){
            btnVolumen.setStyle(estiloOFF);
        }else {
            btnVolumen.setStyle(estiloON);
        }
        btnVolumen.setPosition(9.7f*ANCHO/10.2f, ALTO/1.1f, Align.center);
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
                    juego.stopMusica();
                    // musicaON = false;
                }else { // La musica esta apagada
                    prefs.putBoolean("musicON", true);
                    prefs.flush();
                    // musicaON = true;
                    juego.playMusica();
                }
                // Guardar en las preferencias la variable musicON
            }
            //musicaON
        });

        // Agregar a la escena el boton
        escenaMenu.addActor(btnJuego);
        escenaMenu.addActor(btnNosotros);
        escenaMenu.addActor(btnAyuda);
        escenaMenu.addActor(btnVolumen);
        // escenaMenu.addActor(btnVolumenOFF);

        // Escena se encarga de atender los eventos de entrada
        Gdx.input.setInputProcessor(escenaMenu);
    }


    private Button crearBoton(String archivo, String archivoclick) {
        Texture texturaBoton=new Texture(archivo);
        TextureRegionDrawable trdBtnRunner=new TextureRegionDrawable(texturaBoton);

        Texture texturaClick=new Texture(archivoclick);
        TextureRegionDrawable trdBtnClick=new TextureRegionDrawable(texturaClick);

        return new Button(trdBtnRunner,trdBtnClick);
    }

    // Se ejecuta automaticamente
    // delta es el tiempo que ha transcurrido el frame
    @Override
    public void render(float delta) {

        actualizarEstrellas(delta);
        actualizarLumil();

        //Fade in
        if (tiempoFadeIn<=0.7f){
            tiempoFadeIn+=delta;
            fadeNegro.fadeIn(delta,0.7f);
        }

        borrarPantalla(0,50,125);

        batch.setProjectionMatrix(camara.combined);

        batch.begin();
        //batch.draw(texturafondo,0,0);
        batch.draw(texturaFondoMenu,0,0);
        if (arrEstrellas!=null){
            for(BrilloLumil estrella: arrEstrellas){
                estrella.render(batch);
            }
        }

        if (arrEstrellasChicas!=null){
            for(BrilloLumil estrella: arrEstrellasChicas){
                estrella.render(batch);
            }
        }

        batch.draw(texturaFondoMenuFrente,0,0);
        lumil.render(batch);
        batch.end();

        // Escena despues del fondo
        escenaMenu.draw();

        if (isTransicionFadingOut){
            fadeNegro.fadeOut(delta,0.5f);
            if (pantallaSiguienteMenu==PantallaSiguienteMenu.PLAY){
                juego.fadeOutMusica();
            }
        }

        if (fadeNegro.isFadeInFinished==false && musicaON){
            juego.playMusica();
        }


        fadeNegro.render(camara);
        if (fadeNegro.isFadeOutFinished){
            switch (pantallaSiguienteMenu){
                case PLAY:
                    juego.stopMusica();
                    juego.setScreen(new PantallaCargando(juego,Pantallasenum.JUEGOGS));
                    break;
                case ABOUT_US:
                    juego.setScreen(new PantallaCargando(juego,Pantallasenum.PANTALLANOSOTROS));
                    break;
                case HELP:
                    juego.setScreen(new PantallaCargando(juego,Pantallasenum.PANTALLAAYUDA));
                    break;
            }
        }

    }

    private void actualizarLumil() {
        angulo ++;
        lumil.sprite.setY(((ALTO/2)-lumil.sprite.getHeight()/2)+(MathUtils.sinDeg(angulo)*10));
    }

    private void actualizarEstrellas(float delta) {
        if(arrEstrellas!=null){
            for(BrilloLumil estrella: arrEstrellas){
                estrella.actualizar(1,1,1,0.02f+MathUtils.random(0,.04f));
            }

        }

        if(arrEstrellasChicas!=null){
            for(BrilloLumil estrella: arrEstrellasChicas){
                estrella.actualizar(1,1,1,0.02f+MathUtils.random(0,.015f));
            }

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
    texturafondo.dispose();
    texturaOFF.dispose();
    texturaON.dispose();
    texturaFondoMenu.dispose();
    texturaFondoMenuFrente.dispose();
    texturaLumil.dispose();
    texturaBrillo.dispose();
    arrEstrellas.clear();
    arrEstrellasChicas.clear();


    }

    private enum PantallaSiguienteMenu
    {
        PLAY,
        ABOUT_US,
        HELP
    }

}


