package juego;


import java.awt.Color;
import java.awt.Image;
import entorno.Entorno;
import entorno.InterfaceJuego;
import java.util.ArrayList;
import java.util.Random;


public class Juego extends InterfaceJuego
{
	// El objeto Entorno que controla el tiempo y otros

	private Entorno entorno;
	private Gondolf gondolf;
	private ArrayList<Roca> rocas;
	private Random random;
	private Menu menu;
	// Variables y métodos propios de cada grupo
	
	
	// ...
	
	Juego()
	{
		// Inicializa el objeto entorno

		this.entorno = new Entorno(this, "Juego Gondolf", 1200, 900);
		this.gondolf = new Gondolf(600, 450);//centrado
		this.rocas = new ArrayList<>();
		this.random = new Random();
		this.menu = new Menu(1000, 200, 900); //200px de ancho
		for (int i = 0; i < 5; i++) {
			rocas.add(new Roca(
					random.nextInt(900) + 50,
					random.nextInt(500) + 50,
					40,
					40));
		}


		
		
		

		// Inicializar lo que haga falta para el juego
		// ...
		
		

		// Inicia el juego!
		this.entorno.iniciar();
	}

	/**
	 * Durante el juego, el método tick() será ejecutado en cada instante y 
	 * por lo tanto es el método más importante de esta clase. Aquí se debe 
	 * actualizar el estado interno del juego para simular el paso del tiempo 
	 * (ver el enunciado del TP para mayor detalle).
	 */
	public void tick()
	{

		manejarEntrada();
		 dibujarMundo();
		
	}
	private void manejarEntrada() {
		double colisionX = gondolf.getX();
	    double colisionY = gondolf.getY();
		if (entorno.estaPresionada(entorno.TECLA_ARRIBA)) {
			gondolf.mover("arriba");
		}
		if (entorno.estaPresionada(entorno.TECLA_ABAJO)) {
			gondolf.mover("abajo");
		}
		if (entorno.estaPresionada(entorno.TECLA_IZQUIERDA)) {
			gondolf.mover("izquierda");
		}
		if (entorno.estaPresionada(entorno.TECLA_DERECHA)){
			gondolf.mover("derecha");
		}
		for (Roca r : rocas) {
			if (r.colisionaCon(gondolf)) {
				gondolf.setX(colisionX);
            	gondolf.setY(colisionY);
            	break;
			}
		}
	}
	 private void dibujarMundo() {
	        entorno.dibujarRectangulo(0, 0, 1000, 900, 0, Color.BLACK);
	        
	        entorno.dibujarCirculo(gondolf.getX(), gondolf.getY(), 30, Color.BLUE);
	        
	        for (Roca r : rocas) {
	            entorno.dibujarRectangulo(r.getX(), r.getY(), r.getAncho(), r.getAlto(), 0, Color.GRAY);
	        }
	        
	        menu.actualizar(gondolf.getVida(), gondolf.getMagia(), 0); // 0 enemigos por ahora
	        menu.dibujar(entorno);
	        
	        entorno.cambiarFont("Arial", 12, Color.WHITE);
	        entorno.escribirTexto(
	            String.format("Pos: (%.0f, %.0f)", gondolf.getX(), gondolf.getY()), 
	            10, 20
	        );
	    }

		// Procesamiento de un instante de tiempo
		
		
		// ...
		
		
		// si el usuario hace click izquierdo del mouse sobre un boton del menu, seleccionarlo
		
		
			
		
		
	
	


	@SuppressWarnings("unused")
	public static void main(String[] args)
	{
		Juego juego = new Juego();
	}
}
