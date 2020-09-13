package com.example.spring.freemark.excel;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelCollection;
import cn.afterturn.easypoi.excel.annotation.ExcelEntity;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import lombok.Data;
import org.apache.poi.ss.usermodel.Workbook;

import java.util.Collections;
import java.util.Date;
import java.util.List;

@Data
public class StudentEntity implements java.io.Serializable {

    //学生
    /**
     * id
     */
    private String id;
    /**
     * 学生姓名
     */
    @Excel(name = "学生姓名", height = 20, width = 30, isImportField = "true_st")
    private String name;
    /**
     * 学生性别
     */
    @Excel(name = "学生性别", replace = {"男_1", "女_2"}, suffix = "生", isImportField = "true_st")
    private int sex;

    @Excel(name = "出生日期", databaseFormat = "yyyyMMddHHmmss", format = "yyyy-MM-dd", isImportField = "true_st", width = 20)
    private Date birthday;

    @Excel(name = "进校日期", databaseFormat = "yyyyMMddHHmmss", format = "yyyy-MM-dd")
    private Date registrationDate;

    //导出
    Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("计算机一班学生", "学生"), StudentEntity.class, Collections.emptyList());

    //课程
    @ExcelTarget("courseEntity")
    public class CourseEntity implements java.io.Serializable {
        /**
         * 主键
         */
        private String id;
        /**
         * 课程名称
         */
        @Excel(name = "课程名称", orderNum = "1", width = 25)
        private String name;
        /**
         * 老师主键
         */
        @ExcelEntity(id = "absent")
        private TeacherEntity mathTeacher;

        @ExcelCollection(name = "学生", orderNum = "4")
        private List<StudentEntity> students;
    }

    //老师
    @ExcelTarget("teacherEntity")
    public class TeacherEntity implements java.io.Serializable {
        private String id;
        /**
         * name
         */
        @Excel(name = "主讲老师_major,代课老师_absent", orderNum = "1", needMerge = true, isImportField = "true_major,true_absent")
        private String name;
    }

    //图片
    @Excel(name = "公司LOGO", type = 2, width = 40, height = 20, imageType = 1)
    private String companyLogo; //可以用相对路径也可以用绝对路径,绝对路径优先依次获取

    @Excel(name = "公司LOGO", type = 2, width = 40, height = 20, imageType = 2)
    private byte[] companyLogoDb; //表示从数据库或者已经读取完毕,字段类型是个字节数组


    /**
     * 注解：
     * @Excel
     * @ExcelEntity :对应一个实体
     * @ExcelCollection：对应一个集合（一对多的情况）
     * @ExcelIgnore：忽略某个字段
     * @ExcelTarget:标记一个实体
     */


    // 导出
//      ExcelExportUtil：
//           exportExcel(ExportParams entity, Class<?> pojoClass, Collection<?> dataSet)
//           exportExcel(ExportParams entity, List<ExcelExportEntity> entityList, Collection<?> dataSet)
//           exportExcel(List<Map<String, Object>> list, ExcelType type)  //多sheet情况一个map对应一个sheet
//           exportExcel(TemplateExportParams params, Map<String, Object> map) //模版导出
//           exportExcel(Map<Integer, Map<String, Object>> map,TemplateExportParams params) //多sheet模版导出
//           exportExcelClone(Map<Integer, List<Map<String, Object>>> map,TemplateExportParams params)
//
//           exportBigExcel(ExportParams entity, Class<?> pojoClass, IExcelExportServer server, Object queryParams)
//           exportBigExcel(ExportParams entity, List<ExcelExportEntity> excelParams,IExcelExportServer server, Object queryParams)


//      导入：
//      ExcelImportUtil：
//           importExcel(File file, Class<?> pojoClass, ImportParams params)
//           importExcel(InputStream inputstream, Class<?> pojoClass,ImportParams params)
//           importExcelMore(InputStream inputstream,Class<?> pojoClass,ImportParams params) //返回校验结果
//           importExcelMore(File file, Class<?> pojoClass,ImportParams params)  //返回校验结果
//           importExcelBySax(InputStream inputstream, Class<?> pojoClass,ImportParams params, IReadHandler handler) //数据量大的导入
//

    /**
     * word导出：
     * WordExportUtil：
     *      exportWord07(String url, Map<String, Object> map)
     *      exportWord07(XWPFDocument document,Map<String, Object> map)
     *      exportWord07(String url, List<Map<String, Object>> list)
     */

    /**
     * pdf导出：
     *  PdfExportUtil：
     *      exportPdf(PdfExportParams entity, Class<?> pojoClass,Collection<?> dataSet, OutputStream outStream)
     *      exportPdf(PdfExportParams entity, List<ExcelExportEntity> entityList,Collection<? extends Map<?, ?>> dataSet,OutputStream outStream
     */

}