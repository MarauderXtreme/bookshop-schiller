package bookshop.model;

import java.io.*;

import javax.servlet.http.HttpServlet;
import org.salespointframework.inventory.Inventory;
import org.salespointframework.inventory.InventoryItem;
import org.salespointframework.order.Order;
import org.salespointframework.order.OrderLine;
import org.salespointframework.useraccount.UserAccount;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;


public class PDFCreator extends HttpServlet {
	
	private final Inventory<InventoryItem> inventory;
	
	/**
	 * Constructor for PDFCreator
	 * @param inventory
	 */
	public PDFCreator(Inventory<InventoryItem> inventory){
		this.inventory = inventory;
	}
	
	
	/**
	 * Create a PDF file
	 * @param order
	 * @param userAccount
	 */
	public void pdfCreate(Order order, UserAccount userAccount){
		Document document = new Document();

	    try {
	        PdfWriter.getInstance(document,
	        		new FileOutputStream(System.getProperty("user.dir") + "/src/main/resources/static/resources/orders/" + order.getIdentifier().toString() + ".pdf"));

	        document.open();
	        document.add(new Paragraph("Bestellnummer:"));
	        document.add(new Paragraph(order.getIdentifier().toString()));
	        document.add(new Paragraph("Datum:"));
	        document.add(new Paragraph(order.getDateCreated().toString()));
	        document.add(new Paragraph("Kunde:"));
	        document.add(new Paragraph(userAccount.getFirstname()));
	        document.add(new Paragraph(userAccount.getLastname()));
	        document.add(Chunk.NEWLINE);
	        
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
	        	table.addCell(orderLine.getQuantity().getAmount().toString());
	        	table.addCell(orderLine.getProductName());
	        	table.addCell(inventory.findByProductIdentifier(orderLine.getProductIdentifier()).get().getProduct().getPrice().toString());
	        	table.addCell(orderLine.getPrice().toString());
	        }
	        	        
	        document.add(table);
	        document.add(new Paragraph("Gesamtbetrag:"));
	        document.add(new Paragraph(order.getTotalPrice().toString()));
	        
	        document.add(Chunk.NEWLINE);
	        document.add(Chunk.NEWLINE);
	        
	        document.add(new Paragraph("Bitte Überweisen sie den Betrag an folgendes Konto:"));
	        document.add(new Paragraph("Konto Nr:"));
	        document.add(new Paragraph("0000000000"));
	        document.add(new Paragraph("BLZ:"));
	        document.add(new Paragraph("00000000"));
	        document.add(new Paragraph("Verwendungszweck:"));
	        document.add(new Paragraph(order.getIdentifier().toString()));
	        
	        document.close();
	        
	    } catch (DocumentException e) {
	        e.printStackTrace();
	    } catch (FileNotFoundException e) {
	        e.printStackTrace();
	    }
	}
   
}
