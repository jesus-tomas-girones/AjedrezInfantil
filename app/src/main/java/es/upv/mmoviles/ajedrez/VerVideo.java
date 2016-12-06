package es.upv.mmoviles.ajedrez;


import android.os.Bundle;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;



/**
 * Created by usuwi on 05/12/2016.
 */

public class VerVideo extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {

    private static final String API_KEY = " "; //Añadir vuestra clave para YoutubeApi
    private String VIDEO_ID;

    // Para
    // private VideoView miVideo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ver_video);
        Bundle bundle = getIntent().getExtras();

        if(bundle.getString("video_id")!= null){
            VIDEO_ID=bundle.getString("video_id");
        }

        YouTubePlayerView youTubePlayerView = (YouTubePlayerView)findViewById(R.id.youtubeplayer);
        youTubePlayerView.initialize(API_KEY,this);

        /* Con MediaPlayer
               miVideo = (VideoView)findViewById(R.id.video1);
        Uri uri = Uri.parse("rtsp://r1---sn-4g5e6n7k.googlevideo.com/Cj0LENy73wIaNAn9LpUFFbtzsxMYDSANFC3NcEZYMOCoAUIASARgku7M1JfZgKJYigELRG54cFhIRGxaS2cM/C532DA2B01733A61100B2BEB2BEAEAA78790D8C7.87334694152A400DFEA13E3F45B61017ADECE327/yt6/1/video.3gp");
        miVideo.setVideoURI(uri);       // miVideo.setVideoPath();
        miVideo.setMediaController(new MediaController(this));
        miVideo.start();*/
    }

    @Override
    protected void onResume() {
      //  miVideo.pause();
        super.onResume();

    }

    @Override
    protected void onStart() {
        super.onStart();
      //  miVideo.start();
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean wasRestored) {
        youTubePlayer.setPlayerStateChangeListener(playerStateChangeListener);
        youTubePlayer.setPlaybackEventListener(playerbackEventListener);

        if(!wasRestored){
            youTubePlayer.cueVideo(VIDEO_ID);
        }

    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

    }

    private YouTubePlayer.PlaybackEventListener playerbackEventListener = new YouTubePlayer.PlaybackEventListener() {
        @Override
        public void onPlaying() {

        }

        @Override
        public void onPaused() {

        }

        @Override
        public void onStopped() {

        }

        @Override
        public void onBuffering(boolean b) {

        }

        @Override
        public void onSeekTo(int i) {

        }
    };

    private YouTubePlayer.PlayerStateChangeListener playerStateChangeListener = new YouTubePlayer.PlayerStateChangeListener() {
        @Override
        public void onLoading() {

        }

        @Override
        public void onLoaded(String s) {

        }

        @Override
        public void onAdStarted() {

        }

        @Override
        public void onVideoStarted() {

        }

        @Override
        public void onVideoEnded() {

        }

        @Override
        public void onError(YouTubePlayer.ErrorReason errorReason) {

        }


    };
}
