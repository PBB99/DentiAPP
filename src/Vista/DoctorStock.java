package Vista;

import java.awt.Color;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.help.HelpBroker;
import javax.help.HelpSet;
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
	private List<InventarioHibernate> elegidos = new ArrayList<>();
	private boolean paso = false;
	private int filaPedido = 0;
	private Color azulito = new Color(148, 220, 219);
	private LineBorder lb = new LineBorder(new Color(240, 240, 240), 3, true);
	private Font font = new Font("Dialog", Font.BOLD, 15);
	private List<InventarioHibernate> listInvent;
	private String selectedProductPedido = null;
	private Boolean borradoFila = false;

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

		JPanel menuStock = new JPanel();
		menuStock.setBackground(new Color(148, 220, 219));
		menuStock.setBounds(30, 50, 600, 705);
		menuStock.setLayout(null);
		panelBackStock.add(menuStock);

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

		// Historial pedidos
		// Panel fondo de Proveedores
		// confirmar pedido
		JLabel jConfirmarPedido = new JLabel();
		jConfirmarPedido.setBackground(new Color(148, 220, 219));
		jConfirmarPedido.setBounds(5, 0, 40, 30);
		jConfirmarPedido.setIcon(new ImageIcon(getClass().getResource("/Resources/images/conpedio.png")));
		jConfirmarPedido.setToolTipText("Confirmar Pedido");

		JLabel jResetearPedido = new JLabel();
		jResetearPedido.setBackground(new Color(148, 220, 219));
		jResetearPedido.setBounds(45, 0, 40, 30);
		jResetearPedido.setIcon(new ImageIcon(getClass().getResource("/Resources/images/resetpedio.png")));
		jResetearPedido.setToolTipText("Resetear Pedido");

		JLabel jBorrarSeleccion = new JLabel();
		jBorrarSeleccion.setBackground(new Color(148, 220, 219));
		jBorrarSeleccion.setBounds(85, 0, 40, 30);
		jBorrarSeleccion.setIcon(new ImageIcon(getClass().getResource("/Resources/images/delete.png")));
		jBorrarSeleccion.setToolTipText("Borrar Seleccion");

		JPanel panelBackUser = new RoundedPanel(50, azulito);
		panelBackUser.setBounds(970, 200, 860, 785);
		panelBackUser.setOpaque(false);
		panelBackUser.setLayout(null);
		contentPane.add(panelBackUser);

		// Panel titulo de Proveedores
		JPanel panelTitleUsers = new JPanel();
		panelTitleUsers.setBounds(25, 15, 810, 745);
		panelTitleUsers.setBorder(
				new TitledBorder(lb, "  Pedido  ", TitledBorder.LEFT, TitledBorder.TOP, font, new Color(51, 51, 51)));
		panelTitleUsers.setOpaque(false);
		panelTitleUsers.setLayout(null);
		panelBackUser.add(panelTitleUsers);
		// Panel del Menú
		JPanel menuPedidos = new JPanel();
		menuPedidos.setBackground(new Color(148, 220, 219));
		menuPedidos.setBounds(30, 50, 800, 705);
		menuPedidos.setLayout(null);
		panelBackUser.add(menuPedidos);
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
				try {
                    File fichero = new File("src/help/help_set.hs");
                    //Con esto cargo el fichero de ayuda
                    URL hsURL = fichero.toURI().toURL();

                    HelpSet helpset = new HelpSet(getClass().getClassLoader(), hsURL);
                    HelpBroker hb = helpset.createHelpBroker();

                    hb.enableHelpOnButton(btnHelp, "manual", helpset);

                    //Para colocarlo en la mitad de la pantlla
                    Dimension pantalla = new Dimension(1000,860);
                    Point p = new Point(200, 200);
                    hb.setLocation(p);
                    hb.setSize(pantalla);
                } catch (Exception e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
			}
		});
		contentPane.add(btnHelp);		
		// -------------------- Lógica --------------------

		tablaPedido.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent evnt) {
				if (evnt.getClickCount() == 1) {
					tablaPedido.addColumnSelectionInterval(0, 1);
				}
			}
		});

		jBorrarSeleccion.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				InventarioHibernate borrado = new InventarioHibernate();
				selectedProductPedido = tablaPedido
						.getValueAt(tablaPedido.getSelectedRow(), tablaPedido.getSelectedColumn()).toString();
				int cantidadproducto = Integer.parseInt(tablaPedido
						.getValueAt(tablaPedido.getSelectedRow(), tablaPedido.getSelectedColumn() + 1).toString());
				for (InventarioHibernate x : elegidos) {
					// encontramos el objeto InventarioHibernate para facilitar su update en la base
					// de datos y pasar este objeto a la otra tabla directamente
					if (selectedProductPedido.equalsIgnoreCase(x.getNombre())) {

						borrado = x;

						if (listInvent.contains(x)) {
							listInvent.get(x.getId_producto()).setCantidad(x.getCantidad() + borrado.getCantidad());
							System.out.println("si entra");

						}

					}

				}

				elegidos.remove(borrado);
				recorrerLista(listInvent);
				for (InventarioHibernate y : listInvent) {
					if (y.getId_producto() == borrado.getId_producto()) {
						y.setCantidad(y.getCantidad() + borrado.getCantidad());

					}
				}
				recorrerLista(listInvent);

				loadSearchStock(tablaPedido, "", listInvent);
				loadTablePedidos(tablaPedido, elegidos);

			}
		});
		//esta funcionalidad actualizara la base de datos de inventario y almacenando dicha acción en otra tabla
		jConfirmarPedido.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				session.beginTransaction();
				
				
				for(InventarioHibernate x: listInvent) {
					session.update(x);
					
					
				}
				
				
				
					for(InventarioHibernate y:elegidos) {
						for(InventarioHibernate c:listInvent) {
							if(y.getId_producto()==c.getId_producto()) {
								y=c;
								c.getUsuarios().add(userHi);
								
								
							}
						}
						
						session.update(y);
						
					   
					
				}
					userHi.setProductos(elegidos);
					session.update(userHi);
				
				
					
				
				session.getTransaction().commit();
				
				for (int i = 0; i < tablaPedido.getModel().getRowCount(); i++) {
					tablaPedido.getModel().setValueAt("", i, 0);
					tablaPedido.getModel().setValueAt("", i, 1);
				}
				elegidos.clear();
				listInvent=loadTableStock(tableStock);
				loadSearchStock(tablaPedido, "", listInvent);
				loadTablePedidos(tablaPedido, elegidos);
			}
		});
		
		jResetearPedido.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				for (int i = 0; i < tablaPedido.getModel().getRowCount(); i++) {
					tablaPedido.getModel().setValueAt("", i, 0);
					tablaPedido.getModel().setValueAt("", i, 1);
				}

				for (InventarioHibernate x : listInvent) {

					for (InventarioHibernate y : elegidos) {
						if (x.getId_producto() == y.getId_producto()) {
							x.setCantidad(x.getCantidad() + y.getCantidad());

						}
					}

				}
				elegidos.clear();

				loadSearchStock(tablaPedido, "", listInvent);
				loadTablePedidos(tablaPedido, elegidos);

			}
		});
		
		// Mostrar las tablas
		listInvent = loadTableStock(tableStock);
		// Acción de seleccionar elemento de la tabla Inventario
		tableStock.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evnt) {
				if (evnt.getClickCount() == 1) {
					InventarioHibernate solicitado = new InventarioHibernate();
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
					int resultado = mostrarInputDialog(selectedProduct);
					for (InventarioHibernate x : listInvent) {
						// encontramos el objeto InventarioHibernate para facilitar su update en la base
						// de datos y pasar este objeto a la otra tabla directamente
						if (selectedProduct.equalsIgnoreCase(x.getNombre())) {
							solicitado.setId_producto(x.getId_producto());
							solicitado.setNombre(x.getNombre());
							solicitado.setCantidad(resultado);
							x.setCantidad(x.getCantidad() - resultado);
						}

					}

					// contemplar si la cantidad elegida es posible o no. En caso de que quedara en
					// negativo se quedarie en 0 aparentemente y en negativo en la base de datos
					// pasando si esta por debajo de cero a ser contemplado por el administrador que
					// hara pedidos o no
//					listInvent.get(solicitado.getId_producto()+1).setCantidad(solicitado.getCantidad()-resultado);
//					System.out.println(listInvent.toString());
//					solicitado.setCantidad(resultado);
					// la lista elegido tendra dentro objetos de inventario hibernate
					elegidos.add(solicitado);
					loadTablePedidos(tablaPedido, elegidos);
					paso = true;
					// recarga con las cantidades bien puestas
					reloadTableStock(tableStock, listInvent);
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
				if (paso == true) {
					loadSearchStock(tableStock, txtInventario.getText(), listInvent);
				} else {

					loadSearchStock(tableStock, txtInventario.getText());
				}

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

		menuTableStock.add(tableStock);
		menuTableStock.setViewportView(tableStock);

		menuStock.add(txtInventario);
		menuStock.add(menuTableStock);
		menuStock.add(jlLupaInventario);

		menuPedidos.add(menuTableProveedor);
		menuTableProveedor.add(tablaPedido);
		menuTableProveedor.setViewportView(tablaPedido);
		menuPedidos.add(jConfirmarPedido);
		menuPedidos.add(jBorrarSeleccion);
		menuPedidos.add(jResetearPedido);
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
			if (producto.getCantidad() < 0) {
				model.setValueAt(0, fila, columna + 1);
			} else {

				model.setValueAt(producto.getCantidad(), fila, columna + 1);
			}

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

	// para recargar
	public void secondLoadTableStock(JTable table) {

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
			if (producto.getCantidad() < 0) {
				model.setValueAt(0, fila, columna + 1);
			} else {

				model.setValueAt(producto.getCantidad(), fila, columna + 1);
			}

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

	// Recarga de la tabla
	public void reloadTableStock(JTable table, List<InventarioHibernate> results) {

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
			if (producto.getCantidad() < 0) {
				model.setValueAt(0, fila, columna + 1);
			} else {

				model.setValueAt(producto.getCantidad(), fila, columna + 1);
			}
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
		if (results.size() < 19) {
			model.setRowCount(18);
		} else {
			model.setRowCount(results.size());
		}
		int fila = 0, columna = 0;

		// Carga los datos
		for (InventarioHibernate producto : results) {
			model.setValueAt(producto.getNombre(), fila, columna);
			if (producto.getCantidad() < 0) {
				model.setValueAt(0, fila, columna + 1);
			} else {

				model.setValueAt(producto.getCantidad(), fila, columna + 1);
			}
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

	public void loadSearchStock(JTable tabla, String busq, List<InventarioHibernate> lista) {
//		// Relaiza la consulta
//		this.session = instancia.openSession();
//		String hql = "FROM InventarioHibernate where nombre like :busq";
//		Query<InventarioHibernate> consulta = session.createQuery(hql, InventarioHibernate.class);
//		consulta.setParameter("busq", "%" + busq + "%");

		// Guarda los datos en una lista
		List<InventarioHibernate> results = new ArrayList<InventarioHibernate>();
		for (InventarioHibernate x : lista) {
			if (x.getNombre().toLowerCase().contains(busq.toLowerCase())) {
				results.add(x);
			}
		}
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
			if (producto.getCantidad() < 0) {
				model.setValueAt(0, fila, columna + 1);
			} else {

				model.setValueAt(producto.getCantidad(), fila, columna + 1);
			}
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

	// imprimir cantidad de los productos para comprobar si se cambia o no
	public static void recorrerLista(List<InventarioHibernate> lista) {
		for (InventarioHibernate x : lista) {
			System.out.println(x.getCantidad() + ",");
		}
	}

	public void loadTablePedidos(JTable table, List<InventarioHibernate> productos) {
		// ESta consulta es para fija el tamaño maximo de la tabla pedidos, de tal forma
		// que nunca va a existir mas filas que posibles productos a pedir
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

		// Carga los datos
		int fila = 0;
		int columna = 0;
		for (InventarioHibernate y : productos) {
			model.setValueAt(y.getNombre(), fila, columna);
			model.setValueAt(y.getCantidad(), fila, columna + 1);
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
