```java
import java.awt.Color;  

public class ColorConverter {  
public static void main(String[] args) {  
	String hexColor = "0x1FF0FF";  
	Color c = Color.decode(hexColor);  
	float[] hsbCode = new float[3];  
	Color.RGBtoHSB(c.getRed(), c.getGreen(), c.getBlue(), hsbCode);  
	System.out.println("Boja u HEX formatu: 0x" +  
	Integer.toHexString(c.getRGB() & 0x00FFFFFF));  
	System.out.println("Boja u RGB formatu: " + c.getRed() + ", " +  
	c.getGreen() + ", " + c.getBlue());  
	System.out.println("Boja u HSB formatu: " + hsbCode[0] * 360 + "°, " +  
	hsbCode[1] * 100 + "%, " + hsbCode[2] * 100 + "%");  
	}  
}
```


- [x] Create class Color so that the source code above will work with your class Color 
- [x] Extend class Color with method to convert RGB to CMYk
- [x] Extend class Color with method to convert RGB to HSL
- [x] Extend class Color with method to convert RGB to HSB
- [x] Create unit tests for class Color
