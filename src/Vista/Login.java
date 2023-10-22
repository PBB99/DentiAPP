package Vista;

import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;
import Controlador.ConexionMySQL;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.Font;

public class Login extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private ConexionMySQL conex;
	private Connection cn;
	private JTextField tfDNI;
	private JTextField tfPassword;
	private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login frame = new Login();
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
	public Login() {
		// -------------------- Conexión --------------------
		
		conex = new ConexionMySQL();
		cn = conex.conectar();
		
		// -------------------- JFrame --------------------
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setBounds(0, 0, 1920, 1080);
		contentPane = new JPanel() {
			@Override	
			public void paint(Graphics g) {
				Image bg = new ImageIcon(getClass().getResource("/Resources/images/bg_Login.jpg")).getImage();
				g.drawImage(bg,0,0,getWidth(),getHeight(), this);
				setOpaque(false);
				super.paint(g);
			}
		};
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		this.setUndecorated(true);
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		// -------------------- Componentes --------------------
		// Login Layout
		JPanel loginPane = new JPanel();
		loginPane.setBounds(685, 165, 550, 750);
		//loginPane.setBorder(new LineBorder(new Color(0, 0, 0)));
		loginPane.setBackground(Color.WHITE);
		contentPane.add(loginPane);
		loginPane.setLayout(null);
		
		JLabel lblLogo = new JLabel("");
		//lblLogo.setBorder(new LineBorder(new Color(0, 0, 0)));
		lblLogo.setBounds(0, 0, 550, 200);
		lblLogo.setIcon(new ImageIcon(getClass().getResource("/Resources/images/logoFinal.png")));
		loginPane.add(lblLogo);
		
		JLabel lblDNI = new JLabel("Usuario");
		//lblDNI.setBorder(new LineBorder(new Color(0, 0, 0)));
		lblDNI.setBounds(75, 250, 400, 50);
		lblDNI.setFont(new Font("Tahoma", Font.PLAIN, 30));
		loginPane.add(lblDNI);
		
		JLabel lblPassword = new JLabel("Contraseña");
		//lblPassword.setBorder(new LineBorder(new Color(0, 0, 0)));
		lblPassword.setBounds(75, 425, 400, 50);
		lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 30));
		loginPane.add(lblPassword);
		
		tfDNI = new JTextField();
		//tfDNI.setBorder(new LineBorder(new Color(0, 0, 0)));
		tfDNI.setBounds(75, 315, 400, 50);
		tfDNI.setFont(new Font("Tahoma", Font.PLAIN, 25));
		loginPane.add(tfDNI);
		
		tfPassword = new JPasswordField();
		//tfPassword.setBorder(new LineBorder(new Color(0, 0, 0)));
		tfPassword.setBounds(75, 490, 400, 50);
		tfPassword.setFont(new Font("Tahoma", Font.PLAIN, 25));
		loginPane.add(tfPassword);
		
		JButton btnLogin = new JButton("Iniciar sesión");
		btnLogin.setFont(new Font("Tahoma", Font.PLAIN, 19));
		btnLogin.setBounds(200, 675, 150, 50);
		loginPane.add(btnLogin);
		
		JButton btnClose = new JButton();
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
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
				btnClose.setIcon(new ImageIcon(getClass().getResource("/Resources/images/logout.png")));
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				btnClose.setIcon(new ImageIcon(getClass().getResource("/Resources/images/logoutWhite.png")));
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		btnClose.setFont(new Font("Tahoma", Font.PLAIN, 19));
		btnClose.setBounds(25, 985, 75, 75);
		btnClose.setIcon(new ImageIcon(getClass().getResource("/Resources/images/logout.png")));
		makeTransparent(btnClose);
		contentPane.add(btnClose);
	}
	
	//Método para transparentar los botones
	public static void makeTransparent(JButton btn) {
		btn.setOpaque(false);
		btn.setContentAreaFilled(false);
		btn.setBorderPainted(false);
	}
}