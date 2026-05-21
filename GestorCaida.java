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
                                                                                                     
public class GestorCaida {                                                                                
    private Array<ObjetoCaida> objetos;                                                                
    private long lastDropTime;                                                                       
    private Texture texturaEsmeralda;                                                                
    private Texture texturaLava;                                                                 
    private Sound sonidoEsmeralda;                                                                         
    private Music musicaFondo;  
    private ConfiguracionNivel configNivel;
                                                                                                     
    public GestorCaida(Texture texturaEsmeralda, Texture texturaLava, Sound ss, Music mm) {                         
        this.musicaFondo = mm;                                                                              
        this.sonidoEsmeralda = ss;                                                                              
        this.texturaEsmeralda = texturaEsmeralda;                                                           
        this.texturaLava = texturaLava;                                                             
    }                                                                                                
                                                                                                     
    public void crear() {
        objetos = new Array<ObjetoCaida>();
        
        // GM-9: Uso del patrón Builder 
        configNivel = new NivelBuilder()
                        .setVelocidadCaida(225f)
                        .setProbabilidadLava(2) 
                        .build();

        crearObjetoCaida();
        musicaFondo.stop();
        musicaFondo.setLooping(true);
        musicaFondo.setVolume(0.8f);
        musicaFondo.play();
    }                                                                                               
                                                                                                     
    private void crearObjetoCaida() {
        float x = 0;
        float y = 864; 
        
        boolean posicionValida = false;
        int intentos = 0;
        
        Rectangle futuraHitbox = new Rectangle(0, y, 64, 64);
        
        while (!posicionValida && intentos < 10) {
            x = MathUtils.random(0, 800 - 64);
            futuraHitbox.x = x;
            posicionValida = true; 
            
            for (int i = 0; i < objetos.size; i++) {
                ObjetoCaida objetoExistente = objetos.get(i);
                
                if (objetoExistente.getHitbox().y > 800) { 
                    if (futuraHitbox.overlaps(objetoExistente.getHitbox())) {
                        posicionValida = false; 
                        break; 
                    }
                }
            }
            intentos++;
        }
        
        // Decisión aleatoria basada en la configuración del Builder
        if (MathUtils.random(1, 10) <= configNivel.getProbabilidadLava()) {
            ObjetoCaida lava = new Lava(texturaLava, x, y);
            // GM-7: Inyección dinámica de la estrategia
            lava.setEstrategia(new MovimientoZigZag()); 
            objetos.add(lava);
        } else {
            objetos.add(new Esmeralda(texturaEsmeralda, x, y, sonidoEsmeralda));
        }
        lastDropTime = TimeUtils.nanoTime();
    }                                                     
                                                                                                
    public void actualizarMovimiento(Aldeano aldeano) {
        if (TimeUtils.nanoTime() - lastDropTime > 100000000) {
            crearObjetoCaida();
        }

        // GM-6: Acceso global al Singleton 
        int puntosActuales = AdministradorJuego.getInstance().getPuntos();
        float velocidadAumentada = configNivel.getVelocidadCaida() + (puntosActuales * 1);

        for (int i = 0; i < objetos.size; i++) {
            ObjetoCaida objeto = objetos.get(i);
            
            // GM-8: Llamada al Template Method 
            objeto.procesarFrame(velocidadAumentada, Gdx.graphics.getDeltaTime(), aldeano);

            if (!objeto.isActivo()) {
                objetos.removeIndex(i);
                i--; 
            }
        }
    }                                                                                         
                                                                                                     
    public void actualizarDibujo(SpriteBatch batch) {                                          
        for (int i = 0; i < objetos.size; i++) {                                                       
            objetos.get(i).dibujar(batch);                                                             
        }                                                                                            
    } 
    
    public void detenerMusica() {
        if (musicaFondo.isPlaying()) {
            musicaFondo.stop();
        }
    }
                                                                                                     
    public void destruir() {                                                                         
        sonidoEsmeralda.dispose();                                                                         
        musicaFondo.dispose();                                                                         
    }                                                                                                
}