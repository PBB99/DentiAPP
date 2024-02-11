package Vista;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import org.hibernate.Query;
import org.hibernate.Session;

import Modelo.ClienteHibernate;
import Modelo.OdontogramaHibernate;
import Vista.AdminCustomers.Renderer;
import btndentiapp.ButtonDentiApp;

import javax.swing.JLabel;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;

import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;

public class CustommerOdont extends JDialog {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
	private JTable tableHis;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
//				try {
//					CustommerOdont frame = new CustommerOdont();
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
	public CustommerOdont(ClienteHibernate cliente, Session session, Boolean admin) {
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		// --------------ELEMENTOS-------------

		setBounds(100, 100, 1440, 810);

		setModal(true);
		setResizable(false);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setBackground(new Color(238, 238, 238));

		setContentPane(contentPane);
		contentPane.setLayout(null);
//---------COMPONENTE GRAFICO-----
		// TABLA
		table = new JTable();
		table.setShowVerticalLines(false);
		table.setShowHorizontalLines(false);
		table.setCellSelectionEnabled(true);
		table.setBackground(new Color(250, 250, 250));
		table.setSelectionBackground(new Color(148, 220, 219));
		table.setShowGrid(false);
		table.setBorder(null);
		table.setFont(new Font("Tahoma", Font.PLAIN, 18));
		table.setRowHeight(35);
		table.getTableHeader().setFont(new Font("Tahoma", Font.PLAIN, 18));
		table.getTableHeader().setBackground(new Color(148, 220, 219));
		table.getTableHeader().setBorder(new LineBorder(new Color(148, 220, 219)));

		table.setBounds(883, 70, 506, 608);
		// Label historial

		JLabel lblHistorial = new JLabel("Historial");
		lblHistorial.setBounds(883, -40, 6000, 150);
		Font font = lblHistorial.getFont();
		Font fuenteNueva = new Font(font.getFontName(), Font.BOLD, 33);
		lblHistorial.setFont(fuenteNueva);

		// Panel del Menú
		JPanel menuPane = new JPanel();
		menuPane.setBackground(new Color(148, 220, 219));
		menuPane.setBounds(0, 0, 135, 1080);

		menuPane.setLayout(null);

		// Label del Logo del Menú
		JLabel lblLogo = new JLabel();
		lblLogo.setBounds(0, 0, 135, 135);
		lblLogo.setIcon(new ImageIcon(CustommerOdont.class.getResource("/Resources/images/logoMenu.png")));
		// Panel odontograma
		JPanel panelOdonto = new JPanel();
		panelOdonto.setBounds(174, 70, 579, 650);

		panelOdonto.setLayout(null);

		// fondo del panel de odontograma
		JLabel lFondo = new JLabel("");
		lFondo.setIcon(new ImageIcon(CustommerOdont.class.getResource("/Resources/images/odontograma.png")));
		lFondo.setBounds(-161, 0, 718, 645);
		// ----------------DIENTES-----------------------
		JButton bDiente17 = new JButton("");
		bDiente17.setToolTipText("Diente 17");
		// bDiente17.setBorderPainted(false);
		bDiente17.setBorder(null);
		bDiente17.setBackground(new Color(128, 64, 0));
		bDiente17.setBounds(46, 138, 90, 123);
		bDiente17.setContentAreaFilled(false);
		bDiente17.setBackground(null);
		bDiente17.setOpaque(false);
		bDiente17.setIcon(new ImageIcon(CustommerOdont.class.getResource("/Resources/images/diente_1.png")));

		JButton bDiente47 = new JButton("");
		bDiente47.setToolTipText("Diente 47");
		bDiente47.setIcon(new ImageIcon(CustommerOdont.class.getResource("/Resources/images/Diente_6.png")));
		bDiente47.setBounds(47, 410, 89, 93);
		bDiente47.setBorderPainted(false);
		bDiente47.setContentAreaFilled(false);
		bDiente47.setFocusPainted(false);
		bDiente47.setOpaque(false);

		JButton bDiente21 = new JButton("");
		bDiente21.setToolTipText("Diente 21");
		bDiente21.setBounds(189, 0, 96, 98);
		bDiente21.setBorderPainted(false);
		bDiente21.setContentAreaFilled(false);
		bDiente21.setFocusPainted(false);
		bDiente21.setOpaque(false);
		bDiente21.setIcon(new ImageIcon(CustommerOdont.class.getResource("/Resources/images/Diente_5.png")));

		JButton bDiente14 = new JButton("");
		bDiente14.setToolTipText("Diente 14");

		bDiente14.setBackground(new Color(128, 64, 0));
		bDiente14.setBounds(85, 42, 98, 84);
		bDiente14.setBorderPainted(false);
		bDiente14.setContentAreaFilled(false);
		bDiente14.setFocusPainted(false);
		bDiente14.setOpaque(false);
		bDiente14.setIcon(new ImageIcon(CustommerOdont.class.getResource("/Resources/images/diente_2.png")));

		JButton bDiente25 = new JButton("");
		bDiente25.setToolTipText("Diente 25");
		bDiente25.setIcon(new ImageIcon("/Resources/images/Diente_4.png"));

		bDiente25.setBounds(269, 72, 98, 84);
		bDiente25.setBorderPainted(false);
		bDiente25.setContentAreaFilled(false);
		bDiente25.setFocusPainted(false);
		bDiente25.setOpaque(false);

		JButton bDiente27 = new JButton("");
		bDiente27.setToolTipText("Diente 27");
		bDiente27.setIcon(new ImageIcon(CustommerOdont.class.getResource("/Resources/images/Diente3.png")));
		bDiente27.setBounds(297, 151, 89, 98);
		bDiente27.setBorderPainted(false);
		bDiente27.setContentAreaFilled(false);
		bDiente27.setFocusPainted(false);
		bDiente27.setOpaque(false);

		JButton bDiente35 = new JButton("");
		bDiente35.setToolTipText("Diente 35");
		bDiente35.setIcon(new ImageIcon(CustommerOdont.class.getResource("/Resources/images/Diente_9.png")));
		bDiente35.setBounds(269, 490, 98, 98);
		bDiente35.setBorderPainted(false);
		bDiente35.setContentAreaFilled(false);
		bDiente35.setFocusPainted(false);
		bDiente35.setOpaque(false);

		JButton bDiente45 = new JButton("");
		bDiente45.setToolTipText("Diente 45");
		bDiente45.setIcon(new ImageIcon(CustommerOdont.class.getResource("/Resources/images/Diente_7.png")));
		bDiente45.setBounds(70, 479, 89, 123);
		bDiente45.setBorderPainted(false);
		bDiente45.setContentAreaFilled(false);
		bDiente45.setFocusPainted(false);
		bDiente45.setOpaque(false);

		JButton bDiente41 = new JButton("");
		bDiente41.setToolTipText("Diente 41");
		bDiente41.setIcon(new ImageIcon(CustommerOdont.class.getResource("/Resources/images/Diente_10.png")));
		bDiente41.setBounds(151, 545, 103, 113);
		bDiente41.setBorderPainted(false);
		bDiente41.setContentAreaFilled(false);
		bDiente41.setFocusPainted(false);
		bDiente41.setOpaque(false);

		JButton bDiente37 = new JButton("");
		bDiente37.setToolTipText("Diente 37");
		bDiente37.setIcon(new ImageIcon(CustommerOdont.class.getResource("/Resources/images/Diente_8.png")));
		bDiente37.setBounds(298, 410, 89, 93);
		bDiente37.setBorderPainted(false);
		bDiente37.setContentAreaFilled(false);
		bDiente37.setFocusPainted(false);
		bDiente37.setOpaque(false);

		// panel donde iran los tabs de historial e inserccion de tratamiento
		JPanel Historial_Insertar = new JPanel();
		Historial_Insertar.setBounds(883, 70, 506, 608);
		// elemento para tener dos pestañas en el panel
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(0, 0, 506, 608);

		JScrollPane menuTableStock = new JScrollPane();
		menuTableStock.setBorder(BorderFactory.createEmptyBorder());
		menuTableStock.setBounds(883, 70, 506, 608);
		menuTableStock.setBackground(new Color(148, 220, 219));

		// ----------------Logica-------------

		bDiente17.addMouseListener(new MouseListener() {

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
				// TODO Auto-generated method stub
				bDiente17.setIcon(new ImageIcon(CustommerOdont.class.getResource("/Resources/images/diente_1.png")));
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				bDiente17.setIcon(
						new ImageIcon(CustommerOdont.class.getResource("/Resources/images/diente_1_verde.png")));

			}

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub

			}
		});

		bDiente17.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (tabbedPane.getTabCount() > 0) {
					// No hay pestañas en el JTabbedPane

					tabbedPane.removeAll();
				}
