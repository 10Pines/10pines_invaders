package com.jp.framework;

import com.jp.framework.Graphics.ImageFormat;

public interface Image {
    public int getWidth();
    public int getHeight();
    public ImageFormat getFormat();
    public void dispose();
}