package Vista;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Controlador.ConexionMySQL;
import Modelo.UserHibernate;
import btndentiapp.ButtonDentiApp;

public class AdminStock extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private ConexionMySQL conex;
	private JFrame parent, frame;
	private UserHibernate userHi;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
//				try {
//					AdminStock frame = new AdminStock(null,null);
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public AdminStock( UserHibernate userHi,JFrame parent) {
		this.userHi=userHi;

		// -------------------- JFrame --------------------
		this.frame = this;
		this.parent = parent;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setResizable(false);
		setContentPane(contentPane);
		contentPane.setLayout(null);

		// -------------------- Componentes Gráficos --------------------
		// Panel del Menú
		JPanel menuPane = new JPanel();
		menuPane.setBackground(new Color(148, 220, 219));
		menuPane.setBounds(0, 0, 135, 1080);
		menuPane.setLayout(null);

		// Label del Logo del Menú
		JLabel lblLogo = new JLabel();
		// lblLogo.setBorder(new LineBorder(new Color(0, 0, 0)));
		lblLogo.setBounds(0, 0, 135, 135);
		lblLogo.setIcon(new ImageIcon(getClass().getResource("/Resources/images/logoMenu.png")));

		// Botón de citas
		ButtonDentiApp btnAppointment = new ButtonDentiApp(0, 135, false,
				new ImageIcon(getClass().getResource("/Resources/images/calendarGrey.png")));
		btnAppointment.setToolTipText("Módulo de citas (Alt+C)");
		btnAppointment.setMnemonic(KeyEvent.VK_C);
		
		// Botón de usuarios
		ButtonDentiApp btnUsers = new ButtonDentiApp(0, 270, false,
				new ImageIcon(getClass().getResource("/Resources/images/usersGrey.png")));
		btnUsers.setToolTipText("Módulo de Usuarios (Alt+U)");
		btnUsers.setMnemonic(KeyEvent.VK_U); 
		
		// Botón de Pacientes
		ButtonDentiApp btnCustomers = new ButtonDentiApp(0, 405, false,
				new ImageIcon(getClass().getResource("/Resources/images/customersGrey.png")));
		btnCustomers.setToolTipText("Módulo de pacientes (Alt+P)");
		btnCustomers.setMnemonic(KeyEvent.VK_P);
		
		// Botón de Inventario
		ButtonDentiApp btnStock = new ButtonDentiApp(0, 540, true,
				new ImageIcon(getClass().getResource("/Resources/images/stock.png")));
		btnStock.setToolTipText("Módulo de materiales (Alt+I)");

		// Botón de Tratamientos y Especialidades
		ButtonDentiApp btnClinic = new ButtonDentiApp(0, 675, false,
				new ImageIcon(getClass().getResource("/Resources/images/clinicGrey.png")));
		btnClinic.setToolTipText("Módulo clínico (Alt+L)");
		btnClinic.setMnemonic(KeyEvent.VK_L);
		
		// Botón del Módulo economico
		ButtonDentiApp btnPayments = new ButtonDentiApp(0, 810, false,
				new ImageIcon(getClass().getResource("/Resources/images/paymentsGrey.png")));
		btnPayments.setToolTipText("Módulo Económico (Alt+E)");
		btnPayments.setMnemonic(KeyEvent.VK_E);
		
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

		// Acción del Módulo de citas
		btnAppointment.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AdminAppointment admAppointment = new AdminAppointment(userHi, frame);
				admAppointment.setVisible(true);
			}
		});

		// Acción del Módulo de usuarios
		btnUsers.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AdminUsers admUsers = new AdminUsers(userHi, frame);
				admUsers.setVisible(true);
			}
		});

		// Acción del Módulo de pacientes
		btnCustomers.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AdminCustomers admCustomers = new AdminCustomers(userHi, frame);
				admCustomers.setVisible(true);
			}
		});

		// Acción del Módulo de la clínica
		btnClinic.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AdminClinic admClinic = new AdminClinic(userHi, frame);
				admClinic.setVisible(true);
			}
		});

		// Acción del Módulo económico
		btnPayments.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AdminPayments admPayments = new AdminPayments(userHi, frame);
				admPayments.setVisible(true);
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
	}

}