//				
				Internal_Historial ih = new Internal_Historial(17, cliente, session);
				Insertar_Trat_Diente it = new Insertar_Trat_Diente(17, cliente, session);

				// Crear paneles para contener los JDialog

				if (admin == false) {
					JPanel panelTratamiento = new JPanel(new BorderLayout());
					panelTratamiento.add(it.getContentPane(), BorderLayout.CENTER);
					panelTratamiento.setBounds(Historial_Insertar.getBounds());
					// Agregar los paneles al JTabbedPane

					tabbedPane.addTab("Tratamiento", panelTratamiento);

					// Mostrar los JDialog después de agregar los paneles al JTabbedPane

					it.pack();
					it.setVisible(true);

					JPanel panelHistorial = new JPanel(new BorderLayout());
					panelHistorial.add(ih.getContentPane(), BorderLayout.CENTER);
					panelHistorial.setBounds(Historial_Insertar.getBounds());
					tabbedPane.addTab("Historial", panelHistorial);
					ih.pack();
					ih.setVisible(true);
				} else {

					loadSearch(table, cliente, 17, session);
				}

			}
		});

		bDiente47.addMouseListener(new MouseListener() {

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
				// TODO Auto-generated method stub
				bDiente47.setIcon(new ImageIcon(CustommerOdont.class.getResource("/Resources/images/Diente_6.png")));

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				bDiente47.setIcon(
						new ImageIcon(CustommerOdont.class.getResource("/Resources/images/Diente_6_verde.png")));

			}

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub

			}
		});

		bDiente47.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (tabbedPane.getTabCount() > 0) {
					// No hay pestañas en el JTabbedPane

					tabbedPane.removeAll();
				}
