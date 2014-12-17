package bookshop.model;


/*
import java.io.*;

import javax.persistence.Entity;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;



*/
public class PDFCreator {
	
	/*
	
	public void PDFerzeugen(){
		    try {
		       PDFCreator pdf = new PDFCreator();
		       Document document = new Document();
		       PdfWriter.getInstance(document,new FileOutputStream("SimplePdf.pdf"));
		       document.open();
		       document.add(new Paragraph(pdf.getLines()));
		       document.close();
		     } catch (Exception e) {
		       e.printStackTrace();
		     }
		  }
	
	private String getLines() throws IOException {
		     BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		     StringBuffer result = new StringBuffer();
		     String line;
		     while((line = reader.readLine()) != null)
		       result.append(line).append("n");
		    return result.toString();
		   }
	*/
	/*
	
	public static final String result
    = "results/part1/chapter01/hello.pdf";

	/**
	 * Creates a PDF file: hello.pdf
	 * @param    args    no arguments needed
	 */
	/*
	public static void main(String[] args)
		throws DocumentException, IOException {
		new PDFCreator().createPdf(result);
	}
	*/
/*
	public PDFCreator(String result){
		//this.result = result;
	}
	/**
	 * Creates a PDF document.
	 * @param filename the path to the new PDF document
	 * @throws    DocumentException 
	 * @throws    IOException 
	 */
/*
	public void createPdf(String filename)
	throws DocumentException, IOException {
		    // step 1
		    Document document = new Document();
		    // step 2
		    PdfWriter.getInstance(document, new FileOutputStream(filename));
		    // step 3
		    document.open();
		    // step 4
		    document.add(new Paragraph("Hello World!"));
		    // step 5
		    document.close();
	}
	*/
}
