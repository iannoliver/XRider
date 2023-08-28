package view;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

//import com.toedter.calendar.JDateChooser;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;

import javax.swing.table.DefaultTableModel;
import javax.xml.crypto.Data;

import model.DAO;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Toolkit;
import javax.swing.JList;
import java.awt.SystemColor;
import java.awt.Cursor;

import com.toedter.calendar.JDateChooser;
import java.awt.ScrollPane;
import javax.swing.SwingConstants;

public class Produtoss extends JDialog {

	private static final long serialVersionUID = 1L;
	// Instance objects JDBC
	DAO dao = new DAO();
	private Connection con;
	private PreparedStatement pst;
	private ResultSet rs;
	// Instance explorer for loading files
	JFileChooser jfc = new JFileChooser();
	// Instance objects inputs
	private FileInputStream fis;

	// Global variable to storage files input sizes
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
	private JComboBox cboUnidade;
	private JTextField txtLote;
	private JDateChooser dataEntrada;
	private JButton btnAdicionar;
	private JDateChooser dataValidade;
	private JLabel lblimg;
	private JList listFornecedor;
	private JScrollPane scrollPane;
	private JList listProdutos;
	private JScrollPane scrollprodutos;
	private JButton btnAlterar;
	private JButton btnExcluir;
	// private JDateChooser dateEntrada;
	// private JDateChooser dateValidade;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Produtoss dialog = new Produtoss();
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
	public Produtoss() {
		getContentPane().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
			System.out.println("FODA-SE");
			scrollprodutos.setVisible(false);
			scrollPane.setVisible(false);
			}
		});
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent e) {
				txtBarcode.requestFocus();
			}

		});
		setTitle("Produtos");
		setResizable(false);
		setModal(true);
		setBounds(100, 100, 800, 600);
		getContentPane().setLayout(null);

		scrollPane = new JScrollPane();
		scrollPane.setVisible(false);
		scrollPane.setBounds(405, 71, 153, 64);
		getContentPane().add(scrollPane);

		listFornecedor = new JList();
		listFornecedor.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int linha = listFornecedor.getSelectedIndex();
				String comando = "Select * from fornecedoresDida where razao like '" + txtFornecedor.getText() + "%'"
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
						// TODO: handle exception
						SQLe.printStackTrace();
					} catch (Exception ex) {
						// TODO: handle exception
						ex.printStackTrace();
					}

				}
			}
		});
		scrollPane.setViewportView(listFornecedor);

		// dateEntrada = new JDateChooser();
		// dateEntrada.setBounds(379, 226, 147, 20);
		// getContentPane().add(dateEntrada);

		JLabel lblButton = new JLabel("");
		lblButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				pesquisarBarcode();
			}
		});
		lblButton.setIcon(new ImageIcon(Produtoss.class.getResource("/img/barcode.png")));
		lblButton.setBounds(22, 31, 64, 45);
		getContentPane().add(lblButton);

		txtBarcode = new JTextField();
		txtBarcode.addKeyListener(new KeyAdapter() {
			@Override
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
		txtCodigo.setBounds(102, 88, 103, 20);
		getContentPane().add(txtCodigo);
		txtCodigo.setColumns(10);

		JButton btnPesquisar = new JButton("Pesquisar");
		btnPesquisar.setForeground(SystemColor.textHighlight);
		btnPesquisar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pesquisarProduto();
			}
		});
		btnPesquisar.setBounds(203, 87, 95, 23);
		getContentPane().add(btnPesquisar);

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Fornecedor", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(380, 25, 375, 67);
		getContentPane().add(panel);
		panel.setLayout(null);

		txtFornecedor = new JTextField();
		txtFornecedor.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				pesquisarFornecedor();
			}
		});
		txtFornecedor.setBounds(25, 26, 152, 20);
		panel.add(txtFornecedor);
		txtFornecedor.setColumns(10);

		JLabel lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setIcon(new ImageIcon(Produtoss.class.getResource("/img/search.png")));
		lblNewLabel_2.setBounds(185, 22, 24, 24);
		panel.add(lblNewLabel_2);

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
			@Override
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

		// dateValidade = new JDateChooser();
		// dateValidade.setBounds(577, 226, 147, 20);
		// getContentPane().add(dateValidade);

		JLabel lblNewLabel_8 = new JLabel("Valor");
		lblNewLabel_8.setBounds(50, 382, 46, 14);
		getContentPane().add(lblNewLabel_8);

		txtValor = new JTextField();
		txtValor.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				onlyNum(e);}
		});
		txtValor.setBounds(89, 379, 103, 20);
		getContentPane().add(txtValor);
		txtValor.setColumns(10);

		JLabel lblNewLabel_9 = new JLabel("Lucro");
		lblNewLabel_9.setBounds(217, 379, 46, 14);
		getContentPane().add(lblNewLabel_9);

		txtLucro = new JTextField();
		txtLucro.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				onlyNum(e);}
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
		txtFabricante.setBounds(303, 283, 147, 20);
		getContentPane().add(txtFabricante);
		txtFabricante.setColumns(10);

		JLabel lblNewLabel_12 = new JLabel("Estoque");
		lblNewLabel_12.setBounds(33, 332, 46, 14);
		getContentPane().add(lblNewLabel_12);

		txtEstoque = new JTextField();
		txtEstoque.addKeyListener(new KeyAdapter() {
			@Override
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
		txtEstoquemin.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				onlyNum(e);
			}
		});
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

		btnAdicionar = new JButton("");
		btnAdicionar.setContentAreaFilled(false);
		btnAdicionar.setToolTipText("Adicionar produto");
		btnAdicionar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnAdicionar.setIcon(new ImageIcon(Produtoss.class.getResource("/img/cliAdd.png")));
		btnAdicionar.setBorder(null);
		btnAdicionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				inserirProduto();
				
			}
		});
		btnAdicionar.setBounds(536, 470, 64, 64);
		getContentPane().add(btnAdicionar);

		btnAlterar = new JButton("");
		btnAlterar.setEnabled(false);
		btnAlterar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editar();
				

			}
		});
		btnAlterar.setToolTipText("Editar produto");
		btnAlterar.setIcon(new ImageIcon(Produtoss.class.getResource("/img/cliEdit.png")));
		btnAlterar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnAlterar.setContentAreaFilled(false);
		btnAlterar.setBorder(null);
		btnAlterar.setBounds(614, 470, 64, 64);
		getContentPane().add(btnAlterar);

		btnExcluir = new JButton("");
		btnExcluir.setEnabled(false);
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				excluir();
			
			}
		});
		btnExcluir.setToolTipText("Excluir produto");
		btnExcluir.setIcon(new ImageIcon(Produtoss.class.getResource("/img/cliRemove.png")));
		btnExcluir.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnExcluir.setBorder(null);
		btnExcluir.setContentAreaFilled(false);
		btnExcluir.setBounds(691, 470, 64, 64);
		getContentPane().add(btnExcluir);

		JLabel lblNewLabel_4_1 = new JLabel("Lote");
		lblNewLabel_4_1.setBounds(40, 286, 46, 14);
		getContentPane().add(lblNewLabel_4_1);

		txtLote = new JTextField();
		txtLote.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
			onlyNum(e);
			}
		});
		txtLote.setColumns(10);
		txtLote.setBounds(90, 283, 50, 20);
		getContentPane().add(txtLote);

		lblimg = new JLabel("");
		lblimg.setHorizontalAlignment(SwingConstants.CENTER);
		lblimg.setHorizontalTextPosition(SwingConstants.CENTER);
		lblimg.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		lblimg.setIcon(new ImageIcon(Produtoss.class.getResource("/img/produtosIcon.png")));
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

		JButton btnLimparCampos = new JButton("");
		btnLimparCampos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limparCampos();
			}
		});
		btnLimparCampos.setIcon(new ImageIcon(Produtoss.class.getResource("/img/erase.png")));
		btnLimparCampos.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnLimparCampos.setToolTipText("Limpar campos");
		btnLimparCampos.setContentAreaFilled(false);
		btnLimparCampos.setBorder(null);
		btnLimparCampos.setBounds(22, 470, 64, 64);
		getContentPane().add(btnLimparCampos);

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
			@Override
			public void mouseClicked(MouseEvent e) {
				listarProdutos();
			}
		});
		scrollprodutos.setViewportView(listProdutos);

		txtDescricao = new JTextArea();
		txtDescricao.setBounds(91, 195, 357, 77);
		getContentPane().add(txtDescricao);
		txtDescricao.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				listarProdutos();
			}
		});
		txtDescricao.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));

	}// fim do construtor

	private void pesquisarFornecedor() {
		DefaultListModel<String> modelo = new DefaultListModel<>();
		listFornecedor.setModel(modelo);
		String comando = "Select * from fornecedoresDida where razao like '" + txtFornecedor.getText() + "%'"
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
			// TODO: handle exception=[
			SQLe.printStackTrace();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	private void pesquisarProduto() {
		// System.out.println("teste bot�o pesquisar produto");
		String comando = "select * from produtosDida where idproduto = ?";
		try {
			Connection con = dao.conectar();
			PreparedStatement pst = con.prepareStatement(comando);
			pst.setString(1, txtCodigo.getText());
			ResultSet rs = pst.executeQuery();
			if (rs.next()) {

				scrollprodutos.setVisible(false);
				//validação
				btnAlterar.setEnabled(true);
				btnExcluir.setEnabled(true);
				
				txtCodigo.setText(rs.getString(1));
				txtProduto.setText(rs.getString(2));
				txtBarcode.setText(rs.getString(3));
				txtFabricante.setText(rs.getString(4));
				txtDescricao.setText(rs.getString(5));
				dataEntrada.setDate(rs.getDate(6));
				// via das duvidas
//				String setarDataent = rs.getString(6);
//				Date dataEntradex = new SimpleDateFormat("yyyy-MM-dd").parse(setarDataent);
//				dataEntrada.setDate(dataEntradex);
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
				}
				catch (Exception e1) {
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
		}catch (SQLException SQLe) {
			// TODO: handle exception
			SQLe.printStackTrace();
		}
		catch (NullPointerException Nulle) {
			// TODO: handle exception
			Nulle.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

//	private void pesquisarBarcode() {
//		// System.out.println("teste bot�o pesquisar produto");
//		String comando = "select * from produtosDida where barcode = ?";
//		try {
//			Connection con = dao.conectar();
//			PreparedStatement pst = con.prepareStatement(comando);
//			pst.setString(1, txtCodigo.getText());
//			ResultSet rs = pst.executeQuery();
//			if (rs.next()) {
//				txtCodigo.setText(rs.getString(1));
//				txtProduto.setText(rs.getString(2));
//				txtBarcode.setText(rs.getString(3));
//				txtFabricante.setText(rs.getString(4));
//				txtDescricao.setText(rs.getString(5));
//				dataEntrada.setDate(rs.getDate(6));
//				dataValidade.setDate(rs.getDate(7));
//
//				Blob blob = (Blob) rs.getBlob(8);
//
//				String setarDataent = rs.getString(6);
//				Date dataEntradex = new SimpleDateFormat("yyyy-MM-dd").parse(setarDataent);
//				dataEntrada.setDate(dataEntradex);
//				txtEstoque.setText(rs.getString(9));
//				txtEstoquemin.setText(rs.getString(10));
//
//				cboUnidade.setSelectedItem(rs.getString(12));
//				txtValor.setText(rs.getString(14));
//				txtLucro.setText(rs.getString(15));
//				txtFornecedor.setText(rs.getString(16));
//
//				// txtBarcode.setText(rs.getString(2));
////				txtProduto.setText(rs.getString(3));
////				txtaDescricao.setText(rs.getString(4));
////				//etar a data do JCalendar
////				String setardataEnt = rs.getString(8);
////				txtFabricante.setText(rs.getString(5));
////				txtEstoque.setText(rs.getString(8));
////				txtEstoquemin.setText(rs.getNString(9));
////				cboUnidade.setSelectedItem(rs.getString(10));
////				txtLocal.setText(rs.getString(11));
////				txtId.setText(rs.getString(14));
////				//formata��o da data recebida pelo MySQL
////				// JCalendar - formata��o para exibi��o
////				String setarData = rs.getString(6);
////				//apoio a l�gica
////				//System.out.println(setarData);
////				Date dataFormatada = new SimpleDateFormat("yyyy-MM-dd").parse(setarData);
////				//dateEntrada.setDate(dataFormatada);
////				String setarData2 = rs.getString(7);
////				Date dataFormatada2 = new SimpleDateFormat("yyyy-MM-dd").parse(setarData2);
////				//dateValidade.setDate(dataFormatada2);
////				txtCusto.setText(rs.getString(12));
////				txtLucro.setText(rs.getString(13));
//			} else {
//				JOptionPane.showMessageDialog(null, "Produto não cadastrado");
//			}
//			con.close();
//		} catch (Exception e) {
//			System.out.println(e);
//		}
//	}

	private void pesquisarProdutoBarcode() {
		// System.out.println("teste bot�o pesquisar produto");
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
				// formata��o da data recebida pelo MySQL
				// JCalendar - formata��o para exibi��o
				String setarData = rs.getString(6);
				// apoio a l�gica
				// System.out.println(setarData);
				Date dataFormatada = new SimpleDateFormat("yyyy-MM-dd").parse(setarData);
				// dateEntrada.setDate(dataFormatada);
				String setarData2 = rs.getString(7);
				Date dataFormatada2 = new SimpleDateFormat("yyyy-MM-dd").parse(setarData2);
				// dateValidade.setDate(dataFormatada2);
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
		// Fazer validacao

		if (txtBarcode.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencher o barcode");
//		} else if (txtCodigo.getText().isEmpty()) {
//			JOptionPane.showMessageDialog(null, "Preencher o codigo");
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
//		else if (lblimg.getIcon().equals("/bin/img/produtosIcon.png")) {JOptionPane.showMessageDialog(null, "Carregar imagem");
		} else {

			/**
			 * Comando insert para criacao de dados no produtos Dida = didatico, versao do
			 * professor O banco de dados produtosDida tambem e diferente Total de 12
			 * colunas
			 */
			String comando = "insert into produtosDida (barcode,produto,descricao,fabricante,datavalidade,foto,estoque,estoquemin,unidademedida,localarmazenagem,valor,lote,lucro,idFornecedor) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			try {

//			Connection con = dao.conectar();
//			PreparedStatement pst = con.prepareStatement(comando);
				con = dao.conectar();
				pst = con.prepareStatement(comando);

				pst.setString(1, txtBarcode.getText());
				pst.setString(2, txtProduto.getText());
				pst.setString(3, txtDescricao.getText());
				pst.setString(4, txtFabricante.getText());
				// Formatacao do valor do JCalendar para a insercao correta no banco
				SimpleDateFormat formatador = new SimpleDateFormat("yyyyMMdd");
				/**
				 * Teoricamente a data formada e levada ao setString 5, que e a coluna da data
				 * privada.
				 */
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
	}//
//	private void editar() {
//		//Validacao dos campos
//		
//		
//		//Comando SQL
//		String comando = "update * from produtos set=?";
//		//Preparacao da conexao com o banco de dados
//		con = dao.conectar();
//		pst = con.prepareStatement(comando);
//	}

	private void loadImage() {

		/**
		 * This method opens an internal File explorer from java (JFileChooser)
		 * 
		 */
		jfc.setDialogTitle("Selecione o arquivo de imagem");
		jfc.setFileFilter(new FileNameExtensionFilter("Arquivo de imagens(*.PNG,*.JPG,*.JPEG)", "png", "jpg", "jpeg"));

//		System.out.println(jfc.getFileFilter());

		// File explorer execution and stores File explorer selected file to result
		int result = jfc.showOpenDialog(this);
		if (result == JFileChooser.APPROVE_OPTION) {
			try {
				IsImageLoaded = true;
				// Catches the file
				fis = new FileInputStream(jfc.getSelectedFile());
				// Storages file size by length, converts long to int
				tamanho = (int) jfc.getSelectedFile().length();

				// na via das duvidas, trocar para java.awt
				java.awt.Image image = ImageIO.read(jfc.getSelectedFile()).getScaledInstance(lblimg.getWidth(),
						lblimg.getHeight(), java.awt.Image.SCALE_SMOOTH);

				// set Jlabel
				lblimg.setIcon(new ImageIcon(image));
				lblimg.updateUI();
			} catch (Exception e) {
				// TODO: handle exception
			}
			// lblimg
		}
	}//

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
//		else if (lblimg.getIcon().equals("/bin/img/produtosIcon.png")) {JOptionPane.showMessageDialog(null, "Carregar imagem");
		} else {

			try {

				String comando = "update produtosDida set barcode=?,produto=?,fabricante=?"
						+ ",descricao=?,datavalidade=?,foto=?,estoque=?,estoquemin=?"
						+ ",valor=?,unidademedida=?,localarmazenagem=?,lote=?,lucro=? where idproduto=?;";
				String comandoWithoutImg = "update produtosDida set barcode=?,produto=?,fabricante=?"
						+ ",descricao=?,datavalidade=?,estoque=?,estoquemin=?"
						+ ",valor=?,unidademedida=?,localarmazenagem=?,lote=?,lucro=? where idproduto=?;";
				
				con = dao.conectar();
				pst = con.prepareStatement(comando);

				// id por ultimo
				pst.setString(14, txtCodigo.getText());
				// resto

				SimpleDateFormat formatador = new SimpleDateFormat("yyyyMMdd");
				String dataformada = formatador.format(dataValidade.getDate());

				
				pst.setString(1, txtBarcode.getText());
				pst.setString(2, txtProduto.getText());
				pst.setString(3, txtFabricante.getText());
				pst.setString(4, txtDescricao.getText());
				pst.setString(5, dataformada);
				
				if(IsImageLoaded) {
					System.out.println("modified");
					
					pst.setBlob(6,fis);
					pst.setString(7, txtEstoque.getText());
					pst.setString(8, txtEstoquemin.getText());
					pst.setString(9, txtValor.getText());
					pst.setString(10, (String) cboUnidade.getSelectedItem());
					pst.setString(11, txtLocal.getText());
					pst.setString(12, txtLote.getText());
					pst.setString(13, txtLucro.getText());
				}else {
					System.out.println("not modified");
					
					pst.setString(6, txtEstoque.getText());
					pst.setString(7, txtEstoquemin.getText());
					pst.setString(8, txtValor.getText());
					pst.setString(11, (String) cboUnidade.getSelectedItem());
					pst.setString(12, txtLocal.getText());
					pst.setString(13, txtLote.getText());
					pst.setString(14, txtLucro.getText());
				}
				
				
				
//				pst.setString(1, txtBarcode.getText());
//				pst.setString(2, txtProduto.getText());
//				pst.setString(3, txtFabricante.getText());
//				pst.setString(4, txtDescricao.getText());
//				pst.setString(5, dataformada);
//								
//				pst.setString(7, txtEstoque.getText());
//				pst.setString(8, txtEstoquemin.getText());
//				pst.setString(9, txtValor.getText());
//				pst.setString(10, (String) cboUnidade.getSelectedItem());
//				pst.setString(11, txtLocal.getText());
//				pst.setString(12, txtLote.getText());
//				pst.setString(13, txtLucro.getText());

//				pst.setString(12, dataEntrada.getText());
				
				pst.executeUpdate();
				
				JOptionPane.showMessageDialog(null, "Edição feita com sucesso");
				limparCampos();
			} catch (SQLException SQLe) {
				// TODO: handle exception
				SQLe.printStackTrace();
				JOptionPane.showMessageDialog(null, "Falha no banco de dados");
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "Falha geral");
			}
		}
	}

	private void excluir() {
		String comando = "delete from produtosDida where idproduto = ?";
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

//			limparCampos();
			} catch (java.sql.SQLIntegrityConstraintViolationException se) {
				//
				JOptionPane.showInternalMessageDialog(null, "Não pode excluir o cliente (Tem registro OS)");
				System.out.println(se);
			} catch (Exception e) {
				//
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
		lblimg.setIcon(new ImageIcon(Produtoss.class.getResource("/img/produtosIcon.png")));
		IsImageLoaded = false;
		
		// Validação
		btnAdicionar.setEnabled(true);
		btnAlterar.setEnabled(false);
		btnExcluir.setEnabled(false);
		
//		dataEntrada.setText(null);
//		dataValidade.setText(null);
	}
	private void listarProdutos() {
		//validação
		
		
		int linha = listProdutos.getSelectedIndex();
		String comando = "Select * from produtosDida where produto like '" + txtProduto.getText() + "%'"
				+ " order by produto limit " + (linha) + ", 1";
		if (linha >= 0) {
			try {
				con = dao.conectar();
				pst = con.prepareStatement(comando);
				rs = pst.executeQuery();

				if (rs.next()) {
					scrollprodutos.setVisible(false);
					
					//validação
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

	private void digitProdutos() {

		DefaultListModel<String> modelo = new DefaultListModel<>();
		listProdutos.setModel(modelo);
		String type = "Select * from produtosDida where produto like '" + txtProduto.getText() + "%'"
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
//					txt.setVisible(false);

					scrollprodutos.setVisible(false);
				}
			}
			con.close();
		} catch (SQLException se) {
			System.out.println(se);
		} catch (Exception e) {
			System.out.println(e);
		}

	}//
	private void pesquisarBarcode() {
		// System.out.println("teste bot�o pesquisar produto");
		String comando = "select * from produtosDida where barcode = ?";
		//validação
		
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
//				txtBarcode.setText(rs.getString(3));
				txtFabricante.setText(rs.getString(4));
				txtDescricao.setText(rs.getString(5));
				dataEntrada.setDate(rs.getDate(6));
				// via das duvidas
//				String setarDataent = rs.getString(6);
//				Date dataEntradex = new SimpleDateFormat("yyyy-MM-dd").parse(setarDataent);
//				dataEntrada.setDate(dataEntradex);
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
				}
				catch (Exception e1) {
					e1.printStackTrace();
				}

				ImageIcon icone = new ImageIcon(imagem);
				Icon foto = new ImageIcon(
						icone.getImage().getScaledInstance(lblimg.getWidth(), lblimg.getHeight(), Image.SCALE_SMOOTH));
				lblimg.setIcon(foto);

			} else {
//				JOptionPane.showMessageDialog(null, "Produto n�o cadastrado");
			}
			con.close();
		}catch (SQLException SQLe) {
			// TODO: handle exception
			SQLe.printStackTrace();
		}
		catch (NullPointerException Nulle) {
			// TODO: handle exception
			Nulle.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void onlyNum(KeyEvent e) {
		char c = e.getKeyChar();
		if (Character.isLetter(c)) {
			e.consume();
		}
	}
}// fim do codigo
