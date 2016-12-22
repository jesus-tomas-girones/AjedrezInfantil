package es.upv.mmoviles.ajedrez;

import android.os.Bundle;
import android.view.View;
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
        avatar.habla(R.raw.colocar_piezas_presentacion);
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
                avatar.habla(R.raw.colocar_piezas_bien);
            } else {
                avatar.habla(R.raw.ok_superado,
                        new VistaAvatar.OnAvatarHabla() {
                            @Override public void onTerminaHabla() { finish();   }
                });
            }
        } else {
            switch (pieza){
                case 'P':
                    avatar.habla(R.raw.colocar_piezas_mal_peon);
                    resaltarCasilla(0, 0, //(c1,f1,c2,f2)-> f2==1   //Poner como Lambda en Java 8
                            new Validador() {
                                @Override
                                public boolean movimientoValido(int colOrigen, int filaOrigen, int colDestino, int filaDestino) {
                                    return filaDestino==1;
                                }
                            });
                    break;
                case 'T':
                    avatar.habla(R.raw.colocar_piezas_mal_torre);
                    resaltarCasilla(0, 0, Movimiento.CORRECTO); resaltarCasilla(7, 0, Movimiento.CORRECTO);
                    break;
                case 'C':
                    avatar.habla(R.raw.colocar_piezas_mal_caballo);
                    resaltarCasilla(1, 0, Movimiento.CORRECTO); resaltarCasilla(6, 0, Movimiento.CORRECTO);
                    break;
                case 'A':
                    avatar.habla(R.raw.colocar_piezas_mal_alfil);
                    resaltarCasilla(2, 0, Movimiento.CORRECTO); resaltarCasilla(5, 0, Movimiento.CORRECTO);
                    break;
                case 'D':
                    avatar.habla(R.raw.colocar_piezas_mal_dama);
                    resaltarCasilla(3, 0, Movimiento.CORRECTO);
                    break;
                case 'R':
                    avatar.habla(R.raw.colocar_piezas_mal_rey);
                    resaltarCasilla(4, 0, Movimiento.CORRECTO);
            }
        }
        return salida;
    }
}