package videoencoder.storage.base;

import java.io.File;

public interface IUploader {

	public boolean sendFile(File file);
}
