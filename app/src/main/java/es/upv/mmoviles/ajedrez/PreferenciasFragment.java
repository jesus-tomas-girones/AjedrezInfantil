package es.upv.mmoviles.ajedrez;

import android.os.Bundle;
import android.preference.PreferenceFragment;

/**
 * Created by usuwi on 10/12/2016.
 */

    public class PreferenciasFragment extends PreferenceFragment {


    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferencias);
    }
}
