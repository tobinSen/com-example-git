package com.example.spring.freemark.util;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.ImageOutputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 图片工具类
 * <p>缩放图像</p>
 * @author BBF
 * @see <a href="http://blog.csdn.net/zhangzhikaixinya/article/details/8459400">Java的图片处理工具类</a>
 */
public class ImageUtil {
  public static final String IMAGE_TYPE_JPG = "jpg";

  /**
   * 缩放图像（按高度和宽度缩放）
   * <p>缩放后为jpg格式</p>
   * @param srcImage 源图像
   * @param width 缩放后的宽度
   * @param height 缩放后的高度
   * @param fixedColor 比例不对时补充的颜色，不补充为<code>null</code>
   * @return {@link Image}
   */
  public static Image scale(Image srcImage, int width, int height, Color fixedColor) {
    int srcHeight = srcImage.getHeight(null);
    int srcWidth = srcImage.getWidth(null);
    double heightRatio = MathUtil.div(height, srcHeight).doubleValue();
    double widthRatio = MathUtil.div(width, srcWidth).doubleValue();
    if (heightRatio == widthRatio) {
      // 长宽都按照相同比例缩放时，返回缩放后的图片
      return scale(srcImage, width, height);
    }
    Image imgTmp;
    // 宽缩放比例小就按照宽缩放，否则按照高缩放
    if (widthRatio < heightRatio) {
      imgTmp = scale(srcImage, width, (int) (srcHeight * widthRatio));
    } else {
      imgTmp = scale(srcImage, (int) (srcWidth * heightRatio), height);
    }
    if (null == fixedColor) {
      // 补白
      fixedColor = Color.WHITE;
    }
    // 绘制新图
    final BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    Graphics2D g = image.createGraphics();
    // 设置背景
    g.setBackground(fixedColor);
    g.clearRect(0, 0, width, height);
    // 计算高度
    final int imgTmpHeight = imgTmp.getHeight(null);
    final int imgTmpWidth = imgTmp.getWidth(null);
    // 在中间贴图
    g.drawImage(imgTmp, (width - imgTmpWidth) / 2, (height - imgTmpHeight) / 2,
        imgTmpWidth, imgTmpHeight, fixedColor, null);
    g.dispose();
    return image;
  }

  /**
   * 缩放图像（按长宽缩放）
   * <p>注意：目标长宽与原图不成比例会变形</p>
   * @param srcImg 源图像来源流
   * @param width 目标宽度
   * @param height 目标高度
   * @return {@link Image}
   */
  public static Image scale(Image srcImg, int width, int height) {
    int srcHeight = srcImg.getHeight(null);
    int srcWidth = srcImg.getWidth(null);
    int scaleType;
    if (srcHeight == height && srcWidth == width) {
      // 源与目标长宽一致返回原图
      return srcImg;
    } else if (srcHeight < height || srcWidth < width) {
      // 放大图片使用平滑模式
      scaleType = Image.SCALE_SMOOTH;
    } else {
      // 缩小图片使用默认模式
      scaleType = Image.SCALE_DEFAULT;
    }
    return srcImg.getScaledInstance(width, height, scaleType);
  }

  /**
   * 缩放图像（按高度和宽度缩放）
   * <p>缩放后为jpg格式</p>
   * @param srcImageFile 源图像文件地址
   * @param destImageFile 缩放后的图像地址
   * @param width 缩放后的宽度
   * @param height 缩放后的高度
   * @param fixedColor 比例不对时填充的颜色，<code>null</code>则不填充
   * @throws IOException IO异常
   */
  public static void scale(File srcImageFile,
                           File destImageFile,
                           int width,
                           int height,
                           Color fixedColor) throws IOException {
    write(scale(read(srcImageFile), width, height, fixedColor), destImageFile);
  }

  /**
   * 写出图像为目标文件扩展名对应的格式
   * @param image {@link Image}
   * @param targetFile 目标文件
   * @throws IOException IO异常
   */
  public static void write(Image image, File targetFile) throws IOException {
    String fileName = targetFile.getName();
    int dotIdx = fileName.lastIndexOf('.');
    String extName = dotIdx == -1 ? IMAGE_TYPE_JPG : fileName.substring(dotIdx + 1);
    ImageIO.write(toBufferedImage(image), extName, targetFile);
  }

