package com.jp.game;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;

import com.jp.framework.Graphics;
import com.jp.game.exceptions.FinDeJuegoException;


public class FlotaDeAliens implements Dibujable {
	
	private static final int TODOS_MUERTOS = -1;
	private Point posicion;
	private int primeraColumna;
	private int ultimaColumna;
	private int ultimaFila;
	private Alien[][] aliens;
	private int sentido;
	private int maxPosicionX;
	private int minPosicionX;
	private int maxPosicionY;
	private int minPosicionY;
	private int primeraFila;

	
	public static FlotaDeAliens create(int filas, int columnas, int minPosicionX, int maxPosicionX,
			int minPosicionY, int maxPosicionY) {
		FlotaDeAliens instance = new FlotaDeAliens();
		instance.aliens = new Alien[columnas][filas];
		instance.posicion = new Point(0,0);
		instance.primeraColumna = 0;
		instance.ultimaColumna = columnas - 1;
		instance.ultimaFila = filas - 1;
		instance.primeraFila = 0;
		instance.sentido = 1;
		instance.maxPosicionX = maxPosicionX;
		instance.minPosicionX = minPosicionX;
		instance.maxPosicionY = maxPosicionY;
		instance.minPosicionY = minPosicionY;
		instance.crearAliens();
		return instance;
	}
	
	private void crearAliens() {
		for (int columna = 0; columna < aliens.length; columna++) {
			for (int fila = 0; fila < aliens[columna].length; fila++){
				Point posicionDentroDeFlota = new Point(columna, fila);
				aliens[columna][fila] = Alien.create(posicion, posicionDentroDeFlota);
			}
		}
	}

	@Override
	public void dibujar(Graphics canvas) {
		dibujarBoundingBox(canvas);
		for (Alien[] columna : aliens) {
			for (Alien alien : columna) {
				alien.dibujar(canvas);
			}
		}
	}

	private void dibujarBoundingBox(Graphics canvas) {
		canvas.drawRect(getBoundingBox(), Color.WHITE);
	}

	public int getMaxPosicionX() {
		return posicion.x + getAnchoAlien() * ultimaColumna;
	}

	public int getMinPosicionX() {
		return posicion.x + getAnchoAlien() * primeraColumna;
	}

	public void avanzar(int velocidad) {
		if(llegaAlLimite()){
			invertirSentido();
			posicion.y += (velocidad * 5);
		}
		int delta = velocidad * sentido;
		posicion.x += delta;
		
	}

	private boolean llegaAlLimite() {
		return (sentido > 0 && this.getMaxPosicionX() >= maxPosicionX) || (sentido < 0 && this.getMinPosicionX() <= minPosicionX);
	}

	private void invertirSentido() {
		sentido = sentido * -1;
	}

	private int getAnchoAlien() {
		return Assets.alien.getWidth();
	}
	private int getAltoAlien() {
		return Assets.alien.getHeight();
	}

	public void recibirProyectiles(List<Proyectil> proyectiles) {
		List<Proyectil> proyectilesImpactados = new ArrayList<Proyectil>();
		for (Proyectil proyectil : proyectiles) {
			impactarEnAlien(proyectil, proyectilesImpactados);
		}
		proyectiles.removeAll(proyectilesImpactados);
	}

	private void impactarEnAlien(Proyectil proyectil, List<Proyectil> proyectilesImpactados) {
		//Verifico si esta en mi bounding box
		Point posicionProyectil = proyectil.getPosicion();
		
		if(Utils.inBounds(posicionProyectil, getBoundingBox())){
			Point posicionImpacto = obtenerPosicionDentroDeLaFlota(posicionProyectil);
			int columna = posicionImpacto.x;
			int fila = posicionImpacto.y;
			if(aliens[columna][fila].estaVivo()){
				aliens[columna][fila].morir();
				ajustarBoundingBox(columna, fila);
				proyectilesImpactados.add(proyectil);
			}
		}
	}


	private void ajustarBoundingBox(int columna, int fila) throws FinDeJuegoException {
		if(columna==primeraColumna){
			primeraColumna = calcularPrimeraColumna();
			if(primeraColumna==TODOS_MUERTOS){
				throw new FinDeJuegoException();
			}
		}
		if(columna==ultimaColumna){
			ultimaColumna = calcularUltimaColumna();
			if(ultimaColumna==TODOS_MUERTOS){
				throw new FinDeJuegoException();
			}
		}
		if(fila==primeraFila){
			primeraFila = calcularPrimeraFila();
			if(primeraFila==TODOS_MUERTOS){
				throw new FinDeJuegoException();
			}
		}
		if(fila==ultimaFila){
			ultimaFila = calcularUltimaFila();
			if(ultimaFila==TODOS_MUERTOS){
				throw new FinDeJuegoException();
			}
		}
		
	}

	private int calcularPrimeraColumna() {
		for(int columna = primeraColumna; columna <= ultimaColumna; columna++) {
			for(int fila = primeraFila; fila <= ultimaFila; fila++){
				if(aliens[columna][fila].estaVivo()) {
					return columna;
				}
			}
		}
		//QUIERE DECIR QUE NO HAY ALIENS VIVOS
		return TODOS_MUERTOS;
	}
	
	private int calcularUltimaColumna() {
		for(int columna = ultimaColumna; columna >= primeraColumna; columna--) {
			for(int fila = primeraFila; fila <= ultimaFila; fila++){
				if(aliens[columna][fila].estaVivo()) {
					return columna;
				}
			}
		}
		//QUIERE DECIR QUE NO HAY ALIENS VIVOS
		return TODOS_MUERTOS;
	}
	
	private int calcularPrimeraFila() {
		for(int fila = primeraFila; fila <= ultimaFila; fila++){
			for(int columna = primeraColumna; columna <= ultimaColumna; columna++) {
				if(aliens[columna][fila].estaVivo()) {
					return fila;
				}
			}
		}
		//QUIERE DECIR QUE NO HAY ALIENS VIVOS
		return TODOS_MUERTOS;
	}
	
	private int calcularUltimaFila() {
		for(int fila = ultimaFila; fila >= primeraFila; fila--){
			for(int columna = primeraColumna; columna <= ultimaColumna; columna++) {
				if(aliens[columna][fila].estaVivo()) {
					return fila;
				}
			}
		}
		//QUIERE DECIR QUE NO HAY ALIENS VIVOS
		return TODOS_MUERTOS;
	}

	private Point obtenerPosicionDentroDeLaFlota(Point posicionAbsoluta) {
		int ancho = getAnchoAlien();
		int alto = getAltoAlien();
		int columna = (posicionAbsoluta.x - posicion.x) / ancho;
		int fila = (posicionAbsoluta.y - posicion.y) / alto;
		return new Point(columna, fila);
	}

	private Rect getBoundingBox() {
		return new Rect(posicion.x + getAnchoAlien() * primeraColumna, posicion.y + getAltoAlien() * primeraFila, 
				posicion.x + getAnchoAlien() * (ultimaColumna+1), posicion.y + getAltoAlien() * (ultimaFila+1));
	}

}
