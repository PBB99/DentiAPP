package Vista;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Controlador.ConexionMySQL;

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
import java.sql.Statement;
import java.awt.event.ActionEvent;
public class Login extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField tfDNI;
	private JTextField tfPassword;
	private ConexionMySQL conex;
	private Connection cn;
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
		conex = new ConexionMySQL();
		cn = conex.conectar();
		
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
				
				try {
					rs = conex.ejecutarSelect("Select * from usuario",cn);
					//conex.ejecutarInsertUpdateDelete("insert into usuario(dni, nombre, apellido, contraseña, estado) values ('79379541G', 'Pedro', 'Pueblo', '1234', true)", cn);
					while (rs.next() && correcto == false) {
		                dni = rs.getString("dni");
		                System.out.println(dni);
		                contra = rs.getString("contraseña");
		                if(dni.equalsIgnoreCase(username) && contra.equalsIgnoreCase(password))
		                	correcto = true;
		            }
					if(correcto == true) {
						JOptionPane.showMessageDialog(null,"You have log in succesfully");
					}else {
						JOptionPane.showMessageDialog(null,"This user doesn't exist in the sistem", "WARNING_MESSAGE",JOptionPane.WARNING_MESSAGE);
					}
				} catch (Exception e2) {
					// TODO: handle exception
				}
			}
		});
		btnLogin.setBounds(155, 206, 89, 23);
		contentPane.add(btnLogin);
	}

}