//				
				Internal_Historial ih = new Internal_Historial(47, cliente, session);
				Insertar_Trat_Diente it = new Insertar_Trat_Diente(47, cliente, session);

				// Crear paneles para contener los JDialog

				if (admin == false) {
					JPanel panelTratamiento = new JPanel(new BorderLayout());
					panelTratamiento.add(it.getContentPane(), BorderLayout.CENTER);
					panelTratamiento.setBounds(Historial_Insertar.getBounds());
					// Agregar los paneles al JTabbedPane

					tabbedPane.addTab("Tratamiento", panelTratamiento);

					// Mostrar los JDialog después de agregar los paneles al JTabbedPane

					it.pack();
					it.setVisible(true);

					JPanel panelHistorial = new JPanel(new BorderLayout());
					panelHistorial.add(ih.getContentPane(), BorderLayout.CENTER);
					panelHistorial.setBounds(Historial_Insertar.getBounds());
					tabbedPane.addTab("Historial", panelHistorial);
					ih.pack();
					ih.setVisible(true);
				} else {

					loadSearch(table, cliente, 47, session);
				}

			}
		});

		bDiente21.addMouseListener(new MouseListener() {

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
				// TODO Auto-generated method stub
				bDiente21.setIcon(new ImageIcon(CustommerOdont.class.getResource("/Resources/images/Diente_5.png")));
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				bDiente21.setIcon(
						new ImageIcon(CustommerOdont.class.getResource("/Resources/images/Diente_5_verde.png")));

			}

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub

			}
		});

		bDiente21.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (tabbedPane.getTabCount() > 0) {
					// No hay pestañas en el JTabbedPane

					tabbedPane.removeAll();
				}