  /**
   * 写出图像为指定扩展名对应的格式
   * @param image {@link Image}
   * @param imageType 图片类型（图片扩展名，无“.”），如果写txt，是无法生成图片的。
   * @param out 写出到的目标流
   * @throws IOException IO异常
   */
  public static void write(Image image,
                           String imageType,
                           OutputStream out) throws IOException {
    if (out instanceof ImageOutputStream) {
      ImageIO.write(toBufferedImage(image), imageType, out);
    } else {
      ImageIO.write(toBufferedImage(image), imageType, out);
    }
  }

  /**
   * 从文件中读取图片
   * @param imageFile 图片文件
   * @return 图片
   * @throws IOException IO异常
   */
  public static BufferedImage read(File imageFile) throws IOException {
    return ImageIO.read(imageFile);
  }

  /**
   * 从图片流中读取图片
   * @param in 图片文件流
   * @return 图片
   * @throws IOException IO异常
   */
  public static BufferedImage read(InputStream in) throws IOException {
    if (in instanceof ImageInputStream) {
      return ImageIO.read(in);
    }
    return ImageIO.read(getImageInputStream(in));
  }

  /**
   * {@link Image} 转 {@link BufferedImage}
   * @param img {@link Image}
   * @return {@link BufferedImage}
   */
  public static BufferedImage toBufferedImage(Image img) {
    if (img instanceof BufferedImage) {
      return (BufferedImage) img;
    }
    return copyImage(img, BufferedImage.TYPE_INT_RGB);
  }

  /**
   * 将已有{@link Image}复制新的一份出来
   * @param img {@link Image}
   * @param imageType {@link BufferedImage}中的常量，图像类型，例如黑白等
   * @return {@link BufferedImage}
   * @see BufferedImage#TYPE_INT_RGB
   * @see BufferedImage#TYPE_INT_ARGB
   * @see BufferedImage#TYPE_INT_ARGB_PRE
   * @see BufferedImage#TYPE_INT_BGR
   * @see BufferedImage#TYPE_3BYTE_BGR
   * @see BufferedImage#TYPE_4BYTE_ABGR
   * @see BufferedImage#TYPE_4BYTE_ABGR_PRE
   * @see BufferedImage#TYPE_BYTE_GRAY
   * @see BufferedImage#TYPE_USHORT_GRAY
   * @see BufferedImage#TYPE_BYTE_BINARY
   * @see BufferedImage#TYPE_BYTE_INDEXED
   * @see BufferedImage#TYPE_USHORT_565_RGB
   * @see BufferedImage#TYPE_USHORT_555_RGB
   */
  public static BufferedImage copyImage(Image img, int imageType) {
    final BufferedImage buffer = new BufferedImage(img.getWidth(null),
        img.getHeight(null), imageType);
    final Graphics2D g = buffer.createGraphics();
    g.drawImage(img, 0, 0, null);
    g.dispose();
    return buffer;
  }

  /**
   * 获取{@link ImageOutputStream}
   * @param out {@link OutputStream}
   * @return {@link ImageOutputStream}
   * @throws IOException IO异常
   */
  private static ImageOutputStream getImageOutputStream(OutputStream out) throws IOException {
    return ImageIO.createImageOutputStream(out);
  }

  /**
   * 获取{@link ImageInputStream}
   * @param in {@link InputStream}
   * @return {@link ImageInputStream}
   * @throws IOException IO异常
   */
  private static ImageInputStream getImageInputStream(InputStream in) throws IOException {
    return ImageIO.createImageInputStream(in);
  }

  /**
   * 控制字符两端对齐
   * <p>如果字符太多而宽度太小，会导致字符叠加</p>
   * @param g {@link Graphics}
   * @param code 字符串
   * @param x 起始x坐标
   * @param y 起始y坐标
   * @param maxWidth 最大宽度
   */
  public static void drawString(Graphics g, String code, int x, int y, int maxWidth) {
    String[] split = code.split("");
    int len = split.length;
    int step = new Long(Math.round(maxWidth / len)).intValue();
    int tmpX = x;
    for (int i = 0; i < len; i++) {
      g.drawString(split[i], tmpX, y);
      tmpX = tmpX + step;
    }
  }
}
