package puppy.code;                                                                                  
                                                                                                     
import com.badlogic.gdx.Gdx;                                                                         
import com.badlogic.gdx.audio.Music;                                                                 
import com.badlogic.gdx.audio.Sound;                                                                 
import com.badlogic.gdx.graphics.Texture;                                                            
import com.badlogic.gdx.graphics.g2d.SpriteBatch;                                                    
import com.badlogic.gdx.math.MathUtils;                                                              
import com.badlogic.gdx.utils.Array;                                                                 
import com.badlogic.gdx.utils.TimeUtils;                                                             
                                                                                                     
public class Lluvia {                                                                                
    // lista de tipo ObjetoCaida                             
    private Array<ObjetoCaida> gotas;                                                                
    private long lastDropTime;                                                                       
    private Texture texturaGotaBuena;                                                                
    private Texture texturaGotaMala;                                                                 
    private Sound dropSound;                                                                         
    private Music rainMusic;                                                                         
                                                                                                     
    public Lluvia(Texture gotaBuena, Texture gotaMala, Sound ss, Music mm) {                         
        rainMusic = mm;                                                                              
        dropSound = ss;                                                                              
        this.texturaGotaBuena = gotaBuena;                                                           
        this.texturaGotaMala = gotaMala;                                                             
    }                                                                                                
                                                                                                     
    public void crear() {                                                                            	
        gotas = new Array<ObjetoCaida>();                                                            
        crearGotaDeLluvia();                                                                         
        // Iniciar la música de fondo                                                                
        rainMusic.setLooping(true);                                                                  
        rainMusic.play();                                                                            
    }                                                                                                
                                                                                                     
    private void crearGotaDeLluvia() {
        float x = MathUtils.random(0, 800 - 64);
        float y = 480;
        
        if (MathUtils.random(1, 10) < 3) {
            ObjetoCaida gotaMala = new GotaMala(texturaGotaMala, x, y);
            gotaMala.setEstrategia(new MovimientoZigZag()); // nueva estrategia
            gotas.add(gotaMala);
        } else {
            gotas.add(new GotaBuena(texturaGotaBuena, x, y, dropSound));
        }
        lastDropTime = TimeUtils.nanoTime();
    }                                                     
                                                                                                
                                                                                                     
    public void actualizarMovimiento(Tarro tarro) {
        if (TimeUtils.nanoTime() - lastDropTime > 100000000) {
            crearGotaDeLluvia();
        }

        for (int i = 0; i < gotas.size; i++) {
            ObjetoCaida gota = gotas.get(i);
            
            // LLAMADA AL TEMPLATE METHOD
            // La gota se mueve, revisa el suelo y revisa si chocó con el tarro ella sola
            gota.procesarFrame(300, Gdx.graphics.getDeltaTime(), tarro);

            if (!gota.isActivo()) {
                gotas.removeIndex(i);
                i--; 
            }
        }
    }                                                                                                
                                                                                                     
    public void actualizarDibujoLluvia(SpriteBatch batch) {                                          
        for (int i = 0; i < gotas.size; i++) {                                                       
            gotas.get(i).dibujar(batch);                                                             
        }                                                                                            
    }                                                                                                
                                                                                                     
    public void destruir() {                                                                         
        dropSound.dispose();                                                                         
        rainMusic.dispose();                                                                         
    }                                                                                                
}                                                                                                    












