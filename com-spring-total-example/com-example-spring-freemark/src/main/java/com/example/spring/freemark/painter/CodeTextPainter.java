package com.example.spring.freemark.painter;

import com.example.spring.freemark.util.ImageUtil;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * 条码文本绘制器，CODE-39和CODE-128
 * <p>ISO/IEC 16388:2007 标准对Code39 进行定义，编码规则
 * 1、每五条线表示一个字符；<br>
 * 2、粗线表示1，细线表示0；<br>
 * 3、线条间的间隙宽的表示1，窄的表示0；<br>
 * 4、五条线加上它们之间的四条间隙就是九位二进制编码，而且这九位中必定有三位是1，所以称为39码；<br>
 * 5、条形码的首尾各一个 * 标识开始和结束。</p>
 * <p>CODE-128码可表示从 ASCII 0 到ASCII 127 共128个字符，故称128码</p>
 * @author BBF
 */
public class CodeTextPainter implements TextPainter {
  /**
   * 获取单例，枚举方式
   */
  private enum Singleton {
    /** 枚举单例 */
    INSTANCE;
    private CodeTextPainter textPainter;

    Singleton() {
      textPainter = new CodeTextPainter();
    }

    private CodeTextPainter getInstance() {
      return textPainter;
    }
  }

  /**
   * 获取单实例
   * @return Ean13TextPainter
   */
  public static CodeTextPainter getInstance() {
    return CodeTextPainter.Singleton.INSTANCE.getInstance();
  }

  private CodeTextPainter() {
  }

  @Override
  public void paintText(BufferedImage image, String code) {
    // 获取条码的图像宽高
    int width = image.getWidth();
    int height = image.getHeight();
    Graphics2D g = image.createGraphics();
    g.drawImage(image, 0, 0, width, height, null);
    // 设置数字的遮盖效果
    g.setColor(Color.WHITE);
    g.fillRect(0, height - TextPainterFactory.FONT_SIZE, width, TextPainterFactory.FONT_SIZE);
    g.setFont(TextPainterFactory.getFont());
    g.setColor(Color.BLACK);
    int fontY = height - TextPainterFactory.FONT_SIZE / 4;
    // 两端对齐
    ImageUtil.drawString(g, code, 2 * TextPainterFactory.MARGIN, fontY,
        width - 2 * TextPainterFactory.MARGIN);
  }
}