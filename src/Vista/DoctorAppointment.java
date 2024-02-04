package Vista;

import java.awt.Color;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
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

import Controlador.ConexionMySQL;
import Modelo.CitaHibernate;
import Modelo.ClienteHibernate;
import Modelo.SpecialityHibernate;
import Modelo.TreatmentsHibernate;
import Modelo.UserHibernate;
import Otros.RoundedPanel;
import Vista.AdminAppointment.Renderer;

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

import com.github.lgooddatepicker.components.CalendarPanel;
import com.github.lgooddatepicker.optionalusertools.CalendarListener;
import com.github.lgooddatepicker.zinternaltools.CalendarSelectionEvent;
import com.github.lgooddatepicker.zinternaltools.YearMonthChangeEvent;
import com.toedter.calendar.JCalendar;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

public class DoctorAppointment extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private ConexionMySQL conex;
	private JFrame parent, frame;
	private SessionFactory instancia;
	private Session session;
	private LineBorder lb = new LineBorder(new Color(240, 240, 240), 3, true);
	private Font font = new Font("Dialog", Font.BOLD, 15);
	private LineBorder lb2 = new LineBorder(new Color(148, 220, 219), 3, true);
	private UserHibernate userHi;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DoctorAppointment frame = new DoctorAppointment(null, null);
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
	public DoctorAppointment(UserHibernate mainUser, JFrame parent) {
		// -------------------- JFrame --------------------
		this.frame = this;
		this.parent = parent;
		this.userHi=mainUser;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setResizable(false);
		setContentPane(contentPane);
		contentPane.setLayout(null);
		this.instancia = (SessionFactory) new Configuration().configure("hibernate.cfg.xml")
				.addAnnotatedClass(UserHibernate.class).addAnnotatedClass(CitaHibernate.class)
				.addAnnotatedClass(TreatmentsHibernate.class).addAnnotatedClass(ClienteHibernate.class)
				.addAnnotatedClass(SpecialityHibernate.class).buildSessionFactory();
		this.session = instancia.openSession();
		this.session.beginTransaction();

		// -------------------- Componentes --------------------
		//nombre esquina
		
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
		// barra oculat de arriba
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 1900, 50);

		menuBar.setMargin(new Insets(50, 0, 0, 25));
		menuBar.setOpaque(false);
		menuBar.setBorderPainted(false);
		menuBar.add(Box.createHorizontalGlue());

		// item
		JMenu mnNewMenu = new JMenu(mainUser.getDni());
		mnNewMenu.setIcon(new ImageIcon(getClass().getResource("/Resources/images/definitiva.png")));
		mnNewMenu.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		mnNewMenu.setOpaque(false);
		mnNewMenu.setBackground(new Color(0, 0, 0, 0));

		// nombre del doctor o admin
		JMenuItem ItemName = new JMenuItem(mainUser.getNombre());
		// ItemName.setText(userHi.getNombre());

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
		contentPane.add(menuPane);
		menuPane.setLayout(null);

		// Label del Logo del Menú
		JLabel lblLogo = new JLabel();
		// lblLogo.setBorder(new LineBorder(new Color(0, 0, 0)));
		lblLogo.setBounds(0, 0, 135, 135);
		lblLogo.setIcon(new ImageIcon(getClass().getResource("/Resources/images/logoMenu.png")));

		// Botón de citas
		ButtonDentiApp btnAppointment = new ButtonDentiApp(0, 135, true,
				new ImageIcon(getClass().getResource("/Resources/images/calendar.png")));
		btnAppointment.setToolTipText("Módulo de citas (Alt+C)");

		// Botón de Pacientes
		ButtonDentiApp btnCustomers = new ButtonDentiApp(0, 270, false,
				new ImageIcon(getClass().getResource("/Resources/images/customersGrey.png")));
		btnCustomers.setToolTipText("Módulo de pacientes (Alt+P)");
		btnCustomers.setMnemonic(KeyEvent.VK_P);

		// Botón de Inventario
		ButtonDentiApp btnStock = new ButtonDentiApp(0, 405, false,
				new ImageIcon(getClass().getResource("/Resources/images/stockGrey.png")));
		btnStock.setToolTipText("Módulo de materiales (Alt+I)");
		btnStock.setMnemonic(KeyEvent.VK_I);

		// -------------------- Lógica --------------------
		// logica jmenu
		// logica click item salir
		ItemOut.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				//System.out.println("funciona");
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
				DChangePass cP = new DChangePass(mainUser);
				cP.setVisible(true);
				cP.setModal(true);
				//System.out.println("PINCHADO");
				session.close();

			}
		});
		// logica de sombreado
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
				mnNewMenu.setBackground(new Color(0, 0, 0, 0));

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

		// Acción de ir a Módulo pacientes
		btnCustomers.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DoctorCustomers docCustomers = new DoctorCustomers(mainUser, frame);
				docCustomers.setVisible(true);
			}
		});

		// Acción de ir a Módulo Stock
		btnStock.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DoctorStock docStock = new DoctorStock(mainUser, frame);
				docStock.setVisible(true);
			}
		});

		// -------------------- Adiciones a los paneles --------------------
		contentPane.add(menuPane);
		menuPane.add(lblLogo);
		menuPane.add(btnAppointment);
		menuPane.add(btnCustomers);
		menuPane.add(btnStock);

		contentPane.add(menuBar);
		menuBar.add(mnNewMenu);
		mnNewMenu.add(ItemName);
		mnNewMenu.add(ItemPass);
		mnNewMenu.add(ItemOut);

		// Panel para el calendario
		JPanel panelCalendar = new RoundedPanel(null, 50, new Color(148, 220, 219));
		panelCalendar.setBounds(1390, 454, 450, 350);
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
		CalendarPanel calendarPanel = new CalendarPanel();
		GridBagLayout gbl_calendarPanel = (GridBagLayout) calendarPanel.getLayout();
		gbl_calendarPanel.rowWeights = new double[] { 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0 };
		gbl_calendarPanel.rowHeights = new int[] { 6, 0, 5, 138, 5, 0, 5 };
		gbl_calendarPanel.columnWeights = new double[] { 1.0, 1.0, 1.0 };
		gbl_calendarPanel.columnWidths = new int[] { 5, 219, 5 };
		calendarPanel.setBounds(25, 25, 400, 300);
		calendarPanel.setOpaque(false);
		calendarPanel.setSelectedDate(LocalDate.now());
		panelCalendar.add(calendarPanel);

		// Panel para las citas
		JPanel panelCitas = new RoundedPanel(50, new Color(148, 220, 219));
		panelCitas.setBounds(250, 202, 1050, 820);
		panelCitas.setOpaque(false);
		panelCitas.setLayout(null);
		contentPane.add(panelCitas);

		// Panel secundario para las citas
		JPanel panelTitleCitas = new JPanel();
		panelTitleCitas.setBounds(15, 15, 1020, 790);
		panelTitleCitas.setBorder(
				new TitledBorder(lb, "  Citas  ", TitledBorder.LEFT, TitledBorder.TOP, font, new Color(51, 51, 51)));
		panelTitleCitas.setOpaque(false);
		panelTitleCitas.setLayout(null);
		panelCitas.add(panelTitleCitas);

		// ScrollPane para cargar la talbla inventario
		JScrollPane menuTableStock = new JScrollPane();
		menuTableStock.setBorder(BorderFactory.createEmptyBorder());
		menuTableStock.setBounds(5, 35, 1006, 750);
		menuTableStock.setBackground(new Color(148, 220, 219));
		panelTitleCitas.add(menuTableStock);

		// Tabla de citas
		DefaultTableModel modelo = new DefaultTableModel();
		JTable table = new JTable();
		table.setBounds(5, 30, 1011, 750);
		// table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
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
		menuTableStock.add(table);
		menuTableStock.setViewportView(table);
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evnt) {
				if (evnt.getClickCount() == 1) {

					// Seleccionar row
					table.addColumnSelectionInterval(0, 2);

					// Cambios en la selección
					table.setColumnSelectionAllowed(true);
					table.setCellSelectionEnabled(true);

				}
			}
		});

		Calendar fechaCalen = new GregorianCalendar();
		DateFormat formateador = new SimpleDateFormat("yyyy-M-dd");
		Query<CitaHibernate> consultaCitas = session
				.createQuery("FROM CitaHibernate where fecha=:fech and usuario_cita=:id", CitaHibernate.class);
		consultaCitas.setParameter("fech", fechaCalen.getTime());
		consultaCitas.setParameter("id", mainUser);
		List<CitaHibernate> allCitas = consultaCitas.getResultList();

		loadCitas(table, calendarPanel, mainUser, formateador);

		calendarPanel.addCalendarListener(new CalendarListener() {

			@Override
			public void yearMonthChanged(YearMonthChangeEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void selectedDateChanged(CalendarSelectionEvent arg0) {
				// TODO Auto-generated method stub
				for (int j = 1; j < table.getModel().getRowCount(); j++) {
					table.getModel().setValueAt("", j, 1);
					table.getModel().setValueAt("", j, 2);
				}
				// Calendar fechaCal = (Calendar) evt.getNewValue();
				java.util.Date d = Date
						.from(calendarPanel.getSelectedDate().atStartOfDay(ZoneId.systemDefault()).toInstant());

				actualizarTabla(table, calendarPanel, mainUser);

			}
		});

	}

	public void actualizarTabla(JTable table, CalendarPanel calendar, UserHibernate us) {
		for (int j = 0; j < table.getModel().getRowCount(); j++) {
			table.getModel().setValueAt("", j, 1);
			table.getModel().setValueAt("", j, 2);
		}
		session = instancia.openSession();
		DateFormat formateador = new SimpleDateFormat("yyyy-M-dd");
		Query<CitaHibernate> consultaCitas = session
				.createQuery("FROM CitaHibernate where fecha=:fech and usuario_cita=:id", CitaHibernate.class);
		java.util.Date d = Date.from(calendar.getSelectedDate().atStartOfDay(ZoneId.systemDefault()).toInstant());
		consultaCitas.setParameter("fech", d);
		consultaCitas.setParameter("id", us);
		List<CitaHibernate> allCitas = consultaCitas.getResultList();

		for (int i = 0; i < allCitas.size(); i++) {
			Date dia = (Date) allCitas.get(i).getFecha();
			if (formateador.format(d).equals(formateador.format(dia))) {
				for (int j = 0; j < table.getModel().getRowCount(); j++) {
					if (table.getModel().getValueAt(j, 0).equals(allCitas.get(i).getHora())) {
						table.getModel().setValueAt(allCitas.get(i).getCliente().getNombre(), j, 1);
						table.getModel().setValueAt(allCitas.get(i).getTratamiento().getNombre(), j, 2);
					}
				}
			}
		}
	}

	public void loadCitas(JTable tabla, CalendarPanel calendar, UserHibernate us, DateFormat formateador) {
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

		// model.insertRow(0, new Object[] { "Hora", "Cita", "Tratamientos" });

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
