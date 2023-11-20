package Vista;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import Modelo.SpecialityHibernate;
import Modelo.TreatmentsHibernate;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class DInsertTratamiento extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField tfName;
	private JTextField tfPrice;
	private JTextField tfSpeciality;
	private SessionFactory instancia;
	private Session session;
	private SpecialityHibernate sh;
	private int idTreatment;
	
	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		try {
//			DInsertTratamiento dialog = new DInsertTratamiento();
//			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
//			dialog.setVisible(true);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

	/**
	 * Create the dialog.
	 */
	public DInsertTratamiento(SessionFactory instancia, SpecialityHibernate sh, int id) {
		this.instancia = instancia;
		this.session = instancia.openSession();
		this.sh = sh;
		this.idTreatment = id;
		
		// -------------------- JDialog --------------------
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(735, 390, 450, 300);
		getContentPane().setLayout(null);
		contentPanel.setBounds(0, 0, 434, 207);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPanel.setLayout(null);

		// -------------------- Componentes --------------------

		// Panel de los botones
		JPanel buttonPane = new JPanel();
		buttonPane.setBounds(0, 218, 434, 45);
		buttonPane.setLayout(null);

		// Botón de guardar/insertar tratamiento
		JButton btnSave = new JButton("Guardar");
		btnSave.setBounds(250, 5, 80, 23);
		getRootPane().setDefaultButton(btnSave);

		// boton de cancelar y salir
		JButton btnCancel = new JButton("Cancel");
		btnCancel.setBounds(337, 5, 80, 23);

		// Etiqueta del nombre de la especialidad
		JLabel lblSpeciality = new JLabel("Especialidad");
		lblSpeciality.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblSpeciality.setBounds(50, 40, 120, 25);

		// TextEdit del nombre de la especialidad
		tfSpeciality = new JTextField();
		tfSpeciality.setEditable(false);
		tfSpeciality.setBounds(174, 40, 200, 25);
		tfSpeciality.setColumns(10);

		// Etiqueta del nombre del tratamiento
		JLabel lblName = new JLabel("Nombre");
		lblName.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblName.setBounds(50, 80, 120, 25);

		// TextEdit del nombre del tratamiento
		tfName = new JTextField();
		tfName.setBounds(174, 80, 200, 25);
		contentPanel.add(tfName);
		tfName.setColumns(10);

		// Etiqueta del precio
		JLabel lblPrice = new JLabel("Precio");
		lblPrice.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblPrice.setBounds(50, 120, 120, 25);

		// TextEdit del precio
		tfPrice = new JTextField();
		tfPrice.setBounds(174, 120, 200, 25);
		tfPrice.setColumns(10);

		// -------------------- Lógica --------------------
		// Acción de cancelar
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				session.close();
				dispose();
			}
		});

		// Acción de guardar
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// variable de control
				boolean isValid = true;
				
				//Creamos el objeto
				TreatmentsHibernate th = new TreatmentsHibernate();
				th.setEspecialidad(sh);
				th.setCodigo_tratamiento(idTreatment);
				
				// Nos aseguramos que rellena todos los campos
				if ((tfName.getText().isBlank() || tfPrice.getText().isBlank())) {
					JOptionPane.showMessageDialog(contentPanel, "Debe rellenar todos los campos", "Error",
							JOptionPane.ERROR_MESSAGE);
					isValid = false;
				}

				if (isValid == true) {
					// Modifica el tratamiento
					th.setNombre(tfName.getText());
					try { // Controla que meta un números
						int numero = Integer.parseInt(tfPrice.getText());

						// Controla que sea positivo
						if (numero < 0) {
							throw new Exception();
						}

						// Actualiza el número
						th.setPrecio(numero);
					} catch (Exception e2) {
						JOptionPane.showMessageDialog(contentPanel, "Valor no valido para el campo precio", "Error",
								JOptionPane.ERROR_MESSAGE);
						isValid = false;
					}
				}

				// Realizamos la operación de update
				if (isValid) {
					session.beginTransaction();
					session.save(th);
					session.getTransaction().commit();

					// Cerramos
					session.close();
					dispose();
				}
			}
		});

		// -------------------- Adiciones a los paneles --------------------
		getContentPane().add(contentPanel);
		getContentPane().add(buttonPane);
		buttonPane.add(btnSave);
		buttonPane.add(btnCancel);
		contentPanel.add(lblSpeciality);
		contentPanel.add(tfSpeciality);
		contentPanel.add(lblName);
		contentPanel.add(lblPrice);
		contentPanel.add(tfPrice);

	}

	// -------------------- Métodos y Funciones --------------------
	// Poner una especialidad
	public void setTextSpeciality(String speciality) {
		tfSpeciality.setText(speciality);
	}

	// Poner un nombre de tratamiento
	public void setTextName(String name) {
		tfName.setText(name);
	}

	// Poner un precio al tratamiento
	public void setTextPrice(int price) {
		tfPrice.setText(String.valueOf(price));
	}
}
