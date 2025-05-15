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
    	 System.out.println(
    		        "Roca - Posición: (" + x + ", " + y + "), " +
    		        "Tamaño: " + ancho + "x" + alto
    		    );
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