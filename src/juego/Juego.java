package juego;


import java.awt.Color;
import entorno.Entorno;
import entorno.InterfaceJuego;
import java.util.ArrayList;
import java.util.Random;


public class Juego extends InterfaceJuego{


	private Entorno entorno;
	private Gondolf gondolf;
	private Random random;
	private Menu menu;
	private boolean juegoTerminado;
	private boolean juegoIniciado;
	private boolean juegoGanado;
	private int enemigosTotales = 50;
	private int enemigosEliminados = 0;
	private int oleadaActual = 1;
	private int enemigosPorOleada = 10;
	private int enemigosBasePorOleada = 10;
	private int enemigosTotalesInactivos = 0;
	private int enemigosTotalesActivos = 2;
	private int enemigosEliminadosPorHechizos = 0;
	private int framesOleada = 0; 
	private long tiempoInicioTotal;  
	private long tiempoInicioOleada;
	private String tiempoTotalFormateado = "00:00";
	private String tiempoOleadaFormateado = "00:00";
    
    private Roca[] rocas;
	private Murcielago[] murcielagos;
    private Hechizo[] hechizosActivos;
    private Pocion[] pociones;
    private IndicadorDaño[] indicadoresDaño;

	  private int cantidadRocas;
	  private int cantidadMurcielagos;
	  private int cantidadHechizos;
	  private int cantidadPociones;
	  private int cantidadIndicadores;
	  
	Juego(){
		this.tiempoInicioTotal = System.currentTimeMillis();
		this.tiempoInicioOleada = System.currentTimeMillis();
		this.entorno = new Entorno(this, "Juego Gondolf", 1200, 900);
		this.gondolf = new Gondolf(600, 450,60);
		this.random = new Random();
		this.menu = new Menu(1000, 200, 900);

		this.juegoGanado = false;
		this.rocas = new Roca[5]; 
	    this.murcielagos = new Murcielago[10]; 
	    this.hechizosActivos = new Hechizo[5];
	    this.pociones = new Pocion[10]; 
	    this.indicadoresDaño = new IndicadorDaño[20];
	        

	        murcielagos[0] = new Murcielago(100, 100, 2, oleadaActual);
	        murcielagos[1] = new Murcielago(200, 200, 2, oleadaActual);
	        cantidadMurcielagos = 2;
	        
	        for (int i = 0; i < 5; i++) {
	            rocas[i] = new Roca(
	                random.nextInt(900) + 50,
	                random.nextInt(500) + 50,
	                40,
	                40
	            );
	        }
	        cantidadRocas = 5;
	    	

		

		this.entorno.iniciar();
	}


	
    private void actualizarContadores() {
        long tiempoTranscurridoTotal = System.currentTimeMillis() - tiempoInicioTotal;
        tiempoTotalFormateado = milisegundosAMinutosSegundos(tiempoTranscurridoTotal);
        
        long tiempoTranscurridoOleada = System.currentTimeMillis() - tiempoInicioOleada;
        tiempoOleadaFormateado = milisegundosAMinutosSegundos(tiempoTranscurridoOleada);
    }

    private String milisegundosAMinutosSegundos(long milisegundos) {
        long segundos = milisegundos / 1000;
        long minutos = segundos / 60;
        segundos = segundos % 60;
        return String.format("%02d:%02d", minutos, segundos);
    }

	
	public void tick(){
		
		 actualizarContadores();
		    
		    if (!juegoIniciado) {
		        manejarPantallaInicio();
		        return;
		    }
		    
		    if (juegoTerminado) {
		        dibujarPantallaFin();
		        return;
		    }
		    
		    ejecutarLogicaDelJuego();
		    actualizarEstadoDelJuego();
		    dibujarElementos();
		}

	private void manejarPantallaInicio() {
	    dibujarPantallaInicio();
	    if (entorno.sePresiono(entorno.TECLA_ENTER)) {
	        juegoIniciado = true;
	    }
	}

	private void ejecutarLogicaDelJuego() {
	    manejarEntrada();
	    manejarHechizos();
	    verificarColisiones();
	    spawnearMurcielagos();
	    moverMurcielagos();
	    actualizarOleada();
	}

