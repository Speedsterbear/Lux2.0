package mx.xul.game;

/*
Esta clase sirve para mostrar la pantalla de historia.
Autor: Carlos Arroyo.
 */


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;

public class PantallaHistoria extends Pantalla {

    private Lux juego;

    // AssetManager
    private AssetManager manager;


    //Fondo
    private Texture texturaFondo; //Fondo de la pantalla
    private Texture texturaHumo; //Textura para dar el eefecto de humo o sombras moviéndose en el fondo
    private ObjetoAnimado humo; //Para la animación del himo/sombras moviéndose en el fondo

    private Texture texturaMensaje;//Mensaje inicial de "Tap para continuar"
    private Objeto mensaje; //Para poder hacerlo fade como los demás

    //Vitrales
    //El número en el nombre indica el orden en el que deben aparecer para contar la historia.

    private Texture texturaVitralUno;
    private Objeto vitralUno;

    private Texture texturaVitralDos;
    private Objeto vitralDos;

    private Texture texturaVitralTres;
    private Objeto vitralTres;

    private Texture texturaVitralCuatro;
    private Objeto vitralCuatro;

    private Texture texturaVitralCinco;
    private Objeto vitralCinco;

    private Texture texturaVitralSeis;
    private Objeto vitralSeis;

    //Versos de la Historia
    //El número en el nombre indica el orden en el que deben aparecer para contar la historia.

    private Texture texturaVersoUno;
    private Objeto versoUno;

    private Texture texturaVersoDos;
    private Objeto versoDos;

    private Texture texturaVersoTres;
    private Objeto versoTres;

    private Texture texturaVersoCuatro;
    private Objeto versoCuatro;

    private Texture texturaVersoCinco;
    private Objeto versoCinco;

    private Texture texturaVersoSeis;
    private Objeto versoSeis;

    //Variables de control
    private int seccionHistoria = 0; //Representa el verso y vitral que se va a mostrar

    private float duracionTransicionSeccion = 2; //Tiempo que se tarda en aparecer la sección
    private float contadorduracionSeccion = 0;//Accumulador para poder controlar el tiempo de duración de la sección

    private float posicionXInicialVitral = ANCHO/4; //3*ANCHO/2; // Inicia afuera (a la derecha)
    private float posicionYInicialVitral = 5*ALTO/3; // Inicia al medio de la altura
    private float posicionYMediaVitral = ALTO/2; // Inicia al medio de la altura
    private float posicionYFinalVitral = -2*ALTO/3; // Inicia al medio de la altura


    private float posicionXInicialVerso = ANCHO - (720/2); // Inicia afuera (abajo)
    private float posicionYInicialVerso = -ALTO/2;
    private float posicionYMediaVerso = ALTO/2;
    private float posicionYFinalVerso = 3*ALTO/2;

    private float tiempo = 0; //Acumulador usado para las animaciones.

    private float dy = (posicionYMediaVerso -posicionYInicialVerso)/(60*duracionTransicionSeccion);
    private float dyVitral = (posicionYInicialVitral- posicionYMediaVitral)/(60*duracionTransicionSeccion);

    //Para el Fade
    private Transicion fadeNegro;

    public PantallaHistoria(Lux juego) {
        this.juego=juego;
        manager = juego.getAssetManager();
    }


    @Override
    public void show() {
        crearFondo();
        crearVitrales();
        crearVersos();

        //Fade
        fadeNegro = new Transicion(0,0,0,1,ALTO,ANCHO);

        Gdx.input.setInputProcessor( new ProcesadorEntradaHistoria());
        // Bloquear la tecla de back
        Gdx.input.setCatchKey(Input.Keys.BACK,true);

    }

