package Vista;

import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

import Modelo.CitaHibernate;
import Modelo.ClienteHibernate;
import Modelo.OdontogramaHibernate;
import Modelo.Specialist;
import Modelo.SpecialityHibernate;
import Modelo.TreatmentsHibernate;
import Modelo.User;
import Modelo.UserHibernate;
import Otros.RoundedPanel;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;
import Controlador.ConexionMySQL;
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
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.border.LineBorder;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.w3c.dom.events.EventTarget;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;

public class Login extends JFrame {
	// declaracion de variables

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private ConexionMySQL conex;
	private Connection cn;
	private JTextField tfDNI;
	private JTextField tfPassword;

	private List<UserHibernate> userList;

	private JFrame parent, frame;
	private SessionFactory instancia;
	private Session session;
	private boolean mostrar=false;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login frame = new Login(null);
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
	public Login(JFrame parent) {
		// ---------------------------------------------Conexiones--------------------------------------
		// declaracion de las conexiones
//		this.conex = new ConexionMySQL();
//		conex.conectar();
		this.instancia = (SessionFactory) new Configuration().configure("hibernate.cfg.xml")
				.addAnnotatedClass(UserHibernate.class).addAnnotatedClass(CitaHibernate.class)
				.addAnnotatedClass(TreatmentsHibernate.class).addAnnotatedClass(ClienteHibernate.class)
				.addAnnotatedClass(SpecialityHibernate.class).addAnnotatedClass(OdontogramaHibernate.class).
				buildSessionFactory();
		this.session = instancia.openSession();
		this.session.beginTransaction();
//		us = new UserController(conex);
//		sp = new SpecialistController(conex);
		// traemos todos los datos de la tabla especialista a nuestro ArrayList del
		// modelo Specialist
		try {
//			speciaList = sp.getAllSpecialist();
//			userList = us.getAllUsers();
			String hql = "FROM UserHibernate";
			Query<UserHibernate> consulta = session.createQuery(hql, UserHibernate.class);
			userList = consulta.getResultList();
			System.out.println(userList.getLast().getEspecialidad());
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		// ---------------------------------------------JFrame--------------------------------------
		this.frame = this;
		this.parent = parent;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		contentPane = new JPanel() {
			@Override
			public void paint(Graphics g) {
				Image bg = new ImageIcon(getClass().getResource("/Resources/images/bg_Login.jpg")).getImage();
				g.drawImage(bg, 0, 0, getWidth(), getHeight(), this);
				setOpaque(false);
				super.paint(g);
			}
		};
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setResizable(false);
	
		setContentPane(contentPane);
		contentPane.setLayout(null);

		// --------------------Componentes--------------------
		// Login Layout
		JPanel loginPane = new RoundedPanel(50,Color.WHITE);
		loginPane.setOpaque(false);
		loginPane.setBounds(685, 165, 550, 750);
		loginPane.setLayout(null);

		// Label del Logo
		JLabel lblLogo = new JLabel("");
		// lblLogo.setBorder(new LineBorder(new Color(0, 0, 0)));
		lblLogo.setBounds(0, 0, 550, 200);
		lblLogo.setIcon(new ImageIcon(getClass().getResource("/Resources/images/logoFinal.png")));

		// Label de usurario
		JLabel lblDNI = new JLabel("Usuario");
		// lblDNI.setBorder(new LineBorder(new Color(0, 0, 0)));
		lblDNI.setBounds(75, 250, 400, 50);
		lblDNI.setFont(new Font("Tahoma", Font.PLAIN, 30));

		// Label de Contraseña
		JLabel lblPassword = new JLabel("Contraseña");
		// lblPassword.setBorder(new LineBorder(new Color(0, 0, 0)));
		lblPassword.setBounds(75, 425, 400, 50);
		lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 30));

		// TexField DNI
		tfDNI = new JTextField();
		// tfDNI.setBorder(new LineBorder(new Color(0, 0, 0)));
		tfDNI.setBounds(75, 315, 400, 50);
		tfDNI.setFont(new Font("Tahoma", Font.PLAIN, 25));

		// TextField Password
		tfPassword = new JPasswordField();
		// tfPassword.setBorder(new LineBorder(new Color(0, 0, 0)));
		tfPassword.setBounds(75, 490, 400, 50);
		tfPassword.setFont(new Font("Tahoma", Font.PLAIN, 25));

		// Botón de Login
		JButton btnLogin = new JButton("INICIAR SESIÓN");
		btnLogin.setMnemonic(KeyEvent.VK_ENTER);
		btnLogin.setFont(new Font("Tahoma", Font.PLAIN, 19));
		btnLogin.setBounds(170, 674, 180, 50);
		btnLogin.setBackground(new Color(148, 220, 219));
		btnLogin.setForeground(Color.white);
		
