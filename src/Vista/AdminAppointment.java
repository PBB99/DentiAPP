package Vista;

import java.awt.Color;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.Connection;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import Modelo.CitaHibernate;
import Modelo.ClienteHibernate;
import Modelo.OdontogramaHibernate;
import Modelo.SpecialityHibernate;
import Modelo.TreatmentsHibernate;
import Modelo.UserHibernate;
import Vista.PruebaOdonto.Renderer;

import javax.swing.JMenuBar;
import java.awt.Point;
import javax.swing.JMenu;
import java.awt.Insets;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import java.awt.Rectangle;
import java.awt.Window.Type;
import java.awt.event.MouseAdapter;

import btndentiapp.ButtonDentiApp;
import com.toedter.calendar.JCalendar;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

public class AdminAppointment extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	// private ConexionMySQL conex;
	private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	private JFrame parent, frame;
	private SessionFactory instancia;
	private Session session;
	private UserHibernate userHi;
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
	public AdminAppointment(UserHibernate userHi, JFrame parent) {
		this.userHi = userHi;
		setType(Type.POPUP);
		setBounds(new Rectangle(10, 0, 0, 0));
		this.instancia = (SessionFactory) new Configuration().configure("hibernate.cfg.xml")
				.addAnnotatedClass(UserHibernate.class).addAnnotatedClass(CitaHibernate.class)
				.addAnnotatedClass(TreatmentsHibernate.class).addAnnotatedClass(ClienteHibernate.class)
				.addAnnotatedClass(SpecialityHibernate.class).buildSessionFactory();
		this.session = instancia.openSession();

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

		// barra oculat de arriba
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 1900, 50);

		menuBar.setMargin(new Insets(50, 0, 0, 25));
		menuBar.setOpaque(false);
		menuBar.setBorderPainted(false);
		menuBar.add(Box.createHorizontalGlue());
		
		// item
		JMenu mnNewMenu = new JMenu("");
		mnNewMenu.setIcon(new ImageIcon(getClass().getResource("/Resources/images/desplegable.png")));
		mnNewMenu.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);

		// nombre del doctor o admin
		JMenuItem ItemName = new JMenuItem("");
		//ItemName.setText(userHi.getNombre());

		// item cambio contraseña
		JMenuItem ItemPass = new JMenuItem("Cambiar Contraseña");
		ItemPass.setIcon(new ImageIcon(getClass().getResource("/Resources/images/keypass.png")));

		// item cerrar sesion
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
		btnAppointment.setToolTipText("Módulo de citas (Alt+C)");

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

		// Acción del Módulo de usuarios
		btnUsers.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AdminUsers admUsers = new AdminUsers(userHi, frame);
				admUsers.setVisible(true);
				session.close();
			}
		});

		// Acción del Módulo de pacientes
		btnCustomers.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AdminCustomers admCustomers = new AdminCustomers(userHi, frame);
				admCustomers.setVisible(true);
				session.close();
			}
		});

		// Acción del Módulo de inventario
		btnStock.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AdminStock admStock = new AdminStock(userHi, frame);
				admStock.setVisible(true);
				session.close();
			}
		});

		// Acción del Módulo de la clínica
		btnClinic.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AdminClinic admClinic = new AdminClinic(userHi, frame);
				admClinic.setVisible(true);
				session.close();
			}
		});

		// Acción del Módulo económico
		btnPayments.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AdminPayments admPayments = new AdminPayments(userHi, frame);
				admPayments.setVisible(true);
				session.close();
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
				session.close();

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
				session.close();

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

		// menuPane.add(btnClose);
		contentPane.add(menuBar);
		menuBar.add(mnNewMenu);
		mnNewMenu.add(ItemName);
		mnNewMenu.add(ItemPass);
		mnNewMenu.add(ItemOut);
		
		//Juan
		JCalendar calendar = new JCalendar();
		calendar.setBounds(1370, 135, 400, 300);
		calendar.setFont(new Font("Tahoma", Font.PLAIN, 18));
		contentPane.add(calendar);

		JComboBox cbUsuarios = new JComboBox();
		cbUsuarios.setBounds(1370, 450, 200, 25);
		cbUsuarios.setFont(new Font("Tahoma", Font.PLAIN, 20));
		contentPane.add(cbUsuarios);
		Query<UserHibernate> consultaUsers = session.createQuery("FROM UserHibernate", UserHibernate.class);
		List<UserHibernate> allUsers = consultaUsers.getResultList();
		for (int i = 0; i < allUsers.size(); i++) {
			if (allUsers.get(i).getEspecialidad().getId_especialidad() != 0) {
				cbUsuarios.addItem(allUsers.get(i));
			}
		}

		DefaultTableModel modelo = new DefaultTableModel();
		JTable table = new JTable();
		table.setBounds(420, 180, 500, 735);
		contentPane.add(table);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setModel(modelo);
		table.setShowVerticalLines(false);
		table.setShowHorizontalLines(false);
		table.setCellSelectionEnabled(true);
		table.setBackground(new Color(250, 250, 250));
		table.setSelectionBackground(new Color(148, 220, 219));
		table.setShowGrid(false);
		table.setBorder(null);
		table.setFont(new Font("Tahoma", Font.PLAIN, 18));
		table.setRowHeight(35);
		table.getTableHeader().setFont(new Font("Tahoma", Font.PLAIN, 18));
		table.getTableHeader().setBackground(new Color(148, 220, 219));
		table.getTableHeader().setBorder(new LineBorder(new Color(148, 220, 219)));
		
		Calendar fechaCalen = new GregorianCalendar();
		DateFormat formateador = new SimpleDateFormat("yyyy-M-dd");
		Query<CitaHibernate> consultaCitas = session.createQuery("FROM CitaHibernate where fecha=:fech and usuario_cita=:id",
				CitaHibernate.class);
		consultaCitas.setParameter("fech", calendar.getCalendar().getTime());
		consultaCitas.setParameter("id", (UserHibernate) cbUsuarios.getSelectedItem());
		List<CitaHibernate> allCitas = consultaCitas.getResultList();

