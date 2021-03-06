package es.upv.mmoviles.ajedrez;

import android.os.Bundle;
import android.widget.ImageView;

/**
 * Created by Jesús Tomás on 28/11/2016.
 */

public class MoverDamaActivity extends MoverPiezaActivity {
    private Validador validadorDama = new Validador() {
        @Override
        public boolean movimientoValido(int colOrigen, int filaOrigen, int colDestino, int filaDestino) {
            boolean diferenteCasilla = (colOrigen != colDestino) || (filaOrigen != filaDestino);
            boolean mismaColumna = (colOrigen == colDestino);
            boolean mismaFila = (filaOrigen == filaDestino);
            boolean mismaDiagonal = (Math.abs(filaOrigen - filaDestino) == Math.abs(colOrigen - colDestino));
            return (diferenteCasilla && (mismaColumna || mismaFila || mismaDiagonal));
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        inicializa(validadorDama, R.drawable.dama_blanca, R.raw.mover_dama_presentacion);
        super.onCreate(savedInstanceState);
    }
}