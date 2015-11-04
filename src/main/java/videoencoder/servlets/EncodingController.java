package videoencoder.servlets;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import videoencoder.encoding.Zencoder;
import videoencoder.encoding.base.IEncoder;
import videoencoder.storage.S3Uploader;
import videoencoder.storage.base.IUploader;

/**
 * Servlet implementation class EncodingController
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
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
	    
	    final Part filePart = request.getPart("file");

	    InputStream filecontent = null;

	    filecontent = filePart.getInputStream();

        IUploader uploader = new S3Uploader();
        String fileUrl = uploader.sendFile(filecontent, filePart.getSize());
        
        if (filecontent != null) {
            filecontent.close();
        }
        
        if (!fileUrl.isEmpty()) {
        	IEncoder encoder = new Zencoder();
        	
        	out.append("{ 'encodedFile':" + encoder.encode(fileUrl) + "}");
        	
        	response.setStatus(HttpServletResponse.SC_OK);
        }
        
        response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        
        out.append("{}");
	}

}
