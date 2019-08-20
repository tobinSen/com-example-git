package com.example.spring.freemark.painter;

import com.google.zxing.BarcodeFormat;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * 条码文本绘制器工厂类
 *
 * @author BBF
 */
public class TextPainterFactory {

    /**
     * 数字部分左右留白宽度
     */
    public static final int MARGIN = 10;
    public static final int FONT_SIZE = 11;
    private static final Font FONT = new Font("monospace", Font.PLAIN, FONT_SIZE);
    private static final int CHAR_WIDTH;

    static {
        final BufferedImage image = new BufferedImage(50, 50, BufferedImage.TYPE_BYTE_BINARY);
        Graphics2D g = image.createGraphics();
        g.setFont(TextPainterFactory.getFont());
        CHAR_WIDTH = g.getFontMetrics().charWidth('0');
    }

    /**
     * 获取字体
     *
     * @return 字体
     */
    public static Font getFont() {
        return FONT;
    }

    /**
     * 获取单个字符宽度
     *
     * @return 单个字符宽度
     */
    public static int getCharWidth() {
        return CHAR_WIDTH;
    }

    /**
     * 获取条码文本绘制实例
     *
     * @param format {@link BarcodeFormat}
     * @return {@link TextPainter}
     */
    public static TextPainter getPainter(BarcodeFormat format) {
        switch (format) {
            case EAN_8:
                return Ean8TextPainter.getInstance();
            case EAN_13:
                return Ean13TextPainter.getInstance();
            case UPC_A:
                return UpcaTextPainter.getInstance();
            case UPC_E:
                return UpceTextPainter.getInstance();
            case CODE_39:
                return CodeTextPainter.getInstance();
            case CODE_128:
                return CodeTextPainter.getInstance();
            default:
                return DefaultTextPainter.getInstance();
        }
    }
}