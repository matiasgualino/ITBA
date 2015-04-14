package PrimerParcial;

public class StaticMethodsHolder {

	public static boolean isBalanced(char[] exp) {
		Stack<String> total = new Stack<String>();
		Stack<String> parentesis = new Stack<String>();
		Stack<String> corchete = new Stack<String>();
		Stack<String> llave = new Stack<String>();
		for (int i = 0; i < exp.length; i++) {
			String aux = String.valueOf(exp[i]);
			if (aux.equals("[")) {
				total.push(aux);
				corchete.push(aux);
			}
			if (aux.equals("(")) {
				total.push(aux);
				parentesis.push(aux);
			}
			if (aux.equals("{")) {
				total.push(aux);
				llave.push(aux);
			}
			if (aux.equals("}")) {
				if (!total.pop().equals("{")) {
					return false;
				}
				llave.pop();
			}
			if (aux.equals(")")) {
				if (!total.pop().equals("(")) {
					return false;
				}
				parentesis.pop();
			}
			if (aux.equals("]")) {
				if (!total.pop().equals("[")) {
					return false;
				}
				corchete.pop();
			}
		}
		return (total.isEmpty() && parentesis.isEmpty() && corchete.isEmpty() && llave.isEmpty());
	}

}
