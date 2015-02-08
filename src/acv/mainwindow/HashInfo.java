package acv.mainwindow;

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
		String lineSeparator = System.getProperty("line.separator");
		sb.append(String.format("File name: %s %s", filename, lineSeparator));
		sb.append(String.format("File size: %s %s", filesize, lineSeparator));
		sb.append(String.format("Algorithm: %s %s", algorithm, lineSeparator));
		sb.append(String.format("Checksum: %s %s", hash, lineSeparator));

		return sb.toString();
	}

	private String filename = "";
	private String filesize = "";
	private String algorithm = "";
	private String hash = "";
}
