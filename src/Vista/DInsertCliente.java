package Vista;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import Modelo.CitaHibernate;
import Modelo.ClienteHibernate;
import Modelo.OdontogramaHibernate;
import Modelo.SpecialityHibernate;
import Modelo.TreatmentsHibernate;
import Modelo.UserHibernate;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.List;
import java.awt.event.ActionEvent;

public class DInsertCliente extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField textDNI;
	private JTextField textName;
	private JTextField textApellido;
	private JTextField textEdad;

	private SessionFactory instancia;
	private Session session;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public DInsertCliente(String dni, String name, String apellido, String edad, boolean edit) {
		this.instancia = (SessionFactory) new Configuration().configure("hibernate.cfg.xml")
				.addAnnotatedClass(UserHibernate.class).addAnnotatedClass(CitaHibernate.class)
				.addAnnotatedClass(TreatmentsHibernate.class).addAnnotatedClass(ClienteHibernate.class)
				.addAnnotatedClass(SpecialityHibernate.class).addAnnotatedClass(OdontogramaHibernate.class).
				buildSessionFactory();
		this.session = instancia.openSession();

		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		JLabel lblDni = new JLabel("DNI");
		lblDni.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblDni.setBounds(50, 35, 120, 25);
		if (edit == true) {
			lblDni.setEnabled(false);
		}
		contentPanel.add(lblDni);

		textDNI = new JTextField();
		textDNI.setColumns(10);
		textDNI.setBounds(174, 35, 200, 25);
		textDNI.setText(dni);
		if (edit == true) {
			textDNI.setEnabled(false);
		}
		contentPanel.add(textDNI);

		JLabel lblName = new JLabel("Nombre");
		lblName.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblName.setBounds(50, 75, 120, 25);
		contentPanel.add(lblName);

		textName = new JTextField();
		textName.setColumns(10);
		textName.setBounds(174, 75, 200, 25);
		textName.setText(name);
		contentPanel.add(textName);

		JLabel lblApellido = new JLabel("Apellido");
		lblApellido.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblApellido.setBounds(50, 115, 120, 25);
		contentPanel.add(lblApellido);

		textApellido = new JTextField();
		textApellido.setColumns(10);
		textApellido.setBounds(174, 115, 200, 25);
		textApellido.setText(apellido);
		contentPanel.add(textApellido);

		JLabel lblEdad = new JLabel("Edad");
		lblEdad.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblEdad.setBounds(50, 151, 120, 25);
		contentPanel.add(lblEdad);

		textEdad = new JTextField();
		textEdad.setColumns(10);
		textEdad.setBounds(174, 151, 200, 25);
		textEdad.setText(edad);
		contentPanel.add(textEdad);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("Guardar");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if (isNumeric(textEdad.getText()) == true) {
							Query<ClienteHibernate> consultaClienteExiste = session.createQuery(
									"FROM ClienteHibernate where dni_cliente=:dni "
											+ "and nombre=:nombre and apellidos=:apellidos and edad=:edad",
									ClienteHibernate.class);
//							if(edit == true) {
//								session.beginTransaction();
//								ClienteHibernate cliente = new ClienteHibernate(dni, name, apellido, Integer.parseInt(edad));
//								session.delete(cliente);
//								session.getTransaction().commit();
////								Query query = session.createQuery("delete ClienteHibernate where dni_cliente =:dni");
////								query.setParameter("dni", dni);
////								session.beginTransaction();
////								query.executeUpdate();
////								session.getTransaction().commit();
//							}
							consultaClienteExiste.setParameter("dni", textDNI.getText());
							consultaClienteExiste.setParameter("nombre", textName.getText());
							consultaClienteExiste.setParameter("apellidos", textApellido.getText());
							consultaClienteExiste.setParameter("edad", Integer.parseInt(textEdad.getText()));
							List<ClienteHibernate> check = consultaClienteExiste.getResultList();
							System.out.println(textEdad.getText().matches("[0-9]+"));
							if (check.isEmpty()) {
								ClienteHibernate cliente = new ClienteHibernate(textDNI.getText(), textName.getText(),
										textApellido.getText(), Integer.parseInt(textEdad.getText()));
								if (edit == false) {
									session.beginTransaction();
									session.save(cliente);
									session.getTransaction().commit();
								}else {
									session.beginTransaction();
									session.update(cliente);
									session.getTransaction().commit();
								}
								System.out.println(
										"------------------------------------------------------------------------------------------------------------------------------------------");
								setModal(false);
								setVisible(false);
								System.out.println("kakakkakak");
								dispose();
								DInsertCliente.this.dispatchEvent(
										new WindowEvent(DInsertCliente.this, WindowEvent.WINDOW_CLOSING));
							} else {
								JOptionPane.showMessageDialog(null, "Este cliente ya se encuentra registrado");
							}
						} else {
							JOptionPane.showMessageDialog(null, "Por favor, introduzca un valor numerico para la edad");
						}
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						setModal(false);
						setVisible(false);
						DInsertCliente.this
								.dispatchEvent(new WindowEvent(DInsertCliente.this, WindowEvent.WINDOW_CLOSING));
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}

	}

	public static boolean isNumeric(String s) {
		if (s == null || s.equals("")) {
			return false;
		}

		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (c < '0' || c > '9') {
				return false;
			}
		}
		return true;
	}
}
