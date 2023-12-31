package Vista;

import java.awt.Color;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.event.KeyEvent;

import java.awt.event.MouseAdapter;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import Controlador.ConexionMySQL;

import Modelo.SpecialityHibernate;
import Modelo.UserHibernate;
import btndentiapp.ButtonDentiApp;
import javax.persistence.*;

public class AdminUsers extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private ConexionMySQL conex;
	private JFrame parent, frame;
	private UserHibernate userHi;
	private SessionFactory instancia;
	private Session miSesion;
	private JTable tabla;
	private int control=0;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AdminUsers frame = new AdminUsers(null, null);
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
	public AdminUsers(UserHibernate userHi, JFrame parent) {

		this.userHi = userHi;
		this.frame = this;
		this.parent = parent;
		this.instancia = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(UserHibernate.class)
				.addAnnotatedClass(SpecialityHibernate.class).buildSessionFactory();
		this.miSesion = instancia.openSession();
		
		// -------------------- JFrame --------------------
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setResizable(false);
		setContentPane(contentPane);
		contentPane.setLayout(null);

		// -------------------- Componentes Gráficos --------------------
		// barra oculat de arriba
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 1900, 50);

		menuBar.setMargin(new Insets(50, 0, 0, 25));
		menuBar.setOpaque(false);
		menuBar.setBorderPainted(false);
		menuBar.add(Box.createHorizontalGlue());
		// Labels
		// añadir
		JLabel lAdd = new JLabel();
		lAdd.setText("Añadir");
		lAdd.setBounds(1477, 205, 100, 20);
		// Modificar
		JLabel lMod = new JLabel();
		lMod.setText("Modificar");
		lMod.setBounds(1577, 205, 100, 20);
		// Borrar
		JLabel lDel = new JLabel();
		lDel.setText("Dar de Baja");
		lDel.setBounds(1677, 205, 100, 20);
		// item
		JMenu mnNewMenu = new JMenu("");
		mnNewMenu.setIcon(new ImageIcon(getClass().getResource("/Resources/images/desplegable.png")));
		mnNewMenu.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);

		// nombre del doctor o admin
		JMenuItem ItemName = new JMenuItem("");
		ItemName.setText(userHi.getNombre());

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

		// panel donde colcare la tabla
		JScrollPane menuUsers = new JScrollPane();
		menuUsers.setBounds(277, 225, 1500, 700);
		// menuUsers.setBorder(BorderFactory.createEmptyBorder());
		menuUsers.setBorder(new LineBorder(new Color(0, 0, 0)));
		menuUsers.setBackground(new Color(148, 220, 219));
		// Boton Doctores
		JButton bDoctor = new JButton();
		bDoctor.setBounds(377, 205, 100, 20);
		bDoctor.setText("Doctor");

		// Boton admins
		JButton bAdmin = new JButton();

		bAdmin.setBounds(277, 205, 100, 20);
		bAdmin.setText("Admin");

		// La tabla
		tabla = new JTable();
		tabla.setShowVerticalLines(false);
		tabla.setShowHorizontalLines(false);
		tabla.setCellSelectionEnabled(true);
		tabla.setBounds(0, 0, 1500, 700);
		tabla.setBackground(new Color(250, 250, 250));
		tabla.setSelectionBackground(new Color(148, 220, 219));
		tabla.setShowGrid(false);
		tabla.setBorder(null);
		tabla.setFont(new Font("Tahoma", Font.PLAIN, 18));
		tabla.setRowHeight(35);
		tabla.getTableHeader().setFont(new Font("Tahoma", Font.PLAIN, 18));
		tabla.getTableHeader().setBackground(new Color(148, 220, 219));
		tabla.getTableHeader().setBorder(new LineBorder(new Color(148, 220, 219)));
