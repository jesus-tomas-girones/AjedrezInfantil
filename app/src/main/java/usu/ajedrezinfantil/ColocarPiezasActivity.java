package usu.ajedrezinfantil;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * Created by Jesús Tomás on 28/11/2016.
 */

public class ColocarPiezasActivity extends EjercicioBaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LinearLayout piezas = (LinearLayout)findViewById(R.id.piezas);
        piezas.setVisibility(View.VISIBLE);
        VistaAvatar avatar = (VistaAvatar) findViewById(R.id.vistaAvatar);
        avatar.setActividad(this);
    }

}
