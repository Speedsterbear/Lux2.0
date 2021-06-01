package mx.xul.game;
// Pantalla que implmenta el menu de ayuda
// Autor: Eduardo Alejandro García y Carlos Arroyo
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;

public class PantallaAyuda extends Pantalla {
    private Lux juego;
    private Texture texturafondo;

    // private Sprite help;
    private Stage escenaMenu;
    private AssetManager manager;

    //Elementos visuales
    private Texture texturaLumil;
    private Objeto visualLumil;
    private boolean isLumilFadingIn = false;
    private boolean isLumilFadingOut = false;

    private Texture texturaOscuridad;
    private Objeto visualOscuridad;
    private boolean isOscuridadFadingIn = false;
    private boolean isOscuridadFadingOut = false;

    private Texture texturaHijos;
    private Objeto visualHijos;
    private boolean isHijosFadingIn = false;
    private boolean isHijosFadingOut = false;

    private Texture texturaPrimarios;
    private Objeto visualPrimarios;
    private boolean isPrimariosFadingIn = false;
    private boolean isPrimariosFadingOut = false;

    private Texture texturaHistoria;
    private Objeto visualHistoria;
    private boolean isHistoriaFadingIn = false;
    private boolean isHistoriaFadingOut = false;

    private Texture texturaLineas;

    //Control del Fade
    private final float DURACION_FADE = 0.2f;

    //Acomodo de elementos
    private final float X_CENTROS_BOTON = ANCHO/4; //X de todos los botones d ela pantalla
    private final float SEPARACION_VERTICAL_BOTONES = 30;// separación vertical entre los botones de la pantalla.

    //Estrellas
    private Texture texturaEstrella;
    private Array<BrilloLumil> arrEstrellas;
    //Las esttellas estan numeradas porque todas reoresentan lo mismo y solo es para separarlas
    private BrilloLumil estrellaUno;
    private BrilloLumil estrellaDos;
    private BrilloLumil estrellaTres;
    private BrilloLumil estrellaCuatro;
    private BrilloLumil estrellaCinco;

    private final float VALOR_MINIMO_ESCALA = 0.04f;
    private final float VALOR_MAXIMO_ESCALA = 0.13f;

    private final float ESCALA_ESTRELLA_UNO = MathUtils.random(VALOR_MINIMO_ESCALA,VALOR_MAXIMO_ESCALA);
    private final float ESCALA_ESTRELLA_DOS = MathUtils.random(VALOR_MINIMO_ESCALA,VALOR_MAXIMO_ESCALA);
    private final float ESCALA_ESTRELLA_TRES = MathUtils.random(VALOR_MINIMO_ESCALA,VALOR_MAXIMO_ESCALA);
    private final float ESCALA_ESTRELLA_CUATRO = MathUtils.random(VALOR_MINIMO_ESCALA,VALOR_MAXIMO_ESCALA);
    private final float ESCALA_ESTRELLA_CINCO = MathUtils.random(VALOR_MINIMO_ESCALA,VALOR_MAXIMO_ESCALA);


    public PantallaAyuda(Lux juego) {
        this.juego=juego;
        manager = juego.getAssetManager();
    }


    private Button crearBoton(String archivo, String archivoclick) {
        // Texture texturaBoton = new Texture("Personajes/Lumil.png");
        Texture texturaBoton=manager.get(archivo);
        TextureRegionDrawable trdBtnRunner=new TextureRegionDrawable(texturaBoton);

        Texture texturaClick= manager.get(archivoclick);
        TextureRegionDrawable trdBtnClick=new TextureRegionDrawable(texturaClick);

        return new Button(trdBtnRunner,trdBtnClick);
    }

    @Override
    public void show() {
        crearAyuda();
        crearVisuales();
        crearEstrellas();

        // Bloquear la tecla de back
        Gdx.input.setCatchKey(Input.Keys.BACK,true);
    }

