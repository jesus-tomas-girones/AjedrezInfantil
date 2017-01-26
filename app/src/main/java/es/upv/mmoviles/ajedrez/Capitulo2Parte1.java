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

public class Capitulo2Parte1 extends AppCompatActivity {

    private VistaAvatar avatar;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setVolumeControlStream(AudioManager.STREAM_MUSIC);
        setContentView(R.layout.capitulo2_parte1);

        Typeface fuente = Typeface.createFromAsset(getAssets(), "fonts/BalooPaaji-Regular.ttf");
        // Typeface fuente = Typeface.createFromAsset(getAssets(), "fonts/BungeeShade-Regular.ttf");
        // Typeface fuente = Typeface.createFromAsset(getAssets(), "fonts/HoltwoodOneSC.ttf");
        Typeface fuente1 = Typeface.createFromAsset(getAssets(),"fonts/Gorditas-Bold.ttf");
        Button boton12 = (Button) findViewById(R.id.boton12);
        boton12.setTypeface(fuente);
        Button boton13 = (Button) findViewById(R.id.boton13);
        boton13.setTypeface(fuente);
        Button boton14 = (Button) findViewById(R.id.boton14);
        boton14.setTypeface(fuente);
        Button boton15 = (Button) findViewById(R.id.boton15);
        boton15.setTypeface(fuente);


        TextView textoCapitulo21 = (TextView)findViewById(R.id.textoCapitulo21);
        textoCapitulo21.setTypeface(fuente1);
        TextView textoLeccion = (TextView)findViewById(R.id.textoLeccion);
        textoLeccion.setTypeface(fuente);
        TextView textoEjercicios = (TextView)findViewById(R.id.textoEjercicios);
        textoEjercicios.setTypeface(fuente);

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

    public void boton12(View v) {
        Intent i = new Intent(Capitulo2Parte1.this, VerVideo.class);
        i.putExtra("video_id", "o-LsTUhLF_g");
        startActivity(i);
    }

    public void boton13(View v) {
        startActivity(new Intent(this, ValoresPiezasActivity.class));
    }

    public void boton14(View v) {
        startActivity(new Intent(this, ColocarPiezasActivity.class));
    }

    public void boton15(View v) {
        startActivity(new Intent(this, MoverReyEnJaqueActivity.class));
    }

}
