package es.upv.mmoviles.ajedrez;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.Random;


public class SenalarCasillas extends EjercicioBaseActivity {

    final static int recursos[] = {R.raw.senyala_casilla_a3, R.raw.senyala_casilla_b4, R.raw.senyala_casilla_c8, R.raw.senyala_casilla_d1, R.raw.senyala_casilla_e3, R.raw.senyala_casilla_f2, R.raw.senyala_casilla_g6, R.raw.senyala_casilla_h5};
    final static String coordenadas[] = {"A3", "B4", "C8", "D1", "E3", "F2", "G6", "H5"};

    private VistaAvatar avatar;
    private int aciertos = 0, numeroaleatorio;
    private MediaPlayer mpA3, mpB4, mpC8, mpD1, mpE3, mpF2, mpG6, mpH5, mpAcertado, mpFallado;
    private Random aleatorio;
    private int contadorPreguntas = 0;
    private int respuestasAcertadas = 0;
    private boolean haSidoPulsadayVerifOK = false;
    private boolean haSidoPulsadayVerifKO = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        avatar = getAvatar();

        aleatorio = new Random();

        //Genero un número aleatorio entre 0 y 7
        numeroaleatorio = aleatorio.nextInt(7 - 0) + 0;
        //Uso el método habla de VistaAvatar para poder reproducir el sonido del tic tac y empezar la cuenta atrás
        avatar.habla(recursos[numeroaleatorio], new VistaAvatar.OnAvatarHabla() {
            @Override
            public void onTerminaHabla() {
                avatar.reproduceEfectoSonido(VistaAvatar.EfectoSonido.TIC_TAC);
                empiezaCuentaAtras();
            }
        });


    }

    //Con este método conseguimos que repita la pregunta si en 8 segundos no se ha señalado la casilla
    @Override
    protected void onFinalCuentaAtras() {
        avatar.habla(recursos[numeroaleatorio], new VistaAvatar.OnAvatarHabla() {
            @Override
            public void onTerminaHabla() {
                avatar.reproduceEfectoSonido(VistaAvatar.EfectoSonido.TIC_TAC);
                empiezaCuentaAtras();
            }
        });
    }

    @Override
    protected boolean onPulsar(ImageView imgView) {
        cancelaCuentaAtras();

        contadorPreguntas++;

        if (contadorPreguntas > 2 ) {
            avatar.lanzaAnimacion(VistaAvatar.Animacion.EJERCICIO_SUPERADO);
            avatar.reproduceEfectoSonido(VistaAvatar.EfectoSonido.EJERCICIO_SUPERADO);
            contadorPreguntas = 0;
            avatar.habla(R.raw.ok_superado, new VistaAvatar.OnAvatarHabla() {
                @Override
                public void onTerminaHabla() {
                    finish();
                }
            });

        } else {
            //contadorErrores++;
            respuestasAcertadas++;
            avatar.habla(R.raw.ok_intenta_otra_vez, new VistaAvatar.OnAvatarHabla() {
                @Override
                public void onTerminaHabla() {
                    avatar.reproduceEfectoSonido(VistaAvatar.EfectoSonido.TIC_TAC);
                    empiezaCuentaAtras();
                }
            });

        }
        resaltarCasilla(imgView);

        return true;

    }


    class MiClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            String casillaPulsada = v.getTag().toString();
            LinearLayout tabla = (LinearLayout) findViewById(R.id.tabla);

            Toast.makeText(getApplicationContext(), "Se ha pulsado la casilla " + casillaPulsada, Toast.LENGTH_SHORT).show();
            //Creo un objeto de ImageView a partir de la vista v
            ImageView imagen = (ImageView) v;
            onPulsar(imagen);
            //Llamo a resaltarCasilla con el imageview imagen para que se resalte cuando se pulse
            resaltarCasilla(imagen);
            //Si es la primera vez que pulso después de hacer una pregunta
            do {
                if (casillaPulsada.equals(coordenadas[numeroaleatorio])) {
                    avatar.reproduceEfectoSonido(VistaAvatar.EfectoSonido.MOVIMIENTO_CORRECTO);
                    avatar.habla(R.raw.ok_has_acertado);
                    contadorPreguntas++;
                    respuestasAcertadas++;
                    haSidoPulsadayVerifOK = true;

                } else {
                    avatar.reproduceEfectoSonido(VistaAvatar.EfectoSonido.MOVIMIENTO_INCORRECTO);
                    //avatar.habla(R.raw.mal_intenta_otra_vez);
                    haSidoPulsadayVerifKO = true;
                }
            } while (haSidoPulsadayVerifOK == false && haSidoPulsadayVerifKO == false);


            //Si ya se ha pulsado una vez y la casilla es correcta
            if (haSidoPulsadayVerifOK == true && haSidoPulsadayVerifKO == false) {
                haSidoPulsadayVerifKO = haSidoPulsadayVerifOK = false;
                //Hago la segunda pregunta

            }
            if (haSidoPulsadayVerifOK == false && haSidoPulsadayVerifKO == true) {

                avatar.habla(R.raw.mal_intenta_otra_vez, new VistaAvatar.OnAvatarHabla() {
                    @Override
                    public void onTerminaHabla() {
                        avatar.reproduceEfectoSonido(VistaAvatar.EfectoSonido.TIC_TAC);
                        empiezaCuentaAtras();
                    }
                });
                //Vuelve a preguntar lo mismo
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


