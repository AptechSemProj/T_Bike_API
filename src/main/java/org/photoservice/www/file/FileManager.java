package org.photoservice.www.file;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;

public interface FileManager {

	String saveFile(String dir, MultipartFile file) throws IOException;

	Path readFile(String dir, String filename);

	boolean deleteFile(String dir, String filename) throws IOException;
}
