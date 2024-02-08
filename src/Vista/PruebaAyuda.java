package Vista;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import javax.help.HelpBroker;
import javax.help.HelpSet;
import javax.help.HelpSetException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class PruebaAyuda extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PruebaAyuda frame = new PruebaAyuda();
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
	public PruebaAyuda() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		
		JButton btnNewButton_1 = new JButton("Boton ");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				File fichero = new File("src/help/help_set.hs");
				try {
					URL hsURL = fichero.toURI().toURL();
					HelpSet helpset = new HelpSet(getClass().getClassLoader(),hsURL);
					HelpBroker hb = helpset.createHelpBroker();
					hb.enableHelpOnButton(btnNewButton_1, "principal", helpset); 
				} catch (MalformedURLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (HelpSetException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}catch (Exception e2) {
					// TODO: handle exception
				}
			}
			
		});
		contentPane.add(btnNewButton_1);
	}

}
