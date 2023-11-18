package Vista;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.GregorianCalendar;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import org.hibernate.cfg.Configuration;

import com.toedter.calendar.JCalendar;
import com.toedter.calendar.JDateChooser;

import Controlador.CitasController;
import Controlador.ConexionMySQL;
import Modelo.Cita;
import Modelo.CitaHibernate;
import Modelo.ClienteHibernate;
import Modelo.TreatmentsHibernate;
import Modelo.User;
import Modelo.UserHibernate;

import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ListSelectionModel;

import org.hibernate.*;

public class PruebasCalendario extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
	private ConexionMySQL conexion;

	private SessionFactory instancia;
	private Session session;
	private JTable table_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PruebasCalendario frame = new PruebasCalendario();
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
	public PruebasCalendario() {

		conexion = new ConexionMySQL();
		// conexion.conectar();

		this.instancia = (SessionFactory) new Configuration().configure("hibernate.cfg.xml")
				.addAnnotatedClass(UserHibernate.class).addAnnotatedClass(CitaHibernate.class)
				.addAnnotatedClass(TreatmentsHibernate.class).addAnnotatedClass(ClienteHibernate.class)
				.buildSessionFactory();
		this.session = instancia.openSession();

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 638, 447);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JCalendar calendar = new JCalendar();
		calendar.setBounds(23, 77, 184, 153);
		contentPane.add(calendar);

		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(74, 11, 96, 22);
		contentPane.add(comboBox);
		Query<UserHibernate> consultaUsers = session.createQuery("FROM UserHibernate", UserHibernate.class);
		List<UserHibernate> allUsers = consultaUsers.getResultList();

		for (int i = 0; i < allUsers.size(); i++) {
			comboBox.addItem(allUsers.get(i).getDni());
		}

		DefaultTableModel modelo = new DefaultTableModel();
		table = new JTable();
		table.setBounds(296, 70, 199, 196);
		contentPane.add(table);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setModel(modelo);
		modelo.addColumn("Hora");
		modelo.addColumn("Cita");
		modelo.insertRow(0, new Object[] { "Hora", "Cita" });
		Calendar fechaCalen = new GregorianCalendar();
		DateFormat formateador = new SimpleDateFormat("yyyy-M-dd");
		java.util.Date fech = fechaCalen.getTime();
		Query<CitaHibernate> consultaCitas = session.createQuery("FROM CitaHibernate where fecha=:fech and dni_doc=:id",
				CitaHibernate.class);
		consultaCitas.setParameter("fech", fech);
		consultaCitas.setParameter("id", comboBox.getSelectedItem());
		System.out.println(comboBox.getSelectedItem());
		List<CitaHibernate> allCitas = consultaCitas.getResultList();
		System.out.println(formateador.format(fechaCalen.getTime()));
		for (int i = 9; i < 15; i++) {
			modelo.insertRow(modelo.getRowCount(), new Object[] { i + ":00", "" });
		}
		for (int i = 17; i < 20; i++) {
			modelo.insertRow(modelo.getRowCount(), new Object[] { i + ":00", "" });
		}


		for (int i = 0; i < allCitas.size(); i++) {
			System.out.println("saddasdas");
			java.util.Date dia = allCitas.get(i).getFecha();
			System.out.println(formateador.format(dia) + "------");
			if (formateador.format(fechaCalen.getTime()).equals(formateador.format(dia))) {
				System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
				for (int j = 0; j < table.getModel().getRowCount(); j++) {
					if (table.getModel().getValueAt(j, 0).equals(allCitas.get(i).getHora())) {
						table.getModel().setValueAt(allCitas.get(i).getCliente().getNombre(), j, 1);
					}
				}
			}
		}

		// JTable table = new JTable();

		calendar.addPropertyChangeListener("calendar", new PropertyChangeListener() {

			@Override
			public void propertyChange(PropertyChangeEvent evt) {

				// TODO Auto-generated method stub
				for (int j = 1; j < table.getModel().getRowCount(); j++) {
					table.getModel().setValueAt("", j, 1);
				}

				Calendar fechaCal = (Calendar) evt.getNewValue();
				Query<CitaHibernate> consulta = session.createQuery("FROM CitaHibernate where fecha=:fech",
						CitaHibernate.class);
				consulta.setParameter("fech", fechaCal.getTime());
				List<CitaHibernate> allCitas = consulta.getResultList();
				for (int i = 0; i < allCitas.size(); i++) {
					Date dia = allCitas.get(i).getFecha();

					System.out.println(formateador.format(fechaCal.getTime()) + "------");
					if (formateador.format(fechaCal.getTime()).equals(formateador.format(dia))) {
						System.out.println("saddasdas");
						for (int j = 0; j < table.getModel().getRowCount(); j++) {
							if (table.getModel().getValueAt(j, 0).equals(allCitas.get(i).getHora())) {
								table.getModel().setValueAt(allCitas.get(i).getCliente().getNombre(), j, 1);
							}
						}
					}
				}
			}
		});

		comboBox.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				// TODO Auto-generated method stub
				for (int j = 1; j < table.getModel().getRowCount(); j++) {
					table.getModel().setValueAt("", j, 1);
				}

				Query<CitaHibernate> consultaCitas = session.createQuery("FROM CitaHibernate where fecha=:fech and dni_doc=:id",
						CitaHibernate.class);
				consultaCitas.setParameter("fech", calendar.getCalendar().getTime());
				consultaCitas.setParameter("id", comboBox.getSelectedItem());
				List<CitaHibernate> allCitas = consultaCitas.getResultList();
				for (int i = 0; i < allCitas.size(); i++) {
					Date dia = allCitas.get(i).getFecha();

					System.out.println(formateador.format(calendar.getCalendar().getTime()) + "------");
					if (formateador.format(calendar.getCalendar().getTime()).equals(formateador.format(dia))) {
						System.out.println("saddasdas");
						for (int j = 0; j < table.getModel().getRowCount(); j++) {
							if (table.getModel().getValueAt(j, 0).equals(allCitas.get(i).getHora())) {
								table.getModel().setValueAt(allCitas.get(i).getCliente().getNombre(), j, 1);
							}
						}
					}
				}

			}
		});
		
		table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
		    @Override
		    public void valueChanged(ListSelectionEvent event) {
		    	if (!event.getValueIsAdjusting()) {//This line prevents double events
		    		System.out.println("--");
		    		AdminInsertUser us = new AdminInsertUser(null, rootPaneCheckingEnabled);
		    		us.setVisible(true);
		        }
		      
//		        if (table.getSelectedRow() > -1) {
//		            // print first column value from selected row
//		            //System.out.println(table.getValueAt(table.getSelectedRow(), 0).toString());
//		            System.out.println("---");
//
//		        }
		    }

		});
		
	}
}
