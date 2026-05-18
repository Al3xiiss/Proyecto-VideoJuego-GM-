package puppy.code;                                                                                  
                                                                                                     
import com.badlogic.gdx.Gdx;                                                                         
import com.badlogic.gdx.audio.Music;                                                                 
import com.badlogic.gdx.audio.Sound;                                                                 
import com.badlogic.gdx.graphics.Texture;                                                            
import com.badlogic.gdx.graphics.g2d.SpriteBatch;                                                    
import com.badlogic.gdx.math.MathUtils;                                                              
import com.badlogic.gdx.math.Rectangle; 
import com.badlogic.gdx.utils.Array;                                                                 
import com.badlogic.gdx.utils.TimeUtils;                                                             
                                                                                                     
public class Lluvia {                                                                                
    private Array<ObjetoCaida> gotas;                                                                
    private long lastDropTime;                                                                       
    private Texture texturaGotaBuena;                                                                
    private Texture texturaGotaMala;                                                                 
    private Sound dropSound;                                                                         
    private Music rainMusic;  
    private ConfiguracionNivel configNivel;
                                                                                                     
    public Lluvia(Texture gotaBuena, Texture gotaMala, Sound ss, Music mm) {                         
        rainMusic = mm;                                                                              
        dropSound = ss;                                                                              
        this.texturaGotaBuena = gotaBuena;                                                           
        this.texturaGotaMala = gotaMala;                                                             
    }                                                                                                
                                                                                                     
    public void crear() {
        gotas = new Array<ObjetoCaida>();
        
        // GM-9: Uso del patrón Builder para establecer la configuración inicial
        configNivel = new NivelBuilder()
                        .setVelocidadCaida(225f)
                        .setProbabilidadGotaMala(2) 
                        .build();

        crearGotaDeLluvia();
        rainMusic.stop();
        rainMusic.setLooping(true);
        rainMusic.play();
    }                                                                                               
                                                                                                     
    private void crearGotaDeLluvia() {
        float x = 0;
        // Las gotas nacen en 864 para entrar de forma fluida desde el nuevo techo (800)
        float y = 864; 
        
        boolean posicionValida = false;
        int intentos = 0;
        
        // Hitbox fantasma para verificar que el espacio esté despejado antes de instanciar
        Rectangle futuraHitbox = new Rectangle(0, y, 64, 64);
        
        while (!posicionValida && intentos < 10) {
            x = MathUtils.random(0, 800 - 64);
            futuraHitbox.x = x;
            posicionValida = true; 
            
            for (int i = 0; i < gotas.size; i++) {
                ObjetoCaida gotaExistente = gotas.get(i);
                
                // Solo evaluamos las gotas que van recién naciendo en la parte superior
                if (gotaExistente.getHitbox().y > 800) { 
                    if (futuraHitbox.overlaps(gotaExistente.getHitbox())) {
                        posicionValida = false; 
                        break; 
                    }
                }
            }
            intentos++;
        }
        
        // Decisión aleatoria basada en la configuración del Builder
        if (MathUtils.random(1, 10) <= configNivel.getProbabilidadGotaMala()) {
            ObjetoCaida gotaMala = new GotaMala(texturaGotaMala, x, y);
            // GM-7: Inyección dinámica de la estrategia de movimiento en zigzag
            gotaMala.setEstrategia(new MovimientoZigZag()); 
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

        // GM-6: Acceso global al Singleton para calcular la dificultad progresiva
        int puntosActuales = AdministradorJuego.getInstance().getPuntos();
        float velocidadAumentada = configNivel.getVelocidadCaida() + (puntosActuales * 1);

        for (int i = 0; i < gotas.size; i++) {
            ObjetoCaida gota = gotas.get(i);
            
            // GM-8: Llamada al Template Method para procesar el ciclo de vida del objeto
            gota.procesarFrame(velocidadAumentada, Gdx.graphics.getDeltaTime(), tarro);

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
    public void detenerMusica() {
        if (rainMusic.isPlaying()) {
            rainMusic.stop();
        }
    }
                                                                                                     
    public void destruir() {                                                                         
        dropSound.dispose();                                                                         
        rainMusic.dispose();                                                                         
    }                                                                                                
}