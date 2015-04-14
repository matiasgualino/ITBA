package utilities;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

public class RandomGameFileGenerator {
	
	public static void createFile(int rows, int cols, int minValue, int maxValue) throws FileNotFoundException, UnsupportedEncodingException{
		PrintWriter writer = new PrintWriter("files/RandomGame.txt", "UTF-8");
		writer.println(Integer.toString(rows));
		writer.println(Integer.toString(cols));
		writer.println("0");
		writer.println("0");
		for (int i = 0; i < rows; i++) {
			StringBuffer aux = new StringBuffer();
			for (int j = 0; j < rows; j++) {
				int rand = minValue + (int)(Math.random() * ((maxValue - minValue) + 1));
				aux.append(Integer.toString(rand));
			}
			writer.println(aux);
		}
		writer.close();
	}
	
}
