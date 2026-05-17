package puppy.code;

import com.badlogic.gdx.graphics.Texture;

public class GotaMala extends ObjetoCaida {

    public GotaMala(Texture textura, float x, float y) {
        super(textura, x, y); 
    }

    @Override
    protected void aplicarEfectoColision(Tarro tarro) {
        tarro.dañar(); 
        this.setActivo(false); 
    }
}