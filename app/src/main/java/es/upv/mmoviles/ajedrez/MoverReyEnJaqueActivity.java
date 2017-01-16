package es.upv.mmoviles.ajedrez;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.Random;
import java.util.Vector;

import static es.upv.mmoviles.ajedrez.Pieza.Color.BLANCO;
import static es.upv.mmoviles.ajedrez.Pieza.Color.NEGRO;
import static es.upv.mmoviles.ajedrez.Pieza.Tipo.ALFIL;
import static es.upv.mmoviles.ajedrez.Pieza.Tipo.CABALLO;
import static es.upv.mmoviles.ajedrez.Pieza.Tipo.DAMA;
import static es.upv.mmoviles.ajedrez.Pieza.Tipo.PEON;
import static es.upv.mmoviles.ajedrez.Pieza.Tipo.REY;
import static es.upv.mmoviles.ajedrez.Pieza.Tipo.TORRE;

public class MoverReyEnJaqueActivity extends EjercicioBaseActivity {
    private VistaAvatar avatar;
    private int contadorMovimientos = 0;
    private Vector<Pieza> vectorPiezasBlancas;
    private Vector<Pieza> vectorPiezasNegras;
    private Vector<Pieza> vectorPiezasBlancasDefensoras; // Que pueden evitar el jaque al Rey.
    private Vector<Pieza> vectorPiezasNegrasAtacantes; // Que hacen jaque al Rey.
    private Random random;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        vectorPiezasBlancas = new Vector<>();
        vectorPiezasBlancasDefensoras = new Vector<>();
        vectorPiezasNegras = new Vector<>();
        vectorPiezasNegrasAtacantes = new Vector<>();
        inicializaJugada(contadorMovimientos);

