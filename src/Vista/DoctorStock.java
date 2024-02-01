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
import java.util.ArrayList;
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
import Modelo.InventarioHibernate;
import Modelo.ProveedorHibernate;
import Modelo.UserHibernate;
import Otros.RoundedPanel;
import Vista.AdminStock.Renderer;
import btndentiapp.ButtonDentiApp;

public class DoctorStock extends JFrame {
	private SessionFactory instancia;
	private Session session;
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private ConexionMySQL conex;
	private JFrame parent, frame;
	private LineBorder lb2 = new LineBorder(new Color(148, 220, 219), 3, true);
	private UserHibernate userHi;
	private int id_producto;
	private JTable tableStock;
	private int lastIdProducto;
	private String selectedProduct = null;
	private InventarioHibernate ph;
	private JTable tablaPedido;
	private List<InventarioHibernate> elegidos=new ArrayList<>();
	private InventarioHibernate solicitado;
	private int filaPedido=0;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
//				try {
//					DoctorStock frame = new DoctorStock(null,null);
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
	public DoctorStock(UserHibernate mainUser, JFrame parent) {
		// -------------------- JFrame --------------------
		this.frame = this;
		this.parent = parent;
		this.userHi = mainUser;

		this.instancia = (SessionFactory) new Configuration().configure("hibernate.cfg.xml")
				.addAnnotatedClass(InventarioHibernate.class).addAnnotatedClass(ProveedorHibernate.class)
				.buildSessionFactory();
		this.session = instancia.openSession();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setResizable(false);
		setContentPane(contentPane);
		contentPane.setLayout(null);

		// -------------------- Componentes --------------------
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
		ButtonDentiApp btnAppointment = new ButtonDentiApp(0, 135, false,
				new ImageIcon(getClass().getResource("/Resources/images/calendarGrey.png")));
		btnAppointment.setToolTipText("Módulo de citas (Alt+C)");
		btnAppointment.setMnemonic(KeyEvent.VK_C);

		// Botón de Pacientes
		ButtonDentiApp btnCustomers = new ButtonDentiApp(0, 270, false,
				new ImageIcon(getClass().getResource("/Resources/images/customersGrey.png")));
		btnCustomers.setToolTipText("Módulo de pacientes (Alt+P)");
		btnCustomers.setMnemonic(KeyEvent.VK_P);

		// Botón de Inventario
		ButtonDentiApp btnStock = new ButtonDentiApp(0, 405, true,
				new ImageIcon(getClass().getResource("/Resources/images/stock.png")));
		btnStock.setToolTipText("Módulo de materiales (Alt+I)");

		// Inventario
		// Panel del Menú
		JPanel menuStock = new JPanel();
		menuStock.setBackground(new Color(148, 220, 219));
		menuStock.setBounds(300, 270, 600, 705);
		menuStock.setLayout(null);

		// Lupa
		JLabel jlLupaInventario = new JLabel();
		jlLupaInventario.setBackground(new Color(148, 220, 219));
		jlLupaInventario.setBounds(5, 0, 40, 30);
		jlLupaInventario.setIcon(new ImageIcon(getClass().getResource("/Resources/images/lookFor.png")));

		// Buscador
		JTextField txtInventario = new JTextField();
		txtInventario.setBorder(new LineBorder(new Color(148, 220, 219)));
		txtInventario.setBounds(45, 5, 200, 25);
		txtInventario.setBackground(new Color(255, 255, 255));
		txtInventario.setToolTipText("Buscador");

		// ScrollPane para cargar la talbla inventario
		JScrollPane menuTableStock = new JScrollPane();
		menuTableStock.setBorder(BorderFactory.createEmptyBorder());
		menuTableStock.setBounds(0, 30, 600, 675);
		menuTableStock.setBackground(new Color(148, 220, 219));

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

		// Pedidos
		// Panel del Menú
		JPanel menuPedidos = new JPanel();
		menuPedidos.setBackground(new Color(148, 220, 219));
		menuPedidos.setBounds(1000, 270, 800, 705);
		menuPedidos.setLayout(null);
		// ScrollPane para cargar la talbla inventario
		JScrollPane menuTableProveedor = new JScrollPane();
		menuTableProveedor.setBorder(BorderFactory.createEmptyBorder());
		menuTableProveedor.setBounds(0, 30, 800, 675);
		menuTableProveedor.setBackground(new Color(148, 220, 219));

		// Tabla
		tablaPedido = new JTable();
		tablaPedido.setShowVerticalLines(false);
		tablaPedido.setShowHorizontalLines(false);
		tablaPedido.setCellSelectionEnabled(true);
		tablaPedido.setBounds(0, 0, 800, 675);
		tablaPedido.setBackground(new Color(250, 250, 250));
		tablaPedido.setSelectionBackground(new Color(148, 220, 219));
		tablaPedido.setShowGrid(false);
		tablaPedido.setBorder(null);
		tablaPedido.setFont(new Font("Tahoma", Font.PLAIN, 18));
		tablaPedido.setRowHeight(35);
		tablaPedido.getTableHeader().setFont(new Font("Tahoma", Font.PLAIN, 18));
		tablaPedido.getTableHeader().setBackground(new Color(148, 220, 219));
		tablaPedido.getTableHeader().setBorder(new LineBorder(new Color(148, 220, 219)));
		// -------------------- Lógica --------------------
		// Mostrar las tablas
		List<InventarioHibernate> listInvent = loadTableStock(tableStock);
		// Acción de seleccionar elemento de la tabla Inventario
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

					// selección del poroducto
					selectedProduct = tableStock.getValueAt(tableStock.getSelectedRow(), tableStock.getSelectedColumn())
							.toString();
					// recorrer y encontrar el id, a traves de for each con una lista local que se
					// carga cada vez que cargamos la lista por que buscar por nombre puede dar
					// problemas
					for (InventarioHibernate x : listInvent) {
						if (selectedProduct.equalsIgnoreCase(x.getNombre())) {
							solicitado=x;
						}

					}

					int resultado = mostrarInputDialog(selectedProduct);
					// contemplar si la cantidad elegida es posible o no
					if (resultado <= solicitado.getCantidad()) {
						solicitado.setCantidad(resultado);
						elegidos.add(solicitado);
						loadTablePedidos(tablaPedido, elegidos);
					} else {
						JOptionPane.showMessageDialog(null, "La cantidad elegida no esta disponible.", "Error",
								JOptionPane.ERROR_MESSAGE);
					}

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

		// logica click item salir
		ItemOut.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.out.println("funciona");
				Login login = new Login(frame);
				login.setVisible(true);

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
				System.out.println("PINCHADO");

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

		// Acción de ir a Módulo Citas
		btnAppointment.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DoctorAppointment docAppointment = new DoctorAppointment(mainUser, frame);
				docAppointment.setVisible(true);
			}
		});

		// Acción de ir a Módulo pacientes
		btnCustomers.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DoctorCustomers docCustomers = new DoctorCustomers(mainUser, frame);
				docCustomers.setVisible(true);
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
		contentPane.add(menuStock);
		menuTableStock.add(tableStock);
		menuTableStock.setViewportView(tableStock);

		menuStock.add(txtInventario);
		menuStock.add(menuTableStock);
		menuStock.add(jlLupaInventario);
		contentPane.add(menuPedidos);

		menuPedidos.add(menuTableProveedor);
		menuTableProveedor.add(tablaPedido);
		menuTableProveedor.setViewportView(tablaPedido);

	}

	public List<InventarioHibernate> loadTableStock(JTable table) {
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
		return results;
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

		int[] anchos = { 400, 200 };
		for (int i = 0; i < 2; i++) {
			tableStock.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
		}
	}

	public static int mostrarInputDialog(String producto) {
		// Crear un cuadro de diálogo de entrada
		int numero = 0;
		String input = JOptionPane.showInputDialog("Ingrese cuantas cantidades vas a utilizar de : " + producto);

		try {
			// Intenta convertir la entrada a un entero
			numero = Integer.parseInt(input);
		} catch (NumberFormatException e) {
			// En caso de error, muestra un mensaje y vuelve a llamar al método
			JOptionPane.showMessageDialog(null, "Por favor, ingrese un número válido.", "Error",
					JOptionPane.ERROR_MESSAGE);

		}
		return numero;
	}

	public void loadTablePedidos(JTable table, List<InventarioHibernate> productos) {
		//ESta consulta es para fija el tamaño maximo de la tabla pedidos, de tal forma que nunca va a existir mas filas que posibles productos a pedir
		// Relaiza la consulta
				String hql = "FROM InventarioHibernate";
				Query<InventarioHibernate> consulta = session.createQuery(hql, InventarioHibernate.class);

				// Guarda los datos en una lista
				List<InventarioHibernate> results = consulta.getResultList();


		// Prepara la tabla
		DefaultTableModel model = new DefaultTableModel(new Object[][] {},
				new String[] { "Producto", "Cantidad" }) {
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
		
		// Carga los datos
		int fila=0;
		int columna=0;
		for(InventarioHibernate y:productos) {
			model.setValueAt(y.getNombre(), fila, columna);
			model.setValueAt(y.getCantidad(), fila, columna + 1);
			fila++;
		}
		

		// Se alinea el texto de las columnas
		Renderer tcr = new Renderer();
		tcr.setHorizontalAlignment(SwingConstants.CENTER);
		table.getColumnModel().getColumn(0).setCellRenderer(tcr);
		table.setDefaultRenderer(Object.class, tcr);

		int[] anchos = { 400, 200};
		for (int i = 0; i < 2; i++) {
			table.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
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
