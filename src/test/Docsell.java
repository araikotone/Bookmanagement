package test;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class Docsell extends JFrame {

	private JPanel contentPane;
	private JTextField snameField;
	private JTextField empField;
	private JTextField rpriceField;
	private final JScrollPane scrollPane1 = new JScrollPane();
	private JTable detailtable;
	private String[] columnNames = { "ID", "タイトル", "著者名", "発行年月日", "ジャンル", "定価" };
	private JFrame frame1;
	private String chkPrice = "";





	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {


//
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					Docsell frame = new Docsell();
//
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}


	/**
	 * Create the frame.
	 * @param chkPrice
	 */
	public Docsell(String chkPrice) {
		this.chkPrice = chkPrice;

		setBackground(Color.WHITE);
		setFont(new Font("UD デジタル 教科書体 NP-R", Font.PLAIN, 14));
		setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\孝司\\Pictures\\tempsnip.png"));
		setTitle("書籍販売申請書");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		setBounds(0, 0, 700,987);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		//図書販売申請ラベル
		JLabel docsellLabel = new JLabel("図書販売申請書");
		docsellLabel.setFont(new Font("UD デジタル 教科書体 NP-R", Font.PLAIN, 25));
		docsellLabel.setBounds(197, 26, 183, 30);
		contentPane.add(docsellLabel);

		//氏名ラベル
		JLabel snameLabel = new JLabel("氏名");
		snameLabel.setFont(new Font("UD デジタル 教科書体 NP-R", Font.PLAIN, 23));
		snameLabel.setBounds(76, 120, 81, 30);
		contentPane.add(snameLabel);

		//社員番号ラベル
		JLabel empLabel = new JLabel("社員番号");
		empLabel.setFont(new Font("UD デジタル 教科書体 NP-R", Font.PLAIN, 22));
		empLabel.setBounds(76, 177, 102, 23);
		contentPane.add(empLabel);

		//売却価格ラベル
		JLabel resultpriceLabel = new JLabel("売却価格");
		resultpriceLabel.setFont(new Font("UD デジタル 教科書体 NP-R", Font.PLAIN, 22));
		resultpriceLabel.setBounds(76, 231, 102, 30);
		contentPane.add(resultpriceLabel);

		//氏名フィールド
		snameField = new JTextField();
		snameField.setFont(new Font("UD デジタル 教科書体 NP-R", Font.PLAIN, 18));
		snameField.setBounds(197, 120, 274, 30);
		contentPane.add(snameField);
		snameField.setColumns(10);

		//社員番号フィールド
		empField = new JTextField();
		empField.setFont(new Font("UD デジタル 教科書体 NP-R", Font.PLAIN, 18));
		empField.setBounds(197, 170, 274, 30);
		contentPane.add(empField);
		empField.setColumns(10);

		//売却価格フィールド
		rpriceField = new JTextField();
		rpriceField.setFont(new Font("UD デジタル 教科書体 NP-R", Font.PLAIN, 17));
		rpriceField.setBounds(197, 231, 123, 30);
		contentPane.add(rpriceField);
		rpriceField.setColumns(10);
		rpriceField.setText(this.chkPrice);

		//円ラベル
		JLabel yenLabel = new JLabel("円");
		yenLabel.setFont(new Font("UD デジタル 教科書体 NP-R", Font.PLAIN, 22));
		yenLabel.setBounds(332, 233, 50, 26);
		contentPane.add(yenLabel);

		//okボタン
		JButton okButton = new JButton("OK");
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				Connection con = null;
				PreparedStatement pstmt = null;
				ResultSet rs = null;

				try {
					//データベースに接続
					con = Connect.getConnection();

					//SQL文作成
					String mySql = "SELECT * FROM bookmng.tbl_book where id=(select max(id) from tbl_book)";
					//ステートメントオブジェクトを作成
					pstmt = con.prepareStatement(mySql);
					//検索するSQL実行
					rs = pstmt.executeQuery();
					// 表のヘッダー部作成
					DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
					// java.sql.ResultSet の行数を取得するためカーソルを最終行に移動
					rs.last();
					// 結果の行数をセット
					tableModel.setRowCount(rs.getRow());
					// カーソルを先頭行に移動
					rs.beforeFirst();
					System.out.println("表件数＝" + tableModel.getRowCount() + "\t");

					int i = 0; // カウントアップ変数
					//結果セットからデータを取り出す next()で次の行に移動
					while (rs.next()) {
						// 検索結果から表に書き込み
						tableModel.setValueAt(java.lang.String.format("%03d", rs.getInt("ID")), i, 0);
						tableModel.setValueAt(rs.getString("TITLE"), i, 1);
						tableModel.setValueAt(rs.getString("AUTHOR"), i, 2);
						tableModel.setValueAt(rs.getString("DATE"), i, 3);
						tableModel.setValueAt(rs.getString("GENRE"), i, 4);
						tableModel.setValueAt(rs.getInt("PRICE"), i, 5);
						System.out.println("行数＝" + i + "\t");
						i++;
					}

					// 表を描画
					//					frame1.getContentPane().add(scrollPane1);
					detailtable.setModel(tableModel);
					detailtable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
					detailtable.setColumnSelectionAllowed(true);
					detailtable.setRowSelectionAllowed(true);
					detailtable.getColumnModel().getColumn(0).setPreferredWidth(48);
					detailtable.getColumnModel().getColumn(1).setPreferredWidth(142);
					detailtable.getColumnModel().getColumn(2).setPreferredWidth(147);
					detailtable.getColumnModel().getColumn(3).setPreferredWidth(83);
					detailtable.getColumnModel().getColumn(4).setPreferredWidth(70);
					detailtable.getColumnModel().getColumn(5).setPreferredWidth(68);
					scrollPane1.setViewportView(detailtable);

					scrollPane1.repaint();

				} catch (Exception ex) {
					System.out.println("例外発生：" + ex.toString());
					JOptionPane.showMessageDialog(frame1, "例外発生：" + ex.toString());
					ex.printStackTrace();
				} finally {
					try {
						// 実行結果をクローズ
						if (rs != null) {
							rs.close();
						}
						// ステートメントをクローズ
						if (pstmt != null) {
							pstmt.close();
						}
						// 接続をクローズ
						if (con != null) {
							con.close();
						}
					} catch (SQLException se) {
						// 何もしない
					}
				}
			}

		});
		okButton.setBackground(Color.WHITE);
		okButton.setFont(new Font("UD デジタル 教科書体 NP-R", Font.PLAIN, 15));
		okButton.setBounds(505, 287, 60, 21);
		contentPane.add(okButton);
		scrollPane1.setBounds(10, 318, 560, 43);
		contentPane.add(scrollPane1);

		detailtable = new JTable();
		detailtable.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, null, null, null, null},
			},
			new String[] {
				"ID", "\u30BF\u30A4\u30C8\u30EB", "\u8457\u8005\u540D", "\u767A\u884C\u5E74\u6708\u65E5", "\u30B8\u30E3\u30F3\u30EB", "\u5B9A\u4FA1"
			}
		));
		detailtable.getColumnModel().getColumn(0).setPreferredWidth(48);
		detailtable.getColumnModel().getColumn(1).setPreferredWidth(142);
		detailtable.getColumnModel().getColumn(2).setPreferredWidth(147);
		detailtable.getColumnModel().getColumn(3).setPreferredWidth(83);
		detailtable.getColumnModel().getColumn(4).setPreferredWidth(70);
		detailtable.getColumnModel().getColumn(5).setPreferredWidth(48);
		detailtable.setFont(new Font("UD デジタル 教科書体 NP-R", Font.PLAIN, 10));
		scrollPane1.setViewportView(detailtable);

		JLabel notice1Label = new JLabel("書籍の販売売上金は来月の給与に合わせて支給されます");
		notice1Label.setFont(new Font("UD デジタル 教科書体 NP-R", Font.PLAIN, 15));
		notice1Label.setBounds(179, 371, 375, 23);
		contentPane.add(notice1Label);

		JLabel sLabel = new JLabel("申請者印");
		sLabel.setFont(new Font("UD デジタル 教科書体 NP-R", Font.PLAIN, 17));
		sLabel.setBounds(391, 469, 71, 23);
		contentPane.add(sLabel);

		JLabel aLabel = new JLabel("承認印");
		aLabel.setFont(new Font("UD デジタル 教科書体 NP-R", Font.PLAIN, 17));
		aLabel.setBounds(483, 469, 60, 23);
		contentPane.add(aLabel);

		JLabel stampLabel = new JLabel("印");
		stampLabel.setForeground(new Color(211, 211, 211));
		stampLabel.setFont(new Font("UD デジタル 教科書体 NP-R", Font.PLAIN, 24));
		stampLabel.setBounds(412, 502, 24, 30);
		contentPane.add(stampLabel);

		JLabel stampLabel1 = new JLabel("印");
		stampLabel1.setForeground(new Color(211, 211, 211));
		stampLabel1.setFont(new Font("UD デジタル 教科書体 NP-R", Font.PLAIN, 24));
		stampLabel1.setBounds(497, 502, 32, 30);
		contentPane.add(stampLabel1);

		JButton printbutton = new JButton("印刷");
		printbutton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
