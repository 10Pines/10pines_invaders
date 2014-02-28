package com.jp.game;

import android.graphics.Point;
import android.graphics.Rect;

import com.jp.framework.Input.TouchEvent;

public class Utils {

	public static boolean inBounds(TouchEvent event, int x, int y, int width,
			int height) {
		Point posicion = new Point(event.x, event.y);
		return inBounds(posicion , x, y, width, height);
	}
	
	public static boolean inBounds(Point posicion, int x, int y, int width,
			int height) {
		return posicion.x > x && posicion.x < x + width - 1 && posicion.y > y
				&& posicion.y < y + height - 1;
	}
	
	public static boolean inBounds(Point punto, Rect box) {
		return box.contains(punto.x, punto.y);
	}
}
