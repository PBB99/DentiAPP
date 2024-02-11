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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.Icon;
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
import Modelo.CitaHibernate;
import Modelo.ClienteHibernate;
import Modelo.OdontogramaHibernate;
import Modelo.SpecialityHibernate;
import Modelo.TreatmentsHibernate;
import Modelo.UserHibernate;
import Otros.RoundedPanel;
import Vista.AdminCustomers.Renderer;
import btndentiapp.ButtonDentiApp;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;

import javax.swing.JComboBox;

public class AdminPayments extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private ConexionMySQL conex;
	private JFrame parent, frame;
	private UserHibernate userHi;
	private JTable table;
	private JTable tableHis;
	private SessionFactory instancia;
	private Session session;
	private String selected;

	private LineBorder lb = new LineBorder(new Color(240, 240, 240), 3, true);
	private Font font = new Font("Dialog", Font.BOLD, 15);
	private LineBorder lb2 = new LineBorder(new Color(148, 220, 219), 3, true);
	private Color azulito = new Color(148, 220, 219);
	private JasperReport reporte;
	private ConexionMySQL cn;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
//				try {
//					AdminPayments frame = new AdminPayments(null,null);
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
	public AdminPayments(UserHibernate userHi, JFrame parent) {
		this.userHi = userHi;
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
		// menu bar
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
		ButtonDentiApp btnClinic = new ButtonDentiApp(0, 675, false,
				new ImageIcon(getClass().getResource("/Resources/images/clinicGrey.png")));
		btnClinic.setToolTipText("Módulo clínico (Alt+L)");
		btnClinic.setMnemonic(KeyEvent.VK_L);

		// Botón del Módulo economico
		ButtonDentiApp btnPayments = new ButtonDentiApp(0, 810, true,
				new ImageIcon(getClass().getResource("/Resources/images/payments.png")));
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

		// Acción del Módulo de la clínica
		btnClinic.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AdminClinic admClinic = new AdminClinic(userHi, frame);
				admClinic.setVisible(true);
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
				// session.close();

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
				// session.close();

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
		panelClientes.setBounds(232, 200, 500, 735);
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

		JTextField txt = new JTextField();
		txt.setBounds(44, 30, 180, 30);
//		txt.addActionListener(new ActionListener() {
//
//			public void actionPerformed(ActionEvent e) {
//
//				loadSearch(table, txt.getText());
//
//			}
//		});
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

		// tabla con los clientes
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
		table.setBounds(0, 0, 450, 675);
		menuTableCliente.add(table);
		menuTableCliente.setViewportView(table);

		// Panel para los historiales
		JPanel panelHist = new RoundedPanel(null, 50, new Color(148, 220, 219));
		panelHist.setBounds(900, 200, 750, 735);
		panelHist.setOpaque(false);
		panelHist.setLayout(null);
		contentPane.add(panelHist);

		// Panel secundario para los historiales
		JPanel panelTitleHist = new JPanel();
		panelTitleHist.setBounds(10, 0, 730, 720);
		panelTitleHist.setBorder(new TitledBorder(lb, "  Historial  ", TitledBorder.LEFT, TitledBorder.TOP, font,
				new Color(51, 51, 51)));
		panelTitleHist.setOpaque(false);
		panelHist.add(panelTitleHist);
		panelTitleHist.setLayout(null);

		// ScrollPane para cargar la talbla con los historiales
		JScrollPane menuTableHist = new JScrollPane();
		menuTableHist.setBorder(BorderFactory.createEmptyBorder());
		menuTableHist.setBounds(5, 75, 720, 640);
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
		JButton BinformeTotal=new JButton("<html><u>Imprimir informe completo</u></html>");
		BinformeTotal.setBorder(null);
		BinformeTotal.setFocusPainted(false);
		BinformeTotal.setBorderPainted(false);
		BinformeTotal.setContentAreaFilled(false);
		BinformeTotal.setOpaque(false);
		BinformeTotal.setBackground(null);
		BinformeTotal.setBounds(1650,200,200,60);
		
		contentPane.add(BinformeTotal);
		BinformeTotal.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
//		JComboBox comboBox = new JComboBox();
//		comboBox.setBounds(269, 115, 96, 22);
//		comboBox.addItem("Pago Inmediato");
//		comboBox.addItem("Tres meses");
//		comboBox.addItem("Seis meses");
//		comboBox.addItem("Doce meses");
//		contentPane.add(comboBox);

//		JButton btnNewButton = new JButton("New button");
//		btnNewButton.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				if (tableHis.getSelectedRow() != -1
//						&& tableHis.getValueAt(tableHis.getSelectedRow(), 0).toString().equals("") == false) {
//					// System.out.println("asigugiasdgvhiasdgyugyuosdayguiodsaygdsagyiasdygiadsgyudsaasyvuhasdvyuh5sadyvhusavyusadvyuh");
//					java.sql.Date date = java.sql.Date
//							.valueOf(tableHis.getValueAt(tableHis.getSelectedRow(), 0).toString());
//					System.out.println(date);
//					Query<CitaHibernate> consultaCitaHibernate = session.createQuery(
//							"FROM CitaHibernate where clientes_dni_cliente=:dni and fecha=:fech", CitaHibernate.class);
//					consultaCitaHibernate.setParameter("dni", selected.split(" ")[0]);
//					consultaCitaHibernate.setParameter("fech", new java.util.Date(date.getTime()));
//					List<CitaHibernate> cita = consultaCitaHibernate.getResultList();
//					if (comboBox.getSelectedIndex() == 0) {
//
//						cita.get(0).setMensualidades(1);
//						session.beginTransaction();
//						session.update(cita.get(0));
//						session.getTransaction().commit();
//						System.out.println(cita.get(0).getIdcita());
//						System.out.println(cita.get(0).getFecha());
//						loadCita(tableHis);
//
//					} else if (comboBox.getSelectedIndex() == 1) {
//						cita.get(0).setMensualidades(3);
//						session.beginTransaction();
//						session.update(cita.get(0));
//						session.getTransaction().commit();
//						System.out.println(cita.get(0).getIdcita());
//						System.out.println(cita.get(0).getFecha());
//						loadCita(tableHis);
//					} else if (comboBox.getSelectedIndex() == 2) {
//						cita.get(0).setMensualidades(6);
//						session.beginTransaction();
//						session.update(cita.get(0));
//						session.getTransaction().commit();
//						System.out.println(cita.get(0).getIdcita());
//						System.out.println(cita.get(0).getFecha());
//						loadCita(tableHis);
//					} else if (comboBox.getSelectedIndex() == 3) {
//						cita.get(0).setMensualidades(12);
//						session.beginTransaction();
//						session.update(cita.get(0));
//						session.getTransaction().commit();
//						System.out.println(cita.get(0).getIdcita());
//						System.out.println(cita.get(0).getFecha());
//						loadCita(tableHis);
//					}
//				}
//			}
//		});
//		btnNewButton.setBounds(279, 148, 89, 23);
//		contentPane.add(btnNewButton);

		JButton btnInforme = new JButton("<html><u>Realizar pago</u></html>");
		btnInforme.setFont(font);
		btnInforme.setBackground(azulito);
		btnInforme.setBorder(null);
		btnInforme.setOpaque(true);
		btnInforme.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (tableHis.getSelectedRow() != -1 && tableHis.getValueAt(tableHis.getSelectedRow(), 0) != null) {
					// System.out.println("asigugiasdgvhiasdgyugyuosdayguiodsaygdsagyiasdygiadsgyudsaasyvuhasdvyuh5sadyvhusavyusadvyuh");
					java.sql.Date date = java.sql.Date
							.valueOf(tableHis.getValueAt(tableHis.getSelectedRow(), 0).toString());
					System.out.println(date);
					Query<CitaHibernate> consultaCitaHibernate = session.createQuery(
							"FROM CitaHibernate where clientes_dni_cliente=:dni and fecha=:fech", CitaHibernate.class);
					consultaCitaHibernate.setParameter("dni", selected.split(" ")[0]);
					consultaCitaHibernate.setParameter("fech", new java.util.Date(date.getTime()));
					List<CitaHibernate> cita = consultaCitaHibernate.getResultList();
					if (cita.get(0).getMensualidades() != 0) {
						Double pagado = cita.get(0).getPagado()
								+ Double.valueOf(cita.get(0).getTratamiento().getPrecio())
										/ Double.valueOf(cita.get(0).getMensualidades());
						cita.get(0).setPagado(pagado);
						session.beginTransaction();
						session.update(cita.get(0));
						session.getTransaction().commit();
						try {
							
							Map parametros = new HashMap();
							//la clave es el mismo parametro que he creado en el informe, el 1 es el id que quiero buscar y del que me haga el informe
							parametros.put("id_cita",cita.get(0).getIdcita() );
							
							// Cual es el tipo de informe que quiero realizar (lo visual)
							reporte = JasperCompileManager.compileReport("src/Pagos_cita.jrxml");
							// Parametros a meter: donde lo tengo el informe, de que parametros lo hago y la conexion
							JasperPrint jp = JasperFillManager.fillReport(reporte, parametros, cn.conectar());
							// Muestro el reporte
							JasperViewer.viewReport(jp,true);
							
							//Para guardar el informe (lo pudo exportar a muchos formatos pero vamos a hacerlo en PDF) / Poner la extension en el nombre sino no lo guarda en ese formato
							//JasperExportManager.exportReportToPdfFile(jp, "Informensito.pdf");
							//En caso de querer guardarlo en una ruta especifica
							String nombre=cita.get(0).getCliente().getApellidos();
							JasperExportManager.exportReportToPdfFile(jp, "C:\\Users\\Pablo\\Escritorio\\"+nombre+cita.get(0).getFecha()+".pdf");
							
							
							
						} catch (Exception e1) {
							e1.printStackTrace();
						}
						System.out.println(pagado + "asdyubasigvyudasbhasdyugvshbdasdyhvbusdaagyubh");
						loadCita(tableHis);
					}
				}else {
					
				}
				

			}
		});
		btnInforme.setBounds(600, 30, 120, 23);
		panelTitleHist.add(btnInforme);

		JButton btnMensualidades = new JButton("<html><u>Asignar tipo de mensualidad</u></html>");
		btnMensualidades.setFont(font);
		btnMensualidades.setBackground(azulito);
		btnMensualidades.setBorder(null);
		btnMensualidades.setOpaque(true);
		// btnMensualidades.setText("Asignar tipo de pago");
		btnMensualidades.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (tableHis.getValueAt(tableHis.getSelectedRow(), 0)!=null) {

					// Obtenemos la fecha de la tabla
					java.sql.Date date = java.sql.Date
							.valueOf(tableHis.getValueAt(tableHis.getSelectedRow(), 0).toString());

					Query<CitaHibernate> consultaCitaHibernate = session.createQuery(
							"FROM CitaHibernate where clientes_dni_cliente=:dni and fecha=:fech", CitaHibernate.class);
					consultaCitaHibernate.setParameter("dni", selected.split(" ")[0]);
					consultaCitaHibernate.setParameter("fech", new java.util.Date(date.getTime()));
					List<CitaHibernate> cita = consultaCitaHibernate.getResultList();

					if (cita.get(0).getPagado() == 0) {
						String[] mensualidades = { "Pago Inmediato", "Tres meses", "Seis meses", "Doce meses" };
						Icon icon = new javax.swing.ImageIcon(getClass().getResource("/Resources/images/money.png"));
						Object opcion = JOptionPane.showInputDialog(null, "Selecciona el tipo de mensualidad", "Elegir",
								JOptionPane.QUESTION_MESSAGE, icon, mensualidades, mensualidades[0]);
						if (opcion != null) {
							if (cita.get(0).getPagado() == 0) {
								if (opcion.toString().equals(mensualidades[0])) {
									cita.get(0).setMensualidades(1);
									session.beginTransaction();
									session.update(cita.get(0));
									session.getTransaction().commit();
								} else if (opcion.toString().equals(mensualidades[1])) {
									cita.get(0).setMensualidades(3);
									session.beginTransaction();
									session.update(cita.get(0));
									session.getTransaction().commit();
								} else if (opcion.toString().equals(mensualidades[2])) {
									cita.get(0).setMensualidades(6);
									session.beginTransaction();
									session.update(cita.get(0));
									session.getTransaction().commit();
								} else if (opcion.toString().equals(mensualidades[3])) {
									cita.get(0).setMensualidades(12);
									session.beginTransaction();
									session.update(cita.get(0));
									session.getTransaction().commit();
								}
							}
						}
						loadCita(tableHis);
					}
				}
			}
		});
		btnMensualidades.setBounds(10, 30, 200, 23);
		panelTitleHist.add(btnMensualidades);

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
					// System.out.println(selected);

				}
			}
		});

		tableHis.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evnt) {
				if (evnt.getClickCount() == 1) {

					// Seleccionar row
					tableHis.addColumnSelectionInterval(0, 3);

					// Cambios en la selección
					tableHis.setColumnSelectionAllowed(true);
					tableHis.setCellSelectionEnabled(true);
				}
			}
		});

	}

	public void loadCitaStart(JTable tabla) {
		// Relaiza la consulta
		this.session = instancia.openSession();
		DefaultTableModel model = new DefaultTableModel(new Object[][] {},
				new String[] { "Fecha", "Doctor", "Tratamiento", "Pago" }) {
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
				new String[] { "Fecha", "Mensualidad", "Tratamiento", "Pago" }) {
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
			// model.setValueAt(cita.getCliente().getNombre(), fila, 1);
			model.setValueAt(cita.getTratamiento().getNombre(), fila, 2);
			if (cita.getMensualidades() == 0) {
				model.setValueAt("No escogida", fila, 1);
			} else {
				model.setValueAt(cita.getMensualidades(), fila, 1);
			}
			if (cita.getPagado() == 0) {
				model.setValueAt("No pagado", fila, 3);
			} else if (cita.getPagado() > 0 && cita.getPagado() < cita.getTratamiento().getPrecio()) {
				model.setValueAt("En proceso", fila, 3);
			} else {
				model.setValueAt("Pagado", fila, 3);
			}
			fila++;
		}

		// Se alinea el texto de las columnas
		Renderer tcr = new Renderer();
		tcr.setHorizontalAlignment(SwingConstants.CENTER);
		tabla.getColumnModel().getColumn(0).setCellRenderer(tcr);
		tabla.setDefaultRenderer(Object.class, tcr);

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
