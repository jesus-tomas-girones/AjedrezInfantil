package es.upv.mmoviles.ajedrez;

import android.util.Log;

/**
 * Created by raulvibo on 29/12/2016.
 */

public class Pieza {
    private Tipo tipo;
    private Color color;
    private String coordenada;

    public enum Tipo {PEON, CABALLO, ALFIL, TORRE, DAMA, REY}
    public enum Color {BLANCO, NEGRO}

    public Pieza(Tipo tipo, Color color, String coordenada) {
        this.coordenada = coordenada;
        this.tipo = tipo;
        this.color = color;
    }

    public String getCoordenada() {
        return coordenada;
    }

    public void setCoordenada(int columna, int fila){
        coordenada = "" + (char)((int)('A') + columna)
                + (char)((int)('1') + fila);
        Log.d("Ajedrez Infantil", "coordenada=" + coordenada);
    }

    public int getColumna() {
        return coordenada.charAt(0)-'A';
    }

    public int getFila() {
        return coordenada.charAt(1)-'1';
    }

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
