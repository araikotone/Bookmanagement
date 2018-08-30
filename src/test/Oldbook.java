package test;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerListModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;
public class Oldbook extends JFrame {

	protected static final String RP = null;
	private JPanel contentPane;
	private JTextField title1TextField;
	private JTextField author1TextField;
	private JLabel genre1Label;
	private JTextField date1Field;
	private JFrame frame;
	private final int MAX_LENGTH =50;
	private final int MAX_LENGTH10 =10;


	protected AbstractButton resultTextfield;

	//	Resultprice rp = new Resultprice();
	static Oldbook ob;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Oldbook frame = new Oldbook();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Oldbook() {
		setForeground(new Color(75, 0, 130));
		setFont(new Font("UD デジタル 教科書体 NP-R", Font.PLAIN, 15));
		setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\孝司\\Pictures\\tempsnip.png"));
		setTitle("INTERFACE書籍買取");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 494, 334);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 228, 225));
		contentPane.setForeground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		//古本査定画面ラベル
		JLabel sateiLabel = new JLabel("古本査定画面");
		sateiLabel.setFont(new Font("UD デジタル 教科書体 NP-R", Font.PLAIN, 23));
		sateiLabel.setBounds(160, 10, 144, 37);
		contentPane.add(sateiLabel);

		//タイトルラベル
		JLabel title1label = new JLabel("タイトル");
		title1label.setFont(new Font("UD デジタル 教科書体 NP-R", Font.PLAIN, 17));
		title1label.setBounds(24, 58, 78, 21);
		contentPane.add(title1label);

		//タイトル入力フィールド
		title1TextField = new JTextField();
		title1TextField.setBackground(new Color(255, 250, 250));
		title1TextField.setBounds(121, 57, 318, 28);
		contentPane.add(title1TextField);
		title1TextField.setColumns(30);

		//著者名ラベル
		JLabel author1Label = new JLabel("著者名");
		author1Label.setFont(new Font("UD デジタル 教科書体 NP-R", Font.PLAIN, 17));
		author1Label.setBounds(24, 95, 65, 23);
		contentPane.add(author1Label);

		//著者名入力フィールド
		author1TextField = new JTextField();
		author1TextField.setBackground(new Color(255, 250, 250));
		author1TextField.setBounds(121, 95, 318, 28);
		contentPane.add(author1TextField);
		author1TextField.setColumns(30);

		//発行年月日ラベル
		JLabel date1Label = new JLabel("発行年月日");
		date1Label.setFont(new Font("UD デジタル 教科書体 NP-R", Font.PLAIN, 17));
		date1Label.setBounds(24, 133, 95, 28);
		contentPane.add(date1Label);

		//発行年月日選択

		JLabel exLabel = new JLabel("例：1997年9月14日→1997/09/14");
		exLabel.setFont(new Font("UD デジタル 教科書体 NP-R", Font.PLAIN, 11));
		exLabel.setBounds(243, 138, 206, 25);
		contentPane.add(exLabel);

		date1Field = new JTextField();
		date1Field.setFont(new Font("UD デジタル 教科書体 NP-R", Font.PLAIN, 14));
		date1Field.setBounds(121, 138, 110, 20);
		contentPane.add(date1Field);
		date1Field.setColumns(10);

		//ジャンル選択
		String[] arraygenre = new String[] { "Java", "MySQL", "C++" };
		@SuppressWarnings("unused")
		SpinnerListModel modelgenre1 = new SpinnerListModel(arraygenre);

		genre1Label = new JLabel("ジャンル");
		genre1Label.setFont(new Font("UD デジタル 教科書体 NP-R", Font.PLAIN, 17));
		genre1Label.setBounds(24, 166, 75, 30);
		contentPane.add(genre1Label);


		SpinnerListModel model = new SpinnerListModel(new String[] { "Java", "MySQL", "C++", "Other" });
		JSpinner g1Spinner = new JSpinner(model);
		g1Spinner.setFont(new Font("Mongolian Baiti", Font.PLAIN, 15));
		g1Spinner.setBackground(new Color(250, 240, 230));
		g1Spinner.setBounds(121, 173, 78, 20);
		contentPane.add(g1Spinner);

		JLabel price1Label = new JLabel("価格 \\");
		price1Label.setFont(new Font("UD デジタル 教科書体 NP-R", Font.PLAIN, 17));
		price1Label.setBounds(24, 203, 65, 30);
		contentPane.add(price1Label);



		SpinnerNumberModel modelnum = new SpinnerNumberModel(0, null, 10000000, 10);
		JSpinner p1Spinner = new JSpinner(modelnum);
