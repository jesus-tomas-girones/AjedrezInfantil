package usu.ajedrezinfantil;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * Created by raulvibo on 28/10/2016.
 */

public class TareaA1Activity extends AppCompatActivity {
    private VistaAvatar avatar;
    private final int REQUEST_RECORD_AUDIO = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tarea_a1);
        avatar = (VistaAvatar) findViewById(R.id.vistaAvatar);
        avatar.setActividad(this);

        Spinner spinnerLecciones = (Spinner) findViewById(R.id.spinner_lecciones);
        spinnerLecciones.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                configuraVoz();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        Spinner spinnerMiradas = (Spinner) findViewById(R.id.spiner_miradas);
        spinnerMiradas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                configuraMirada();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
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

    public void habla(View view) {
        avatar.habla();
    }

    public void calla(View view) {
        avatar.calla();
    }

    public void levantaCejas(View view) {
        avatar.levantaCejas();
    }

    public void parpadea(View view) {
        avatar.parpadea();
    }

    void configuraVoz() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED) {
            Spinner spinnerLecciones = (Spinner) findViewById(R.id.spinner_lecciones);
            if (spinnerLecciones == null) return;
            String opcion = spinnerLecciones.getSelectedItem().toString();
            if (opcion.equals("Presentación")) avatar.habla(R.raw.presentacion);
            else if (opcion.equals("Lección 1")) avatar.habla(R.raw.leccion1);
            else if (opcion.equals("Lección 2")) avatar.habla(R.raw.leccion2);
            else if (opcion.equals("Lección 3")) avatar.habla(R.raw.leccion3);
        } else {
            solicitarPermisoRecordAudio();
        }
    }

    void configuraMirada() {
        Spinner spinnerMiradas = (Spinner) findViewById(R.id.spiner_miradas);
        if (spinnerMiradas == null) return;
        String opcion = spinnerMiradas.getSelectedItem().toString();
        if (opcion.equals("centro")) avatar.setMirada(VistaAvatar.DireccionMirada.CENTER);
        else if (opcion.equals("izquierda"))
            avatar.setMirada(VistaAvatar.DireccionMirada.LEFT_CENTER);
        else if (opcion.equals("derecha"))
            avatar.setMirada(VistaAvatar.DireccionMirada.RIGHT_CENTER);
    }

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
                    ActivityCompat.requestPermissions(TareaA1Activity.this, new String[]{
                            Manifest.permission.RECORD_AUDIO}, REQUEST_RECORD_AUDIO);
                }
            }).show();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, REQUEST_RECORD_AUDIO);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == REQUEST_RECORD_AUDIO) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                configuraVoz();
            } else {
                Snackbar snackbar = Snackbar.make(avatar, "Sin el permiso grabación de audio,\n"
                        + "no puedo mostrarte el avatar hablando.", Snackbar.LENGTH_LONG);
                View snackbarView = snackbar.getView();
                TextView textView = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
                textView.setMaxLines(2);
                snackbar.show();
            }
        }
    }
}
