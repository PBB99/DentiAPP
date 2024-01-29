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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
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
import Modelo.CitaHibernate;
import Modelo.ClienteHibernate;
import Modelo.OdontogramaHibernate;
import Modelo.SpecialityHibernate;
import Modelo.TreatmentsHibernate;
import Modelo.UserHibernate;
import Vista.AdminCustomers.Renderer;
import btndentiapp.ButtonDentiApp;
import javax.swing.JComboBox;
import javax.swing.JTextPane;

public class DoctorCustomers extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private ConexionMySQL conex;
	private JFrame parent, frame;
	private UserHibernate userHi;
	private JTable table;
	private JTable tableHis;
	private String selected;
	private SessionFactory instancia;
	private Session session;
	private JButton btnOdonto;
	private CitaHibernate citaHoy;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
//				try {
//					DoctorCustomers frame = new DoctorCustomers(null, null);
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
	public DoctorCustomers(UserHibernate mainUser, JFrame parent) {
		this.userHi = mainUser;
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

		// -------------------- Componentes --------------------
		JLabel lblNAdmin = new JLabel(mainUser.getNombre()+" "+mainUser.getNombre());
		lblNAdmin.setBounds(250, 10, 800, 135);
		lblNAdmin.setFont(new Font("Tahoma", Font.PLAIN, 60));
contentPane.add(lblNAdmin);
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
		ButtonDentiApp btnCustomers = new ButtonDentiApp(0, 270, true,
				new ImageIcon(getClass().getResource("/Resources/images/customers.png")));
		btnCustomers.setToolTipText("Módulo de pacientes (Alt+P)");

		// Botón de Inventario
		ButtonDentiApp btnStock = new ButtonDentiApp(0, 405, false,
				new ImageIcon(getClass().getResource("/Resources/images/stockGrey.png")));
		btnStock.setToolTipText("Módulo de materiales (Alt+I)");
		btnStock.setMnemonic(KeyEvent.VK_I);
		
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
				mnNewMenu.setBackground(new Color(0,0,0,0));
				
				

				// nombre del doctor o admin
				JMenuItem ItemName = new JMenuItem(userHi.getNombre());
				//ItemName.setText(userHi.getNombre());

				// item cambio contraseña
				JMenuItem ItemPass = new JMenuItem("Cambiar Contraseña");
				ItemPass.setIcon(new ImageIcon(getClass().getResource("/Resources/images/keypass.png")));

				// item cerrar sesion
				JMenuItem ItemOut = new JMenuItem("Cerrar Sesión");
				ItemOut.setIcon(new ImageIcon(getClass().getResource("/Resources/images/logout.png")));


		// -------------------- Lógica --------------------
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
		//logica de sombreado
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
						mnNewMenu.setBackground(new Color(0,0,0,0));
						
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

		// Acción de ir a Módulo Stock
		btnStock.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DoctorStock docStock = new DoctorStock(mainUser, frame);
				docStock.setVisible(true);
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
		table.setBounds(220, 180, 500, 735);

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
		tableHis.setBounds(820, 180, 500, 735);
		contentPane.add(tableHis);

		loadClientes(table);
		loadCitaStart(tableHis);
		contentPane.add(table);

		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBounds(1350, 350, 443, 225);
		contentPane.add(panel);
		
		JLabel lblNewLabel = new JLabel("Parece que has tenido una consulta con (nombre del cliente)");
		lblNewLabel.setBounds(26, 5, 355, 14);
		panel.add(lblNewLabel);

		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evnt) {
				if (evnt.getClickCount() == 1) {

					// Seleccionar row
					table.addColumnSelectionInterval(0, 3);

					// Cambios en la selección
					table.setColumnSelectionAllowed(true);
					table.setCellSelectionEnabled(true);
					if (table.getSelectedRow() != 0) {
						// selección del tratamiento
						selected = table.getValueAt(table.getSelectedRow(), 0).toString() + " "
								+ table.getValueAt(table.getSelectedRow(), 1).toString() + " "
								+ table.getValueAt(table.getSelectedRow(), 2).toString() + " "
								+ table.getValueAt(table.getSelectedRow(), 3).toString() + " ";
						loadCita(tableHis);
						System.out.println(selected);
					} else {
						selected = null;
					}
					LocalDate localDate = LocalDate.now();
					java.sql.Date dia = Date.valueOf(
							LocalDate.of(localDate.getYear(), localDate.getMonth(), localDate.getDayOfMonth()));

					session = instancia.openSession();
					String hql = "FROM CitaHibernate where clientes_dni_cliente=:dni and fecha=:fech and usuarios_dni_usuario=:us";
					Query<CitaHibernate> consulta = session.createQuery(hql, CitaHibernate.class);
					consulta.setParameter("dni", selected.split(" ")[0]);
					consulta.setParameter("fech", dia);
					consulta.setParameter("us", mainUser.getDni());

					// Guarda los datos en una lista
					List<CitaHibernate> results = consulta.getResultList();
					System.out.println(results.size());

					if (results.isEmpty() == false) {
						panel.setVisible(true);
						lblNewLabel.setText("Parece que has tenido una consulta con " + results.get(0).getCliente().getNombre() + " " + results.get(0).getCliente().getApellidos());
						//Parece que has tenido una consulta con (nombre del cliente)
						citaHoy = results.get(0);
					} else {
						panel.setVisible(false);
					}

				}
			}
		});

		btnOdonto = new JButton("Odontograma");
		btnOdonto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (selected != null) {
					Query<ClienteHibernate> consultaClienteExiste = session
							.createQuery("FROM ClienteHibernate where dni_cliente=:dni ", ClienteHibernate.class);
					consultaClienteExiste.setParameter("dni", selected.split(" ")[0]);
					List<ClienteHibernate> cliente = consultaClienteExiste.getResultList();
					CustommerOdont cust = new CustommerOdont(cliente.get(0),session,false);
					cust.setModal(true);
					cust.setVisible(true);
				}
			}
		});
		btnOdonto.setBackground(new Color(148, 220, 219));
		btnOdonto.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnOdonto.setBounds(1320, 815, 100, 100);
		btnOdonto.setBorder(null);
		contentPane.add(btnOdonto);

		JTextField txt = new JTextField();
		txt.setBounds(220, 150, 100, 30);
		txt.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				loadSearch(table, txt.getText());

			}
		});
		contentPane.add(txt);

		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(138, 30, 162, 22);
		panel.add(comboBox);

		Query<UserHibernate> consultaUsuarios = session.createQuery("FROM UserHibernate where dni=:dni",
				UserHibernate.class);
		consultaUsuarios.setParameter("dni", mainUser.getDni());
		UserHibernate usuario = consultaUsuarios.getSingleResult();
		SpecialityHibernate especialidad = usuario.getEspecialidad();

		List<TreatmentsHibernate> allTratamientos = especialidad.getTratamientos();
		for (int j = 0; j < allTratamientos.size(); j++) {
			comboBox.addItem(allTratamientos.get(j));
		}

		panel.setVisible(false);

		JTextPane textPane = new JTextPane();
		textPane.setBounds(32, 90, 377, 88);
		panel.add(textPane);

		JLabel lblNewLabel_1 = new JLabel("Tratamiento:");
		lblNewLabel_1.setBounds(32, 34, 83, 14);
		panel.add(lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel("Observaciones:");
		lblNewLabel_2.setBounds(38, 65, 126, 14);
		panel.add(lblNewLabel_2);

		JButton btnNewButton = new JButton("Actualizar");
		btnNewButton.setBounds(310, 189, 95, 23);
		panel.add(btnNewButton);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LocalDate localDate = LocalDate.now();
				java.sql.Date dia = Date.valueOf(
						LocalDate.of(localDate.getYear(), localDate.getMonth(), localDate.getDayOfMonth()));
				
				session = instancia.openSession();
				session.beginTransaction();
				String hql = "FROM CitaHibernate where clientes_dni_cliente=:dni and fecha=:fech and usuarios_dni_usuario=:us";
				Query<CitaHibernate> consulta = session.createQuery(hql, CitaHibernate.class);
				consulta.setParameter("dni", selected.split(" ")[0]);
				consulta.setParameter("fech", dia);
				consulta.setParameter("us", mainUser.getDni());

				// Guarda los datos en una lista
				List<CitaHibernate> results = consulta.getResultList();
				results.get(0).setTratamiento((TreatmentsHibernate) comboBox.getSelectedItem());
				results.get(0).setObservaciones(textPane.getText());
				System.out.println(results.size());
				session.update(results.get(0));
				session.getTransaction().commit();
			}
		});

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
		model.setRowCount(1);
		JTableHeader header = tabla.getTableHeader();
		if (results.size() < 19) {
			model.setRowCount(18);
		} else {
			System.out.println("oooooooooooooooo");
			model.setRowCount(results.size() + 1);
		}
		int fila = 1, columna = 0;

		model.setValueAt("DNI", 0, 0);
		model.setValueAt("Nombre", 0, 1);
		model.setValueAt("Apellido", 0, 2);
		model.setValueAt("Años", 0, 3);

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
		model.setRowCount(1);
		JTableHeader header = tabla.getTableHeader();
		model.setRowCount(34);
		int fila = 1, columna = 0;

		model.setValueAt("Fecha", 0, 0);
		model.setValueAt("Doctor", 0, 1);
		model.setValueAt("Tratamiento", 0, 2);

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
		String hql = "FROM CitaHibernate where clientes_dni_cliente=:dni and usuarios_dni_usuario=:us";
		Query<CitaHibernate> consulta = session.createQuery(hql, CitaHibernate.class);
		consulta.setParameter("dni", selected.split(" ")[0]);
		consulta.setParameter("us", userHi.getDni());

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
		model.setRowCount(1);
		JTableHeader header = tabla.getTableHeader();
		if (results.size() < 35) {
			model.setRowCount(34);
		} else {
			System.out.println("oooooooooooooooo");
			model.setRowCount(results.size() + 1);
		}
		int fila = 1, columna = 0;

		model.setValueAt("Fecha", 0, 0);
		model.setValueAt("Doctor", 0, 1);
		model.setValueAt("Tratamiento", 0, 2);

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
			model.setRowCount(results.size() + 1);
		}
		int fila = 1, columna = 0;

		model.setValueAt("DNI", 0, 0);
		model.setValueAt("Nombre", 0, 1);
		model.setValueAt("Apellido", 0, 2);
		model.setValueAt("Años", 0, 3);

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

			if (row == 0) {
				setBackground(new Color(148, 220, 219));
			} else if (row % 2 == 0) {
				setBackground(new Color(220, 220, 220));
			} else {
				setBackground(new Color(250, 250, 250));
			}

			return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		}

	}

}