//		
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
		ButtonDentiApp btnUsers = new ButtonDentiApp(0, 270, true,
				new ImageIcon(getClass().getResource("/Resources/images/users.png")));
		btnUsers.setToolTipText("Módulo de Usuarios (Alt+U)");
		
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
		ButtonDentiApp btnPayments = new ButtonDentiApp(0, 810, false,
				new ImageIcon(getClass().getResource("/Resources/images/paymentsGrey.png")));
		btnPayments.setToolTipText("Módulo Económico (Alt+E)");
		btnPayments.setMnemonic(KeyEvent.VK_E);
		
		// -------------------- Lógica --------------------
		// Acción para cerrar la ventana solo cuando se ha abierto la siguiente
		cargarTabla("Admin", tabla);
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

		// Logica de lables
		lAdd.addMouseListener(new MouseListener() {

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

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				AdminInsertUser u = new AdminInsertUser(instancia, frame, true);
				u.setVisible(true);
				u.addWindowListener(new WindowListener() {
					
					@Override
					public void windowOpened(WindowEvent e) {
						// TODO Auto-generated method stub
						
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
						if(control==0) {
							cargarTabla("admin", tabla);
						}else {
							cargarTabla("doctor", tabla);
						}
					}
				});

			}
		});
		lMod.addMouseListener(new MouseListener() {

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

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				ChangeUser c = new ChangeUser(instancia, frame, true);
				c.setVisible(true);
				c.addWindowListener(new WindowListener() {
					
					@Override
					public void windowOpened(WindowEvent e) {
						// TODO Auto-generated method stub
						
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
						
						if(control==0) {
							cargarTabla("admin", tabla);
							System.out.println("CARGA");
						}else {
							
							cargarTabla("doctor", tabla);
							System.out.println("SIGUE CARGANDO");
						}
						
						
					}
					
					@Override
					public void windowActivated(WindowEvent e) {
						// TODO Auto-generated method stub
						
					}
				});

			}
		});
		lDel.addMouseListener(new MouseListener() {

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

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				Query<UserHibernate> consulta2 = miSesion.createQuery("FROM UserHibernate", UserHibernate.class);
				List<UserHibernate> listaUsers = consulta2.getResultList();
				List<String> listaDnis = new ArrayList<String>();
				for (UserHibernate x : listaUsers) {
					listaDnis.add(x.getDni());
				}
				String resultado = (String) JOptionPane.showInputDialog(null, "Usuarios", "Elegir",
						JOptionPane.QUESTION_MESSAGE, null, listaDnis.toArray(), listaDnis.toArray()[0]);
				System.out.println(resultado);
				UserHibernate user = miSesion.get(UserHibernate.class, resultado);
				user.setDni(resultado);
				user.setEstado(0);
				System.out.println(user.getEstado());
				miSesion.beginTransaction();
				miSesion.update(user);
				miSesion.getTransaction().commit();
				
			}

		});

		// Acción del Módulo de citas
		btnAppointment.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AdminAppointment admAppointment = new AdminAppointment(userHi, frame);
				admAppointment.setVisible(true);
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

		// Acción del Módulo económico
		btnPayments.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AdminPayments admPayments = new AdminPayments(userHi, frame);
				admPayments.setVisible(true);
			}
		});

		// Accion boton Doctor
		bDoctor.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				cargarTabla("Doctor", tabla);
				control=1;

			}
		});
		// Accion boton Admin
		bAdmin.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				cargarTabla("Admin", tabla);
				control=0;

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
				miSesion.close();

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
				

			}
		});

		// -------------------- Adiciones a los paneles --------------------
		contentPane.add(menuPane);
		contentPane.add(menuUsers);
		contentPane.add(bDoctor);
		contentPane.add(bAdmin);
		contentPane.add(menuBar);
		contentPane.add(lAdd);
		contentPane.add(lMod);
		contentPane.add(lDel);
		menuPane.add(lblLogo);
		menuPane.add(btnAppointment);
		menuPane.add(btnUsers);
		menuPane.add(btnCustomers);
		menuPane.add(btnStock);
		menuPane.add(btnClinic);
		menuPane.add(btnPayments);
		menuUsers.add(tabla);
		menuUsers.setViewportView(tabla);
		menuBar.add(mnNewMenu);
		mnNewMenu.add(ItemName);
		mnNewMenu.add(ItemPass);
		mnNewMenu.add(ItemOut);

//		JButton btnNewButton = new JButton("PRUEBA");
//		btnNewButton.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//			//	AdminInsertUser us = new AdminInsertUser(userHi,  frame);
//				//us.setVisible(true);
//
//			}
//		});
//		btnNewButton.setBounds(524, 525, 89, 23);
//		contentPane.add(btnNewButton);

		/*
		 * JInternalFrame internalFrame = new JInternalFrame("New JInternalFrame");
		 * internalFrame.setBorder(null); internalFrame.setBounds(185, 135, 1685, 810);
		 * internalFrame.setBorder(new LineBorder(new Color(0, 0, 0)));
		 * contentPane.add(internalFrame); internalFrame.setVisible(true);
		 */
	}

