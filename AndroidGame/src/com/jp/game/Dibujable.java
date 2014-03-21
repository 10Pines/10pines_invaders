package com.jp.game;

import android.graphics.Rect;

import com.jp.framework.Graphics;

public interface Dibujable {
	
	public Rect getBoundingBox();
	
	public void dibujar(Graphics canvas);
	
}
