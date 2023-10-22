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
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JInternalFrame;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class pAdmin extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					pAdmin frame = new pAdmin();
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
	public pAdmin() {
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
		btnCitas.setBackground(Color.WHITE);
		btnCitas.setBounds(0, 100, 100, 100);
		panelIzquierdo.add(btnCitas);
		
		JButton btnUsuarios = new JButton("Usuarios");
		btnUsuarios.setBackground(Color.CYAN);
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
	}
}