//				
				Internal_Historial ih = new Internal_Historial(21, cliente, session);
				Insertar_Trat_Diente it = new Insertar_Trat_Diente(21, cliente, session);

				// Crear paneles para contener los JDialog

				if (admin == false) {
					JPanel panelTratamiento = new JPanel(new BorderLayout());
					panelTratamiento.add(it.getContentPane(), BorderLayout.CENTER);
					panelTratamiento.setBounds(Historial_Insertar.getBounds());
					// Agregar los paneles al JTabbedPane

					tabbedPane.addTab("Tratamiento", panelTratamiento);

					// Mostrar los JDialog después de agregar los paneles al JTabbedPane

					it.pack();
					it.setVisible(true);

					JPanel panelHistorial = new JPanel(new BorderLayout());
					panelHistorial.add(ih.getContentPane(), BorderLayout.CENTER);
					panelHistorial.setBounds(Historial_Insertar.getBounds());
					tabbedPane.addTab("Historial", panelHistorial);
					ih.pack();
					ih.setVisible(true);
				} else {

					loadSearch(table, cliente, 21, session);
				}

			}
		});
		bDiente14.addMouseListener(new MouseListener() {

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
				// TODO Auto-generated method stub
				bDiente14.setIcon(new ImageIcon(CustommerOdont.class.getResource("/Resources/images/diente_2.png")));

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				bDiente14.setIcon(
						new ImageIcon(CustommerOdont.class.getResource("/Resources/images/diente_2_verde.png")));
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub

			}
		});
		bDiente14.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (tabbedPane.getTabCount() > 0) {
					// No hay pestañas en el JTabbedPane

					tabbedPane.removeAll();
				}
//				
				Internal_Historial ih = new Internal_Historial(14, cliente, session);
				Insertar_Trat_Diente it = new Insertar_Trat_Diente(14, cliente, session);

				// Crear paneles para contener los JDialog

				if (admin == false) {
					JPanel panelTratamiento = new JPanel(new BorderLayout());
					panelTratamiento.add(it.getContentPane(), BorderLayout.CENTER);
					panelTratamiento.setBounds(Historial_Insertar.getBounds());
					// Agregar los paneles al JTabbedPane

					tabbedPane.addTab("Tratamiento", panelTratamiento);

					// Mostrar los JDialog después de agregar los paneles al JTabbedPane

					it.pack();
					it.setVisible(true);

					JPanel panelHistorial = new JPanel(new BorderLayout());
					panelHistorial.add(ih.getContentPane(), BorderLayout.CENTER);
					panelHistorial.setBounds(Historial_Insertar.getBounds());
					tabbedPane.addTab("Historial", panelHistorial);
					ih.pack();
					ih.setVisible(true);
				} else {

					loadSearch(table, cliente, 14, session);
				}

			}
		});

		bDiente25.addMouseListener(new MouseListener() {

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
				// TODO Auto-generated method stub
				bDiente25.setIcon(new ImageIcon(CustommerOdont.class.getResource("/Resources/images/Diente_4.png")));

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				bDiente25.setIcon(
						new ImageIcon(CustommerOdont.class.getResource("/Resources/images/Diente_4_verde.png")));

			}

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub

			}
		});
		bDiente25.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (tabbedPane.getTabCount() > 0) {
					// No hay pestañas en el JTabbedPane

					tabbedPane.removeAll();
				}
//				
				Internal_Historial ih = new Internal_Historial(25, cliente, session);
				Insertar_Trat_Diente it = new Insertar_Trat_Diente(25, cliente, session);

				// Crear paneles para contener los JDialog

				if (admin == false) {
					JPanel panelTratamiento = new JPanel(new BorderLayout());
					panelTratamiento.add(it.getContentPane(), BorderLayout.CENTER);
					panelTratamiento.setBounds(Historial_Insertar.getBounds());
					// Agregar los paneles al JTabbedPane

					tabbedPane.addTab("Tratamiento", panelTratamiento);

					// Mostrar los JDialog después de agregar los paneles al JTabbedPane

					it.pack();
					it.setVisible(true);

					JPanel panelHistorial = new JPanel(new BorderLayout());
					panelHistorial.add(ih.getContentPane(), BorderLayout.CENTER);
					panelHistorial.setBounds(Historial_Insertar.getBounds());
					tabbedPane.addTab("Historial", panelHistorial);
					ih.pack();
					ih.setVisible(true);
				} else {

					loadSearch(table, cliente, 25, session);
				}

			}
		});

		bDiente27.addMouseListener(new MouseListener() {

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
				// TODO Auto-generated method stub
				bDiente27.setIcon(new ImageIcon(CustommerOdont.class.getResource("/Resources/images/Diente3.png")));

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				bDiente27.setIcon(
						new ImageIcon(CustommerOdont.class.getResource("/Resources/images/Diente3_verde.png")));

			}

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub

			}
		});

		bDiente27.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (tabbedPane.getTabCount() > 0) {
					// No hay pestañas en el JTabbedPane

					tabbedPane.removeAll();
				}
