package Vista;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import Controlador.ConexionMySQL;
import Controlador.UserController;
import Modelo.SpecialityHibernate;
import Modelo.UserHibernate;

import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.CardLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class DChangePass extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JPasswordField TFoldPass;
	private JPasswordField TFnewPass1;
	private JPasswordField TFnewPass2;
	private boolean show=false;
	private char contra='1';
	private ConexionMySQL conexion;
	private String usuario;
	private UserController userController;
	private SessionFactory instancia;
	private Session session;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
//		try {
//		DChangePass dialog = new DChangePass();
//		dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
//		dialog.setVisible(true);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
	}

	/**
	 * Create the dialog.
	 */
	public DChangePass(UserHibernate userHi) {
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		//conexion HIbernate
		this.instancia = (SessionFactory) new Configuration().configure("hibernate.cfg.xml")
				.addAnnotatedClass(UserHibernate.class).buildSessionFactory();
		this.session = instancia.openSession();
		this.session.beginTransaction();
		userController=new UserController(conexion);
		//aparece en x:1360 y y:0, se extiende 560 pixeles  a la derecha y 1080 hacia abajo
		setBounds(1360, 0, 560, 1080);
		getContentPane().setLayout(null);
		contentPanel.setBounds(196, 11, 20, 20);
		contentPanel.setBackground(new Color(238,238,238));
		contentPanel.setLayout(new FlowLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBounds(10, 425, 524, 76);
			getContentPane().add(buttonPane);
			buttonPane.setLayout(null);
			{
				JButton okButton = new JButton("CAMBIAR CONTRASEÑA");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
					//para cambiar la contraseña primero deben coindicir la contraseña del usuario que esta en la app en ese momento que entra por parametro
						if(userHi.getContraseña().equals(TFoldPass.getText())) {
							//si coinciden debe comprobar que la contraseña nueva es la misma ambos JPasswordField
							if(TFnewPass1.getText().equals(TFnewPass2.getText())) {
								//modifica la contraseña a nivel local
								userHi.setContraseña(TFnewPass1.getText());
								//actualiza el usuario en la base de datos
								session.update(userHi);
								//confirma los cambios en la base de datos
								session.getTransaction().commit();
								JOptionPane.showMessageDialog(okButton, "Contrseña cambiada correctamene","Contraseña Actualizada",JOptionPane.INFORMATION_MESSAGE);
								dispose();
							}else {
								JOptionPane.showMessageDialog(okButton, "Las contraseñas nuevas no coinciden ","Error contraseña nueva",JOptionPane.WARNING_MESSAGE);
							}
						}else {
							JOptionPane.showMessageDialog(okButton, "La contraseña introducida no coincide con la de este usuario","Error contraseña antigua",JOptionPane.WARNING_MESSAGE);
						}
						
					}
				});
				okButton.setBounds(243, 0, 281, 37);
				okButton.setBorderPainted(false);
				okButton.setBackground(new Color(148, 220, 219));
				okButton.setForeground(new Color(255, 255, 255));
				okButton.setFont(new Font("Dialog", Font.BOLD, 20));
				buttonPane.add(okButton);
				okButton.setActionCommand("OK");
				getRootPane().setDefaultButton(okButton);
			}
			
			JButton Btoggle = new JButton("MOSTRAR");
			Btoggle.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(show==false) {
					 contra=TFoldPass.getEchoChar();
						TFoldPass.setEchoChar((char)0);
						TFnewPass1.setEchoChar((char)0);
						TFnewPass2.setEchoChar((char)0);
						Btoggle.setText("Ocultar");
						show=true;
					}else {
						TFoldPass.setEchoChar((char)contra);
						TFnewPass1.setEchoChar((char)contra);
						TFnewPass2.setEchoChar((char)contra);
						Btoggle.setText("Mostrar");
						show=false;
					}
					
				}
			});
			Btoggle.setBounds(9, 0, 224, 37);
			Btoggle.setForeground(Color.WHITE);
			Btoggle.setFont(new Font("Gentium Book Basic", Font.BOLD, 23));
			Btoggle.setBorderPainted(false);
			Btoggle.setBackground(new Color(148, 220, 219));
			Btoggle.setActionCommand("OK");
			buttonPane.add(Btoggle);
		}
		
		TFoldPass = new JPasswordField();
		TFoldPass.setBorder(BorderFactory.createMatteBorder(0,0,1,0, Color.BLACK));
		TFoldPass.setBackground(new Color(238, 238, 238));
		
		TFoldPass.setBounds(49, 91, 339, 46);
		getContentPane().add(TFoldPass);
		TFoldPass.setColumns(10);
		{
			TFnewPass1 = new JPasswordField();
			TFnewPass1.setColumns(10);
			TFnewPass1.setBorder(BorderFactory.createMatteBorder(0,0,1,0, Color.BLACK));
			TFnewPass1.setBackground(new Color(238, 238, 238));
			TFnewPass1.setBounds(49, 183, 339, 46);
			getContentPane().add(TFnewPass1);
		}
		{
			TFnewPass2 = new JPasswordField();
			TFnewPass2.setColumns(10);
			TFnewPass2.setBorder(BorderFactory.createMatteBorder(0,0,1,0, Color.BLACK));
			TFnewPass2.setBackground(new Color(238, 238, 238));
			TFnewPass2.setBounds(49, 304, 339, 46);
			getContentPane().add(TFnewPass2);
		}
		
		JLabel LoldPass = new JLabel("CONTRASEÑA ACTUAL");
		LoldPass.setFont(new Font("Gentium Book Basic", Font.BOLD, 30));
		LoldPass.setBounds(49, 42, 339, 38);
		getContentPane().add(LoldPass);
		
		JLabel lnewPass1 = new JLabel("NUEVA CONTRASEÑA");
		lnewPass1.setFont(new Font("Gentium Book Basic", Font.BOLD, 30));
		lnewPass1.setBounds(49, 148, 339, 38);
		getContentPane().add(lnewPass1);
		
		JLabel lnewPass2 = new JLabel("REPITA CONTRASEÑA");
		lnewPass2.setFont(new Font("Gentium Book Basic", Font.BOLD, 30));
		lnewPass2.setBounds(49, 268, 379, 38);
		getContentPane().add(lnewPass2);
		
		
	}
}