        avatar = getAvatar();
        avatar.habla(R.raw.mover_rey_en_jaque, new VistaAvatar.OnAvatarHabla() {
            @Override
            public void onTerminaHabla() {
                avatar.mueveOjos(VistaAvatar.MovimientoOjos.DERECHA);
                avatar.reproduceEfectoSonido(VistaAvatar.EfectoSonido.TIC_TAC);
                empiezaCuentaAtras();
            }
        });
    }

    private Validador validadorPeon = new Validador() {
        @Override
        public boolean movimientoValido(int colOrigen, int filaOrigen, int colDestino, int filaDestino) {
            boolean esPeon = (getTipoPieza(colOrigen, filaOrigen) == PEON);
            boolean esBlanco = (getColorPieza(colOrigen, filaOrigen) == BLANCO);
            boolean diferenteCasilla = (colOrigen != colDestino) || (filaOrigen != filaDestino);
            boolean avanzaUno = ((esBlanco && (filaDestino == filaOrigen + 1))
                    || (!esBlanco && (filaDestino == filaOrigen - 1))
                    && (colDestino == colOrigen));
            return (esPeon && diferenteCasilla && avanzaUno);
        }
    };

    private Validador validadorCaballo = new Validador() {
        @Override
        public boolean movimientoValido(int colOrigen, int filaOrigen, int colDestino, int filaDestino) {
            boolean esCaballo = (getTipoPieza(colOrigen, filaOrigen) == CABALLO);
            boolean diferenteCasilla = (colOrigen != colDestino) || (filaOrigen != filaDestino);
            boolean vertical2horizintal1 = (Math.abs(filaOrigen - filaDestino) == 2)
                    && (Math.abs(colOrigen - colDestino) == 1);
            boolean vertical1horizintal2 = (Math.abs(filaOrigen - filaDestino) == 1)
                    && (Math.abs(colOrigen - colDestino) == 2);
            return (esCaballo && diferenteCasilla && (vertical2horizintal1 || vertical1horizintal2));
        }
    };

    private Validador validadorAlfil = new Validador() {
        @Override
        public boolean movimientoValido(int colOrigen, int filaOrigen, int colDestino, int filaDestino) {
            boolean esAlfil = (getTipoPieza(colOrigen, filaOrigen) == ALFIL);
            boolean diferenteCasilla = (colOrigen != colDestino) || (filaOrigen != filaDestino);
            boolean mismaDiagonal = (Math.abs(filaOrigen - filaDestino) == Math.abs(colOrigen - colDestino));
            boolean saltaPiezas = saltaPiezas(colOrigen, filaOrigen, colDestino, filaDestino);
            return (esAlfil && diferenteCasilla && mismaDiagonal && !saltaPiezas);
        }
    };

    private Validador validadorTorre = new Validador() {
        @Override
        public boolean movimientoValido(int colOrigen, int filaOrigen, int colDestino, int filaDestino) {
            boolean esTorre = (getTipoPieza(colOrigen, filaOrigen) == TORRE);
            boolean diferenteCasilla = (colOrigen != colDestino) || (filaOrigen != filaDestino);
            boolean mismaColumna = (colOrigen == colDestino);
            boolean mismaFila = (filaOrigen == filaDestino);
            boolean saltaPiezas = saltaPiezas(colOrigen, filaOrigen, colDestino, filaDestino);
            return (esTorre && diferenteCasilla && (mismaColumna || mismaFila) && !saltaPiezas);
        }
    };

    private Validador validadorDama = new Validador() {
        @Override
        public boolean movimientoValido(int colOrigen, int filaOrigen, int colDestino, int filaDestino) {
            boolean esDama = (getTipoPieza(colOrigen, filaOrigen) == DAMA);
            boolean diferenteCasilla = (colOrigen != colDestino) || (filaOrigen != filaDestino);
            boolean mismaColumna = (colOrigen == colDestino);
            boolean mismaFila = (filaOrigen == filaDestino);
            boolean mismaDiagonal = (Math.abs(filaOrigen - filaDestino) == Math.abs(colOrigen - colDestino));
            boolean saltaPiezas = saltaPiezas(colOrigen, filaOrigen, colDestino, filaDestino);
            return (esDama && diferenteCasilla && (mismaColumna || mismaFila || mismaDiagonal) && !saltaPiezas);
        }
    };

    private Validador validadorRey = new Validador() {
        @Override
        public boolean movimientoValido(int colOrigen, int filaOrigen, int colDestino, int filaDestino) {
            boolean esRey = (getTipoPieza(colOrigen, filaOrigen) == REY);
            boolean diferenteCasilla = (colOrigen != colDestino) || (filaOrigen != filaDestino);
            boolean distanciaUno = (Math.abs(filaOrigen - filaDestino) <= 1)
                    && (Math.abs(colOrigen - colDestino) <= 1);
            return (esRey && diferenteCasilla && distanciaUno);
        }
    };

    private Validador validadorGenerico = new Validador() {
        @Override
        public boolean movimientoValido(int colOrigen, int filaOrigen, int colDestino, int filaDestino) {
            boolean movimientoValidoBlancas = false;
            if (hayPieza(colOrigen, filaOrigen) && casillaDisponible(colOrigen, filaOrigen, colDestino, filaDestino)) {
                switch (getTipoPieza(colOrigen, filaOrigen)) {
                    case PEON:
                        movimientoValidoBlancas = validadorPeon.movimientoValido(colOrigen, filaOrigen, colDestino, filaDestino);
                        break;
                    case CABALLO:
                        movimientoValidoBlancas = validadorCaballo.movimientoValido(colOrigen, filaOrigen, colDestino, filaDestino);
                        break;
                    case ALFIL:
                        movimientoValidoBlancas = validadorAlfil.movimientoValido(colOrigen, filaOrigen, colDestino, filaDestino);
                        break;
                    case TORRE:
                        movimientoValidoBlancas = validadorTorre.movimientoValido(colOrigen, filaOrigen, colDestino, filaDestino);
                        break;
                    case DAMA:
                        movimientoValidoBlancas = validadorDama.movimientoValido(colOrigen, filaOrigen, colDestino, filaDestino);
                        break;
                    case REY:
                        movimientoValidoBlancas = validadorRey.movimientoValido(colOrigen, filaOrigen, colDestino, filaDestino);
                        break;
                }
            }
            return (movimientoValidoBlancas);
        }
    };

    private Validador validador = new Validador() {
        @Override
        public boolean movimientoValido(int colOrigen, int filaOrigen, int colDestino, int filaDestino) {
            boolean validador = false;
            Pieza pieza = getPieza(colOrigen, filaOrigen);

            if (pieza != null) {
                boolean movimientoValidoBlancas = validadorGenerico.movimientoValido(colOrigen, filaOrigen, colDestino, filaDestino);
                Log.d("Ajedrez Infantil", "***movimientoValidoBlancas=" + movimientoValidoBlancas);
                Pieza piezaDestino = getPieza(colDestino, filaDestino);
                if (piezaDestino != null && piezaDestino.getColor() == NEGRO) {
                    vectorPiezasNegras.remove(piezaDestino);
                }
                pieza.setCoordenada(colDestino, filaDestino);
                boolean reyEnJaque = reyEnJaque();
                Log.d("Ajedrez Infantil", "***reyEnJaque=" + reyEnJaque);
                validador = movimientoValidoBlancas && !reyEnJaque;
                pieza.setCoordenada(colOrigen, filaOrigen);
                if (piezaDestino != null && piezaDestino.getColor() == NEGRO) {
                    vectorPiezasNegras.add(piezaDestino);
                }
            }
            return (validador);
        }
    };

    private boolean reyEnJaque() {
        vectorPiezasNegrasAtacantes.removeAllElements();
        Pieza reyBlanco = vectorPiezasBlancas.get(0);
        Log.d("Ajedrez Infantil", "reyBlanco.getTipo()=" + reyBlanco.getTipo());
        for (int i = 0; i < vectorPiezasNegras.size(); i++) {
            Pieza piezaNegra = vectorPiezasNegras.get(i);
            Log.d("Ajedrez Infantil", "piezaNegra.getTipo()=" + piezaNegra.getTipo());
            switch (piezaNegra.getTipo()) {
                case PEON:
                    if (validadorPeon.movimientoValido(piezaNegra.getColumna(), piezaNegra.getFila(),
                            reyBlanco.getColumna(), reyBlanco.getFila())) {
                        vectorPiezasNegrasAtacantes.add(piezaNegra);
                    }
                    break;

                case CABALLO:
                    if (validadorCaballo.movimientoValido(piezaNegra.getColumna(), piezaNegra.getFila(),
                            reyBlanco.getColumna(), reyBlanco.getFila())) {
                        vectorPiezasNegrasAtacantes.add(piezaNegra);
                    }
                    break;

                case ALFIL:
                    if (validadorAlfil.movimientoValido(piezaNegra.getColumna(), piezaNegra.getFila(),
                            reyBlanco.getColumna(), reyBlanco.getFila())) {
                        vectorPiezasNegrasAtacantes.add(piezaNegra);
                    }
                    break;

                case TORRE:
                    if (validadorTorre.movimientoValido(piezaNegra.getColumna(), piezaNegra.getFila(),
                            reyBlanco.getColumna(), reyBlanco.getFila())) {
                        vectorPiezasNegrasAtacantes.add(piezaNegra);
                    }
                    break;

                case DAMA:
                    if (validadorDama.movimientoValido(piezaNegra.getColumna(), piezaNegra.getFila(),
                            reyBlanco.getColumna(), reyBlanco.getFila())) {
                        vectorPiezasNegrasAtacantes.add(piezaNegra);
                    }
                    break;

                case REY:
                    if (validadorRey.movimientoValido(piezaNegra.getColumna(), piezaNegra.getFila(),
                            reyBlanco.getColumna(), reyBlanco.getFila())) {
                        vectorPiezasNegrasAtacantes.add(piezaNegra);
                    }
                    break;
            }
        }
        return (vectorPiezasNegrasAtacantes.size() > 0);
    }

    private boolean reyEnJaqueMate() {
        vectorPiezasBlancasDefensoras.removeAllElements();
        for (int p = 0; p < vectorPiezasBlancas.size(); p++) {
            Pieza pieza = vectorPiezasBlancas.get(p);
            for (int c = 0; c < 8; c++) {
                for (int f = 0; f < 8; f++) {
                    if (validador.movimientoValido(pieza.getColumna(), pieza.getFila(), c, f)) {
                        vectorPiezasBlancasDefensoras.add(pieza);
                    }
                    ;
                }
            }
        }
        return (vectorPiezasBlancasDefensoras.size() == 0);
    }

    public boolean saltaPiezas(int colOrigen, int filaOrigen, int colDestino, int filaDestino) {
        boolean saltaPiezas = false;
        int distanciaCol = Math.abs(colOrigen - colDestino);
        int distanciaFila = Math.abs(filaOrigen - filaDestino);
        int distancia = Math.max(distanciaCol, distanciaFila);
        int col = colOrigen;
        int fila = filaOrigen;
        for (int i = 1; i < distancia && !saltaPiezas; i++) {
            if (col < colDestino) col += 1;
            if (col > colDestino) col -= 1;
            if (fila < filaDestino) fila += 1;
            if (fila > filaDestino) fila -= 1;
            saltaPiezas = hayPieza(col, fila);
        }
        return saltaPiezas;
    }

    public boolean capturaPieza(int colOrigen, int filaOrigen, int colDestino, int filaDestino) {
        Pieza piezaOrigen = getPieza(colOrigen, filaOrigen);
        Pieza piezaDestino = getPieza(colDestino, filaDestino);
        return (piezaOrigen != null && piezaDestino != null && piezaOrigen.getColor() != piezaDestino.getColor());
    }

    public boolean casillaDisponible(int colOrigen, int filaOrigen, int colDestino, int filaDestino) {
        return (!hayPieza(colDestino, filaDestino) || capturaPieza(colOrigen, filaOrigen, colDestino, filaDestino));
    }

    protected void retiraPiezas() {
        LinearLayout tabla = (LinearLayout) findViewById(R.id.tabla);
        for (int f = 1, iMax = tabla.getChildCount() - 1; f < iMax; f++) {
            View vista = tabla.getChildAt(f);
            if (vista instanceof LinearLayout) {
                LinearLayout linea = (LinearLayout) vista;
                for (int c = 1, jMax = linea.getChildCount() - 1; c < jMax; c++) {
                    ImageView imagen = (ImageView) linea.getChildAt(c);
                    imagen.setImageDrawable(null);
                    if (esCuadriculaNegra(imagen)) {
                        imagen.setBackgroundResource(R.color.cuadriculaNegra);
                    }
                    else {
                        imagen.setBackgroundResource(R.color.cuadriculaBlanca);
                    }
                }
            }
        }
    }

    public Pieza getPieza(int columna, int fila) {
        Pieza pieza = null;
        Vector<Pieza> vectorPiezas = new Vector<>();
        vectorPiezas.addAll(vectorPiezasBlancas);
        vectorPiezas.addAll(vectorPiezasNegras);
        for (int i = 0; i < vectorPiezas.size() && pieza == null; i++) {
            if (vectorPiezas.get(i).getColumna() == columna
                    && vectorPiezas.get(i).getFila() == fila) {
                pieza = vectorPiezas.get(i);
            }
        }
        return pieza;
    }

    public boolean hayPieza(int columna, int fila) {
        Pieza pieza = getPieza(columna, fila);
        return (pieza != null);
    }

    public void eliminaPieza(Pieza pieza) {
        vectorPiezasBlancas.removeElement(pieza);
        vectorPiezasNegras.removeElement(pieza);
    }

    public Pieza.Tipo getTipoPieza(int columna, int fila) {
        Pieza.Tipo tipo = null;
        Pieza pieza = getPieza(columna, fila);
        if (pieza != null) tipo = pieza.getTipo();
        return tipo;
    }

    public Pieza.Color getColorPieza(int columna, int fila) {
        Pieza.Color color = null;
        Pieza pieza = getPieza(columna, fila);
        if (pieza != null) color = pieza.getColor();
        return color;
    }

    public void colocaPieza(Pieza pieza) {
        int idImageView = getResources().getIdentifier(pieza.getCoordenada(), "id", getPackageName());
        ImageView imageView = (ImageView) findViewById(idImageView);
        int idDrawablePieza = getDrawablePieza(pieza);
        imageView.setImageResource(idDrawablePieza);
        Log.d("Ajedrez Infantil", "tipo=" + pieza.getTipo() + " color=" + pieza.getColor() + " coordenada=" + pieza.getCoordenada() + " getResourceName(idImageView)=" + getResources().getResourceName(idImageView));
    }

    public int getDrawablePieza(Pieza pieza) {
        int idDrawable = 0;
        if (pieza.getColor() == BLANCO) {
            switch (pieza.getTipo()) {
                case PEON:
                    idDrawable = R.drawable.peon_blanco;
                    break;
                case CABALLO:
                    idDrawable = R.drawable.caballo_blanco;
                    break;
                case ALFIL:
                    idDrawable = R.drawable.alfil_blanco;
                    break;
                case TORRE:
                    idDrawable = R.drawable.torre_blanca;
                    break;
                case DAMA:
                    idDrawable = R.drawable.dama_blanca;
                    break;
                case REY:
                    idDrawable = R.drawable.rey_blanco;
                    break;
            }
        } else {
            switch (pieza.getTipo()) {
                case PEON:
                    idDrawable = R.drawable.peon_negro;
                    break;
                case CABALLO:
                    idDrawable = R.drawable.caballo_negro;
                    break;
                case ALFIL:
                    idDrawable = R.drawable.alfil_negro;
                    break;
                case TORRE:
                    idDrawable = R.drawable.torre_negra;
                    break;
                case DAMA:
                    idDrawable = R.drawable.dama_negra;
                    break;
                case REY:
                    idDrawable = R.drawable.rey_negro;
                    break;
            }
        }
        return idDrawable;
    }

    private void colocaPiezas() {
        for (Pieza pieza : vectorPiezasBlancas) {
            colocaPieza(pieza);
        }
        for (Pieza pieza : vectorPiezasNegras) {
            colocaPieza(pieza);
        }
    }

    private void inicializaJugada(int jugada) {
        random = new Random(System.currentTimeMillis());
        retiraPiezas();
        Log.d("Ajedrez Infantil", "jugada=" + jugada);
        switch (jugada) {
            case 0:
                inicializaJugadaMoverRey();
                break;
            case 1:
                inicializaJugadaInterponerPieza();
                break;
            case 2:
                inicializaJugadaCapturaPieza();
                break;
        }
    }

    private void inicializaJugadaMoverRey() {
        vectorPiezasBlancas.removeAllElements();
        vectorPiezasNegras.removeAllElements();
        int variante = random.nextInt(4);
        switch (variante) {
            case 0:
                Pieza piezaBlanca1 = new Pieza(REY, BLANCO, "E1");
                vectorPiezasBlancas.add(piezaBlanca1);
                Pieza piezaNegra1 = new Pieza(REY, Pieza.Color.NEGRO, "E8");
                vectorPiezasNegras.add(piezaNegra1);
                Pieza piezaNegra2 = new Pieza(ALFIL, Pieza.Color.NEGRO, "A5");
                vectorPiezasNegras.add(piezaNegra2);
                break;
            case 1:
                piezaBlanca1 = new Pieza(REY, BLANCO, "F2");
                vectorPiezasBlancas.add(piezaBlanca1);
                piezaNegra1 = new Pieza(REY, Pieza.Color.NEGRO, "G8");
                vectorPiezasNegras.add(piezaNegra1);
                piezaNegra2 = new Pieza(TORRE, Pieza.Color.NEGRO, "C2");
                vectorPiezasNegras.add(piezaNegra2);
                break;
            case 2:
                piezaBlanca1 = new Pieza(REY, BLANCO, "D3");
                vectorPiezasBlancas.add(piezaBlanca1);
                piezaNegra1 = new Pieza(REY, Pieza.Color.NEGRO, "F7");
                vectorPiezasNegras.add(piezaNegra1);
                piezaNegra2 = new Pieza(DAMA, Pieza.Color.NEGRO, "D5");
                vectorPiezasNegras.add(piezaNegra2);
                break;
            case 3:
                piezaBlanca1 = new Pieza(REY, BLANCO, "A1");
                vectorPiezasBlancas.add(piezaBlanca1);
                piezaNegra1 = new Pieza(REY, Pieza.Color.NEGRO, "H7");
                vectorPiezasNegras.add(piezaNegra1);
                piezaNegra2 = new Pieza(CABALLO, Pieza.Color.NEGRO, "C2");
                vectorPiezasNegras.add(piezaNegra2);
                break;
        }
        colocaPiezas();
    }

    private void inicializaJugadaInterponerPieza() {
        vectorPiezasBlancas.removeAllElements();
        vectorPiezasNegras.removeAllElements();
        int variante = random.nextInt(3);
        switch (variante) {
            case 0:
                Pieza piezaBlanca1 = new Pieza(REY, BLANCO, "E1");
                vectorPiezasBlancas.add(piezaBlanca1);
                Pieza piezaBlanca2 = new Pieza(TORRE, BLANCO, "D1");
                vectorPiezasBlancas.add(piezaBlanca2);
                Pieza piezaNegra1 = new Pieza(REY, Pieza.Color.NEGRO, "E8");
                vectorPiezasNegras.add(piezaNegra1);
                Pieza piezaNegra2 = new Pieza(ALFIL, Pieza.Color.NEGRO, "A5");
                vectorPiezasNegras.add(piezaNegra2);
                Pieza piezaNegra3 = new Pieza(CABALLO, Pieza.Color.NEGRO, "G3");
                vectorPiezasNegras.add(piezaNegra3);
                Pieza piezaNegra4 = new Pieza(TORRE, Pieza.Color.NEGRO, "H2");
                vectorPiezasNegras.add(piezaNegra4);
                break;
            case 1:
                piezaBlanca1 = new Pieza(REY, BLANCO, "F2");
                vectorPiezasBlancas.add(piezaBlanca1);
                piezaBlanca2 = new Pieza(CABALLO, BLANCO, "G1");
                vectorPiezasBlancas.add(piezaBlanca2);
                piezaNegra1 = new Pieza(REY, Pieza.Color.NEGRO, "G8");
                vectorPiezasNegras.add(piezaNegra1);
                piezaNegra2 = new Pieza(TORRE, Pieza.Color.NEGRO, "C2");
                vectorPiezasNegras.add(piezaNegra2);
                piezaNegra3 = new Pieza(DAMA, Pieza.Color.NEGRO, "H3");
                vectorPiezasNegras.add(piezaNegra3);
                piezaNegra4 = new Pieza(CABALLO, Pieza.Color.NEGRO, "F5");
                vectorPiezasNegras.add(piezaNegra4);
                Pieza piezaNegra5 = new Pieza(ALFIL, Pieza.Color.NEGRO, "B4");
                vectorPiezasNegras.add(piezaNegra5);
                break;
            case 2:
                piezaBlanca1 = new Pieza(REY, BLANCO, "D3");
                vectorPiezasBlancas.add(piezaBlanca1);
                piezaBlanca1 = new Pieza(ALFIL, BLANCO, "B2");
                vectorPiezasBlancas.add(piezaBlanca1);
                piezaNegra1 = new Pieza(REY, Pieza.Color.NEGRO, "F7");
                vectorPiezasNegras.add(piezaNegra1);
                piezaNegra2 = new Pieza(DAMA, Pieza.Color.NEGRO, "D5");
                vectorPiezasNegras.add(piezaNegra2);
                piezaNegra3 = new Pieza(TORRE, Pieza.Color.NEGRO, "C8");
                vectorPiezasNegras.add(piezaNegra3);
                piezaNegra4 = new Pieza(TORRE, Pieza.Color.NEGRO, "E8");
                vectorPiezasNegras.add(piezaNegra4);
                break;
        }
        colocaPiezas();
    }

    private void inicializaJugadaCapturaPieza() {
        vectorPiezasBlancas.removeAllElements();
        vectorPiezasNegras.removeAllElements();
        int variante = random.nextInt(4);
        switch (variante) {
            case 0:
                Pieza piezaBlanca1 = new Pieza(REY, BLANCO, "E1");
                vectorPiezasBlancas.add(piezaBlanca1);
                Pieza piezaBlanca2 = new Pieza(TORRE, BLANCO, "A1");
                vectorPiezasBlancas.add(piezaBlanca2);
                Pieza piezaNegra1 = new Pieza(REY, Pieza.Color.NEGRO, "E8");
                vectorPiezasNegras.add(piezaNegra1);
                Pieza piezaNegra2 = new Pieza(ALFIL, Pieza.Color.NEGRO, "A5");
                vectorPiezasNegras.add(piezaNegra2);
                Pieza piezaNegra3 = new Pieza(CABALLO, Pieza.Color.NEGRO, "G3");
                vectorPiezasNegras.add(piezaNegra3);
                Pieza piezaNegra4 = new Pieza(TORRE, Pieza.Color.NEGRO, "H2");
                vectorPiezasNegras.add(piezaNegra4);
                Pieza piezaNegra5 = new Pieza(DAMA, Pieza.Color.NEGRO, "D8");
                vectorPiezasNegras.add(piezaNegra5);
                break;
            case 1:
                piezaBlanca1 = new Pieza(REY, BLANCO, "F2");
                vectorPiezasBlancas.add(piezaBlanca1);
                piezaBlanca2 = new Pieza(TORRE, BLANCO, "G1");
                vectorPiezasBlancas.add(piezaBlanca2);
                Pieza piezaBlanca3 = new Pieza(DAMA, BLANCO, "B1");
                vectorPiezasBlancas.add(piezaBlanca3);
                piezaNegra1 = new Pieza(REY, Pieza.Color.NEGRO, "G8");
                vectorPiezasNegras.add(piezaNegra1);
                piezaNegra2 = new Pieza(TORRE, Pieza.Color.NEGRO, "C2");
                vectorPiezasNegras.add(piezaNegra2);
                piezaNegra3 = new Pieza(DAMA, Pieza.Color.NEGRO, "H3");
                vectorPiezasNegras.add(piezaNegra3);
                piezaNegra4 = new Pieza(CABALLO, Pieza.Color.NEGRO, "F5");
                vectorPiezasNegras.add(piezaNegra4);
                piezaNegra5 = new Pieza(ALFIL, Pieza.Color.NEGRO, "B4");
                vectorPiezasNegras.add(piezaNegra5);
                break;
            case 2:
                piezaBlanca1 = new Pieza(REY, BLANCO, "D3");
                vectorPiezasBlancas.add(piezaBlanca1);
                piezaBlanca1 = new Pieza(ALFIL, BLANCO, "G2");
                vectorPiezasBlancas.add(piezaBlanca1);
                piezaNegra1 = new Pieza(REY, Pieza.Color.NEGRO, "F7");
                vectorPiezasNegras.add(piezaNegra1);
                piezaNegra2 = new Pieza(DAMA, Pieza.Color.NEGRO, "D5");
                vectorPiezasNegras.add(piezaNegra2);
                piezaNegra3 = new Pieza(TORRE, Pieza.Color.NEGRO, "C8");
                vectorPiezasNegras.add(piezaNegra3);
                piezaNegra4 = new Pieza(TORRE, Pieza.Color.NEGRO, "E8");
                vectorPiezasNegras.add(piezaNegra4);
                break;
            case 3:
                piezaBlanca1 = new Pieza(REY, BLANCO, "A1");
                vectorPiezasBlancas.add(piezaBlanca1);
                piezaBlanca1 = new Pieza(CABALLO, BLANCO, "E1");
                vectorPiezasBlancas.add(piezaBlanca1);
                piezaNegra1 = new Pieza(REY, Pieza.Color.NEGRO, "H7");
                vectorPiezasNegras.add(piezaNegra1);
                piezaNegra2 = new Pieza(CABALLO, Pieza.Color.NEGRO, "C2");
                vectorPiezasNegras.add(piezaNegra2);
                piezaNegra3 = new Pieza(TORRE, Pieza.Color.NEGRO, "B8");
                vectorPiezasNegras.add(piezaNegra3);
                piezaNegra4 = new Pieza(ALFIL, Pieza.Color.NEGRO, "E6");
                vectorPiezasNegras.add(piezaNegra4);
                break;
        }
        colocaPiezas();
    }

    @Override
    protected void onFinalCuentaAtras() {
        avatar.habla(R.raw.mover_rey_en_jaque, new VistaAvatar.OnAvatarHabla() {
            @Override
            public void onTerminaHabla() {
                avatar.mueveOjos(VistaAvatar.MovimientoOjos.DERECHA);
                avatar.reproduceEfectoSonido(VistaAvatar.EfectoSonido.TIC_TAC);
                empiezaCuentaAtras();
            }
        });
    }

    @Override
    protected boolean onMovimiento(int colOrigen, int filaOrigen, int colDestino, int filaDestino) {
        Log.d("Ajedrez Infantil", "onMovimiento colOrigen=" + colOrigen + " filaOrigen=" + filaOrigen + " colDestino=" + colDestino + " filaDestino=" + filaDestino);
        cancelaCuentaAtras();
        Pieza piezaDestino = getPieza(colDestino, filaDestino);
        boolean movimientoPiezaBlanca = (getColorPieza(colOrigen, filaOrigen) == BLANCO);
        boolean movimientoValido = validador.movimientoValido(colOrigen, filaOrigen, colDestino, filaDestino);
        boolean capturaPieza = (capturaPieza(colOrigen, filaOrigen, colDestino, filaDestino));
        boolean movimientoCorrecto = movimientoPiezaBlanca && movimientoValido;
        if (movimientoCorrecto && capturaPieza) eliminaPieza(piezaDestino);
        Log.d("Ajedrez Infantil", "movimientoPiezaBlanca=" + movimientoPiezaBlanca);
        Log.d("Ajedrez Infantil", "movimientoValido=" + movimientoValido);
        Log.d("Ajedrez Infantil", "capturaPieza=" + capturaPieza);
        Log.d("Ajedrez Infantil", "movimientoCorrecto=" + movimientoCorrecto);
        return movimientoCorrecto;
    }

    @Override
    protected void onMovimiento(boolean movimientoValido, int colOrigen, int filaOrigen, int colDestino, int filaDestino) {
        Log.d("Ajedrez Infantil", "onMovimiento movimientoValido=" + movimientoValido + " colOrigen=" + colOrigen + " filaOrigen=" + filaOrigen + " colDestino=" + colDestino + " filaDestino=" + filaDestino);
        avatar.mueveOjos(VistaAvatar.MovimientoOjos.DERECHA);
        if (movimientoValido) {
            contadorMovimientos++;
            avatar.reproduceEfectoSonido(VistaAvatar.EfectoSonido.MOVIMIENTO_CORRECTO);
            avatar.mueveCejas(VistaAvatar.MovimientoCejas.ARQUEAR);
            if (contadorMovimientos < 3) {
                avatar.lanzaAnimacion(VistaAvatar.Animacion.MOVIMIENTO_CORRECTO);
                avatar.habla(R.raw.ok_intenta_otra_vez, new VistaAvatar.OnAvatarHabla() {
                    @Override
                    public void onTerminaHabla() {
                        avatar.reproduceEfectoSonido(VistaAvatar.EfectoSonido.TIC_TAC);
                        empiezaCuentaAtras();
                    }
                });
                inicializaJugada(contadorMovimientos);
            } else {
                avatar.lanzaAnimacion(VistaAvatar.Animacion.EJERCICIO_SUPERADO);
                avatar.reproduceEfectoSonido(VistaAvatar.EfectoSonido.EJERCICIO_SUPERADO);
                avatar.habla(R.raw.ok_superado, new VistaAvatar.OnAvatarHabla() {
                    @Override
                    public void onTerminaHabla() {
                        finish();
                    }
                });
            }
        }
        else {
            avatar.lanzaAnimacion(VistaAvatar.Animacion.MOVIMIENTO_INCORRECTO);
            avatar.reproduceEfectoSonido(VistaAvatar.EfectoSonido.MOVIMIENTO_INCORRECTO);
            avatar.mueveCejas(VistaAvatar.MovimientoCejas.FRUNCIR);
            avatar.habla(R.raw.mover_rey_en_jaque_mal, new VistaAvatar.OnAvatarHabla() {
                @Override
                public void onTerminaHabla() {
                    avatar.reproduceEfectoSonido(VistaAvatar.EfectoSonido.TIC_TAC);
                    empiezaCuentaAtras();
                }
            });
            for (int i = 0; i < vectorPiezasNegrasAtacantes.size(); i++) {
                Toast.makeText(this, "Jaque de "
                        + vectorPiezasNegrasAtacantes.get(i).getTipo().toString().toLowerCase()
                        + " al rey", Toast.LENGTH_SHORT).show();
            }
            Pieza pieza = getPieza(colOrigen, filaOrigen);
            if (!reyEnJaqueMate()) {
                Pieza piezaDefensora = vectorPiezasBlancasDefensoras.get(0);
                resaltarCasilla(colDestino, filaDestino, Movimiento.INCORRECTO);
                resaltarCasilla(piezaDefensora.getColumna(), piezaDefensora.getFila(), Movimiento.ORIGEN);
                resaltarCasilla(piezaDefensora.getColumna(), piezaDefensora.getFila(), validador);
            }
        }
    }
}