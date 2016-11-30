package usu.ajedrezinfantil;

import android.content.ClipData;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * Created by Jesús Tomás on 21/11/2016.
 */

public class EjercicioBaseActivity extends AppCompatActivity {
    private VistaAvatar avatar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tablero);
        asignaListeners();
        avatar = (VistaAvatar) findViewById(R.id.vistaAvatar);
        avatar.setActividad(this);
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

    public VistaAvatar getAvatar() {
        return avatar;
    }

    void asignaListeners() {
        MiTouchListener touchListener = new MiTouchListener();
        LinearLayout piezas = (LinearLayout) findViewById(R.id.piezas);
        for (int i = 0, iMax = piezas.getChildCount(); i < iMax; i++) {
            LinearLayout linearLayout = (LinearLayout) piezas.getChildAt(i);
            View vista = linearLayout.getChildAt(0);
            if (vista instanceof ImageView) {
                ImageView imagen = (ImageView) vista;
                imagen.setOnTouchListener(touchListener);
            }
        }
        MiDragListener dragListener = new MiDragListener();
        LinearLayout tabla = (LinearLayout) findViewById(R.id.tabla);
        for (int i = 1, iMax = tabla.getChildCount() - 1; i < iMax; i++) {
            //Para cada fila (de 0 a 9) obtenemos la vista
            View vista = tabla.getChildAt(i);
            //Instance of es una comprobación para preguntar si un objeto es una instancia de una clase que le preguntemos
            //¿Es vista una instancia de LinearLayout?
            if (vista instanceof LinearLayout) {
                LinearLayout fila = (LinearLayout) vista;
                for (int j = 1, jMax = fila.getChildCount() - 1; j < jMax; j++) {
                    View vista2 = (ImageView) fila.getChildAt(j);
                    if (vista2 instanceof ImageView) {
                        ImageView imagen = (ImageView) vista2;
                        //A cada casilla le damos la opción de poder clickar en ella con la imagen para arrastrarla a otra casilla
                        imagen.setOnDragListener(dragListener);
                        imagen.setOnTouchListener(touchListener);
                    }
                }
            }
        }
    }

    class MiTouchListener implements View.OnTouchListener {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                ClipData data = ClipData.newPlainText("", "");
//                ClipData data = ClipData.newPlainText("ColorFondo", v.getBackground());
                //Construimos la sombra de arrastre de la Vista

/*                ImageView sombra = new ImageView(EjercicioBaseActivity.this);
//                sombra.setImageResource(R.mipmap.ic_launcher);
                sombra.setImageDrawable(((ImageView) v).getDrawable());
                sombra.getDrawable().setBounds(0, 0, v.getWidth(), v.getHeight());
*/
//                ImageView peon = (ImageView)findViewById(R.id.peon);

                //v.setBackgroundColor(Color.TRANSPARENT);
                MiDragShadowBuilder shadowBuilder = new MiDragShadowBuilder(v); //sombra);

//                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(new View(getApplicationContext()));

//                shadowBuilder.getView().setBackgroundColor(Color.TRANSPARENT);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    v.startDragAndDrop(data, shadowBuilder, v, 0);
                } else {
                    v.startDrag(data, shadowBuilder, v, 0);
                }
                return true; //Indicamos que hemos gestionado el evento.
            } else {
                return false;
            }
        }
    }

    private static class MiDragShadowBuilder extends View.DragShadowBuilder {

        // The drag shadow image, defined as a drawable thing
        private static Drawable shadow;

        // Defines the constructor for myDragShadowBuilder
        public MiDragShadowBuilder(View v) {

            // Stores the View parameter passed to myDragShadowBuilder.
            super(v);

            // Creates a draggable image that will fill the Canvas provided by the system.
            shadow = ((ImageView) v).getDrawable();
            if (shadow == null) {
                shadow = new ColorDrawable(Color.TRANSPARENT);
            }
        }

        // Defines a callback that sends the drag shadow dimensions and touch point back to the
        // system.
        @Override
        public void onProvideShadowMetrics(Point size, Point touch) {
            // Defines local variables
            int width, height;

            // Sets the width of the shadow to half the width of the original View
            width = getView().getWidth();

            // Sets the height of the shadow to half the height of the original View
            height = getView().getHeight();

            // The drag shadow is a ColorDrawable. This sets its dimensions to be the same as the
            // Canvas that the system will provide. As a result, the drag shadow will fill the
            // Canvas.
            shadow.setBounds(0, 0, width, height);

            // Sets the size parameter's width and height values. These get back to the system
            // through the size parameter.
            size.set(width, height);

            // Sets the touch point's position to be in the middle of the drag shadow
            touch.set(width / 2, height / 2);
        }

        // Defines a callback that draws the drag shadow in a Canvas that the system constructs
        // from the dimensions passed in onProvideShadowMetrics().
        @Override
        public void onDrawShadow(Canvas canvas) {
            // Draws the ColorDrawable in the Canvas passed in from the system.
            shadow.draw(canvas);
        }
    }

    //Listener que establece el evento onDrag de una vista
    class MiDragListener implements View.OnDragListener {

        @Override
        public boolean onDrag(View v, DragEvent event) {
            ImageView vistaDestino = (ImageView) v;
            ImageView vistaOrigen = (ImageView) event.getLocalState();
            int accion = event.getAction();
            switch (accion) {
                case DragEvent.ACTION_DRAG_STARTED:
                    break;
                case DragEvent.ACTION_DRAG_LOCATION:
                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    break;
                case DragEvent.ACTION_DRAG_ENTERED:
                    break;
                case DragEvent.ACTION_DROP:
                    //Log.i("Script", vistaDestino.getTag() + "- ACTION_DROP");
                    boolean movimientoValido;
                    if ((vistaOrigen.getTag() == null) ||
                            (vistaDestino.getTag() == null)) {
                        movimientoValido = true;     // No podemos aplicar validación de movimiento
                    } else {
                        String origen = vistaOrigen.getTag().toString();
                        String destino = vistaDestino.getTag().toString();
                        int colDestino = destino.charAt(0) - 'A';
                        int filaDestino = destino.charAt(1) - '1';
                        if (origen.charAt(0) == 'P'){ //Arrastramos una pieza de fuera al tablero

                            movimientoValido =
                                    (vistaDestino.getDrawable() == null) &&  //No hay una pieza ya colocada
                                    onColocar(origen.charAt(1), colDestino, filaDestino); //La posición es correcta
                        } else {                      // Arrastramos una pieza del tablero al tablero
                            int colOrigen = origen.charAt(0) - 'A';
                            int filaOrigen = origen.charAt(1) - '1';
                            movimientoValido = onMovimiento(colOrigen, filaOrigen, colDestino, filaDestino);
                        }
                    }
                    if (movimientoValido && (vistaOrigen != vistaDestino)) {
                        if (vistaOrigen.getDrawable() != null) {
                            vistaDestino.setImageDrawable(vistaOrigen.getDrawable());
                        }
                        if ((vistaOrigen.getTag() != null) &&
                            (vistaOrigen.getTag().toString().charAt(0) != 'P')) {
                            vistaOrigen.setImageDrawable(null);
                        }
                        vistaDestino.invalidate();
                    }
                case DragEvent.ACTION_DRAG_ENDED:
                    //Log.i("Script", "- ACTION_DRAG_ENDED");
                    //vistaDestino.invalidate();
                default:
                    break;
            }
            return true;
        }
    }

    /**
     * método ha de ser sobreescrito por los descencientes para comprobar que se ha
     * producido un movimiento y para validarlo
     *
     * @param colOrigen   columna origen "A" -> 0, "B· ->1, ...
     * @param filaOrigen  fila origen "1" -> 0, "2" ->1, ...
     * @param colDestino  columna destino
     * @param filaDestino fila destino
     * @return true: validamos movimiento, false: lo anulamos
     */
    protected boolean onMovimiento(int colOrigen, int filaOrigen, int colDestino, int filaDestino) {
        return true;
    }

    /**
     * método ha de ser sobreescrito por los descencientes para comprobar que se ha
     * producido una colocación de pieza desde fuera y para validarla
     *
     * @param pieza  "T" -> Torre, "A" -> Alfil, ...
     * @param colDestino  columna destino
     * @param filaDestino fila destino
     * @return true: validamos movimiento, false: lo anulamos
     */
    protected boolean onColocar(char pieza, int colDestino, int filaDestino) {
        return true;
    }

    boolean funcion(int colOrigen, int filaOrigen, int colDestino, int filaDestino) {
        return (filaOrigen == filaDestino) || (colOrigen == colDestino) || //misma fila o columna
                (Math.abs(filaOrigen - filaDestino) == Math.abs(colOrigen - colDestino)); //misma diagonal
    }

    //Todo: terminar implementar resaltaCasilla
    void resaltarCasilla(int colOrigen, int filaOrigen, boolean _funcion) { //Este parámetro se pasa con un landa
        LinearLayout tabla = (LinearLayout) findViewById(R.id.tabla);
        for (int f = 1, iMax = tabla.getChildCount() - 1; f < iMax; f++) {
            //Para cada fila (de 0 a 9) obtenemos la vista
            View vista = tabla.getChildAt(f);
            //Instance of es una comprobación para preguntar si un objeto es una instancia de una clase que le preguntemos
            //¿Es vista una instancia de LinearLayout?
            if (vista instanceof LinearLayout) {
                LinearLayout fila = (LinearLayout) vista;
                for (int c = 1, jMax = fila.getChildCount() - 1; c < jMax; c++) {
                    View vista2 = (ImageView) fila.getChildAt(c);
                    if (vista2 instanceof ImageView) {
                        ImageView imagen = (ImageView) vista2;
                        if (funcion(colOrigen, filaOrigen, c-1, f-1)) {

                            //Todo: Crear una animación
                            imagen.startAnimation(null); //
                        }
                    }
                }
            }
        }
    }


}



