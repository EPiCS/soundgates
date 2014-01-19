package soundgates.codegen.zipExporter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipExporter {

	public static void zipFiles(String zipFilename,
			LinkedList<String> sourcesFilenames) {
		// Create a buffer for reading the files
		byte[] buf = new byte[1024];

		try {
			// Create the ZIP file
			ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipFilename));
			// Compress the files
			for (String filename : sourcesFilenames) {
				FileInputStream in = new FileInputStream(filename);
				// Add ZIP entry to output stream.
				File file = new File(filename); // "Users/you/image.jpg"
				out.putNextEntry(new ZipEntry(file.getName()));
				// Transfer bytes from the file to the ZIP file
				int len;
				while ((len = in.read(buf)) > 0) {
					out.write(buf, 0, len);
				}
				// Complete the entry
				out.closeEntry();
				in.close();
			} // Complete the ZIP file
			out.close();

		} catch (IOException e) {
		}
	}
}
