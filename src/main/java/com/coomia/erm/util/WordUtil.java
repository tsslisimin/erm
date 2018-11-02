package com.coomia.erm.util;


import org.apache.poi.POIXMLDocument;
import org.apache.poi.xwpf.usermodel.*;

import java.io.FileOutputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 适用于word 2007
 * poi版本 3.7
 * @author Joanna.Yan
 *
 */
public class WordUtil {

	public static void searchAndReplace(String srcPath, String destPath,Map<String, String> map) {
		try {
			XWPFDocument document = new XWPFDocument(POIXMLDocument.openPackage(srcPath));
			/**
			 * 替换段落中的指定文字
			 */
			Iterator<XWPFParagraph> itPara = document.getParagraphsIterator();
			while (itPara.hasNext()) {
				XWPFParagraph paragraph = (XWPFParagraph) itPara.next();
				Set<String> set = map.keySet();
				Iterator<String> iterator = set.iterator();
				while (iterator.hasNext()) {
					String key = iterator.next();
					List<XWPFRun> run=paragraph.getRuns();
					for(int i=0;i<run.size();i++)
					{
						if(run.get(i).getText(run.get(i).getTextPosition())!=null &&
								run.get(i).getText(run.get(i).getTextPosition()).equals(key))
						{
							/**
							 * 参数0表示生成的文字是要从哪一个地方开始放置,设置文字从位置0开始
							 * 就可以把原来的文字全部替换掉了
							 */
							run.get(i).setText(map.get(key),0);
						}
					}
				}
			}

			/**
			 * 替换表格中的指定文字
			 */
			Iterator<XWPFTable> itTable = document.getTablesIterator();
			while (itTable.hasNext()) {
				XWPFTable table = (XWPFTable) itTable.next();
				int count = table.getNumberOfRows();
				for (int i = 0; i < count; i++) {
					XWPFTableRow row = table.getRow(i);
					List<XWPFTableCell> cells = row.getTableCells();
					for (XWPFTableCell cell : cells) {
						for (Map.Entry<String, String> e : map.entrySet()) {
							if (cell.getText().equals(e.getKey())) {
								cell.removeParagraph(0);
								cell.setText(e.getValue());
							}
						}
					}
				}
			}
			FileOutputStream outStream = null;
			outStream = new FileOutputStream(destPath);
			document.write(outStream);
			outStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}