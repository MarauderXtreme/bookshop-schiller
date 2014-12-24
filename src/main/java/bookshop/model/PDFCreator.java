package bookshop.model;

import java.io.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;

import org.salespointframework.order.Order;
import org.salespointframework.order.OrderLine;
import org.salespointframework.useraccount.UserAccount;

import com.itextpdf.text.*;
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
	
	/**
	 * Create a PDF file
	 * @param order
	 * @param userAccount
	 */
	public void pdfCreate(Order order, UserAccount userAccount){
		Document document = new Document();
		HttpServletResponse test;
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
	        /*
	        PDFImageWriter image = new PDFImageWriter();
	    	image.writeImage(document, "png", null, 0, 0, System.getProperty("user.dir") + "/src/main/resources/static/resources/orders/" + order.getIdentifier().toString());
	    	*/
	    } catch (DocumentException e) {
	        e.printStackTrace();
	    } catch (FileNotFoundException e) {
	        e.printStackTrace();
	    }
	}
	
	/*
    public void pdfToImage(Order order) {
    	
    	try {
            String sourceDir = System.getProperty("user.dir") + "/src/main/resources/static/resources/orders/" + order.getIdentifier().toString() + ".pdf"; // Pdf files are read from this folder
            String destinationDir = System.getProperty("user.dir") + "/src/main/resources/static/resources/orders/"; // converted images from pdf document are saved here

            File sourceFile = new File(sourceDir);
            File destinationFile = new File(destinationDir);
            if (!destinationFile.exists()) {
                destinationFile.mkdir();
                System.out.println("Folder Created -> "+ destinationFile.getAbsolutePath());
            }
            if (sourceFile.exists()) {
                System.out.println("Images copied to Folder: "+ destinationFile.getName());             
                PDDocument document = PDDocument.load(sourceDir);
                List<PDPage> list = document.getDocumentCatalog().getAllPages();
                System.out.println("Total files to be converted -> "+ list.size());

                String fileName = sourceFile.getName().replace(".pdf", "");             
                int pageNumber = 1;
                for (PDPage page : list) {
                    BufferedImage image = page.convertToImage();
                    File outputfile = new File(destinationDir + fileName +"_"+ pageNumber +".jpg");
                    System.out.println("Image Created -> "+ outputfile.getName());
                    ImageIO.write(image, "jpg", outputfile);
                    pageNumber++;
                }
                document.close();
                System.out.println("Converted Images are saved at -> "+ destinationFile.getAbsolutePath());
            } else {
                System.err.println(sourceFile.getName() +" File not exists");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    	*/  
    
}
