package Vista;

import java.awt.Color;
import java.awt.Component;
import java.awt.ComponentOrientation;
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

import Controlador.ConexionMySQL;
import Modelo.ClienteHibernate;
import Modelo.InventarioHibernate;
import Modelo.ProveedorHibernate;
import Modelo.SpecialityHibernate;
import Modelo.TreatmentsHibernate;
import Modelo.UserHibernate;
import Otros.RoundedPanel;
import Vista.AdminClinic.Renderer;
import btndentiapp.ButtonDentiApp;

public class AdminStock extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private ConexionMySQL conex;
	private JFrame parent, frame;
	private UserHibernate userHi;
	private SessionFactory instancia;
	private Session session;

	private JTable tableStock;
	private int lastIdProducto;
	private String selectedProduct = null;
	private InventarioHibernate ph;

	private JTable tableProveedor;
	private String selectedProveedor = null;
	private ProveedorHibernate prh;
	private LineBorder lb2 = new LineBorder(new Color(148, 220, 219), 3, true);

	private LineBorder lb = new LineBorder(new Color(240, 240, 240), 3, true);
	private Font font = new Font("Dialog", Font.BOLD, 15);
	private Color azulito = new Color(148, 220, 219);

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AdminStock frame = new AdminStock(null, null);
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
	public AdminStock(UserHibernate userHi, JFrame parent) {
		this.userHi = userHi;

		// -------------------- Conexión ------------------
		this.instancia = (SessionFactory) new Configuration().configure("hibernate.cfg.xml")
				.addAnnotatedClass(InventarioHibernate.class).addAnnotatedClass(ProveedorHibernate.class)
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
		setLocationRelativeTo(null);
		contentPane.setLayout(null);

		// -------------------- Componentes Gráficos --------------------
		// menu bar
		// nombre
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
		contentPane.add(menuPane);

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
		ButtonDentiApp btnStock = new ButtonDentiApp(0, 540, true,
				new ImageIcon(getClass().getResource("/Resources/images/stock.png")));
		btnStock.setToolTipText("Módulo de materiales (Alt+I)");

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

		// Inventario

		// Panel fondo de stock
		JPanel panelBackStock = new RoundedPanel(50, azulito);
		panelBackStock.setBounds(230, 200, 660, 785);
		panelBackStock.setOpaque(false);
		panelBackStock.setLayout(null);
		contentPane.add(panelBackStock);

		// Panel titulo de stock
		JPanel panelTitleStock = new JPanel();
		panelTitleStock.setBounds(25, 15, 610, 745);
		panelTitleStock.setBorder(new TitledBorder(lb, "  Inventario  ", TitledBorder.LEFT, TitledBorder.TOP, font,
				new Color(51, 51, 51)));
		panelTitleStock.setOpaque(false);
		panelTitleStock.setLayout(null);
		panelBackStock.add(panelTitleStock);

		// Panel del Menú
		JPanel menuStock = new JPanel();
		menuStock.setBackground(new Color(148, 220, 219));
		menuStock.setBounds(30, 50, 600, 705);
		menuStock.setLayout(null);
		panelBackStock.add(menuStock);

		// Lupa
		JLabel jlLupaInventario = new JLabel();
		jlLupaInventario.setBackground(new Color(148, 220, 219));
		jlLupaInventario.setBounds(20, -5, 40, 30);
		jlLupaInventario.setIcon(new ImageIcon(getClass().getResource("/Resources/images/lookFor.png")));
		menuStock.add(jlLupaInventario);

		// Buscador
		JTextField txtInventario = new JTextField();
		txtInventario.setBorder(new LineBorder(new Color(148, 220, 219)));
		txtInventario.setBounds(65, 0, 200, 25);
		txtInventario.setBackground(new Color(255, 255, 255));
		txtInventario.setToolTipText("Buscador");
		menuStock.add(txtInventario);

		// Botón de insertar producto
		JButton btnInsertProduct = new JButton();
		btnInsertProduct.setBackground(new Color(148, 220, 219));
		btnInsertProduct.setBounds(520, -5, 40, 30);
		btnInsertProduct.setIcon(new ImageIcon(getClass().getResource("/Resources/images/add.png")));
		btnInsertProduct.setBorderPainted(false);
		btnInsertProduct.setToolTipText("Insertar Producto");
		menuStock.add(btnInsertProduct);

		// Botón de modificar producto
		JButton btnUpadateProduct = new JButton();
		btnUpadateProduct.setBackground(new Color(148, 220, 219));
		btnUpadateProduct.setBounds(560, -5, 40, 30);
		btnUpadateProduct.setIcon(new ImageIcon(getClass().getResource("/Resources/images/edit.png")));
		btnUpadateProduct.setBorderPainted(false);
		btnUpadateProduct.setToolTipText("Modificar Producto");
		menuStock.add(btnUpadateProduct);

		// ScrollPane para cargar la talbla inventario
		JScrollPane menuTableStock = new JScrollPane();
		menuTableStock.setBorder(BorderFactory.createEmptyBorder());
		menuTableStock.setBounds(0, 30, 600, 675);
		menuTableStock.setBackground(new Color(148, 220, 219));
		menuStock.add(menuTableStock);

		// Tabla
		tableStock = new JTable();
		tableStock.setShowVerticalLines(false);
		tableStock.setShowHorizontalLines(false);
		tableStock.setCellSelectionEnabled(true);
		tableStock.setBounds(0, 0, 600, 675);
		tableStock.setBackground(new Color(250, 250, 250));
		tableStock.setSelectionBackground(new Color(148, 220, 219));
		tableStock.setShowGrid(false);
		tableStock.setBorder(null);
		tableStock.setFont(new Font("Tahoma", Font.PLAIN, 18));
		tableStock.setRowHeight(35);
		tableStock.getTableHeader().setFont(new Font("Tahoma", Font.PLAIN, 18));
		tableStock.getTableHeader().setBackground(new Color(148, 220, 219));
		tableStock.getTableHeader().setBorder(new LineBorder(new Color(148, 220, 219)));
		menuTableStock.add(tableStock);
		menuTableStock.setViewportView(tableStock);

		// Proveedores
		// Panel fondo de Proveedores
		JPanel panelBackUser = new RoundedPanel(50, azulito);
		panelBackUser.setBounds(970, 200, 860, 785);
		panelBackUser.setOpaque(false);
		panelBackUser.setLayout(null);
		contentPane.add(panelBackUser);

		// Panel titulo de Proveedores
		JPanel panelTitleUsers = new JPanel();
		panelTitleUsers.setBounds(25, 15, 810, 745);
		panelTitleUsers.setBorder(new TitledBorder(lb, "  Proveedores  ", TitledBorder.LEFT, TitledBorder.TOP, font,
				new Color(51, 51, 51)));
		panelTitleUsers.setOpaque(false);
		panelTitleUsers.setLayout(null);
		panelBackUser.add(panelTitleUsers);

		// Panel del Menú
		JPanel menuProovedores = new JPanel();
		menuProovedores.setBackground(new Color(148, 220, 219));
		menuProovedores.setBounds(30, 50, 800, 705);
		menuProovedores.setLayout(null);
		panelBackUser.add(menuProovedores);

		// Lupa
		JLabel jlLupaProveedores = new JLabel();
		jlLupaProveedores.setBackground(new Color(148, 220, 219));
		jlLupaProveedores.setBounds(5, -5, 40, 30);
		jlLupaProveedores.setIcon(new ImageIcon(getClass().getResource("/Resources/images/lookFor.png")));
		menuProovedores.add(jlLupaProveedores);

		// Buscador
		JTextField txtProveedores = new JTextField();
		txtProveedores.setBorder(new LineBorder(new Color(148, 220, 219)));
		txtProveedores.setBounds(45, 0, 200, 25);
		txtProveedores.setBackground(new Color(255, 255, 255));
		txtProveedores.setToolTipText("Buscador");
		menuProovedores.add(txtProveedores);

		// Botón de insertar proveedor
		JButton btnInsertProveedor = new JButton();
		btnInsertProveedor.setBackground(new Color(148, 220, 219));
		btnInsertProveedor.setBounds(720, -5, 40, 30);
		btnInsertProveedor.setIcon(new ImageIcon(getClass().getResource("/Resources/images/add.png")));
		btnInsertProveedor.setBorderPainted(false);
		btnInsertProveedor.setToolTipText("Insertar Producto");
		menuProovedores.add(btnInsertProveedor);

		// Botón de modificar proveedor
		JButton btnUpadateProveedor = new JButton();
		btnUpadateProveedor.setBackground(new Color(148, 220, 219));
		btnUpadateProveedor.setBounds(760, -5, 40, 30);
		btnUpadateProveedor.setIcon(new ImageIcon(getClass().getResource("/Resources/images/edit.png")));
		btnUpadateProveedor.setBorderPainted(false);
		btnUpadateProveedor.setToolTipText("Modificar Producto");
		menuProovedores.add(btnUpadateProveedor);

		// ScrollPane para cargar la talbla inventario
		JScrollPane menuTableProveedor = new JScrollPane();
		menuTableProveedor.setBorder(BorderFactory.createEmptyBorder());
		menuTableProveedor.setBounds(0, 30, 800, 675);
		menuTableProveedor.setBackground(new Color(148, 220, 219));
		menuProovedores.add(menuTableProveedor);

		// Tabla
		tableProveedor = new JTable();
		tableProveedor.setShowVerticalLines(false);
		tableProveedor.setShowHorizontalLines(false);
		tableProveedor.setCellSelectionEnabled(true);
		tableProveedor.setBounds(0, 0, 800, 675);
		tableProveedor.setBackground(new Color(250, 250, 250));
		tableProveedor.setSelectionBackground(new Color(148, 220, 219));
		tableProveedor.setShowGrid(false);
		tableProveedor.setBorder(null);
		tableProveedor.setFont(new Font("Tahoma", Font.PLAIN, 18));
		tableProveedor.setRowHeight(35);
		tableProveedor.getTableHeader().setFont(new Font("Tahoma", Font.PLAIN, 18));
		tableProveedor.getTableHeader().setBackground(new Color(148, 220, 219));
		tableProveedor.getTableHeader().setBorder(new LineBorder(new Color(148, 220, 219)));
		menuTableProveedor.add(tableProveedor);
		menuTableProveedor.setViewportView(tableProveedor);

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

		// Mostrar las tablas
		loadTableStock(tableStock);
		loadTableProveedores(tableProveedor);

		// Acción de seleccionar elemento de la tabla Inventario
		tableStock.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evnt) {
				if (evnt.getClickCount() == 1) {

					// Deselecciona en la tabla proveedores
					tableProveedor.setColumnSelectionAllowed(false);
					tableProveedor.setCellSelectionEnabled(false);
					tableProveedor.setColumnSelectionAllowed(false);
					tableProveedor.setCellSelectionEnabled(false);
					selectedProveedor = null;

					// Seleccionar row
					tableStock.addColumnSelectionInterval(0, 1);

					// Cambios en la selección
					tableStock.setColumnSelectionAllowed(false);
					tableStock.setCellSelectionEnabled(false);
					tableStock.setColumnSelectionAllowed(true);
					tableStock.setCellSelectionEnabled(true);

					// selección de la especialidad
					selectedProduct = tableStock.getValueAt(tableStock.getSelectedRow(), tableStock.getSelectedColumn())
							.toString();

					System.out.println("Producto " + selectedProduct);
				}
			}
		});

		// Acción de seleccionar elemento de la tabla Proveedor
		tableProveedor.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evnt) {
				if (evnt.getClickCount() == 1) {

					// Deselecciona en la tabla Inventario
					tableStock.setColumnSelectionAllowed(false);
					tableStock.setCellSelectionEnabled(false);
					tableStock.setColumnSelectionAllowed(false);
					tableStock.setCellSelectionEnabled(false);
					selectedProduct = null;

					// Seleccionar row
					tableProveedor.addColumnSelectionInterval(0, 3);

					// Cambios en la selección
					tableProveedor.setColumnSelectionAllowed(false);
					tableProveedor.setCellSelectionEnabled(false);
					tableProveedor.setColumnSelectionAllowed(true);
					tableProveedor.setCellSelectionEnabled(true);

					// selección de la especialidad
					selectedProveedor = tableProveedor
							.getValueAt(tableProveedor.getSelectedRow(), tableProveedor.getSelectedColumn()).toString();

					System.out.println("CIF " + selectedProveedor);
				}
			}
		});

		// Acción de desseleccionar de las tablas
		contentPane.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evnt) {
				if (evnt.getClickCount() == 1) {
					// Cambios en la selección
					tableStock.setColumnSelectionAllowed(false);
					tableStock.setCellSelectionEnabled(false);
					tableStock.setColumnSelectionAllowed(false);
					tableStock.setCellSelectionEnabled(false);
					selectedProduct = null;

					tableProveedor.setColumnSelectionAllowed(false);
					tableProveedor.setCellSelectionEnabled(false);
					tableProveedor.setColumnSelectionAllowed(false);
					tableProveedor.setCellSelectionEnabled(false);
					selectedProveedor = null;
				}
			}
		});

		// Acción de buscar en la tabla
		txtInventario.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {

			}

			@Override
			public void keyReleased(KeyEvent e) {
				loadSearchStock(tableStock, txtInventario.getText());
			}

			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub

			}
		});

		// Acción de insertar en proveedor
		btnInsertProveedor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Crea el JDialog para modificar el tratamiento
				InsertProveedor ut = new InsertProveedor(instancia);
				ut.setSession(session);
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
						if (!txtProveedores.getText().isBlank()) {
							loadSearchProveedor(tableProveedor, txtProveedores.getText());
						} else {
							// Recargamos la tabla
							loadTableProveedores(tableProveedor);
						}
					}

					@Override
					public void windowActivated(WindowEvent e) {
						// TODO Auto-generated method stub

					}
				});
			}
		});

		// Acción de buscar en la tabla
		txtProveedores.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {

			}

			@Override
			public void keyReleased(KeyEvent e) {
				loadSearchProveedor(tableProveedor, txtProveedores.getText());
			}

			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
			}
		});

		// Acción de insertar en producto
		btnInsertProduct.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Crea el JDialog para modificar el tratamiento
				InsertarProducto ut = new InsertarProducto(instancia);

				// Relaiza la consulta
				String hql = "FROM ProveedorHibernate";
				Query<ProveedorHibernate> consulta = session.createQuery(hql, ProveedorHibernate.class);

				// Guarda los datos en una lista
				List<ProveedorHibernate> results = consulta.getResultList();

				for (ProveedorHibernate p : results) {
					ut.addCboxItem(p.getCif());
				}

				ut.setIdProducto(lastIdProducto + 1);
				ut.setSession(session);
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
						if (!txtInventario.getText().isBlank()) {
							loadSearchStock(tableStock, txtInventario.getText());
						} else {
							// Recargamos la tabla
							loadTableStock(tableStock);
						}
					}

					@Override
					public void windowActivated(WindowEvent e) {
						// TODO Auto-generated method stub

					}
				});
			}
		});

		// Acción de modificar inventario
		btnUpadateProduct.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				// Comprueba si hay una especialidad a modificar
				if (selectedProduct != null) {
					// Busca la especialidad
					String hql = "FROM InventarioHibernate WHERE nombre=:nombre";
					Query<InventarioHibernate> consulta = session.createQuery(hql, InventarioHibernate.class);
					consulta.setParameter("nombre", selectedProduct);
					ph = consulta.getSingleResult();

					// Feedback al usuario
					if (ph.getId_producto() == null) {
						JOptionPane.showMessageDialog(contentPane, "No se ha encontrado la especialidad", "Error",
								JOptionPane.ERROR_MESSAGE);
					} else {
						// Crea el JDialog para modificar el tratamiento
						ChangeProducto ut = new ChangeProducto(instancia, ph);
						ut.setSession(session);
						ut.setTextCantidad(ph.getCantidad().toString());
						ut.setTextNombre(ph.getNombre());
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
								if (!txtInventario.getText().isBlank()) {
									loadSearchStock(tableStock, txtInventario.getText());
								} else {
									// Recargamos la tabla
									loadTableStock(tableStock);
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

		// Acción de Modificar proveedor
		btnUpadateProveedor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				// Comprueba si hay un tratamiento seleccionado
				if (selectedProveedor != null) {
					// Busca el tratmiento
					String hql = "FROM ProveedorHibernate WHERE cif=:cif";
					Query<ProveedorHibernate> consulta = session.createQuery(hql, ProveedorHibernate.class);
					consulta.setParameter("cif", selectedProveedor);
					prh = consulta.getSingleResult();

					// Feedback al usuario
					if (prh.getCif() == null) {
						JOptionPane.showMessageDialog(contentPane, "No se ha encontrado el tratamiento", "Error",
								JOptionPane.ERROR_MESSAGE);
					} else {
						// Crea el JDialog para modificar el tratamiento
						ChangeProveedor ut = new ChangeProveedor(instancia, prh);
						ut.setSession(session);
						ut.setTextCif(prh.getCif());
						ut.setTextNombre(prh.getNombre());
						ut.setTextCorreo(prh.getCorreo());
						ut.setEstado(prh.getEstado());
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
								if (!txtProveedores.getText().isBlank()) {
									loadSearchProveedor(tableProveedor, txtProveedores.getText());
								} else {
									// Recargamos la tabla
									loadTableProveedores(tableProveedor);
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

	}

	// -------------------- Métodos y funciones --------------------
	public void loadTableStock(JTable table) {
		// Relaiza la consulta
		String hql = "FROM InventarioHibernate";
		Query<InventarioHibernate> consulta = session.createQuery(hql, InventarioHibernate.class);

		// Guarda los datos en una lista
		List<InventarioHibernate> results = consulta.getResultList();

		// Prepara la tabla
		DefaultTableModel model = new DefaultTableModel(new Object[][] {}, new String[] { "Producto", "Cantidad" }) {
			@Override
			public boolean isCellEditable(int row, int column) {
				// all cells false
				return false;
			}
		};
		table.setModel(model);
		JTableHeader header = table.getTableHeader();
		if (results.size() < 20) {
			model.setRowCount(19);
		} else {
			model.setRowCount(results.size());
		}
		int fila = 0, columna = 0;

		// Carga los datos
		for (InventarioHibernate producto : results) {
			model.setValueAt(producto.getNombre(), fila, columna);
			model.setValueAt(producto.getCantidad(), fila, columna + 1);
			fila++;
		}

		// Se alinea el texto de las columnas
		Renderer tcr = new Renderer();
		tcr.setHorizontalAlignment(SwingConstants.CENTER);
		table.getColumnModel().getColumn(0).setCellRenderer(tcr);
		table.setDefaultRenderer(Object.class, tcr);

		int[] anchos = { 400, 200 };
		for (int i = 0; i < 2; i++) {
			table.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
		}

		// Guarda el último id del inventario
		if (!results.isEmpty()) {
			lastIdProducto = results.getLast().getId_producto();
		} else {
			lastIdProducto = 0;
		}
	}

	// Método para hacer consulta en el buscador
	public void loadSearchStock(JTable tabla, String busq) {
		// Relaiza la consulta
		this.session = instancia.openSession();
		String hql = "FROM InventarioHibernate where nombre like :busq";
		Query<InventarioHibernate> consulta = session.createQuery(hql, InventarioHibernate.class);
		consulta.setParameter("busq", "%" + busq + "%");

		// Guarda los datos en una lista
		List<InventarioHibernate> results = consulta.getResultList();

		// Prepara la tabla
		DefaultTableModel model = new DefaultTableModel(new Object[][] {}, new String[] { "Producto", "Cantidad" }) {
			@Override
			public boolean isCellEditable(int row, int column) {
				// all cells false
				return false;
			}
		};
		tableStock.setModel(model);
		JTableHeader header = tableStock.getTableHeader();
		if (results.size() < 20) {
			model.setRowCount(19);
		} else {
			model.setRowCount(results.size());
		}
		int fila = 0, columna = 0;

		// Carga los datos
		for (InventarioHibernate producto : results) {
			model.setValueAt(producto.getNombre(), fila, columna);
			model.setValueAt(producto.getCantidad(), fila, columna + 1);
			fila++;
		}

		// Se alinea el texto de las columnas
		Renderer tcr = new Renderer();
		tcr.setHorizontalAlignment(SwingConstants.CENTER);
		tableStock.getColumnModel().getColumn(0).setCellRenderer(tcr);
		tableStock.setDefaultRenderer(Object.class, tcr);

		int[] anchos = { 400, 200 };
		for (int i = 0; i < 2; i++) {
			tableStock.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
		}
	}

	public void loadTableProveedores(JTable table) {
		// Relaiza la consulta
		String hql = "FROM ProveedorHibernate";
		Query<ProveedorHibernate> consulta = session.createQuery(hql, ProveedorHibernate.class);

		// Guarda los datos en una lista
		List<ProveedorHibernate> results = consulta.getResultList();

		// Prepara la tabla
		DefaultTableModel model = new DefaultTableModel(new Object[][] {},
				new String[] { "CIF", "Nombre", "Estado", "Correo" }) {
			@Override
			public boolean isCellEditable(int row, int column) {
				// all cells false
				return false;
			}
		};
		table.setModel(model);
		JTableHeader header = table.getTableHeader();
		if (results.size() < 20) {
			model.setRowCount(19);
		} else {
			model.setRowCount(results.size());
		}
		int fila = 0, columna = 0;

		// Carga los datos
		for (ProveedorHibernate proveedor : results) {
			model.setValueAt(proveedor.getCif(), fila, columna);
			model.setValueAt(proveedor.getNombre(), fila, columna + 1);
			if (proveedor.getEstado() == 0) {
				model.setValueAt("Inactivo", fila, columna + 2);
			} else {
				model.setValueAt("Activo", fila, columna + 2);
			}
			model.setValueAt(proveedor.getCorreo(), fila, columna + 3);
			fila++;
		}

		// Se alinea el texto de las columnas
		Renderer tcr = new Renderer();
		tcr.setHorizontalAlignment(SwingConstants.CENTER);
		table.getColumnModel().getColumn(0).setCellRenderer(tcr);
		table.setDefaultRenderer(Object.class, tcr);

		int[] anchos = { 125, 250, 75, 350 };
		for (int i = 0; i < 4; i++) {
			table.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
		}

	}

	// Método para hacer consulta en el buscador de proveedor
	public void loadSearchProveedor(JTable tabla, String busq) {
		// Relaiza la consulta
		this.session = instancia.openSession();
		String hql = "FROM ProveedorHibernate where nombre like :busq or cif like :busq or correo like :busq";
		Query<ProveedorHibernate> consulta = session.createQuery(hql, ProveedorHibernate.class);
		consulta.setParameter("busq", "%" + busq + "%");

		// Guarda los datos en una lista
		List<ProveedorHibernate> results = consulta.getResultList();

		// Prepara la tabla
		DefaultTableModel model = new DefaultTableModel(new Object[][] {},
				new String[] { "CIF", "Nombre", "Estado", "Correo" }) {
			@Override
			public boolean isCellEditable(int row, int column) {
				// all cells false
				return false;
			}
		};
		tabla.setModel(model);
		JTableHeader header = tabla.getTableHeader();
		if (results.size() < 20) {
			model.setRowCount(19);
		} else {
			model.setRowCount(results.size());
		}
		int fila = 0, columna = 0;

		// Carga los datos
		for (ProveedorHibernate proveedor : results) {
			model.setValueAt(proveedor.getCif(), fila, columna);
			model.setValueAt(proveedor.getNombre(), fila, columna + 1);
			model.setValueAt(proveedor.getEstado(), fila, columna + 2);
			model.setValueAt(proveedor.getCorreo(), fila, columna + 3);
			fila++;
		}

		// Se alinea el texto de las columnas
		Renderer tcr = new Renderer();
		tcr.setHorizontalAlignment(SwingConstants.CENTER);
		tabla.getColumnModel().getColumn(0).setCellRenderer(tcr);
		tabla.setDefaultRenderer(Object.class, tcr);

		int[] anchos = { 125, 250, 75, 350 };
		for (int i = 0; i < 4; i++) {
			tabla.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
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
