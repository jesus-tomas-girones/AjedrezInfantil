package es.upv.mmoviles.ajedrez;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

import static es.upv.mmoviles.ajedrez.R.raw.musica;

public class MainActivity extends AppCompatActivity {
    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }
    private Handler handler;
    private Runnable runnable;
    private VistaAvatar avatar;
    private MediaPlayer mediaPlayerMusica;
    private int volumenMusica;
    private boolean musicaActivada;
    private final int SOLICITUD_GRABAR_AUDIO = 0;
    private final int RETRASO_PRESENTACION = 3000; // Milisegundos que pasan hasta que empieza

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handler = new Handler();
        setContentView(R.layout.activity_main);

        Typeface fuente = Typeface.createFromAsset(getAssets(), "fonts/BalooPaaji-Regular.ttf");
        Typeface fuente1 = Typeface.createFromAsset(getAssets(), "fonts/Gorditas-Bold.ttf");
        //Typeface fuente = Typeface.createFromAsset(getAssets(), "fonts/BungeeShade-Regular.ttf");
        //Typeface fuente = Typeface.createFromAsset(getAssets(), "fonts/HoltwoodOneSC.ttf");
        Button boton1 = (Button) findViewById(R.id.boton1);
        boton1.setTypeface(fuente);
        Button boton2 = (Button) findViewById(R.id.boton2);
        boton2.setTypeface(fuente);
        Button boton3 = (Button) findViewById(R.id.boton3);
        boton3.setTypeface(fuente);

        //Button boton4 = (Button) findViewById(R.id.boton4);
        //boton4.setTypeface(fuente);

        TextView textoTituloApp = (TextView)findViewById(R.id.textoTituloApp);
        textoTituloApp.setTypeface(fuente1);
        TextView textoContenidos = (TextView)findViewById(R.id.textoContenidos);
        textoContenidos.setTypeface(fuente);

        ImageView ajustes = (ImageView) findViewById(R.id.ajustes);
        ImageView acercaDe = (ImageView) findViewById(R.id.info);

        mediaPlayerMusica = MediaPlayer.create(this, musica);
        mediaPlayerMusica.setLooping(true);

        avatar = (VistaAvatar) findViewById(R.id.vistaAvatar);
        avatar.setActividad(this);
        leerPreferencias();
        presentacion();
    }

    @Override
    public void onRestart() {
        super.onRestart();
        modificaVolumenMusica(0, 100, 0, (RETRASO_PRESENTACION/100));
    }

    @Override
    public void onResume() {
        super.onResume();
        leerPreferencias();
        avatar.reanudar();
    }

    @Override
    public void onPause() {
        avatar.pausar();
        handler.removeCallbacks(runnable);
        mediaPlayerMusica.pause();
        super.onPause();
    }

    // Referencia: http://stackoverflow.com/questions/6884590/android-how-to-create-fade-in-fade-out-sound-effects-for-any-music-file-that-my
    private void actualizaVolumenMusica(int cambio){
        volumenMusica += cambio;
        if (volumenMusica < 0)
            volumenMusica = 0;
        else if (volumenMusica > 100)
            volumenMusica = 100;
        float floatVolumenMusica = 1 - ((float) Math.log(100 - volumenMusica) / (float) Math.log(100));
        if (floatVolumenMusica < 0)
            floatVolumenMusica = 0;
        else if (floatVolumenMusica > 1)
            floatVolumenMusica = 1;
        mediaPlayerMusica.setVolume(floatVolumenMusica, floatVolumenMusica);
    }

    // Referencia: http://stackoverflow.com/questions/6884590/android-how-to-create-fade-in-fade-out-sound-effects-for-any-music-file-that-my
    private void modificaVolumenMusica(final int volumenInicial, final int volumenFinal, int retraso, int periodo) {
        try {
            volumenMusica = volumenInicial;
            final Timer timer = new Timer();
            TimerTask timerTask = new TimerTask() {
                @Override
                public void run() {
                    if (volumenInicial < volumenFinal)
                        actualizaVolumenMusica(1);
                    else
                        actualizaVolumenMusica(-1);
                    if (volumenMusica == volumenFinal) {
                        if (volumenFinal==0) {
                            mediaPlayerMusica.pause();
                        }
                        timer.cancel();
                        timer.purge();
                    }
                }
            };
            timer.schedule(timerTask, retraso, periodo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void presentacion() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED) {
            runnable = new Runnable() {
                @Override
                public void run() {
                    avatar.habla(R.raw.presentacion, new VistaAvatar.OnAvatarHabla() {
                        @Override
                        public void onTerminaHabla() {
                            if (musicaActivada) {
                                mediaPlayerMusica.start();
                                modificaVolumenMusica(0, 100, 0, 30);
                            }
                        }
                    });
                }
            };
            handler.postDelayed(runnable, RETRASO_PRESENTACION);
            if (musicaActivada) {
                mediaPlayerMusica.start();
                modificaVolumenMusica(100, 0, 0, (RETRASO_PRESENTACION / 100));
            }
        } else {
            solicitarPermisoRecordAudio();
        }
    }

    public void boton1(View v) { startActivity(new Intent(this, Capitulo1Parte1.class)); }

    public void boton2(View v) { startActivity(new Intent(this, Capitulo1Parte2.class));
    }

    public void boton3(View v) {
        startActivity(new Intent(this, Capitulo2Parte1.class));
    }

    public void boton4(View v) { startActivity(new Intent(this, MoverDamaActivity.class)); }

    public void acercaDe(View v){ startActivity(new Intent(this, AcercaDeActivity.class));}

    public void ajustes(View v){ startActivity(new Intent(this, Preferencias.class));}

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
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{
                            Manifest.permission.RECORD_AUDIO}, SOLICITUD_GRABAR_AUDIO);
                }
            }).show();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, SOLICITUD_GRABAR_AUDIO);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == SOLICITUD_GRABAR_AUDIO) {
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
    }

    private void leerPreferencias(){
        SharedPreferences preferencias = PreferenceManager.getDefaultSharedPreferences(this);
        boolean musicaActivada = preferencias.getBoolean("musica", true);
        if (musicaActivada) {
            mediaPlayerMusica.start();
            if (!this.musicaActivada){
                this.musicaActivada=musicaActivada;
                modificaVolumenMusica(0, 100, 0, 30);
            }
        }
        avatar.setSonidosActivados(preferencias.getBoolean("sonidos", true));
    }

}
