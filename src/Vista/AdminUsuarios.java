package Vista;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.SoftBevelBorder;

public class AdminUsuarios extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AdminUsuarios frame = new AdminUsuarios();
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
	public AdminUsuarios() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setBounds(0, 0, 1920, 1080);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		//this.setUndecorated(true);
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel menuPane = new JPanel();
		menuPane.setBackground(new Color(148,220,219));
		menuPane.setBounds(0, 0, 135, 1080);
		contentPane.add(menuPane);
		menuPane.setLayout(null);
		
		JLabel lblLogo = new JLabel("");
		//lblLogo.setBorder(new LineBorder(new Color(0, 0, 0)));
		lblLogo.setBounds(0, 0, 135, 135);
		lblLogo.setIcon(new ImageIcon(getClass().getResource("/Resources/images/logoMenu.png")));
		menuPane.add(lblLogo);
		
		JButton btnAppointment = new JButton();
		btnAppointment.setBackground(new Color(238,238,238));
		btnAppointment.setBounds(0, 135, 135, 135);
		btnAppointment.setBorderPainted(false);
		btnAppointment.setIcon(new ImageIcon(getClass().getResource("/Resources/images/calendar.png")));
		menuPane.add(btnAppointment);
		
		JButton btnUsers = new JButton("Usuarios");
		btnUsers.setBackground(new Color(148,220,219));
		btnUsers.setBounds(0, 270, 135, 135);
		btnUsers.setBorderPainted(false);
		btnUsers.setIcon(new ImageIcon(getClass().getResource("/Resources/images/usersGrey.png")));
		menuPane.add(btnUsers);
		
		JButton btnCustomers = new JButton("Pacientes");
		btnCustomers.setBackground(new Color(148,220,219));
		btnCustomers.setBounds(0, 405, 135, 135);
		btnCustomers.setBorderPainted(false);
		btnCustomers.setIcon(new ImageIcon(getClass().getResource("/Resources/images/customersGrey.png")));
		menuPane.add(btnCustomers);
		
		JButton btnStock = new JButton("Inventario");
		btnStock.setBackground(new Color(148,220,219));
		btnStock.setBounds(0, 540, 135, 135);
		btnStock.setBorderPainted(false);
		btnStock.setIcon(new ImageIcon(getClass().getResource("/Resources/images/stockGrey.png")));
		menuPane.add(btnStock);
		
		JButton btnClinic = new JButton("Clinica");
		btnClinic.setBackground(new Color(148,220,219));
		btnClinic.setBounds(0, 675, 135, 135);
		btnClinic.setBorderPainted(false);
		btnClinic.setIcon(new ImageIcon(getClass().getResource("/Resources/images/clinicGrey.png")));
		menuPane.add(btnClinic);
		
		JButton btnPayments = new JButton("Econimico");
		btnPayments.setBackground(new Color(148,220,219));
		btnPayments.setBounds(0,810,135,135);
		btnPayments.setBorderPainted(false);
		btnPayments.setIcon(new ImageIcon(getClass().getResource("/Resources/images/paymentsGrey.png")));
		menuPane.add(btnPayments);
		
		JButton btnClose = new JButton();
		btnClose.setBackground(new Color(148,220,219));
		btnClose.setBounds(0,945,135,135);
		menuPane.add(btnClose);
		btnClose.setIcon(new ImageIcon(getClass().getResource("/Resources/images/logoutMenuGrey.png")));
		btnClose.setBorderPainted(false);
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
	}
}
