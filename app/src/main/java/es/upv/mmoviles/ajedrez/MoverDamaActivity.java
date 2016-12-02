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
            return (filaOrigen == filaDestino) || (colOrigen == colDestino) || //misma fila o columna
                    (Math.abs(filaOrigen - filaDestino) == Math.abs(colOrigen - colDestino)); //misma diagonal
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ImageView C3 = (ImageView) findViewById(R.id.C3);
        C3.setImageResource(R.drawable.dama_blanca);
        avatar = getAvatar();
        avatar.habla(R.raw.mover_dama_presentacion);
    }

/*    public void presentacion() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED) {
            avatar.habla(R.raw.presentacion, new VistaAvatar.OnAvatarHabla() {
                @Override
                public void onTerminaHabla() {
                    finish();
                }
            });
        } else {
            solicitarPermisoRecordAudio();
        }
    }*/



/*    boolean movimientoDama(int colOrigen, int filaOrigen, int colDestino, int filaDestino) {
      return (filaOrigen == filaDestino) || (colOrigen == colDestino) || //misma fila o columna
             (Math.abs(filaOrigen - filaDestino) == Math.abs(colOrigen - colDestino)); //misma diagonal
    }*/

    @Override
    protected boolean onMovimiento(int colOrigen, int filaOrigen, int colDestino, int filaDestino) {
        boolean movimientoCorrecto = validadorDama.movimientoValido(colOrigen, filaOrigen, colDestino, filaDestino);
        if (movimientoCorrecto) {
            contadorMovimientos++;
            avatar.habla(R.raw.mover_dama_bien); //Todo: (Jesús) Cambiar nombre mover_dama_bien -> bien_intenta_otra_vez
            if (contadorMovimientos > 3) {
                avatar.habla(R.raw.mover_dama_superado, new VistaAvatar.OnAvatarHabla() { //Todo: (Jesús) Cambiar nombre mover_dama_superado -> ejercicio_superado
                    @Override
                    public void onTerminaHabla() {
                        finish();
                    }
                });
            }
        } else {
            //contadorErrores++;
            avatar.habla(R.raw.mover_dama_mal);
            resaltarCasilla(colOrigen, filaOrigen, validadorDama);
        }
        return movimientoCorrecto;
    }

/*    void solicitarPermisoRecordAudio() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.RECORD_AUDIO)) {
            Snackbar snackbar = Snackbar.make(avatar, "Sin el permiso grabación de audio,\n"
                    + "no puedo mostrarte el avatar hablando.", Snackbar.LENGTH_INDEFINITE);
            View snackbarView = snackbar.getView();
            TextView textView = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
            textView.setMaxLines(2);
            snackbar.setAction("OK", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ActivityCompat.requestPermissions(MoverDamaActivity.this, new String[]{
                            Manifest.permission.RECORD_AUDIO}, REQUEST_RECORD_AUDIO);
                }
            }).show();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, REQUEST_RECORD_AUDIO);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == REQUEST_RECORD_AUDIO) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                presentacion();
            } else {
                Snackbar snackbar = Snackbar.make(avatar, "Sin el permiso grabación de audio,\n"
                        + "no puedo mostrarte el avatar hablando.", Snackbar.LENGTH_LONG);
                View snackbarView = snackbar.getView();
                TextView textView = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
                textView.setMaxLines(2);
                snackbar.show();
            }
        }
    }*/
}
