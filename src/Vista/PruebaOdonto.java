package Vista;

import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
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

import Modelo.CitaHibernate;
import Modelo.ClienteHibernate;
import Modelo.SpecialityHibernate;
import Modelo.TreatmentsHibernate;
import Modelo.UserHibernate;
import Vista.AdminClinic.Renderer;

import javax.swing.JComboBox;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class PruebaOdonto extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private SessionFactory instancia;
	private Session session;
	private int lastCLiente;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PruebaOdonto frame = new PruebaOdonto();
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
	public PruebaOdonto() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 799, 544);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		this.instancia = (SessionFactory) new Configuration().configure("hibernate.cfg.xml")
				.addAnnotatedClass(UserHibernate.class).addAnnotatedClass(CitaHibernate.class)
				.addAnnotatedClass(TreatmentsHibernate.class).addAnnotatedClass(ClienteHibernate.class)
				.buildSessionFactory();
		this.session = instancia.openSession();
		setContentPane(contentPane);
		contentPane.setLayout(null);

		table = new JTable();

		Query<ClienteHibernate> consultaClientes = session.createQuery("FROM ClienteHibernate", ClienteHibernate.class);
		List<ClienteHibernate> allClientes = consultaClientes.getResultList();

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

		loadSpecialities(table);
		table.setBounds(0, 29, 500, 675);
		contentPane.add(table);

		JButton btnInsert = new JButton();
		btnInsert.setBorderPainted(false);
		btnInsert.setBackground(new Color(148, 220, 219));
		btnInsert.setBounds(0, 0, 40, 30);
		btnInsert.setIcon(new ImageIcon(getClass().getResource("/Resources/images/add.png")));
		btnInsert.addActionListener(new ActionListener() {
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
							loadSpecialities(table);
						}

						@Override
						public void windowActivated(WindowEvent e) {
							// TODO Auto-generated method stub

						}
					});
				}
			}
		});
		contentPane.add(btnInsert);

		JTextField txt = new JTextField();
		txt.setBounds(40, 0, 100, 30);
		txt.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				loadSearch(table, txt.getText());

			}
		});
		contentPane.add(txt);

	}

	public void loadSpecialities(JTable tabla) {
		// Relaiza la consulta
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
			model.setRowCount(results.size());
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

		// Guarda el último id de las especialidades
		if (!results.isEmpty()) {
			lastCLiente = results.size();
			System.out.println("kkkkk");
		} else {
			lastCLiente = 0;
		}

	}

	public void loadSearch(JTable tabla, String busq) {
		// Relaiza la consulta
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

		// Guarda el último id de las especialidades
		if (!results.isEmpty()) {
			lastCLiente = results.size();
			System.out.println("kkkkk");
		} else {
			lastCLiente = 0;
		}

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
