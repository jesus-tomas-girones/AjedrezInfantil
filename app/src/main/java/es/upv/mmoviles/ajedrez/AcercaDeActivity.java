package es.upv.mmoviles.ajedrez;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Created by usuwi on 09/12/2016.
 */

public class AcercaDeActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acerca_de);

        Typeface fuente = Typeface.createFromAsset(getAssets(), "fonts/BalooPaaji-Regular.ttf");
        TextView acercaDeDialog = (TextView)findViewById(R.id.acercade);
        //acercaDeDialog.setTypeface(fuente);
        TextView tituloCreditosApp = (TextView)findViewById(R.id.tituloCreditosApp);
        //tituloCreditosApp.setTypeface(fuente);
        TextView textoCreditosApp = (TextView)findViewById(R.id.textoCreditosApp);
        //textoCreditosApp.setTypeface(fuente);
        TextView tituloCreditosVideo = (TextView)findViewById(R.id.tituloCreditosVideo);
        //tituloCreditosVideo.setTypeface(fuente);
        TextView textoCreditosVideo = (TextView)findViewById(R.id.textoCreditosVideo);
        //textoCreditosVideo.setTypeface(fuente);

    }
}
