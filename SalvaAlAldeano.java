package puppy.code;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class SalvaAlAldeano extends ApplicationAdapter {
	private SpriteBatch batch;
	private BitmapFont font;
	private OrthographicCamera camera;
	
	private Aldeano aldeano;
	private GestorCaida gestorCaida;
	private Texture fondo;

	private boolean enMenu = true;
	private boolean juegoGanado = false; 

	@Override
	public void create () {
		font = new BitmapFont(); 
		font.getData().setScale(1.5f);
		 
		Sound hitAldeano = Gdx.audio.newSound(Gdx.files.internal("hitAldeano.ogg"));
		aldeano = new Aldeano(new Texture(Gdx.files.internal("aldeanoo.png")), hitAldeano);
         
		Texture esmeralda = new Texture(Gdx.files.internal("esmeralda.png")); 
		Texture lava = new Texture(Gdx.files.internal("lava.png"));
         
		Sound aldeanoFeliz = Gdx.audio.newSound(Gdx.files.internal("AldeanoFeliz.ogg"));
		Music ambiente = Gdx.audio.newMusic(Gdx.files.internal("SonidoFondo.mp3"));
		gestorCaida = new GestorCaida(esmeralda, lava, aldeanoFeliz, ambiente);
	      
		fondo = new Texture(Gdx.files.internal("fondo.png"));

		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 800);
		batch = new SpriteBatch();
		
		aldeano.crear();
		gestorCaida.crear();
	}

	@Override
	public void render () {
		ScreenUtils.clear(0, 0, 0, 1);
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		
		batch.draw(fondo, 0, 0, 800, 800);
		
		if (enMenu) {
			font.draw(batch, "MINECRAFT: EVADE LA LAVA", 800/2 - 160, 800/2 + 60);
			font.draw(batch, "Meta: Consigue 1100 puntos para ganar", 800/2 - 200, 800/2 + 10);
			font.draw(batch, "Presiona ESPACIO para comenzar", 800/2 - 175, 800/2 - 40);
			
			if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
				enMenu = false;
				juegoGanado = false; 
				AdministradorJuego.getInstance().iniciarNuevoJuego();
				aldeano.crear();
				gestorCaida.crear();
			}
			
		} else if (juegoGanado) {
			font.draw(batch, "¡VICTORIA!", 800/2 - 60, 800/2 + 80);
			font.draw(batch, "¡SALVASTE AL ALDEANO!", 800/2 - 140, 800/2 + 20);
			font.draw(batch, "Puntuación final: " + AdministradorJuego.getInstance().getPuntos(), 800/2 - 110, 800/2 - 20);
			font.draw(batch, "Presiona ENTER para jugar de nuevo", 800/2 - 180, 800/2 - 80);
			font.draw(batch, "Presiona ESCAPE para volver al Menú", 800/2 - 180, 800/2 - 130);
			
			if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
				juegoGanado = false;
				AdministradorJuego.getInstance().iniciarNuevoJuego();
				aldeano.crear();
				gestorCaida.crear();
			}
			if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
				juegoGanado = false;
				enMenu = true;
			}

		} else if (AdministradorJuego.getInstance().isJuegoTerminado()) {
			gestorCaida.detenerMusica(); 
			font.draw(batch, "GAME OVER", 800/2 - 60, 800/2 + 80);
			font.draw(batch, "Esmeraldas conseguidas: " + AdministradorJuego.getInstance().getPuntos(), 800/2 - 140, 800/2 + 20);
			font.draw(batch, "Presiona ENTER para reintentar", 800/2 - 160, 800/2 - 40);
			font.draw(batch, "Presiona ESCAPE para volver al Menú", 800/2 - 180, 800/2 - 100);
			
			if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
				AdministradorJuego.getInstance().iniciarNuevoJuego();
				aldeano.crear();
				gestorCaida.crear();
			}
			if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
				enMenu = true;
			}
			
		} else {
			font.draw(batch, "Puntos: " + AdministradorJuego.getInstance().getPuntos() + " / 1100", 20, 775);
			font.draw(batch, "Vidas: " + AdministradorJuego.getInstance().getVidas(), 680, 775);
			
			if (!aldeano.estaHerido()) {
				aldeano.actualizarMovimiento();        
				gestorCaida.actualizarMovimiento(aldeano);	   
			}
			aldeano.dibujar(batch);
			gestorCaida.actualizarDibujo(batch);

			if (AdministradorJuego.getInstance().getPuntos() >= 1100) {
				juegoGanado = true; 
				gestorCaida.detenerMusica(); 
			}
		}
		
		batch.end();	
	}

	@Override
	public void dispose () {
		aldeano.destruir();
		gestorCaida.destruir();
		batch.dispose();
		font.dispose();
		fondo.dispose();
	}
}
