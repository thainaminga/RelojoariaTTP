package view;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import model.DAO;

@SuppressWarnings("unused")
public class Servicos extends JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField txtOS;
	private JTextField txtData;
	private JTextField txtEquipamento;
	private JTextField txtValor;
	private JTextField txtID;
	private JTextField txtDefeito;

	DAO dao = new DAO();
	private Connection con;
	private PreparedStatement pst;
	private ResultSet rs;
	private JTextField txtCliente;
	private JLabel lblID;
	private JScrollPane scrollPaneClientes;
	@SuppressWarnings("rawtypes")
	private JList listClientes;
	private JTextField txtDiagnostico;

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
	@SuppressWarnings("rawtypes")
	public Servicos() {
		setTitle("Serviços");
		getContentPane().setBackground(new Color(128, 128, 128));
		getContentPane().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				scrollPaneClientes.setVisible(false);
			}
		});
		setIconImage(Toolkit.getDefaultToolkit().getImage(Servicos.class.getResource("/img/serviço.png")));
		setModal(true);
		setBounds(100, 100, 544, 533);
		getContentPane().setLayout(null);

		JLabel lblOS = new JLabel("OS*");
		lblOS.setForeground(new Color(192, 192, 192));
		lblOS.setBounds(28, 25, 46, 20);
		getContentPane().add(lblOS);

		txtOS = new JTextField();
		txtOS.setForeground(new Color(255, 255, 255));
		txtOS.setBorder(new EmptyBorder(0, 0, 0, 0));
		txtOS.setBackground(new Color(169, 169, 169));
		txtOS.setEditable(false);
		txtOS.setBounds(28, 45, 139, 20);
		getContentPane().add(txtOS);
		txtOS.setColumns(10);

		JLabel lblNewLabel = new JLabel("Data*");
		lblNewLabel.setForeground(new Color(192, 192, 192));
		lblNewLabel.setBounds(28, 85, 213, 22);
		getContentPane().add(lblNewLabel);

		txtData = new JTextField();
		txtData.setForeground(new Color(255, 255, 255));
		txtData.setBorder(new EmptyBorder(0, 0, 0, 0));
		txtData.setBackground(new Color(169, 169, 169));
		txtData.setEditable(false);
		txtData.setBounds(28, 104, 234, 22);
		getContentPane().add(txtData);
		txtData.setColumns(10);

		JLabel lblNewLabel_1 = new JLabel("Bijuteria*");
		lblNewLabel_1.setForeground(new Color(192, 192, 192));
		lblNewLabel_1.setBounds(254, 148, 144, 20);
		getContentPane().add(lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel("Valor");
		lblNewLabel_2.setForeground(new Color(192, 192, 192));
		lblNewLabel_2.setBounds(28, 148, 46, 20);
		getContentPane().add(lblNewLabel_2);

		txtEquipamento = new JTextField();
		txtEquipamento.setForeground(new Color(255, 255, 255));
		txtEquipamento.setBorder(new EmptyBorder(0, 0, 0, 0));
		txtEquipamento.setBackground(new Color(169, 169, 169));
		txtEquipamento.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
		txtEquipamento.setBounds(254, 167, 234, 20);
		getContentPane().add(txtEquipamento);
		txtEquipamento.setColumns(10);

		txtValor = new JTextField();
		txtValor.setForeground(new Color(255, 255, 255));
		txtValor.setBorder(new EmptyBorder(0, 0, 0, 0));
		txtValor.setBackground(new Color(169, 169, 169));
		txtValor.setBounds(28, 168, 209, 20);
		getContentPane().add(txtValor);
		txtValor.setColumns(10);

		JButton btnBuscar = new JButton("");
		btnBuscar.setContentAreaFilled(false);
		btnBuscar.setBorder(null);
		btnBuscar.setToolTipText("Buscar");
		btnBuscar.setIcon(new ImageIcon(Servicos.class.getResource("/img/pesquisar.png")));
		btnBuscar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buscar();
			}
		});

		getRootPane().setDefaultButton(btnBuscar);

		btnBuscar.setBounds(177, 26, 48, 48);
		getContentPane().add(btnBuscar);

		JButton bntAdicionar = new JButton("");
		bntAdicionar.setContentAreaFilled(false);
		bntAdicionar.setBorder(null);
		bntAdicionar.setIcon(new ImageIcon(Servicos.class.getResource("/img/add.png")));
		bntAdicionar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		bntAdicionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				adicionar();
			}
		});
		bntAdicionar.setBounds(28, 423, 48, 48);
		getContentPane().add(bntAdicionar);

		JButton bntEditar = new JButton("");
		bntEditar.setContentAreaFilled(false);
		bntEditar.setBorder(null);
		bntEditar.setIcon(new ImageIcon(Servicos.class.getResource("/img/edit (2).png")));
		bntEditar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		bntEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editarOS();
			}
		});
		bntEditar.setBounds(138, 423, 48, 48);
		getContentPane().add(bntEditar);

		JButton btnExcluir = new JButton("");
		btnExcluir.setContentAreaFilled(false);
		btnExcluir.setBorder(null);
		btnExcluir.setIcon(new ImageIcon(Servicos.class.getResource("/img/delete (2).png")));
		btnExcluir.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				excluirOS();
			}
		});
		btnExcluir.setBounds(342, 423, 48, 48);
		getContentPane().add(btnExcluir);

		JLabel lblNewLabel_3 = new JLabel("Defeito*");
		lblNewLabel_3.setForeground(new Color(192, 192, 192));
		lblNewLabel_3.setBounds(28, 289, 93, 32);
		getContentPane().add(lblNewLabel_3);

		txtDefeito = new JTextField();
		txtDefeito.setForeground(new Color(255, 255, 255));
		txtDefeito.setBorder(new EmptyBorder(0, 0, 0, 0));
		txtDefeito.setBackground(new Color(169, 169, 169));
		txtDefeito.setBounds(28, 319, 460, 75);
		getContentPane().add(txtDefeito);
		txtDefeito.setColumns(10);

		JButton btnLimpar = new JButton("");
		btnLimpar.setContentAreaFilled(false);
		btnLimpar.setBorder(null);
		btnLimpar.setIcon(new ImageIcon(Servicos.class.getResource("/img/borracha.png")));
		btnLimpar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LimparCampos();
			}
		});
		btnLimpar.setBounds(440, 423, 48, 48);
		getContentPane().add(btnLimpar);

		JPanel panel = new JPanel();
		panel.setForeground(new Color(192, 192, 192));
		panel.setBorder(new TitledBorder(
				new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Cliente",
				TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel.setBounds(272, 28, 206, 99);
		getContentPane().add(panel);
		panel.setLayout(null);

		txtCliente = new JTextField();
		txtCliente.setForeground(new Color(255, 255, 255));
		txtCliente.setBorder(new EmptyBorder(0, 0, 0, 0));
		txtCliente.setBackground(new Color(169, 169, 169));
		txtCliente.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				listarClientes();
			}
		});

		scrollPaneClientes = new JScrollPane();
		scrollPaneClientes.setVisible(false);
		scrollPaneClientes.setBounds(10, 41, 186, 39);
		panel.add(scrollPaneClientes);

		listClientes = new JList();
		listClientes.setForeground(new Color(255, 255, 255));
		listClientes.setBackground(new Color(192, 192, 192));
		listClientes.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				buscarClientesLista();
			}
		});
		scrollPaneClientes.setViewportView(listClientes);
		txtCliente.setBounds(10, 23, 186, 20);
		panel.add(txtCliente);
		txtCliente.setColumns(10);

		txtID = new JTextField();
		txtID.setForeground(new Color(0, 0, 0));
		txtID.setEditable(false);
		txtID.setBounds(10, 60, 86, 20);
		panel.add(txtID);
		txtID.setColumns(10);

		lblID = new JLabel("ID*");
		lblID.setBounds(10, 42, 100, 20);
		panel.add(lblID);

		JButton btnNewButton = new JButton("Pesquisa");
		btnNewButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnNewButton.setBounds(107, 59, 89, 23);
		panel.add(btnNewButton);

		JButton btnOS = new JButton("");
		btnOS.setContentAreaFilled(false);
		btnOS.setBorder(null);
		btnOS.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnOS.setIcon(new ImageIcon(Servicos.class.getResource("/img/imprimir.png")));
		btnOS.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				imprimirOS();
			}
		});
		btnOS.setBounds(245, 423, 48, 48);
		getContentPane().add(btnOS);

		JLabel lblNewLabel_3_1 = new JLabel("Diagnóstico Técnico*");
		lblNewLabel_3_1.setForeground(Color.LIGHT_GRAY);
		lblNewLabel_3_1.setBounds(28, 201, 173, 27);
		getContentPane().add(lblNewLabel_3_1);

		txtDiagnostico = new JTextField();
		txtDiagnostico.setForeground(Color.WHITE);
		txtDiagnostico.setColumns(10);
		txtDiagnostico.setBorder(new EmptyBorder(0, 0, 0, 0));
		txtDiagnostico.setBackground(new Color(169, 169, 169));
		txtDiagnostico.setBounds(28, 231, 460, 47);
		getContentPane().add(txtDiagnostico);

	}

	private void LimparCampos() {
		txtCliente.setText(null);
		txtOS.setText(null);
		txtData.setText(null);
		txtEquipamento.setText(null);
		txtDiagnostico.setText(null);
		txtDefeito.setText(null);
		txtValor.setText(null);
		txtID.setText(null);

	}

	private void buscar() {
		String numOS = JOptionPane.showInputDialog("Número da OS ");

		String read = "select * from servicos inner join clientes on servicos.idcli= clientes.idcli where os = ?";
		try {

			con = dao.conectar();

			pst = con.prepareStatement(read);

			pst.setString(1, numOS);

			rs = pst.executeQuery();

			if (rs.next()) {

				txtOS.setText(rs.getString(1));
				txtData.setText(rs.getString(2));
				txtEquipamento.setText(rs.getString(3));
				txtDefeito.setText(rs.getString(4));
				txtDiagnostico.setText(rs.getString(5));
				txtValor.setText(rs.getString(6));
				txtID.setText(rs.getString(7));
				txtCliente.setText(rs.getString(8));

			} else {

				JOptionPane.showMessageDialog(null, "Serviço inexistente! ");

			}
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	/**
	 * Método responsavel por: Adicionar a OS
	 */
	private void adicionar() {
		if (txtEquipamento.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha a Bijuteria para o serviço! ");
			txtEquipamento.requestFocus();

		} else if (txtID.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o ID do cliente!");
			txtID.requestFocus();

		} else {

			String create = "insert into servicos (equipamento,defeito,diagnostico,valor,idcli)values (?,?,?,?,?)";

			try {

				con = dao.conectar();

				pst = con.prepareStatement(create);

				pst.setString(1, txtEquipamento.getText());
				pst.setString(2, txtDefeito.getText());
				pst.setString(3, txtDiagnostico.getText());
				pst.setString(4, txtValor.getText());
				pst.setString(5, txtID.getText());

				pst.executeUpdate();

				JOptionPane.showMessageDialog(null, " Serviço adicionado! ");

				LimparCampos();

				con.close();

			}

			catch (java.sql.SQLIntegrityConstraintViolationException e1) {
				JOptionPane.showMessageDialog(null, "Serviço não adicionado.\nEsta OS já está sendo utilizado.");
				txtOS.setText(null);
				txtOS.requestFocus();
			} catch (Exception e2) {
				System.out.println(e2);
			}
		}

	}
	/**
	 * Método responsavel por:Edutar a OS
	 */
	private void editarOS() {
		if (txtEquipamento.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Digite a bijuteria do cliente ");
			txtEquipamento.requestFocus();

		} else if (txtDefeito.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Digite a defeito do cliente ");
			txtDefeito.requestFocus();

		} else {
			// logica principal
			// CRUD - Update
			String update = "update servicos set os =?, equipamento=?, defeito=?, diagnostico=? where idcli=?";
			try {
				con = dao.conectar();
				pst = con.prepareStatement(update);
				pst.setString(1, txtOS.getText());
				pst.setString(2, txtEquipamento.getText());
				pst.setString(3, txtDefeito.getText());
				pst.setString(4, txtDiagnostico.getText());
				pst.setString(5, txtID.getText());
				pst.executeUpdate();
				JOptionPane.showMessageDialog(null, "Dados do serviço editado com sucesso! ");
				LimparCampos();
				con.close();

			} catch (Exception e) {
				System.out.println(e);
			}

		}

	}
	/**
	 * Método responsavel por: Apagar da OS
	 */
	private void excluirOS() {

		if (txtEquipamento.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o campo equipamento");
			txtEquipamento.requestFocus();
		} else if (txtDefeito.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o campo defeito");
			txtDefeito.requestFocus();

		} else if (txtValor.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o campo valor");
			txtValor.requestFocus();

		} else {

			int confirma = JOptionPane.showConfirmDialog(null, " Confirmar a exclusão desse serviço? ",
					" !!!Atenção!!! ", JOptionPane.YES_NO_OPTION);
			if (confirma == JOptionPane.YES_OPTION) {

				String delete = "delete from servicos where idcli=?";

				try {
					con = dao.conectar();

					pst = con.prepareStatement(delete);

					pst.setString(1, txtID.getText());

					pst.executeUpdate();
					JOptionPane.showMessageDialog(null, " Serviço excluído! ");
					LimparCampos();

					con.close();

				}

				catch (Exception e) {
					System.out.print(e);
				}
			}
		}
	}
	/**
	 * Método responsavel por: Impressão da OS
	 */
	@SuppressWarnings("unchecked")
	private void listarClientes() {

		DefaultListModel<String> modelo = new DefaultListModel<>();

		listClientes.setModel(modelo);

		String readLista = "select *from clientes where nome like '" + txtCliente.getText() + "%'" + "order by nome";
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
	/**
	 * Método responsavel por: Buscar os clientes
	 */
	private void buscarClientesLista() {

		int linha = listClientes.getSelectedIndex();
		if (linha >= 0) {
			String readBuscaLista = "select *from clientes where nome like '" + txtCliente.getText() + "%'"
					+ "order by nome limit " + (linha) + " ,1";
			try {
				con = dao.conectar();
				pst = con.prepareStatement(readBuscaLista);
				rs = pst.executeQuery();
				if (rs.next()) {
					scrollPaneClientes.setVisible(false);

					txtID.setText(rs.getString(1));//
					txtCliente.setText(rs.getString(2));//

				} else {
					JOptionPane.showMessageDialog(null, "Cliente inexistente! ");

				}
				con.close();
			} catch (Exception e) {

			}

		} else {
			scrollPaneClientes.setVisible(false);

		}
	}

	/**
	 * Método responsavel por: Impressão da OS
	 */
	private void imprimirOS() {

		Document document = new Document();

		if (txtEquipamento.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o campo do equipamento");
			txtEquipamento.requestFocus();

		} else if (txtDefeito.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o campo defeito");
			txtDefeito.requestFocus();

		} else if (txtValor.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o campo valor");
			txtValor.requestFocus();

		} else {

			try {
				PdfWriter.getInstance(document, new FileOutputStream("os.pdf"));
				document.open();

				String readOS = "select * from servicos inner join clientes on servicos.idcli = clientes.idcli where os=?";

				try {

					con = dao.conectar();
					pst = con.prepareStatement(readOS);
					pst.setString(1, txtOS.getText());
					rs = pst.executeQuery();

					if (rs.next()) {

						Image imagem = Image.getInstance(Servicos.class.getResource("/img/TTP.png"));
						imagem.scaleToFit(128, 128);
						imagem.setAlignment(Element.ALIGN_LEFT);
						document.add(imagem);

						Paragraph OS = new Paragraph("Relojuaria");
						OS.setAlignment(Element.ALIGN_CENTER);
						document.add(OS);

						Paragraph via = new Paragraph(" (Via cliente) ");
						via.setAlignment(Element.ALIGN_CENTER);
						document.add(via);

						Paragraph cp = new Paragraph("COMPROVANTE DE RETIRADA",
								FontFactory.getFont(FontFactory.HELVETICA, 12, Font.BOLD));
						cp.setAlignment(Element.ALIGN_RIGHT);
						document.add(cp);

						Paragraph space = new Paragraph(" ");
						space.setAlignment(Element.ALIGN_MIDDLE);
						document.add(space);

						Paragraph termos = new Paragraph("Termos de Serviço:\n\n"
								+ "1. A Relojoaria TTP fornecerá reparo apenas para os objetos mencionados nesta ordem de serviço.\n"
								+ "2. O cliente é responsável por fornecer informações precisas sobre o defeito do aparelho.\n"
								+ "3. A garantia para o serviço prestado será válida por 30 dias após a data de emissão desta ordem de serviço.\n"
								+ "4. Se durante os 30 DIAS ouver quaisquer danos ao objeto será levado para a análise e será avaliado os danos e caso necessário troca do objeto/produto.\n"
								+

								"5. Quaisquer danos adicionais causados ao aparelho durante o transporte são de responsabilidade do cliente.\n"
								+ "6. O valor total do serviço é devido no momento da retirada do objeto reparado.\n"
								+ "7. O não pagamento do valor devido pode resultar na retenção do objeto até que o pagamento seja efetuado.\n"
								+ "8. A Relojoaria TTP não se responsabiliza por perda de acessórios durante o reparo.\n"
								+ "9. O Cliente está ciente dos termos e concorda com todas as alegações.\n"
								+ "______________________________________________________________________________");

						termos.setAlignment(Element.ALIGN_LEFT);
						document.add(termos);

						Paragraph desc = new Paragraph(
								" Pessoa Jurídica \n CNPJ.: 90090090090099 Senac Tatuapé - Cel. Luís Americano \n Endereço: Tatuape, São Paulo - SP, Loja 1 - Loja 1\n CEP.:03302000 \n Laboratório Técnico:  R. Cel. Luís Americano, 130 \n (11)  2191-2900 \n Email: relojuariaTTP@gmail.com");
						desc.setAlignment(Element.ALIGN_LEFT);
						document.add(desc);

						Paragraph space2 = new Paragraph(" ");
						space2.setAlignment(Element.ALIGN_MIDDLE);
						document.add(space2);

						Paragraph desc2 = new Paragraph("SERVIÇO ESPECIALIZADO",
								FontFactory.getFont(FontFactory.HELVETICA, 16, Font.BOLD));
						desc2.setAlignment(Element.ALIGN_LEFT);
						document.add(desc2);

						Paragraph TVS = new Paragraph(" Relóigios - Colares - Bricos - Pulseiras");
						TVS.setAlignment(Element.ALIGN_RIGHT);
						document.add(TVS);

						Paragraph os = new Paragraph("OS: " + rs.getString(1) + "\n");
						os.setAlignment(Element.ALIGN_RIGHT);

						document.add(os);

						Paragraph nome = new Paragraph("Cliente: " + rs.getString(11));
						nome.setAlignment(Element.ALIGN_LEFT);
						document.add(nome);

						Paragraph endereco = new Paragraph("Endereço: " + rs.getString(16));
						endereco.setAlignment(Element.ALIGN_LEFT);
						document.add(endereco);

						Paragraph bairro = new Paragraph("Bairro: " + rs.getString(19));
						bairro.setAlignment(Element.ALIGN_LEFT);
						document.add(bairro);

						Paragraph under = new Paragraph(
								"\n______________________________________________________________________________\n");
						under.setAlignment(Element.ALIGN_LEFT);
						document.add(under);

						Paragraph ap = new Paragraph(
								"Eu estou ciente que o objeto abaixo está sendo retirado pela Relojuaria TTP para reparo. \n");
						ap.setAlignment(Element.ALIGN_LEFT);
						document.add(ap);

						Paragraph aparelho = new Paragraph("\n Dados do Objeto: \n ",
								FontFactory.getFont(FontFactory.HELVETICA, 12, Font.BOLD));
						aparelho.setAlignment(Element.ALIGN_MIDDLE);
						document.add(aparelho);

						PdfPTable tabela = new PdfPTable(4);
						PdfPCell col1 = new PdfPCell(new Paragraph("Equipamento: "));

						PdfPCell col2 = new PdfPCell(new Paragraph("Marca: "));

						PdfPCell col3 = new PdfPCell(new Paragraph("Modelo: "));

						PdfPCell col4 = new PdfPCell(new Paragraph("Valor: "));

						tabela.addCell(col1);
						tabela.addCell(col2);
						tabela.addCell(col3);
						tabela.addCell(col4);

						tabela.addCell(rs.getString(3));
						tabela.addCell(rs.getString(4));
						tabela.addCell(rs.getString(5));
						tabela.addCell(rs.getString(6));

						document.add(tabela);

						Paragraph def = new Paragraph("Defeito: \n(estado do objeto/acessório) \n" + rs.getString(4)
								+ "\n______________________________________________________________________________");
						def.setAlignment(Element.ALIGN_LEFT);
						document.add(def);

						Paragraph assin = new Paragraph(
								"\n \n \n __________________ \n \n Relojuaria TTP São Paulo ____/____/____ \n ");
						assin.setAlignment(Element.ALIGN_LEFT);
						document.add(assin);

						Paragraph data = new Paragraph("Data da emissão da OS: " + rs.getString(2));
						data.setAlignment(Element.ALIGN_CENTER);
						document.add(data);
					}
					con.close();
				} catch (Exception e) {
					System.out.println(e);
				}
			} catch (Exception e) {
				System.out.println(e);
			} finally {
				document.close();

				try {

					Desktop.getDesktop().open(new File("os.pdf"));
				} catch (Exception e) {
					System.out.println(e);
				}
			}

		}

	}

}