//		double val = Double.parseDouble(String.valueOf(modelnum.getValue()));
		p1Spinner.setBackground(new Color(255, 245, 238));
		p1Spinner.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
		p1Spinner.setBounds(121, 210, 78, 20);
		contentPane.add(p1Spinner);

		//売却ボタン
		JButton sellButton = new JButton("売却");
		sellButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				//売却と同時にデータベースに登録する作業ここから
				Connection con = null;
				PreparedStatement pstmt = null;
				try {
					// 空白チェック
					int errorCode = Validate.chkNull(author1TextField.getText());
					if (errorCode != Validate.getSuccsessCode0()) {
						//エラーダイアログ表示　（文字列が入力されていません）
						JOptionPane.showMessageDialog(frame, Validate.getErrMsg(errorCode));
						return;
					}
					// 空白チェック
					errorCode = Validate.chkNull(title1TextField.getText());
					if (errorCode != Validate.getSuccsessCode0()) {
						//エラーダイアログ表示　（文字列が入力されていません）
						JOptionPane.showMessageDialog(frame, Validate.getErrMsg(errorCode));
						return;
					}
					// 空白チェック
					errorCode = Validate.chkNull(date1Field.getText());
					if (errorCode != Validate.getSuccsessCode0()) {
						//エラーダイアログ表示　（文字列が入力されていません）
						JOptionPane.showMessageDialog(frame, Validate.getErrMsg(errorCode));
						return;
					}

					// 入力文字数チェック
					errorCode = Validate.chkLength(author1TextField.getText(), MAX_LENGTH);
					if (errorCode != Validate.getSuccsessCode0()) {
						//エラーダイアログ表示　（文字が最大数50文字を超えている）
						JOptionPane.showMessageDialog(frame, Validate.getErrMsg(errorCode, MAX_LENGTH));
						return;
					}
					// 入力文字数チェック
					errorCode = Validate.chkLength(title1TextField.getText(), MAX_LENGTH);
					if (errorCode != Validate.getSuccsessCode0()) {
						//エラーダイアログ表示　（文字が最大数50文字を超えている）
						JOptionPane.showMessageDialog(frame, Validate.getErrMsg(errorCode, MAX_LENGTH));
						return;
					}
					// 入力文字数チェック日付
					errorCode = Validate.chkLengthdate(date1Field.getText(), MAX_LENGTH10);
					if (errorCode != Validate.getSuccsessCode0()) {
						//エラーダイアログ表示　（文字が最大数10文字を超えている）
						JOptionPane.showMessageDialog(frame, Validate.getErrMsg(errorCode, MAX_LENGTH10));
						return;
					}
					// 入力文字内容チェック
					errorCode = Validate.chkContents(author1TextField.getText());
					if (errorCode != Validate.getSuccsessCode0()) {
						//エラーダイアログ表示　（半角文字が含まれています）
						JOptionPane.showMessageDialog(frame, Validate.getErrMsg(errorCode));
						return;
					}
					// 入力文字内容チェック
					errorCode = Validate.chkContents(title1TextField.getText());
					if (errorCode != Validate.getSuccsessCode0()) {
						//エラーダイアログ表示　（半角文字が含まれています）
						JOptionPane.showMessageDialog(frame, Validate.getErrMsg(errorCode));
						return;
					}
					 //入力文字内容チェック
					errorCode = Validate.chkContentsdate(date1Field.getText());
					if (errorCode != Validate.getSuccsessCode0()) {
						//エラーダイアログ表示　（半角数字と記号のみで入力してください。）
						JOptionPane.showMessageDialog(frame, Validate.getErrMsg(errorCode));
						return;
					}


					// データベースに接続
					con = Connect.getConnection();
					// オートコミット解除
					con.setAutoCommit(false);
					// SQL文作成
					String mySql = "insert into bookmng.TBL_BOOK (ID, TITLE,AUTHOR,DATE,GENRE,PRICE) select COALESCE(max(ID), 0) + 1,?,?,?,?,? from bookmng.TBL_BOOK";
					// ステートメントオブジェクトを作成
					pstmt = con.prepareStatement(mySql);

					pstmt.setString(1, title1TextField.getText());
					pstmt.setString(2, author1TextField.getText());
					pstmt.setString(3, date1Field.getText());
					pstmt.setString(4, (String) g1Spinner.getValue());
					pstmt.setInt(5, (int) p1Spinner.getValue());

					int num = pstmt.executeUpdate();
					System.out.println("結果：" + num + "\t");

					// コミット
					con.commit();

				} catch (Exception ex) {
					try {
						// ロールバック
						if (con != null) {
							con.rollback();
						}
					} catch (SQLException e1) {
						// 何もしない
					} finally {
						System.out.println("例外発生：" + ex.toString());
						JOptionPane.showMessageDialog(frame, "例外発生：" + ex.toString());
					}
				} finally {
					try {
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
				//売却画面に切り替え
				double val = Double.parseDouble(String.valueOf(modelnum.getValue()));
				String g1 = (String) model.getValue();
				String chkPrice = String.valueOf(Calculation.chkresultPrice(val, g1));
				Docsell ds = new Docsell(chkPrice);
				ds.setVisible(true);
//				resultTextfield.setText(RP);
				return;




			}
		});

		sellButton.setFont(new Font("UD デジタル 教科書体 NP-R", Font.PLAIN, 17));
		sellButton.setBackground(new Color(204, 153, 204));
		sellButton.setForeground(new Color(255, 255, 255));
		sellButton.setBounds(251, 251, 78, 34);
		contentPane.add(sellButton);

		//査定ボタン
		JButton chkpriceButton = new JButton("査定");
		chkpriceButton.setForeground(new Color(255, 255, 255));
		chkpriceButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
//				Calculation.chkresultPrice(val, g1);
				double val = Double.parseDouble(String.valueOf(modelnum.getValue()));
				String g1 = (String) model.getValue();
				String chkPrice = String.valueOf(Calculation.chkresultPrice(val, g1));
				Resultprice rp = new Resultprice(chkPrice);
				rp.setVisible(true);

//				resultTextfield.setText(RP);
				return;

			}
		});
		chkpriceButton.setFont(new Font("UD デジタル 教科書体 NP-R", Font.PLAIN, 17));
		chkpriceButton.setBackground(new Color(204, 153, 204));
		chkpriceButton.setBounds(133, 251, 78, 34);
		contentPane.add(chkpriceButton);

	}
}