package Vista;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import Modelo.SpecialityHibernate;
import Modelo.UserHibernate;

public class ChangeUser extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JPanel contentPane;
	private JTextField tfNombre;
	private JTextField tfContraseña;
	private JTextField tfApellido;
	private SessionFactory instancia;
	private Session miSesion;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {

	}

	/**
	 * Create the dialog.
	 */
	public ChangeUser(AdminUsers parent, boolean modal) {

		this.instancia = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(UserHibernate.class)
				.addAnnotatedClass(SpecialityHibernate.class).buildSessionFactory();
		this.miSesion = instancia.openSession();
		miSesion.beginTransaction();
		setModal(modal);
		// -----------------------COMPONENTES-------------------

		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPanel.setLayout(null);

		
		JLabel lblDNI = new JLabel("DNI");
		lblDNI.setBounds(20, 13, 18, 14);

		JLabel lblNombre = new JLabel("Nombre");
		lblNombre.setBounds(20, 82, 37, 14);
		contentPanel.add(lblNombre);

		tfNombre = new JTextField();
		tfNombre.setColumns(10);
		tfNombre.setBounds(67, 79, 86, 20);

		JLabel lblContraseña = new JLabel("Contraseña");
		lblContraseña.setBounds(218, 13, 56, 14);

		tfContraseña = new JTextField();
		tfContraseña.setColumns(10);
		tfContraseña.setBounds(311, 10, 86, 20);

		JLabel lblApellido = new JLabel("Apellido");
		lblApellido.setBounds(218, 82, 37, 14);

		tfApellido = new JTextField();
		tfApellido.setColumns(10);
		tfApellido.setBounds(311, 79, 86, 20);

		JLabel lblEspecialidad = new JLabel("Especialidad");
		lblEspecialidad.setBounds(136, 155, 58, 14);

		JButton okButton = new JButton("OK");
		okButton.setActionCommand("OK");
		getRootPane().setDefaultButton(okButton);
		
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setModal(false);
				setVisible(false);
				ChangeUser.this.dispatchEvent(new WindowEvent(
						ChangeUser.this, WindowEvent.WINDOW_CLOSING));
			}
		});
		cancelButton.setActionCommand("Cancel");
		
		// comboboxes de Especialidad y DNIS
		JComboBox cbEspecialidad = new JComboBox();
		cbEspecialidad.setBounds(218, 152, 86, 20);

		cbEspecialidad.addItem("");

		JComboBox cbDni = new JComboBox();
		cbDni.setBounds(67, 10, 86, 20);

		cbDni.addItem("");
		//--------------------------LOGICA--------------------------

		// cargar comobox

		try {// CARGAR EN EL COMBO BOX LAS ESPECIALIDADES DE LA CLINICA
			String hql = "From SpecialityHibernate";
			Query<SpecialityHibernate> consultaEspe = miSesion.createQuery(hql, SpecialityHibernate.class);
			List<SpecialityHibernate> lista = consultaEspe.getResultList();
			// se borra el primer elemento para evitar que al admin se le quite el permiso
			// del admin al modificar
			lista.removeFirst();
			for (SpecialityHibernate x : lista) {
				cbEspecialidad.addItem(x);

			}
		} catch (Exception e2) {
			// TODO: handle exception
		}

		try {
			String hql2 = "From UserHibernate";
			Query<UserHibernate> consultaDni = miSesion.createQuery(hql2, UserHibernate.class);
			List<UserHibernate> listaUsuarios = consultaDni.getResultList();
			for (UserHibernate y : listaUsuarios) {
				cbDni.addItem(y.getDni());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//BUtton panel
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);
		{
			
			okButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					String dni = cbDni.getSelectedItem().toString();
					String nom = tfNombre.getText();
					String ape = tfApellido.getText();
					String contra = tfContraseña.getText();
					String especialidad = cbEspecialidad.getSelectedItem().toString();
					
					
				
					try {
						if(dni.isEmpty() || nom.isEmpty() || ape.isEmpty() || contra.isEmpty() || cbEspecialidad.getSelectedItem().toString().equalsIgnoreCase("") ) {
							JOptionPane.showMessageDialog(null,"Debes rellenar todos los campos", "WARNING_MESSAGE",JOptionPane.WARNING_MESSAGE);
						}else {
				                	UserHibernate usuario=new UserHibernate(dni,nom,ape,contra,1);
				                	miSesion.save(usuario);
				                	JOptionPane.showMessageDialog(null," USUARIO MODIFICADO");
				                	miSesion.getTransaction().commit();
				                	miSesion.close();
				                }
				            
							
						
							
							setModal(false);
							setVisible(false);
							dispose();
//							AdminInsertUser.this.dispatchEvent(new WindowEvent(
//									AdminInsertUser.this, WindowEvent.WINDOW_CLOSING));
						
						
					} catch (Exception e2) {
						// TODO: handle exception
					}
				}
			});
			
		
		}
		// ----------------------------ADICIONES-----------------------------
		
		
		contentPanel.add(cbDni);
		contentPanel.add(cbEspecialidad);
		contentPanel.add(lblEspecialidad);
		contentPanel.add(tfApellido);
		contentPanel.add(tfContraseña);
		contentPanel.add(lblContraseña);
		contentPanel.add(lblApellido);
		contentPanel.add(tfNombre);
		contentPanel.add(lblDNI);
	}

	//método para poner los datos de las especialidades en los textField
	public static void cargarDatos(String Dni,Session miSesion,JTextField nombre,JTextField apell) {
		String hql="From UserHibernate where dni=:Dni";
		Query<UserHibernate>consulta=miSesion.createQuery(hql,UserHibernate.class);
		consulta.setParameter("Dni", Dni);
		UserHibernate u=consulta.getSingleResult();
		nombre.setText(u.getNombre());
		apell.setText(u.getApellido());
		
	}
}