//				
				Internal_Historial ih = new Internal_Historial(27, cliente, session);
				Insertar_Trat_Diente it = new Insertar_Trat_Diente(27, cliente, session);

				// Crear paneles para contener los JDialog
				if (admin == false) {
					JPanel panelTratamiento = new JPanel(new BorderLayout());
					panelTratamiento.add(it.getContentPane(), BorderLayout.CENTER);
					panelTratamiento.setBounds(Historial_Insertar.getBounds());
					// Agregar los paneles al JTabbedPane

					tabbedPane.addTab("Tratamiento", panelTratamiento);

					// Mostrar los JDialog después de agregar los paneles al JTabbedPane

					it.pack();
					it.setVisible(true);

					JPanel panelHistorial = new JPanel(new BorderLayout());
					panelHistorial.add(ih.getContentPane(), BorderLayout.CENTER);
					panelHistorial.setBounds(Historial_Insertar.getBounds());
					tabbedPane.addTab("Historial", panelHistorial);
					ih.pack();
					ih.setVisible(true);
				} else {

					loadSearch(table, cliente, 27, session);
				}

			}
		});

		bDiente35.addMouseListener(new MouseListener() {

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
				// TODO Auto-generated method stub
				bDiente35.setIcon(new ImageIcon(CustommerOdont.class.getResource("/Resources/images/Diente_9.png")));

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				bDiente35.setIcon(
						new ImageIcon(CustommerOdont.class.getResource("/Resources/images/Diente_9_verde.png")));

			}

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub

			}
		});

		bDiente35.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (tabbedPane.getTabCount() > 0) {
					// No hay pestañas en el JTabbedPane

					tabbedPane.removeAll();
				}
//				
				Internal_Historial ih = new Internal_Historial(35, cliente, session);
				Insertar_Trat_Diente it = new Insertar_Trat_Diente(35, cliente, session);

				// Crear paneles para contener los JDialog

				if (admin == false) {
					JPanel panelTratamiento = new JPanel(new BorderLayout());
					panelTratamiento.add(it.getContentPane(), BorderLayout.CENTER);
					panelTratamiento.setBounds(Historial_Insertar.getBounds());
					// Agregar los paneles al JTabbedPane

					tabbedPane.addTab("Tratamiento", panelTratamiento);

					// Mostrar los JDialog después de agregar los paneles al JTabbedPane

					it.pack();
					it.setVisible(true);

					JPanel panelHistorial = new JPanel(new BorderLayout());
					panelHistorial.add(ih.getContentPane(), BorderLayout.CENTER);
					panelHistorial.setBounds(Historial_Insertar.getBounds());
					tabbedPane.addTab("Historial", panelHistorial);
					ih.pack();
					ih.setVisible(true);
				} else {

					loadSearch(table, cliente, 35, session);
				}

			}
		});

		bDiente45.addMouseListener(new MouseListener() {

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
				// TODO Auto-generated method stub
				bDiente45.setIcon(new ImageIcon(CustommerOdont.class.getResource("/Resources/images/Diente_7.png")));

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				bDiente45.setIcon(
						new ImageIcon(CustommerOdont.class.getResource("/Resources/images/Diente_7_verde.png")));

			}

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub

			}
		});

		bDiente45.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (tabbedPane.getTabCount() > 0) {
					// No hay pestañas en el JTabbedPane

					tabbedPane.removeAll();
				}
//				
				Internal_Historial ih = new Internal_Historial(45, cliente, session);
				Insertar_Trat_Diente it = new Insertar_Trat_Diente(45, cliente, session);

				if (admin == false) {
					JPanel panelTratamiento = new JPanel(new BorderLayout());
					panelTratamiento.add(it.getContentPane(), BorderLayout.CENTER);
					panelTratamiento.setBounds(Historial_Insertar.getBounds());
					// Agregar los paneles al JTabbedPane

					tabbedPane.addTab("Tratamiento", panelTratamiento);

					// Mostrar los JDialog después de agregar los paneles al JTabbedPane

					it.pack();
					it.setVisible(true);

					JPanel panelHistorial = new JPanel(new BorderLayout());
					panelHistorial.add(ih.getContentPane(), BorderLayout.CENTER);
					panelHistorial.setBounds(Historial_Insertar.getBounds());
					tabbedPane.addTab("Historial", panelHistorial);
					ih.pack();
					ih.setVisible(true);
				} else {

					loadSearch(table, cliente, 45, session);
				}

			}
		});
		bDiente41.addMouseListener(new MouseListener() {

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
				// TODO Auto-generated method stub
				bDiente41.setIcon(new ImageIcon("/Resources/images/Diente_10.png"));

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				bDiente41.setIcon(
						new ImageIcon(CustommerOdont.class.getResource("/Resources/images/Diente_10_verde.png")));

			}

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub

			}
		});
		bDiente41.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				if (tabbedPane.getTabCount() > 0) {
					// No hay pestañas en el JTabbedPane

					tabbedPane.removeAll();
				}
