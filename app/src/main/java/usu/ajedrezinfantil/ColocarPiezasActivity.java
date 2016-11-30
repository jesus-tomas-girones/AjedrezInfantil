package usu.ajedrezinfantil;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * Created by Jesús Tomás on 28/11/2016.
 */

public class ColocarPiezasActivity extends EjercicioBaseActivity {

    private VistaAvatar avatar;
    private int aciertos = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LinearLayout piezas = (LinearLayout)findViewById(R.id.piezas);
        piezas.setVisibility(View.VISIBLE);
        avatar = getAvatar();
        //avatar.habla(R.raw.colocar_piezas_presentacion); //Todo: (Jesús) Grabar audio
    }

    @Override
    protected boolean onMovimiento(int colOrigen, int filaOrigen, int colDestino, int filaDestino) {
        return false; //Una vez colocada no permitimos moverla
    }

    @Override
    protected boolean onColocar(char pieza, int colDestino, int filaDestino) {
        boolean salida;
        switch (pieza){
            case 'P': salida = filaDestino==1; break;
            case 'T': salida = filaDestino==0 && (colDestino==0 || colDestino==7); break;
            case 'C': salida = filaDestino==0 && (colDestino==1 || colDestino==6); break;
            case 'A': salida = filaDestino==0 && (colDestino==2 || colDestino==5); break;
            case 'D': salida = filaDestino==0 && colDestino==3; break;
            case 'R': salida = filaDestino==0 && colDestino==4; break;
            default: salida = false;
        }
        if (salida) {
            aciertos++;
            if (aciertos < 16) {
                avatar.habla(R.raw.mover_dama_bien); //Todo: (Jesús) Cambiar nombre mover_dama_bien -> ejercicio_bien
            } else {
                avatar.habla(R.raw.mover_dama_superado,
                        new VistaAvatar.OnAvatarHabla() {
                            @Override public void onTerminaHabla() { finish();   }
                });
            }
        } else {
            switch (pieza){
                case 'P':
                    //avatar.habla(R.raw.colocar_piezas_mal_peon); //Todo: (Jesús) Grabar audio
                    // resaltarCasilla(0, 0, boolean _funcion -> fila==1)
                    break;
                case 'T':
                    //avatar.habla(R.raw.colocar_piezas_mal_torre); //Todo: (Jesús) Grabar audio
                    // resaltarCasilla(0,0); resaltarCasilla(7,0);
                    break;
                case 'C':
                    //avatar.habla(R.raw.colocar_piezas_mal_caballo); //Todo: (Jesús) Grabar audio
                    // resaltarCasilla(1,0); resaltarCasilla(6,0);
                    break;
                case 'A':
                    //avatar.habla(R.raw.colocar_piezas_mal_alfil); //Todo: (Jesús) Grabar audio
                    // resaltarCasilla(2,0); resaltarCasilla(5,0);
                    break;
                case 'D':
                    //avatar.habla(R.raw.colocar_piezas_mal_dama); //Todo: (Jesús) Grabar audio
                    // resaltarCasilla(3,0);
                    break;
                case 'R':
                    //avatar.habla(R.raw.colocar_piezas_mal_rey); //Todo: (Jesús) Grabar audio
                    // resaltarCasilla(4,0);
            }
        }
        return salida;
    }
}
