package Vista;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;

public class DInsertCliente extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField textDNI;
	private JTextField textName;
	private JTextField textApellido;
	private JTextField textEdad;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			DInsertCliente dialog = new DInsertCliente();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public DInsertCliente() {
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel lblDni = new JLabel("DNI");
		lblDni.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblDni.setBounds(50, 35, 120, 25);
		contentPanel.add(lblDni);
		
		textDNI = new JTextField();
		textDNI.setColumns(10);
		textDNI.setBounds(174, 35, 200, 25);
		contentPanel.add(textDNI);
		
		JLabel lblName = new JLabel("Nombre");
		lblName.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblName.setBounds(50, 75, 120, 25);
		contentPanel.add(lblName);
		
		textName = new JTextField();
		textName.setColumns(10);
		textName.setBounds(174, 75, 200, 25);
		contentPanel.add(textName);
		
		JLabel lblApellido = new JLabel("Apellido");
		lblApellido.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblApellido.setBounds(50, 115, 120, 25);
		contentPanel.add(lblApellido);
		
		textApellido = new JTextField();
		textApellido.setColumns(10);
		textApellido.setBounds(174, 115, 200, 25);
		contentPanel.add(textApellido);
		
		JLabel lblEdad = new JLabel("Edad");
		lblEdad.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblEdad.setBounds(50, 151, 120, 25);
		contentPanel.add(lblEdad);
		
		textEdad = new JTextField();
		textEdad.setColumns(10);
		textEdad.setBounds(174, 151, 200, 25);
		contentPanel.add(textEdad);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("Guardar");
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
}
