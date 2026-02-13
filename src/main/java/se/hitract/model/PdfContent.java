package se.hitract.model;

import com.lowagie.text.*;
import com.lowagie.text.alignment.HorizontalAlignment;

public class PdfContent {

	public static Paragraph getParagraph(String string, int headerSize, int spacingBefore, int spacingAfter) {
		Paragraph paragraph = getParagraph(string, headerSize);
		paragraph.setSpacingBefore(spacingBefore);
		paragraph.setSpacingAfter(spacingAfter);
		return paragraph;
	}

	public static Paragraph getParagraph(String string, int headerSize, int spacingBefore) {
		Paragraph paragraph = getParagraph(string, headerSize);
		paragraph.setSpacingBefore(spacingBefore);
		return paragraph;
	}

	public static Paragraph getParagraph(String text, int size) {
		Paragraph paragraph = new Paragraph(new Phrase(text, new Font(Font.COURIER, size, Font.BOLD)));
		paragraph.setAlignment(Paragraph.ALIGN_LEFT);
		return paragraph;
	}
	
	public static Paragraph getBodyParagraph(String text, int size) {
		Paragraph paragraph = new Paragraph(new Phrase(text, new Font(Font.COURIER, size)));
		paragraph.setAlignment(Paragraph.ALIGN_LEFT);
		return paragraph;
	}
	
	public static Table createTable(int size, int[] widths) {
		Table table = new Table(size);
		table.setPadding(2);
		table.setWidth(100);
		table.setBorder(0);
		table.setWidths(widths);
		return table;
	}

	public static Cell createCell(String content) {
		return createCell(content, null);
	}
	
	public static Cell createCell(String content, Integer side) {
		Cell cell5 = new Cell(new Phrase(content, new Font(Font.COURIER, 6.5f, side != null ? Font.BOLD : Font.NORMAL)));
		cell5.setHorizontalAlignment(HorizontalAlignment.LEFT);
		cell5.setBorder(0);
		if(side != null) {
			cell5.enableBorderSide(side);
		}
		return cell5;
	}

	public static Cell createCellReceipt(String content) {
		float size = 9f;
		return createCell(content, null, size);
	}

	public static Cell createCell(String content, Integer side, float size) {
		Cell cell5 = new Cell(new Phrase(content, new Font(Font.COURIER, size, side != null ? Font.BOLD : Font.NORMAL)));
		cell5.setHorizontalAlignment(HorizontalAlignment.LEFT);
		cell5.setBorder(0);
		if(side != null) {
			cell5.enableBorderSide(side);
		}
		return cell5;
	}
}
