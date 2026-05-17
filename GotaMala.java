package puppy.code;

import com.badlogic.gdx.graphics.Texture;

public class GotaMala extends ObjetoCaida {

    public GotaMala(Texture textura, float x, float y) {
        super(textura, x, y); // Llama al constructor de la clase abstracta padre
    }

    @Override
    public void chocarConTarro(Tarro tarro) {
        // Verificamos el booleano para asegurarnos de que la gota aún está activa
        if (this.isActivo()) {
            tarro.dañar(); // Le avisa al tarro que debe restar vidas y sonar
            this.setActivo(false); // Cambiamos el estado de la gota para que desaparezca
        }
    }
}