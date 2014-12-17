package bookshop.model;



import java.io.*;

import javax.persistence.Entity;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;

import org.salespointframework.order.Order;
import org.salespointframework.order.OrderIdentifier;
import org.salespointframework.order.OrderLine;
import org.salespointframework.useraccount.UserAccount;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;




public class PDFCreator extends HttpServlet {
	
	/*
	private final OrderIdentifier orderId;

	public PDFCreator(OrderIdentifier orderId){
		this.orderId = orderId;
	}
	*/
	public void pdfCreate(Order order, UserAccount userAccount){
		Document document = new Document();
		HttpServletResponse test;
		//String curDir = System.getProperty(")
	    try {
	        PdfWriter.getInstance(document,
	            new FileOutputStream(System.getProperty("user.dir") + "/src/main/resources/orders/" + order.getIdentifier().toString() + ".pdf"));
	
	        document.open();
	        document.add(new Paragraph("Bestellnummer:"));
	        document.add(new Paragraph(order.getIdentifier().toString()));
	        document.add(new Paragraph("Kunde:"));
	        document.add(new Paragraph(userAccount.getFirstname()));
	        document.add(new Paragraph(userAccount.getLastname()));
	        document.add(new Paragraph("Artikel:"));
	        
	        for(OrderLine orderLine : order.getOrderLines()){
	        	document.add(new Paragraph(orderLine.getProductName()));
	        	document.add(new Paragraph(orderLine.getQuantity().toString()));
	        }
	        document.close();
	
	    } catch (DocumentException e) {
	        e.printStackTrace();
	    } catch (FileNotFoundException e) {
	        e.printStackTrace();
	    }
	}
	/*
	
	public void pdfCreate(Order order, UserAccount userAccount, HttpServletResponse response)throws ServletException, IOException{
	
		Document document = new Document();
		//orderId = orderId + ".pdf";
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		//PdfWriter writer = PdfWriter.getInstance(document, baos);
		
		try {
	        PdfWriter.getInstance(document,baos);
	
	        document.open();
	        document.add(new Paragraph("Bestellnummer:"));
	        document.add(new Paragraph(order.getIdentifier().toString()));
	        document.add(new Paragraph("Kunde:"));
	        document.add(new Paragraph(userAccount.getFirstname()));
	        document.add(new Paragraph(userAccount.getLastname()));
	        document.add(new Paragraph("Artikel:"));
	        
	        for(OrderLine orderLine : order.getOrderLines()){
	        	document.add(new Paragraph(orderLine.getProductName()));
	        	document.add(new Paragraph(orderLine.getQuantity().toString()));
	        }
	        document.close(); 
	        
	        // setting some response headers
	            response.setHeader("Expires", "0");
	            response.setHeader("Cache-Control",
	                "must-revalidate, post-check=0, pre-check=0");
	            response.setHeader("Pragma", "public");
            // setting the content type
	            response.setContentType("application/pdf");
            // the contentlength
	            response.setContentLength(baos.size());
            // write ByteArrayOutputStream to the ServletOutputStream
	            OutputStream os = response.getOutputStream();
	            baos.writeTo(os);
	            os.flush();
	            os.close();

	
	    } catch (DocumentException e) {
	    	throw new IOException(e.getMessage());
	    }
		
	
	}	
	*/
	
}
