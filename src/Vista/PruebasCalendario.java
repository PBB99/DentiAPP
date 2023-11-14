package Vista;

import java.awt.EventQueue;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Date;
import java.util.GregorianCalendar;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JCalendar;
import com.toedter.calendar.JDateChooser;

import Controlador.CitasController;
import Controlador.ConexionMySQL;
import Modelo.Cita;
import Modelo.User;

import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ListSelectionModel;

public class PruebasCalendario extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTable table;
	private ConexionMySQL conexion;
	

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
		conexion.conectar();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 638, 447);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JCalendar calendar = new JCalendar();
		calendar.setBounds(23, 77, 184, 153);
		contentPane.add(calendar);
		
		JDateChooser dateChooser = new JDateChooser();
		dateChooser.setBounds(221, 12, 70, 20);
		contentPane.add(dateChooser);
		
		textField = new JTextField();
		textField.setBounds(10, 12, 31, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setBounds(47, 12, 31, 20);
		contentPane.add(textField_1);
		textField_1.setColumns(10);
		
		JLabel lblNewLabel = new JLabel(":");
		lblNewLabel.setBounds(42, 15, 11, 14);
		contentPane.add(lblNewLabel);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"a.m", "p.m"}));
		comboBox.setBounds(82, 11, 48, 22);
		contentPane.add(comboBox);
		
		DefaultTableModel modelo = new DefaultTableModel();
		table = new JTable();
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setModel(modelo);
		modelo.addColumn("Hora");
		modelo.addColumn("Cita");
		modelo.insertRow(0, new Object[] {"Hora", "Cita" });
		LocalDate fecha = LocalDate.now();
		Calendar fechaCalen = new GregorianCalendar();
		DateFormat formateador= new SimpleDateFormat("dd/M/yy");
		System.out.println(formateador.format(fechaCalen.getTime()));
		CitasController citaCont = new CitasController(conexion);
		ArrayList<Cita> allCitas;
		try {
			allCitas = citaCont.getAllSpecialist();
			for (int i = 9; i < 15; i++) {
				modelo.insertRow(modelo.getRowCount(), new Object[] { i+":00", "" });
			}
			for (int i = 17; i < 20; i++) {
				modelo.insertRow(modelo.getRowCount(), new Object[] { i+":00", "" });
			}
			table.setBounds(302, 77, 199, 196);
			contentPane.add(table);
			
			for (int i = 0; i < allCitas.size(); i++) {
				System.out.println("saddasdas");
				Date dia = allCitas.get(i).getFecha();
				System.out.println(formateador.format(dia) + "------");
				if(formateador.format(fechaCalen.getTime()).equals(formateador.format(dia))) {
					for (int j = 0; j < table.getModel().getRowCount(); j++) {
						if(table.getModel().getValueAt(j, 0).equals(allCitas.get(i).getHora())){
							table.getModel().setValueAt("Texto de ejemplo", j, 1);
						}
					}
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		//JTable table = new JTable();
		
		calendar.addPropertyChangeListener("calendar", new PropertyChangeListener() {
			
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				
				// TODO Auto-generated method stub
				for (int j = 1; j < table.getModel().getRowCount(); j++) {
					table.getModel().setValueAt("", j, 1);
				}
				
				
				Calendar fechaCal = (Calendar) evt.getNewValue();
				CitasController citaCont = new CitasController(conexion);
				ArrayList<Cita> allCitas;
				try {
					allCitas = citaCont.getAllSpecialist();
					for (int i = 0; i < allCitas.size(); i++) {
						Date dia = allCitas.get(i).getFecha();
						
						System.out.println(formateador.format(fechaCal.getTime()) + "------");
						if(formateador.format(fechaCal.getTime()).equals(formateador.format(dia))) {
							System.out.println("saddasdas");
							for (int j = 0; j < table.getModel().getRowCount(); j++) {
								if(table.getModel().getValueAt(j, 0).equals(allCitas.get(i).getHora())){
									table.getModel().setValueAt("Texto de ejemplo", j, 1);
								}
							}
						}
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}
}
