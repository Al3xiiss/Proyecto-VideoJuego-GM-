package puppy.code;

public class AdministradorJuego {
    // 1. Instancia estática y privada (la única que existirá en memoria)
    private static AdministradorJuego instancia;

    // Atributos globales del juego
    private int vidas;
    private int puntos;

    // 2. Constructor privado (nadie más puede hacer 'new AdministradorJuego()')
    private AdministradorJuego() {
        vidas = 3;
        puntos = 0;
    }

    // 3. Método público y estático para obtener la instancia única
    public static AdministradorJuego getInstance() {
        if (instancia == null) {
            instancia = new AdministradorJuego();
        }
        return instancia;
    }

    // Métodos para encapsular y manejar el estado (GM-5)
    public int getVidas() {
        return vidas;
    }

    public void restarVida() {
        vidas--;
    }

    public int getPuntos() {
        return puntos;
    }

    public void sumarPuntos(int cantidad) {
        puntos += cantidad;
    }
}