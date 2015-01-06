package bookshop.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itextpdf.text.pdf.codec.Base64.OutputStream;

/**
 * Servlet implementation class PDF
 */
@WebServlet("/pdftext")
public class PDF extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		performTask(request, response);
	}

	private void performTask(HttpServletRequest request, HttpServletResponse response) throws ServletException,
	IOException {

		String pdfFileName = "4e0319bf-47dd-4c20-bf0a-3c8df6a6394d.pdf";
		String contextPath = getServletContext().getRealPath(File.separator);
		File pdfFile = new File(contextPath + pdfFileName);
		
		response.setContentType("application/pdf");
		response.addHeader("Content-Disposition", "attachment; filename=" + pdfFileName);
		response.setContentLength((int) pdfFile.length());
		
		FileInputStream fileInputStream = new FileInputStream(pdfFile);
		ServletOutputStream responseOutputStream = response.getOutputStream();
		int bytes;
		while ((bytes = fileInputStream.read()) != -1) {
			responseOutputStream.write(bytes);
	}

}
}