		//ojo mostrar o no contraseña
		JLabel lblOjo=new JLabel();
		lblOjo.setBounds(485, 495, 30, 30);
		lblOjo.setIcon(new ImageIcon(getClass().getResource("/Resources/images/mostrar.png")));
		loginPane.add(lblOjo);
		
		//Ayuda
		JButton btnHelp = new JButton();
		btnHelp.setBounds(480, 683, 40, 40);
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
		loginPane.add(btnHelp);
		
		
		// ----------------------------------------------LOGICA----------------------------------------------------------
		
		//Mostrar o no contraseña
		             
		lblOjo.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e)  
		    {  
		       // you can open a new frame here as
		       // i have assumed you have declared "frame" as instance variable
				if(mostrar==false) {
					//mostrar contrseña
					lblOjo.setIcon(new ImageIcon(getClass().getResource("/Resources/images/ocultar.png")));
					((JPasswordField) tfPassword).setEchoChar((char)0);
					mostrar=true;
				}else {
					//no mostrar contraseña
					lblOjo.setIcon(new ImageIcon(getClass().getResource("/Resources/images/mostrar.png")));
					((JPasswordField) tfPassword).setEchoChar('•');
					mostrar=false;
				}
				

		    }  
		});
		// Acción para cerrar la ventana solo cuando se ha abierto la siguiente
		btnLogin.addMouseListener(new MouseListener() {
			
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
				btnLogin.setBackground(new Color(148, 220, 219));
				
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				btnLogin.setBackground(new Color(20, 220, 219));
				
				
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		this.addWindowListener(new WindowListener() {

			@Override
			public void windowOpened(WindowEvent e) {
				try {
					Thread.sleep(300);
				} catch (Exception e2) {
					e2.printStackTrace();
				}
				if (parent != null) {
					parent.dispose();
				}

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

		// acciones del boton login, carga dos tablas, compara los datos el usuario
		// introducido y da paso o no a la siguiente pantalla

		// ----------------------------------------------LOGICA----------------------------------------------------------
		// Acción para cerrar la ventana solo cuando se ha abierto la siguiente
		this.addWindowListener(new WindowListener() {

			@Override
			public void windowOpened(WindowEvent e) {
				try {
					Thread.sleep(300);
				} catch (Exception e2) {
					e2.printStackTrace();
				}
				if (parent != null) {
					parent.dispose();
				}
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

		// acciones del boton login, carga dos tablas, compara los datos el usuario
		// introducido y da paso o no a la siguiente pantalla

		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String username = tfDNI.getText();
				String password = tfPassword.getText();
				boolean aux2 = true;

				for (UserHibernate x : userList) {
					// si coinciden nombre y contraseña con alguno de los usuarios
					if ((x.getDni().toString().equals(username)) && (x.getContraseña().toString().equals(password)) && aux2) {
						// esta dado de alta
						aux2 = false;
						if (x.getEstado()==1) {
							if (x.getEspecialidad().getId_especialidad() == 0) {
								// se abre la pantalla de admin
								AdminAppointment pa = new AdminAppointment(x, frame);
								pa.setVisible(true);
								session.close();
								try {

									// Ponemos a "Dormir" el programa para que cargue
									Thread.sleep(500);
								} catch (Exception ex) {
									System.out.println(ex);
								}
								// Ponemos a "Dormir" el programa para que cargue
								try {
									Thread.sleep(500);
								} catch (InterruptedException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
							} else {
								DoctorAppointment pd = new DoctorAppointment(x, frame);
								pd.setVisible(true);
								session.close();
							}
							try {

								// Ponemos a "Dormir" el programa para que cargue
								Thread.sleep(500);
							} catch (Exception ex) {
								System.out.println(ex);
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
		
		//Acción de atajo de teclado para iniciar sesion
		tfDNI.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode()==KeyEvent.VK_ENTER) {
					String username = tfDNI.getText();
					String password = tfPassword.getText();
					boolean aux2 = true;

					for (UserHibernate x : userList) {
						// si coinciden nombre y contraseña con alguno de los usuarios
						if ((x.getDni().toString().equals(username)) && (x.getContraseña().toString().equals(password)) && aux2) {
							// esta dado de alta
							aux2 = false;
							if (x.getEstado()==1) {
								if (x.getEspecialidad().getId_especialidad() == 0) {
									// se abre la pantalla de admin
									AdminAppointment pa = new AdminAppointment(x, frame);
									pa.setVisible(true);
									session.close();
									try {

										// Ponemos a "Dormir" el programa para que cargue
										Thread.sleep(500);
									} catch (Exception ex) {
										System.out.println(ex);
									}
									// Ponemos a "Dormir" el programa para que cargue
									try {
										Thread.sleep(500);
									} catch (InterruptedException e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									}
								} else {
									DoctorAppointment pd = new DoctorAppointment(x, frame);
									pd.setVisible(true);
									session.close();
								}
								try {

									// Ponemos a "Dormir" el programa para que cargue
									Thread.sleep(500);
								} catch (Exception ex) {
									System.out.println(ex);
								}
							} else {// esta dado de baja
								JOptionPane.showMessageDialog(frame, "Cuidado", "Este usuario ya no es válido",
										JOptionPane.WARNING_MESSAGE);
								break;
							}
						}
					}
					if (aux2 == true) {
						JOptionPane.showMessageDialog(frame, "Su usuario o contraseña no coincide.\n Intentelo de nuevo",
								"Error", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		
		//Acción de atajo de teclado para iniciar sesion
		tfPassword.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode()==KeyEvent.VK_ENTER) {
					String username = tfDNI.getText();
					String password = tfPassword.getText();
					boolean aux2 = true;

					for (UserHibernate x : userList) {
						// si coinciden nombre y contraseña con alguno de los usuarios
						if ((x.getDni().toString().equals(username)) && (x.getContraseña().toString().equals(password)) && aux2) {
							// esta dado de alta
							aux2 = false;
							if (x.getEstado()==1) {
								if (x.getEspecialidad().getId_especialidad() == 0) {
									// se abre la pantalla de admin
									AdminAppointment pa = new AdminAppointment(x, frame);
									pa.setVisible(true);
									session.close();
									try {

										// Ponemos a "Dormir" el programa para que cargue
										Thread.sleep(500);
									} catch (Exception ex) {
										System.out.println(ex);
									}
									// Ponemos a "Dormir" el programa para que cargue
									try {
										Thread.sleep(500);
									} catch (InterruptedException e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									}
								} else {
									DoctorAppointment pd = new DoctorAppointment(x, frame);
									pd.setVisible(true);
									session.close();
								}
								try {

									// Ponemos a "Dormir" el programa para que cargue
									Thread.sleep(500);
								} catch (Exception ex) {
									System.out.println(ex);
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
			}
		});
		
		//Acción de atajo de teclado para iniciar sesion
		btnLogin.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode()==KeyEvent.VK_ENTER) {
					String username = tfDNI.getText();
					String password = tfPassword.getText();
					boolean aux2 = true;

					for (UserHibernate x : userList) {
						// si coinciden nombre y contraseña con alguno de los usuarios
						if ((x.getDni().toString().equals(username)) && (x.getContraseña().toString().equals(password)) && aux2) {
							// esta dado de alta
							aux2 = false;
							if (x.getEstado()==1) {
								if (x.getEspecialidad().getId_especialidad() == 0) {
									// se abre la pantalla de admin
									AdminAppointment pa = new AdminAppointment(x, frame);
									pa.setVisible(true);
									session.close();
									try {

										// Ponemos a "Dormir" el programa para que cargue
										Thread.sleep(500);
									} catch (Exception ex) {
										System.out.println(ex);
									}
									// Ponemos a "Dormir" el programa para que cargue
									try {
										Thread.sleep(500);
									} catch (InterruptedException e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									}
								} else {
									DoctorAppointment pd = new DoctorAppointment(x, frame);
									pd.setVisible(true);
									session.close();
								}
								try {

									// Ponemos a "Dormir" el programa para que cargue
									Thread.sleep(500);
								} catch (Exception ex) {
									System.out.println(ex);
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
			}
		});
		
		//Easter egg
		ArrayList<Integer> combo = new ArrayList<>();
		btnLogin.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {

			}

			@Override
			public void keyPressed(KeyEvent e) {
				combo.add(e.getKeyCode());
				if (combo.size() > 1) {
					if (combo.contains(77) && combo.contains(83)) {
						combo.clear();
						Pedro p = new Pedro(756,318,2);
						p.setVisible(true);
					}
				}

			}

			@Override
			public void keyReleased(KeyEvent e) {

				if (combo != null) {
					if (combo.contains(77) || combo.contains(83)) {
						combo.remove(e.getKeyCode());
					}
				}

			}
		});
		String texto="<html><body>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; © 2024 DentiAPP <br> Todos los Derechos Reservados</body></html>";
		JLabel lblCopy = new JLabel(texto);
		lblCopy.setForeground(Color.GRAY);
		lblCopy.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD, 17));
		lblCopy.setBounds(829, 920, 250, 200);
		contentPane.add(lblCopy);
// -------------------------------------ADICIONES AL PANEL Y AL LOGIN
// PANEL-----------------
		contentPane.add(loginPane);
		loginPane.add(btnLogin);
		loginPane.add(tfPassword);
		loginPane.add(tfDNI);
		loginPane.add(lblPassword);
		loginPane.add(lblDNI);
		loginPane.add(lblLogo);
	}
}
