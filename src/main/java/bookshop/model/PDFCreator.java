package bookshop.model;



import java.io.*;

import javax.persistence.Entity;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.salespointframework.inventory.InventoryItem;
import org.salespointframework.order.Order;
import org.salespointframework.order.OrderIdentifier;
import org.salespointframework.order.OrderLine;
import org.salespointframework.useraccount.UserAccount;
import org.springframework.core.io.ClassPathResource;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
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
		InventoryItem item;
		//String curDir = System.getProperty(")
	    try {
	        PdfWriter.getInstance(document,
	            new FileOutputStream(new File( order.getIdentifier().toString() + ".pdf")));
	//("user.dir") + "/src/main/resources/static/orders/" + order.getIdentifier().toString() + ".pdf"
	        
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

	        document.close();
	
	    } catch (DocumentException e) {
	        e.printStackTrace();
	    } catch (FileNotFoundException e) {
	        e.printStackTrace();
	    }
	}
	
	
	public void pdfCreate2(Order order, UserAccount userAccount, HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		
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
		/*
		public void test() throws DocumentException, IOException {
		    ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
		    templateResolver.setPrefix("META-INF/pdfTemplates/");
		    templateResolver.setSuffix(".html");
		    templateResolver.setTemplateMode("XHTML");
		    templateResolver.setCharacterEncoding("UTF-8");

		    TemplateEngine templateEngine = new TemplateEngine();
		    templateEngine.setTemplateResolver(templateResolver);

		    Context ctx = new Context();
		    ctx.setVariable("message", "I don't want to live on this planet anymore");
		    String htmlContent = templateEngine.process("messageTpl", ctx);

		    ByteOutputStream os = new ByteOutputStream();
		    ITextRenderer renderer = new ITextRenderer();
		    ITextFontResolver fontResolver = renderer.getFontResolver();

		    ClassPathResource regular = new ClassPathResource("/META-INF/fonts/LiberationSerif-Regular.ttf");
		    fontResolver.addFont(regular.getURL().toString(), BaseFont.IDENTITY_H, true);

		    renderer.setDocumentFromString(htmlContent);
		    renderer.layout();
		    renderer.createPDF(os);

		    byte[] pdfAsBytes = os.getBytes();
		    os.close();

		    FileOutputStream fos = new FileOutputStream(new File("/tmp/message.pdf"));
		    fos.write(pdfAsBytes);
		    fos.close();
		  }
		*/
		
	}	
	
	
}
