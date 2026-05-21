package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;

public class Aldeano {
	private Rectangle hitbox;
	private Texture imagenAldeano;
	private Sound sonidoHerido;
	private int velx = 400;
	private boolean herido = false;
	private int tiempoHeridoMax = 50;
	private int tiempoHerido;

	public Aldeano(Texture tex, Sound ss) {
		imagenAldeano = tex;
		sonidoHerido = ss;
	}

	public int getVidas() {
		return AdministradorJuego.getInstance().getVidas();
	}

	public int getPuntos() {
		return AdministradorJuego.getInstance().getPuntos();
	}

	public Rectangle getArea() {
		return hitbox;
	}

	public void sumarPuntos(int pp) {
		AdministradorJuego.getInstance().sumarPuntos(pp);
	}

	public void crear() {
		hitbox = new Rectangle();
		hitbox.x = 800 / 2 - 64 / 2;
		hitbox.y = 20;
		hitbox.width = 64;
		hitbox.height = 64;
	}

	public void dañar() {
		AdministradorJuego.getInstance().restarVida();
		herido = true;
		tiempoHerido = tiempoHeridoMax;
		sonidoHerido.play();
	}

	public void dibujar(SpriteBatch batch) {
		if (!herido)
			batch.draw(imagenAldeano, hitbox.x, hitbox.y);
		else {
			batch.draw(imagenAldeano, hitbox.x, hitbox.y + MathUtils.random(-5, 5));
			tiempoHerido--;
			if (tiempoHerido <= 0)
				herido = false;
		}
	}

	public void actualizarMovimiento() {
		if (Gdx.input.isKeyPressed(Input.Keys.LEFT))
			hitbox.x -= velx * Gdx.graphics.getDeltaTime();
		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT))
			hitbox.x += velx * Gdx.graphics.getDeltaTime();
		
		if (hitbox.x < 0)
			hitbox.x = 0;
		if (hitbox.x > 800 - 64)
			hitbox.x = 800 - 64;
	}

	public void destruir() {
		imagenAldeano.dispose();
	}

	public boolean estaHerido() {
		return herido;
	}
}