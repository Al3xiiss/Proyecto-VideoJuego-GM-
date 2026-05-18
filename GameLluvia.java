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

public class GameLluvia extends ApplicationAdapter {
	private SpriteBatch batch;
	private BitmapFont font;
	private OrthographicCamera camera;
	
	private Tarro tarro;
	private Lluvia lluvia;
	private Texture fondo;

	// Variables de control de estado
	private boolean enMenu = true;
	private boolean juegoGanado = false; // <-- NUEVA VARIABLE DE VICTORIA

	@Override
	public void create () {
		font = new BitmapFont(); 
		font.getData().setScale(1.5f);
		 
		Sound hurtSound = Gdx.audio.newSound(Gdx.files.internal("DañoAldeano.ogg"));
		tarro = new Tarro(new Texture(Gdx.files.internal("aldeanoo.png")), hurtSound);
         
		Texture gota = new Texture(Gdx.files.internal("esmeralda.png")); 
		Texture gotaMala = new Texture(Gdx.files.internal("lava.png"));
         
		Sound dropSound = Gdx.audio.newSound(Gdx.files.internal("AldeanoFeliz.ogg"));
		Music rainMusic = Gdx.audio.newMusic(Gdx.files.internal("SonidoFondo.mp3"));
		lluvia = new Lluvia(gota, gotaMala, dropSound, rainMusic);
	      
		fondo = new Texture(Gdx.files.internal("fondo.png"));

		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 800);
		batch = new SpriteBatch();
		
		tarro.crear();
		lluvia.crear();
	}

	@Override
	public void render () {
		ScreenUtils.clear(0, 0, 0, 1);
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		
		// Dibujamos el fondo 
		batch.draw(fondo, 0, 0, 800, 800);
		
		// MÁQUINA DE ESTADOS
		if (enMenu) {
			// --- ESTADO 1: MENÚ PRINCIPAL ---
			font.draw(batch, "MINECRAFT: EVADE LA LAVA", 800/2 - 160, 800/2 + 60);
			font.draw(batch, "Meta: Consigue 1100 puntos para ganar", 800/2 - 200, 800/2 + 10);
			font.draw(batch, "Presiona ESPACIO para comenzar", 800/2 - 175, 800/2 - 40);
			
			if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
				enMenu = false;
				juegoGanado = false; // Nos aseguramos de reiniciar el estado
				AdministradorJuego.getInstance().iniciarNuevoJuego();
				tarro.crear();
				lluvia.crear();
			}
			
		} else if (juegoGanado) {
			// --- ESTADO 2: PANTALLA DE VICTORIA ---
			font.draw(batch, "¡VICTORIA!", 800/2 - 60, 800/2 + 80);
			font.draw(batch, "¡SALVASTE AL ALDEANO!", 800/2 - 140, 800/2 + 20);
			font.draw(batch, "Puntuación final: " + AdministradorJuego.getInstance().getPuntos(), 800/2 - 110, 800/2 - 20);
			font.draw(batch, "Presiona ENTER para jugar de nuevo", 800/2 - 180, 800/2 - 80);
			font.draw(batch, "Presiona ESCAPE para volver al Menú", 800/2 - 180, 800/2 - 130);
			
			// Reiniciar directo
			if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
				juegoGanado = false;
				AdministradorJuego.getInstance().iniciarNuevoJuego();
				tarro.crear();
				lluvia.crear();
			}
			// Regresar al menú
			if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
				juegoGanado = false;
				enMenu = true;
			}

		} else if (AdministradorJuego.getInstance().isJuegoTerminado()) {
			// --- ESTADO 3: GAME OVER (DERROTA) ---
			lluvia.detenerMusica(); // Apaga la música ambiental al perder
			font.draw(batch, "GAME OVER", 800/2 - 60, 800/2 + 80);
			font.draw(batch, "Esmeraldas conseguidas: " + AdministradorJuego.getInstance().getPuntos(), 800/2 - 140, 800/2 + 20);
			font.draw(batch, "Presiona ENTER para reintentar", 800/2 - 160, 800/2 - 40);
			font.draw(batch, "Presiona ESCAPE para volver al Menú", 800/2 - 180, 800/2 - 100);
			
			if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
				AdministradorJuego.getInstance().iniciarNuevoJuego();
				tarro.crear();
				lluvia.crear();
			}
			if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
				enMenu = true;
			}
			
		} else {
			// --- ESTADO 4: JUEGO ACTIVO ---
			font.draw(batch, "Puntos: " + AdministradorJuego.getInstance().getPuntos() + " / 1100", 20, 775);
			font.draw(batch, "Vidas: " + AdministradorJuego.getInstance().getVidas(), 680, 775);
			
			if (!tarro.estaHerido()) {
				tarro.actualizarMovimiento();        
				lluvia.actualizarMovimiento(tarro);	   
			}
			tarro.dibujar(batch);
			lluvia.actualizarDibujoLluvia(batch);

			// COMPROBACIÓN DE CONDICIÓN DE VICTORIA
			if (AdministradorJuego.getInstance().getPuntos() >= 1100) {
				juegoGanado = true; // El próximo frame saltará a la pantalla de victoria
				lluvia.detenerMusica(); // Apaga la música ambiental inmediatamente al ganar
			}
		}
		
		batch.end();	
	}

	@Override
	public void dispose () {
		tarro.destruir();
		lluvia.destruir();
		batch.dispose();
		font.dispose();
		fondo.dispose();
	}
}