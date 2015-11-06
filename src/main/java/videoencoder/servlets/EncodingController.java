package videoencoder.servlets;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import videoencoder.encoding.Zencoder;
import videoencoder.encoding.base.IEncoder;
import videoencoder.storage.S3Uploader;
import videoencoder.storage.base.IUploader;

/**
 * Servlet implementation class EncodingController
 */
public class EncodingController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	// location to store file uploaded
    private static final String UPLOAD_DIRECTORY = "upload";
 
    // upload settings
    private static final int MEMORY_THRESHOLD   = 1024 * 1024 * 200;  // 3MB
    private static final int MAX_FILE_SIZE      = 1024 * 1024 * 200; // 40MB
    private static final int MAX_REQUEST_SIZE   = 1024 * 1024 * 400; // 50MB
	
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public EncodingController() {
		super();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();

		if (isMultipart) {
		    FileItemFactory factory = new DiskFileItemFactory();
		    ServletFileUpload upload = new ServletFileUpload(factory);

		    try {
		        List items = upload.parseRequest(request);
		        Iterator iterator = items.iterator();
		        while (iterator.hasNext()) {
		            FileItem item = (FileItem) iterator.next();
		            if (!item.isFormField()) {
		                String fileName = item.getName();
		                
		                IUploader uploader = new S3Uploader();
		                String fileUrl = uploader.sendFile(item.getInputStream(), item.getSize());
		                		                
		                if (!fileUrl.isEmpty()) {
		                	IEncoder encoder = new Zencoder();
		                	
		                	out.append("{ \"encodedFile\":" + encoder.encode(fileUrl) + "}");
		                	
		                	response.setStatus(HttpServletResponse.SC_OK);
		                	
		                	return;
		                }
		            }
		        }
		    } catch (Exception e) {}
		    
		    response.setStatus(HttpServletResponse.SC_NO_CONTENT);
	        
	        out.append("{}");
		}
		
    }
}
