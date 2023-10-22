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
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.CYAN);
		panel_1.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
		panel_1.setBounds(0, 0, 100, 736);
		panel.add(panel_1);
		panel_1.setLayout(null);
		
		JButton btnNewButton = new JButton("Citas");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNewButton.setBackground(Color.WHITE);
		btnNewButton.setBounds(0, 100, 100, 100);
		panel_1.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Usuarios");
		btnNewButton_1.setBackground(Color.CYAN);
		btnNewButton_1.setBounds(0, 200, 100, 100);
		panel_1.add(btnNewButton_1);
		
		JButton btnNewButton_1_1 = new JButton("Pacientes");
		btnNewButton_1_1.setBackground(Color.CYAN);
		btnNewButton_1_1.setBounds(0, 300, 100, 100);
		panel_1.add(btnNewButton_1_1);
		
		JButton btnNewButton_1_1_1 = new JButton("Material");
		btnNewButton_1_1_1.setBackground(Color.CYAN);
		btnNewButton_1_1_1.setBounds(0, 400, 100, 100);
		panel_1.add(btnNewButton_1_1_1);
		
		JButton btnNewButton_1_1_1_1 = new JButton("Medica");
		btnNewButton_1_1_1_1.setBackground(Color.CYAN);
		btnNewButton_1_1_1_1.setBounds(0, 500, 100, 100);
		panel_1.add(btnNewButton_1_1_1_1);
		
		JButton btnNewButton_1_1_1_1_1 = new JButton("Economico");
		btnNewButton_1_1_1_1_1.setBackground(Color.CYAN);
		btnNewButton_1_1_1_1_1.setBounds(0, 600, 100, 100);
		panel_1.add(btnNewButton_1_1_1_1_1);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBounds(100, 0, 784, 736);
		panel.add(panel_2);
	}
}