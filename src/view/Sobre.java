package view;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.net.URI;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Cursor;

public class Sobre extends JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLabel lblGit;

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
	public Sobre() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(Sobre.class.getResource("/img/IconBike.png")));
		setBounds(new Rectangle(0, 0, 665, 301));
		getContentPane().setBackground(new Color(255, 255, 255));
		getContentPane().setLayout(null);
		
		JLabel lblNewLabel_4 = new JLabel("sistemaOS");
		lblNewLabel_4.setFont(new Font("Times New Roman", Font.BOLD, 16));
		lblNewLabel_4.setBounds(22, 81, 123, 20);
		getContentPane().add(lblNewLabel_4);
		
		JLabel lblNewLabel_5 = new JLabel("Autor :");
		lblNewLabel_5.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_5.setBounds(22, 112, 46, 14);
		getContentPane().add(lblNewLabel_5);
		
		JLabel lblNewLabel_6 = new JLabel("Iann Oliveira");
		lblNewLabel_6.setBounds(67, 112, 103, 14);
		getContentPane().add(lblNewLabel_6);
		
		JLabel lblNewLabel_7 = new JLabel("New label");
		lblNewLabel_7.setIcon(new ImageIcon(Sobre.class.getResource("/img/mit.png")));
		lblNewLabel_7.setBounds(507, 55, 128, 128);
		getContentPane().add(lblNewLabel_7);
		
		JLabel lblNewLabel_8 = new JLabel("Sistema de Gestão de serviços e controle de estoque para XRider");
		lblNewLabel_8.setBackground(new Color(255, 255, 255));
		lblNewLabel_8.setFont(new Font("Tahoma", Font.BOLD, 19));
		lblNewLabel_8.setBounds(10, 11, 625, 64);
		getContentPane().add(lblNewLabel_8);
		
		JLabel lblNewLabel_10 = new JLabel("");
		lblNewLabel_10.setIcon(new ImageIcon(Sobre.class.getResource("/img/Giticon.png")));
		lblNewLabel_10.setToolTipText("github");
		lblNewLabel_10.setBounds(0, 194, 64, 64);
		getContentPane().add(lblNewLabel_10);
		
		JLabel lblNewLabel_11 = new JLabel("");
		lblNewLabel_11.setIcon(new ImageIcon(Sobre.class.getResource("/img/linkedin.png")));
		lblNewLabel_11.setToolTipText("Linkedin");
		lblNewLabel_11.setBounds(376, 197, 64, 64);
		getContentPane().add(lblNewLabel_11);
		
		lblGit = new JLabel("https://github.com/iannoliver/XRider");
		lblGit.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblGit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				link("https://github.com/iannoliver/XRider");
			}
		});
		lblGit.setFont(new Font("Sylfaen", Font.BOLD, 16));
		lblGit.setBounds(68, 208, 281, 38);
		getContentPane().add(lblGit);
		
		JLabel lblNewLabel_13 = new JLabel("Iann Oliveira");
		lblNewLabel_13.setFont(new Font("Sylfaen", Font.BOLD, 16));
		lblNewLabel_13.setBounds(452, 215, 128, 24);
		getContentPane().add(lblNewLabel_13);
		
		JLabel lblNewLabel_9 = new JLabel("");
		lblNewLabel_9.setOpaque(true);
		lblNewLabel_9.setBackground(new Color(12, 161, 143));
		lblNewLabel_9.setBounds(0, 183, 649, 78);
		getContentPane().add(lblNewLabel_9);
	}
	
	private void link(String site) {
		Desktop desktop = Desktop.getDesktop();
		try {
			URI uri = new URI(site);
			desktop.browse(uri);
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
