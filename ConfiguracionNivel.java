package puppy.code;

public class ConfiguracionNivel {
    private float velocidadCaida;
    private int probabilidadLava;

    // Constructor que será llamado por el Builder
    public ConfiguracionNivel(float velocidad, int probabilidad) {
        this.velocidadCaida = velocidad;
        this.probabilidadLava = probabilidad;
    }

    public float getVelocidadCaida() {
        return velocidadCaida;
    }

    public int getProbabilidadLava() {
        return probabilidadLava;
    }
}