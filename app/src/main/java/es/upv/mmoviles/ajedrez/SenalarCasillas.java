package es.upv.mmoviles.ajedrez;
import android.os.Bundle;
import android.widget.ImageView;
import java.util.Random;
import java.util.Vector;



public class SenalarCasillas extends EjercicioBaseActivity {
    private Vector<String> vectorCoordenadas;
    private String coordenadaSolicitada;
    private int audioCoordenadaSolicitada;
    private int coordenadasAcertadas;
    private Random random;
    private VistaAvatar avatar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        vectorCoordenadas = new Vector<String>();
        vectorCoordenadas.add("A3");
        vectorCoordenadas.add("B4");
        vectorCoordenadas.add("C8");
        vectorCoordenadas.add("D1");
        vectorCoordenadas.add("E3");
        vectorCoordenadas.add("F2");
        vectorCoordenadas.add("G6");
        vectorCoordenadas.add("H5");
        random = new Random(System.currentTimeMillis());
        avatar = getAvatar();
        coordenadasAcertadas = 0;
        avatar.habla(R.raw.senyala_casilla_presentacion, new VistaAvatar.OnAvatarHabla() {
            @Override
            public void onTerminaHabla() {
                seleccionaCoordenada();
            }
        });
    }

    public void seleccionaCoordenada() {
        int indice = random.nextInt(vectorCoordenadas.size());
        coordenadaSolicitada = vectorCoordenadas.elementAt(indice);
        vectorCoordenadas.remove(indice); // Elimino la coordenada para no repetirla.
        audioCoordenadaSolicitada = this.getResources().getIdentifier("senyala_casilla_" + coordenadaSolicitada.toLowerCase(), "raw", this.getPackageName());
        preguntaCoordenada();
    }

    public void preguntaCoordenada() {
        avatar.habla(audioCoordenadaSolicitada, new VistaAvatar.OnAvatarHabla() {
            @Override
            public void onTerminaHabla() {
                avatar.reproduceEfectoSonido(VistaAvatar.EfectoSonido.TIC_TAC);
                empiezaCuentaAtras();
            }
        });
    }

    @Override
    protected void onFinalCuentaAtras() {
        avatar.habla(audioCoordenadaSolicitada, new VistaAvatar.OnAvatarHabla() {
            @Override
            public void onTerminaHabla() {
                avatar.mueveOjos(VistaAvatar.MovimientoOjos.DERECHA);
                avatar.reproduceEfectoSonido(VistaAvatar.EfectoSonido.TIC_TAC);
                empiezaCuentaAtras();
            }
        });
    }

    @Override
    protected boolean onPulsar(ImageView imageView) {
        if (coordenadaSolicitada == null) return false;
        cancelaCuentaAtras();
        avatar.paraEfectoSonido(VistaAvatar.EfectoSonido.TIC_TAC);
        avatar.mueveOjos(VistaAvatar.MovimientoOjos.DERECHA);

        int colCoordenadaSolicitada = coordenadaSolicitada.charAt(0) - 'A';
        int filaCoordenadaSolicitada = coordenadaSolicitada.charAt(1) - '1';
        resaltarCasilla(colCoordenadaSolicitada, filaCoordenadaSolicitada, Movimiento.CORRECTO);

        String coordenadaPulsada = imageView.getTag().toString();
        if (coordenadaPulsada.equals(coordenadaSolicitada)) {
            coordenadasAcertadas++;
            avatar.mueveCejas(VistaAvatar.MovimientoCejas.ARQUEAR);
            switch (coordenadasAcertadas) {
                case 1:
                    avatar.reproduceEfectoSonido(VistaAvatar.EfectoSonido.MOVIMIENTO_CORRECTO);
                    avatar.lanzaAnimacion(VistaAvatar.Animacion.MOVIMIENTO_CORRECTO);
                    avatar.habla(R.raw.ok_has_acertado, new VistaAvatar.OnAvatarHabla() {
                        @Override
                        public void onTerminaHabla() {
                            seleccionaCoordenada();
                        }
                    });
                    break;

                case 2:
                    avatar.reproduceEfectoSonido(VistaAvatar.EfectoSonido.MOVIMIENTO_CORRECTO);
                    avatar.lanzaAnimacion(VistaAvatar.Animacion.MOVIMIENTO_CORRECTO);
                    avatar.habla(R.raw.ok_muy_bien, new VistaAvatar.OnAvatarHabla() {
                        @Override
                        public void onTerminaHabla() {
                            seleccionaCoordenada();
                        }
                    });
                    break;

                case 3:
                    avatar.reproduceEfectoSonido(VistaAvatar.EfectoSonido.EJERCICIO_SUPERADO);
                    avatar.lanzaAnimacion(VistaAvatar.Animacion.EJERCICIO_SUPERADO);
                    avatar.habla(R.raw.ok_superado, new VistaAvatar.OnAvatarHabla() {
                        @Override
                        public void onTerminaHabla() {
                            finish();
                        }
                    });
                    break;
            }
        } else {
            avatar.reproduceEfectoSonido(VistaAvatar.EfectoSonido.MOVIMIENTO_INCORRECTO);
            avatar.lanzaAnimacion(VistaAvatar.Animacion.MOVIMIENTO_INCORRECTO);
            avatar.mueveCejas(VistaAvatar.MovimientoCejas.FRUNCIR);
            int colCoordenadaPulsada = coordenadaPulsada.charAt(0) - 'A';
            int filaCoordenadaPulsada = coordenadaPulsada.charAt(1) - '1';
            resaltarCasilla(colCoordenadaPulsada, filaCoordenadaPulsada, Movimiento.INCORRECTO);
            preguntaCoordenada();
        }
        return true;
    }
}