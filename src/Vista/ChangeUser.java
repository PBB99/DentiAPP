package Vista;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
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
	public ChangeUser(SessionFactory instancia,JFrame parent, boolean modal) {

		this.instancia = instancia;
		this.miSesion = instancia.openSession();
	
		setModal(modal);
		setContentPane(contentPanel);
	

		
	
		// -----------------------COMPONENTES-------------------

		setBounds(960, 540, 650, 400);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPanel.setLayout(null);
		
		JLabel lEspeAct = new JLabel("");
		lEspeAct.setBounds(130, 155, 144, 14);
		
		
		JLabel lNuevaEsp = new JLabel("Nueva Especialidad");
		lNuevaEsp.setBounds(311, 155, 122, 14);
		
		JLabel lblDNI = new JLabel("DNI");
		lblDNI.setBounds(20, 13, 18, 14);
		contentPanel.setLayout(null);

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

		JLabel lblEspecialidad = new JLabel("Especialidad Act:");
		lblEspecialidad.setBounds(22, 155, 86, 14);

		JButton okButton = new JButton("OK");
		okButton.setBounds(87, 254, 124, 20);
		okButton.setActionCommand("OK");
		getRootPane().setDefaultButton(okButton);
		okButton.setMnemonic(KeyEvent.VK_ENTER);
		
		JButton cancelButton = new JButton("Cancel");
		cancelButton.setBounds(232, 254, 124, 20);
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
		cbEspecialidad.setBounds(453, 152, 86, 20);

		

		JComboBox cbDni = new JComboBox();
		cbDni.setBounds(67, 10, 86, 20);

		cbDni.addItem("");
		//--------------------------LOGICA--------------------------

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
		
		//BUtton panel
		
		
			
			okButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					String dni = cbDni.getSelectedItem().toString();
					String nom = tfNombre.getText();
					String ape = tfApellido.getText();
					String contra = tfContraseña.getText();
					String especialidad = cbEspecialidad.getSelectedItem().toString();
					
					
						if( nom.isEmpty() || ape.isEmpty() || contra.isEmpty() || cbEspecialidad.getSelectedItem().toString().equalsIgnoreCase("") ) {
							JOptionPane.showMessageDialog(null,"Debes rellenar todos los campos", "WARNING_MESSAGE",JOptionPane.WARNING_MESSAGE);
						}else {
									
									UserHibernate usuario1=miSesion.get(UserHibernate.class,dni);
									//especialidad que elijo el mas uno es por que no aparece admin (admin sera 0, medico 1) pero en el combobox  seria la eleccion 0
									SpecialityHibernate especialidadO=miSesion.get(SpecialityHibernate.class,cbEspecialidad.getSelectedIndex()+1 );
									usuario1.setNombre(nom);
									usuario1.setApellido(ape);
									usuario1.setContraseña(contra);
									usuario1.getSpeciality().remove(0);
									usuario1.getSpeciality().add(especialidadO);
									
									miSesion.beginTransaction();
									
				                	System.out.println("cambiado");
				                	miSesion.update(usuario1);
				                	JOptionPane.showMessageDialog(null," USUARIO MODIFICADO");
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
					cargarDatos(cbDni.getSelectedItem().toString(), miSesion, tfNombre, tfApellido,lEspeAct);
					
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
		
		
	}

	//método para poner los datos de las especialidades en los textField
	public static void cargarDatos(String Dni,Session miSesion,JTextField nombre,JTextField apell,JLabel espeActu) {
		String hql="From UserHibernate where dni=:Dni";
		Query<UserHibernate>consulta=miSesion.createQuery(hql,UserHibernate.class);
		consulta.setParameter("Dni", Dni);
		UserHibernate x=miSesion.get(UserHibernate.class,Dni );
		UserHibernate u=consulta.getSingleResult();
		nombre.setText(u.getNombre());
		apell.setText(u.getApellido());
		espeActu.setText(u.getSpeciality().get(0).getEspecialidad());
		
		
		
		
		
	}
}
