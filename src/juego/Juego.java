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
	private boolean juegoTerminado;
	private int tiempoInicio;
	private boolean juegoIniciado;
	private int oleadaActual;
	private Image fondo;
	private ArrayList<Murcielago> murcielagos;
	private ArrayList<Hechizo> hechizosActivos;
	// Variables y métodos propios de cada grupo
	
	
	// ...
	
	Juego()
	{
		// Inicializa el objeto entorno

		this.entorno = new Entorno(this, "Juego Gondolf", 1200, 900);
		this.gondolf = new Gondolf(600, 450,60);//centrado
		this.rocas = new ArrayList<>();
		this.random = new Random();
		this.menu = new Menu(1000, 200, 900); //200px de ancho
		this.hechizosActivos = new ArrayList<>();
		this.murcielagos = new ArrayList<>();
		
		murcielagos.add(new Murcielago(100, 100, 2));
		murcielagos.add(new Murcielago(200, 200, 2));

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
		// Lógica para lanzar hechizos con barra espaciadora
	    if (entorno.sePresiono(' ')) {
	        int mouseX = entorno.mouseX();
	        int mouseY = entorno.mouseY();

	        // Evitar lanzar hechizos sobre el menú (que empieza en x=1000)
	        if (mouseX < 1000) {
	            BotonHechizo boton = menu.getHechizoSeleccionado();
	            if (boton != null) {
	                int costo = boton.getCosto();
	                if (gondolf.getMagia() >= costo) {
	                    Hechizo h = new Hechizo(mouseX, mouseY, 50, 1, costo); // radio 50, daño 1
	                    hechizosActivos.add(h);
	                    gondolf.usarMagia(costo);
	                    menu.resetSeleccion();
	                    System.out.println("Hechizo lanzado en (" + mouseX + ", " + mouseY + ")");
	                } else {
	                    System.out.println("No hay suficiente magia");
	                }
	            }
	        }
	    }
		 dibujarMundo();
		
	}
	private void manejarEntrada() {
		double colisionX = gondolf.getX();
	    double colisionY = gondolf.getY();
	    
		if (entorno.estaPresionada(entorno.TECLA_ARRIBA)) {
			
			gondolf.mover("arriba", entorno);
		}
		if (entorno.estaPresionada(entorno.TECLA_ABAJO)) { 
			
			gondolf.mover("abajo", entorno);
		}
		if (entorno.estaPresionada(entorno.TECLA_IZQUIERDA)) {
				 
			gondolf.mover("izquierda", entorno);
		}
		if (entorno.estaPresionada(entorno.TECLA_DERECHA)) {
				
			
			gondolf.mover("derecha", entorno);
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
	        entorno.dibujarRectangulo(0, 0, 1000, 900, 0, Color.black);
	        
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
	     // Dibujar murciélagos y moverlos
	        for (Murcielago m : murcielagos) {
	            m.moverHacia(gondolf);
	            m.dibujar(entorno);
	        }

	        // Dibujar hechizos activos y afectar murciélagos
	        for (Hechizo h : hechizosActivos) {
	            if (h.estaActivo()) {
	                h.dibujar(entorno);
	                // Afectar murciélagos dentro del área de efecto
	                for (Murcielago m : murcielagos) {
	                	if (m.estaActivo() && h.afectaA(m)) {

	                        m.recibirDanio(h.getDaño());
	                    }
	                }
	                h.desactivar(); // El hechizo dura solo un tick
	            }
	        }

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
