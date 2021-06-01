package mx.xul.game;
/*
Esta pantalla sirve para mostrar la información de la aplicación y sus creadores.
Autores: Carlos Arroyo y David
 */

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
//import org.graalvm.compiler.core.common.type.ArithmeticOpTable;


public class PantallaNosotros extends Pantalla {
    private Texture texturafondo;
    private Stage escenaMenu;
    private Sprite aboutus;
    private Lux juego;

    //private Texture androidImage;

    private Texture tecImage;

    private Texture mailImage;
    //Para controlar el movimiento de las pantallas
    private int mover = 0;
    private float avancePantalla = 20;
    private int pantalla = 1; //Pantalla 1 es la de info general, la 2 es la de los integrantes

    // AssetManager
    private AssetManager manager;

    //Cristales de colores en los fondos
    private int cristalColor = 0;
    //Texturas de los cristales (una por color)
    private Texture texturaCristalW;
    private Texture texturaCristalG;
    private Texture texturaCristalR;
    private Texture texturaCristalB;
    private Texture texturaCristalY;
    private Texture texturaCristalC;

    //Imagenes de la información de las personas
    private Texture texturaAndrea;
    private Texture texturaDavid;
    private Texture texturaEduardo;
    private Texture texturaRicardo;
    private Texture texturaCarlos;
    private Texture texturaInicial;

    private Texture texturaD;
    private Texture texturaI;
    private Texture texturaA;

    //Para crear el El cristal
    private Cristal cristalW;
    private Cristal cristalG;
    private Cristal cristalR;
    private Cristal cristalB;
    private Cristal cristalY;
    private Cristal cristalC;
    private float Tiempo = 0f;

    //Brillo
    private Texture texturaBrillo;
    private BrilloLumil brilloCentro;
    private BrilloLumil brilloCentroMedio;
    private BrilloLumil brilloCentroFrente;
    private BrilloLumil brilloHorizontal;
    private BrilloLumil brilloVertical;
    private BrilloLumil brilloDiagonalAscendente;
    private BrilloLumil brilloDiagonalDescendente;
    private final float SCALA_INICIAL = 0.5f;
    private final float CAMBIO_ESCALA = 0.2f;
    private float angulo = 0; //Acumulador para movimiento
    private final float AVANCE_BRILLO = 110;//Movimiento horizontal del brillo
    private float colorR = 1; //Color Rojo
    private float colorG = 1; //Color Verde
    private float colorB = 1; //Color Azul

    //Brillo Flechas
    private BrilloLumil brilloFlechaUno; //El numero representa su posición, los mas pequeños estan más a la derecha
    private BrilloLumil brilloFlechaDos;
    private BrilloLumil brilloFlechaTres;
    private BrilloLumil brilloFlechaCuatro;
    private BrilloLumil brilloFlechaCinco;
    private BrilloLumil brilloFlechaSeis;
    private BrilloLumil brilloFlechaSiete;
    private BrilloLumil brilloFlechaOcho;

    private Array<BrilloLumil> arrFlechaArriba;


    private final float X_PUNTA_FLECHA_IZQ = ANCHO-300;
    private final float X_PUNTA_FLECHA_DER = ANCHO+300;
    private final float Y_PUNTA_FLECHA = ALTO - 100;

    private final float X_PUNTA_FLECHA_ARR = (ANCHO*2)-100;
    private final float Y_PUNTA_FLECHA_ARR = 200;

    private final float SEPARACION_BRILLO_FLECHA = 55;
    private float avanceBrilloFlecha = 6;
    private float nuevaPosicionX = 0;
    private float nuevaPosicionY = 0;
    private final float ESCALA_BRILLO_FLECHAS = 0.22f;

    //Cristal
    private Texture texturaRomboPerder;
    private Objeto rombo;
    private final float DY_ROMBO = 10;

    private Objeto flechaD;
    private Objeto flechaI;
    private Objeto flechaA;



