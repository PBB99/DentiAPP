package Vista;

import java.awt.EventQueue;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import Modelo.CitaHibernate;
import Modelo.ClienteHibernate;
import Modelo.TreatmentsHibernate;
import Modelo.UserHibernate;
import javax.swing.JComboBox;

public class PruebaOdonto extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private SessionFactory instancia;
	private Session session;



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
		
		JComboBox comboBox = new JComboBox();
		contentPane.add(comboBox);
		
		Query<ClienteHibernate> consultaClientes = session.createQuery("FROM ClienteHibernate", ClienteHibernate.class);
		List<ClienteHibernate> allClientes = consultaClientes.getResultList();
		
		for (int i = 0; i < allClientes.size(); i++) {
			comboBox.addItem(allClientes.get(i));
		}
	}

}