//				
				Internal_Historial ih = new Internal_Historial(41, cliente, session);
				Insertar_Trat_Diente it = new Insertar_Trat_Diente(41, cliente, session);

				// Crear paneles para contener los JDialog
				if (admin == false) {
					JPanel panelTratamiento = new JPanel(new BorderLayout());
					panelTratamiento.add(it.getContentPane(), BorderLayout.CENTER);
					panelTratamiento.setBounds(Historial_Insertar.getBounds());
					// Agregar los paneles al JTabbedPane

					tabbedPane.addTab("Tratamiento", panelTratamiento);

					// Mostrar los JDialog después de agregar los paneles al JTabbedPane

					it.pack();
					it.setVisible(true);

					JPanel panelHistorial = new JPanel(new BorderLayout());
					panelHistorial.add(ih.getContentPane(), BorderLayout.CENTER);
					panelHistorial.setBounds(Historial_Insertar.getBounds());
					tabbedPane.addTab("Historial", panelHistorial);
					ih.pack();
					ih.setVisible(true);
				} else {

					loadSearch(table, cliente, 41, session);
				}

			}
		});

		bDiente37.addMouseListener(new MouseListener() {

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
				// TODO Auto-generated method stub
				bDiente37.setIcon(new ImageIcon(CustommerOdont.class.getResource("/Resources/images/Diente_8.png")));

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				bDiente37.setIcon(
						new ImageIcon(CustommerOdont.class.getResource("/Resources/images/Diente_8_verde.png")));

			}

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub

			}
		});

		bDiente37.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				if (tabbedPane.getTabCount() > 0) {
					// No hay pestañas en el JTabbedPane

					tabbedPane.removeAll();
				}
