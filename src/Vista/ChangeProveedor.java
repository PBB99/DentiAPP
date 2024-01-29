package Vista;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import Modelo.ProveedorHibernate;
import Modelo.SpecialityHibernate;
import Modelo.UserHibernate;

public class ChangeProveedor extends JDialog {

	private static final long serialVersionUID = 1L;

	private JComboBox cbEstado = new JComboBox();
	private JTextField tfCif;
	private JTextField tfNombre;
	private JTextField tfCorreo;
	private SessionFactory instancia;
	private Session miSesion;
	private final JPanel contentPanel = new JPanel();
	private ProveedorHibernate prh;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ChangeProveedor dialog = new ChangeProveedor(null, null);
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the dialog.
	 */
	public ChangeProveedor(SessionFactory instancia, ProveedorHibernate prh) {

		this.instancia = instancia;
		//this.miSesion = instancia.openSession();
		this.prh = prh;

		cbEstado.addItem("Desactivado");
		cbEstado.addItem("Activo");

		setModal(true);
		setContentPane(contentPanel);

		// -----------------------COMPONENTES-------------------

		setBounds(0, 0, 460, 550);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPanel.setLayout(null);
		setLocationRelativeTo(null);

		JLabel lblCif = new JLabel("CIF:");
		lblCif.setBounds(30, 50, 200, 20);
		lblCif.setFont(new Font("Tahoma", Font.PLAIN, 18));

		tfCif = new JTextField();
		tfCif.setColumns(10);
		tfCif.setFont(new Font("Tahoma", Font.PLAIN, 18));
		tfCif.setBounds(150, 50, 250, 25);

		JLabel lblNombre = new JLabel("Nombre:");
		lblNombre.setBounds(30, 100, 200, 20);
		lblNombre.setFont(new Font("Tahoma", Font.PLAIN, 18));

		tfNombre = new JTextField();
		tfNombre.setColumns(10);
		tfNombre.setFont(new Font("Tahoma", Font.PLAIN, 18));
		tfNombre.setBounds(150, 100, 250, 25);

		JLabel lblEstado = new JLabel("Estado:");
		lblEstado.setBounds(30, 150, 200, 20);
		lblEstado.setFont(new Font("Tahoma", Font.PLAIN, 18));

		cbEstado.setBounds(150, 150, 250, 25);
		cbEstado.setFont(new Font("Tahoma", Font.PLAIN, 18));

		JLabel lblCorreo = new JLabel("Correo:");
		lblCorreo.setBounds(30, 200, 200, 20);
		lblCorreo.setFont(new Font("Tahoma", Font.PLAIN, 18));

		tfCorreo = new JTextField();
		tfCorreo.setColumns(10);
		tfCorreo.setBounds(150, 200, 250, 25);
		tfCorreo.setFont(new Font("Tahoma", Font.PLAIN, 18));

		JButton okButton = new JButton("OK");
		okButton.setBounds(70, 450, 125, 25);
		okButton.setFont(new Font("Tahoma", Font.PLAIN, 18));
		okButton.setActionCommand("OK");
		getRootPane().setDefaultButton(okButton);
		okButton.setMnemonic(KeyEvent.VK_ENTER);

		JButton cancelButton = new JButton("Cancel");
		cancelButton.setBounds(265, 450, 125, 25);
		cancelButton.setFont(new Font("Tahoma", Font.PLAIN, 18));
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setModal(false);
				setVisible(false);
				ChangeProveedor.this.dispatchEvent(new WindowEvent(ChangeProveedor.this, WindowEvent.WINDOW_CLOSING));
			}
		});
		cancelButton.setActionCommand("Cancel");

		// --------------------------LOGICA--------------------------

		// Acción de guardar
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// variable de control
				boolean isValid = true;

				// Nos aseguramos que rellena todos los campos
				if ((tfNombre.getText().isBlank() || tfCif.getText().isBlank() || tfCorreo.getText().isBlank())) {
					JOptionPane.showMessageDialog(contentPanel, "Debe rellenar todos los campos", "Error",
							JOptionPane.ERROR_MESSAGE);
					isValid = false;
				}

				if (isValid) {
					// Modifica el tratamiento
					prh.setNombre(tfNombre.getText());
					prh.setCif(tfCif.getText());
					prh.setCorreo(tfCorreo.getText());

					if (cbEstado.getSelectedIndex() == 0) {
						prh.setEstado(0);
					} else {
						prh.setEstado(1);
					}
				}

				// Realizamos la operación de update
				if (isValid) {
					miSesion.beginTransaction();
					miSesion.update(prh);
					miSesion.getTransaction().commit();

					// Cerramos
					//miSesion.close();
					dispose();

				}
			}
		});

		// ----------------------------ADICIONES-----------------------------

		contentPanel.add(lblCorreo);
		contentPanel.add(tfCorreo);
		contentPanel.add(lblEstado);
		contentPanel.add(tfNombre);
		contentPanel.add(lblCif);
		contentPanel.add(okButton);
		contentPanel.add(cancelButton);
		contentPanel.add(lblNombre);
		contentPanel.add(tfCif);
		contentPanel.add(cbEstado);

	}

	// ----------------------------Métodos-----------------------------
	// -------------------- Métodos y Funciones --------------------
	// Poner un cif
	public void setTextCif(String cif) {
		tfCif.setText(cif);
	}

	// Poner un nombre
	public void setTextNombre(String nombre) {
		tfNombre.setText(nombre);
	}

	// Poner un correo
	public void setTextCorreo(String correo) {
		tfCorreo.setText(correo);
	}

	// Poner un estado
	public void setEstado(int estado) {
		if (estado == 0) {
			cbEstado.setSelectedIndex(0);
		} else {
			cbEstado.setSelectedIndex(1);
		}
	}

	// Pasar la sesion
	public void setSession(Session session) {
		miSesion = session;
	}
}
