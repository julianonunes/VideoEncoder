package videoencoder.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
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
