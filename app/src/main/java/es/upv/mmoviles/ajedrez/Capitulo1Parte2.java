package es.upv.mmoviles.ajedrez;

import android.content.Intent;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by usuwi on 05/12/2016.
 */

public class Capitulo1Parte2 extends AppCompatActivity {

    private VistaAvatar avatar;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setVolumeControlStream(AudioManager.STREAM_MUSIC);
        setContentView(R.layout.capitulo1_parte2);

        Typeface fuente = Typeface.createFromAsset(getAssets(), "fonts/BalooPaaji-Regular.ttf");
        // Typeface fuente = Typeface.createFromAsset(getAssets(), "fonts/BungeeShade-Regular.ttf");
        // Typeface fuente = Typeface.createFromAsset(getAssets(), "fonts/HoltwoodOneSC.ttf");
        Typeface fuente1 = Typeface.createFromAsset(getAssets(),"fonts/Gorditas-Bold.ttf");
        Button boton8 = (Button) findViewById(R.id.boton8);
        boton8.setTypeface(fuente);
        Button boton9 = (Button) findViewById(R.id.boton9);
        boton9.setTypeface(fuente);
        Button boton10 = (Button) findViewById(R.id.boton10);
        boton10.setTypeface(fuente);
        Button boton11 = (Button) findViewById(R.id.boton11);
        boton11.setTypeface(fuente);


        TextView textoCapitulo12 = (TextView)findViewById(R.id.textoCapitulo12);
        textoCapitulo12.setTypeface(fuente1);

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

    public void boton8(View v) {
        Intent i = new Intent(Capitulo1Parte2.this, VerVideo.class);
        i.putExtra("video_id", "_XAzOaFtxCk");
        startActivity(i);
    }

    public void boton9(View v) {
        startActivity(new Intent(this, MoverTorreActivity.class));
    }

    public void boton10(View v) {
        startActivity(new Intent(this, MoverAlfilActivity.class));
    }

    public void boton11(View v) {
        startActivity(new Intent(this, MoverDamaActivity.class));
    }

}
