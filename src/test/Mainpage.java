package test;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.BevelBorder;
import javax.swing.table.DefaultTableModel;

public class Mainpage {

	JFrame frmInterface;
	private JTable table;
	private JTextField TextFieldNaiyou;
	private JScrollPane scrollPane = null;
	Newbook nb = new Newbook();

	private final int MAX_LENGTH =50;
	private final int MAX_LENGTH10 =10;
	private String[] columnNames = { "ID", "タイトル", "著者名", "発行年月日", "ジャンル", "定価（円）" };

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Mainpage window = new Mainpage();
					window.frmInterface.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Mainpage() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		frmInterface = new JFrame();
		frmInterface.setForeground(new Color(255, 102, 153));
		frmInterface.getContentPane().setFont(new Font("UD デジタル 教科書体 NP-R", Font.PLAIN, 15));
		frmInterface.setBackground(new Color(199, 21, 133));
		frmInterface.setFont(new Font("UD デジタル 教科書体 NP-R", Font.PLAIN, 13));
		frmInterface.getContentPane().setBackground(new Color(255, 228, 225));
		frmInterface.getContentPane().setForeground(new Color(128, 0, 128));
		frmInterface.setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\孝司\\Pictures\\tempsnip.png"));
		frmInterface.setTitle("INTERFACE 書籍検索画面");
		frmInterface.setBounds(100, 100, 927, 635);
		frmInterface.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmInterface.getContentPane().setLayout(null);

		//社内書籍検索
		JLabel companybookLabel = new JLabel("社内書籍検索");
		companybookLabel.setFont(new Font("UD デジタル 教科書体 NP-R", Font.PLAIN, 26));
		companybookLabel.setBounds(462, 29, 167, 39);
		frmInterface.getContentPane().add(companybookLabel);

		//インターフェイスロゴ
		JLabel logoLabel = new JLabel("");
		logoLabel.setIcon(new ImageIcon("C:\\workspacekotone\\test\\Saved Pictures\\IF_Logo_200.png"));
		logoLabel.setBounds(244, 13, 223, 52);
		frmInterface.getContentPane().add(logoLabel);

		//更新

