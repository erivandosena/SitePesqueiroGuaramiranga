package br.net.rwd.website.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class FileParaBytes {

	public static byte[] getFileBytes(File file) {
		ByteArrayOutputStream ous = null;
		InputStream ios = null;
		try {
			byte[] buffer = new byte[4096];
			ous = new ByteArrayOutputStream();
			try {
				ios = new FileInputStream(file);
			} catch (FileNotFoundException ioe) {
				ioe.printStackTrace();
			}
			int read = 0;
			try {
				while ((read = ios.read(buffer)) != -1)
					ous.write(buffer, 0, read);
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		} finally {
			try {
				if (ous != null)
					ous.close();
			} catch (IOException ioe) {
				ioe.getStackTrace();
			}
			try {
				if (ios != null)
					ios.close();
			} catch (IOException ioe) {
				ioe.getStackTrace();
			}
		}
		return ous.toByteArray();
	}

}
