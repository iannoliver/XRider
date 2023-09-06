package view;

import java.awt.Desktop;
import java.awt.EventQueue;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.util.Date;

import javax.swing.JDialog;

import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import model.DAO;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;
import javax.swing.border.EmptyBorder;
import java.awt.Cursor;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Toolkit;
import java.awt.Font;

public class Relatorios extends JDialog {
	DAO dao = new DAO();
	private Connection con;
	private PreparedStatement pst;
	private ResultSet rs;

	private static final long serialVersionUID = 1L;
	private JButton btnServicos_1;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Relatorios dialog = new Relatorios();
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Relatorios() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(Relatorios.class.getResource("/img/IconBike.png")));
		setTitle("Relatórios");
		setResizable(false);
		setModal(true);
		setBounds(100, 100, 800, 600);
		getContentPane().setLayout(null);

		JButton btnClientes = new JButton("");
		btnClientes.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnClientes.setContentAreaFilled(false);
		btnClientes.setBorder(new EmptyBorder(0, 0, 0, 0));
		btnClientes.setToolTipText("Clientes");
		btnClientes.setIcon(new ImageIcon(Relatorios.class.getResource("/img/Clientecli.png")));
		btnClientes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				relatorioClientes();
			}
		});

		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setIcon(new ImageIcon(Relatorios.class.getResource("/img/iconbike2.png")));
		lblNewLabel_1.setBounds(378, 513, 48, 48);
		getContentPane().add(lblNewLabel_1);
		btnClientes.setBounds(115, 77, 128, 128);
		getContentPane().add(btnClientes);

		JButton btnServicos = new JButton("");
		btnServicos.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnServicos.setContentAreaFilled(false);
		btnServicos.setBorder(new EmptyBorder(0, 0, 0, 0));
		btnServicos.setIcon(new ImageIcon(Relatorios.class.getResource("/img/RelOs.png")));
		btnServicos.setToolTipText("Serviços");
		btnServicos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				relatorioServicos();
			}
		});
		btnServicos.setBounds(544, 77, 128, 128);
		getContentPane().add(btnServicos);

		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setOpaque(true);
		lblNewLabel.setBackground(new Color(73, 156, 148));
		lblNewLabel.setBounds(0, 491, 784, 70);
		getContentPane().add(lblNewLabel);

		JLabel lblNewLabel_2 = new JLabel("Clientes");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblNewLabel_2.setBounds(149, 234, 91, 30);
		getContentPane().add(lblNewLabel_2);

		JLabel lblNewLabel_2_1 = new JLabel("Serviços");
		lblNewLabel_2_1.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblNewLabel_2_1.setBounds(576, 234, 91, 30);
		getContentPane().add(lblNewLabel_2_1);

		JLabel lblNewLabel_2_1_1 = new JLabel("Patrimônio");
		lblNewLabel_2_1_1.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblNewLabel_2_1_1.setBounds(344, 370, 118, 48);
		getContentPane().add(lblNewLabel_2_1_1);
		
		btnServicos_1 = new JButton("");
		btnServicos_1.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnServicos_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				relatorioPatrimonio();
			}
		});
		btnServicos_1.setIcon(new ImageIcon(Relatorios.class.getResource("/img/Estoque.png")));
		btnServicos_1.setToolTipText("Serviços");
		btnServicos_1.setContentAreaFilled(false);
		btnServicos_1.setBorder(new EmptyBorder(0, 0, 0, 0));
		btnServicos_1.setBounds(329, 234, 128, 128);
		getContentPane().add(btnServicos_1);

	}

	private void relatorioClientes() {
		Document document = new Document();
		try {
			PdfWriter.getInstance(document, new FileOutputStream("clientes.pdf"));
			document.open();
			Date dataRelatorio = new Date();
			DateFormat formatador = DateFormat.getDateInstance(DateFormat.FULL);
			document.add(new Paragraph(formatador.format(dataRelatorio)));
			document.add(new Paragraph("Clientes:"));
			document.add(new Paragraph(" "));
			String readClientes = "select cliente,contato from clientes order by cliente";
			try {
				con = dao.conectar();
				pst = con.prepareStatement(readClientes);
				rs = pst.executeQuery();
				PdfPTable tabela = new PdfPTable(2);
				PdfPCell col1 = new PdfPCell(new Paragraph("Cliente"));
				PdfPCell col2 = new PdfPCell(new Paragraph("Contato"));
				tabela.addCell(col1);
				tabela.addCell(col2);
				while (rs.next()) {
					// popular a tabela
					tabela.addCell(rs.getString(1));
					tabela.addCell(rs.getString(2));
				}
				document.add(tabela);
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		document.close();
		try {
			Desktop.getDesktop().open(new File("clientes.pdf"));
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	private void relatorioServicos() {
		Document document = new Document();
		document.setPageSize(PageSize.A4.rotate());
		try {
			PdfWriter.getInstance(document, new FileOutputStream("serviços.pdf"));
			document.open();
			Date dataRelatorio = new Date();
			DateFormat formatador = DateFormat.getDateInstance(DateFormat.FULL);
			document.add(new Paragraph(formatador.format(dataRelatorio)));
			document.add(new Paragraph("Serviços:"));
			document.add(new Paragraph(" "));
			String readServicos = "select os, dataOS, marca, modelo, cor, cliente from servicos inner join clientes on servicos.idcli = clientes.idcli;";
			try {
				con = dao.conectar();
				pst = con.prepareStatement(readServicos);
				rs = pst.executeQuery();
				PdfPTable tabela = new PdfPTable(6);
				PdfPCell col1 = new PdfPCell(new Paragraph("OS"));
				PdfPCell col2 = new PdfPCell(new Paragraph("Data"));
				PdfPCell col3 = new PdfPCell(new Paragraph("Marca"));
				PdfPCell col4 = new PdfPCell(new Paragraph("Modelo"));
				PdfPCell col5 = new PdfPCell(new Paragraph("Cor"));
				PdfPCell col6 = new PdfPCell(new Paragraph("Cliente"));
				tabela.addCell(col1);
				tabela.addCell(col2);
				tabela.addCell(col3);
				tabela.addCell(col4);
				tabela.addCell(col5);
				tabela.addCell(col6);
				while (rs.next()) {

					tabela.addCell(rs.getString(1));
					tabela.addCell(rs.getString(2));
					tabela.addCell(rs.getString(3));
					tabela.addCell(rs.getString(4));
					tabela.addCell(rs.getString(5));
					tabela.addCell(rs.getString(6));
				}

				document.add(tabela);
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		document.close();
		try {
			Desktop.getDesktop().open(new File("serviços.pdf"));
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	private void relatorioPatrimonio() {

		Document document = new Document();

		try {
			PdfWriter.getInstance(document, new FileOutputStream("estoque.pdf"));
			document.open();
			Date dataRelatorio = new Date();
			DateFormat formatador = DateFormat.getDateInstance(DateFormat.FULL);
			document.add(new Paragraph(formatador.format(dataRelatorio)));
			document.add(new Paragraph("Estoque:"));
			document.add(new Paragraph(" "));
			String readClientes = "select idproduto as idproduto, produto, date_format(datavalidade, '%d/%m/%Y') as validade, estoque, estoquemin as estóque_mínimo from produtos where estoque < estoquemin";
			try {
				con = dao.conectar();
				pst = con.prepareStatement(readClientes);
				rs = pst.executeQuery();
				PdfPTable tabela = new PdfPTable(5);
				PdfPCell col1 = new PdfPCell(new Paragraph("código: "));
				PdfPCell col2 = new PdfPCell(new Paragraph("produto: "));
				PdfPCell col3 = new PdfPCell(new Paragraph("validade: "));
				PdfPCell col4 = new PdfPCell(new Paragraph("estoque: "));
				PdfPCell col5 = new PdfPCell(new Paragraph("estoque mínimo: "));
				tabela.addCell(col1);
				tabela.addCell(col2);
				tabela.addCell(col3);
				tabela.addCell(col4);
				tabela.addCell(col5);
				while (rs.next()) {
					tabela.addCell(rs.getString(1));
					tabela.addCell(rs.getString(2));
					tabela.addCell(rs.getString(3));
					tabela.addCell(rs.getString(4));
					tabela.addCell(rs.getString(5));
				}
				document.add(tabela);
				document.add(new Paragraph("Validade:"));
				document.add(new Paragraph(" "));
				String read = "select idproduto as idproduto, produto, date_format(datavalidade, '%d/%m/%Y') as validade from produtos where datavalidade < dataentrada;";
				pst = con.prepareStatement(read);
				rs = pst.executeQuery();
				PdfPTable tabela2 = new PdfPTable(3);
				PdfPCell col6 = new PdfPCell(new Paragraph("código: "));
				PdfPCell col7 = new PdfPCell(new Paragraph("produto: "));
				PdfPCell col8 = new PdfPCell(new Paragraph("validade: "));
				tabela2.addCell(col6);
				tabela2.addCell(col7);
				tabela2.addCell(col8);
				while (rs.next()) {
					tabela2.addCell(rs.getString(1));
					tabela2.addCell(rs.getString(2));
					tabela2.addCell(rs.getString(3));
				}
				document.add(tabela2);
				document.add(new Paragraph(" "));
				document.add(new Paragraph("Patrimônio (Custo):"));
				document.add(new Paragraph(" "));
				String read2 = "select sum(valor * estoque) as Total from produtos";
				pst = con.prepareStatement(read2);
				rs = pst.executeQuery();
				PdfPTable tabela3 = new PdfPTable(1);
				PdfPCell col12 = new PdfPCell(new Paragraph("Patrimônio custo: "));
				tabela3.addCell(col12);
				while (rs.next()) {
					tabela3.addCell(rs.getString(1));
				}
				document.add(tabela3);
				document.add(new Paragraph(" "));
				document.add(new Paragraph("Patrimônio (venda):"));
				document.add(new Paragraph(" "));
				String readVenda = "select sum((valor + (valor * lucro)/100) * estoque) as total from produtos";
				pst = con.prepareStatement(readVenda);
				rs = pst.executeQuery();
				PdfPTable tabela4 = new PdfPTable(1);
				PdfPCell col43 = new PdfPCell(new Paragraph("Patrimônio venda: "));
				tabela4.addCell(col43);
				while (rs.next()) {
					tabela4.addCell(rs.getString(1));
				}
				document.add(tabela4);
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		document.close();
		try {
			Desktop.getDesktop().open(new File("estoque.pdf"));
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
