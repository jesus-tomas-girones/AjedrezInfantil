package es.upv.mmoviles.ajedrez;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.Random;


public class SenalarCasillas extends EjercicioBaseActivity {

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

        mpA3 = MediaPlayer.create(this, R.raw.senyala_casilla_A3);
        mpB4 = MediaPlayer.create(this, R.raw.senyala_casilla_B4);
        mpC8 = MediaPlayer.create(this, R.raw.senyala_casilla_C8);
        mpD1 = MediaPlayer.create(this, R.raw.senyala_casilla_D1);
        mpE3 = MediaPlayer.create(this, R.raw.senyala_casilla_E3);
        mpF2 = MediaPlayer.create(this, R.raw.senyala_casilla_F2);
        mpG6 = MediaPlayer.create(this, R.raw.senyala_casilla_G6);
        mpH5 = MediaPlayer.create(this, R.raw.senyala_casilla_H5);
        mpAcertado = MediaPlayer.create(this, R.raw.ok_has_acertado);
        mpFallado = MediaPlayer.create(this, R.raw.mal_intenta_otra_vez);
        aleatorio = new Random();
        //Genero un número aleatorio entre 1 y 8
        numeroaleatorio = aleatorio.nextInt(8 - 1) + 1;
        switch (numeroaleatorio) {
            case 1:
                mpA3.start();
                break;

            case 2:
                mpB4.start();
                break;

            case 3:
                mpC8.start();
                break;

            case 4:
                mpD1.start();
                break;

            case 5:
                mpE3.start();
                break;

            case 6:
                mpF2.start();
                break;

            case 7:
                mpG6.start();
                break;

            case 8:
                mpH5.start();
                break;

            default:
        }

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

            if (casillaPulsada.equals("A3") && numeroaleatorio == 1)
                mpAcertado.start();
            if (casillaPulsada.equals("B4") && numeroaleatorio == 2)
                mpAcertado.start();
            if (casillaPulsada.equals("C8") && numeroaleatorio == 3)
                mpAcertado.start();
            if (casillaPulsada.equals("D1") && numeroaleatorio == 4)
                mpAcertado.start();
            if (casillaPulsada.equals("E3") && numeroaleatorio == 5)
                mpAcertado.start();
            if (casillaPulsada.equals("F2") && numeroaleatorio == 6)
                mpAcertado.start();
            if (casillaPulsada.equals("G6") && numeroaleatorio == 7)
                mpAcertado.start();
            if (casillaPulsada.equals("H5") && numeroaleatorio == 8)
                mpAcertado.start();


            if ((!casillaPulsada.equals("A3")) && (!casillaPulsada.equals("B4")) && (!casillaPulsada.equals("C8")) && (!casillaPulsada.equals("D1")) && (!casillaPulsada.equals("E3")) && (!casillaPulsada.equals("F2")) && (!casillaPulsada.equals("G6")) && (!casillaPulsada.equals("H5"))) {
                mpFallado.start();
            }
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
