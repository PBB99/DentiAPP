package Vista;
import java.awt.EventQueue;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import btndentiapp.ButtonDentiApp;

import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;

import java.awt.Color;

public class CustommerOdont extends JDialog {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CustommerOdont frame = new CustommerOdont();
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
	public CustommerOdont() {
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		// --------------ELEMENTOS-------------
	
		setBounds(100, 100, 1440, 810);
		
		setModal(true);
		setResizable(false);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setBackground(new Color(238,238,238));
	
		setContentPane(contentPane);
		contentPane.setLayout(null);
//---------COMPONENTE GRAFICO-----
		
		// Panel del Menú
				JPanel menuPane = new JPanel();
				menuPane.setBackground(new Color(148, 220, 219));
				menuPane.setBounds(0, 0, 135, 1080);
				
				menuPane.setLayout(null);

				// Label del Logo del Menú
				JLabel lblLogo = new JLabel();
				// lblLogo.setBorder(new LineBorder(new Color(0, 0, 0)));
				lblLogo.setBounds(0, 0, 135, 135);
				lblLogo.setIcon(new ImageIcon(CustommerOdont.class.getResource("/Resources/images/logoMenu.png")));

				
				
		JPanel panel = new JPanel();
		panel.setBounds(174, 70, 579, 608);

		panel.setLayout(null);
		
		JLabel lFondo = new JLabel("");
		lFondo.setIcon(new ImageIcon(CustommerOdont.class.getResource("/Resources/images/odontograma.png")));
		lFondo.setBounds(-161, 0, 718, 603);
		
		

		JButton bDiente17 = new JButton("");
		bDiente17.setBorderPainted(false);
		bDiente17.setBackground(new Color(128, 64, 0));
		bDiente17.setBounds(46, 138, 90, 81);
		bDiente17.setContentAreaFilled(false);
		bDiente17.setBackground(null);
		bDiente17.setOpaque(false);
		bDiente17.setIcon(new ImageIcon(CustommerOdont.class.getResource("/Resources/images/diente_1.png")));

		JButton bDiente47 = new JButton("");
		bDiente47.setIcon(new ImageIcon(CustommerOdont.class.getResource("/Resources/images/Diente_6.png")));
		bDiente47.setBounds(47, 410, 89, 51);
		bDiente47.setBorderPainted(false);
		bDiente47.setContentAreaFilled(false);
		bDiente47.setFocusPainted(false);
		bDiente47.setOpaque(false);

		JButton bDiente11 = new JButton("");
		bDiente11.setBounds(189, 0, 96, 56);
		bDiente11.setBorderPainted(false);
		bDiente11.setContentAreaFilled(false);
		bDiente11.setFocusPainted(false);
		bDiente11.setOpaque(false);
		bDiente11.setIcon(new ImageIcon(CustommerOdont.class.getResource("/Resources/images/Diente_5.png")));

		JButton bDiente15 = new JButton("");

		bDiente15.setBackground(new Color(128, 64, 0));
		bDiente15.setBounds(85, 42, 98, 42);
		bDiente15.setBorderPainted(false);
		bDiente15.setContentAreaFilled(false);
		bDiente15.setFocusPainted(false);
		bDiente15.setOpaque(false);
		bDiente15.setIcon(new ImageIcon(CustommerOdont.class.getResource("/Resources/images/diente_2.png")));

		JButton bDiente25 = new JButton("");
		bDiente25.setIcon(new ImageIcon("/Resources/images/Diente_4.png"));

		bDiente25.setBounds(269, 72, 98, 42);
		bDiente25.setBorderPainted(false);
		bDiente25.setContentAreaFilled(false);
		bDiente25.setFocusPainted(false);
		bDiente25.setOpaque(false);

		

		JButton bDiente27 = new JButton("");
		bDiente27.setIcon(new ImageIcon(CustommerOdont.class.getResource("/Resources/images/Diente3.png")));
		bDiente27.setBounds(297, 151, 89, 56);
		bDiente27.setBorderPainted(false);
		bDiente27.setContentAreaFilled(false);
		bDiente27.setFocusPainted(false);
		bDiente27.setOpaque(false);
		
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
				bDiente17.setIcon(new ImageIcon(CustommerOdont.class.getResource("/Resources/images/diente_1_verde.png")));

			}

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub

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
				bDiente47.setIcon(new ImageIcon(CustommerOdont.class.getResource("/Resources/images/Diente_6_verde.png")));

			}

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub

			}
		});

		bDiente11.addMouseListener(new MouseListener() {

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
				bDiente11.setIcon(new ImageIcon(CustommerOdont.class.getResource("/Resources/images/Diente_5.png")));
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				bDiente11.setIcon(new ImageIcon(CustommerOdont.class.getResource("/Resources/images/Diente_5_verde.png")));

			}

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub

			}
		});

		

		bDiente15.addMouseListener(new MouseListener() {

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
				bDiente15.setIcon(new ImageIcon(CustommerOdont.class.getResource("/Resources/images/diente_2.png")));

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				bDiente15.setIcon(new ImageIcon(CustommerOdont.class.getResource("/Resources/images/diente_2_verde.png")));
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub

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
				bDiente25.setIcon(new ImageIcon(CustommerOdont.class.getResource("/Resources/images/Diente_4_verde.png")));

			}

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub

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
				bDiente27.setIcon(new ImageIcon(CustommerOdont.class.getResource("/Resources/images/Diente3_verde.png")));

			}

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub

			}
		});
		
		JButton bDiente35 = new JButton("");
		bDiente35.setIcon(new ImageIcon(CustommerOdont.class.getResource("/Resources/images/Diente_9.png")));
		bDiente35.setBounds(269, 490, 98, 56);
		bDiente35.setBorderPainted(false);
		bDiente35.setContentAreaFilled(false);
		bDiente35.setFocusPainted(false);
		bDiente35.setOpaque(false);
		
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
				bDiente35.setIcon(new ImageIcon(CustommerOdont.class.getResource("/Resources/images/Diente_9_verde.png")));
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
				JButton bDiente37 = new JButton("");
				bDiente37.setIcon(new ImageIcon(CustommerOdont.class.getResource("/Resources/images/Diente_8.png")));
				bDiente37.setBounds(298, 410, 89, 51);
				bDiente37.setBorderPainted(false);
				bDiente37.setContentAreaFilled(false);
				bDiente37.setFocusPainted(false);
				bDiente37.setOpaque(false);
				
						
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
								bDiente37.setIcon(new ImageIcon(CustommerOdont.class.getResource("/Resources/images/Diente_8_verde.png")));
				
							}
				
							@Override
							public void mouseClicked(MouseEvent e) {
								// TODO Auto-generated method stub
				
							}
						});
						
						JButton bDiente45 = new JButton("");
						bDiente45.setIcon(new ImageIcon(CustommerOdont.class.getResource("/Resources/images/Diente_7.png")));
						bDiente45.setBounds(70, 479, 89, 81);
						bDiente45.setBorderPainted(false);
						bDiente45.setContentAreaFilled(false);
						bDiente45.setFocusPainted(false);
						bDiente45.setOpaque(false);
						
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
								bDiente45.setIcon(new ImageIcon(CustommerOdont.class.getResource("/Resources/images/Diente_7_verde.png")));
								
							}
							
							@Override
							public void mouseClicked(MouseEvent e) {
								// TODO Auto-generated method stub
								
							}
						});
						
						JButton bDiente41 = new JButton("");
						bDiente41.setIcon(new ImageIcon(CustommerOdont.class.getResource("/Resources/images/Diente_10.png")));
						bDiente41.setBounds(151, 545, 103, 71);
						bDiente41.setBorderPainted(false);
						bDiente41.setContentAreaFilled(false);
						bDiente41.setFocusPainted(false);
						bDiente41.setOpaque(false);
						
						// ----------------Logica-------------
						
						
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
								bDiente41.setIcon(new ImageIcon(CustommerOdont.class.getResource("/Resources/images/Diente_10_verde.png")));
								
							}
							
							@Override
							public void mouseClicked(MouseEvent e) {
								// TODO Auto-generated method stub
								
							}
						});
						panel.add(bDiente41);
						panel.add(bDiente45);
						panel.add(bDiente37);
		panel.add(bDiente35);
		
		
		
		
	
		
		
		
		

		// ---------------ADICIONES-----------------
		panel.add(bDiente11);
		panel.add(bDiente27);
		panel.add(bDiente15);
		panel.add(bDiente47);
		panel.add(bDiente25);
		panel.add(bDiente17);
		contentPane.add(panel);
		panel.add(lFondo);
		contentPane.add(menuPane);
		menuPane.add(lblLogo);
		
		JPanel Historial_Insertar = new JPanel();
		Historial_Insertar.setBounds(883, 70, 506, 608);
		contentPane.add(Historial_Insertar);
	}
}
