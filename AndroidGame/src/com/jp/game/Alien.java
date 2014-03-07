package com.jp.game;

import com.jp.framework.Graphics;

import android.graphics.Point;


public class Alien extends ObjetoMovible {
	
	private Point posicionDentroDeFlota;
	private Point posicionFlota;
	private EstadoAlien estado;
	public static Alien create(Point posicionFlota, Point posicionDentroDeFlota) {
		Alien instance = new Alien();
		instance.posicionFlota = posicionFlota;
		instance.posicionDentroDeFlota = posicionDentroDeFlota;
		instance.estado = EstadoAlien.VIVO;
		instance.setImagen(Assets.alien);
	
		return instance;
	}

	private Point obtenerPosicionAbsoluta() {
		int posicionX = posicionFlota.x + posicionDentroDeFlota.x * getAncho();
		int posicionY = posicionFlota.y + posicionDentroDeFlota.y * getAlto();
		return new Point(posicionX, posicionY);
	}
	
	@Override
	public Point getPosicion() {
		return obtenerPosicionAbsoluta();
	}
	
	@Override
	public void dibujar(Graphics canvas) {
		estado.dibujar(this, canvas);
	}

	public void dibujarVivo(Graphics canvas) {
		super.dibujar(canvas);
	}

	public boolean estaVivo() {
		return estado.estaVivo();
	}

	public void morir() {
		explotar();
		estado = EstadoAlien.MUERTO;
		
	}

	private void explotar() {
		// TODAVIA NO HACE NADA
		
	}
}
