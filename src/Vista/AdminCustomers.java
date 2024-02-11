package Vista;

import java.awt.Color;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.Window.Type;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
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
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
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
import Otros.RoundedPanel;
import Vista.PruebaOdonto.Renderer;
import btndentiapp.ButtonDentiApp;

public class AdminCustomers extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JFrame parent, frame;
	private UserHibernate userHi;
	private JTable table;
	private JTable tableHis;
	private String selected;
	private SessionFactory instancia;
	private Session session;
	private JButton btnOdonto;

	private LineBorder lb = new LineBorder(new Color(240, 240, 240), 3, true);
	private Font font = new Font("Dialog", Font.BOLD, 15);
	private LineBorder lb2 = new LineBorder(new Color(148, 220, 219), 3, true);

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
//				try {
//					AdminCustomers frame = new AdminCustomers(null,null);
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
	public AdminCustomers(UserHibernate userHi, JFrame parent) {
		this.userHi = userHi;
		setType(Type.POPUP);
		setBounds(new Rectangle(10, 0, 0, 0));
		this.instancia = (SessionFactory) new Configuration().configure("hibernate.cfg.xml")
				.addAnnotatedClass(UserHibernate.class).addAnnotatedClass(CitaHibernate.class)
				.addAnnotatedClass(TreatmentsHibernate.class).addAnnotatedClass(ClienteHibernate.class)
				.addAnnotatedClass(SpecialityHibernate.class).addAnnotatedClass(OdontogramaHibernate.class)
				.buildSessionFactory();
		this.session = instancia.openSession();

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

		// -------------------- Componentes Gráficos --------------------
		
		JButton BinformeTotal=new JButton("<html><u>Imprimir historial completo</u></html>");
		BinformeTotal.setBorder(null);
		BinformeTotal.setFocusPainted(false);
		BinformeTotal.setBorderPainted(false);
		BinformeTotal.setContentAreaFilled(false);
		BinformeTotal.setOpaque(false);
		BinformeTotal.setBackground(null);
		BinformeTotal.setBounds(1650,200,200,60);
		contentPane.add(BinformeTotal);
		// menubar

		// nombre esquina

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
		ButtonDentiApp btnCustomers = new ButtonDentiApp(0, 405, true,
				new ImageIcon(getClass().getResource("/Resources/images/customers.png")));
		btnCustomers.setToolTipText("Módulo de pacientes (Alt+P)");

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
				// Agregar tiempos de carga
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

		contentPane.add(menuBar);
		menuBar.add(mnNewMenu);
		mnNewMenu.add(ItemName);
		mnNewMenu.add(ItemPass);
		mnNewMenu.add(ItemOut);

		// Panel para los clientes
		JPanel panelClientes = new RoundedPanel(null, 50, new Color(148, 220, 219));
		panelClientes.setBounds(200, 200, 500, 735);
		panelClientes.setOpaque(false);
		panelClientes.setLayout(null);
		contentPane.add(panelClientes);

		// Panel secundario para los clientes
		JPanel panelTitleCliente = new JPanel();
		panelTitleCliente.setBounds(10, 0, 480, 720);
		panelTitleCliente.setBorder(
				new TitledBorder(lb, "  Cliente  ", TitledBorder.LEFT, TitledBorder.TOP, font, new Color(51, 51, 51)));
		panelTitleCliente.setOpaque(false);
		panelClientes.add(panelTitleCliente);
		panelTitleCliente.setLayout(null);

		// ScrollPane para cargar la talbla con los clientes
		JScrollPane menuTableCliente = new JScrollPane();
		menuTableCliente.setBorder(BorderFactory.createEmptyBorder());
		menuTableCliente.setBounds(5, 75, 470, 640);
		menuTableCliente.setBackground(new Color(148, 220, 219));
		panelTitleCliente.add(menuTableCliente);

		table = new JTable();
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
		table.setBounds(0, 50, 450, 725);
		menuTableCliente.add(table);
		menuTableCliente.setViewportView(table);

		// Panel para los historiales
		JPanel panelHist = new RoundedPanel(null, 50, new Color(148, 220, 219));
		panelHist.setBounds(900, 200, 500, 735);
		panelHist.setOpaque(false);
		panelHist.setLayout(null);
		contentPane.add(panelHist);

		// Panel secundario para los historiales
		JPanel panelTitleHist = new JPanel();
		panelTitleHist.setBounds(10, 11, 480, 705);
		panelTitleHist.setBorder(new TitledBorder(lb, "  Historial  ", TitledBorder.LEFT, TitledBorder.TOP, font,
				new Color(51, 51, 51)));
		panelTitleHist.setOpaque(false);
		panelHist.add(panelTitleHist);
		panelTitleHist.setLayout(null);

		// ScrollPane para cargar la talbla con los historiales
		JScrollPane menuTableHist = new JScrollPane();
		menuTableHist.setBorder(BorderFactory.createEmptyBorder());
		menuTableHist.setBounds(5, 25, 470, 675);
		menuTableHist.setBackground(new Color(148, 220, 219));
		panelTitleHist.add(menuTableHist);

		tableHis = new JTable();
		tableHis.setShowVerticalLines(false);
		tableHis.setShowHorizontalLines(false);
		tableHis.setCellSelectionEnabled(true);
		tableHis.setBackground(new Color(250, 250, 250));
		tableHis.setSelectionBackground(new Color(148, 220, 219));
		tableHis.setShowGrid(false);
		tableHis.setBorder(null);
		tableHis.setFont(new Font("Tahoma", Font.PLAIN, 18));
		tableHis.setRowHeight(35);
		tableHis.getTableHeader().setFont(new Font("Tahoma", Font.PLAIN, 18));
		tableHis.getTableHeader().setBackground(new Color(148, 220, 219));
		tableHis.getTableHeader().setBorder(new LineBorder(new Color(148, 220, 219)));
		tableHis.setBounds(0, 0, 500, 735);
		menuTableHist.add(tableHis);
		menuTableHist.setViewportView(tableHis);

		loadClientes(table);
		loadCitaStart(tableHis);

		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evnt) {
				if (evnt.getClickCount() == 1) {

					// Seleccionar row
					table.addColumnSelectionInterval(0, 3);

					// Cambios en la selección
					table.setColumnSelectionAllowed(true);
					table.setCellSelectionEnabled(true);
					// selección del tratamiento
					selected = table.getValueAt(table.getSelectedRow(), 0).toString() + " "
							+ table.getValueAt(table.getSelectedRow(), 1).toString() + " "
							+ table.getValueAt(table.getSelectedRow(), 2).toString() + " "
							+ table.getValueAt(table.getSelectedRow(), 3).toString() + " ";
					loadCita(tableHis);
					System.out.println(selected);

				}
			}
		});

		JButton btnInsert = new JButton();
		btnInsert.setBorderPainted(false);
		btnInsert.setBackground(new Color(148, 220, 219));
		btnInsert.setBounds(390, 30, 40, 30);
		btnInsert.setIcon(new ImageIcon(getClass().getResource("/Resources/images/add.png")));
		btnInsert.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DInsertCliente it = new DInsertCliente("", "", "", "", false);
				it.setModal(true);
				it.setLocationRelativeTo(null);
				it.setVisible(true);
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
						loadClientes(table);
					}

					@Override
					public void windowActivated(WindowEvent e) {
						// TODO Auto-generated method stub

					}
				});

			}
		});
		panelTitleCliente.add(btnInsert);

		btnOdonto = new JButton("Odontograma");
		btnOdonto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (selected != null) {
					Query<ClienteHibernate> consultaClienteExiste = session
							.createQuery("FROM ClienteHibernate where dni_cliente=:dni ", ClienteHibernate.class);
					consultaClienteExiste.setParameter("dni", selected.split(" ")[0]);
					List<ClienteHibernate> cliente = consultaClienteExiste.getResultList();
					CustommerOdont cust = new CustommerOdont(cliente.get(0), session, true);
					cust.setModal(true);
					cust.setVisible(true);
				}
			}
		});
		btnOdonto.setBackground(new Color(148, 220, 219));
		btnOdonto.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnOdonto.setBounds(1398, 800, 100, 100);
		btnOdonto.setBorder(null);
		contentPane.add(btnOdonto);

		JTextField txt = new JTextField();
		txt.setBounds(44, 30, 180, 30);
		txt.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {

			}

			@Override
			public void keyReleased(KeyEvent e) {
				loadSearch(table, txt.getText());
			}

			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub

			}
		});
		panelTitleCliente.add(txt);

		JLabel jlLupa = new JLabel();
		jlLupa.setBackground(new Color(148, 220, 219));
		jlLupa.setBounds(10, 30, 36, 28);
		jlLupa.setIcon(new ImageIcon(getClass().getResource("/Resources/images/lookFor.png")));
		panelTitleCliente.add(jlLupa);

		JButton btnUpdateCliente = new JButton();
		btnUpdateCliente.setBorderPainted(false);
		btnUpdateCliente.setBackground(new Color(148, 220, 219));
		btnUpdateCliente.setBounds(420, 30, 40, 30);
		btnUpdateCliente.setIcon(new ImageIcon(getClass().getResource("/Resources/images/edit.png")));
		btnUpdateCliente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (selected != null) {
					DInsertCliente at = new DInsertCliente(selected.split(" ")[0], selected.split(" ")[1],
							selected.split(" ")[2], selected.split(" ")[3], true);
					at.setModal(true);
					at.setLocationRelativeTo(null);
					at.setVisible(true);
					at.addWindowListener(new WindowListener() {

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
							loadClientes(table);
							System.out.println("------");
						}

						@Override
						public void windowClosing(WindowEvent e) {
							// TODO Auto-generated method stub
							loadClientes(table);
							System.out.println("ola");
						}

						@Override
						public void windowClosed(WindowEvent e) {
							loadClientes(table);
							System.out.println("adi");
						}

						@Override
						public void windowActivated(WindowEvent e) {
							// TODO Auto-generated method stub

						}
					});
				}
			}
		});
		panelTitleCliente.add(btnUpdateCliente);


	}

	public void loadClientes(JTable tabla) {
		// Relaiza la consulta
		this.session = instancia.openSession();
		String hql = "FROM ClienteHibernate";
		Query<ClienteHibernate> consulta = session.createQuery(hql, ClienteHibernate.class);

		// Guarda los datos en una lista
		List<ClienteHibernate> results = consulta.getResultList();
		System.out.println(results.size());

		// Prepara la tabla
		DefaultTableModel model = new DefaultTableModel(new Object[][] {},
				new String[] { "DNI", "Nombre", "Apellido", "Años" }) {
			@Override
			public boolean isCellEditable(int row, int column) {
				// all cells false
				return false;
			}
		};
		tabla.setModel(model);
		JTableHeader header = tabla.getTableHeader();
		if (results.size() < 19) {
			model.setRowCount(18);
		} else {
			System.out.println("oooooooooooooooo");
			model.setRowCount(results.size());
		}
		int fila = 0, columna = 0;

		// Carga los datos
		for (ClienteHibernate especialidad : results) {
			model.setValueAt(especialidad.getDni_cliente(), fila, 0);
			model.setValueAt(especialidad.getNombre(), fila, 1);
			model.setValueAt(especialidad.getApellidos(), fila, 2);
			model.setValueAt(especialidad.getEdad(), fila, 3);
			fila++;
		}

		// Se alinea el texto de las columnas
		Renderer tcr = new Renderer();
		tcr.setHorizontalAlignment(SwingConstants.CENTER);
		tabla.getColumnModel().getColumn(0).setCellRenderer(tcr);
		tabla.setDefaultRenderer(Object.class, tcr);

	}

	public void loadCitaStart(JTable tabla) {
		// Relaiza la consulta
		this.session = instancia.openSession();
		DefaultTableModel model = new DefaultTableModel(new Object[][] {},
				new String[] { "Fecha", "Doctor", "Tratamiento" }) {
			@Override
			public boolean isCellEditable(int row, int column) {
				// all cells false
				return false;
			}
		};
		tabla.setModel(model);
		JTableHeader header = tabla.getTableHeader();
		model.setRowCount(34);

		// Carga los datos
//		for (ClienteHibernate especialidad : results) {
//			model.setValueAt(especialidad.getDni_cliente(), fila, 0);
//			model.setValueAt(especialidad.getNombre(), fila, 1);
//			model.setValueAt(especialidad.getApellidos(), fila, 2);
//			fila++;
//		}

		// Se alinea el texto de las columnas
		Renderer tcr = new Renderer();
		tcr.setHorizontalAlignment(SwingConstants.CENTER);
		tabla.getColumnModel().getColumn(0).setCellRenderer(tcr);
		tabla.setDefaultRenderer(Object.class, tcr);

	}

	public void loadCita(JTable tabla) {
		// Relaiza la consulta
		this.session = instancia.openSession();
		String hql = "FROM CitaHibernate where clientes_dni_cliente=:dni";
		Query<CitaHibernate> consulta = session.createQuery(hql, CitaHibernate.class);
		consulta.setParameter("dni", selected.split(" ")[0]);

		// Guarda los datos en una lista
		List<CitaHibernate> results = consulta.getResultList();
		System.out.println(results.size());

		// Prepara la tabla
		DefaultTableModel model = new DefaultTableModel(new Object[][] {},
				new String[] { "Fecha", "Doctor", "Tratamiento" }) {
			@Override
			public boolean isCellEditable(int row, int column) {
				// all cells false
				return false;
			}
		};
		tabla.setModel(model);
		JTableHeader header = tabla.getTableHeader();
		if (results.size() < 35) {
			model.setRowCount(34);
		} else {
			System.out.println("oooooooooooooooo");
			model.setRowCount(results.size());
		}
		int fila = 0, columna = 0;

		// Carga los datos
		for (CitaHibernate cita : results) {
			model.setValueAt(cita.getFecha(), fila, 0);
			model.setValueAt(cita.getCliente().getNombre(), fila, 1);
			model.setValueAt(cita.getTratamiento().getNombre(), fila, 2);
			fila++;
		}

		// Se alinea el texto de las columnas
		Renderer tcr = new Renderer();
		tcr.setHorizontalAlignment(SwingConstants.CENTER);
		tabla.getColumnModel().getColumn(0).setCellRenderer(tcr);
		tabla.setDefaultRenderer(Object.class, tcr);

	}

	public void loadSearch(JTable tabla, String busq) {
		// Relaiza la consulta
		this.session = instancia.openSession();
		String hql = "FROM ClienteHibernate where nombre like :busq or apellidos like :busq or edad like :busq or dni_cliente like :busq";
		Query<ClienteHibernate> consulta = session.createQuery(hql, ClienteHibernate.class);
		consulta.setParameter("busq", "%" + busq + "%");

		// Guarda los datos en una lista
		List<ClienteHibernate> results = consulta.getResultList();

		// Prepara la tabla
		DefaultTableModel model = new DefaultTableModel(new Object[][] {},
				new String[] { "DNI", "Nombre", "Apellido", "Años" }) {
			@Override
			public boolean isCellEditable(int row, int column) {
				// all cells false
				return false;
			}
		};
		tabla.setModel(model);
		JTableHeader header = tabla.getTableHeader();
		if (results.size() < 19) {
			model.setRowCount(18);
		} else {
			model.setRowCount(results.size());
		}
		int fila = 0, columna = 0;

		// Carga los datos
		for (ClienteHibernate especialidad : results) {
			System.out.println("aa");
			model.setValueAt(especialidad.getDni_cliente(), fila, 0);
			model.setValueAt(especialidad.getNombre(), fila, 1);
			model.setValueAt(especialidad.getApellidos(), fila, 2);
			model.setValueAt(especialidad.getEdad(), fila, 3);
			fila++;
		}

		// Se alinea el texto de las columnas
		Renderer tcr = new Renderer();
		tcr.setHorizontalAlignment(SwingConstants.CENTER);
		tabla.getColumnModel().getColumn(0).setCellRenderer(tcr);
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
