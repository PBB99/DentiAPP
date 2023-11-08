package Vista;

import java.awt.EventQueue;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Calendar;
import java.util.Iterator;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JCalendar;
import com.toedter.calendar.JDateChooser;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

public class PruebasCalendario extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTable table;

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
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 638, 447);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JCalendar calendar = new JCalendar();
		calendar.setBounds(108, 50, 184, 153);
		contentPane.add(calendar);
		
		JDateChooser dateChooser = new JDateChooser();
		dateChooser.setBounds(105, 301, 70, 20);
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
		table.setModel(modelo);
		modelo.addColumn("Hora");
		modelo.addColumn("Cita");
		for (int i = 9; i < 15; i++) {
			modelo.insertRow(modelo.getRowCount(), new Object[] { i+":00", "" });
		}
		for (int i = 17; i < 20; i++) {
			modelo.insertRow(modelo.getRowCount(), new Object[] { i+":00", "" });
		}
		table.setBounds(338, 168, 199, 196);
		contentPane.add(table);
		
		//JTable table = new JTable();
		
		calendar.addPropertyChangeListener("calendar", new PropertyChangeListener() {
			
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				// TODO Auto-generated method stub
				System.out.println("asddasdsaasd");
				Calendar c = (Calendar) evt.getNewValue();
				System.out.println(c.get(Calendar.DAY_OF_MONTH));
				System.out.println(c.get(Calendar.MONTH)+1);
				System.out.println(c.get(Calendar.YEAR));
			}
		});
	}
}
