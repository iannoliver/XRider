package view;

import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import model.DAO;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import javax.swing.ImageIcon;
import java.awt.Toolkit;

public class Login extends JFrame {
	DAO dao = new DAO();
	private Connection con;
	private PreparedStatement pst;
	private ResultSet rs;
	Principal principal = new Principal();
	


	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtLogin;
	private JPasswordField txtSenha;
	private JLabel lblRodape;
	private JLabel lblStatus;


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


	public Login() {
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(Login.class.getResource("/img/IconBike.png")));
		setTitle("XRider Sistemas");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 411, 256);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		lblStatus = new JLabel("");
		lblStatus.setIcon(new ImageIcon(Login.class.getResource("/img/Databaseoff.png")));
		lblStatus.setToolTipText("on/off");
		lblStatus.setBounds(337, 169, 48, 48);
		contentPane.add(lblStatus);
		
		JLabel lblNewLabel = new JLabel("Login:");
		lblNewLabel.setBounds(10, 39, 46, 14);
		contentPane.add(lblNewLabel);
		
		txtLogin = new JTextField();
		txtLogin.setBounds(60, 36, 186, 20);
		contentPane.add(txtLogin);
		txtLogin.setColumns(10);
		
		txtSenha = new JPasswordField();
		txtSenha.setBounds(60, 81, 186, 20);
		contentPane.add(txtSenha);
		
		JLabel lblNewLabel_1 = new JLabel("Senha:");
		lblNewLabel_1.setBounds(10, 84, 40, 14);
		contentPane.add(lblNewLabel_1);
		
		JButton btnAcessar = new JButton("Acessar");
		btnAcessar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				logar();
			}
		});
		getRootPane().setDefaultButton(btnAcessar);
		btnAcessar.setBounds(184, 124, 89, 23);
		contentPane.add(btnAcessar);
		
		lblRodape = new JLabel("");
		lblRodape.setOpaque(true);
		lblRodape.setBackground(new Color(9, 162, 147));
		lblRodape.setBounds(0, 169, 395, 48);
		contentPane.add(lblRodape);
	}
	
	private void logar() {
		String capturaSenha = new String(txtSenha.getPassword());
		
		if(txtLogin.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o login!");
			txtLogin.requestFocus();
		} else if (capturaSenha.length() == 0) {
			JOptionPane.showMessageDialog(null, "Preencha a senha!");
			txtSenha.requestFocus();
		} else {
			String read = "select * from usuarios where login=? and senha= md5(?)";
			try {
				con = dao.conectar();
				pst = con.prepareStatement(read);
				pst.setString(1, txtLogin.getText());
				pst.setString(2, capturaSenha);
				rs = pst.executeQuery();
				if (rs.next()) {
					String perfil = rs.getString(5);
					if(perfil.equals("admin")) {
						principal.setVisible(true);
						principal.lblUsu.setText(rs.getString(2));
						principal.btnRelatorio.setEnabled(true);
						principal.btnUsuarios.setEnabled(true);
						principal.panelRodapé.setBackground(Color.RED);
						principal.lblUsu.setBackground(Color.WHITE);
						principal.lblData.setBackground(Color.WHITE);
						this.dispose();
					} else {
						principal.setVisible(true);
						principal.lblUsu.setText(rs.getString(2));
						this.dispose();
					}
					this.dispose();
				} else {
					JOptionPane.showMessageDialog(null, "O usuario e/ou senha inválidos");
				}
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
			
		}
		
	}
	
	@SuppressWarnings("unused")
	private void status () {
		try {
			con = dao.conectar();
			if (con == null) {
				lblStatus.setIcon(new ImageIcon(Login.class.getResource("/img/Databaseoff.png")));
				
			} else {

				lblStatus.setIcon(new ImageIcon(Login.class.getResource("/img/Databaseon.png")));
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
}
