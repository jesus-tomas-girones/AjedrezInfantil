package es.upv.mmoviles.ajedrez;

import android.os.Bundle;

/**
 * Created by Jesús Tomás on 28/11/2016.
 */

public class MoverReyActivity extends MoverPiezaActivity {
    private Validador validadorRey = new Validador() {
        @Override
        public boolean movimientoValido(int colOrigen, int filaOrigen, int colDestino, int filaDestino) {
            boolean diferenteCasilla = (colOrigen != colDestino) || (filaOrigen != filaDestino);
            boolean distanciaUno =  (Math.abs(filaOrigen - filaDestino) <=1)
                                 && (Math.abs(colOrigen - colDestino)<=1);
            return (diferenteCasilla && distanciaUno);
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        inicializa(validadorRey, R.drawable.rey_blanco, R.raw.mover_dama_presentacion);
        super.onCreate(savedInstanceState);
    }
}