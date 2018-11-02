package com.coomia.erm.util;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReportMergeXls {

    /**
     * 多行表头
     * dataList：导出的数据；sheetName：表头名称； head0：表头第一行列名；headnum0：第一行合并单元格的参数
     * head1：表头第二行列名；headnum1：第二行合并单元格的参数；detail：导出的表体字段
     *
     */
    public static void reportMergeXls(HttpServletRequest request,
                               HttpServletResponse response, List<Map<String, Object>> dataList,
                               String sheetName, String[] head0, String[] headnum0,
                               String[] head1, String[] headnum1, String[] detail , String date)
            throws Exception {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet(sheetName);// 创建一个表
        // 表头标题样式
        HSSFFont headfont = workbook.createFont();
        headfont.setFontName("宋体");
        headfont.setFontHeightInPoints((short) 22);// 字体大小
        HSSFCellStyle headstyle = workbook.createCellStyle();
        headstyle.setFont(headfont);
        headstyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 左右居中
        headstyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 上下居中
        headstyle.setLocked(true);
        // 表头时间样式
        HSSFFont datefont = workbook.createFont();
        datefont.setFontName("宋体");
        datefont.setFontHeightInPoints((short) 12);// 字体大小
        HSSFCellStyle datestyle = workbook.createCellStyle();
        datestyle.setFont(datefont);
        datestyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 左右居中
        datestyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 上下居中
        datestyle.setLocked(true);
        // 列名样式
        HSSFFont font = workbook.createFont();
        font.setFontName("宋体");
        font.setFontHeightInPoints((short) 12);// 字体大小
        HSSFCellStyle style = workbook.createCellStyle();
//        style.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
//        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
//        style.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
//        style.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
        style.setFont(font);
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 左右居中
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 上下居中
        style.setLocked(true);
        // 普通单元格样式（中文）
        HSSFFont font2 = workbook.createFont();
        font2.setFontName("宋体");
        font2.setFontHeightInPoints((short) 12);
        HSSFCellStyle style2 = workbook.createCellStyle();
        style2.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
        style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
        style2.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
        style2.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
        style2.setFont(font2);
        style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 左右居中
        style2.setWrapText(true); // 换行
        style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 上下居中
        // 设置列宽  （第几列，宽度）
        sheet.setColumnWidth( 0, 1600);
        sheet.setColumnWidth( 1, 3600);
        sheet.setColumnWidth( 2, 2800);
        sheet.setColumnWidth( 3, 2800);
        sheet.setColumnWidth( 4, 2800);
        sheet.setColumnWidth( 5, 2800);
        sheet.setColumnWidth( 6, 4500);
        sheet.setColumnWidth( 7, 3600);
        sheet.setColumnWidth( 9, 4500);
        sheet.setColumnWidth( 10, 4500);
        sheet.setColumnWidth( 11, 4500);
        sheet.setColumnWidth( 12, 4500);
        sheet.setColumnWidth( 13, 4500);
        sheet.setColumnWidth( 14, 4500);
        sheet.setColumnWidth( 15, 4500);
        sheet.setColumnWidth( 16, 4500);
        sheet.setColumnWidth( 17, 4500);
        sheet.setColumnWidth( 18, 4500);
        sheet.setColumnWidth( 19, 4500);
        sheet.setColumnWidth( 20, 4500);
        sheet.setColumnWidth( 21, 4500);
        sheet.setColumnWidth( 22, 4500);
        sheet.setColumnWidth( 23, 4500);
        sheet.setColumnWidth( 24, 4500);
        sheet.setColumnWidth( 25, 4500);
        sheet.setColumnWidth( 26, 4500);
        sheet.setColumnWidth( 27, 4500);
        sheet.setColumnWidth( 28, 4500);
        sheet.setColumnWidth( 29, 4500);
        sheet.setColumnWidth( 30, 4500);
        sheet.setColumnWidth( 31, 4500);
        sheet.setColumnWidth( 32, 4500);
        sheet.setColumnWidth( 33, 4500);
        sheet.setColumnWidth( 34, 4500);
        sheet.setColumnWidth( 35, 4500);
        sheet.setColumnWidth( 36, 4500);
        sheet.setColumnWidth( 37, 4500);
        sheet.setColumnWidth( 38, 4500);
        sheet.setColumnWidth( 39, 4500);

        sheet.setDefaultRowHeight((short)360);//设置行高
        // 第一行表头标题
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 40));
        HSSFRow row = sheet.createRow(0);
        row.setHeight((short) 0x349);
        HSSFCell cell = row.createCell(0);
        cell.setCellStyle(headstyle);
        CellUtil.setCellValue(cell, "慈利县2018年春季学期本乡镇（或本校）内就读学生信息台账");
        // 第二行时间
        sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 40));
        HSSFRow row1 = sheet.createRow(1);
        row1.setHeight((short) 0x349);
        HSSFCell cell1 = row1.createCell(0);
        cell1.setCellStyle(datestyle);
        CellUtil.setCellValue(cell1, "（乡镇中学、零阳镇中心校、普高和中职学校填写）");
        //第三行表头
        sheet.addMergedRegion(new CellRangeAddress(2, 2, 0, 10));
        HSSFRow row2 = sheet.createRow(2);
        row2.setHeight((short) 0x349);
        HSSFCell cell2 = row2.createCell(0);
        cell2.setCellStyle(style);
        CellUtil.setCellValue(cell2, "  单位名称(盖公章)："+date);
        sheet.addMergedRegion(new CellRangeAddress(2, 2, 12, 19));
        HSSFCell cell3 = row2.createCell(12);
        cell3.setCellStyle(style);
        CellUtil.setCellValue(cell3, "  填表人（签名）：");
        sheet.addMergedRegion(new CellRangeAddress(2, 2, 22, 35));
        HSSFCell cell4 = row2.createCell(22);
        cell4.setCellStyle(style);
        CellUtil.setCellValue(cell4, " 填表时间：");

        //第四行表头

        sheet.addMergedRegion(new CellRangeAddress(3, 3, 1, 7));
        HSSFRow row3 = sheet.createRow(3);
        row2.setHeight((short) 0x349);
        HSSFCell cell5 = row3.createCell(1);
        cell5.setCellStyle(style);
        CellUtil.setCellValue(cell5, " 学生及家长基本信息");
        sheet.addMergedRegion(new CellRangeAddress(3, 3, 8, 18));
        HSSFCell cell6 = row3.createCell(8);
        cell6.setCellStyle(style);
        CellUtil.setCellValue(cell6, " 学生就读信息");
        sheet.addMergedRegion(new CellRangeAddress(3, 3, 19, 24));
        HSSFCell cell7 = row3.createCell(19);
        cell7.setCellStyle(style);
        CellUtil.setCellValue(cell7, "家庭住址");
        sheet.addMergedRegion(new CellRangeAddress(3, 3, 25, 30));
        HSSFCell cell8 = row3.createCell(25);
        cell8.setCellStyle(style);
        CellUtil.setCellValue(cell8, " 贫困类型（在相应类型下画“1”）");
        sheet.addMergedRegion(new CellRangeAddress(3, 3, 31, 34));
        HSSFCell cell9 = row3.createCell(31);
        cell9.setCellStyle(style);
        CellUtil.setCellValue(cell9, "“湖南省扶贫补贴明白折”信息");
        sheet.addMergedRegion(new CellRangeAddress(3, 3, 35, 36));
        HSSFCell cell10 = row3.createCell(35);
        cell10.setCellStyle(style);
        CellUtil.setCellValue(cell10, " “学生资助卡”信息");
        sheet.addMergedRegion(new CellRangeAddress(3, 3, 37, 40));
        HSSFCell cell11 = row3.createCell(37);
        cell11.setCellStyle(style);
        CellUtil.setCellValue(cell11, "学生帮扶责任人");
        // 第三行表头列名
        row = sheet.createRow(4);
        for (int i = 0; i < head0.length; i++) {
            cell = row.createCell(i);
            cell.setCellValue(head0[i]);
            cell.setCellStyle(style);
        }
        //动态合并单元格
        for (int i = 0; i < headnum0.length; i++) {
            String[] temp = headnum0[i].split(",");
            Integer startrow = Integer.parseInt(temp[0]);
            Integer overrow = Integer.parseInt(temp[1]);
            Integer startcol = Integer.parseInt(temp[2]);
            Integer overcol = Integer.parseInt(temp[3]);
            sheet.addMergedRegion(new CellRangeAddress(startrow, overrow,
                    startcol, overcol));
        }
        //设置合并单元格的参数并初始化带边框的表头（这样做可以避免因为合并单元格后有的单元格的边框显示不出来）
        row = sheet.createRow(5);//因为下标从0开始，所以这里表示的是excel中的第四行
        for (int i = 0; i < head0.length; i++) {
            cell = row.createCell(i);
            cell.setCellStyle(style);//设置excel中第四行的1、2、7、8列的边框
            if(i > 10 && i< 16) {
                for (int j = 0; j < head1.length; j++) {
                    cell = row.createCell(j + 11);
                    cell.setCellValue(head1[j]);//给excel中第四行的3、4、5、6列赋值（"温度℃", "湿度%", "温度℃", "湿度%"）
                    cell.setCellStyle(style);//设置excel中第四行的3、4、5、6列的边框
                }
            }
        }
        //动态合并单元格
        for (int i = 0; i < headnum1.length; i++) {
            String[] temp = headnum1[i].split(",");
            Integer startrow = Integer.parseInt(temp[0]);
            Integer overrow = Integer.parseInt(temp[1]);
            Integer startcol = Integer.parseInt(temp[2]);
            Integer overcol = Integer.parseInt(temp[3]);
            sheet.addMergedRegion(new CellRangeAddress(startrow, overrow,
                    startcol, overcol));
        }

        // 设置列值-内容
        for (int i = 0; i < dataList.size(); i++) {
            row = sheet.createRow(i + 6 );
            for (int j = 0; j < detail.length; j++) {
                Map tempmap = (HashMap) dataList.get(i);
                Object data = tempmap.get(detail[j]);
                cell = row.createCell(j);
                cell.setCellStyle(style2);
                cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                CellUtil.setCellValue(cell, data);
            }
        }
        String fileName = new String(sheetName.getBytes("gb2312"), "ISO8859-1");
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        workbook.write(baos);
        response.setContentType("application/x-download;charset=utf-8");
        response.addHeader("Content-Disposition", "attachment;filename="
                + fileName + ".xls");
        OutputStream os = response.getOutputStream();
        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
        byte[] b = new byte[1024];
        while ((bais.read(b)) > 0) {
            os.write(b);
        }
        bais.close();
        os.flush();
        os.close();
    }



}
