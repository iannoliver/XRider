package view;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import model.DAO;
import utils.Validador;
import javax.swing.JScrollPane;
import javax.swing.JList;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.Font;

@SuppressWarnings("serial")
public class Clientes extends JDialog {
	DAO dao = new DAO();
	private Connection con;
	private PreparedStatement pst;
	private ResultSet rs;

	private JTextField txtCliente;
	private JTextField txtContato;
	private JTextField txtEndereco;
	private JTextField txtComplemento;
	private JTextField txtCpfeCnpj;
	private JTextField txtCidade;
	private JTextField txtCep;
	private JTextField txtIdcli;
	private JButton btnExcluir;
	private JButton btnAdicionar;
	private JButton btnEditar;
	private JButton btnLimpar;
	private JTextField txtBairro;
	private JTextField txtNumero;
	@SuppressWarnings("rawtypes")
	private JComboBox cboUf;
	private JScrollPane scrollPaneClientes;
	@SuppressWarnings("rawtypes")
	private JList listClientes;
	private JLabel lblNewLabel_2;
	private JLabel lblNewLabel_3;
	private JLabel lblNewLabel_4;
	private JLabel lblNewLabel_5;
	private JLabel lblNewLabel_2_1;
	private JLabel lblNewLabel_3_1;
	private JLabel lblNewLabel_6;
	private JTextField txtEmail;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Clientes dialog = new Clientes();
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Clientes() {
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(Clientes.class.getResource("/img/IconBike.png")));
		setTitle("Clientes");
		setBounds(100, 100, 800, 600);
		getContentPane().setLayout(null);

		scrollPaneClientes = new JScrollPane();
		scrollPaneClientes.setVisible(false);

		lblNewLabel_3_1 = new JLabel("Contato / Cliente");
		lblNewLabel_3_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_3_1.setBounds(10, 11, 189, 14);
		getContentPane().add(lblNewLabel_3_1);

		lblNewLabel_3 = new JLabel("Endereço");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_3.setBounds(8, 236, 189, 14);
		getContentPane().add(lblNewLabel_3);
		scrollPaneClientes.setBounds(59, 57, 263, 63);
		getContentPane().add(scrollPaneClientes);

