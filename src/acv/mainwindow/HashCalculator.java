package acv.mainwindow;

import java.io.File;
import java.io.FileInputStream;
import java.security.MessageDigest;
import java.util.zip.CRC32;
import java.util.zip.Adler32;
import javax.swing.SwingWorker;

/**
 *
 * @author andre
 */
public class HashCalculator extends SwingWorker<HashInfo, Void> {

	private final static int KILO = 1024;
	private final static int MEGA = KILO * KILO;
	private final static int GIGA = MEGA * KILO;

	public final static String PROPERTY_PROGRESS_VALUE = "progress";
	public final static String PROPERTY_PROGRESS_STRING = "progressString";

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public void setAlgorithm(Algorithm algorithm) {
		this.algorithm = algorithm;
	}

	public void setMainWindow(MainWindow mainWindow) {
		this.mainWindow = mainWindow;
	}

	@Override
	protected HashInfo doInBackground() throws Exception {
		firePropertyChange(PROPERTY_PROGRESS_STRING, "", "0%");
		setProgress(0);
		HashInfo hashInfo = new HashInfo();
		hashInfo.setAlgorithm(algorithm.getDescription());
		File file = new File(fileName);
		hashInfo.setFilename(file.getName());

		long fileSize = file.length();
		String sizeText = "";
		if (fileSize < KILO) {
			sizeText = String.format("%d", fileSize);
		} else if (fileSize < MEGA) {
			sizeText = String.format("%s Kb",
					numberToString(((double) fileSize) / KILO, 0, 2));
		} else if (fileSize < GIGA) {
			sizeText = String.format("%s Mb",
					numberToString(((double) fileSize) / MEGA, 0, 2));
		} else {
			sizeText = String.format("%s Gb",
					numberToString(((double) fileSize) / GIGA, 0, 2));
		}
		hashInfo.setFilesize(sizeText);

		long totalRead = 0;
		try (FileInputStream inputStream = new FileInputStream(file)) {
			CRC32 crc32 = null;
			Adler32 adler32 = null;
			MessageDigest digest = null;

			if (algorithm.isCrc32()) {
				crc32 = new CRC32();
			} else if (algorithm.isAdler32()) {
				adler32 = new Adler32();
			} else {
				digest = MessageDigest.getInstance(algorithm.getId());
			}

			byte[] bytesBuffer = new byte[1024];
			int bytesRead = -1;

			while ((bytesRead = inputStream.read(bytesBuffer)) != -1) {
				if (algorithm.isCrc32()) {
					crc32.update(bytesBuffer, 0, bytesRead);
				} else if (algorithm.isAdler32()) {
					adler32.update(bytesBuffer, 0, bytesRead);
				} else {
					digest.update(bytesBuffer, 0, bytesRead);
				}
				totalRead += bytesRead;
				int percent = (int) (totalRead * 100 / fileSize);
				setProgress(percent);
				firePropertyChange(PROPERTY_PROGRESS_STRING, "",
						String.format("%d%%", percent));
			}

			if (algorithm.isCrc32()) {
				long crc = crc32.getValue();
				hashInfo.setHash(String.valueOf(crc));
			} else if (algorithm.isAdler32()) {
				long value = adler32.getValue();
				hashInfo.setHash(String.valueOf(value));
			} else {
				byte[] hashedBytes = digest.digest();
				hashInfo.setHash(convertByteArrayToHexString(hashedBytes));
			}
			return hashInfo;
		} catch (Exception ex) {
			throw ex;
		}
	}

	@Override
	protected void done() {
		super.done();
		mainWindow.hashCalculated();
	}

	private static String convertByteArrayToHexString(byte[] arrayBytes) {
		StringBuilder stringBuffer = new StringBuilder();
		for (int i = 0; i < arrayBytes.length; i++) {
			stringBuffer.append(Integer.toString((arrayBytes[i] & 0xff) + 0x100, 16)
					.substring(1));
		}
		return stringBuffer.toString();
	}

	private String numberToString(int number, int digit) {
		StringBuilder s = new StringBuilder(String.valueOf(number));
		while (s.length() < digit) {
			s.insert(0, "0");
		}
		return s.toString();
	}

	private String numberToString(double number, int digit, int scale) {
		int intNumber = (int) number;
		double part = number - intNumber;
		StringBuilder s = new StringBuilder(numberToString(intNumber, digit));
		s.append('.');

		for (int i = 1; i <= scale; i++) {
			part *= 10;
		}
		intNumber = (int) Math.round(part);
		s.append(numberToString(intNumber, scale));
		return s.toString();
	}

	private String fileName;
	private Algorithm algorithm;
	private MainWindow mainWindow;
}
