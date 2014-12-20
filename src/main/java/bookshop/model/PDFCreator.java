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
	
	/*
	private final OrderIdentifier orderId;

	public PDFCreator(OrderIdentifier orderId){
		this.orderId = orderId;
	}
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
	public void createImg(Order order){
		String destinationImageFormat = "jpg";
		boolean success = false;
		InputStream is = getClass().getClassLoader().getResourceAsStream(System.getProperty("user.dir") + "/src/main/resources/static/resources/orders/" + order.getIdentifier().toString() + ".pdf");
		PDDocument pdf;
		try {
			pdf = PDDocument.load( is, true );
			int resolution = 256;
			String password = "";
			String outputPrefix = "myImageFile";
	
			PDFImageWriter imageWriter = new PDFImageWriter();    
	
			success = imageWriter.writeImage(pdf, 
			                    destinationImageFormat, 
			                    password, 
			                    1, 
			                    2, 
			                    outputPrefix, 
			                    BufferedImage.TYPE_INT_RGB, 
			                    resolution);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void pdfCreate2( HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		
		Document document = new Document();
		//orderId = orderId + ".pdf";
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		//PdfWriter writer = PdfWriter.getInstance(document, baos);
		
		try {
	        PdfWriter.getInstance(document,baos);
	
	        document.open();
	        document.add(new Paragraph("Bestellnummer:"));
	       // document.add(new Paragraph(order.getIdentifier().toString()));
	        document.add(new Paragraph("Kunde:"));
	        //document.add(new Paragraph(userAccount.getFirstname()));
	       // document.add(new Paragraph(userAccount.getLastname()));
	        document.add(new Paragraph("Artikel:"));
	        
	      //  for(OrderLine orderLine : order.getOrderLines()){
	        //	document.add(new Paragraph(orderLine.getProductName()));
	       // 	document.add(new Paragraph(orderLine.getQuantity().toString()));
	        ///}
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
	
    public void pdfToImage(Order order) {
    	
    	try {
            String sourceDir = System.getProperty("user.dir") + "/src/main/resources/static/resources/orders/" + order.getIdentifier().toString() + ".pdf"; // Pdf files are read from this folder
            String destinationDir = System.getProperty("user.dir") + "/src/main/resources/static/resources/orders/" + order.getIdentifier().toString(); // converted images from pdf document are saved here

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
    	
    	
    	/*
    	PDDocument document = new PDDocument();
    	//PDDocument document = PDDocument.load(System.getProperty("user.dir") + "/src/main/resources/static/resources/orders/" + order.getIdentifier().toString() + ".pdf");
    	PDFImageWriter image = new PDFImageWriter();
    	try {
    		document = PDDocument.load(System.getProperty("user.dir") + "/src/main/resources/static/resources/orders/" + order.getIdentifier().toString() + ".pdf");
			image.writeImage(document, "png", null, 0, 0, System.getProperty("user.dir") + "/src/main/resources/static/resources/orders/" + order.getIdentifier().toString());
		}catch (Exception e) {
	        e.printStackTrace();
	    }
    	*/
    	/*
    	try {
        String sourceDir = System.getProperty("user.dir") + "/src/main/resources/static/resources/orders/" + order.getIdentifier().toString() + ".pdf"; // Pdf files are read from this folder
        String destinationDir = System.getProperty("user.dir") + "/src/main/resources/static/resources/orders/" + order.getIdentifier().toString(); // converted images from pdf document are saved here

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
                File outputfile = new File(destinationDir + fileName +"_"+ pageNumber +".png");
                System.out.println("Image Created -> "+ outputfile.getName());
                ImageIO.write(image, "png", outputfile);
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
}
