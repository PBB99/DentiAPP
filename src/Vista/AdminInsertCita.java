package Vista;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Component;

import javax.swing.border.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import Controlador.ConexionMySQL;
import Modelo.Cita;
import Modelo.CitaHibernate;
import Modelo.ClienteHibernate;
import Modelo.ProveedorHibernate;
import Modelo.Specialist;
import Modelo.Speciality;
import Modelo.SpecialityHibernate;
import Modelo.TreatmentsHibernate;
import Modelo.UserHibernate;
import Otros.RoundedPanel;
import Vista.AdminStock.Renderer;

import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JInternalFrame;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.util.Date;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.JComboBox;
import com.toedter.components.JLocaleChooser;

public class AdminInsertCita extends JDialog {
	// -------------------------------VARIABLES------------------------

	private JPanel contentPane;
	private Connection cn;
	private ConexionMySQL conex;
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();

	private SessionFactory instancia;
	private Session session;
	private Font font = new Font("Dialog", Font.BOLD, 15);
	private LineBorder lb = new LineBorder(new Color(240, 240, 240), 3, true);

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		try {
//			AdminInsertUser dialog = new AdminInsertUser();
//			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
//			dialog.setVisible(true);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

	/**
	 * Create the dialog.
	 */
	public AdminInsertCita(String dniDoctor, Date fecha, String hora) {
		this.instancia = (SessionFactory) new Configuration().configure("hibernate.cfg.xml")
				.addAnnotatedClass(UserHibernate.class).addAnnotatedClass(CitaHibernate.class)
				.addAnnotatedClass(TreatmentsHibernate.class).addAnnotatedClass(ClienteHibernate.class)
				.addAnnotatedClass(SpecialityHibernate.class).buildSessionFactory();
		this.session = instancia.openSession();

		setLocationRelativeTo(null);
		// -----------------------COMPONENTES-------------------
		setBounds(100, 100, 450, 550);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPanel.setLayout(null);

		JLabel lblPac = new JLabel("Paciente");
		lblPac.setBounds(75, 55, 84, 14);

		JLabel lblTrata = new JLabel("Tratamiento");
		lblTrata.setBounds(75, 310, 75, 14);

		JButton okButton = new JButton("OK");
		okButton.setActionCommand("OK");
		getRootPane().setDefaultButton(okButton);

		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setModal(false);
				setVisible(false);
				AdminInsertCita.this.dispatchEvent(new WindowEvent(AdminInsertCita.this, WindowEvent.WINDOW_CLOSING));
			}
		});
		cancelButton.setActionCommand("Cancel");

