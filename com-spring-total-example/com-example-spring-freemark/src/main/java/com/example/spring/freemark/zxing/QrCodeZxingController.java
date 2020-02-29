package com.example.spring.freemark.zxing;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeReader;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;


@RestController
public class QrCodeZxingController {

    // LOGO宽度
    private static final int WIDTH = 60;
    // LOGO高度
    private static final int HEIGHT = 60;
    // 二维码尺寸
    private static final int QRCODE_SIZE = 300;
    @Autowired
    private ResourceLoader resourceLoader;


    //将BitMatrix转换成BufferedImage.
    @RequestMapping("printQrCode.do")
    public String printQrCode(String code, HttpServletResponse response) throws Exception {
        try {
            //BufferedImage bufferedImage = MatrixToImageWriter.toBufferedImage(encode(code), new MatrixToImageConfig(Color.BLACK.getRGB(), Color.WHITE.getRGB()));
            //BufferedImage bufferedImage = encode("www.baidu.com", "statics/images/test.png", true);
            //ImageIO.write(bufferedImage, "jpeg", response.getOutputStream());
            linkEncode(response);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    @RequestMapping("printQrDecodeCode.do")
    public String qrCodeDecode(String code, HttpServletResponse response) throws Exception {
        try {
            //BufferedImage bufferedImage = MatrixToImageWriter.toBufferedImage(encode(code), new MatrixToImageConfig(Color.BLACK.getRGB(), Color.WHITE.getRGB()));
            //ImageIO.write(bufferedImage, "jpeg", response.getOutputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    //链接二维码
    private static void linkEncode(HttpServletResponse response) throws Exception {
        //二维码内容
        String text = "http://blog.csdn.net/rongbo_j";
        int width = 200;   //二维码图片宽度
        int height = 200;   //高度
        String format = "gif";  //图片格式

        Hashtable<EncodeHintType, String> hints = new Hashtable<>();
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        BitMatrix bitMatrix = null; //二维码矩阵
        try {
            //编码 writer的实现类
            bitMatrix = new MultiFormatWriter().encode(text, BarcodeFormat.QR_CODE, width, height, hints);
        } catch (WriterException e1) {
            e1.printStackTrace();
        }
        // File outputFile = new File("src/1.gif");
        try {
            //输出二维码图片（矩阵-->图片）
            MatrixToImageWriter.writeToStream(bitMatrix, format, response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //生成二维矩阵
    private BufferedImage encode(String contents, String imgPath, boolean needCompress) throws Exception {
        final Map<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        hints.put(EncodeHintType.MARGIN, 1);  // 二维码两边空白区域大小
        //通过二维码编码
        BitMatrix bitMatrix = new QRCodeWriter().encode(contents, BarcodeFormat.QR_CODE, 320, 320, hints);
        int width = bitMatrix.getWidth();
        int height = bitMatrix.getHeight();
        //产生一个java的图片
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, bitMatrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
            }
        }
        if (imgPath == null || "".equals(imgPath)) {
            return image;
        }
        insertImage(image, imgPath, needCompress);
        return image;
    }

    /**
     * 在生成的二维码中插入图片
     *
     * @param source
     * @param imgPath
     * @param needCompress
     * @throws Exception
     */
    private static void insertImage(BufferedImage source, String imgPath, boolean needCompress) throws Exception {
        File file = new File(imgPath);
        //Resource resource = new ClassPathResource(imgPath);
        //File file = new File(QrCodeZxingController.class.getResource(imgPath).getFile());
        //Thread.currentThread().getContextClassLoader().getResourceAsStream(imgPath);
        //resourceLoader.getClassLoader().
        if (!file.exists()) {
            System.err.println("" + imgPath + "   该文件不存在！");
            return;
        }
        Image src = ImageIO.read(new File(imgPath));
        int width = src.getWidth(null);
        int height = src.getHeight(null);
        if (needCompress) { // 压缩LOGO
            if (width > WIDTH) {
                width = WIDTH;
            }
            if (height > HEIGHT) {
                height = HEIGHT;
            }
            Image image = src.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics g = tag.getGraphics();
            g.drawImage(image, 0, 0, null); // 绘制缩小后的图
            g.dispose();
            src = image;
        }
        // 插入LOGO
        Graphics2D graph = source.createGraphics();
        int x = (QRCODE_SIZE - width) / 2;
        int y = (QRCODE_SIZE - height) / 2;
        graph.drawImage(src, x, y, width, height, null);
        Shape shape = new RoundRectangle2D.Float(x, y, width, width, 6, 6);
        graph.setStroke(new BasicStroke(3f));
        graph.draw(shape);
        graph.dispose();
    }


    /**
     * 解析图片文件上的二维码
     *
     * @param imageFile 图片文件
     * @return 解析的结果，null表示解析失败
     */
    private String decode(File imageFile) {
        try {
            BufferedImage image = ImageIO.read(imageFile);
            LuminanceSource luminanceSource = new BufferedImageLuminanceSource(image);
            Binarizer binarizer = new HybridBinarizer(luminanceSource);

            BinaryBitmap binaryBitmap = new BinaryBitmap(binarizer);

            Map<DecodeHintType, Object> hints = new HashMap<>();
            hints.put(DecodeHintType.CHARACTER_SET, "utf-8");
            hints.put(DecodeHintType.TRY_HARDER, Boolean.TRUE);
            hints.put(DecodeHintType.POSSIBLE_FORMATS, BarcodeFormat.QR_CODE);

            Result result = new QRCodeReader().decode(binaryBitmap, hints);

            return result.getText();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
