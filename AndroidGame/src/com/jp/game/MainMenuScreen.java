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
	
	private static final int MAX_POSICION_X = 640;
	private static final int MIN_POSICION_X = 0;
	private ObjetoMovible nave;
	private ObjetoMovible alien;
	private List<ObjetoMovible> dibujables;
	private int sentido;
	private int velocidad = 3;

	@Override
	public void init() {
		Graphics canvas = game.getGraphics();
		Assets.nave = canvas.newImage("nave.png", ImageFormat.RGB565);
		Assets.alien = canvas.newImage("alien.png", ImageFormat.RGB565);
		
		nave = ObjetoMovible.create(Assets.nave, 200, 500);
		alien = ObjetoMovible.create(Assets.alien, 350, 240);
		
		dibujables = Arrays.asList(nave, alien);
	}
	
	public MainMenuScreen(Game game) {
		super(game);
	}

	@Override
	public void update(float deltaTime) {
		Graphics canvas = game.getGraphics();
		canvas.clearScreen(Color.GRAY);
		
		Paint paint = new Paint();
		paint.setColor(Color.WHITE);
		paint.setTextSize(32);
		canvas.drawString("Delta time:" + deltaTime, 0, 32, paint);
		canvas.drawLine(0, 0, 500, 500, Color.WHITE);
		
		moverAlien();
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

	private void moverAlien() {
		
		if((velocidad > 0 && alien.getPosicionX() >= MAX_POSICION_X) || (velocidad < 0 && alien.getPosicionX() <= MIN_POSICION_X)){
			velocidad = velocidad * -1;
		}
		
		alien.desplazarX(velocidad);
	}

	private void dibujarDibujables(Graphics canvas) {
		for (ObjetoMovible dibujable : dibujables) {
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