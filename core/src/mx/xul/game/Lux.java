package mx.xul.game;
// // Administra la carga de los assets del juego
// Autor: Ricardo Solis y Carlos Arroyo
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

import mx.xul.game.pantallaBienvenida.PantallaBienvenida;

public class Lux extends Game {

	//Musica
	private Music musicaPantallasSecundarias;
	private Music musicaPantallasSecundariasIntro;
	private final float VOLUMEN_DESEADO = 0.7f;
	private float volumenMusica=VOLUMEN_DESEADO;
	private float tiempoJugando =0;// Acumulador para saber cuanto tiempo se tardó pasar el juego.

	private final AssetManager assetManager = new AssetManager();

	@Override
	public void create() {
		crearMusica();
		// Hace que muestre la primer pantalla
		setScreen(new PantallaBienvenida(this));
		//setScreen(new PantallaMenu(this)); // Primera pantalla visible
	}

	private void crearMusica() {
		musicaPantallasSecundarias = Gdx.audio.newMusic(Gdx.files.internal("Sonidos/musicaMenuLoop.mp3"));
		musicaPantallasSecundarias.setLooping(true);
		musicaPantallasSecundarias.setVolume(VOLUMEN_DESEADO);

		musicaPantallasSecundariasIntro = Gdx.audio.newMusic(Gdx.files.internal("Sonidos/musicaMenuIntro.mp3"));
		musicaPantallasSecundariasIntro.setVolume(VOLUMEN_DESEADO);
	}

	public void playMusica(){
		volumenMusica = VOLUMEN_DESEADO;
		//System.out.println(musicaPantallasSecundariasIntro.isPlaying());
		//System.out.println(musicaPantallasSecundarias.isPlaying());
		if(musicaPantallasSecundariasIntro.isPlaying()==false && musicaPantallasSecundarias.isPlaying()==false){
			//System.out.println("reproducir");
			musicaPantallasSecundariasIntro.play();
			musicaPantallasSecundariasIntro.setVolume(volumenMusica);
			musicaPantallasSecundariasIntro.setOnCompletionListener(new Music.OnCompletionListener() {
				@Override
				public void onCompletion(Music music) {
					musicaPantallasSecundarias.play();
					musicaPantallasSecundarias.setVolume(volumenMusica);
				}
			});

		}


	}
	public void fadeOutMusica(){
		if (musicaPantallasSecundarias.isPlaying()){
			System.out.println(musicaPantallasSecundarias.getVolume());
			if (volumenMusica>0){
				volumenMusica -= 0.04f;
			}else {volumenMusica=0;}
			musicaPantallasSecundarias.setVolume(volumenMusica);
		}

		if (musicaPantallasSecundariasIntro.isPlaying()){
			System.out.println(musicaPantallasSecundariasIntro.getVolume());
			if (volumenMusica>0){
				volumenMusica -= 0.04f;
			}else {volumenMusica=0;}
			musicaPantallasSecundariasIntro.setVolume(volumenMusica);
		}

	}
	public void stopMusica(){
		musicaPantallasSecundarias.stop();
		musicaPantallasSecundariasIntro.stop();
	}

	public void inicializarCuentaSegundos(){
		tiempoJugando =0;
	}

	public void incrementarCuentaSegundos(float delta){
		tiempoJugando +=delta;
	}

	public String getCuentaSegundos(){
		//String string = Float.toString(tiempoJugando);;
		return String.format("%.2f",tiempoJugando);
	}

	// Método accesor de assetManager
	public AssetManager getAssetManager() {

		return assetManager;
	}

	public void dispose() {
		super.dispose();
		assetManager.clear();

	}
}
