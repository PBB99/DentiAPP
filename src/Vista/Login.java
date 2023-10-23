package Vista;

import java.awt.EventQueue;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

import Modelo.Specialist;
import Modelo.User;

import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
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
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.Font;



public class Login extends JFrame {
	// declaracion de variables

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private ConexionMySQL conex;

	private Connection cn;
	private JTextField tfDNI;
	private JTextField tfPassword;
	private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	private UserController us;
	private ArrayList<User> userList;
	private ArrayList<Specialist> speciaList;
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

		// declaracion de las conexiones
		this.conex = new ConexionMySQL();
		conex.conectar();
		us = new UserController(conex);
		sp = new SpecialistController(conex);
		// traemos todos los datos de la tabla especialista a nuestro ArrayList del
		// modelo Specialist
		try {
			speciaList = sp.getAllSpecialist();
			userList = us.getAllUsers();
		} catch (SQLException e) {
			e.printStackTrace();
		}


		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setBounds(0, 0, 1920, 1080);
		
		//---------------------------------------------IMAGEN DE FONDO--------------------------------------
		contentPane = new JPanel() {
			@Override	
			public void paint(Graphics g) {
				Image bg = new ImageIcon(getClass().getResource("/Resources/images/bg_Login.jpg")).getImage();
				g.drawImage(bg,0,0,getWidth(),getHeight(), this);
				setOpaque(false);
				super.paint(g);
			}
		};
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		this.setUndecorated(true);
		setContentPane(contentPane);
		contentPane.setLayout(null);

		
		// -------------------- Componentes --------------------
		// Login Layout
		JPanel loginPane = new JPanel();
		loginPane.setBounds(685, 165, 550, 750);
		//loginPane.setBorder(new LineBorder(new Color(0, 0, 0)));
		loginPane.setBackground(Color.WHITE);
		loginPane.setLayout(null);
		
		// Label del Logo
		JLabel lblLogo = new JLabel("");
		//lblLogo.setBorder(new LineBorder(new Color(0, 0, 0)));
		lblLogo.setBounds(0, 0, 550, 200);
		lblLogo.setIcon(new ImageIcon(getClass().getResource("/Resources/images/logoFinal.png")));
		
		
		// Label de usurario
		JLabel lblDNI = new JLabel("Usuario");
		//lblDNI.setBorder(new LineBorder(new Color(0, 0, 0)));
		lblDNI.setBounds(75, 250, 400, 50);
		lblDNI.setFont(new Font("Tahoma", Font.PLAIN, 30));
		
		
		//Label de Contraseña
		JLabel lblPassword = new JLabel("Contraseña");
		//lblPassword.setBorder(new LineBorder(new Color(0, 0, 0)));
		lblPassword.setBounds(75, 425, 400, 50);
		lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 30));
		
		
		//TexField DNI
		tfDNI = new JTextField();
		//tfDNI.setBorder(new LineBorder(new Color(0, 0, 0)));
		tfDNI.setBounds(75, 315, 400, 50);
		tfDNI.setFont(new Font("Tahoma", Font.PLAIN, 25));
		
		
		//TextField Password
		tfPassword = new JPasswordField();
		//tfPassword.setBorder(new LineBorder(new Color(0, 0, 0)));
		tfPassword.setBounds(75, 490, 400, 50);
		tfPassword.setFont(new Font("Tahoma", Font.PLAIN, 25));
		
		
		//Botón de Login
		JButton btnLogin = new JButton("Iniciar sesión");
		btnLogin.setFont(new Font("Tahoma", Font.PLAIN, 19));
		btnLogin.setBounds(200, 675, 150, 50);
		
		//Botón de Close
				JButton btnClose = new JButton();
				btnClose.setFont(new Font("Tahoma", Font.PLAIN, 19));
				btnClose.setBounds(25, 985, 75, 75);
				btnClose.setIcon(new ImageIcon(getClass().getResource("/Resources/images/logout.png")));
				makeTransparent(btnClose);
		
		//----------------------------------------------LOGICA----------------------------------------------------------
		
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnClose.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				btnClose.setIcon(new ImageIcon(getClass().getResource("/Resources/images/logout.png")));
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				btnClose.setIcon(new ImageIcon(getClass().getResource("/Resources/images/logoutWhite.png")));
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
			
			}});

		//acciones del boton login, carga dos tablas, compara los datos el usuario introducido y da paso o no a la siguiente pantalla
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String username = tfDNI.getText();
				String password = tfPassword.getText();
				boolean aux2 = true;

				for (User x : userList) {
					// si coinciden nombre y contraseña con alguno de los usuarios
					if ((x.getDni().toString().equals(username)) && (x.getPass().toString().equals(password)) && aux2) {
						// esta dado de alta

						aux2 = false;
						if (x.isState()) {

							for (Specialist s : speciaList) {// interactua por todos los especistas existentes
								if (x.getDni().toString().equalsIgnoreCase(s.getDni().toString())) {

									// en el caso de que el usuario este dentro de los especialistas del centro
									// dental
									if (s.getId_speciality() == 1) {
										// se abre la pantalla de admin
										System.out.println("adsijdashbasdhi");
										User aux = x;
										AdminUsers pa=new AdminUsers();

										pa.setVisible(true);
										dispose();
									} else {// si no es admin es doctor
											// declaracion de la pantalla doctor
										dispose();
									}
								}
							}
						} else {// esta dado de baja
							JOptionPane.showMessageDialog(btnLogin, "Cuidado", "Este usuario ya no es válido",
									JOptionPane.WARNING_MESSAGE);
							break;
						}
					}
				}
				if (aux2 == true) {
					JOptionPane.showMessageDialog(btnLogin, "Su usuario o contraseña no coincide.\n Intentelo de nuevo",
							"Error", JOptionPane.ERROR_MESSAGE);
				}


			}
		});
		
		//-------------------------------------ADICIONES AL PANEL Y AL LOGIN PANEL-----------------
		contentPane.add(btnClose);
		contentPane.add(loginPane);
		loginPane.add(btnLogin);
		loginPane.add(tfPassword);
		loginPane.add(tfDNI);
		loginPane.add(lblPassword);
		loginPane.add(lblDNI);
		loginPane.add(lblLogo);
	}
		
	// -------------------- Métodos y Funciones --------------------
	//Método para transparentar los botones
	public static void makeTransparent(JButton btn) {
		btn.setOpaque(false);
		btn.setContentAreaFilled(false);
		btn.setBorderPainted(false);
	}
}