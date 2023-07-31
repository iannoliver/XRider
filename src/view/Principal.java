package view;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.text.DateFormat;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.DAO;
import java.awt.Dimension;

public class Principal extends JFrame {
	DAO dao = new DAO();
	private Connection con;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel lblStatus;
	public JLabel panelRodapé;
	public JLabel lblData;
	private JLabel lblNewLabel_2;
	//está label abaixo será alterada pela tela de login
	public JLabel lblUsu;
	public JButton btnUsuarios;
	public JButton btnRelatorio;
	private JLabel lblUsuario;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Principal frame = new Principal();
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
	public Principal() {
		setBackground(new Color(255, 255, 255));
		setTitle("Sistema - Assistência de Bicicletas");
		setIconImage(Toolkit.getDefaultToolkit().getImage(Principal.class.getResource("/img/IconBike.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(0, 65, 236));
		contentPane.setBorder(null);

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		btnUsuarios = new JButton("");
		btnUsuarios.setEnabled(false);
		btnUsuarios.setBorder(null);
		btnUsuarios.setForeground(new Color(255, 255, 255));
		btnUsuarios.setBackground(new Color(240, 240, 240));
		btnUsuarios.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Usuarios usuarios = new Usuarios();
				usuarios.setVisible(true);
			}
		});
		
		lblUsu = new JLabel("");
		lblUsu.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblUsu.setBounds(265, 537, 173, 14);
		contentPane.add(lblUsu);
		
		lblUsuario = new JLabel("Usuário:");
		lblUsuario.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblUsuario.setBounds(197, 537, 73, 13);
		contentPane.add(lblUsuario);
		
		lblData = new JLabel("New label");
		lblData.setBackground(new Color(255, 255, 255));
		lblData.setFont(new Font("Tempus Sans ITC", Font.PLAIN, 16));
		lblData.setForeground(new Color(0, 0, 0));
		lblData.setBounds(0, 486, 363, 32);
		contentPane.add(lblData);
		
		lblStatus = new JLabel("");
		lblStatus.setIcon(new ImageIcon(Principal.class.getResource("/img/2124496_app_data_essential_ui_icon.png")));
		lblStatus.setBounds(656, 433, 128, 128);
		contentPane.add(lblStatus);
		
		panelRodapé = new JLabel("");
		panelRodapé.setOpaque(true);
		panelRodapé.setBackground(new Color(12, 163, 143));
		panelRodapé.setBounds(0, 433, 784, 138);
		contentPane.add(panelRodapé);
		btnUsuarios.setIcon(new ImageIcon(Principal.class.getResource("/img/6789698_interface_navigation_ui_user_icon.png")));
		btnUsuarios.setToolTipText("Usuários");
		btnUsuarios.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnUsuarios.setBounds(10, 174, 128, 128);
		contentPane.add(btnUsuarios);
		
		JButton btnSobre = new JButton("");
		btnSobre.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Sobre sobre = new Sobre();
				sobre.setVisible(true);
			}
		});
		btnSobre.setIcon(new ImageIcon(Principal.class.getResource("/img/3017950_advice_attention_care_caution_exclamation_icon.png")));
		btnSobre.setToolTipText("Sobre");
		btnSobre.setContentAreaFilled(false);
		btnSobre.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnSobre.setBorder(null);
		btnSobre.setBounds(726, 11, 48, 48);
		contentPane.add(btnSobre);
		
		JLabel lblNewLabel_1 = new JLabel("New label");
		lblNewLabel_1.setIcon(new ImageIcon(Principal.class.getResource("/img/IconBike.png")));
		lblNewLabel_1.setBounds(646, 70, 128, 128);
		contentPane.add(lblNewLabel_1);
		
		lblNewLabel_2 = new JLabel("XRaider");
		lblNewLabel_2.setForeground(new Color(255, 255, 255));
		lblNewLabel_2.setFont(new Font("Sylfaen", Font.BOLD, 19));
		lblNewLabel_2.setBounds(677, 209, 97, 32);
		contentPane.add(lblNewLabel_2);
		
		JLabel lblUsuarios = new JLabel("Usuários");
		lblUsuarios.setFont(new Font("Sylfaen", Font.BOLD, 19));
		lblUsuarios.setForeground(new Color(255, 255, 255));
		lblUsuarios.setBounds(37, 313, 97, 25);
		contentPane.add(lblUsuarios);
		
		JButton btnClientes = new JButton("");
		btnClientes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Clientes clientes = new Clientes();
				clientes.setVisible(true);
			}
		});
		btnClientes.setBorder(null);
		btnClientes.setIcon(new ImageIcon(Principal.class.getResource("/img/370076_account_avatar_client_male_person_icon.png")));
		btnClientes.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnClientes.setToolTipText("Clientes");
		btnClientes.setBounds(10, 11, 128, 128);
		contentPane.add(btnClientes);
		
		
		btnRelatorio = new JButton("");
		btnRelatorio.setEnabled(false);
		btnRelatorio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Relatorios relatorios = new Relatorios();
				relatorios.setVisible(true);
			}
		});
		btnRelatorio.setBorder(null);
		btnRelatorio.setIcon(new ImageIcon(Principal.class.getResource("/img/3615746_checklist_clipboard_package_report_restriction_icon.png")));
		btnRelatorio.setToolTipText("Relatório");
		btnRelatorio.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnRelatorio.setBounds(179, 11, 128, 128);
		contentPane.add(btnRelatorio);
		
		JLabel lblNewLabel_4 = new JLabel("Relatório");
		lblNewLabel_4.setForeground(new Color(255, 255, 255));
		lblNewLabel_4.setFont(new Font("Sylfaen", Font.BOLD, 19));
		lblNewLabel_4.setBounds(197, 150, 166, 25);
		contentPane.add(lblNewLabel_4);
		
		JLabel lblNewLabel_4_1 = new JLabel("Clientes");
		lblNewLabel_4_1.setForeground(Color.WHITE);
		lblNewLabel_4_1.setFont(new Font("Sylfaen", Font.BOLD, 19));
		lblNewLabel_4_1.setBounds(37, 150, 150, 25);
		contentPane.add(lblNewLabel_4_1);
		
		JLabel lblNewLabel_6 = new JLabel("Ordem de Serviço");
		lblNewLabel_6.setFont(new Font("Sylfaen", Font.BOLD, 19));
		lblNewLabel_6.setForeground(new Color(255, 255, 255));
		lblNewLabel_6.setBounds(340, 146, 158, 32);
		contentPane.add(lblNewLabel_6);
		
		JButton btnOS = new JButton("");
		btnOS.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Servicos servicos = new Servicos();
				servicos.setVisible(true);
			}
		});
		btnOS.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnOS.setIcon(new ImageIcon(Principal.class.getResource("/img/6159284_bicycle_bike_cycling_isometric_mountain bike_icon.png")));
		btnOS.setToolTipText("Ordem de Serviço");
		btnOS.setBorder(null);
		btnOS.setBounds(351, 11, 128, 128);
		contentPane.add(btnOS);
		
		JButton btnUsuarios_1 = new JButton("");
		btnUsuarios_1.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnUsuarios_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Produtos produtos = new Produtos();
				produtos.setVisible(true);
			}
		});
		btnUsuarios_1.setIcon(new ImageIcon(Principal.class.getResource("/img/Produtos.png")));
		btnUsuarios_1.setToolTipText("Produtos");
		btnUsuarios_1.setForeground(Color.WHITE);
		btnUsuarios_1.setBorder(null);
		btnUsuarios_1.setBackground(SystemColor.menu);
		btnUsuarios_1.setBounds(179, 174, 128, 128);
		contentPane.add(btnUsuarios_1);
		
		JButton btnUsuarios_2 = new JButton("");
		btnUsuarios_2.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnUsuarios_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Fornecedores fornecedores = new Fornecedores();
				fornecedores.setVisible(true);
			}
		});
		btnUsuarios_2.setIcon(new ImageIcon(Principal.class.getResource("/img/BMW.png")));
		btnUsuarios_2.setToolTipText("Fornecedores");
		btnUsuarios_2.setForeground(Color.WHITE);
		btnUsuarios_2.setBorder(null);
		btnUsuarios_2.setBackground(SystemColor.menu);
		btnUsuarios_2.setBounds(350, 174, 128, 128);
		contentPane.add(btnUsuarios_2);
		
		JLabel lblNewLabel_3_1 = new JLabel("Produtos");
		lblNewLabel_3_1.setForeground(Color.WHITE);
		lblNewLabel_3_1.setFont(new Font("Sylfaen", Font.BOLD, 19));
		lblNewLabel_3_1.setBounds(199, 313, 97, 25);
		contentPane.add(lblNewLabel_3_1);
		
		JLabel lblNewLabel_3_2 = new JLabel("Fornecedores");
		lblNewLabel_3_2.setForeground(Color.WHITE);
		lblNewLabel_3_2.setFont(new Font("Sylfaen", Font.BOLD, 19));
		lblNewLabel_3_2.setBounds(351, 313, 128, 25);
		contentPane.add(lblNewLabel_3_2);
		
		JLabel lblNewLabel_7 = new JLabel("");
		lblNewLabel_7.setToolTipText("iconUsu");
		lblNewLabel_7.setBounds(148, 537, 46, 14);
		contentPane.add(lblNewLabel_7);
		status();
		setarData();
	}
	private void status() {
		try {
			con = dao.conectar();
			if (con == null) {
				lblStatus.setIcon(new ImageIcon(Usuarios.class.getResource("/img/2124496_app_data_essential_ui_icon.png")));
				
			} else {
				lblStatus.setIcon(new ImageIcon(Usuarios.class.getResource("/img/2124501_app_check_data_essential_ui_icon.png")));
			}
		} catch (Exception e) {
			System.out.println(e);
		}
}
	/**
	 * Método responsável por setar a data do rodapé
	 */
	private void setarData() {
		Date date = new Date();
		DateFormat formatador = DateFormat.getDateInstance(DateFormat.FULL);
		//alterar o texto da label
		lblData.setText(formatador.format(date));
	}
	}
