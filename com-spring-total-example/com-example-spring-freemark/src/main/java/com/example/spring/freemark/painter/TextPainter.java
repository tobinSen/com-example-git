package com.example.spring.freemark.painter;

import java.awt.image.BufferedImage;

/**
 * 条码文本绘制器接口类
 * @author BBF
 */
public interface TextPainter {

  /**
   * 在条码图像上绘制条码文本
   * @param image {@link BufferedImage}
   * @param code 条码文本
   */
  void paintText(BufferedImage image, String code);
}