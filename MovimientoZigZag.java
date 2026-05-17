package puppy.code;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;

public class MovimientoZigZag implements EstrategiaMovimiento {
    private float tiempo = 0;

    @Override
    public void mover(Rectangle hitbox, float velocidad, float deltaTime) {
        hitbox.y -= velocidad * deltaTime; // Cae hacia abajo
        tiempo += deltaTime;
        
        // Le sumamos un movimiento horizontal usando la función seno para el zigzag
        hitbox.x += MathUtils.sin(tiempo * 5) * 150 * deltaTime;
        
        // Evitamos que la gota se salga de los bordes laterales
        if (hitbox.x < 0) hitbox.x = 0;
        if (hitbox.x > 800 - 64) hitbox.x = 800 - 64;
    }
}