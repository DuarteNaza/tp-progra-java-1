package juego;


import java.awt.Color;
import java.awt.Image;
import entorno.Entorno;
import entorno.InterfaceJuego;
import java.util.ArrayList;
import java.util.Random;


public class Juego extends InterfaceJuego{


	private Entorno entorno;
	private Gondolf gondolf;
	private ArrayList<Roca> rocas;
	private Random random;
	private Menu menu;
	private boolean juegoTerminado;
	private int tiempoInicio;
	private boolean juegoIniciado;
	private Image fondo;
	private ArrayList<Murcielago> murcielagos;
	private ArrayList<Hechizo> hechizosActivos;
	private boolean juegoGanado;
	private int enemigosTotales = 50;
	private int enemigosEliminados = 0;
	private int oleadaActual = 1;
	private int enemigosPorOleada = 10;
	private int enemigosBasePorOleada = 10;
	private ArrayList<Pocion> pociones = new ArrayList<>();
	private int framesOleada = 0; // Variable nueva en Juego.java

	Juego(){

		this.entorno = new Entorno(this, "Juego Gondolf", 1200, 900);
		this.gondolf = new Gondolf(600, 450,60);
		this.rocas = new ArrayList<>();
		this.random = new Random();
		this.menu = new Menu(1000, 200, 900);
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


		

		this.entorno.iniciar();
	}


	

	
	public void tick(){
		 if (!juegoIniciado) {
		        dibujarPantallaInicio();
		        if (entorno.sePresiono(entorno.TECLA_ESPACIO)) {
		            juegoIniciado = true;
		        }
		        return;
		    }
		manejarEntrada();
		manejarHechizos();  
		dibujarMundo();
		verificarColisiones();
		spawnearMurcielagos();
		moverMurcielagos();
		actualizarOleada();
		murcielagos.removeIf(m -> !m.estaActivo());
		menu.actualizar(gondolf.getVida(), gondolf.getMagia(), enemigosEliminados);
		
		if (gondolf.getVida() <= 0) {
		    juegoTerminado = true;
		    juegoGanado = false;
		} else if (enemigosEliminados >= enemigosTotales && murcielagos.isEmpty()) {
		    juegoTerminado = true;
		    juegoGanado = true;
		}
		if (framesOleada > 0) {
		    entorno.cambiarFont("Arial", 30, Color.ORANGE);
		    entorno.escribirTexto("¡OLEADA " + oleadaActual + "!", 500, 300);
		    framesOleada--;
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
	        for (Murcielago m : murcielagos) {
	            m.moverHacia(gondolf);
	            m.dibujar(entorno);
	            
	        }

	        for (Hechizo h : hechizosActivos) {
	            if (h.estaActivo()) {
	                h.dibujar(entorno);
	                for (Murcielago m : murcielagos) {
	                	if (m.estaActivo() && h.afectaA(m)) {
	                		enemigosEliminados++;
	                        m.recibirDanio(h.getDaño());
	                    }
	                }
	                h.desactivar();
	            }
	        }
	        for (Pocion p : pociones) {
	            p.dibujar(entorno);
	        }

	    }
	
	
	
	private void manejarHechizos() {
		 if (entorno.sePresiono(entorno.TECLA_ALT)) {
			 	int mouseX = entorno.mouseX();
		        int mouseY = entorno.mouseY();
		        
		        if (mouseX > 1000) { 
		            menu.manejarClick(mouseX, mouseY); 
		        } 
		        else if (menu.getHechizoSeleccionado() != null) {
		            lanzarHechizo(mouseX, mouseY);
		        }
		    }
		}

	
	
	private void lanzarHechizo(int x, int y) {
		    BotonHechizo boton = menu.getHechizoSeleccionado();
		    if (boton != null && gondolf.getMagia() >= boton.getCosto()) {
		        int daño = boton.getNombre().equals("Explosion") ? 2 : 1;
		        hechizosActivos.add(new Hechizo(x, y, 50, daño, daño));
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
		}
		
	
	private void verificarColisiones() {
		for (Pocion p : new ArrayList<>(pociones)) {
		    if (p.colisionaCon(gondolf)) {
		        if (p.getTipo() == 0) gondolf.recuperarVida(20);
		        else gondolf.recuperarMagia(15);
		        p.desactivar();
		        pociones.remove(p);
		    }
		}
		    for (Murcielago m : murcielagos) {
		        if (m.estaActivo() && distancia(gondolf, m) < 30) { 
		            gondolf.recibirDanio(10); 
		            m.recibirDanio(100); 
		            enemigosEliminados++;
		        }
		    }
		    for (Murcielago murcielago : new ArrayList<>(murcielagos)) {
		        if (!murcielago.estaActivo()) {
		            if (Math.random() < 0.1) {
		                pociones.add(new Pocion(murcielago.getX(), murcielago.getY()));
		            }
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
	

	
	private void actualizarOleada() {
	    if (enemigosEliminados >= oleadaActual * enemigosPorOleada) {
	        oleadaActual++;
	        enemigosPorOleada = enemigosBasePorOleada + (oleadaActual * 2);
	        framesOleada = 60; 
	    }
	}

		
			
		
		
	


	@SuppressWarnings("unused")
	public static void main(String[] args)
	{
		Juego juego = new Juego();
	}
}
