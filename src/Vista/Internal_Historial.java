package Vista;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JTextArea;

public class Internal_Historial extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	/*
	 id_odontograma
	 id_cliente
	 observaciones
	 fecha
	 clientes_dni_clientes*/

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			Internal_Historial dialog = new Internal_Historial();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public Internal_Historial() {
		setBounds(0, 0, 506, 680);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel lblidDiente = new JLabel("Diiente: ");
			lblidDiente.setBounds(31, 125, 46, 14);
			contentPanel.add(lblidDiente);
		}
		
		JLabel lblFecha = new JLabel("Fecha");
		lblFecha.setBounds(272, 125, 46, 14);
		contentPanel.add(lblFecha);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(328, 121, 30, 22);
		contentPanel.add(comboBox);
		
		JLabel lblObservaciones = new JLabel("Tratamiento: ");
		lblObservaciones.setBounds(29, 224, 79, 14);
		contentPanel.add(lblObservaciones);
		
		JTextArea textArea = new JTextArea();
		textArea.setBounds(29, 249, 436, 288);
		contentPanel.add(textArea);
		
		JLabel lblNombrePaciente = new JLabel("NOMBRE");
		lblNombrePaciente.setBounds(31, 34, 192, 14);
		contentPanel.add(lblNombrePaciente);
		
		JLabel lblimagenDiente = new JLabel("Imagen");
		lblimagenDiente.setBounds(304, 34, 66, 54);
		contentPanel.add(lblimagenDiente);
		{
			JPanel bpBotonera = new JPanel();
			bpBotonera.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(bpBotonera, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("Imprimir");
				okButton.setActionCommand("OK");
				bpBotonera.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Salir");
				cancelButton.setActionCommand("Cancel");
				bpBotonera.add(cancelButton);
			}
		}
	}
}