		JButton updateButton = new JButton("UPDATE");
		updateButton.setForeground(new Color(0, 0, 0));
		updateButton.setBackground(new Color(255, 153, 153));
		updateButton.setFont(new Font("UD デジタル 教科書体 NP-R", Font.PLAIN, 15));
		updateButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Connection con = null;
				PreparedStatement pstmt = null;
				try {


					// データベースに接続
					con = Connect.getConnection();
					// オートコミット解除
					con.setAutoCommit(false);
					// SQL文作成
					String mySql = "update bookmng.TBL_BOOK SET title = ?,author=?,date =?,genre=?,price=? where id = ?";

					for (int i = 0; i < table.getRowCount(); i++) {
						// IDをstring型で返す
						String id = String.valueOf(table.getValueAt(i, 0));
						// タイトルをstring型で返す
						String title = String.valueOf(table.getValueAt(i, 1));
						//著者名をstring型で返す
						String author = String.valueOf(table.getValueAt(i, 2));
						// 発行年月日をstring型で返す
						String date = String.valueOf(table.getValueAt(i, 3));
						// ジャンルをstring型で返す
						String genre = String.valueOf(table.getValueAt(i, 4));
						// 定価をstring型で返す
						String price = String.valueOf(table.getValueAt(i, 5));
						System.out.println("id：" + id + "\t");
						System.out.println("title：" + title + "\t");
						System.out.println("author：" + author + "\t");
						System.out.println("date：" + date + "\t");
						System.out.println("genre：" + genre + "\t");
						System.out.println("price：" + price + "\t");
						// 更新データ存在チェック
						int errorCode = Validate.chkExistsUpdData(con, id);
						if (errorCode != Validate.getSuccsessCode0()) {
							//エラーダイアログ表示（更新または削除対象のデータが存在しません）
							JOptionPane.showMessageDialog(frmInterface, Validate.getErrMsg(errorCode));
							return;
						}

						// 空白チェック
						errorCode = Validate.chkNull(author);
						if (errorCode != Validate.getSuccsessCode0()) {
							//エラーダイアログ表示　（文字列が入力されていません）
							JOptionPane.showMessageDialog(frmInterface, Validate.getErrMsg(errorCode));
							return;
						}
						// 空白チェック
						errorCode = Validate.chkNull(title);
						if (errorCode != Validate.getSuccsessCode0()) {
							//エラーダイアログ表示　（文字列が入力されていません）
							JOptionPane.showMessageDialog(frmInterface, Validate.getErrMsg(errorCode));
							return;
						}
						// 空白チェック
						errorCode = Validate.chkNull(date);
						if (errorCode != Validate.getSuccsessCode0()) {
							//エラーダイアログ表示　（文字列が入力されていません）
							JOptionPane.showMessageDialog(frmInterface, Validate.getErrMsg(errorCode));
							return;
						}
						// 空白チェック
						errorCode = Validate.chkNull(price);
						if (errorCode != Validate.getSuccsessCode0()) {
							//エラーダイアログ表示　（文字列が入力されていません）
							JOptionPane.showMessageDialog(frmInterface, Validate.getErrMsg(errorCode));
							return;
						}
						// 空白チェック
						errorCode = Validate.chkNull(genre);
						if (errorCode != Validate.getSuccsessCode0()) {
							//エラーダイアログ表示　（文字列が入力されていません）
							JOptionPane.showMessageDialog(frmInterface, Validate.getErrMsg(errorCode));
							return;
						}


						// 入力文字数チェック
						errorCode = Validate.chkLength(author, MAX_LENGTH);
						if (errorCode != Validate.getSuccsessCode0()) {
							//エラーダイアログ表示　（文字が最大数50文字を超えている）
							JOptionPane.showMessageDialog(frmInterface, Validate.getErrMsg(errorCode, MAX_LENGTH));
							return;
						}
						// 入力文字数チェック
						errorCode = Validate.chkLength(title, MAX_LENGTH);
						if (errorCode != Validate.getSuccsessCode0()) {
							//エラーダイアログ表示　（文字が最大数50文字を超えている）
							JOptionPane.showMessageDialog(frmInterface, Validate.getErrMsg(errorCode, MAX_LENGTH));
							return;
						}
						// 入力文字数チェック日付
						errorCode = Validate.chkLengthdate(date, MAX_LENGTH10);
						if (errorCode != Validate.getSuccsessCode0()) {
							//エラーダイアログ表示　（文字が最大数10文字を超えている）
							JOptionPane.showMessageDialog(frmInterface, Validate.getErrMsg(errorCode, MAX_LENGTH10));
							return;
						}
						// 入力文字内容チェック
						errorCode = Validate.chkContents(author);
						if (errorCode != Validate.getSuccsessCode0()) {
							//エラーダイアログ表示　（半角文字が含まれています）
							JOptionPane.showMessageDialog(frmInterface, Validate.getErrMsg(errorCode));
							return;
						}
						// 入力文字内容チェック
						errorCode = Validate.chkContents(title);
						if (errorCode != Validate.getSuccsessCode0()) {
							//エラーダイアログ表示　（半角文字が含まれています）
							JOptionPane.showMessageDialog(frmInterface, Validate.getErrMsg(errorCode));
							return;
						}
						//入力文字内容チェックジャンル
						errorCode = Validate.chkContentsgenre(genre);
						if (errorCode != Validate.getSuccsessCode0()) {
							//エラーダイアログ表示　（ジャンル選択内の項目と同じように入力してください）
							JOptionPane.showMessageDialog(frmInterface, Validate.getErrMsg(errorCode));
							return;
						}
						// 入力文字内容チェック定価
						errorCode = Validate.chkContentsprice(price);
						if (errorCode != Validate.getSuccsessCode0()) {
							//エラーダイアログ表示　（数字のみで入力してください）
							JOptionPane.showMessageDialog(frmInterface, Validate.getErrMsg(errorCode));
							return;
						}

						//入力文字内容チェック日付
						errorCode = Validate.chkContentsdate(date);
						if (errorCode != Validate.getSuccsessCode0()) {
							//エラーダイアログ表示　（半角数字と記号のみで入力してください。）
							JOptionPane.showMessageDialog(frmInterface, Validate.getErrMsg(errorCode));
							return;
						}

						//ステートメントオブジェクトを作成
						pstmt = con.prepareStatement(mySql);
						// 条件値をセット
						pstmt.setString(1, title);
						pstmt.setString(2, author);
						pstmt.setString(3, date);
						pstmt.setString(4, genre);
						pstmt.setString(5, price);
						//String を int に変換

						pstmt.setInt(6, Integer.parseInt(id));
						// SQL実行
						int num = pstmt.executeUpdate();
						System.out.println("結果：" + num + "\t");
					}
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
						JOptionPane.showMessageDialog(frmInterface, "例外発生：" + ex.toString());
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
				// 表示ボタン押下
			}
		});

		//全表示
		JButton allView = new JButton("ALL");
		allView.setBackground(new Color(255, 153, 153));
		allView.setFont(new Font("UD デジタル 教科書体 NP-R", Font.PLAIN, 15));
		allView.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				Connection con = null;
				PreparedStatement pstmt = null;
				ResultSet rs = null;

				try {
					//データベースに接続
					con = Connect.getConnection();

					//SQL文作成
					String mySql = "select ID,TITLE,AUTHOR,DATE,GENRE,PRICE from bookmng.TBL_BOOK";
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
					frmInterface.getContentPane().add(scrollPane);
					table.setModel(tableModel);
					table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
					table.setColumnSelectionAllowed(true);
					table.setRowSelectionAllowed(true);
					table.getColumnModel().getColumn(0).setPreferredWidth(46);
					table.getColumnModel().getColumn(1).setPreferredWidth(175);
					table.getColumnModel().getColumn(1).setMinWidth(150);
					table.getColumnModel().getColumn(2).setPreferredWidth(150);
					table.getColumnModel().getColumn(2).setMinWidth(1);
					table.getColumnModel().getColumn(5).setPreferredWidth(60);
					scrollPane.setViewportView(table);

					scrollPane.repaint();

				} catch (Exception ex) {
					System.out.println("例外発生：" + ex.toString());
					JOptionPane.showMessageDialog(frmInterface, "例外発生：" + ex.toString());
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

		allView.setBounds(754, 205, 98, 33);
		frmInterface.getContentPane().add(allView);

		//入力内容ラベル
		JLabel Labelnaiyou = new JLabel("入力内容");
		Labelnaiyou.setFont(new Font("UD デジタル 教科書体 NP-R", Font.PLAIN, 14));
		Labelnaiyou.setBounds(65, 105, 59, 21);
		frmInterface.getContentPane().add(Labelnaiyou);

		//内容テキストフィールド
		TextFieldNaiyou = new JTextField();
		TextFieldNaiyou.setBackground(new Color(255, 250, 250));
		TextFieldNaiyou.setBounds(65, 136, 340, 26);
		frmInterface.getContentPane().add(TextFieldNaiyou);
		TextFieldNaiyou.setColumns(10);
		updateButton.setBounds(65, 182, 98, 45);
		frmInterface.getContentPane().add(updateButton);

		//削除ボタン
		JButton deleteButton = new JButton("DELETE");
		deleteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				int row = table.getSelectedRow();
				System.out.println("getSelectedRow＝" + row + "\t");
				Connection con = null;
				PreparedStatement pstmt = null;
				try {
					// データベースに接続
					con = Connect.getConnection();
					// オートコミット解除（トランザクションをコミット後にデータ反映）
					con.setAutoCommit(false);

					 //更新データ存在チェック
					int errorCode = Validate.chkExistsUpdData(con,String.valueOf(table.getValueAt(row, 0)));
					if (errorCode != Validate.getSuccsessCode0()) {
						//エラーダイアログ表示（更新または削除対象のデータが存在しません）
						JOptionPane.showMessageDialog(frmInterface, Validate.getErrMsg(errorCode));
						return;
					}
					// SQL文作成
					String mySql = "DELETE FROM bookmng.TBL_BOOK where id = ?";
					//ステートメントオブジェクトを作成
					pstmt = con.prepareStatement(mySql);
					// 条件値をセット
					int val = Integer.parseInt(String.valueOf(table.getValueAt(row, 0)));
					System.out.println("Cell＝" + val + "\t");
					pstmt.setInt(1, val);
					// SQL実行
					int updateCount = pstmt.executeUpdate();
					System.out.println("Delete: " + updateCount);

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
						JOptionPane.showMessageDialog(frmInterface, "例外発生：" + ex.toString());
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
				allView.doClick();
			}
		});
		deleteButton.setBackground(new Color(255, 153, 153));
		deleteButton.setFont(new Font("UD デジタル 教科書体 NP-R", Font.PLAIN, 15));
		deleteButton.setBounds(185, 182, 98, 45);
		frmInterface.getContentPane().add(deleteButton);

		//追加にジャンプするボタン
		JButton jumptoAdd = new JButton("ADD");
		jumptoAdd.setBackground(new Color(255, 153, 153));
		jumptoAdd.setFont(new Font("UD デジタル 教科書体 NP-R", Font.PLAIN, 15));
		jumptoAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Mainpage window = new Mainpage();
				window.frmInterface.setVisible(false);
				nb.setVisible(true);

			}
		});
		jumptoAdd.setBounds(307, 182, 98, 45);
		frmInterface.getContentPane().add(jumptoAdd);

		//キーワード検索ラベル
		JLabel Labelkeyword = new JLabel("ジャンルで検索");
		Labelkeyword.setFont(new Font("UD デジタル 教科書体 NP-R", Font.PLAIN, 14));
		Labelkeyword.setBounds(450, 113, 167, 17);
		frmInterface.getContentPane().add(Labelkeyword);

		//ラジオボタンjava
		JRadioButton JavaRBtn = new JRadioButton("Java");
		JavaRBtn.setName("Java");
		JavaRBtn.setBackground(new Color(255, 228, 225));
		JavaRBtn.setFont(new Font("UD デジタル 教科書体 NP-R", Font.PLAIN, 15));
		JavaRBtn.setBounds(450, 137, 59, 21);

		frmInterface.getContentPane().add(JavaRBtn);

		//ラジオボタンmysql
		JRadioButton MysqlRBtn = new JRadioButton("MySQL");
		MysqlRBtn.setName("MySQL");
		MysqlRBtn.setBackground(new Color(255, 228, 225));
		MysqlRBtn.setFont(new Font("UD デジタル 教科書体 NP-R", Font.PLAIN, 14));
		MysqlRBtn.setBounds(513, 137, 81, 21);
		frmInterface.getContentPane().add(MysqlRBtn);

		//ラジオボタンC++
		JRadioButton CRBtn = new JRadioButton("C++");
		CRBtn.setBackground(new Color(255, 228, 225));
		CRBtn.setName("C++");
		CRBtn.setFont(new Font("UD デジタル 教科書体 NP-R", Font.PLAIN, 15));
		CRBtn.setBounds(598, 137, 59, 21);
		frmInterface.getContentPane().add(CRBtn);

		//ラジオボタン　その他
		JRadioButton otherRBtn = new JRadioButton("Other");
		otherRBtn.setName("Other");
		otherRBtn.setBackground(new Color(255, 228, 225));
		otherRBtn.setFont(new Font("UD デジタル 教科書体 NP-R", Font.PLAIN, 14));
		otherRBtn.setBounds(661, 137, 69, 21);
		frmInterface.getContentPane().add(otherRBtn);


		//ボタングループを作成
		final ButtonGroup bg = new ButtonGroup();
		bg.add(JavaRBtn);
		bg.add(MysqlRBtn);
		bg.add(CRBtn);
		bg.add(otherRBtn);

		//検索ボタン

		JButton searchButton = new JButton("SEARCH");
		searchButton.setBackground(new Color(255, 153, 153));
		searchButton.setFont(new Font("Leelawadee UI Semilight", Font.PLAIN, 13));
		searchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				Connection con = null;
				PreparedStatement pstmt = null;
				ResultSet rs = null;

				// 選択されているラジオボタンから条件をセット
				if(JavaRBtn.isSelected()){
					// ジャンル名をテキストフィールドにセット
					String str = JavaRBtn.getName();
					TextFieldNaiyou.setText(str);

				} else if (MysqlRBtn.isSelected()) {
					String str = MysqlRBtn.getName();
					TextFieldNaiyou.setText(str);

				} else if (CRBtn.isSelected()) {
					String str = CRBtn.getName();
					TextFieldNaiyou.setText(str);


				} else if (otherRBtn.isSelected()) {
					String str = otherRBtn.getName();
					TextFieldNaiyou.setText(str);


				} else {
					JOptionPane.showMessageDialog(null,
							"ジャンルを一つ選択してください");
				}
				try {
					//データベースに接続
					con = Connect.getConnection();

					//SQL文作成
					String mySql = "select * from bookmng.TBL_BOOK where genre=?";
					//ステートメントオブジェクトを作成
					pstmt = con.prepareStatement(mySql);
					//条件値をセット
					pstmt.setString(1, TextFieldNaiyou.getText());

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
					frmInterface.getContentPane().add(scrollPane);
					table.setModel(tableModel);
					table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
					table.setColumnSelectionAllowed(true);
					table.setRowSelectionAllowed(true);
					table.getColumnModel().getColumn(0).setPreferredWidth(46);
					table.getColumnModel().getColumn(1).setPreferredWidth(175);
					table.getColumnModel().getColumn(1).setMinWidth(150);
					table.getColumnModel().getColumn(2).setPreferredWidth(150);
					table.getColumnModel().getColumn(2).setMinWidth(1);
					table.getColumnModel().getColumn(5).setPreferredWidth(60);
					scrollPane.setViewportView(table);

					scrollPane.repaint();

				} catch (Exception ex) {
					System.out.println("例外発生：" + ex.toString());
					JOptionPane.showMessageDialog(frmInterface, "例外発生：" + ex.toString());
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
		searchButton.setBounds(754, 129, 88, 33);
		frmInterface.getContentPane().add(searchButton);

		//表
		scrollPane = new JScrollPane();
		scrollPane.setToolTipText("");
		scrollPane.setBounds(50, 248, 802, 325);
		frmInterface.getContentPane().add(scrollPane);

		table = new JTable();
		table.setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(240, 128, 128), new Color(240, 128, 128),
				new Color(240, 128, 128), new Color(240, 128, 128)));
		table.setBackground(new Color(255, 250, 250));
		table.getTableHeader().setFont(new Font("UD デジタル 教科書体 NP-R", Font.PLAIN, 14));
		table.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, null, null, null, null},
				{null, null, null, null, null, null},
				{null, null, null, null, null, null},
				{null, null, null, null, null, null},
				{null, null, null, null, null, null},
				{null, null, null, null, null, null},
				{null, null, null, null, null, null},
				{null, null, null, null, null, null},
			},
			new String[] {
				"ID", "タイトル", "著者名", "発行年月日", "ジャンル", "定価（円）"
			}
		) {
			boolean[] columnEditables = new boolean[] {
				false, true, true, false, true, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		table.getColumnModel().getColumn(0).setPreferredWidth(46);
		table.getColumnModel().getColumn(1).setPreferredWidth(175);
		table.getColumnModel().getColumn(1).setMinWidth(150);
		table.getColumnModel().getColumn(2).setPreferredWidth(150);
		table.getColumnModel().getColumn(2).setMinWidth(1);
		table.getColumnModel().getColumn(5).setPreferredWidth(60);

		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setColumnSelectionAllowed(true);
		table.setRowSelectionAllowed(true);
		scrollPane.setViewportView(table);
	}

	public void setVisible(boolean b) {
		// TODO 自動生成されたメソッド・スタブ

	}

}
