package es.upv.mmoviles.ajedrez;

import android.os.Bundle;
import android.preference.PreferenceActivity;

/**
 * Created by usuwi on 10/12/2016.
 */

public class Preferencias extends PreferenceActivity {
    @Override protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
       /* Código para mostrar preferencias sin Fragment
        addPreferencesFromResource(R.xml.preferencias);*/
        getFragmentManager().beginTransaction().replace(android.R.id.content,new PreferenciasFragment()).commit();
    }
}
