package com.jp.game;

public class Impacto<U> {

	private Alien alien;
	private U objetoMobible;

	public static <T> Impacto<T> create(Alien alien, T objetoMovible) {
		Impacto<T> impacto = new Impacto<T>();
		impacto.alien = alien;
		impacto.objetoMobible = objetoMovible;
		return impacto ;
	}

	public U getObjetoMovible() {
		return objetoMobible;
	}

	public Alien getAlien() {
		return alien;
	}

	
}