//		JComboBox cbPaciente = new JComboBox();
//		cbPaciente.setBounds(199, 91, 129, 20);
//		Query<ClienteHibernate> consultaClientes = session.createQuery("FROM ClienteHibernate", ClienteHibernate.class);
//		List<ClienteHibernate> allClientes = consultaClientes.getResultList();
//
//		for (int i = 0; i < allClientes.size(); i++) {
//			cbPaciente.addItem(allClientes.get(i));
//		}

		JComboBox cbTratamiento = new JComboBox();
		cbTratamiento.setBounds(150, 27, 105, 22);

		Query<UserHibernate> consultaUsuarios = session.createQuery("FROM UserHibernate where dni=:dni",
				UserHibernate.class);
		consultaUsuarios.setParameter("dni", dniDoctor);
		UserHibernate usuario = consultaUsuarios.getSingleResult();
		SpecialityHibernate especialidad = usuario.getEspecialidad();

		List<TreatmentsHibernate> allTratamientos = especialidad.getTratamientos();
		for (int j = 0; j < allTratamientos.size(); j++) {
			cbTratamiento.addItem(allTratamientos.get(j));
		}

		DefaultTableModel modelo = new DefaultTableModel();
		JTable table = new JTable();
		table.setShowVerticalLines(false);
		table.setShowHorizontalLines(false);
		table.setCellSelectionEnabled(true);
		table.setBounds(0, 0, 100, 100);
		table.setBackground(new Color(250, 250, 250));
		table.setSelectionBackground(new Color(148, 220, 219));
		table.setShowGrid(false);
		table.setBorder(null);
		table.setFont(new Font("Tahoma", Font.PLAIN, 12));
		table.setRowHeight(35);
		table.getTableHeader().setFont(new Font("Tahoma", Font.PLAIN, 18));
		table.getTableHeader().setBackground(new Color(148, 220, 219));
		table.getTableHeader().setBorder(new LineBorder(new Color(148, 220, 219)));
		table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		table.setColumnSelectionAllowed(false);
	    table.setRowSelectionAllowed(true);
		
		loadTableClientes(table);
		
		JPanel panelTable = new RoundedPanel(null, 50, new Color(148, 220, 219));
		panelTable.setBounds(10, 11, 414, 350);
		panelTable.setOpaque(false);
		panelTable.setLayout(null);
		contentPanel.add(panelTable);

		// Panel secundario para el calendario
		JPanel panelTitleTable = new JPanel();
		panelTitleTable.setBounds(15, 15, 389, 315);
		panelTitleTable.setBorder(new TitledBorder(lb, "  Clientes  ", TitledBorder.LEFT, TitledBorder.TOP, font, new Color(51, 51, 51)));
		panelTitleTable.setOpaque(false);
		panelTable.add(panelTitleTable);
		
		JPanel panelTratamiento = new RoundedPanel(null, 50, new Color(148, 220, 219));
		panelTratamiento.setBounds(10, 372, 414, 96);
		panelTratamiento.setOpaque(false);
		panelTratamiento.setLayout(null);
		contentPanel.add(panelTratamiento);

		// Panel secundario para el calendario
		JPanel panelTitleTratamiento = new JPanel();
		panelTitleTratamiento.setBounds(15, 15, 389, 70);
		panelTitleTratamiento.setBorder(new TitledBorder(lb, "  Clientes  ", TitledBorder.LEFT, TitledBorder.TOP, font, new Color(51, 51, 51)));
		panelTitleTratamiento.setOpaque(false);
		panelTratamiento.add(panelTitleTratamiento);

		// ------------------------------------------LOGICA-----------------------------------------------

		// Comprobamos si el doctor tiene una cita en la fecha que se especifica
		Query<CitaHibernate> consultaCitaExiste = session.createQuery(
				"FROM CitaHibernate where fecha=:fech and hora=:hora and usuarios_dni_usuario=:id",
				CitaHibernate.class);

		java.sql.Date dia = new java.sql.Date(fecha.getTime());
		consultaCitaExiste.setParameter("fech", dia);
		consultaCitaExiste.setParameter("hora", hora);
		consultaCitaExiste.setParameter("id", dniDoctor);
		List<CitaHibernate> check = consultaCitaExiste.getResultList();

		Query<ClienteHibernate> consultaClientes = session.createQuery("FROM ClienteHibernate", ClienteHibernate.class);
		List<ClienteHibernate> allClientes = consultaClientes.getResultList();

		if (check.isEmpty() == false) {
			for (int j = 0; j < allTratamientos.size(); j++) {
				if (allTratamientos.get(j).getCodigo_tratamiento() == check.get(0).getTratamiento()
						.getCodigo_tratamiento()) {
					cbTratamiento.setSelectedIndex(j);
				}
			}
			for (int j = 0; j < allClientes.size(); j++) {
				if (allClientes.get(j).getDni_cliente().equals(check.get(0).getCliente().getDni_cliente())) {
					table.setRowSelectionInterval(j	, j);
					System.out.println("dasguhuiodasguidasguguasdugidsagigiasdggaisduksdabasdvuyhijdsayuvfsadhjdyasivhkjsdyuvhasdjasdyusjhadusyadvj5h");
				}
			}
		}

		JScrollPane menuClientes = new JScrollPane();
		menuClientes.setBorder(BorderFactory.createEmptyBorder());
		menuClientes.setBounds(10, 21, 369, 283);
		menuClientes.setBackground(new Color(148, 220, 219));

		getContentPane().add(contentPanel, BorderLayout.CENTER);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{

				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						java.sql.Date dia = new java.sql.Date(fecha.getTime());
						Query<CitaHibernate> consultaCitaExiste = session.createQuery(
								"FROM CitaHibernate where fecha=:fech and hora=:hora and usuarios_dni_usuario=:id",
								CitaHibernate.class);
						consultaCitaExiste.setParameter("fech", dia);
						consultaCitaExiste.setParameter("hora", hora);
						consultaCitaExiste.setParameter("id", dniDoctor);
						List<CitaHibernate> check = consultaCitaExiste.getResultList();

						if (table.getSelectedRow() != -1) {
							
							
							Query<ClienteHibernate> consultaClientes = session.createQuery("FROM ClienteHibernate where dni_cliente=:cli", ClienteHibernate.class);
							consultaClientes.setParameter("cli", table.getValueAt(table.getSelectedRow(), 0).toString());
							List<ClienteHibernate> cliente = consultaClientes.getResultList();
							
							if (check.isEmpty()) {
								Query<CitaHibernate> consultaCitas = session.createQuery("FROM CitaHibernate",
										CitaHibernate.class);
								List<CitaHibernate> allCitas = consultaCitas.getResultList();
								CitaHibernate cita = new CitaHibernate(
										allCitas.get(allCitas.size() - 1).getIdcita() + 1, dia, hora, "");
								// cita.setCliente(allClientes.get(cbPaciente.getSelectedIndex()));
								cita.setCliente(cliente.get(0));
								cita.setUser(usuario);
								cita.setTratamiento((TreatmentsHibernate) cbTratamiento.getSelectedItem());
								session.beginTransaction();
								session.save(cita);
								session.getTransaction().commit();
							} else {
								CitaHibernate citaEx = consultaCitaExiste.getSingleResult();
								// citaEx.setCliente(allClientes.get(cbPaciente.getSelectedIndex()));
								citaEx.setCliente(cliente.get(0));
								citaEx.setUser(usuario);
								citaEx.setTratamiento((TreatmentsHibernate) cbTratamiento.getSelectedItem());
								session.beginTransaction();
								session.update(citaEx);
								session.getTransaction().commit();
							}
						}
						setModal(false);
						dispose();
						setVisible(false);
						AdminInsertCita.this
								.dispatchEvent(new WindowEvent(AdminInsertCita.this, WindowEvent.WINDOW_CLOSING));
					}
				});

			}
			{// -------------------ADICIONES DE LOS COMPONENTES---------------
				buttonPane.add(okButton);
				buttonPane.add(cancelButton);
				contentPanel.add(lblPac);
				contentPanel.add(lblTrata);
			}
		}
		panelTitleTable.setLayout(null);
		//contentPanel.add(table);
		//contentPanel.add(menuClientes);
		menuClientes.add(table);
		menuClientes.setViewportView(table);
		panelTitleTable.add(menuClientes);
		panelTitleTratamiento.setLayout(null);
		panelTitleTratamiento.add(cbTratamiento);

	}

	public void loadTableClientes(JTable table) {
		// Relaiza la consulta
		Query<ClienteHibernate> consultaClientes = session.createQuery("FROM ClienteHibernate", ClienteHibernate.class);

		// Guarda los datos en una lista
		List<ClienteHibernate> allClientes = consultaClientes.getResultList();

		// Prepara la tabla
		DefaultTableModel model = new DefaultTableModel(new Object[][] {},
				new String[] { "DNI", "Nombre", "Apellido" }) {
			@Override
			public boolean isCellEditable(int row, int column) {
				// all cells false
				return false;
			}
		};
		table.setModel(model);
		JTableHeader header = table.getTableHeader();
		if (allClientes.size() < 19) {
			model.setRowCount(18);
		} else {
			model.setRowCount(allClientes.size());
		}
		int fila = 0, columna = 0;

		// Carga los datos
		for (ClienteHibernate cliente : allClientes) {
			model.setValueAt(cliente.getDni_cliente(), fila, columna);
			model.setValueAt(cliente.getNombre(), fila, columna + 1);
			model.setValueAt(cliente.getApellidos(), fila, columna + 2);
			fila++;
		}

		// Se alinea el texto de las columnas
		Renderer tcr = new Renderer();
		tcr.setHorizontalAlignment(SwingConstants.CENTER);
		table.getColumnModel().getColumn(0).setCellRenderer(tcr);
		table.setDefaultRenderer(Object.class, tcr);

//		int[] anchos = { 125, 250, 75, 350 };
//		for (int i = 0; i < 3; i++) {
//			table.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
//		}

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
