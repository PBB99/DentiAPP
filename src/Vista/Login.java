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
	private JTextField dniText;
	private JTextField passwordText;
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
		
		JLabel loginLabel = new JLabel("Login");
		loginLabel.setBounds(182, 23, 46, 14);
		contentPane.add(loginLabel);
		
		JLabel dniLabel = new JLabel("DNI");
		dniLabel.setBounds(94, 74, 62, 14);
		contentPane.add(dniLabel);
		
		JLabel passwordLabel = new JLabel("Password");
		passwordLabel.setBounds(94, 129, 46, 14);
		contentPane.add(passwordLabel);
		
		dniText = new JTextField();
		dniText.setBounds(240, 71, 75, 20);
		contentPane.add(dniText);
		dniText.setColumns(10);
		
		passwordText = new JTextField();
		passwordText.setBounds(240, 126, 75, 20);
		contentPane.add(passwordText);
		passwordText.setColumns(10);
		
		JButton loginButton = new JButton("Login");
		loginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String username = dniText.getText();
				String password = passwordText.getText();
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
		loginButton.setBounds(155, 206, 89, 23);
		contentPane.add(loginButton);
	}

}