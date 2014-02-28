package com.jp.game;

import java.util.Arrays;
import java.util.List;

import android.graphics.Color;
import android.graphics.Paint;

import com.jp.framework.Game;
import com.jp.framework.Graphics;
import com.jp.framework.Graphics.ImageFormat;
import com.jp.framework.Input.TouchEvent;
import com.jp.framework.Screen;

public class MainMenuScreen extends Screen {
	
	private static final int MAX_POSICION_X = 680;
	private static final int MIN_POSICION_X = 0;
	private static final int MIN_POSICION_Y = 0;
	private static final int MAX_POSICION_Y = 1090;
	private ObjetoMovible nave;
	private ObjetoMovible alien;
	private List<Dibujable> dibujables;
	private FlotaDeAliens flota;
	private int sentido;
	private int velocidad = 3;

	@Override
	public void init() {
		Graphics canvas = game.getGraphics();
		Assets.nave = canvas.newImage("nave.png", ImageFormat.RGB565);
		Assets.alien = canvas.newImage("alien.png", ImageFormat.RGB565);
		
		flota = FlotaDeAliens.create(3, 4, MIN_POSICION_X, MAX_POSICION_X, MIN_POSICION_Y, MAX_POSICION_Y);
		nave = ObjetoMovible.create(Assets.nave, 200, 500);
		
		dibujables = Arrays.asList(nave, flota);
	}
	
	public MainMenuScreen(Game game) {
		super(game);
	}

	@Override
	public void update(float deltaTime) {
		Graphics canvas = game.getGraphics();
		canvas.clearScreen(Color.GRAY);

		moverObjetos();
		dibujarDibujables(canvas);
		
		List<TouchEvent> touchEvents = game.getInput().getTouchEvents();

		
		int len = touchEvents.size();
		for (int i = 0; i < len; i++) {
			TouchEvent event = touchEvents.get(i);
			if (event.type == TouchEvent.TOUCH_UP) {

				if (inBounds(event, 0, 0, 250, 250)) {
					// START GAME
					game.setScreen(new GameScreen(game));
				}

			}
		}
		
	}

	private void moverObjetos() {
		flota.avanzar(velocidad);
		
	}

	private void dibujarDibujables(Graphics canvas) {
		for (Dibujable dibujable : dibujables) {
			dibujable.dibujar(canvas);
		}
	}

	private boolean inBounds(TouchEvent event, int x, int y, int width,
			int height) {
		if (event.x > x && event.x < x + width - 1 && event.y > y
				&& event.y < y + height - 1)
			return true;
		else
			return false;
	}

	@Override
	public void paint(float deltaTime) {
		Graphics g = game.getGraphics();
		//g.drawImage(Assets.menu, 0, 0);
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {

	}

	@Override
	public void dispose() {

	}

	@Override
	public void backButton() {
		// Display "Exit Game?" Box

	}
}