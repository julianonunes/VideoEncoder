package videoencoder.storage;

import java.io.File;
import java.util.UUID;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;

import videoencoder.storage.base.IUploader;

public class S3Uploader implements IUploader {

	private static final String BUCKET_NAME = "videoencoder-objects";	
	
	@Override
	public boolean sendFile(File file) {
		AmazonS3 s3Client = new AmazonS3Client(new ProfileCredentialsProvider());
		
		try {
			// Atribuo um UUID aleatório ao enviar para o Amazon S3.
			PutObjectResult result = s3Client.putObject(
					new PutObjectRequest(BUCKET_NAME, UUID.randomUUID().toString(), file));
		
			// Se diferente de nulo, obteve sucesso (true).
			return (result != null);
		
		}
		catch (AmazonServiceException ase) {
            System.out.println("Caught an AmazonServiceException, which " +
            		"means your request made it " +
                    "to Amazon S3, but was rejected with an error response" +
                    " for some reason.");
            System.out.println("Error Message:    " + ase.getMessage());
            System.out.println("HTTP Status Code: " + ase.getStatusCode());
            System.out.println("AWS Error Code:   " + ase.getErrorCode());
            System.out.println("Error Type:       " + ase.getErrorType());
            System.out.println("Request ID:       " + ase.getRequestId());
        } 
		catch (AmazonClientException ace) {
            System.out.println("Caught an AmazonClientException, which " +
            		"means the client encountered " +
                    "an internal error while trying to " +
                    "communicate with S3, " +
                    "such as not being able to access the network.");
            System.out.println("Error Message: " + ace.getMessage());
        }
		
		return false;
		
	}

}
