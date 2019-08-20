package com.example.spring.freemark.painter;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * 条码文本绘制器，通用型
 * @author BBF
 */
public class DefaultTextPainter implements TextPainter {
  /**
   * 获取单例，枚举方式
   */
  private enum Singleton {
    /** 枚举单例 */
    INSTANCE;
    private DefaultTextPainter textPainter;

    Singleton() {
      textPainter = new DefaultTextPainter();
    }

    private DefaultTextPainter getInstance() {
      return textPainter;
    }
  }

  /**
   * 获取单实例
   * @return Ean13TextPainter
   */
  public static DefaultTextPainter getInstance() {
    return DefaultTextPainter.Singleton.INSTANCE.getInstance();
  }

  private DefaultTextPainter() {
  }

  @Override
  public void paintText(BufferedImage image, String code) {
    //获取条码的图像宽高
    int width = image.getWidth();
    int height = image.getHeight();
    Graphics2D g = image.createGraphics();
    g.drawImage(image, 0, 0, width, height, null);
    // 设置数字的遮盖效果
    g.setColor(Color.WHITE);
    g.fillRect(0, height - TextPainterFactory.FONT_SIZE, width, TextPainterFactory.FONT_SIZE);
    // EAN13，中轴线两侧各6个数字，最前1个数字
    g.setFont(TextPainterFactory.getFont());
    g.setColor(Color.BLACK);
    int fontX = width / 2 - TextPainterFactory.getCharWidth() / 2;
    int fontY = height - TextPainterFactory.FONT_SIZE / 4;
    // 两个字符间距
    g.drawString(code, fontX, fontY);
  }
}