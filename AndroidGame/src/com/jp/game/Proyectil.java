package com.jp.game;

import android.graphics.Point;

public class Proyectil extends ObjetoMovible {

	private static final int velocidad = -5;

	public static Proyectil create(Point posicionInicial) {
		Proyectil instance = new Proyectil();
		instance.setImagen(Assets.proyectil);
		instance.setPosicion(posicionInicial);
		return instance;
	}
	
	public void avanzar() {
		this.desplazarY(velocidad);
	}

	public boolean debeDestruir() {
		return getPosicion().y <= 0;
	}

}
