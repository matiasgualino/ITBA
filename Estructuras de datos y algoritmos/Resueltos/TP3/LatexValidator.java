package TP3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Deque;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.xml.bind.ValidationException;

public class LatexValidator {
	private Pattern pattern = Pattern.compile("\\\\(begin|end)\\{(\\w+)\\}");
	private BufferedReader reader;

	public LatexValidator(String fileName) throws IOException {
		reader = new BufferedReader(new FileReader(fileName));
	}

	public void validate() throws IOException, ValidationException {
		Deque<String> stack = new LinkedList<String>();
		String line, command, name;
		while ((line = reader.readLine()) != null) {
			Matcher matcher = pattern.matcher(line);
			while (matcher.find()) {
				command = matcher.group(1);
				name = matcher.group(2);
				if (command.equals("begin")) {
					stack.push(name);
				} else {
					if (stack.isEmpty()) {
						throw new ValidationException("End sin un begin.");
					}
					if (!stack.pop().equals(name)) {
						throw new ValidationException(
								"End de un entorno incorrecto.");
					}
				}
			}
		}
		if (!stack.isEmpty()) {
			throw new ValidationException("Falta cerrar entornos.");
		}
	}
	
}
