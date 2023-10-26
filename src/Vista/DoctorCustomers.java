package Vista;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Controlador.ConexionMySQL;

public class DoctorCustomers extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private ConexionMySQL conex;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
//				try {
//					DoctorCustomers frame = new DoctorCustomers();
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public DoctorCustomers(ConexionMySQL conex) {
		this.conex = conex;
		
		// -------------------- JFrame --------------------
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setBounds(0, 0, 1920, 1080);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		this.setUndecorated(true);
		setContentPane(contentPane);
		contentPane.setLayout(null);

		// -------------------- Componentes --------------------
		// Panel del Menú
		JPanel menuPane = new JPanel();
		menuPane.setBackground(new Color(148, 220, 219));
		menuPane.setBounds(0, 0, 135, 1080);
		contentPane.add(menuPane);
		menuPane.setLayout(null);

		// Label del Logo del Menú
		JLabel lblLogo = new JLabel();
		// lblLogo.setBorder(new LineBorder(new Color(0, 0, 0)));
		lblLogo.setBounds(0, 0, 135, 135);
		lblLogo.setIcon(new ImageIcon(getClass().getResource("/Resources/images/logoMenu.png")));

		// Botón de citas
		JButton btnAppointment = new JButton();
		btnAppointment.setBackground(new Color(148, 220, 219));
		btnAppointment.setBounds(0, 135, 135, 135);
		btnAppointment.setBorderPainted(false);
		btnAppointment.setIcon(new ImageIcon(getClass().getResource("/Resources/images/calendarGrey.png")));
		btnAppointment.setToolTipText("Módulo de citas");
		btnAppointment.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseExited(MouseEvent e) {
				btnAppointment.setBackground(new Color(148, 220, 219));
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				btnAppointment.setBackground(new Color(31, 192, 191));
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub

			}
		});

		// Botón de Pacientes
		JButton btnCustomers = new JButton();
		btnCustomers.setBackground(new Color(238, 238, 238));
		btnCustomers.setBounds(0, 270, 135, 135);
		btnCustomers.setBorderPainted(false);
		btnCustomers.setIcon(new ImageIcon(getClass().getResource("/Resources/images/customers.png")));
		btnCustomers.setToolTipText("Módulo de pacientes");

		// Botón de Inventario
		JButton btnStock = new JButton();
		btnStock.setBackground(new Color(148, 220, 219));
		btnStock.setBounds(0, 405, 135, 135);
		btnStock.setBorderPainted(false);
		btnStock.setIcon(new ImageIcon(getClass().getResource("/Resources/images/stockGrey.png")));
		btnStock.setToolTipText("Módulo de materiales");
		btnStock.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseExited(MouseEvent e) {
				btnStock.setBackground(new Color(148, 220, 219));
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				btnStock.setBackground(new Color(31, 192, 191));
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub

			}
		});

		// Botón de Salir
		JButton btnClose = new JButton();
		btnClose.setBackground(new Color(148, 220, 219));
		btnClose.setBounds(0, 945, 135, 135);
		btnClose.setIcon(new ImageIcon(getClass().getResource("/Resources/images/logoutMenuGrey.png")));
		btnClose.setToolTipText("Módulo Económico");
		btnClose.setBorderPainted(false);
		btnClose.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseExited(MouseEvent e) {
				btnClose.setBackground(new Color(148, 220, 219));
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				btnClose.setBackground(new Color(31, 192, 191));
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub

			}
		});

		// -------------------- Lógica --------------------
		// Acción de salir
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});

		// Acción de ir a Módulo Citas
		btnAppointment.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DoctorAppointment docAppointment = new DoctorAppointment(conex);
				docAppointment.setVisible(true);
				dispose();
			}
		});

		// Acción de ir a Módulo Stock
		btnStock.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DoctorStock docStock = new DoctorStock(conex);
				docStock.setVisible(true);
				dispose();
			}
		});

		// -------------------- Adiciones a los paneles --------------------
		contentPane.add(menuPane);
		menuPane.add(lblLogo);
		menuPane.add(btnAppointment);
		menuPane.add(btnCustomers);
		menuPane.add(btnStock);
		menuPane.add(btnClose);
	}

}
