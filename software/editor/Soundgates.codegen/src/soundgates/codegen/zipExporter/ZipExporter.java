package soundgates.codegen.zipExporter;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipExporter {

	ZipOutputStream out;
	byte[] buf;

	public ZipExporter(String zipFilename) {
		// Create a buffer for reading the files
		buf = new byte[1024];
		// Create the ZIP file
		try {
			out = new ZipOutputStream(new FileOutputStream(zipFilename));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void zipFile(String filePath, String zipEntry) {
		try {
			// Compress the files
			
			FileInputStream in = new FileInputStream(filePath);
			// Add ZIP entry to output stream.
			out.putNextEntry(new ZipEntry(zipEntry));
			// Transfer bytes from the file to the ZIP file
			int len;
			while ((len = in.read(buf)) > 0) {
				out.write(buf, 0, len);
			}
			// Complete the entry
			out.closeEntry();
			in.close();

		} catch (IOException e) {
		}
	}
	
	public void zipFileIntoFolder(String filePath, String zipEntry, String folder) {
		try {
			// Compress the files
			
			FileInputStream in = new FileInputStream(filePath);
			// Add ZIP entry to output stream.
			out.putNextEntry(new ZipEntry(folder+"/"+zipEntry));
			// Transfer bytes from the file to the ZIP file
			int len;
			while ((len = in.read(buf)) > 0) {
				out.write(buf, 0, len);
			}
			// Complete the entry
			out.closeEntry();
			in.close();

		} catch (IOException e) {
		}
	}

	public void close() {
		try {
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
