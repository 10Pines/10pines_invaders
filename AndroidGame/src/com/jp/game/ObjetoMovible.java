package com.jp.game;

import android.graphics.Point;

import com.jp.framework.Graphics;
import com.jp.framework.Image;

public abstract class ObjetoMovible implements Dibujable {
	
	protected Image imagen;
	private Point posicion = new Point();
	
	public void dibujar(Graphics canvas){
		canvas.drawImage(imagen, getPosicion());
	}
	
	public Point getPosicion() {
		return posicion;
	}
	
	public void setPosicion(Point posicion) {
		this.posicion = posicion;
	}
	
	public void setPosicionX(int posicionX) {
		this.posicion.x = posicionX;
	}
	
	public void setPosicionY(int posicionY) {
		this.posicion.y = posicionY;
	}

	public void desplazarX(int velocidad) {
		this.posicion.x += velocidad;
		
	}

	public void desplazarY(int velocidad) {
		this.posicion.y += velocidad;
	}

	public int getAlto() {
		return imagen.getHeight();
	}

	public int getAncho() {
		return imagen.getWidth();
	}

	public Image getImagen() {
		return imagen;
	}

	public void setImagen(Image imagen) {
		this.imagen = imagen;
	}
	
}
