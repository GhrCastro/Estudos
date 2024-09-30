import java.io.*;
import java.util.Formatter;
import java.util.Scanner;
import java.io.File;
import java.nio.charset.*;


public class arqJava {
	public final static String textEncode = "ISO-8859-1";
	public static File file = new File("numsToRead.txt");
	public static RandomAccessFile raf;

	public static void main(String[] args) {

		int n = MyIO.readInt();
		writeAndCloseFile(n);

		openAndPrintFile(n);

	}

	private static void openAndPrintFile(int n) {
		try {
			raf = new RandomAccessFile("numsToRead.txt", "r");
			// calcula o deslocamento para o ultimo double do arquivo
			long posicao = (n - 1) * 8; // cada double ocupa 8 bytes
			for (int i = 0; i < n; i++) {
				raf.seek(posicao - (i * 8));

				double value = raf.readDouble();

				if (value == (int) value) {
					System.out.println((int) value);
				} else {
					System.out.println(value);
				}
			}
			raf.close();
		} catch (Exception e) {
			System.out.println("Erro ao ler o arquivo: " + e.getMessage());
		}
	}

	private static void writeAndCloseFile(int n) {
		try {
			raf = new RandomAccessFile("numsToRead.txt", "rw");
			for (int i = 0; i < n; i++) {
				double value = MyIO.readDouble();
				raf.writeDouble(value);
			}
			raf.close();
		} catch (IOException e) {
			System.out.println("Erro ao escrever no arquivo: " + e.getMessage());
		}
	}
}