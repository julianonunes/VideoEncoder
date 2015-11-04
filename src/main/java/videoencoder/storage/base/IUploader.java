package videoencoder.storage.base;

import java.io.File;
import java.io.InputStream;

public interface IUploader {

	public boolean sendFile(InputStream stream, Long contentLength);
}