    public PantallaNosotros(Lux juego) {
        this.juego=juego;
        manager = juego.getAssetManager();
    }
    private void crearNosotros(){
       //androidImage= manager.get("Nosotros/Androidst.png");
        tecImage = manager.get("Nosotros/tecst.png");
        texturafondo= manager.get("Nosotros/fondoAbout.jpg");
        mailImage = manager.get("Nosotros/mailst.png");
        escenaMenu=new Stage(vista);

        Button btnRegresar = crearBoton("Menu/buttonback.png", "Menu/clickback.png");
        btnRegresar.setPosition(160,ALTO-80, Align.center);
        btnRegresar.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Cambiar de pantalla a Menu
                juego.setScreen(new PantallaMenu(juego));


            }
        });

        Button btnCreditos = crearBoton("Botones/btnTransparente.png", "Botones/btnTransparente.png");
        btnCreditos.setPosition(ANCHO-450,ALTO-100, Align.center);
        btnCreditos.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Cambiar de pantalla a Juego
                //juego.setScreen(new PantallaCreditos(juego));
                //1 Significa que va hacia la derecha
                mover = 1;
                pantalla = 2;
                avanceBrilloFlecha = 12;

            }
        });

        Button btnNosotros = crearBoton("Botones/btnTransparente.png", "Botones/btnTransparente.png");
        btnNosotros.setPosition(ANCHO+450,  ALTO-100, Align.center);
        btnNosotros.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Cambiar de pantalla a Juego
                //juego.setScreen(new PantallaCreditos(juego));
                //2 significa que va hacia la izquierda
                mover = 2;
                cristalColor = 0;
                pantalla = 1;

            }
        });


        Button btnC = crearBoton("Botones/btnTransparenteVertical.png", "Botones/btnTransparenteVertical.png");
        btnC.setPosition(2*ANCHO - 100,100, Align.center);
        btnC.addListener(new ClickListener(){
            @Override
        public void clicked(InputEvent event, float x, float y) {
        // Cambiar de pantalla a Juego
        //juego.setScreen(new PantallaCreditos(juego));
        //2 significa que va hacia la izquierda
                cristalColor +=1;
                if (cristalColor > 5){
                    cristalColor = 0;
                }
        }
        });


        escenaMenu.addActor(btnRegresar);
        escenaMenu.addActor(btnCreditos);
        escenaMenu.addActor(btnNosotros);
        escenaMenu.addActor(btnC);
        Gdx.input.setInputProcessor(escenaMenu);
    }
    @Override
    public void show() {
        crearBrillos();
        crearBrilloFlechas();
        crearNosotros();
        crearCristales();
        crearRombo();
        Gdx.input.setCatchKey(Input.Keys.BACK,true);
    }

    private void crearBrilloFlechas() {
        final float MARGEN_EXTERIOR = 50f;
        brilloFlechaUno = new BrilloLumil(texturaBrillo,MathUtils.random(-MARGEN_EXTERIOR,0),MathUtils.random(0,ALTO));
        brilloFlechaDos = new BrilloLumil(texturaBrillo,MathUtils.random(0,3*ANCHO/2),MathUtils.random(-MARGEN_EXTERIOR,0));
        brilloFlechaTres = new BrilloLumil(texturaBrillo,MathUtils.random(0,3*ANCHO/2),MathUtils.random(ALTO,ALTO+MARGEN_EXTERIOR));
        brilloFlechaCuatro = new BrilloLumil(texturaBrillo,MathUtils.random(0,3*ANCHO/2),MathUtils.random(-MARGEN_EXTERIOR,0));
        brilloFlechaSeis = new BrilloLumil(texturaBrillo,MathUtils.random(-MARGEN_EXTERIOR,0),MathUtils.random(0,ALTO));
        brilloFlechaCinco = new BrilloLumil(texturaBrillo,MathUtils.random(0,3*ANCHO/2),MathUtils.random(ALTO,ALTO+MARGEN_EXTERIOR));
        brilloFlechaSiete = new BrilloLumil(texturaBrillo,MathUtils.random(0,3*ANCHO/2),MathUtils.random(-MARGEN_EXTERIOR,0));
        brilloFlechaOcho = new BrilloLumil(texturaBrillo,MathUtils.random(0,3*ANCHO/2),MathUtils.random(ALTO,ALTO+MARGEN_EXTERIOR));

        arrFlechaArriba = new Array<>();
        for (int  i=0; i<=8; i++){
            float x = 0;
            float y = 0;
            switch (i){
                case 0:
                    x=X_PUNTA_FLECHA_ARR;
                    y=Y_PUNTA_FLECHA_ARR;
                    break;
                case 1:
                    x=X_PUNTA_FLECHA_ARR - (SEPARACION_BRILLO_FLECHA/2);
                    y=Y_PUNTA_FLECHA_ARR - (SEPARACION_BRILLO_FLECHA/2);
                    break;
                case 2:
                    x=X_PUNTA_FLECHA_ARR + (SEPARACION_BRILLO_FLECHA/2);
                    y=Y_PUNTA_FLECHA_ARR - (SEPARACION_BRILLO_FLECHA/2);
                    break;
                case 3:
                    x=X_PUNTA_FLECHA_ARR - (SEPARACION_BRILLO_FLECHA);
                    y=Y_PUNTA_FLECHA_ARR - (SEPARACION_BRILLO_FLECHA);
                    break;
                case 4:
                    x=X_PUNTA_FLECHA_ARR + (SEPARACION_BRILLO_FLECHA);
                    y=Y_PUNTA_FLECHA_ARR - (SEPARACION_BRILLO_FLECHA);
                    break;
                case 5:
                    x=X_PUNTA_FLECHA_ARR;
                    y=Y_PUNTA_FLECHA_ARR - (SEPARACION_BRILLO_FLECHA);
                    break;
                case 6:
                    x=X_PUNTA_FLECHA_ARR;
                    y=Y_PUNTA_FLECHA_ARR - (2* SEPARACION_BRILLO_FLECHA);
                    break;
                case 7:
                    x=X_PUNTA_FLECHA_ARR;
                    y=Y_PUNTA_FLECHA_ARR - (3* SEPARACION_BRILLO_FLECHA);
                    break;
            }
            BrilloLumil brilloFlecha = new BrilloLumil(texturaBrillo, x,y);
            arrFlechaArriba.add(brilloFlecha);
        }
    }

    private void crearRombo() {
        texturaRomboPerder =  manager.get("Utileria/cristalRomboBlanco.png");
        rombo = new Objeto(texturaRomboPerder,(3*ANCHO/2)+150,5*ALTO/8);
        rombo.sprite.setScale(0.3f);
    }

    private void crearBrillos() {
        texturaBrillo = manager.get("Utileria/brilloLumil.png");
        brilloCentro = new BrilloLumil(texturaBrillo,(3*ANCHO/2)+150,5*ALTO/8);
        brilloCentroMedio = new BrilloLumil(texturaBrillo,(3*ANCHO/2)+150,5*ALTO/8);
        brilloCentroFrente = new BrilloLumil(texturaBrillo,(3*ANCHO/2)+150,5*ALTO/8);
        brilloHorizontal = new BrilloLumil(texturaBrillo,(3*ANCHO/2)+150,5*ALTO/8);
        brilloVertical = new BrilloLumil(texturaBrillo,(3*ANCHO/2)+150,5*ALTO/8);
        brilloDiagonalAscendente = new BrilloLumil(texturaBrillo,(3*ANCHO/2)+150,5*ALTO/8);
        brilloDiagonalDescendente = new BrilloLumil(texturaBrillo,(3*ANCHO/2)+150,5*ALTO/8);


    }

    private void crearCristales() {
        texturaCristalW = manager.get("Nosotros/Cristales-W.png");
        cristalW = new Cristal(texturaCristalW,texturafondo.getWidth()/2,texturaCristalW.getHeight()/4,4,2,1/4f,1);

        texturaCristalG = manager.get("Nosotros/Cristales-G.png");
        cristalG = new Cristal(texturaCristalG,texturafondo.getWidth()/2,texturaCristalG.getHeight()/4,4,2,1/4f,1);

        texturaCristalR = manager.get("Nosotros/Cristales-R.png");
        cristalR = new Cristal(texturaCristalR,texturafondo.getWidth()/2,texturaCristalR.getHeight()/4,4,2,1/4f,1);

        texturaCristalB = manager.get("Nosotros/Cristales-B.png");
        cristalB = new Cristal(texturaCristalB,texturafondo.getWidth()/2,texturaCristalB.getHeight()/4,4,2,1/4f,1);

        texturaCristalY =manager.get("Nosotros/Cristales-Y.png");
        cristalY = new Cristal(texturaCristalY,texturafondo.getWidth()/2,texturaCristalY.getHeight()/4,4,2,1/4f,1);

        texturaCristalC = manager.get("Nosotros/Cristales-C.png");
        cristalC = new Cristal(texturaCristalC,texturafondo.getWidth()/2,texturaCristalC.getHeight()/4,4,2,1/4f,1);

        texturaD = manager.get("Nosotros/flechaColorSolidoDer.png");
        texturaI= manager.get("Nosotros/flechaColorSolidoIzq.png");
        texturaA = manager.get("Nosotros/flechaColorSolidoArriba.png");

        flechaD = new Objeto(texturaD,ANCHO-350,  ALTO-100);
        flechaI = new Objeto(texturaI, ANCHO+350,ALTO-100);
        flechaA = new Objeto(texturaA,2*ANCHO - 100,100);

        flechaD.sprite.setColor(.7f,.7f,.7f,1);

        texturaAndrea = manager.get("Nosotros/andrea.png");
        texturaCarlos = manager.get("Nosotros/carlos.png");
        texturaDavid = manager.get("Nosotros/david.png");
        texturaEduardo = manager.get("Nosotros/eduardo.png");
        texturaRicardo = manager.get("Nosotros/ricardo.png");
        texturaInicial = manager.get("Nosotros/CasoInicial.png");

    }

    private Button crearBoton(String archivo, String archivoclick) {
        Texture texturaBoton= manager.get(archivo);
        TextureRegionDrawable trdBtnRunner=new TextureRegionDrawable(texturaBoton);

        Texture texturaClick= manager.get(archivoclick);
        TextureRegionDrawable trdBtnClick=new TextureRegionDrawable(texturaClick);

        return new Button(trdBtnRunner,trdBtnClick);
    }

    @Override
    public void render(float delta) {
        Tiempo += Gdx.graphics.getDeltaTime(); // Tiempo que pasó entre render.
        borrarPantalla(0,50,125);

        actualizarBrillo();
        actualizarRombo();


        if(Gdx.input.isKeyPressed(Input.Keys.BACK)){
            // Regresar a la pantalla anterior (ACCION)
            juego.setScreen(new PantallaMenu(juego));
        }

        recorrerPantalla();

        batch.setProjectionMatrix(camara.combined);

        batch.begin();
        batch.draw(texturafondo,0,0);
        //flechaD.render(batch);
        //flechaA.render(batch);
        //flechaI.render(batch);
       //batch.draw(androidImage,75,350);
        batch.draw(tecImage, 25,200);
        batch.draw(mailImage, 75,0);

        dibujarCristales();

        dibujarBrillo();
        dibujarBrilloFlechas();

        batch.end();

        escenaMenu.draw();
    }

    private void dibujarBrilloFlechas() {
        brilloFlechaUno.render(batch);
        brilloFlechaDos.render(batch);
        brilloFlechaTres.render(batch);
        brilloFlechaCuatro.render(batch);
        brilloFlechaCinco.render(batch);
        brilloFlechaSeis.render(batch);
        brilloFlechaSiete.render(batch);
        brilloFlechaOcho.render(batch);


        for(BrilloLumil brilloFlechaArriba: arrFlechaArriba){
            brilloFlechaArriba.render(batch);
        }

    }




    private void actualizarRombo() {
        rombo.sprite.setY((5*ALTO/8)-(rombo.sprite.getHeight()/2)+(MathUtils.sinDeg(angulo)*DY_ROMBO));
        brilloCentro.mover((5*ALTO/8)+((MathUtils.sinDeg(angulo)*DY_ROMBO)));
        brilloCentroMedio.mover((5*ALTO/8)+((MathUtils.sinDeg(angulo)*DY_ROMBO)));
        brilloCentroFrente.mover((5*ALTO/8)+((MathUtils.sinDeg(angulo)*DY_ROMBO)));
    }

    private void dibujarBrillo() {

        if (angulo>=90 && angulo <180) {
            brilloHorizontal.render(batch);
            brilloVertical.render(batch);
            brilloCentro.render(batch);
            brilloCentroMedio.render(batch);
            rombo.render(batch);
            brilloCentroFrente.render(batch);
            //rombo.render(batch);
            brilloDiagonalAscendente.render(batch);
            brilloDiagonalDescendente.render(batch);

        }else if (angulo>=180 && angulo <270) {
            brilloHorizontal.render(batch);
            brilloDiagonalDescendente.render(batch);
            brilloCentro.render(batch);
            brilloCentroMedio.render(batch);
            rombo.render(batch);
            brilloCentroFrente.render(batch);
            //rombo.render(batch);
            brilloDiagonalAscendente.render(batch);
            brilloVertical.render(batch);

            }else if (angulo>=0 && angulo <90){
            brilloDiagonalAscendente.render(batch);
            brilloVertical.render(batch);
            brilloCentro.render(batch);
            brilloCentroMedio.render(batch);
            rombo.render(batch);
            brilloCentroFrente.render(batch);
            //rombo.render(batch);
            brilloHorizontal.render(batch);
            brilloDiagonalDescendente.render(batch);
        } else {
            brilloDiagonalAscendente.render(batch);
            brilloDiagonalDescendente.render(batch);
            brilloCentro.render(batch);
            brilloCentroMedio.render(batch);
            rombo.render(batch);
            brilloCentroFrente.render(batch);
            //rombo.render(batch);
            brilloHorizontal.render(batch);
            brilloVertical.render(batch);
        }
    }

    private void moverBrilloaCoordenada(float x, float y,float avance, BrilloLumil brilloLumil){
        float restaX = x - brilloLumil.getXCentro();
        float restaY = y - brilloLumil.getYCentro();

        //Coordenada en X
        if (restaX > 0){//El brillo esta a la izquierda del valor objetivo.
            nuevaPosicionX = brilloLumil.getXCentro()+(avance*MathUtils.random(0.85f,1.4f));
            if (nuevaPosicionX>x){
                nuevaPosicionX = x;
            }

            brilloLumil.moverX(nuevaPosicionX);

        } else if (restaX <0) {//El brillo esta a la derecha del valor objetivo.
            nuevaPosicionX = brilloLumil.getXCentro() - (avance*MathUtils.random(0.95f,1.2f));
            if (nuevaPosicionX < x) {
                nuevaPosicionX = x;
            }

            brilloLumil.moverX(nuevaPosicionX);
        }


        //Coordenada en Y
        if (restaY > 0){//El brillo esta abajo del valor objetivo.
            nuevaPosicionY = brilloLumil.getYCentro()+(avance*MathUtils.random(0.35f,0.5f));
            if (nuevaPosicionY>y){
                nuevaPosicionY = y;
            }

            brilloLumil.mover(nuevaPosicionY);

        } else if (restaY <0) {//El brillo esta arriba del valor objetivo.
            nuevaPosicionY = brilloLumil.getYCentro() - (avance*MathUtils.random(0.45f,0.6f));
            if (nuevaPosicionY < y) {
                nuevaPosicionY = y;
            }

            brilloLumil.mover(nuevaPosicionY);
        }



    }

    private void actualizarBrillo() {

        actualizarBrilloFlechas();

        angulo +=1;
        if (angulo>=360){
            angulo=0;
        }

        switch (cristalColor){
            case 1:
                colorR = 1;
                colorG = 0;
                colorB= 0;
                break;
            case 2:
                colorR = 1;
                colorG = 1;
                colorB= 0;
                break;
            case 3:
                colorR = 0;
                colorG = 1;
                colorB= 0;
                break;
            case 4:
                colorR = 0;
                colorG = 1;
                colorB= 1;
                break;
            case 5:
                colorR = 0;
                colorG = 0;
                colorB= 1;
                break;
            default:
                colorR = 1;
                colorG = 1;
                colorB= 1;
                break;

        }
        brilloCentro.actualizar(colorR,colorG,colorB,1f);
        brilloCentroMedio.actualizar(colorR,colorG,colorB,0.8f);
        brilloCentroFrente.actualizar(colorR,colorG,colorB,0.6f);
        // Cambiar color del rombo
        rombo.sprite.setColor(0.8f+(colorR*.2f),0.8f+(colorG*.2f),0.8f+(colorB*.2f),1);
        flechaI.sprite.setColor(colorR*.7f, colorG*.7f,colorB*.7f,1);
        flechaA.sprite.setColor(colorR*.7f, colorG*.7f,colorB*.7f,1);
        //rombo.sprite.setColor(colorR,colorG,colorB,1);

        brilloHorizontal.actualizar(colorR,colorG,colorB,SCALA_INICIAL-0.2f+((MathUtils.sinDeg(-(angulo-90))*CAMBIO_ESCALA)));
        brilloHorizontal.moverX(((3*ANCHO/2)+150)+((MathUtils.sinDeg(angulo)*AVANCE_BRILLO)));

        brilloVertical.actualizar(colorR,colorG,colorB,SCALA_INICIAL-0.2f+((MathUtils.sinDeg(-(angulo))*CAMBIO_ESCALA)));
        brilloVertical.mover((5*ALTO/8)+((MathUtils.cosDeg(angulo)*AVANCE_BRILLO)));

        brilloDiagonalAscendente.actualizar(colorR,colorG,colorB,SCALA_INICIAL-0.2f+((MathUtils.sinDeg(angulo-90)*CAMBIO_ESCALA)));
        brilloDiagonalAscendente.moverX(((3*ANCHO/2)+150)+((MathUtils.sinDeg(-angulo)*AVANCE_BRILLO)));

        brilloDiagonalDescendente.actualizar(colorR,colorG,colorB,SCALA_INICIAL-0.2f+((MathUtils.sinDeg(angulo)*CAMBIO_ESCALA)));
        brilloDiagonalDescendente.mover((5*ALTO/8)-((MathUtils.cosDeg(-angulo)*AVANCE_BRILLO)));

    }

    private void actualizarBrilloFlechas() {
        brilloFlechaUno.actualizar(colorR,colorG,colorB,ESCALA_BRILLO_FLECHAS);
        brilloFlechaDos.actualizar(colorR,colorG,colorB,ESCALA_BRILLO_FLECHAS);
        brilloFlechaTres.actualizar(colorR,colorG,colorB,ESCALA_BRILLO_FLECHAS);
        brilloFlechaCuatro.actualizar(colorR,colorG,colorB,ESCALA_BRILLO_FLECHAS);
        brilloFlechaCinco.actualizar(colorR,colorG,colorB,ESCALA_BRILLO_FLECHAS);
        brilloFlechaSeis.actualizar(colorR,colorG,colorB,ESCALA_BRILLO_FLECHAS);
        brilloFlechaSiete.actualizar(colorR,colorG,colorB,ESCALA_BRILLO_FLECHAS);
        brilloFlechaOcho.actualizar(colorR,colorG,colorB,ESCALA_BRILLO_FLECHAS);


        for(BrilloLumil brilloFlechaArriba: arrFlechaArriba){
            brilloFlechaArriba.actualizar(colorR,colorG,colorB,ESCALA_BRILLO_FLECHAS);
        }

        if (pantalla==1){
            formarFlechaIzq();
        }else {
            formarFlechaDer();
        }
    }

    private void formarFlechaIzq() {

        //Brillo Flecha 1
        moverBrilloaCoordenada(X_PUNTA_FLECHA_IZQ,Y_PUNTA_FLECHA, avanceBrilloFlecha,brilloFlechaUno);

        //Brillo Flecha 2
        moverBrilloaCoordenada(X_PUNTA_FLECHA_IZQ-(SEPARACION_BRILLO_FLECHA/2),
                Y_PUNTA_FLECHA+(SEPARACION_BRILLO_FLECHA/2), avanceBrilloFlecha,brilloFlechaDos);

        //Brillo Flecha 3
        moverBrilloaCoordenada(X_PUNTA_FLECHA_IZQ -(SEPARACION_BRILLO_FLECHA/2),
                Y_PUNTA_FLECHA-(SEPARACION_BRILLO_FLECHA/2), avanceBrilloFlecha,brilloFlechaTres);


        //Brillo Flecha 4
        moverBrilloaCoordenada(X_PUNTA_FLECHA_IZQ -(SEPARACION_BRILLO_FLECHA),
                Y_PUNTA_FLECHA+(SEPARACION_BRILLO_FLECHA), avanceBrilloFlecha,brilloFlechaCuatro);

        //Brillo Flecha 5
        moverBrilloaCoordenada(X_PUNTA_FLECHA_IZQ -(SEPARACION_BRILLO_FLECHA),
                Y_PUNTA_FLECHA, avanceBrilloFlecha,brilloFlechaCinco);

        //Brillo Flecha 6
        moverBrilloaCoordenada(X_PUNTA_FLECHA_IZQ -(SEPARACION_BRILLO_FLECHA),
                Y_PUNTA_FLECHA-(SEPARACION_BRILLO_FLECHA), avanceBrilloFlecha,brilloFlechaSeis);

        //Brillo Flecha 7
        moverBrilloaCoordenada(X_PUNTA_FLECHA_IZQ -(SEPARACION_BRILLO_FLECHA*2),
                Y_PUNTA_FLECHA, avanceBrilloFlecha,brilloFlechaSiete);

        //Brillo Flecha 8
        moverBrilloaCoordenada(X_PUNTA_FLECHA_IZQ -(SEPARACION_BRILLO_FLECHA*3),
                Y_PUNTA_FLECHA, avanceBrilloFlecha,brilloFlechaOcho);
    }

    private void formarFlechaDer() {

        //Brillo Flecha 1
        moverBrilloaCoordenada(X_PUNTA_FLECHA_DER,Y_PUNTA_FLECHA, avanceBrilloFlecha,brilloFlechaCuatro);

        //Brillo Flecha 2
        moverBrilloaCoordenada(X_PUNTA_FLECHA_DER+(SEPARACION_BRILLO_FLECHA/2),
                Y_PUNTA_FLECHA+(SEPARACION_BRILLO_FLECHA/2), avanceBrilloFlecha,brilloFlechaOcho);

        //Brillo Flecha 3
        moverBrilloaCoordenada(X_PUNTA_FLECHA_DER +(SEPARACION_BRILLO_FLECHA/2),
                Y_PUNTA_FLECHA-(SEPARACION_BRILLO_FLECHA/2), avanceBrilloFlecha,brilloFlechaSiete);


        //Brillo Flecha 4
        moverBrilloaCoordenada(X_PUNTA_FLECHA_DER + (SEPARACION_BRILLO_FLECHA),
                Y_PUNTA_FLECHA+(SEPARACION_BRILLO_FLECHA), avanceBrilloFlecha,brilloFlechaCinco);

        //Brillo Flecha 5
        moverBrilloaCoordenada(X_PUNTA_FLECHA_DER + (SEPARACION_BRILLO_FLECHA),
                Y_PUNTA_FLECHA, avanceBrilloFlecha,brilloFlechaTres);

        //Brillo Flecha 6
        moverBrilloaCoordenada(X_PUNTA_FLECHA_DER + (SEPARACION_BRILLO_FLECHA),
                Y_PUNTA_FLECHA-(SEPARACION_BRILLO_FLECHA), avanceBrilloFlecha,brilloFlechaUno);

        //Brillo Flecha 7
        moverBrilloaCoordenada(X_PUNTA_FLECHA_DER + (SEPARACION_BRILLO_FLECHA*2),
                Y_PUNTA_FLECHA, avanceBrilloFlecha,brilloFlechaSeis);

        //Brillo Flecha 8
        moverBrilloaCoordenada(X_PUNTA_FLECHA_DER + (SEPARACION_BRILLO_FLECHA*3),
                Y_PUNTA_FLECHA, avanceBrilloFlecha,brilloFlechaDos);
    }

    private void dibujarCristales() {

        switch (cristalColor) {
            case 0:
                cristalW.animationRender(batch, Tiempo);
                batch.draw(texturaInicial,ANCHO*3/2-400,ALTO/2-250);
                break;
            case 1:
                cristalR.animationRender(batch, Tiempo);
                batch.draw(texturaAndrea,ANCHO*3/2-400,ALTO/2-250);
                break;
            case 2:
                cristalY.animationRender(batch, Tiempo);
                batch.draw(texturaDavid,ANCHO*3/2-400,ALTO/2-250);
                break;
            case 3:
                cristalG.animationRender(batch, Tiempo);
                batch.draw(texturaCarlos,ANCHO*3/2-400,ALTO/2-250);
                break;
            case 4:
                cristalC.animationRender(batch, Tiempo);
                batch.draw(texturaEduardo,ANCHO*3/2-400,ALTO/2-270);
                break;
            case 5:
                cristalB.animationRender(batch, Tiempo);
                batch.draw(texturaRicardo,ANCHO*3/2-400,ALTO/2-270);
                break;
            default :
                cristalW.animationRender(batch, Tiempo);
                break;
        }
    }

    private void recorrerPantalla() {
        //Si se oprimen los botones para deslizar la pantalla comienza a cambiar la posición
        if (mover!=0) {

            if (mover==1){
                if (camara.position.x <ANCHO*1.5f) {
                    camara.position.x += avancePantalla;
                } else {mover=0;}
            } else {
                if (camara.position.x >ANCHO/2) {
                    camara.position.x -= avancePantalla;
                } else {mover=0;}
            }
            camara.update();
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
       //manager.unload("Nosotros/Androidst.png");
        manager.unload("Botones/btnTransparente.png");
        manager.unload("Botones/btnTransparenteVertical.png");
        manager.unload("Nosotros/fondoAbout.jpg");
        manager.unload("Menu/buttonback.png");
        manager.unload("Menu/clickback.png");
        manager.unload("Nosotros/btnFlechaDer_OFF.png");
        manager.unload("Nosotros/btnFlechaDer_ON.png");
        manager.unload("Nosotros/btnFlechaIzq_OFF.png");
        manager.unload("Nosotros/btnFlechaIzq_ON.png");
        manager.unload("Nosotros/btnFlechaArriba_OFF.png");
        manager.unload("Nosotros/btnFlechaArriba_ON.png");
        manager.unload("Nosotros/Cristales-W.png");
        manager.unload("Nosotros/Cristales-G.png");
        manager.unload("Nosotros/Cristales-R.png");
        manager.unload("Nosotros/Cristales-B.png");
        manager.unload("Nosotros/Cristales-Y.png");
        manager.unload("Nosotros/Cristales-C.png");
        manager.unload("Nosotros/andrea.png");
        manager.unload("Nosotros/carlos.png");
        manager.unload("Nosotros/david.png");
        manager.unload("Nosotros/eduardo.png");
        manager.unload("Nosotros/ricardo.png");
        manager.unload("Nosotros/CasoInicial.png");

    }
}
