package Vista;

import java.awt.EventQueue;
import java.awt.MediaTracker;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.border.EmptyBorder;
import javax.swing.*;
import java.awt.BorderLayout;

public class Video extends JFrame {


	private static final long serialVersionUID = 1L;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Video frame = new Video();
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
	public Video() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 853, 480);
		setUndecorated(true);
		setResizable(false);
		setLocationRelativeTo(null);
		getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(Video.class.getResource("/Resources/images/inicio.gif")));
		lblNewLabel.setBounds(0, 0, 853, 480);
		getContentPane().add(lblNewLabel);
		Timer timer=new Timer(6500, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				dispose();
				Login log=new Login(Video.this);
				log.setVisible(true);
			}
		});
		timer.setRepeats(false);
        timer.start();
		
		
		
	}

}
