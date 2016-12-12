package es.upv.mmoviles.ajedrez;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.Random;


public class SenalarCasillas extends EjercicioBaseActivity {

    final static int recursos[] = {R.raw.senyala_casilla_a3,R.raw.senyala_casilla_b4, R.raw.senyala_casilla_c8, R.raw.senyala_casilla_d1, R.raw.senyala_casilla_e3, R.raw.senyala_casilla_f2, R.raw.senyala_casilla_g6, R.raw.senyala_casilla_h5 };
    final static String coordenadas[] = {"A3","B4","C8","D1", "E3", "F2", "G6", "H5" };

    private VistaAvatar avatar;
    private int aciertos = 0, numeroaleatorio;
    private MediaPlayer mpA3, mpB4, mpC8, mpD1, mpE3, mpF2, mpG6, mpH5, mpAcertado, mpFallado;
    private Random aleatorio;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //LinearLayout piezas = (LinearLayout)findViewById(R.id.piezas);
        //piezas.setVisibility(View.VISIBLE);
        avatar = getAvatar();

        /*mpA3 = MediaPlayer.create(this, R.raw.senyala_casilla_a3);
        mpB4 = MediaPlayer.create(this, R.raw.senyala_casilla_b4);
        mpC8 = MediaPlayer.create(this, R.raw.senyala_casilla_c8);
        mpD1 = MediaPlayer.create(this, R.raw.senyala_casilla_d1);
        mpE3 = MediaPlayer.create(this, R.raw.senyala_casilla_e3);
        mpF2 = MediaPlayer.create(this, R.raw.senyala_casilla_f2);
        mpG6 = MediaPlayer.create(this, R.raw.senyala_casilla_g6);
        mpH5 = MediaPlayer.create(this, R.raw.senyala_casilla_h5);
        mpAcertado = MediaPlayer.create(this, R.raw.ok_has_acertado);
        mpFallado = MediaPlayer.create(this, R.raw.mal_intenta_otra_vez);*/
        aleatorio = new Random();
        //Genero un número aleatorio entre 0 y 7
        numeroaleatorio = aleatorio.nextInt(7 - 0) + 0;

        avatar.habla(recursos[numeroaleatorio]);


    }

    @Override
    protected boolean onMovimiento(int colOrigen, int filaOrigen, int colDestino, int filaDestino) {
        return false; //Una vez colocada no permitimos moverla
    }


    class MiClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            String casillaPulsada = v.getTag().toString();


            Toast.makeText(getApplicationContext(), "Se ha pulsado la casilla " + casillaPulsada, Toast.LENGTH_SHORT).show();

            if (casillaPulsada.equals(coordenadas[numeroaleatorio]))
                avatar.habla(R.raw.ok_has_acertado);


            else avatar.habla(R.raw.mal_intenta_otra_vez);
            }
        }



    @Override
    void asignaListeners() {

        //TOUCH LISTENER
        MiTouchListener touchListener = new MiTouchListener();
        LinearLayout piezas = (LinearLayout) findViewById(R.id.piezas);
        for (int i = 0, iMax = piezas.getChildCount(); i < iMax; i++) {
            LinearLayout linearLayout = (LinearLayout) piezas.getChildAt(i);
            View vista = linearLayout.getChildAt(0);
            if (vista instanceof ImageView) {
                ImageView imagen = (ImageView) vista;
                imagen.setOnTouchListener(touchListener);
            }
        }

        //DRAG LISTENER Y CLICK LISTENER
        MiClickListener clickListener = new MiClickListener();
        //MiDragListener dragListener = new MiDragListener();

        LinearLayout tabla = (LinearLayout) findViewById(R.id.tabla);
        for (int i = 1, iMax = tabla.getChildCount() - 1; i < iMax; i++) {
            //Para cada fila (de 0 a 9) obtenemos la vista
            View vista = tabla.getChildAt(i);
            //Instance of es una comprobación para preguntar si un objeto es una instancia de una clase que le preguntemos
            //¿Es vista una instancia de LinearLayout?
            if (vista instanceof LinearLayout) {
                LinearLayout fila = (LinearLayout) vista;
                for (int j = 1, jMax = fila.getChildCount() - 1; j < jMax; j++) {
                    View vista2 = (ImageView) fila.getChildAt(j);
                    if (vista2 instanceof ImageView) {
                        ImageView imagen = (ImageView) vista2;
                        //A cada casilla le damos la opción de poder clickar en ella con la imagen para arrastrarla a otra casilla
                        imagen.setOnClickListener(clickListener);


                    }
                }
            }


        }
    }


}
