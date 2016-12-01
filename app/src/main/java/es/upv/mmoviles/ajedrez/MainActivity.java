package es.upv.mmoviles.ajedrez;

import android.content.Intent;
import android.media.AudioManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity  {
    // ESTO ES UNA PRUEBA

    //Todo: (Raúl) Solicitar permiso grabación audio

    //Todo: Poner avatar a la izquierda y botones a la derecha con Capítulo 1, capítulo 2, ...

    //Todo: Crear actividad Capitulo 1: avatar a la izquierda y botones a la derecha. Botones: verVideo, Ejercicio coordenadas, Ejercicio Colocar fichas,

    //Todo: Crear actividad ver video


    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setVolumeControlStream(AudioManager.STREAM_MUSIC);
        setContentView(R.layout.activity_main);
    }

    public void boton1(View v) {
        startActivity(new Intent(this, EjercicioBaseActivity.class));
    }

    public void boton2(View v) {
        startActivity(new Intent(this, TareaA1Activity.class));
    }

    public void boton3(View v) {
        startActivity(new Intent(this, ColocarPiezasActivity.class));
    }

    public void boton4(View v) {
        startActivity(new Intent(this, MoverDamaActivity.class));
    }

}
