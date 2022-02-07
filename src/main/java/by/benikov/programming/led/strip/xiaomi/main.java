package by.benikov.programming.led.strip.xiaomi;

import by.benikov.programming.led.strip.xiaomi.enumeration.YeelightEffect;
import by.benikov.programming.led.strip.xiaomi.exception.YeelightResultErrorException;
import com.mollin.yapi.exception.YeelightSocketException;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class main {
    public static void main(String[] args) throws IOException, AWTException, InterruptedException, com.mollin.yapi.exception.YeelightResultErrorException, YeelightSocketException {
        boolean connected = false;
        int red, green, blue;
        YeelightDevice device = null;
        try {
            device = new YeelightDevice("192.168.43.14",55443);
            connected = true;
            while(true){
                red = getPredominantColor().getRed();
                green = getPredominantColor().getGreen();
                blue = getPredominantColor().getBlue();
                if(red > 0 && green > 0 && blue > 0)
                    device.setRGB(red, green, blue);
            }
        } catch (YeelightSocketException e) {
            connected = false;
            System.out.println("Cant connect to Strip!");
        }
        if(!connected){
            while(true) {
                red = getPredominantColor().getRed();
                green = getPredominantColor().getGreen();
                blue = getPredominantColor().getBlue();
            }
        }
    }

    public static BufferedImage getScreenshot() throws AWTException, IOException, InterruptedException {
        Thread.currentThread().sleep(500);
        BufferedImage srceenshot = new Robot().createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
        ImageIO.write(srceenshot,"png",new File("s.png"));
        return srceenshot;
    }

    public static Map<Color, Integer> getColorMap(BufferedImage screenshot){
        Map<Color, Integer> colorMap = new HashMap<>();
        Color color;
        for (int y = 0; y < screenshot.getHeight(); y++){
            for (int x = 0; x < screenshot.getWidth(); x++){
                color = new Color(screenshot.getRGB(x,y));
                if(!colorExist(color, colorMap)){
                    colorMap.put(color,1);
                    continue;
                }
                colorMap.put(color,colorMap.get(color) + 1);
            }
        }
        return colorMap;
    }

    public static boolean colorExist(Color color, Map<Color, Integer> colorMap){
        return colorMap.containsKey(color);
    }

    public static Color getPredominantColor() throws AWTException, IOException, InterruptedException {
        Map<Color, Integer> colorMap = getColorMap(getScreenshot());
        Set<Map.Entry<Color, Integer>> entrySet = colorMap.entrySet();
        int max = 0;
        for(Map.Entry<Color, Integer> pair: entrySet){
            if(pair.getValue() > max){
                max = pair.getValue();
            }
        }
        for(Map.Entry<Color, Integer> pair: entrySet){
            if(pair.getValue() == max){
                System.out.println(pair.getKey());
                return pair.getKey();
            }
        }
        return null;
    }
}
