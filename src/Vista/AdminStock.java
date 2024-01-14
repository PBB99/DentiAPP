package Vista;

import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
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
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
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
import Modelo.SpecialityHibernate;
import Modelo.TreatmentsHibernate;
import Modelo.UserHibernate;
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
				.addAnnotatedClass(InventarioHibernate.class).buildSessionFactory();
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

		// Panel del Menú
		JPanel menuStock = new JPanel();
		menuStock.setBackground(new Color(148, 220, 219));
		menuStock.setBounds(300, 270, 800, 705);
		menuStock.setLayout(null);

		// Buscador
		JTextField txt = new JTextField();
		txt.setBorder(new LineBorder(new Color(148, 220, 219)));
		txt.setBounds(0, 5, 200, 25);
		txt.setBackground(new Color(255, 255, 255));

		// Botón de insertar producto
		JButton btnInsertProduct = new JButton();
		btnInsertProduct.setBackground(new Color(148, 220, 219));
		btnInsertProduct.setBounds(720, 0, 40, 30);
		btnInsertProduct.setIcon(new ImageIcon(getClass().getResource("/Resources/images/add.png")));
		btnInsertProduct.setBorderPainted(false);

		// Botón de modificar producto
		JButton btnUpadateProduct = new JButton();
		btnUpadateProduct.setBackground(new Color(148, 220, 219));
		btnUpadateProduct.setBounds(760, 0, 40, 30);
		btnUpadateProduct.setIcon(new ImageIcon(getClass().getResource("/Resources/images/edit.png")));
		btnUpadateProduct.setBorderPainted(false);

		// ScrollPane para cargar la talbla inventario
		JScrollPane menuTableStock = new JScrollPane();
		menuTableStock.setBorder(BorderFactory.createEmptyBorder());
		menuTableStock.setBounds(0, 30, 800, 675);
		menuTableStock.setBackground(new Color(148, 220, 219));

		// Tabla
		tableStock = new JTable();
		tableStock.setShowVerticalLines(false);
		tableStock.setShowHorizontalLines(false);
		tableStock.setCellSelectionEnabled(true);
		tableStock.setBounds(0, 0, 800, 675);
		tableStock.setBackground(new Color(250, 250, 250));
		tableStock.setSelectionBackground(new Color(148, 220, 219));
		tableStock.setShowGrid(false);
		tableStock.setBorder(null);
		tableStock.setFont(new Font("Tahoma", Font.PLAIN, 18));
		tableStock.setRowHeight(35);
		tableStock.getTableHeader().setFont(new Font("Tahoma", Font.PLAIN, 18));
		tableStock.getTableHeader().setBackground(new Color(148, 220, 219));
		tableStock.getTableHeader().setBorder(new LineBorder(new Color(148, 220, 219)));

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
		loadTable(tableStock);

		// Acción de seleccionar elemento de la tabla
		tableStock.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evnt) {
				if (evnt.getClickCount() == 1) {

					// Seleccionar row
					tableStock.addColumnSelectionInterval(0, 1);

					// Cambios en la selección
					tableStock.setColumnSelectionAllowed(false);
					tableStock.setCellSelectionEnabled(false);
					tableStock.setColumnSelectionAllowed(true);
					tableStock.setCellSelectionEnabled(true);
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
				}
			}
		});

		// Acción de buscar en la tabla
		txt.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {

			}

			@Override
			public void keyReleased(KeyEvent e) {
				loadSearch(tableStock, txt.getText());
			}

			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub

			}
		});

		// -------------------- Adiciones a los paneles --------------------
		contentPane.add(menuStock);
		contentPane.add(menuPane);

		menuPane.add(lblLogo);
		menuPane.add(btnAppointment);
		menuPane.add(btnUsers);
		menuPane.add(btnCustomers);
		menuPane.add(btnStock);
		menuPane.add(btnClinic);
		menuPane.add(btnPayments);
		
		menuTableStock.add(tableStock);
		menuTableStock.setViewportView(tableStock);
		
		menuStock.add(txt);
		menuStock.add(menuTableStock);
		menuStock.add(btnInsertProduct);
		menuStock.add(btnUpadateProduct);
	}

	// -------------------- Métodos y funciones --------------------
	public void loadTable(JTable table) {
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
		if (results.size() < 19) {
			model.setRowCount(18);
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
	}

	// Método para hacer consulta en el buscador
	public void loadSearch(JTable tabla, String busq) {
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
		if (results.size() < 19) {
			model.setRowCount(18);
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
