package bookshop.model;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//import bookshop.model.Book;

import org.salespointframework.order.Order;
import org.salespointframework.order.OrderLine;
import org.salespointframework.useraccount.UserAccount;

import bookshop.controller.AbstractITextPdfView;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

/**
 * This view class generates a PDF document 'on the fly' based on the data
 * contained in the model.
 * @author www.codejava.net
 *
 */
public class PDFBuilder extends AbstractITextPdfView {

	
	@Override
	protected void buildPdfDocument(Map<String, Object> model, Document document,
			PdfWriter writer, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		
		Order order = (Order) model.get("orderPDF");		
		
		// get data model which is passed by the Spring container
		document.add(new Paragraph("Bestellnummer:"));
        document.add(new Paragraph(order.getIdentifier().toString()));
        document.add(new Paragraph("Datum:"));
        document.add(new Paragraph(order.getDateCreated().toString()));
        document.add(new Paragraph("Kunde:"));
        //document.add(new Paragraph(userAccount.getFirstname()));
        //document.add(new Paragraph(userAccount.getLastname()));
        //document.add(new Paragraph("Artikel:"));
        
        PdfPTable table = new PdfPTable(4);
        PdfPCell cell1 = new PdfPCell(new Paragraph("Menge"));
        PdfPCell cell2 = new PdfPCell(new Paragraph("Artikel"));
        PdfPCell cell3 = new PdfPCell(new Paragraph("Preis"));
        PdfPCell cell4 = new PdfPCell(new Paragraph("Gesamt"));
        
        /*
        cell1.setBorder(Rectangle.NO_BORDER);
        cell2.setBorder(Rectangle.NO_BORDER);
        cell3.setBorder(Rectangle.NO_BORDER);
        cell4.setBorder(Rectangle.NO_BORDER);
        */
        
        table.addCell(cell1);
        table.addCell(cell2);
        table.addCell(cell3);
        table.addCell(cell4);
        
        for(OrderLine orderLine : order.getOrderLines()){
        	table.addCell(orderLine.getQuantity().toString());
        	table.addCell(orderLine.getProductName());
        	table.addCell("test");
        	table.addCell(orderLine.getPrice().toString());
        }
        	        
        document.add(table);
        document.add(new Paragraph("Gesamtbetrag:"));
        document.add(new Paragraph(order.getTotalPrice().toString()));
		
		
		/*
		List<Book> listBooks = (List<Book>) model.get("listBooks");
		
		document.add(new Paragraph("Recommended books for Spring framework"));
		
		PdfPTable table = new PdfPTable(5);
		table.setWidthPercentage(100.0f);
		table.setWidths(new float[] {3.0f, 2.0f, 2.0f, 2.0f, 1.0f});
		table.setSpacingBefore(10);
		
		// define font for table header row
		Font font = FontFactory.getFont(FontFactory.HELVETICA);
		font.setColor(BaseColor.WHITE);
		
		// define table header cell
		PdfPCell cell = new PdfPCell();
		cell.setBackgroundColor(BaseColor.BLUE);
		cell.setPadding(5);
		
		// write table header 
		cell.setPhrase(new Phrase("Book Title", font));
		table.addCell(cell);
		
		cell.setPhrase(new Phrase("Author", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("ISBN", font));
		table.addCell(cell);
		
		cell.setPhrase(new Phrase("Published Date", font));
		table.addCell(cell);
		
		cell.setPhrase(new Phrase("Price", font));
		table.addCell(cell);
		
		// write table row data
		for (Book aBook : listBooks) {
			table.addCell(aBook.getTitle());
			table.addCell(aBook.getAuthor());
			table.addCell(aBook.getIsbn());
			table.addCell(aBook.getPublishedDate());
			table.addCell(String.valueOf(aBook.getPrice()));
		}
		
		document.add(table);
		*/
	}

}