//-----------------------Métodos------------------------------------------
	public String mostrarEspecialidades(UserHibernate u) {
		String total = "";
		for (int i = 0; i < u.getSpeciality().size(); i++) {
			total = total + u.getSpeciality().get(i).getEspecialidad() + ",";
		}
		return total;
	}

	public void cargarTabla(String nombreTabla, JTable tablaDoctores) {
		// Relaiza la consulta de todos los usuarios y los extrae
		miSesion=instancia.openSession();
		String hql = "FROM UserHibernate ";
		Query<UserHibernate> consulta = miSesion.createQuery(hql, UserHibernate.class);
		List<UserHibernate> userList = consulta.getResultList();
		List<UserHibernate> admin = new ArrayList<UserHibernate>();
		// preparacion de la tabla

		DefaultTableModel model = new DefaultTableModel(new Object[][] {},
				new String[] { "DNI", "Nombre", "Apellido", "Especialidad" }) {
			@Override
			public boolean isCellEditable(int row, int column) {
				// all cells false
				return false;
			}
		};
		// este string entrará por parametro y sera elegido en función de si pulsa el
		// boron Doctor o Administrador
		if (nombreTabla.equalsIgnoreCase("Admin")) {

			for (UserHibernate x : userList) {
				// dentro de los admin solo puede tener una espcialidad, pero en el caso de que
				// se le haya metido en varias ocasiones la especialidad admin tenemos en cuenta
				// que pueda tener mas especialidades
				if (!x.getSpeciality().isEmpty()) {

					if (x.getSpeciality().get(0).getId_especialidad() == 0) {
						admin.add(x);
					}
				}

			}
			tablaDoctores.setModel(model);
			JTableHeader header = tablaDoctores.getTableHeader();
			if (admin.size() < 18) {
				model.setRowCount(19);
			} else {
				model.setRowCount(admin.size());
			}

			int fila = 0;

			for (UserHibernate y : admin) {
				model.setValueAt(y.getDni(), fila, 0);
				model.setValueAt(y.getNombre(), fila, 1);
				model.setValueAt(y.getApellido(), fila, 2);
				model.setValueAt(mostrarEspecialidades(y), fila, 3);
				fila++;
			}

			// Se alinea el texto de las columnas
			Renderer tcr = new Renderer();
			tcr.setHorizontalAlignment(SwingConstants.CENTER);
			tablaDoctores.getColumnModel().getColumn(0).setCellRenderer(tcr);
			tablaDoctores.setDefaultRenderer(Object.class, tcr);
		} else {
			for (UserHibernate x : userList) {
				// los doctores tienen al menos una especialidad con posbilidad de tener mas y
				// distintas de cero
				if (!x.getSpeciality().isEmpty()) {

					if (x.getSpeciality().get(0).getId_especialidad() != 0) {
						admin.add(x);
					}
				}

			}
			tablaDoctores.setModel(model);
			if (admin.size() < 18) {
				model.setRowCount(19);
			} else {
				model.setRowCount(admin.size());
			}
			JTableHeader header = tablaDoctores.getTableHeader();
			
			int fila = 0;

			for (UserHibernate y : admin) {
				model.setValueAt(y.getDni(), fila, 0);
				model.setValueAt(y.getNombre(), fila, 1);
				model.setValueAt(y.getApellido(), fila, 2);
				model.setValueAt(mostrarEspecialidades(y), fila, 3);
				fila++;
			}

			// Se alinea el texto de las columnas
			Renderer tcr = new Renderer();
			tcr.setHorizontalAlignment(SwingConstants.CENTER);
			tablaDoctores.getColumnModel().getColumn(0).setCellRenderer(tcr);
			tablaDoctores.setDefaultRenderer(Object.class, tcr);
		}
	}

	// clase Render
	public class Renderer extends DefaultTableCellRenderer {

		private static final long serialVersionUID = 1L;

		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {

			// Evalua en que fila esta
			if (row % 2 == 0) {
				setBackground(new Color(220, 220, 220));
			} else {
				setBackground(new Color(250, 250, 250));
			}

			return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		}

	}

}