		listClientes = new JList();
		scrollPaneClientes.setViewportView(listClientes);
		listClientes.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				buscarClientesLista();
			}
		});

		JLabel lblCliente = new JLabel("Cliente:");
		lblCliente.setBounds(10, 42, 83, 20);
		getContentPane().add(lblCliente);

		txtCliente = new JTextField();
		txtCliente.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				listarClientes();
			}
		});
		txtCliente.setBounds(59, 42, 263, 20);
		getContentPane().add(txtCliente);
		txtCliente.setColumns(10);
		txtCliente.setDocument(new Validador(50));

		JLabel lblCliente_1 = new JLabel("Contato:");
		lblCliente_1.setBounds(10, 104, 83, 20);
		getContentPane().add(lblCliente_1);

		txtContato = new JTextField();
		txtContato.setColumns(10);
		txtContato.setBounds(67, 104, 173, 20);
		getContentPane().add(txtContato);
		txtContato.setDocument(new Validador(15));

		JLabel lblCliente_1_1 = new JLabel("Endereço:");
		lblCliente_1_1.setBounds(10, 315, 83, 20);
		getContentPane().add(lblCliente_1_1);

		txtEndereco = new JTextField();
		txtEndereco.setColumns(10);
		txtEndereco.setBounds(80, 315, 199, 20);
		getContentPane().add(txtEndereco);
		txtEndereco.setDocument(new Validador(50));

		JLabel lblCliente_1_1_1 = new JLabel("Complemento:");
		lblCliente_1_1_1.setBounds(10, 284, 83, 20);
		getContentPane().add(lblCliente_1_1_1);

		JLabel lblCliente_1_1_2 = new JLabel("CPF/CNPJ:");
		lblCliente_1_1_2.setBounds(10, 73, 83, 20);
		getContentPane().add(lblCliente_1_1_2);

		JLabel lblCliente_1_1_3 = new JLabel("Cidade:");
		lblCliente_1_1_3.setBounds(10, 346, 83, 20);
		getContentPane().add(lblCliente_1_1_3);

		txtComplemento = new JTextField();
		txtComplemento.setColumns(10);
		txtComplemento.setBounds(101, 284, 83, 20);
		getContentPane().add(txtComplemento);
		txtComplemento.setDocument(new Validador(10));

		txtCpfeCnpj = new JTextField();
		txtCpfeCnpj.setColumns(10);
		txtCpfeCnpj.setBounds(80, 73, 199, 20);
		getContentPane().add(txtCpfeCnpj);
		txtCpfeCnpj.setDocument(new Validador(14));

		txtCidade = new JTextField();
		txtCidade.setColumns(10);
		txtCidade.setBounds(59, 346, 138, 20);
		getContentPane().add(txtCidade);
		txtCidade.setDocument(new Validador(40));

		JLabel lblCliente_1_1_3_1 = new JLabel("Cep:");
		lblCliente_1_1_3_1.setBounds(249, 439, 83, 20);
		getContentPane().add(lblCliente_1_1_3_1);

		txtCep = new JTextField();
		txtCep.setBounds(287, 439, 104, 20);
		getContentPane().add(txtCep);
		txtCep.setColumns(10);
		txtCep.setDocument(new Validador(10));

		btnExcluir = new JButton("");
		btnExcluir.setEnabled(false);
		btnExcluir.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				excluir();
			}
		});
		btnExcluir.setContentAreaFilled(false);
		btnExcluir.setBorder(null);
		btnExcluir.setIcon(new ImageIcon(Clientes.class.getResource("/img/Lixeira2.png")));
		btnExcluir.setToolTipText("Excluir");
		btnExcluir.setBounds(274, 502, 48, 48);
		getContentPane().add(btnExcluir);

		btnAdicionar = new JButton("");
		btnAdicionar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnAdicionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				adicionar();
			}
		});
		btnAdicionar.setContentAreaFilled(false);
		btnAdicionar.setBorder(null);
		btnAdicionar.setIcon(new ImageIcon(Clientes.class.getResource("/img/Add.png")));
		btnAdicionar.setToolTipText("Adicionar");
		btnAdicionar.setBounds(59, 502, 48, 48);
		getContentPane().add(btnAdicionar);

		btnLimpar = new JButton("");
		btnLimpar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limparCampos();
			}
		});
		btnLimpar.setIcon(new ImageIcon(Clientes.class.getResource("/img/Burracha.png")));
		btnLimpar.setContentAreaFilled(false);
		btnLimpar.setBorder(null);
		btnLimpar.setToolTipText("Limpar");
		btnLimpar.setBounds(662, 502, 48, 48);
		getContentPane().add(btnLimpar);

		btnEditar = new JButton("");
		btnEditar.setEnabled(false);
		btnEditar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editar();
			}
		});
		btnEditar.setIcon(new ImageIcon(Clientes.class.getResource("/img/EditarCli.png")));
		btnEditar.setContentAreaFilled(false);
		btnEditar.setBorder(null);
		btnEditar.setToolTipText("Editar");
		btnEditar.setBounds(455, 502, 48, 48);
		getContentPane().add(btnEditar);

		JLabel lblNewLabel = new JLabel("ID: ");
		lblNewLabel.setBounds(368, 45, 46, 14);
		getContentPane().add(lblNewLabel);

		txtIdcli = new JTextField();
		txtIdcli.setEditable(false);
		txtIdcli.setBounds(401, 42, 59, 20);
		getContentPane().add(txtIdcli);
		txtIdcli.setColumns(10);

		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setOpaque(true);
		lblNewLabel_1.setBackground(new Color(33, 146, 123));
		lblNewLabel_1.setBounds(0, 480, 784, 81);
		getContentPane().add(lblNewLabel_1);

		JButton btnBuscaCep = new JButton("");
		btnBuscaCep.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnBuscaCep.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buscaCep();
			}
		});
		btnBuscaCep.setContentAreaFilled(false);
		btnBuscaCep.setBorder(null);
		btnBuscaCep.setToolTipText("BuscaCep");
		btnBuscaCep.setIcon(new ImageIcon(Clientes.class.getResource("/img/BuscaCEP.png")));
		btnBuscaCep.setBounds(401, 411, 48, 48);
		getContentPane().add(btnBuscaCep);

		JLabel lblCliente_1_1_3_2 = new JLabel("Bairro:");
		lblCliente_1_1_3_2.setBounds(10, 377, 83, 20);
		getContentPane().add(lblCliente_1_1_3_2);

		JLabel lblCliente_1_1_3_2_1 = new JLabel("Uf :");
		lblCliente_1_1_3_2_1.setBounds(10, 408, 53, 20);
		getContentPane().add(lblCliente_1_1_3_2_1);

		txtBairro = new JTextField();
		txtBairro.setBounds(59, 377, 145, 20);
		getContentPane().add(txtBairro);
		txtBairro.setColumns(10);
		txtCep.setDocument(new Validador(15));

		JLabel lblCliente_1_1_3_2_2 = new JLabel("N° :");
		lblCliente_1_1_3_2_2.setBounds(10, 439, 53, 20);
		getContentPane().add(lblCliente_1_1_3_2_2);

		txtNumero = new JTextField();
		txtNumero.setColumns(10);
		txtNumero.setBounds(39, 439, 145, 20);
		getContentPane().add(txtNumero);
		txtNumero.setDocument(new Validador(5));

		cboUf = new JComboBox();
		cboUf.setModel(new DefaultComboBoxModel(
				new String[] { "", "AC", "AL", "AP", "AM", "BA", "CE", "DF", "ES", "GO", "MA", "MT", "MS", "MG", "PA",
						"PB", "PR", "PE", "PI", "RJ", "RN", "RS", "RO", "RR", "SC", "SP", "TO" }));
		cboUf.setBounds(39, 407, 68, 22);
		getContentPane().add(cboUf);

		lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setOpaque(true);
		lblNewLabel_2.setBackground(new Color(33, 146, 123));
		lblNewLabel_2.setBounds(0, 228, 784, 31);
		getContentPane().add(lblNewLabel_2);

		lblNewLabel_4 = new JLabel("");
		lblNewLabel_4.setOpaque(true);
		lblNewLabel_4.setBackground(new Color(33, 146, 123));
		lblNewLabel_4.setBounds(0, 0, 784, 31);
		getContentPane().add(lblNewLabel_4);

		lblNewLabel_5 = new JLabel("");
		lblNewLabel_5.setIcon(new ImageIcon(Clientes.class.getResource("/img/IconBike.png")));
		lblNewLabel_5.setToolTipText("Xrider");
		lblNewLabel_5.setBounds(656, 45, 128, 128);
		getContentPane().add(lblNewLabel_5);

		lblNewLabel_2_1 = new JLabel("XRaider");
		lblNewLabel_2_1.setForeground(Color.BLACK);
		lblNewLabel_2_1.setFont(new Font("Sylfaen", Font.BOLD, 19));
		lblNewLabel_2_1.setBounds(687, 174, 97, 32);
		getContentPane().add(lblNewLabel_2_1);

		lblNewLabel_6 = new JLabel("Email:");
		lblNewLabel_6.setBounds(10, 144, 46, 14);
		getContentPane().add(lblNewLabel_6);

		txtEmail = new JTextField();
		txtEmail.setBounds(59, 141, 181, 20);
		getContentPane().add(txtEmail);
		txtEmail.setColumns(10);

	}

	private void limparCampos() {
		txtIdcli.setText(null);
		txtCliente.setText(null);
		txtContato.setText(null);
		txtEmail.setText(null);
		txtCpfeCnpj.setText(null);
		txtEndereco.setText(null);
		txtComplemento.setText(null);
		txtCidade.setText(null);
		txtCep.setText(null);
		txtBairro.setText(null);
		cboUf.setSelectedItem("");
		txtNumero.setText(null);
		btnAdicionar.setEnabled(true);
		btnEditar.setEnabled(false);
		btnExcluir.setEnabled(false);
		scrollPaneClientes.setVisible(false);
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

	private void buscarClientesLista() {
		int linha = listClientes.getSelectedIndex();
		if (linha >= 0) {
			String readListaUsuario = "select * from clientes where cliente like '" + txtCliente.getText() + "%'"
					+ "order by cliente limit " + (linha) + " , 1";
			try {
				con = dao.conectar();
				pst = con.prepareStatement(readListaUsuario);
				rs = pst.executeQuery();
				if (rs.next()) {
					scrollPaneClientes.setVisible(false);
					txtIdcli.setText(rs.getString(1));
					txtCliente.setText(rs.getString(2));
					txtContato.setText(rs.getString(3));
					txtEmail.setText(rs.getString(4));
					txtEndereco.setText(rs.getString(5));
					txtComplemento.setText(rs.getString(6));
					txtCpfeCnpj.setText(rs.getString(7));
					txtCidade.setText(rs.getString(8));
					txtCep.setText(rs.getString(9));
					txtBairro.setText(rs.getString(10));
					cboUf.setSelectedItem(rs.getString(11));
					txtNumero.setText(rs.getString(12));
					btnAdicionar.setEnabled(false);
					btnEditar.setEnabled(true);
					btnExcluir.setEnabled(true);
				} else {
					JOptionPane.showMessageDialog(null, "Usuário inexistente");
				}
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		} else {
			scrollPaneClientes.setVisible(false);
		}
	}

	@SuppressWarnings("unchecked")
	private void listarClientes() {
		DefaultListModel<String> modelo = new DefaultListModel<>();
		listClientes.setModel(modelo);
		String readLista = "select * from clientes where cliente like '" + txtCliente.getText() + "%'"
				+ "order by cliente";
		try {
			con = dao.conectar();
			pst = con.prepareStatement(readLista);
			rs = pst.executeQuery();
			while (rs.next()) {
				scrollPaneClientes.setVisible(true);
				modelo.addElement(rs.getString(2));
				if (txtCliente.getText().isEmpty()) {
					scrollPaneClientes.setVisible(false);
				}
			}
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	private void adicionar() {
		if (txtCliente.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Nome do Cliente");
			txtCliente.requestFocus();
		} else if (txtContato.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Contato do Cliente");
			txtContato.requestFocus();
		} else if (txtEmail.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Email do Cliente");
			txtEmail.requestFocus();
		} else if (txtEndereco.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Endereço do Cliente");
			txtEndereco.requestFocus();
		} else if (txtCpfeCnpj.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o CPF/CNPJ do Cliente");
			txtCpfeCnpj.requestFocus();
		} else if (txtCidade.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha a Cidade do Cliente");
			txtCidade.requestFocus();
		} else if (txtCep.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o CEP do Cliente");
			txtCep.requestFocus();
		} else if (txtNumero.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Número do Cliente");
			txtNumero.requestFocus();
		} else {

			String create = "insert into clientes (cliente, contato, email, endereco, complemento, cpfecnpj, cidade, cep, bairro, uf, numero) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			try {
				con = dao.conectar();
				pst = con.prepareStatement(create);
				pst.setString(1, txtCliente.getText());
				pst.setString(2, txtContato.getText());
				pst.setString(3, txtEmail.getText());
				pst.setString(4, txtEndereco.getText());
				pst.setString(5, txtComplemento.getText());
				pst.setString(6, txtCpfeCnpj.getText());
				pst.setString(7, txtCidade.getText());
				pst.setString(8, txtCep.getText());
				pst.setString(9, txtBairro.getText());
				pst.setString(10, cboUf.getSelectedItem().toString());
				pst.setString(11, txtNumero.getText());
				pst.executeUpdate();
				JOptionPane.showMessageDialog(null, "Cliente adicionado!!");
				limparCampos();
				con.close();
			} catch (java.sql.SQLIntegrityConstraintViolationException e1) {
				JOptionPane.showMessageDialog(null, "Cliente não adicionado.\nEste CPF/CNPJ já está sendo utilizado.");
				txtCpfeCnpj.setText(null);
				txtCpfeCnpj.requestFocus();
			} catch (Exception e2) {
				System.out.println(e2);
			}
		}
	}

	private void editar() {
		if (txtCliente.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Nome do Cliente");
			txtCliente.requestFocus();
		} else if (txtContato.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Contato do Cliente");
			txtContato.requestFocus();
		} else if (txtEmail.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Email do Cliente");
			txtEmail.requestFocus();
		} else if (txtEndereco.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Endereço do Cliente");
			txtEndereco.requestFocus();
		} else if (txtCpfeCnpj.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o CPF/CNPJ do Cliente");
			txtCpfeCnpj.requestFocus();
		} else if (txtCidade.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha a Cidade do Cliente");
			txtCidade.requestFocus();
		} else if (txtCep.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o CEP do Cliente");
			txtCep.requestFocus();
		} else if (txtNumero.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Número do Cliente");
			txtCep.requestFocus();
		} else {
			String update = "update clientes set cliente = ?, contato = ?, email = ?, endereco = ?, complemento = ?, cpfecnpj = ?, cidade = ?, cep = ?, bairro = ?, uf = ?, numero = ? where idcli = ?";
			try {
				con = dao.conectar();
				pst = con.prepareStatement(update);
				pst.setString(1, txtCliente.getText());
				pst.setString(2, txtContato.getText());
				pst.setString(3, txtEmail.getText());
				pst.setString(4, txtEndereco.getText());
				pst.setString(5, txtComplemento.getText());
				pst.setString(6, txtCpfeCnpj.getText());
				pst.setString(7, txtCidade.getText());
				pst.setString(8, txtCep.getText());
				pst.setString(9, txtBairro.getText());
				pst.setString(10, cboUf.getSelectedItem().toString());
				pst.setString(11, txtNumero.getText());
				pst.setString(12, txtIdcli.getText());
				pst.executeUpdate();
				JOptionPane.showMessageDialog(null, "Dados do cliente editados com sucesso!!");
				limparCampos();
				con.close();
			} catch (java.sql.SQLIntegrityConstraintViolationException e1) {
				JOptionPane.showMessageDialog(null, "Cliente não editado.\nEste CPF/CNPJ já está sendo utilizado.");
				txtCpfeCnpj.setText(null);
				txtCpfeCnpj.requestFocus();
			} catch (Exception e2) {
				System.out.println(e2);
			}
		}
	}

	private void excluir() {
		if (txtCpfeCnpj.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "SEM IDENTIFICAÇÃO -> Preencha o CPF do Cliente");
		} else {
			int confirm = JOptionPane.showConfirmDialog(null, "Confirma a exclusão deste Cliente?", "Atenção!!",
					JOptionPane.YES_NO_OPTION);
			if (confirm == JOptionPane.YES_OPTION) {
				String delete = "delete from clientes where idcli = ?";
				try {
					con = dao.conectar();
					pst = con.prepareStatement(delete);
					pst.setString(1, txtIdcli.getText());
					pst.executeUpdate();
					JOptionPane.showMessageDialog(null, "Cliente excluído!");
					con.close();
				} catch (java.sql.SQLIntegrityConstraintViolationException e1) {
					JOptionPane.showMessageDialog(null, "Cliente não pode ser excluido.\nServiço pendente!!");
				} catch (Exception e2) {
					System.out.println(e2);
				}
			}
		}

	}
}
