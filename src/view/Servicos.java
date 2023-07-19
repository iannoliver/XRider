package view;

import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import model.DAO;
import utils.Validador;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import javax.swing.JScrollPane;
import javax.swing.JList;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileOutputStream;

import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Toolkit;

public class Servicos extends JDialog {
	DAO dao = new DAO();
	private Connection con;
	private PreparedStatement pst;
	private ResultSet rs;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField txtOS;
	private JTextField txtData;
	private JTextField txtBicicleta;
	private JTextField txtDefeito;
	private JTextField txtValor;
	private JTextField txtID;
	private JButton btnAdicionar;
	private JButton btnEditar;
	private JButton btnExcluir;
	private JButton btnLimpar;
	private JButton btnBuscar;
	private JTextField txtCliente;
	private JScrollPane scrollPaneServicos;
	private JList listServicos;
	private JLabel lblNewLabel_4;
	private JButton btnOS;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Servicos dialog = new Servicos();
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
	public Servicos() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(Servicos.class.getResource("/img/IconBike.png")));
		getContentPane().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				scrollPaneServicos.setVisible(false);
			}
		});
		setTitle("Serviços");
		setModal(true);
		setBounds(100, 100, 516, 392);
		getContentPane().setLayout(null);
		
		JLabel lblOS = new JLabel("OS: ");
		lblOS.setBounds(28, 30, 46, 14);
		getContentPane().add(lblOS);
		
		JLabel lblNewLabel = new JLabel("Data:");
		lblNewLabel.setBounds(28, 63, 46, 14);
		getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Bicicleta: ");
		lblNewLabel_1.setBounds(28, 102, 73, 14);
		getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("Defeito: ");
		lblNewLabel_1_1.setBounds(28, 134, 73, 14);
		getContentPane().add(lblNewLabel_1_1);
		
		JLabel lblNewLabel_2 = new JLabel("Valor: ");
		lblNewLabel_2.setBounds(28, 169, 62, 14);
		getContentPane().add(lblNewLabel_2);
		
		txtOS = new JTextField();
		txtOS.setEditable(false);
		txtOS.setBounds(51, 27, 55, 20);
		getContentPane().add(txtOS);
		txtOS.setColumns(10);
		
		txtData = new JTextField();
		txtData.setEditable(false);
		txtData.setBounds(61, 60, 170, 20);
		getContentPane().add(txtData);
		txtData.setColumns(10);
		
		txtBicicleta = new JTextField();
		txtBicicleta.setBounds(86, 99, 348, 20);
		getContentPane().add(txtBicicleta);
		txtBicicleta.setColumns(10);
		txtBicicleta.setDocument(new Validador(200));
		
		txtDefeito = new JTextField();
		txtDefeito.setColumns(10);
		txtDefeito.setBounds(75, 131, 368, 20);
		getContentPane().add(txtDefeito);
		txtDefeito.setDocument(new Validador(200));
		
		txtValor = new JTextField();
		txtValor.setBounds(61, 166, 123, 20);
		getContentPane().add(txtValor);
		txtValor.setColumns(10);
		txtValor.setDocument(new Validador(10));
		
		btnBuscar = new JButton("");
		btnBuscar.setBorder(null);
		btnBuscar.setContentAreaFilled(false);
		btnBuscar.setToolTipText("Buscar");
		btnBuscar.setIcon(new ImageIcon(Servicos.class.getResource("/img/PesquisarCli.png")));
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buscar();
			}
		});
		btnBuscar.setBounds(117, 11, 48, 48);
		getContentPane().add(btnBuscar);
		
		getRootPane().setDefaultButton(btnBuscar);
		
		btnAdicionar = new JButton("");
		btnAdicionar.setContentAreaFilled(false);
		btnAdicionar.setBorder(null);
		btnAdicionar.setIcon(new ImageIcon(Servicos.class.getResource("/img/Add.png")));
		btnAdicionar.setToolTipText("Adicionar");
		btnAdicionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				adicionar();
			}
		});
		btnAdicionar.setBounds(53, 278, 48, 48);
		getContentPane().add(btnAdicionar);
		
		btnEditar = new JButton("");
		btnEditar.setIcon(new ImageIcon(Servicos.class.getResource("/img/EditarCli.png")));
		btnEditar.setToolTipText("Editar");
		btnEditar.setBorder(null);
		btnEditar.setContentAreaFilled(false);
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editar();
			}
		});
		btnEditar.setEnabled(false);
		btnEditar.setBounds(183, 278, 48, 48);
		getContentPane().add(btnEditar);
		
		btnExcluir = new JButton("");
		btnExcluir.setIcon(new ImageIcon(Servicos.class.getResource("/img/Lixeira2.png")));
		btnExcluir.setContentAreaFilled(false);
		btnExcluir.setBorder(null);
		btnExcluir.setToolTipText("Editar");
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				excluir();
			}
		});
		btnExcluir.setEnabled(false);
		btnExcluir.setBounds(291, 278, 48, 48);
		getContentPane().add(btnExcluir);
		
		btnLimpar = new JButton("");
		btnLimpar.setBorder(null);
		btnLimpar.setContentAreaFilled(false);
		btnLimpar.setToolTipText("Limpar");
		btnLimpar.setIcon(new ImageIcon(Servicos.class.getResource("/img/eraser.png")));
		btnLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limparCampos();
			}
		});
		btnLimpar.setBounds(386, 278, 48, 48);
		getContentPane().add(btnLimpar);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Cliente", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(246, 11, 232, 77);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		scrollPaneServicos = new JScrollPane();
		scrollPaneServicos.setVisible(false);
		scrollPaneServicos.setBounds(10, 33, 199, 33);
		panel.add(scrollPaneServicos);
		
		listServicos = new JList();
		listServicos.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				 buscarListaServicos(); 
			}
		});
		scrollPaneServicos.setViewportView(listServicos);
		
		txtID = new JTextField();
		txtID.setEditable(false);
		txtID.setBounds(47, 46, 86, 20);
		panel.add(txtID);
		txtID.setColumns(10);
		txtID.setDocument(new Validador(3));
		
		JLabel lblNewLabel_3 = new JLabel("ID:");
		lblNewLabel_3.setBounds(10, 49, 46, 14);
		panel.add(lblNewLabel_3);
		
		txtCliente = new JTextField();
		txtCliente.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				listarServicos();
			}
		});
		txtCliente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		txtCliente.setBounds(10, 18, 199, 17);
		panel.add(txtCliente);
		txtCliente.setColumns(10);
		
		lblNewLabel_4 = new JLabel("");
		lblNewLabel_4.setOpaque(true);
		lblNewLabel_4.setBackground(new Color(12, 139, 136));
		lblNewLabel_4.setBounds(0, 246, 500, 107);
		getContentPane().add(lblNewLabel_4);
		
		btnOS = new JButton("");
		btnOS.setIcon(new ImageIcon(Servicos.class.getResource("/img/OS.png")));
		btnOS.setToolTipText("OS");
		btnOS.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				imprimirOS();
			}
		});
		btnOS.setBounds(230, 188, 48, 48);
		getContentPane().add(btnOS);

	}
	
	private void limparCampos() {
		txtID.setText(null);
		txtOS.setText(null);
		txtData.setText(null);
		txtBicicleta.setText(null);
		txtDefeito.setText(null);
		txtValor.setText(null);
		txtCliente.setText(null);
		btnAdicionar.setEnabled(false);
		btnEditar.setEnabled(false);
		btnExcluir.setEnabled(false);
		btnBuscar.setEnabled(true);
		scrollPaneServicos.setVisible(false);
	}
	
	private void buscarListaServicos() {
		//variável que captura o indice da linha da lista
		int linha = listServicos.getSelectedIndex();
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
					scrollPaneServicos.setVisible(false);
					//setar os campos
					txtID.setText(rs.getString(1));
					txtCliente.setText(rs.getString(2));
				} else {
					JOptionPane.showMessageDialog(null, "Cliente inexistente");
				}
				//fechar conexão
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		} else {
			// se não existir no banco um usuário da lista
				scrollPaneServicos.setVisible(false);
		}
	}
	
	/**
	 * método usado para listar o nome dos usuarios da lista
	 */
	private void listarServicos() {
		//a linha abaixo cria um objeto usando como referência um vetor dinâmico, 
		//este objeto ira temporariamente armazenar os nomes
		DefaultListModel<String> modelo = new DefaultListModel<>();
		//setar o modelo (vetor da lista)
		listServicos.setModel(modelo);
		//Query (instrução sql)
		String readLista = "select * from clientes where cliente like '" + txtCliente.getText() + "%'" + "order by cliente";
		try {
			con = dao.conectar();
			pst= con.prepareStatement(readLista);
			rs = pst.executeQuery();
			//uso do while para trazer os usuarios enqunto existir
			while(rs.next()) {
				//mostrar barra de rolagem
				scrollPaneServicos.setVisible(true);
				//adicionar os usuarios ao vetor -> lista
				modelo.addElement(rs.getString(2));
				//esconder a lista se nenhuma letra for digitada
				if (txtCliente.getText().isEmpty()) {
					scrollPaneServicos.setVisible(false);
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
			if (txtBicicleta.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha a Bicicleta");
			txtBicicleta.requestFocus();
		}  else if (txtID.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o ID");
			txtID.requestFocus();
		}else if (txtDefeito.getText().isEmpty()) {
				JOptionPane.showMessageDialog(null, "Preencha o Defeito");
				txtDefeito.requestFocus();
		} else {
			
			//lógica principal
			String create = "insert into servicos (bicicleta, defeito, valor, idcli) values (?, ?, ?, ?);";
			//tratamento de exceções
			try {
				//abrir a conexão
				con = dao.conectar();
				//PREPARA A EXECUÇÃO DA QUERY(instrução SQL - CRUD Create)
				pst = con.prepareStatement(create);
				pst.setString(1,txtBicicleta.getText());
				pst.setString(2,txtDefeito.getText());
				pst.setString(3,txtValor.getText());
				pst.setString(4,txtID.getText());
				//executa a query(Instrução SQL (CRUD - Create)
				pst.executeUpdate();
				//confirmar
				JOptionPane.showMessageDialog(null, "Novo Serviço Adicionado!!");
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
		String numOS = JOptionPane.showInputDialog("número da OS");
		String read = "select * from servicos where os = ?";
		//Tratramento de exceções
		try {
			//abrir a conexão
			con = dao.conectar();
			//PREPARA A EXECUÇÃO DA QUERY(instrução SQL - CRUD Read) 
			pst = con.prepareStatement(read);
			//substituir a ?(parâmetro) pelonúmero da OS
			pst.setString(1,  numOS);
			//executar a Query e buscar o resultado
			rs = pst.executeQuery();
			// uso da estrutura IFELSE para verificar se existe o contato
			// rs.next() -> se existir um contato no banco
			if (rs.next()) {
				//preeccher as caixas de texto com informações
				txtOS.setText(rs.getString(1));
				txtData.setText(rs.getString(2));
				txtBicicleta.setText(rs.getString(3));
				txtDefeito.setText(rs.getString(4));
				txtValor.setText(rs.getString(5));
				txtID.setText(rs.getString(6));
				btnEditar.setEnabled(true);
				btnExcluir.setEnabled(true);
				btnBuscar.setEnabled(false);
			} else {
				//se não exisir um contato
				JOptionPane.showMessageDialog(null, "Ordem de Serviço Inexistente");
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
		int confirm = JOptionPane.showConfirmDialog(null, "Confirma a exclusão desta OS ?", "Atenção!!",JOptionPane.YES_NO_OPTION);
		if (confirm == JOptionPane.YES_OPTION) {
			//CRUD - Delete
			String delete = "delete from servicos where os = ?";
			//tratamento de exceções
			try {
				//abrir a conexão
				con = dao.conectar();
				//preparar a query (instrução sql)
				pst = con.prepareStatement(delete);
				//substituir a ? pelo id do contato
				pst.setString(1, txtOS.getText());
				//executar a query
				pst.executeUpdate();
				//limpar campos
				limparCampos();
				//exibir uma mensagem de usuário
				JOptionPane.showMessageDialog(null, "Serviço excluído!");
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
		if (txtBicicleta.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha a Bicicleta");
			txtBicicleta.requestFocus();
		}  else if (txtID.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o ID");
			txtID.requestFocus();
		}else if (txtDefeito.getText().isEmpty()) {
				JOptionPane.showMessageDialog(null, "Preencha o Defeito");
				txtDefeito.requestFocus();
		} else {
			//Lógica principal
			//CRUD - Update
			String update = "update servicos set os = ?, bicicleta = ?, defeito = ?, valor = ? where idcli = ?";
			try {
				//abrir conexão
				con = dao.conectar();
				//prepara a query (instrução sql)
				pst = con.prepareStatement(update);
				pst.setString(1,txtOS.getText());
				pst.setString(2,txtBicicleta.getText());
				pst.setString(3,txtDefeito.getText());
				pst.setString(4,txtValor.getText());
				pst.setString(5,txtID.getText());
				
				//executar a query
				pst.executeUpdate();
				//confirmar para o usuário
				JOptionPane.showMessageDialog(null, "Dados do cliente editados com sucesso!!");
				//Limpar os campos
				limparCampos();
				//fechar conexão
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}//Fim do método editar contato
	
	private void imprimirOS() {
		// instanciar objeto para usar os métodos da biblioteca
		Document document = new Document();
		// documento pdf
		if (txtID.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o ID do Cliente");
			txtID.requestFocus();
		} else if (txtOS.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Selecione a OS");
			txtOS.requestFocus();
		}
		else {
			try {
			// criar um documento em branco (pdf) de nome clientes.pdf
			PdfWriter.getInstance(document, new FileOutputStream("os.pdf"));
			// abrir o documento (formatar e inserir o conteúdo)
			document.open();
			String readOS = "select * from servicos where os = ?";
			// conexão com o banco
			try {
				// abrir a conexão
				con = dao.conectar();
				// preparar a execução da query (instrução sql)
				pst = con.prepareStatement(readOS);
				pst.setString(1, txtOS.getText());
				// executar a query
				rs = pst.executeQuery();
				// se existir a OS
				if (rs.next()) {					
					//document.add(new Paragraph("OS: " + rs.getString(1)));
					Paragraph os = new Paragraph ("OS: " + rs.getString(1));
					os.setAlignment(Element.ALIGN_RIGHT);
					document.add(os);
						
					Paragraph usuario = new Paragraph ("Bicicleta: " + rs.getString(3));
					usuario.setAlignment(Element.ALIGN_LEFT);
					document.add(usuario);
					
					Paragraph defeito = new Paragraph ("Defeito: " + rs.getString(4));
					defeito.setAlignment(Element.ALIGN_LEFT);
					document.add(defeito);
					
					Paragraph data = new Paragraph ("Data: " + rs.getString(2));
					data.setAlignment(Element.ALIGN_LEFT);
					document.add(data);
					
					Paragraph valor = new Paragraph ("Valor: " + rs.getString(5));
					valor.setAlignment(Element.ALIGN_LEFT);
					document.add(valor);
				
					//imprimir imagens
					Image imagem = Image.getInstance(Servicos.class.getResource("/img/IconBike.png"));
					imagem.scaleToFit(192,70);
					imagem.setAbsolutePosition(20, 600);
					document.add(imagem);					
				}
				// fechar a conexão com o banco
				con.close();
				} catch (Exception e) {
					System.out.println(e);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		// fechar o documento (pronto para "impressão" (exibir o pdf))
		document.close();
		// Abrir o desktop do sistema operacional e usar o leitor padrão
		// de pdf para exibir o documento
		try {
			Desktop.getDesktop().open(new File("os.pdf"));
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	}
}
