package Vista;

import java.awt.Color;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
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

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
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
import Otros.*;
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
import com.github.lgooddatepicker.components.CalendarPanel;

public class AdminAppointment extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	// private ConexionMySQL conex;
	private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	private JFrame parent, frame;
	private SessionFactory instancia;
	private Session session;
	private UserHibernate userHi;
	private LineBorder lb = new LineBorder(new Color(240, 240, 240), 3, true);
	private Font font = new Font("Dialog", Font.BOLD, 15);

	/**
	 * Launch the application.
	 */

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AdminAppointment frame = new AdminAppointment(null, null);
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
		setLocationRelativeTo(null);
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
		JMenu mnNewMenu = new JMenu(userHi.getDni());
		mnNewMenu.setIcon(new ImageIcon(getClass().getResource("/Resources/images/definitiva.png")));
		mnNewMenu.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		mnNewMenu.setOpaque(false);
		mnNewMenu.setBackground(new Color(0,0,0,0));

		// nombre del doctor o admin
		JMenuItem ItemName = new JMenuItem("");
		// ItemName.setText(userHi.getNombre());

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

		// Citas
		JLabel lblCitas = new JLabel("Citas");
		lblCitas.setBounds(250, 10, 150, 135);
		lblCitas.setFont(new Font("Tahoma", Font.PLAIN, 60));

		// Panel para las citas
		JPanel panelCitas = new RoundedPanel(50, new Color(148, 220, 219));
		panelCitas.setBounds(250, 202, 1050, 800);
		panelCitas.setOpaque(false);
		panelCitas.setLayout(null);
		contentPane.add(panelCitas);

		// Panel secundario para las citas
		JPanel panelTitleCitas = new JPanel();
		panelTitleCitas.setBounds(15, 15, 1020, 770);
		panelTitleCitas.setBorder(
				new TitledBorder(lb, "  Citas  ", TitledBorder.LEFT, TitledBorder.TOP, font, new Color(51, 51, 51)));
		panelTitleCitas.setOpaque(false);
		panelTitleCitas.setLayout(null);
		panelCitas.add(panelTitleCitas);

		// ScrollPane para cargar la talbla inventario
		JScrollPane menuTableStock = new JScrollPane();
		menuTableStock.setBorder(BorderFactory.createEmptyBorder());
		menuTableStock.setBounds(5, 20, 1011, 745);
		menuTableStock.setBackground(new Color(148, 220, 219));
		panelTitleCitas.add(menuTableStock);
		
		// Tabla de citas
		DefaultTableModel modelo = new DefaultTableModel();
		JTable table = new JTable();
		table.setBounds(5, 20, 1011, 745);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setModel(modelo);
		table.setShowVerticalLines(false);
		table.setShowHorizontalLines(false);
		table.setCellSelectionEnabled(true);
		table.setBackground(new Color(240, 240, 240));
		table.setSelectionBackground(new Color(148, 220, 219));
		table.setShowGrid(false);
		table.setBorder(null);
		table.setFont(new Font("Tahoma", Font.PLAIN, 18));
		table.setRowHeight(35);
		table.getTableHeader().setFont(new Font("Tahoma", Font.PLAIN, 18));
		table.getTableHeader().setBackground(new Color(148, 220, 219));
		table.getTableHeader().setBorder(new LineBorder(new Color(148, 220, 219)));
		panelTitleCitas.add(table);
		menuTableStock.add(table);
		menuTableStock.setViewportView(table);
		
		// Panel para el calendario
		JPanel panelCalendar = new RoundedPanel(null, 50, new Color(148, 220, 219));
		panelCalendar.setBounds(1390, 202, 450, 350);
		panelCalendar.setOpaque(false);
		panelCalendar.setLayout(null);
		contentPane.add(panelCalendar);

		// Panel secundario para el calendario
		JPanel panelTitleCalendar = new JPanel();
		panelTitleCalendar.setBounds(15, 15, 415, 315);
		panelTitleCalendar.setBorder(new TitledBorder(lb, "  Calendario  ", TitledBorder.LEFT, TitledBorder.TOP, font,
				new Color(51, 51, 51)));
		panelTitleCalendar.setOpaque(false);
		panelCalendar.add(panelTitleCalendar);

		// Calendario
		JCalendar calendar = new JCalendar();
		calendar.setBounds(25, 25, 400, 300);
		calendar.setFont(new Font("Tahoma", Font.PLAIN, 18));
		// panelCalendar.add(calendar);

		CalendarPanel calendarPanel = new CalendarPanel();
		GridBagLayout gbl_calendarPanel = (GridBagLayout) calendarPanel.getLayout();
		gbl_calendarPanel.rowWeights = new double[] { 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0 };
		gbl_calendarPanel.rowHeights = new int[] { 6, 0, 5, 138, 5, 0, 5 };
		gbl_calendarPanel.columnWeights = new double[] { 1.0, 1.0, 1.0 };
		gbl_calendarPanel.columnWidths = new int[] { 5, 219, 5 };
		calendarPanel.setBounds(25, 25, 400, 300);
		calendarPanel.setOpaque(false);
		panelCalendar.add(calendarPanel);

		// Panel para los doctores
		JPanel panelDoctors = new RoundedPanel(50, new Color(148, 220, 219));
		panelDoctors.setBounds(1390, 580, 450, 420);
		panelDoctors.setOpaque(false);
		panelDoctors.setLayout(null);
		contentPane.add(panelDoctors);

		// Panel secundario para doctores
		JPanel panelTitleDoctores = new JPanel();
		panelTitleDoctores.setBounds(15, 15, 415, 390);
		panelTitleDoctores.setBorder(
				new TitledBorder(lb, "  Doctores  ", TitledBorder.LEFT, TitledBorder.TOP, font, new Color(0, 0, 0)));
		panelTitleDoctores.setOpaque(false);
		panelTitleDoctores.setLayout(new GridLayout(0, 1, 0, 0));
		panelDoctors.add(panelTitleDoctores);

		// ScrollPane doctores
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(30, 30, 395, 368);
		scrollPane.setBorder(null);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		panelDoctors.add(scrollPane);

		JPanel panelSCrollDoctors = new JPanel();
		panelSCrollDoctors.setLayout(new GridLayout(0, 1, 0, 0));
		panelSCrollDoctors.setBackground(new Color(148, 220, 219));
		scrollPane.setViewportView(panelSCrollDoctors);

		// Cargar los doctores en el panel de doctores
		ButtonGroup buttonGroup = new ButtonGroup();

		Query<UserHibernate> consultaUsers = session.createQuery("FROM UserHibernate", UserHibernate.class);
		List<UserHibernate> allUsers = consultaUsers.getResultList();

		for (int i = 0; i < allUsers.size(); i++) {
			if (allUsers.get(i).getEspecialidad().getId_especialidad() != 0) {
				RadioButton radioButton = new RadioButton(
						allUsers.get(i).getNombre() + " " + allUsers.get(i).getApellido(), allUsers.get(i).getDni());
				radioButton.setFont(font);
				radioButton.setOpaque(false);
				buttonGroup.add(radioButton);
				panelSCrollDoctors.add(radioButton);
				if(i==0) {
					radioButton.setSelected(true);
					actualizarTabla(table, calendar, userHi);
					System.out.println("Seleccionaste: " + radioButton.getText() + " DNI: " + radioButton.getId());
				}
				radioButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						System.out.println("Seleccionaste: " + radioButton.getText() + " DNI: " + radioButton.getId());
						//System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
					}
				});
			}
		}

		// Doctores
		JComboBox cbUsuarios = new JComboBox();
		cbUsuarios.setBounds(1370, 450, 200, 25);
		cbUsuarios.setFont(new Font("Tahoma", Font.PLAIN, 20));
		// contentPane.add(cbUsuarios);
		// Query<UserHibernate> consultaUsers = session.createQuery("FROM
		// UserHibernate", UserHibernate.class);
		// List<UserHibernate> allUsers = consultaUsers.getResultList();
		for (int i = 0; i < allUsers.size(); i++) {
			if (allUsers.get(i).getEspecialidad().getId_especialidad() != 0) {
				cbUsuarios.addItem(allUsers.get(i));
			}
		}

		// -------------------- Lógica --------------------
		// Acción para cerrar la ventana solo cuando se ha abierto la siguiente
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
		contentPane.add(menuBar);
		contentPane.add(lblCitas);

		menuPane.add(lblLogo);
		menuPane.add(btnAppointment);
		menuPane.add(btnUsers);
		menuPane.add(btnCustomers);
		menuPane.add(btnStock);
		menuPane.add(btnClinic);
		menuPane.add(btnPayments);
		menuBar.add(mnNewMenu);

		mnNewMenu.add(ItemName);
		mnNewMenu.add(ItemPass);
		mnNewMenu.add(ItemOut);

		Calendar fechaCalen = new GregorianCalendar();
		DateFormat formateador = new SimpleDateFormat("yyyy-M-dd");
		Query<CitaHibernate> consultaCitas = session
				.createQuery("FROM CitaHibernate where fecha=:fech and usuario_cita=:id", CitaHibernate.class);
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

	public void actualizarTabla(JTable table, JCalendar calendar, UserHibernate us) {
		for (int j = 1; j < table.getModel().getRowCount(); j++) {
			table.getModel().setValueAt("", j, 1);
			table.getModel().setValueAt("", j, 2);
		}
		DateFormat formateador = new SimpleDateFormat("yyyy-M-dd");
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
				new String[] { "Hora", "Cita", "Tratamiento" }) {
			@Override
			public boolean isCellEditable(int row, int column) {
				// all cells false
				return false;
			}
		};

		//model.insertRow(0, new Object[] { "Hora", "Cita", "Tratamientos" });

		for (int i = 8; i <= 15; i++) {
			model.insertRow(model.getRowCount(), new Object[] { i + ":00", "" });
			if (i < 15) {
				model.insertRow(model.getRowCount(), new Object[] { i + ":30", "" });
			}
		}
		for (int i = 17; i <= 20; i++) {
			model.insertRow(model.getRowCount(), new Object[] { i + ":00", "" });
			if (i < 20) {
				model.insertRow(model.getRowCount(), new Object[] { i + ":30", "" });
			}
		}

		tabla.setModel(model);
		JTableHeader header = tabla.getTableHeader();

		// Carga los datos
		actualizarTabla(tabla, calendar, us);

		// Se alinea el texto de las columnas
		Renderer tcr = new Renderer();
		tcr.setHorizontalAlignment(SwingConstants.CENTER);
		tabla.setDefaultRenderer(Object.class, tcr);

	}

	public class Renderer extends DefaultTableCellRenderer {

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
