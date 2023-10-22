package Vista;

import java.awt.EventQueue;
import Modelo.Specialist;
import Modelo.User;

import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Controlador.ConexionMySQL;
import Controlador.SpecialistController;
import Controlador.UserController;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;
public class Login extends JFrame {
	//declaracion de variables

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField tfDNI;
	private JTextField tfPassword;
	private ConexionMySQL conex;
	private UserController us;
	private ArrayList<User> userList;
	private ArrayList<Specialist>speciaList;
	private SpecialistController sp;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login frame = new Login();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Login() {
		//declaracion de las conexiones
		this.conex = new ConexionMySQL();
		 conex.conectar();
		us=new UserController(conex);
		sp=new SpecialistController(conex);
		//traemos todos los datos de la tabla especialista a nuestro ArrayList del modelo Specialist
		try {
			speciaList=sp.getAllSpecialist();
			userList=us.getAllUsers();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblLogin = new JLabel("Login");
		lblLogin.setBounds(182, 23, 46, 14);
		contentPane.add(lblLogin);
		
		JLabel lblDNI = new JLabel("DNI");
		lblDNI.setBounds(94, 74, 62, 14);
		contentPane.add(lblDNI);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(94, 129, 46, 14);
		contentPane.add(lblPassword);
		
		tfDNI = new JTextField();
		tfDNI.setBounds(240, 71, 75, 20);
		contentPane.add(tfDNI);
		tfDNI.setColumns(10);
		
		tfPassword = new JTextField();
		tfPassword.setBounds(240, 126, 75, 20);
		contentPane.add(tfPassword);
		tfPassword.setColumns(10);
		
		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String username = tfDNI.getText();
				String password = tfPassword.getText();
				
				
				for(User x:userList) {
					//si coinciden nombre y contraseña con alguno de los usuarios
					if((x.getDni().toString().equals(username))&&(x.getPass().toString().equals(password))) {
						//esta dado de alta
						if(x.isState()) {
							
							for(Specialist s:speciaList) {//interactua por todos los especistas existentes
								if(x.getDni().toString().equalsIgnoreCase(s.getDni().toString())) {
									//en el caso de que el usuario este dentro de los especialistas del centro dental
									if(s.getId_speciality()==1) {
										//se abre la pantalla de admin
										User aux=x;
										pAdmin pa=new pAdmin();
										
										pa.setVisible(true);
									}else {//si no es admin es doctor
										//declaracion de la pantalla doctor
									}
								}else {
									//si no encuentra el usuario en los especialistas
									JOptionPane.showMessageDialog(btnLogin, "Error","Su usuario no esta registrado como especialista",JOptionPane.ERROR_MESSAGE);
								}
							}
						}else {//esta dado de baja
							JOptionPane.showMessageDialog(btnLogin,"Cuidado","Este usuario ya no es válido",JOptionPane.WARNING_MESSAGE);
						}
					}else {
						JOptionPane.showMessageDialog(btnLogin, "Error","Su usuario o contraseña no coincide.\n Intentelo de nuevo",JOptionPane.ERROR_MESSAGE);
					}
				}
			
			}
		});
		btnLogin.setBounds(155, 206, 89, 23);
		contentPane.add(btnLogin);
	}

}