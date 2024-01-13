package Vista;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import Modelo.ClienteHibernate;
import Modelo.OdontogramaHibernate;
import Modelo.UserHibernate;

import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JTextArea;

public class Internal_Historial extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	/*
	 id_odontograma
	 id_cliente
	 observaciones
	 fecha
	 clientes_dni_clientes*/

private SessionFactory instancia;
private Session session;
private List<OdontogramaHibernate> odonList;
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
	public Internal_Historial(int id_diente,ClienteHibernate cliente) {
		
		this.instancia = (SessionFactory) new Configuration().configure("hibernate.cfg.xml")
				.addAnnotatedClass(UserHibernate.class).addAnnotatedClass(OdontogramaHibernate.class).addAnnotatedClass(ClienteHibernate.class).buildSessionFactory();
		this.session = instancia.openSession();
		this.session.beginTransaction();
		
		//sacamos toda la info del diente que queramos teniendo en cuenta el paciente que tenemos
		String hql="From odontogramas where id_diente="+id_diente+" & clientes_dni_clientes=" +cliente.getDni_cliente();
		Query<OdontogramaHibernate> consulta = session.createQuery(hql, OdontogramaHibernate.class);
		odonList=consulta.getResultList();
		String consultaNombre=" from clientes where dni_cliente="+cliente.getDni_cliente();
		Query<ClienteHibernate>resultadoConsultaNombre=session.createQuery(consultaNombre,ClienteHibernate.class);
		ClienteHibernate nombreCliente=resultadoConsultaNombre.getSingleResult();
		
		setBounds(0, 0, 506, 680);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel lblidDiente = new JLabel("Diiente: ");
			lblidDiente.setBounds(31, 125, 46, 14);
			contentPanel.add(lblidDiente);
		}
		//---------------componentes gráficos-----------------
		JLabel lblFecha = new JLabel("Fecha");
		lblFecha.setBounds(272, 125, 46, 14);
		contentPanel.add(lblFecha);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(328, 121, 30, 22);
		for(OdotongramaHibernate x:odonList) {
			
		}
		contentPanel.add(comboBox);
		
		JLabel lblObservaciones = new JLabel("Tratamiento: ");
		lblObservaciones.setBounds(29, 224, 79, 14);
		contentPanel.add(lblObservaciones);
		
		JTextArea textArea = new JTextArea();
		textArea.setBounds(29, 249, 436, 288);
		contentPanel.add(textArea);
		
		JLabel lblNombrePaciente = new JLabel("");
		lblNombrePaciente.setText(nombreCliente.getNombre());
		lblNombrePaciente.setBounds(31, 34, 192, 14);
		
		contentPanel.add(lblNombrePaciente);
		
		JLabel lblimagenDiente = new JLabel("");
		lblimagenDiente.setBounds(304, 34, 66, 54);
		imagenDiente(id_diente, lblimagenDiente);
		contentPanel.add(lblimagenDiente);
		{
			JPanel bpBotonera = new JPanel();
			bpBotonera.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(bpBotonera, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("Imprimir");
				okButton.setActionCommand("OK");
				bpBotonera.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Salir");
				cancelButton.setActionCommand("Cancel");
				bpBotonera.add(cancelButton);
			}
		}
		
		
	}
	
	public static void imagenDiente(int id_diente,JLabel imagen) {
		switch (id_diente) {
		case 11:
			imagen.setIcon(new ImageIcon(CustommerOdont.class.getResource("/Resources/images/Diente_5.png")));
		break;
		case 15:
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
