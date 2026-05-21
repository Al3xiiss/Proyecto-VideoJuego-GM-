package puppy.code;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

public class Esmeralda extends ObjetoCaida {
    private Sound sonidoRecoleccion;

    public Esmeralda(Texture textura, float x, float y, Sound sonido) {
        super(textura, x, y); 
        this.sonidoRecoleccion = sonido;
    }

    @Override
    protected void aplicarEfectoColision(Aldeano aldeano) {
        aldeano.sumarPuntos(10);
        sonidoRecoleccion.play(0.1f);
        this.setActivo(false); 
    }
}