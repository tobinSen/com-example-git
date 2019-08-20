package com.example.spring.freemark.util;

import com.example.spring.freemark.painter.TextPainterFactory;
import com.google.zxing.*;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * 基于com.google.zxing的二维码工具类
 * @author BBF
 */
public class BrQrCodeUtil {

  public static final int BLACK = 0xFF000000;
  public static final int WHITE = 0xFFFFFFFF;
  /**
   * 条形码默认宽度和高度
   */
  private static final int BR_CODE_WIDTH = 120;
  private static final int BR_CODE_HEIGHT = 50;

  private static final String UTF8 = "UTF-8";
  /**
   * 提供给编码器的附加参数
   */
  private static final Map<DecodeHintType, Object> HINTS_DECODE;

  static {
    HINTS_DECODE = new HashMap<>(1);
    HINTS_DECODE.put(DecodeHintType.CHARACTER_SET, UTF8);
  }

  /**
   * 将文本内容编码为条形码或二维码
   * @param content 文本内容
   * @param format 格式枚举
   * @param width 宽度
   * @param height 高度
   * @return {@link BitMatrix}
   * @throws WriterException 编码失败异常
   */
  private static BitMatrix encode(String content,
                                  BarcodeFormat format,
                                  int width,
                                  int height) throws WriterException {
    final MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
    // 提供给编码器的附加参数
    final Map<EncodeHintType, Object> hints = new HashMap<>(2);
    hints.put(EncodeHintType.CHARACTER_SET, UTF8);
    if (format == BarcodeFormat.QR_CODE) {
      hints.put(EncodeHintType.MARGIN, 1);
    } else {
      // 左右边距。实际宽度是width + margin
      hints.put(EncodeHintType.MARGIN, 2 * TextPainterFactory.MARGIN);
    }
    return multiFormatWriter.encode(content, format, width, height, hints);
  }

  /**
   * 生成二维码到文件，二维码图片格式取决于文件的扩展名
   * @param content 文本内容
   * @param targetFile 目标文件，扩展名决定输出格式
   * @param width 宽度
   * @param height 高度
   * @return 目标文件
   * @throws IOException IO异常
   * @throws WriterException 编码失败异常
   */
  public static File qrEncode(String content,
                              File targetFile,
                              int width,
                              int height) throws IOException, WriterException {
    final BufferedImage image = qrEncode(content, width, height);
    ImageUtil.write(image, targetFile);
    return targetFile;
  }

  /**
   * 生成二维码到输出流
   * @param content 文本内容
   * @param imageType 图片类型（图片扩展名），见{@link ImageUtil}
   * @param out 目标流
   * @param width 宽度
   * @param height 高度
   * @throws IOException IO异常
   * @throws WriterException 编码失败异常
   */
  public static void qrEncode(String content,
                              String imageType,
                              OutputStream out,
                              int width,
                              int height) throws IOException, WriterException {
    final BufferedImage image = qrEncode(content, width, height);
    ImageUtil.write(image, imageType, out);
  }

  /**
   * 生成二维码图片
   * @param content 文本内容
   * @param width 宽度
   * @param height 高度
   * @return 二维码图片（黑白）
   */
  public static BufferedImage qrEncode(String content, int width, int height) throws WriterException {
    final BitMatrix bitMatrix = encode(content, BarcodeFormat.QR_CODE, width, height);
    return toImage(bitMatrix);
  }

  /**
   * 解码条形码或二维码图片为文本
   * @param qrCodeInputStream 条形码或二维码输入流
   * @return 解码文本
   * @throws IOException IO异常
   * @throws NotFoundException 解码失败异常
   */
  public static String decode(InputStream qrCodeInputStream) throws IOException, NotFoundException {
    return decode(ImageUtil.read(qrCodeInputStream));
  }

  /**
   * 解码条形码或二维码图片为文本
   * @param qrCodeFile 条形码或二维码图片
   * @return 解码文本
   * @throws IOException IO异常
   * @throws NotFoundException 解码失败异常
   */
  public static String decode(File qrCodeFile) throws IOException, NotFoundException {
    return decode(ImageUtil.read(qrCodeFile));
  }

