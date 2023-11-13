package Vista;

import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import Controlador.ConexionMySQL;
<<<<<<< HEAD
import javax.swing.JMenuBar;
import java.awt.Point;
import javax.swing.JMenu;
import java.awt.Insets;
import javax.swing.JMenuItem;
import java.awt.Rectangle;
import java.awt.Window.Type;
import java.awt.event.MouseAdapter;
=======
import btndentiapp.ButtonDentiApp;
>>>>>>> master

public class AdminAppointment extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private ConexionMySQL conex;
	private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	private JFrame parent, frame;

	/**
	 * Launch the application.
	 */
<<<<<<< HEAD
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
			try {
					AdminAppointment frame = new AdminAppointment(null,null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
=======
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					AdminAppointment frame = new AdminAppointment(null, null);
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}
>>>>>>> master

	/**
	 * Create the frame.
	 */
	public AdminAppointment(ConexionMySQL conex, JFrame parent) {
<<<<<<< HEAD
		setType(Type.POPUP);
		setBounds(new Rectangle(10, 0, 0, 0));
		this.conex = conex; 
		
=======
		this.conex = conex;

>>>>>>> master
		// -------------------- JFrame --------------------
		this.parent = parent;
		this.frame = this;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		// -------------------- Componentes --------------------
		// Panel del Menú
		JPanel menuPane = new JPanel();
		menuPane.setBackground(new Color(148, 220, 219));
		menuPane.setBounds(0, 0, 135, 1080);
		contentPane.add(menuPane);
		menuPane.setLayout(null);
		
		//barra oculat de arriba
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 1900, 50);
		
		menuBar.setMargin(new Insets(50, 0, 0, 25));
		menuBar.setOpaque(false);
		menuBar.setBorderPainted(false);
		menuBar.add(Box.createHorizontalGlue());
		//item 
		JMenu mnNewMenu = new JMenu("");
		mnNewMenu.setIcon(new ImageIcon(getClass().getResource("/Resources/images/desplegable.png")));
		mnNewMenu.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		
		//nombre del doctor o admin
		JMenuItem ItemName = new JMenuItem("");
		ItemName.setText("name");
		
		//item cambio contraseña
		JMenuItem ItemPass = new JMenuItem("Cambiar Contraseña");
		ItemPass.setIcon(new ImageIcon(getClass().getResource("/Resources/images/keypass.png")));
		
		//item cerrar sesion
		JMenuItem ItemOut = new JMenuItem("Cerrar Sesión");
		ItemOut.setIcon(new ImageIcon(getClass().getResource("/Resources/images/logout.png")));
		
		// Label del Logo del Menú
		JLabel lblLogo = new JLabel();
		// lblLogo.setBorder(new LineBorder(new Color(0, 0, 0)));
		lblLogo.setBounds(0, 0, 135, 135);
		lblLogo.setIcon(new ImageIcon(getClass().getResource("/Resources/images/logoMenu.png")));

		// Botón de citas
		ButtonDentiApp btnAppointment = new ButtonDentiApp(0, 135, true,
				new ImageIcon(getClass().getResource("/Resources/images/calendar.png")));
		btnAppointment.setToolTipText("Módulo de citas");

		// Botón de usuarios
		ButtonDentiApp btnUsers = new ButtonDentiApp(0, 270, false,
				new ImageIcon(getClass().getResource("/Resources/images/usersGrey.png")));
		btnAppointment.setToolTipText("Módulo de Usuarios");

		// Botón de Pacientes
		ButtonDentiApp btnCustomers = new ButtonDentiApp(0, 405, false,
				new ImageIcon(getClass().getResource("/Resources/images/customersGrey.png")));
		btnCustomers.setToolTipText("Módulo de pacientes");

		// Botón de Inventario
		ButtonDentiApp btnStock = new ButtonDentiApp(0, 540, false,
				new ImageIcon(getClass().getResource("/Resources/images/stockGrey.png")));
		btnStock.setToolTipText("Módulo de materiales");

		// Botón de Tratamientos y Especialidades
		ButtonDentiApp btnClinic = new ButtonDentiApp(0, 675, false,
				new ImageIcon(getClass().getResource("/Resources/images/clinicGrey.png")));
		btnClinic.setToolTipText("Módulo clínico");

		// Botón del Módulo economico
		ButtonDentiApp btnPayments = new ButtonDentiApp(0, 810, false,
				new ImageIcon(getClass().getResource("/Resources/images/paymentsGrey.png")));
		btnPayments.setToolTipText("Módulo Económico");

		// -------------------- Lógica --------------------
		// Acción para cerrar la ventana solo cuando se ha abierto la siguiente
		this.addWindowListener(new WindowListener() {

			@Override
			public void windowOpened(WindowEvent e) {
				try {
					Thread.sleep(300);
				} catch (Exception e2) {
					e2.printStackTrace();
				}
				parent.dispose();

			}

			@Override
			public void windowIconified(WindowEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowDeiconified(WindowEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowDeactivated(WindowEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowClosing(WindowEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowClosed(WindowEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowActivated(WindowEvent e) {
				// TODO Auto-generated method stub

			}
		});

		// Acción del Módulo de usuarios
		btnUsers.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AdminUsers admUsers = new AdminUsers(conex, frame);
				admUsers.setVisible(true);
			}
		});

		// Acción del Módulo de pacientes
		btnCustomers.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AdminCustomers admCustomers = new AdminCustomers(conex, frame);
				admCustomers.setVisible(true);
			}
		});

		// Acción del Módulo de inventario
		btnStock.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AdminStock admStock = new AdminStock(conex, frame);
				admStock.setVisible(true);
			}
		});

		// Acción del Módulo de la clínica
		btnClinic.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AdminClinic admClinic = new AdminClinic(conex, frame);
				admClinic.setVisible(true);
			}
		});

		// Acción del Módulo económico
		btnPayments.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AdminPayments admPayments = new AdminPayments(conex, frame);
				admPayments.setVisible(true);
			}
		});
		
		//logica click item salir
		ItemOut.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.out.println("funciona");
				Login login=new Login(frame);
				login.setVisible(true);
			
			}
		});
		
		

		// -------------------- Adiciones a los paneles --------------------
		contentPane.add(menuPane);
		menuPane.add(lblLogo);
		menuPane.add(btnAppointment);
		menuPane.add(btnUsers);
		menuPane.add(btnCustomers);
		menuPane.add(btnStock);
		menuPane.add(btnClinic);
		menuPane.add(btnPayments);
<<<<<<< HEAD
		menuPane.add(btnClose);
		contentPane.add(menuBar);
		menuBar.add(mnNewMenu);
		mnNewMenu.add(ItemName);
		mnNewMenu.add(ItemPass);
		mnNewMenu.add(ItemOut);
		
		
		
		
=======

>>>>>>> master
	}
}
