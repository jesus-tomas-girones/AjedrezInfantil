package es.upv.mmoviles.ajedrez;

import android.os.Bundle;

/**
 * Created by Jesús Tomás on 11/12/2016.
 */

public class MoverCaballoActivity extends MoverPiezaActivity {
    private Validador validadorCaballo = new Validador() {
        @Override
        public boolean movimientoValido(int colOrigen, int filaOrigen, int colDestino, int filaDestino) {
            boolean diferenteCasilla = (colOrigen != colDestino) || (filaOrigen != filaDestino);
            boolean vertical2horizintal1 =  (Math.abs(filaOrigen - filaDestino) == 2)
                                         && (Math.abs(colOrigen - colDestino) == 1);
            boolean vertical1horizintal2 =  (Math.abs(filaOrigen - filaDestino) == 1)
                                         && (Math.abs(colOrigen - colDestino) == 2);
            return (diferenteCasilla && (vertical2horizintal1 || vertical1horizintal2));
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        inicializa(validadorCaballo, R.drawable.caballo_blanco, R.raw.mover_dama_presentacion);
        super.onCreate(savedInstanceState);
    }
}