    private void crearVersos() {
        texturaVersoUno = manager.get("PantallaHistoria/Letras_1.png");
        versoUno = new Objeto(texturaVersoUno,posicionXInicialVerso,posicionYInicialVerso);

        texturaVersoDos = manager.get("PantallaHistoria/Letras_2.png");
        versoDos = new Objeto(texturaVersoDos,posicionXInicialVerso,posicionYInicialVerso);

        texturaVersoTres = manager.get("PantallaHistoria/Letras_3.png");
        versoTres = new Objeto(texturaVersoTres,posicionXInicialVerso,posicionYInicialVerso);

        texturaVersoCuatro = manager.get("PantallaHistoria/Letras_4.png");
        versoCuatro = new Objeto(texturaVersoCuatro,posicionXInicialVerso,posicionYInicialVerso);

        texturaVersoCinco = manager.get("PantallaHistoria/Letras_5.png");
        versoCinco = new Objeto(texturaVersoCinco,posicionXInicialVerso,posicionYInicialVerso);

        texturaVersoSeis = manager.get("PantallaHistoria/Letras_6.png");
        versoSeis = new Objeto(texturaVersoSeis,posicionXInicialVerso,posicionYInicialVerso);
    }

    private void crearFondo() {
        texturaFondo = manager.get("PantallaHistoria/fondo_humo.jpg");

        texturaHumo = manager.get("PantallaHistoria/Humo.png");
        humo = new ObjetoAnimado(texturaHumo,ANCHO/2,ALTO/2,5,1,1/6f,2);
        //humo.sprite.setColor(1,1,1,1);

        texturaMensaje = manager.get("PantallaHistoria/mensaje_inicial.png");
        mensaje = new Objeto(texturaMensaje,ANCHO/2,ALTO/2);
        mensaje.alfaSprite=1;
    }

    private void crearVitrales() {
        texturaVitralUno = manager.get("PantallaHistoria/Vitral_No1.png");
        vitralUno = new Objeto(texturaVitralUno,posicionXInicialVitral,posicionYInicialVitral);

        texturaVitralDos = manager.get("PantallaHistoria/Vitral_No2.png");
        vitralDos = new Objeto(texturaVitralDos,posicionXInicialVitral,posicionYInicialVitral);

        texturaVitralTres = manager.get("PantallaHistoria/Vitral_No3.png");
        vitralTres = new Objeto(texturaVitralTres,posicionXInicialVitral,posicionYInicialVitral);

        texturaVitralCuatro = manager.get("PantallaHistoria/Vitral_No4.png");
        vitralCuatro = new Objeto(texturaVitralCuatro,posicionXInicialVitral,posicionYInicialVitral);

        texturaVitralCinco = manager.get("PantallaHistoria/Vitral_No5.png");
        vitralCinco = new Objeto(texturaVitralCinco,posicionXInicialVitral,posicionYInicialVitral);

        texturaVitralSeis = manager.get("PantallaHistoria/Vitral_No6.png");
        vitralSeis = new Objeto(texturaVitralSeis,posicionXInicialVitral,posicionYInicialVitral);
    }

    @Override
    public void render(float delta) {

        borrarPantalla(0,0,0);

        if(Gdx.input.isKeyPressed(Input.Keys.BACK)){
            // Regresar a la pantalla anterior (ACCION)
            juego.setScreen(new PantallaAyuda(juego));
        }

        actualizar(delta);

        batch.setProjectionMatrix(camara.combined);

        batch.begin();

        //Dibujar fondo
        batch.draw(texturaFondo,0,0);
        humo.animationRender(batch,tiempo);

        switch (seccionHistoria){
            case 0:
                mensaje.render(batch);
                break;
            case 1:
                mensaje.render(batch);
                vitralUno.render(batch);
                versoUno.render(batch);
                break;
            case 2:
                vitralUno.render(batch);
                versoUno.render(batch);
                vitralDos.render(batch);
                versoDos.render(batch);
                break;
            case 3:
                vitralDos.render(batch);
                versoDos.render(batch);
                vitralTres.render(batch);
                versoTres.render(batch);
                break;
            case 4:
                vitralTres.render(batch);
                versoTres.render(batch);
                vitralCuatro.render(batch);
                versoCuatro.render(batch);
                break;
            case 5:
                vitralCuatro.render(batch);
                versoCuatro.render(batch);
                vitralCinco.render(batch);
                versoCinco.render(batch);
                break;
            case 6:
                vitralCinco.render(batch);
                versoCinco.render(batch);
                vitralSeis.render(batch);
                versoSeis.render(batch);
                break;
            case 7:
                vitralSeis.render(batch);
                versoSeis.render(batch);
                break;
        }

        batch.end();


        fadeNegro.render(camara);

        if (fadeNegro.isFadeOutFinished){
            juego.setScreen(new PantallaCargando(juego,Pantallasenum.PANTALLAAYUDA));
        }

    }

