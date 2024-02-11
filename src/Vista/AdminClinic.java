package Vista;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
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
import Otros.RoundedPanel;
import btndentiapp.ButtonDentiApp;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import java.awt.Component;
import java.awt.ComponentOrientation;

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

	private LineBorder lb = new LineBorder(new Color(240, 240, 240), 3, true);
	private LineBorder lb2 = new LineBorder(new Color(148, 220, 219), 3, true);
	private Font font = new Font("Dialog", Font.BOLD, 15);
	private Color azulito = new Color(148, 220, 219);
	private Color blanquito = new Color(240, 240, 240);

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
		// nombre
		// Citas
		JPanel panelTitleAdmin = new JPanel();
		panelTitleAdmin.setBounds(1, 2, 170, 90);
		panelTitleAdmin
				.setBorder(new TitledBorder(lb2, "", TitledBorder.LEFT, TitledBorder.TOP, null, new Color(51, 51, 51)));
		panelTitleAdmin.setOpaque(false);
		panelTitleAdmin.setLayout(null);

		// rounded panel de fomdo para el nombre
		JPanel panelnombre = new RoundedPanel(30, new Color(240, 240, 240));
		panelnombre.setBounds(136, 0, 150, 60);
		panelnombre.setOpaque(false);
		panelnombre.setLayout(null);
		contentPane.add(panelnombre);
		panelnombre.add(panelTitleAdmin);
		String htmlString = "<html><body><sup>" + userHi.getNombre() + "</sup><span>" + userHi.getApellido()
				+ "</span></body></html>";
		JLabel lblNAdmin = new JLabel(htmlString);
		lblNAdmin.setToolTipText("Nombre & Apellido");
		lblNAdmin.setBounds(10, 5, 150, 60);
		lblNAdmin.setFont(new Font("metropolis", Font.PLAIN, 20));
		panelTitleAdmin.add(lblNAdmin);
		// menubar

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
		mnNewMenu.setBackground(new Color(0, 0, 0, 0));
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

		// nombre del doctor o admin
		JMenuItem ItemName = new JMenuItem(userHi.getNombre());
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
		ButtonDentiApp btnClinic = new ButtonDentiApp(0, 675, true,
				new ImageIcon(getClass().getResource("/Resources/images/clinic.png")));
		btnClinic.setToolTipText("Módulo clínico (Alt+L)");

		// Botón del Módulo economico
		ButtonDentiApp btnPayments = new ButtonDentiApp(0, 810, false,
				new ImageIcon(getClass().getResource("/Resources/images/paymentsGrey.png")));
		btnPayments.setToolTipText("Módulo Económico (Alt+E)");
		btnPayments.setMnemonic(KeyEvent.VK_E);

		// Especialidades
		// Panel fondo de especialidad
		JPanel panelBackSpeciality = new RoundedPanel(50, azulito);
		panelBackSpeciality.setBounds(335, 202, 560, 775);
		panelBackSpeciality.setOpaque(false);
		panelBackSpeciality.setLayout(null);
		contentPane.add(panelBackSpeciality);

		// Panel titulo de especialidad
		JPanel panelTitleSpeciality = new JPanel();
		panelTitleSpeciality.setBounds(25, 15, 510, 735);
		panelTitleSpeciality.setBorder(new TitledBorder(lb, "  Especialidades  ", TitledBorder.LEFT, TitledBorder.TOP,
				font, new Color(51, 51, 51)));
		panelTitleSpeciality.setOpaque(false);
		panelTitleSpeciality.setLayout(null);
		panelBackSpeciality.add(panelTitleSpeciality);

		// Botón de insertar especialidad
		JButton btnInsertSpeciality = new JButton();
		btnInsertSpeciality.setBackground(new Color(148, 220, 219));
		btnInsertSpeciality.setBounds(425, 25, 40, 30);
		btnInsertSpeciality.setIcon(new ImageIcon(getClass().getResource("/Resources/images/add.png")));
		btnInsertSpeciality.setBorderPainted(false);
		panelTitleSpeciality.add(btnInsertSpeciality);

		// Botón de modificar especialidad
		JButton btnUpadateSpeciality = new JButton();
		btnUpadateSpeciality.setBackground(new Color(148, 220, 219));
		btnUpadateSpeciality.setBounds(465, 25, 40, 30);
		btnUpadateSpeciality.setIcon(new ImageIcon(getClass().getResource("/Resources/images/edit.png")));
		btnUpadateSpeciality.setBorderPainted(false);
		panelTitleSpeciality.add(btnUpadateSpeciality);

		// Lupa
		JLabel jlLupaSpeciality = new JLabel();
		jlLupaSpeciality.setBackground(azulito);
		jlLupaSpeciality.setBounds(20, 25, 40, 30);
		jlLupaSpeciality.setIcon(new ImageIcon(getClass().getResource("/Resources/images/lookFor.png")));
		panelTitleSpeciality.add(jlLupaSpeciality);

		// Buscador
		JTextField txtBuscadorSpeciality = new JTextField();
		txtBuscadorSpeciality.setBorder(new LineBorder(azulito));
		txtBuscadorSpeciality.setBounds(60, 28, 200, 25);
		txtBuscadorSpeciality.setBackground(new Color(255, 255, 255));
		txtBuscadorSpeciality.setToolTipText("Buscador");
		panelTitleSpeciality.add(txtBuscadorSpeciality);

		// ScrollPane para cargar la talbla
		JScrollPane menuSpecialities = new JScrollPane();
		menuSpecialities.setBorder(BorderFactory.createEmptyBorder());
		menuSpecialities.setBounds(30, 70, 500, 675);
		menuSpecialities.setBackground(new Color(148, 220, 219));
		panelBackSpeciality.add(menuSpecialities);

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
		menuSpecialities.add(tableSpeciality);
		menuSpecialities.setViewportView(tableSpeciality);

		// Tratamientos
		// Panel fondo de Tratamientos
		JPanel panelBackTreatment = new RoundedPanel(50, azulito);
		panelBackTreatment.setBounds(1000, 202, 560, 775);
		panelBackTreatment.setOpaque(false);
		panelBackTreatment.setLayout(null);
		contentPane.add(panelBackTreatment);

		// Panel titulo de tratamientos
		JPanel panelTitleTreatment = new JPanel();
		panelTitleTreatment.setBounds(25, 15, 510, 735);
		panelTitleTreatment.setBorder(new TitledBorder(lb, "  Tratamientos  ", TitledBorder.LEFT, TitledBorder.TOP,
				font, new Color(51, 51, 51)));
		panelTitleTreatment.setOpaque(false);
		panelTitleTreatment.setLayout(null);
		panelBackTreatment.add(panelTitleTreatment);

		// Lupa
		JLabel jlLupaTreatments = new JLabel();
		jlLupaTreatments.setBackground(azulito);
		jlLupaTreatments.setBounds(20, 25, 40, 30);
		jlLupaTreatments.setIcon(new ImageIcon(getClass().getResource("/Resources/images/lookFor.png")));
		panelTitleTreatment.add(jlLupaTreatments);

		// Buscador
		JTextField txtBuscadorTreatments = new JTextField();
		txtBuscadorTreatments.setBorder(new LineBorder(azulito));
		txtBuscadorTreatments.setBounds(60, 28, 200, 25);
		txtBuscadorTreatments.setBackground(new Color(255, 255, 255));
		txtBuscadorTreatments.setToolTipText("Buscador");
		panelTitleTreatment.add(txtBuscadorTreatments);

		// Botón de insertar tratamiento
		JButton btnInsertTreatment = new JButton();
		btnInsertTreatment.setBackground(new Color(148, 220, 219));
		btnInsertTreatment.setBounds(425, 25, 40, 30);
		btnInsertTreatment.setIcon(new ImageIcon(getClass().getResource("/Resources/images/add.png")));
		btnInsertTreatment.setBorderPainted(false);
		panelTitleTreatment.add(btnInsertTreatment);

		// Botón de modificar tratamiento
		JButton btnUpadateTreatment = new JButton();
		btnUpadateTreatment.setBackground(new Color(148, 220, 219));
		btnUpadateTreatment.setBounds(465, 25, 40, 30);
		btnUpadateTreatment.setIcon(new ImageIcon(getClass().getResource("/Resources/images/edit.png")));
		btnUpadateTreatment.setBorderPainted(false);
		panelTitleTreatment.add(btnUpadateTreatment);

		// Panel de tratamientos
		JScrollPane menuTreatments = new JScrollPane();
		menuTreatments.setBounds(30, 70, 500, 675);
		menuTreatments.setBorder(BorderFactory.createEmptyBorder());
		menuTreatments.setBackground(new Color(148, 220, 219));
		panelBackTreatment.add(menuTreatments);

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
		menuTreatments.add(tableTreatments);
		menuTreatments.setViewportView(tableTreatments);

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

					// selección de la especialidad
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
					sh=result;
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
					if (!txtBuscadorSpeciality.getText().isBlank()) {
						loadSearchSpecialities(tableSpeciality, txtBuscadorSpeciality.getText());
					} else {
						// Recargamos la tabla
						loadSpecialities(tableSpeciality);
					}

				}
			}
		});

		// Acción de buscar en la tabla
		txtBuscadorSpeciality.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {

			}

			@Override
			public void keyReleased(KeyEvent e) {
				loadSearchSpecialities(tableSpeciality, txtBuscadorSpeciality.getText());
			}

			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
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
							if (!txtBuscadorTreatments.getText().isBlank()) {
								loadSearchTreatments(tableTreatments, sh, txtBuscadorTreatments.getText());
							} else {
								// Recargamos la tabla
								loadTreatments(tableTreatments, sh);
							}
						}

						@Override
						public void windowActivated(WindowEvent e) {
							// TODO Auto-generated method stub

						}
					});
				}
			}
		});

		// Acción de buscar en la tabla
		txtBuscadorTreatments.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {

			}

			@Override
			public void keyReleased(KeyEvent e) {
				loadSearchTreatments(tableTreatments, sh, txtBuscadorTreatments.getText());
			}

			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
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

							// Recarga la tabla
							if (!txtBuscadorSpeciality.getText().isBlank()) {
								loadSearchSpecialities(tableSpeciality, txtBuscadorSpeciality.getText());
							} else {
								// Recargamos la tabla
								loadSpecialities(tableSpeciality);
							}
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
						DUpdateTratamiento ut = new DUpdateTratamiento(th, instancia);
						ut.setModal(true);
						ut.setSession(session);
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
								if (!txtBuscadorTreatments.getText().isBlank()) {
									loadSearchTreatments(tableTreatments, th.getEspecialidad(), txtBuscadorTreatments.getText());
								} else {
									// Recargamos la tabla
									loadTreatments(tableTreatments, th.getEspecialidad());
								}
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
		menuBar.add(mnNewMenu);
		mnNewMenu.add(ItemName);
		mnNewMenu.add(ItemPass);
		mnNewMenu.add(ItemOut);

		menuPane.add(lblLogo);
		menuPane.add(btnAppointment);
		menuPane.add(btnUsers);
		menuPane.add(btnCustomers);
		menuPane.add(btnStock);
		menuPane.add(btnClinic);
		menuPane.add(btnPayments);
	}

	// -------------------- Métodos y funciones --------------------
	public void loadSpecialities(JTable tableSpeciality) {
		// Relaiza la consulta
		String hql = "FROM SpecialityHibernate";
		Query<SpecialityHibernate> consulta = session.createQuery(hql, SpecialityHibernate.class);

		// Guarda los datos en una lista
		List<SpecialityHibernate> results = consulta.getResultList();

		// Eliminamos la especialidad de Administrador
		results.remove(0);

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
		if (results.size() < 20) {
			model.setRowCount(19);
		} else {
			model.setRowCount(results.size());
		}
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

	public void loadSearchSpecialities(JTable tableSpeciality, String busq) {
		// Relaiza la consulta
		String hql = "FROM SpecialityHibernate where especialidad like:busq";
		Query<SpecialityHibernate> consulta = session.createQuery(hql, SpecialityHibernate.class);
		consulta.setParameter("busq", "%" + busq + "%");

		// Guarda los datos en una lista
		List<SpecialityHibernate> results = consulta.getResultList();

		// Eliminamos la especialidad de Administrador
		if (results.get(0).getEspecialidad().equalsIgnoreCase("administrador")) {
			results.remove(0);
		}

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
		if (results.size() < 20) {
			model.setRowCount(19);
		} else {
			model.setRowCount(results.size());
		}
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
			model.setRowCount(19);
			JTableHeader header = tableTreatments.getTableHeader();
			Renderer tcr2 = new Renderer();
			tableTreatments.setDefaultRenderer(Object.class, tcr2);

			int[] anchos = { 400, 100 };
			for (int i = 0; i < 2; i++) {
				tableTreatments.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
			}
		} else {
			// Busca en la tabla los tratamientos cuyo atributo objeto especialidad es igual
			// al que sacamos de la consulta anterior
			String hql = "FROM TreatmentsHibernate where especialidad_tratamiento=:especialidad";
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
			if (results.size() < 20) {
				model.setRowCount(19);
			} else {
				model.setRowCount(results.size());
			}
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

			int[] anchos = { 400, 100 };
			for (int i = 0; i < 2; i++) {
				tableTreatments.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
			}
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

	public void loadSearchTreatments(JTable tableTreatments, SpecialityHibernate especialidad, String busq) {
		// Busca en la tabla los tratamientos cuyo atributo objeto especialidad es igual
		// al que sacamos de la consulta anterior
		String hql = "FROM TreatmentsHibernate where especialidad_tratamiento=:especialidad and nombre like:busq";
		Query<TreatmentsHibernate> consulta = session.createQuery(hql, TreatmentsHibernate.class);
		consulta.setParameter("especialidad", especialidad);
		consulta.setParameter("busq", "%" + busq + "%");

		// Guarda los datos en una lista
		List<TreatmentsHibernate> results = consulta.getResultList();

		// Prepara la tabla
		DefaultTableModel model = new DefaultTableModel(new Object[][] {}, new String[] { "Tratamiento", "Precio" }) {

			@Override
			public boolean isCellEditable(int row, int column) {
				// all cells false
				return false;
			}
		};
		tableTreatments.setModel(model);
		JTableHeader header = tableTreatments.getTableHeader();
		if (results.size() < 20) {
			model.setRowCount(19);
		} else {
			model.setRowCount(results.size());
		}
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

		int[] anchos = { 400, 100 };
		for (int i = 0; i < 2; i++) {
			tableTreatments.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
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
