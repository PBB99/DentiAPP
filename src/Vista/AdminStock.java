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

public class AdminStock extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AdminStock frame = new AdminStock();
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
	public AdminStock() {
		// -------------------- JFrame --------------------
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setBounds(0, 0, 1920, 1080);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		this.setUndecorated(true);
		setContentPane(contentPane);
		contentPane.setLayout(null);

		// -------------------- Componentes Gráficos --------------------
		// Panel del Menú
		JPanel menuPane = new JPanel();
		menuPane.setBackground(new Color(148, 220, 219));
		menuPane.setBounds(0, 0, 135, 1080);
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
		btnAppointment.setToolTipText("Módulo de citas");

		// Botón de usuarios
		JButton btnUsers = new JButton();
		btnUsers.setBackground(new Color(148, 220, 219));
		btnUsers.setBounds(0, 270, 135, 135);
		btnUsers.setBorderPainted(false);
		btnUsers.setIcon(new ImageIcon(getClass().getResource("/Resources/images/usersGrey.png")));
		btnUsers.setToolTipText("Módulo de usuarios");
		btnUsers.addMouseListener(new MouseListener() {
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
				btnUsers.setBackground(new Color(148, 220, 219));
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				btnUsers.setBackground(new Color(31, 192, 191));
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub

			}
		});

		// Botón de Pacientes
		JButton btnCustomers = new JButton();
		btnCustomers.setBackground(new Color(148, 220, 219));
		btnCustomers.setBounds(0, 405, 135, 135);
		btnCustomers.setBorderPainted(false);
		btnCustomers.setIcon(new ImageIcon(getClass().getResource("/Resources/images/customersGrey.png")));
		btnCustomers.setToolTipText("Módulo de pacientes");
		btnCustomers.addMouseListener(new MouseListener() {
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
				btnCustomers.setBackground(new Color(148, 220, 219));
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				btnCustomers.setBackground(new Color(31, 192, 191));
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub

			}
		});

		// Botón de Inventario
		JButton btnStock = new JButton();
		btnStock.setBackground(new Color(238, 238, 238));
		btnStock.setBounds(0, 540, 135, 135);
		btnStock.setBorderPainted(false);
		btnStock.setIcon(new ImageIcon(getClass().getResource("/Resources/images/stock.png")));
		btnStock.setToolTipText("Módulo de materiales");

		// Botón de Tratamientos y Especialidades
		JButton btnClinic = new JButton();
		btnClinic.setBackground(new Color(148, 220, 219));
		btnClinic.setBounds(0, 675, 135, 135);
		btnClinic.setBorderPainted(false);
		btnClinic.setIcon(new ImageIcon(getClass().getResource("/Resources/images/clinicGrey.png")));
		btnClinic.setToolTipText("Módulo clínico");
		btnClinic.addMouseListener(new MouseListener() {
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
				btnClinic.setBackground(new Color(148, 220, 219));
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				btnClinic.setBackground(new Color(31, 192, 191));
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub

			}
		});

		// Botón del Módulo economico
		JButton btnPayments = new JButton();
		btnPayments.setBackground(new Color(148, 220, 219));
		btnPayments.setBounds(0, 810, 135, 135);
		btnPayments.setBorderPainted(false);
		btnPayments.setIcon(new ImageIcon(getClass().getResource("/Resources/images/paymentsGrey.png")));
		btnPayments.setToolTipText("Módulo Económico");
		btnPayments.addMouseListener(new MouseListener() {
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
				btnPayments.setBackground(new Color(148, 220, 219));
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				btnPayments.setBackground(new Color(31, 192, 191));
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
		
		// Acción del Módulo de citas
		btnAppointment.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AdminAppointment admAppointment = new AdminAppointment();
				admAppointment.setVisible(true);
				dispose();
			}
		});
		
		// Acción del Módulo de usuarios
		btnUsers.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AdminUsers admUsers = new AdminUsers();
				admUsers.setVisible(true);
				dispose();
			}
		});

		// Acción del Módulo de pacientes
		btnCustomers.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AdminCustomers admCustomers = new AdminCustomers();
				admCustomers.setVisible(true);
				dispose();
			}
		});

		// Acción del Módulo de la clínica
		btnClinic.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AdminClinic admClinic = new AdminClinic();
				admClinic.setVisible(true);
				dispose();
			}
		});

		// Acción del Módulo económico
		btnPayments.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AdminPayments admPayments = new AdminPayments();
				admPayments.setVisible(true);
				dispose();
			}
		});

		// -------------------- Adiciones a los paneles --------------------
		contentPane.add(menuPane);
		menuPane.add(lblLogo);
		menuPane.add(btnAppointment);
		menuPane.add(btnUsers);
		menuPane.add(btnCustomers);
		menuPane.add(btnStock);
		menuPane.add(btnClinic);
		menuPane.add(btnPayments);
		menuPane.add(btnClose);
	}

}
