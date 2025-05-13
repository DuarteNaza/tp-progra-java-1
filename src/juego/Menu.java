package juego;

public class Menu{
	private int x;
	private int ancho;
	private int alto;

	private BotonHechizo[] botones;
	private int hechizoSeleccionado;
	
	private int enemigosEliminados;
	private int vidaJugador;
	private int energiaMagica;
	
	public Menu(int x, int ancho, int alto) {
		this.x = x;
		this.ancho = ancho;
		this.alto = alto;
		this.hechizoSeleccionado =  -1;
		
		this.botones = new BotonHechizo[] {
				new BotonHechizo("Explosion",100,0), 
				new BotonHechizo("Fuego",160,30)
				
		};
		
	}
	public void actualizar (int vida, int magia,int kills) {
		this.vidaJugador = vida;
		this.energiaMagica = magia;
		this.enemigosEliminados = kills;
		
	}
