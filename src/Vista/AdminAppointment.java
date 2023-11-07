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
import javax.swing.JMenuBar;
import java.awt.Point;
import javax.swing.JMenu;
import java.awt.Insets;
import javax.swing.JMenuItem;
import java.awt.Rectangle;
import java.awt.Window.Type;
import java.awt.event.MouseAdapter;

public class AdminAppointment extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private ConexionMySQL conex;
	private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	private JFrame parent, frame;
	
	/**
	 * Launch the application.
	 */
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

	/**
	 * Create the frame.
	 */
	public AdminAppointment(ConexionMySQL conex, JFrame parent) {
		setType(Type.POPUP);
		setBounds(new Rectangle(10, 0, 0, 0));
		this.conex = conex; 
		
		// -------------------- JFrame --------------------
		this.parent=parent;
		this.frame=this;
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
		JButton btnAppointment = new JButton();
		btnAppointment.setBackground(new Color(238, 238, 238));
		btnAppointment.setBounds(0, 135, 135, 135);
		btnAppointment.setBorderPainted(false);
		btnAppointment.setIcon(new ImageIcon(getClass().getResource("/Resources/images/calendar.png")));
		btnAppointment.setToolTipText("Módulo de citas");

		// Botón de usuarios
		JButton btnUsers = new JButton();
		btnUsers.setBackground(new Color(148, 220, 219));
		btnUsers.setBounds(0, 270, 135, 135);
		btnUsers.setBorderPainted(false);
		btnUsers.setIcon(new ImageIcon(getClass().getResource("/Resources/images/usersGrey.png")));
		btnUsers.setToolTipText("Módulo de usuarios");
		btnUsers.addMouseListener(new MouseListener() {
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
				btnUsers.setBackground(new Color(148, 220, 219));
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				btnUsers.setBackground(new Color(31, 192, 191));
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub

			}
		});

		// Botón de Pacientes
		JButton btnCustomers = new JButton();
		btnCustomers.setBackground(new Color(148, 220, 219));
		btnCustomers.setBounds(0, 405, 135, 135);
		btnCustomers.setBorderPainted(false);
		btnCustomers.setIcon(new ImageIcon(getClass().getResource("/Resources/images/customersGrey.png")));
		btnCustomers.setToolTipText("Módulo de pacientes");
		btnCustomers.addMouseListener(new MouseListener() {
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
				btnCustomers.setBackground(new Color(148, 220, 219));
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				btnCustomers.setBackground(new Color(31, 192, 191));
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub

			}
		});

		// Botón de Inventario
		JButton btnStock = new JButton();
		btnStock.setBackground(new Color(148, 220, 219));
		btnStock.setBounds(0, 540, 135, 135);
		btnStock.setBorderPainted(false);
		btnStock.setIcon(new ImageIcon(getClass().getResource("/Resources/images/stockGrey.png")));
		btnStock.setToolTipText("Módulo de materiales");
		btnStock.addMouseListener(new MouseListener() {
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
				btnStock.setBackground(new Color(148, 220, 219));
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				btnStock.setBackground(new Color(31, 192, 191));
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub

			}
		});

		// Botón de Tratamientos y Especialidades
		JButton btnClinic = new JButton();
		btnClinic.setBackground(new Color(148, 220, 219));
		btnClinic.setBounds(0, 675, 135, 135);
		btnClinic.setBorderPainted(false);
		btnClinic.setIcon(new ImageIcon(getClass().getResource("/Resources/images/clinicGrey.png")));
		btnClinic.setToolTipText("Módulo clínico");
		btnClinic.addMouseListener(new MouseListener() {
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
				btnClinic.setBackground(new Color(148, 220, 219));
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				btnClinic.setBackground(new Color(31, 192, 191));
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub

			}
		});

		// Botón del Módulo economico
		JButton btnPayments = new JButton();
		btnPayments.setBackground(new Color(148, 220, 219));
		btnPayments.setBounds(0, 810, 135, 135);
		btnPayments.setBorderPainted(false);
		btnPayments.setIcon(new ImageIcon(getClass().getResource("/Resources/images/paymentsGrey.png")));
		btnPayments.setToolTipText("Módulo Económico");
		btnPayments.addMouseListener(new MouseListener() {
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
				btnPayments.setBackground(new Color(148, 220, 219));
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				btnPayments.setBackground(new Color(31, 192, 191));
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub

			}
		});

		// Botón de Salir
		JButton btnClose = new JButton();
		btnClose.setBackground(new Color(148, 220, 219));
		btnClose.setBounds(0, 945, 135, 135);
		btnClose.setIcon(new ImageIcon(getClass().getResource("/Resources/images/logoutMenuGrey.png")));
		btnClose.setToolTipText("Módulo Económico");
		btnClose.setBorderPainted(false);
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
				btnClose.setBackground(new Color(148, 220, 219));
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				btnClose.setBackground(new Color(31, 192, 191));
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub

			}
		});

		// -------------------- Lógica --------------------
		//Acción para cerrar la ventana solo cuando se ha abierto la siguiente
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
        
		// Acción de salir
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Login log = new Login(frame);
				log.setVisible(true);
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
		menuPane.add(btnClose);
		contentPane.add(menuBar);
		menuBar.add(mnNewMenu);
		mnNewMenu.add(ItemName);
		mnNewMenu.add(ItemPass);
		mnNewMenu.add(ItemOut);
		
		
		
		
	}
}
