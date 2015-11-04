package videoencoder.servlets;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import videoencoder.storage.S3Uploader;
import videoencoder.storage.base.IUploader;

/**
 * Servlet implementation class Encode
 */
@WebServlet(urlPatterns = { "/Encode" })
@MultipartConfig(fileSizeThreshold=1024*1024*10,    // 10 MB 
	maxFileSize=1024*1024*50,          // 50 MB
	maxRequestSize=1024*1024*100)      // 100 MB
public class EncodingController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EncodingController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json");
	    
	    final Part filePart = request.getPart("file");

	    InputStream filecontent = null;

	    filecontent = filePart.getInputStream();

        IUploader uploader = new S3Uploader();
        uploader.sendFile(filecontent, filePart.getSize());
        
        if (filecontent != null) {
            filecontent.close();
        }
        
		response.setStatus(HttpServletResponse.SC_OK);
	}
	
	private String getFileName(final Part part) {
	    //final String partHeader = part.getHeader("content-disposition");
	    
	    for (String content : part.getHeader("content-disposition").split(";")) {
	        if (content.trim().startsWith("filename")) {
	            return content.substring(
	                    content.indexOf('=') + 1).trim().replace("\"", "");
	        }
	    }
	    return null;
	}

}
