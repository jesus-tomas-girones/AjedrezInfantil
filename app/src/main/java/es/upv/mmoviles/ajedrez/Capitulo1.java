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

    //Todo: Crear actividad ver video

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        Log.d("Capitulo1", "hasta aquí llega");
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

   /* public void presentacion() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED) {
            avatar.habla(R.raw.presentacion);
        } else {
            solicitarPermisoRecordAudio();
        }
    }*/

    public void boton1(View v) { startActivity(new Intent(this, VerVideo.class)); }

    public void boton2(View v) {
        startActivity(new Intent(this, TareaA1Activity.class));
    }

    public void boton3(View v) {
        startActivity(new Intent(this, ColocarPiezasActivity.class));
    }


/*
    void solicitarPermisoRecordAudio() {
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
                    ActivityCompat.requestPermissions(Capitulo1.this, new String[]{
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
                finish();
            }
        }
    }*/



}
