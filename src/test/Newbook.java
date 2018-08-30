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

public class Newbook extends JFrame {

	private JPanel contentPane;
	private JTextField titleTextField;
	private JTextField authorTextField;
	private JLabel dateLabel;
	private JLabel genreLabel;
	private JFrame frame;
	private JTextField dateField;
	private final int MAX_LENGTH = 50;
	private final int MAX_LENGTH10 = 10;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Newbook frame = new Newbook();
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
	public Newbook() {
		setForeground(new Color(75, 0, 130));
		setFont(new Font("UD デジタル 教科書体 NP-R", Font.PLAIN, 15));
		setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\孝司\\Pictures\\tempsnip.png"));
		setTitle("INTERFACE書籍登録 ");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 494, 334);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 228, 225));
		contentPane.setForeground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		//書籍新規登録ラベル
		JLabel newBookLabel = new JLabel("書籍新規登録");
		newBookLabel.setFont(new Font("UD デジタル 教科書体 NP-R", Font.PLAIN, 23));
		newBookLabel.setBounds(160, 10, 144, 37);
		contentPane.add(newBookLabel);

		//タイトルラベル
		JLabel titleLabel = new JLabel("タイトル");
		titleLabel.setFont(new Font("UD デジタル 教科書体 NP-R", Font.PLAIN, 17));
		titleLabel.setBounds(36, 71, 78, 21);
		contentPane.add(titleLabel);

		//タイトル入力フィールド
		titleTextField = new JTextField();
		titleTextField.setBackground(new Color(255, 250, 250));
		titleTextField.setBounds(136, 70, 318, 28);
		contentPane.add(titleTextField);
		titleTextField.setColumns(30);

		//著者名ラベル
		JLabel authorLabel = new JLabel("著者名");
		authorLabel.setFont(new Font("UD デジタル 教科書体 NP-R", Font.PLAIN, 17));
		authorLabel.setBounds(36, 108, 65, 23);
		contentPane.add(authorLabel);

		//著者名入力フィールド
		authorTextField = new JTextField();
		authorTextField.setBackground(new Color(255, 250, 250));
		authorTextField.setBounds(136, 108, 318, 28);
		contentPane.add(authorTextField);
		authorTextField.setColumns(30);

		//発行年月日選択

		JLabel exLabel = new JLabel("例：1997年9月14日→1997/09/14");
		exLabel.setFont(new Font("UD デジタル 教科書体 NP-R", Font.PLAIN, 11));
		exLabel.setBounds(258, 143, 206, 25);
		contentPane.add(exLabel);

		dateField = new JTextField();
		dateField.setFont(new Font("UD デジタル 教科書体 NP-R", Font.PLAIN, 14));
		dateField.setBounds(136, 146, 110, 20);
		contentPane.add(dateField);
		dateField.setColumns(10);

		//発行年ラベル
		dateLabel = new JLabel("発行年月日");
		dateLabel.setFont(new Font("UD デジタル 教科書体 NP-R", Font.PLAIN, 17));
		dateLabel.setBounds(36, 143, 85, 28);
		contentPane.add(dateLabel);

		//ジャンル選択画面

		String[] array = new String[] { "Java", "MySQL", "C++" };
		@SuppressWarnings("unused")
		SpinnerListModel modelgenre = new SpinnerListModel(array);

		genreLabel = new JLabel("ジャンル");
		genreLabel.setFont(new Font("UD デジタル 教科書体 NP-R", Font.PLAIN, 17));
		genreLabel.setBounds(36, 175, 75, 30);
		contentPane.add(genreLabel);
		JSpinner genreSpinner = new JSpinner(new SpinnerListModel(new String[] { "Java", "MySQL", "C++", "Other" }));
		genreSpinner.setFont(new Font("Mongolian Baiti", Font.PLAIN, 15));
		genreSpinner.setBackground(new Color(250, 240, 230));
		genreSpinner.setBounds(136, 182, 78, 20);
		contentPane.add(genreSpinner);

		//価格ラベル
		JLabel priceLabel = new JLabel("価格 \\");
		priceLabel.setFont(new Font("UD デジタル 教科書体 NP-R", Font.PLAIN, 17));
		priceLabel.setBounds(36, 208, 65, 30);
		contentPane.add(priceLabel);

		//価格選択画面
		JSpinner priceSpinner = new JSpinner();
		priceSpinner.setBackground(new Color(255, 245, 238));
		priceSpinner.setModel(new SpinnerNumberModel(0, null, 1000000, 10));
		priceSpinner.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
		priceSpinner.setBounds(136, 216, 78, 20);
		contentPane.add(priceSpinner);

		//登録ボタン
		JButton addButton = new JButton("登録");
		addButton.setForeground(new Color(255, 255, 255));
		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				Connection con = null;
				PreparedStatement pstmt = null;
				try {

					// 空白チェック
					int errorCode = Validate.chkNull(authorTextField.getText());
					if (errorCode != Validate.getSuccsessCode0()) {
						//エラーダイアログ表示　（文字列が入力されていません）
						JOptionPane.showMessageDialog(frame, Validate.getErrMsg(errorCode));
						return;
					}

					// 空白チェック
					errorCode = Validate.chkNull(titleTextField.getText());
					if (errorCode != Validate.getSuccsessCode0()) {
						//エラーダイアログ表示　（文字列が入力されていません）
						JOptionPane.showMessageDialog(frame, Validate.getErrMsg(errorCode));
						return;
					}
					// 空白チェック
					errorCode = Validate.chkNull(dateField.getText());
					if (errorCode != Validate.getSuccsessCode0()) {
						//エラーダイアログ表示　（文字列が入力されていません）
						JOptionPane.showMessageDialog(frame, Validate.getErrMsg(errorCode));
						return;
					}
					// 入力文字数チェック
					errorCode = Validate.chkLength(titleTextField.getText(), MAX_LENGTH);
					if (errorCode != Validate.getSuccsessCode0()) {
						//エラーダイアログ表示　（文字が最大数50文字を超えている）
						JOptionPane.showMessageDialog(frame, Validate.getErrMsg(errorCode, MAX_LENGTH));
						return;
					}
					// 入力文字数チェック
					errorCode = Validate.chkLength(authorTextField.getText(), MAX_LENGTH);
					if (errorCode != Validate.getSuccsessCode0()) {
						//エラーダイアログ表示　（文字が最大数50文字を超えている）
						JOptionPane.showMessageDialog(frame, Validate.getErrMsg(errorCode, MAX_LENGTH));
						return;
					}
					// 入力文字数チェック
					errorCode = Validate.chkLengthdate(dateField.getText(), MAX_LENGTH10);
					if (errorCode != Validate.getSuccsessCode0()) {
						//エラーダイアログ表示　（文字が最大数10文字を超えている）
						JOptionPane.showMessageDialog(frame, Validate.getErrMsg(errorCode, MAX_LENGTH10));
						return;
					}
					// 入力文字内容チェック
					errorCode = Validate.chkContents(titleTextField.getText());
					if (errorCode != Validate.getSuccsessCode0()) {
						//エラーダイアログ表示　（半角文字が含まれています）
						JOptionPane.showMessageDialog(frame, Validate.getErrMsg(errorCode));
						return;
					}
					// 入力文字内容チェック
					errorCode = Validate.chkContents(authorTextField.getText());
					if (errorCode != Validate.getSuccsessCode0()) {
						//エラーダイアログ表示　（半角文字が含まれています）
						JOptionPane.showMessageDialog(frame, Validate.getErrMsg(errorCode));
						return;
					}
					// 入力文字内容チェック
					errorCode = Validate.chkContentsdate(dateField.getText());
					if (errorCode != Validate.getSuccsessCode0()) {
						//エラーダイアログ表示　（半角文字が含まれています）
						JOptionPane.showMessageDialog(frame, Validate.getErrMsg(errorCode));
						return;
					}

					// データベースに接続
					con = Connect.getConnection();
					// オートコミット解除
					con.setAutoCommit(false);
					// SQL文作成
					String mySql = "insert into bookmng.TBL_BOOK (ID, TITLE,AUTHOR,DATE,GENRE,PRICE) select COALESCE(max(ID), 0) + 1,?,?,?,?,? from bookmng.TBL_BOOK order by ID";
					// ステートメントオブジェクトを作成
					pstmt = con.prepareStatement(mySql);

					pstmt.setString(1, titleTextField.getText());
					pstmt.setString(2, authorTextField.getText());
					pstmt.setString(3, dateField.getText());
					pstmt.setString(4, (String) genreSpinner.getValue());
					pstmt.setInt(5, (int) priceSpinner.getValue());

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

			}
		});

		addButton.setFont(new Font("UD デジタル 教科書体 NP-R", Font.PLAIN, 17));
		addButton.setBackground(new Color(204, 153, 204));
		addButton.setBounds(198, 251, 78, 34);
		contentPane.add(addButton);

	}
}
