package Otros;

import javax.swing.JRadioButton;

public class RadioButton extends JRadioButton{
	// Atributos
	private String id;

	//Constructores
	public RadioButton() {
		super();
	}
	
	public RadioButton(String text) {
		super();
		this.setText(text);
	}

	public RadioButton(String text, String id) {
		super();
		this.setText(text);
		this.id = id;
	}
	//Métodos
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
}
