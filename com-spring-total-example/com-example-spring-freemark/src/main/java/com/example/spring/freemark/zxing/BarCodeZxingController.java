package com.example.spring.freemark.zxing;

import com.example.spring.freemark.painter.TextPainterFactory;
import com.example.spring.freemark.util.BrQrCodeUtil;
import com.example.spring.freemark.util.ImageUtil;
import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

@RestController
public class BarCodeZxingController {

    @RequestMapping("barCodeEncode.do")
    public String barCodeEncode(String code, HttpServletResponse response) throws Exception {
        try {
            String contents = "6901236341292";
            int width = 200, height = 150;
            //encode(code, width, height, response);
            encode(code, 200, 50, 10, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void encode(String contents, int width, int height, int offset, HttpServletResponse response) throws WriterException, FileNotFoundException, IOException {
        contents = new String(contents.getBytes("UTF-8"), "ISO-8859-1");
        //这里是生成CODE-39条形码，如果生成EAN-13条形码，请将CODE_36改成EAN_13，并在生成时先校验内容。
        BitMatrix matrix = new MultiFormatWriter().encode(contents, BarcodeFormat.CODE_39, width - offset, height);
        BufferedImage image = getBufferImage(matrix, new MatrixToImageConfig());
        paintText(image, contents);
        //MatrixToImageWriter.writeToStream(matrix, "jpeg", response.getOutputStream());
        ImageIO.write(image, "jpeg", response.getOutputStream());
    }

    private static BufferedImage getBufferImage(BitMatrix matrix, MatrixToImageConfig config) {
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        BufferedImage image = new BufferedImage(width, height, 1);
        int onColor = config.getPixelOnColor();
        int offColor = config.getPixelOffColor();
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, matrix.get(x, y) ? onColor : offColor);
            }
        }
        return image;
    }


    /**
     * 条形码编码
     */
    public void encode(String contents, int width, int height, HttpServletResponse response) {
        int codeWidth = 3 + // start guard
                (7 * 6) + // left bars
                5 + // middle guard
                (7 * 6) + // right bars
                3; // end guard
        codeWidth = Math.max(codeWidth, width);
        try {
            BitMatrix bitMatrix = new MultiFormatWriter().encode(contents,
                    BarcodeFormat.CODE_93, codeWidth, height, null);

            MatrixToImageWriter.writeToStream(bitMatrix, "png", response.getOutputStream());

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 解析条形码
     */
    public String decode(String imgPath) {
        BufferedImage image = null;
        Result result = null;
        try {
            image = ImageIO.read(new File(imgPath));
            if (image == null) {
                System.out.println("the decode image may be not exit.");
            }
            LuminanceSource source = new BufferedImageLuminanceSource(image);
            BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

            result = new MultiFormatReader().decode(bitmap, null);
            return result.getText();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    private static final int PART_WIDTH = 42;
    private static final int NO_CHECKSUM_LENGTH = 12;

    public static void paintText(BufferedImage image, String code) {
        //获取条码的图像宽高
        int width = image.getWidth();
        int height = image.getHeight();
        // 以中轴线开始左右两部分数字的起始位置
        int rightX = width / 2 + 2;
        int leftX = rightX - PART_WIDTH - 4;
        Graphics2D g = image.createGraphics();
        g.drawImage(image, 0, 0, width, height, null);
        // 设置数字的遮盖效果
        g.setColor(Color.WHITE);
        g.fillRect(leftX, height - TextPainterFactory.FONT_SIZE, PART_WIDTH, TextPainterFactory.FONT_SIZE);
        g.fillRect(rightX, height - TextPainterFactory.FONT_SIZE, PART_WIDTH, TextPainterFactory.FONT_SIZE);
        g.fillRect(0, height - TextPainterFactory.FONT_SIZE / 2, width, TextPainterFactory.FONT_SIZE);
        // EAN13，中轴线两侧各6个数字，最前1个数字
        g.setFont(TextPainterFactory.getFont());
        g.setColor(Color.BLACK);
        String p1 = code.substring(0, 1);
        String p2 = code.substring(1, 7);
        String p3 = code.substring(7);
        if (code.length() == NO_CHECKSUM_LENGTH) {
            try {
                p3 = p3.concat(String.valueOf(BrQrCodeUtil.getUpcEanChecksum(code)));
            } catch (Exception ex) {
                // 这里不会抛出异常，如果校验失败，是无法执行这个方法的。
            }
        }
        int fontY = height - TextPainterFactory.FONT_SIZE / 4;
        // 两个字符间距
        g.drawString(p1, leftX - 2 * TextPainterFactory.getCharWidth(), fontY);
        // 两端对齐
        ImageUtil.drawString(g, p2, leftX + 1, fontY, PART_WIDTH);
        ImageUtil.drawString(g, p3, rightX + 1, fontY, PART_WIDTH);
    }

}
