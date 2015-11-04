package videoencoder.storage.base;

import java.io.InputStream;

public interface IUploader {

	public String sendFile(InputStream stream, Long contentLength);
}
