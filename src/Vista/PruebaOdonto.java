package Vista;

import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
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

		
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(56, 17, 30, 22);
		contentPane.add(comboBox);
		
		Query<ClienteHibernate> consultaClientes = session.createQuery("FROM ClienteHibernate", ClienteHibernate.class);
		List<ClienteHibernate> allClientes = consultaClientes.getResultList();
		
		for (int i = 0; i < allClientes.size(); i++) {
			comboBox.addItem(allClientes.get(i));
		}
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
		table.setBounds(376, 102, 149, 100);
		contentPane.add(table);
		
	}
	
	public void loadSpecialities(JTable tableSpeciality) {
		// Relaiza la consulta
		String hql = "FROM ClienteHibernate";
		Query<ClienteHibernate> consulta = session.createQuery(hql, ClienteHibernate.class);

		// Guarda los datos en una lista
		List<ClienteHibernate> results = consulta.getResultList();

		// Prepara la tabla
		DefaultTableModel model = new DefaultTableModel(new Object[][] {}, new String[] { "Nombre" , "Apellido" }) {
			@Override
			public boolean isCellEditable(int row, int column) {
				// all cells false
				return false;
			}
		};
		tableSpeciality.setModel(model);
		JTableHeader header = tableSpeciality.getTableHeader();
		if(results.size()<19) {
			model.setRowCount(18);
		}else {
			model.setRowCount(results.size());
		}
		int fila = 0, columna = 0;

		// Carga los datos
		for (ClienteHibernate especialidad : results) {
			System.out.println("aa");
			model.setValueAt(especialidad.getNombre(), fila, columna);
			model.setValueAt(especialidad.getApellidos(), fila, columna+1);
			fila++;
		}

		// Se alinea el texto de las columnas
		Renderer tcr = new Renderer();
		tcr.setHorizontalAlignment(SwingConstants.CENTER);
		tableSpeciality.getColumnModel().getColumn(0).setCellRenderer(tcr);
		tableSpeciality.setDefaultRenderer(Object.class, tcr);

		// Guarda el último id de las especialidades
		if (!results.isEmpty()) {
			lastCLiente = results.size()+1;
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
			if (row % 2 == 0) {
				setBackground(new Color(220, 220, 220));
			} else {
				setBackground(new Color(250, 250, 250));
			}

			return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		}

	}

}
