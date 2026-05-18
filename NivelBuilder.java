package puppy.code;

public class NivelBuilder {
    // Valores por defecto (Dificultad Normal)
    private float velocidad = 300f; 
    private int probabilidadMala = 3; 

    public NivelBuilder setVelocidadCaida(float velocidad) {
        this.velocidad = velocidad;
        return this; // Retornar 'this' permite encadenar los métodos
    }

    public NivelBuilder setProbabilidadGotaMala(int probabilidad) {
        this.probabilidadMala = probabilidad;
        return this;
    }

    // El método final que ensambla el objeto
    public ConfiguracionNivel build() {
        return new ConfiguracionNivel(velocidad, probabilidadMala);
    }
}