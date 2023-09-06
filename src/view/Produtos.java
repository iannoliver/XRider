package view;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//import com.toedter.calendar.JDateChooser;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.toedter.calendar.JDateChooser;

import model.DAO;
import utils.Validador;
import java.awt.Toolkit;

public class Produtos extends JDialog {

	private static final long serialVersionUID = 1L;
	DAO dao = new DAO();
	private Connection con;
	private PreparedStatement pst;
	private ResultSet rs;
	JFileChooser jfc = new JFileChooser();
	private FileInputStream fis;
	private int tamanho;
	private boolean IsImageLoaded;

	private JTextField txtBarcode;
	private JTextField txtCodigo;
	private JTextField txtFornecedor;
	private JTextField txtId;
	private JTextField txtProduto;
	private JTextField txtValor;
	private JTextField txtLucro;
	private JTextField txtFabricante;
	private JTextField txtEstoque;
	private JTextField txtEstoquemin;
	private JTextField txtLocal;
	private JTextArea txtDescricao;
	@SuppressWarnings("rawtypes")
	private JComboBox cboUnidade;
	private JTextField txtLote;
	private JDateChooser dataEntrada;
	private JButton btnAdicionar;
	private JDateChooser dataValidade;
	private JLabel lblimg;
	@SuppressWarnings("rawtypes")
	private JList listFornecedor;
	private JScrollPane scrollPane;
	@SuppressWarnings("rawtypes")
	private JList listProdutos;
	private JScrollPane scrollprodutos;
	private JButton btnAlterar;
	private JButton btnExcluir;
	private JButton btnPesquisar;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Produtos dialog = new Produtos();
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Produtos() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(Produtos.class.getResource("/img/IconBike.png")));
		getContentPane().addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				scrollPane.setVisible(false);
				scrollprodutos.setVisible(false);
			}
		});
		addWindowListener(new WindowAdapter() {
			public void windowActivated(WindowEvent e) {
				txtBarcode.requestFocus();
			}

		});
		setTitle("Produtos");
		setResizable(false);
		setModal(true);
		setBounds(100, 100, 800, 600);
		getContentPane().setLayout(null);

		JButton btnLimparCampos = new JButton("");
		btnLimparCampos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limparCampos();
			}
		});

		btnAdicionar = new JButton("");
		btnAdicionar.setContentAreaFilled(false);
		btnAdicionar.setToolTipText("Adicionar produto");
		btnAdicionar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnAdicionar.setIcon(new ImageIcon(Produtos.class.getResource("/img/Add.png")));
		btnAdicionar.setBorder(null);
		btnAdicionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				inserirProduto();
				IsImageLoaded = false;
			}
		});

		btnAlterar = new JButton("");
		btnAlterar.setEnabled(false);
		btnAlterar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editar();
				IsImageLoaded = false;

			}
		});

		btnExcluir = new JButton("");
		btnExcluir.setEnabled(false);
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				excluir();
				IsImageLoaded = false;
			}
		});
		btnExcluir.setToolTipText("Excluir produto");
		btnExcluir.setIcon(new ImageIcon(Produtos.class.getResource("/img/Lixeira2.png")));
		btnExcluir.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnExcluir.setBorder(null);
		btnExcluir.setContentAreaFilled(false);
		btnExcluir.setBounds(303, 497, 64, 64);
		getContentPane().add(btnExcluir);
		btnAlterar.setToolTipText("Editar produto");
		btnAlterar.setIcon(new ImageIcon(Produtos.class.getResource("/img/EditarCli.png")));
		btnAlterar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnAlterar.setContentAreaFilled(false);
		btnAlterar.setBorder(null);
		btnAlterar.setBounds(452, 497, 64, 64);
		getContentPane().add(btnAlterar);
		btnAdicionar.setBounds(179, 497, 64, 64);
		getContentPane().add(btnAdicionar);
		btnLimparCampos.setIcon(new ImageIcon(Produtos.class.getResource("/img/Burracha.png")));
		btnLimparCampos.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnLimparCampos.setToolTipText("Limpar campos");
		btnLimparCampos.setContentAreaFilled(false);
		btnLimparCampos.setBorder(null);
		btnLimparCampos.setBounds(76, 497, 64, 64);
		getContentPane().add(btnLimparCampos);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(405, 71, 153, 64);
		getContentPane().add(scrollPane);

		listFornecedor = new JList();
		listFornecedor.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int linha = listFornecedor.getSelectedIndex();
				String comando = "Select * from fornecedores where razao like '" + txtFornecedor.getText() + "%'"
						+ " order by razao limit " + (linha) + ", 1";
				if (linha >= 0) {
					try {
						con = dao.conectar();
						pst = con.prepareStatement(comando);
						rs = pst.executeQuery();

						if (rs.next()) {
							scrollPane.setVisible(false);

							txtId.setText(rs.getString(1));
							txtFornecedor.setText(rs.getString(2));

							System.out.println("saida com sucesso");

						}

					} catch (SQLException SQLe) {
						SQLe.printStackTrace();
					} catch (Exception ex) {
						ex.printStackTrace();
					}

				}
			}
		});
		scrollPane.setViewportView(listFornecedor);

		JLabel lblButton = new JLabel("");
		lblButton.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {

				pesquisarBarcode();
			}
		});
		lblButton.setIcon(new ImageIcon(Produtos.class.getResource("/img/barcode.png")));
		lblButton.setBounds(22, 31, 64, 45);
		getContentPane().add(lblButton);

		txtBarcode = new JTextField();
		txtBarcode.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					pesquisarBarcode();
				}
			}
		});
		txtBarcode.setBounds(91, 43, 240, 20);
		getContentPane().add(txtBarcode);
		txtBarcode.setColumns(10);

		JLabel lblNewLabel_1 = new JLabel("C\u00F3digo");
		lblNewLabel_1.setBounds(33, 91, 46, 14);
		getContentPane().add(lblNewLabel_1);

		txtCodigo = new JTextField();
		txtCodigo.setBounds(90, 88, 103, 20);
		getContentPane().add(txtCodigo);
		txtCodigo.setColumns(10);

		btnPesquisar = new JButton("");
		btnPesquisar.setContentAreaFilled(false);
		btnPesquisar.setBorder(null);
		btnPesquisar.setIcon(new ImageIcon(Produtos.class.getResource("/img/PesquisarCli.png")));
		btnPesquisar.setToolTipText("Pesquisar");
		btnPesquisar.setForeground(SystemColor.textHighlight);
		btnPesquisar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pesquisarProduto();
			}
		});
		btnPesquisar.setBounds(203, 71, 48, 48);
		getContentPane().add(btnPesquisar);

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Fornecedor", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(380, 25, 375, 67);
		getContentPane().add(panel);
		panel.setLayout(null);

		txtFornecedor = new JTextField();
		txtFornecedor.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				pesquisarFornecedor();
			}
		});
		txtFornecedor.setBounds(25, 26, 152, 20);
		panel.add(txtFornecedor);
		txtFornecedor.setColumns(10);

		JLabel lblNewLabel_3 = new JLabel("ID");
		lblNewLabel_3.setBounds(243, 29, 24, 14);
		panel.add(lblNewLabel_3);

		txtId = new JTextField();
		txtId.setBounds(268, 26, 86, 20);
		panel.add(txtId);
		txtId.setColumns(10);

		JLabel lblNewLabel_4 = new JLabel("Produto");
		lblNewLabel_4.setBounds(33, 140, 46, 14);
		getContentPane().add(lblNewLabel_4);

		txtProduto = new JTextField();
		txtProduto.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				digitProdutos();
			}
		});
		txtProduto.setBounds(90, 137, 360, 20);
		getContentPane().add(txtProduto);
		txtProduto.setColumns(10);

		JLabel lblNewLabel_5 = new JLabel("Descri\u00E7\u00E3o");
		lblNewLabel_5.setBounds(22, 201, 58, 14);
		getContentPane().add(lblNewLabel_5);

		JLabel lblNewLabel_6 = new JLabel("Entrada");
		lblNewLabel_6.setBounds(385, 326, 58, 14);
		getContentPane().add(lblNewLabel_6);

		JLabel lblNewLabel_7 = new JLabel("Validade");
		lblNewLabel_7.setBounds(386, 387, 64, 14);
		getContentPane().add(lblNewLabel_7);

		JLabel lblNewLabel_8 = new JLabel("Valor");
		lblNewLabel_8.setBounds(50, 382, 46, 14);
		getContentPane().add(lblNewLabel_8);

		txtValor = new JTextField();
		txtValor.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				onlyNum(e);
			}
		});
		txtValor.setBounds(89, 379, 103, 20);
		getContentPane().add(txtValor);
		txtValor.setColumns(10);

		JLabel lblNewLabel_9 = new JLabel("Lucro");
		lblNewLabel_9.setBounds(217, 379, 46, 14);
		getContentPane().add(lblNewLabel_9);

		txtLucro = new JTextField();
		txtLucro.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				onlyNum(e);
			}
		});
		txtLucro.setBounds(260, 376, 38, 20);
		getContentPane().add(txtLucro);
		txtLucro.setColumns(10);

		JLabel lblNewLabel_10 = new JLabel("%");
		lblNewLabel_10.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_10.setBounds(303, 379, 28, 14);
		getContentPane().add(lblNewLabel_10);

		JLabel lblNewLabel_11 = new JLabel("Fabricante");
		lblNewLabel_11.setBounds(234, 286, 64, 14);
		getContentPane().add(lblNewLabel_11);

		txtFabricante = new JTextField();
		txtFabricante.addKeyListener(new KeyAdapter() {
		});
		txtFabricante.setBounds(303, 283, 147, 20);
		getContentPane().add(txtFabricante);
		txtFabricante.setColumns(10);

		JLabel lblNewLabel_12 = new JLabel("Estoque");
		lblNewLabel_12.setBounds(33, 332, 46, 14);
		getContentPane().add(lblNewLabel_12);

		txtEstoque = new JTextField();
		txtEstoque.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				onlyNum(e);
			}
		});
		txtEstoque.setBounds(89, 329, 51, 20);
		getContentPane().add(txtEstoque);
		txtEstoque.setColumns(10);

		JLabel lblNewLabel_13 = new JLabel("Estoque m\u00EDnimo");
		lblNewLabel_13.setBounds(171, 332, 95, 14);
		getContentPane().add(lblNewLabel_13);

		txtEstoquemin = new JTextField();
		txtEstoquemin.setColumns(10);
		txtEstoquemin.setBounds(267, 329, 51, 20);
		getContentPane().add(txtEstoquemin);

		JLabel lblNewLabel_14 = new JLabel("Unidade");
		lblNewLabel_14.setBounds(39, 430, 58, 14);
		getContentPane().add(lblNewLabel_14);

		cboUnidade = new JComboBox();
		cboUnidade.setModel(new DefaultComboBoxModel(new String[] { "", "UN", "PC", "CX", "KG", "g", "M", "CM" }));
		cboUnidade.setBounds(90, 426, 51, 22);
		getContentPane().add(cboUnidade);

		JLabel lblNewLabel_15 = new JLabel("Local");
		lblNewLabel_15.setBounds(161, 430, 38, 14);
		getContentPane().add(lblNewLabel_15);

		txtLocal = new JTextField();
		txtLocal.setBounds(203, 427, 116, 20);
		getContentPane().add(txtLocal);
		txtLocal.setColumns(10);

		JLabel lblNewLabel_4_1 = new JLabel("Lote");
		lblNewLabel_4_1.setBounds(40, 286, 46, 14);
		getContentPane().add(lblNewLabel_4_1);

		txtLote = new JTextField();
		txtLote.setColumns(10);
		txtLote.setBounds(90, 283, 121, 20);
		getContentPane().add(txtLote);

		lblimg = new JLabel("");
		lblimg.setHorizontalAlignment(SwingConstants.CENTER);
		lblimg.setHorizontalTextPosition(SwingConstants.CENTER);
		lblimg.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		lblimg.setIcon(new ImageIcon(Produtos.class.getResource("/img/CAm.png")));
		lblimg.setBounds(496, 129, 256, 256);
		getContentPane().add(lblimg);

		JButton btnImg = new JButton("Carregar imagem");
		btnImg.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loadImage();
			}
		});
		btnImg.setForeground(SystemColor.textHighlight);
		btnImg.setBounds(611, 395, 141, 23);
		getContentPane().add(btnImg);

		dataEntrada = new JDateChooser();
		dataEntrada.setEnabled(false);
		dataEntrada.setBounds(329, 351, 157, 20);
		getContentPane().add(dataEntrada);

		dataValidade = new JDateChooser();
		dataValidade.setBounds(329, 412, 157, 20);
		getContentPane().add(dataValidade);

		scrollprodutos = new JScrollPane();
		scrollprodutos.setVisible(false);
		scrollprodutos.setBounds(91, 157, 359, 64);
		getContentPane().add(scrollprodutos);

		listProdutos = new JList();
		listProdutos.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				listarProdutos();
			}
		});
		scrollprodutos.setViewportView(listProdutos);

		txtDescricao = new JTextArea();
		txtDescricao.setBounds(91, 195, 357, 77);
		getContentPane().add(txtDescricao);
		txtDescricao.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				listarProdutos();
			}
		});
		txtDescricao.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));

		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setOpaque(true);
		lblNewLabel.setBackground(new Color(77, 143, 149));
		lblNewLabel.setBounds(0, 492, 784, 69);
		getContentPane().add(lblNewLabel);

		txtBarcode.setDocument(new Validador(30));
		txtCodigo.setDocument(new Validador(50));
		txtProduto.setDocument(new Validador(45));
		txtDescricao.setDocument(new Validador(200));
		txtLote.setDocument(new Validador(45));
		txtFabricante.setDocument(new Validador(45));
		txtLocal.setDocument(new Validador(50));
	}

	@SuppressWarnings("unchecked")
	private void pesquisarFornecedor() {
		DefaultListModel<String> modelo = new DefaultListModel<>();
		listFornecedor.setModel(modelo);
		String comando = "Select * from fornecedores where razao like '" + txtFornecedor.getText() + "%'"
				+ " order by razao ";
		try {
			con = dao.conectar();
			pst = con.prepareStatement(comando);
			rs = pst.executeQuery();

			while (rs.next()) {
				listFornecedor.setVisible(true);
				scrollPane.setVisible(true);
				modelo.addElement(rs.getString(2));
				if (txtFornecedor.getText().isEmpty()) {
					scrollPane.setVisible(false);
				}
			}
			con.close();
		} catch (SQLException SQLe) {
			SQLe.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void pesquisarProduto() {
		@SuppressWarnings("unused")
		String comando2 = "select * from produtos where idproduto = ?";
		String comando = "select * from produtos inner join fornecedores";

		try {
			Connection con = dao.conectar();
			PreparedStatement pst = con.prepareStatement(comando);
			ResultSet rs = pst.executeQuery();
			if (rs.next()) {

				scrollprodutos.setVisible(false);
				btnAlterar.setEnabled(true);
				btnExcluir.setEnabled(true);

				txtCodigo.setText(rs.getString(1));
				txtProduto.setText(rs.getString(2));
				txtBarcode.setText(rs.getString(3));
				txtFabricante.setText(rs.getString(4));
				txtDescricao.setText(rs.getString(5));
				dataEntrada.setDate(rs.getDate(6));
				dataValidade.setDate(rs.getDate(7));
				Blob blob = (Blob) rs.getBlob(8);
				txtEstoque.setText(rs.getString(9));
				txtEstoquemin.setText(rs.getString(10));
				txtValor.setText(rs.getString(11));
				cboUnidade.setSelectedItem(rs.getString(12));

				txtLocal.setText(rs.getString(13));
				txtLote.setText(rs.getString(14));
				txtLucro.setText(rs.getString(15));
				txtId.setText(rs.getString(16));
				txtFornecedor.setText(rs.getString(18));
				byte[] img = blob.getBytes(1, (int) blob.length());
				BufferedImage imagem = null;
				try {
					imagem = ImageIO.read(new ByteArrayInputStream(img));
				} catch (Exception e1) {
					e1.printStackTrace();
				}

				ImageIcon icone = new ImageIcon(imagem);
				Icon foto = new ImageIcon(
						icone.getImage().getScaledInstance(lblimg.getWidth(), lblimg.getHeight(), Image.SCALE_SMOOTH));
				lblimg.setIcon(foto);

			} else {
				JOptionPane.showMessageDialog(null, "Produto n�o cadastrado");
			}
			con.close();
		} catch (SQLException SQLe) {
			SQLe.printStackTrace();
		} catch (NullPointerException Nulle) {
			Nulle.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unused")
	private void pesquisarProdutoBarcode() {
		String read = "select * from produtos where barcode = ?";
		try {
			Connection con = dao.conectar();
			PreparedStatement pst = con.prepareStatement(read);
			pst.setString(1, txtBarcode.getText());
			ResultSet rs = pst.executeQuery();
			if (rs.next()) {
				txtCodigo.setText(rs.getString(1));
				txtProduto.setText(rs.getString(3));
				txtDescricao.setText(rs.getString(4));
				txtFabricante.setText(rs.getString(5));
				txtEstoque.setText(rs.getString(8));
				txtEstoquemin.setText(rs.getString(9));
				cboUnidade.setSelectedItem(rs.getString(10));
				txtLocal.setText(rs.getString(11));
				txtId.setText(rs.getString(14));
				String setarData = rs.getString(6);
				Date dataFormatada = new SimpleDateFormat("yyyy-MM-dd").parse(setarData);
				String setarData2 = rs.getString(7);
				Date dataFormatada2 = new SimpleDateFormat("yyyy-MM-dd").parse(setarData2);
				txtValor.setText(rs.getString(12));
				txtLucro.setText(rs.getString(13));
			} else {
			}
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	private void inserirProduto() {

		if (txtBarcode.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencher o barcode");
		} else if (txtProduto.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencher o Produto");
		} else if (txtDescricao.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencher a Descrição");
		} else if (txtLote.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencher o Lote");
		} else if (txtFabricante.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencher o Fabricante");
		} else if (txtEstoque.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencher o Estoque");
		} else if (txtEstoquemin.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencher o Estoque minimo");
		} else if (txtValor.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencher o Estoque minimo");
		} else if (txtLucro.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencher o Estoque minimo");
		} else if (cboUnidade.getSelectedItem().equals("")) {
			JOptionPane.showMessageDialog(null, "Preencher o Estoque minimo");
		} else if (txtLocal.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencher o Estoque minimo");
		} else if (dataValidade.getDate() == null) {
			JOptionPane.showMessageDialog(null, "Preencher a data de validade");
			System.out.println("Date empty");
		} else if (txtId.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencher o fornecedor");
			System.out.println("Date empty");
		} else {
			String comando = "insert into produtos (barcode,produto,descricao,fabricante,datavalidade,foto,estoque,estoquemin,unidademedida,localarmazenagem,valor,lote,lucro,idFornecedor) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			try {
				con = dao.conectar();
				pst = con.prepareStatement(comando);

				pst.setString(1, txtBarcode.getText());
				pst.setString(2, txtProduto.getText());
				pst.setString(3, txtDescricao.getText());
				pst.setString(4, txtFabricante.getText());
				SimpleDateFormat formatador = new SimpleDateFormat("yyyyMMdd");
				String dataformada = formatador.format(dataValidade.getDate());
				pst.setString(5, dataformada);
				pst.setBlob(6, fis, tamanho);
				pst.setString(7, txtEstoque.getText());
				pst.setString(8, txtEstoquemin.getText());
				pst.setString(9, cboUnidade.getSelectedItem().toString());
				pst.setString(10, txtLocal.getText());
				pst.setString(11, txtValor.getText());
				pst.setString(12, txtLote.getText());
				pst.setString(13, txtLucro.getText());
				pst.setString(14, txtId.getText());
				int confirma = pst.executeUpdate();
				if (confirma == 1) {
					JOptionPane.showMessageDialog(null, "Produto cadastrado com sucesso");
					limparCampos();
				} else {
					JOptionPane.showMessageDialog(null, "Erro ao cadastrar o produto");
				}
				con.close();
			} catch (SQLIntegrityConstraintViolationException SQLIe) {
				JOptionPane.showMessageDialog(null, "Erro ao utilizar o fornecedor");
			} catch (SQLException SQLe) {
				SQLe.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void loadImage() {
		jfc.setDialogTitle("Selecione o arquivo de imagem");
		jfc.setFileFilter(new FileNameExtensionFilter("Arquivo de imagens(*.PNG,*.JPG,*.JPEG)", "png", "jpg", "jpeg"));
		int result = jfc.showOpenDialog(this);
		if (result == JFileChooser.APPROVE_OPTION) {
			try {
				IsImageLoaded = true;
				fis = new FileInputStream(jfc.getSelectedFile());
				tamanho = (int) jfc.getSelectedFile().length();

				java.awt.Image image = ImageIO.read(jfc.getSelectedFile()).getScaledInstance(lblimg.getWidth(),
						lblimg.getHeight(), java.awt.Image.SCALE_SMOOTH);

				lblimg.setIcon(new ImageIcon(image));
				lblimg.updateUI();
			} catch (Exception e) {
			}

		}
	}

	private void editar() {

		if (txtBarcode.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencher o barcode");
		} else if (txtCodigo.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencher o codigo");
		} else if (txtProduto.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencher o Produtp");
		} else if (txtDescricao.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencher a Descrição");
		} else if (txtLote.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencher o Lote");
		} else if (txtFabricante.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencher o Fabricante");
		} else if (txtEstoque.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencher o Estoque");
		} else if (txtEstoquemin.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencher o Estoque minimo");
		} else if (txtValor.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencher o Estoque minimo");
		} else if (txtLucro.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencher o Estoque minimo");
		} else if (cboUnidade.getSelectedItem().equals("")) {
			JOptionPane.showMessageDialog(null, "Preencher o Estoque minimo");
		} else if (txtLocal.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencher o Estoque minimo");
		} else if (dataValidade.getDate() == null) {
			JOptionPane.showMessageDialog(null, "Preencher a data de validade");
			System.out.println("Date empty");
		} else {

			try {

				String comando = "update produtos set barcode=?,produto=?,fabricante=?"
						+ ",descricao=?,datavalidade=?,foto=?,estoque=?,estoquemin=?"
						+ ",valor=?,unidademedida=?,localarmazenagem=?,lote=?,lucro=? where idproduto=?;";

				String comandoWithoutImg = "update produtos set barcode=?,produto=?,fabricante=?"
						+ ",descricao=?,datavalidade=?,estoque=?,estoquemin=?"
						+ ",valor=?,unidademedida=?,localarmazenagem=?,lote=?,lucro=? where idproduto=?;";

				con = dao.conectar();

				if (IsImageLoaded) {

					pst = con.prepareStatement(comando);

					pst.setString(1, txtBarcode.getText());
					pst.setString(2, txtProduto.getText());
					pst.setString(3, txtFabricante.getText());
					pst.setString(4, txtDescricao.getText());
					SimpleDateFormat formatador = new SimpleDateFormat("yyyyMMdd");
					String dataformada = formatador.format(dataValidade.getDate());
					pst.setString(5, dataformada);
					pst.setBlob(6, fis);
					pst.setString(7, txtEstoque.getText());
					pst.setString(8, txtEstoquemin.getText());
					pst.setString(9, txtValor.getText());
					pst.setString(10, (String) cboUnidade.getSelectedItem());
					pst.setString(11, txtLocal.getText());
					pst.setString(12, txtLote.getText());
					pst.setString(13, txtLucro.getText());
					pst.setString(14, txtCodigo.getText());
				} else {
					pst = con.prepareStatement(comandoWithoutImg);

					pst.setString(1, txtBarcode.getText());
					pst.setString(2, txtProduto.getText());
					pst.setString(3, txtFabricante.getText());
					pst.setString(4, txtDescricao.getText());
					SimpleDateFormat formatador = new SimpleDateFormat("yyyyMMdd");
					String dataformada = formatador.format(dataValidade.getDate());
					pst.setString(5, dataformada);
					pst.setString(6, txtEstoque.getText());
					pst.setString(7, txtEstoquemin.getText());
					pst.setString(8, txtValor.getText());
					pst.setString(9, (String) cboUnidade.getSelectedItem());
					pst.setString(10, txtLocal.getText());
					pst.setString(11, txtLote.getText());
					pst.setString(12, txtLucro.getText());
					pst.setString(13, txtCodigo.getText());
				}
				pst.executeUpdate();
				con.close();

				JOptionPane.showMessageDialog(null, "Edição feita com sucesso");
				limparCampos();

			} catch (SQLException SQLe) {
				SQLe.printStackTrace();
				JOptionPane.showMessageDialog(null, "Falha no banco de dados");
			} catch (Exception e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "Falha geral");
			}
		}
	}

	private void excluir() {
		String comando = "delete from produtos where idproduto = ?";
		int confirma = JOptionPane.showConfirmDialog(null, "Confirma a exclusão deste produto?", "Atenção!",
				JOptionPane.YES_NO_OPTION);

		if (confirma == JOptionPane.YES_OPTION) {
			try {
				con = dao.conectar();

				pst = con.prepareStatement(comando);
				pst.setString(1, txtCodigo.getText());
				pst.executeUpdate();

				con.close();
				JOptionPane.showInternalMessageDialog(null, "Removido com sucesso");
				limparCampos();

			} catch (java.sql.SQLIntegrityConstraintViolationException se) {
				JOptionPane.showInternalMessageDialog(null, "Não pode excluir o cliente (Tem registro OS)");
				System.out.println(se);
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}

	private void limparCampos() {
		txtBarcode.setText(null);
		txtCodigo.setText(null);
		txtProduto.setText(null);
		txtDescricao.setText(null);
		txtId.setText(null);
		txtLote.setText(null);
		txtFabricante.setText(null);
		txtEstoque.setText(null);
		txtEstoquemin.setText(null);
		txtValor.setText(null);
		txtLucro.setText(null);
		cboUnidade.setSelectedItem("");
		txtLocal.setText(null);
		lblimg.setIcon(new ImageIcon(Produtos.class.getResource("/img/CAm.png")));
		IsImageLoaded = false;
		dataEntrada.setDate(null);
		dataValidade.setDate(null);
		btnAdicionar.setEnabled(true);
		btnAlterar.setEnabled(false);
		btnExcluir.setEnabled(false);

		txtFornecedor.setText(null);
	}

	private void listarProdutos() {

		int linha = listProdutos.getSelectedIndex();
		String comando = "Select * from produtos where produto like '" + txtProduto.getText() + "%'"
				+ " order by produto limit " + (linha) + ", 1";
		if (linha >= 0) {
			try {
				con = dao.conectar();
				pst = con.prepareStatement(comando);
				rs = pst.executeQuery();

				if (rs.next()) {

					scrollprodutos.setVisible(false);
					btnAlterar.setEnabled(true);
					btnExcluir.setEnabled(true);
					txtCodigo.setText(rs.getString(1));
					txtProduto.setText(rs.getString(2));
					txtBarcode.setText(rs.getString(3));
					txtFabricante.setText(rs.getString(4));
					txtDescricao.setText(rs.getString(5));
					dataEntrada.setDate(rs.getDate(6));
					dataValidade.setDate(rs.getDate(7));
					Blob blob = (Blob) rs.getBlob(8);
					String setarDataent = rs.getString(6);
					Date dataEntradex = new SimpleDateFormat("yyyy-MM-dd").parse(setarDataent);
					dataEntrada.setDate(dataEntradex);
					txtEstoque.setText(rs.getString(9));
					txtEstoquemin.setText(rs.getString(10));
					txtValor.setText(rs.getString(11));
					cboUnidade.setSelectedItem(rs.getString(12));
					txtLocal.setText(rs.getString(13));
					txtLote.setText(rs.getString(14));
					txtLucro.setText(rs.getString(15));
					txtId.setText(rs.getString(16));

					byte[] img = blob.getBytes(1, (int) blob.length());
					BufferedImage imagem = null;
					try {

						imagem = ImageIO.read(new ByteArrayInputStream(img));
					} catch (Exception e1) {
						System.out.println(e1);
					}

					ImageIcon icone = new ImageIcon(imagem);
					Icon foto = new ImageIcon(icone.getImage().getScaledInstance(lblimg.getWidth(), lblimg.getHeight(),
							Image.SCALE_SMOOTH));
					lblimg.setIcon(foto);
				}
			} catch (SQLException se) {
				se.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			scrollPane.setVisible(false);
		}
	}

	@SuppressWarnings("unchecked")
	private void digitProdutos() {

		DefaultListModel<String> modelo = new DefaultListModel<>();
		listProdutos.setModel(modelo);
		String type = "Select * from produtos where produto like '" + txtProduto.getText() + "%'"
				+ " order by produto ";
		try {

			con = dao.conectar();
			pst = con.prepareStatement(type);
			rs = pst.executeQuery();
			System.out.println("Conexão");
			while (rs.next()) {
				listProdutos.setVisible(true);
				scrollprodutos.setVisible(true);
				modelo.addElement(rs.getString(2));
				if (txtProduto.getText().isEmpty()) {
					System.out.println("Condição");
					scrollprodutos.setVisible(false);
				}
			}
			con.close();
		} catch (SQLException se) {
			System.out.println(se);
		} catch (Exception e) {
			System.out.println(e);
		}

	}

	private void pesquisarBarcode() {
		String comando = "select * from produtos where barcode = ?";

		try {
			Connection con = dao.conectar();
			PreparedStatement pst = con.prepareStatement(comando);
			pst.setString(1, txtBarcode.getText());
			ResultSet rs = pst.executeQuery();
			if (rs.next()) {
				btnAlterar.setEnabled(true);
				btnExcluir.setEnabled(true);
				scrollprodutos.setVisible(false);
				txtCodigo.setText(rs.getString(1));
				txtProduto.setText(rs.getString(2));
				txtFabricante.setText(rs.getString(4));
				txtDescricao.setText(rs.getString(5));
				dataEntrada.setDate(rs.getDate(6));
				dataValidade.setDate(rs.getDate(7));
				Blob blob = (Blob) rs.getBlob(8);
				txtEstoque.setText(rs.getString(9));
				txtEstoquemin.setText(rs.getString(10));
				txtValor.setText(rs.getString(11));
				cboUnidade.setSelectedItem(rs.getString(12));
				txtLocal.setText(rs.getString(13));
				txtLote.setText(rs.getString(14));
				txtLucro.setText(rs.getString(15));
				txtId.setText(rs.getString(16));
				byte[] img = blob.getBytes(1, (int) blob.length());
				BufferedImage imagem = null;
				try {
					imagem = ImageIO.read(new ByteArrayInputStream(img));
				} catch (Exception e1) {
					e1.printStackTrace();
				}

				ImageIcon icone = new ImageIcon(imagem);
				Icon foto = new ImageIcon(
						icone.getImage().getScaledInstance(lblimg.getWidth(), lblimg.getHeight(), Image.SCALE_SMOOTH));
				lblimg.setIcon(foto);

			} else {
				JOptionPane.showMessageDialog(null, "Produto não cadastrado");
			}
			con.close();
		} catch (SQLException SQLe) {
			SQLe.printStackTrace();
		} catch (NullPointerException Nulle) {
			Nulle.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void onlyNum(KeyEvent e) {
		String caracteres = "0123456789.";
		if (!caracteres.contains(e.getKeyChar() + "")) {
			e.consume();
		}
	}
}