package usu.ajedrezinfantil;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.media.audiofx.Visualizer;
import android.net.Uri;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;

import java.util.HashMap;
import java.util.Random;

/**
 * Created by raulvibo on 26/10/2016.
 */

public class VistaAvatar extends FrameLayout {
    private Activity activity;
    private ThreadAvatar thread;
    private ImageView imageViewCara;
    private ImageView imageViewCejas;
    private ImageView imageViewOjos;
    private ImageView imageViewBoca;
    private HashMap<DireccionMirada, Drawable> hashMapMiradas;
    private MediaPlayer mediaPlayerVoz;
    private Visualizer visualizerVoz;
    private long ultimaActualizacion;
    private long ultimoParpadeo;
    private Random random;
    private static int PERIODO_ACTUALIZACION = 50;
    private int PERIODO_PARPADEO = 6 * 1000;
    private DireccionMirada direccionMirada;

    public enum DireccionMirada {
        LEFT_TOP, LEFT_CENTER, LEFT_BOTTOM,
        RIGHT_TOP, RIGHT_CENTER, RIGHT_BOTTOM,
        CENTER_TOP, CENTER, CENTER_BOTTOM,
        CLOSED_EYES
    }
/*    private OnMovimientoListener onMovimientoListener;

    public interface OnMovimientoListener {
        boolean onMovimiento(String origen, String destino);
    }
    public void setOnMovimientoListener(OnMovimientoListener escuchador) {
        onMovimientoListener = escuchador;
    } */

    public VistaAvatar(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.avatar, this, true);
        imageViewCara = (ImageView) findViewById(R.id.imageViewCara);
        imageViewCejas = (ImageView) findViewById(R.id.imageViewCejas);
        imageViewOjos = (ImageView) findViewById(R.id.imageViewOjos);
        imageViewBoca = (ImageView) findViewById(R.id.imageViewBoca);

        HashMap<VistaAvatar.DireccionMirada, Drawable> miradas = new HashMap<VistaAvatar.DireccionMirada, Drawable>();
        miradas.put(VistaAvatar.DireccionMirada.LEFT_CENTER, getResources().getDrawable(R.drawable.mirada_left_center));
        miradas.put(VistaAvatar.DireccionMirada.CENTER, getResources().getDrawable(R.drawable.mirada_center));
        miradas.put(VistaAvatar.DireccionMirada.RIGHT_CENTER, getResources().getDrawable(R.drawable.mirada_right_center));
        miradas.put(VistaAvatar.DireccionMirada.CLOSED_EYES, getResources().getDrawable(R.drawable.ojos_cerrados));
        setMiradas(miradas);

