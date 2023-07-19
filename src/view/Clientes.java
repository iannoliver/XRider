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
	private JComboBox cboUf;
	private JScrollPane scrollPaneClientes;
	private JList listClientes;

	/**
	 * Launch the application.
	 */
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

	/**
	 * Create the dialog.
	 */
	public Clientes() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(Clientes.class.getResource("/img/IconBike.png")));
		setTitle("Cadastro de Clientes");
		setBounds(100, 100, 546, 436);
		getContentPane().setLayout(null);
		
		scrollPaneClientes = new JScrollPane();
		scrollPaneClientes.setVisible(false);
		scrollPaneClientes.setBounds(59, 78, 263, 63);
		getContentPane().add(scrollPaneClientes);
		
		listClientes = new JList();
		listClientes.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				buscarClientesLista();
			}
		});
		scrollPaneClientes.setViewportView(listClientes);
		
		JLabel lblCliente = new JLabel("Cliente:");
		lblCliente.setBounds(10, 61, 83, 20);
		getContentPane().add(lblCliente);
		
		txtCliente = new JTextField();
		txtCliente.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				 listarClientes(); 
			}
		});
		txtCliente.setBounds(59, 61, 263, 20);
		getContentPane().add(txtCliente);
		txtCliente.setColumns(10);
		txtCliente.setDocument(new Validador(50));
		
		JLabel lblCliente_1 = new JLabel("Contato:");
		lblCliente_1.setBounds(10, 92, 83, 20);
		getContentPane().add(lblCliente_1);
		
		txtContato = new JTextField();
		txtContato.setColumns(10);
		txtContato.setBounds(59, 92, 173, 20);
		getContentPane().add(txtContato);
		txtContato.setDocument(new Validador(15));
		
		JLabel lblCliente_1_1 = new JLabel("Endereço:");
		lblCliente_1_1.setBounds(10, 153, 83, 20);
		getContentPane().add(lblCliente_1_1);
		
		txtEndereco = new JTextField();
		txtEndereco.setColumns(10);
		txtEndereco.setBounds(69, 153, 199, 20);
		getContentPane().add(txtEndereco);
		txtEndereco.setDocument(new Validador(50));
		
		JLabel lblCliente_1_1_1 = new JLabel("Complemento:");
		lblCliente_1_1_1.setBounds(278, 153, 83, 20);
		getContentPane().add(lblCliente_1_1_1);
		
		JLabel lblCliente_1_1_2 = new JLabel("CPF/CNPJ:");
		lblCliente_1_1_2.setBounds(10, 11, 83, 20);
		getContentPane().add(lblCliente_1_1_2);
		
		JLabel lblCliente_1_1_3 = new JLabel("Cidade:");
		lblCliente_1_1_3.setBounds(10, 184, 83, 20);
		getContentPane().add(lblCliente_1_1_3);
		
		txtComplemento = new JTextField();
		txtComplemento.setColumns(10);
		txtComplemento.setBounds(365, 153, 83, 20);
		getContentPane().add(txtComplemento);
		txtComplemento.setDocument(new Validador(10));
		
		txtCpfeCnpj = new JTextField();
		txtCpfeCnpj.setColumns(10);
		txtCpfeCnpj.setBounds(80, 11, 199, 20);
		getContentPane().add(txtCpfeCnpj);
		txtCpfeCnpj.setDocument(new Validador(14));
		
		txtCidade = new JTextField();
		txtCidade.setColumns(10);
		txtCidade.setBounds(59, 184, 138, 20);
		getContentPane().add(txtCidade);
		txtCidade.setDocument(new Validador(40));
		
		JLabel lblCliente_1_1_3_1 = new JLabel("Cep:");
		lblCliente_1_1_3_1.setBounds(313, 215, 83, 20);
		getContentPane().add(lblCliente_1_1_3_1);
		
		txtCep = new JTextField();
		txtCep.setBounds(344, 215, 104, 20);
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
		btnExcluir.setBounds(107, 338, 48, 48);
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
		btnAdicionar.setBounds(35, 338, 48, 48);
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
		btnLimpar.setBounds(223, 338, 48, 48);
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
		btnEditar.setBounds(165, 338, 48, 48);
		getContentPane().add(btnEditar);
		
		JLabel lblNewLabel = new JLabel("ID: ");
		lblNewLabel.setBounds(402, 14, 46, 14);
		getContentPane().add(lblNewLabel);
		
		txtIdcli = new JTextField();
		txtIdcli.setEditable(false);
		txtIdcli.setBounds(430, 11, 59, 20);
		getContentPane().add(txtIdcli);
		txtIdcli.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setOpaque(true);
		lblNewLabel_1.setBackground(new Color(33, 146, 123));
		lblNewLabel_1.setBounds(0, 316, 530, 81);
		getContentPane().add(lblNewLabel_1);
		
		JButton btnBuscaCep = new JButton("");
		btnBuscaCep.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buscaCep();
			}
		});
		btnBuscaCep.setContentAreaFilled(false);
		btnBuscaCep.setBorder(null);
		btnBuscaCep.setToolTipText("BuscaCep");
		btnBuscaCep.setIcon(new ImageIcon(Clientes.class.getResource("/img/BuscaCEP.png")));
		btnBuscaCep.setBounds(455, 199, 48, 48);
		getContentPane().add(btnBuscaCep);
		
		JLabel lblCliente_1_1_3_2 = new JLabel("Bairro:");
		lblCliente_1_1_3_2.setBounds(10, 215, 83, 20);
		getContentPane().add(lblCliente_1_1_3_2);
		
		JLabel lblCliente_1_1_3_2_1 = new JLabel("Uf :");
		lblCliente_1_1_3_2_1.setBounds(10, 246, 53, 20);
		getContentPane().add(lblCliente_1_1_3_2_1);
		
		txtBairro = new JTextField();
		txtBairro.setBounds(52, 215, 145, 20);
		getContentPane().add(txtBairro);
		txtBairro.setColumns(10);
		txtCep.setDocument(new Validador(15));
		
		JLabel lblCliente_1_1_3_2_2 = new JLabel("N° :");
		lblCliente_1_1_3_2_2.setBounds(10, 272, 53, 20);
		getContentPane().add(lblCliente_1_1_3_2_2);
		
		txtNumero = new JTextField();
		txtNumero.setColumns(10);
		txtNumero.setBounds(35, 272, 145, 20);
		getContentPane().add(txtNumero);
		txtNumero.setDocument(new Validador(5));
		
		cboUf = new JComboBox();
		cboUf.setModel(new DefaultComboBoxModel(new String[] {"", "AC", "AL", "AP", "AM", "BA", "CE", "DF", "ES", "GO", "MA", "MT", "MS", "MG", "PA", "PB", "PR", "PE", "PI", "RJ", "RN", "RS", "RO", "RR", "SC", "SP", "TO"}));
		cboUf.setBounds(35, 246, 68, 22);
		getContentPane().add(cboUf);
	
	}
	
	private void limparCampos() {
		txtIdcli.setText(null);
		txtCliente.setText(null);
		txtContato.setText(null);
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
	
	private void buscarClientesLista() {
		//variável que captura o indice da linha da lista
		int linha = listClientes.getSelectedIndex();
		if(linha >= 0) {
			//Query (instrução SQL)
			//limit (0.1) -> seleciona o indice 0 e 1 usuário da lista
			String readListaUsuario = "select * from clientes where cliente like '" + txtCliente.getText() + "%'"	+ "order by cliente limit " + (linha) + " , 1";
			try {
				con = dao.conectar();
				pst = con.prepareStatement(readListaUsuario);
				rs = pst.executeQuery();
				if (rs.next()) {
					//esconder a lista
					scrollPaneClientes.setVisible(false);
					//setar os campos
					txtIdcli.setText(rs.getString(1));
					txtCliente.setText(rs.getString(2));
					txtContato.setText(rs.getString(3));
					txtEndereco.setText(rs.getString(4));
					txtComplemento.setText(rs.getString(5));
					txtCpfeCnpj.setText(rs.getString(6));
					txtCidade.setText(rs.getString(7));
					txtCep.setText(rs.getString(8));
					txtBairro.setText(rs.getString(9));
					cboUf.setSelectedItem(rs.getString(10));
					txtNumero.setText(rs.getString(11));
					btnAdicionar.setEnabled(false);
					btnEditar.setEnabled(true);
					btnExcluir.setEnabled(true);
				} else {
					JOptionPane.showMessageDialog(null, "Usuário inexistente");
				}
				//fechar conexão
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		} else {
			// se não existir no banco um usuário da lista
				scrollPaneClientes.setVisible(false);
		}
	}
	
	/**
	 * método usado para listar o nome dos usuarios da lista
	 */
	private void listarClientes() {
		//a linha abaixo cria um objeto usando como referência um vetor dinâmico, 
		//este objeto ira temporariamente armazenar os nomes
		DefaultListModel<String> modelo = new DefaultListModel<>();
		//setar o modelo (vetor da lista)
		listClientes.setModel(modelo);
		//Query (instrução sql)
		String readLista = "select * from clientes where cliente like '" + txtCliente.getText() + "%'" + "order by cliente";
		try {
			con = dao.conectar();
			pst= con.prepareStatement(readLista);
			rs = pst.executeQuery();
			//uso do while para trazer os usuarios enqunto existir
			while(rs.next()) {
				//mostrar barra de rolagem
				scrollPaneClientes.setVisible(true);
				//adicionar os usuarios ao vetor -> lista
				modelo.addElement(rs.getString(2));
				//esconder a lista se nenhuma letra for digitada
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
		//System.out.println("teste");
		//Validação de campos obrigatórios
		if (txtCliente.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Nome do Cliente");
			txtCliente.requestFocus();
		} else if (txtContato.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Contato do Cliente");
			txtContato.requestFocus();
		} else if (txtEndereco.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Endereço do Cliente");
			txtEndereco.requestFocus();
		}  else if (txtCpfeCnpj.getText().isEmpty()) {
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
		}
		else {
			
			//lógica principal
			String create = "insert into clientes (cliente, contato, endereco, complemento, cpfecnpj, cidade, cep, bairro, uf, numero) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			//tratamento de exceções
			try {
				//abrir a conexão
				con = dao.conectar();
				//PREPARA A EXECUÇÃO DA QUERY(instrução SQL - CRUD Crate)
				pst = con.prepareStatement(create);
				pst.setString(1,  txtCliente.getText());
				pst.setString(2,  txtContato.getText());
				pst.setString(3,  txtEndereco.getText());
				pst.setString(4,  txtComplemento.getText());
				pst.setString(5,  txtCpfeCnpj.getText());
				pst.setString(6,  txtCidade.getText());
				pst.setString(7,  txtCep.getText());
				pst.setString(8,  txtBairro.getText());
				pst.setString(9,  cboUf.getSelectedItem().toString());
				pst.setString(10,  txtNumero.getText());
				//executa a query(Instrução SQL (CRUD - Create)
				pst.executeUpdate();
				//confirmar
				JOptionPane.showMessageDialog(null, "Cliente adicionado!!");
				//limpar campos
				limparCampos();
				//fechar conexão
				con.close();
			} catch (java.sql.SQLIntegrityConstraintViolationException e1) {
				JOptionPane.showMessageDialog(null, "Cliente não adicionado.\nEste CPF/CNPJ já está sendo utilizado.");
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
		if (txtCliente.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Nome do Cliente");
			txtCliente.requestFocus();
		} else if (txtContato.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Contato do Cliente");
			txtContato.requestFocus();
		} else if (txtEndereco.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Endereço do Cliente");
			txtEndereco.requestFocus();
		}  else if (txtCpfeCnpj.getText().isEmpty()) {
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
		}
		else {
			//Lógica principal
			//CRUD - Update
			String update = "update clientes set cliente = ?, contato = ?, endereco = ?, complemento = ?, cpfecnpj = ?, cidade = ?, cep = ?, bairro = ?, uf = ?, numero = ? where idcli = ?";
			try {
				//abrir conexão
				con = dao.conectar();
				//prepara a query (instrução sql)
				pst = con.prepareStatement(update);
				pst.setString(1,txtCliente.getText());
				pst.setString(2,txtContato.getText());
				pst.setString(3,txtEndereco.getText());
				pst.setString(4,txtComplemento.getText());
				pst.setString(5,txtCpfeCnpj.getText());
				pst.setString(6,txtCidade.getText());
				pst.setString(7,txtCep.getText());
				pst.setString(8,txtBairro.getText());
				pst.setString(9,cboUf.getSelectedItem().toString());
				pst.setString(10,txtNumero.getText());
				pst.setString(11,txtIdcli.getText());
				//executar a query
				pst.executeUpdate();
				//confirmar para o usuário
				JOptionPane.showMessageDialog(null, "Dados do cliente editados com sucesso!!");
				//Limpar os campos
				limparCampos();
				//fechar conexão
				con.close();
			}catch (java.sql.SQLIntegrityConstraintViolationException e1) {
				JOptionPane.showMessageDialog(null, "Cliente não editado.\nEste CPF/CNPJ já está sendo utilizado.");
				txtCpfeCnpj.setText(null);
				txtCpfeCnpj.requestFocus();
			} catch (Exception e2) {
				System.out.println(e2);
			}
		}
	}//Fim do método editar contato
	
	/**
	 * Método usado para excluir contatos
	 */
	private void excluir() {
		if (txtCpfeCnpj.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "SEM IDENTIFICAÇÃO -> Preencha o CPF do Cliente");
		}
		else {
			//System.out.println("Teste do botão");
			//Validação de exclusão
			int confirm = JOptionPane.showConfirmDialog(null, "Confirma a exclusão deste Cliente?", "Atenção!!",JOptionPane.YES_NO_OPTION);
			if (confirm == JOptionPane.YES_OPTION) {
				//CRUD - Delete
				String delete = "delete from clientes where idcli = ?";
				//tratamento de exceções
				try {
					//abrir a conexão
					con = dao.conectar();
					//preparar a query (instrução sql)
					pst = con.prepareStatement(delete);
					//substituir a ? pelo id do contato
					pst.setString(1, txtIdcli.getText());
					//executar a query
					pst.executeUpdate();
					//limpar campos
					limparCampos();
					//exibir uma mensagem de usuário
					JOptionPane.showMessageDialog(null, "Cliente excluído!");
					//fechar a conexão
					con.close();
				}  catch (java.sql.SQLIntegrityConstraintViolationException e1) {
					JOptionPane.showMessageDialog(null, "Cliente não pode ser excluido.\nServiço pendente!!");
				} catch (Exception e2) {
					System.out.println(e2);
				}
			}
		}
		
	}//Fim do método excluir contato
}
