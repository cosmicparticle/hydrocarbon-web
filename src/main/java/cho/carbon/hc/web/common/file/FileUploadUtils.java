package cho.carbon.hc.web.common.file;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.FileCopyUtils;

public class FileUploadUtils {
	private final String absPath;
	private final String folderUri;
	Logger logger = LoggerFactory.getLogger(FileUploadUtils.class);
	
	
	public FileUploadUtils(String absPath, String folderUri) {
		this.absPath = absPath;
		this.folderUri = folderUri;
	}
	
	public String saveFile(String fileName, InputStream in) throws IOException{
		File file = new File(absPath + "/" + fileName);
		File folder = file.getParentFile();
		if(!folder.exists()){
			folder.mkdirs();
		}
		file.createNewFile();
		FileOutputStream fo = new FileOutputStream(file);
		FileCopyUtils.copy(in, fo);
		return folderUri + "/" + fileName;
	}


	public String getFolderUri() {
		return this.folderUri;
	}
	
	

}
