package juego;


import java.awt.Color;

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
	// Variables y métodos propios de cada grupo
	// ...
	
	Juego()
	{
		// Inicializa el objeto entorno
		this.entorno = new Entorno(this, "Proyecto para TP", 1200, 900);
		this.gondolf = new Gondolf(500, 300);
		this.rocas = new ArrayList<>();
		this.random = new Random();

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
		
	}
	private void manejarEntrada() {
		if (entorno.estaPresionada(entorno.TECLA_ARRIBA)) {
			gondolf.mover("arriba");
		}
		if (entorno.estaPresionada(entorno.TECLA_ABAJO)) {
			gondolf.mover("abajo");
		}
		if (entorno.estaPresionada(entorno.TECLA_IZQUIERDA)) {
			gondolf.mover("izquierda");
		}
		if (entorno.estaPresionada(entorno.TECLA_DERECHA)) {
			gondolf.mover("derecha");
		}

		for (Roca r : rocas) {
			if (r.colisionaCon(gondolf)) {
				gondolf.mover("arriba");
			}
		}
	}

	@SuppressWarnings("unused")
	public static void main(String[] args)
	{
		Juego juego = new Juego();
	}
}
