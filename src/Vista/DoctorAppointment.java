package Vista;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.toedter.calendar.JCalendar;

import Controlador.ConexionMySQL;
import Modelo.CitaHibernate;
import Modelo.ClienteHibernate;
import Modelo.SpecialityHibernate;
import Modelo.TreatmentsHibernate;
import Modelo.UserHibernate;
import btndentiapp.ButtonDentiApp;

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
	public DoctorAppointment(ConexionMySQL conex, JFrame parent) {
		this.conex = conex;
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
		btnAppointment.setToolTipText("Módulo de citas");

		// Botón de Pacientes
		ButtonDentiApp btnCustomers = new ButtonDentiApp(0, 270, false,
				new ImageIcon(getClass().getResource("/Resources/images/customersGrey.png")));
		btnCustomers.setToolTipText("Módulo de pacientes");

		// Botón de Inventario
		ButtonDentiApp btnStock = new ButtonDentiApp(0, 405, false,
				new ImageIcon(getClass().getResource("/Resources/images/stockGrey.png")));
		btnStock.setToolTipText("Módulo de materiales");

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
				DoctorCustomers docCustomers = new DoctorCustomers(conex, frame);
				docCustomers.setVisible(true);
			}
		});

		// Acción de ir a Módulo Stock
		btnStock.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DoctorStock docStock = new DoctorStock(conex, frame);
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
		
		JComboBox cbUsuarios = new JComboBox();
		cbUsuarios.setBounds(180, 120, 96, 22);
		contentPane.add(cbUsuarios);
		Query<UserHibernate> consultaUsers = session.createQuery("FROM UserHibernate", UserHibernate.class);
		List<UserHibernate> allUsers = consultaUsers.getResultList();
		int x = 0;
		for (int i = 0; i < allUsers.size(); i++) {
			List<SpecialityHibernate> allSpecialities = allUsers.get(i).getSpeciality();
			for (int j = 0; j < allSpecialities.size(); j++) {
				if(allSpecialities.get(j).getId_especialidad() == 0)
					x++;
			}
			if(x==0) {
				cbUsuarios.addItem(allUsers.get(i));
			}
			x = 0;
		}

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
		consultaCitas.setParameter("id", (UserHibernate) cbUsuarios.getSelectedItem());
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
				consulta.setParameter("id",(UserHibernate) cbUsuarios.getSelectedItem());
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
		
		cbUsuarios.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				// TODO Auto-generated method stub
				for (int j = 1; j < table.getModel().getRowCount(); j++) {
					table.getModel().setValueAt("", j, 1);
				}

				Query<CitaHibernate> consultaCitas = session
						.createQuery("FROM CitaHibernate where fecha=:fech and dni_doc=:id", CitaHibernate.class);
				consultaCitas.setParameter("fech", calendar.getCalendar().getTime());
				consultaCitas.setParameter("id", (UserHibernate) cbUsuarios.getSelectedItem());
				List<CitaHibernate> allCitas = consultaCitas.getResultList();

				System.out.println(allCitas.size());

				for (int i = 0; i < allCitas.size(); i++) {
					Date dia = (Date) allCitas.get(i).getFecha();
					System.out.println(formateador.format(calendar.getCalendar().getTime()) + "------");
					if (formateador.format(calendar.getCalendar().getTime()).equals(formateador.format(dia))) {
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

		table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent event) {
				String a = (table.getValueAt(table.getSelectedRow(), 0).toString());
				if (!event.getValueIsAdjusting()) {// This line prevents double events
					String[] opc = { "ELIMINAR CITA", "ACTUALIZAR/INSERTAR CITA" };
					System.out.println("--");
					if (fechaCalen.getTime().before(calendar.getCalendar().getTime())
							|| (fechaCalen.getTime().getDay() == calendar.getCalendar().getTime().getDay())
									&& (fechaCalen.getTime().getMonth() == calendar.getCalendar().getTime().getMonth())
									&& (fechaCalen.getTime().getYear() == calendar.getCalendar().getTime().getYear())) {
						int opcionDml = JOptionPane.showOptionDialog(null, "¿Qué quieres hacer?", "Elige",
								JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, opc, opc[0]);
						if (opcionDml == 0) {
							session.beginTransaction();
							Query<CitaHibernate> consultaCitas = session.createQuery(
									"FROM CitaHibernate where fecha=:fech and hora=:hora and dni_doc=:id",
									CitaHibernate.class);
							consultaCitas.setParameter("fech", calendar.getCalendar().getTime());
							consultaCitas.setParameter("hora", a);
							consultaCitas.setParameter("id", (UserHibernate) cbUsuarios.getSelectedItem());
							CitaHibernate h3 = consultaCitas.getSingleResult();
							session.delete(h3);
							session.getTransaction().commit();
							actualizarTabla();
						} else if (opcionDml == 1) {
							UserHibernate u = (UserHibernate) cbUsuarios.getSelectedItem();
							AdminInsertCita us = new AdminInsertCita(u.getDni(),
									calendar.getCalendar().getTime(),
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
				// table.clearSelection();
//		        if (table.getSelectedRow() > -1) {
//		            // print first column value from selected row
//		            //System.out.println(table.getValueAt(table.getSelectedRow(), 0).toString());
//		            System.out.println("---");
//
//		        }
			}
			
			
			public void actualizarTabla() {
				for (int j = 1; j < table.getModel().getRowCount(); j++) {
					table.getModel().setValueAt("", j, 1);
				}

				Query<CitaHibernate> consultaCitas = session
						.createQuery("FROM CitaHibernate where fecha=:fech and dni_doc=:id", CitaHibernate.class);
				consultaCitas.setParameter("fech", calendar.getCalendar().getTime());
				consultaCitas.setParameter("id", (UserHibernate) cbUsuarios.getSelectedItem());
				List<CitaHibernate> allCitas = consultaCitas.getResultList();

				for (int i = 0; i < allCitas.size(); i++) {
					Date dia = (Date) allCitas.get(i).getFecha();
					System.out.println(formateador.format(calendar.getCalendar().getTime()) + "------");
					if (formateador.format(calendar.getCalendar().getTime()).equals(formateador.format(dia))) {
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
