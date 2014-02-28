package com.jp.game;


public class Alien extends ObjetoMovible {
	
	public static Alien create(int posicionX, int posicionY) {
		Alien instance = new Alien();
		
		instance.setImagen(Assets.alien);
		instance.setPosicionX(posicionX);
		instance.setPosicionY(posicionY);
		
		return instance;
	}


}
