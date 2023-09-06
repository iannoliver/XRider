package view;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Iterator;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import model.DAO;
import utils.Validador;

public class Fornecedores extends JDialog {
	DAO dao = new DAO();
	private Connection con;
	private PreparedStatement pst;
	private ResultSet rs;

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
	@SuppressWarnings("rawtypes")
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
	@SuppressWarnings("rawtypes")
	private JComboBox cboUf;

	public static void main(String[] args) {
		try {
			Fornecedores dialog = new Fornecedores();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Fornecedores() {
		setModal(true);
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(Fornecedores.class.getResource("/img/IconBike.png")));
		setTitle("Fornecedores");
		setBounds(100, 100, 800, 600);
		getContentPane().setLayout(null);

		scrollPaneFor = new JScrollPane();
		scrollPaneFor.setVisible(false);

		JLabel lblNewLabel_3_1 = new JLabel("Contato / Fornecedor");
		lblNewLabel_3_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_3_1.setBounds(11, 11, 189, 14);
		getContentPane().add(lblNewLabel_3_1);

		JLabel lblNewLabel_3 = new JLabel("Endereço");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_3.setBounds(10, 259, 189, 14);
		getContentPane().add(lblNewLabel_3);
		scrollPaneFor.setBounds(103, 121, 263, 69);
		getContentPane().add(scrollPaneFor);

		listFornecedores = new JList();
		scrollPaneFor.setViewportView(listFornecedores);
		listFornecedores.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				buscarFornecedoresLista();
			}
		});

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
		btnLimpar.setBounds(593, 502, 48, 48);
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
		btnEditar.setBounds(458, 502, 48, 48);
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
		btnExcluir.setBounds(310, 502, 48, 48);
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
		btnAdicionar.setBounds(152, 502, 48, 48);
		getContentPane().add(btnAdicionar);

		JLabel lblCliente_1_1_2 = new JLabel("CPF/CNPJ:");
		lblCliente_1_1_2.setBounds(10, 52, 83, 20);
		getContentPane().add(lblCliente_1_1_2);

		txtCpfeCnpj = new JTextField();
		txtCpfeCnpj.setColumns(10);
		txtCpfeCnpj.setBounds(79, 52, 199, 20);
		getContentPane().add(txtCpfeCnpj);
		txtCpfeCnpj.setDocument(new Validador(13));

		JLabel lblRazao = new JLabel("Razão Social:");
		lblRazao.setBounds(10, 108, 83, 20);
		getContentPane().add(lblRazao);

		txtRazaoSocial = new JTextField();
		txtRazaoSocial.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				listarFornecedores();
			}
		});
		txtRazaoSocial.setColumns(10);
		txtRazaoSocial.setBounds(103, 108, 263, 20);
		getContentPane().add(txtRazaoSocial);
		txtRazaoSocial.setDocument(new Validador(50));

		JLabel lblVendedor = new JLabel("Vendedor:");
		lblVendedor.setBounds(10, 139, 68, 20);
		getContentPane().add(lblVendedor);

		txtVendedor = new JTextField();
		txtVendedor.setColumns(10);
		txtVendedor.setBounds(70, 139, 173, 20);
		getContentPane().add(txtVendedor);
		txtVendedor.setDocument(new Validador(20));

		JLabel lblNewLabel = new JLabel("ID: ");
		lblNewLabel.setBounds(337, 55, 46, 14);
		getContentPane().add(lblNewLabel);

		txtID = new JTextField();
		txtID.setEditable(false);
		txtID.setColumns(10);
		txtID.setBounds(371, 52, 59, 20);
		getContentPane().add(txtID);

		JLabel lblCliente_1_1 = new JLabel("Endereço:");
		lblCliente_1_1.setBounds(10, 314, 83, 20);
		getContentPane().add(lblCliente_1_1);

		JLabel lblCliente_1_1_3 = new JLabel("Cidade:");
		lblCliente_1_1_3.setBounds(10, 345, 59, 20);
		getContentPane().add(lblCliente_1_1_3);

		JLabel lblCliente_1_1_3_2 = new JLabel("Bairro:");
		lblCliente_1_1_3_2.setBounds(10, 376, 83, 20);
		getContentPane().add(lblCliente_1_1_3_2);

		JLabel lblCliente_1_1_1 = new JLabel("Complemento:");
		lblCliente_1_1_1.setBounds(185, 407, 83, 20);
		getContentPane().add(lblCliente_1_1_1);

		JLabel lblCliente_1_1_3_1 = new JLabel("Cep:");
		lblCliente_1_1_3_1.setBounds(225, 438, 83, 20);
		getContentPane().add(lblCliente_1_1_3_1);

		JLabel lblCliente_1_1_3_2_1 = new JLabel("Uf :");
		lblCliente_1_1_3_2_1.setBounds(10, 407, 53, 20);
		getContentPane().add(lblCliente_1_1_3_2_1);

		txtEndereco = new JTextField();
		txtEndereco.setColumns(10);
		txtEndereco.setBounds(79, 314, 199, 20);
		getContentPane().add(txtEndereco);
		txtEndereco.setDocument(new Validador(20));

		txtCidade = new JTextField();
		txtCidade.setColumns(10);
		txtCidade.setBounds(62, 345, 138, 20);
		getContentPane().add(txtCidade);
		txtCidade.setDocument(new Validador(40));

		txtBairro = new JTextField();
		txtBairro.setColumns(10);
		txtBairro.setBounds(55, 376, 145, 20);
		getContentPane().add(txtBairro);
		txtBairro.setDocument(new Validador(30));

		txtCep = new JTextField();
		txtCep.setColumns(10);
		txtCep.setBounds(263, 438, 104, 20);
		getContentPane().add(txtCep);
		txtCep.setDocument(new Validador(10));

		txtComplemento = new JTextField();
		txtComplemento.setColumns(10);
		txtComplemento.setBounds(275, 407, 83, 20);
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
		btnBuscaCep.setBounds(382, 410, 48, 48);
		getContentPane().add(btnBuscaCep);

		JLabel lblCliente_1_1_3_2_2 = new JLabel("N° :");
		lblCliente_1_1_3_2_2.setBounds(10, 438, 53, 20);
		getContentPane().add(lblCliente_1_1_3_2_2);

		txtNumero = new JTextField();
		txtNumero.setColumns(10);
		txtNumero.setBounds(44, 438, 145, 20);
		getContentPane().add(txtNumero);
		txtNumero.setDocument(new Validador(5));

		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setOpaque(true);
		lblNewLabel_1.setBackground(new Color(58, 163, 157));
		lblNewLabel_1.setBounds(0, 492, 784, 69);
		getContentPane().add(lblNewLabel_1);

		lblNewLabel_2 = new JLabel("Email:");
		lblNewLabel_2.setBounds(306, 142, 46, 14);
		getContentPane().add(lblNewLabel_2);

		txtEmail = new JTextField();
		txtEmail.setBounds(349, 139, 229, 20);
		getContentPane().add(txtEmail);
		txtEmail.setColumns(10);
		txtEmail.setDocument(new Validador(30));

		JLabel lblNomeFantasia = new JLabel("Nome Fantasia:");
		lblNomeFantasia.setBounds(10, 83, 93, 14);
		getContentPane().add(lblNomeFantasia);

		txtNomeFanta = new JTextField();
		txtNomeFanta.setBounds(111, 83, 132, 20);
		getContentPane().add(txtNomeFanta);
		txtNomeFanta.setColumns(10);
		txtNomeFanta.setDocument(new Validador(50));

		JLabel lblFone = new JLabel("Fone:");
		lblFone.setBounds(10, 170, 46, 14);
		getContentPane().add(lblFone);

		txtFone = new JTextField();
		txtFone.setBounds(44, 167, 132, 20);
		getContentPane().add(txtFone);
		txtFone.setColumns(10);
		txtFone.setDocument(new Validador(15));

		JLabel lblSite = new JLabel("Site:");
		lblSite.setBounds(10, 201, 46, 14);
		getContentPane().add(lblSite);

		txtSite = new JTextField();
		txtSite.setBounds(44, 198, 308, 20);
		getContentPane().add(txtSite);
		txtSite.setColumns(10);
		txtSite.setDocument(new Validador(50));

		JLabel lblIe = new JLabel("IE:");
		lblIe.setBounds(355, 201, 46, 14);
		getContentPane().add(lblIe);

		txtIE = new JTextField();
		txtIE.setBounds(384, 198, 195, 20);
		getContentPane().add(txtIE);
		txtIE.setColumns(10);
		txtIE.setDocument(new Validador(20));

		cboUf = new JComboBox();
		cboUf.setModel(new DefaultComboBoxModel(
				new String[] { "", "AC", "Al", "AP", "AM", "BA", "CE", "DF", "DS", "GO", "MA", "MT", "MS", "MG", "PA",
						"PR", "PE", "PI", "RJ", "RN", "RS", "RO", "RR", "SC", "SP", "SE", "TO" }));
		cboUf.setBounds(44, 406, 68, 22);
		getContentPane().add(cboUf);

		JLabel lblNewLabel_1_1 = new JLabel("");
		lblNewLabel_1_1.setOpaque(true);
		lblNewLabel_1_1.setBackground(new Color(58, 163, 157));
		lblNewLabel_1_1.setBounds(0, 248, 784, 38);
		getContentPane().add(lblNewLabel_1_1);

		JLabel lblNewLabel_1_1_1 = new JLabel("");
		lblNewLabel_1_1_1.setOpaque(true);
		lblNewLabel_1_1_1.setBackground(new Color(58, 163, 157));
		lblNewLabel_1_1_1.setBounds(0, 0, 784, 38);
		getContentPane().add(lblNewLabel_1_1_1);

		JLabel lblNewLabel_4 = new JLabel("");
		lblNewLabel_4.setToolTipText("Xrider");
		lblNewLabel_4.setIcon(new ImageIcon(Fornecedores.class.getResource("/img/IconBike.png")));
		lblNewLabel_4.setBounds(656, 56, 128, 128);
		getContentPane().add(lblNewLabel_4);

		JLabel lblNewLabel_2_1 = new JLabel("XRaider");
		lblNewLabel_2_1.setForeground(new Color(0, 0, 0));
		lblNewLabel_2_1.setFont(new Font("Sylfaen", Font.BOLD, 19));
		lblNewLabel_2_1.setBounds(687, 195, 97, 32);
		getContentPane().add(lblNewLabel_2_1);
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
		int linha = listFornecedores.getSelectedIndex();
		if (linha >= 0) {
			String readListaUsuario = "select * from fornecedores where razao like '" + txtRazaoSocial.getText() + "%'"
					+ "order by razao limit " + (linha) + " , 1";
			try {
				con = dao.conectar();
				pst = con.prepareStatement(readListaUsuario);
				rs = pst.executeQuery();
				if (rs.next()) {
					scrollPaneFor.setVisible(false);
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
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		} else {
			scrollPaneFor.setVisible(false);
		}
	}

	@SuppressWarnings("unchecked")
	private void listarFornecedores() {
		DefaultListModel<String> modelo = new DefaultListModel<>();
		listFornecedores.setModel(modelo);
		String readLista = "select * from fornecedores where razao like '" + txtRazaoSocial.getText() + "%'"
				+ "order by razao";
		try {
			con = dao.conectar();
			pst = con.prepareStatement(readLista);
			rs = pst.executeQuery();
			while (rs.next()) {
				scrollPaneFor.setVisible(true);
				modelo.addElement(rs.getString(2));
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
		if (txtRazaoSocial.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Fornecedor");
			txtRazaoSocial.requestFocus();
		} else if (txtFone.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Fone do Fornecedor");
			txtFone.requestFocus();
		} else if (txtNomeFanta.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Nome Fantasia do Fornecedor");
			txtNomeFanta.requestFocus();
		} else if (txtEmail.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Email do Fornecedor");
			txtEmail.requestFocus();
		} else if (txtEndereco.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Endereço do Fornecedor");
			txtEndereco.requestFocus();
		} else if (txtCpfeCnpj.getText().isEmpty()) {
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
		} else {

			String create = "insert into fornecedores (razao, fantasia, fone, vendedor, email, site, endereco, complemento, cpfecnpj, ie, cidade, cep, bairro, uf, numero) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			try {
				con = dao.conectar();
				pst = con.prepareStatement(create);
				pst.setString(1, txtRazaoSocial.getText());
				pst.setString(2, txtNomeFanta.getText());
				pst.setString(3, txtFone.getText());
				pst.setString(4, txtVendedor.getText());
				pst.setString(5, txtEmail.getText());
				pst.setString(6, txtSite.getText());
				pst.setString(7, txtEndereco.getText());
				pst.setString(8, txtComplemento.getText());
				pst.setString(9, txtCpfeCnpj.getText());
				pst.setString(10, txtIE.getText());
				pst.setString(11, txtCidade.getText());
				pst.setString(12, txtCep.getText());
				pst.setString(13, txtBairro.getText());
				pst.setString(14, cboUf.getSelectedItem().toString());
				pst.setString(15, txtNumero.getText());
				pst.executeUpdate();
				JOptionPane.showMessageDialog(null, "Fornecedor adicionado!!");
				limparCampos();
				con.close();
			} catch (java.sql.SQLIntegrityConstraintViolationException e1) {
				JOptionPane.showMessageDialog(null,
						"Fornecedor não adicionado.\nEste CPF/CNPJ já está sendo utilizado.");
				txtCpfeCnpj.setText(null);
				txtCpfeCnpj.requestFocus();
			} catch (Exception e2) {
				System.out.println(e2);
			}
		}
	}

	private void editar() {
		if (txtRazaoSocial.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Fornecedor");
			txtRazaoSocial.requestFocus();
		} else if (txtFone.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Fone do Fornecedor");
			txtFone.requestFocus();
		} else if (txtNomeFanta.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Nome Fantasia do Fornecedor");
			txtNomeFanta.requestFocus();
		} else if (txtEmail.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Email do Fornecedor");
			txtEmail.requestFocus();
		} else if (txtEndereco.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Endereço do Fornecedor");
			txtEndereco.requestFocus();
		} else if (txtCpfeCnpj.getText().isEmpty()) {
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
		} else {
			String update = "update fornecedores set razao = ?, fantasia = ?, fone = ?, vendedor = ?, email = ?, site = ?, endereco = ?, complemento = ?, cpfecnpj = ?, ie = ?, cidade = ?, cep = ?, bairro = ?, uf = ?, numero = ? where idfornecedor = ?";
			try {
				con = dao.conectar();
				pst = con.prepareStatement(update);
				pst.setString(1, txtRazaoSocial.getText());
				pst.setString(2, txtNomeFanta.getText());
				pst.setString(3, txtFone.getText());
				pst.setString(4, txtVendedor.getText());
				pst.setString(5, txtEmail.getText());
				pst.setString(6, txtSite.getText());
				pst.setString(7, txtEndereco.getText());
				pst.setString(8, txtComplemento.getText());
				pst.setString(9, txtCpfeCnpj.getText());
				pst.setString(10, txtIE.getText());
				pst.setString(11, txtCidade.getText());
				pst.setString(12, txtCep.getText());
				pst.setString(13, txtBairro.getText());
				pst.setString(14, cboUf.getSelectedItem().toString());
				pst.setString(15, txtNumero.getText());
				pst.setString(16, txtID.getText());
				pst.executeUpdate();
				JOptionPane.showMessageDialog(null, "Dados do fornecedor editados com sucesso!!");
				limparCampos();
				con.close();
			} catch (java.sql.SQLIntegrityConstraintViolationException e1) {
				JOptionPane.showMessageDialog(null, "Fornecedor não editado.\nEste CPF/CNPJ já está sendo utilizado.");
				txtCpfeCnpj.setText(null);
				txtCpfeCnpj.requestFocus();
			} catch (Exception e2) {
				System.out.println(e2);
			}
		}
	}

	private void excluir() {
		if (txtCpfeCnpj.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "SEM IDENTIFICAÇÃO -> Preencha o CPF/CNPJ do Fornecedor");
		} else {
			int confirm = JOptionPane.showConfirmDialog(null, "Confirma a exclusão deste Fornecedor?", "Atenção!!",
					JOptionPane.YES_NO_OPTION);
			if (confirm == JOptionPane.YES_OPTION) {
				String delete = "delete from fornecedores where idfornecedor = ?";
				try {
					con = dao.conectar();
					pst = con.prepareStatement(delete);
					pst.setString(1, txtID.getText());
					pst.executeUpdate();
					limparCampos();
					JOptionPane.showMessageDialog(null, "Fornecedor excluído!");
					con.close();
				} catch (java.sql.SQLIntegrityConstraintViolationException e1) {
					JOptionPane.showMessageDialog(null, "Fornecedor não pode ser excluido.\nServiço pendente!!");
				} catch (Exception e2) {
					System.out.println(e2);
				}
			}
		}

	}
}
