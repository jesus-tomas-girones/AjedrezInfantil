package es.upv.mmoviles.ajedrez;

import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.MediaController;
import android.widget.VideoView;

import static es.upv.mmoviles.ajedrez.R.id.capitulo1;

/**
 * Created by usuwi on 05/12/2016.
 */

public class VerVideo extends AppCompatActivity {

    private VideoView miVideo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ver_video);
        miVideo = (VideoView)findViewById(R.id.video1);
        Uri uri = Uri.parse("android.resource://"+ getPackageName() +"/" + R.raw.capitulo1);
        miVideo.setVideoURI(uri);       // miVideo.setVideoPath();
        miVideo.setMediaController(new MediaController(this));
        miVideo.start();
    }

    @Override
    protected void onResume() {
        miVideo.pause();
        super.onResume();

    }

    @Override
    protected void onStart() {
        super.onStart();
        miVideo.start();
    }
}
