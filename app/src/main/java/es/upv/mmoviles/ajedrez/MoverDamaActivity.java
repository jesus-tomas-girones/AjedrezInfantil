package es.upv.mmoviles.ajedrez;

import android.os.Bundle;
import android.widget.ImageView;

/**
 * Created by Jesús Tomás on 28/11/2016.
 */

public class MoverDamaActivity extends EjercicioBaseActivity {
    private VistaAvatar avatar;
    private int contadorMovimientos = 0;
    //private int contadorErrores = 0;
    //private final int REQUEST_RECORD_AUDIO = 0;
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
        super.onCreate(savedInstanceState);
        ImageView C3 = (ImageView) findViewById(R.id.C3);
        C3.setImageResource(R.drawable.dama_blanca);
        avatar = getAvatar();
        avatar.habla(R.raw.mover_dama_presentacion, new VistaAvatar.OnAvatarHabla() {
            @Override
            public void onTerminaHabla() {
                avatar.reproduceEfectoSonido(VistaAvatar.EfectoSonido.TIC_TAC);
            }
        });
    }

/*    boolean movimientoDama(int colOrigen, int filaOrigen, int colDestino, int filaDestino) {
      return (filaOrigen == filaDestino) || (colOrigen == colDestino) || //misma fila o columna
             (Math.abs(filaOrigen - filaDestino) == Math.abs(colOrigen - colDestino)); //misma diagonal
    }*/

    @Override
    protected boolean onMovimiento(int colOrigen, int filaOrigen, int colDestino, int filaDestino) {
        boolean movimientoCorrecto = validadorDama.movimientoValido(colOrigen, filaOrigen, colDestino, filaDestino);
        if (movimientoCorrecto) {
            contadorMovimientos++;
            avatar.reproduceEfectoSonido(VistaAvatar.EfectoSonido.CORRECTO);
            avatar.habla(R.raw.mover_dama_bien, new VistaAvatar.OnAvatarHabla() {
                @Override
                public void onTerminaHabla() {
                    avatar.reproduceEfectoSonido(VistaAvatar.EfectoSonido.TIC_TAC);
                }
            }); //Todo: (Jesús) Cambiar nombre mover_dama_bien -> bien_intenta_otra_vez
            if (contadorMovimientos > 3) {
                avatar.reproduceEfectoSonido(VistaAvatar.EfectoSonido.APLAUSOS);
                avatar.habla(R.raw.mover_dama_superado, new VistaAvatar.OnAvatarHabla() { //Todo: (Jesús) Cambiar nombre mover_dama_superado -> ejercicio_superado
                    @Override
                    public void onTerminaHabla() {
                        finish();
                    }
                });
            }
        } else {
            //contadorErrores++;
            avatar.reproduceEfectoSonido(VistaAvatar.EfectoSonido.INCORRECTO);
            avatar.habla(R.raw.mover_dama_mal, new VistaAvatar.OnAvatarHabla() {
                @Override
                public void onTerminaHabla() {
                    avatar.reproduceEfectoSonido(VistaAvatar.EfectoSonido.TIC_TAC);
                }
            });
            resaltarCasilla(colOrigen, filaOrigen, validadorDama);
        }
        return movimientoCorrecto;
    }
}
