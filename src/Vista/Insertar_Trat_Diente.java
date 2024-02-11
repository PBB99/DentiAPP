package Vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import Modelo.CitaHibernate;
import Modelo.ClienteHibernate;
import Modelo.OdontogramaHibernate;
import Modelo.SpecialityHibernate;
import Modelo.TreatmentsHibernate;
import Modelo.UserHibernate;

public class Insertar_Trat_Diente extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private SessionFactory instancia;
	private Session session;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
//		try {
//			Insertar_Trat_Diente dialog = new Insertar_Trat_Diente();
//			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
//			dialog.setVisible(true);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
	}

	/**
	 * Create the dialog.
	 */
	public Insertar_Trat_Diente(int id_diente, ClienteHibernate cliente, Session session) {

		// fecha del dia actual
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("uuuu/MM/dd");
		LocalDate localDate = LocalDate.now();
		System.out.println(dtf.format(localDate));
		setUndecorated(true);
		
		this.session = session;
		
		setBounds(0, 0, 506, 680);
		getContentPane().setLayout(null);
		contentPanel.setBounds(0, 0, 506, 600);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPanel.setBackground(new Color(238,238,238));
		// ------------COMPONENTES GRAFICOS------------

		JLabel lblFecha = new JLabel("Fecha:   " + dtf.format(localDate));
		lblFecha.setBounds(272, 125, 168, 14);

		JLabel lblidDiente = new JLabel("Diente: " + id_diente);
		lblidDiente.setBounds(31, 125, 161, 14);

		JLabel lblObservaciones = new JLabel("Tratamiento: ");
		lblObservaciones.setBounds(29, 224, 188, 14);

		JTextArea taObservaciones = new JTextArea();
		taObservaciones.setBounds(29, 249, 438, 233);

		JLabel lblNombrePaciente = new JLabel("");
		lblNombrePaciente.setText(cliente.getNombre());
		lblNombrePaciente.setBounds(31, 34, 242, 14);

		JLabel lblimagenDiente = new JLabel("");
		lblimagenDiente.setBounds(304, 34, 66, 54);
		imagenDiente(id_diente, lblimagenDiente);
		contentPanel.setLayout(null);

		// -------------------Adicciones----------------

		getContentPane().add(contentPanel);
		contentPanel.add(lblFecha);
		contentPanel.add(lblObservaciones);
		contentPanel.add(taObservaciones);
		contentPanel.add(lblNombrePaciente);
		contentPanel.add(lblimagenDiente);
		contentPanel.add(lblidDiente);
		JPanel buttonPane = new JPanel();
		buttonPane.setBounds(0, 504, 506, 33);
		contentPanel.add(buttonPane);
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		
				JButton bCancelar = new JButton("Cancelar");
				bCancelar.setActionCommand("Cancel");
				
						
				
						JButton bInsertar = new JButton("Insertar");
						bInsertar.setActionCommand("OK");
						getRootPane().setDefaultButton(bInsertar);
						
								// -------------------LOGICA------------------
						
								bInsertar.addActionListener(new ActionListener() {
						
									@Override
									public void actionPerformed(ActionEvent e) {
										// TODO Auto-generated method stub
										OdontogramaHibernate odonto = new OdontogramaHibernate( id_diente, taObservaciones.getText(),
												Date.valueOf(localDate));
										odonto.setCliente(cliente);
										session.beginTransaction();
										session.save(odonto);
										session.getTransaction().commit();
										System.out.println(odonto.getId_odontogramas());
										
										dispose();
									}
								});
								
								bCancelar.addActionListener(new ActionListener() {
									
									@Override
									public void actionPerformed(ActionEvent e) {
										// TODO Auto-generated method stub
										dispose();
									}
								});
		buttonPane.add(bInsertar);
		buttonPane.add(bCancelar);
	}

	// metodo
	public static void imagenDiente(int id_diente, JLabel imagen) {
		switch (id_diente) {
		case 21:
			imagen.setIcon(new ImageIcon(CustommerOdont.class.getResource("/Resources/images/Diente_5.png")));
			break;
		case 14:
			imagen.setIcon(new ImageIcon(CustommerOdont.class.getResource("/Resources/images/Diente_2.png")));
			break;
		case 17:
			imagen.setIcon(new ImageIcon(CustommerOdont.class.getResource("/Resources/images/Diente_1.png")));
			break;
		case 25:
			imagen.setIcon(new ImageIcon(CustommerOdont.class.getResource("/Resources/images/Diente_4.png")));
			break;
		case 27:
			imagen.setIcon(new ImageIcon(CustommerOdont.class.getResource("/Resources/images/Diente3.png")));
			break;
		case 35:
			imagen.setIcon(new ImageIcon(CustommerOdont.class.getResource("/Resources/images/Diente_9.png")));
			break;
		case 37:
			imagen.setIcon(new ImageIcon(CustommerOdont.class.getResource("/Resources/images/Diente_8.png")));
			break;
		case 41:
			imagen.setIcon(new ImageIcon(CustommerOdont.class.getResource("/Resources/images/Diente_10.png")));
			break;
		case 45:
			imagen.setIcon(new ImageIcon(CustommerOdont.class.getResource("/Resources/images/Diente_7.png")));
			break;
		case 47:
			imagen.setIcon(new ImageIcon(CustommerOdont.class.getResource("/Resources/images/Diente_6.png")));
			break;

		}
	}
}