  /**
   * 将条形码或二维码图片解码为文本
   * @param image {@link Image} 条形码或二维码图片
   * @return 解码后的文本
   * @throws NotFoundException 解码失败异常
   */
  public static String decode(Image image) throws NotFoundException {
    final LuminanceSource source = new BufferedImageLuminanceSource(ImageUtil.toBufferedImage(image));
    final BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(source));
    return new MultiFormatReader().decode(binaryBitmap, HINTS_DECODE).getText();
  }

  /**
   * 生成条形码到文件，条形码图片格式取决于文件的扩展名
   * @param content 文本内容
   * @param format {@link BarcodeFormat}
   * @param targetFile 目标文件，扩展名决定输出格式
   * @throws IOException IO异常
   * @throws WriterException 编码失败异常
   */
  public static void brEncode(String content,
                              BarcodeFormat format,
                              File targetFile) throws IOException, WriterException {
    final Image image = brEncode(content, format);
    ImageUtil.write(image, targetFile);
  }

  /**
   * 生成条形码到文件，条形码图片格式取决于文件的扩展名
   * @param content 文本内容
   * @param format {@link BarcodeFormat}
   * @param targetFile 目标文件，扩展名决定输出格式
   * @param width 目标文件宽度
   * @param height 目标文件高度
   * @throws IOException IO异常
   * @throws WriterException 编码失败异常
   */
  public static void brEncode(String content,
                              BarcodeFormat format,
                              File targetFile,
                              int width,
                              int height) throws IOException, WriterException {
    final Image image = brEncode(content, format, width, height);
    ImageUtil.write(image, targetFile);
  }

  /**
   * 生成条形码图片
   * <p>最小宽度是115px</p>
   * @param content 文本内容
   * @param format {@link BarcodeFormat}
   * @param width 目标文件宽度
   * @param height 目标文件高度
   * @return 条形码图片（黑白）
   * @throws WriterException 编码失败异常
   */
  public static Image brEncode(String content,
                               BarcodeFormat format,
                               int width,
                               int height) throws WriterException {
    Image image = brEncode(content, format);
    return ImageUtil.scale(image, width, height, Color.WHITE);
  }

  /**
   * 生成条形码图片
   * <p>最小宽度是115px</p>
   * @param content 文本内容
   * @param format {@link BarcodeFormat}
   * @return 条形码图片（黑白）
   * @throws WriterException 编码失败异常
   */
  public static Image brEncode(String content, BarcodeFormat format) throws WriterException {
    final BitMatrix bitMatrix = encode(content, format, BR_CODE_WIDTH, BR_CODE_HEIGHT);
    BufferedImage image = toImage(bitMatrix);
    TextPainterFactory.getPainter(format).paintText(image, content);
    return image;
  }

  /**
   * {@link BitMatrix} 转 {@link BufferedImage}
   * @param matrix {@link BitMatrix}
   * @return {@link BufferedImage}
   */
  public static BufferedImage toImage(BitMatrix matrix) {
    int width = matrix.getWidth();
    int height = matrix.getHeight();
    BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_BINARY);
    for (int x = 0; x < width; x++) {
      for (int y = 0; y < height; y++) {
        image.setRGB(x, y, matrix.get(x, y) ? BLACK : WHITE);
      }
    }
    return image;
  }


  /**
   * EAN（国际标准条码）计算校验码
   * <p>如果是UPC算法，需要在前面补一个0</p>
   * @param s 去掉最后一位的数字字符串
   * @return 一位校验码
   * @throws FormatException 非数字异常
   * @see com.google.zxing.oned.UPCEANReader
   */
  public static int getUpcEanChecksum(CharSequence s) throws FormatException {
    int length = s.length();
    int sum = 0;
    for (int i = length - 1; i >= 0; i -= 2) {
      int digit = s.charAt(i) - '0';
      if (digit < 0 || digit > 9) {
        throw FormatException.getFormatInstance();
      }
      sum += digit;
    }
    sum *= 3;
    for (int i = length - 2; i >= 0; i -= 2) {
      int digit = s.charAt(i) - '0';
      if (digit < 0 || digit > 9) {
        throw FormatException.getFormatInstance();
      }
      sum += digit;
    }
    return (1000 - sum) % 10;
  }
}