//				PrinterJob job = PrinterJob.getPrinterJob();
//				Printable painter = new Printable() {
//
//					@Override
//					public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
//						if (pageIndex > 0) {
//							return NO_SUCH_PAGE;
//						}
//
//						return PAGE_EXISTS;
//					}
//				};
//				job.setPrintable(painter);
//
//				boolean print = job.printDialog();
//				if (print) {
//					try {
//						job.print();
//					} catch (PrinterException e1) {
//						// TODO 自動生成された catch ブロック
//						e1.printStackTrace();
//					}
//				}

				 printComponenet();{

				    PrinterJob pj = PrinterJob.getPrinterJob();
				    pj.setJobName(" Print Component ");

				    pj.setPrintable (new Printable() {
				    public int print(Graphics pg, PageFormat pf, int pageNum){
				     if (pageNum > 0){
				     return Printable.NO_SUCH_PAGE;
				     }

				     Graphics2D g2 = (Graphics2D) pg;
				     g2.translate(pf.getImageableX(), pf.getImageableY());
				     contentPane.paint(g2);
				     return Printable.PAGE_EXISTS;
				    }
				    });
				    if (pj.printDialog() == false)
				    return;

				    try {
				     pj.print();
				    } catch (PrinterException ex) {
				     // handle exception
				    }
				 }

			}
		});

		printbutton.setForeground(new Color(211, 211, 211));
		printbutton.setBackground(new Color(255, 250, 250));
		printbutton.setFont(new Font("UD デジタル 教科書体 NP-R", Font.PLAIN, 15));
		printbutton.setBounds(483, 886, 81, 21);
		contentPane.add(printbutton);

	}


	protected void printComponenet() {
		// TODO 自動生成されたメソッド・スタブ

	}

}
