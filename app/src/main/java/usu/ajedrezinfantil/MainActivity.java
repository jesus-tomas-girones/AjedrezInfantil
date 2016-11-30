package usu.ajedrezinfantil;

import android.content.Intent;
import android.media.AudioManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity  {

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