        mediaPlayerVoz = new MediaPlayer();
        random = new Random(System.currentTimeMillis());
    }

    public void setActividad(Activity activity) {
        this.activity = activity;
        arrancaThread();
    }

    private void arrancaThread() {
        thread = new ThreadAvatar();
        thread.start();
        ultimaActualizacion = System.currentTimeMillis();
        ultimoParpadeo = System.currentTimeMillis();
    }

    public HashMap<DireccionMirada, Drawable> getMiradas() {
        return hashMapMiradas;
    }

    public void levantaCejas() {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AnimationDrawable animationDrawableCejas = (AnimationDrawable) imageViewCejas.getDrawable();
                animationDrawableCejas.stop();
                animationDrawableCejas.start();
            }
        });
    }

    public void parpadea() {
        synchronized (imageViewOjos) {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    AnimationDrawable animationDrawableOjos = (AnimationDrawable) imageViewOjos.getDrawable();
                    animationDrawableOjos.stop();
                    animationDrawableOjos.start();
                }
            });
        }
    }

    public void mueveBoca() {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AnimationDrawable animationDrawableBoca = (AnimationDrawable) imageViewBoca.getDrawable();
                animationDrawableBoca.start();
            }
        });
    }

    public void cierraBoca() {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AnimationDrawable animationDrawableBoca = (AnimationDrawable) imageViewBoca.getDrawable();
                animationDrawableBoca.stop();
                animationDrawableBoca.selectDrawable(0);
            }
        });
    }

    public void setMiradas(HashMap<DireccionMirada, Drawable> miradas) {
        synchronized (imageViewOjos) {
            this.hashMapMiradas = miradas;
        }
    }

    public Drawable getMirada(DireccionMirada direccionMirada) {
        return hashMapMiradas.get(direccionMirada);
    }

    public void setMirada(final DireccionMirada direccionMirada) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AnimationDrawable animationDrawableOjos = new AnimationDrawable();
                animationDrawableOjos.addFrame(hashMapMiradas.get(direccionMirada), 150);
                animationDrawableOjos.addFrame(hashMapMiradas.get(DireccionMirada.CLOSED_EYES), 150);
                animationDrawableOjos.addFrame(hashMapMiradas.get(direccionMirada), 150);
                animationDrawableOjos.setOneShot(true);
                synchronized (imageViewOjos) {
                    imageViewOjos.setImageDrawable(animationDrawableOjos);
                }
                animationDrawableOjos = (AnimationDrawable) imageViewOjos.getDrawable();
                animationDrawableOjos.stop();
                animationDrawableOjos.start();
            }
        });
    }

    public DireccionMirada getDireccionMirada() {
        return direccionMirada;
    }

    public void setDireccionMirada(DireccionMirada direccionMirada) {
        this.direccionMirada = direccionMirada;
    }

    public ImageView getImageViewCuerpo() {
        return imageViewCara;
    }

    public void setImageViewCara(ImageView imageViewCara) {
        this.imageViewCara = imageViewCara;
    }

    public ImageView getImageViewBoca() {
        return imageViewBoca;
    }

    public ImageView getImageViewCejas() {
        return imageViewCejas;
    }

    public void setImageViewCejas(ImageView imageViewCejas) {
        this.imageViewCejas = imageViewCejas;
    }

    public void setImageViewBoca(ImageView imageViewBoca) {
        this.imageViewBoca = imageViewBoca;
    }

    public ImageView getImageViewOjos() {
        return imageViewOjos;
    }

    public void setImageViewOjos(ImageView imageViewOjos) {
        synchronized (imageViewOjos) {
            this.imageViewOjos = imageViewOjos;
        }
    }

    public MediaPlayer getMediaPlayerVoz() {
        return mediaPlayerVoz;
    }

    public interface OnAvatarHabla{
        void onTerminaHabla();
    }

    private OnAvatarHabla onAvatarHabla;

    public void habla(int idRecurso) {
        habla(idRecurso, null);
    }
    public void habla(int idRecurso, OnAvatarHabla escuchador) {
        onAvatarHabla = escuchador;
        try {
            mediaPlayerVoz.reset();
            mediaPlayerVoz.setDataSource(activity,
                    Uri.parse("android.resource://" + activity.getPackageName() + "/" + idRecurso));
            mediaPlayerVoz.prepare();
            sincronizaBoca();
            habla();
        }
        catch(Exception e){
            Log.e("AjedrezEjercicioBase", e.toString());
        }
    }

    public void habla() {
        if (mediaPlayerVoz != null) mediaPlayerVoz.start();
        if (visualizerVoz != null) visualizerVoz.setEnabled(true);
    }

    public void calla() {
        if (mediaPlayerVoz != null) mediaPlayerVoz.pause();
        if (visualizerVoz != null) visualizerVoz.setEnabled(false);
        cierraBoca();
    }

    public void setMediaPlayerVoz(MediaPlayer mediaPlayerVoz) {
        this.mediaPlayerVoz = mediaPlayerVoz;
        sincronizaBoca();
    }

    private void sincronizaBoca(){
        this.mediaPlayerVoz.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            public void onCompletion(MediaPlayer mediaPlayer) {
                visualizerVoz.setEnabled(false);
                if (onAvatarHabla!=null) {
                    onAvatarHabla.onTerminaHabla();
                }
            }
        });
        visualizerVoz = new Visualizer(mediaPlayerVoz.getAudioSessionId());
        visualizerVoz.setEnabled(false);
        visualizerVoz.setCaptureSize(Visualizer.getCaptureSizeRange()[0]);
    }

    private void actualizaOjos() {
        long now = System.currentTimeMillis();
        if (now - ultimoParpadeo > PERIODO_PARPADEO) {
            parpadea();
            ultimoParpadeo = System.currentTimeMillis();
            PERIODO_PARPADEO = (2 + random.nextInt(8)) * 1000;
        }
    }

    private void actualizaBoca() {
        if (visualizerVoz != null && visualizerVoz.getEnabled()) {
            byte[] bytes = new byte[visualizerVoz.getCaptureSize()];
            if (visualizerVoz.getWaveForm(bytes) != Visualizer.SUCCESS) return;
            int valor;
            int min=255;
            for(int i=0;i<bytes.length-1;i++) {
                valor=Math.abs(bytes[i]);
                min=Math.min(min, valor);
            }
            if (min>=127)
                cierraBoca();
            else
                mueveBoca();
        }
        else cierraBoca();
    }

    private void actualizaAvatar() {
        long now = System.currentTimeMillis();
        if (now - ultimaActualizacion < PERIODO_ACTUALIZACION) {
            return; // Salir si el período de proceso no se ha cumplido.
        }
        actualizaOjos();
        actualizaBoca();
        ultimaActualizacion = System.currentTimeMillis();
    }

    public ThreadAvatar getThread() {
        return thread;
    }

    public void pausar(){
       if (thread!=null) thread.pausar();
    }

    public void reanudar(){
        if (thread!=null) thread.reanudar();
    }

    class ThreadAvatar extends Thread {
        private boolean pausa, corriendo;

        public synchronized void pausar() {
            pausa = true;
            calla();
        }

        public synchronized void reanudar() {
            pausa = false;
            notify();
        }

        public ThreadAvatar() {
        }

        @Override
        public void run() {
            corriendo = true;
            while (corriendo) {
                actualizaAvatar();
                synchronized (this) {
                    while (pausa) {
                        try {
                            wait();
                        } catch (Exception e) {
                        }
                    }
                }
            }
        }
    }
}
