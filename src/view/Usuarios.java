package view;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;

import model.DAO;
import utils.Validador;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;
import java.awt.Font;
import javax.swing.border.EmptyBorder;
import javax.swing.border.CompoundBorder;

public class Usuarios extends JDialog {
	DAO dao = new DAO();
	private Connection con;
	private PreparedStatement pst;
	private ResultSet rs;

	private static final long serialVersionUID = 1L;
	private JButton btnAdicionar;
	private JButton btnLimpar;
	private JButton btnEditar;
	private JButton btnExcluir;
	private final JList listUsers = new JList();
	private JScrollPane scrollPaneUsers;
	private JLabel lblPerfil;
	private JComboBox cboPerfil;
	private JCheckBox chckSenha;
	private JTextField txtSenha;
	private JTextField txtNome;
	private JTextField txtID;
	private JTextField txtLogin;
	private JLabel lblLogin;
	private JLabel lblId;
	private JButton btnPesquisar;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Usuarios dialog = new Usuarios();
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		});
	}

	public Usuarios() {
		setModal(true);
		setResizable(false);
		getContentPane().addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				scrollPaneUsers.setVisible(false);
			}

		});
		getContentPane().setBackground(new Color(255, 255, 255));
		setTitle("Usuarios");
		setIconImage(Toolkit.getDefaultToolkit().getImage(Usuarios.class.getResource("/img/IconBike.png")));
		setBounds(100, 100, 800, 600);
		getContentPane().setLayout(null);

		btnExcluir = new JButton("");
		btnExcluir.setEnabled(false);
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				excluirUsuario();
			}
		});

		scrollPaneUsers = new JScrollPane();
		scrollPaneUsers.setVisible(false);
		scrollPaneUsers.setBorder(null);
		scrollPaneUsers.setBounds(221, 270, 253, 68);
		getContentPane().add(scrollPaneUsers);
		scrollPaneUsers.setViewportView(listUsers);
		listUsers.setFont(new Font("Tahoma", Font.PLAIN, 28));

		listUsers.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				buscarUsuarioLista();
			}
		});
		listUsers.setBorder(null);
		btnExcluir.setIcon(
				new ImageIcon(Usuarios.class.getResource("/img/3017912_annul_cancel_clean_delete_eliminate_icon.png")));
		btnExcluir.setBorder(null);
		btnExcluir.setContentAreaFilled(false);
		btnExcluir.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnExcluir.setToolTipText("Excluir");
		btnExcluir.setBounds(435, 486, 64, 64);
		getContentPane().add(btnExcluir);

		JLabel lblSenha = new JLabel("Senha:");
		lblSenha.setFont(new Font("Tahoma", Font.PLAIN, 28));
		lblSenha.setBounds(113, 310, 115, 33);
		getContentPane().add(lblSenha);

		btnLimpar = new JButton("");
		btnLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limparCampos();
			}
		});
		btnLimpar.setIcon(new ImageIcon(Usuarios.class.getResource("/img/2124188_app_eraser_essential_ui_icon.png")));
		btnLimpar.setToolTipText("Limpar");
		btnLimpar.setContentAreaFilled(false);
		btnLimpar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnLimpar.setBorder(null);
		btnLimpar.setBounds(613, 486, 64, 64);
		getContentPane().add(btnLimpar);

		btnAdicionar = new JButton("");
		btnAdicionar.setContentAreaFilled(false);
		btnAdicionar.setEnabled(false);
		btnAdicionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				adicionar();
			}
		});
		btnAdicionar.setBorder(null);
		btnAdicionar.setIcon(new ImageIcon(
				Usuarios.class.getResource("/img/7548825_user_interface_add_shield_protection_icon.png")));
		btnAdicionar.setToolTipText("Adicionar");
		btnAdicionar.setBounds(84, 486, 64, 64);
		getContentPane().add(btnAdicionar);

		btnEditar = new JButton("");
		btnEditar.setEnabled(false);
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (chckSenha.isSelected()) {
					editarUsuario();
				} else {
					editarUsuarioExcetoSenha();

				}

			}
		});
		btnEditar.setBorder(null);
		btnEditar.setContentAreaFilled(false);
		btnEditar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnEditar.setIcon(new ImageIcon(Usuarios.class.getResource("/img/Edit.png")));
		btnEditar.setToolTipText("editar");
		btnEditar.setBounds(249, 486, 64, 64);
		getContentPane().add(btnEditar);

		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setOpaque(true);
		lblNewLabel.setBackground(new Color(7, 188, 148));
		lblNewLabel.setBounds(0, 486, 784, 75);
		getContentPane().add(lblNewLabel);

		lblPerfil = new JLabel("Perfil:");
		lblPerfil.setFont(new Font("Tahoma", Font.PLAIN, 28));
		lblPerfil.setBounds(542, 427, 88, 34);
		getContentPane().add(lblPerfil);

		cboPerfil = new JComboBox();
		cboPerfil.setFont(new Font("Tahoma", Font.PLAIN, 28));
		cboPerfil.setModel(new DefaultComboBoxModel(new String[] { "", "admin", "user" }));
		cboPerfil.setBounds(640, 425, 115, 38);
		getContentPane().add(cboPerfil);

		chckSenha = new JCheckBox("Alterar a senha");
		chckSenha.setFont(new Font("Tahoma", Font.PLAIN, 28));
		chckSenha.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (chckSenha.isSelected()) {
					txtSenha.setText(null);
					txtSenha.requestFocus();
					txtSenha.setBackground(Color.YELLOW);
				} else {
					txtSenha.setBackground(Color.WHITE);
				}
			}
		});
		chckSenha.setBounds(533, 310, 222, 33);
		getContentPane().add(chckSenha);

		txtSenha = new JTextField();
		txtSenha.setFont(new Font("Tahoma", Font.PLAIN, 28));
		txtSenha.setBounds(221, 309, 253, 33);
		getContentPane().add(txtSenha);
		txtSenha.setColumns(10);

		txtNome = new JTextField();
		txtNome.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				listarUsuarios();
			}
		});
		txtNome.setFont(new Font("Tahoma", Font.PLAIN, 28));
		txtNome.setColumns(10);
		txtNome.setBounds(221, 244, 253, 33);
		getContentPane().add(txtNome);

		JLabel lblNome = new JLabel("Nome:");
		lblNome.setFont(new Font("Tahoma", Font.PLAIN, 28));
		lblNome.setBounds(113, 245, 115, 33);
		getContentPane().add(lblNome);

		txtLogin = new JTextField();
		txtLogin.setFont(new Font("Tahoma", Font.PLAIN, 28));
		txtLogin.setColumns(10);
		txtLogin.setBounds(221, 180, 253, 33);
		getContentPane().add(txtLogin);

		txtID = new JTextField();
		txtID.setEditable(false);
		txtID.setFont(new Font("Tahoma", Font.PLAIN, 28));
		txtID.setColumns(10);
		txtID.setBounds(221, 122, 130, 33);
		getContentPane().add(txtID);

		lblLogin = new JLabel("Login:");
		lblLogin.setFont(new Font("Tahoma", Font.PLAIN, 28));
		lblLogin.setBounds(124, 181, 115, 33);
		getContentPane().add(lblLogin);

		lblId = new JLabel("ID:");
		lblId.setFont(new Font("Tahoma", Font.PLAIN, 28));
		lblId.setBounds(157, 123, 115, 33);
		getContentPane().add(lblId);

		btnPesquisar = new JButton("");
		btnPesquisar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buscarUsuario();
			}
		});
		btnPesquisar.setBorder(null);
		btnPesquisar.setContentAreaFilled(false);
		btnPesquisar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnPesquisar.setIcon(new ImageIcon(Usuarios.class.getResource("/img/Search.png")));
		btnPesquisar.setToolTipText("Pesquisar");
		btnPesquisar.setBounds(500, 151, 64, 64);
		getContentPane().add(btnPesquisar);
		getRootPane().setDefaultButton(btnPesquisar);

	}

	private void listarUsuarios() {
		DefaultListModel<String> modelo = new DefaultListModel<>();
		listUsers.setModel(modelo);
		String readLista = "select * from usuarios where nome like '" + txtNome.getText() + "%'" + "order by nome";
		try {
			con = dao.conectar();
			pst = con.prepareStatement(readLista);
			rs = pst.executeQuery();
			while (rs.next()) {
				scrollPaneUsers.setVisible(true);
				modelo.addElement(rs.getString(2));
				if (txtNome.getText().isEmpty()) {
					scrollPaneUsers.setVisible(false);
				}
			}
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	private void buscarUsuario() {
		String read = "select * from usuarios where login = ?";
		try {
			con = dao.conectar();
			pst = con.prepareStatement(read);
			pst.setString(1, txtLogin.getText());
			rs = pst.executeQuery();
			if (rs.next()) {
				txtID.setText(rs.getString(1));
				txtNome.setText(rs.getString(2));
				txtLogin.setText(rs.getString(3));
				txtSenha.setText(rs.getString(4));
				cboPerfil.setSelectedItem(rs.getString(5));
				btnEditar.setEnabled(true);
				btnExcluir.setEnabled(true);
				btnPesquisar.setEnabled(false);
			} else {
				JOptionPane.showMessageDialog(null, "Usuário inexistente");
				btnAdicionar.setEnabled(true);
				btnPesquisar.setEnabled(false);
			}
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	private void buscarUsuarioLista() {
		int linha = listUsers.getSelectedIndex();
		if (linha >= 0) {
			String readListaUsuario = "select * from usuarios where nome like '" + txtNome.getText() + "%'"
					+ "order by nome limit " + (linha) + " , 1";
			try {
				con = dao.conectar();
				pst = con.prepareStatement(readListaUsuario);
				rs = pst.executeQuery();
				if (rs.next()) {
					scrollPaneUsers.setVisible(false);
					txtID.setText(rs.getString(1));
					txtNome.setText(rs.getString(2));
					txtLogin.setText(rs.getString(3));
					txtSenha.setText(rs.getString(4));
					cboPerfil.setSelectedItem(rs.getString(5));
					btnEditar.setEnabled(true);
					btnExcluir.setEnabled(true);
					btnPesquisar.setEnabled(false);
				} else {
					JOptionPane.showMessageDialog(null, "Usuário inexistente");
					btnAdicionar.setEnabled(true);
					btnPesquisar.setEnabled(false);
				}
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		} else {
			scrollPaneUsers.setVisible(false);
		}
	}

	private void limparCampos() {
		txtID.setText(null);
		txtNome.setText(null);
		txtLogin.setText(null);
		txtSenha.setText(null);
		txtSenha.setBackground(Color.WHITE);
		btnAdicionar.setEnabled(false);
		btnEditar.setEnabled(false);
		btnExcluir.setEnabled(false);
		btnPesquisar.setEnabled(true);
		scrollPaneUsers.setVisible(false);
		cboPerfil.setSelectedItem("");
		chckSenha.setSelected(false);
	}

	private void adicionar() {
		if (txtNome.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Nome do Usuário");
			txtNome.requestFocus();
		} else if (txtLogin.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Login do Usuário");
			txtLogin.requestFocus();
		} else if (txtSenha.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha a Senha do Usuário");
		} else if (cboPerfil.getSelectedItem().equals("")) {
			JOptionPane.showMessageDialog(null, "Preencha o perfil do Usuário");
		} else {

			String create = "insert into usuarios (nome, login, senha, perfil) values (?, ?, md5(?), ?)";
			try {
				con = dao.conectar();
				pst = con.prepareStatement(create);
				pst.setString(1, txtNome.getText());
				pst.setString(2, txtLogin.getText());
				pst.setString(3, txtSenha.getText());
				pst.setString(4, cboPerfil.getSelectedItem().toString());
				pst.executeUpdate();
				JOptionPane.showMessageDialog(null, "Usuário adicionado!!");
				limparCampos();
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}

	private void editarUsuario() {
		if (txtNome.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Digite o nome");
			txtNome.requestFocus();
		} else if (txtLogin.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Digite o login");
			txtLogin.requestFocus();
		} else if (txtLogin.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Digite a senha");
			txtLogin.requestFocus();
		} else if (cboPerfil.getSelectedItem().equals("")) {
			JOptionPane.showMessageDialog(null, "Preencha o perfil");
		} else {
			String update = "update usuarios set nome = ?, login = ?, senha = md5(?), perfil = ? where id = ?";
			try {
				con = dao.conectar();
				pst = con.prepareStatement(update);
				pst.setString(1, txtNome.getText());
				pst.setString(2, txtLogin.getText());
				pst.setString(3, txtSenha.getText());
				pst.setString(4, cboPerfil.getSelectedItem().toString());
				pst.setString(5, txtID.getText());
				pst.executeUpdate();
				JOptionPane.showMessageDialog(null, "Dados do Usuario editados com sucesso");
				limparCampos();
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}

	private void editarUsuarioExcetoSenha() {
		if (txtNome.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Digite o nome");
			txtNome.requestFocus();
		} else if (txtLogin.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Digite o login");
			txtLogin.requestFocus();
		} else if (txtLogin.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Digite a senha");
			txtLogin.requestFocus();
		} else if (cboPerfil.getSelectedItem().equals("")) {
			JOptionPane.showMessageDialog(null, "Preencha o perfil");
		} else {
			String update2 = "update usuarios set nome = ?, login = ?, perfil = ? where id = ?";
			try {
				con = dao.conectar();
				pst = con.prepareStatement(update2);
				pst.setString(1, txtNome.getText());
				pst.setString(2, txtLogin.getText());
				pst.setString(3, cboPerfil.getSelectedItem().toString());
				pst.setString(4, txtID.getText());
				pst.executeUpdate();
				JOptionPane.showMessageDialog(null, "Dados do Usuario editados com sucesso");
				limparCampos();
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}

	private void excluirUsuario() {
		int confirm = JOptionPane.showConfirmDialog(null, "Confirma a exclusão deste usuario?", "Atenção!!",
				JOptionPane.YES_NO_OPTION);
		if (confirm == JOptionPane.YES_OPTION) {
			String delete = "delete from usuarios where id = ?";
			try {
				con = dao.conectar();
				pst = con.prepareStatement(delete);
				pst.setString(1, txtID.getText());
				pst.executeUpdate();
				limparCampos();
				JOptionPane.showMessageDialog(null, "Usuario excluído!");
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		}

	}
}
