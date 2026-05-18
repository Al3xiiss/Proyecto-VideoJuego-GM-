package puppy.code;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

public class GotaBuena extends ObjetoCaida {
    private Sound sonidoRecoleccion;

    public GotaBuena(Texture textura, float x, float y, Sound sonido) {
        super(textura, x, y); 
        this.sonidoRecoleccion = sonido;
    }

    @Override
    protected void aplicarEfectoColision(Tarro tarro) {
        tarro.sumarPuntos(10);
        sonidoRecoleccion.play(0.1f);
        this.setActivo(false); 
    }
}