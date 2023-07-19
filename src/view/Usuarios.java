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

public class Usuarios extends JDialog {
	DAO dao = new DAO();
	private Connection con;
	private PreparedStatement pst;
	private ResultSet rs;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField txtID;
	private JTextField txtNome;
	private JTextField txtLogin;
	private JPasswordField txtSenha;
	private JButton btnAdicionar;
	private JButton btnLimpar;
	private JButton btnEditar;
	private JButton btnPesquisar;
	private JButton btnExcluir;
	private final JList listUsers = new JList();
	private JScrollPane scrollPaneUsers;
	private JLabel lblPerfil;
	private JComboBox cboPerfil;
	private JCheckBox chckSenha;

	/**
	 * Launch the application.
	 */
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

	/**
	 * Create the dialog.
	 */
	public Usuarios() {
		getContentPane().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//clicar no painel do JDialog
				scrollPaneUsers.setVisible(false);
			}
			
		});
		getContentPane().setBackground(new Color(255, 255, 255));
		setTitle("Usuarios");
		setIconImage(Toolkit.getDefaultToolkit().getImage(Usuarios.class.getResource("/img/IconBike.png")));
		setBounds(100, 100, 442, 293);
		getContentPane().setLayout(null);
		
		btnExcluir = new JButton("");
		btnExcluir.setEnabled(false);
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				excluirUsuario();
			}
		});
		
		txtSenha = new JPasswordField();
		txtSenha.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		txtSenha.setBounds(55, 136, 184, 20);
		getContentPane().add(txtSenha);
		txtSenha.setDocument(new Validador(50));
		
		scrollPaneUsers = new JScrollPane();
		scrollPaneUsers.setVisible(false);
		scrollPaneUsers.setBorder(null);
		scrollPaneUsers.setBounds(55, 113, 184, 53);
		getContentPane().add(scrollPaneUsers);
		
		listUsers.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				buscarUsuarioLista();
			}
		});
		listUsers.setBorder(null);
		scrollPaneUsers.setViewportView(listUsers);
		btnExcluir.setIcon(new ImageIcon(Usuarios.class.getResource("/img/3017912_annul_cancel_clean_delete_eliminate_icon.png")));
		btnExcluir.setBorder(null);
		btnExcluir.setContentAreaFilled(false);
		btnExcluir.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnExcluir.setToolTipText("Excluir");
		btnExcluir.setBounds(146, 186, 64, 64);
		getContentPane().add(btnExcluir);
		
		JLabel lblID = new JLabel("ID");
		lblID.setBounds(10, 23, 46, 14);
		getContentPane().add(lblID);
		
		JLabel lblNome = new JLabel("Nome");
		lblNome.setBounds(10, 101, 46, 14);
		getContentPane().add(lblNome);
		
		JLabel lblLogin = new JLabel("Login");
		lblLogin.setBounds(10, 59, 46, 14);
		getContentPane().add(lblLogin);
		
		JLabel lblSenha = new JLabel("Senha");
		lblSenha.setBounds(10, 139, 46, 14);
		getContentPane().add(lblSenha);
		
		txtID = new JTextField();
		txtID.setEditable(false);
		txtID.setBounds(55, 20, 70, 20);
		getContentPane().add(txtID);
		txtID.setColumns(10);
		
		txtNome = new JTextField();
		txtNome.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		txtNome.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				//pressionar uma tecla
				
			}
			@Override
			public void keyReleased(KeyEvent e) {
				//soltar uma tecla
				listarUsuarios();
			}
		});
		txtNome.setBounds(55, 98, 184, 20);
		getContentPane().add(txtNome);
		txtNome.setColumns(10);
		txtNome.setDocument(new Validador(50));
		
		txtLogin = new JTextField();
		txtLogin.setBounds(55, 56, 184, 20);
		getContentPane().add(txtLogin);
		txtLogin.setColumns(10);
		txtLogin.setDocument(new Validador(30));
		
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
		btnLimpar.setBounds(221, 186, 64, 64);
		getContentPane().add(btnLimpar);
		
		btnPesquisar = new JButton("");
		btnPesquisar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buscar();
			}
		});
		btnPesquisar.setToolTipText("Pesquisar");
		btnPesquisar.setIcon(new ImageIcon(Usuarios.class.getResource("/img/4476868_find_search_ui_interface_mobile_icon.png")));
		btnPesquisar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnPesquisar.setContentAreaFilled(false);
		btnPesquisar.setBorder(null);
		btnPesquisar.setBounds(249, 44, 36, 34);
		getContentPane().add(btnPesquisar);
		
		getRootPane().setDefaultButton(btnPesquisar);
		
		btnAdicionar = new JButton("");
		btnAdicionar.setContentAreaFilled(false);
		btnAdicionar.setEnabled(false);
		btnAdicionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				adicionar();
			}
		});
		btnAdicionar.setBorder(null);
		btnAdicionar.setIcon(new ImageIcon(Usuarios.class.getResource("/img/7548825_user_interface_add_shield_protection_icon.png")));
		btnAdicionar.setToolTipText("Adicionar");
		btnAdicionar.setBounds(10, 186, 64, 64);
		getContentPane().add(btnAdicionar);
		
		btnEditar = new JButton("");
		btnEditar.setEnabled(false);
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//evento que vai verificar se o checkbox foi selecionado
				if(chckSenha.isSelected()) {
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
		btnEditar.setBounds(84, 186, 64, 64);
		getContentPane().add(btnEditar);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setOpaque(true);
		lblNewLabel.setBackground(new Color(7, 188, 148));
		lblNewLabel.setBounds(0, 186, 434, 75);
		getContentPane().add(lblNewLabel);
		
		lblPerfil = new JLabel("Perfil:");
		lblPerfil.setBounds(278, 139, 36, 14);
		getContentPane().add(lblPerfil);
		
		cboPerfil = new JComboBox();
		cboPerfil.setModel(new DefaultComboBoxModel(new String[] {"", "admin", "user"}));
		cboPerfil.setBounds(315, 135, 79, 22);
		getContentPane().add(cboPerfil);
		
		chckSenha = new JCheckBox("Alterar a senha");
		chckSenha.setVisible(false);
		chckSenha.setEnabled(false);
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
		chckSenha.setBounds(237, 163, 111, 23);
		getContentPane().add(chckSenha);
	}
	/**
	 * método para buscar usuario em lista
	 */
	private void buscarUsuarioLista() {
		//variável que captura o indice da linha da lista
		int linha = listUsers.getSelectedIndex();
		if(linha >= 0) {
			//Query (instrução SQL)
			//limit (0.1) -> seleciona o indice 0 e 1 usuário da lista
			String readListaUsuario = "select * from usuarios where nome like '" + txtNome.getText() + "%'"	+ "order by nome limit " + (linha) + " , 1";
			try {
				con = dao.conectar();
				pst = con.prepareStatement(readListaUsuario);
				rs = pst.executeQuery();
				if (rs.next()) {
					//esconder a lista
					scrollPaneUsers.setVisible(false);
					//setar os campos
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
				//fechar conexão
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		} else {
			// se não existir no banco um usuário da lista
				scrollPaneUsers.setVisible(false);
		}
	}
	
	/**
	 * método usado para listar o nome dos usuarios da lista
	 */
	private void listarUsuarios() {
		//a linha abaixo cria um objeto usando como referência um vetor dinâmico, 
		//este objeto ira temporariamente armazenar os nomes
		DefaultListModel<String> modelo = new DefaultListModel<>();
		//setar o modelo (vetor da lista)
		listUsers.setModel(modelo);
		//Query (instrução sql)
		String readLista = "select * from usuarios where nome like '" + txtNome.getText() + "%'" + "order by nome";
		try {
			con = dao.conectar();
			pst= con.prepareStatement(readLista);
			rs = pst.executeQuery();
			//uso do while para trazer os usuarios enqunto existir
			while(rs.next()) {
				//mostrar barra de rolagem
				scrollPaneUsers.setVisible(true);
				//adicionar os usuarios ao vetor -> lista
				modelo.addElement(rs.getString(2));
				//esconder a lista se nenhuma letra for digitada
				if (txtNome.getText().isEmpty()) {
					scrollPaneUsers.setVisible(false);
				}
			}
			con.close();
		} catch (Exception e) {
			System.out.println(e);
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
	
	private void buscar () {
		//testar o evento primeiro
		//System.out.println("Teste do botão buscar");
		//Criar uma variável com a Query (instrução do banco) 
		String read = "select * from usuarios where login = ?";
		//Tratramento de exceções
		try {
			//abrir a conexão
			con = dao.conectar();
			//PREPARA A EXECUÇÃO DA QUERY(instrução SQL - CRUD Read)
			//O parametro 1 substitui a / pelo conteúdo da caixa de texto 
			pst = con.prepareStatement(read);
			pst.setString(1,  txtLogin.getText());
			//executar a Query e buscar o resultado
			rs = pst.executeQuery();
			// uso da estrutura IFELSE para verificar se existe o contato
			// rs.next() -> se existir um contato no banco
			if (rs.next()) {
				//preeccher as caixas de texto com informações
				txtID.setText(rs.getString(1)); //1° campo da tabela
				txtNome.setText(rs.getString(2));//3° campo da tabela
				txtSenha.setText(rs.getString(4));//4° campo da tabela
				cboPerfil.setSelectedItem(rs.getString(5));
				btnEditar.setEnabled(true);
				btnExcluir.setEnabled(true);
				btnPesquisar.setEnabled(false);
			} else {
				//se não exisir um contato
				JOptionPane.showMessageDialog(null, "Usuário Inexistente");
				btnAdicionar.setEnabled(true);
				btnPesquisar.setEnabled(false);
			}
			// fechar a conexão(importante)
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	private void adicionar( ) {
		//System.out.println("teste");
		//Validação de campos obrigatórios
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
			
			//lógica principal
			String create = "insert into usuarios (nome, login, senha, perfil) values (?, ?, md5(?), ?)";
			//tratamento de exceções
			try {
				//abrir a conexão
				con = dao.conectar();
				//PREPARA A EXECUÇÃO DA QUERY(instrução SQL - CRUD Crate)
				pst = con.prepareStatement(create);
				pst.setString(1,  txtNome.getText());
				pst.setString(2,  txtLogin.getText());
				pst.setString(3,  txtSenha.getText());
				pst.setString(4,  cboPerfil.getSelectedItem().toString());
				//executa a query(Instrução SQL (CRUD - Create)
				pst.executeUpdate();
				//confirmar
				JOptionPane.showMessageDialog(null, "Usuário adicionado!!");
				//limpar campos
				limparCampos();
				//fechar conexão
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
		}// Fim do método adicionar
		
		/**
		 * Método para editar um contato(Atenção! usar o id)
		 */
		private void editarUsuario() {
			//System.out.println("teste do método editar");
			//Validação dos campos obrigatórios
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
				//Lógica principal
				//CRUD - Update
				String update = "update usuarios set nome = ?, login = ?, senha = md5(?), perfil = ? where id = ?";
				try {
					//abrir conexão
					con = dao.conectar();
					//prepara a query (instrução sql)
					pst = con.prepareStatement(update);
					pst.setString(1,txtNome.getText());
					pst.setString(2,txtLogin.getText());
					pst.setString(3,txtSenha.getText());
					pst.setString(4,cboPerfil.getSelectedItem().toString());
					pst.setString(5,txtID.getText());
					//executar a query
					pst.executeUpdate();
					//confirmar para o usuário
					JOptionPane.showMessageDialog(null, "Dados do Usuario editados com sucesso");
					//Limpar os campos
					limparCampos();
					//fechar conexão
					con.close();
				} catch (Exception e) {
					System.out.println(e);
				}
			}
		}//Fim do método editar contato
		
		private void editarUsuarioExcetoSenha() {
			//System.out.println("teste do método editar");
			//Validação dos campos obrigatórios
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
				//Lógica principal
				//CRUD - Update
				String update2 = "update usuarios set nome = ?, login = ?, perfil = ? where id = ?";
				try {
					//abrir conexão
					con = dao.conectar();
					//prepara a query (instrução sql)
					pst = con.prepareStatement(update2);
					pst.setString(1,txtNome.getText());
					pst.setString(2,txtLogin.getText());
					pst.setString(3,cboPerfil.getSelectedItem().toString());
					pst.setString(4,txtID.getText());
					//executar a query
					pst.executeUpdate();
					//confirmar para o usuário
					JOptionPane.showMessageDialog(null, "Dados do Usuario editados com sucesso");
					//Limpar os campos
					limparCampos();
					//fechar conexão
					con.close();
				} catch (Exception e) {
					System.out.println(e);
				}
			}
		}//Fim do método editar contato
		
		/**
		 * Método usado para excluir contatos
		 */
		private void excluirUsuario() {
			//System.out.println("Teste do botão");
			//Validação de exclusão
			int confirm = JOptionPane.showConfirmDialog(null, "Confirma a exclusão deste usuario?", "Atenção!!",JOptionPane.YES_NO_OPTION);
			if (confirm == JOptionPane.YES_OPTION) {
				//CRUD - Delete
				String delete = "delete from usuarios where id = ?";
				//tratamento de exceções
				try {
					//abrir a conexão
					con = dao.conectar();
					//preparar a query (instrução sql)
					pst = con.prepareStatement(delete);
					//substituir a ? pelo id do contato
					pst.setString(1, txtID.getText());
					//executar a query
					pst.executeUpdate();
					//limpar campos
					limparCampos();
					//exibir uma mensagem de usuário
					JOptionPane.showMessageDialog(null, "Usuario excluído!");
					//fechar a conexão
					con.close();
				} catch (Exception e) {
					System.out.println(e);
				}
			}
			
		}//Fim do método excluir contato
	}
