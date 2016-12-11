package es.upv.mmoviles.ajedrez;

import android.os.Bundle;
import android.widget.ImageView;

/**
 * Created by Jesús Tomás on 10/12/2016.
 */

public abstract class MoverPiezaActivity extends EjercicioBaseActivity {
    private VistaAvatar avatar;
    private int contadorMovimientos = 0;
    //private int contadorErrores = 0;

    protected Validador validador;
    protected int drawablePieza;
    protected int moverPiezaPresentacion;

    public void inicializa(Validador validador, int drawablePieza, int moverPiezaPresentacion){
        this.validador=validador;
        this.drawablePieza=drawablePieza;
        this.moverPiezaPresentacion=moverPiezaPresentacion;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ImageView C3 = (ImageView) findViewById(R.id.C3);
        C3.setImageResource(drawablePieza);
        avatar = getAvatar();
        avatar.habla(moverPiezaPresentacion, new VistaAvatar.OnAvatarHabla() {
            @Override
            public void onTerminaHabla() {
                avatar.reproduceEfectoSonido(VistaAvatar.EfectoSonido.TIC_TAC);
                empiezaCuentaAtras();
            }
        });
    }

    @Override
    protected void onFinalCuentaAtras(){
        avatar.habla(moverPiezaPresentacion, new VistaAvatar.OnAvatarHabla() {
            @Override
            public void onTerminaHabla() {
                avatar.reproduceEfectoSonido(VistaAvatar.EfectoSonido.TIC_TAC);
                empiezaCuentaAtras();
            }
        });
    }

    @Override
    protected boolean onMovimiento(int colOrigen, int filaOrigen, int colDestino, int filaDestino) {
        cancelaCuentaAtras();
        boolean movimientoCorrecto = validador.movimientoValido(colOrigen, filaOrigen, colDestino, filaDestino);
        if (movimientoCorrecto) {
            contadorMovimientos++;
            avatar.reproduceEfectoSonido(VistaAvatar.EfectoSonido.CORRECTO);
            if (contadorMovimientos > 3) {
                avatar.lanzaAnimacion(VistaAvatar.Animacion.ANIMACION_APLAUSOS);
                avatar.reproduceEfectoSonido(VistaAvatar.EfectoSonido.APLAUSOS);
                avatar.habla(R.raw.ok_superado, new VistaAvatar.OnAvatarHabla() {
                    @Override
                    public void onTerminaHabla() {
                        finish();
                    }
                });
            }
            else {
                avatar.lanzaAnimacion(VistaAvatar.Animacion.ANIMACION_CORRECTO);
                avatar.habla(R.raw.ok_intenta_otra_vez, new VistaAvatar.OnAvatarHabla() {
                    @Override
                    public void onTerminaHabla() {
                        avatar.reproduceEfectoSonido(VistaAvatar.EfectoSonido.TIC_TAC);
                        empiezaCuentaAtras();
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
                    empiezaCuentaAtras();
                }
            });
            resaltarCasilla(colOrigen, filaOrigen, validador);
        }
        return movimientoCorrecto;
    }
}