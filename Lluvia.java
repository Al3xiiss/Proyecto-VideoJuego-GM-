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
                                                                                                     
        // Un 20% de probabilidad de generar una gota mala (números 1 o 2 de 10)                     
        if (MathUtils.random(1, 10) < 3) {                                                           
            gotas.add(new GotaMala(texturaGotaMala, x, y));                                          
        } else {                                                                                     
            gotas.add(new GotaBuena(texturaGotaBuena, x, y, dropSound));                             
        }                                                                                            
        lastDropTime = TimeUtils.nanoTime();                                                         
    }                                                                                                
                                                                                                     
    public void actualizarMovimiento(Tarro tarro) {                                                  
        // Generar nuevas gotas de lluvia cada cierto tiempo                                         
        if (TimeUtils.nanoTime() - lastDropTime > 100000000) {                                       
            crearGotaDeLluvia();                                                                     	
        }                                                                                            
                                                                                                     
        // Iterar sobre las gotas                                                                    
        for (int i = 0; i < gotas.size; i++) {                                                       
            ObjetoCaida gota = gotas.get(i);                                                         
                                                                                                     
            // Le decimos a la gota que caiga (le pasamos velocidad y deltaTime)                     
            gota.caer(300, Gdx.graphics.getDeltaTime());                                             
                                                                                                     
            // Si choca con el tarro, ejecutamos el método de la interfaz                            
            if (gota.getHitbox().overlaps(tarro.getArea())) {                                        
                gota.chocarConTarro(tarro);                                                          
            }                                                                                        
                                                                                                     
            // Revisar el estado lógico para saber si debemos borrarla de la lista                   
            // (Ya sea porque chocó contra el piso o porque el tarro la atrapó)                      
            if (!gota.isActivo()) {                                                                  
                gotas.removeIndex(i);                                                                
                i--; // Importante: restar 1 a 'i' para no saltarnos la siguiente gota al borrar esta
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












