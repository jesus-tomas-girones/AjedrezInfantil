package es.upv.mmoviles.ajedrez;

import android.os.Bundle;

/**
 * Created by Jesús Tomás on 11/12/2016.
 */

public class MoverTorreActivity extends MoverPiezaActivity {

    private Validador validadorTorre = new Validador() {
        @Override
        public boolean movimientoValido(int colOrigen, int filaOrigen, int colDestino, int filaDestino) {
            boolean diferenteCasilla = (colOrigen != colDestino) || (filaOrigen != filaDestino);
            boolean mismaColumna = (colOrigen == colDestino);
            boolean mismaFila = (filaOrigen == filaDestino);
            return (diferenteCasilla && (mismaColumna || mismaFila));
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        inicializa(validadorTorre, R.drawable.torre_blanca, R.raw.mover_dama_presentacion); //Todo: reemplazar mover_dama_presentacion
        super.onCreate(savedInstanceState);
    }

}
