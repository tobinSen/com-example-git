package com.example.spring.freemark.controller;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.example.spring.freemark.excel.User;
import com.example.spring.freemark.pdf.PdfUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfig;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class ExcelController {

    @Autowired
    private FreeMarkerConfig freeMarkerConfig;

    /**
     * 导入Excel
     * <p>
     * 1.Excel导入
     * <p>
     * 注解导入
     * Map导入
     * 大数据量导入sax模式
     * 导入文件保存
     * 文件校验
     * 字段校验
     */
    @RequestMapping(value = "/import", method = RequestMethod.POST)
    public String importExcel(MultipartFile file) throws Exception {
        ImportParams params = new ImportParams();
        params.setTitleRows(0);
        params.setHeadRows(1);
        params.setNeedSave(false);
        params.setSheetNum(1);//需要读取几个sheet
        List<User> list = ExcelImportUtil.importExcel(file.getInputStream(), User.class, params);
        //mUserService.save(list);
        return "redirect:/";
    }

    /**
     * 导出Excel
     */
    @RequestMapping(value = "/export")
    public void export(HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setHeader("content-Type", "application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment;filename=pdfTemplate.pdf");
//        List<User> list = Lists.newArrayList();
//        User user1 = new User();
//        user1.setId(1);
//        user1.setAge(12);
//        user1.setName("tom");
//        user1.setSex(1);
//        user1.setCreateTime(new Date());
//        list.add(user1);

        // 方式一：
        // Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams(), User.class, list);

        //方式二：只能使用map
//        List<Map<String, Object>> list = new ArrayList<>();
//        Map<String,Object> map = new HashMap<>();
//        map.put("name", "童健");
//        map.put("sex", "男");
//        map.put("age", "24");
//        Map<String,Object> map1 = new HashMap<>();
//        map1.put("name", "童健");
//        map1.put("sex", "男");
//        map1.put("age", "25");
//        list.add(map);
//        list.add(map1);
//        List<ExcelExportEntity> entityList = new ArrayList<>();
//        entityList.add(new ExcelExportEntity("姓名", "name"));
//        entityList.add(new ExcelExportEntity("性别", "sex"));
//        entityList.add(new ExcelExportEntity("年龄", "age"));
//        //entityList.add(new ExcelExportEntity("创建时间", "createTime").setFormat("yyyy-MM-dd HH:mm:ss"));

        //方式三：ExcelType.HSSF 只能用这个
//        List<Map<String, Object>> list = new ArrayList<>();
//        Map<String, Object> map = new HashMap<>();
//        map.put("title", new ExportParams("sheet1-title", "sheet1-title"));
//        map.put("entity", User.class);
//        map.put("data", Collections.emptyList());
//        Map<String, Object> map1 = new HashMap<>();
//        map1.put("title", new ExportParams("sheet2-title", "sheet2-title"));
//        map1.put("entity", User.class);
//        map1.put("data", Collections.emptyList());
//        list.add(map);
//        list.add(map1);
//        Workbook workbook = ExcelExportUtil.exportExcel(list, ExcelType.HSSF);

        //方式四：模版中不存在，自动为空
//        TemplateExportParams params = new TemplateExportParams("web/template.xlsx", "template-sheet");
//        Map<String, Object> map = new HashMap<>();
//        map.put("title", "template-sheet");
//        //map.put("name", "template-sheet-name");
//        Workbook workbook = ExcelExportUtil.exportExcel(params, map);

        //方式五：
//        TemplateExportParams params = new TemplateExportParams("web/template.xlsx", true);
//        Map<Integer, Map<String, Object>> map = new HashMap<>();
//        Map<String, Object> sheet = new HashMap<>();
//        sheet.put("title", "template-sheet");
//        sheet.put("name", "template-sheet-name");
//        map.put(0, sheet);
//        map.put(1, sheet);

        //方式六：
//        TemplateExportParams params = new TemplateExportParams("web/template.xlsx", true);
//        Map<Integer, List<Map<String, Object>>> map = new HashMap<>();
//        List<Map<String, Object>> list = new ArrayList<>();
//        Map<String, Object> sheetMap = new HashMap<>();
//        sheetMap.put("title", "title");
//        list.add(sheetMap);
//        map.put(1, list);
//        Workbook workbook = ExcelExportUtil.exportExcelClone(map, params);
//        workbook.write(response.getOutputStream());


        //word导出ImageEntity image = new ImageEntity();
//        Map<String, Object> map = new HashMap<>();
//        map.put("title", "word-title");
//        XWPFDocument doc = WordExportUtil.exportWord07("web/wordTemplate.docx", map);
//        doc.write(response.getOutputStream());

//        PdfExportParams params = new PdfExportParams();
//        List<MsgClient> list = new ArrayList<MsgClient>();
//        for (int i = 0; i < 10; i++) {
//            MsgClient client = new MsgClient();
//            client.setBirthday(new Date());
//            client.setClientName("小明" + i);
//            client.setClientPhone("18797" + i);
//            client.setCreateBy("JueYue");
//            client.setId("1" + i);
//            client.setRemark("测试" + i);
//            list.add(client);
//        }
//        //pdf导出
//        PdfExportUtil.exportPdf(params, MsgClient.class, list, response.getOutputStream());

//        Map<String, Object> map = new HashMap<>();
//        map.put("title", "word-title");
//        Template template = freeMarkerConfig.getConfiguration().getTemplate("web/freemark.ftl");
//        String htmlStr = FreeMarkerTemplateUtils.processTemplateIntoString(template, map);
//        ITextRenderer renderer = new ITextRenderer();
//        renderer.setDocumentFromString(htmlStr);
//        ITextFontResolver fontResolver = renderer.getFontResolver();
//        //增加字体
//        renderer.layout();
//        renderer.createPDF(response.getOutputStream());
        Map<String, Object> map = new HashMap<>();
        map.put("title", "word-title");
        byte[] bytes = PdfUtil.generatePdf("insurantList", "web/freemarkpdf.ftl", map, "static/images/test.png", "fonts/");
        response.getWriter().print(bytes);
    }

}
