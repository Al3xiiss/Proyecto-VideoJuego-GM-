package puppy.code;

public class AdministradorJuego {
    private static AdministradorJuego instancia;

    private int vidas;
    private int puntos;
    private boolean juegoTerminado; 

    private AdministradorJuego() {
        iniciarNuevoJuego();
    }

    public static AdministradorJuego getInstance() {
        if (instancia == null) {
            instancia = new AdministradorJuego();
        }
        return instancia;
    }

    // <-- Nuevo método para resetear las estadísticas
    public void iniciarNuevoJuego() {
        vidas = 3;
        puntos = 0;
        juegoTerminado = false;
    }

    public int getVidas() { return vidas; }
    public int getPuntos() { return puntos; }
    public boolean isJuegoTerminado() { return juegoTerminado; }

    public void sumarPuntos(int cantidad) {
        if (!juegoTerminado) puntos += cantidad;
    }

    public void restarVida() {
        if (!juegoTerminado && vidas > 0) {
            vidas--;
            if (vidas == 0) {
                juegoTerminado = true; // Si llega a 0, se acaba el juego
            }
        }
    }
}