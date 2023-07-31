package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import model.DAO;
import utils.Validador;

import java.awt.Toolkit;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Iterator;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JList;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.DefaultComboBoxModel;

public class Fornecedores extends JDialog {
	DAO dao = new DAO();
	private Connection con;
	private PreparedStatement pst;
	private ResultSet rs;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField txtCpfeCnpj;
	private JTextField txtRazaoSocial;
	private JTextField txtVendedor;
	private JTextField txtID;
	private JTextField txtEndereco;
	private JTextField txtCidade;
	private JTextField txtBairro;
	private JTextField txtComplemento;
	private JTextField txtNumero;
	private JTextField txtCep;
	private JScrollPane scrollPaneFor;
	private JList listFornecedores;
	private JButton btnAdicionar;
	private JButton btnExcluir;
	private JButton btnEditar;
	private JButton btnLimpar;
	private JLabel lblNewLabel_2;
	private JTextField txtEmail;
	private JTextField txtNomeFanta;
	private JTextField txtFone;
	private JTextField txtSite;
	private JTextField txtIE;
	private JComboBox cboUf;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			Fornecedores dialog = new Fornecedores();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public Fornecedores() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(Fornecedores.class.getResource("/img/IconBike.png")));
		setTitle("Fornecedores");
		setBounds(100, 100, 615, 442);
		getContentPane().setLayout(null);
		
		scrollPaneFor = new JScrollPane();
		scrollPaneFor.setVisible(false);
		scrollPaneFor.setBounds(89, 69, 263, 69);
		getContentPane().add(scrollPaneFor);
		
		listFornecedores = new JList();
		listFornecedores.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				buscarFornecedoresLista();
			}
		});
		scrollPaneFor.setViewportView(listFornecedores);
		
		btnLimpar = new JButton("");
		btnLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limparCampos();
			}
		});
		btnLimpar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnLimpar.setIcon(new ImageIcon(Fornecedores.class.getResource("/img/Burracha.png")));
		btnLimpar.setToolTipText("Limpar");
		btnLimpar.setContentAreaFilled(false);
		btnLimpar.setBorder(null);
		btnLimpar.setBounds(421, 355, 48, 48);
		getContentPane().add(btnLimpar);
		
		btnEditar = new JButton("");
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editar();
			}
		});
		btnEditar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnEditar.setIcon(new ImageIcon(Fornecedores.class.getResource("/img/EditarCli.png")));
		btnEditar.setToolTipText("Editar");
		btnEditar.setEnabled(false);
		btnEditar.setContentAreaFilled(false);
		btnEditar.setBorder(null);
		btnEditar.setBounds(327, 355, 48, 48);
		getContentPane().add(btnEditar);
		
		btnExcluir = new JButton("");
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				excluir();
			}
		});
		btnExcluir.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnExcluir.setIcon(new ImageIcon(Fornecedores.class.getResource("/img/Lixeira2.png")));
		btnExcluir.setToolTipText("Excluir");
		btnExcluir.setEnabled(false);
		btnExcluir.setContentAreaFilled(false);
		btnExcluir.setBorder(null);
		btnExcluir.setBounds(230, 355, 48, 48);
		getContentPane().add(btnExcluir);
		
		btnAdicionar = new JButton("");
		btnAdicionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				adicionar();
			}
		});
		btnAdicionar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnAdicionar.setIcon(new ImageIcon(Fornecedores.class.getResource("/img/Add.png")));
		btnAdicionar.setToolTipText("Adicionar");
		btnAdicionar.setContentAreaFilled(false);
		btnAdicionar.setBorder(null);
		btnAdicionar.setBounds(116, 355, 48, 48);
		getContentPane().add(btnAdicionar);
		
		JLabel lblCliente_1_1_2 = new JLabel("CPF/CNPJ:");
		lblCliente_1_1_2.setBounds(10, 11, 83, 20);
		getContentPane().add(lblCliente_1_1_2);
		
		txtCpfeCnpj = new JTextField();
		txtCpfeCnpj.setColumns(10);
		txtCpfeCnpj.setBounds(79, 11, 199, 20);
		getContentPane().add(txtCpfeCnpj);
		txtCpfeCnpj.setDocument(new Validador(13));
		
		JLabel lblRazao = new JLabel("Razão Social:");
		lblRazao.setBounds(10, 51, 83, 20);
		getContentPane().add(lblRazao);
		
		txtRazaoSocial = new JTextField();
		txtRazaoSocial.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				listarFornecedores();
			}
		});
		txtRazaoSocial.setColumns(10);
		txtRazaoSocial.setBounds(89, 51, 263, 20);
		getContentPane().add(txtRazaoSocial);
		txtRazaoSocial.setDocument(new Validador(50));
		
		JLabel lblVendedor = new JLabel("Vendedor:");
		lblVendedor.setBounds(11, 82, 68, 20);
		getContentPane().add(lblVendedor);
		
		txtVendedor = new JTextField();
		txtVendedor.setColumns(10);
		txtVendedor.setBounds(62, 82, 173, 20);
		getContentPane().add(txtVendedor);
		txtVendedor.setDocument(new Validador(20));
		
		JLabel lblNewLabel = new JLabel("ID: ");
		lblNewLabel.setBounds(384, 14, 46, 14);
		getContentPane().add(lblNewLabel);
		
		txtID = new JTextField();
		txtID.setEditable(false);
		txtID.setColumns(10);
		txtID.setBounds(410, 11, 59, 20);
		getContentPane().add(txtID);
		
		JLabel lblCliente_1_1 = new JLabel("Endereço:");
		lblCliente_1_1.setBounds(25, 180, 83, 20);
		getContentPane().add(lblCliente_1_1);
		
		JLabel lblCliente_1_1_3 = new JLabel("Cidade:");
		lblCliente_1_1_3.setBounds(25, 211, 59, 20);
		getContentPane().add(lblCliente_1_1_3);
		
		JLabel lblCliente_1_1_3_2 = new JLabel("Bairro:");
		lblCliente_1_1_3_2.setBounds(25, 242, 83, 20);
		getContentPane().add(lblCliente_1_1_3_2);
		
		JLabel lblCliente_1_1_1 = new JLabel("Complemento:");
		lblCliente_1_1_1.setBounds(345, 216, 83, 20);
		getContentPane().add(lblCliente_1_1_1);
		
		JLabel lblCliente_1_1_3_1 = new JLabel("Cep:");
		lblCliente_1_1_3_1.setBounds(372, 258, 83, 20);
		getContentPane().add(lblCliente_1_1_3_1);
		
		JLabel lblCliente_1_1_3_2_1 = new JLabel("Uf :");
		lblCliente_1_1_3_2_1.setBounds(25, 273, 53, 20);
		getContentPane().add(lblCliente_1_1_3_2_1);
		
		txtEndereco = new JTextField();
		txtEndereco.setColumns(10);
		txtEndereco.setBounds(79, 180, 199, 20);
		getContentPane().add(txtEndereco);
		txtEndereco.setDocument(new Validador(20));
		
		txtCidade = new JTextField();
		txtCidade.setColumns(10);
		txtCidade.setBounds(62, 211, 138, 20);
		getContentPane().add(txtCidade);
		txtCidade.setDocument(new Validador(40));
		
		txtBairro = new JTextField();
		txtBairro.setColumns(10);
		txtBairro.setBounds(62, 242, 145, 20);
		getContentPane().add(txtBairro);
		txtBairro.setDocument(new Validador(30));
		
		txtCep = new JTextField();
		txtCep.setColumns(10);
		txtCep.setBounds(402, 258, 104, 20);
		getContentPane().add(txtCep);
		txtCep.setDocument(new Validador(10));
		
		txtComplemento = new JTextField();
		txtComplemento.setColumns(10);
		txtComplemento.setBounds(423, 216, 83, 20);
		getContentPane().add(txtComplemento);
		txtCep.setDocument(new Validador(15));
		
		JButton btnBuscaCep = new JButton("");
		btnBuscaCep.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buscaCep();
			}
		});
		btnBuscaCep.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnBuscaCep.setIcon(new ImageIcon(Fornecedores.class.getResource("/img/BuscaCEP.png")));
		btnBuscaCep.setToolTipText("BuscaCep");
		btnBuscaCep.setContentAreaFilled(false);
		btnBuscaCep.setBorder(null);
		btnBuscaCep.setBounds(530, 243, 48, 48);
		getContentPane().add(btnBuscaCep);
		
		JLabel lblCliente_1_1_3_2_2 = new JLabel("N° :");
		lblCliente_1_1_3_2_2.setBounds(25, 303, 53, 20);
		getContentPane().add(lblCliente_1_1_3_2_2);
		
		txtNumero = new JTextField();
		txtNumero.setColumns(10);
		txtNumero.setBounds(52, 303, 145, 20);
		getContentPane().add(txtNumero);
		txtNumero.setDocument(new Validador(5));
		
		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setOpaque(true);
		lblNewLabel_1.setBackground(new Color(58, 163, 157));
		lblNewLabel_1.setBounds(0, 334, 608, 69);
		getContentPane().add(lblNewLabel_1);
		
		lblNewLabel_2 = new JLabel("Email:");
		lblNewLabel_2.setBounds(243, 85, 46, 14);
		getContentPane().add(lblNewLabel_2);
		
		txtEmail = new JTextField();
		txtEmail.setBounds(277, 82, 229, 20);
		getContentPane().add(txtEmail);
		txtEmail.setColumns(10);
		txtEmail.setDocument(new Validador(30));
		
		JLabel lblNomeFantasia = new JLabel("Nome Fantasia:");
		lblNomeFantasia.setBounds(362, 54, 93, 14);
		getContentPane().add(lblNomeFantasia);
		
		txtNomeFanta = new JTextField();
		txtNomeFanta.setBounds(446, 51, 132, 20);
		getContentPane().add(txtNomeFanta);
		txtNomeFanta.setColumns(10);
		txtNomeFanta.setDocument(new Validador(50));
		
		JLabel lblFone = new JLabel("Fone:");
		lblFone.setBounds(10, 113, 46, 14);
		getContentPane().add(lblFone);
		
		txtFone = new JTextField();
		txtFone.setBounds(44, 113, 132, 20);
		getContentPane().add(txtFone);
		txtFone.setColumns(10);
		txtFone.setDocument(new Validador(15));
		
		JLabel lblSite = new JLabel("Site:");
		lblSite.setBounds(10, 141, 46, 14);
		getContentPane().add(lblSite);
		
		txtSite = new JTextField();
		txtSite.setBounds(44, 138, 308, 20);
		getContentPane().add(txtSite);
		txtSite.setColumns(10);
		txtSite.setDocument(new Validador(50));
		
		JLabel lblIe = new JLabel("IE:");
		lblIe.setBounds(359, 141, 46, 14);
		getContentPane().add(lblIe);
		
		txtIE = new JTextField();
		txtIE.setBounds(383, 138, 195, 20);
		getContentPane().add(txtIE);
		txtIE.setColumns(10);
		txtIE.setDocument(new Validador(20));
		
		cboUf = new JComboBox();
		cboUf.setModel(new DefaultComboBoxModel(new String[] {"", "AC", "Al", "AP", "AM", "BA", "CE", "DF", "DS", "GO", "MA", "MT", "MS", "MG", "PA", "PR", "PE", "PI", "RJ", "RN", "RS", "RO", "RR", "SC", "SP", "SE", "TO"}));
		cboUf.setBounds(52, 272, 68, 22);
		getContentPane().add(cboUf);
	}
	
	private void limparCampos() {
		txtID.setText(null);
		txtRazaoSocial.setText(null);
		txtVendedor.setText(null);
		txtCpfeCnpj.setText(null);
		txtEndereco.setText(null);
		txtComplemento.setText(null);
		txtIE.setText(null);
		txtFone.setText(null);
		txtSite.setText(null);
		txtNomeFanta.setText(null);
		txtCidade.setText(null);
		txtCep.setText(null);
		txtBairro.setText(null);
		txtEmail.setText(null);
		cboUf.setSelectedItem("");
		txtNumero.setText(null);
		btnAdicionar.setEnabled(true);
		btnEditar.setEnabled(false);
		btnExcluir.setEnabled(false);
		scrollPaneFor.setVisible(false);
	}
	
	/**
	 * buscarCep
	 */
	private void buscaCep() {
		String logradouro = "";
		String tipoLogradouro = "";
		String resultado = null;
		String cep = txtCep.getText();
		try {
			URL url = new URL("http://cep.republicavirtual.com.br/web_cep.php?cep=" + cep + "&formato=xml");
			SAXReader xml = new SAXReader();
			Document documento = xml.read(url);
			Element root = documento.getRootElement();
			for (Iterator<Element> it = root.elementIterator(); it.hasNext();) {
				Element element = it.next();
				if (element.getQualifiedName().equals("cidade")) {
					txtCidade.setText(element.getText());
				}
				if (element.getQualifiedName().equals("bairro")) {
					txtBairro.setText(element.getText());
				}
				if (element.getQualifiedName().equals("uf")) {
					cboUf.setSelectedItem(element.getText());
				}
				if (element.getQualifiedName().equals("tipo_logradouro")) {
					tipoLogradouro = element.getText();
				}
				if (element.getQualifiedName().equals("logradouro")) {
					logradouro = element.getText();
				}
				if (element.getQualifiedName().equals("resultado")) {
					resultado = element.getText();
					if (resultado.equals("1")) {
						System.out.println("OK");
					} else {
						JOptionPane.showMessageDialog(null, "CEP não encontrado");
					}
				}
			}
			txtEndereco.setText(tipoLogradouro + " " + logradouro);
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	private void buscarFornecedoresLista() {
		//variável que captura o indice da linha da lista
		int linha = listFornecedores.getSelectedIndex();
		if(linha >= 0) {
			//Query (instrução SQL)
			//limit (0.1) -> seleciona o indice 0 e 1 usuário da lista
			String readListaUsuario = "select * from fornecedores where razao like '" + txtRazaoSocial.getText() + "%'"	+ "order by razao limit " + (linha) + " , 1";
			try {
				con = dao.conectar();
				pst = con.prepareStatement(readListaUsuario);
				rs = pst.executeQuery();
				if (rs.next()) {
					//esconder a lista
					scrollPaneFor.setVisible(false);
					//setar os campos
					txtID.setText(rs.getString(1));
					txtRazaoSocial.setText(rs.getString(2));
					txtNomeFanta.setText(rs.getString(3));
					txtFone.setText(rs.getString(4));
					txtVendedor.setText(rs.getString(5));
					txtEmail.setText(rs.getString(6));
					txtSite.setText(rs.getString(7));
					txtEndereco.setText(rs.getString(8));
					txtComplemento.setText(rs.getString(9));
					txtCpfeCnpj.setText(rs.getString(10));
					txtIE.setText(rs.getString(11));
					txtCidade.setText(rs.getString(12));
					txtCep.setText(rs.getString(13));
					txtBairro.setText(rs.getString(14));
					cboUf.setSelectedItem(rs.getString(15));
					txtNumero.setText(rs.getString(16));
					btnAdicionar.setEnabled(false);
					btnEditar.setEnabled(true);
					btnExcluir.setEnabled(true);
				} else {
					JOptionPane.showMessageDialog(null, "Fornecedor inexistente");
				}
				//fechar conexão
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		} else {
			// se não existir no banco um usuário da lista
				scrollPaneFor.setVisible(false);
		}
	}
	
	/**
	 * método usado para listar o nome dos usuarios da lista
	 */
	private void listarFornecedores() {
		//a linha abaixo cria um objeto usando como referência um vetor dinâmico, 
		//este objeto ira temporariamente armazenar os nomes
		DefaultListModel<String> modelo = new DefaultListModel<>();
		//setar o modelo (vetor da lista)
		listFornecedores.setModel(modelo);
		//Query (instrução sql)
		String readLista = "select * from fornecedores where razao like '" + txtRazaoSocial.getText() + "%'" + "order by razao";
		try {
			con = dao.conectar();
			pst= con.prepareStatement(readLista);
			rs = pst.executeQuery();
			//uso do while para trazer os usuarios enqunto existir
			while(rs.next()) {
				//mostrar barra de rolagem
				scrollPaneFor.setVisible(true);
				//adicionar os usuarios ao vetor -> lista
				modelo.addElement(rs.getString(2));
				//esconder a lista se nenhuma letra for digitada
				if (txtRazaoSocial.getText().isEmpty()) {
					scrollPaneFor.setVisible(false);
				}
			}
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	private void adicionar() {
		//System.out.println("teste");
		//Validação de campos obrigatórios
		if (txtRazaoSocial.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Fornecedor");
			txtRazaoSocial.requestFocus();
		} else if (txtFone.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Fone do Fornecedor");
			txtFone.requestFocus();
		}else if (txtNomeFanta.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Nome Fantasia do Fornecedor");
			txtNomeFanta.requestFocus();
		}else if (txtEmail.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Email do Fornecedor");
			txtEmail.requestFocus();
		}else if (txtEndereco.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Endereço do Fornecedor");
			txtEndereco.requestFocus();
		}  else if (txtCpfeCnpj.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o CPF/CNPJ do Fornecedor");
			txtCpfeCnpj.requestFocus();
		} else if (txtCidade.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha a Cidade do Fornecedor");
			txtCidade.requestFocus();
		} else if (txtCep.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o CEP do Fornecedor");
			txtCep.requestFocus();
		} else if (txtNumero.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Número do Fornecedor");
			txtNumero.requestFocus();
		}
		else {
			
			//lógica principal
			String create = "insert into fornecedores (razao, fantasia, fone, vendedor, email, site, endereco, complemento, cpfecnpj, ie, cidade, cep, bairro, uf, numero) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			//tratamento de exceções
			try {
				//abrir a conexão
				con = dao.conectar();
				//PREPARA A EXECUÇÃO DA QUERY(instrução SQL - CRUD Crate)
				pst = con.prepareStatement(create);
				pst.setString(1,  txtRazaoSocial.getText());
				pst.setString(2,  txtNomeFanta.getText());
				pst.setString(3,  txtFone.getText());
				pst.setString(4,  txtVendedor.getText());
				pst.setString(5,  txtEmail.getText());
				pst.setString(6,  txtSite.getText());
				pst.setString(7,  txtEndereco.getText());
				pst.setString(8,  txtComplemento.getText());
				pst.setString(9,  txtCpfeCnpj.getText());
				pst.setString(10,  txtIE.getText());
				pst.setString(11,  txtCidade.getText());
				pst.setString(12,  txtCep.getText());
				pst.setString(13,  txtBairro.getText());
				pst.setString(14,  cboUf.getSelectedItem().toString());
				pst.setString(15,  txtNumero.getText());
				//executa a query(Instrução SQL (CRUD - Create)
				pst.executeUpdate();
				//confirmar
				JOptionPane.showMessageDialog(null, "Fornecedor adicionado!!");
				//limpar campos
				limparCampos();
				//fechar conexão
				con.close();
			} catch (java.sql.SQLIntegrityConstraintViolationException e1) {
				JOptionPane.showMessageDialog(null, "Fornecedor não adicionado.\nEste CPF/CNPJ já está sendo utilizado.");
				txtCpfeCnpj.setText(null);
				txtCpfeCnpj.requestFocus();
			} catch (Exception e2) {
				System.out.println(e2);
			}
		}
		}// Fim do método adicionar
	
	private void editar() {
		//System.out.println("teste do método editar");
		//Validação dos campos obrigatórios
		if (txtRazaoSocial.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Fornecedor");
			txtRazaoSocial.requestFocus();
		} else if (txtFone.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Fone do Fornecedor");
			txtFone.requestFocus();
		}else if (txtNomeFanta.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Nome Fantasia do Fornecedor");
			txtNomeFanta.requestFocus();
		}else if (txtEmail.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Email do Fornecedor");
			txtEmail.requestFocus();
		}else if (txtEndereco.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Endereço do Fornecedor");
			txtEndereco.requestFocus();
		}  else if (txtCpfeCnpj.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o CPF/CNPJ do Fornecedor");
			txtCpfeCnpj.requestFocus();
		} else if (txtCidade.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha a Cidade do Fornecedor");
			txtCidade.requestFocus();
		} else if (txtCep.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o CEP do Fornecedor");
			txtCep.requestFocus();
		} else if (txtNumero.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Número do Fornecedor");
			txtNumero.requestFocus();
		}
		else {
			//Lógica principal
			//CRUD - Update
			String update = "update fornecedores set razao = ?, fantasia = ?, fone = ?, vendedor = ?, email = ?, site = ?, endereco = ?, complemento = ?, cpfecnpj = ?, ie = ?, cidade = ?, cep = ?, bairro = ?, uf = ?, numero = ? where idfornecedor = ?";
			try {
				//abrir conexão
				con = dao.conectar();
				//prepara a query (instrução sql)
				pst = con.prepareStatement(update);
				pst.setString(1,txtRazaoSocial.getText());
				pst.setString(2,txtNomeFanta.getText());
				pst.setString(3,txtFone.getText());
				pst.setString(4,txtVendedor.getText());
				pst.setString(5,txtEmail.getText());
				pst.setString(6,txtSite.getText());
				pst.setString(7,txtEndereco.getText());
				pst.setString(8,txtComplemento.getText());
				pst.setString(9,txtCpfeCnpj.getText());
				pst.setString(10,txtIE.getText());
				pst.setString(11,txtCidade.getText());
				pst.setString(12,txtCep.getText());
				pst.setString(13,txtBairro.getText());
				pst.setString(14,cboUf.getSelectedItem().toString());
				pst.setString(15,txtNumero.getText());
				pst.setString(16,txtID.getText());
				//executar a query
				pst.executeUpdate();
				//confirmar para o usuário
				JOptionPane.showMessageDialog(null, "Dados do fornecedor editados com sucesso!!");
				//Limpar os campos
				limparCampos();
				//fechar conexão
				con.close();
			}catch (java.sql.SQLIntegrityConstraintViolationException e1) {
				JOptionPane.showMessageDialog(null, "Fornecedor não editado.\nEste CPF/CNPJ já está sendo utilizado.");
				txtCpfeCnpj.setText(null);
				txtCpfeCnpj.requestFocus();
			} catch (Exception e2) {
				System.out.println(e2);
			}
		}
	}//Fim do método editar contato
	
	private void excluir() {
		if (txtCpfeCnpj.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "SEM IDENTIFICAÇÃO -> Preencha o CPF/CNPJ do Fornecedor");
		}
		else {
			//System.out.println("Teste do botão");
			//Validação de exclusão
			int confirm = JOptionPane.showConfirmDialog(null, "Confirma a exclusão deste Fornecedor?", "Atenção!!",JOptionPane.YES_NO_OPTION);
			if (confirm == JOptionPane.YES_OPTION) {
				//CRUD - Delete
				String delete = "delete from fornecedores where idfornecedor = ?";
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
					JOptionPane.showMessageDialog(null, "Fornecedor excluído!");
					//fechar a conexão
					con.close();
				}  catch (java.sql.SQLIntegrityConstraintViolationException e1) {
					JOptionPane.showMessageDialog(null, "Fornecedor não pode ser excluido.\nServiço pendente!!");
				} catch (Exception e2) {
					System.out.println(e2);
				}
			}
		}
		
	}//Fim do método excluir contato
}
