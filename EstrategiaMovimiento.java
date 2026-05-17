package puppy.code;

import com.badlogic.gdx.math.Rectangle;

public interface EstrategiaMovimiento {
    // Todas las estrategias de vuelo deberán programar cómo mover la hitbox
    void mover(Rectangle hitbox, float velocidad, float deltaTime);
}