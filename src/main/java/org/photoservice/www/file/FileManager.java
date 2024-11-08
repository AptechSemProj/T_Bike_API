package org.photoservice.www.file;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;

public interface FileManager {

	String saveFile(String dir, MultipartFile file) throws IOException;

	Path readFile(String dir, String filename);

	boolean deleteFile(String dir, String filename) throws IOException;

	record Configuration(Path root, Map<String, String> directories) {
		@Override
		public Map<String, String> directories() {
			return directories == null ? Map.of() : directories;
		}
	}
}
