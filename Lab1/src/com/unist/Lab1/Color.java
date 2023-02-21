package com.unist.Lab1;

public class Color {
    private final int value;

    public Color(int r, int g, int b, int a) {
        value = ((a & 0xFF) << 24) |
                ((r & 0xFF) << 16) |
                ((g & 0xFF) << 8) |
                ((b & 0xFF));

    }

    public static float[] RGBtoHSL(int rgb, float[] hslvals) {
        float r = ((0x00ff0000 & rgb) >> 16) / 255.f;
        float g = ((0x0000ff00 & rgb) >> 8) / 255.f;
        float b = ((0x000000ff & rgb)) / 255.f;
        float max = Math.max(Math.max(r, g), b);
        float min = Math.min(Math.min(r, g), b);
        float c = max - min;

        float h_ = 0.f;
        if (c == 0) {
            h_ = 0;
        } else if (max == r) {
            h_ = (g - b) / c;
            if (h_ < 0) h_ += 6.f;
        } else if (max == g) {
            h_ = (b - r) / c + 2.f;
        } else if (max == b) {
            h_ = (r - g) / c + 4.f;
        }
        float h = 60.f * h_;

        float l = (max + min) * 0.5f;

        float s;
        if (c == 0) {
            s = 0.f;
        } else {
            s = c / (1 - Math.abs(2.f * l - 1.f));
        }

        hslvals[0] = h;
        hslvals[1] = s;
        hslvals[2] = l;
        return hslvals;
    }

    public static Color decode(String nm) throws NumberFormatException {
        int i = Integer.decode(nm);
        return new Color((i >> 16) & 0xFF, (i >> 8) & 0xFF, i & 0xFF, 255);
    }

    public static float[] RGBtoHSB(int r, int g, int b, float[] hsbvals) {
        float hue, saturation, brightness;
        if (hsbvals == null) {
            hsbvals = new float[3];
        }
        int cmax = Math.max(r, g);
        if (b > cmax) cmax = b;
        int cmin = Math.min(r, g);
        if (b < cmin) cmin = b;

        brightness = ((float) cmax) / 255.0f;
        if (cmax != 0)
            saturation = ((float) (cmax - cmin)) / ((float) cmax);
        else
            saturation = 0;
        if (saturation == 0)
            hue = 0;
        else {
            float redc = ((float) (cmax - r)) / ((float) (cmax - cmin));
            float greenc = ((float) (cmax - g)) / ((float) (cmax - cmin));
            float bluec = ((float) (cmax - b)) / ((float) (cmax - cmin));
            if (r == cmax)
                hue = bluec - greenc;
            else if (g == cmax)
                hue = 2.0f + redc - bluec;
            else
                hue = 4.0f + greenc - redc;
            hue = hue / 6.0f;
            if (hue < 0)
                hue = hue + 1.0f;
        }
        hsbvals[0] = hue;
        hsbvals[1] = saturation;
        hsbvals[2] = brightness;
        return hsbvals;
    }

    public static float[] RGBtoCMYK(int r, int g, int b, float[] cmyk) {
        float percentageR = (float) r / 255;
        float percentageG = (float) g / 255;
        float percentageB = (float) b / 255;

        float k = 1 - Math.max(Math.max(percentageR, percentageG), percentageB);
        if (k == 100) {
            cmyk[0] = 0;
            cmyk[1] = 0;
            cmyk[2] = 0;
            cmyk[3] = 100;
        } else {
            float c = ((1 - percentageR - k) / (1 - k));
            float m = ((1 - percentageG - k) / (1 - k));
            float y = ((1 - percentageB - k) / (1 - k));

            cmyk[0] = c;
            cmyk[1] = m;
            cmyk[2] = y;
            cmyk[3] = k;
        }
        return cmyk;
    }

    public int getRGB() {
        return value;
    }

    public int getRed() {
        return (getRGB() >> 16) & 0xFF;
    }

    public int getGreen() {
        return (getRGB() >> 8) & 0xFF;
    }

    public int getBlue() {
        return (getRGB()) & 0xFF;
    }

    public String toString() {
        return getClass().getName() + "[r=" + getRed() + ",g=" + getGreen() + ",b=" + getBlue() + "]";
    }
}
