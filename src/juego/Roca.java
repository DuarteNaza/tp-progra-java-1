package juego;

public class Roca {
    private double x, y;
    private double ancho, alto;

    public Roca(double x, double y, double ancho, double alto) {
        this.x = x;
        this.y = y;
        this.ancho = ancho;
        this.alto = alto;
    }

    public boolean colisionaCon(Gondolf gondolf) {
        return gondolf.getX() > x - ancho / 2 && gondolf.getX() < x + ancho / 2 &&
                gondolf.getY() > y - alto / 2 && gondolf.getY() < y + alto / 2;
    }

    public void dibujar() {
        System.out.printf("Roca - Posición: (%.1f, %.1f), Tamaño: %.1fx%.1f%n",
                x, y, ancho, alto);
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getAncho() {
        return ancho;
    }

    public double getAlto() {
        return alto;
    }
}