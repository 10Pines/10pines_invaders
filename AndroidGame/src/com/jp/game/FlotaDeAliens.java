package com.jp.game;

import java.util.ArrayList;
import java.util.Arrays;
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
	private int primeraFila;
	private Rect areaFlota;

	
	public static FlotaDeAliens create(int columnas, int filas, Rect areaFlota) {
		FlotaDeAliens instance = new FlotaDeAliens();
		instance.aliens = new Alien[columnas][filas];
		instance.posicion = new Point(0,0);
		instance.primeraColumna = 0;
		instance.ultimaColumna = columnas - 1;
		instance.ultimaFila = filas - 1;
		instance.primeraFila = 0;
		instance.sentido = 1;
		instance.areaFlota = areaFlota;
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
		for (Alien[] columna : aliens) {
			for (Alien alien : columna) {
				alien.dibujar(canvas);
			}
		}
	}

	public int getMaxPosicionX() {
		return getBoundingBox().right;
	}

	public int getMinPosicionX() {
		return getBoundingBox().left;
	}

	public void avanzar(int velocidad) throws FinDeJuegoException {
		if(llegaAlLimiteEnX()){
			invertirSentido();
			posicion.y += (velocidad * 5);
			if(llegaAlLimiteEnY()){
				throw new FinDeJuegoException();
			}
		}
		int delta = velocidad * sentido;
		posicion.x += delta;
		
	}

	private boolean llegaAlLimiteEnY() {
		int maxPosicionY = areaFlota.bottom;
		return this.getMaxPosicionY() >= maxPosicionY;
	}

	private int getMaxPosicionY() {
		return getBoundingBox().bottom;
	}

	private boolean llegaAlLimiteEnX() {
		int maxPosicionX = areaFlota.right;
		int minPosicionX = areaFlota.left;
		return (sentido > 0 && this.getMaxPosicionX() >= maxPosicionX ) || (sentido < 0 && this.getMinPosicionX() <= minPosicionX );
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

	public void verificarColisionesConProyectiles(List<Proyectil> proyectiles) {
		List<Impacto<Proyectil>> impactos = detectarImpactosCon(proyectiles);
		for (Impacto<Proyectil> impacto : impactos) {
			Alien alienImpactado = impacto.getAlien();
			alienImpactado.morir();
			proyectiles.remove(impacto.getObjetoMovible());
		}
		ajustarBoundingBox();
	}

	private <T extends ObjetoMovible> List<Impacto<T>> detectarImpactosCon(List<T> objetosMovibles) {
		List<Impacto<T>> impactos = new ArrayList<Impacto<T>>();
		
		for (T objetoMovible : objetosMovibles) {
			//Verifico si esta en mi bounding box
			Point puntoDeColision = objetoMovible.getPuntoDeColision();
			Rect boundingBoxFlota = getBoundingBox();
			if(boundingBoxFlota.contains(puntoDeColision.x, puntoDeColision.y)){
				Point posicionImpacto = obtenerPosicionDentroDeLaFlota(puntoDeColision);
				int columna = posicionImpacto.x;
				int fila = posicionImpacto.y;
				if(aliens[columna][fila].estaVivo()){
					Impacto<T> impacto = Impacto.create(aliens[columna][fila], objetoMovible);
					impactos.add(impacto);
				}
			}
		}
		
		return impactos;
		
	}

	private void ajustarBoundingBox() throws FinDeJuegoException {
		primeraColumna = calcularPrimeraColumna();
		if(primeraColumna==TODOS_MUERTOS){
			throw new FinDeJuegoException();
		}
		ultimaColumna = calcularUltimaColumna();
		if(ultimaColumna==TODOS_MUERTOS){
			throw new FinDeJuegoException();
		}
		primeraFila = calcularPrimeraFila();
		if(primeraFila==TODOS_MUERTOS){
			throw new FinDeJuegoException();
		}
		ultimaFila = calcularUltimaFila();
		if(ultimaFila==TODOS_MUERTOS){
			throw new FinDeJuegoException();
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

	@Override
	public Rect getBoundingBox() {
		return new Rect(posicion.x + getAnchoAlien() * primeraColumna, posicion.y + getAltoAlien() * primeraFila, 
				posicion.x + getAnchoAlien() * (ultimaColumna+1), posicion.y + getAltoAlien() * (ultimaFila+1));
	}

	public void verificarColisionConNave(Nave nave) throws FinDeJuegoException {
		List<Nave> listaNave = Arrays.asList(nave);
		List<Impacto<Nave>> impactos = detectarImpactosCon(listaNave);
		if(!impactos.isEmpty()){
			throw new FinDeJuegoException();
		}
		
	}

}
