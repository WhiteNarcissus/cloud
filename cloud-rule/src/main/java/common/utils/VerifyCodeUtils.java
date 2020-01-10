package common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.security.SecureRandom;
import java.util.Arrays;

/**
 * 
 * @author liufei
 * @CreatedDate 2017-11-28
 * @Description 验证码工具类
 */
public class VerifyCodeUtils {
	//定义验证码字符源
    private static final String VERIFY_CODES = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static SecureRandom random = new SecureRandom();
    private static Logger logger = LoggerFactory.getLogger(VerifyCodeUtils.class);

    /**
     * 生成验证码
     * @param verifySize 验证码长度
     * @return
     */

    public static String generateVerifyCode(int verifySize) {
        return generateVerifyCode(verifySize, VERIFY_CODES);
    }

    private static String generateVerifyCode(int verifySize, String sources) {
        if (sources == null || sources.length() == 0) {
            sources = VERIFY_CODES;
        }

        int codesLen = sources.length();
        SecureRandom random = new SecureRandom();
        StringBuilder verifyCode = new StringBuilder(verifySize);

        for(int i = 0; i < verifySize; ++i) {
            verifyCode.append(sources.charAt(random.nextInt(codesLen - 1)));
        }

        return verifyCode.toString();
    }
    /**
     * 生成随机验证码文件并返回验证码字符串值
     * @param width
     * @param height
     * @param outputFile
     * @param verifySize
     * @return
     * @throws IOException
     */
    public static String outputVerifyImage(int width, int height, File outputFile, int verifySize) throws IOException {
        String verifyCode = generateVerifyCode(verifySize);
        outputImage(width, height, outputFile, verifyCode);
        return verifyCode;
    }
    /**
     * 生成随机验证码图片流并返回验证码字符串值
     * @param width
     * @param height
     * @param os
     * @param verifySize
     * @return
     * @throws IOException
     */
    public static String outputVerifyImage(int width, int height, OutputStream os, int verifySize) throws IOException {
        String verifyCode = generateVerifyCode(verifySize);
        outputImage(width, height, os, verifyCode);
        return verifyCode;
    }
    /**
     * 生成指定验证码图片流
     * @param width
     * @param height
     * @param os
     * @param verifyCode
     * @throws IOException
     */
    public static void outputImage(int width, int height, OutputStream os, String verifyCode) throws IOException {
        int verifySize = verifyCode.length();
        BufferedImage image = new BufferedImage(width, height, 1);
        SecureRandom random = new SecureRandom();
        Graphics2D graphics2d = image.createGraphics();
        graphics2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        Color[] colors = new Color[5];
        Color[] colorSpaces = new Color[]{Color.WHITE, Color.CYAN, Color.GRAY, Color.LIGHT_GRAY, Color.MAGENTA, Color.ORANGE, Color.PINK, Color.YELLOW};
        float[] fractions = new float[colors.length];

        for(int i = 0; i < colors.length; ++i) {
            colors[i] = colorSpaces[random.nextInt(colorSpaces.length)];
            fractions[i] = random.nextFloat();
        }

        Arrays.sort(fractions);
        graphics2d.setColor(Color.LIGHT_GRAY);//设置边框色
        graphics2d.fillRect(0, 0, width, height);
        Color c = getRandColor(200, 250);
        graphics2d.setBackground(Color.LIGHT_GRAY);//设置背景色
        graphics2d.fillRect(0, 2, width, height - 4);
        graphics2d.setColor(getRandColor(160, 200));
        //绘制干扰线
        int area;
        int fontSize;
        int x;
        int y;
        for(int i = 0; i < 20; ++i) {
            area = random.nextInt(width - 1);
            fontSize = random.nextInt(height - 1);
            x = random.nextInt(6) + 1;
            y = random.nextInt(12) + 1;
            graphics2d.drawLine(area, fontSize, area + x + 40, fontSize + y + 20);
        }
        //添加噪点
        float yawpRate = 0.05F;
        area = (int)(yawpRate * (float)width * (float)height);

        int i;
        for(fontSize = 0; fontSize < area; ++fontSize) {
            x = random.nextInt(width);
            y = random.nextInt(height);
            i = getRandomIntColor();
            image.setRGB(x, y, i);
        }
        //使图片扭曲
        shear(graphics2d, width, height, c);
        graphics2d.setColor(getRandColor(100, 160));
        fontSize = height - 4;
        Font font = new Font("Algerian", 2, fontSize);
        graphics2d.setFont(font);
        char[] chars = verifyCode.toCharArray();

        for(i = 0; i < verifySize; ++i) {
            AffineTransform affineTransform = new AffineTransform();
            affineTransform.setToRotation(0.7853981633974483D * random.nextDouble() * (double)(random.nextBoolean() ? 1 : -1), (double)(width / verifySize * i + fontSize / 2), (double)(height / 2));
            graphics2d.setTransform(affineTransform);
            graphics2d.drawChars(chars, i, 1, (width - 10) / verifySize * i + 5, height / 2 + fontSize / 2 - 10);
        }

        graphics2d.dispose();
        ImageIO.write(image, "jpg", os);
    }
    /**
     * 使得验证码扭曲倾斜
     * @param graphics2d
     * @param width
     * @param height
     * @param c
     */
    private static void shear(Graphics2D graphics2d, int width, int height, Color c) {
        shearX(graphics2d, width, height, c);
        shearY(graphics2d, width, height, c);
    }