//		System.out.println(formateador.format(fechaCalen.getTime()));
//		for (int i = 8; i < 15; i++) {
//			modelo.insertRow(modelo.getRowCount(), new Object[] { i + ":00", "" });
//			modelo.insertRow(modelo.getRowCount(), new Object[] { i + ":30", "" });
//		}
//		for (int i = 17; i < 20; i++) {
//			modelo.insertRow(modelo.getRowCount(), new Object[] { i + ":00", "" });
//			modelo.insertRow(modelo.getRowCount(), new Object[] { i + ":30", "" });
//		}
//
//		for (int i = 0; i < allCitas.size(); i++) {
//			java.util.Date dia = allCitas.get(i).getFecha();
//			if (formateador.format(fechaCalen.getTime()).equals(formateador.format(dia))) {
//				for (int j = 0; j < table.getModel().getRowCount(); j++) {
//					if (table.getModel().getValueAt(j, 0).equals(allCitas.get(i).getHora())) {
//						table.getModel().setValueAt(allCitas.get(i).getCliente().getNombre(), j, 1);
//					}
//				}
//			}
//		}
		
		loadCitas(table, calendar, (UserHibernate) cbUsuarios.getSelectedItem(), formateador);

		calendar.addPropertyChangeListener("calendar", new PropertyChangeListener() {

			@Override
			public void propertyChange(PropertyChangeEvent evt) {

				// TODO Auto-generated method stub
				for (int j = 1; j < table.getModel().getRowCount(); j++) {
					table.getModel().setValueAt("", j, 1);
					table.getModel().setValueAt("", j, 2);
				}

				Calendar fechaCal = (Calendar) evt.getNewValue();
				Query<CitaHibernate> consulta = session
						.createQuery("FROM CitaHibernate where fecha=:fech and usuario_cita=:id", CitaHibernate.class);
				consulta.setParameter("fech", fechaCal.getTime());
				consulta.setParameter("id", (UserHibernate) cbUsuarios.getSelectedItem());
				List<CitaHibernate> allCitas = consulta.getResultList();

				System.out.println(allCitas.size());

				for (int i = 0; i < allCitas.size(); i++) {
					Date dia = (Date) allCitas.get(i).getFecha();
					if (formateador.format(fechaCal.getTime()).equals(formateador.format(dia))) {
						for (int j = 0; j < table.getModel().getRowCount(); j++) {
							if (table.getModel().getValueAt(j, 0).equals(allCitas.get(i).getHora())) {
								table.getModel().setValueAt(allCitas.get(i).getCliente().getNombre(), j, 1);
								table.getModel().setValueAt(allCitas.get(i).getTratamiento().getNombre(), j, 2);
							}
						}
					}
				}
			}
		});

		cbUsuarios.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				// TODO Auto-generated method stub
				for (int j = 1; j < table.getModel().getRowCount(); j++) {
					table.getModel().setValueAt("", j, 1);
					table.getModel().setValueAt("", j, 2);
				}

				Query<CitaHibernate> consultaCitas = session
						.createQuery("FROM CitaHibernate where fecha=:fech and usuario_cita=:id", CitaHibernate.class);
				consultaCitas.setParameter("fech", calendar.getCalendar().getTime());
				consultaCitas.setParameter("id", (UserHibernate) cbUsuarios.getSelectedItem());
				List<CitaHibernate> allCitas = consultaCitas.getResultList();

				System.out.println(allCitas.size());

				for (int i = 0; i < allCitas.size(); i++) {
					Date dia = (Date) allCitas.get(i).getFecha();
					if (formateador.format(calendar.getCalendar().getTime()).equals(formateador.format(dia))) {
						for (int j = 0; j < table.getModel().getRowCount(); j++) {
							if (table.getModel().getValueAt(j, 0).equals(allCitas.get(i).getHora())) {
								table.getModel().setValueAt(allCitas.get(i).getCliente().getNombre(), j, 1);
								table.getModel().setValueAt(allCitas.get(i).getTratamiento().getNombre(), j, 2);
							}
						}
					}
				}

			}
		});

		table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent event) {
				String a = (table.getValueAt(table.getSelectedRow(), 0).toString());
				if (!event.getValueIsAdjusting()) {// This line prevents double events
					System.out.println("--");
					if (fechaCalen.getTime().before(calendar.getCalendar().getTime())
							|| (fechaCalen.getTime().getDay() == calendar.getCalendar().getTime().getDay())
									&& (fechaCalen.getTime().getMonth() == calendar.getCalendar().getTime().getMonth())
									&& (fechaCalen.getTime().getYear() == calendar.getCalendar().getTime().getYear())) {
						
							UserHibernate u = (UserHibernate) cbUsuarios.getSelectedItem();
							AdminInsertCita us = new AdminInsertCita(u.getDni(), calendar.getCalendar().getTime(),
									(table.getValueAt(table.getSelectedRow(), 0).toString()));
							us.setVisible(true);
							us.setLocationRelativeTo(null);
							us.addWindowListener(new WindowListener() {

								@Override
								public void windowClosed(WindowEvent e) {
									// TODO Auto-generated method stub
									actualizarTabla();
								}

								@Override
								public void windowOpened(WindowEvent e) {
									// TODO Auto-generated method stub

								}

								@Override
								public void windowClosing(WindowEvent e) {
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
								public void windowActivated(WindowEvent e) {
									// TODO Auto-generated method stub

								}

								@Override
								public void windowDeactivated(WindowEvent e) {
									// TODO Auto-generated method stub

								}

							});
						}
					}

				
				
			}

			public void actualizarTabla() {
				for (int j = 1; j < table.getModel().getRowCount(); j++) {
					table.getModel().setValueAt("", j, 1);
					table.getModel().setValueAt("", j, 2);
				}
				Query<CitaHibernate> consultaCitas = session
						.createQuery("FROM CitaHibernate where fecha=:fech and usuario_cita=:id", CitaHibernate.class);
				consultaCitas.setParameter("fech", calendar.getCalendar().getTime());
				consultaCitas.setParameter("id", (UserHibernate) cbUsuarios.getSelectedItem());
				List<CitaHibernate> allCitas = consultaCitas.getResultList();

				for (int i = 0; i < allCitas.size(); i++) {
					Date dia = (Date) allCitas.get(i).getFecha();
					if (formateador.format(calendar.getCalendar().getTime()).equals(formateador.format(dia))) {
						for (int j = 0; j < table.getModel().getRowCount(); j++) {
							if (table.getModel().getValueAt(j, 0).equals(allCitas.get(i).getHora())) {
								table.getModel().setValueAt(allCitas.get(i).getCliente().getNombre(), j, 1);
								table.getModel().setValueAt(allCitas.get(i).getTratamiento().getNombre(), j, 2);
							}
						}
					}
				}
			}
		});

		
	}
	
	public void actualizarTabla(JTable table, JCalendar calendar, UserHibernate us, DateFormat formateador) {
		for (int j = 1; j < table.getModel().getRowCount(); j++) {
			table.getModel().setValueAt("", j, 1);
			table.getModel().setValueAt("", j, 2);
		}
		
		Query<CitaHibernate> consultaCitas = session
				.createQuery("FROM CitaHibernate where fecha=:fech and usuario_cita=:id", CitaHibernate.class);
		consultaCitas.setParameter("fech", calendar.getCalendar().getTime());
		consultaCitas.setParameter("id", us);
		List<CitaHibernate> allCitas = consultaCitas.getResultList();

		for (int i = 0; i < allCitas.size(); i++) {
			Date dia = (Date) allCitas.get(i).getFecha();
			if (formateador.format(calendar.getCalendar().getTime()).equals(formateador.format(dia))) {
				for (int j = 0; j < table.getModel().getRowCount(); j++) {
					if (table.getModel().getValueAt(j, 0).equals(allCitas.get(i).getHora())) {
						table.getModel().setValueAt(allCitas.get(i).getCliente().getNombre(), j, 1);
						table.getModel().setValueAt(allCitas.get(i).getTratamiento().getNombre(), j, 2);
					}
				}
			}
		}
	}
	
	public void loadCitas(JTable tabla, JCalendar calendar, UserHibernate us, DateFormat formateador) {
		// Relaiza la consulta
		this.session = instancia.openSession();
		// Prepara la tabla
		DefaultTableModel model = new DefaultTableModel(new Object[][] {},
				new String[] { "Hora", "Cita", "Tratamiento"}) {
			@Override
			public boolean isCellEditable(int row, int column) {
				// all cells false
				return false;
			}
		};
		
		model.insertRow(0, new Object[] { "Hora", "Cita", "Tratamientos" });
		
		for (int i = 8; i < 15; i++) {
			model.insertRow(model.getRowCount(), new Object[] { i + ":00", "" });
			model.insertRow(model.getRowCount(), new Object[] { i + ":30", "" });
		}
		for (int i = 17; i < 20; i++) {
			model.insertRow(model.getRowCount(), new Object[] { i + ":00", "" });
			model.insertRow(model.getRowCount(), new Object[] { i + ":30", "" });
		}
		
		tabla.setModel(model);
		JTableHeader header = tabla.getTableHeader();

		// Carga los datos
		actualizarTabla(tabla, calendar, us, formateador);

		// Se alinea el texto de las columnas
		Renderer tcr = new Renderer();
		tcr.setHorizontalAlignment(SwingConstants.CENTER);
		tabla.getColumnModel().getColumn(0).setCellRenderer(tcr);
		tabla.setDefaultRenderer(Object.class, tcr);

		// Guarda el último id de las especialidades
//		if (!results.isEmpty()) {
//			lastCLiente = results.size();
//			System.out.println("kkkkk");
//		} else {
//			lastCLiente = 0;
//		}

	}
	
	public class Renderer extends DefaultTableCellRenderer {

		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {

			// Evalua en que fila esta

			if (row == 0) {
				setBackground(new Color(148, 220, 219));
			} else if (row % 2 == 0) {
				setBackground(new Color(220, 220, 220));
			} else {
				setBackground(new Color(250, 250, 250));
			}

			return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		}

	}
	
}
