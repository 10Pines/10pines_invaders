package com.jp.game;

import android.graphics.Point;

import com.jp.framework.Input.TouchEvent;


public class Nave extends ObjetoMovible {
	
	private Point inicioZonaTouch;
	private Point finZonaTouch;
	
	public static Nave create(Point posicion, Point inicioZonaTouch, Point finZonaTouch) {
		Nave instance = new Nave();
		instance.setImagen(Assets.nave);
		instance.setPosicion(posicion);
		instance.inicioZonaTouch = inicioZonaTouch;
		instance.finZonaTouch = finZonaTouch;
		return instance;
	}
		
	public boolean debeDisparar(TouchEvent event) {
		return event.type == TouchEvent.TOUCH_DOWN;
	}

	
	
	public void mover(TouchEvent event) {
		int posX = this.getPosicion().x;
		int posY = this.getPosicion().y;
		int ancho = this.getAncho();
		int alto = this.getAlto();
		if (event.type == TouchEvent.TOUCH_DRAGGED) {
			if (Utils.inBounds(event, posX, posY, ancho, alto)) {
				// Muevo la nave
				this.setPosicionX(event.x - ancho/2);
			}
		}
		if (event.type == TouchEvent.TOUCH_DOWN) {
			int deltaX = finZonaTouch.x - inicioZonaTouch.x;
			int deltaY = finZonaTouch.y - inicioZonaTouch.y;
			if (Utils.inBounds(event, inicioZonaTouch.x, inicioZonaTouch.y, deltaX, deltaY)) {
				// Muevo la nave
				this.setPosicionX(event.x - ancho/2);
			}
		}
	}

	public Proyectil disparar() {
		Proyectil proyectil = Proyectil.create(getPosicionPuntaNave());
		return proyectil;
	}

	private Point getPosicionPuntaNave() {
		Point posicion = new Point(this.getPosicion().x + this.getAncho()/2, this.getPosicion().y);
		return posicion;
	}

}
