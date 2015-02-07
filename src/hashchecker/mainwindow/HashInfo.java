package hashchecker.mainwindow;

import java.io.File;

/**
 *
 * @author andre
 */
public class HashInfo {

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getAlgorithm() {
		return algorithm;
	}

	public void setAlgorithm(String algorithm) {
		this.algorithm = algorithm;
	}

	public String getFilesize() {
		return filesize;
	}

	public void setFilesize(String filesize) {
		this.filesize = filesize;
	}

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	public String createFileContent() {
		StringBuilder sb = new StringBuilder();
		sb.append( String.format("Имя файла: %s\n", filename) );
		sb.append( String.format("Размер файла: %s\n", filesize) );
		sb.append( String.format("Алгоритм: %s\n", algorithm) );
		sb.append( String.format("Хэш-сумма: %s\n", hash) );
		
		return sb.toString();
	}
	
	private String filename = "";
	private String filesize = "";
	private String algorithm = "";
	private String hash = "";
}
