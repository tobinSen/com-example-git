package com.uplooking.print;

import javax.print.*;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.Copies;
import javax.print.attribute.standard.Sides;
import java.awt.print.PrinterJob;
import java.io.*;

public class JPGPrint {


    public static void main(String[] argv) throws Exception {
        File file = new File("E:\\a.jpg");
        String printerName = "HP MFP M436 PCL6";//打印机名包含字串
        JPGPrint(file, printerName);
    }

    /**
     *
     * javax操作打印机步骤：
     *   1、获取本机连接的所有打印机
     *      PrintService[] printServices = PrinterJob.lookupPrintServices();
     *   2、创建打印job
     *      DocPrintJob job = printService.createPrintJob(); // 创建打印作业
     *   3、进行打印
     *      job.print(doc, aset);
     */

    /**
     * 打印流程：
     * 1、printPreview -->wo:工单，printInfo:打印信息
     * DO->printVO->printTemplate
     * wo:{}
     * printInfo:
     * printConfig:
     * zplList:
     * 2、printInfo -->更新打印状态
     * 3、zplPrint--》进行打印
     * ZplPrinterFactory->ZplPrinter->zplPrinterAgent
     * PrintServiceLookup:
     * PrintService:
     * DocPrintJob
     * DocFlavor
     * Doc
     * String zpl = builder.getZpl(zplJson);【重要】画笔将每个单元格转换内存图，字符串
     * zplPrinter.print(zpl);
     */

    // 传入文件和打印机名称
    public static void JPGPrint(File file, String printerName) throws PrintException {
        if (file == null) {
            System.err.println("缺少打印文件");
        }
        InputStream fis = null;
        try {
            // 设置打印格式，如果未确定类型，可选择autosense
            DocFlavor flavor = DocFlavor.INPUT_STREAM.JPEG;
            // 设置打印参数
            PrintRequestAttributeSet aset = new HashPrintRequestAttributeSet();
            aset.add(new Copies(1)); //份数
            // aset.add(MediaSize.ISO.A4); //纸张
            // aset.add(Finishings.STAPLE);//装订
            aset.add(Sides.DUPLEX);//单双面
            // 定位打印服务
            PrintService printService = null;
            if (printerName != null) {
                //获得本台电脑连接的所有打印机
                PrintService[] printServices = PrinterJob.lookupPrintServices();
                if (printServices == null || printServices.length == 0) {
                    System.out.print("打印失败，未找到可用打印机，请检查。");
                    return;
                }
                //匹配指定打印机
                for (int i = 0; i < printServices.length; i++) {
                    System.out.println(printServices[i].getName());
                    if (printServices[i].getName().contains(printerName)) {
                        printService = printServices[i];
                        break;
                    }
                }
                if (printService == null) {
                    System.out.print("打印失败，未找到名称为" + printerName + "的打印机，请检查。");
                    return;
                }
            }
            fis = new FileInputStream(file); // 构造待打印的文件流
            Doc doc = new SimpleDoc(fis, flavor, null);
            DocPrintJob job = printService.createPrintJob(); // 创建打印作业
            job.print(doc, aset);
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } finally {
            // 关闭打印的文件流
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * wms的打印思路：
     * 司机行程单打印：
     * 1、查询Id下面的数据：DO
     * 2、将DO-->DTO 和 printJobInfoDTO
     * Response printResponse = printService.addPrintJob(printJobInfoDTO);
     * 3、添加到job中 PrintHandlerAdapter
     *  adapter.handle(printRequestDTO);
     * 4、保存每次打印的数据信息，到printReq.xml中
     *  Long reqId = saveReqeuest(printRequestDTO);
     * 5、通过适配器模式，获取到不同的实现类
     *  AbstractPrintHandler handler = getHandler(printRequestDTO);
     * 6、AbstractPrintHandler
     * 7、pdfConverter.exportToExcel。将数据导出到本机的excel中(通过freeMaker模版写的)
     * 8、remoteFileOperation.upload(localURL, remoteURL);在上传到远程的服务器上，删除本地临时文件
     * 9、printJob进行保存
     *  this.printJobManager.insert(job);
     */

    /**
     * DO->DTO->requestDO
     * service-->adapter(记录请求信息)-->Handler(1.生成excel.ftl文件 2.发送给打印机remoteUrl由打印机下载文件打印)
     */

    /**
     * public void sendPrintRequest(PrinterDTO printerDTO, JSONObject data) throws Exception {
     *         String url = String.format("http://%s:%s/wmsprinter/zpl/print.do", printerDTO.getPrinterIp(), printerDTO.getPrinterPort());
     *         // 加一个超时的配置，先设置大一点进行测试
     *         RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(20000).setConnectionRequestTimeout(10000).build();
     *         JSONObject result = HttpUtil.getJsonDataByPost(url, data.toJSONString(), requestConfig);
     *         Guard.notNull(result, "打印时获取返回数据失败");
     *         logger.warn("打印服务返回：" + result.toJSONString());
     *     }
     *
     *  1、数据导出生excel，上传远程
     *  2、并发送远程连接到客户端，进行拉取
     *
     *  3、拉取远程连接的内容
     *  4、下载内容
     *  5、开始打印
     */

}

