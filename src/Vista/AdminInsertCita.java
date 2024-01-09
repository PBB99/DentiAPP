package Vista;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.border.BevelBorder;
import javax.swing.border.MatteBorder;
import javax.swing.border.SoftBevelBorder;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import Controlador.ConexionMySQL;
import Modelo.Cita;
import Modelo.CitaHibernate;
import Modelo.ClienteHibernate;
import Modelo.Specialist;
import Modelo.Speciality;
import Modelo.SpecialityHibernate;
import Modelo.TreatmentsHibernate;
import Modelo.UserHibernate;

import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
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
import javax.swing.JComboBox;


public class AdminInsertCita extends JDialog {
	//-------------------------------VARIABLES------------------------

	private JPanel contentPane;
	private Connection cn;
	private ConexionMySQL conex;
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	
	private SessionFactory instancia;
	private Session session;

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
				.buildSessionFactory();
		this.session = instancia.openSession();
		
		//-----------------------COMPONENTES-------------------
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPanel.setLayout(null);
		
		JLabel lblDNI = new JLabel("Paciente");
		lblDNI.setBounds(105, 94, 84, 14);
		
		
		JLabel lblContraseña = new JLabel("Tratamiento");
		lblContraseña.setBounds(105, 136, 84, 14);
		
		
		JButton okButton = new JButton("OK");
		okButton.setActionCommand("OK");
		getRootPane().setDefaultButton(okButton);
		
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setModal(false);
				setVisible(false);
				AdminInsertCita.this.dispatchEvent(new WindowEvent(
						AdminInsertCita.this, WindowEvent.WINDOW_CLOSING));
			}
		});
		cancelButton.setActionCommand("Cancel");
		
		JComboBox cbPaciente = new JComboBox();
		cbPaciente.setBounds(199, 91, 129, 20);
		Query<ClienteHibernate> consultaClientes = session.createQuery("FROM ClienteHibernate", ClienteHibernate.class);
		List<ClienteHibernate> allClientes = consultaClientes.getResultList();

		for (int i = 0; i < allClientes.size(); i++) {
			cbPaciente.addItem(allClientes.get(i));
		}
		
		JComboBox cbTratamiento = new JComboBox();
		cbTratamiento.setBounds(199, 132, 129, 20);
		contentPanel.add(cbTratamiento);
		
		
		
		Query<UserHibernate> consultaUsuarios = session.createQuery("FROM UserHibernate where dni=:dni", UserHibernate.class);
		consultaUsuarios.setParameter("dni", dniDoctor);
		UserHibernate usuario = consultaUsuarios.getSingleResult();
		List<SpecialityHibernate> allEspecialidades = usuario.getSpeciality();
		
		for (int i = 0; i < allEspecialidades.size(); i++) {
			List<TreatmentsHibernate> allTratamientos = allEspecialidades.get(i).getTratamientos();
			for (int j = 0; j < allTratamientos.size(); j++) {
				cbTratamiento.addItem(allTratamientos.get(j));
			}
		}
				
		//------------------------------------------LOGICA-----------------------------------------------
		ResultSet resSet = null;
		try {//CARGAR EN EL COMBO BOX LAS ESPECIALIDADES DE LA CLINICA
		
			resSet = conex.ejecutarSelect("Select * from especialidades");
			//conex.ejecutarInsertUpdateDelete("insert into usuario(dni, nombre, apellido, contraseña, estado) values ('79379541G', 'Pedro', 'Pueblo', '1234', true)", cn);
			while (resSet.next()) {
				cbPaciente.addItem(resSet.getString("especialidad"));
            }
		} catch (Exception e2) {
			// TODO: handle exception
		}
		
		
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						java.sql.Date dia = new java.sql.Date(fecha.getTime());
						Query<CitaHibernate> consultaCitaExiste = session.createQuery("FROM CitaHibernate where fecha=:fech and hora=:hora and dni_doc=:id",
		    					CitaHibernate.class);
						consultaCitaExiste.setParameter("fech",dia);
						consultaCitaExiste.setParameter("hora", hora);
						consultaCitaExiste.setParameter("id",dniDoctor);
						List<CitaHibernate> check = consultaCitaExiste.getResultList();
						
						if(check.isEmpty()) {
							Query<CitaHibernate> consultaCitas = session.createQuery("FROM CitaHibernate", CitaHibernate.class);
							List<CitaHibernate> allCitas = consultaCitas.getResultList();
							CitaHibernate cita = new CitaHibernate(allCitas.get(allCitas.size() - 1).getIdcita() + 1, dia, hora);
							cita.setCliente(allClientes.get(cbPaciente.getSelectedIndex()));
							cita.setUser(usuario);
							cita.setTratamiento((TreatmentsHibernate) cbTratamiento.getSelectedItem());
							session.beginTransaction();
							session.save(cita);
							session.getTransaction().commit();
							System.out.println("------------------------------------------------------------------------------------------------------------------------------------------");
						}else {
							CitaHibernate citaEx = consultaCitaExiste.getSingleResult();
							citaEx.setCliente(allClientes.get(cbPaciente.getSelectedIndex()));
							citaEx.setUser(usuario);
							citaEx.setTratamiento((TreatmentsHibernate) cbTratamiento.getSelectedItem());
							session.beginTransaction();
							session.update(citaEx);
							session.getTransaction().commit();
							System.out.println("------------------------------------------------------------------------------------------------------------------------------------------");
						}
						setModal(false);
						dispose();
						setVisible(false);
						AdminInsertCita.this.dispatchEvent(new WindowEvent(
								AdminInsertCita.this, WindowEvent.WINDOW_CLOSING));
					}
				});
				
			
			}
			{//-------------------ADICIONES DE LOS COMPONENTES---------------
				buttonPane.add(okButton);
				buttonPane.add(cancelButton);
				contentPanel.add(cbPaciente);
				contentPanel.add(lblContraseña);
				contentPanel.add(lblDNI);
			}
		}
		
	}
}
