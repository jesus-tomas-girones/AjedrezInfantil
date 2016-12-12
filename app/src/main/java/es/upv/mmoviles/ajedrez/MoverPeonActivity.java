package es.upv.mmoviles.ajedrez;

import android.os.Bundle;

/**
 * Created by Jesús Tomás on 11/12/2016.
 */

public class MoverPeonActivity extends MoverPiezaActivity {
    private Validador validadorPeon = new Validador() {
        @Override
        public boolean movimientoValido(int colOrigen, int filaOrigen, int colDestino, int filaDestino) {
            boolean diferenteCasilla = (colOrigen != colDestino) || (filaOrigen != filaDestino);
            boolean avanzaUno =  (filaDestino==filaOrigen+1) && (colDestino==colOrigen);
            return (diferenteCasilla && avanzaUno);
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        inicializa(validadorPeon, R.drawable.peon_blanco, R.raw.mover_dama_presentacion);
        super.onCreate(savedInstanceState);
    }
}