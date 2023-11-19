package Vista;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.TableColumnModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

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
import java.awt.Component;

public class AdminClinic extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private ConexionMySQL conex;
	private SessionFactory instancia;
	private Session session;
	private JFrame parent, frame;
	private int lastIdSpeciality, lastlastIdTreatements;
	private String selectedSpeciality = null;
	private String selectedTreatment = null;
	private UserHibernate userHi;
	private JTable tableSpeciality;
	private JTable tableTreatments;
	private TreatmentsHibernate th;
	private SpecialityHibernate sh;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AdminClinic frame = new AdminClinic(null, null);
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
	public AdminClinic(UserHibernate userHi, JFrame parent) {
		this.userHi = userHi;

		// -------------------- Conexión ------------------
		this.instancia = (SessionFactory) new Configuration().configure("hibernate.cfg.xml")
				.addAnnotatedClass(SpecialityHibernate.class).addAnnotatedClass(TreatmentsHibernate.class)
				.buildSessionFactory();
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
		btnInsertSpeciality.setBackground(new Color(148, 220, 219));
		btnInsertSpeciality.setBounds(798, 240, 40, 30);
		btnInsertSpeciality.setIcon(new ImageIcon(getClass().getResource("/Resources/images/add.png")));
		btnInsertSpeciality.setBorderPainted(false);

		// Botón de insertar tratamiento
		JButton btnInsertTreatment = new JButton();
		btnInsertTreatment.setBackground(new Color(148, 220, 219));
		btnInsertTreatment.setBounds(1517, 240, 40, 30);
		btnInsertTreatment.setIcon(new ImageIcon(getClass().getResource("/Resources/images/add.png")));
		btnInsertTreatment.setBorderPainted(false);

		// Botón de modificar tratamiento
		JButton btnUpadateSpeciality = new JButton();
		btnUpadateSpeciality.setBackground(new Color(148, 220, 219));
		btnUpadateSpeciality.setBounds(838, 240, 40, 30);
		btnUpadateSpeciality.setIcon(new ImageIcon(getClass().getResource("/Resources/images/edit.png")));
		btnUpadateSpeciality.setBorderPainted(false);

		// Botón de modificar tratamiento
		JButton btnUpadateTreatment = new JButton();
		btnUpadateTreatment.setBackground(new Color(148, 220, 219));
		btnUpadateTreatment.setBounds(1557, 240, 40, 30);
		btnUpadateTreatment.setIcon(new ImageIcon(getClass().getResource("/Resources/images/edit.png")));
		btnUpadateTreatment.setBorderPainted(false);

		// Botón de borrar especialidad
		JButton btnDeleteSpeciality = new JButton();
		btnDeleteSpeciality.setBackground(new Color(148, 220, 219));
		btnDeleteSpeciality.setBounds(878, 240, 40, 30);
		btnDeleteSpeciality.setIcon(new ImageIcon(getClass().getResource("/Resources/images/delete.png")));
		btnDeleteSpeciality.setBorderPainted(false);

		// Botón de borrar tratamiento
		JButton btnDeleteTreatment = new JButton();
		btnDeleteTreatment.setBackground(new Color(148, 220, 219));
		btnDeleteTreatment.setBounds(1597, 240, 40, 30);
		btnDeleteTreatment.setIcon(new ImageIcon(getClass().getResource("/Resources/images/delete.png")));
		btnDeleteTreatment.setBorderPainted(false);

		// ScrollPane para cargar la talbla
		JScrollPane menuSpecialities = new JScrollPane();
		menuSpecialities.setBorder(BorderFactory.createEmptyBorder());
		menuSpecialities.setBounds(418, 270, 500, 675);
		menuSpecialities.setBackground(new Color(148, 220, 219));

		// Tabla
		tableSpeciality = new JTable();
		tableSpeciality.setShowVerticalLines(false);
		tableSpeciality.setShowHorizontalLines(false);
		tableSpeciality.setCellSelectionEnabled(true);
		tableSpeciality.setBounds(0, 0, 500, 675);
		tableSpeciality.setBackground(new Color(250, 250, 250));
		tableSpeciality.setSelectionBackground(new Color(148, 220, 219));
		tableSpeciality.setShowGrid(false);
		tableSpeciality.setBorder(null);
		tableSpeciality.setFont(new Font("Tahoma", Font.PLAIN, 18));
		tableSpeciality.setRowHeight(35);
		tableSpeciality.getTableHeader().setFont(new Font("Tahoma", Font.PLAIN, 18));
		tableSpeciality.getTableHeader().setBackground(new Color(148, 220, 219));
		tableSpeciality.getTableHeader().setBorder(new LineBorder(new Color(148, 220, 219)));

		// Panel de tratamientos
		JScrollPane menuTreatments = new JScrollPane();
		menuTreatments.setBounds(1137, 270, 500, 675);
		menuTreatments.setBorder(BorderFactory.createEmptyBorder());
		menuTreatments.setBackground(new Color(148, 220, 219));

		// Tabla de tratamientos
		tableTreatments = new JTable();
		tableTreatments.setShowVerticalLines(false);
		tableTreatments.setShowHorizontalLines(false);
		tableTreatments.setCellSelectionEnabled(true);
		tableTreatments.setBounds(0, 0, 500, 675);
		tableTreatments.setBackground(new Color(250, 250, 250));
		tableTreatments.setSelectionBackground(new Color(148, 220, 219));
		tableTreatments.setShowGrid(false);
		tableTreatments.setBorder(null);
		tableTreatments.setFont(new Font("Tahoma", Font.PLAIN, 18));
		tableTreatments.setRowHeight(35);
		tableTreatments.getTableHeader().setFont(new Font("Tahoma", Font.PLAIN, 18));
		tableTreatments.getTableHeader().setBackground(new Color(148, 220, 219));
		tableTreatments.getTableHeader().setBorder(new LineBorder(new Color(148, 220, 219)));

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

		// Acción del Módulo económico
		btnPayments.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AdminPayments admPayments = new AdminPayments(userHi, frame);
				admPayments.setVisible(true);
			}
		});

		// Mostrar las tablas
		loadSpecialities(tableSpeciality);
		loadTreatments(tableTreatments, null);

		// Acción de seleccionar elemento de la tabla especialidad
		tableSpeciality.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evnt) {
				if (evnt.getClickCount() == 1) {
					// Cambios en la selección
					tableTreatments.setColumnSelectionAllowed(false);
					tableTreatments.setCellSelectionEnabled(false);
					tableSpeciality.setColumnSelectionAllowed(true);
					tableSpeciality.setCellSelectionEnabled(true);
					selectedTreatment = null;

					// selección del tratamiento
					selectedSpeciality = tableSpeciality
							.getValueAt(tableSpeciality.getSelectedRow(), tableSpeciality.getSelectedColumn())
							.toString();

					// Busca el id
					String hql = "FROM SpecialityHibernate where especialidad=:especialidad";
					Query<SpecialityHibernate> consulta = session.createQuery(hql, SpecialityHibernate.class);
					consulta.setParameter("especialidad", selectedSpeciality);

					// result es un objeto de especialidad con todos los campos
					SpecialityHibernate result = consulta.getSingleResult();

					// Carga los resultados
					loadTreatments(tableTreatments, result);
				}
			}
		});

		// Acción de seleccionar elemento de la tabla tratamientos
		tableTreatments.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evnt) {
				if (evnt.getClickCount() == 1) {

					// Seleccionar row
					tableTreatments.addColumnSelectionInterval(0, 1);

					// Cambios en la selección
					tableSpeciality.setColumnSelectionAllowed(false);
					tableSpeciality.setCellSelectionEnabled(false);
					tableTreatments.setColumnSelectionAllowed(true);
					tableTreatments.setCellSelectionEnabled(true);
					selectedSpeciality = null;

					// selección del tratamiento
					selectedTreatment = tableTreatments.getValueAt(tableTreatments.getSelectedRow(), 0).toString();

				}
			}
		});

		// Acción de desseleccionar de las tablas
		contentPane.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evnt) {
				if (evnt.getClickCount() == 1) {
					// Cambios en la selección
					tableSpeciality.setColumnSelectionAllowed(false);
					tableSpeciality.setCellSelectionEnabled(false);
					tableTreatments.setColumnSelectionAllowed(false);
					tableTreatments.setCellSelectionEnabled(false);
					selectedSpeciality = null;
					selectedTreatment = null;
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
					sh = new SpecialityHibernate((lastIdSpeciality + 1), especialidad);
					session.beginTransaction();
					session.save(sh);
					session.getTransaction().commit();

					// Recarga la tabla
					loadSpecialities(tableSpeciality);

				}
			}
		});

		// Acción de insertar en tratamientos
		btnInsertTreatment.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Variable de control
				boolean hasSpeciality = false;

				// Dialog de insertar
				DInsertTratamiento it = null;

				// Comprueba si hay una especialidad seleccionada
				if (selectedSpeciality != null) {
					// Busca la especialidad
					String hql = "FROM SpecialityHibernate WHERE especialidad=:especialidad";
					Query<SpecialityHibernate> consulta = session.createQuery(hql, SpecialityHibernate.class);
					consulta.setParameter("especialidad", selectedSpeciality);
					sh = consulta.getSingleResult();

					// Crea el Dialog de insertar
					it = new DInsertTratamiento(instancia, sh, lastlastIdTreatements + 1);
					it.setTextSpeciality(sh.getEspecialidad());

					hasSpeciality = true;
				} else if (selectedTreatment != null) {
					// Busca el tratmiento
					String hql = "FROM TreatmentsHibernate WHERE nombre=:nombre";
					Query<TreatmentsHibernate> consulta = session.createQuery(hql, TreatmentsHibernate.class);
					consulta.setParameter("nombre", selectedTreatment);
					th = consulta.getSingleResult();

					// Busca saca la especialidad
					sh = th.getEspecialidad();

					// Crea el Dialog de insertar
					it = new DInsertTratamiento(instancia, sh, lastlastIdTreatements + 1);
					it.setTextSpeciality(sh.getEspecialidad());

					hasSpeciality = true;
				} else {
					JOptionPane.showMessageDialog(contentPane, "No se ha sleccionado ninguna especialidad", "Error",
							JOptionPane.ERROR_MESSAGE);
				}

				if (hasSpeciality) {
					it.setModal(true);
					it.setVisible(hasSpeciality);
					it.addWindowListener(new WindowListener() {

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
							loadTreatments(tableTreatments, sh);
						}

						@Override
						public void windowActivated(WindowEvent e) {
							// TODO Auto-generated method stub

						}
					});
				}
			}
		});

		// Acción de Modificar en especialidades
		btnUpadateSpeciality.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				// Comprueba si hay una especialidad a borrar
				if (selectedSpeciality != null) {
					// Busca la especialidad
					String hql = "FROM SpecialityHibernate WHERE especialidad=:especialidad";
					Query<SpecialityHibernate> consulta = session.createQuery(hql, SpecialityHibernate.class);
					consulta.setParameter("especialidad", selectedSpeciality);
					sh = consulta.getSingleResult();

					// Feedback al usuario
					if (sh.getId_especialidad() == null) {
						JOptionPane.showMessageDialog(contentPane, "No se ha encontrado la especialidad", "Error",
								JOptionPane.ERROR_MESSAGE);
					} else {
						// Solicita la nueva especialidad
						String newSpeciality = null;
						newSpeciality = JOptionPane.showInputDialog("Introduzca la nueva especialidad");

						if (newSpeciality != null) {
							// Modificar la especialidad
							session.beginTransaction();
							sh.setEspecialidad(newSpeciality);
							session.update(sh);
							session.getTransaction().commit();

							// Recargamos la tabla
							loadSpecialities(tableSpeciality);
						}
					}
				}
			}
		});

		// Acción de Modificar en tratamientos
		btnUpadateTreatment.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				// Comprueba si hay un tratamiento seleccionado
				if (selectedTreatment != null) {
					// Busca el tratmiento
					String hql = "FROM TreatmentsHibernate WHERE nombre=:nombre";
					Query<TreatmentsHibernate> consulta = session.createQuery(hql, TreatmentsHibernate.class);
					consulta.setParameter("nombre", selectedTreatment);
					th = consulta.getSingleResult();

					// Feedback al usuario
					if (th.getCodigo_tratamiento() == null) {
						JOptionPane.showMessageDialog(contentPane, "No se ha encontrado el tratamiento", "Error",
								JOptionPane.ERROR_MESSAGE);
					} else {
						// Crea el JDialog para modificar el tratamiento
						DUpdateTratamiento ut = new DUpdateTratamiento(instancia, th);
						ut.setModal(true);
						ut.setTextSpeciality(th.getEspecialidad().getEspecialidad());
						ut.setTextName(th.getNombre());
						ut.setTextPrice(th.getPrecio());
						ut.setVisible(true);

						// Cargar la tabla tratamientos
						ut.addWindowListener(new WindowListener() {

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
								loadTreatments(tableTreatments, th.getEspecialidad());

							}

							@Override
							public void windowActivated(WindowEvent e) {
								// TODO Auto-generated method stub

							}
						});

					}
				}
			}
		});

		// Acción de Borrar en especialidades
		btnDeleteSpeciality.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				// Comprueba si hay una especialidad a borrar
				if (selectedSpeciality != null) {
					// Busca la especialidad
					String hql = "FROM SpecialityHibernate WHERE especialidad=:especialidad";
					Query<SpecialityHibernate> consulta = session.createQuery(hql, SpecialityHibernate.class);
					consulta.setParameter("especialidad", selectedSpeciality);
					sh = consulta.getSingleResult();

					// Feedback al usuario
					if (sh.getId_especialidad() == null) {
						JOptionPane.showMessageDialog(contentPane, "No se ha encontrado la especialidad", "Error",
								JOptionPane.ERROR_MESSAGE);
					} else {
						// Confirmación
						int resp = JOptionPane.showConfirmDialog(contentPane,
								"¿Esta seguro de que desea eliminar la especialidad " + selectedSpeciality + "?",
								"Eliminar", JOptionPane.YES_NO_OPTION);

						if (resp == 0) {
							// Borrar la especialidad
							session.beginTransaction();
							session.delete(sh);
							session.getTransaction().commit();
							selectedSpeciality = null;

							// Recargamos las tablas
							loadSpecialities(tableSpeciality);
							loadTreatments(tableTreatments, null);
						}
					}
				}
			}
		});

		// Acción de borrar tratamientos
		btnDeleteTreatment.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				// Comprueba si hay un tratamiento a borrar
				if (selectedTreatment != null) {
					// Busca el tratamiento
					String hql = "FROM TreatmentsHibernate";
					Query<TreatmentsHibernate> consulta = session.createQuery(hql, TreatmentsHibernate.class);
					th = new TreatmentsHibernate();
					List<TreatmentsHibernate> results = consulta.getResultList();
					for (TreatmentsHibernate treat : results) {
						if (treat.getNombre().equalsIgnoreCase(selectedTreatment)) {
							th = treat;
							continue;
						}
					}

					// Feedback al usuario
					if (th.getCodigo_tratamiento() == null) {
						JOptionPane.showMessageDialog(contentPane, "No se ha encontrado la especialidad", "Error",
								JOptionPane.ERROR_MESSAGE);
					} else {
						// Confirmación
						int resp = JOptionPane.showConfirmDialog(contentPane,
								"¿Esta seguro de que desea eliminar el tratamiento " + selectedTreatment + "?",
								"Eliminar", JOptionPane.YES_NO_OPTION);

						if (resp == 0) {
							// Borrar la especialidad
							session.beginTransaction();
							session.delete(th);
							session.getTransaction().commit();
							selectedTreatment = null;

							// Recargamos la tabla
							loadTreatments(tableTreatments, th.getEspecialidad());
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
		contentPane.add(btnInsertTreatment);
		contentPane.add(btnUpadateSpeciality);
		contentPane.add(btnUpadateTreatment);
		contentPane.add(btnDeleteSpeciality);
		contentPane.add(btnDeleteTreatment);
		menuPane.add(lblLogo);
		menuPane.add(btnAppointment);
		menuPane.add(btnUsers);
		menuPane.add(btnCustomers);
		menuPane.add(btnStock);
		menuPane.add(btnClinic);
		menuPane.add(btnPayments);
		menuSpecialities.add(tableSpeciality);
		menuTreatments.add(tableTreatments);
		menuTreatments.setViewportView(tableTreatments);
		menuSpecialities.setViewportView(tableSpeciality);
	}

	// -------------------- Métodos y funciones --------------------
	public void loadSpecialities(JTable tableSpeciality) {
		// Relaiza la consulta
		String hql = "FROM SpecialityHibernate";
		Query<SpecialityHibernate> consulta = session.createQuery(hql, SpecialityHibernate.class);

		// Guarda los datos en una lista
		List<SpecialityHibernate> results = consulta.getResultList();

		// Prepara la tabla
		DefaultTableModel model = new DefaultTableModel(new Object[][] {}, new String[] { "Especialidad" }) {
			@Override
			public boolean isCellEditable(int row, int column) {
				// all cells false
				return false;
			}
		};
		tableSpeciality.setModel(model);
		JTableHeader header = tableSpeciality.getTableHeader();
		model.setRowCount(results.size());
		int fila = 0, columna = 0;

		// Carga los datos
		for (SpecialityHibernate especialidad : results) {
			model.setValueAt(especialidad.getEspecialidad(), fila, columna);
			fila++;
		}

		// Se alinea el texto de las columnas
		Renderer tcr = new Renderer();
		tcr.setHorizontalAlignment(SwingConstants.CENTER);
		tableSpeciality.getColumnModel().getColumn(0).setCellRenderer(tcr);
		tableSpeciality.setDefaultRenderer(Object.class, tcr);

		// Guarda el último id de las especialidades
		if (!results.isEmpty()) {
			lastIdSpeciality = results.getLast().getId_especialidad();
		} else {
			lastIdSpeciality = 0;
		}

	}

	public void loadTreatments(JTable tableTreatments, SpecialityHibernate especialidad) {
		if (especialidad == null) {
			// Prepara la tabla
			DefaultTableModel model = new DefaultTableModel(new Object[][] {},
					new String[] { "Tratamiento", "Precio" }) {

				@Override
				public boolean isCellEditable(int row, int column) {
					// all cells false
					return false;
				}
			};
			tableTreatments.setModel(model);
			JTableHeader header = tableTreatments.getTableHeader();
			model.setRowCount(0);

		} else {
			// Busca en la tabla los tratamientos cuyo atributo objeto especialidad es igual
			// al que sacamos de la consulta anterior
			String hql = "FROM TreatmentsHibernate where especialidad=:especialidad";
			Query<TreatmentsHibernate> consulta = session.createQuery(hql, TreatmentsHibernate.class);
			consulta.setParameter("especialidad", especialidad);

			// Guarda los datos en una lista
			List<TreatmentsHibernate> results = consulta.getResultList();

			// Prepara la tabla
			DefaultTableModel model = new DefaultTableModel(new Object[][] {},
					new String[] { "Tratamiento", "Precio" }) {

				@Override
				public boolean isCellEditable(int row, int column) {
					// all cells false
					return false;
				}
			};
			tableTreatments.setModel(model);
			JTableHeader header = tableTreatments.getTableHeader();
			model.setRowCount(results.size());
			int fila = 0, columna = 0;

			// Carga los datos
			for (TreatmentsHibernate tratamiento : results) {
				model.setValueAt(tratamiento.getNombre(), fila, columna);
				model.setValueAt(tratamiento.getPrecio(), fila, columna + 1);
				fila++;
			}

			// Se alinea el texto de las columnas
			Renderer tcr2 = new Renderer();
			tcr2.setHorizontalAlignment(SwingConstants.CENTER);
			tableTreatments.getColumnModel().getColumn(0).setCellRenderer(tcr2);
			tableTreatments.getColumnModel().getColumn(1).setCellRenderer(tcr2);
			tableTreatments.setDefaultRenderer(Object.class, tcr2);
		}

		// Guarda el último id de los tratamientos
		String hql2 = "FROM TreatmentsHibernate";
		Query<TreatmentsHibernate> consulta2 = session.createQuery(hql2, TreatmentsHibernate.class);
		List<TreatmentsHibernate> results2 = consulta2.getResultList();
		if (!results2.isEmpty()) {
			lastlastIdTreatements = results2.getLast().getCodigo_tratamiento();
		} else {
			lastlastIdTreatements = 0;
		}

	}

	public JTable getTableTreatments() {
		return tableTreatments;
	}

	// Clase para cambiar el color de las filas
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
