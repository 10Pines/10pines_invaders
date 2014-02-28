package com.jp.game;

import com.jp.framework.Graphics;


public class FlotaDeAliens implements Dibujable {
	
	private int posicionX;
	private int posicionY;
	private int primeraColumna;
	private int ultimaColumna;
	private int ultimaFila;
	private Alien[][] aliens;
	private int sentido;
	private int maxPosicionX;
	private int minPosicionX;
	private int maxPosicionY;
	private int minPosicionY;

	
	public static FlotaDeAliens create(int filas, int columnas, int minPosicionX, int maxPosicionX,
			int minPosicionY, int maxPosicionY) {
		FlotaDeAliens instance = new FlotaDeAliens();
		instance.aliens = new Alien[filas][columnas];
		instance.crearAliens();
		instance.posicionX = 0;
		instance.posicionY = 0;
		instance.primeraColumna = 0;
		instance.ultimaColumna = columnas - 1;
		instance.ultimaFila = filas - 1;
		instance.sentido = 1;
		instance.maxPosicionX = maxPosicionX;
		instance.minPosicionX = minPosicionX;
		instance.maxPosicionY = maxPosicionY;
		instance.minPosicionY = minPosicionY;
		return instance;
	}
	
	private void crearAliens() {
		int ancho = (int) (Assets.alien.getWidth() * 1.1);
		int alto = (int) (Assets.alien.getHeight() * 1.1);
		for (int fila = 0; fila < aliens.length; fila++) {
			for (int columna = 0; columna < aliens[fila].length; columna++){
				aliens[fila][columna] = Alien.create(columna * ancho, fila * alto);
			}
		}
	}

	@Override
	public void dibujar(Graphics canvas) {
		for (Alien[] fila : aliens) {
			for (Alien alien : fila) {
				alien.dibujar(canvas);
			}
		}
	}

	public int getMaxPosicionX() {
		return posicionX + getAncho() * ultimaColumna;
	}

	public int getMinPosicionX() {
		return posicionX + getAncho() * primeraColumna;
	}

	public void avanzar(int velocidad) {
		if(llegaAlLimite()){
			invertirSentido();
			bajarAliens(velocidad * 5);
			posicionY += velocidad;
		}
		int delta = velocidad * sentido;
		posicionX += delta;
		desplazarAliens(delta);
		
		
	}

	private boolean llegaAlLimite() {
		return (sentido > 0 && this.getMaxPosicionX() >= maxPosicionX) || (sentido < 0 && this.getMinPosicionX() <= minPosicionX);
	}

	private void invertirSentido() {
		sentido = sentido * -1;
	}

	private void desplazarAliens(int velocidad) {
		for (Alien[] fila : aliens) {
			for (Alien alien : fila) {
				alien.desplazarX(velocidad);
			}
		}
	}

	private void bajarAliens(int velocidad) {
		for (Alien[] fila : aliens) {
			for (Alien alien : fila) {
				alien.desplazarY(velocidad);
			}
		}
	}
	
	private int getAncho() {
		return Assets.alien.getWidth();
	}
}
