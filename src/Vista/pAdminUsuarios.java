package Vista;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.border.BevelBorder;
import javax.swing.border.MatteBorder;
import javax.swing.border.SoftBevelBorder;

import Controlador.ConexionMySQL;

import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import javax.swing.JInternalFrame;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JComboBox;

public class pAdminUsuarios extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField tfDNI;
	private JTextField tfNombre;
	private JTextField tfContraseña;
	private JTextField tfApellido;
	private Connection cn;
	private ConexionMySQL conex;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					pAdminUsuarios frame = new pAdminUsuarios();
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
	public pAdminUsuarios() {
		conex = new ConexionMySQL();
		cn = conex.conectar();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 900, 775);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(255, 255, 255)));
		panel.setBounds(0, 0, 884, 736);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JPanel panelIzquierdo = new JPanel();
		panelIzquierdo.setBackground(Color.CYAN);
		panelIzquierdo.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
		panelIzquierdo.setBounds(0, 0, 100, 736);
		panel.add(panelIzquierdo);
		panelIzquierdo.setLayout(null);
		
		JButton btnCitas = new JButton("Citas");
		btnCitas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnCitas.setBackground(Color.CYAN);
		btnCitas.setBounds(0, 100, 100, 100);
		panelIzquierdo.add(btnCitas);
		
		JButton btnUsuarios = new JButton("Usuarios");
		btnUsuarios.setBackground(Color.WHITE);
		btnUsuarios.setBounds(0, 200, 100, 100);
		panelIzquierdo.add(btnUsuarios);
		
		JButton btnPacientes = new JButton("Pacientes");
		btnPacientes.setBackground(Color.CYAN);
		btnPacientes.setBounds(0, 300, 100, 100);
		panelIzquierdo.add(btnPacientes);
		
		JButton btnMaterial = new JButton("Material");
		btnMaterial.setBackground(Color.CYAN);
		btnMaterial.setBounds(0, 400, 100, 100);
		panelIzquierdo.add(btnMaterial);
		
		JButton btnMedica = new JButton("Medica");
		btnMedica.setBackground(Color.CYAN);
		btnMedica.setBounds(0, 500, 100, 100);
		panelIzquierdo.add(btnMedica);
		
		JButton btnEconomica = new JButton("Economico");
		btnEconomica.setBackground(Color.CYAN);
		btnEconomica.setBounds(0, 600, 100, 100);
		panelIzquierdo.add(btnEconomica);
		
		JPanel panelDerecho = new JPanel();
		panelDerecho.setBounds(100, 0, 784, 736);
		panel.add(panelDerecho);
		panelDerecho.setLayout(null);
		
		JLabel lblDNI = new JLabel("DNI");
		lblDNI.setBounds(68, 51, 62, 14);
		panelDerecho.add(lblDNI);
		
		JLabel lblNombre = new JLabel("Nombre");
		lblNombre.setBounds(68, 179, 62, 14);
		panelDerecho.add(lblNombre);
		
		tfDNI = new JTextField();
		tfDNI.setColumns(10);
		tfDNI.setBounds(214, 48, 75, 20);
		panelDerecho.add(tfDNI);
		
		tfNombre = new JTextField();
		tfNombre.setColumns(10);
		tfNombre.setBounds(214, 176, 75, 20);
		panelDerecho.add(tfNombre);
		
		JLabel lblContraseña = new JLabel("Contraseña");
		lblContraseña.setBounds(419, 51, 91, 14);
		panelDerecho.add(lblContraseña);
		
		tfContraseña = new JTextField();
		tfContraseña.setColumns(10);
		tfContraseña.setBounds(565, 48, 75, 20);
		panelDerecho.add(tfContraseña);
		
		JLabel lblApellido = new JLabel("Apellido");
		lblApellido.setBounds(419, 176, 75, 14);
		panelDerecho.add(lblApellido);
		
		tfApellido = new JTextField();
		tfApellido.setColumns(10);
		tfApellido.setBounds(565, 173, 75, 20);
		panelDerecho.add(tfApellido);
		
		JLabel lblEspecialidad = new JLabel("Especialidad");
		lblEspecialidad.setBounds(231, 273, 75, 14);
		panelDerecho.add(lblEspecialidad);
		
		JComboBox cbEspecialidad = new JComboBox();
		cbEspecialidad.setBounds(316, 269, 114, 22);
		panelDerecho.add(cbEspecialidad);
		cbEspecialidad.addItem("");
		ResultSet rs = null;
		try {
			rs = conex.ejecutarSelect("Select * from especialidades",cn);
			//conex.ejecutarInsertUpdateDelete("insert into usuario(dni, nombre, apellido, contraseña, estado) values ('79379541G', 'Pedro', 'Pueblo', '1234', true)", cn);
			while (rs.next()) {
				cbEspecialidad.addItem(rs.getString("especialidad"));
            }
		} catch (Exception e2) {
			// TODO: handle exception
		}
		
		JButton btnInsertar = new JButton("Insertar");
		btnInsertar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String dni = tfDNI.getText();
				String nom = tfNombre.getText();
				String ape = tfApellido.getText();
				String contra = tfContraseña.getText();
				String especialidad = cbEspecialidad.getSelectedItem().toString();
				boolean encontrado = false;
				int numIdEspecialista;
				int nEsp = 0;
				ResultSet rs = null;
				try {
					if(dni.isEmpty() || nom.isEmpty() || ape.isEmpty() || contra.isEmpty() || cbEspecialidad.getSelectedItem().toString().equalsIgnoreCase("") ) {
						JOptionPane.showMessageDialog(null,"Debes rellenar todos los campos", "WARNING_MESSAGE",JOptionPane.WARNING_MESSAGE);
					}else {
						rs = conex.ejecutarSelect("Select * from especialista",cn);
						//conex.ejecutarInsertUpdateDelete("insert into usuario(dni, nombre, apellido, contraseña, estado) values ('79379541G', 'Pedro', 'Pueblo', '1234', true)", cn);
						rs.last();
						numIdEspecialista = rs.getInt("id_especialista") + 1;
						conex.ejecutarInsertUpdateDelete("insert into usuario values ('" + dni + "', '" + nom + "', '" + ape + "', '" + contra + "', true)", cn);
						rs = conex.ejecutarSelect("Select * from especialidades",cn);
						System.out.println(especialidad);
						while (encontrado == false && rs.next() ) {
			                if(especialidad.equalsIgnoreCase(rs.getString("especialidad"))) {
			                	encontrado = true;
			                	nEsp = rs.getInt("id_especialidad");
			                }
			            }
						conex.ejecutarInsertUpdateDelete("insert into especialista values ('" + numIdEspecialista + "', '" + dni + "', '" + nEsp + "')", cn);
						JOptionPane.showMessageDialog(null,"Nuevo usuario creado");
					}
					
				} catch (Exception e2) {
					// TODO: handle exception
				}
			}
		});
		btnInsertar.setBounds(314, 395, 89, 23);
		panelDerecho.add(btnInsertar);
		
		
	}
}