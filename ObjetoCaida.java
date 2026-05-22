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

    // GM-8: TEMPLATE METHOD
    public final void procesarFrame(float velocidad, float deltaTime, Aldeano aldeano) {
        if (!activo) return; 
        
        estrategia.mover(hitbox, velocidad, deltaTime);
        
        if (hitbox.y + 64 < 0) {
            activo = false;
        }
        
        if (hitbox.overlaps(aldeano.getArea())) {
            aplicarEfectoColision(aldeano); 
        }
    }
    
    // Paso protegido: Obliga a las hijas a definir que pasa al chocar
    protected abstract void aplicarEfectoColision(Aldeano aldeano);

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
    
    @Override
    public void chocarConAldeano(Aldeano aldeano) {}
}