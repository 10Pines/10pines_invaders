package com.jp.game;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;

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
	private static final int PANTALLA_ALTO = 1280;
	private static final int PANTALLA_ANCHO = 800;
	private static final int CANTIDAD_FILAS_ALIENS = 4;
	private static final int CANTIDAD_COLUMNAS_ALIENS = 8;
	private static final int POSICION_INICIAL_NAVE_X = 300;
	private static final int POSICION_INICIAL_NAVE_Y = 1100;
	private Nave nave;
	private List<Dibujable> dibujables;
	private FlotaDeAliens flota;
	private int sentido;
	private int velocidad = 3;
	private List<Proyectil> proyectiles = new ArrayList<Proyectil>();

	@Override
	public void init() {
		Graphics canvas = game.getGraphics();
		Assets.nave = canvas.newImage("nave.png", ImageFormat.RGB565);
		Assets.alien = canvas.newImage("alien.png", ImageFormat.RGB565);
		Assets.proyectil = canvas.newImage("proyectil.png", ImageFormat.RGB565);
		
		flota = FlotaDeAliens.create(5, 8, MIN_POSICION_X, MAX_POSICION_X, MIN_POSICION_Y, MAX_POSICION_Y);
		
		Point puntoInicialNave =  new Point(POSICION_INICIAL_NAVE_X, POSICION_INICIAL_NAVE_Y);
		Point inicioZonaTouchNave = new Point(0, POSICION_INICIAL_NAVE_Y);
		Point finZonaTouchNave = new Point(PANTALLA_ANCHO, PANTALLA_ALTO);
		nave = Nave.create(puntoInicialNave, inicioZonaTouchNave, finZonaTouchNave);
		
		dibujables = Arrays.asList(nave, flota);
	}

	private void dibujarTimestamp(Graphics canvas) {
		Paint paint = new Paint();
		paint.setTextSize(32);
		paint.setColor(Color.WHITE);
		canvas.drawString(Calendar.getInstance().toString(), 10, 10, paint);
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
			
		detectarTouches();
		
	}

	private void detectarTouches() {
		List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
		int len = touchEvents.size();
		for (int i = 0; i < len; i++) {
			TouchEvent event = touchEvents.get(i);
			nave.mover(event);
			if(nave.debeDisparar(event)){
				Proyectil proyectil = nave.disparar();
				proyectiles.add(proyectil);
			}
			
			/*
			if (event.type == TouchEvent.TOUCH_UP) {
				if (Utils.inBounds(event, 0, 0, 250, 250)) {
					// START GAME
					game.setScreen(new GameScreen(game));
				}
			}
			*/
			
		}
	}

	private void moverObjetos() {
		flota.avanzar(velocidad);
		moverProyectiles();
		verificarImpactos(flota, proyectiles);
	}

	private void verificarImpactos(FlotaDeAliens flota,
			List<Proyectil> proyectiles) {
		flota.recibirProyectiles(proyectiles);
	}

	private void moverProyectiles() {
		for (Proyectil proyectil : proyectiles) {
			proyectil.avanzar();
			if(proyectil.debeDestruir()){
				proyectiles.remove(proyectil);
			}
		}
		
	}

	private void dibujarDibujables(Graphics canvas) {
		for (Dibujable dibujable : dibujables) {
			dibujable.dibujar(canvas);
		}
		for (Proyectil proyectil : proyectiles) {
			proyectil.dibujar(canvas);
		}
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