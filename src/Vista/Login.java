package Vista;

import java.awt.EventQueue;
import Modelo.Specialist;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Controlador.ConexionMySQL;
import Controlador.SpecialistController;

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
	private SpecialistController sp;
	private ArrayList<Specialist> speciaList;
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
		sp=new SpecialistController(conex);
		//traemos todos los datos de la tabla especialista a nuestro ArrayList del modelo Specialist
		try {
			speciaList=sp.getAllSpecialist();
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
				ResultSet rs = null;
				String dni = null;
				String contra = null;
				boolean correcto = false;
				
				for(Specialist x:speciaList) {
					if()
				}
			
			}
		});
		btnLogin.setBounds(155, 206, 89, 23);
		contentPane.add(btnLogin);
	}

}