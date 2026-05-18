package puppy.code;

public class ConfiguracionNivel {
    private float velocidadCaida;
    private int probabilidadGotaMala;

    // Constructor que será llamado por el Builder
    public ConfiguracionNivel(float velocidad, int probabilidad) {
        this.velocidadCaida = velocidad;
        this.probabilidadGotaMala = probabilidad;
    }

    public float getVelocidadCaida() {
        return velocidadCaida;
    }

    public int getProbabilidadGotaMala() {
        return probabilidadGotaMala;
    }
}