	private void actualizarEstadoDelJuego() {
	    for (int i = 0; i < cantidadMurcielagos; i++) {
	        if (!murcielagos[i].estaActivo()) {

	            murcielagos[i] = murcielagos[--cantidadMurcielagos];
	            murcielagos[cantidadMurcielagos] = null;
	            i--; 
	        }
	    }
	    
	    menu.actualizar(gondolf.getVida(), gondolf.getMagia(), enemigosEliminadosPorHechizos);
	    verificarFinDelJuego();
	    actualizarMensajeOleada();
	}

	
	private void verificarFinDelJuego() {
	    if (gondolf.getVida() <= 0) {
	        juegoTerminado = true;
	        juegoGanado = false;
	    } else if (enemigosEliminados >= enemigosTotales && cantidadMurcielagos == 0) {
	        juegoTerminado = true;
	        juegoGanado = true;
	    }
	}

	private void actualizarMensajeOleada() {
	    if (framesOleada > 0) {
	        entorno.cambiarFont("Arial", 30, Color.ORANGE);
	        entorno.escribirTexto("¡OLEADA " + oleadaActual + "!", 500, 300);
	        framesOleada--;
	    }
	}

	private void dibujarElementos() {
	    dibujarMundo();
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
    
    // Dibujar rocas
    for (int i = 0; i < cantidadRocas; i++) {
        Roca r = rocas[i];
        entorno.dibujarRectangulo(r.getX(), r.getY(), r.getAncho(), r.getAlto(), 0, Color.GRAY);
    }
    
    menu.dibujar(entorno, tiempoTotalFormateado, tiempoOleadaFormateado);
    
    // Dibujar murciélagos
    for (int i = 0; i < cantidadMurcielagos; i++) {
        Murcielago m = murcielagos[i];
        if (m != null && m.estaActivo()) {
            m.moverHacia(gondolf);
            m.dibujar(entorno);
        }
    }
    
    // Procesar hechizos y aplicar daño
    for (int i = 0; i < cantidadHechizos; i++) {
        Hechizo h = hechizosActivos[i];
        if (h != null && h.estaActivo()) {
            h.dibujar(entorno);
            
            // Aplicar daño a enemigos
            for (int j = 0; j < cantidadMurcielagos; j++) {
                Murcielago m = murcielagos[j];
                if (m != null && m.estaActivo() && h.afectaA(m)) {
                    m.recibirDanio(h.getDaño());
                    enemigosEliminadosPorHechizos++;
                    enemigosTotalesInactivos++;
                    enemigosTotalesActivos--;
                    
                    // Generar pociones si el murciélago muere
                    if (!m.estaActivo() && Math.random() < 0.1 && cantidadPociones < pociones.length) {
                        pociones[cantidadPociones++] = new Pocion(m.getX(), m.getY());
                    }
                }
            }
            
            h.desactivar();
            // Eliminar hechizo después de usarlo
            hechizosActivos[i] = hechizosActivos[--cantidadHechizos];
            hechizosActivos[cantidadHechizos] = null;
            i--; // Revisar la nueva posición
        }
    }
    
    // Dibujar pociones
    for (int i = 0; i < cantidadPociones; i++) {
        if (pociones[i] != null) {
            pociones[i].dibujar(entorno);
        }
    }
    
    // Dibujar indicadores de daño
    for (int i = 0; i < cantidadIndicadores; i++) {
        IndicadorDaño ind = indicadoresDaño[i];
        if (ind != null) {
            ind.dibujar(entorno);
            if (!ind.estaActivo()) {
                indicadoresDaño[i] = indicadoresDaño[--cantidadIndicadores];
                indicadoresDaño[cantidadIndicadores] = null;
            }
        }
    }
}

	    
	
	
	
