package Vista;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
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

import Controlador.ConexionMySQL;
import Controlador.SpecialistController;
import Modelo.Specialist;

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
	private Connection cn;
	private ConexionMySQL conex;
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();

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
	public AdminInsertUser(ConexionMySQL conex, AdminUsers parent, boolean modal) {
		this.conex=conex;
		setModal(modal);
		
		//-----------------------COMPONENTES-------------------
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPanel.setLayout(null);
		
		JLabel lblDNI = new JLabel("DNI");
		lblDNI.setBounds(20, 13, 18, 14);
		
		
		JLabel lblNombre = new JLabel("Nombre");
		lblNombre.setBounds(20, 82, 37, 14);
		contentPanel.add(lblNombre);
		
		tfDNI = new JTextField();
		tfDNI.setColumns(10);
		tfDNI.setBounds(67, 10, 86, 20);
		
		
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
				AdminInsertUser.this.dispatchEvent(new WindowEvent(
						AdminInsertUser.this, WindowEvent.WINDOW_CLOSING));
			}
		});
		cancelButton.setActionCommand("Cancel");
		
		JComboBox cbEspecialidad = new JComboBox();
		cbEspecialidad.setBounds(218, 152, 86, 20);
		
		cbEspecialidad.addItem("");
		
		//------------------------------------------LOGICA-----------------------------------------------
		ResultSet rs = null;
		try {//CARGAR EN EL COMBO BOX LAS ESPECIALIDADES DE LA CLINICA
		
			rs = conex.ejecutarSelect("Select * from especialidades");
			//conex.ejecutarInsertUpdateDelete("insert into usuario(dni, nombre, apellido, contraseña, estado) values ('79379541G', 'Pedro', 'Pueblo', '1234', true)", cn);
			while (rs.next()) {
				cbEspecialidad.addItem(rs.getString("especialidad"));
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
						int numIdEspecialista;
						int nEsp = 0;
						ArrayList<Specialist> arSpe = null;
						try {
							if(dni.isEmpty() || nom.isEmpty() || ape.isEmpty() || contra.isEmpty() || cbEspecialidad.getSelectedItem().toString().equalsIgnoreCase("") ) {
								JOptionPane.showMessageDialog(null,"Debes rellenar todos los campos", "WARNING_MESSAGE",JOptionPane.WARNING_MESSAGE);
							}else {
								arSpe = new SpecialistController(conex).getAllSpecialist();
								//conex.ejecutarInsertUpdateDelete("insert into usuario(dni, nombre, apellido, contraseña, estado) values ('79379541G', 'Pedro', 'Pueblo', '1234', true)", cn);
								//numIdEspecialista = arSpe.get(arSpe.size()).getId_specialist();
								conex.ejecutarInsertUpdateDelete("insert into usuario values ('" + dni + "', '" + nom + "', '" + ape + "', '" + contra + "', true)");
								//arSpe = conex.ejecutarSelect("Select * from especialidades");
								System.out.println(especialidad);
								while (encontrado == false && rs.next() ) {
					                if(especialidad.equalsIgnoreCase(rs.getString("especialidad"))) {
					                	encontrado = true;
					                	nEsp = rs.getInt("id_especialidad");
					                }
					            }
								conex.ejecutarInsertUpdateDelete("insert into especialista values ('" + numIdEspecialista + "', '" + dni + "', '" + nEsp + "')");
								JOptionPane.showMessageDialog(null,"Nuevo usuario creado");
								setModal(false);
								setVisible(false);
								AdminInsertUser.this.dispatchEvent(new WindowEvent(
										AdminInsertUser.this, WindowEvent.WINDOW_CLOSING));
							}
							
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
