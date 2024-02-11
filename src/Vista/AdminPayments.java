package Vista;

import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import Controlador.ConexionMySQL;
import Modelo.UserHibernate;
import Otros.RoundedPanel;
import btndentiapp.ButtonDentiApp;

public class AdminPayments extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private ConexionMySQL conex;
	private JFrame parent, frame;
	private UserHibernate userHi;
	private LineBorder lb2 = new LineBorder(new Color(148, 220, 219), 3, true);
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
//				try {
//					AdminPayments frame = new AdminPayments(null,null);
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
	public AdminPayments( UserHibernate userHi,JFrame parent) {
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
		//nombre
		// Citas
				JPanel panelTitleAdmin = new JPanel();
				panelTitleAdmin.setBounds(1, 2, 170, 90);
				panelTitleAdmin.setBorder(new TitledBorder(lb2, "", TitledBorder.LEFT, TitledBorder.TOP, null, new Color(51, 51, 51)));
				panelTitleAdmin.setOpaque(false);
				panelTitleAdmin.setLayout(null);
				
				//rounded panel de fomdo para el nombre 
				JPanel panelnombre = new RoundedPanel(30, new Color(240, 240, 240));
				panelnombre.setBounds(136, 0, 150, 60);
				panelnombre.setOpaque(false);
				panelnombre.setLayout(null);
				contentPane.add(panelnombre);
				panelnombre.add(panelTitleAdmin);
				String htmlString = "<html><body><sup>" 
				+ userHi.getNombre() + "</sup><span>" + userHi.getApellido() + 
				"</span></body></html>";
				JLabel lblNAdmin = new JLabel(htmlString);
				lblNAdmin.setToolTipText("Nombre & Apellido");
				lblNAdmin.setBounds(10, 5, 150, 60);
				lblNAdmin.setFont(new Font("metropolis",Font.PLAIN,20));
				panelTitleAdmin.add(lblNAdmin);
		//menu bar
		// barra oculat de arriba
				JMenuBar menuBar = new JMenuBar();
				menuBar.setBounds(0, 0, 1900, 50);

				menuBar.setMargin(new Insets(50, 0, 0, 25));
				menuBar.setOpaque(false);
				menuBar.setBorderPainted(false);
				menuBar.add(Box.createHorizontalGlue());
				
				// item
				JMenu mnNewMenu = new JMenu(userHi.getDni());
				mnNewMenu.setIcon(new ImageIcon(getClass().getResource("/Resources/images/definitiva.png")));
				mnNewMenu.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
				mnNewMenu.setOpaque(false);
				mnNewMenu.setBackground(new Color(0,0,0,0));
				mnNewMenu.addMouseListener(new MouseListener() {
					
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
						// TODO Auto-generated method stub
						mnNewMenu.setOpaque(false);
						mnNewMenu.setBackground(new Color(0,0,0,0));
						
					}
					
					@Override
					public void mouseEntered(MouseEvent e) {
						// TODO Auto-generated method stub
						mnNewMenu.setOpaque(true);
						mnNewMenu.setBackground(Color.LIGHT_GRAY);
					}
					
					@Override
					public void mouseClicked(MouseEvent e) {
						// TODO Auto-generated method stub

						
					}
				});
				

				// nombre del doctor o admin
				JMenuItem ItemName = new JMenuItem(userHi.getNombre());
				//ItemName.setText(userHi.getNombre());

				// item cambio contraseña
				JMenuItem ItemPass = new JMenuItem("Cambiar Contraseña");
				ItemPass.setIcon(new ImageIcon(getClass().getResource("/Resources/images/keypass.png")));

				// item cerrar sesion
				JMenuItem ItemOut = new JMenuItem("Cerrar Sesión");
				ItemOut.setIcon(new ImageIcon(getClass().getResource("/Resources/images/logout.png")));
		
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
		ButtonDentiApp btnStock = new ButtonDentiApp(0, 540, false,
				new ImageIcon(getClass().getResource("/Resources/images/stockGrey.png")));
		btnStock.setToolTipText("Módulo de materiales (Alt+I)");
		btnStock.setMnemonic(KeyEvent.VK_I);
		
		// Botón de Tratamientos y Especialidades
		ButtonDentiApp btnClinic = new ButtonDentiApp(0, 675, false,
				new ImageIcon(getClass().getResource("/Resources/images/clinicGrey.png")));
		btnClinic.setToolTipText("Módulo clínico (Alt+L)");
		btnClinic.setMnemonic(KeyEvent.VK_L);
		
		// Botón del Módulo economico
		ButtonDentiApp btnPayments = new ButtonDentiApp(0, 810, true,
				new ImageIcon(getClass().getResource("/Resources/images/payments.png")));
		btnPayments.setToolTipText("Módulo Económico (Alt+E)");
		btnPayments.setMnemonic(KeyEvent.VK_E);
		
		// Ayuda
		JButton btnHelp = new JButton();
		btnHelp.setBounds(300, 10, 40, 40);
		btnHelp.setBorder(null);
		btnHelp.setFocusPainted(false);
		btnHelp.setBorderPainted(false);
		btnHelp.setContentAreaFilled(false);
		btnHelp.setOpaque(false);
		btnHelp.setBackground(null);
		btnHelp.setIcon(new ImageIcon(getClass().getResource("/Resources/images/help.png")));
		btnHelp.setToolTipText("Ayuda (Alt+H)");
		btnHelp.setMnemonic(KeyEvent.VK_H);
		btnHelp.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Mostrar ayuda");
			}
		});
		contentPane.add(btnHelp);		
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

		// Acción del Módulo de inventario
		btnStock.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AdminStock admStock = new AdminStock(userHi, frame);
				admStock.setVisible(true);
			}
		});

		// Acción del Módulo de la clínica
		btnClinic.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AdminClinic admClinic = new AdminClinic(userHi, frame);
				admClinic.setVisible(true);
			}
		});
		
		// logica click item salir
				ItemOut.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						System.out.println("funciona");
						Login login = new Login(frame);
						login.setVisible(true);
						//session.close();

					}
				});

				// logica click cambiar contraseña
				ItemPass.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						DChangePass cP = new DChangePass(userHi);
						cP.setVisible(true);
						cP.setModal(true);
						System.out.println("PINCHADO");
						//session.close();

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
		contentPane.add(menuBar);
		menuBar.add(mnNewMenu);
		mnNewMenu.add(ItemName);
		mnNewMenu.add(ItemPass);
		mnNewMenu.add(ItemOut);

	}

}
