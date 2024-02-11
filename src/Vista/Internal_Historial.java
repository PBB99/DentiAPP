package Vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import Modelo.CitaHibernate;
import Modelo.ClienteHibernate;
import Modelo.OdontogramaHibernate;
import Modelo.SpecialityHibernate;
import Modelo.TreatmentsHibernate;
import Modelo.UserHibernate;

import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JTextArea;

public class Internal_Historial extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	/*
	 * id_odontograma id_cliente observaciones fecha clientes_dni_clientes
	 */

	private SessionFactory instancia;
	private Session session;
	private List<OdontogramaHibernate> odonList;
	private List<OdontogramaHibernate> odonList2;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
//		try {
//			Internal_Historial dialog = new Internal_Historial();
//			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
//			dialog.setVisible(true);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
	}

	/**
	 * Create the dialog.
	 */
	public Internal_Historial(int id_diente, ClienteHibernate cliente,Session session) {

		this.instancia = (SessionFactory) new Configuration().configure("hibernate.cfg.xml")
				.addAnnotatedClass(UserHibernate.class).addAnnotatedClass(CitaHibernate.class)
				.addAnnotatedClass(TreatmentsHibernate.class).addAnnotatedClass(OdontogramaHibernate.class).addAnnotatedClass(ClienteHibernate.class)
				.addAnnotatedClass(SpecialityHibernate.class).buildSessionFactory();
		this.session = session;
		

		// sacamos toda la info del diente que queramos teniendo en cuenta el paciente
		// que tenemos
		String hql = "From OdontogramaHibernate where id_diente=:id_diente and clientes_dni_cliente=:dni_cliente";
        Query<OdontogramaHibernate> consulta = session.createQuery(hql, OdontogramaHibernate.class);
        consulta.setParameter("id_diente", id_diente);
        consulta.setParameter("dni_cliente", cliente.getDni_cliente());
        odonList = consulta.getResultList();
		
		// esto no sirve, ida de olla pero estructura comentada por si acaso es util en
		// otro contexto
//		String consultaNombre = " from clientes where dni_cliente=" + cliente.getDni_cliente();
//		Query<ClienteHibernate> resultadoConsultaNombre = session.createQuery(consultaNombre, ClienteHibernate.class);
//		ClienteHibernate nombreCliente = resultadoConsultaNombre.getSingleResult();
		setUndecorated(true);
		setBounds(0, 0, 506, 680);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPanel.setBackground(new Color(238,238,238));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{

		}
		// ---------------componentes gráficos-----------------
		JLabel lblFecha = new JLabel("Fecha");
		lblFecha.setBounds(272, 125, 46, 14);

		JLabel lblidDiente = new JLabel("Diente: "+id_diente);
		lblidDiente.setBounds(31, 125, 110, 14);

		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(328, 121, 137, 22);
		// mostrar las fechas en las que ese diente de ese cliente han tenido registros
		// de tratamiento
		for (OdontogramaHibernate x : odonList) {
			comboBox.addItem(x.getFecha());

		}

		JLabel lblObservaciones = new JLabel("Tratamiento: ");
		lblObservaciones.setBounds(29, 224, 137, 14);

		JTextArea taObservaciones = new JTextArea();
		taObservaciones.setBounds(29, 249, 436, 288);
		taObservaciones.setEditable(false);

		JLabel lblNombrePaciente = new JLabel("");
		lblNombrePaciente.setText(cliente.getNombre());
		lblNombrePaciente.setBounds(31, 34, 218, 14);

		JLabel lblimagenDiente = new JLabel("");
		lblimagenDiente.setBounds(304, 34, 66, 54);

		JPanel bpBotonera = new JPanel();
		bpBotonera.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(bpBotonera, BorderLayout.SOUTH);

		JButton bImprimir = new JButton("Imprimir");

		// aqui en teoria deberia imprimirse el informe que aun no hemos realizado
		bImprimir.setActionCommand("OK");

		JButton bRecargar = new JButton("Recargar");
		// aqui se cierra la ventana
		bRecargar.setActionCommand("Cancel");
		getRootPane().setDefaultButton(bImprimir);

		// --------LOGICA-------------------

		imagenDiente(id_diente, lblimagenDiente);

		comboBox.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Date eleccion = (Date) comboBox.getSelectedItem();
				String hql2 = "FROM OdontogramaHibernate where id_diente=:id_diente and fecha=:fecha";
				Query<OdontogramaHibernate> consultaDatos = session.createQuery(hql2, OdontogramaHibernate.class);
				consultaDatos.setParameter("id_diente", id_diente);
				consultaDatos.setParameter("fecha", eleccion);
				OdontogramaHibernate dienteCargado = consultaDatos.getSingleResult();
				
				taObservaciones.setText(dienteCargado.getObservaciones());

			}
		});

		bImprimir.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				// aqui el metodo para imprimir el informe
			}
		});

		bRecargar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// salir
				
				DefaultComboBoxModel<String> model = (DefaultComboBoxModel<String>)comboBox.getModel();
				model.removeAllElements();
				String hql = "From OdontogramaHibernate where id_diente=:id_diente and clientes_dni_cliente=:dni_cliente";
				Query<OdontogramaHibernate> consulta = session.createQuery(hql, OdontogramaHibernate.class);
		        consulta.setParameter("id_diente", id_diente);
		        consulta.setParameter("dni_cliente", cliente.getDni_cliente());
		        
		        odonList2 = consulta.getResultList();
				for (OdontogramaHibernate y : odonList2) {
					
					comboBox.addItem(y.getFecha());

				}
			}
		});
		// -----------------------------ADICIONES----------------------------
		contentPanel.add(comboBox);
		contentPanel.add(lblFecha);
		contentPanel.add(lblObservaciones);
		contentPanel.add(taObservaciones);
		contentPanel.add(lblNombrePaciente);
		contentPanel.add(lblimagenDiente);
		contentPanel.add(lblidDiente);
		//bpBotonera.add(bImprimir);
		//bpBotonera.add(bRecargar);

	}

//--------METODOS Y FUNCIONES--------------------

	// metodo para poner la imagen correspondiente al diente seleccionado
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
