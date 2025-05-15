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
	private ArrayList<Hechizos> hechizosActivos;
	private boolean juegoGanado;
	private int enemigosTotales = 50;
	private int enemigosEliminados = 0;

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
		this.juegoGanado = false;
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
		 if (!juegoIniciado) {
		        dibujarPantallaInicio();
		        if (entorno.sePresiono(entorno.TECLA_ESPACIO)) {
		            juegoIniciado = true;
		        }
		        return;
		    }
		manejarEntrada();
		manejarHechizos();  // Usa este método en lugar del código con barra espaciadora
		dibujarMundo();
		verificarColisiones();
		spawnearMurcielagos();
		moverMurcielagos();
		murcielagos.removeIf(m -> !m.estaActivo());
		menu.actualizar(gondolf.getVida(), gondolf.getMagia(), enemigosEliminados);
		if (gondolf.getVida() <= 0) {
		    juegoTerminado = true;
		    juegoGanado = false;
		} else if (enemigosEliminados >= enemigosTotales && murcielagos.isEmpty()) {
		    juegoTerminado = true;
		    juegoGanado = true;
		}
	}
	
	private void manejarEntrada() {
		double colisionX = gondolf.getX();
	    double colisionY = gondolf.getY();
	    if (juegoTerminado) {
	        return;
	    }
		if (entorno.estaPresionada(entorno.TECLA_ARRIBA) || entorno.estaPresionada('w')) {
			
			gondolf.mover("arriba", entorno);
		}
		if (entorno.estaPresionada(entorno.TECLA_ABAJO) || entorno.estaPresionada('s')) { 
			
			gondolf.mover("abajo", entorno);
		}
		if (entorno.estaPresionada(entorno.TECLA_IZQUIERDA) || entorno.estaPresionada('a')) {
				 
			gondolf.mover("izquierda", entorno);
		}
		if (entorno.estaPresionada(entorno.TECLA_DERECHA) || entorno.estaPresionada('d')) {	
			
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
	        
	        if (!juegoIniciado) {
	            dibujarPantallaInicio();
	            return;
	        }
	        
	        if (juegoTerminado) {
	            dibujarPantallaFin();
	            return;
	        }
	     // Dibujar murciélagos y moverlos
	        for (Murcielago m : murcielagos) {
	            m.moverHacia(gondolf);
	            m.dibujar(entorno);
	            
	        }

	        // Dibujar hechizos activos y afectar murciélagos
	        for (Hechizos h : hechizosActivos) {
	            if (h.estaActivo()) {
	                h.dibujar(entorno);
	                // Afectar murciélagos dentro del área de efecto
	                for (Murcielago m : murcielagos) {
	                	if (m.estaActivo() && h.afectaA(m)) {
	                		enemigosEliminados++;
	                        m.recibirDanio(h.getDaño());
	                    }
	                }
	                h.desactivar(); // El hechizo dura solo un tick
	            }
	        }

	    }
	
	private void manejarHechizos() {
		 if (entorno.sePresiono(entorno.TECLA_SHIFT)) {
			 	int mouseX = entorno.mouseX();  // Obtener posición X del mouse
		        int mouseY = entorno.mouseY();  // Obtener posición Y del mouse
		        
		        if (mouseX > 1000) {
		            menu.manejarClick(mouseX, mouseY);
		        } else if (menu.getHechizoSeleccionado() != null) {
		            lanzarHechizo(mouseX, mouseY);
		        }
		    }
		}

	private void lanzarHechizo(int x, int y) {
		    BotonHechizo boton = menu.getHechizoSeleccionado();
		    if (boton != null && gondolf.getMagia() >= boton.getCosto()) {
		        int daño = boton.getNombre().equals("Explosion") ? 2 : 1;
		        hechizosActivos.add(new Hechizos(x, y, 50, daño));
		        gondolf.usarMagia(boton.getCosto());
		        menu.resetSeleccion();
		    }
		}
		
	private void dibujarPantallaInicio() {
	    entorno.dibujarRectangulo(
	        600, 250,  
	        550, 60,  
	        0,         
	        new Color(10, 30, 100, 220) 
	    );
	    entorno.cambiarFont("Arial", 40, Color.YELLOW);
	    entorno.escribirTexto("EL CAMINO DE GONDOLF", 330, 260);


	    entorno.dibujarRectangulo(
	        600, 350,
	        450, 40, 
	        0,
	        new Color(100, 20, 20, 220) 
	    );
	    entorno.cambiarFont("Arial", 20, Color.WHITE);
	    entorno.escribirTexto("Presiona ESPACIO para comenzar", 380, 360);
	}


	private void dibujarPantallaFin() {
		    entorno.cambiarFont("Arial", 40, 
		        juegoGanado ? Color.GREEN : Color.RED);
		    entorno.escribirTexto(
		        juegoGanado ? "¡VICTORIA!" : "GAME OVER", 
		        450, 300);
		    // ... más detalles
		}
		
	private void verificarColisiones() {
		    for (Roca r : rocas) {
		        if (r.colisionaCon(gondolf)) {
		            double dx = gondolf.getX() - r.getX();
		            double dy = gondolf.getY() - r.getY();
		            double distancia = Math.sqrt(dx*dx + dy*dy);
		            double distanciaMinima = 35; // Mayor que antes
		            
		            if (distancia < distanciaMinima) {
		                double ajusteX = dx/distancia * (distanciaMinima - distancia);
		                double ajusteY = dy/distancia * (distanciaMinima - distancia);
		                gondolf.setX(gondolf.getX() + ajusteX);
		                gondolf.setY(gondolf.getY() + ajusteY);
		            }
		        }
		    }
		    for (Murcielago m : murcielagos) {
		        if (m.estaActivo() && distancia(gondolf, m) < 30) { // Radio de colisión
		            gondolf.recibirDanio(10); // Daño por contacto
		            m.recibirDanio(100); // Elimina al murciélago
		            enemigosEliminados++; // Actualizar contador
		        }
		    }
		}
		
	
	private double distancia(Gondolf g, Murcielago m) {
	    return Math.sqrt(Math.pow(g.getX() - m.getX(), 2) + Math.pow(g.getY() - m.getY(), 2));
	}
	

	private void spawnearMurcielagos() {
	    if (murcielagos.size() < 10 && enemigosEliminados < enemigosTotales) {
	    	
	        double x, y;
	        int borde = random.nextInt(4); // 0: arriba, 1: derecha, 2: abajo, 3: izquierda
	        
	        switch (borde) {
	            case 0: // Arriba
	                x = random.nextInt(1000);
	                y = 0;
	                break;
	            case 1: // Derecha
	                x = 1000;
	                y = random.nextInt(900);
	                break;
	            case 2: // Abajo
	                x = random.nextInt(1000);
	                y = 900;
	                break;
	            case 3: // Izquierda
	                x = 0;
	                y = random.nextInt(900);
	                break;
	            default:
	                x = 0; y = 0;
	        }
	        
	        murcielagos.add(new Murcielago(x, y, 2));
	        }
	    }
	
	private void moverMurcielagos() {
	    for (Murcielago murcielago : murcielagos) {
	        double newX = murcielago.getX(); 
	        double newY = murcielago.getY(); 
	        murcielago.mover(newX, newY, murcielagos, 10); 
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
