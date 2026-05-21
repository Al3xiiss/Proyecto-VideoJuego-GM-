package puppy.code;

import com.badlogic.gdx.graphics.Texture;

public class Lava extends ObjetoCaida {

    public Lava(Texture textura, float x, float y) {
        super(textura, x, y); 
    }

    @Override
    protected void aplicarEfectoColision(Aldeano aldeano) {
        aldeano.dañar(); 
        this.setActivo(false); 
    }
}