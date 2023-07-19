package view;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import java.awt.Rectangle;

public class Sobre extends JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Sobre() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(Sobre.class.getResource("/img/1055114_bike_bicycle_icon.png")));
		setBounds(new Rectangle(0, 0, 600, 500));
		getContentPane().setBackground(new Color(255, 255, 255));
		getContentPane().setLayout(null);
		
		JLabel lblNewLabel_4 = new JLabel("sistemaOS");
		lblNewLabel_4.setFont(new Font("Times New Roman", Font.BOLD, 16));
		lblNewLabel_4.setBounds(22, 55, 123, 20);
		getContentPane().add(lblNewLabel_4);
		
		JLabel lblNewLabel_5 = new JLabel("Autor :");
		lblNewLabel_5.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_5.setBounds(22, 86, 46, 14);
		getContentPane().add(lblNewLabel_5);
		
		JLabel lblNewLabel_6 = new JLabel("Iann Oliveira");
		lblNewLabel_6.setBounds(63, 86, 103, 14);
		getContentPane().add(lblNewLabel_6);
		
		JLabel lblNewLabel_7 = new JLabel("New label");
		lblNewLabel_7.setIcon(new ImageIcon(Sobre.class.getResource("/img/mit-icon.png")));
		lblNewLabel_7.setBounds(366, 55, 128, 128);
		getContentPane().add(lblNewLabel_7);
		
		JLabel lblNewLabel_8 = new JLabel("Gestão para Manutenção de Bikes e Acessórios");
		lblNewLabel_8.setBackground(new Color(255, 255, 255));
		lblNewLabel_8.setFont(new Font("Tahoma", Font.BOLD, 19));
		lblNewLabel_8.setBounds(22, 11, 461, 53);
		getContentPane().add(lblNewLabel_8);
		
		JLabel lblNewLabel_10 = new JLabel("");
		lblNewLabel_10.setIcon(new ImageIcon(Sobre.class.getResource("/img/1964417_github_logo_media_social_icon.png")));
		lblNewLabel_10.setToolTipText("github");
		lblNewLabel_10.setBounds(0, 194, 64, 64);
		getContentPane().add(lblNewLabel_10);
		
		JLabel lblNewLabel_11 = new JLabel("");
		lblNewLabel_11.setIcon(new ImageIcon(Sobre.class.getResource("/img/5305157_connection_linkedin_network_linkedin logo_icon.png")));
		lblNewLabel_11.setToolTipText("Linkedin");
		lblNewLabel_11.setBounds(254, 194, 64, 64);
		getContentPane().add(lblNewLabel_11);
		
		JLabel lblNewLabel_12 = new JLabel("github.com/iannoliver");
		lblNewLabel_12.setFont(new Font("Sylfaen", Font.BOLD, 16));
		lblNewLabel_12.setBounds(68, 208, 176, 38);
		getContentPane().add(lblNewLabel_12);
		
		JLabel lblNewLabel_13 = new JLabel("Iann Oliveira");
		lblNewLabel_13.setFont(new Font("Sylfaen", Font.BOLD, 16));
		lblNewLabel_13.setBounds(328, 215, 166, 24);
		getContentPane().add(lblNewLabel_13);
		
		JLabel lblNewLabel_9 = new JLabel("");
		lblNewLabel_9.setOpaque(true);
		lblNewLabel_9.setBackground(new Color(12, 161, 143));
		lblNewLabel_9.setBounds(0, 183, 504, 78);
		getContentPane().add(lblNewLabel_9);
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Sobre dialog = new Sobre();
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the dialog.
	 */
	public void Sobre() {
		getContentPane().setBackground(new Color(255, 255, 255));
		setModal(true);
		setIconImage(Toolkit.getDefaultToolkit().getImage(Sobre.class.getResource("/img/notebook.png")));
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("DBsistemas");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNewLabel.setBounds(30, 30, 190, 29);
		getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Autor: Iann Oliveira");
		lblNewLabel_1.setBounds(40, 70, 108, 22);
		getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Sob a Liçenca MIT");
		lblNewLabel_2.setBounds(30, 113, 163, 29);
		getContentPane().add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("MIT");
		lblNewLabel_3.setIcon(new ImageIcon(Sobre.class.getResource("/img/mit-icon.png")));
		lblNewLabel_3.setBounds(306, 133, 128, 128);
		getContentPane().add(lblNewLabel_3);
	
	}

}
