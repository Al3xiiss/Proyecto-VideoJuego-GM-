package puppy.code;

import com.badlogic.gdx.math.Rectangle;

public class MovimientoRecto implements EstrategiaMovimiento {
    @Override
    public void mover(Rectangle hitbox, float velocidad, float deltaTime) {
        // Cae en línea recta hacia abajo
        hitbox.y -= velocidad * deltaTime; 
    }
}