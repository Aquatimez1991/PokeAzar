package util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.net.URL;

public class ImageToAscii {

    private static final String ASCII_CHARS = "@%#*+=-:. ";

    public static void printImageAsAscii(String imageUrl, int width, int height) {
        try {
            InputStream input = new URL(imageUrl).openStream();
            BufferedImage image = ImageIO.read(input);

            BufferedImage gray = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
            Graphics g = gray.getGraphics();
            g.drawImage(image, 0, 0, null);
            g.dispose();

            Image scaledImage = gray.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            BufferedImage scaled = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
            Graphics2D g2 = scaled.createGraphics();
            g2.drawImage(scaledImage, 0, 0, null);
            g2.dispose();

            for (int y = 0; y < scaled.getHeight(); y++) {
                for (int x = 0; x < scaled.getWidth(); x++) {
                    int pixel = scaled.getRGB(x, y) & 0xFF;
                    char asciiChar = ASCII_CHARS.charAt(pixel * (ASCII_CHARS.length() - 1) / 255);
                    System.out.print(asciiChar);
                }
                System.out.println();
            }

        } catch (Exception e) {
            System.out.println("⚠️ Error al mostrar imagen en ASCII: " + e.getMessage());
        }
    }
}
