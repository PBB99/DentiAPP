package Vista;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
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
import org.hibernate.cfg.Configuration;

import Modelo.SpecialityHibernate;
import Modelo.UserHibernate;

public class ChangeUser extends JDialog {

	private static final long serialVersionUID = 1L;

	private JTextField tfNombre;
	private JTextField tfContraseña;
	private JTextField tfApellido;
	private SessionFactory instancia;
	private Session miSesion;
	private final JPanel contentPanel = new JPanel();
	private List<SpecialityHibernate> lista;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {

	}

	/**
	 * Create the dialog.
	 */
	public ChangeUser(SessionFactory instancia, JFrame parent, boolean modal) {

		this.instancia = instancia;
		this.miSesion = instancia.openSession();

		setModal(modal);
		setContentPane(contentPanel);

		// -----------------------COMPONENTES-------------------

		setBounds(0, 0, 460, 550);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPanel.setLayout(null);
		setLocationRelativeTo(null);

		JLabel lblDNI = new JLabel("DNI:");
		lblDNI.setBounds(30, 50, 200, 20);
		lblDNI.setFont(new Font("Tahoma", Font.PLAIN, 18));
		
		JLabel lblEstado = new JLabel("Estado:");
		lblEstado.setBounds(30, 350, 165, 30);
		lblEstado.setFont(new Font("Tahoma", Font.PLAIN, 18));

		JComboBox cbDni = new JComboBox();
		cbDni.setBounds(250, 50, 150, 25);
		cbDni.setFont(new Font("Tahoma", Font.PLAIN, 18));
		cbDni.addItem("");

		JLabel lblNombre = new JLabel("Nombre:");
		lblNombre.setBounds(30, 100, 200, 20);
		lblNombre.setFont(new Font("Tahoma", Font.PLAIN, 18));
		contentPanel.add(lblNombre);

		tfNombre = new JTextField();
		tfNombre.setColumns(10);
		tfNombre.setFont(new Font("Tahoma", Font.PLAIN, 18));
		tfNombre.setBounds(250, 100, 150, 25);

		JLabel lblApellido = new JLabel("Apellido:");
		lblApellido.setBounds(30, 150, 200, 20);
		lblApellido.setFont(new Font("Tahoma", Font.PLAIN, 18));

		tfApellido = new JTextField();
		tfApellido.setColumns(10);
		tfApellido.setBounds(250, 150, 150, 25);
		tfApellido.setFont(new Font("Tahoma", Font.PLAIN, 18));

		JLabel lEspeAct = new JLabel("");
		lEspeAct.setBounds(250, 200, 200, 25);

		JLabel lblEspecialidad = new JLabel("Especialidad:");
		lblEspecialidad.setBounds(30, 200, 200, 20);
		lblEspecialidad.setFont(new Font("Tahoma", Font.PLAIN, 18));

		JLabel lNuevaEsp = new JLabel("Nueva Especialidad:");
		lNuevaEsp.setBounds(30, 250, 200, 20);
		lNuevaEsp.setFont(new Font("Tahoma", Font.PLAIN, 18));
		
		JComboBox cbEspecialidad = new JComboBox();
		cbEspecialidad.setBounds(250, 250, 150, 25);
		
		JComboBox cbEstado = new JComboBox();
		cbEstado.setBounds(250, 357, 150, 22);
		//condicion
		
		JLabel lblContraseña = new JLabel("Contraseña:");
		lblContraseña.setBounds(30, 300, 200, 20);
		lblContraseña.setFont(new Font("Tahoma", Font.PLAIN, 18));

		tfContraseña = new JTextField();
		tfContraseña.setColumns(10);
		tfContraseña.setBounds(250, 300, 150, 25);
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
				ChangeUser.this.dispatchEvent(new WindowEvent(
						ChangeUser.this, WindowEvent.WINDOW_CLOSING));
			}
		});
		cancelButton.setActionCommand("Cancel");

		// --------------------------LOGICA--------------------------

		// cargar comobox

		try {// CARGAR EN EL COMBO BOX LAS ESPECIALIDADES DE LA CLINICA
			String hql = "From SpecialityHibernate";
			Query<SpecialityHibernate> consultaEspe = miSesion.createQuery(hql, SpecialityHibernate.class);
			lista = consultaEspe.getResultList();
			// se borra el primer elemento para evitar que al admin se le quite el permiso
			// del admin al modificar
			lista.removeFirst();
			for (SpecialityHibernate x : lista) {
				cbEspecialidad.addItem(x.getEspecialidad());

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

		// BUtton panel

		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String dni = cbDni.getSelectedItem().toString();
				String nom = tfNombre.getText();
				String ape = tfApellido.getText();
				String contra = tfContraseña.getText();
				String especialidad = cbEspecialidad.getSelectedItem().toString();
				
				if (nom.isEmpty() || ape.isEmpty() || contra.isEmpty()
						|| cbEspecialidad.getSelectedItem().toString().equalsIgnoreCase("")) {
					JOptionPane.showMessageDialog(null, "Debes rellenar todos los campos", "WARNING_MESSAGE",
							JOptionPane.WARNING_MESSAGE);
				} else {

					UserHibernate usuario1 = miSesion.get(UserHibernate.class, dni);
					// especialidad que elijo el mas uno es por que no aparece admin (admin sera 0,
					// medico 1) pero en el combobox seria la eleccion 0
					SpecialityHibernate especialidadO = miSesion.get(SpecialityHibernate.class,
							cbEspecialidad.getSelectedIndex() + 1);
					usuario1.setNombre(nom);
					usuario1.setApellido(ape);
					usuario1.setContraseña(contra);
					usuario1.setEspecialidad(especialidadO);
					//condicion para dar de alta o baja
					if(usuario1.getEstado()==0) {
						if(cbEstado.getSelectedIndex()==0) {
							usuario1.setEstado(0);
						}else {
							usuario1.setEstado(1);
						}
					}else {
						if(cbEstado.getSelectedIndex()==0) {
							usuario1.setEstado(1);
						}else {
							usuario1.setEstado(0);
						}
					}

					miSesion.beginTransaction();

					System.out.println("cambiado");
					miSesion.update(usuario1);
					JOptionPane.showMessageDialog(null, " USUARIO MODIFICADO");
					miSesion.getTransaction().commit();

				}

				setModal(false);
				setVisible(false);
				dispose();
//							AdminInsertUser.this.dispatchEvent(new WindowEvent(
//									AdminInsertUser.this, WindowEvent.WINDOW_CLOSING));

			}
		});

		cbDni.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				cargarDatos(cbDni.getSelectedItem().toString(), miSesion, tfNombre, tfApellido, lEspeAct,cbEstado);

			}
		});

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
		contentPanel.add(okButton);
		contentPanel.add(cancelButton);
		contentPanel.add(lEspeAct);
		contentPanel.add(lNuevaEsp);
		
		
		contentPanel.add(lblEstado);
		
		
		contentPanel.add(cbEstado);

	}

	// método para poner los datos de las especialidades en los textField
	public static void cargarDatos(String Dni, Session miSesion, JTextField nombre, JTextField apell, JLabel espeActu,JComboBox estado) {
		String hql = "From UserHibernate where dni=:Dni";
		Query<UserHibernate> consulta = miSesion.createQuery(hql, UserHibernate.class);
		consulta.setParameter("Dni", Dni);
		UserHibernate x = miSesion.get(UserHibernate.class, Dni);
		UserHibernate u = consulta.getSingleResult();
		nombre.setText(u.getNombre());
		apell.setText(u.getApellido());
		espeActu.setText(u.getEspecialidad().getEspecialidad());
		if(u.getEstado()==0) {
			estado.addItem("Baja");
			estado.addItem("Alta");
		}else {
			estado.addItem("Alta");
			estado.addItem("Baja");
		}

	}
}
