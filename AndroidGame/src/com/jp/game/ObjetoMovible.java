package com.jp.game;

import com.jp.framework.Graphics;
import com.jp.framework.Image;

public class ObjetoMovible {
	private Image imagen;
	private int posicionX;
	private int posicionY;

	public static ObjetoMovible create(Image imagen, int posicionX, int posicionY) {
		ObjetoMovible instance = new ObjetoMovible();
		instance.imagen = imagen;
		instance.posicionX = posicionX;
		instance.posicionY = posicionY;
		
		return instance;
	}
	
	public void dibujar(Graphics canvas){
		canvas.drawImage(imagen, posicionX, posicionY);
	}
	
	public void setPosicion(int posicionX, int posicionY) {
		this.posicionX = posicionX;
		this.posicionY = posicionY;
	}

	public void desplazarX(int velocidad) {
		this.posicionX += velocidad;
		
	}

	public int getPosicionX() {
		return this.posicionX;
	}
}
