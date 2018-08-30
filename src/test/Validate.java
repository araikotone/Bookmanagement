package test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Validate {

	//エラーコード数字を代入
	private static final int SUCCSESS_CODE_0 = 0;
	private static final int ERROR_CODE_1000 = 1000;
	private static final int ERROR_CODE_1010 = 1010;
	private static final int ERROR_CODE_1020 = 1020;
	private static final int ERROR_CODE_1030 = 1030;
	private static final int ERROR_CODE_1040 = 1040;
	private static final int ERROR_CODE_1050 = 1050;
	private static final int ERROR_CODE_1060 = 1060;
	private static final int ERROR_CODE_1070 = 1070;
	private static final int ERROR_CODE_1080 = 1080;

	private static final String ERROR_MESSAGE_1000 = "入力フィールドに文字列が入力されていません。すべての項目を入力してください。";
	private static final String ERROR_MESSAGE_1010 = "入力フィールドに入力された文字数が%sを超えています。";
	private static final String ERROR_MESSAGE_1020 = "入力フィールドに半角カナが含まれています。";
	private static final String ERROR_MESSAGE_1030 = "更新または削除対象のデータが存在しません。";
	private static final String ERROR_MESSAGE_1040 = "表示対象のデータが存在しません。";
	private static final String ERROR_MESSAGE_1050 = "発行年月日は半角数字と記号のみで入力してください。";
	private static final String ERROR_MESSAGE_1060 = "発行年月日は半角数字と「/」記号合わせて%s文字で入力してください。";
	private static final String ERROR_MESSAGE_1070 = "ジャンルは選択項目にあるものと同じように入力してください。";
	private static final String ERROR_MESSAGE_1080 = "定価は数字のみで入力してください";

	//％ｓは戻り値をstring formatで置換
	/**
	* @return SUCCSESS_CODE_0
	*/
	public static int getSuccsessCode0() {
		return SUCCSESS_CODE_0;
	}

	/**
	* @return ERROR_CODE_1000
	*/
	public static int getErrorCode1000() {
		return ERROR_CODE_1000;
	}

	/**
	* @return ERROR_CODE_1010
	*/
	public static int getErrorCode1010() {
		return ERROR_CODE_1010;
	}

	/**
	* @return ERROR_CODE_1020
	*/
	public static int getErrorCode1020() {
		return ERROR_CODE_1020;
	}

	/**
	* @return ERROR_CODE_1030
	*/
	public static int getErrorCode1030() {
		return ERROR_CODE_1030;
	}

	/**
	* @return ERROR_CODE_1040
	*/
	public static int getErrorCode1040() {
		return ERROR_CODE_1040;
	}

	/**
	* @return ERROR_CODE_1050
	*/
	public static int getErrorCode1050() {
		return ERROR_CODE_1050;
	}

	/**
	* @return ERROR_CODE_1060
	*/
	public static int getErrorCode1060() {
		return ERROR_CODE_1060;
	}

	/**
	* @return ERROR_CODE_1070
	*/
	public static int getErrorCode1070() {
		return ERROR_CODE_1070;
	}
	/**
	* @return ERROR_CODE_1080
	*/
	public static int getErrorCode1080() {
		return ERROR_CODE_1080;
	}


	/**
	* エラーメッセージ取得
	* @param errorCode エラーコード
	* @return エラーメッセージ
	*/
	public static String getErrMsg(int errorCode) {
		switch (errorCode) {
		case ERROR_CODE_1000:
			return ERROR_MESSAGE_1000;
		case ERROR_CODE_1010:
			return ERROR_MESSAGE_1010;
		case ERROR_CODE_1020:
			return ERROR_MESSAGE_1020;
		case ERROR_CODE_1030:
			return ERROR_MESSAGE_1030;
		case ERROR_CODE_1040:
			return ERROR_MESSAGE_1040;
		case ERROR_CODE_1050:
			return ERROR_MESSAGE_1050;
		case ERROR_CODE_1060:
			return ERROR_MESSAGE_1060;
		case ERROR_CODE_1070:
			return ERROR_MESSAGE_1070;
		case ERROR_CODE_1080:
			return ERROR_MESSAGE_1080;
		default:
			return "";
		}
	}

	/**
	* エラーメッセージ取得（メッセージ部分置換）
	* @param errorCode エラーコード
	* @param replaceVal 置き換え文字（int型）
	* @return エラーメッセージ
	*/
	public static String getErrMsg(int errorCode, int replaceVal) {
		return String.format(getErrMsg(errorCode), String.valueOf(replaceVal));
	}

	/**
	* 空白チェック
	* @param str チェック文字列
	* @return 判定結果
	*/
	public static int chkNull(String str) {
		if (str == null
				|| str.length() == 0
				|| str.trim().length() == 0) {
			return ERROR_CODE_1000;
		}
		return SUCCSESS_CODE_0;
	}

	/**
	* 入力文字数チェック
	* @param str チェック文字列
	* @param maxLen チェック桁数
	* @return 判定結果
	*/
	public static int chkLength(String str, int maxLen) {
		if (str.length() > maxLen) {
			return ERROR_CODE_1010;
		}
		return SUCCSESS_CODE_0;
	}

	/**
	* 入力文字内容チェック
	* @param str チェック文字列
	* @return 判定結果
	*/
	public static int chkContents(String str) {
		char[] chars = str.toCharArray();
		for (int i = 0; i < chars.length; i++) {
			char c = chars[i];
			if ((c >= '\uff61' && c <= '\uff9f') // 半角カナのunicode
			) {
				return ERROR_CODE_1020;
			}
		}
		return SUCCSESS_CODE_0;
	}

	/**
	* 更新データ存在チェック
	* @param con コネクション
	* @param str チェック文字列
	* @return 判定結果
	* @throws Exception
	*/
	public static int chkExistsUpdData(Connection con, String str) throws Exception {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			//SQL文作成
			String mySql = "select count(*) as cnt from bookmng.TBL_BOOK where id = ?;";
			//ステートメントオブジェクトを作成
			pstmt = con.prepareStatement(mySql);
			// 検索条件
			pstmt.setInt(1, Integer.parseInt(str));
			//検索するSQL実行
			rs = pstmt.executeQuery();

			int count = 0; // 検索結果（件数）
			//結果セットからデータを取り出す next()で次の行に移動
			while (rs.next()) {
				count = rs.getInt("cnt");
			}

			// 検索結果件数チェック
			if (count == 0) {
				return ERROR_CODE_1030;
			}

		} catch (Exception ex) {
			System.out.println("例外発生：" + ex);
			ex.printStackTrace();
			throw ex;
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
			} catch (SQLException se) {
				// 何もしない
			}
		}
		return SUCCSESS_CODE_0;
	}

	/**
	* 表示データ存在チェック
	* @param con コネクション
	* @return 判定結果
	* @throws Exception
	*/
	public static int chkExistsData(Connection con) throws Exception {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			//SQL文作成
			String mySql = "select count(*) as cnt from bookmng.TBL_BOOK;";
			//ステートメントオブジェクトを作成
			pstmt = con.prepareStatement(mySql);
			//検索するSQL実行
			rs = pstmt.executeQuery();

			int count = 0; // 検索結果（件数）
			//結果セットからデータを取り出す next()で次の行に移動
			while (rs.next()) {
				count = rs.getInt("cnt");
			}

			// 検索結果件数チェック
			if (count == 0) {
				return ERROR_CODE_1040;
			}

		} catch (Exception ex) {
			System.out.println("例外発生：" + ex);
			ex.printStackTrace();
			throw ex;
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
			} catch (SQLException se) {
				// 何もしない
			}
		}
		return SUCCSESS_CODE_0;
	}

	/**
	* 入力文字内容チェック日付
	* @param str チェック文字列
	* @return 判定結果
	*/
	public static int chkContentsdate(String str) {
		char[] chars = str.toCharArray();
		for (int i = 0; i < chars.length; i++) {
			char c = chars[i];
			if ((c <= '\u003a') || // 半角数字のunicode
					(c == '\u002f') // /記号

			) {
				return SUCCSESS_CODE_0;

			}
		}
		return ERROR_CODE_1050;
	}

	/**
	* 入力文字数チェック日付
	* @param str チェック文字列
	* @param maxLen10 チェック桁数
	* @return 判定結果
	*/
	public static int chkLengthdate(String str, int maxLen10) {
		if (str.length() != maxLen10) {
			return ERROR_CODE_1060;
		}
		return SUCCSESS_CODE_0;
	}

	/**
	* 入力文字内容チェックジャンル
	* @param str チェック文字列
	* @return 判定結果
	*/
	public static int chkContentsgenre(String str) {
			if("Java".equals(str)){
				return SUCCSESS_CODE_0;
			}
			else if("MySQL".equals(str)){
				return SUCCSESS_CODE_0;
			}
			else if ("C++".equals(str)){
				return SUCCSESS_CODE_0;
			}
			else if ("Other".equals(str)){
				return SUCCSESS_CODE_0;
			}
			else {
				return ERROR_CODE_1070;
			}
	}
	/**
	* 入力文字内容チェック定価
	* @param str チェック文字列
	* @return 判定結果
	*/
	public static int chkContentsprice(String str) {
		char[] chars = str.toCharArray();
		for (int i = 0; i < chars.length; i++) {
			char c = chars[i];
			if ((c <= '\u003a')

			) {
				return SUCCSESS_CODE_0;

			}
		}
		return ERROR_CODE_1080;
	}


}