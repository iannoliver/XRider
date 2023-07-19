package utils;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;


//PlainDocument - recursos para formatação 
public class Validador extends PlainDocument {
	//variavel que ira armazenar o número máximo de caracteres permitidos
	private int limite;
	//construtor personalizado -> será usado pelas caixas de texto JTextField

	public Validador(int limite) {
		super();
		this.limite = limite;
	}
	//Método interno para validar o limite de caracteres
	//BadLocation -  tratamento de exceções 
	public void insertString(int ofs, String str, AttributeSet a) throws BadLocationException {
		//importar o javax.swing.text.AttributeSet; ao invés do print
		//se o limite não for ultrapassado, permitir a digitação
		if ((getLength() + str.length()) <= limite) {
			super.insertString(ofs, str, a);
		}
	}
	

}