    private void crearEstrellas() {
        arrEstrellas = new Array<>();
        texturaEstrella =manager.get("Utileria/brilloLumil.png");
        for (int  i=0; i<=35; i++){
            System.out.println("crear Estrella");
            BrilloLumil estrella = new BrilloLumil(texturaEstrella, MathUtils.random(0,ANCHO),MathUtils.random(0,ALTO));
            arrEstrellas.add(estrella);
        }

        estrellaUno = new BrilloLumil(texturaEstrella, MathUtils.random(0,ANCHO),MathUtils.random(0,ALTO));
        estrellaDos = new BrilloLumil(texturaEstrella, MathUtils.random(0,ANCHO),MathUtils.random(0,ALTO));
        estrellaTres = new BrilloLumil(texturaEstrella, MathUtils.random(0,ANCHO),MathUtils.random(0,ALTO));
        estrellaCuatro = new BrilloLumil(texturaEstrella, MathUtils.random(0,ANCHO),MathUtils.random(0,ALTO));
        estrellaCinco = new BrilloLumil(texturaEstrella, MathUtils.random(0,ANCHO),MathUtils.random(0,ALTO));
    }

    private void crearVisuales() {
        //Normal
        texturaLineas = manager.get("PantallaAyuda/ayudaLineas.png");

        //Lumil
        texturaLumil = manager.get("PantallaAyuda/ayudaLumil.png");
        visualLumil = new Objeto(texturaLumil,ANCHO-(texturaLineas.getWidth()/2),ALTO/2);
        visualLumil.sprite.setAlpha(0);

        //Oscuridad
        texturaOscuridad = manager.get("PantallaAyuda/ayudaOscuridad.png");
        visualOscuridad = new Objeto(texturaOscuridad,ANCHO-(texturaLineas.getWidth()/2),ALTO/2);
        visualOscuridad.sprite.setAlpha(0);

        //Hijos Oscuridad
        texturaHijos = manager.get("PantallaAyuda/ayudaHijosOscuridad.png");
        visualHijos = new Objeto(texturaHijos,ANCHO-(texturaLineas.getWidth()/2),ALTO/2);
        visualHijos.sprite.setAlpha(0);

        //Primarios
        texturaPrimarios = manager.get("PantallaAyuda/ayudaPrimarios.png");
        visualPrimarios = new Objeto(texturaPrimarios,ANCHO-(texturaLineas.getWidth()/2),ALTO/2);
        visualPrimarios.sprite.setAlpha(0);

        //Historia
        texturaHistoria = manager.get("PantallaAyuda/ayudaHistoria.png");
        visualHistoria = new Objeto(texturaHistoria,ANCHO-(texturaLineas.getWidth()/2),ALTO/2);
        visualHistoria.sprite.setAlpha(0);

    }

