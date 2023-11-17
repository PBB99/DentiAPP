package Vista;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import Controlador.ConexionMySQL;
import Modelo.SpecialityHibernate;
import Modelo.TreatmentsHibernate;
import Modelo.UserHibernate;
import btndentiapp.ButtonDentiApp;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class AdminClinic extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private ConexionMySQL conex;
	private SessionFactory instancia;
	private Session session;
	private JFrame parent, frame;
	private int lastIdSpeciality;
	private String selectedSpeciality = null;
	private UserHibernate userHi;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					AdminClinic frame = new AdminClinic(null, null);
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
	}

	/**
	 * Create the frame.
	 */
	public AdminClinic( UserHibernate userHi,JFrame parent) {
		this.userHi=userHi;

		// -------------------- Conexión ------------------
		this.instancia = (SessionFactory) new Configuration().configure("hibernate.cfg.xml")
				.addAnnotatedClass(SpecialityHibernate.class).buildSessionFactory();
		this.session = instancia.openSession();

		// -------------------- JFrame --------------------
		this.frame = this;
		this.parent = parent;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setResizable(false);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
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
		btnAppointment.setToolTipText("Módulo de citas");

		// Botón de usuarios
		ButtonDentiApp btnUsers = new ButtonDentiApp(0, 270, false,
				new ImageIcon(getClass().getResource("/Resources/images/usersGrey.png")));
		btnAppointment.setToolTipText("Módulo de Usuarios");

		// Botón de Pacientes
		ButtonDentiApp btnCustomers = new ButtonDentiApp(0, 405, false,
				new ImageIcon(getClass().getResource("/Resources/images/customersGrey.png")));
		btnCustomers.setToolTipText("Módulo de pacientes");

		// Botón de Inventario
		ButtonDentiApp btnStock = new ButtonDentiApp(0, 540, false,
				new ImageIcon(getClass().getResource("/Resources/images/stockGrey.png")));
		btnStock.setToolTipText("Módulo de materiales");

		// Botón de Tratamientos y Especialidades
		ButtonDentiApp btnClinic = new ButtonDentiApp(0, 675, true,
				new ImageIcon(getClass().getResource("/Resources/images/clinic.png")));
		btnClinic.setToolTipText("Módulo clínico");

		// Botón del Módulo economico
		ButtonDentiApp btnPayments = new ButtonDentiApp(0, 810, false,
				new ImageIcon(getClass().getResource("/Resources/images/paymentsGrey.png")));
		btnPayments.setToolTipText("Módulo Económico");

		// Botón de insertar especialidad
		JButton btnInsertSpeciality = new JButton();
		btnInsertSpeciality.setBackground(Color.WHITE);
		btnInsertSpeciality.setBounds(858, 240, 30, 30);
		btnInsertSpeciality.setIcon(new ImageIcon(getClass().getResource("/Resources/images/add.png")));
		btnInsertSpeciality.setBorderPainted(false);

		// Botón de insertar especialidad
		JButton btnDeleteSpeciality = new JButton();
		btnDeleteSpeciality.setBackground(Color.WHITE);
		btnDeleteSpeciality.setBounds(888, 240, 30, 30);
		btnDeleteSpeciality.setIcon(new ImageIcon(getClass().getResource("/Resources/images/delete.png")));
		btnDeleteSpeciality.setBorderPainted(false);

		// Panel de especialidades
		JPanel menuSpecialities = new JPanel();
		menuSpecialities.setBorder(new LineBorder(new Color(0, 0, 0)));
		menuSpecialities.setBounds(418, 270, 500, 675);
		menuSpecialities.setLayout(null);

		// Tabla para mostrar los datos
		JTable tableSpeciality = new JTable();
		tableSpeciality.setBounds(0, 0, 500, 675);
		tableSpeciality.setVisible(true);

		// Panel de tratamientos
		JPanel menuTreatments = new JPanel();
		menuTreatments.setBorder(new LineBorder(new Color(0, 0, 0)));
		menuTreatments.setBounds(1137, 270, 500, 675);
		menuTreatments.setLayout(null);

		// Tabla para mostrar los datos
		JTable tableTreatments = new JTable();
		tableTreatments.setBounds(0, 0, 500, 675);
		tableTreatments.setVisible(true);

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
				AdminAppointment admAppointment = new AdminAppointment( userHi,frame);
				admAppointment.setVisible(true);
			}
		});

		// Acción del Módulo de usuarios
		btnUsers.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AdminUsers admUsers = new AdminUsers( userHi,frame);
				admUsers.setVisible(true);
			}
		});

		// Acción del Módulo de pacientes
		btnCustomers.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AdminCustomers admCustomers = new AdminCustomers(userHi,frame);
				admCustomers.setVisible(true);
			}
		});

		// Acción del Módulo de inventario
		btnStock.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AdminStock admStock = new AdminStock(userHi,frame);
				admStock.setVisible(true);
			}
		});

		// Acción del Módulo económico
		btnPayments.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AdminPayments admPayments = new AdminPayments(userHi, frame);
				admPayments.setVisible(true);
			}
		});

		// Mostrar especialidades en la tabla
		cargarEspecialidades(tableSpeciality);

		// Acción de seleccionar elemento de la tabla
		tableSpeciality.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evnt) {
				if (evnt.getClickCount() == 1) {
					System.out.println(tableSpeciality
							.getValueAt(tableSpeciality.getSelectedRow(), tableSpeciality.getSelectedColumn())
							.toString());
					selectedSpeciality = tableSpeciality
							.getValueAt(tableSpeciality.getSelectedRow(), tableSpeciality.getSelectedColumn())
							.toString();

					// Busca el id
					String hql = "FROM SpecialityHibernate where especialidad=:especialidad";
					Query<SpecialityHibernate> consulta = session.createQuery(hql, SpecialityHibernate.class);
					consulta.setParameter("especialidad", selectedSpeciality);
					SpecialityHibernate result = consulta.getSingleResult();

					// Carga los resultados
					cargarTratamientos(tableTreatments, result.getId_especialidad());
				}
			}
		});

		// Acción de insertar en especialidades
		btnInsertSpeciality.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Solicita los parámetros de inserción
				String especialidad = JOptionPane.showInputDialog("Introduzca el nombre de la especialidad");
				if (especialidad != null) {
					// Inserta la especialidad
					SpecialityHibernate sh = new SpecialityHibernate((lastIdSpeciality + 1), especialidad);
					session.beginTransaction();
					session.save(sh);
					session.getTransaction().commit();

					// Recarga la tabla
					cargarEspecialidades(tableSpeciality);

					// Feedback al usuario
					JOptionPane.showMessageDialog(contentPane, "Se ha insertado la especiaidad correctamente",
							"Insertado", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});

		// Acción de Borrar en especialidades
		btnDeleteSpeciality.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Elige la especialidad a borrar
				String especialidad;
				if (selectedSpeciality == null) {
					// Solicita los parámetros de inserción
					especialidad = JOptionPane.showInputDialog("Introduzca el nombre de la especialidad a borrar");
				} else {
					especialidad = selectedSpeciality;
				}

				if (especialidad != null) {
					// Busca la especialidad
					String hql = "FROM SpecialityHibernate";
					Query<SpecialityHibernate> consulta = session.createQuery(hql, SpecialityHibernate.class);
					SpecialityHibernate sh = new SpecialityHibernate();
					List<SpecialityHibernate> results = consulta.getResultList();
					for (SpecialityHibernate spec : results) {
						if (spec.getEspecialidad().equalsIgnoreCase(especialidad)) {
							sh = spec;
							continue;
						}
					}

					// Feedback al usuario
					if (sh.getId_especialidad() == null) {
						JOptionPane.showMessageDialog(contentPane, "No se ha encontrado la especialidad", "Error",
								JOptionPane.ERROR_MESSAGE);
					} else {
						// Confirmación
						int resp = JOptionPane.showConfirmDialog(contentPane,
								"¿Esta seguro de que desea eliminar la especialidad " + especialidad + "?", "Eliminar",
								JOptionPane.YES_NO_OPTION);

						if (resp == 0) {
							// Borrar la especialidad
							session.beginTransaction();
							session.delete(sh);
							session.getTransaction().commit();
							selectedSpeciality = null;

							// Recargamos la tabla
							cargarEspecialidades(tableSpeciality);

							// Feedback al usuario
							JOptionPane.showMessageDialog(contentPane, "Se ha borrardo la especiaidad correctamente",
									"Borrar", JOptionPane.INFORMATION_MESSAGE);
						}
					}
				}
			}
		});

		// -------------------- Adiciones a los paneles --------------------
		contentPane.add(menuPane);
		contentPane.add(menuSpecialities);
		contentPane.add(menuTreatments);
		contentPane.add(btnInsertSpeciality);
		contentPane.add(btnDeleteSpeciality);
		menuPane.add(lblLogo);
		menuPane.add(btnAppointment);
		menuPane.add(btnUsers);
		menuPane.add(btnCustomers);
		menuPane.add(btnStock);
		menuPane.add(btnClinic);
		menuPane.add(btnPayments);
		menuSpecialities.add(tableSpeciality);
		menuTreatments.add(tableTreatments);
	}

	// -------------------- Métodos y funciones --------------------
	public void cargarEspecialidades(JTable tableSpeciality) {
		// Relaiza la consulta
		String hql = "FROM SpecialityHibernate";
		Query<SpecialityHibernate> consulta = session.createQuery(hql, SpecialityHibernate.class);

		// Guarda los datos en una lista
		List<SpecialityHibernate> results = consulta.getResultList();

		// Prepara la tabla
		DefaultTableModel model = new DefaultTableModel() {
			@Override
			public boolean isCellEditable(int row, int column) {
				// all cells false
				return false;
			}
		};
		tableSpeciality.setModel(model);
		// model.setColumnIdentifiers(new String[] {"Id Especialidad", "Especialidad"});
		model.setColumnIdentifiers(new String[] { "Especialidad" });
		model.setRowCount(results.size() + 1);
		int fila = 0, columna = 0;

		// Pone el titulo de las columnas
		// model.setValueAt("Id Especialidad", fila, columna);
		// model.setValueAt("Especialidad", fila, columna + 1);
		model.setValueAt("Especialidad", fila, columna);
		fila++;

		// Carga los datos
		for (SpecialityHibernate especialidad : results) {
			// model.setValueAt(especialidad.getId_especialidad(), fila, columna);
			// model.setValueAt(especialidad.getEspecialidad(), fila, columna + 1);
			model.setValueAt(especialidad.getEspecialidad(), fila, columna);
			fila++;
		}

		// Guarda el último id de las especialidades
		lastIdSpeciality = results.getLast().getId_especialidad();
	}

	public void cargarTratamientos(JTable tableTreatments, int especialidad) {
		// Relaiza la consulta
		String hql = "FROM TreatmentsHibernate where especialidad=:especialidad";
		Query<TreatmentsHibernate> consulta = session.createQuery(hql, TreatmentsHibernate.class);
		consulta.setParameter("especialidad", especialidad);

		// Guarda los datos en una lista
		List<TreatmentsHibernate> results = consulta.getResultList();

		// Prepara la tabla
		DefaultTableModel model = new DefaultTableModel() {
			@Override
			public boolean isCellEditable(int row, int column) {
				// all cells false
				return false;
			}
		};
		tableTreatments.setModel(model);
		model.setColumnIdentifiers(new String[] { "Tratamiento", "Precio" });
		model.setRowCount(results.size() + 1);
		int fila = 0, columna = 0;

		// Pone el titulo de las columnas
		model.setValueAt("Tratamiento", fila, columna);
		model.setValueAt("Precio", fila, columna + 1);
		fila++;

		// Carga los datos
		for (TreatmentsHibernate tratamiento : results) {
			model.setValueAt(tratamiento.getNombre(), fila, columna);
			model.setValueAt(tratamiento.getPrecio(), fila, columna + 1);
			fila++;
		}

		// Guarda el último id de los tratamientos
		lastIdSpeciality = results.getLast().getCodigo_tratamiento();
	}
}
