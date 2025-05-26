package juego;

import entorno.Entorno;

public class Gondolf {

    double x;
    private double y;
    private int vida, magia;
    private double velocidad;
    boolean estaVivo;
    private double ancho;
    private double velocidadOriginal= 9.0;;
    private int framesRalentizado;
    private static int vidaMaxima = 100;
    private static int magiaMaxima=50;
    public Gondolf(double x, double y, double ancho) {
        this.x = x;
        this.setY(y);
        this.vida = 100;
        this.magia = 50;
        this.velocidad = 9.0;
        this.estaVivo = true;
        this.ancho=ancho;
        
    }

    public void mover(String direccion, Entorno entorno) {
        double radio = this.ancho/2;
        double limiteDerecho = 1000 - radio; 
        double limiteInferior = 900 - radio; 
        double limiteSuperior = radio;
        double limiteIzquierdo = radio;
        
        switch (direccion.toLowerCase()) {
            case "arriba":
                setY(Math.max(limiteSuperior, getY() - velocidad));
                break;
            case "abajo":
                setY(Math.min(limiteInferior, getY() + velocidad));
                break;
            case "izquierda":
                x = Math.max(limiteIzquierdo, x - velocidad);
                break;
            case "derecha":
                x = Math.min(limiteDerecho, x + velocidad);
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


    
    public void recuperarVida(int cantidad) {
        vida = Math.min(100, vida + cantidad); 
    }

    public void recuperarMagia(int cantidad) {
        magia = Math.min(50, magia + cantidad);
    }
    
    public void ralentizar(double factor) {
        if (velocidad == velocidadOriginal) { // Solo aplicar si no está ya ralentizado
            velocidad *= factor;
        }
    }
    
    public void restaurarVelocidad() {
        velocidad = velocidadOriginal;
    }

    public void actualizarEfectos() {
        if (framesRalentizado > 0) {
            framesRalentizado--;
            if (framesRalentizado == 0) {
                velocidad = velocidadOriginal;
            }
        }
      }
    
    public void aumentarVidaMaxima(int cantidad) {
        this.vidaMaxima += cantidad;
        this.vida += cantidad;
    }

    public void aumentarMagiaMaxima(int cantidad) {
        this.magiaMaxima += cantidad;
        this.magia += cantidad; 
    }
    
    public void setVelocidad(double velocidad) {
        this.velocidad = velocidad;
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
    public static int getVidaMaxima() {
        return vidaMaxima;
    }

    public static int getMagiaMaxima() {
        return magiaMaxima;
    }
}
