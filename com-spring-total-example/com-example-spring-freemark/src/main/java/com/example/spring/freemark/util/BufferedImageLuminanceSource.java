package com.example.spring.freemark.util;

import com.google.zxing.LuminanceSource;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

/**
 * 解析条形码或二维码{@link BufferedImage} 图片
 * @author zxing
 * @see <a href="http://blog.csdn.net/yangxin_blog/article/details/50850701">生成二维码和解析</a>
 */
public final class BufferedImageLuminanceSource extends LuminanceSource {

  private final BufferedImage image;
  private final int left;
  private final int top;

  /**
   * 构造函数
   * @param image {@link BufferedImage}
   */
  public BufferedImageLuminanceSource(BufferedImage image) {
    this(image, 0, 0, image.getWidth(), image.getHeight());
  }

  /**
   * 构造函数
   * @param image {@link BufferedImage}
   * @param left 左边间隔
   * @param top 顶部间隔
   * @param width 宽度
   * @param height 高度
   */
  public BufferedImageLuminanceSource(BufferedImage image, int left, int top, int width, int height) {
    super(width, height);
    int sourceWidth = image.getWidth();
    int sourceHeight = image.getHeight();
    if (left + width > sourceWidth || top + height > sourceHeight) {
      throw new IllegalArgumentException("Crop rectangle does not fit within image data.");
    }
    for (int y = top; y < top + height; y++) {
      for (int x = left; x < left + width; x++) {
        if ((image.getRGB(x, y) & BrQrCodeUtil.BLACK) == 0) {
          // 白色
          image.setRGB(x, y, BrQrCodeUtil.WHITE);
        }
      }
    }

    this.image = new BufferedImage(sourceWidth, sourceHeight, BufferedImage.TYPE_BYTE_GRAY);
    this.image.getGraphics().drawImage(image, 0, 0, null);
    this.left = left;
    this.top = top;
  }

  @Override
  public byte[] getRow(int y, byte[] row) {
    if (y < 0 || y >= getHeight()) {
      throw new IllegalArgumentException("Requested row is outside the image: " + y);
    }
    int width = getWidth();
    if (row == null || row.length < width) {
      row = new byte[width];
    }
    image.getRaster().getDataElements(left, top + y, width, 1, row);
    return row;
  }

  @Override
  public byte[] getMatrix() {
    int width = getWidth();
    int height = getHeight();
    int area = width * height;
    byte[] matrix = new byte[area];
    image.getRaster().getDataElements(left, top, width, height, matrix);
    return matrix;
  }

  @Override
  public boolean isCropSupported() {
    return true;
  }

  @Override
  public LuminanceSource crop(int left, int top, int width, int height) {
    return new BufferedImageLuminanceSource(image, this.left + left, this.top + top, width, height);
  }

  @Override
  public boolean isRotateSupported() {
    return true;
  }

  @Override
  public LuminanceSource rotateCounterClockwise() {
    // 旋转90度
    int sourceWidth = image.getWidth();
    int sourceHeight = image.getHeight();
    AffineTransform transform = new AffineTransform(0.0, -1.0, 1.0, 0.0, 0.0, sourceWidth);
    BufferedImage rotatedImage = new BufferedImage(sourceHeight, sourceWidth, BufferedImage.TYPE_BYTE_GRAY);
    Graphics2D g = rotatedImage.createGraphics();
    g.drawImage(image, transform, null);
    g.dispose();
    int width = getWidth();
    return new BufferedImageLuminanceSource(rotatedImage, top, sourceWidth - (left + width), getHeight(), width);
  }
}