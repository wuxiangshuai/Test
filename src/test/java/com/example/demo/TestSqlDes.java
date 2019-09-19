package com.example.demo;

import java.io.File;
import java.io.FileOutputStream;
import java.math.BigInteger;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.TextAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @ClassName: TestSqlDes
 * @Author: WuXiangShuai
 * @Time: 16:30 2019/9/9.
 * @Description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TestSqlDes {

    @Value("${data_name}")
    private String dataName;
    @Value("${doc_dir_path}")
    private String docDirPath;
    @Autowired
    DriverManagerDataSource driverManagerDataSource;
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Test
    public void test() {
        createWord();
    }

    /**
     * 生成数据库设计文档
     */
    public void createWord() {
        XWPFDocument xdoc = new XWPFDocument();
        XWPFParagraph title = xdoc.createParagraph();
        title.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun rt = title.createRun();
        rt.setBold(true);
        rt.setFontFamily("微软雅黑");
        rt.setText(dataName+"数据库设计文档");
        rt.setFontSize(20);
        rt.setColor("333333");
        rt.setBold(true);

        Map<String, String[][]> datas = dataInfo(dataName);
        Set<String> keySet = datas.keySet();
        // ---
        title(xdoc, rt, datas, dataName);
        // ---
        for (String table : keySet) {
            XWPFParagraph headLine1 = xdoc.createParagraph();
            headLine1.setAlignment(ParagraphAlignment.LEFT);
            XWPFRun runHeadLine1 = headLine1.createRun();
            runHeadLine1.setText(table);
            runHeadLine1.setFontSize(10);
            runHeadLine1.setFontFamily("微软雅黑");
//            runHeadLine1.setColor("a6a6a6");

            String[][] clumns = datas.get(table);

            XWPFTable dTable = xdoc.createTable(clumns.length + 1, 6);
            createTable(dTable, xdoc, clumns);
            setEmptyRow(xdoc, rt);
        }
        // 在服务器端生成
        FileOutputStream fos = null;
        try {
            String docPath = docDirPath+ File.separator+dataName+"_"+(new Date()).getTime()+".docx";
            FileUtils.forceMkdirParent(new File(docPath));
            fos = new FileOutputStream(docPath);
            xdoc.write(fos);
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void title(XWPFDocument xdoc, XWPFRun rt, Map<String, String[][]> datas, String dataName) {
        List<Map<String, Object>> list = jdbcTemplate.queryForList(
                "select table_name tbName, table_comment tbCom from information_schema.tables where table_schema = ?", dataName);

        XWPFParagraph headLine1 = xdoc.createParagraph();
        headLine1.setAlignment(ParagraphAlignment.LEFT);
        XWPFRun runHeadLine1 = headLine1.createRun();
        runHeadLine1.setText("123");
        runHeadLine1.setFontSize(10);
        runHeadLine1.setFontFamily("微软雅黑");
//            runHeadLine1.setColor("a6a6a6");

        XWPFTable dTable = xdoc.createTable(list.size() + 1, 3);

        String titleColor = "F2F2F2";
        String bgColor = "000000";
        CTTbl ttbl = dTable.getCTTbl();
        CTTblPr tblPr = ttbl.getTblPr() == null ? ttbl.addNewTblPr() : ttbl.getTblPr();
        CTTblWidth tblWidth = tblPr.isSetTblW() ? tblPr.getTblW() : tblPr.addNewTblW();
        tblWidth.setW(new BigInteger("8600"));
        tblWidth.setType(STTblWidth.DXA);
        setCellText(xdoc, getCellHight(dTable, 0, 0), "序号", titleColor, 600);
        setCellText(xdoc, getCellHight(dTable, 0, 1), "数据表", titleColor, 4500);
        setCellText(xdoc, getCellHight(dTable, 0, 2), "注释", titleColor, 3000);
        for (int i = 0; i < list.size(); i++) {
            setCellText(xdoc, getCellHight(dTable, i + 1, 0), String.valueOf(i + 1), bgColor, 600);
            setCellText(xdoc, getCellHight(dTable, i + 1, 1), list.get(i).get("tbName").toString(), bgColor, 4500);
            setCellText(xdoc, getCellHight(dTable, i + 1, 2), list.get(i).get("tbCom").toString(), bgColor, 3000);
        }
        setEmptyRow(xdoc, rt);

    }

    /**
     * 获取数据库每个表的信息
     *
     * @param data
     * @return
     */
    public Map<String, String[][]> dataInfo(String data) {
        List<Map<String, Object>> list = jdbcTemplate.queryForList(
                "select table_name,table_comment from information_schema.tables where table_schema = ?", data);
        Map<String, String[][]> datas = new HashMap<String, String[][]>();
        for (Map<String, Object> map : list) {
            String table_name = map.get("table_name") + "";
            String table_comment = map.get("table_comment") + "";
            datas.put("表 : " + table_name + " : " + table_comment, tableInfo(data + "." + table_name));
        }
        return datas;
    }

    /**
     * 获取每个表的字段信息
     *
     * @param table
     * @return
     */
    public String[][] tableInfo(String table) {
        List<Map<String, Object>> list = jdbcTemplate.queryForList("SHOW FULL FIELDS FROM " + table);
        String[][] tables = new String[list.size()][6];
        for (int i = 0; i < list.size(); i++) {
            Map<String, Object> map = list.get(i);
            String[] info = new String[6];
            info[0] = map.get("Field") + "";    // 字段名
            info[1] = map.get("Type") + "";     // 数据类型
            info[2] = map.get("Default") + "";  // 默认值
            info[3] = map.get("Null") + "";     // 是否允许该字段为空
            info[4] = map.get("Extra") + "";    // 是否递增
            info[5] = map.get("Comment") + "";  // 字段描述
            tables[i] = info;
        }
        return tables;
    }

    /**
     * 生成表格
     *
     * @param xTable
     * @param xdoc
     */
    public static void createTable(XWPFTable xTable, XWPFDocument xdoc, String[][] clumns) {
        String titleColor = "F2F2F2";
        String bgColor = "000000";
        CTTbl ttbl = xTable.getCTTbl();
        CTTblPr tblPr = ttbl.getTblPr() == null ? ttbl.addNewTblPr() : ttbl.getTblPr();
        CTTblWidth tblWidth = tblPr.isSetTblW() ? tblPr.getTblW() : tblPr.addNewTblW();
        tblWidth.setW(new BigInteger("8600"));
        tblWidth.setType(STTblWidth.DXA);
        setCellText(xdoc, getCellHight(xTable, 0, 0), "字段名", titleColor, 1800);
        setCellText(xdoc, getCellHight(xTable, 0, 1), "类型", titleColor, 1300);
        setCellText(xdoc, getCellHight(xTable, 0, 2), "默认值", titleColor, 800);
        setCellText(xdoc, getCellHight(xTable, 0, 3), "允许为空", titleColor, 1000);
        setCellText(xdoc, getCellHight(xTable, 0, 4), "自动递增", titleColor, 1000);
        setCellText(xdoc, getCellHight(xTable, 0, 5), "说明", titleColor, 2800);
        int length = clumns.length;
        for (int i = 0; i < length; i++) {
            setCellText(xdoc, getCellHight(xTable, i + 1, 0), clumns[i][0], bgColor, 1800);
            setCellText(xdoc, getCellHight(xTable, i + 1, 1), clumns[i][1], bgColor, 1300);
            setCellText(xdoc, getCellHight(xTable, i + 1, 2), clumns[i][2], bgColor, 800);
            setCellText(xdoc, getCellHight(xTable, i + 1, 3), clumns[i][3], bgColor, 1000);
            setCellText(xdoc, getCellHight(xTable, i + 1, 4), clumns[i][4], bgColor, 1000);
            setCellText(xdoc, getCellHight(xTable, i + 1, 5), clumns[i][5], bgColor, 2800);
        }
    }

    // 设置表格高度
    private static XWPFTableCell getCellHight(XWPFTable xTable, int rowNomber, int cellNumber) {
        XWPFTableRow row = null;
        row = xTable.getRow(rowNomber);
        row.setHeight(100);
        XWPFTableCell cell = null;
        cell = row.getCell(cellNumber);
        return cell;
    }

    /**
     * 单元格设置文本
     *
     * @param xDocument
     * @param cell
     * @param text
     * @param bgcolor
     * @param width
     */
    private static void setCellText(XWPFDocument xDocument, XWPFTableCell cell, String text, String bgcolor,
                                    int width) {
        CTTc cttc = cell.getCTTc();
        CTTcPr cellPr = cttc.addNewTcPr();
        cellPr.addNewTcW().setW(BigInteger.valueOf(width));
        XWPFParagraph pIO = cell.addParagraph();
        cell.removeParagraph(0);
        XWPFRun rIO = pIO.createRun();
        rIO.setFontFamily("微软雅黑");
        rIO.setFontSize(9);
//        rIO.setColor(bgcolor);
        rIO.setTextPosition(3);
        if (null == text || "".equals(text) || "null".equals(text)) {
            rIO.setText("");
        } else {
            rIO.setText(text);
        }
    }

    // 设置表格间的空行
    public static void setEmptyRow(XWPFDocument xdoc, XWPFRun r1) {
        XWPFParagraph p1 = xdoc.createParagraph();
        p1.setAlignment(ParagraphAlignment.CENTER);
        p1.setVerticalAlignment(TextAlignment.CENTER);
        r1 = p1.createRun();
    }

}