    private static void shearY(Graphics2D graphics2d, int width, int height, Color c) {
        int period = random.nextInt(2);
        boolean borderGap = true;
        int frames = 1;
        int phase = random.nextInt(2);

        for(int i = 0; i < height; ++i) {
            double d = (double)(period >> 1) * Math.sin((double)i / (double)period + 6.283185307179586D * (double)phase / (double)frames);
            graphics2d.copyArea(0, i, width, 1, (int)d, 0);
            if (borderGap) {
                graphics2d.setColor(c);
                graphics2d.drawLine((int)d, i, 0, i);
                graphics2d.drawLine((int)d + width, i, width, i);
            }
        }

    }

    private static void shearX(Graphics2D graphics2d, int width, int height, Color c) {
        int period = random.nextInt(40) + 10;
        boolean borderGap = true;
        int frames = 20;
        int phase = 7;

        for(int i = 0; i < height; ++i) {
            double d = (double)(period >> 1) * Math.sin((double)i / (double)period + 6.283185307179586D * (double)phase / (double)frames);
            graphics2d.copyArea(i, 0, 1, height, 0, (int)d);
            if (borderGap) {
                graphics2d.setColor(c);
                graphics2d.drawLine(i, (int)d, i, 0);
                graphics2d.drawLine(i, (int)d + height, i, height);
            }
        }

    }

    private static int getRandomIntColor() {
        int[] rgb = getRandomRGB();
        int color = 0;
        int[] var5 = rgb;
        int var4 = rgb.length;

        for(int var3 = 0; var3 < var4; ++var3) {
            int c = var5[var3];
            color <<= 8;
            color |= c;
        }

        return color;
    }

    private static int[] getRandomRGB() {
        int[] rgb = new int[3];

        for(int i = 0; i < 3; ++i) {
            rgb[i] = random.nextInt(255);
        }

        return rgb;
    }

    private static Color getRandColor(int fc, int bc) {
        if (fc > 255) {
            fc = 255;
        }

        if (bc > 255) {
            bc = 255;
        }

        int r = fc + random.nextInt(bc - fc);
        int g = fc + random.nextInt(bc - fc);
        int b = fc + random.nextInt(bc - fc);
        return new Color(r, g, b);
    }

    public static void outputImage(int width, int height, File outputFile, String verifyCode) throws IOException {
        if (outputFile != null) {
            File dir = outputFile.getParentFile();
            if (!dir.exists()) {
                dir.mkdirs();
            }
            FileOutputStream fos = null;
            try {
                outputFile.createNewFile();
                fos = new FileOutputStream(outputFile);
                outputImage(width, height, (OutputStream)fos, verifyCode);
                fos.close();
            } catch (IOException var6) {
                var6.printStackTrace();
                logger.error("error:{}", var6.toString());
            } finally{
            	if(fos!=null){
            		fos.close();
            	}
            }

        }
    }
 
}