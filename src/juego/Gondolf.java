package juego;

public class Gondolf {

    double x;
    private double y;
    private int vida, magia;
    private double velocidad;
    boolean estaVivo;

    public Gondolf(double x, double y) {
        this.x = x;
        this.setY(y);
        this.vida = 100;
        this.magia = 50;
        this.velocidad = 2.0;
        this.estaVivo = true;
    }

    public void mover(String direccion) {

        switch (direccion.toLowerCase()) {

            case "arriba":
                setY(getY() - velocidad);
                break;
            case "abajo":
                setY(getY() + velocidad);
                break;
            case "izquierda":
                x -= velocidad;
                break;
            case "derecha":
                x += velocidad;
                break;
        }
    }

    public void recibirDanio(int cantidad) {
        vida -= cantidad;
        if (vida <= 0) {
            estaVivo = false;
        }
    }

    public void usarMagia(int cantidad) {
        if (magia >= cantidad) {
            magia -= cantidad;
        }
    }

    public void dibujar() {
    	 System.out.println(
    		        "Gondolf - Posición: (" + x + ", " + y + "), " +
    		        "Vida: " + vida + ", Magia: " + magia
    		    );
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getVelocidad() {
        return velocidad;
    }

    public void setX(double x) {
        this.x = x;
    }

    public int getVida() {
        return vida;
    }

    public int getMagia() {
        return magia;
    }

    public boolean estaVivo() {
        return estaVivo;
    }

}
