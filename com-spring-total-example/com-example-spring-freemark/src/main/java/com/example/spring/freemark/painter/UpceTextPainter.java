package com.example.spring.freemark.painter;

import com.example.spring.freemark.util.BrQrCodeUtil;
import com.example.spring.freemark.util.ImageUtil;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * 条码文本绘制器，UPC-E
 * <p>UPC码是由美国统一代码委员会制定的一种条码，UPC-E为通用商品缩短编码，由七位数字和最后一位校验码组成</p>
 * @author BBF
 */
public class UpceTextPainter implements TextPainter {
  /**
   * 获取单例，枚举方式
   */
  private enum Singleton {
    /** 枚举单例 */
    INSTANCE;
    private UpceTextPainter textPainter;

    Singleton() {
      textPainter = new UpceTextPainter();
    }

    private UpceTextPainter getInstance() {
      return textPainter;
    }
  }

  /**
   * 条码中间数字部分宽度
   */
  private static final int PART_WIDTH = 42;
  /**
   * EAN-13，无验证码的长度是12
   */
  private static final int NO_CHECKSUM_LENGTH = 7;

  /**
   * 获取单实例
   * @return Ean13TextPainter
   */
  public static UpceTextPainter getInstance() {
    return UpceTextPainter.Singleton.INSTANCE.getInstance();
  }

  private UpceTextPainter() {
  }

  @Override
  public void paintText(BufferedImage image, String code) {
    //获取条码的图像宽高
    int width = image.getWidth();
    int height = image.getHeight();
    // 以中轴线开始左右两部分数字的起始位置
    int leftX = width / 2 - PART_WIDTH / 2;
    Graphics2D g = image.createGraphics();
    g.drawImage(image, 0, 0, width, height, null);
    // 设置数字的遮盖效果
    g.setColor(Color.WHITE);
    g.fillRect(leftX, height - TextPainterFactory.FONT_SIZE, PART_WIDTH, TextPainterFactory.FONT_SIZE);
    g.fillRect(0, height - TextPainterFactory.FONT_SIZE / 2, width, TextPainterFactory.FONT_SIZE);
    // UPC-A，中间6个数字，最前和最后各1个数字
    g.setFont(TextPainterFactory.getFont());
    g.setColor(Color.BLACK);
    String p1 = code.substring(0, 1);
    String p2 = code.substring(1, 7);
    String p3 = "";
    if (code.length() == NO_CHECKSUM_LENGTH) {
      try {
        p3 = String.valueOf(BrQrCodeUtil.getUpcEanChecksum(code));
      } catch (Exception ex) {
        // 这里不会抛出异常，如果校验失败，是无法执行这个方法的。
      }
    } else {
      p3 = code.substring(7);
    }
    int fontY = height - TextPainterFactory.FONT_SIZE / 4;
    // 两个字符间距
    g.drawString(p1, leftX - 2 * TextPainterFactory.getCharWidth(), fontY);
    // 两端对齐
    ImageUtil.drawString(g, p2, leftX + 1, fontY, PART_WIDTH);
    g.drawString(p3, leftX + PART_WIDTH + TextPainterFactory.getCharWidth(), fontY);
  }
}