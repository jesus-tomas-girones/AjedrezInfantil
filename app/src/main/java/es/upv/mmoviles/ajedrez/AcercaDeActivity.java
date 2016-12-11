package es.upv.mmoviles.ajedrez;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

/**
 * Created by usuwi on 09/12/2016.
 */

public class AcercaDeActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acerca_de);

        Typeface fuente = Typeface.createFromAsset(getAssets(),"fonts/CabinSketch-Bold.ttf");
        Typeface fuente2 = Typeface.createFromAsset(getAssets(),"fonts/IndieFlower.ttf");
        Typeface fuente1 = Typeface.createFromAsset(getAssets(),"fonts/LoveYaLikeASister.ttf");
        Typeface fuente3 = Typeface.createFromAsset(getAssets(),"fonts/Dekko-Regular.ttf");


        TextView acercaDeDialog = (TextView)findViewById(R.id.acercade);
        acercaDeDialog.setTypeface(fuente3);

        TextView linkAndroidCurso = (TextView)findViewById(R.id.linkAndroidCurso);
        linkAndroidCurso.setTypeface(fuente3);
        linkAndroidCurso.setMovementMethod(LinkMovementMethod.getInstance());

        TextView acercaDe2Dialog = (TextView)findViewById(R.id.acercade2);
        acercaDe2Dialog.setTypeface(fuente3);

        TextView linkCanal = (TextView)findViewById(R.id.linkCanal);
        linkCanal.setTypeface(fuente3);
        linkCanal.setMovementMethod(LinkMovementMethod.getInstance());



        TextView tituloCreditosApp = (TextView)findViewById(R.id.tituloCreditosApp);
        tituloCreditosApp.setTypeface(fuente1);

        TextView tituloCreditosAppProgramacion = (TextView)findViewById(R.id.tituloCreditosAppProgramacion);
        tituloCreditosAppProgramacion.setTypeface(fuente);
        TextView textoCreditosAppProgramacion = (TextView)findViewById(R.id.textoCreditosAppProgramacion);
        textoCreditosAppProgramacion.setTypeface(fuente2);


        TextView tituloCreditosAppDiseño = (TextView)findViewById(R.id.tituloCreditosAppDiseño);
        tituloCreditosAppDiseño.setTypeface(fuente);
        TextView textoCreditosAppDiseño = (TextView)findViewById(R.id.textoCreditosAppDiseño);
        textoCreditosAppDiseño.setTypeface(fuente2);

        TextView tituloCreditosAppIdea = (TextView)findViewById(R.id.tituloCreditosAppIdea);
        tituloCreditosAppIdea.setTypeface(fuente);
        TextView textoCreditosAppIdea = (TextView)findViewById(R.id.textoCreditosAppIdea);
        textoCreditosAppIdea.setTypeface(fuente2);

        TextView tituloCreditosAppGuion = (TextView)findViewById(R.id.tituloCreditosAppGuion);
        tituloCreditosAppGuion.setTypeface(fuente);
        TextView textoCreditosAppGuion = (TextView)findViewById(R.id.textoCreditosAppGuion);
        textoCreditosAppGuion.setTypeface(fuente2);


        TextView tituloCreditosAppDireccion = (TextView)findViewById(R.id.tituloCreditosAppDireccion);
        tituloCreditosAppDireccion.setTypeface(fuente);
        TextView textoCreditosAppDireccion = (TextView)findViewById(R.id.textoCreditosAppDireccion);
        textoCreditosAppDireccion.setTypeface(fuente2);



        TextView tituloCreditosVideo = (TextView)findViewById(R.id.tituloCreditosVideo);
        tituloCreditosVideo.setTypeface(fuente1);

        TextView  tituloCreditosVideoGuion = (TextView)findViewById(R.id.tituloCreditosVideoGuion);
        tituloCreditosVideoGuion.setTypeface(fuente);
        TextView  textoCreditosVideoGuion = (TextView)findViewById(R.id.textoCreditosVideoGuion);
        textoCreditosVideoGuion.setTypeface(fuente2);

        TextView  tituloCreditosVideoIdea= (TextView)findViewById(R.id.tituloCreditosVideoIdea);
        tituloCreditosVideoIdea.setTypeface(fuente);
        TextView  textoCreditosVideoIdea= (TextView)findViewById(R.id.textoCreditosVideoIdea);
        textoCreditosVideoIdea.setTypeface(fuente2);

        TextView  tituloCreditosVideoDiseño= (TextView)findViewById(R.id.tituloCreditosVideoDiseño);
        tituloCreditosVideoDiseño.setTypeface(fuente);
        TextView  textoCreditosVideoDiseño= (TextView)findViewById(R.id.textoCreditosVideoDiseño);
        textoCreditosVideoDiseño.setTypeface(fuente2);

        TextView  tituloCreditosVideoAnimacion= (TextView)findViewById(R.id.tituloCreditosVideoAnimacion);
        tituloCreditosVideoAnimacion.setTypeface(fuente);
        TextView  textoCreditosVideoAnimacion= (TextView)findViewById(R.id.textoCreditosVideoAnimacion);
        textoCreditosVideoAnimacion.setTypeface(fuente2);

        TextView  tituloCreditosVideoVoces= (TextView)findViewById(R.id.tituloCreditosVideoVoces);
        tituloCreditosVideoVoces.setTypeface(fuente);
        TextView  textoCreditosVideoVoces= (TextView)findViewById(R.id.textoCreditosVideoVoces);
        textoCreditosVideoVoces.setTypeface(fuente2);

        TextView  tituloCreditosVideoSupervision= (TextView)findViewById(R.id.tituloCreditosVideoSuperVision);
        tituloCreditosVideoSupervision.setTypeface(fuente);
        TextView  textoCreditosVideoSupervision= (TextView)findViewById(R.id.textoCreditosVideoSuperVision);
        textoCreditosVideoSupervision.setTypeface(fuente2);





    }
}
