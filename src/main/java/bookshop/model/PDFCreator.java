package bookshop.model;



import java.awt.image.BufferedImage;
import java.io.*;
import java.util.List;

import javax.imageio.ImageIO;
import javax.persistence.Entity;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.util.PDFImageWriter;
import org.salespointframework.inventory.InventoryItem;
import org.salespointframework.order.Order;
import org.salespointframework.order.OrderIdentifier;
import org.salespointframework.order.OrderLine;
import org.salespointframework.useraccount.UserAccount;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;


public class PDFCreator extends HttpServlet {
	
	/**
	 * Create a PDF file
	 * @param order
	 * @param userAccount
	 */
	public void pdfCreate(Order order, UserAccount userAccount){
		Document document = new Document();
		//InventoryItem item;
		//String curDir = System.getProperty(")
		System.out.println(order.getIdentifier().toString());
	    try {
	        PdfWriter.getInstance(document,
	        		new FileOutputStream(System.getProperty("user.dir") + "/src/main/resources/static/resources/orders/" + order.getIdentifier().toString() + ".pdf"));
	    	//PdfWriter.getInstance(document,
	    	//new FileOutputStream(System.getProperty(("user.dir") + "/src/main/resources/static/orders/" + order.getIdentifier().toString() + ".pdf"));
	        
	        document.open();
	        document.add(new Paragraph("Bestellnummer:"));
	        document.add(new Paragraph(order.getIdentifier().toString()));
	        document.add(new Paragraph("Datum:"));
	        document.add(new Paragraph(order.getDateCreated().toString()));
	        document.add(new Paragraph("Kunde:"));
	        document.add(new Paragraph(userAccount.getFirstname()));
	        document.add(new Paragraph(userAccount.getLastname()));
	        //document.add(new Paragraph("Artikel:"));
	        
	        PdfPTable table = new PdfPTable(4);
	        PdfPCell cell1 = new PdfPCell(new Paragraph("Menge"));
	        PdfPCell cell2 = new PdfPCell(new Paragraph("Artikel"));
	        PdfPCell cell3 = new PdfPCell(new Paragraph("Preis"));
	        PdfPCell cell4 = new PdfPCell(new Paragraph("Gesamt"));
	 
	        
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

	        document.close();
	        
	    } catch (DocumentException e) {
	        e.printStackTrace();
	    } catch (FileNotFoundException e) {
	        e.printStackTrace();
	    }
	}
   
}
