package puppy.code;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

public class GotaBuena extends ObjetoCaida {
    private Sound sonidoRecoleccion;

    public GotaBuena(Texture textura, float x, float y, Sound sonido) {
        super(textura, x, y); // Llama al constructor de la clase abstracta padre
        this.sonidoRecoleccion = sonido;
    }

    @Override
    public void chocarConTarro(Tarro tarro) {
        if (this.isActivo()) {
            tarro.sumarPuntos(10);
            sonidoRecoleccion.play();
            this.setActivo(false); // Cambiamos el booleano porque ya la atrapamos
        }
    }
}