package Vista;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.border.BevelBorder;
import javax.swing.border.MatteBorder;
import javax.swing.border.SoftBevelBorder;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import Controlador.ConexionMySQL;
import Controlador.SpecialistController;
import Controlador.SpecialityController;
import Modelo.Specialist;
import Modelo.Speciality;
import Modelo.SpecialityHibernate;
import Modelo.UserHibernate;

import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import javax.swing.JInternalFrame;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JComboBox;

public class AdminInsertUser extends JDialog {
	// -------------------------------VARIABLES------------------------

	private JPanel contentPane;
	private JTextField tfDNI;
	private JTextField tfNombre;
	private JTextField tfContraseña;
	private JTextField tfApellido;
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private SessionFactory instancia;
	private Session miSesion;

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		try {
//			AdminInsertUser dialog = new AdminInsertUser();
//			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
//			dialog.setVisible(true);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

	/**
	 * Create the dialog.
	 */
	public AdminInsertUser(SessionFactory instancia, JFrame parent, boolean modal) {
		this.instancia = instancia;
		this.miSesion = instancia.openSession();
		miSesion.beginTransaction();
		setModal(modal);

		// -----------------------COMPONENTES-------------------
		setBounds(0, 0, 460, 550);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPanel.setLayout(null);
		setLocationRelativeTo(null);
		
		JLabel lblDNI = new JLabel("DNI: ");
		lblDNI.setBounds(30, 50, 200, 20);
		lblDNI.setFont(new Font("Tahoma", Font.PLAIN, 18));

		tfDNI = new JTextField();
		tfDNI.setColumns(10);
		tfDNI.setBounds(250, 50, 150, 25);
		tfDNI.setFont(new Font("Tahoma", Font.PLAIN, 18));
		
		JLabel lblNombre = new JLabel("Nombre: ");
		lblNombre.setBounds(30, 100, 200, 20);
		lblNombre.setFont(new Font("Tahoma", Font.PLAIN, 18));
		contentPanel.add(lblNombre);	

		tfNombre = new JTextField();
		tfNombre.setColumns(10);
		tfNombre.setFont(new Font("Tahoma", Font.PLAIN, 18));
		tfNombre.setBounds(250, 100, 150, 25);
		
		JLabel lblApellido = new JLabel("Apellido: ");
		lblApellido.setBounds(30, 150, 200, 20);
		lblApellido.setFont(new Font("Tahoma", Font.PLAIN, 18));

		tfApellido = new JTextField();
		tfApellido.setColumns(10);
		tfApellido.setBounds(250, 150, 150, 25);
		tfApellido.setFont(new Font("Tahoma", Font.PLAIN, 18));
		
		JLabel lblEspecialidad = new JLabel("Especialidad:");
		lblEspecialidad.setBounds(30, 200, 200, 20);
		lblEspecialidad.setFont(new Font("Tahoma", Font.PLAIN, 18));
		
		JComboBox cbEspecialidad = new JComboBox();
		cbEspecialidad.setBounds(250, 200, 150, 25);
		
		JLabel lblContraseña = new JLabel("Contraseña: ");
		lblContraseña.setBounds(30, 250, 200, 20);
		lblContraseña.setFont(new Font("Tahoma", Font.PLAIN, 18));

		tfContraseña = new JTextField();
		tfContraseña.setColumns(10);
		tfContraseña.setBounds(250, 250, 150, 25);
		tfContraseña.setFont(new Font("Tahoma", Font.PLAIN, 18));

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
				AdminInsertUser.this.dispatchEvent(new WindowEvent(AdminInsertUser.this, WindowEvent.WINDOW_CLOSING));
				miSesion.close();
			}
		});
		cancelButton.setActionCommand("Cancel");
		// ------------------------------------------LOGICA-----------------------------------------------

		try {// CARGAR EN EL COMBO BOX LAS ESPECIALIDADES DE LA CLINICA
			String hql = "From SpecialityHibernate";
			Query<SpecialityHibernate> consultaEspe = miSesion.createQuery(hql, SpecialityHibernate.class);
			List<SpecialityHibernate> lista = consultaEspe.getResultList();
			for (SpecialityHibernate x : lista) {
				cbEspecialidad.addItem(x.getEspecialidad());

			}

		} catch (Exception e2) {
			// TODO: handle exception
		}

		getContentPane().add(contentPanel, BorderLayout.CENTER);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{

				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						String dni = tfDNI.getText();
						String nom = tfNombre.getText();
						String ape = tfApellido.getText();
						String contra = tfContraseña.getText();
						String especialidad = cbEspecialidad.getSelectedItem().toString();
						boolean encontrado = false;
						
						try {
							//Comprobamos si todos los campos estan rellenos
							if (dni.isEmpty() || nom.isEmpty() || ape.isEmpty() || contra.isEmpty()
									|| cbEspecialidad.getSelectedItem().toString().equalsIgnoreCase("")) {
								JOptionPane.showMessageDialog(null, "Debes rellenar todos los campos",
										"WARNING_MESSAGE", JOptionPane.WARNING_MESSAGE);
							} else {
								
								// Relaiza la consulta
								String hql = "FROM UserHibernate";
								Query<UserHibernate> consulta = miSesion.createQuery(hql, UserHibernate.class);

								// Guarda los datos en una lista
								List<UserHibernate> results = consulta.getResultList();
								
								//Comprobamos
								for (UserHibernate u : results) {
									if (u.getDni().equalsIgnoreCase(dni)) {
										encontrado = true;
									}
								}

								//Si existe lo notificamos si no lo creamos
								if (encontrado) {
									JOptionPane.showMessageDialog(null, "Ese DNI ya esta ligado a un usuari/a",
											"WARNING_MESSAGE", JOptionPane.WARNING_MESSAGE);
								} else {
									UserHibernate usuario = new UserHibernate(dni, nom, ape, contra, 1);
									SpecialityHibernate sp = miSesion.get(SpecialityHibernate.class,
											cbEspecialidad.getSelectedIndex());
									usuario.setEspecialidad(sp);
									miSesion.save(usuario);
									miSesion.update(sp);
									JOptionPane.showMessageDialog(null, "Nuevo usuario creado");
									miSesion.getTransaction().commit();
									miSesion.close();
									
									setModal(false);
									setVisible(false);
									dispose();
								}
							}

						} catch (Exception e2) {
							// TODO: handle exception
						}
					}
				});

			}
			{// -------------------ADICIONES DE LOS COMPONENTES---------------
				contentPanel.add(okButton);
				contentPanel.add(cancelButton);
				contentPanel.add(cbEspecialidad);
				contentPanel.add(lblEspecialidad);
				contentPanel.add(tfApellido);
				contentPanel.add(tfContraseña);
				contentPanel.add(lblContraseña);
				contentPanel.add(lblApellido);
				contentPanel.add(tfNombre);
				contentPanel.add(tfDNI);
				contentPanel.add(lblDNI);
			}
		}

	}

}
