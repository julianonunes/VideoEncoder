package videoencoder.storage;

import static org.junit.Assert.*;

import org.junit.Test;

import videoencoder.storage.base.IUploader;

public class UploaderTest {

	@Test
	public void mustReturnTrueForSuccessfulUpload() {
		IUploader uploader = new S3Uploader();
		
		assertTrue(uploader.sendFile(null));
	}

}
