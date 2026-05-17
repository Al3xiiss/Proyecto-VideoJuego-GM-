package puppy.code;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public abstract class ObjetoCaida implements Colisionable {
    private Rectangle hitbox;
    private Texture textura;
    private boolean activo; 
    protected EstrategiaMovimiento estrategia; 

    public ObjetoCaida(Texture textura, float x, float y) {
        this.textura = textura;
        this.hitbox = new Rectangle(x, y, 64, 64);
        this.activo = true; 
        this.estrategia = new MovimientoRecto(); 
    }

    // GM-8: TEMPLATE METHOD (Plantilla del ciclo de vida del objeto)
    public final void procesarFrame(float velocidad, float deltaTime, Tarro tarro) {
        if (!activo) return; // Si ya no está activo, ignoramos todo
        
        // Paso 1 de la plantilla: Moverse (Delega al Strategy GM-7)
        estrategia.mover(hitbox, velocidad, deltaTime);
        
        // Paso 2 de la plantilla: Verificar límites (si toca el suelo)
        if (hitbox.y + 64 < 0) {
            activo = false;
        }
        
        // Paso 3 de la plantilla: Verificar colisión con el tarro
        if (hitbox.overlaps(tarro.getArea())) {
            aplicarEfectoColision(tarro); // Este paso lo definen las hijas
        }
    }

    // Método que las clases hijas están obligadas a programar
    protected abstract void aplicarEfectoColision(Tarro tarro);

    public void dibujar(SpriteBatch batch) {
        if (activo) {
            batch.draw(textura, hitbox.x, hitbox.y);
        }
    }

    public void setEstrategia(EstrategiaMovimiento nuevaEstrategia) {
        this.estrategia = nuevaEstrategia;
    }

    public Rectangle getHitbox() { return hitbox; }
    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }
    
    // Dejamos este vacío por defecto para cumplir con la interfaz original si se necesita
    @Override
    public void chocarConTarro(Tarro tarro) {}
}