    private void crearAyuda(){
        texturafondo= manager.get("PantallaAyuda/fondoAyuda.jpg");
        escenaMenu = new Stage(vista);

        //HISTORIA ------------------------------
        Button btnHistoria = crearBoton("PantallaAyuda/btnAyudaHistoriaOFF.png","PantallaAyuda/btnAyudaHistoriaON.png");
        btnHistoria.setPosition(X_CENTROS_BOTON, (2*SEPARACION_VERTICAL_BOTONES) + (btnHistoria.getHeight()/2), Align.center);
        escenaMenu.addActor(btnHistoria);

        btnHistoria.addListener(new ActorGestureListener() {
            @Override
            public void touchDown(InputEvent event, float x, float y, int pointer, int button){
                isHistoriaFadingIn = true;
                isHistoriaFadingOut = false;
            }
        });

        btnHistoria.addListener(new ActorGestureListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button){
                isHistoriaFadingIn = false;
                isHistoriaFadingOut = true;
            }
        });

        btnHistoria.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                juego.setScreen(new PantallaCargando(juego, Pantallasenum.PANTALLAHISTORIA));
            }
        });

        //HIJOS ------------------------------
        Button btnHijos = crearBoton("PantallaAyuda/btnAyudaHijosOFF.png","PantallaAyuda/btnAyudaHijosON.png");
        btnHijos.setPosition(X_CENTROS_BOTON, btnHistoria.getY()+btnHistoria.getHeight()+(btnHijos.getHeight()/2f)+SEPARACION_VERTICAL_BOTONES, Align.center);
        escenaMenu.addActor(btnHijos);

        btnHijos.addListener(new ActorGestureListener() {
            @Override
            public void touchDown(InputEvent event, float x, float y, int pointer, int button){
                isHijosFadingIn = true;
                isHijosFadingOut = false;
            }
        });

        btnHijos.addListener(new ActorGestureListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button){
                isHijosFadingIn = false;
                isHijosFadingOut = true;
            }
        });

        btnHijos.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                juego.setScreen(new PantallaAyudaHabitantes(juego));
            }
        });

        //OSCURIDAD ------------------------
        Button btnOscuridad = crearBoton("PantallaAyuda/btnAyudaOscuridadOFF.png","PantallaAyuda/btnAyudaOscuridadON.png");
        btnOscuridad.setPosition(X_CENTROS_BOTON, btnHijos.getY()+btnHijos.getHeight()+(btnOscuridad.getHeight()/2f)+SEPARACION_VERTICAL_BOTONES, Align.center);
        escenaMenu.addActor(btnOscuridad);

        btnOscuridad.addListener(new ActorGestureListener() {
            @Override
            public void touchDown(InputEvent event, float x, float y, int pointer, int button){
                isOscuridadFadingIn = true;
                isOscuridadFadingOut = false;
            }
        });

        btnOscuridad.addListener(new ActorGestureListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button){
                isOscuridadFadingIn = false;
                isOscuridadFadingOut = true;
            }
        });

        btnOscuridad.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                juego.setScreen(new PantallaAyudaOscuridad(juego));
            }
        });


        //PRIMARIOS ------------------------------
        Button btnPrimarios = crearBoton("PantallaAyuda/btnAyudaPrimariosOFF.png","PantallaAyuda/btnAyudaPrimariosON.png");
        btnPrimarios.setPosition(X_CENTROS_BOTON, btnOscuridad.getY()+btnOscuridad.getHeight()+(btnPrimarios.getHeight()/2f)+SEPARACION_VERTICAL_BOTONES, Align.center);
        escenaMenu.addActor(btnPrimarios);

        btnPrimarios.addListener(new ActorGestureListener() {
            @Override
            public void touchDown(InputEvent event, float x, float y, int pointer, int button){
                isPrimariosFadingIn = true;
                isPrimariosFadingOut = false;
            }
        });

        btnPrimarios.addListener(new ActorGestureListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button){
                isPrimariosFadingIn = false;
                isPrimariosFadingOut = true;
            }
        });

        btnPrimarios.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                juego.setScreen(new PantallaAyudaPrimarios(juego));
            }
        });

        //LUMIL ------------------------------------------
        Button btnLumil = crearBoton("PantallaAyuda/btnAyudaLumilOFF.png","PantallaAyuda/btnAyudaLumilON.png");
        btnLumil.setPosition(X_CENTROS_BOTON, btnPrimarios.getY()+btnPrimarios.getHeight()+(btnLumil.getHeight()/2f)+SEPARACION_VERTICAL_BOTONES, Align.center);
        escenaMenu.addActor(btnLumil);

        btnLumil.addListener(new ActorGestureListener() {
            @Override
            public void touchDown(InputEvent event, float x, float y, int pointer, int button){
                isLumilFadingIn = true;
                isLumilFadingOut = false;
            }
        });

        btnLumil.addListener(new ActorGestureListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button){
                isLumilFadingIn = false;
                isLumilFadingOut = true;
            }
        });

        btnLumil.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                juego.setScreen(new PantallaAyudaLumil(juego));
            }
        });

        Gdx.input.setInputProcessor(escenaMenu);
        // help= new Sprite(new Texture("Menu/clickayuda.png"));
        // help.setPosition(ANCHO/2-help.getWidth(),ALTO/2-help.getHeight());
        // help.setSize(794,454);

        Button btnRegresar = crearBoton("Menu/buttonback.png", "Menu/clickback.png");
        btnRegresar.setPosition(160,ALTO-80, Align.center);
        btnRegresar.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Cambiar de pantalla a Juego
                juego.setScreen(new PantallaMenu(juego));
            }
        });

        escenaMenu.addActor(btnRegresar);
        Gdx.input.setInputProcessor(escenaMenu);
    }

    private Button crearBoton(String archivo) {
        Texture texturaBoton = manager.get(archivo);
        TextureRegionDrawable trdBtnLumil = new TextureRegionDrawable(texturaBoton);
        return new Button(trdBtnLumil);
    }

    @Override
    public void render(float delta) {
        borrarPantalla(0,50,125);
        if(Gdx.input.isKeyPressed(Input.Keys.BACK)){
            // Regresar a la pantalla anterior (ACCION)
            juego.setScreen(new PantallaMenu(juego));
        }
        actualizarVisuales(delta);
        actualizarEstrellas(delta);

        batch.setProjectionMatrix(camara.combined);

        batch.begin();
        batch.draw(texturafondo,0,0);

        //Dibujar las estrellas
        estrellaUno.render(batch);
        estrellaDos.render(batch);
        estrellaTres.render(batch);
        estrellaCuatro.render(batch);
        estrellaCinco.render(batch);
        if (arrEstrellas!=null){
            for(BrilloLumil estrella: arrEstrellas){
                estrella.render(batch);
            }

        }

        //Dibujar los elementos Visuales
        visualLumil.render(batch);
        visualOscuridad.render(batch);
        visualPrimarios.render(batch);
        visualHijos.render(batch);
        visualHistoria.render(batch);

        batch.draw(texturaLineas,ANCHO- (texturaLineas.getWidth()),0);
        // help.draw(batch);
        batch.end();

        escenaMenu.draw();
    }

    private void actualizarEstrellas(float delta) {
        if(arrEstrellas!=null){
            for(BrilloLumil estrella: arrEstrellas){
                estrella.actualizar(1,1,1,0.02f+MathUtils.random(0,.03f));
            }

        }
        estrellaUno.actualizar(1,1,1,ESCALA_ESTRELLA_UNO+MathUtils.random(0,.03f));
        estrellaDos.actualizar(1,1,1,ESCALA_ESTRELLA_DOS+MathUtils.random(0,.03f));
        estrellaTres.actualizar(1,1,1,ESCALA_ESTRELLA_TRES+MathUtils.random(0,.03f));
        estrellaCuatro.actualizar(1,1,1,ESCALA_ESTRELLA_CUATRO+MathUtils.random(0,.03f));
        estrellaCinco.actualizar(1,1,1,ESCALA_ESTRELLA_CINCO+MathUtils.random(0,.03f));
    }

    private void actualizarVisuales(float delta) {
        //Lumil
        actualizarObjetoIndividual(delta,visualLumil,isLumilFadingIn,isLumilFadingOut);

        //Oscuridad
        actualizarObjetoIndividual(delta,visualOscuridad,isOscuridadFadingIn,isOscuridadFadingOut);
        //actualizarOscuridad(delta);

        //Primarios
        actualizarObjetoIndividual(delta,visualPrimarios,isPrimariosFadingIn,isPrimariosFadingOut);

        //Hijos
        actualizarObjetoIndividual(delta,visualHijos,isHijosFadingIn,isHijosFadingOut);

        //Historia
        actualizarObjetoIndividual(delta,visualHistoria,isHistoriaFadingIn,isHistoriaFadingOut);

    }

    private void actualizarObjetoIndividual(float delta, Objeto objeto,boolean isFadingIn, boolean isFadingOut) {
        //Hacer el fade in
        if (isFadingIn){
            objeto.fadeIn(delta,DURACION_FADE);
        }
        //Hacer el Fade out
        if (isFadingOut && objeto.alfaSprite>0){
            objeto.fadeOut(delta,DURACION_FADE);
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

        manager.unload("PantallaAyuda/fondoAyuda.jpg");
        manager.unload("PantallaAyuda/ayudaHijosOscuridad.png");
        manager.unload("PantallaAyuda/ayudaHistoria.png");
        manager.unload("PantallaAyuda/ayudaLineas.png");
        manager.unload("PantallaAyuda/ayudaLumil.png");
        manager.unload("PantallaAyuda/ayudaOscuridad.png");
        manager.unload("PantallaAyuda/ayudaPrimarios.png");

        manager.unload("PantallaAyuda/btnAyudaHistoriaOFF.png");
        manager.unload("PantallaAyuda/btnAyudaHistoriaON.png");
        manager.unload("PantallaAyuda/btnAyudaHijosOFF.png");
        manager.unload("PantallaAyuda/btnAyudaHijosON.png");
        manager.unload("PantallaAyuda/btnAyudaLumilOFF.png");
        manager.unload("PantallaAyuda/btnAyudaLumilON.png");
        manager.unload("PantallaAyuda/btnAyudaOscuridadOFF.png");
        manager.unload("PantallaAyuda/btnAyudaOscuridadON.png");
        manager.unload("PantallaAyuda/btnAyudaPrimariosOFF.png");
        manager.unload("PantallaAyuda/btnAyudaPrimariosON.png");
        manager.unload("Menu/buttonback.png");
        manager.unload("Menu/clickback.png");
        manager.unload("Utileria/brilloLumil.png");

        arrEstrellas.clear();
    }

}

