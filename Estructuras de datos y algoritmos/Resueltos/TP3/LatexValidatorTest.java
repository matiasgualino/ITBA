package TP3;

import java.io.IOException;

import javax.xml.bind.ValidationException;

public class LatexValidatorTest {

	public static void main(String[] args) {
		try {
			LatexValidator validator = new LatexValidator(args[0]);
			validator.validate();
			System.out.println("Archivo correcto.");
		} catch (ValidationException e) {
			System.out.println("Archivo incorrecto: " + e);
		} catch (IOException e) {
			System.out.println("Error al abrir archivo: " + args[0]);
		} catch (ArrayIndexOutOfBoundsException e){
			System.out.println("No se ingreso archivo");
		}
	}
	
}
