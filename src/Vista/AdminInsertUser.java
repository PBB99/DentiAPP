package Vista;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

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
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JComboBox;


public class AdminInsertUser extends JDialog {
	//-------------------------------VARIABLES------------------------

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
	public AdminInsertUser( SessionFactory instancia,JFrame parent, boolean modal) {
		
		this.instancia = instancia;
		this.miSesion = instancia.openSession();
		miSesion.beginTransaction();
		setModal(modal);
		
		//-----------------------COMPONENTES-------------------
		setBounds(960, 540, 650, 400);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPanel.setLayout(null);
		
		JLabel lblDNI = new JLabel("DNI");
		lblDNI.setBounds(20, 13, 37, 14);
		
		
		JLabel lblNombre = new JLabel("Nombre");
		lblNombre.setBounds(20, 82, 49, 14);
		contentPanel.add(lblNombre);
		
		tfDNI = new JTextField();
		tfDNI.setColumns(10);
		tfDNI.setBounds(67, 10, 86, 20);
		
		
		tfNombre = new JTextField();
		tfNombre.setColumns(10);
		tfNombre.setBounds(67, 79, 86, 20);
		
		
		JLabel lblContraseña = new JLabel("Contraseña");
		lblContraseña.setBounds(266, 13, 86, 14);
		
		
		tfContraseña = new JTextField();
		tfContraseña.setColumns(10);
		tfContraseña.setBounds(395, 10, 86, 20);
		
		
		JLabel lblApellido = new JLabel("Apellido");
		lblApellido.setBounds(266, 82, 86, 14);
		
		
		tfApellido = new JTextField();
		tfApellido.setColumns(10);
		tfApellido.setBounds(395, 79, 86, 20);
		
		
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
				AdminInsertUser.this.dispatchEvent(new WindowEvent(
						AdminInsertUser.this, WindowEvent.WINDOW_CLOSING));
				miSesion.close();
			}
		});
		cancelButton.setActionCommand("Cancel");
		
		JComboBox cbEspecialidad = new JComboBox();
		cbEspecialidad.setBounds(218, 152, 86, 20);
		
		
		
		//------------------------------------------LOGICA-----------------------------------------------
		
		try {//CARGAR EN EL COMBO BOX LAS ESPECIALIDADES DE LA CLINICA
			String hql="From SpecialityHibernate";
		Query<SpecialityHibernate>consultaEspe=miSesion.createQuery(hql,SpecialityHibernate.class);
		List<SpecialityHibernate>lista=consultaEspe.getResultList();	
		for(SpecialityHibernate x:lista) {
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
						String hql2="From UserHibernate where dni=:dni";
					
						Query<UserHibernate>consultaDni=miSesion.createQuery(hql2,UserHibernate.class);
						consultaDni.setParameter("dni", dni);
						
						try {
							if(dni.isEmpty() || nom.isEmpty() || ape.isEmpty() || contra.isEmpty() || cbEspecialidad.getSelectedItem().toString().equalsIgnoreCase("") ) {
								JOptionPane.showMessageDialog(null,"Debes rellenar todos los campos", "WARNING_MESSAGE",JOptionPane.WARNING_MESSAGE);
							}else if(consultaDni==null) {
								
								JOptionPane.showMessageDialog(null,"Ese DNI ya esta ligado a un usuari/a", "WARNING_MESSAGE",JOptionPane.WARNING_MESSAGE);
					                }else {
					                	UserHibernate usuario=new UserHibernate(dni,nom,ape,contra,1);
					                	SpecialityHibernate sp=miSesion.get(SpecialityHibernate.class, cbEspecialidad.getSelectedIndex());
					                	usuario.addEspeciality(sp);
					                	sp.addUser(usuario);
					                	miSesion.save(usuario);
					                	miSesion.update(sp);
					                	JOptionPane.showMessageDialog(null,"Nuevo usuario creado");
					                	miSesion.getTransaction().commit();
					                	miSesion.close();
					                }
					            
								
							
								
								setModal(false);
								setVisible(false);
								dispose();
								
//								AdminInsertUser.this.dispatchEvent(new WindowEvent(
//										AdminInsertUser.this, WindowEvent.WINDOW_CLOSING));
							
							
						} catch (Exception e2) {
							// TODO: handle exception
						}
					}
				});
				
			
			}
			{//-------------------ADICIONES DE LOS COMPONENTES---------------
				buttonPane.add(okButton);
				buttonPane.add(cancelButton);
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
