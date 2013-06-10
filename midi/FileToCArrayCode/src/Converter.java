import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class Converter {

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {

		if (args.length > 0) {
			File file = new File(args[0]);
			FileInputStream fis = new FileInputStream(file);
			byte[] b = new byte[(int) file.length()];
			fis.read(b);
			printCode(b);
			fis.close();
		}
	}

	public static void printCode(byte[] b) {
		StringBuilder sb = new StringBuilder();
		sb.append("byte name[" + b.length + "] = {");
		for (int i = 0; i < b.length; i++) {
			if (i % 15 == 0 && i != 0) {
				sb.append("\n");
			}
			sb.append(b[i] & 0xFF);

			if (i < b.length - 1) {
				sb.append(", ");
			}
		}
		sb.append("};");
		System.out.print(sb.toString());
	}
}
