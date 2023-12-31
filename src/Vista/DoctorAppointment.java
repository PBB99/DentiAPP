package Vista;

import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.EventQueue;
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
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

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

public class DoctorAppointment extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private ConexionMySQL conex;
	private JFrame parent, frame;
	private SessionFactory instancia;
	private Session session;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
//				try {
//					DoctorAppointment frame = new DoctorAppointment(null,null);
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
	public DoctorAppointment(UserHibernate mainUser, JFrame parent) {
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
		this.instancia = (SessionFactory) new Configuration().configure("hibernate.cfg.xml")
				.addAnnotatedClass(UserHibernate.class).addAnnotatedClass(CitaHibernate.class)
				.addAnnotatedClass(TreatmentsHibernate.class).addAnnotatedClass(ClienteHibernate.class)
				.buildSessionFactory();
		this.session = instancia.openSession();
		this.session.beginTransaction();

		// -------------------- Componentes --------------------
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
		
		JCalendar calendar = new JCalendar();
		calendar.setBounds(180, 180, 184, 153);
		contentPane.add(calendar);
		
		DefaultTableModel modelo = new DefaultTableModel();
		JTable table = new JTable();
		table.setBounds(420, 180, 199, 196);
		contentPane.add(table);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setModel(modelo);
		modelo.addColumn("Hora");
		modelo.addColumn("Cita");
		modelo.insertRow(0, new Object[] { "Hora", "Cita" });
		Calendar fechaCalen = new GregorianCalendar();
		DateFormat formateador = new SimpleDateFormat("yyyy-M-dd");
		Query<CitaHibernate> consultaCitas = session.createQuery("FROM CitaHibernate where fecha=:fech and dni_doc=:id",
				CitaHibernate.class);
		consultaCitas.setParameter("fech", fechaCalen.getTime());
		consultaCitas.setParameter("id", mainUser);
		List<CitaHibernate> allCitas = consultaCitas.getResultList();
		
		System.out.println(formateador.format(fechaCalen.getTime()));
		for (int i = 9; i < 15; i++) {
			modelo.insertRow(modelo.getRowCount(), new Object[] { i + ":00", "" });
		}
		for (int i = 17; i < 20; i++) {
			modelo.insertRow(modelo.getRowCount(), new Object[] { i + ":00", "" });
		}

		System.out.println(allCitas.size());
		for (int i = 0; i < allCitas.size(); i++) {
			//System.out.println("saddasdas");
			java.util.Date dia = allCitas.get(i).getFecha();
			//System.out.println(formateador.format(dia) + "------");
			if (formateador.format(fechaCalen.getTime()).equals(formateador.format(dia))) {
				//System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
				for (int j = 0; j < table.getModel().getRowCount(); j++) {
					if (table.getModel().getValueAt(j, 0).equals(allCitas.get(i).getHora())) {
						table.getModel().setValueAt(allCitas.get(i).getCliente().getNombre(), j, 1);
					}
				}
			}
		}
		
		calendar.addPropertyChangeListener("calendar", new PropertyChangeListener() {

			@Override
			public void propertyChange(PropertyChangeEvent evt) {

				// TODO Auto-generated method stub
				for (int j = 1; j < table.getModel().getRowCount(); j++) {
					table.getModel().setValueAt("", j, 1);
				}

				Calendar fechaCal = (Calendar) evt.getNewValue();
				Query<CitaHibernate> consulta = session
						.createQuery("FROM CitaHibernate where fecha=:fech and dni_doc=:id", CitaHibernate.class);
				consulta.setParameter("fech", fechaCal.getTime());
				consulta.setParameter("id",mainUser);
				List<CitaHibernate> allCitas = consulta.getResultList();

				System.out.println(allCitas.size());

				for (int i = 0; i < allCitas.size(); i++) {
					Date dia = (Date) allCitas.get(i).getFecha();

					System.out.println(formateador.format(fechaCal.getTime()) + "------");
					if (formateador.format(fechaCal.getTime()).equals(formateador.format(dia))) {
						System.out.println("saddasdas");
						for (int j = 0; j < table.getModel().getRowCount(); j++) {
							if (table.getModel().getValueAt(j, 0).equals(allCitas.get(i).getHora())) {
								table.getModel().setValueAt(allCitas.get(i).getCliente().getNombre(), j, 1);
							}
						}
					}
				}
			}
		});
		
	
	}

}