//				
				Internal_Historial ih = new Internal_Historial(37, cliente, session);
				Insertar_Trat_Diente it = new Insertar_Trat_Diente(37, cliente, session);

				if (admin == false) {
					JPanel panelTratamiento = new JPanel(new BorderLayout());
					panelTratamiento.add(it.getContentPane(), BorderLayout.CENTER);
					panelTratamiento.setBounds(Historial_Insertar.getBounds());
					// Agregar los paneles al JTabbedPane

					tabbedPane.addTab("Tratamiento", panelTratamiento);

					// Mostrar los JDialog después de agregar los paneles al JTabbedPane

					it.pack();
					it.setVisible(true);

					JPanel panelHistorial = new JPanel(new BorderLayout());
					panelHistorial.add(ih.getContentPane(), BorderLayout.CENTER);
					panelHistorial.setBounds(Historial_Insertar.getBounds());
					tabbedPane.addTab("Historial", panelHistorial);
					ih.pack();
					ih.setVisible(true);
				} else {

					loadSearch(table, cliente, 37, session);
				}

			}
		});

		// ---------------ADICIONES-----------------
		if (admin) {

			contentPane.add(lblHistorial);
		}

		JLabel lblDiente21 = new JLabel("21");
		lblDiente21.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblDiente21.setBounds(222, 6, 46, 24);
		panelOdonto.add(lblDiente21);
		panelOdonto.add(bDiente37);
		panelOdonto.add(bDiente21);
		panelOdonto.add(bDiente27);
		panelOdonto.add(bDiente14);
		panelOdonto.add(bDiente47);
		panelOdonto.add(bDiente25);
		panelOdonto.add(bDiente17);
		contentPane.add(panelOdonto);
		panelOdonto.add(lFondo);
		contentPane.add(menuPane);
		menuPane.add(lblLogo);
		Historial_Insertar.setLayout(null);
		Historial_Insertar.add(tabbedPane);
		contentPane.add(Historial_Insertar);
		panelOdonto.add(bDiente41);
		panelOdonto.add(bDiente45);
		panelOdonto.add(bDiente35);

		JLabel lblDiente17 = new JLabel("17");
		lblDiente17.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblDiente17.setBounds(38, 170, 46, 14);
		panelOdonto.add(lblDiente17);

		JLabel lblDiente14 = new JLabel("14");
		lblDiente14.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblDiente14.setBounds(90, 42, 46, 14);
		panelOdonto.add(lblDiente14);

		JLabel lblDiente25 = new JLabel("25");
		lblDiente25.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblDiente25.setBounds(350, 95, 46, 14);
		panelOdonto.add(lblDiente25);

		JLabel lblDiente27 = new JLabel("27");
		lblDiente27.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblDiente27.setBounds(381, 189, 46, 14);
		panelOdonto.add(lblDiente27);

		JLabel lblDiente41 = new JLabel("41");
		lblDiente41.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblDiente41.setBounds(189, 629, 46, 14);
		panelOdonto.add(lblDiente41);

		JLabel lblDiente37 = new JLabel("37");
		lblDiente37.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblDiente37.setBounds(381, 453, 46, 14);
		panelOdonto.add(lblDiente37);

		JLabel lblDiente35 = new JLabel("35");
		lblDiente35.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblDiente35.setBounds(350, 541, 46, 14);
		panelOdonto.add(lblDiente35);

		JLabel lblDiente45 = new JLabel("45");
		lblDiente45.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblDiente45.setBounds(38, 544, 46, 14);
		panelOdonto.add(lblDiente45);

		JLabel lblDiente47 = new JLabel("47");
		lblDiente47.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblDiente47.setBounds(22, 453, 46, 14);
		panelOdonto.add(lblDiente47);
		JLabel lblCliente = new JLabel("CLIENTE: " + cliente.getNombre() + " " + cliente.getApellidos());
		lblCliente.setFont(new Font("Tahoma", Font.PLAIN, 22));
		lblCliente.setBounds(174, 24, 386, 30);
		contentPane.add(lblCliente);
		contentPane.add(menuTableStock);
		menuTableStock.add(table);
		menuTableStock.setViewportView(table);

	}

	public void loadSearch(JTable tabla, ClienteHibernate cliente, int diente, Session session) {
		// Relaiza la consulta
		table.setOpaque(true);
		String hql = "From OdontogramaHibernate where id_diente=:id_diente and clientes_dni_cliente=:dni_cliente";
		Query<OdontogramaHibernate> consulta = session.createQuery(hql, OdontogramaHibernate.class);
		consulta.setParameter("id_diente", diente);
		consulta.setParameter("dni_cliente", cliente.getDni_cliente());

		// Guarda los datos en una lista
		List<OdontogramaHibernate> odonList = consulta.getResultList();

		// Prepara la tabla
		DefaultTableModel model = new DefaultTableModel(new Object[][] {},
				new String[] { "DNI", "Diente", "Fecha", "Observaciones" }) {
			@Override
			public boolean isCellEditable(int row, int column) {
				// all cells false
				return false;
			}
		};

		tabla.setModel(model);
		model.setRowCount(1);

		JTableHeader header = tabla.getTableHeader();
		if (odonList.size() < 19) {
			model.setRowCount(18);
		} else {
			model.setRowCount(odonList.size() + 1);
		}
		int fila = 0, columna = 0;

		// Carga los datos
		for (OdontogramaHibernate x : odonList) {
			System.out.println("aa");
			model.setValueAt(x.getCliente().getDni_cliente(), fila, 0);
			model.setValueAt(x.getId_diente(), fila, 1);
			model.setValueAt(x.getObservaciones(), fila, 3);
			model.setValueAt(x.getFecha(), fila, 2);
			fila++;
		}

		// Se alinea el texto de las columnas
		Renderer tcr = new Renderer();
		tcr.setHorizontalAlignment(SwingConstants.CENTER);
		tabla.getColumnModel().getColumn(0).setCellRenderer(tcr);
		tabla.setDefaultRenderer(Object.class, tcr);

	}

	public class Renderer extends DefaultTableCellRenderer {

		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {

			// Evalua en que fila esta

			if (row % 2 == 0) {
				setBackground(new Color(220, 220, 220));
			} else {
				setBackground(new Color(250, 250, 250));
			}

			return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		}

	}
}
