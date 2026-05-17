package puppy.code;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public abstract class ObjetoCaida implements Colisionable {
    // GM-5: Encapsulamiento (atributos privados)
    private Rectangle hitbox;
    private Texture textura;
    private boolean activo; // Usamos un booleano para saber si la gota debe seguir en pantalla

    public ObjetoCaida(Texture textura, float x, float y) {
        this.textura = textura;
        this.hitbox = new Rectangle(x, y, 64, 64);
        this.activo = true; 
    }

    // Logica comun para cualquier objeto que caiga
    public void caer(float velocidad, float deltaTime) {
        hitbox.y -= velocidad * deltaTime;
        // Si toca el suelo, cambiamos el booleano para desactivarlo
        if (hitbox.y + 64 < 0) {
            activo = false;
        }
    }

    public void dibujar(SpriteBatch batch) {
        if (activo) {
            batch.draw(textura, hitbox.x, hitbox.y);
        }
    }

    // Getters y setters para acceder a los datos de forma segura
    public Rectangle getHitbox() {
        return hitbox;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
}