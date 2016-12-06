package es.upv.mmoviles.ajedrez;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by usuwi on 05/12/2016.
 */

public class Capitulo1 extends AppCompatActivity {

    private VistaAvatar avatar;
    private final int REQUEST_RECORD_AUDIO = 0;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setVolumeControlStream(AudioManager.STREAM_MUSIC);
        setContentView(R.layout.capitulo1);

        Typeface fuente = Typeface.createFromAsset(getAssets(), "fonts/BalooPaaji-Regular.ttf");
        // Typeface fuente = Typeface.createFromAsset(getAssets(), "fonts/BungeeShade-Regular.ttf");
        // Typeface fuente = Typeface.createFromAsset(getAssets(), "fonts/HoltwoodOneSC.ttf");
        Button boton1 = (Button) findViewById(R.id.boton5);
        boton1.setTypeface(fuente);
        Button boton2 = (Button) findViewById(R.id.boton6);
        boton2.setTypeface(fuente);
        Button boton3 = (Button) findViewById(R.id.boton7);
        boton3.setTypeface(fuente);

        avatar = (VistaAvatar) findViewById(R.id.vistaAvatar1);
        avatar.setActividad(this);
       // presentacion();
    }

    @Override
    public void onResume() {
        super.onResume();
        avatar.reanudar();
    }

    @Override
    public void onPause() {
        avatar.pausar();
        super.onPause();
    }

    public void boton1(View v) {
        Intent i = new Intent(Capitulo1.this, VerVideo.class);
        i.putExtra("video_id", "s3O7FQWVLv0");
        startActivity(i);
    }

    public void boton2(View v) {
        startActivity(new Intent(this, TareaA1Activity.class));
    }

    public void boton3(View v) {
        startActivity(new Intent(this, ColocarPiezasActivity.class));
    }

}
