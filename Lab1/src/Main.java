import com.unist.Lab1.Color;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {

        String hexColor = "0x1FF0FF";

        Color c = Color.decode(hexColor);
        System.out.println(c);

        float[] hsbCode = new float[3];
        float[] hslCode = new float[3];
        float[] cmykCode = new float[4];

        Color.RGBtoHSB(c.getRed(), c.getGreen(), c.getBlue(), hsbCode);

        System.out.println("Color in HEX format: 0x" + Integer.toHexString(c.getRGB() & 0x00FFFFFF));
        System.out.println("Color in RGB format: " + c.getRed() + ", " + c.getGreen() + ", " + c.getBlue());
        System.out.println("Color in HSB format: " + hsbCode[0] * 360 + "°, " + hsbCode[1] * 100 + "%, " + hsbCode[2] * 100 + "%");

        Color.RGBtoHSL(c.getRGB(), hslCode);
        System.out.println("Color in HSL format: " + hslCode[0] + ", " + hslCode[1] + "%, " + hslCode[2] + "%");

        Color.RGBtoCMYK(c.getRed(), c.getGreen(), c.getBlue(), cmykCode);
        System.out.println("Color in CMYK format: " + Math.round(cmykCode[0] * 100) + ", " +
                Math.round(cmykCode[1] * 100) + ", " + Math.round(cmykCode[2] * 100) + ", " + Math.round(cmykCode[3] * 100));
    }
}