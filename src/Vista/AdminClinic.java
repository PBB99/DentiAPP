package Vista;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import Controlador.ConexionMySQL;
import Modelo.SpecialityHibernate;
import btndentiapp.ButtonDentiApp;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class AdminClinic extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private ConexionMySQL conex;
	private JFrame parent, frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AdminClinic frame = new AdminClinic(null, null);
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
	public AdminClinic(ConexionMySQL conex, JFrame parent) {
		this.conex = conex;

		// -------------------- Conexión ------------------
		SessionFactory instancia = (SessionFactory) new Configuration().configure("hibernate.cfg.xml")
				.addAnnotatedClass(SpecialityHibernate.class).buildSessionFactory();
		Session session = instancia.openSession();

		// -------------------- JFrame --------------------
		this.frame = this;
		this.parent = parent;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
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
		btnAppointment.setToolTipText("Módulo de citas");

		// Botón de usuarios
		ButtonDentiApp btnUsers = new ButtonDentiApp(0, 270, false,
				new ImageIcon(getClass().getResource("/Resources/images/usersGrey.png")));
		btnAppointment.setToolTipText("Módulo de Usuarios");

		// Botón de Pacientes
		ButtonDentiApp btnCustomers = new ButtonDentiApp(0, 405, false,
				new ImageIcon(getClass().getResource("/Resources/images/customersGrey.png")));
		btnCustomers.setToolTipText("Módulo de pacientes");

		// Botón de Inventario
		ButtonDentiApp btnStock = new ButtonDentiApp(0, 540, false,
				new ImageIcon(getClass().getResource("/Resources/images/stockGrey.png")));
		btnStock.setToolTipText("Módulo de materiales");

		// Botón de Tratamientos y Especialidades
		ButtonDentiApp btnClinic = new ButtonDentiApp(0, 675, true,
				new ImageIcon(getClass().getResource("/Resources/images/clinic.png")));
		btnClinic.setToolTipText("Módulo clínico");

		// Botón del Módulo economico
		ButtonDentiApp btnPayments = new ButtonDentiApp(0, 810, false,
				new ImageIcon(getClass().getResource("/Resources/images/paymentsGrey.png")));
		btnPayments.setToolTipText("Módulo Económico");

		// Panel de especialidades
		JPanel menuSpecialities = new JPanel();
		menuSpecialities.setBackground(new Color(148, 220, 219));
		menuSpecialities.setBorder(new LineBorder(new Color(0, 0, 0)));
		menuSpecialities.setBounds(418, 270, 500, 675);
		menuSpecialities.setLayout(null);

		// Tabla para mostrar los datos
		JTable specialityTable = new JTable();
		specialityTable.setBounds(0, 0, 500, 675);
		specialityTable.setVisible(true);

		// Panel de tratamientos
		JPanel menuTreatments = new JPanel();
		menuTreatments.setBackground(new Color(148, 220, 219));
		menuTreatments.setBorder(new LineBorder(new Color(0, 0, 0)));
		menuTreatments.setBounds(1137, 270, 500, 675);
		menuTreatments.setLayout(null);

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
				AdminAppointment admAppointment = new AdminAppointment(conex, frame);
				admAppointment.setVisible(true);
			}
		});

		// Acción del Módulo de usuarios
		btnUsers.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AdminUsers admUsers = new AdminUsers(conex, frame);
				admUsers.setVisible(true);
			}
		});

		// Acción del Módulo de pacientes
		btnCustomers.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AdminCustomers admCustomers = new AdminCustomers(conex, frame);
				admCustomers.setVisible(true);
			}
		});

		// Acción del Módulo de inventario
		btnStock.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AdminStock admStock = new AdminStock(conex, frame);
				admStock.setVisible(true);
			}
		});

		// Acción del Módulo económico
		btnPayments.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AdminPayments admPayments = new AdminPayments(conex, frame);
				admPayments.setVisible(true);
			}
		});
		
		//Mostrar cosas en la tabla
		//Consulta 1
        String hql = "FROM SpecialityHibernate"; 
        Query<SpecialityHibernate> consulta = session.createQuery(hql,SpecialityHibernate.class); 
        List<SpecialityHibernate> results = consulta.getResultList();
        DefaultTableModel model = (DefaultTableModel) specialityTable.getModel();
		model.setColumnIdentifiers(new String[] { "Id Especialidad", "Especialidad"});
		model.setRowCount(results.size()+1);
		int fila = 0, columna=0;
		model.setValueAt("Id Especialidad", fila, columna);
		model.setValueAt("Especialidad", fila, columna+1);
		fila++;
        for(SpecialityHibernate especialidad : results){
        	model.setValueAt(especialidad.getId_especialidad(), fila, columna);
            model.setValueAt(especialidad.getEspecialidad(), fila, columna+1);
            fila++;
            columna=0;
        }

		// -------------------- Adiciones a los paneles --------------------
		contentPane.add(menuPane);
		contentPane.add(menuSpecialities);
		contentPane.add(menuTreatments);
		menuPane.add(lblLogo);
		menuPane.add(btnAppointment);
		menuPane.add(btnUsers);
		menuPane.add(btnCustomers);
		menuPane.add(btnStock);
		menuPane.add(btnClinic);
		menuPane.add(btnPayments);
		menuSpecialities.add(specialityTable);
	}

}
