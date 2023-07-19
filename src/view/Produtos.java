package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.imageio.ImageIO;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import model.DAO;
import java.awt.Cursor;

public class Produtos extends JDialog {
	DAO dao = new DAO();
	private Connection con;
	private PreparedStatement pst;
	private ResultSet rs;
	
	private FileInputStream fis;
	
	private int tamanho;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField txtID;
	private JTextField txtProduto;
	private JTextField txtEstoque;
	private JTextField txtEstoqueMin;
	private JTextField txtValor;
	private JTextField txtArmazenagem;
	private JComboBox cboMedida;
	private JTextField txtFornecedor;
	private JTextField txtIDF;
	private JScrollPane scrollPaneFornecedor;
	private JButton btnAdicionar;
	private JButton btnEditar;
	private JButton btnExcluir;
	private JButton btnBuscar;
	private JList listFornecedores;
	private JTextPane txtDescricao;
	private JButton btnCarregarFoto;
	private JLabel lblFoto;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			Produtos dialog = new Produtos();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public Produtos() {
		setTitle("Produtos");
		setIconImage(Toolkit.getDefaultToolkit().getImage(Produtos.class.getResource("/img/IconBike.png")));
		setBounds(100, 100, 616, 442);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JButton btnLimpar = new JButton("");
			btnLimpar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			btnLimpar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					limparCampos();
				}
			});
			btnLimpar.setIcon(new ImageIcon(Produtos.class.getResource("/img/Burracha.png")));
			btnLimpar.setToolTipText("Limpar");
			btnLimpar.setContentAreaFilled(false);
			btnLimpar.setBorder(null);
			btnLimpar.setBounds(323, 344, 48, 48);
			contentPanel.add(btnLimpar);
		}
		{
			btnExcluir = new JButton("");
			btnExcluir.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			btnExcluir.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					excluir();
				}
			});
			btnExcluir.setIcon(new ImageIcon(Produtos.class.getResource("/img/Lixeira2.png")));
			btnExcluir.setToolTipText("Excluir");
			btnExcluir.setEnabled(false);
			btnExcluir.setContentAreaFilled(false);
			btnExcluir.setBorder(null);
			btnExcluir.setBounds(254, 344, 48, 48);
			contentPanel.add(btnExcluir);
		}
		{
			btnEditar = new JButton("");
			btnEditar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			btnEditar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					editar();
				}
			});
			btnEditar.setIcon(new ImageIcon(Produtos.class.getResource("/img/EditarCli.png")));
			btnEditar.setToolTipText("Editar");
			btnEditar.setEnabled(false);
			btnEditar.setContentAreaFilled(false);
			btnEditar.setBorder(null);
			btnEditar.setBounds(173, 344, 48, 48);
			contentPanel.add(btnEditar);
		}
		{
			btnAdicionar = new JButton("");
			btnAdicionar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			btnAdicionar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					adicionar();
				}
			});
			btnAdicionar.setIcon(new ImageIcon(Produtos.class.getResource("/img/Add.png")));
			btnAdicionar.setToolTipText("Adicionar");
			btnAdicionar.setContentAreaFilled(false);
			btnAdicionar.setBorder(null);
			btnAdicionar.setBounds(88, 344, 48, 48);
			contentPanel.add(btnAdicionar);
		}
		
		scrollPaneFornecedor = new JScrollPane();
		scrollPaneFornecedor.setVisible(false);
		scrollPaneFornecedor.setBounds(407, 60, 175, 32);
		contentPanel.add(scrollPaneFornecedor);
		
		listFornecedores = new JList();
		scrollPaneFornecedor.setViewportView(listFornecedores);
		listFornecedores.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				buscarListaFornecedores();
			}
		});
		{
			JLabel lblId = new JLabel("ID:");
			lblId.setBounds(10, 31, 46, 14);
			contentPanel.add(lblId);
		}
		{
			JLabel lblDescrio = new JLabel("Descrição:");
			lblDescrio.setBounds(10, 81, 73, 14);
			contentPanel.add(lblDescrio);
		}
		{
			JLabel lblNewLabel_1 = new JLabel("Produto:");
			lblNewLabel_1.setBounds(10, 56, 73, 14);
			contentPanel.add(lblNewLabel_1);
		}
		{
			JLabel lblNewLabel_1_1 = new JLabel("Foto:");
			lblNewLabel_1_1.setBounds(10, 213, 73, 14);
			contentPanel.add(lblNewLabel_1_1);
		}
		{
			JLabel lblNewLabel_2 = new JLabel("Estoque:");
			lblNewLabel_2.setBounds(344, 160, 62, 14);
			contentPanel.add(lblNewLabel_2);
		}
		{
			txtID = new JTextField();
			txtID.setEditable(false);
			txtID.setColumns(10);
			txtID.setBounds(36, 28, 55, 20);
			contentPanel.add(txtID);
		}
		{
			txtProduto = new JTextField();
			txtProduto.setColumns(10);
			txtProduto.setBounds(58, 53, 185, 20);
			contentPanel.add(txtProduto);
		}
		{
			txtEstoque = new JTextField();
			txtEstoque.setColumns(10);
			txtEstoque.setBounds(394, 157, 196, 20);
			contentPanel.add(txtEstoque);
		}
		{
			JLabel lblNewLabel_2 = new JLabel("EstoqueMin:");
			lblNewLabel_2.setBounds(323, 135, 62, 14);
			contentPanel.add(lblNewLabel_2);
		}
		{
			JLabel lblNewLabel_2 = new JLabel("Valor:");
			lblNewLabel_2.setBounds(434, 188, 46, 14);
			contentPanel.add(lblNewLabel_2);
		}
		{
			JLabel lblNewLabel = new JLabel("Medida:");
			lblNewLabel.setBounds(434, 213, 62, 14);
			contentPanel.add(lblNewLabel);
		}
		{
			JLabel lblNewLabel_2 = new JLabel("Armazenagem:");
			lblNewLabel_2.setBounds(312, 241, 73, 14);
			contentPanel.add(lblNewLabel_2);
		}
		{
			txtEstoqueMin = new JTextField();
			txtEstoqueMin.setColumns(10);
			txtEstoqueMin.setBounds(394, 129, 196, 20);
			contentPanel.add(txtEstoqueMin);
		}
		{
			txtValor = new JTextField();
			txtValor.setColumns(10);
			txtValor.setBounds(470, 185, 120, 20);
			contentPanel.add(txtValor);
		}
		{
			txtArmazenagem = new JTextField();
			txtArmazenagem.setColumns(10);
			txtArmazenagem.setBounds(394, 238, 196, 20);
			contentPanel.add(txtArmazenagem);
		}
		{
			cboMedida = new JComboBox();
			cboMedida.setModel(new DefaultComboBoxModel(new String[] {"", "UN ", "CX ", "PC ", "Kg ", "m"}));
			cboMedida.setBounds(480, 211, 62, 18);
			contentPanel.add(cboMedida);
		}
		
		txtDescricao = new JTextPane();
		txtDescricao.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		txtDescricao.setBounds(82, 81, 231, 121);
		contentPanel.add(txtDescricao);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Fornecedores", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setName("Cliente");
		panel.setToolTipText("Cliente");
		panel.setBounds(325, 21, 265, 74);
		contentPanel.add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Fornecedor:");
		lblNewLabel.setBounds(10, 22, 70, 14);
		panel.add(lblNewLabel);
		
		txtFornecedor = new JTextField();
		txtFornecedor.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				listarFornecedores();
			}
		});
		txtFornecedor.setBounds(81, 19, 174, 20);
		panel.add(txtFornecedor);
		txtFornecedor.setColumns(10);
		
		JLabel lblNewLabel_3 = new JLabel("ID:");
		lblNewLabel_3.setBounds(10, 47, 31, 14);
		panel.add(lblNewLabel_3);
		{
			txtIDF = new JTextField();
			txtIDF.setEditable(false);
			txtIDF.setColumns(10);
			txtIDF.setBounds(36, 44, 55, 20);
			panel.add(txtIDF);
		}
		{
			JLabel lblNewLabel_4 = new JLabel("");
			lblNewLabel_4.setOpaque(true);
			lblNewLabel_4.setBackground(new Color(36, 185, 182));
			lblNewLabel_4.setBounds(0, 329, 600, 74);
			contentPanel.add(lblNewLabel_4);
		}
		{
			btnBuscar = new JButton("");
			btnBuscar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					buscar();
				}
			});
			btnBuscar.setIcon(new ImageIcon(Produtos.class.getResource("/img/PesquisarCli.png")));
			btnBuscar.setToolTipText("Buscar");
			btnBuscar.setContentAreaFilled(false);
			btnBuscar.setBorder(null);
			btnBuscar.setBounds(254, 27, 48, 48);
			contentPanel.add(btnBuscar);
			
			getRootPane().setDefaultButton(btnBuscar);
		}
		
		lblFoto = new JLabel("");
		lblFoto.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		lblFoto.setIcon(new ImageIcon(Produtos.class.getResource("/img/photo.png")));
		lblFoto.setBounds(47, 213, 80, 74);
		contentPanel.add(lblFoto);
		
		btnCarregarFoto = new JButton("Carregar Foto");
		btnCarregarFoto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				carregarFoto();
				
			}
		});
		btnCarregarFoto.setBounds(143, 264, 120, 23);
		contentPanel.add(btnCarregarFoto);
	}
	
	private void limparCampos() {
		txtID.setText(null);
		txtProduto.setText(null);
		txtDescricao.setText(null);
		txtEstoque.setText(null);
		txtEstoqueMin.setText(null);
		txtArmazenagem.setText(null);
		txtValor.setText(null);
		cboMedida.setSelectedItem("");
		txtFornecedor.setText(null);
		txtIDF.setText(null);
		btnAdicionar.setEnabled(true);
		btnEditar.setEnabled(false);
		btnExcluir.setEnabled(false);
		btnBuscar.setEnabled(true);
		scrollPaneFornecedor.setVisible(false);
		lblFoto.setIcon(new ImageIcon(Principal.class.getResource("/img/photo.png")));
	}
	
	private void buscarListaFornecedores() {
		//variável que captura o indice da linha da lista
		int linha = listFornecedores.getSelectedIndex();
		if(linha >= 0) {
			//Query (instrução SQL)
			//limit (0.1) -> seleciona o indice 0 e 1 usuário da lista
			String readListaUsuario = "select * from fornecedores where fornecedor like '" + txtFornecedor.getText() + "%'"	+ "order by fornecedor limit " + (linha) + " , 1";
			try {
				con = dao.conectar();
				pst = con.prepareStatement(readListaUsuario);
				rs = pst.executeQuery();
				if (rs.next()) {
					//esconder a lista
					scrollPaneFornecedor.setVisible(false);
					//setar os campos
					txtIDF.setText(rs.getString(1));
					txtFornecedor.setText(rs.getString(2));
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
				scrollPaneFornecedor.setVisible(false);
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
		String readLista = "select * from fornecedores where fornecedor like '" + txtFornecedor.getText() + "%'" + "order by fornecedor";
		try {
			con = dao.conectar();
			pst= con.prepareStatement(readLista);
			rs = pst.executeQuery();
			//uso do while para trazer os usuarios enqunto existir
			while(rs.next()) {
				//mostrar barra de rolagem
				scrollPaneFornecedor.setVisible(true);
				//adicionar os usuarios ao vetor -> lista
				modelo.addElement(rs.getString(2));
				//esconder a lista se nenhuma letra for digitada
				if (txtFornecedor.getText().isEmpty()) {
					scrollPaneFornecedor.setVisible(false);
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
			if (txtProduto.getText().isEmpty()) {
				JOptionPane.showMessageDialog(null, "Preencha o nome do Produto");
				txtProduto.requestFocus();
		}else if (txtDescricao.getText().isEmpty()) {
				JOptionPane.showMessageDialog(null, "Preencha a Descrição do Produto");
				txtDescricao.requestFocus();
		}else if (txtEstoque.getText().isEmpty()) {
				JOptionPane.showMessageDialog(null, "Preencha o Estoque");
				txtEstoque.requestFocus();
		}else if (txtEstoqueMin.getText().isEmpty()) {
				JOptionPane.showMessageDialog(null, "Preencha o Estoque MINIMO");
				txtEstoqueMin.requestFocus();
		}else if (txtArmazenagem.getText().isEmpty()) {
				JOptionPane.showMessageDialog(null, "Preencha a Armazenagem do Produto");
				txtArmazenagem.requestFocus();
		}else if (txtIDF.getText().isEmpty()) {
				JOptionPane.showMessageDialog(null, "Preencha o ID do Fornecedor");
				txtIDF.requestFocus();
		}else {
			
			//lógica principal
			String create = "insert into produtos (nome, descricao, foto, estoque, estoquemin, valor, medida, armazenagem, id) values (?, ?, ?, ?, ?, ?, ?, ?, ?)";
			//tratamento de exceções
			try {
				//abrir a conexão
				con = dao.conectar();
				//PREPARA A EXECUÇÃO DA QUERY(instrução SQL - CRUD Create)
				pst = con.prepareStatement(create);
				pst.setString(1,txtProduto.getText());
				pst.setString(2,txtDescricao.getText());
				pst.setString(3, lblFoto.getText());
				pst.setString(4,txtEstoque.getText());
				pst.setString(5,txtEstoqueMin.getText());
				pst.setString(6,txtValor.getText());
				pst.setString(7,cboMedida.getSelectedItem().toString());
				pst.setString(8,txtArmazenagem.getText());
				pst.setString(9,txtIDF.getText());
				//executa a query(Instrução SQL (CRUD - Create)
				pst.executeUpdate();
				//confirmar
				JOptionPane.showMessageDialog(null, "Novo Produtos Adicionado!!");
				//limpar campos
				limparCampos();
				//fechar conexão
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
		}// Fim do método adicionar
		
	
	private void buscar () {
		//captura do número da OS (sem usar a caixa de texto)
		String idproduto = JOptionPane.showInputDialog("Id do Produto");
		String read = "select * from produtos where idproduto = ?";
		//Tratramento de exceções
		try {
			//abrir a conexão
			con = dao.conectar();
			//PREPARA A EXECUÇÃO DA QUERY(instrução SQL - CRUD Read) 
			pst = con.prepareStatement(read);
			//substituir a ?(parâmetro) pelonúmero da OS
			pst.setString(1,  idproduto);
			//executar a Query e buscar o resultado
			rs = pst.executeQuery();
			// uso da estrutura IFELSE para verificar se existe o contato
			// rs.next() -> se existir um contato no banco
			if (rs.next()) {
				//preeccher as caixas de texto com informações
				txtID.setText(rs.getString(1));
				txtProduto.setText(rs.getString(2));
				txtDescricao.setText(rs.getString(4));
				Blob blob = (Blob) rs.getBlob(5);
				txtEstoque.setText(rs.getString(6));
				txtEstoqueMin.setText(rs.getString(7));
				txtValor.setText(rs.getString(8));
				cboMedida.setSelectedItem(rs.getString(9));
				txtArmazenagem.setText(rs.getString(10));
				txtIDF.setText(rs.getString(11));
				btnAdicionar.setEnabled(false);
				btnEditar.setEnabled(true);
				btnExcluir.setEnabled(true);
				btnBuscar.setEnabled(false);
				
				byte[] img = blob.getBytes(1, (int) blob.length());
				BufferedImage imagem = null;
				try {
					imagem = ImageIO.read(new ByteArrayInputStream(img));
				} catch (Exception e) {
					System.out.println(e);
				}
				ImageIcon icone = new ImageIcon(imagem);
				Icon foto = new ImageIcon(icone.getImage().getScaledInstance(lblFoto.getWidth(), lblFoto.getHeight(), Image.SCALE_SMOOTH));
				lblFoto.setIcon(foto);
			
			} else {
				//se não exisir um contato
				JOptionPane.showMessageDialog(null, "Produto Inexistente");
				btnAdicionar.setEnabled(true);
				btnBuscar.setEnabled(false);
			}
			// fechar a conexão(importante)
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	private void excluir() {
		//System.out.println("Teste do botão");
		//Validação de exclusão
		int confirm = JOptionPane.showConfirmDialog(null, "Confirma a exclusão desse produto?", "Atenção!!",JOptionPane.YES_NO_OPTION);
		if (confirm == JOptionPane.YES_OPTION) {
			//CRUD - Delete
			String delete = "delete from produtos where idproduto = ?";
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
				JOptionPane.showMessageDialog(null, "Produto excluído!");
				//fechar a conexão
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
		
	}//Fim do método excluir contato
	
	private void editar() {
		//System.out.println("teste do método editar");
		//Validação dos campos obrigatórios
		if (txtProduto.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o nome do Produto");
			txtProduto.requestFocus();
	}  else if (txtDescricao.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha a Descrição do Produto");
			txtDescricao.requestFocus();
	}else if (txtEstoque.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Estoque");
			txtEstoque.requestFocus();
	}else if (txtEstoqueMin.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Estoque MINIMO");
			txtEstoqueMin.requestFocus();
	}else if (txtEstoque.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha a Medida");
			txtEstoque.requestFocus();
	}else if (txtEstoque.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha a Armazenagem do Produto");
			txtEstoque.requestFocus();
	} else {
			//Lógica principal
			//CRUD - Update
			String update = "update produtos set nome = ?, descricao = ?, foto = ?,estoque = ?, estoquemin = ?, valor = ?, medida = ?, armazenagem = ? idfornecedor = ? where idproduto = ?";
			try {
				//abrir conexão
				con = dao.conectar();
				//prepara a query (instrução sql)
				pst = con.prepareStatement(update);
				pst.setString(1,txtProduto.getText());
				pst.setString(2,txtDescricao.getText());
				Blob blob = (Blob) rs.getBlob(3);
				pst.setString(4,txtEstoque.getText());
				pst.setString(5,txtEstoqueMin.getText());
				pst.setString(6,txtValor.getText());
				pst.setString(7,cboMedida.getSelectedItem().toString());
				pst.setString(8,txtArmazenagem.getText());
				pst.setString(9,txtIDF.getText());
				
				//executar a query
				pst.executeUpdate();
				//confirmar para o usuário
				JOptionPane.showMessageDialog(null, "Dados do produto editados com sucesso!!");
				//Limpar os campos
				limparCampos();
				//fechar conexão
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}//Fim do método editar contato
	
	private void carregarFoto() {
		JFileChooser jfc = new JFileChooser();
		jfc.setDialogTitle("Selecionar Arquivo");
		jfc.setFileFilter(new FileNameExtensionFilter("Arquivo de Imagens(*.PNG, *JPG, *JPEG)", "png","jpg", "jpeg"));
		int resultado = jfc.showOpenDialog(this);
		if(resultado == JFileChooser.APPROVE_OPTION) {
		try{
			fis = new FileInputStream(jfc.getSelectedFile());
			tamanho = (int) jfc.getSelectedFile().length();
			Image foto = ImageIO.read(jfc.getSelectedFile()).getScaledInstance(lblFoto.getWidth(), lblFoto.getHeight(), Image.SCALE_SMOOTH);
			lblFoto.setIcon(new ImageIcon(foto));
			lblFoto.updateUI();
		} catch (Exception e) {
			System.out.println(e);
		}
	    }
			
	}
}
