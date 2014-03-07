package com.jp.game;

import com.jp.framework.Graphics;

public enum EstadoAlien {
	VIVO {

		@Override
		public void dibujar(Alien alien, Graphics canvas) {
			alien.dibujarVivo(canvas);
		}

		@Override
		public boolean estaVivo() {
			return true;
		}
		
	},
	MUERTO {

		@Override
		public void dibujar(Alien alien, Graphics canvas) {
			// NO HAGO NADA
		}

		@Override
		public boolean estaVivo() {
			return false;
		}
		
	};

	public abstract void dibujar(Alien alien, Graphics canvas);

	public abstract boolean estaVivo();
	
}