    private void actualizar(float delta) {
        tiempo +=delta;
        switch (seccionHistoria){
            case 0:
                fadeNegro.fadeIn(delta,duracionTransicionSeccion);
                break;
            case 1:
                fadeNegro.alfaRectangulo=0;
                mensaje.fadeOut(delta,duracionTransicionSeccion);

                vitralUno.fadeIn(delta,duracionTransicionSeccion);

                if (vitralUno.sprite.getY()>(posicionYMediaVitral -(vitralUno.sprite.getHeight()/2))){
                    vitralUno.sprite.setY(vitralUno.sprite.getY()-dyVitral);
                }


                versoUno.fadeIn(delta,duracionTransicionSeccion);
                if (versoUno.sprite.getY()<(posicionYMediaVerso -(versoUno.sprite.getHeight()/2))){
                    versoUno.sprite.setY(versoUno.sprite.getY()+dy);
                }

                break;
            case 2:

                vitralUno.fadeOut(delta,duracionTransicionSeccion);
                if (vitralUno.sprite.getY()>(posicionYFinalVitral -(vitralUno.sprite.getHeight()/2))){
                    vitralUno.sprite.setY(vitralUno.sprite.getY()-dyVitral);
                }

                versoUno.fadeOut(delta,duracionTransicionSeccion);
                if (versoUno.sprite.getY()<(posicionYFinalVerso -(versoUno.sprite.getHeight()/2))){
                    versoUno.sprite.setY(versoUno.sprite.getY()+dy);
                }


                vitralDos.fadeIn(delta,duracionTransicionSeccion);
                if (vitralDos.sprite.getY()>(posicionYMediaVitral -(vitralDos.sprite.getHeight()/2))){
                    vitralDos.sprite.setY(vitralDos.sprite.getY()-dyVitral);
                }


                versoDos.fadeIn(delta,duracionTransicionSeccion);
                if (versoDos.sprite.getY()<(posicionYMediaVerso -(versoDos.sprite.getHeight()/2))){
                    versoDos.sprite.setY(versoDos.sprite.getY()+dy);
                }

                break;
            case 3:

                vitralDos.fadeOut(delta,duracionTransicionSeccion);
                if (vitralDos.sprite.getY()>(posicionYFinalVitral -(vitralDos.sprite.getHeight()/2))){
                    vitralDos.sprite.setY(vitralDos.sprite.getY()-dyVitral);
                }

                versoDos.fadeOut(delta,duracionTransicionSeccion);
                if (versoDos.sprite.getY()<(posicionYFinalVerso -(versoDos.sprite.getHeight()/2))){
                    versoDos.sprite.setY(versoDos.sprite.getY()+dy);
                }


                vitralTres.fadeIn(delta,duracionTransicionSeccion);
                if (vitralTres.sprite.getY()>(posicionYMediaVitral -(vitralTres.sprite.getHeight()/2))){
                    vitralTres.sprite.setY(vitralTres.sprite.getY()-dyVitral);
                }


                versoTres.fadeIn(delta,duracionTransicionSeccion);
                if (versoTres.sprite.getY()<(posicionYMediaVerso -(versoTres.sprite.getHeight()/2))){
                    versoTres.sprite.setY(versoTres.sprite.getY()+dy);
                }

                break;
            case 4:

                vitralTres.fadeOut(delta,duracionTransicionSeccion);
                if (vitralTres.sprite.getY()>(posicionYFinalVitral -(vitralTres.sprite.getHeight()/2))){
                    vitralTres.sprite.setY(vitralTres.sprite.getY()-dyVitral);
                }

                versoTres.fadeOut(delta,duracionTransicionSeccion);
                if (versoTres.sprite.getY()<(posicionYFinalVerso -(versoTres.sprite.getHeight()/2))){
                    versoTres.sprite.setY(versoTres.sprite.getY()+dy);
                }


                vitralCuatro.fadeIn(delta,duracionTransicionSeccion);
                if (vitralCuatro.sprite.getY()>(posicionYMediaVitral -(vitralCuatro.sprite.getHeight()/2))){
                    vitralCuatro.sprite.setY(vitralCuatro.sprite.getY()-dyVitral);
                }


                versoCuatro.fadeIn(delta,duracionTransicionSeccion);
                if (versoCuatro.sprite.getY()<(posicionYMediaVerso -(versoCuatro.sprite.getHeight()/2))){
                    versoCuatro.sprite.setY(versoCuatro.sprite.getY()+dy);
                }

                break;
            case 5:

                vitralCuatro.fadeOut(delta,duracionTransicionSeccion);
                if (vitralCuatro.sprite.getY()>(posicionYFinalVitral -(vitralCuatro.sprite.getHeight()/2))){
                    vitralCuatro.sprite.setY(vitralCuatro.sprite.getY()-dyVitral);
                }

                versoCuatro.fadeOut(delta,duracionTransicionSeccion);
                if (versoCuatro.sprite.getY()<(posicionYFinalVerso -(versoCuatro.sprite.getHeight()/2))){
                    versoCuatro.sprite.setY(versoCuatro.sprite.getY()+dy);
                }


                vitralCinco.fadeIn(delta,duracionTransicionSeccion);
                if (vitralCinco.sprite.getY()>(posicionYMediaVitral -(vitralCinco.sprite.getHeight()/2))){
                    vitralCinco.sprite.setY(vitralCinco.sprite.getY()-dyVitral);
                }


                versoCinco.fadeIn(delta,duracionTransicionSeccion);
                if (versoCinco.sprite.getY()<(posicionYMediaVerso -(versoCinco.sprite.getHeight()/2))){
                    versoCinco.sprite.setY(versoCinco.sprite.getY()+dy);
                }
                break;
            case 6:

                vitralCinco.fadeOut(delta,duracionTransicionSeccion);
                if (vitralCinco.sprite.getY()>(posicionYFinalVitral -(vitralCinco.sprite.getHeight()/2))){
                    vitralCinco.sprite.setY(vitralCinco.sprite.getY()-dyVitral);
                }

                versoCinco.fadeOut(delta,duracionTransicionSeccion);
                if (versoCinco.sprite.getY()<(posicionYFinalVerso -(versoCinco.sprite.getHeight()/2))){
                    versoCinco.sprite.setY(versoCinco.sprite.getY()+dy);
                }


                vitralSeis.fadeIn(delta,duracionTransicionSeccion);
                if (vitralSeis.sprite.getY()>(posicionYMediaVitral -(vitralSeis.sprite.getHeight()/2))){
                    vitralSeis.sprite.setY(vitralSeis.sprite.getY()-dyVitral);
                }


                versoSeis.fadeIn(delta,duracionTransicionSeccion);
                if (versoSeis.sprite.getY()<(posicionYMediaVerso -(versoSeis.sprite.getHeight()/2))){
                    versoSeis.sprite.setY(versoSeis.sprite.getY()+dy);
                }
                break;
            default:
                fadeNegro.fadeOut(delta,duracionTransicionSeccion);
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

        manager.unload("PantallaHistoria/mensaje_inicial.png");
        manager.unload("PantallaHistoria/Humo.png");
        manager.unload("PantallaHistoria/fondo_humo.jpg");
        manager.unload("PantallaHistoria/Letras_1.png");
        manager.unload("PantallaHistoria/Letras_2.png");
        manager.unload("PantallaHistoria/Letras_3.png");
        manager.unload("PantallaHistoria/Letras_4.png");
        manager.unload("PantallaHistoria/Letras_5.png");
        manager.unload("PantallaHistoria/Letras_6.png");
        manager.unload("PantallaHistoria/Vitral_No1.png");
        manager.unload("PantallaHistoria/Vitral_No2.png");
        manager.unload("PantallaHistoria/Vitral_No3.png");
        manager.unload("PantallaHistoria/Vitral_No4.png");
        manager.unload("PantallaHistoria/Vitral_No5.png");
        manager.unload("PantallaHistoria/Vitral_No6.png");

    }

    private class ProcesadorEntradaHistoria implements InputProcessor {
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

        @Override
        public boolean touchDown(int screenX, int screenY, int pointer, int button) {
            return false;
        }

        @Override
        public boolean touchUp(int screenX, int screenY, int pointer, int button) {
            if (fadeNegro.isFadeInFinished){
                seccionHistoria ++;
            }
            return true;
        }

        @Override
        public boolean touchDragged(int screenX, int screenY, int pointer) {
            return false;
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
}