	private void manejarHechizos() {
		 if (entorno.sePresionoBoton(entorno.BOTON_IZQUIERDO)) {
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
	    if (boton != null && gondolf.getMagia() >= boton.getCosto() && cantidadHechizos < hechizosActivos.length) {
	        int daño = boton.getNombre().equals("Explosion") ? 2 : 1;
	        int costo = boton.getCosto();
	        hechizosActivos[cantidadHechizos++] = new Hechizo(x, y, 50, daño, costo);
	        gondolf.usarMagia(costo);
	        menu.resetSeleccion();
	    } else {
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
	    entorno.escribirTexto("Presiona ENTER para comenzar", 380, 360);
	}


	private void dibujarPantallaFin() {
		    entorno.cambiarFont("Arial", 40, 
		        juegoGanado ? Color.GREEN : Color.RED);
		    entorno.escribirTexto(
		        juegoGanado ? "¡VICTORIA!" : "GAME OVER", 
		        450, 300);
		}
		
	
	private void verificarColisiones() {
	    // Pociones
	    for (int i = 0; i < cantidadPociones; i++) {
	        Pocion p = pociones[i];
	        if (p != null && p.colisionaCon(gondolf)) {
	            if (p.getTipo() == 1) {
	                gondolf.recuperarVida(20);
	                agregarIndicador(gondolf.getX(), gondolf.getY(), 20, Color.GREEN);
	            } else {
	                gondolf.recuperarMagia(15);
	                agregarIndicador(gondolf.getX(), gondolf.getY(), 15, Color.BLUE);
	            }
	            p.desactivar();
	            // Eliminar la poción moviendo las últimas
	            pociones[i] = pociones[--cantidadPociones];
	            pociones[cantidadPociones] = null;
	        }
	    }

	    // Murciélagos
	    for (int i = 0; i < cantidadMurcielagos; i++) {
	        Murcielago m = murcielagos[i];
	        if (m != null && m.estaActivo() && distancia(gondolf, m) < 30) {
	            gondolf.recibirDanio(10);
	            agregarIndicador(gondolf.getX(), gondolf.getY(), -10, Color.RED);
	            
	            m.recibirDanio(100);
	            agregarIndicador(m.getX(), m.getY(), -100, Color.ORANGE);
	            
	            enemigosEliminadosPorHechizos++;
	            enemigosTotalesInactivos++;
	            enemigosTotalesActivos--;
	            
	            // 10% de chance de generar poción
	            if (Math.random() < 0.1 && cantidadPociones < pociones.length) {
	                pociones[cantidadPociones++] = new Pocion(m.getX(), m.getY());
	            }
	        }
	    }
	}

	
	private void agregarIndicador(double x, double y, int valor, Color color) {
	    if (cantidadIndicadores < indicadoresDaño.length) {
	        indicadoresDaño[cantidadIndicadores++] = new IndicadorDaño(x, y, valor, color);
	    }
	}

	// Este bloque debería estar en otro método o ser reemplazado por:
	private void generarPocionesPorMurcielagosMuertos() {
	    for (int i = 0; i < cantidadMurcielagos; i++) {
	        Murcielago m = murcielagos[i];
	        if (m != null && !m.estaActivo() && Math.random() < 0.1 && cantidadPociones < pociones.length) {
	            pociones[cantidadPociones++] = new Pocion(m.getX(), m.getY());
	        }
	    }
	}
	
	

	private double distancia(Gondolf g, Murcielago m) {
	    return Math.sqrt(Math.pow(g.getX() - m.getX(), 2) + Math.pow(g.getY() - m.getY(), 2));
	}
	

	
	
	private void spawnearMurcielagos() {
	    if (cantidadMurcielagos < 10 && (enemigosTotalesInactivos + enemigosTotalesActivos) < enemigosPorOleada 
	        && enemigosEliminados < enemigosTotales) {
	        
	        double x, y;
	        int borde = random.nextInt(4);
	        
	        switch (borde) {
	            case 0: x = random.nextInt(1000); y = 0; break;
	            case 1: x = 1000; y = random.nextInt(900); break;
	            case 2: x = random.nextInt(1000); y = 900; break;
	            case 3: x = 0; y = random.nextInt(900); break;
	            default: x = 0; y = 0;
	        }
	        
	        murcielagos[cantidadMurcielagos++] = new Murcielago(x, y, 2.0, oleadaActual);
	        enemigosTotalesActivos++;
	    }
	}
	
	
	private void moverMurcielagos() {
	    for (int i = 0; i < cantidadMurcielagos; i++) {
	        Murcielago m = murcielagos[i];
	        double newX = m.getX(); 
	        double newY = m.getY(); 
	        m.mover(newX, newY, murcielagos, cantidadMurcielagos, 10);
	    }
	}
	

	
	private void actualizarOleada() {
	    if (enemigosTotalesInactivos >= enemigosPorOleada) {
	        oleadaActual++;
	        tiempoInicioOleada = System.currentTimeMillis();
	        enemigosPorOleada = enemigosBasePorOleada + (oleadaActual * 2);
	        enemigosTotalesActivos = 0;
	        enemigosTotalesInactivos = 0;
	        framesOleada = 60; 
	    }
	    
	}
	
	
	
	
	@SuppressWarnings("unused")
	public static void main(String[] args)
	{
		Juego juego = new Juego();
	}
}
